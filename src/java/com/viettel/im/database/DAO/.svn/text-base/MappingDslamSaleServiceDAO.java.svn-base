/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import static com.viettel.database.DAO.BaseDAOAction.getSafeFileName;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.LookupIsdnForm;
import com.viettel.im.client.form.MappingDslamSaleServiceForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.MappingDslamSaleService;
import com.viettel.im.database.BO.UserToken;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServletRequest;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;

/**
 * 2040811 R6342
 *
 * @author truongnq5
 */
public class MappingDslamSaleServiceDAO extends BaseDAOAction {

    private Log log = LogFactory.getLog(MappingDslamSaleServiceDAO.class);
    private String pageForward;
    private final String REQUEST_LIST_MAPPING = "listMappingDslamSaleService";
    private final String REQUEST_MESSAGE = "messageList";
    private final String RESULT = "resultFile";
    private final String RESULT_ERROR = "resultFileError";
    private MappingDslamSaleServiceForm mappingDslamSaleServiceForm = new MappingDslamSaleServiceForm();
    /* Phan trang tren danh sach hop dong */
    //So luong ban ghi 1 lan lay du lieu trong database;
    private int pageSize = 20;
    private int startRow = 0;
    private String selectedPage = "1";

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public String getSelectedPage() {
        return selectedPage;
    }

    public void setSelectedPage(String selectedPage) {
        this.selectedPage = selectedPage;
    }

    public MappingDslamSaleServiceForm getMappingDslamSaleServiceForm() {
        return mappingDslamSaleServiceForm;
    }

    public void setMappingDslamSaleServiceForm(MappingDslamSaleServiceForm mappingDslamSaleServiceForm) {
        this.mappingDslamSaleServiceForm = mappingDslamSaleServiceForm;
    }

    public String preparePage() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = "preparePage";
        this.mappingDslamSaleServiceForm.setMappingType(2L);
        resetForm();
        return pageForward;
    }

    public List<ImSearchBean> getListDslam(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter = new ArrayList();
        StringBuffer strQuery = new StringBuffer("SELECT new com.viettel.im.client.bean.ImSearchBean(d.code, d.name) FROM Dslam d WHERE 1=1 ");
        if (imSearchBean.getCode() != null && !"".equals(imSearchBean.getCode())) {
            strQuery.append(" and lower(d.code) like lower(?) ");
            listParameter.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !"".equals(imSearchBean.getName())) {
            strQuery.append(" and lower(d.name) like lower(?) ");
            listParameter.add(imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery.append(" and  d.status = 1 ORDER BY d.code ASC ");
        Query query1 = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            query1.setParameter(i, listParameter.get(i));
        }
        List<ImSearchBean> tmpList = query1.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }
        return listImSearchBean;
    }

    public Long getListDslamSize(ImSearchBean imSearchBean) {
        StringBuffer strQuery = new StringBuffer("SELECT count(*) FROM Dslam WHERE status = 1");
        Query query1 = getSession().createQuery(strQuery.toString());
        List<Long> tmpList = query1.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return null;
        }
    }

    public List<ImSearchBean> getListSaleService(ImSearchBean imSearchBean) {
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List listParameter = new ArrayList();
        StringBuffer strQuery = new StringBuffer("SELECT   new com.viettel.im.client.bean.ImSearchBean(s.code, s.name) FROM SaleServices s WHERE 1 = 1 ");
        if (imSearchBean.getCode() != null && !"".equals(imSearchBean.getCode())) {
            strQuery.append(" AND lower(s.code) like lower(?) ");
            listParameter.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !"".equals(imSearchBean.getName())) {
            strQuery.append(" AND lower(s.name) like lower(?) ");
            listParameter.add(imSearchBean.getName().trim().toLowerCase() + "%");
        }
        strQuery.append(" AND s.status = 1 AND s.telecomServicesId > 2 ORDER BY s.code ASC");//--Khong lay cac DVBH di dong, homephone
        Query query1 = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter.size(); i++) {
            query1.setParameter(i, listParameter.get(i));
        }
        List<ImSearchBean> tmpList = query1.list();
        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);
        }
        return listImSearchBean;
    }

    public Long getListSaleServiceSize(ImSearchBean imSearchBean) {
        StringBuffer strQuery = new StringBuffer("SELECT   count(*)   FROM SaleServices s WHERE 1 = 1 AND s.status = 1 AND s.telecomServicesId > 2");//--Khong lay cac DVBH di dong, homephone
        Query query1 = getSession().createQuery(strQuery.toString());
        List<Long> tmpList = query1.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return null;
        }
    }

    public String search() throws Exception {
        HttpServletRequest req = getRequest();
        try {
            List<MappingDslamSaleService> listMappingDslamSaleService =
                    getListMapping(this.mappingDslamSaleServiceForm.getDslamCode(), this.mappingDslamSaleServiceForm.getSaleServiceCode(), this.mappingDslamSaleServiceForm.getStatus());
            req.getSession().removeAttribute(REQUEST_LIST_MAPPING);
            req.getSession().setAttribute(REQUEST_LIST_MAPPING, listMappingDslamSaleService);
//            req.setAttribute(REQUEST_MESSAGE, "MSG.Serch.1" + " " + listMappingDslamSaleService.size() + " " + "MSG.Serch.2");
            List listMessageParam = new ArrayList();
            listMessageParam.add(listMappingDslamSaleService.size());
            req.setAttribute(REQUEST_MESSAGE, "stockIsdnTransManagement.searchMessage");
            req.setAttribute("messageParam", listMessageParam);
            pageForward = "mappingDslamSaleServiceResult";
        } catch (Exception e) {
            e.printStackTrace();
            pageForward = "preparePage";
            throw e;
        }
        return pageForward;
    }

    public String add() throws Exception {
        HttpServletRequest req = getRequest();
        String dslamCode;
        String saleServiceCode;
        Long status = null;
        MappingDslamSaleServiceForm tempForm = new MappingDslamSaleServiceForm();
        int countSuccess = 0;
        try {
            pageForward = "mappingDslamSaleServiceResult";
            if (this.mappingDslamSaleServiceForm.getMappingType() == 2L) {
                dslamCode = this.mappingDslamSaleServiceForm.getDslamCode();
                saleServiceCode = this.mappingDslamSaleServiceForm.getSaleServiceCode();
                status = this.mappingDslamSaleServiceForm.getStatus();
                if (!checkValidateAdd(dslamCode, saleServiceCode, status)) {
                    req.setAttribute(REQUEST_MESSAGE, "MSG.GOD.287");//Cac truong bat buoc khong duoc de trong
                    List<MappingDslamSaleService> listMappingDslamSaleService = getListMapping(null, null, null);
                    req.getSession().removeAttribute(REQUEST_LIST_MAPPING);
                    req.getSession().setAttribute(REQUEST_LIST_MAPPING, listMappingDslamSaleService);//load lai danh sach
                    return pageForward;
                }
                if (addMapping(dslamCode, saleServiceCode)) {
                    resetForm();
                }
                return pageForward;
            } else {
                //Xu ly lay so dong can mapping tu DB
                String sqlNumber = new String("SELECT value FROM app_params WHERE TYPE = 'DSLAM_SALE_SERVICE_MAX_ROW' AND code = 1 and status = 1");
                Query queryNumber = getSession().createSQLQuery(sqlNumber.toString());
                List<String> lst = queryNumber.list();
                Long rowNumber = null;
                try {
                    rowNumber = Long.valueOf(lst.get(0).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    rowNumber = 300L;
                }
                //List result
                List<MappingDslamSaleService> resultList = new ArrayList<MappingDslamSaleService>();
                tempForm = getDataListfromFile(this.mappingDslamSaleServiceForm);
                if (tempForm.getDslamCodeList() == null || tempForm.getDslamCodeList().isEmpty()) {
                    req.setAttribute(REQUEST_MESSAGE, "ERROR.NOT.FOUND.Dslam.in.file");//Không tìm thấy mã trạm nào trong file
                    List<MappingDslamSaleService> listMappingDslamSaleService = getListMapping(null, null, null);
                    req.getSession().removeAttribute(REQUEST_LIST_MAPPING);
                    req.getSession().setAttribute(REQUEST_LIST_MAPPING, listMappingDslamSaleService);//load lai danh sach
                    return pageForward;
                }
                if (tempForm.getSaleServiceCodeList() == null || tempForm.getSaleServiceCodeList().isEmpty()) {
                    req.setAttribute(REQUEST_MESSAGE, "ERROR.NOT.FOUND.SaleService.in.file");//Không tìm thấy mã trạm nào trong file
                    List<MappingDslamSaleService> listMappingDslamSaleService = getListMapping(null, null, null);
                    req.getSession().removeAttribute(REQUEST_LIST_MAPPING);
                    req.getSession().setAttribute(REQUEST_LIST_MAPPING, listMappingDslamSaleService);//load lai danh sach
                    return pageForward;
                }
                int sizeList = tempForm.getDslamCodeList().size();
                if (tempForm.getDslamCodeList().size() < tempForm.getSaleServiceCodeList().size()) {
                    sizeList = tempForm.getDslamCodeList().size();
                }
                if (sizeList <= rowNumber) {
                    for (int i = 0; i < sizeList; i++) {
                        MappingDslamSaleService mappingDslamSaleService = new MappingDslamSaleService();
                        if (addMapping(tempForm.getDslamCodeList().get(i).toString().trim(), tempForm.getSaleServiceCodeList().get(i).toString().trim())) {
                            countSuccess++;
                            mappingDslamSaleService.setDslam(tempForm.getDslamCodeList().get(i).toString().trim());
                            mappingDslamSaleService.setSaleService(tempForm.getSaleServiceCodeList().get(i).toString().trim());
                            mappingDslamSaleService.setStatus("Successfull");
                        } else {
                            mappingDslamSaleService.setDslam(tempForm.getDslamCodeList().get(i).toString().trim());
                            mappingDslamSaleService.setSaleService(tempForm.getSaleServiceCodeList().get(i).toString().trim());
                            mappingDslamSaleService.setStatus(getText(req.getAttribute(REQUEST_MESSAGE).toString()));
                        }
                        resultList.add(mappingDslamSaleService);
                    }
                } else {
                    for (int i = 0; i < rowNumber; i++) {
                        MappingDslamSaleService mappingDslamSaleService = new MappingDslamSaleService();
                        if (addMapping(tempForm.getDslamCodeList().get(i).toString().trim(), tempForm.getSaleServiceCodeList().get(i).toString().trim())) {
                            countSuccess++;
                            mappingDslamSaleService.setDslam(tempForm.getDslamCodeList().get(i).toString().trim());
                            mappingDslamSaleService.setSaleService(tempForm.getSaleServiceCodeList().get(i).toString().trim());
                            mappingDslamSaleService.setStatus("Successfull");
                        } else {
                            mappingDslamSaleService.setDslam(tempForm.getDslamCodeList().get(i).toString().trim());
                            mappingDslamSaleService.setSaleService(tempForm.getSaleServiceCodeList().get(i).toString().trim());
                            mappingDslamSaleService.setStatus(getText(req.getAttribute(REQUEST_MESSAGE).toString()));
                        }
                        resultList.add(mappingDslamSaleService);
                    }
                }
                DownloadDAO downloadDAO = new DownloadDAO();
                String fileName = downloadDAO.getFileNameEncNew(exportResult(resultList).toString(), req.getSession());
                if (fileName == null || "".equals(fileName)) {
                    List listMessageParam = new ArrayList();
                    listMessageParam.add(countSuccess);
                    listMessageParam.add(tempForm.getDslamCodeList().size());
                    req.setAttribute(REQUEST_MESSAGE, "Có lỗi sảy ra khi mapping");
                    req.setAttribute("messageParam", listMessageParam);
                    return pageForward;
                }
                req.setAttribute(RESULT_ERROR, fileName);
//                req.setAttribute("mappingMessage", "mappingMessage");
                List<MappingDslamSaleService> listMappingDslamSaleService = getListMapping(null, null, null);
                req.getSession().removeAttribute(REQUEST_LIST_MAPPING);
                req.getSession().setAttribute(REQUEST_LIST_MAPPING, listMappingDslamSaleService);//load lai danh sach
//                req.setAttribute(REQUEST_MESSAGE, "Mapping.by.file.Successfull" + " " + countSuccess + "/" + tempForm.getDslamCodeList().size());
                List listMessageParam = new ArrayList();
                listMessageParam.add(countSuccess);
                listMessageParam.add(tempForm.getDslamCodeList().size());
                req.setAttribute(REQUEST_MESSAGE, "Mapping.by.file.Successfull");
                req.setAttribute("messageParam", listMessageParam);
                return pageForward;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        resetForm();
        return pageForward;
    }

    public String delete() throws Exception {
        HttpServletRequest req = getRequest();
        pageForward = "preparePage";
        try {
            String strDslamId = (String) QueryCryptUtils.getParameter(req, "dslamId");
            String strSaleServiceId = (String) QueryCryptUtils.getParameter(req, "saleServiceId");
            Long dslamId;
            Long saleServiceId;
            try {
                dslamId = Long.parseLong(strDslamId);
                saleServiceId = Long.parseLong(strSaleServiceId);
                if (dslamId != null && saleServiceId != null) {
                    StringBuilder sql = new StringBuilder("UPDATE dslam_sale_service_map SET status = 0  WHERE dslam_id = ? AND sale_services_id = ? ");
                    Query queryInsert = getSession().createSQLQuery(sql.toString());
                    queryInsert.setParameter(0, dslamId);
                    queryInsert.setParameter(1, saleServiceId);
                    queryInsert.executeUpdate();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "Delete.mapping.Not.Successfull");
        }
        req.setAttribute(REQUEST_MESSAGE, "Delete.mapping.Successfull");
        this.mappingDslamSaleServiceForm.setMappingType(2L);
        resetForm();
        return pageForward;
    }

    public boolean checkValidateAdd(String dslamCode, String saleServiceCode, Long status) {
        if (dslamCode != null && !"".equals(dslamCode)
                && saleServiceCode != null && !"".equals(saleServiceCode) && status != null) {
            return true;
        }
        return false;
    }

    public List<MappingDslamSaleService> getListMapping(String dslamCode, String saleServiceCode, Long status) {
        try {
            List listParameter = new ArrayList();
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT a.code  || ' - ' ||  a.name AS dslam, ");
            sql.append("        b.code  || ' - ' ||  b.name AS saleService, ");
            sql.append("        DECODE (c.status, 1, 'Activate', 'Ineffective') AS status, ");
            sql.append("        a.dslam_id AS dslamId, ");
            sql.append("        b.sale_services_id AS saleServiceId ");
            sql.append(" FROM   dslam a, sale_services b, dslam_sale_service_map c ");
            sql.append(" WHERE  1 = 1 ");
            sql.append("        AND a.dslam_id = c.dslam_id ");
            sql.append("        AND b.sale_services_id = c.sale_services_id ");
            if (dslamCode != null && !"".equals(dslamCode)) {
                sql.append("    AND LOWER (a.code) LIKE LOWER (?) ");
                listParameter.add(dslamCode.trim());
            }
            if (saleServiceCode != null && !"".equals(saleServiceCode)) {
                sql.append("    AND LOWER (b.code) LIKE LOWER (?) ");
                listParameter.add(saleServiceCode.trim());
            }
            if (status != null) {
                sql.append("    AND c.status = ? ");
                listParameter.add(status);
            }
            sql.append(" ORDER BY   a.code, b.code ASC ");
            Query querySelect = getSession().createSQLQuery(sql.toString())
                    .addScalar("dslam", Hibernate.STRING)
                    .addScalar("saleService", Hibernate.STRING)
                    .addScalar("status", Hibernate.STRING)
                    .addScalar("dslamId", Hibernate.LONG)
                    .addScalar("saleServiceId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(MappingDslamSaleService.class));
            for (int i = 0; i < listParameter.size(); i++) {
                querySelect.setParameter(i, listParameter.get(i));
            }
            List<MappingDslamSaleService> listMappingDslamSaleService = (List<MappingDslamSaleService>) querySelect.list();
            return listMappingDslamSaleService;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void resetForm() {
        HttpServletRequest req = getRequest();
        this.mappingDslamSaleServiceForm.setDslamCode("");
        this.mappingDslamSaleServiceForm.setSaleServiceCode("");
        this.mappingDslamSaleServiceForm.setDslamName("");
        this.mappingDslamSaleServiceForm.setSaleServiceName("");
        this.mappingDslamSaleServiceForm.setStatus(null);
        List<MappingDslamSaleService> listMappingDslamSaleService = getListMapping(null, null, null);
        req.getSession().removeAttribute(REQUEST_LIST_MAPPING);
        req.getSession().setAttribute(REQUEST_LIST_MAPPING, listMappingDslamSaleService);
    }

    public String pageNavigator() throws Exception {
        return "mappingDslamSaleServiceResult";
    }

    private MappingDslamSaleServiceForm getDataListfromFile(MappingDslamSaleServiceForm f) {
        MappingDslamSaleServiceForm formTemp = (MappingDslamSaleServiceForm) f;
        try {
            formTemp.setDslamCodeList(null);
            formTemp.setSaleServiceCodeList(null);
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            // Fix ATTT.
            String serverFilePath = getRequest().getSession().getServletContext().getRealPath(tempPath + getSafeFileName(f.getServerFileName()));
            File clientFile = new File(serverFilePath);
            //File clientFile = f.getIsdnFile();
            List<String> dslamCodeList = new ArrayList<String>();
            List<String> saleServiceCodeList = new ArrayList<String>();
            List listObject = new CommonDAO().readExcelFile(clientFile, Constant.SHEET_NAME_DEFAULT, 1, 0, 1);//Lay tu dong thu 2
            for (int i = 0; i < listObject.size(); i++) {
                Object[] obj = (Object[]) listObject.get(i);
                dslamCodeList.add(obj[0].toString().trim());
                saleServiceCodeList.add(obj[1].toString().trim());
            }
            formTemp.setDslamCodeList(dslamCodeList);
            formTemp.setSaleServiceCodeList(saleServiceCodeList);
        } catch (Exception ex) {
            formTemp.setResultMsg(ex.getMessage());
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
        }
        return formTemp;
    }

    private boolean addMapping(String dslamCode, String saleServiceCode) {
        HttpServletRequest req = getRequest();
        Long dslamId = null;
        Long saleServiceId = null;

        if (dslamCode != null && !"".equals(dslamCode)) {
            String sql = new String("SELECT dslamId FROM Dslam WHERE lower(code) = lower(?) AND status = 1");
            Query querySelect = getSession().createQuery(sql.toString());
            querySelect.setParameter(0, dslamCode.toString().trim());
            List<Long> list = querySelect.list();
            if (list == null || list.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "ERROR.Dslam.Invalid");//Ma tram khong ton tai
                return false;
            } else {
                dslamId = Long.valueOf(list.get(0));
            }
        } else {
            req.setAttribute(REQUEST_MESSAGE, "ERROR.Not.Dslam.Code");//Bạn phải nhập mã trạm
            return false;
        }
        if (saleServiceCode != null && !"".equals(saleServiceCode)) {
            String sql = new String("SELECT a.saleServicesId FROM SaleServices a WHERE lower(a.code) = lower(?) AND a.status = 1 AND a.telecomServicesId > 2");
            Query querySelect = getSession().createQuery(sql.toString());
            querySelect.setParameter(0, saleServiceCode.toString().trim());
            List<Long> list = querySelect.list();
            if (list == null || list.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "ERROR.SaleService.Invalid");//dich vu ban hang khong ton tai
                return false;
            } else {
                saleServiceId = Long.valueOf(list.get(0));
            }
        } else {
            req.setAttribute(REQUEST_MESSAGE, "ERROR.Not.SaleService.Code");//Bạn phải nhập mã dịch vụ bán hàng
            return false;
        }
        if (dslamCode != null && !"".equals(dslamCode)
                && saleServiceCode != null && !"".equals(saleServiceCode)) {
            StringBuilder sql = new StringBuilder();
            sql.append(" SELECT c.dslam_id");
            sql.append(" FROM   dslam a, sale_services b, dslam_sale_service_map c ");
            sql.append(" WHERE  1=1 ");
            sql.append("        AND LOWER(a.code) = lower(?) ");
            sql.append("        AND lower(b.code) = lower(?) ");
            sql.append("        AND c.status = 1 ");
            sql.append("        AND a.dslam_id = c.dslam_id ");
            sql.append("        AND b.sale_services_id = c.sale_services_id ");
            Query querySelect = getSession().createSQLQuery(sql.toString());
            querySelect.setParameter(0, dslamCode.toString().trim());
            querySelect.setParameter(1, saleServiceCode.toString().trim());
            List<Long> list = querySelect.list();
            if (list != null && !list.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "EROR.Mapping.Is.Exist");//!!! Lỗi. Nhà trạm và dịch vụ bán hàng đã được mapping
                return false;
            } else {
                String sqlInsert = new String("INSERT INTO dslam_sale_service_map values (?,?,?)");
                Query queryInsert = getSession().createSQLQuery(sqlInsert.toString());
                queryInsert.setParameter(0, dslamId);
                queryInsert.setParameter(1, saleServiceId);
                queryInsert.setParameter(2, Constant.STATUS_USE);
                queryInsert.executeUpdate();
            }
        }
        req.setAttribute(REQUEST_MESSAGE, "MSG.INF.StockModelIptvMap.AddNew.Success");
        return true;
    }

    private String exportResult(List<MappingDslamSaleService> resultList) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddhh24mmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        String date = sdf.format(cal.getTime());
        String realPath = null;
        String templateRealPath = null;
        try {
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
//            ResourceBundle reportFileResource = ResourceBundle.getBundle("report_server");
//            String filePath = reportFileResource.getString("download_file_path");
            ResourceBundle reportFileResource = ResourceBundle.getBundle("config");
            String filePathTmp = reportFileResource.getString("LINK_TO_DOWNLOAD_FILE");
            String filePath = req.getSession().getServletContext().getRealPath(filePathTmp) + "//";
            String templateFileName = "mapping_dslam_sale_service";
            String templatePath = tmp + templateFileName + ".xls";
            filePath += templateFileName + "_resultFile_" + userToken.getLoginName() + "_" + date + ".xls";
            realPath = filePath;
            templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            if (resultList != null && !resultList.isEmpty()) {
                Long no = 1L;
                for (int i = 0; i < resultList.size(); i++) {
                    resultList.get(i).setNo(no);
                    no++;
                }
                Map beans = new HashMap();
                beans.put("listMappingDslamSaleService", resultList);
                XLSTransformer transformer = new XLSTransformer();
                transformer.transformXLS(templateRealPath, beans, realPath);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return realPath;
    }

    public String exportExcel() {
        HttpServletRequest req = getRequest();
        pageForward = "mappingDslamSaleServiceResult";
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddhh24mmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        String date = sdf.format(cal.getTime());
        try {
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
//            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
//            ResourceBundle reportFileResource = ResourceBundle.getBundle("report_server");
//            String filePath = reportFileResource.getString("download_file_path");
            ResourceBundle reportFileResource = ResourceBundle.getBundle("config");
            String filePathTmp = reportFileResource.getString("LINK_TO_DOWNLOAD_FILE");
            String filePath = req.getSession().getServletContext().getRealPath(filePathTmp) + "//";
            String templateFileName = "mapping_dslam_sale_service";
            String templatePath = tmp + templateFileName + ".xls";
            filePath += templateFileName + "_" + userToken.getLoginName() + "_" + date + ".xls";
            String realPath = filePath;
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            List<MappingDslamSaleService> listMappingDslamSaleService =
                    getListMapping(this.mappingDslamSaleServiceForm.getDslamCode(), this.mappingDslamSaleServiceForm.getSaleServiceCode(), this.mappingDslamSaleServiceForm.getStatus());
            if (listMappingDslamSaleService != null && !listMappingDslamSaleService.isEmpty()) {
                Long no = 1L;
                for (int i = 0; i < listMappingDslamSaleService.size(); i++) {
                    listMappingDslamSaleService.get(i).setNo(no);
                    no++;
                }
                Map beans = new HashMap();
                beans.put("listMappingDslamSaleService", listMappingDslamSaleService);
                XLSTransformer transformer = new XLSTransformer();
                transformer.transformXLS(templateRealPath, beans, realPath);
                //Cap nhat ATTT
                DownloadDAO downloadDAO = new DownloadDAO();
                req.setAttribute(RESULT, downloadDAO.getFileNameEncNew(filePath, req.getSession()));
            } else {
                req.setAttribute(REQUEST_MESSAGE, "MSG.not.found.records");
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "MSG.not.found.records");
        }
        resetForm();
        return pageForward;
    }
}
