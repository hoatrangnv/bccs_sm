package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.InvoiceListReportBean;
import com.viettel.im.client.bean.InvoiceTransferLogBean;
import com.viettel.im.database.BO.InvoiceList;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

import com.viettel.im.client.bean.CurrentInvoiceListBean;
import com.viettel.im.common.Constant;

/**
 * A data access object (DAO) providing persistence and search support for InvoiceList entities.
 * Transaction control of the save(), update() and delete() operations
can directly support Spring container-managed transactions or they can be augmented	to handle user-managed Spring transactions. 
Each of these methods provides additional information for how to configure it for the desired type of transaction control. 	
 * @see com.viettel.im.database.BO.InvoiceList
 * @author MyEclipse Persistence Tools
 *  @author TungTV
 */
public class InvoiceListDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(InvoiceListDAO.class);
    //property constants
    public static final String BOOK_TYPE_ID = "bookTypeId";
    public static final String SERIAL_NO = "serialNo";
    public static final String BLOCK_NO = "blockNo";
    public static final String FROM_INVOICE = "fromInvoice";
    public static final String TO_INVOICE = "toInvoice";
    public static final String CURR_INVOICE_NO = "currInvoiceNo";
    public static final String SHOP_ID = "shopId";
    public static final String USER_CREATED = "userCreated";
    public static final String STATUS = "status";
//    public static final Long INVOICE_RETRIEVED = 4L;
//    public static final Long ASSIGNED_UNCONFIRM = 2L;
    private Long fromInvoiceFilter;
    private Long toInvoiceFilter;
    private Long bookTypeIdFilter;
    private String serialNoFilter;
    private String billDepartmentKind;
    private String billDepartmentName;
    private Long shopId;

    private String status;
    private Long statusType;

    private String includeStaff;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getStatusType() {
        return statusType;
    }

    public void setStatusType(Long statusType) {
        this.statusType = statusType;
    }

    public String getIncludeStaff() {
        return includeStaff;
    }

    public void setIncludeStaff(String includeStaff) {
        this.includeStaff = includeStaff;
    }
    
    


    public List<InvoiceTransferLogBean> lookUpInvoiceHistory(String serialNo, Long blockNo, Long fromInvoice, Long toInvoice){
       StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT DISTINCT ");
        sqlBuffer.append(" inv.serial_no as serialNo, ");
        sqlBuffer.append(" inv.block_no as blockNo, ");
        sqlBuffer.append(" inv.from_invoice as fromInvoice, ");
        sqlBuffer.append(" inv.to_invoice as toInvoice, ");
        sqlBuffer.append(" inv.date_created as dateCreated, ");
        sqlBuffer.append(" inv.user_created as userCreated, ");

        sqlBuffer.append(" stf.staff_code as staffCode, ");
        sqlBuffer.append(" shpa.shop_code as childShopCode, ");
        sqlBuffer.append(" shpb.shop_code as parentShopCode, ");
        sqlBuffer.append(" inv.transfer_type as transferType, ");
        sqlBuffer.append(" decode(inv.transfer_type, 1, 'Tạo hóa đơn', 2, 'Giao hóa đơn',");
        sqlBuffer.append(" 3, 'Xác nhận hóa đơn', 4, 'Thu hồi hóa đơn', 5, 'Hủy - Chờ duyệt', ");
        sqlBuffer.append(" 6, 'Khôi phục huỷ', 7, 'Đã duyệt huỷ', 8, 'Xóa ở cấp Viettel', ");
        sqlBuffer.append(" 9, 'Khôi phục HĐ đã lập' )");

        sqlBuffer.append(" transferTypeName ");

        sqlBuffer.append(" FROM invoice_transfer_log inv ");
        sqlBuffer.append(" LEFT JOIN shop shpa ");
        sqlBuffer.append(" ON shpa.shop_id = inv.child_shop_id ");
        sqlBuffer.append(" LEFT JOIN shop shpb ");
        sqlBuffer.append(" ON shpb.shop_id = inv.parent_shop_id ");
        sqlBuffer.append(" LEFT JOIN staff stf ");
        sqlBuffer.append(" ON stf.staff_id = inv.staff_id ");

        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" inv.serial_no = ? ");
        parameterList.add(serialNo);

//        sqlBuffer.append(" AND inv.block_no = ? ");
//        parameterList.add(blockNo);

        sqlBuffer.append(" AND inv.from_invoice <= ? ");
        parameterList.add(fromInvoice);
        sqlBuffer.append(" AND inv.to_invoice >= ? ");
        parameterList.add(toInvoice);
        sqlBuffer.append(" AND inv.transfer_type in  (" + 1 + "," + 2 + "," + 3 + "," + 4 + "," + 5 + "," + 6 + "," + 7 + "," + 8 + "," + 9 + ")");
        //parameterList.add("(" + ASSIGNED_UNCONFIRM + "," + INVOICE_RETRIEVED + ")");
        
        sqlBuffer.append(" ORDER BY inv.date_created ");

        Session session = getSession();
        Query queryObject = session.createSQLQuery(sqlBuffer.toString()).
                addScalar("serialNo", Hibernate.STRING).
                addScalar("blockNo", Hibernate.LONG).
                addScalar("fromInvoice", Hibernate.LONG).
                addScalar("toInvoice", Hibernate.LONG).
                addScalar("dateCreated", Hibernate.TIMESTAMP).
                addScalar("userCreated", Hibernate.STRING).
                addScalar("staffCode", Hibernate.STRING).
                addScalar("childShopCode", Hibernate.STRING).
                addScalar("parentShopCode", Hibernate.STRING).
                addScalar("transferTypeName", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(InvoiceTransferLogBean.class));

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }

        return queryObject.list();
    }

    public List<InvoiceListReportBean> reportInvoiceList(String serial) {
        String sqlBuffer = "";
        StringBuffer sqlBufferList = new StringBuffer();
        StringBuffer sqlBufferDestroyed = new StringBuffer();
        List parameterList = new ArrayList();

        if (statusType == null || statusType.compareTo(1L)==0){
            sqlBufferList.append(reportNormalInvoiceListSQL(parameterList, serial));
        }
        if (statusType == null || statusType.compareTo(2L)==0){
            sqlBufferDestroyed.append(reportDestroyInvoiceListSQL(parameterList, serial));
        }
        if ((!"".equals(sqlBufferList.toString().trim())) && (!"".equals(sqlBufferDestroyed.toString().trim())))
            sqlBuffer = " UNION ALL ";
        sqlBuffer = sqlBufferList.toString() + sqlBuffer + sqlBufferDestroyed;
        sqlBuffer += " ORDER BY serialno, blockno, frominvoice ";

        Session session = getSession();
        Query queryObject = session.createSQLQuery(sqlBuffer.toString()).
                addScalar("serialNo", Hibernate.STRING).
                addScalar("invoiceListId", Hibernate.LONG).
                addScalar("fromInvoice", Hibernate.LONG).
                addScalar("toInvoice", Hibernate.LONG).
                addScalar("currInvoiceNo", Hibernate.LONG).
                addScalar("dateAssign", Hibernate.DATE).
                addScalar("blockNo", Hibernate.STRING).
                addScalar("staffCode", Hibernate.STRING).
                addScalar("departmentCode", Hibernate.STRING).
                addScalar("parentName", Hibernate.STRING).
                addScalar("status", Hibernate.LONG).
                addScalar("statusCode", Hibernate.STRING).
                addScalar("statusName", Hibernate.STRING).
                addScalar("parentId", Hibernate.LONG).
                setResultTransformer(Transformers.aliasToBean(InvoiceListReportBean.class));
        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }
        return queryObject.list();
    }

    private String reportNormalInvoiceListSQL(List parameterList, String serial) {
        StringBuffer sqlBuffer = new StringBuffer();

        sqlBuffer.append("SELECT ");
        sqlBuffer.append("ivl.serial_no as serialNo, ");
        sqlBuffer.append("ivl.invoice_list_id as invoiceListId, ");
        
//        sqlBuffer.append("ivl.from_invoice as fromInvoice, ");
//        sqlBuffer.append("ivl.to_invoice as toInvoice, ");
        sqlBuffer.append("ivl.v_from_invoice as fromInvoice, ");
        sqlBuffer.append("ivl.v_to_invoice as toInvoice, ");
        
        sqlBuffer.append("ivl.curr_invoice_no as currInvoiceNo, ");
        sqlBuffer.append("ivl.date_assign as dateAssign, ");
        sqlBuffer.append("ivl.block_no as blockNo, ");
        sqlBuffer.append("stf.staff_code as staffCode, ");
        sqlBuffer.append("shpa.shop_code as departmentCode, ");
        sqlBuffer.append("shpb.name as parentName, ");
        sqlBuffer.append("shpb.shop_id as parentId, ");

        sqlBuffer.append("ivl.status as status, ");
        sqlBuffer.append("ivl.v_status as statusCode, ");

//        sqlBuffer.append("decode(ivl.status, 1, 'Chưa sử dụng', 2, 'Chưa xác nhận',");
//        sqlBuffer.append("3, 'Đã xác nhận', 4, 'Đã sử dụng') ");
//        sqlBuffer.append(" statusName ");

        sqlBuffer.append(" (SELECT aps1.name FROM app_params aps1 WHERE aps1.TYPE = 'INVOICE_LIST.STATUS' AND aps1.code = ivl.v_status) AS statusName ");

        //Thay 'FROM invoice_list ivl' = 'FROM v_invoice_list ivl'
        sqlBuffer.append(" FROM v_invoice_list ivl ");

        sqlBuffer.append(" JOIN book_type bt ");
        sqlBuffer.append(" ON bt.book_type_id = ivl.book_type_id ");
        sqlBuffer.append(" LEFT JOIN shop shpa ");
        sqlBuffer.append(" ON shpa.shop_id = ivl.shop_id ");
        sqlBuffer.append(" LEFT JOIN shop shpb ");
        sqlBuffer.append(" ON shpb.shop_id = shpa.parent_shop_id ");
        sqlBuffer.append(" LEFT JOIN staff stf ");
        sqlBuffer.append(" ON stf.staff_id = ivl.staff_id ");
        sqlBuffer.append(" WHERE 1 = 1");

        if (serial != null && !serial.trim().equals("")) {
            sqlBuffer.append(" AND ivl.serial_no = ? ");
            parameterList.add(serial);
        }

        sqlBuffer.append(getInvoiceListFilter(parameterList));
        return sqlBuffer.toString();
    }

    private String reportDestroyInvoiceListSQL(List parameterList, String serial) {
        StringBuffer sqlBuffer = new StringBuffer();

        sqlBuffer.append("SELECT ");
        sqlBuffer.append("ivl.serial_no as serialNo, ");
        sqlBuffer.append("ivl.invoice_list_id as invoiceListId, ");
        sqlBuffer.append("ivl.from_invoice as fromInvoice, ");
        sqlBuffer.append("ivl.to_invoice as toInvoice, ");
        sqlBuffer.append("null as currInvoiceNo, ");
        sqlBuffer.append("null as dateAssign, ");
        sqlBuffer.append("ivl.block_no as blockNo, ");
        sqlBuffer.append("stf.staff_code as staffCode, ");
        sqlBuffer.append("shpa.shop_code as departmentCode, ");
        sqlBuffer.append("shpb.name as parentName, ");
        sqlBuffer.append("shpb.shop_id as parentId, ");
        sqlBuffer.append(" ivl.status as status, ");
        sqlBuffer.append(" to_char(ivl.status) as statusCode, ");
//        sqlBuffer.append("decode(ivl.status, 0, 'Đang chờ duyệt hủy', 1, 'Đã hủy',");
//        sqlBuffer.append(" 2, 'Chờ duyệt tổn thất', 3, 'Đã tổn thất') ");
//        sqlBuffer.append(" statusName ");

        sqlBuffer.append(" (SELECT aps2.name FROM app_params aps2 WHERE aps2.TYPE = 'INVOICE_DESTROYED.STATUS' AND aps2.code = ivl.status) AS statusName ");
        
        sqlBuffer.append(" FROM invoice_destroyed ivl ");
        sqlBuffer.append(" JOIN book_type bt ");
        sqlBuffer.append(" ON bt.book_type_id = ivl.book_type_id ");
        sqlBuffer.append(" LEFT JOIN shop shpa ");
        sqlBuffer.append(" ON shpa.shop_id = ivl.shop_id ");
        sqlBuffer.append(" LEFT JOIN shop shpb ");
        sqlBuffer.append(" ON shpb.shop_id = shpa.parent_shop_id ");
        sqlBuffer.append(" LEFT JOIN staff stf ");
        sqlBuffer.append(" ON stf.staff_id = ivl.staff_id ");
        sqlBuffer.append(" WHERE 1 = 1 ");
        if (serial != null && !serial.trim().equals("")) {
            sqlBuffer.append(" AND ivl.serial_no = ? ");
            parameterList.add(serial);
        }

        /** Filter */
        sqlBuffer.append(getInvoiceListFilter(parameterList));
        return sqlBuffer.toString();

    }

    public List<InvoiceList> findAllSerialInvoiceList(Long shopId, Long staffId) {
        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT DISTINCT ");
        sqlBuffer.append(" inv.SERIAL_NO as serialNo ");
        sqlBuffer.append(" FROM INVOICE_LIST inv ");
        sqlBuffer.append(" WHERE 1=1 ");
        
        sqlBuffer.append(" AND BOOK_TYPE_ID in (select BOOK_TYPE_ID from BOOK_TYPE WHERE INVOICE_TYPE = ? ) ");
        parameterList.add(Constant.INVOICE_TYPE_SALE);
        
        sqlBuffer.append(" AND inv.SHOP_ID = ? ");
        parameterList.add(shopId);
//        sqlBuffer.append(" AND ");
//        sqlBuffer.append(" inv.STAFF_ID = ? ");
//        parameterList.add(staffId);
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" inv.STATUS = ? ");
        //parameterList.add(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF);
        parameterList.add(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" inv.CURR_INVOICE_NO >= inv.FROM_INVOICE ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" inv.CURR_INVOICE_NO <= inv.TO_INVOICE ");        
        if (this.serialNoFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" inv.SERIAL_NO = ? ");
            parameterList.add(this.serialNoFilter);
        }
        sqlBuffer.append(" ORDER BY  serialNo ");

        Session session = getSession();
        Query queryObject = session.createSQLQuery(sqlBuffer.toString()).
                addScalar("serialNo", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(InvoiceList.class));

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }

        return queryObject.list();
    }

    public List<InvoiceList> findInvoiceListBySerialNo(Long shopId, Long staffId) {
        StringBuffer sqlBuffer = new StringBuffer();
        List parameterList = new ArrayList();

        sqlBuffer.append(" SELECT ");
        sqlBuffer.append(" inv.* ");
        sqlBuffer.append(" FROM INVOICE_LIST inv ");
        sqlBuffer.append(" WHERE ");
        sqlBuffer.append(" inv.SHOP_ID = ? ");
        parameterList.add(shopId);
//        sqlBuffer.append(" AND ");
//        sqlBuffer.append(" inv.STAFF_ID = ? ");
//        parameterList.add(staffId);
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" inv.STATUS = ? ");
        //parameterList.add(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_STAFF);
        parameterList.add(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" inv.CURR_INVOICE_NO IS NOT NULL ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" inv.CURR_INVOICE_NO > 0 ");
        if (this.serialNoFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" inv.SERIAL_NO = ? ");
            parameterList.add(this.serialNoFilter);
        }
        sqlBuffer.append(" ORDER BY  inv.INVOICE_LIST_ID ");

        Session session = getSession();
        Query queryObject = session.createSQLQuery(sqlBuffer.toString()).addEntity(InvoiceList.class);

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }

        return queryObject.list();
    }

    private String getInvoiceListFilter(List parameterList) {
        StringBuffer sqlBuffer = new StringBuffer();
        if (this.bookTypeIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" ivl.book_type_id = ? ");
            parameterList.add(this.bookTypeIdFilter);
        }

        if (this.bookTypeIdFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" ivl.book_type_id = ? ");
            parameterList.add(this.bookTypeIdFilter);
        }
        if (this.fromInvoiceFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append("( ivl.from_invoice >= ? OR ( ivl.from_invoice <= ? AND ? <= ivl.to_invoice ))");
            parameterList.add(this.fromInvoiceFilter);
            parameterList.add(this.fromInvoiceFilter);
            parameterList.add(this.fromInvoiceFilter);
        }
        if (this.toInvoiceFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append("( ivl.to_invoice <= ? OR ( ivl.from_invoice <= ? AND ? <= ivl.to_invoice ))");
            parameterList.add(this.toInvoiceFilter);
            parameterList.add(this.toInvoiceFilter);
            parameterList.add(this.toInvoiceFilter);
        }

        
        if (this.shopId != null) {
            sqlBuffer.append(" AND ");
            if (this.billDepartmentKind.equals("1")) {
                sqlBuffer.append(" ivl.shop_id = ? ");
                parameterList.add(this.shopId);
                if (null != this.includeStaff && "0".equals(this.includeStaff.trim())){
                    sqlBuffer.append(" AND ivl.staff_id IS NULL ");
                }
            } else
                if (this.billDepartmentKind.equals("2")) {
                sqlBuffer.append("ivl.staff_id = ?");
                parameterList.add(this.shopId);
            } 
            if (null == this.billDepartmentKind.trim() || "".equals(this.billDepartmentKind.trim()) ) {
                sqlBuffer.append(" ( ivl.staff_id = ? OR ivl.shop_id = ? ) ");
                parameterList.add(this.shopId);
                parameterList.add(this.shopId);                
            }
        }
        
        if ((this.status != null) && (!"".equals(this.status.trim()))) {
            sqlBuffer.append(" AND ");
            if (this.statusType.compareTo(1L) == 0){
                sqlBuffer.append("ivl.v_status LIKE ?");
                parameterList.add(this.status + "%");
            }
            else{
                sqlBuffer.append("ivl.status = ? ");
                parameterList.add(this.status);
            }
        }


        return sqlBuffer.toString();
    }

    public void save(InvoiceList transientInstance) {
        log.debug("saving InvoiceList instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(InvoiceList persistentInstance) {
        log.debug("deleting InvoiceList instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public InvoiceList findById(java.lang.Long id) {
        log.debug("getting InvoiceList instance with id: " + id);
        try {
            InvoiceList instance = (InvoiceList) getSession().get("com.viettel.im.database.BO.InvoiceList", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(InvoiceList instance) {
        log.debug("finding InvoiceList instance by example");
        try {
            List results = getSession().createCriteria("com.viettel.im.database.BO.InvoiceList").add(Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding InvoiceList instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from InvoiceList as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByBookTypeId(Object bookTypeId) {
        return findByProperty(BOOK_TYPE_ID, bookTypeId);
    }

    public List findBySerialNo(Object serialNo) {
        return findByProperty(SERIAL_NO, serialNo);
    }

    public List findByBlockNo(Object blockNo) {
        return findByProperty(BLOCK_NO, blockNo);
    }

    public List findByFromInvoice(Object fromInvoice) {
        return findByProperty(FROM_INVOICE, fromInvoice);
    }

    public List findByToInvoice(Object toInvoice) {
        return findByProperty(TO_INVOICE, toInvoice);
    }

    public List findByCurrInvoiceNo(Object currInvoiceNo) {
        return findByProperty(CURR_INVOICE_NO, currInvoiceNo);
    }

    public List findByShopId(Object shopId) {
        return findByProperty(SHOP_ID, shopId);
    }

    public List findByUserCreated(Object userCreated) {
        return findByProperty(USER_CREATED, userCreated);
    }

    public List findByStatus(Object status) {
        return findByProperty(STATUS, status);
    }

    public List findAll() {
        log.debug("finding all InvoiceList instances");
        try {
            String queryString = "from InvoiceList";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public InvoiceList merge(InvoiceList detachedInstance) {
        log.debug("merging InvoiceList instance");
        try {
            InvoiceList result = (InvoiceList) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(InvoiceList instance) {
        log.debug("attaching dirty InvoiceList instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(InvoiceList instance) {
        log.debug("attaching clean InvoiceList instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public Long getBookTypeIdFilter() {
        return bookTypeIdFilter;
    }

    public void setBookTypeIdFilter(Long bookTypeIdFilter) {
        this.bookTypeIdFilter = bookTypeIdFilter;
    }

    public Long getFromInvoiceFilter() {
        return fromInvoiceFilter;
    }

    public void setFromInvoiceFilter(Long fromInvoiceFilter) {
        this.fromInvoiceFilter = fromInvoiceFilter;
    }

    public Long getToInvoiceFilter() {
        return toInvoiceFilter;
    }

    public void setToInvoiceFilter(Long toInvoiceFilter) {
        this.toInvoiceFilter = toInvoiceFilter;
    }

    public String getSerialNoFilter() {
        return serialNoFilter;
    }

    public void setSerialNoFilter(String serialNoFilter) {
        this.serialNoFilter = serialNoFilter;
    }

    public String getBillDepartmentKind() {
        return billDepartmentKind;
    }

    public void setBillDepartmentKind(String billDepartmentKind) {
        this.billDepartmentKind = billDepartmentKind;
    }

    public String getBillDepartmentName() {
        return billDepartmentName;
    }

    public void setBillDepartmentName(String billDepartmentName) {
        this.billDepartmentName = billDepartmentName;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    
    public List getCurrentInvoiceList(Long status, Long staffId, Long shopId, Long invoiceListId, String serialNo, Long currInvoiceNo){
        List lstParam = new ArrayList();
        String queryString = "";
        queryString = "";
        queryString += " SELECT il.invoice_list_id AS invoiceListId ";
        queryString += "         ,il.serial_no AS serialNo ";
        queryString += "         ,il.block_no AS blockNo ";
        queryString += "         ,il.from_invoice AS fromInvoice ";
        queryString += "         ,il.to_invoice AS toInvoice ";
        queryString += "         ,il.curr_invoice_no AS currInvoiceNo ";
        queryString += "         ,(il.serial_no || LPAD(il.block_no,(SELECT bt.length_name FROM book_type bt WHERE bt.book_type_id = il.book_type_id),'0') || LPAD(il.curr_invoice_no,(SELECT bt.length_invoice FROM book_type bt WHERE bt.book_type_id = il.book_type_id),'0')) AS invoiceNumber ";
        queryString += " FROM invoice_list IL ";
        queryString += " WHERE 1=1 ";
        queryString += " AND il.curr_invoice_no > 0 ";
        queryString += " AND il.curr_invoice_no >= il.from_invoice ";
        queryString += " AND il.curr_invoice_no <= il.to_invoice ";
        if (invoiceListId != null){            
            queryString += " AND il.invoice_list_id = ? ";
            lstParam.add(invoiceListId);
        }
        if (status != null){
            queryString += " AND il.status = ? ";
            lstParam.add(status);
        }
        if (shopId != null){
            queryString += " AND il.shop_id = ? ";
            //lstParam.add(shopId);
            lstParam.add(Constant.MOV_SHOP_ID); //lay tu cap cong ty
        }
        if (staffId != null){
            queryString += " AND il.staff_id = ? ";
            lstParam.add(staffId);
        }
        if (serialNo != null){
            queryString += " AND il.serial_No = ? ";
            lstParam.add(serialNo);
        }
        if (currInvoiceNo != null){
            queryString += " AND il.curr_invoice_no = ? ";
            lstParam.add(currInvoiceNo);
        }
        queryString += " ORDER BY serialNo, currInvoiceNo ";
        
        Query queryObject = getSession().createSQLQuery(queryString).
            addScalar("invoiceListId", Hibernate.LONG).
            addScalar("serialNo", Hibernate.STRING).
            addScalar("blockNo", Hibernate.STRING).
            addScalar("fromInvoice", Hibernate.STRING).
            addScalar("toInvoice", Hibernate.STRING).
            addScalar("currInvoiceNo", Hibernate.STRING).
            addScalar("invoiceNumber", Hibernate.STRING).
            setResultTransformer(Transformers.aliasToBean(CurrentInvoiceListBean.class));
        for (int idx = 0; idx < lstParam.size(); idx++) {
            queryObject.setParameter(idx, lstParam.get(idx));
        }
        return queryObject.list();
    }
    public List getCurrentCreditInvoiceList(Long status, Long staffId, Long shopId, Long invoiceListId, String serialNo, Long currInvoiceNo) {
        System.out.println("tamdt1 - bat dau ham getCurrentInvoiceList");

        List lstParam = new ArrayList();
        String queryString = "";
        queryString = "";
        queryString += " SELECT il.invoice_list_id AS invoiceListId ";
        queryString += "         ,il.serial_no AS serialNo ";
        queryString += "         ,il.block_no AS blockNo ";
        queryString += "         ,il.from_invoice AS fromInvoice ";
        queryString += "         ,il.to_invoice AS toInvoice ";
        queryString += "         ,il.curr_invoice_no AS currInvoiceNo ";
        queryString += "         ,(il.serial_no || LPAD(il.block_no,(SELECT bt.length_name FROM book_type bt WHERE bt.book_type_id = il.book_type_id),'0') || LPAD(il.curr_invoice_no,(SELECT bt.length_invoice FROM book_type bt WHERE bt.book_type_id = il.book_type_id),'0')) AS invoiceNumber ";
        queryString += " FROM invoice_list IL, book_type bt ";
        queryString += " WHERE 1=1 ";
        queryString += " AND il.book_type_id = bt.book_type_id ";
        queryString += " AND il.curr_invoice_no > 0 ";
        queryString += " AND il.curr_invoice_no >= il.from_invoice ";
        queryString += " AND il.curr_invoice_no <= il.to_invoice ";
        //HaiNT41 2/7/2012 : them truong invoiceType
//        queryString += " AND bt.invoice_type = ? ";
//        lstParam.add(Constant.INVOICE_TYPE_CREDIT);
        //end HaiNT41
        if (invoiceListId != null) {
            queryString += " AND il.invoice_list_id = ? ";
            lstParam.add(invoiceListId);
        }
        if (status != null) {
            queryString += " AND il.status = ? ";
            lstParam.add(status);
        }
//        if (shopId != null) {
        queryString += " AND il.shop_id = ? ";
        lstParam.add(Constant.MOV_SHOP_ID);
//        }
        if (staffId != null) {
            queryString += " AND il.staff_id = ? ";
            lstParam.add(staffId);
        }
        if (serialNo != null) {
            queryString += " AND il.serial_No = ? ";
            lstParam.add(serialNo);
        }
        if (currInvoiceNo != null) {
            queryString += " AND il.curr_invoice_no = ? ";
            lstParam.add(currInvoiceNo);
        }
        queryString += " ORDER BY serialNo, currInvoiceNo ";

        System.out.println("tamdt1 - getCurrentInvoiceList, queryString = " + queryString);
        System.out.println("tamdt1 - getCurrentInvoiceList, param = ");
        for (int idx = 0; idx < lstParam.size(); idx++) {
            System.out.println("tamdt1 - getCurrentInvoiceList, param[" + idx + "]=" + lstParam.get(idx));
        }

        Query queryObject = getSession().createSQLQuery(queryString).
                addScalar("invoiceListId", Hibernate.LONG).
                addScalar("serialNo", Hibernate.STRING).
                addScalar("blockNo", Hibernate.STRING).
                addScalar("fromInvoice", Hibernate.STRING).
                addScalar("toInvoice", Hibernate.STRING).
                addScalar("currInvoiceNo", Hibernate.STRING).
                addScalar("invoiceNumber", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(CurrentInvoiceListBean.class));
        for (int idx = 0; idx < lstParam.size(); idx++) {
            queryObject.setParameter(idx, lstParam.get(idx));
        }

        System.out.println("tamdt1 - ket thuc ham getCurrentInvoiceList");

        return queryObject.list();
    }
//    public List<InvoiceList> findSerialInvoiceListCheckUsed(Long shopId, Long staffId) {
//        AppParamsDAO appParamsDAO = new AppParamsDAO();
//        appParamsDAO.setSession(getSession());
//        String checkGetInvoiceUsed = appParamsDAO.findValueAppParams(Constant.GET_INVOICE_USED, "0");
//        StringBuffer sqlBuffer = new StringBuffer();
//        List parameterList = new ArrayList();
//        sqlBuffer.append(" SELECT  ");
//        sqlBuffer.append(" inv.serial_no as serialNo ");
//        sqlBuffer.append(" FROM INVOICE_LIST inv, BOOK_TYPE bt ");
//        sqlBuffer.append(" WHERE inv.book_type_id = bt.book_type_id");
//        sqlBuffer.append(" AND inv.SHOP_ID = ? ");
//        //sonbc2
//        parameterList.add(Constant.MOV_SHOP_ID);
////        sqlBuffer.append(" AND ");
////        sqlBuffer.append(" inv.STAFF_ID = ? ");
////        parameterList.add(staffId);
//        sqlBuffer.append(" AND ");
//        sqlBuffer.append(" inv.STATUS = ? ");
//        parameterList.add(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
//        //end sonbc2
//        sqlBuffer.append(" AND ");
//        sqlBuffer.append(" inv.CURR_INVOICE_NO >= inv.FROM_INVOICE ");
//        sqlBuffer.append(" AND ");
//        sqlBuffer.append(" inv.CURR_INVOICE_NO <= inv.TO_INVOICE ");
//        sqlBuffer.append(" AND ");
//        sqlBuffer.append(" inv.INVOICE_TYPE = ? ");
//        parameterList.add(Constant.INVOICE_TYPE_SALE);
//        if (this.serialNoFilter != null) {
//            sqlBuffer.append(" AND ");
//            sqlBuffer.append(" bt.serial_real = ? ");
//            parameterList.add(this.serialNoFilter);
//        }
//        if (checkGetInvoiceUsed != null && "1".equals(checkGetInvoiceUsed)) {
//            sqlBuffer.append(" AND ");
//            sqlBuffer.append(" bt.serial_real IN (SELECT VALUE FROM app_params WHERE TYPE = ? AND status = ?) ");
//            parameterList.add(Constant.INVOICE_USED);
//            parameterList.add(Constant.STATUS_ACTIVE.toString());
//        }
//        sqlBuffer.append(" ORDER BY serialNo ");
//
//        Session session = getSession();
//        Query queryObject = session.createSQLQuery(sqlBuffer.toString()).
//                addScalar("serialNo", Hibernate.STRING).
//                setResultTransformer(Transformers.aliasToBean(InvoiceList.class));
//
//        for (int i = 0; i < parameterList.size(); i++) {
//            queryObject.setParameter(i, parameterList.get(i));
//        }
//
//        return queryObject.list();
//    }
//
    public List<InvoiceList> findSerialCreditInvoiceListCheckUsed(Long shopId, Long staffId) {
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        String checkGetInvoiceUsed = appParamsDAO.findValueAppParams(Constant.GET_INVOICE_USED, "0");
        StringBuilder sqlBuffer = new StringBuilder();
        List parameterList = new ArrayList();
        sqlBuffer.append(" SELECT  ");
        sqlBuffer.append(" inv.serial_no as serialNo ");
        sqlBuffer.append(" FROM INVOICE_LIST inv, BOOK_TYPE bt ");
        sqlBuffer.append(" WHERE inv.book_type_id = bt.book_type_id");
        sqlBuffer.append(" AND inv.SHOP_ID = ? ");
        parameterList.add(Constant.MOV_SHOP_ID);
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" inv.STATUS = ? ");
        parameterList.add(Constant.INVOICE_LIST_STATUS_AVAILABLE_IN_SHOP);
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" inv.CURR_INVOICE_NO >= inv.FROM_INVOICE ");
        sqlBuffer.append(" AND ");
        sqlBuffer.append(" inv.CURR_INVOICE_NO <= inv.TO_INVOICE ");
//        tannh 20181101
//        sqlBuffer.append(" AND ");
//        sqlBuffer.append(" bt.INVOICE_TYPE = ? ");
//        parameterList.add(Constant.INVOICE_TYPE_CREDIT);
        if (this.serialNoFilter != null) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" bt.serial_real = ? ");
            parameterList.add(this.serialNoFilter);
        }
        if (checkGetInvoiceUsed != null && "1".equals(checkGetInvoiceUsed)) {
            sqlBuffer.append(" AND ");
            sqlBuffer.append(" bt.serial_real IN (SELECT VALUE FROM app_params WHERE TYPE = ? AND status = ?) ");
            parameterList.add(Constant.CREDIT_INVOICE_USED);
            parameterList.add(Constant.STATUS_ACTIVE.toString());
        }
        sqlBuffer.append(" ORDER BY serialNo ");

        Session session = getSession();
        Query queryObject = session.createSQLQuery(sqlBuffer.toString()).
                addScalar("serialNo", Hibernate.STRING).
                setResultTransformer(Transformers.aliasToBean(InvoiceList.class));

        for (int i = 0; i < parameterList.size(); i++) {
            queryObject.setParameter(i, parameterList.get(i));
        }

        return queryObject.list();
    }
}