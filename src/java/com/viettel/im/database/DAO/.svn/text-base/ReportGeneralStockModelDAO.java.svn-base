/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author Vunt
 */
import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ChangeGoodBeanDetail;
import com.viettel.im.client.bean.ChangeGoodBeanGenaral;
import com.viettel.im.client.bean.ChangeGoodSerialBean;
import com.viettel.im.client.bean.ReportGeneralStockModelBean;
import com.viettel.im.client.bean.ReportGeneralStockModelBean;
import com.viettel.im.client.form.ReportChangeGoodForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.UserToken;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.mina.filter.reqres.RequestResponseFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

public class ReportGeneralStockModelDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportChangeGoodDAO.class);
    public String pageForward;
    private static final Long STOCK_MODEL_NEW = 1L;
    private ReportChangeGoodForm reportChangeGoodForm = new ReportChangeGoodForm();

    public List getListStockModel() {
        return listStockModel;
    }

    public void setListStockModel(List listStockModel) {
        this.listStockModel = listStockModel;
    }

    public ReportChangeGoodForm getReportChangeGoodForm() {
        return reportChangeGoodForm;
    }

    public void setReportChangeGoodForm(ReportChangeGoodForm reportChangeGoodForm) {
        this.reportChangeGoodForm = reportChangeGoodForm;
    }
    private List listStockModel = new ArrayList();

    public String preparePage() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportGeneralStockModel.preparePage");
        HttpServletRequest req = getRequest();
        pageForward = "ReportGeneralStockModel";
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findByProperty("checkExp", 1L);
        req.setAttribute("lstStockType", lstStockType);
        /*
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
        req.setAttribute("lstTelecomService", lstTelecomService);
         */
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Long shopId = userToken.getShopId();
        //ShopDAO shopDAO = new ShopDAO();
        //shopDAO.setSession(this.getSession());
        //List listShop = shopDAO.findShopUnder(shopId);
        //req.setAttribute("listShop", listShop);
        reportChangeGoodForm.setShopCode(userToken.getShopCode());
        reportChangeGoodForm.setShopName(userToken.getShopName());
        reportChangeGoodForm.setStateId(Constant.STATE_NEW);
        reportChangeGoodForm.setReportType(1L);
        reportChangeGoodForm.setChannelAgent(true);
        reportChangeGoodForm.setChannelCTV(true);
        reportChangeGoodForm.setChannelShop(true);
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        List<ChannelType> listChannelType = channelTypeDAO.findByIsVtUnitAndIsPrivate(
                Constant.IS_NOT_VT_UNIT,
                Constant.CHANNEL_TYPE_IS_NOT_PRIVATE,
                Constant.OBJECT_TYPE_STAFF);
        req.getSession().setAttribute("listChannelType", listChannelType);
        log.debug("# End method ReportGeneralStockModel.preparePage");
        return pageForward;
    }

    public String preparePageReportStocktotalDetail() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportGeneralStockModel.preparePage");
        HttpServletRequest req = getRequest();
        pageForward = "ReportStockTotalDeital";
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        reportChangeGoodForm.setShopCode(userToken.getShopCode());
        reportChangeGoodForm.setShopName(userToken.getShopName());
        reportChangeGoodForm.setStateId(Constant.STATE_NEW);
        reportChangeGoodForm.setChannelShop(true);
        reportChangeGoodForm.setChannelAgent(false);
        reportChangeGoodForm.setChannelCTV(false);

        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        List<ChannelType> listChannelType = channelTypeDAO.findByIsVtUnitAndIsPrivate(
                Constant.IS_NOT_VT_UNIT,
                Constant.CHANNEL_TYPE_IS_NOT_PRIVATE,
                Constant.OBJECT_TYPE_STAFF);
        req.getSession().setAttribute("listChannelType", listChannelType);


        log.debug("# End method ReportGeneralStockModel.preparePage");
        return pageForward;
    }

    //Lay mat hang tu loai hang hoa
    public String selectStockType() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportGeneralStockModel.selectTelecomService");
        HttpServletRequest req = getRequest();
        pageForward = "selectTelecomService";
        Long stockTypeId = reportChangeGoodForm.getStockTypeId();
        if (stockTypeId == null || stockTypeId.equals(0L)) {
            String[] header = {"", getText("MSG.GOD.217")};
            listStockModel.add(header);
            return pageForward;
        }
        String SQL_SELECT = "select stockModelId, name from StockModel where stockTypeId = ? and status = ? ";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, stockTypeId);
        q.setParameter(1, Constant.STATUS_USE);
        List lst = q.list();

        String[] header = {"", getText("MSG.GOD.217")};
        listStockModel.add(header);
        listStockModel.addAll(lst);
        log.debug("# End method ReportGeneralStockModel.selectTelecomService");
        return pageForward;
    }

    //Tao bao cao
    public String exportReport() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportGeneralStockModel.exportReport");
        HttpServletRequest req = getRequest();
        pageForward = "ReportGeneralStockModel";
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            Long shopId = userToken.getShopId();
            //ShopDAO shopDAO = new ShopDAO();
            //shopDAO.setSession(this.getSession());
            //List listShop = shopDAO.findShopUnder(shopId);
            //req.setAttribute("listShop", listShop);
            StockTypeDAO stockTypeDAO = new StockTypeDAO();
            stockTypeDAO.setSession(this.getSession());
            List lstStockType = stockTypeDAO.findByProperty("checkExp", 1L);
            req.setAttribute("lstStockType", lstStockType);

            Long stockTypeId = reportChangeGoodForm.getStockTypeId();
            if (stockTypeId != null && stockTypeId.compareTo(0L) != 0) {
                String SQL_SELECT = "from StockModel where stockTypeId = ? and status = ? ";
                Query q = getSession().createQuery(SQL_SELECT);
                q.setParameter(0, stockTypeId);
                q.setParameter(1, Constant.STATUS_USE);
                List lst = q.list();
                listStockModel.addAll(lst);
            }
            req.setAttribute("lstStockModel", listStockModel);
            //trung dh3
            Long subChannelTypeId = reportChangeGoodForm.getSubChannelTypeId();
            //Lay ten stockmodel
            Long stockModelID = reportChangeGoodForm.getStockModelId();
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel stockmodel = null;
            if (stockModelID != null) {
                stockmodel = stockModelDAO.findById(stockModelID);
            }
            String shopCode = reportChangeGoodForm.getShopCode();
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            if (shop == null) {
//                req.setAttribute("resultStockImpExpRpt", "Mã đơn vị không chính xác");
                req.setAttribute("resultStockImpExpRpt", "ERR.RET.031");
                return pageForward;
            }
            reportChangeGoodForm.setShopId(shop.getShopId());


//            List<ReportGeneralStockModelBean> listStock = GetListStockModel(stockModelID);
//            //Tinh tong lai hang ton kho
//            listStock = reSumTotal(listStock);
            /*TuanPV - 24/02/2010 - Comment code cũ thử chuyển sang mô hình ReportServer*/
            /*
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "GeneralStockModel_report";

            //Giao dich xuat            
            templatePath = tmp + prefixPath + ".xls";
            filePath += prefixPath + date + ".xls";

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            java.io.InputStream is = new BufferedInputStream(new FileInputStream(templateRealPath));
            HSSFWorkbook resultWorkbook = new HSSFWorkbook();
            XLSTransformer transformer = new XLSTransformer();

            List sheetNames = new ArrayList();
            List tempNames = new ArrayList();
            List maps = new ArrayList();
            String sheetName;
            String tempName;
            String nameStatus;
            if (reportChangeGoodForm.getStateId().equals(Constant.STATE_NEW)) {
            nameStatus = "Hàng mới";
            } else {
            nameStatus = "Hàng hỏng";
            }
            Long totalSheet = 20000L;
            int sheetNum = (int) Math.ceil(listStock.size() / (float) totalSheet);
            if (listStock.size() == 0) {
            tempName = "BCHT";
            sheetName = "BCHT0";
            tempNames.add(tempName);
            sheetNames.add(sheetName);
            Map beans = new HashMap();
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            beans.put("userCreate", userToken.getFullName());
            beans.put("stockModelName", stockmodel.getName());
            beans.put("nameStatus", nameStatus);
            beans.put("listStock", listStock);
            maps.add(beans);

            } else {
            List<ReportGeneralStockModelBean> displaySheet = new ArrayList();
            Long begin;
            Long end;
            Long size = listStock.size() * 1L;
            for (int i = 0; i < sheetNum; i++) {
            displaySheet = new ArrayList();
            begin = totalSheet * i;
            end = totalSheet * (i + 1);
            if (begin.compareTo(size) > 0) {
            break;
            }
            if (end.compareTo(size) > 0) {
            end = size;
            }
            displaySheet = listStock.subList(begin.intValue(), end.intValue());
            sheetName = "BCHT" + i;
            tempName = "BCHT";
            tempNames.add(tempName);
            sheetNames.add(sheetName);
            Map beans = new HashMap();
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            beans.put("userCreate", userToken.getFullName());
            beans.put("stockModelName", stockmodel.getName());
            beans.put("nameStatus", nameStatus);
            beans.put("listStock", displaySheet);
            maps.add(beans);
            }

            }
            resultWorkbook = transformer.transformXLS(is, tempNames, sheetNames, maps);
            
            java.io.OutputStream os = new BufferedOutputStream(new FileOutputStream(realPath));
            resultWorkbook.write(os);
            os.close();

            //Map beans = new HashMap();
            //set ngay tao
            //beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            //beans.put("userCreate", userToken.getFullName());
            //beans.put("stockModelName", stockmodel.getName());
            //beans.put("listStock", listStock);
            //XLSTransformer transformer = new XLSTransformer();
            //transformer.transformXLS(templateRealPath, beans, realPath);

             *//*End TuanPV comment*/
            boolean channelShop = reportChangeGoodForm.isChannelShop();
            boolean channelCTV = reportChangeGoodForm.isChannelCTV();
            boolean channelAgent = reportChangeGoodForm.isChannelAgent();
            if (!channelShop && !channelCTV && !channelAgent) {
                req.setAttribute("resultStockImpExpRpt", "ERR.RET.060");
                return pageForward;
            }
            ViettelMsg request = new OriginalViettelMsg();
            request.set("CHANNEL_SHOP", channelShop);
            request.set("CHANNEL_CTV", channelCTV);
            request.set("CHANNEL_AGENT", channelAgent);
            request.set("USER_NAME", userToken.getLoginName());
            request.set("STOCK_TYPE_ID", stockTypeId);
            if (shop != null) {
                request.set("SHOP_ID", shop.getShopId());
                request.set("SHOP_NAME", shop.getName());
                request.set("SHOP_ADDRESS", shop.getAddress());
            } else {
                request.set("SHOP_ID", userToken.getShopId());
                request.set("SHOP_NAME", userToken.getShopName());
            }
            if (stockModelID != null) {
            request.set("STOCK_MODEL_ID", stockmodel.getStockModelId());
            request.set("STOCK_MODEL_NAME", stockmodel.getName());
            } else {
                request.set("STOCK_MODEL_ID", "");
                request.set("STOCK_MODEL_NAME", "");
            }
            request.set("STATE_ID", reportChangeGoodForm.getStateId());
            if (subChannelTypeId != null) {
                request.set("SUB_CHANNEL_TYPE_ID", subChannelTypeId);
            }
            if (reportChangeGoodForm.getStateId().equals(Constant.STATE_NEW)) {
                request.set("NAME_STATUS", "Hàng mới");
            } else {
                request.set("NAME_STATUS", "Hàng hỏng");
            }
            request.set("REPORT_TYPE", reportChangeGoodForm.getReportType());
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_GENERAL_STOCK_MODEL);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null
                    && response.get(ReportConstant.RESULT_FILE) != null
                    && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportAccountMessage", "reportRevenue.reportRevenueMessage");
            } else {
                req.setAttribute("resultStockImpExpRpt", "report.warning.noResult");
            }

        } catch (Exception ex) {
            req.setAttribute("resultStockImpExpRpt", "stock.report.general.undefine");
            ex.printStackTrace();
        }
        log.debug("# End method ReportGeneralStockModel.exportReport");
        return pageForward;
    }

    //Tao bao cao
    public String exportReportStockTotalDetail() throws Exception {
        /** Action common object */
        log.debug("# Begin method ReportGeneralStockModel.exportReport");
        HttpServletRequest req = getRequest();
        pageForward = "ReportStockTotalDeital";
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            //Lay ten stockmodel            
            String shopCode = reportChangeGoodForm.getShopCode();
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            if (shop == null) {
//                req.setAttribute("resultStockImpExpRpt", "Mã đơn vị không chính xác");
                req.setAttribute("resultStockImpExpRpt", "ERR.RET.031");
                return pageForward;
            }
            reportChangeGoodForm.setShopId(shop.getShopId());
            boolean channelShop = reportChangeGoodForm.isChannelShop();
            boolean channelCTV = reportChangeGoodForm.isChannelCTV();
            boolean channelAgent = reportChangeGoodForm.isChannelAgent();
            //trung dh3
            Long subChannelTypeId=reportChangeGoodForm.getSubChannelTypeId();
            if (!channelShop && !channelCTV && !channelAgent) {
                req.setAttribute("resultStockImpExpRpt", "ERR.RET.060");
                return pageForward;
            }
            ViettelMsg request = new OriginalViettelMsg();
            AppParamsDAO appParamsDAO = new AppParamsDAO();
            appParamsDAO.setSession(getSession());
            String getDatabase = appParamsDAO.findValueAppParams("GET_DATABASE_ONLINE", String.valueOf(ReportConstant.IM_REPORT_STOCK_TOTAL_DETAIL));
            if (getDatabase == null) {
                request.set("DATABASE_ONLINE", "1");
            } else {
                if (!getDatabase.equals("1")) {
                    request.set("DATABASE_ONLINE", "0");
                } else {
                    request.set("DATABASE_ONLINE", "1");

                }
            }
            String sql = "From Shop where parentShopId = ? and channelTypeId = 1";
            Query sqlQuery = getSession().createQuery(sql);
            sqlQuery.setParameter(0, shop.getShopId());
            List<Shop> list = sqlQuery.list();
            if (list != null && list.size() > 0) {
                request.set("CHECK_SELECT", "true");
            } else {
                request.set("CHECK_SELECT", "false");
            }
            request.set("CHANNEL_SHOP", channelShop);
            request.set("CHANNEL_CTV", channelCTV);
            request.set("CHANNEL_AGENT", channelAgent);
            request.set("USER_NAME", userToken.getLoginName());
            if(subChannelTypeId!=null &&  subChannelTypeId !=0L){
                    request.set("SUB_CHANNEL_TYPE_ID", subChannelTypeId);
            }
            if (shop != null) {
                request.set("SHOP_ID", shop.getShopId());
                request.set("SHOP_NAME", shop.getShopCode() + " - " + shop.getName());
                request.set("SHOP_ADDRESS", shop.getAddress());
            } else {
                request.set("SHOP_ID", userToken.getShopId());
                request.set("SHOP_NAME", userToken.getShopCode() + " - " + userToken.getShopName());
            }
            request.set("STATE_ID", reportChangeGoodForm.getStateId());
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_STOCK_TOTAL_DETAIL);
            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null
                    && response.get(ReportConstant.RESULT_FILE) != null
                    && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportAccountMessage", "reportRevenue.reportRevenueMessage");
            } else {
                req.setAttribute("resultStockImpExpRpt", "report.warning.noResult");
            }

        } catch (Exception ex) {
            req.setAttribute("resultStockImpExpRpt", "stock.report.general.undefine");
            ex.printStackTrace();
        }
        log.debug("# End method ReportGeneralStockModel.exportReport");
        return pageForward;
    }

    public List<ReportGeneralStockModelBean> GetListStockModel(Long StockModelID) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String queryStringDetail = "";
        queryStringDetail += "SELECT     SYS_CONNECT_BY_PATH (shop_id, '___') AS tree,";
        queryStringDetail += " LPAD (' ', 6 * (LEVEL - 1)) || shopcode || ' - ' || NAME AS treename, NAME AS nameshoporstaff,";
        queryStringDetail += " LEVEL AS leveltree, shop_id AS shopid,";
        queryStringDetail += " shop_path || '_' AS shoppath, quantity AS quantity,";
        queryStringDetail += " quantityissue AS quantityissue, stateid as stateid";
        queryStringDetail += " FROM (SELECT sh.shop_id, sh.NAME, sh.parent_shop_id, sh.shop_path,";
        queryStringDetail += " st.quantity AS quantity,";
        queryStringDetail += " st.quantity_issue AS quantityissue, st.state_id as stateid , sh.shop_code as shopcode";
        queryStringDetail += " FROM shop sh, stock_total st, channel_type ch";
        queryStringDetail += " WHERE sh.shop_id = st.owner_id";
        queryStringDetail += " AND st.owner_type = 1";
        queryStringDetail += " AND st.stock_model_id = ?";
        queryStringDetail += " AND sh.channel_type_id = ch.channel_type_id";
        queryStringDetail += " AND (ch.object_type <>1 or ch.is_vt_unit <>2)";
        queryStringDetail += " AND st.state_id = ?";
        queryStringDetail += " AND sh.shop_id <> ?";
        //queryStringDetail += " AND st.quantity <> 0";
        queryStringDetail += " UNION";
        queryStringDetail += " SELECT sh.shop_id, sh.NAME, sh.parent_shop_id, sh.shop_path,";
        queryStringDetail += " DECODE (st.quantity, NULL, 0, st.quantity) AS quantity,";
        queryStringDetail += " DECODE (st.quantity_issue,";
        queryStringDetail += " NULL, 0,";
        queryStringDetail += " st.quantity_issue";
        queryStringDetail += " ) AS quantityissue,";
        queryStringDetail += " st.state_id AS stateid, sh.shop_code AS shopcode";
        queryStringDetail += " FROM shop sh,";
        queryStringDetail += " (SELECT *";
        queryStringDetail += " FROM stock_total st";
        queryStringDetail += " WHERE 1 = 1";
        queryStringDetail += " AND (st.owner_type = 1 OR st.owner_type IS NULL)";
        queryStringDetail += " AND (   st.stock_model_id = ?";
        queryStringDetail += " OR st.stock_model_id IS NULL";
        queryStringDetail += " )";
        queryStringDetail += " AND (st.state_id = ? OR st.state_id IS NULL)) st,";
        queryStringDetail += " channel_type ch";
        queryStringDetail += " WHERE sh.shop_id = st.owner_id(+)";
        queryStringDetail += " AND sh.channel_type_id = ch.channel_type_id";
        queryStringDetail += " AND (ch.object_type <> 1 OR ch.is_vt_unit <> 2)";
        queryStringDetail += " AND sh.shop_id = ?";
        queryStringDetail += " UNION";
        queryStringDetail += " SELECT -staff.staff_id AS shopid, staff.NAME,";
        queryStringDetail += " staff.shop_id AS parent_shop_id,";
        queryStringDetail += " shop.shop_path || '_' || staff_id AS shop_path,";
        queryStringDetail += " st.quantity AS quantity,";
        queryStringDetail += " st.quantity_issue AS quantityissue, st.state_id as stateid , staff.staff_code as shopcode";
        queryStringDetail += " FROM staff, shop, stock_total st";
        queryStringDetail += " WHERE staff.shop_id = shop.shop_id";
        queryStringDetail += " AND staff.staff_id = st.owner_id";
        queryStringDetail += " AND st.owner_type = 2";
        queryStringDetail += " AND staff.channel_type_id = 14";
        queryStringDetail += " AND st.stock_model_id = ?";
        queryStringDetail += " AND st.quantity <> 0 ";
        queryStringDetail += " AND st.state_id = ?";
        queryStringDetail += " ) tmp";
        queryStringDetail += " START WITH shop_id = ?";
        queryStringDetail += " CONNECT BY PRIOR shop_id = parent_shop_id";
        queryStringDetail += " ORDER SIBLINGS BY shop_id";
        Query queryDetail;
        queryDetail = getSession().createSQLQuery(queryStringDetail.toString()).
                addScalar("tree", Hibernate.STRING).
                addScalar("treename", Hibernate.STRING).
                addScalar("nameshoporstaff", Hibernate.STRING).
                addScalar("leveltree", Hibernate.STRING).
                addScalar("shopid", Hibernate.LONG).
                addScalar("shoppath", Hibernate.STRING).
                addScalar("quantity", Hibernate.LONG).
                addScalar("quantityissue", Hibernate.LONG).
                addScalar("stateid", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(ReportGeneralStockModelBean.class));
        queryDetail.setParameter(0, StockModelID);
        queryDetail.setParameter(1, reportChangeGoodForm.getStateId());
        if (reportChangeGoodForm.getShopId() != null && !reportChangeGoodForm.getShopId().equals(0L)) {
            queryDetail.setParameter(2, reportChangeGoodForm.getShopId());
        } else {
            queryDetail.setParameter(2, userToken.getShopId());
        }
        queryDetail.setParameter(3, StockModelID);
        queryDetail.setParameter(4, reportChangeGoodForm.getStateId());
        if (reportChangeGoodForm.getShopId() != null && !reportChangeGoodForm.getShopId().equals(0L)) {
            queryDetail.setParameter(5, reportChangeGoodForm.getShopId());
        } else {
            queryDetail.setParameter(5, userToken.getShopId());
        }
        queryDetail.setParameter(6, StockModelID);
        queryDetail.setParameter(7, reportChangeGoodForm.getStateId());
        if (reportChangeGoodForm.getShopId() != null && !reportChangeGoodForm.getShopId().equals(0L)) {
            queryDetail.setParameter(8, reportChangeGoodForm.getShopId());
        } else {
            queryDetail.setParameter(8, userToken.getShopId());
        }
        List listStock = queryDetail.list();
        return listStock;
    }

    private List<ReportGeneralStockModelBean> reSumTotal(List<ReportGeneralStockModelBean> listStock) {
        if (listStock == null || listStock.size() == 0) {
            return listStock;
        }
        Long reSum = 0L;
        ReportGeneralStockModelBean stockmodeladd = new ReportGeneralStockModelBean();
        stockmodeladd.setShoppath("-1");
        for (int i = 0; i < listStock.size() - 1; i += 1) {
            reSum = listStock.get(i).getQuantity();
            for (int j = i + 1; j < listStock.size(); j += 1) {
                if (listStock.get(j).getShoppath().indexOf(listStock.get(i).getShoppath()) != -1) {
                    if (listStock.get(j) == null || listStock.get(j).getQuantity() == null) {
                        System.out.println("nul;");
                    } else {
                        reSum += listStock.get(j).getQuantity();
                    }


                }
            }
            listStock.get(i).setQuantityTotal(reSum);
        }
        listStock.get(listStock.size() - 1).setQuantityTotal(listStock.get(listStock.size() - 1).getQuantity());
        return listStock;
    }
}
