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
import com.viettel.im.database.BO.UserToken;
import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import com.viettel.im.database.BO.IsdnTrans;
import com.viettel.im.database.BO.IsdnTransDetail;
import com.viettel.im.common.util.DateTimeUtils;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import jxl.Sheet;
import jxl.Workbook;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Session;

/**
 *
 * @author Doan Thanh 8
 * Modified by NamNX , bo sung tinh nang nhap theo File
 * thuc gan loai so moi
 *
 */
public class AssignIsdnTypeDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(AssignIsdnTypeDAO.class);
    //cac ten bien session hoac request
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String REQUEST_ERROR_FILE_PATH = "errorFilePath";
    private final String REQUEST_DETAIL_ISDN_RANGE_PATH = "detailIsdnRangePath";
    private final String REQUEST_DETAIL_ISDN_RANGE_MESSAGE = "detailIsdnRangeMessage";
    private final String SESSION_LIST_STOCK_ISDN = "listStockIsdn";
    //cac hang so forward
    private String pageForward;
    private final String ASSIGN_ISDN_TYPE = "assignIsdnType";
    private final String LIST_ASSIGN_ISDN_TYPE = "listAssignIsdnType";
    private Long IMP_BY_ISDN_RANGE = 1L;
    private Long IMP_BY_FILE = 2L;
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
    private int NUMBER_CMD_IN_BATCH = 10000; //so luong ban ghi se commit 1 lan

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
            this.assignIsdnTypeForm.resetForm();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            //xoa bien session
            req.getSession().setAttribute(SESSION_LIST_STOCK_ISDN, null);

            //kho mac dinh la kho cua user dang nhap
//            this.assignIsdnTypeForm.setShopCode(userToken.getShopCode());
//            this.assignIsdnTypeForm.setShopName(userToken.getShopName());


            //
            List<String> listMessage = new ArrayList<String>();
            String userSessionId = req.getSession().getId();
            AssignIsdnTypeDAO.listProgressMessage.put(userSessionId, listMessage);

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
        log.info("begin method searchIsdnRange of AssignIsdnTypeDAO...");
        HttpServletRequest req = getRequest();


        try {


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
            strIsdnRangeQuery.append("          isdn - ROW_NUMBER () OVER (ORDER BY isdn_type,status, isdn) rn, ");
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
            } else {
                strIsdnRangeQuery.append("      and (status = ? or status = ?) ");
                listParameter.add(Constant.STATUS_ISDN_NEW);
                listParameter.add(Constant.STATUS_ISDN_SUSPEND);
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

//            BaseDAOAction baseDAOAction = new BaseDAOAction();

            if (listAssignIsdnTypeForm != null && listAssignIsdnTypeForm.size() != 0) {
//                req.setAttribute(REQUEST_MESSAGE, "Tìm kiếm được " + listAssignIsdnTypeForm.size() + " dải số thỏa mãn điều kiện");
                req.setAttribute(REQUEST_MESSAGE, getText("MSG.ISN.052") + " " + listAssignIsdnTypeForm.size() + " " + getText("MSG.ISN.053"));
            } else {
//                req.setAttribute(REQUEST_MESSAGE, "Không tìm kiếm được 0 dải số nào thỏa mãn điều kiện");
                req.setAttribute(REQUEST_MESSAGE, getText("MSG.ISN.054"));
            }

            pageForward = ASSIGN_ISDN_TYPE;
            log.info("end method searchIsdnRange of AssignIsdnTypeDAO");
            return pageForward;

        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("end method searchIsdnRange of AssignIsdnTypeDAO");
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

            String[] isdnIdList = this.assignIsdnTypeForm.getIsdnIdList();
            if (isdnIdList == null || isdnIdList.length == 0) {
                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.listIsdnRangeEmpty");

                pageForward = ASSIGN_ISDN_TYPE;
                log.debug("end method assignIsdnType of AssignIsdnTypeDAO");
                return pageForward;
            }

//            Long[] arrSelectedFormId = this.assignIsdnTypeForm.getArrSelectedFormId();
//            if (arrSelectedFormId == null || arrSelectedFormId.length == 0) {
//                req.setAttribute(REQUEST_MESSAGE, "assignIsdnType.error.listIsdnRangeEmpty");
//
//                pageForward = ASSIGN_ISDN_TYPE;
//                log.debug("end method assignIsdnType of AssignIsdnTypeDAO");
//                return pageForward;
//            }

            String newIsdnType = this.assignIsdnTypeForm.getNewIsdnType();
            String newIsdnStatus = this.assignIsdnTypeForm.getNewIsdnStatus();

            if ((newIsdnStatus == null || newIsdnStatus.trim().equals("")) && (newIsdnType == null || newIsdnType.trim().equals(""))) {
                req.setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Phải chọn ít nhất một loại tác động để thực hiện");

                pageForward = ASSIGN_ISDN_TYPE;
                log.debug("end method assignIsdnType of AssignIsdnTypeDAO");
                return pageForward;
            }

            BaseStockDAO baseStockDAO = new BaseStockDAO(); //lay ten bang
//            for (int i = 0; i < arrSelectedFormId.length; i++) {
//                AssignIsdnTypeForm tmpAssignIsdnTypeForm = listAssignIsdnTypeForm.get(arrSelectedFormId[i].intValue() - 1); //-1 do chi so mang bat dau tu 0, rownum bat dau tu 1
            for (int i = 0; i < isdnIdList.length; i++) {
                String tmp = isdnIdList[i];

                AssignIsdnTypeForm tmpAssignIsdnTypeForm = listAssignIsdnTypeForm.get(Integer.parseInt(isdnIdList[i]) - 1); //-1 do chi so mang bat dau tu 0, rownum bat dau tu 1
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
                    if (newIsdnType != null && !newIsdnType.trim().equals("") && newIsdnStatus != null && !newIsdnStatus.trim().equals("")) {
                        strQuery.append("set isdnType = ?, status = ? ");
                        listParameter.add(newIsdnType.trim());
                        listParameter.add(Long.parseLong(newIsdnStatus.trim()));
                    } else if (newIsdnType != null && !newIsdnType.trim().equals("")) {
                        strQuery.append("set isdnType = ? ");
                        listParameter.add(newIsdnType.trim());
                    } else if (newIsdnStatus != null && !newIsdnStatus.trim().equals("")) {
                        strQuery.append("set status = ? ");
                        listParameter.add(Long.parseLong(newIsdnStatus.trim()));
                    }

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

                    strQuery.append("set isdnType = ? ");
                    listParameter.add(newIsdnType);

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

                //------------------------start----------------------------------------
                //              Andv
                //luu thong tin dai so can tao isdnTrans va isdnTransDetail
                Session session = getSession();
                Long stockTypeId = tmpAssignIsdnTypeForm.getStockTypeId();
                UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                String message = "";
//                message = simpleDateFormat.format(DateTimeUtils.getSysdate()) + " bắt đầu lưu thông tin dải số cần tạo";
//                System.out.println(message);

                IsdnTrans isdnTrans = new IsdnTrans();
                Long isdnTransId = getSequence("ISDN_TRANS_SEQ");
                isdnTrans.setIsdnTransId(isdnTransId);
                isdnTrans.setStockTypeId(stockTypeId);
                isdnTrans.setTransType(Constant.ISDN_TRANS_TYPE_ASSIGN_TYPE);
                isdnTrans.setLastUpdateUser(userToken.getLoginName() + "-" + req.getRemoteHost());
                isdnTrans.setLastUpdateIpAddress(req.getRemoteAddr());
                isdnTrans.setLastUpdateTime(DateTimeUtils.getSysDate());
                session.save(isdnTrans);


                if (((newIsdnStatus == null || newIsdnStatus.trim().equals(""))
                        || (newIsdnStatus != null && !newIsdnStatus.toString().equals("") && newIsdnStatus.trim().equals(status.toString())))
                        && (((newIsdnType == null || newIsdnType.trim().equals(""))
                        || (newIsdnType != null && !newIsdnType.toString().equals("") && isdnType != null && newIsdnType.equals(isdnType))))) {
                    IsdnTransDetail isdnTransDetail = new IsdnTransDetail();
                    isdnTransDetail.setIsdnTransId(isdnTransId);
                    isdnTransDetail.setFromIsdn(tmpAssignIsdnTypeForm.getFromIsdn().trim());
                    isdnTransDetail.setToIsdn(tmpAssignIsdnTypeForm.getToIsdn().trim());
                    isdnTransDetail.setQuantity(tmpAssignIsdnTypeForm.getRealQuantity());
                    Long isdnTransDetailId = getSequence("ISDN_TRANS_DETAIL_SEQ");
                    isdnTransDetail.setIsdnTransDetailId(isdnTransDetailId);
                    session.save(isdnTransDetail);
                    session.flush();
                }
                if (newIsdnType != null && !newIsdnType.toString().equals("")
                        && (isdnType == null || (isdnType != null && !newIsdnType.trim().equals(isdnType.trim())))) {
                    IsdnTransDetail isdnTransDetail = new IsdnTransDetail();
                    isdnTransDetail.setIsdnTransId(isdnTransId);
                    isdnTransDetail.setFromIsdn(tmpAssignIsdnTypeForm.getFromIsdn().trim());
                    isdnTransDetail.setToIsdn(tmpAssignIsdnTypeForm.getToIsdn().trim());
                    isdnTransDetail.setQuantity(tmpAssignIsdnTypeForm.getRealQuantity());
                    Long isdnTransDetailId = getSequence("ISDN_TRANS_DETAIL_SEQ");
                    isdnTransDetail.setIsdnTransDetailId(isdnTransDetailId);
                    isdnTransDetail.setOldValue(tmpAssignIsdnTypeForm.getIsdnType());
                    isdnTransDetail.setNewValue(tmpAssignIsdnTypeForm.getNewIsdnType());
                    session.save(isdnTransDetail);
                    session.flush();
                }
                if (newIsdnStatus != null && !newIsdnStatus.toString().equals("") && !newIsdnStatus.equals(status.toString())) {
                    IsdnTransDetail isdnTransDetail2 = new IsdnTransDetail();
                    isdnTransDetail2.setIsdnTransId(isdnTransId);
                    isdnTransDetail2.setFromIsdn(tmpAssignIsdnTypeForm.getFromIsdn().trim());
                    isdnTransDetail2.setToIsdn(tmpAssignIsdnTypeForm.getToIsdn().trim());
                    isdnTransDetail2.setQuantity(tmpAssignIsdnTypeForm.getRealQuantity());
                    Long isdnTransDetailId = getSequence("ISDN_TRANS_DETAIL_SEQ");
                    isdnTransDetail2.setIsdnTransDetailId(isdnTransDetailId);
                    isdnTransDetail2.setOldValue(status.toString());
                    isdnTransDetail2.setNewValue(newIsdnStatus.trim());
                    session.save(isdnTransDetail2);
                    session.flush();
                }

//                message = simpleDateFormat.format(DateTimeUtils.getSysdate()) + " kết thúc lưu thông tin dải số cần gán";
//                System.out.println(message);
                //end: andv
            }

            searchIsdnRange();
            //
            req.setAttribute(REQUEST_MESSAGE, "M.100001");

            //reset lai form
            //this.assignIsdnTypeForm.resetForm();

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

    /* author   : NamNX
     * date     : 06/04/2010
     * purpose  : gan loai so du lieu doc tu file
     *
     */
    public String assignIsdnTypeFromFile() throws Exception {
        log.debug("begin method assignIsdnTypeFromFile of AssignIsdnTypeDAO...");

        try {
            HttpServletRequest req = getRequest();

            String serverFileName = CommonDAO.getSafeFileName(this.assignIsdnTypeForm.getServerFileName());
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
            int countError = 1;
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
                        LogAssignIsdnStatusBean logBean = new LogAssignIsdnStatusBean(countError++, isdn, strNewIsdnType, strNewStatus, errorMessage);
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
                    beans.put("dateCreate", getSysdate());
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
     * author   : tamdt1
     * date     : 18/08/2010
     * purpose  : cap nhat mat hang cho so bang file
     *
     */
    public String assignStatusForIsdnByFile() throws Exception {
        log.info("begin method assignStatusForIsdnByFile of AssignIsdnTypeDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        boolean rejectLogErrorAssignIsdnStatus = false;
        String strRejectLogErrorAssignIsdnStatus = ResourceBundleUtils.getResource("rejectLogErrorAssignIsdnStatus");
        strRejectLogErrorAssignIsdnStatus = strRejectLogErrorAssignIsdnStatus != null ? strRejectLogErrorAssignIsdnStatus : "";

        if (AuthenticateDAO.checkAuthority(strRejectLogErrorAssignIsdnStatus, req)) {
            //bo qua viec ghi log khi phan phoi so
            rejectLogErrorAssignIsdnStatus = true;
        }

        try {
            Connection conn = null;
            PreparedStatement stmUpdate = null;

            conn = session.connection();

            Long stockTypeId = this.assignIsdnTypeForm.getStockTypeId();
            String strTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            String userSessionId = req.getSession().getId();

            //tao cau lenh update
            StringBuffer strUpdateQuery = new StringBuffer();
            strUpdateQuery.append("update " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " a ");
            strUpdateQuery.append("set  a.last_update_user = ?, "
                    + "                 a.last_update_ip_address = ?, "
                    + "                 a.last_update_time = sysdate, "
                    + "                 a.last_update_key = ?, "
                    + "                 a.status = ?,  "
                    + "                 a.isdn_type = ?  ");
            strUpdateQuery.append("where 1 = 1 ");
            strUpdateQuery.append("and to_number(a.isdn) = ? ");

            //chi cho phep gan trang thai va loai so cho so moi va so ngung su dung
            strUpdateQuery.append("and (a.status = " + Constant.STATUS_ISDN_NEW + " or a.status = " + Constant.STATUS_ISDN_SUSPEND + " ) ");
            strUpdateQuery.append("log errors reject limit unlimited"); //chuyen cac ban insert loi ra bang log

            stmUpdate = conn.prepareStatement(strUpdateQuery.toString());

            stmUpdate.setString(1, userToken.getLoginName()); //last_update_user
            stmUpdate.setString(2, req.getRemoteAddr()); //last_update_ip_address
            stmUpdate.setString(3, Constant.LAST_UPDATE_KEY); //last_update_key
//            stmUpdate.setLong(4, tmpNewStatus); //thiet lap truong newStatus
//            stmUpdate.setLong(5, tmpNewIsdnType); //thiet lap truong newIsdnType
//            stmUpdate.setLong(6, Long.parseLong(tmpIsdn)); //thiet lap truong isdn

            //lay du lieu tu file
            List<AssignIsdnTypeForm> listError = new ArrayList<AssignIsdnTypeForm>();
            String serverFileName = this.assignIsdnTypeForm.getServerFileName();
            serverFileName = CommonDAO.getSafeFileName(serverFileName);
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);

            Workbook workbook = null;
            Sheet sheet = null;
            int numberRow = 0;
            try {
                File impFile = new File(serverFilePath);
                workbook = Workbook.getWorkbook(impFile);
                sheet = workbook.getSheet(0);
                numberRow = sheet.getRows();
            } catch (Exception ex) {
                ex.printStackTrace();
                pageForward = ASSIGN_ISDN_TYPE;
                req.setAttribute(REQUEST_MESSAGE, "Error. File must be MS Excel 2003 version!");
                return pageForward;
            }


            numberRow = numberRow - 1; //bo dong dau tien chua title

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            List<String> listMessage = AssignIsdnTypeDAO.listProgressMessage.get(userSessionId);
            String message = "";

            //lay cac message thong bao loi
//            BaseDAOAction baseDAOAction = new BaseDAOAction();
            HashMap<String, String> hashMapError = new HashMap<String, String>();
            hashMapError.put("ERR.ISN.125", getText("ERR.ISN.125")); //!!!Err. Cột isdn, trạng thái mới và loại số mới không được để trống
            hashMapError.put("ERR.ISN.122", getText("ERR.ISN.122")); //!!!Err. Cột isdn không đúng định dạng số
            hashMapError.put("ERR.ISN.123", getText("ERR.ISN.123")); //!!!Err. Số isdn không tồn tại hoặc số isdn thuộc kho không được phép phân phối
            hashMapError.put("ERR.ISN.126", getText("ERR.ISN.126")); //!!!Err. Cột trạng thái mới không đúng định dạng
            hashMapError.put("ERR.ISN.127", getText("ERR.ISN.127")); //!!!Err. Cột loại số mới không đúng định dạngng

            HashMap<String, String> hashMapMessage = new HashMap<String, String>();
            hashMapMessage.put("MSG.ISN.048", getText("MSG.ISN.048")); //đang xử lý từ dòng
            hashMapMessage.put("MSG.ISN.049", getText("MSG.ISN.049")); //đến
            hashMapMessage.put("MSG.ISN.050", getText("MSG.ISN.050")); //dòng trong file
            hashMapMessage.put("MSG.ISN.057", getText("MSG.ISN.057")); //Gán trạng thái số và loại số thành công

            HashMap<String, Long> hashMapIsdnStatus = new HashMap<String, Long>();
            hashMapIsdnStatus.put(Constant.ISDN_STATUS_NEW_DESC, Constant.STATUS_ISDN_NEW);
            hashMapIsdnStatus.put(Constant.ISDN_STATUS_SUSPEND_DESC, Constant.STATUS_ISDN_SUSPEND);

            HashMap<String, String> hashMapIsdnType = new HashMap<String, String>();
            hashMapIsdnType.put(Constant.ISDN_TYPE_POST_DESC, Constant.ISDN_TYPE_POST);
            hashMapIsdnType.put(Constant.ISDN_TYPE_PRE_DESC, Constant.ISDN_TYPE_PRE);
            hashMapIsdnType.put(Constant.ISDN_TYPE_SPEC_DESC, Constant.ISDN_TYPE_SPEC);

            HashMap<String, Long> hashMapStockModel = new HashMap<String, Long>();


            if (numberRow > 0) {
                int toRow = NUMBER_CMD_IN_BATCH;
                toRow = toRow < numberRow ? toRow : numberRow;
                message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " " + hashMapMessage.get("MSG.ISN.048") + " 1 " + hashMapMessage.get("MSG.ISN.049") + " " + toRow + "/ " + numberRow + " " + hashMapMessage.get("MSG.ISN.050");
                listMessage.add(message);
            }


            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet, cot dau tien la isdn, cot sau la ma mat hang moi can cap nhat
                String tmpStrIsdn = sheet.getCell(0, rowIndex).getContents();
                Long tmpIsdn = -1L;
                String tmpStrNewStatus = sheet.getCell(1, rowIndex).getContents();
                Long tmpNewStatus = -1L;
                String tmpStrNewIsdnType = sheet.getCell(2, rowIndex).getContents();
                String tmpNewIsdnType = "";
                if (tmpStrIsdn == null || tmpStrIsdn.trim().equals("")
                        || tmpStrNewStatus == null || tmpStrNewStatus.trim().equals("")
                        || tmpStrNewIsdnType == null || tmpStrNewIsdnType.trim().equals("")) {
                    AssignIsdnTypeForm errAssignIsdnTypeForm = new AssignIsdnTypeForm();
                    errAssignIsdnTypeForm.setFromIsdn(tmpStrIsdn);
                    errAssignIsdnTypeForm.setNewIsdnStatus(tmpStrNewStatus);
                    errAssignIsdnTypeForm.setNewIsdnType(tmpStrNewIsdnType);
                    errAssignIsdnTypeForm.setErrorMessage(hashMapError.get("ERR.ISN.125")); //!!!Err. Cột isdn, trạng thái mới và loại số mới không được để trống
                    listError.add(errAssignIsdnTypeForm);
                    continue;
                } else {
                    tmpStrIsdn = tmpStrIsdn.trim();
                    try {
                        tmpIsdn = Long.parseLong(tmpStrIsdn);
                    } catch (NumberFormatException ex) {
                        //isdn khong dung dinh dang
                        AssignIsdnTypeForm errAssignIsdnTypeForm = new AssignIsdnTypeForm();
                        errAssignIsdnTypeForm.setFromIsdn(tmpStrIsdn);
                        errAssignIsdnTypeForm.setNewIsdnStatus(tmpStrNewStatus);
                        errAssignIsdnTypeForm.setNewIsdnType(tmpStrNewIsdnType);
                        errAssignIsdnTypeForm.setErrorMessage(hashMapError.get("ERR.ISN.122")); //!!!Err. Cột isdn không đúng định dạng số
                        listError.add(errAssignIsdnTypeForm);
                        continue;
                    }
                    tmpStrNewStatus = tmpStrNewStatus.trim();
                    tmpNewStatus = hashMapIsdnStatus.get(tmpStrNewStatus);
                    if (tmpNewStatus == null) {
                        //loai so moi khong dung
                        AssignIsdnTypeForm errAssignIsdnTypeForm = new AssignIsdnTypeForm();
                        errAssignIsdnTypeForm.setFromIsdn(tmpStrIsdn);
                        errAssignIsdnTypeForm.setNewIsdnStatus(tmpStrNewStatus);
                        errAssignIsdnTypeForm.setNewIsdnType(tmpStrNewIsdnType);
                        errAssignIsdnTypeForm.setErrorMessage(hashMapError.get("ERR.ISN.126")); //!!!Err. Cột trạng thái mới không đúng định dạng
                        listError.add(errAssignIsdnTypeForm);
                        continue;
                    }
                    tmpStrNewIsdnType = tmpStrNewIsdnType.trim();
                    tmpNewIsdnType = hashMapIsdnType.get(tmpStrNewIsdnType);
                    if (tmpNewIsdnType == null) {
                        //loai so moi khong dung
                        AssignIsdnTypeForm errAssignIsdnTypeForm = new AssignIsdnTypeForm();
                        errAssignIsdnTypeForm.setFromIsdn(tmpStrIsdn);
                        errAssignIsdnTypeForm.setNewIsdnStatus(tmpStrNewStatus);
                        errAssignIsdnTypeForm.setNewIsdnType(tmpStrNewIsdnType);
                        errAssignIsdnTypeForm.setErrorMessage(hashMapError.get("ERR.ISN.127")); //!!!Err. Cột loại số mới không đúng định dạng
                        listError.add(errAssignIsdnTypeForm);
                        continue;
                    }
                }

                Long tmpOldStockModelId = -1L;
                if (!rejectLogErrorAssignIsdnStatus) {
                    tmpOldStockModelId = getIsdnStockModelId(strTableName, tmpIsdn);
                    if (tmpOldStockModelId == null) {
                        //so khong ton tai hoac so khong thuoc kho duoc phep phan phoi
                        AssignIsdnTypeForm errAssignIsdnTypeForm = new AssignIsdnTypeForm();
                        errAssignIsdnTypeForm.setFromIsdn(tmpStrIsdn);
                        errAssignIsdnTypeForm.setNewIsdnStatus(tmpStrNewStatus);
                        errAssignIsdnTypeForm.setNewIsdnType(tmpStrNewIsdnType);
                        errAssignIsdnTypeForm.setErrorMessage(hashMapError.get("ERR.ISN.123"));
                        listError.add(errAssignIsdnTypeForm);
                        continue;
                    }
                }

                try {
                    //bat dau tu 4, vi 3 chi so dau da duoc thiet lap o tren khi tao cau lenh
                    stmUpdate.setLong(4, tmpNewStatus); //
                    stmUpdate.setString(5, tmpNewIsdnType); //
                    stmUpdate.setLong(6, tmpIsdn); //thiet lap truong isdn
                    stmUpdate.addBatch();

                } catch (Exception ex) {
                    //
                    AssignIsdnTypeForm errAssignIsdnTypeForm = new AssignIsdnTypeForm();
                    errAssignIsdnTypeForm.setFromIsdn(tmpStrIsdn);
                    errAssignIsdnTypeForm.setNewIsdnStatus(tmpStrNewStatus);
                    errAssignIsdnTypeForm.setNewIsdnType(tmpStrNewIsdnType);
                    errAssignIsdnTypeForm.setErrorMessage("!!!Ex. " + ex.getMessage());
                    listError.add(errAssignIsdnTypeForm);
                    continue;
                }

                if (rowIndex % NUMBER_CMD_IN_BATCH == 0) {
                    //commit
                    conn.setAutoCommit(false);
                    //chay batch chen du lieu vao cac bang tuong ung
                    int[] updateCount = stmUpdate.executeBatch();
                    conn.commit();
//                    conn.setAutoCommit(true);tronglv comment 120604

                    int fromRow = rowIndex + 1;
                    int toRow = fromRow + NUMBER_CMD_IN_BATCH;
                    toRow = toRow < numberRow ? toRow : numberRow;
//                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " đang xử lý từ dòng " + (fromRow + 1) + " đến " + toRow + "/ " + numberRow + " dòng trong file";
                    message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " " + hashMapMessage.get("MSG.ISN.048") + " " + (fromRow + 1) + " " + hashMapMessage.get("MSG.ISN.049") + " " + toRow + "/ " + numberRow + " " + hashMapMessage.get("MSG.ISN.050");
                    listMessage.add(message);
                }
            }

            //chen not cac ban ghi con lai
            //commit
            conn.setAutoCommit(false);
            //chay batch chen du lieu vao cac bang tuong ung
            int[] updateCount = stmUpdate.executeBatch();
            conn.commit();
//            conn.setAutoCommit(true);tronglv comment 120604


            //ket xuat ket qua ra file excel trong truong hop co loi
            if (listError != null && listError.size() > 0) {
                req.setAttribute(REQUEST_MESSAGE, hashMapMessage.get("MSG.ISN.057") + " " + (numberRow - listError.size()) + "/ " + numberRow);
                try {
                    String DATE_FORMAT = "yyyyMMddHHmmss";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
                    filePath = filePath != null ? filePath : "/";
                    filePath += com.viettel.security.util.StringEscapeUtils.getSafeFileName("assignIsdnTypeErr_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls");
                    String realPath = req.getSession().getServletContext().getRealPath(filePath);
                    String templatePath = ResourceBundleUtils.getResource("AIT_TMP_PATH_ERR");
                    if (templatePath == null || templatePath.trim().equals("")) {
                        //khong tim thay duong dan den file template de xuat ket qua
                        req.setAttribute(REQUEST_ERROR_FILE_PATH, "assignStockModelForIsdn.error.errTemplateNotExist");
                    }
                    String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
                    File fTemplateFile = new File(realTemplatePath);
                    if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                        //file ko ton tai
                        req.setAttribute(REQUEST_ERROR_FILE_PATH, "assignStockModelForIsdn.error.errTemplateNotExist");
                    }
                    Map beans = new HashMap();
                    beans.put("listError", listError);
                    XLSTransformer transformer = new XLSTransformer();
                    transformer.transformXLS(realTemplatePath, beans, realPath);
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, filePath);

                } catch (Exception ex) {
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, "!!!Ex. " + ex.toString());
                }

            } else {
                req.setAttribute(REQUEST_MESSAGE, hashMapMessage.get("MSG.ISN.057") + " " + numberRow + "/ " + numberRow);
            }

            //resetForm
            this.assignIsdnTypeForm.resetForm();

            //reset lai progress
            AssignIsdnTypeDAO.listProgressMessage.put(userSessionId, new ArrayList<String>());

            pageForward = ASSIGN_ISDN_TYPE;
            log.info("end method assignStockModelForIsdnByFile of AssignStockModelForIsdnDAO");
            return pageForward;

        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("end method assignStatusForIsdnByFile of AssignIsdnTypeDAO");
            throw ex;
        }
    }
    //bien phuc vu viec hien thi progress
    private static HashMap<String, List<String>> listProgressMessage = new HashMap<String, List<String>>(); //

    /**
     *
     * author   : tamdt1
     * date     : 14/11/2009
     * desc     : tra ve du lieu cap nhat cho divProgress
     *
     */
    public String updateProgressDiv(HttpServletRequest req) {
        log.info("Begin updateProgressDiv of StockIsdnDAO");

        try {
            String userSessionId = req.getSession().getId();
            List<String> listMessage = AssignIsdnTypeDAO.listProgressMessage.get(userSessionId);
            if (listMessage != null && listMessage.size() > 0) {
                String message = listMessage.get(0);
                listMessage.remove(0);
                return message;
            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/08/2010
     * desc     : kiem tra 1 so co hop le khong
     *
     */
    private Long getIsdnStockModelId(String strTableName, Long strIsdn) {
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            Session session = getSession();

            //
            List listParameter = new ArrayList();
            StringBuffer strQuery = new StringBuffer("");
            strQuery.append("SELECT stock_model_id ");
            strQuery.append("FROM " + com.viettel.security.util.StringEscapeUtils.getSafeFieldName(strTableName) + " a ");
            strQuery.append("WHERE 1 = 1 ");
            strQuery.append("      and to_number(a.isdn) = ? ");
            listParameter.add(strIsdn);

            //chi cho phep phan phoi so moi va so ngung su dung
            strQuery.append("and (status = ? or status = ? ) ");
            listParameter.add(Constant.STATUS_ISDN_NEW);
            listParameter.add(Constant.STATUS_ISDN_SUSPEND);

            strQuery.append("and exists (SELECT 'X' FROM SHOP WHERE shop_id = a.owner_id and (shop_id = ? or shop_path like ?) ) ");
            listParameter.add(userToken.getShopId());
            listParameter.add("%_" + userToken.getShopId() + "_%");

            Query query = session.createSQLQuery(strQuery.toString());
            for (int i = 0; i < listParameter.size(); i++) {
                query.setParameter(i, listParameter.get(i));
            }

            List list = query.list();
            if (list != null && list.size() == 1) {
                Object tmpItem = list.get(0);
                if (tmpItem != null && !tmpItem.toString().equals("")) {
                    return Long.valueOf(tmpItem.toString());
                } else {
                    return -1L;
                }
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
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
            errorMessage = "Số thuê bao không hợp lệ";
            return false;
        }


        if (strIsdnType.equals("") && strStatus.equals("")) {
            errorMessage = "Loại số mới và trạng thái mới rỗng";
            return false;
        }
        if (!strIsdnType.equals("")) {
            try {
                Long isdnType = Long.parseLong(strIsdnType);
                if (isdnType < 1 || isdnType > 3) {
                    errorMessage = "Loại số mới không hợp lệ";
                    return false;
                }
            } catch (Exception e) {
                errorMessage = "Loại số mới không hợp lệ";
                return false;
            }
        }
        if (!strStatus.equals("")) {
            try {
                Long status = Long.parseLong(strStatus);
                if (status < 1 || status > 5) {
                    errorMessage = "Trạng thái mới không hợp lệ";
                    return false;
                }
            } catch (Exception e) {
                errorMessage = "Trạng thái mới không hợp lệ";
                return false;
            }
        }








        return true;
    }
}
