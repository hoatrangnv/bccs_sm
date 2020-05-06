/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.PackageGoodsDetailV2Bean;
import com.viettel.im.client.bean.PackageGoodsListBean;
import com.viettel.im.client.bean.PackageGoodsReplaceBean;
import com.viettel.im.client.bean.PackageGoodsV2Bean;
import com.viettel.im.client.form.PackageGoodsDetailV2Form;
import com.viettel.im.client.form.PackageGoodsReplaceForm;
import com.viettel.im.client.form.PackageGoodsV2Form;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.PackageGoodsDetailV2;
import com.viettel.im.database.BO.PackageGoodsReplace;
import com.viettel.im.database.BO.PackageGoodsV2;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.UserToken;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author kdvt_tronglv
 */
public class PackageGoodsV2DAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(PackageGoodsV2DAO.class);
    private final String PF_PREPARE_PAGE = "preparePage";
    private final String PF_ADD_NEW_PACKAGE_GOODS = "addNewPackageGoods";
    private final String PF_EDIT_PACKAGE_GOODS = "editPackageGoods";
    private final String PF_LIST_PACKAGE_GOODS_DETAIL = "packageGoodsDetailList";
    private final String PF_EDIT_PACKAGE_GOODS_DETAIL = "addNewPackageGoodsDetail";
    private final String PF_ADD_NEW_PACKAGE_GOODS_DETAIL = "editPackageGoodsDetail";
    private final String REQUEST_LIST_PACKAGE_GOODS = "packageGoodsList";
    private final String REQUEST_LIST_PACKAGE_GOODS_DETAIL = "packageGoodsDetailList";
    //haint41 4/11/2011 : Popup quan ly hang hoa thay the
    private final String REPLACE_GOODS_MANAGEMENT = "replaceGoodsManagement";
    private final String PACKAGE_GOODS_REPLACE_LIST = "packageGoodsReplaceList";
//    private PackageGoodsV2 packageGoodsV2 = new PackageGoodsV2();
//    private PackageGoodsDetailV2 packageGoodsDetailV2 = new PackageGoodsDetailV2();
    private PackageGoodsV2Bean packageGoodsV2Bean = new PackageGoodsV2Bean();
    private PackageGoodsDetailV2Bean packageGoodsDetailV2Bean = new PackageGoodsDetailV2Bean();
    private PackageGoodsV2Form packageGoodsV2Form = new PackageGoodsV2Form();
    private PackageGoodsDetailV2Form packageGoodsDetailV2Form = new PackageGoodsDetailV2Form();
    //haint41 4/11/2011 : 
    private final String PACKAGE_GOODS_V2_LIST = "packageGoodsV2List";
    private PackageGoodsReplaceForm packageGoodsReplaceForm = new PackageGoodsReplaceForm();

    public PackageGoodsReplaceForm getPackageGoodsReplaceForm() {
        return packageGoodsReplaceForm;
    }

    public void setPackageGoodsReplaceForm(PackageGoodsReplaceForm packageGoodsReplaceForm) {
        this.packageGoodsReplaceForm = packageGoodsReplaceForm;
    }

    public PackageGoodsDetailV2Form getPackageGoodsDetailV2Form() {
        return packageGoodsDetailV2Form;
    }

    public void setPackageGoodsDetailV2Form(PackageGoodsDetailV2Form packageGoodsDetailV2Form) {
        this.packageGoodsDetailV2Form = packageGoodsDetailV2Form;
    }

    public PackageGoodsV2Form getPackageGoodsV2Form() {
        return packageGoodsV2Form;
    }

    public void setPackageGoodsV2Form(PackageGoodsV2Form packageGoodsV2Form) {
        this.packageGoodsV2Form = packageGoodsV2Form;
    }

    public PackageGoodsV2 findById(java.lang.Long id) {
        log.debug("getting PackageGoodsV2 instance with id: " + id);
        try {
            PackageGoodsV2 instance = (PackageGoodsV2) getSession().get("com.viettel.im.database.BO.PackageGoodsV2", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public PackageGoodsDetailV2 findPackageGoodsDetailV2ById(java.lang.Long id) {
        log.debug("getting PackageGoodsDetailV2 instance with id: " + id);
        try {
            PackageGoodsDetailV2 instance = (PackageGoodsDetailV2) getSession().get("com.viettel.im.database.BO.PackageGoodsDetailV2", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding PackageGoodsV2 instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PackageGoodsV2 as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findPackageGoodsDetailV2ByProperty(String propertyName, Object value) {
        log.debug("finding PackageGoodsDetailV2 instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PackageGoodsDetailV2 as model where model." + propertyName + "= ? and model.packageGoodsId in (select packageGoodsId from PackageGoodsV2 pg where pg.status = 1 ) ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findPackageGoodsDetailV2ByPropertyAndStatus(String propertyName, Object value) {
        log.debug("finding PackageGoodsDetailV2 instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from PackageGoodsDetailV2 as model where model." + propertyName + "= ? and model.status = ? and model.packageGoodsId in (select packageGoodsId from PackageGoodsV2 pg where pg.status = 1 ) ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            queryObject.setParameter(1, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findPackageGoodsDetailV2(Long packageGoodsId, Long stockModelId) {
        log.debug("finding PackageGoodsDetailV2 instance with packageGoodsId: " + packageGoodsId + ", stockModelId: " + stockModelId);
        try {
            String queryString = "from PackageGoodsDetailV2 as model where model.packageGoodsId=? and model.stockModelId = ? and model.status = ? and model.packageGoodsId in (select packageGoodsId from PackageGoodsV2 pg where pg.status = 1 )";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, packageGoodsId);
            queryObject.setParameter(1, stockModelId);
            queryObject.setParameter(2, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findPackageGoodsDetailV2(Long packageGoodsId, Long stockModelId, Long checkRequired) {
        log.debug("finding PackageGoodsDetailV2 instance with stockModelId: " + stockModelId);
        try {
            String queryString = "from PackageGoodsDetailV2 as model where model.stockModelId = ? and model.status = ? and model.packageGoodsId in (select packageGoodsId from PackageGoodsV2 pg where pg.status = 1 )";

            if (checkRequired != null) {
                queryString += " and model.checkRequired= ? ";
            }
            if (packageGoodsId != null) {
                queryString += " and model.packageGoodsId= ? ";
            }

            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, stockModelId);
            queryObject.setParameter(1, Constant.STATUS_USE);

            int i = 2;
            if (checkRequired != null) {
                queryObject.setParameter(i, checkRequired);
                i++;
            }
            if (packageGoodsId != null) {
                queryObject.setParameter(i, packageGoodsId);
                i++;
            }
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    public String savePackageGoods() throws Exception {
        log.info("Begin method addOrEditPackageGoods of PackageGoodsV2DAO ...");
        String pageForward = this.PF_ADD_NEW_PACKAGE_GOODS;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (packageGoodsV2Form == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100011");
            return pageForward;
        }
        if (packageGoodsV2Form.getCode() == null || packageGoodsV2Form.getCode().trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100011");//Lỗi. Bạn chưa nhập mã bộ hàng hoá!
            return pageForward;
        }
        if (packageGoodsV2Form.getName() == null || packageGoodsV2Form.getName().trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100012");//Lỗi. Bạn chưa nhập tên bộ hàng hoá!
            return pageForward;
        }
        if (packageGoodsV2Form.getStatus() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100013");//Lỗi. Bạn chưa nhập trạng thái bộ hàng hoá!
            return pageForward;
        }
        if (packageGoodsV2Form.getPackageGoodsId() == null) {//add new            
            List list = this.findByProperty("code", packageGoodsV2Form.getCode().trim().toUpperCase());
            if (list != null && list.size() > 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, "E.100020");//Lỗi. Mã bộ hàng hoá đã tồn tại
                return pageForward;
            }
            PackageGoodsV2 packageGoodsV2 = new PackageGoodsV2();
            packageGoodsV2.setPackageGoodsId(this.getSequence("PACKAGE_GOODS_V2_SEQ"));
            packageGoodsV2.setCode(packageGoodsV2Form.getCode().trim().toUpperCase());
            packageGoodsV2.setName(packageGoodsV2Form.getName().trim());
            packageGoodsV2.setNote(packageGoodsV2Form.getNote().trim());
            packageGoodsV2.setStatus(packageGoodsV2Form.getStatus());
            packageGoodsV2.setCreatedBy(userToken.getLoginName());
            packageGoodsV2.setCreatedDate(getSysdate());

            this.getSession().save(packageGoodsV2);
        } else {//edit

            PackageGoodsV2 packageGoodsV2 = this.findById(packageGoodsV2Form.getPackageGoodsId());
            if (packageGoodsV2 == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "E.100021");//Lỗi. Không tìm thấy thông tin bộ hàng hoá để sửa
                return pageForward;
            }


            //check truong hop mat hang trong goi hang do' la check duy nhat trong bo va check duy nhat trong bo khac
            if (packageGoodsV2.getStatus().equals(Constant.STATUS_DELETE) && packageGoodsV2Form.getStatus().equals(Constant.STATUS_USE)) {
                String queryString = " SELECT COUNT (*) FROM package_goods_detail_v2 WHERE 1 = 1 "
                        + " AND status = 1 "
                        + " AND check_required = 1 "
                        + " AND package_goods_id =? "
                        + " and (exists (select 'x' from package_goods_detail_v2 pgd2 where pgd2.status=1 and pgd2.check_required=1 "
                        + " and pgd2.stock_model_id = package_goods_detail_v2.stock_model_id "
                        + " and pgd2.package_goods_id in (select package_goods_id from package_goods_v2 where package_goods_v2.status = 1 and package_goods_v2.package_goods_id <> ? ))) ";

                Query queryObject = getSession().createSQLQuery(queryString);
                queryObject.setParameter(0, packageGoodsV2.getPackageGoodsId());
                queryObject.setParameter(1, packageGoodsV2.getPackageGoodsId());
                BigDecimal amount = (BigDecimal) queryObject.uniqueResult();
                if (amount != null && !amount.equals(BigDecimal.ZERO)) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "E.100033");//Lỗi. Không thể kích hoạt bộ hàng hoá vì trong bộ có hàng hoá là bắt buộc và đã ch�?n là bắt buộc trong bộ khác
                    return pageForward;
                }
            }

            packageGoodsV2.setName(packageGoodsV2Form.getName().trim());
            packageGoodsV2.setNote(packageGoodsV2Form.getNote().trim());
            packageGoodsV2.setStatus(packageGoodsV2Form.getStatus());
            packageGoodsV2.setLastUpdatedBy(userToken.getLoginName());
            packageGoodsV2.setLastUpdatedDate(getSysdate());
            this.getSession().update(packageGoodsV2);

            //req.setAttribute(Constant.RETURN_MESSAGE, "saleRetail.warn.saveSuccess");
            //return pageForward;

        }

        req.setAttribute("result", true);
        return pageForward;
    }

    public String cancelPackageGoods() throws Exception {
        log.info("Begin method cancelPackageGoods of PackageGoodsV2DAO ...");
        String pageForward = this.PF_ADD_NEW_PACKAGE_GOODS;

        HttpServletRequest req = getRequest();


        return pageForward;
    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    public String preparePage() throws Exception {
        log.info("Begin method preparePage of PackageGoodsV2DAO ...");
        String pageForward = this.PF_PREPARE_PAGE;

        HttpServletRequest req = getRequest();


        log.info("End method preparePage of PackageGoodsV2DAO ...");
        return pageForward;
    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    public String deletePackageGoods() throws Exception {
        log.info("Begin method deletePackageGoods of PackageGoodsV2DAO ...");
        String pageForward = PF_PREPARE_PAGE;

        HttpServletRequest req = getRequest();

        String packageGoodsId = (String) req.getParameter("packageGoodsId");
        if (packageGoodsId == null || packageGoodsId.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100022");//Lỗi. Không tìm thấy thông tin bộ hàng hoá để xoá
            return pageForward;
        }
        PackageGoodsV2 packageGoodsV2 = this.findById(Long.valueOf(packageGoodsId.trim()));
        if (packageGoodsV2 == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100022");//Lỗi. Không tìm thấy thông tin bộ hàng hoá để xoá
            return pageForward;
        }

        //CHECK DIEU KIEN XOA
        //1. KHI XOA : SE XOA TOAN BO BAN GHI CHI TIET
        //haint41 14/11/2011 : xoa toan bo ban ghi chi tiet hang hoa thay the
        String strQuery = "delete from PackageGoodsReplace where packageGoodsDetailId in "
                + "(select packageGoodsDetailId from PackageGoodsDetailV2 where packageGoodsId = ? )";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, packageGoodsV2.getPackageGoodsId());
        int numDelete = query.executeUpdate();

        String queryString = "delete from PackageGoodsDetailV2 as model where model.packageGoodsId=? ";
        Query queryObject = getSession().createQuery(queryString);
        queryObject.setParameter(0, packageGoodsV2.getPackageGoodsId());
        int result = queryObject.executeUpdate();

        this.getSession().delete(packageGoodsV2);

        packageGoodsV2Form = new PackageGoodsV2Form();
        List packageGoodsList = this.getPackageGoodsList();
        req.setAttribute(this.REQUEST_LIST_PACKAGE_GOODS, packageGoodsList);


        req.setAttribute(Constant.RETURN_MESSAGE, "M.100006");//Xoá bộ hàng hoá thành công

        return pageForward;
    }

    public String prepareAddNewPackageGoods() throws Exception {
        log.info("Begin method prepareAddNewPackageGoods of PackageGoodsV2DAO ...");
        String pageForward = PF_ADD_NEW_PACKAGE_GOODS;

        HttpServletRequest req = getRequest();


        return pageForward;
    }

    public String prepareEditPackageGoods() throws Exception {
        log.info("Begin method prepareEditPackageGoods of PackageGoodsV2DAO ...");
        String pageForward = PF_EDIT_PACKAGE_GOODS;

        HttpServletRequest req = getRequest();

        String packageGoodsId = (String) req.getParameter("packageGoodsId");
        if (packageGoodsId == null || packageGoodsId.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100021");//Lỗi. Không tìm thấy thông tin bộ hàng hoá để sửa
            return pageForward;
        }

        PackageGoodsV2 packageGoodsV2 = this.findById(Long.valueOf(packageGoodsId.trim()));
        if (packageGoodsV2 == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100021");//Lỗi. Không tìm thấy thông tin bộ hàng hoá để sửa
            return pageForward;
        }

        packageGoodsV2Form.setPackageGoodsId(packageGoodsV2.getPackageGoodsId());
        packageGoodsV2Form.setCode(packageGoodsV2.getCode());
        packageGoodsV2Form.setName(packageGoodsV2.getName());
        packageGoodsV2Form.setNote(packageGoodsV2.getNote());
        packageGoodsV2Form.setStatus(packageGoodsV2.getStatus());
        return pageForward;
    }

    public String searchPackageGoods() throws Exception {
        log.info("Begin method searchPackageGoods of PackageGoodsV2DAO ...");
        String pageForward = PF_PREPARE_PAGE;

        HttpServletRequest req = getRequest();

        List packageGoodsList = this.getPackageGoodsList();

        req.setAttribute(this.REQUEST_LIST_PACKAGE_GOODS, packageGoodsList);

        //Thong bao ket qua tim kiem
        if (packageGoodsList != null && packageGoodsList.size() > 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "M.100003");
            List listParamValue = new ArrayList();
            listParamValue.add(packageGoodsList.size());
            req.setAttribute(Constant.RETURN_MESSAGE_VALUE, listParamValue);
        } else {
            req.setAttribute(Constant.RETURN_MESSAGE, "M.100005");
        }


        return pageForward;
    }

    private List getPackageGoodsList() {
        StringBuilder strQuery = new StringBuilder("");
        List parameterList = new ArrayList();

        strQuery.append(" from PackageGoodsV2 where 1=1 ");

        if (packageGoodsV2Form.getCodeSearch() != null && !packageGoodsV2Form.getCodeSearch().trim().equals("")) {
            strQuery.append(" and upper(code) like ? ");
            parameterList.add(packageGoodsV2Form.getCodeSearch().trim().toUpperCase() + "%");
        }
        if (packageGoodsV2Form.getNameSearch() != null && !packageGoodsV2Form.getNameSearch().trim().equals("")) {
            strQuery.append(" and lower(name) like ? ");
            parameterList.add("%" + packageGoodsV2Form.getNameSearch().trim().toLowerCase() + "%");
        }

        if (packageGoodsV2Form.getStatusSearch() != null) {
            strQuery.append(" and status= ? ");
            parameterList.add(packageGoodsV2Form.getStatusSearch());
        }

        strQuery.append("order by code ");

        Query query = this.getSession().
                createQuery(strQuery.toString());

        for (int i = 0; i < parameterList.size(); i++) {
            query.setParameter(i, parameterList.get(i));
        }

        return query.list();

    }

    public String searchPackageGoodsDetail() throws Exception {
        log.info("Begin method getPackageGoodsDetailList of PackageGoodsV2DAO ...");
        String pageForward = PF_LIST_PACKAGE_GOODS_DETAIL;

        HttpServletRequest req = getRequest();

        String packageGoodsId = (String) req.getParameter("packageGoodsId");
        if (packageGoodsId == null || packageGoodsId.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100023");//Lỗi. Không tìm thấy thông tin bộ hàng hoá
            return pageForward;
        }
        List packageGoodsDetailList = this.getPackageGoodsDetailList(Long.valueOf(packageGoodsId.trim()));
        req.setAttribute(this.REQUEST_LIST_PACKAGE_GOODS_DETAIL, packageGoodsDetailList);
        return pageForward;
    }

    private List getPackageGoodsDetailList(Long packageGoodsId) {
        StringBuilder strQuery = new StringBuilder("");
        List parameterList = new ArrayList();
        strQuery.append(" SELECT a.package_goods_detail_id as packageGoodsDetailId, a.package_goods_id as packageGoodsId, a.stock_model_id as stockModelId, ");
        strQuery.append(" b.stock_model_code as stockModelCode, b.NAME AS stockModelName, a.check_required as checkRequired, a.created_date as createdDate, a.created_by as createdBy,last_Updated_Date as lastUpdatedDate, last_Updated_By as lastUpdatedBy, a.note, ");
        strQuery.append(" a.check_replaced checkReplace,(select wm_concat(name) from stock_model where stock_model_id in (select stock_model_id from package_goods_replace where package_goods_detail_id = a.package_goods_detail_id )) listReplaceGoods");
        strQuery.append(" FROM package_goods_detail_v2 a, stock_model b");
        strQuery.append(" WHERE 1 = 1 AND a.status = 1 AND a.stock_model_id = b.stock_model_id and a.package_Goods_Id = ? ");
        parameterList.add(packageGoodsId);
        strQuery.append("order by b.stock_model_code ");

        Query query = this.getSession().createSQLQuery(strQuery.toString()).
                addScalar("packageGoodsDetailId", Hibernate.LONG).
                addScalar("packageGoodsId", Hibernate.LONG).
                addScalar("stockModelId", Hibernate.LONG).
                addScalar("stockModelCode", Hibernate.STRING).
                addScalar("stockModelName", Hibernate.STRING).
                addScalar("checkRequired", Hibernate.LONG).
                addScalar("createdDate", Hibernate.DATE).
                addScalar("createdBy", Hibernate.STRING).
                addScalar("lastUpdatedDate", Hibernate.DATE).
                addScalar("lastUpdatedBy", Hibernate.STRING).
                addScalar("note", Hibernate.STRING).
                addScalar("listReplaceGoods", Hibernate.STRING).
                addScalar("checkReplace", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(PackageGoodsDetailV2Bean.class));

        for (int i = 0; i < parameterList.size(); i++) {
            query.setParameter(i, parameterList.get(i));
        }

        return query.list();

    }

    /**
     * 
     * @return
     * @throws Exception 
     */
    public String deletePackageGoodsDetail() throws Exception {
        log.info("Begin method deletePackageGoodsDetail of PackageGoodsV2DAO ...");
        String pageForward = PF_LIST_PACKAGE_GOODS_DETAIL;

        HttpServletRequest req = getRequest();

        String packageGoodsDetailId = (String) req.getParameter("packageGoodsDetailId");
        if (packageGoodsDetailId == null || packageGoodsDetailId.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE + "2", "E.100024");//Lỗi. Không tìm thấy thông tin hàng hoá trong bộ
            return pageForward;
        }

        PackageGoodsDetailV2 packageGoodsDetailV2 = this.findPackageGoodsDetailV2ById(Long.valueOf(packageGoodsDetailId.trim()));
        if (packageGoodsDetailV2 == null) {
            req.setAttribute(Constant.RETURN_MESSAGE + "2", "E.100024");//Lỗi. Không tìm thấy thông tin hàng hoá trong bộ
            return pageForward;
        }

        Long packageGoodsId = packageGoodsDetailV2.getPackageGoodsId();


        //CHECK DIEU KIEN XOA
        //1. KHONG CHO XOA MAT HANG LA KEY NEU GOI HANG DO CHI CO 1 KEY

        //danh sach mat hang key trong goi hang : check co la key duy nhat hay khong
        if (packageGoodsDetailV2.getCheckRequired() != null && packageGoodsDetailV2.getCheckRequired().equals(1L)) {
            String queryString = "from PackageGoodsDetailV2 as model where model.packageGoodsId=? and model.checkRequired = 1 and model.status = ? and model.stockModelId <> ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, packageGoodsDetailV2.getPackageGoodsId());
            queryObject.setParameter(1, Constant.STATUS_USE);
            queryObject.setParameter(2, packageGoodsDetailV2.getStockModelId());
            List listCheckNumOfGoods = queryObject.list();
            if (listCheckNumOfGoods == null || listCheckNumOfGoods.size() < 1) {
                List packageGoodsDetailList = this.getPackageGoodsDetailList(Long.valueOf(packageGoodsId));
                req.setAttribute(this.REQUEST_LIST_PACKAGE_GOODS_DETAIL, packageGoodsDetailList);

                req.setAttribute(Constant.RETURN_MESSAGE + "2", "E.100032");//Lỗi. Hàng hoá đã được ch�?n bắt buộc va duy nhất trong bộ
                return pageForward;
            }
        }

        //2. so luong hang hoa phai >= 2
        List<PackageGoodsDetailV2> listPGD = this.findPackageGoodsDetailV2ByPropertyAndStatus("packageGoodsId", packageGoodsDetailV2.getPackageGoodsId());
        if (listPGD == null || listPGD.size() <= 2) {
            List packageGoodsDetailList = this.getPackageGoodsDetailList(Long.valueOf(packageGoodsId));
            req.setAttribute(this.REQUEST_LIST_PACKAGE_GOODS_DETAIL, packageGoodsDetailList);

            req.setAttribute(Constant.RETURN_MESSAGE + "2", "E.100031");//Lỗi. Hàng hoá trong bộ phải lớn hơn hoặc bằng 2
            return pageForward;
        }

        //haint41 14/11/2011 : khi xóa thì xóa cả hàng hóa thay thế
        String strQuery = " delete PackageGoodsReplace where packageGoodsDetailId = ? ";
        Query query = getSession().createQuery(strQuery);
        query.setParameter(0, Long.valueOf(packageGoodsDetailId));
        int numDelete = query.executeUpdate();

        this.getSession().delete(packageGoodsDetailV2);
        this.getSession().flush();

        //Thuc hien refesh lai danh sach
        List packageGoodsDetailList = this.getPackageGoodsDetailList(Long.valueOf(packageGoodsId));
        req.setAttribute(this.REQUEST_LIST_PACKAGE_GOODS_DETAIL, packageGoodsDetailList);

        req.setAttribute(Constant.RETURN_MESSAGE + "2", "M.100007");//Xoá hàng hoá kh�?i bộ thành công

        return pageForward;
    }

    public String prepareAddNewPackageGoodsDetail() throws Exception {
        log.info("Begin method prepareAddNewPackageGoodsDetail of PackageGoodsV2DAO ...");
        String pageForward = PF_ADD_NEW_PACKAGE_GOODS_DETAIL;

        HttpServletRequest req = getRequest();

        String packageGoodsId = (String) req.getParameter("packageGoodsId");
        if (packageGoodsId == null || packageGoodsId.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100023");//Lỗi. Không tìm thấy thông tin bộ hàng hoá
            return pageForward;
        }
        packageGoodsDetailV2Form = new PackageGoodsDetailV2Form();
        packageGoodsDetailV2Form.setPackageGoodsId(Long.valueOf(packageGoodsId));

        return pageForward;
    }

    public String prepareEditPackageGoodsDetail() throws Exception {
        log.info("Begin method prepareEditPackageGoodsDetail of PackageGoodsV2DAO ...");
        String pageForward = PF_EDIT_PACKAGE_GOODS_DETAIL;

        HttpServletRequest req = getRequest();

        String packageGoodsDetailId = (String) req.getParameter("packageGoodsDetailId");
        if (packageGoodsDetailId == null || packageGoodsDetailId.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100024");//Lỗi. Không tìm thấy thông tin hàng hoá trong bộ
            return pageForward;
        }

        PackageGoodsDetailV2 packageGoodsDetailV2 = this.findPackageGoodsDetailV2ById(Long.valueOf(packageGoodsDetailId.trim()));
        if (packageGoodsDetailV2 == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100024");//Lỗi. Không tìm thấy thông tin hàng hoá trong bộ
            return pageForward;
        }

        packageGoodsDetailV2Form.setPackageGoodsDetailId(packageGoodsDetailV2.getPackageGoodsDetailId());
        packageGoodsDetailV2Form.setPackageGoodsId(packageGoodsDetailV2.getPackageGoodsId());
        packageGoodsDetailV2Form.setStockModelId(packageGoodsDetailV2.getStockModelId());
        packageGoodsDetailV2Form.setNote(packageGoodsDetailV2.getNote());
        if (packageGoodsDetailV2.getCheckRequired() != null && packageGoodsDetailV2.getCheckRequired().equals(1L)) {
            packageGoodsDetailV2Form.setCheckRequired(true);
        } else {
            packageGoodsDetailV2Form.setCheckRequired(false);
        }
        //haint41 14/11/2011 : set gia tri check_replace
        if (packageGoodsDetailV2.getCheckReplaced() != null && packageGoodsDetailV2.getCheckReplaced().equals(1L)) {
            packageGoodsDetailV2Form.setCheckReplaced(true);
        } else {
            packageGoodsDetailV2Form.setCheckReplaced(false);
        }


        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(packageGoodsDetailV2.getStockModelId());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100024");//Lỗi. Không tìm thấy thông tin hàng hoá trong bộ
            return pageForward;
        }
        packageGoodsDetailV2Form.setStockModelCode(stockModel.getStockModelCode());
        packageGoodsDetailV2Form.setStockModelName(stockModel.getName());

        return pageForward;
    }

    public String savePackageGoodsDetail() throws Exception {
        log.info("Begin method savePackageGoodsDetail of PackageGoodsV2DAO ...");
        String pageForward = this.PF_ADD_NEW_PACKAGE_GOODS_DETAIL;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        if (packageGoodsDetailV2Form == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100011");
            return pageForward;
        }
        if (packageGoodsDetailV2Form.getPackageGoodsId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100023");//Lỗi. Không tìm thấy thông tin bộ hàng hoá
            return pageForward;
        }
        if (packageGoodsDetailV2Form.getStockModelCode() == null || packageGoodsDetailV2Form.getStockModelCode().trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100025");//Lỗi. Bạn chưa nhập thông tin hàng hoá
            return pageForward;
        }
        if (packageGoodsDetailV2Form.getCheckRequired() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100026");//Lỗi. Bạn chưa ch�?n hàng hoá bắt buộc hay không
            return pageForward;
        }
        //haint41 14/11/2011 : check xem co chon hang hoa bat buoc lam hang hoa thay the hay ko
        if (packageGoodsDetailV2Form.getCheckRequired() && packageGoodsDetailV2Form.getCheckReplaced()) {
            req.setAttribute(Constant.RETURN_MESSAGE, "Không được ch�?n hàng hóa bắt buộc làm hàng hóa có thể thay thế");//Lỗi. Không được ch�?n hàng hóa bắt buộc làm hàng hóa có thể thay thế
            return pageForward;
        }
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.getStockModelByCode(packageGoodsDetailV2Form.getStockModelCode().trim().toUpperCase());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100024");//Lỗi. Không tìm thấy thông tin hàng hoá trong bộ
            return pageForward;
        }


        if (packageGoodsDetailV2Form.getPackageGoodsDetailId() == null) {//add new

            //1. CHECK DA TON TAI MAT HANG TRONG GOI CHUA?
            List listCheckExists = this.findPackageGoodsDetailV2(packageGoodsDetailV2Form.getPackageGoodsId(), stockModel.getStockModelId());
            if (listCheckExists != null && listCheckExists.size() > 0) {
                req.setAttribute(Constant.RETURN_MESSAGE, "E.100029");//Lỗi. Hàng hoá đã được gán trong bộ
                return pageForward;
            }
            //2. CHECK NEU CHON LA KEY THI DA LA KEY CUA BO KHAC HAY CHUA?
            if (packageGoodsDetailV2Form.getCheckRequired()) {
                List listCheckRequired = this.findPackageGoodsDetailV2(null, stockModel.getStockModelId(), 1L);//check ca duoc chon duy nhat hay khong duoc chon duy nhat
                if (listCheckRequired != null && listCheckRequired.size() > 0) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "E.100030");//Lỗi. Hàng hoá đã được ch�?n bắt buộc trong bộ khác
                    return pageForward;
                }
            }


            PackageGoodsDetailV2 packageGoodsDetailV2 = new PackageGoodsDetailV2();
            packageGoodsDetailV2.setPackageGoodsId(packageGoodsDetailV2Form.getPackageGoodsId());
            packageGoodsDetailV2.setPackageGoodsDetailId(this.getSequence("PACKAGE_GOODS_DETAIL_V2_SEQ"));
            packageGoodsDetailV2.setStockModelId(stockModel.getStockModelId());
            if (packageGoodsDetailV2Form.getCheckRequired()) {
                packageGoodsDetailV2.setCheckRequired(1L);
            } else {
                packageGoodsDetailV2.setCheckRequired(0L);
            }
            //haint41 14/11/2011 : luu them truong check_replace
            if (packageGoodsDetailV2Form.getCheckReplaced()) {
                packageGoodsDetailV2.setCheckReplaced(1L);
            } else {
                packageGoodsDetailV2.setCheckReplaced(0L);
            }
            if (packageGoodsDetailV2Form.getNote() != null) {
                packageGoodsDetailV2.setNote(packageGoodsDetailV2Form.getNote().trim());
            }
            packageGoodsDetailV2.setStatus(Constant.STATUS_USE);
            packageGoodsDetailV2.setCreatedBy(userToken.getLoginName());
            packageGoodsDetailV2.setCreatedDate(getSysdate());

            this.getSession().save(packageGoodsDetailV2);
        } else {//edit
            PackageGoodsDetailV2 packageGoodsDetailV2 = this.findPackageGoodsDetailV2ById(packageGoodsDetailV2Form.getPackageGoodsDetailId());
            if (packageGoodsDetailV2 == null) {
                req.setAttribute(Constant.RETURN_MESSAGE, "E.100027");//Lỗi. Không tìm thấy thông tin hàng hoá
                return pageForward;
            }

            packageGoodsDetailV2.setNote(packageGoodsDetailV2Form.getNote().trim());
            //check dieu kien update
            if (packageGoodsDetailV2Form.getCheckRequired()) {
                //1. neu da la key cua goi hang khac : bao loi
                List<PackageGoodsDetailV2> listCheckRequired = this.findPackageGoodsDetailV2(null, stockModel.getStockModelId(), 1L);//check ca truong hop chon duy nhat hay khong chon duy nhat
                if (listCheckRequired != null && listCheckRequired.size() > 0 && listCheckRequired.get(0).getPackageGoodsDetailId().compareTo(packageGoodsDetailV2.getPackageGoodsDetailId()) != 0) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "E.100030");//Lỗi. Hàng hoá đã được ch�?n bắt buộc trong bộ khác
                    return pageForward;
                }
                packageGoodsDetailV2.setCheckRequired(1L);
            } else {
                //1. neu dang la key cua goi hang : kiem tra goi hang da co key hay chua?
                String queryString = "from PackageGoodsDetailV2 as model where model.packageGoodsId=? and model.checkRequired = 1 and model.status = ? and model.stockModelId <> ? ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, packageGoodsDetailV2.getPackageGoodsId());
                queryObject.setParameter(1, Constant.STATUS_USE);
                queryObject.setParameter(2, packageGoodsDetailV2.getStockModelId());
                List listCheckNumOfGoods = queryObject.list();
                if (listCheckNumOfGoods == null || listCheckNumOfGoods.size() < 1) {
                    req.setAttribute(Constant.RETURN_MESSAGE, "E.100032");//Lỗi. Hàng hoá đã được ch�?n bắt buộc va duy nhất trong bộ
                    return pageForward;
                }


                packageGoodsDetailV2.setCheckRequired(0L);
            }
            //haint41 14/11/2011 : luu them truong check_replace
            if (packageGoodsDetailV2Form.getCheckReplaced()) {
                packageGoodsDetailV2.setCheckReplaced(1L);
            } else {
                packageGoodsDetailV2.setCheckReplaced(0L);
            }
            packageGoodsDetailV2.setLastUpdatedBy(userToken.getLoginName());
            packageGoodsDetailV2.setLastUpdatedDate(getSysdate());
            this.getSession().update(packageGoodsDetailV2);
        }

        req.setAttribute("result", true);
        return pageForward;
    }

    public List<ImSearchBean> getListStockModel(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery1.append("from StockModel a, StockType b ");
        strQuery1.append("where 1 = 1 and a.stockTypeId  = b.stockTypeId and b.checkExp = 1 ");
        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.status = ? ");
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
     * author   : tamdt1
     * date     : 21/01/2009
     * purpose  : lay danh sach mat hang
     *
     */
    public Long getListStockModelSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Long result = 0L;
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from StockModel a, StockType b ");
        strQuery1.append("where 1 = 1 and a.stockTypeId  = b.stockTypeId and b.checkExp = 1 ");

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.stockModelCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and a.status = ? ");
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
     * author   : tamdt
     * date     : 18/11/2009
     * purpose  : lay ten mat hang
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
        strQuery1.append("from StockModel a, StockType b ");
        strQuery1.append("where 1 = 1 and a.stockTypeId  = b.stockTypeId and b.checkExp = 1 ");

        strQuery1.append("and lower(a.stockModelCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery1.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery1.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        listImSearchBean = query1.list();

        return listImSearchBean;
    }

    public String prepareReplaceGoodsManagement() throws Exception {
        log.info("Begin method prepareReplaceGoodsManagement of PackageGoodsV2DAO ...");
        String pageForward = REPLACE_GOODS_MANAGEMENT;

        HttpServletRequest req = getRequest();

        String packageGoodsDetailId = (String) req.getParameter("packageGoodsDetailId");
        if (packageGoodsDetailId == null || packageGoodsDetailId.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100024");//Lỗi. Không tìm thấy thông tin hàng hoá trong bộ
            return pageForward;
        }

        PackageGoodsDetailV2 packageGoodsDetailV2 = this.findPackageGoodsDetailV2ById(Long.valueOf(packageGoodsDetailId.trim()));
        if (packageGoodsDetailV2 == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100024");//Lỗi. Không tìm thấy thông tin hàng hoá trong bộ
            return pageForward;
        }

        List packageGoodsReplaceList = this.getListPackageGoodsReplace(Long.valueOf(packageGoodsDetailId));
        req.setAttribute(this.PACKAGE_GOODS_REPLACE_LIST, packageGoodsReplaceList);

        packageGoodsReplaceForm = new PackageGoodsReplaceForm();
        packageGoodsReplaceForm.setPackageGoodsDetailId(Long.valueOf(packageGoodsDetailId));
        packageGoodsReplaceForm.setPackageGoodsId(packageGoodsDetailV2.getPackageGoodsId());

        return pageForward;

    }

    public List getListPackageGoodsReplace(Long packageGoodsDetailId) {
        StringBuilder strQuery = new StringBuilder();
        strQuery.append(" select sm.stock_model_id stockModelId, sm.stock_model_code stockModelCode, sm.name stockModelName, pgr.package_goods_replace_id packageGoodsReplaceId");
        strQuery.append(" from stock_model sm, package_goods_replace pgr");
        strQuery.append(" where sm.stock_model_id = pgr.stock_model_id ");
        strQuery.append(" and pgr.package_goods_detail_id = ? ");
        Query query = this.getSession().createSQLQuery(strQuery.toString()).
                addScalar("stockModelId", Hibernate.LONG).
                addScalar("stockModelCode", Hibernate.STRING).
                addScalar("stockModelName", Hibernate.STRING).
                addScalar("packageGoodsReplaceId", Hibernate.LONG).setResultTransformer(Transformers.aliasToBean(PackageGoodsReplaceBean.class));
        query.setParameter(0, packageGoodsDetailId);
        return query.list();
    }

    public String savePackageGoodsReplace() throws Exception {
        log.info("Begin method savePackageGoodsDetail of PackageGoodsV2DAO ...");
        String pageForward = REPLACE_GOODS_MANAGEMENT;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

//        if (packageGoodsReplaceForm == null) {
//            req.setAttribute(Constant.RETURN_MESSAGE, "E.100011");
//            return pageForward;
//        }
        if (packageGoodsReplaceForm.getPackageGoodsDetailId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.PACKAGE.GOODS.001");//Lỗi. Không tìm thấy thông tin bộ hàng hoá
            return pageForward;
        }
        if (packageGoodsReplaceForm.getStockModelCode() == null || packageGoodsReplaceForm.getStockModelCode().trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.PACKAGE.GOODS.002");//Lỗi. Bạn chưa nhập thông tin hàng hoá
            return pageForward;
        }

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.getStockModelByCode(packageGoodsReplaceForm.getStockModelCode().trim().toUpperCase());
        if (stockModel == null || stockModel.getStockModelId() == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.PACKAGE.GOODS.003");//Lỗi. Không tìm thấy thông tin hàng hoá trong bộ
            return pageForward;
        }

        //CHECK DA TON TAI MAT HANG TRONG GOI CHUA?
        Long packageGoodsId = packageGoodsReplaceForm.getPackageGoodsId();
        List listCheckExists = this.findPackageGoodsDetailV2(packageGoodsId, stockModel.getStockModelId());
        if (listCheckExists != null && listCheckExists.size() > 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100029");//Lỗi. Hàng hoá đã được gán trong bộ
            return pageForward;
        }
        
        List listCheckExistsGoodsReplace = this.findPackageGoodsReplace(packageGoodsReplaceForm.getPackageGoodsDetailId(), stockModel.getStockModelId());
        if (listCheckExistsGoodsReplace != null && listCheckExistsGoodsReplace.size() > 0) {
            req.setAttribute(Constant.RETURN_MESSAGE, "E.100029");//Lỗi. Hàng hoá đã được gán trong bộ
            return pageForward;
        }

        PackageGoodsReplace packageGoodsReplace = new PackageGoodsReplace();
        packageGoodsReplace.setPackageGoodsReplaceId(this.getSequence("PACKAGE_GOODS_REPLACE_SEQ"));
        packageGoodsReplace.setPackageGoodsDetailId(packageGoodsReplaceForm.getPackageGoodsDetailId());
        packageGoodsReplace.setStockModelId(stockModel.getStockModelId());
        packageGoodsReplace.setStatus(Constant.STATUS_USE);
        if (packageGoodsReplaceForm.getNote() != null) {
            packageGoodsReplace.setNote(packageGoodsReplaceForm.getNote().trim());
        }
        packageGoodsReplace.setCreatedDate(getSysdate());
        packageGoodsReplace.setCreatedBy(userToken.getLoginName());

        this.getSession().save(packageGoodsReplace);
        this.getSession().flush();
        List packageGoodsReplaceList = this.getListPackageGoodsReplace(packageGoodsReplace.getPackageGoodsDetailId());
        req.setAttribute(this.PACKAGE_GOODS_REPLACE_LIST, packageGoodsReplaceList);
        req.setAttribute(Constant.RETURN_MESSAGE, "M.100009");

        packageGoodsReplaceForm = new PackageGoodsReplaceForm();
        packageGoodsReplaceForm.setPackageGoodsDetailId(packageGoodsReplace.getPackageGoodsDetailId());
        packageGoodsReplaceForm.setPackageGoodsId(packageGoodsId);
        
        //req.setAttribute("result", true);
        return pageForward;
    }

    public String deletePackageGoodsReplace() throws Exception {
        log.info("Begin method deletePackageGoodsReplace of PackageGoodsV2DAO ...");
        String pageForward = REPLACE_GOODS_MANAGEMENT;

        HttpServletRequest req = getRequest();

        Long packageGoodsId = packageGoodsReplaceForm.getPackageGoodsId();
        String packageGoodsReplaceId = (String) req.getParameter("packageGoodsReplaceId");
        if (packageGoodsReplaceId == null || packageGoodsReplaceId.trim().equals("")) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.PACKAGE.GOODS.005");//Lỗi. Không tìm thấy thông tin hàng hoá thay thế để xoá
            return pageForward;
        }
        PackageGoodsReplace packageGoodsReplace = this.findPackageGoodsReplaceById(Long.valueOf(packageGoodsReplaceId.trim()));
        if (packageGoodsReplaceId == null) {
            req.setAttribute(Constant.RETURN_MESSAGE, "ERR.PACKAGE.GOODS.005");//Lỗi. Không tìm thấy thông tin hàng hoá thay thế để xoá
            return pageForward;
        }

        Long packageGoodsDetailId = packageGoodsReplace.getPackageGoodsDetailId();
        this.getSession().delete(packageGoodsReplace);
        this.getSession().flush();

        List packageGoodsReplaceList = this.getListPackageGoodsReplace(packageGoodsDetailId);
        req.setAttribute(this.PACKAGE_GOODS_REPLACE_LIST, packageGoodsReplaceList);

        packageGoodsReplaceForm = new PackageGoodsReplaceForm();
        packageGoodsReplaceForm.setPackageGoodsDetailId(packageGoodsDetailId);
        packageGoodsReplaceForm.setPackageGoodsId(packageGoodsId);

        req.setAttribute(Constant.RETURN_MESSAGE, "M.100008");//Xóa hàng hóa thay thế kh�?i bộ thành công

        return pageForward;
    }

    public String export2Excel() throws Exception {
        String pageForward = PF_PREPARE_PAGE;
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        try {
            List<PackageGoodsV2> packageGoodsList = this.getPackageGoodsList();
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String templatePath = "";

            templatePath = tmp + "ListOfPackage.xls";
            filePath += "ListOfPackage_" + userToken.getLoginName() + "_" + date + ".xls";
            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            List<PackageGoodsV2> lstPackageGoodsV2 = findPackageGoodsToExcel();

            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", DateTimeUtils.date2ddMMyyyy(getSysdate()));
            //set nguoi tao
            //UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            //beans.put("userCreate", actionStaffName);
            //beans.put("actionCode", actionCode.toUpperCase());
            //beans.put("fromOwnerName", fromOwnerName);
            //beans.put("fromOwnerAddress", fromOwnerAddress);
            //beans.put("toOwnerName", toOwnerName);
            //beans.put("toOwnerAddress", toOwnerAddress);
            //beans.put("reasonName", reasonName);


            beans.put("lstPackageGoods", lstPackageGoodsV2);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            req.setAttribute("filePath", filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageForward;
    }

    public List<ImSearchBean> getListStockModelReplace(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        String packageGoodsDetailId = imSearchBean.getOtherParamValue().trim();
        if (packageGoodsDetailId == null || packageGoodsDetailId.equals("")) {
            packageGoodsDetailId = "-1";
        }

        List listParameter1 = new ArrayList();
        StringBuilder strQuery = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery.append(" from StockModel a, StockType b ");
        strQuery.append(" where 1 = 1 and a.stockTypeId  = b.stockTypeId and b.checkExp = 1 ");
        strQuery.append(" and a.stockModelId not in (select stockModelId from PackageGoodsReplace where packageGoodsDetailId = ? )");
        strQuery.append(" and a.status = ? ");
        listParameter1.add(Long.valueOf(packageGoodsDetailId));
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> listImSearchBean = query1.list();

        return listImSearchBean;
    }

    /**
     *
     * author   : haint41
     * date     : 18/11/2011
     * purpose  : lay danh sach mat hang
     *
     */
    public Long getListStockModelReplaceSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        Long result = 0L;
        List listParameter1 = new ArrayList();

        String packageGoodsDetailId = imSearchBean.getOtherParamValue().trim();
        if (packageGoodsDetailId == null || packageGoodsDetailId.equals("")) {
            packageGoodsDetailId = "-1";
        }
        StringBuilder strQuery = new StringBuilder("select count(*) ");
        strQuery.append(" from StockModel a, StockType b ");
        strQuery.append(" where 1 = 1 and a.stockTypeId  = b.stockTypeId and b.checkExp = 1 ");
        strQuery.append(" and a.stockModelId not in (select stockModelId from PackageGoodsDetailV2 where packageGoodsDetailId = ? )");
        strQuery.append(" and a.stockModelId not in (select stockModelId from PackageGoodsReplace where packageGoodsDetailId = ? )");
        strQuery.append("and a.status = ? ");
        listParameter1.add(Long.valueOf(packageGoodsDetailId));
        listParameter1.add(Long.valueOf(packageGoodsDetailId));
        listParameter1.add(Constant.STATUS_ACTIVE);

        Query query1 = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<Long> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            if (tmpList1.get(0) != null) {
                result = tmpList1.get(0);
            }
        }

        return result;
    }

    /**
     *
     * author   : haint41
     * date     : 18/11/2011
     * purpose  : lay ten mat hang
     *
     */
    public List<ImSearchBean> getStockModelReplaceName(ImSearchBean imSearchBean) {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        if (imSearchBean.getCode() == null || imSearchBean.getCode().trim().equals("")) {
            return listImSearchBean;
        }

        String packageGoodsDetailId = imSearchBean.getOtherParamValue().trim();
        if (packageGoodsDetailId == null || packageGoodsDetailId.equals("")) {
            packageGoodsDetailId = "-1";
        }
       
        //lay ten cua mat hang dua tren code
        List listParameter1 = new ArrayList();
        StringBuilder strQuery = new StringBuilder("select new com.viettel.im.client.bean.ImSearchBean(a.stockModelCode, a.name) ");
        strQuery.append(" from StockModel a, StockType b ");
        strQuery.append(" where 1 = 1 and a.stockTypeId  = b.stockTypeId and b.checkExp = 1 ");

        strQuery.append("and lower(a.stockModelCode) = ? ");
        listParameter1.add(imSearchBean.getCode().trim().toLowerCase());

        strQuery.append("and a.status = ? ");
        listParameter1.add(Constant.STATUS_ACTIVE);

        strQuery.append(" and a.stockModelId not in (select stockModelId from PackageGoodsReplace where packageGoodsDetailId = ? )");
        listParameter1.add(Long.valueOf(packageGoodsDetailId));

        strQuery.append("order by nlssort(a.stockModelCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        listImSearchBean = query1.list();

        return listImSearchBean;
    }

    public PackageGoodsReplace findPackageGoodsReplaceById(java.lang.Long id) {
        log.debug("getting PackageGoodsV2 instance with id: " + id);
        try {
            PackageGoodsReplace instance = (PackageGoodsReplace) getSession().get("com.viettel.im.database.BO.PackageGoodsReplace", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public String reloadListPackageGoods() throws Exception {
        log.info("Begin method searchPackageGoods of PackageGoodsV2DAO ...");
        String pageForward = PACKAGE_GOODS_V2_LIST;
        HttpServletRequest req = getRequest();
        List packageGoodsList = this.getPackageGoodsList();
        req.setAttribute(this.REQUEST_LIST_PACKAGE_GOODS, packageGoodsList);
        return pageForward;
    }

    public List findPackageGoodsToExcel() {
        try {
            String strQuery = "from PackageGoodsV2 where status = ? ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, Constant.STATUS_ACTIVE);
            List lst = query.list();

            //Lay thong tin packageGoodsDetail
            Long stt = 1L;
            for (int i = 0; i < lst.size(); i++) {
                PackageGoodsV2 packageGoodsV2 = (PackageGoodsV2) lst.get(i);
                packageGoodsV2.setStt(stt++);
                //String sql = "select stockModelCode code,name from StockModel where stockModelId in (select stockModelId from PackageGoodsDetailV2 where packageGoodsId = ?)";
                String sql = "select stock_model_code stockModelCode,name stockModelName from stock_model where stock_model_id in (select stock_model_id from package_goods_detail_V2 where package_goods_id = ?)";
                Query query1 = getSession().createSQLQuery(sql).addScalar("stockModelCode", Hibernate.STRING).addScalar("stockModelName", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PackageGoodsDetailV2Bean.class));
                query1.setParameter(0, packageGoodsV2.getPackageGoodsId());
                List lstPackageGoodsDetailV2 = query1.list();
                //List lstPackageGoodsDetailV2 = findPackageGoodsDetailV2ByProperty("packageGoodsId",packageGoodsV2.getPackageGoodsId());
                packageGoodsV2.setLstPackageGoodsDetailV2(lstPackageGoodsDetailV2);
            }
            return lst;
        } catch (Exception e) {
            return null;
        }
    }

    public InputStream getExportToExcel() throws Exception {
        try {
            InputStream result = new ByteArrayInputStream(new byte[]{0});
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

//            String DATE_FORMAT = "yyyyMMddhh24mmss";
//            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
//            Calendar cal = Calendar.getInstance();
//            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
//            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String templatePath = "";

            templatePath = tmp + "ListOfPackage.xls";
//            filePath += "ListOfPackage_" + userToken.getLoginName() + "_" + date + ".xls";
//            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            //Tao byte array, workbook va worksheet cua file excel
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
            File xlsTemplFile = new File(templateRealPath);
            Workbook workbook = Workbook.getWorkbook(xlsTemplFile);
            WritableWorkbook wrWorkbook = Workbook.createWorkbook(byteOutput, workbook);
            WritableSheet wrSheet = wrWorkbook.getSheet(0);
            workbook.close();

            //Tao font de format du lieu
            WritableFont normalFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false);
            WritableCellFormat cellFormat = new WritableCellFormat(normalFont);
            cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            cellFormat.setWrap(true);
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);

            WritableCellFormat cellCenterFormat = new WritableCellFormat(normalFont);
            cellCenterFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            cellCenterFormat.setWrap(true);
            cellCenterFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellCenterFormat.setAlignment(jxl.format.Alignment.CENTRE);

            WritableCellFormat cellLeftFormat = new WritableCellFormat(normalFont);
            cellLeftFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            cellLeftFormat.setWrap(true);
            cellLeftFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellLeftFormat.setAlignment(jxl.format.Alignment.LEFT);
            cellLeftFormat.setBackground(Colour.PALE_BLUE);

            WritableCellFormat cellLeftFormatWhite = new WritableCellFormat(normalFont);
            cellLeftFormatWhite.setVerticalAlignment(VerticalAlignment.CENTRE);
            cellLeftFormatWhite.setWrap(true);
            cellLeftFormatWhite.setBorder(Border.ALL, BorderLineStyle.THIN);
            cellLeftFormatWhite.setAlignment(jxl.format.Alignment.LEFT);

            WritableFont boldFont = new WritableFont(WritableFont.TAHOMA, 8, WritableFont.BOLD, false);
            WritableCellFormat cellBoldFormat = new WritableCellFormat(boldFont);
            cellBoldFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            cellBoldFormat.setAlignment(jxl.format.Alignment.CENTRE);
            cellBoldFormat.setWrap(true);
            cellBoldFormat.setBorder(Border.NONE, BorderLineStyle.NONE);

            //Ghi du lieu cua cac khieu nai ra file excel
            int beginRow = 9;
            StringBuilder strQuery = new StringBuilder();
            strQuery.append(" select pgV2.code packageCode, pgV2.name packageName, pgV2.status status, pgV2.note note, pgV2.created_by userCreate, to_char(pgV2.created_date,'dd/mm/yyyy') createDate,");
            strQuery.append(" sm.stock_model_code goodsCode, sm.name goodsName, pgdV2.check_required checked, pgdV2.check_replaced replaceGoods ");
            strQuery.append(" from package_goods_V2 pgV2 LEFT OUTER JOIN package_goods_detail_V2 pgdV2 ON ( pgV2.package_goods_id = pgdV2.package_goods_id ) ");
            strQuery.append(" LEFT OUTER JOIN stock_model sm ON (pgdV2.stock_model_id  = sm.stock_model_id)");
            strQuery.append(" ORDER BY pgV2.code ");

            Query query = getSession().createSQLQuery(strQuery.toString()).addScalar("packageCode", Hibernate.STRING).addScalar("packageName", Hibernate.STRING).addScalar("status", Hibernate.STRING).addScalar("note", Hibernate.STRING).addScalar("userCreate", Hibernate.STRING).addScalar("createDate", Hibernate.STRING).addScalar("goodsCode", Hibernate.STRING).addScalar("goodsName", Hibernate.STRING).addScalar("checked", Hibernate.STRING).addScalar("replaceGoods", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(PackageGoodsListBean.class));
            List<PackageGoodsListBean> lstPackageGoodsListBean = query.list();

            int listSize = (lstPackageGoodsListBean != null) ? lstPackageGoodsListBean.size() : 0;
            //Kiem tra xem co khieu nai nao duoc chon khong. Neu co thi export
            int stt = 0;

            wrSheet.addCell(new Label(1, 3, "Report date : " + DateTimeUtils.date2ddMMyyyy(getSysdate())));
            if (lstPackageGoodsListBean != null && listSize > 0) {
                for (int i = 0; i < listSize; i++) {
                    PackageGoodsListBean packageGoodsListBean = lstPackageGoodsListBean.get(i);
                    if ("1".equals(packageGoodsListBean.getChecked())) {
                        packageGoodsListBean.setChecked("Yes");
                    } else if ("0".equals(packageGoodsListBean.getChecked())) {
                        packageGoodsListBean.setChecked("No");
                    }
                    if ("1".equals(packageGoodsListBean.getReplaceGoods())) {
                        packageGoodsListBean.setReplaceGoods("Yes");
                    } else if ("0".equals(packageGoodsListBean.getReplaceGoods())) {
                        packageGoodsListBean.setReplaceGoods("No");
                    }
                    if (i > 0) {
                        if (!lstPackageGoodsListBean.get(i - 1).getPackageCode().equals(packageGoodsListBean.getPackageCode())) {
                            stt++;
                            wrSheet.addCell(new Label(0, beginRow + i, "" + (stt), cellLeftFormat));
                            wrSheet.addCell(new Label(1, beginRow + i, packageGoodsListBean.getPackageCode(), cellLeftFormat));
                            wrSheet.addCell(new Label(2, beginRow + i, packageGoodsListBean.getPackageName(), cellLeftFormat));
                            wrSheet.addCell(new Label(3, beginRow + i, packageGoodsListBean.getStatus(), cellLeftFormat));
                            wrSheet.addCell(new Label(4, beginRow + i, packageGoodsListBean.getNote(), cellLeftFormat));
                            wrSheet.addCell(new Label(5, beginRow + i, packageGoodsListBean.getUserCreate(), cellLeftFormat));
                            wrSheet.addCell(new Label(6, beginRow + i, packageGoodsListBean.getCreateDate(), cellLeftFormat));
                            wrSheet.addCell(new Label(7, beginRow + i, packageGoodsListBean.getGoodsCode(), cellLeftFormat));
                            wrSheet.addCell(new Label(8, beginRow + i, packageGoodsListBean.getGoodsName(), cellLeftFormat));
                            wrSheet.addCell(new Label(9, beginRow + i, packageGoodsListBean.getChecked(), cellLeftFormat));
                            wrSheet.addCell(new Label(10, beginRow + i, packageGoodsListBean.getReplaceGoods(), cellLeftFormat));
                        } else {
                            wrSheet.addCell(new Label(0, beginRow + i, "", cellLeftFormatWhite));
                            wrSheet.addCell(new Label(1, beginRow + i, "", cellLeftFormatWhite));
                            wrSheet.addCell(new Label(2, beginRow + i, "", cellLeftFormatWhite));
                            wrSheet.addCell(new Label(3, beginRow + i, "", cellLeftFormatWhite));
                            wrSheet.addCell(new Label(4, beginRow + i, "", cellLeftFormatWhite));
                            wrSheet.addCell(new Label(5, beginRow + i, "", cellLeftFormatWhite));
                            wrSheet.addCell(new Label(6, beginRow + i, "", cellLeftFormatWhite));
                            wrSheet.addCell(new Label(7, beginRow + i, packageGoodsListBean.getGoodsCode(), cellLeftFormatWhite));
                            wrSheet.addCell(new Label(8, beginRow + i, packageGoodsListBean.getGoodsName(), cellLeftFormatWhite));
                            wrSheet.addCell(new Label(9, beginRow + i, packageGoodsListBean.getChecked(), cellLeftFormatWhite));
                            wrSheet.addCell(new Label(10, beginRow + i, packageGoodsListBean.getReplaceGoods(), cellLeftFormatWhite));
                        }
                    } else {
                        stt++;
                        wrSheet.addCell(new Label(0, beginRow + i, "" + (stt), cellLeftFormat));
                        wrSheet.addCell(new Label(1, beginRow + i, packageGoodsListBean.getPackageCode(), cellLeftFormat));
                        wrSheet.addCell(new Label(2, beginRow + i, packageGoodsListBean.getPackageName(), cellLeftFormat));
                        wrSheet.addCell(new Label(3, beginRow + i, packageGoodsListBean.getStatus(), cellLeftFormat));
                        wrSheet.addCell(new Label(4, beginRow + i, packageGoodsListBean.getNote(), cellLeftFormat));
                        wrSheet.addCell(new Label(5, beginRow + i, packageGoodsListBean.getUserCreate(), cellLeftFormat));
                        wrSheet.addCell(new Label(6, beginRow + i, packageGoodsListBean.getCreateDate(), cellLeftFormat));
                        wrSheet.addCell(new Label(7, beginRow + i, packageGoodsListBean.getGoodsCode(), cellLeftFormat));
                        wrSheet.addCell(new Label(8, beginRow + i, packageGoodsListBean.getGoodsName(), cellLeftFormat));
                        wrSheet.addCell(new Label(9, beginRow + i, packageGoodsListBean.getChecked(), cellLeftFormat));
                        wrSheet.addCell(new Label(10, beginRow + i, packageGoodsListBean.getReplaceGoods(), cellLeftFormat));
                    }
                }
            }

            wrWorkbook.write();
            wrWorkbook.close();
            wrWorkbook = null;

            result = new ByteArrayInputStream(byteOutput.toByteArray());
            return result;
        } catch (Exception e) {
            log.error("Error:" + e.getMessage(), e);
            throw e;
        }

    }
    
    public List findPackageGoodsReplace(Long packageGoodsDetailId, Long stockModelId) {
        log.debug("finding PackageGoodsReplace instance with packageGoodsDetailId: " + packageGoodsDetailId + ", stockModelId: " + stockModelId);
        try {
            String queryString = "from PackageGoodsReplace as model where model.packageGoodsDetailId=? and model.stockModelId = ? and model.status = ? and model.packageGoodsDetailId in (select packageGoodsDetailId from PackageGoodsDetailV2 pg where pg.status = 1 )";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, packageGoodsDetailId);
            queryObject.setParameter(1, stockModelId);
            queryObject.setParameter(2, Constant.STATUS_USE);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
}
