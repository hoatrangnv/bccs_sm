/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

/**
 *
 * @author lamnt
 */
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ReportStockGTHH;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.UserToken;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.Session;

public class ReportStockGTHHActionDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(ReportChangeGoodDAO.class);
    public String pageForward;
    ArrayList<ReportStockGTHH> lst = new ArrayList<ReportStockGTHH>();

    public String preparePageReportStockGTHH() throws Exception {
        ReportStockGTHH form = new ReportStockGTHH();
        log.debug("# Begin method ReportGeneralStockModel.preparePageReportStockGTHH" + form);
        HttpServletRequest req = getRequest();
        pageForward = "ReportStockGTHH";

        String DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        String fromDate = sdf.format(cal.getTime());
        String toDate = sdf.format(cal.getTime());
        form.setFromDate(fromDate);
        form.setToDate(toDate);
//        GetListStock(fromDate,toDate);
        req.setAttribute("fromDate", fromDate);
        req.setAttribute("toDate", toDate);


        GetListStock("01-08-2017", "15-08-2017");
        req.setAttribute("lst", lst);
        log.debug("# End method preparePageReportStockGTHH start");
        return pageForward;
    }

    public List<ReportStockGTHH> GetListStock(String fromDate, String toDate) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String queryStringDetail = "";
        queryStringDetail += "select distinct(sp.name) ma_kho,b.action_code lenhphieu_xuat,b.username nguoi_xuat, ";
        queryStringDetail += " d.stock_model_code ma_mat_hang,d.name ten_mat_hang, ";
        queryStringDetail += " (CASE ";
        queryStringDetail += "WHEN reason=1  THEN 'Xuat giam tru do thanh ly' ";
        queryStringDetail += "WHEN reason=2  THEN 'Xuat giam tru do sai sot nghiep vu' ";
        queryStringDetail += "WHEN reason=3  THEN 'Xuat giam tru do that thoat nhung da den bu' ";
        queryStringDetail += "WHEN reason=4  THEN 'Xuat giam tru do tiêu huy hang hoa' ";
        queryStringDetail += "WHEN reason=5  THEN 'Xuat giam tru do trao, bieu, tang khach hang' ";
        queryStringDetail += "WHEN reason=6  THEN 'Xuat giam tru do bán cho doi tac' ";
        queryStringDetail += "WHEN reason=7  THEN 'Ly do khac' ";
        queryStringDetail += "End) AS Reason,b.ma_to_trinh";
        queryStringDetail += " from ";
        queryStringDetail += "    (select stock_trans_id,to_owner_id,create_datetime from stock_trans where from_owner_id=7282 ";
        queryStringDetail += "    and to_owner_id=1003583 "
                + " and create_datetime>=trunc(to_date('" + fromDate + "','dd-MM-yyyy')) "
                + " and create_datetime<=trunc(to_date('" + toDate + "','dd-MM-yyyy')) and stock_trans_status=3 ) a, ";
        queryStringDetail += "    (select stock_trans_id,username,action_code,reason,file_upload as ma_to_trinh from stock_trans_action) b, ";
        queryStringDetail += "    (select stock_trans_id,stock_model_id,quantity_res from stock_trans_detail) c, ";
        queryStringDetail += "    (select stock_model_id,stock_model_code,name from stock_model where status=1) d, ";
        queryStringDetail += "    (select stock_model_id,price from price where status=1 and end_date is not null) e, shop sp ";
        queryStringDetail += " where a.stock_trans_id=b.stock_trans_id and b.stock_trans_id=c.stock_trans_id and c.stock_model_id=d.stock_model_id and d.stock_model_id=e.stock_model_id and sp.shop_id= a.to_owner_id ";

        Query queryObject = getSession().createSQLQuery(queryStringDetail.toString());

        List<ReportStockGTHH> listStock = queryObject.list();
        for (int i = 0; i < listStock.size(); i++) {
            lst.add(listStock.get(i));
        }
        return lst;
    }
}
