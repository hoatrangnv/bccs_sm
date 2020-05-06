/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author User
 */
import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ChangeGoodBeanDetail;
import com.viettel.im.client.bean.ChangeGoodBeanGenaral;
import com.viettel.im.client.bean.ChangeGoodSerialBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.ViewDepositGeneralBean;
import com.viettel.im.client.form.ReportDepositForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.ViewDepositGeneral;
import com.viettel.im.database.BO.ViewDepositShop;
import com.viettel.im.database.BO.ViewDepostiStaff;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

public class ReportDepositDAO extends BaseDAOAction
{

    private static final Log log = LogFactory.getLog(ReportDepositDAO.class);    
    private ReportDepositForm reportDepositForm = new ReportDepositForm();
    private static Long SHOP_VT_ID = 7282L;
    private static String REPORT_DEPOSIT_BRANCH = "ReportDepositBranch";
    private static String pageForward;

    public ReportDepositForm getReportDepositForm()
    {
        return reportDepositForm;
    }

    public void setReportDepositForm(ReportDepositForm reportDepositForm)
    {
        this.reportDepositForm = reportDepositForm;
    }

    public String preparePage() throws Exception
    {
        /** Action common object */
        log.debug("# Begin method ReportChangeGood.preparePage");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "ReportDeposit";
        pageForward  = REPORT_DEPOSIT_BRANCH;
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat dateFomat = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        reportDepositForm.setToDate(dateFomat.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // substract 1 month
        reportDepositForm.setFromDate(dateFomat.format(cal.getTime()));
        reportDepositForm.setReportDetail(0L);

        reportDepositForm.setShopCodeBranch(userToken.getShopCode());
        reportDepositForm.setShopNameBranch(userToken.getShopName());

        reportDepositForm.setReportDetail(1L);

        log.debug("# End method ReportChangeGood.preparePage");
        return pageForward;
    }

    //Tao bao cao
    public String exportReportDepositReport() throws Exception
    {
        /** Action common object */
        log.debug("# Begin method ReportReportChangeGoodDAO.exportReportChangeGoodReport");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        pageForward = "ReportDeposit";
        try
        {
            String shopCode = reportDepositForm.getShopCode();
            String shopCodeBranch = reportDepositForm.getShopCodeBranch();
            if(shopCodeBranch == null || "".equals(shopCodeBranch.trim()))
            {
//                req.setAttribute("displayResultMsgClient", "Chưa chọn mã đơn vị");
                req.setAttribute("displayResultMsgClient", "ERR.RET.034");
                return pageForward;
            }
            if((shopCode == null || "".equals(shopCode.trim())) &&
                    (reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_SET) || reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_GET)))
            {
//                req.setAttribute("displayResultMsgClient", "Chưa chọn mã đơn vị");
                req.setAttribute("displayResultMsgClient", "ERR.RET.034");
                return pageForward;
            }
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
            Shop shopVT = shopDAO.findById(SHOP_VT_ID);
            Shop shopCN = new Shop();
            List listParameter1 = new ArrayList();
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.database.BO.Shop(a.shopId, a.name) ");
            strQuery1.append("from Shop a,Area ar ");
            strQuery1.append("where 1 = 1 ");
            strQuery1.append("and a.province = ar.province ");
            strQuery1.append("and ar.parentCode is null and a.status = 1 ");
            strQuery1.append("and a.parentShopId = 7282 and a.channelTypeId = 1 and a.shopType = 2 ");
            //strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
            //listParameter1.add("%_" + userToken.getShopId() + "_%");
            //listParameter1.add(userToken.getShopId());
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add(shopCodeBranch.trim().toLowerCase());
            Query query1 = getSession().createQuery(strQuery1.toString());
            for(int i = 0; i < listParameter1.size(); i++)
            {
                query1.setParameter(i, listParameter1.get(i));
            }
            List<Shop> tmpList1 = query1.list();
            if(tmpList1 == null || tmpList1.size() == 0)
            {
//                req.setAttribute("displayResultMsgClient", "Mã đơn vị không chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.031");
                return pageForward;
            }else
            {
                shopCN = tmpList1.get(0);
            }
            if(shop == null &&
                    (reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_SET) || reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_GET)))
            {
//                req.setAttribute("displayResultMsgClient", "Mã đơn vị không chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.031");
                return pageForward;
            }
            String sFromDate = reportDepositForm.getFromDate();
            String sToDate = reportDepositForm.getToDate();
            if(sFromDate == null || "".equals(sFromDate.trim()))
            {
//                req.setAttribute("displayResultMsgClient", "Chưa chọn từ ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.022");
                return pageForward;
            }
            if(sToDate == null || "".equals(sToDate.trim()))
            {
//                req.setAttribute("displayResultMsgClient", "Chưa chọn đến ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.023");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try
            {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            }catch(Exception ex)
            {
//                req.setAttribute("displayResultMsgClient", "Từ ngày chưa chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.024");
                return pageForward;
            }
            try
            {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            }catch(Exception ex)
            {
//                req.setAttribute("displayResultMsgClient", "Đến ngày không chính xác");
                req.setAttribute("displayResultMsgClient", "ERR.RET.025");
                return pageForward;
            }
            if(fromDate.after(toDate))
            {
//                req.setAttribute("displayResultMsgClient", "Từ ngày không được lớn hơn đến ngày");
                req.setAttribute("displayResultMsgClient", "ERR.RET.026");
                return pageForward;
            }

            if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
                req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.fromDateToDateNotSame");
                return pageForward;
            }

            /*TuanPV - 25/02/2010 - Comment để chuyển sang mô hình ReportServer*/
            /*
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            String prefixPath = "Deposit_report";
            List<ViewDepositShop> listViewDepositShop = new ArrayList<ViewDepositShop>();
            List<ViewDepostiStaff> listViewDepositStaff = new ArrayList<ViewDepostiStaff>();
            List<ViewDepositGeneralBean> listViewDepositGeneral = new ArrayList<ViewDepositGeneralBean>();

            //Giao dich xuat
            String sql;
            Query query;
            Date toDateS = DateTimeUtils.addDate(toDate, 1);
            if(reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_SET))
            {
                templatePath = tmp + prefixPath + "_set.xls";
                filePath += prefixPath + "_set_" + date + ".xls";
                sql = "From ViewDepositShop where type = ? and shopId = ? and createdate >= ? and createdate <=?";
                query = getSession().createQuery(sql);
                query.setParameter(0, "1");
                query.setParameter(1, shop.getShopId());
                query.setParameter(2, fromDate);
                query.setParameter(3, toDateS);
                listViewDepositShop = query.list();

            }
            if(reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_GET))
            {
                templatePath = tmp + prefixPath + "_get.xls";
                filePath += prefixPath + "_get_" + date + ".xls";
                sql = "From ViewDepositShop where type = ? and shopId = ? and createdate >= ? and createdate <=?";
                query = getSession().createQuery(sql);
                query.setParameter(0, "2");
                query.setParameter(1, shop.getShopId());
                query.setParameter(2, fromDate);
                query.setParameter(3, toDateS);
                listViewDepositShop = query.list();
            }
            if(reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_STAFF))
            {
                templatePath = tmp + prefixPath + "_staff.xls";
                filePath += prefixPath + "_staff_" + date + ".xls";
                if(shop == null)
                {
                    sql = "From ViewDepostiStaff where shopPath like ? and createdate >= ? and createdate <=?";
                    sql += " order by nlssort(nameshop, 'nls_sort=Vietnamese') asc,createdate desc ";
                    query = getSession().createQuery(sql);
                    query.setParameter(0, "%_" + shopCN.getShopId() + "_%");
                    query.setParameter(1, fromDate);
                    query.setParameter(2, toDateS);
                    listViewDepositStaff = query.list();
                }else
                {
                    sql = "From ViewDepostiStaff where shopPath like ? and createdate >= ? and createdate <=? and shopId = ?";
                    sql += " order by nlssort(nameshop, 'nls_sort=Vietnamese') asc,createdate desc ";
                    query = getSession().createQuery(sql);
                    query.setParameter(0, "%_" + shopCN.getShopId() + "_%");
                    query.setParameter(1, fromDate);
                    query.setParameter(2, toDateS);
                    query.setParameter(3, shop.getShopId());
                    listViewDepositStaff = query.list();
                }

            }
            if(reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_GENERAL))
            {
                templatePath = tmp + prefixPath + "_general.xls";
                filePath += prefixPath + "_general_" + date + ".xls";
                sql = " select dp.shop_id as shopId,to_date(dp.create_date) as createdate,sh.name as shopname,dp.branch_id as branchId ";
                sql += " ,sum(decode(dp.type,1,dp.amount,0)) as  amountset";
                sql += " ,sum(decode(dp.type,2,dp.amount,0)) as amountget";
                sql += " ,sh.shop_path as shopPath";
                sql += " from deposit dp,shop sh";
                sql += " where 1 = 1";
                sql += " and dp.shop_id = sh.shop_id";
                sql += " and dp.deposit_type_id = 2";
                sql += " and dp.receipt_id is not null";
                sql += " and sh.shop_path like ?";
                sql += " and dp.create_date >= ?";
                sql += " and dp.create_date <= ?";
                if(shop != null)
                {
                    sql += " and dp.shop_id = ?";
                }
                sql += " group by dp.shop_id,to_date(dp.create_date),sh.name,dp.branch_id,sh.shop_path";
                sql += " order by nlssort(sh.name, 'nls_sort=Vietnamese') asc,to_date(dp.create_date) desc ";
                query = getSession().createSQLQuery(sql).
                        addScalar("shopId", Hibernate.LONG).
                        addScalar("createdate", Hibernate.DATE).
                        addScalar("shopname", Hibernate.STRING).
                        addScalar("branchId", Hibernate.LONG).
                        addScalar("amountset", Hibernate.LONG).
                        addScalar("amountget", Hibernate.LONG).
                        addScalar("shopPath", Hibernate.STRING).
                        setResultTransformer(Transformers.aliasToBean(ViewDepositGeneralBean.class));
                query.setParameter(0, "%_" + shopCN.getShopId() + "_%");
                query.setParameter(1, fromDate);
                query.setParameter(2, toDateS);
                if(shop != null)
                {
                    query.setParameter(3, shop.getShopId());
                }
                listViewDepositGeneral = query.list();
            }

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            beans.put("fromDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(sFromDate)));
            beans.put("toDate", simpleDateFormat.format(DateTimeUtils.convertStringToDate(sToDate)));
            //beans.put("fromDate", fromDate);
            //beans.put("toDate", toDate);
            if(shop != null)
            {
                beans.put("shopName", shop.getName());
            }
            beans.put("branchName", shopCN.getName());
            //beans.put("shopAddress", shop.getAddress());
            if(reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_SET))
            {
                beans.put("lstItems", listViewDepositShop);
                XLSTransformer transformer = new XLSTransformer();
                transformer.transformXLS(templateRealPath, beans, realPath);
                req.setAttribute("filePath", filePath);

            }else
            {
                if(reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_GET))
                {
                    beans.put("lstItems", listViewDepositShop);
                    XLSTransformer transformer = new XLSTransformer();
                    transformer.transformXLS(templateRealPath, beans, realPath);
                    req.setAttribute("filePath", filePath);
                }else
                {
                    if(reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_STAFF))
                    {
                        beans.put("lstItems", listViewDepositStaff);
                        XLSTransformer transformer = new XLSTransformer();
                        transformer.transformXLS(templateRealPath, beans, realPath);
                        req.setAttribute("filePath", filePath);
                    }else
                    {
                        if(reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_GENERAL))
                        {
                            beans.put("lstItems", listViewDepositGeneral);
                            XLSTransformer transformer = new XLSTransformer();
                            transformer.transformXLS(templateRealPath, beans, realPath);
                            req.setAttribute("filePath", filePath);
                        }

                    }

                }
            }
            //XLSTransformer transformer = new XLSTransformer();
            //transformer.transformXLS(templateRealPath, beans, realPath);
            End TuanPV Comment*/

            /*TuanPV - 25/02/2010 - Gửi lệnh xuất báo cáo sang app ReportServer*/
            ViettelMsg request = new OriginalViettelMsg();
            request.set("FROM_DATE", sFromDate);
            request.set("TO_DATE", sToDate);
            request.set("USER_NAME", userToken.getLoginName());
            request.set("REPORT_DETAIL", reportDepositForm.getReportDetail());
            
            if(shop != null)
            {
                request.set("SHOP_ID", shop.getShopId());
                request.set("SHOP_NAME", shop.getName());
                request.set("SHOP_ADDRESS", shop.getAddress());
            }
            if(reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_STAFF)
                    || reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_GENERAL))
            {
                if(shopCN != null)
                {
                    request.set("SHOP_PATH", "%_" + shopCN.getShopId() + "_%");
                }
            }
            if(shopCN != null)
            {
                request.set("BRANCH_NAME", shopCN.getName());
            }
            if(reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_SET))
            {
                request.set("TYPE", "1");
            }else if(reportDepositForm.getReportDetail().equals(Constant.REPORT_DEPOSIT_GET))
            {
                request.set("TYPE", "2");
            }

            
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_DEPOSIT);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if(response != null 
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString()))
            {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportAccountMessage", "reportRevenue.reportRevenueMessage");
            }else
            {
                req.setAttribute("displayResultMsgClient", "report.warning.noResult");
            }

        }catch(Exception ex)
        {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        log.debug("# End method ReportReportChangeGoodDAO.exportReportChangeGoodReport");
        return pageForward;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach chi nhanh
     *
     */
    public List<ImSearchBean> getListBranch(ImSearchBean imSearchBean)
    {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a,Area ar ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and a.province = ar.province ");
        strQuery1.append("and ar.parentCode is null and a.status = 1 ");
        strQuery1.append("and a.parentShopId = 7282 and a.channelTypeId = 1 and a.shopType = 2 ");
        //strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        //listParameter1.add("%_" + userToken.getShopId() + "_%");
        //listParameter1.add(userToken.getShopId());
        if(imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals(""))
        {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if(imSearchBean.getName() != null && !imSearchBean.getName().trim().equals(""))
        {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for(int i = 0; i < listParameter1.size(); i++)
        {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if(tmpList1 != null && tmpList1.size() > 0)
        {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : Lay ten CN
     *
     */
    public List<ImSearchBean> getNameBranch(ImSearchBean imSearchBean)
    {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a,Area ar ");
        strQuery1.append("where 1 = 1 ");
        strQuery1.append("and a.province = ar.province ");
        strQuery1.append("and ar.parentCode is null and a.status = 1 ");
        strQuery1.append("and a.parentShopId = 7282 and a.channelTypeId = 1 and a.shopType = 2 ");
        //strQuery1.append("and (a.shopPath like ? or a.shopId = ?) ");
        //listParameter1.add("%_" + userToken.getShopId() + "_%");
        //listParameter1.add(userToken.getShopId());
        if(imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals(""))
        {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }else
        {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add("");
        }

        if(imSearchBean.getName() != null && !imSearchBean.getName().trim().equals(""))
        {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for(int i = 0; i < listParameter1.size(); i++)
        {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if(tmpList1 != null && tmpList1.size() > 0)
        {
            listImSearchBean.addAll(tmpList1);
        }
        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay danh sach cac kho
     *
     */
    public List<ImSearchBean> getListShop(ImSearchBean imSearchBean)
    {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Long shopId;

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

        if(imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals(""))
        {
            StringBuffer sql = new StringBuffer("select new com.viettel.im.database.BO.Shop(a.shopId, a.shopCode) ");
            sql.append("from Shop a,Area ar ");
            sql.append("where 1 = 1 ");
            sql.append("and a.province = ar.province ");
            sql.append("and ar.parentCode is null ");
            sql.append("and a.parentShopId = 7282 and a.channelTypeId = 1 and a.shopType = 2 ");
            sql.append("and a.shopCode = ?");
            Query query = getSession().createQuery(sql.toString());
            query.setParameter(0, imSearchBean.getOtherParamValue().trim());
            List<Shop> list = query.list();
            if(list != null && list.size() > 0)
            {
                shopId = list.get(0).getShopId();
            }else
            {
                return listImSearchBean;
            }

        }else
        {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }
        strQuery1.append("and a.shopPath like ? ");
        listParameter1.add("%_" + shopId + "_%");
        strQuery1.append("and a.shopId <> ? and a.status =? ");
        listParameter1.add(shopId);
        listParameter1.add(Constant.STATUS_ACTIVE);
        strQuery1.append(" and a.channelTypeId = 1 ");
        if(imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals(""))
        {
            strQuery1.append("and lower(a.shopCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if(imSearchBean.getName() != null && !imSearchBean.getName().trim().equals(""))
        {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for(int i = 0; i < listParameter1.size(); i++)
        {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if(tmpList1 != null && tmpList1.size() > 0)
        {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    /**
     *
     * author   : tamdt1
     * date     : 18/11/2009
     * purpose  : lay ten shop
     *
     */
    public List<ImSearchBean> getNameShop(ImSearchBean imSearchBean)
    {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        Long shopId;

        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.shopCode, a.name) ");
        strQuery1.append("from Shop a ");
        strQuery1.append("where 1 = 1 ");

        if(imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals(""))
        {
            StringBuffer sql = new StringBuffer("select new com.viettel.im.database.BO.Shop(a.shopId, a.shopCode) ");
            sql.append("from Shop a,Area ar ");
            sql.append("where 1 = 1 ");
            sql.append("and a.province = ar.province ");
            sql.append("and ar.parentCode is null ");
            sql.append("and a.parentShopId = 7282 and a.channelTypeId = 1 and a.shopType = 2 ");
            sql.append("and a.shopCode = ?");
            Query query = getSession().createQuery(sql.toString());
            query.setParameter(0, imSearchBean.getOtherParamValue().trim());
            List<Shop> list = query.list();
            if(list != null && list.size() > 0)
            {
                shopId = list.get(0).getShopId();
            }else
            {
                return listImSearchBean;
            }

        }else
        {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }
        strQuery1.append("and a.shopPath like ? ");
        listParameter1.add("%_" + shopId + "_%");
        strQuery1.append("and a.shopId <> ? and a.status =? ");
        listParameter1.add(shopId);
        listParameter1.add(Constant.STATUS_ACTIVE);
        strQuery1.append(" and a.channelTypeId = 1 ");

        if(imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals(""))
        {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        }else
        {
            strQuery1.append("and lower(a.shopCode) = ? ");
            listParameter1.add("");
        }

        if(imSearchBean.getName() != null && !imSearchBean.getName().trim().equals(""))
        {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for(int i = 0; i < listParameter1.size(); i++)
        {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if(tmpList1 != null && tmpList1.size() > 0)
        {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }



    public String exportReportDepositBranch() throws Exception
    {
        log.debug("# Begin method ReportDepositDAO.exportReportDepositBranch");
        HttpServletRequest req = getRequest();
        pageForward = REPORT_DEPOSIT_BRANCH;
        try
        {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

            Shop shop = new Shop();
            Shop parentShop = new Shop();

            if (!(reportDepositForm.getReportDetail() != null && reportDepositForm.getReportDetail().toString().equals("3"))){
                
           
                String shopCodeBranch = reportDepositForm.getShopCodeBranch();
                if (shopCodeBranch == null || "".equals(shopCodeBranch.trim())) {
                    req.setAttribute("displayResultMsgClient", "ERR.RET.034");
                    return pageForward;
                }

                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                List lstParam = new ArrayList();
                StringBuffer queryString = new StringBuffer();
                queryString.append(" from Shop where lower(shopCode) = ? and status = ?  ");
                lstParam.add(shopCodeBranch.trim().toLowerCase());
                lstParam.add(Constant.STATUS_USE);
                queryString.append("    AND (shopPath LIKE (?) escape '$' OR shopId = ?) ");
                lstParam.add("%$_" + userToken.getShopId().toString() + "$_%");
                lstParam.add(userToken.getShopId());

                Query query = getSession().createQuery(queryString.toString());
                for (int i = 0; i < lstParam.size(); i++) {
                    query.setParameter(i, lstParam.get(i));
                }
                List<Shop> tmpList = query.list();
                if (tmpList == null || tmpList.size() == 0) {
                    req.setAttribute("displayResultMsgClient", "ERR.RET.053");
                    return pageForward;
                }
                shop = tmpList.get(0);

                //KIEM TRA CO PHAI LA CUA HANG HAY KHONG
                lstParam = new ArrayList();
                queryString = new StringBuffer(" select a.shopId from Shop a, ChannelType b where 1=1 and a.channelTypeId = b.channelTypeId and a.status= ? and a.parentShopId = ? and b.isVtUnit = ? ");
                lstParam.add(Constant.STATUS_USE);
                lstParam.add(shop.getShopId());
                lstParam.add(Constant.IS_VT_UNIT);

                Query tmpQuery = getSession().createQuery(queryString.toString());

                for (int i = 0; i < lstParam.size(); i++) {
                    tmpQuery.setParameter(i, lstParam.get(i));
                }
                List l = tmpQuery.list();
                if (l == null || l.size() == 0) {//NEU LA CUA HANG
                    parentShop = shopDAO.findById(shop.getParentShopId());//THONG TIN CHI NHANH
                }            
            }
            
            
            String sFromDate = reportDepositForm.getFromDate();
            String sToDate = reportDepositForm.getToDate();
            if(sFromDate == null || "".equals(sFromDate.trim()))
            {
                req.setAttribute("displayResultMsgClient", "ERR.RET.055");
                return pageForward;
            }
            if(sToDate == null || "".equals(sToDate.trim()))
            {
                req.setAttribute("displayResultMsgClient", "ERR.RET.056");
                return pageForward;
            }
            Date fromDate;
            Date toDate;
            try
            {
                fromDate = DateTimeUtils.convertStringToDate(sFromDate);
            }catch(Exception ex)
            {
                req.setAttribute("displayResultMsgClient", "ERR.RET.024");
                return pageForward;
            }
            try
            {
                toDate = DateTimeUtils.convertStringToDate(sToDate);
            }catch(Exception ex)
            {
                req.setAttribute("displayResultMsgClient", "ERR.RET.057");
                return pageForward;
            }
            if(fromDate.after(toDate))
            {
                req.setAttribute("displayResultMsgClient", "ERR.RET.058");
                return pageForward;
            }

            if (reportDepositForm.getReportDetail() != null && reportDepositForm.getReportDetail().toString().equals("3")){
                if(reportDepositForm.getIsdn() == null || reportDepositForm.getIsdn().trim().equals("")) {
                    req.setAttribute("displayResultMsgClient", "ERR.RET.063");
                    return pageForward;
                }
            }
            else{
                if (toDate.getMonth() != fromDate.getMonth() || toDate.getYear() != fromDate.getYear()) {
                    req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.fromDateToDateNotSame");
                    return pageForward;
                }
            }
            
            ViettelMsg request = new OriginalViettelMsg();
            request.set("FROM_DATE", sFromDate);
            request.set("TO_DATE", sToDate);
            request.set("USER_CODE", userToken.getLoginName());
            request.set("REPORT_TYPE", reportDepositForm.getReportDetail());
            request.set("DEPOSIT_TYPE_ID", reportDepositForm.getDepositTypeId());

            if(shop != null)
            {
                if (shop.getParentShopId() == null){
                    request.set("SHOP_VT", true);
                }
                
                request.set("SHOP_ID", shop.getShopId());
                request.set("SHOP_NAME", shop.getName());//TEN CUA HANG (NEU LA CHI NHANH THI TEN CHI NHANH)

            }

            if(parentShop != null && parentShop.getShopId() != null)
            {
                request.set("PARENT_SHOP_NAME", parentShop.getName());//TEN CHI NHANH

                request.set("TYPE_STAFF", true);//CUA HANG - GROUP THEO NHAN VIEN
            }
            
            request.set("ISDN", reportDepositForm.getIsdn());
            
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_DEPOSIT_BRANCH);

            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if(response != null
                && response.get(ReportConstant.RESULT_FILE) != null
                && !"".equals(response.get(ReportConstant.RESULT_FILE).toString()))
            {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportAccountMessage", "reportRevenue.reportRevenueMessage");
            }else
            {
                req.setAttribute("displayResultMsgClient", "report.warning.noResult");
            }

        }catch(Exception ex)
        {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        log.debug("# End method ReportDepositDAO.exportReportDepositBranch");
        return pageForward;
    }    
}
