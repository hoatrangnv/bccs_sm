/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.StockDepositForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.database.BO.UserToken;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.StockModel;
import java.util.ArrayList;
import org.hibernate.Query;

/**
 *
 * @author hanhnt80_s
 */
public class ReportMaxDebitCatalogueDAO extends BaseDAOAction {

    private static String Report_Max_Debit_Catalogue = "reportMaxDebitCatalogue";
    private static String pageForward;
    private StockDepositForm stockDepositForm = new StockDepositForm();
    private ImSearchBean imSearchBean = new ImSearchBean();

    public StockDepositForm getStockDepositForm() {
        return stockDepositForm;
    }

    public void setStockDepositForm(StockDepositForm stockDepositForm) {
        this.stockDepositForm = stockDepositForm;
    }

    public String preparePage() throws Exception {
        /** Action common object */
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        removeTabSession("listStaffImportFile");

        if (stockDepositForm == null) {
            stockDepositForm = new StockDepositForm();
        }
        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List<ChannelType> listChannelType = ctDao.findByIsVtUnit(Constant.IS_NOT_VT_UNIT);
        req.getSession().setAttribute("listChannelType", listChannelType);

        pageForward = Report_Max_Debit_Catalogue;
        return pageForward;
    }

    public String exportReportMaxDebitCatalogue() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = Report_Max_Debit_Catalogue;
        try {
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            if (userToken == null) {
                req.setAttribute("displayResultMsgClient", "Time out session");
                return pageForward;
            }
            ViettelMsg request = new OriginalViettelMsg();
            request.set("chanel_type_id", stockDepositForm.getChanelTypeId());

            /*neu ma mat hang != null thi check co ton tai hay khong */
            if (stockDepositForm.getStockModelCode() != null && !stockDepositForm.getStockModelCode().trim().equals("")) {
                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(getSession());
                StockModel stockModel = stockModelDAO.getStockModelByCode(stockDepositForm.getStockModelCode().trim());
                if (stockModel == null) {
                    req.setAttribute("displayResultMsgClient", "stock.balance.stockModelNotValid");
                    return pageForward;
                }
                request.set("stock_model_id", stockModel.getStockModelId());
            }
            request.set(ReportConstant.REPORT_KIND, ReportConstant.IM_REPORT_MAX_DEBIT_CATALOGUE);
            ViettelMsg response = ReportRequestSender.sendRequest(request);
            if (response != null
                    && response.get(ReportConstant.RESULT_FILE) != null
                    && !"".equals(response.get(ReportConstant.RESULT_FILE).toString())) {
                //req.setAttribute("filePath", response.get(ReportConstant.RESULT_FILE).toString());
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute("filePath", downloadDAO.getFileNameEncNew(response.get(ReportConstant.RESULT_FILE).toString(), req.getSession()));
                req.setAttribute("reportStockImportPendingMessage", "reportRevenue.reportRevenueMessage");
                System.out.println(response.get(ReportConstant.RESULT_FILE).toString());
            } else {
                req.setAttribute("displayResultMsgClient", "report.warning.noResult");
                return pageForward;
            }
        } catch (Exception ex) {
            req.setAttribute("displayResultMsgClient", "stock.report.impExp.error.undefine");
            ex.printStackTrace();
        }
        return pageForward;
    }

    public List<ImSearchBean> getListStockModel(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        List listParameter1 = new ArrayList();

        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> listImSearchBean = query1.list();

        return listImSearchBean;
    }

    /**
     *
     * author : tamdt1 date : 21/01/2009 purpose : lay danh sach mat hang
     *
     */
    public Long getListStockModelSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        Long result = 0L;
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() == 1) {
            if (tmpList1.get(0) != null) {
                result = tmpList1.get(0);
            }
        }

        return result;
    }

    /**
     *
     * author : tamdt1 date : 18/11/2009 purpose : lay ten mat hang
     *
     */
    public List<ImSearchBean> getStockModelName(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        //lay ten cua mat hang dua tren code
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and lower(a.stockModelCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        listImSearchBean = query1.list();
        return listImSearchBean;
    }
}
