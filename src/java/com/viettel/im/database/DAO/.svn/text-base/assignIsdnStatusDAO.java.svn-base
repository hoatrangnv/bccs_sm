package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.LogAssignIsdnStatusBean;
import com.viettel.im.client.form.AssignIsdnTypeForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.UserToken;
import com.viettel.security.util.StringEscapeUtils;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

/**
 *
 * @author Doan Thanh 8
 * thuc gan loai so moi
 *
 */
public class assignIsdnStatusDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(assignIsdnStatusDAO.class);
    //cac ten bien session hoac request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_DETAIL_ISDN_RANGE_PATH = "detailIsdnRangePath";
    private final String REQUEST_DETAIL_ISDN_RANGE_MESSAGE = "detailIsdnRangeMessage";
    private final String SESSION_LIST_STOCK_ISDN = "listStockIsdn";
    //cac hang so forward
    private String pageForward;
    private final String ASSIGN_ISDN_TYPE = "assignISDNStatus";
    private final String LIST_ASSIGN_ISDN_TYPE = "listAssignISDNStatus";
    private final Long STATUS_USE = 2L;
    private String errorMessage;
    //cac bien form
    private AssignIsdnTypeForm assignIsdnTypeForm = new AssignIsdnTypeForm();

    public AssignIsdnTypeForm getAssignIsdnTypeForm() {
        return assignIsdnTypeForm;
    }

    public void setAssignIsdnTypeForm(AssignIsdnTypeForm assignIsdnTypeForm) {
        this.assignIsdnTypeForm = assignIsdnTypeForm;
    }

    /**
     *
     * author   : tamdt1
     * date     : 23/11/2009
     * purpose  : chuan bi form gan loai so
     *
     */
    public String preparePage() throws Exception {
        log.info("begin method preparePage of AssignIsdnTypeDAO...");

        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            //xoa bien session
            req.getSession().setAttribute(SESSION_LIST_STOCK_ISDN, null);

            //kho mac dinh la kho cua user dang nhap
            this.assignIsdnTypeForm.setShopCode(userToken.getShopCode());
            this.assignIsdnTypeForm.setShopName(userToken.getShopName());

            pageForward = ASSIGN_ISDN_TYPE;
            log.info("end method preparePage of AssignIsdnTypeDAO");
            return pageForward;

        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("end method preparePage of AssignIsdnTypeDAO");
            throw ex;
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 23/11/2009
     * purpose  : tim kiem thong tin dai so de gan loai so moi
     *
     */
    public String searchIsdnRange() throws Exception {
        log.info("begin method searchIsdnRange of assignIsdnStatusDAO...");

        try {
            HttpServletRequest req = getRequest();

            if (!checkValidIsdnRange()) {
                pageForward = ASSIGN_ISDN_TYPE;
                log.info("end method searchIsdnRange of AssignIsdnTypeDAO");
                return pageForward;
            }

            Long stockTypeId = this.assignIsdnTypeForm.getStockTypeId();
            Long shopId = this.assignIsdnTypeForm.getShopId();
            String isdnType = this.assignIsdnTypeForm.getIsdnType();
            String strFromIsdn = this.assignIsdnTypeForm.getFromIsdn();
            String strToIsdn = this.assignIsdnTypeForm.getToIsdn();
            Long status = this.assignIsdnTypeForm.getStatus();
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            List listParameter = new ArrayList();

            //
            //cau lenh select ra cac khoang isdn theo min, max, isdn_type, status, owner_id
            StringBuffer strIsdnRangeQuery = new StringBuffer("");
            strIsdnRangeQuery.append("SELECT MIN(isdn) lb, MAX (isdn) ub, isdn_type, status, owner_id ");
            strIsdnRangeQuery.append("FROM (SELECT isdn isdn, ");
            strIsdnRangeQuery.append("          isdn - ROW_NUMBER () OVER (ORDER BY isdn) rn, ");
            strIsdnRangeQuery.append("          isdn_type, status, owner_id ");
            strIsdnRangeQuery.append("      FROM " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " ");
            strIsdnRangeQuery.append("      WHERE 1 = 1 ");
            strIsdnRangeQuery.append("          and to_number(isdn) >= ? ");
            listParameter.add(Long.valueOf(strFromIsdn.trim()));
            strIsdnRangeQuery.append("          and to_number(isdn) <= ? ");
            listParameter.add(Long.valueOf(strToIsdn.trim()));

            if (shopId != null && shopId.compareTo(0L) > 0) {
                strIsdnRangeQuery.append("      and owner_id = ? and owner_type = ? ");
                listParameter.add(shopId);
                listParameter.add(Constant.OWNER_TYPE_SHOP);
            }

            if (isdnType != null && !isdnType.trim().equals("")) {
                if (isdnType.equals("-1")) {
                    strIsdnRangeQuery.append("      and isdn_type is null ");
                } else {
                    strIsdnRangeQuery.append("      and isdn_type = ? ");
                    listParameter.add(isdnType.trim());
                }
            }

            if (status != null) {
                strIsdnRangeQuery.append("      and status = ? ");
                listParameter.add(status);
            }

            strIsdnRangeQuery.append("      ) ");
            strIsdnRangeQuery.append("GROUP BY rn, isdn_type, status, owner_id ");
            strIsdnRangeQuery.append("ORDER BY 1 ");

            //join bang shop voi cau lenh tren de tim ra danh sach cac khoang isdn + thong tin ve kho chua isdn
            //ham khoi tao: AssignIsdnTypeForm(Long assignIsdnTypeFormId, Long stockTypeId, String fromIsdn, String toIsdn, Long realQuantity, String isdnType, Long status, Long shopId, String shopCode, String shopName) {
            StringBuffer strSearchQuery = new StringBuffer("");
            strSearchQuery.append("SELECT rownum assignIsdnTypeFormId, " + stockTypeId + " stockTypeId, a.lb fromIsdn, a.ub toIsdn, (a.ub - a.lb + 1) realQuantity, a.isdn_type isdnType, a.status status, a.owner_id shopId, b.shop_code shopCode, b.name shopName ");
            strSearchQuery.append("FROM (").append(strIsdnRangeQuery).append(") a, shop b ");
            strSearchQuery.append("WHERE a.owner_id = b.shop_id ");

            Query searchQuery = getSession().createSQLQuery(strSearchQuery.toString()).addScalar("assignIsdnTypeFormId", Hibernate.LONG).addScalar("stockTypeId", Hibernate.LONG).addScalar("fromIsdn", Hibernate.STRING).addScalar("toIsdn", Hibernate.STRING).addScalar("realQuantity", Hibernate.LONG).addScalar("isdnType", Hibernate.STRING).addScalar("status", Hibernate.LONG).addScalar("shopId", Hibernate.LONG).addScalar("shopCode", Hibernate.STRING).addScalar("shopName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AssignIsdnTypeForm.class));
            for (int i = 0; i < listParameter.size(); i++) {
                searchQuery.setParameter(i, listParameter.get(i));
            }

            List<AssignIsdnTypeForm> listAssignIsdnTypeForm = searchQuery.list();
            req.getSession().setAttribute(SESSION_LIST_STOCK_ISDN, listAssignIsdnTypeForm);
            if (listAssignIsdnTypeForm != null && listAssignIsdnTypeForm.size() != 0) {
                req.setAttribute(REQUEST_MESSAGE, "Tìm kiếm được" + listAssignIsdnTypeForm.size() + " dải số thỏa mãn điều kiện");
            } else {
                req.setAttribute(REQUEST_MESSAGE, "Không tìm kiếm được 0 dải số nào thỏa mãn điều kiện");
            }


            pageForward = ASSIGN_ISDN_TYPE;
            log.info("end method searchIsdnRange of assignIsdnStatusDAO");
            return pageForward;

        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("end method searchIsdnRange of assignIsdnStatusDAO");
            throw ex;
        }
    }

    /**
     *
     * author   :tamdt1
     * date     : 19/09/2009
     * purpose  : kiem tra tinh hop le cua dai so truoc khi gan
     *
     */
    private boolean checkValidIsdnRange() {
        HttpServletRequest req = getRequest();

        try {
            String shopCode = this.assignIsdnTypeForm.getShopCode();
            Long stockTypeId = this.assignIsdnTypeForm.getStockTypeId();
            String strFromIsdn = this.assignIsdnTypeForm.getFromIsdn();
            String strToIsdn = this.assignIsdnTypeForm.getToIsdn();

            //kiem tra cac truong bat buoc
            if (stockTypeId == null
                    || strFromIsdn == null || strFromIsdn.trim().equals("")
                    || strToIsdn == null || strToIsdn.trim().equals("")) {

                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.requiredFieldsEmpty");
                return false;
            }

            //kiem tra su ton tai cua stock_type_id
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            if (strTableName == null || strTableName.equals("")) {
                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.stockTypeTableNotExist");
                return false;
            }

            //kiem tra shop co ton tai khong
            Long ownerId = 0L;
            if (shopCode != null && !shopCode.trim().equals("")) {
                String strShopQuery = "from Shop where lower(shopCode) = ? ";
                Query shopQuery = getSession().createQuery(strShopQuery);
                shopQuery.setParameter(0, shopCode.trim().toLowerCase());
                List<Shop> listShop = shopQuery.list();
                if (listShop == null || listShop.size() == 0) {
                    req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.shopNotExist");
                    return false;
                }
                ownerId = listShop.get(0).getShopId();
            } else {
                shopCode = "";
            }

            //kiem tra truong fromIsdn, toIsdn phai la so khong am
            Long fromIsdn = 0L;
            Long toIsdn = 0L;
            try {
                fromIsdn = Long.valueOf(strFromIsdn.trim());
                toIsdn = Long.valueOf(strToIsdn.trim());
            } catch (NumberFormatException ex) {
                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.isdnNegative");
                return false;
            }

            //kiem tra truong fromNumber khong duoc lon hon truong toNumber
            if (fromIsdn.compareTo(toIsdn) > 0) {
                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.invalidIsdn");
                return false;
            }

            //kiem tra so luong so 1 lan tao dai so ko duoc vuot qua so luong max (hien tai la 2trieu so/lan)
            int maxIsdnAssignType = -1;
            try {
                String strMaxIsdnAssignType = ResourceBundleUtils.getResource("MAX_ISDN_ASSIGN_TYPE");
                maxIsdnAssignType = Integer.parseInt(strMaxIsdnAssignType.toString());
            } catch (Exception ex) {
                ex.printStackTrace();
                maxIsdnAssignType = -1;
            }

            if ((toIsdn - fromIsdn + 1) > maxIsdnAssignType) {
                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.quantityOverMaximum");
                List listParam = new ArrayList();
                listParam.add(maxIsdnAssignType);
                req.setAttribute(REQUEST_MESSAGE_PARAM, listParam);
                return false;
            }

            //
            this.assignIsdnTypeForm.setShopId(ownerId);

            //trim cac truong can thiet
            this.assignIsdnTypeForm.setShopCode(shopCode.trim());
            this.assignIsdnTypeForm.setFromIsdn(strFromIsdn.trim());
            this.assignIsdnTypeForm.setToIsdn(strToIsdn.trim());

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Error function checkValidIsdnRange(): " + e.toString());
            return false;

        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach tat ca cac kho
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

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
        listParameter1.add(300L);

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

    /**
     *
     * author   : tamdt1
     * date     : 12/05/2009
     * purpose  : xem danh sach chi tiet cac serial cua 1 dai so
     *
     */
    public String detailIsdnRange() throws Exception {
//        log.debug("begin method detailIsdnRange of AssignIsdnTypeDAO");
//        HttpServletRequest req = getRequest();
//
//        String strAssignIsdnFormId = req.getParameter("assignIsdnFormId");
//        Long assignIsdnFormId = -1L;
//        try {
//            assignIsdnFormId = Long.valueOf(strAssignIsdnFormId);
//        } catch (NumberFormatException ex) {
//            ex.printStackTrace();
//            assignIsdnFormId = -1L;
//        }
//        //tim dai so thoa man dieu kien tim kiem
//        List<AssignISDNForm> listAssignISDNForms = (List<AssignISDNForm>) req.getSession().getAttribute(SESSION_LIST_STOCK_ISDN);
//        if (listAssignISDNForms == null) {
//            listAssignISDNForms = new ArrayList<AssignISDNForm>();
//        }
//
//        AssignISDNForm selectedAssignISDNForm = null;
//        for(int i = 0; i < listAssignISDNForms.size(); i++) {
//            if(listAssignISDNForms.get(i).getAssignIsdnFormId().equals(assignIsdnFormId)) {
//                selectedAssignISDNForm = listAssignISDNForms.get(i);
//                break;
//            }
//        }
//
//        if (selectedAssignISDNForm == null) {
//            pageForward = LIST_ASSIGN_ISDN;
//            log.debug("end method detailIsdnRange of AssignISDNDAO");
//            return pageForward;
//        }
//
//        //lay du lieu chi tiet, ghi ra file text
//        Long serviceType = Long.valueOf(selectedAssignISDNForm.getServiceType());
//        String strTableName = new BaseStockDAO().getTableNameByStockType(
//                serviceType, BaseStockDAO.NAME_TYPE_HIBERNATE);
//
//        String strQuery;
//        if (!selectedAssignISDNForm.getIsdnType().equals(ISDN_UNKNOWN_ISDN_TYPE_ID)) {
//            //
//            strQuery = "select isdn from " + strTableName + " " +
//                    "where to_number(isdn) >= ? and to_number(isdn) <= ? and " +
//                    "status = ? and ownerId = ? and isdnType = ? ";
//        } else {
//            //truong hop cac so chua xac dinh loai
//            strQuery = "select isdn from " + strTableName + " " +
//                    "where to_number(isdn) >= ? and to_number(isdn) <= ? and " +
//                    "status = ? and ownerId = ? and isdnType is null ";
//        }
//
//        Query query = getSession().createQuery(strQuery);
//        query.setParameter(0, Long.valueOf(selectedAssignISDNForm.getFromNumber()));
//        query.setParameter(1, Long.valueOf(selectedAssignISDNForm.getToNumber()));
//        query.setParameter(2, Long.valueOf(selectedAssignISDNForm.getStatus()));
//        query.setParameter(3, Long.valueOf(selectedAssignISDNForm.getShopId()));
//        if (!selectedAssignISDNForm.getIsdnType().equals(ISDN_UNKNOWN_ISDN_TYPE_ID)) {
//            query.setParameter(4, selectedAssignISDNForm.getIsdnType());
//        }
//
//        List listIsdn = query.list();
//        if (listIsdn != null && listIsdn.size() > 0) {
//            String strServiceType =  "";
//            if(serviceType.equals(Constant.STOCK_ISDN_MOBILE)) {
//                strServiceType = "mobile";
//            } else if(serviceType.equals(Constant.STOCK_ISDN_HOMEPHONE)) {
//                strServiceType = "homephone";
//            } else if(serviceType.equals(Constant.STOCK_ISDN_PSTN)) {
//                strServiceType = "pstn";
//            } else {
//                strServiceType = "unknown";
//            }
//
//            String strIsdnType = "";
//            if(selectedAssignISDNForm.getIsdnType().equals(ISDN_UNKNOWN_ISDN_TYPE_ID)) {
//                strIsdnType = "not classified";
//            }else if(selectedAssignISDNForm.getIsdnType().equals(Constant.ISDN_TYPE_PRE)) {
//                strIsdnType = "pre paid";
//            } else if(selectedAssignISDNForm.getIsdnType().equals(Constant.ISDN_TYPE_POST)) {
//                strIsdnType = "post paid";
//            } else if(selectedAssignISDNForm.getIsdnType().equals(Constant.ISDN_TYPE_SPEC)) {
//                strIsdnType = "special";
//            } else {
//
//            }
//
//            String strIsdnStatus = "";
//            if(selectedAssignISDNForm.getStatus().equals(Constant.STATUS_ISDN_NEW)) {
//                strIsdnStatus = "new isdn";
//            } else if(selectedAssignISDNForm.getStatus().equals(Constant.STATUS_ISDN_SUSPEND)) {
//                strIsdnStatus = "suspend isdn";
//            }
//
//            String DATE_FORMAT = "yyyyMMddHHmmss";
//            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
//            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
//            filePath += "assignIsdnType_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".txt";
//            String realPath = req.getSession().getServletContext().getRealPath(filePath);
//            PrintWriter output = null;
//            File fIsdnDetail = new File(realPath);
//            output = new PrintWriter(fIsdnDetail);
//            output.println("shopCode/ shopName/ serviceType/ isdn/ isdnType/ isdnStatus");
//            for (Object stockIsdn : listIsdn) {
//                output.println(selectedAssignISDNForm.getShopCode() + "\t" +
//                        selectedAssignISDNForm.getShopName() + "\t" +
//                        strServiceType + "\t" +
//                        stockIsdn.toString() + "\t" +
//                        strIsdnType + "\t" +
//                        strIsdnStatus);
//            }
//            output.flush();
//            output.close();
//
//            req.setAttribute(REQUEST_DETAIL_ISDN_RANGE_PATH, filePath);
//            req.setAttribute(REQUEST_DETAIL_ISDN_RANGE_MESSAGE, "assignIsdn.detailIsdnRangeMessage");
//
//            List lisMessageParam = new ArrayList();
//            lisMessageParam.add(selectedAssignISDNForm.getFromNumber());
//            lisMessageParam.add(selectedAssignISDNForm.getToNumber());
//            req.setAttribute(REQUEST_MESSAGE_PARAM, lisMessageParam);
//        }
//
//
//        //cmt, 11/06/2009
////        String strToNumber = req.getParameter("toNumber");
////        String strServiceType = req.getParameter("serviceType");
////        Long fromNumber = Long.valueOf(strFromNumber);
////        Long toNumber = Long.valueOf(strToNumber);
////        Long serviceType = Long.valueOf(strServiceType);
////
////        //lay du lieu chi tiet, ghi ra file text
////        if (serviceType.equals(Constant.STOCK_ISDN_MOBILE)) {
////            //neu dich vu la mobile
////            String strQuery = "from StockIsdnMobile " +
////                    "where to_number(isdn) >= ? and to_number(isdn) <= ? ";
////            Query query = getSession().createQuery(strQuery);
////            query.setParameter(0, fromNumber);
////            query.setParameter(1, toNumber);
////            List<StockIsdnMobile> listStockIsdnMobile = query.list();
////
////            //ghi du lieu ra file
////            if(listStockIsdnMobile != null && listStockIsdnMobile.size() > 0) {
////                try {
////                    String DATE_FORMAT = "yyyyMMddHHmmss";
////                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
////                    String filePath = "/share/report_out/";
////                    UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
////                    filePath += "detailIsdnRange_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".csv";
////                    String realPath = req.getSession().getServletContext().getRealPath(filePath);
////                    PrintWriter output = null;
////                    File fNiceNumber = new File(realPath);
////                    output = new PrintWriter(fNiceNumber);
////                    output.println("Isdn range detail\t" + userToken.getLoginName() + "\t" + new Date());
////                    for (StockIsdnMobile stockIsdnMobile : listStockIsdnMobile) {
////                        output.println(stockIsdnMobile.getIsdn());
////                    }
////                    output.flush();
////                    output.close();
////
////                    req.setAttribute(REQUEST_DETAIL_ISDN_RANGE_PATH, filePath);
////                    req.setAttribute(REQUEST_DETAIL_ISDN_RANGE_MESSAGE, "Bấm vào đây để tải thông tin chi tiết về dải số từ " + strFromNumber + " đến " + strToNumber);
////                } catch (Exception ex) {
////                    ex.printStackTrace();
////                }
////            }
////
////        } else if (serviceType.equals(Constant.STOCK_ISDN_HOMEPHONE)) {
////            //neu dich vu la homephone
////            String strQuery = "from StockIsdnHomephone " +
////                    "where to_number(isdn) >= ? and to_number(isdn) <= ? ";
////            Query query = getSession().createQuery(strQuery);
////            query.setParameter(0, fromNumber);
////            query.setParameter(1, toNumber);
////
////            List<StockIsdnHomephone> listStockIsdnHomephone = query.list();
////
////            //ghi du lieu ra file
////            if(listStockIsdnHomephone != null && listStockIsdnHomephone.size() > 0) {
////                try {
////                    String DATE_FORMAT = "yyyyMMddHHmmss";
////                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
////                    String filePath = "/share/report_out/";
////                    UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
////                    filePath += "detailIsdnRange_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".csv";
////                    String realPath = req.getSession().getServletContext().getRealPath(filePath);
////                    PrintWriter output = null;
////                    File fNiceNumber = new File(realPath);
////                    output = new PrintWriter(fNiceNumber);
////                    output.println("Isdn range detail\t" + userToken.getLoginName() + "\t" + new Date());
////                    for (StockIsdnHomephone stockIsdnHomephone : listStockIsdnHomephone) {
////                        output.println(stockIsdnHomephone.getIsdn());
////                    }
////                    output.flush();
////                    output.close();
////
////                    req.setAttribute(REQUEST_DETAIL_ISDN_RANGE_PATH, filePath);
////                    req.setAttribute(REQUEST_DETAIL_ISDN_RANGE_MESSAGE, "Bấm vào đây để tải thông tin chi tiết về dải số tứ " + strFromNumber + " đến " + strToNumber);
////
////                } catch (Exception ex) {
////                    ex.printStackTrace();
////                }
////            }
////
////        } else {
////
////        }
//
//        pageForward = LIST_ASSIGN_ISDN;
//
//        log.debug("end method detailIsdnRange of AssignIsdnTypeDAO");

        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 12/05/2009
     * purpose  : gan loai so
     *
     */
    public String assignIsdnType() throws Exception {
        log.debug("begin method assignIsdnType of AssignIsdnTypeDAO...");

        try {
            HttpServletRequest req = getRequest();
            List<AssignIsdnTypeForm> listAssignIsdnTypeForm = (List<AssignIsdnTypeForm>) req.getSession().getAttribute(SESSION_LIST_STOCK_ISDN);
            if (listAssignIsdnTypeForm == null || listAssignIsdnTypeForm.size() == 0) {
                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.listIsdnRangeEmpty");

                pageForward = ASSIGN_ISDN_TYPE;
                log.debug("end method assignIsdnType of AssignIsdnTypeDAO");
                return pageForward;
            }
            Long[] arrSelectedFormId = this.assignIsdnTypeForm.getArrSelectedFormId();
            if (arrSelectedFormId == null || arrSelectedFormId.length == 0) {
                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.listIsdnRangeEmpty");

                pageForward = ASSIGN_ISDN_TYPE;
                log.debug("end method assignIsdnType of AssignIsdnTypeDAO");
                return pageForward;
            }

            Long newStatus = this.assignIsdnTypeForm.getNewStatus();
            BaseStockDAO baseStockDAO = new BaseStockDAO(); //lay ten bang
            for (int i = 0; i < arrSelectedFormId.length; i++) {
                AssignIsdnTypeForm tmpAssignIsdnTypeForm = listAssignIsdnTypeForm.get(arrSelectedFormId[i].intValue() - 1); //-1 do chi so mang bat dau tu 0, rownum bat dau tu 1
                Long tmpStockTypeId = tmpAssignIsdnTypeForm.getStockTypeId();
                String tmpTableName = baseStockDAO.getTableNameByStockType(tmpStockTypeId, BaseStockDAO.NAME_TYPE_HIBERNATE);
                Long fromIsdn = Long.valueOf(tmpAssignIsdnTypeForm.getFromIsdn());
                Long toIsdn = Long.valueOf(tmpAssignIsdnTypeForm.getToIsdn());
                String isdnType = tmpAssignIsdnTypeForm.getIsdnType();
                Long status = tmpAssignIsdnTypeForm.getStatus();
                Long ownerId = tmpAssignIsdnTypeForm.getShopId();
                StringBuffer strQuery = new StringBuffer("");
                List listParameter = new ArrayList();

                if (isdnType != null && !isdnType.equals("-1")) {
                    //truong hop so da xac dinh loai
                    strQuery.append("update " + tmpTableName + " ");

                    strQuery.append("set status = ? ");
                    listParameter.add(newStatus);

                    strQuery.append("where to_number(isdn) >= ? and to_number(isdn) <= ? ");
                    listParameter.add(fromIsdn);
                    listParameter.add(toIsdn);

                    strQuery.append("and ownerId = ? and ownerType = ? ");
                    listParameter.add(ownerId);
                    listParameter.add(Constant.OWNER_TYPE_SHOP);

                    strQuery.append("and status = ? and isdnType = ? ");
                    listParameter.add(status);
                    listParameter.add(isdnType);

                } else {
                    //truong hop cac so chua xac dinh loai
                    strQuery.append("update " + tmpTableName + " ");

                    strQuery.append("set status = ? ");
                    listParameter.add(newStatus);

                    strQuery.append("where to_number(isdn) >= ? and to_number(isdn) <= ? ");
                    listParameter.add(fromIsdn);
                    listParameter.add(toIsdn);

                    strQuery.append("and ownerId = ? and ownerType = ? ");
                    listParameter.add(ownerId);
                    listParameter.add(Constant.OWNER_TYPE_SHOP);

                    strQuery.append("and status = ? and isdnType is null ");
                    listParameter.add(status);

                }

                Query query = getSession().createQuery(strQuery.toString());
                for (int j = 0; j < listParameter.size(); j++) {
                    query.setParameter(j, listParameter.get(j));
                }

                int result = query.executeUpdate();
            }

            //
            req.setAttribute(REQUEST_MESSAGE, "assignIsdnStatus.success");

            //reset lai form
            this.assignIsdnTypeForm.resetForm();

            //kho mac dinh la kho cua user dang nhap
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            this.assignIsdnTypeForm.setShopCode(userToken.getShopCode());
            this.assignIsdnTypeForm.setShopName(userToken.getShopName());

            //remove list isdn
            req.getSession().removeAttribute(SESSION_LIST_STOCK_ISDN);


            pageForward = ASSIGN_ISDN_TYPE;
            log.debug("end method assignIsdnType of AssignIsdnTypeDAO");
            return pageForward;

        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("end method assignIsdnType of AssignIsdnTypeDAO");
            throw ex;
        }
    }

    /**
     *
     * author   : NamNX
     * date     : 06/04/2010
     * purpose  : gan loai so du lieu doc tu file
     *
     */
    public String assignIsdnTypeFromFile() throws Exception {
        log.debug("begin method assignIsdnTypeFromFile of AssignIsdnTypeDAO...");

        try {
            HttpServletRequest req = getRequest();

            String serverFileName = StringEscapeUtils.getSafeFileName(this.assignIsdnTypeForm.getServerFileName());
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
            File impFile = new File(serverFilePath);
            List listIsdn = new CommonDAO().readExcelFile(impFile, "Sheet1", 0, 0, 2);

            String strTableName = new BaseStockDAO().getTableNameByStockType(this.assignIsdnTypeForm.getStockTypeId(), BaseStockDAO.NAME_TYPE_HIBERNATE);
            if (listIsdn == null) {

                req.setAttribute(REQUEST_MESSAGE, "assignIsdnStatus.error.errorReadFile");

                pageForward = ASSIGN_ISDN_TYPE;
                log.debug("End method addIsdnRange of DistributeIsdnDAO");
                return pageForward;
            }
            if (listIsdn.size() == 0) {

                req.setAttribute(REQUEST_MESSAGE, "assignIsdnStatus.error.fileNotHasData");

                pageForward = ASSIGN_ISDN_TYPE;
                log.debug("End method addIsdnRange of DistributeIsdnDAO");
                return pageForward;
            }
            List lstError = new ArrayList<LogAssignIsdnStatusBean>();


            int totalRecord = listIsdn.size();
            int successRecord = 0;
            int countError=1;
            for (int idx = 0; idx < listIsdn.size(); idx++) {
                Object[] obj = (Object[]) listIsdn.get(idx); //1 hang trong file excel
                if (obj != null && obj.length >= 3) {

                    //So isdn
                    String isdn = obj[0] != null ? obj[0].toString().trim() : "";
                    // Loai so moi
                    String strNewIsdnType = obj[1] != null ? obj[1].toString().trim() : "";
                    //Trang thai moi
                    String strNewStatus = obj[2] != null ? obj[2].toString().trim() : "";

                    if (!checkValidateIsdn(isdn, strNewIsdnType, strNewStatus)) {
                        LogAssignIsdnStatusBean logBean = new LogAssignIsdnStatusBean(countError++,isdn, strNewIsdnType, strNewStatus, errorMessage);
                        lstError.add(logBean);
                    } else {

                        StringBuffer strQuery = new StringBuffer("");
                        List listParameter = new ArrayList();
                        strQuery.append("UPDATE ");
                        strQuery.append(strTableName);
                        if (strNewIsdnType.equals("") && !strNewStatus.equals("")) {
                            strQuery.append(" SET status = ?");
                            listParameter.add(Long.parseLong(strNewStatus));
                        }

                        if (!strNewIsdnType.equals("") && strNewStatus.equals("")) {
                            strQuery.append(" SET isdnType = ?");
                            listParameter.add(strNewIsdnType);
                        }

                        if (!strNewIsdnType.equals("") && !strNewStatus.equals("")) {
                            strQuery.append(" SET isdnType = ?, status = ? ");
                            listParameter.add(strNewIsdnType);
                            listParameter.add(Long.parseLong(strNewStatus));
                        }
                        strQuery.append(" WHERE to_number(isdn) = ?");
                        listParameter.add(isdn);

                        Query query = getSession().createQuery(strQuery.toString());
                        for (int i = 0; i < listParameter.size(); i++) {
                            query.setParameter(i, listParameter.get(i));
                        }
                       
                        successRecord += query.executeUpdate();

                    }

                }


            }


            if (lstError.size() > 0) {
                try {
                    UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
                    String DATE_FORMAT = "yyyyMMddhh24mmss";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    Calendar cal = Calendar.getInstance();

                    String date = sdf.format(cal.getTime());
                    String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
                    String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
                    String templatePath = "";
                    //Giao dich xuat

                    templatePath = tmp + "Log_assign_isdn_status.xls";
                    filePath += "Log_assign_isdn_status_" + userToken.getLoginName() + "_" + date + ".xls";

                    this.assignIsdnTypeForm.setPathExpFile(filePath);
                    String realPath = req.getSession().getServletContext().getRealPath(filePath);
                    String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

                    Map beans = new HashMap();
                    //set ngay tao
                    beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
                    //set nguoi tao
                    beans.put("userCreate", userToken.getFullName());


                    beans.put("listLogs", lstError);
                    XLSTransformer transformer = new XLSTransformer();
                    transformer.transformXLS(templateRealPath, beans, realPath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }










            req.setAttribute(REQUEST_MESSAGE, "Gán số từ File thành công " + String.valueOf(successRecord) + "/" + String.valueOf(totalRecord));

            //reset lai form
            this.assignIsdnTypeForm.resetForm();

            //kho mac dinh la kho cua user dang nhap
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            this.assignIsdnTypeForm.setShopCode(userToken.getShopCode());
            this.assignIsdnTypeForm.setShopName(userToken.getShopName());
            //remove list isdn
            req.getSession().removeAttribute(SESSION_LIST_STOCK_ISDN);


            pageForward = ASSIGN_ISDN_TYPE;
            log.debug("end method assignIsdnType of AssignIsdnTypeDAO");
            return pageForward;

        } catch (Exception ex) {
            ex.printStackTrace();
//            log.info("end method assignIsdnTypeFromFile of AssignIsdnTypeDAO");
//            throw ex;
        }

        pageForward = ASSIGN_ISDN_TYPE;
        log.debug("end method assignIsdnType of AssignIsdnTypeDAO");
        return pageForward;


    }

    /**
     *
     * author   : NamNX
     * date     : 06/04/2010
     * purpose  : kiem tra tinh hop le du lieu doc tu file
     *
     */
    private Boolean checkValidateIsdn(String isdn, String strIsdnType, String strStatus) {


        if (isdn.equals("")) {

            errorMessage = "Số thuê bao trống";
            return false;

        }

        if (strIsdnType.equals("") && strStatus.equals("")) {
            errorMessage = "Loại số mới và trạng thái mới rỗng";
            return false;
        }

        if (!strStatus.equals("")) {
            try {
                Long status = Long.parseLong(strStatus);
                if (status < 1 || status > 5) {
                    errorMessage = "Trạng thái mới không hợp lệ";
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        if (!strIsdnType.equals("")) {
            try {
                Long isdnType = Long.parseLong(strIsdnType);
                if (isdnType < 1 || isdnType > 3) {
                    errorMessage = "Loại số mới không hợp lệ";
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        try {
            Long isdnNumber = Long.parseLong(isdn);
            //Trang thai cua isdn la 2 - dang su dung thi khong duoc update
            String strTableName = new BaseStockDAO().getTableNameByStockType(this.assignIsdnTypeForm.getStockTypeId(), BaseStockDAO.NAME_TYPE_HIBERNATE);
            StringBuffer strQuery = new StringBuffer("");

            strQuery.append("SELECT status FROM ");
            strQuery.append(strTableName);
            strQuery.append(" WHERE to_number(isdn) = ");
            strQuery.append(isdnNumber);
            Query query = getSession().createQuery(strQuery.toString());
            if (query.list().size() == 0) {
                errorMessage = "Số thuê bao không hợp lệ";
                return false;

            }
            Long oldStatus = (Long) query.list().get(0);
            if (oldStatus.equals(STATUS_USE)) {
                errorMessage = "Số thuê bao đang sử dụng";
                return false;
            }
        } catch (Exception e) {
        }






        return true;
    }
}
