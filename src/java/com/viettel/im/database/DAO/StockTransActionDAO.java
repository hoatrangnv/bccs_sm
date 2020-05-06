package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.ImportStockForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Partner;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockTransAction entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockTransAction
 * @author MyEclipse Persistence Tools
 */
public class StockTransActionDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(StockTransActionDAO.class);
    // property constants
    public static final String STOCK_TRANS_ID = "stockTransId";
    public static final String ACTION_CODE = "actionCode";
    public static final String ACTION_TYPE = "actionType";
    public static final String NOTE = "note";
    public static final String USERNAME = "username";
    public static final String ACTION_STAFF_ID = "actionStaffId";
    public static final String APPROVE_STAFF_ID = "approveStaffId";


    /*
     * Author: ThanhNC
     * Date created: 18/03/2009
     * Purpose: Copy data from StockTransAction to ExportStockForm
     */
    public boolean copyBOToExpForm(StockTransAction bo, ExportStockForm form) throws Exception {
        if (bo == null) {
            return false;
        }
        form.setStockTransId(bo.getStockTransId()); //        Huynq13 20170406 set StockTransId to form
        form.setCmdExportCode(bo.getActionCode());
        form.setActionId(bo.getActionId());
        form.setSenderId(bo.getActionStaffId());
        if (bo.getActionStaffId() != null) {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findById(bo.getActionStaffId());
            form.setSender(staff.getName());

        }
        //Tim kiem giao dich xuat nhap
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findById(bo.getStockTransId());
        if (stockTrans != null) {

            //TrongLV: kenh can gan cho hang hoa trong lenh xuat
            form.setChannelTypeId(stockTrans.getChannelTypeId());
            //TrongLV

            //Thong tin kho nhap

            form.setToOwnerId(stockTrans.getToOwnerId());
            form.setToOwnerType(stockTrans.getToOwnerType());

            form.setShopImportedId(stockTrans.getToOwnerId());            
            form.setShopImportedType(stockTrans.getToOwnerType());
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            if (stockTrans.getToOwnerType() != null && Constant.OWNER_TYPE_SHOP.equals(stockTrans.getToOwnerType())) {
                Shop shopImp = shopDAO.findById(stockTrans.getToOwnerId());
                if (shopImp != null) {
                    form.setShopImportedName(shopImp.getName());
                    form.setShopImportedCode(shopImp.getShopCode());
                    form.setShopImportedChannelTypeId(shopImp.getChannelTypeId());
//                    lamnt bo sung shop de nhan dien kho giam tru
                    form.setShopId(shopImp.getShopId().toString());
//                    end
                }
            }
            if (stockTrans.getToOwnerType() != null && Constant.OWNER_TYPE_STAFF.equals(stockTrans.getToOwnerType())) {
                Staff staffImp = staffDAO.findById(stockTrans.getToOwnerId());
                if (staffImp != null) {
                    form.setShopImportedCode(staffImp.getStaffCode());
                    form.setShopImportedName(staffImp.getName());
                    form.setShopImportedChannelTypeId(staffImp.getChannelTypeId());
                }
            }
            if (stockTrans.getToOwnerType() != null && Constant.OWNER_TYPE_PARTNER.equals(stockTrans.getToOwnerType())) {
                PartnerDAO partnerDAO = new PartnerDAO();
                partnerDAO.setSession(this.getSession());
                Partner partnerImp = partnerDAO.findById(stockTrans.getToOwnerId());
                if (partnerImp != null) {
                    form.setShopImportedCode(partnerImp.getPartnerCode());
                    form.setShopImportedName(partnerImp.getPartnerName());
                }
            }
            //Set cac trang thai no, thanh toan, trang thai boc tham
            form.setDepositStatus(stockTrans.getDepositStatus());
            form.setPayStatus(stockTrans.getPayStatus());
            form.setDrawStatus(stockTrans.getDrawStatus());
            //Thong tin kho xuat
            form.setShopExportId(stockTrans.getFromOwnerId());
            //Neu loai kho la kho cua hang chi nhanh
            if (stockTrans.getFromOwnerId() != null && Constant.OWNER_TYPE_SHOP.equals(stockTrans.getFromOwnerType())) {

                Shop shopExp = shopDAO.findById(stockTrans.getFromOwnerId());
                if (shopExp != null) {
                    form.setShopExportCode(shopExp.getShopCode());
                    form.setShopExportName(shopExp.getName());
                }
            }
            if (Constant.OWNER_TYPE_STAFF.equals(stockTrans.getFromOwnerType()) && stockTrans.getFromOwnerId() != null) {
                Staff staffExp = staffDAO.findById(stockTrans.getFromOwnerId());
                if (staffExp != null) {
                    form.setShopExportCode(staffExp.getStaffCode());
                    form.setShopExportName(staffExp.getName());
                }
            }
            form.setDateExported(DateTimeUtils.convertDateTimeToString(stockTrans.getCreateDatetime(),"yyyy-MM-dd"));
            
            if (stockTrans.getReasonId() != null) {
                ReasonDAO reasonDAO = new ReasonDAO();
                reasonDAO.setSession(this.getSession());
                Reason reason = reasonDAO.findById((Long) stockTrans.getReasonId());
                if (reason != null && reason.getReasonStatus().equals(Constant.STATUS_USE.toString())) {
                    form.setReasonName(reason.getReasonName());
                }
                form.setReasonId(stockTrans.getReasonId().toString());
            }
            form.setStatus(stockTrans.getStockTransStatus());
        }

        form.setNote(bo.getNote());

        return true;
    }
    /*
     * Author: ThanhNC
     * Date created: 18/03/2009
     * Purpose: Copy data from StockTransAction to ImportStockForm
     */

    public boolean copyBOToImpForm(StockTransAction bo, ImportStockForm form) throws Exception {
        if (bo == null) {
            return false;
        }
        form.setCmdImportCode(bo.getActionCode());
        form.setReceiverId(bo.getActionStaffId());
        form.setActionId(bo.getActionId());
        if (bo.getActionStaffId() != null) {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(this.getSession());
            Staff staff = staffDAO.findById(bo.getActionStaffId());
            form.setReceiver(staff.getName());
        }
        //Tim kiem giao dich xuat nhap
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findById(bo.getStockTransId());
        if (stockTrans != null) {
            //Thong tin kho nhap
            form.setShopImportId(stockTrans.getToOwnerId());
            //Thong tin kho xuat
            form.setShopExportId(stockTrans.getFromOwnerId());

            //Neu loai kho la kho cua hang chi nhanh
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            Shop shopExp = shopDAO.findById(form.getShopExportId());
            if (shopExp != null) {
                form.setShopExportName(shopExp.getName());
                form.setShopExportCode(shopExp.getShopCode());
            }

            //tamdt1, 15/07/2010, start
            //them kho xuat trong truong hop thu hoi hang tu nhan vien
            if (Constant.OWNER_TYPE_STAFF.equals(form.getFromOwnerType())) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                Staff expStaff = staffDAO.findById(form.getShopExportId());
                if (expStaff != null) {
                    form.setShopExportName(expStaff.getName());
                    form.setShopExportCode(expStaff.getStaffCode());
                }
            }
            //tamdt1, end

            if (Constant.OWNER_TYPE_SHOP.equals(stockTrans.getToOwnerType()) && stockTrans.getToOwnerId() != null) {
                Shop shop = shopDAO.findById(stockTrans.getToOwnerId());
                form.setShopImportCode(shop.getShopCode());
                form.setShopImportName(shop.getName());
            }
            
            // tutm1 14/08/2013 truong hop nhap hang tu doi tac. 
            if (stockTrans.getFromOwnerType() != null && Constant.OWNER_TYPE_PARTNER.equals(stockTrans.getFromOwnerType())) {
                PartnerDAO partnerDAO = new PartnerDAO();
                partnerDAO.setSession(this.getSession());
                Partner partnerImp = partnerDAO.findById(stockTrans.getFromOwnerId());
                if (partnerImp != null) {
                    form.setShopExportCode(partnerImp.getPartnerCode());
                    form.setShopExportName(partnerImp.getPartnerName());
                    form.setFromOwnerCode(partnerImp.getPartnerCode());
                    form.setFromOwnerName(partnerImp.getPartnerName());
                }
            }
            // end tutm1. 
                        
            form.setDateImported(DateTimeUtils.convertDateTimeToString(stockTrans.getCreateDatetime(),"yyyy-MM-dd"));
            if (stockTrans.getReasonId() != null) {
                ReasonDAO reasonDAO = new ReasonDAO();
                reasonDAO.setSession(this.getSession());
                Reason reason = reasonDAO.findById((Long) stockTrans.getReasonId());
                if (reason != null && reason.getReasonStatus().equals(Constant.STATUS_USE.toString())) {
                    form.setReasonName(reason.getReasonName());
                }
                form.setReasonId(stockTrans.getReasonId().toString());
            }
            form.setStatus(stockTrans.getStockTransStatus());
        }

        form.setNote(bo.getNote());

        return true;
    }

    /*
     * Author: ThanhNC
     * Date created: 25/03/2009
     * Purpose: Tim kiem StockTransAction theo id phieu (lenh) xuat (nhap), loai giao dich và kho nhân hàng
     */
    public StockTransAction findByActionIdTypeAndShopImp(Long actionId, Long actionType, Long toOwnerType, Long toOwnerId) {
        log.debug("finding StockTransAction instance with ACTION_ID = " + actionId + " and ACTION_TYPE= " + actionType + "TO_OWNER_ID =" + toOwnerId);
        try {
            String queryString = "from StockTransAction as model where actionId= ? and model.actionType= ?" +
                    " and exists (select stockTransId from StockTrans where stockTransId=model.stockTransId and toOwnerType= ? and toOwnerId =? ) ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, actionId);
            queryObject.setParameter(1, actionType);
            queryObject.setParameter(2, toOwnerType);
            queryObject.setParameter(3, toOwnerId);
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (StockTransAction) lst.get(0);
            }
        } catch (RuntimeException re) {
            log.error("find by ACTION_CODE, ACTION_TYPE and OWNER_IMP failed", re);
            throw re;
        }

        return null;
    }
//    public StockTransAction findByActionCodeTypeAndShopImp(Object actionCode, Object actionType, Object toOwnerType, Object toOwnerId) {
//        log.debug("finding StockTransAction instance with ACTION_CODE = " + actionCode + " and ACTION_TYPE= " + actionType + "TO_OWNER_ID =" + toOwnerId);
//        try {
//            String queryString = "from StockTransAction as model where lower(model.actionCode)= ? and model.actionType= ?" +
//                    " and exists (select stockTransId from StockTrans where stockTransId=model.stockTransId and toOwnerType= ? and toOwnerId =? ) ";
//            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setParameter(0, actionCode.toString().toLowerCase());
//            queryObject.setParameter(1, actionType);
//            queryObject.setParameter(2, toOwnerType);
//            queryObject.setParameter(3, toOwnerId);
//            List lst = queryObject.list();
//            if (lst.size() > 0) {
//                return (StockTransAction) lst.get(0);
//            }
//        } catch (RuntimeException re) {
//            log.error("find by ACTION_CODE, ACTION_TYPE and OWNER_IMP failed", re);
//            throw re;
//        }
//
//        return null;
//    }
    /*
     * Author: ThanhNC
     * Date created: 25/03/2009
     * Purpose: Tim kiem StockTransAction theo ID phieu (lenh) xuat (nhap), loai giao dich và kho xuat hang
     */

    public StockTransAction findByActionIdTypeAndShopExp(Long actionId, Long actionType, Long fromOwnerType, Long fromOwnerId) {
        log.debug("finding StockTransAction instance with ACTION_ID = " + actionId + " and ACTION_TYPE= " + actionType + "FROM_OWNER_ID =" + fromOwnerId);
        try {
            String queryString = "from StockTransAction as model where actionId= ? and model.actionType= ?" +
                    " and exists (select stockTransId from StockTrans where stockTransId=model.stockTransId and fromOwnerType= ? and fromOwnerId =? ) ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, actionId);
            queryObject.setParameter(1, actionType);
            queryObject.setParameter(2, fromOwnerType);
            queryObject.setParameter(3, fromOwnerId);
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (StockTransAction) lst.get(0);
            }
        } catch (RuntimeException re) {
            log.error("find by ACTION_CODE, ACTION_TYPE, and OWNER_EXP  failed", re);
            throw re;
        }

        return null;
    }
//    public StockTransAction findByActionCodeTypeAndShopExp(Object actionCode, Object actionType, Object fromOwnerType, Object fromOwnerId) {
//        log.debug("finding StockTransAction instance with ACTION_CODE = " + actionCode + " and ACTION_TYPE= " + actionType + "FROM_OWNER_ID =" + fromOwnerId);
//        try {
//            String queryString = "from StockTransAction as model where lower(model.actionCode)= ? and model.actionType= ?" +
//                    " and exists (select stockTransId from StockTrans where stockTransId=model.stockTransId and fromOwnerType= ? and fromOwnerId =? ) ";
//            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setParameter(0, actionCode.toString().toLowerCase());
//            queryObject.setParameter(1, actionType);
//            queryObject.setParameter(2, fromOwnerType);
//            queryObject.setParameter(3, fromOwnerId);
//            List lst = queryObject.list();
//            if (lst.size() > 0) {
//                return (StockTransAction) lst.get(0);
//            }
//        } catch (RuntimeException re) {
//            log.error("find by ACTION_CODE, ACTION_TYPE, and OWNER_EXP  failed", re);
//            throw re;
//        }
//
//        return null;
//    }
    /*
     * Author: ThanhNC
     * Date created: 25/03/2009
     * Purpose: Tim kiem StockTransAction theo ma phieu (lenh) xuat (nhap) va loai giao dich
     */

    public StockTransAction findByActionCodeAndType(Object actionCode, Object actionType) {
        log.debug("finding StockTransAction instance with ACTION_CODE = " + actionCode + " and ACTION_TYPE= " + actionType);
        try {
            String queryString = "from StockTransAction as model where lower(model.actionCode)= ? and model.actionType= ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, actionCode.toString().toLowerCase());
            queryObject.setParameter(1, actionType);
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (StockTransAction) lst.get(0);
            }
        } catch (RuntimeException re) {
            log.error("find by ACTION_CODE and ACTION_TYPE failed", re);
            throw re;
        }

        return null;
    }

    public void save(StockTransAction transientInstance) {
        log.debug("saving StockTransAction instance");
        try {
            getSession().save(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }

    public void delete(StockTransAction persistentInstance) {
        log.debug("deleting StockTransAction instance");
        try {
            getSession().delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }

    public StockTransAction findById(java.lang.Long id) {
        log.debug("getting StockTransAction instance with id: " + id);
        try {
            StockTransAction instance = (StockTransAction) getSession().get("com.viettel.im.database.BO.StockTransAction",id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }

    public List findByExample(StockTransAction instance) {
        log.debug("finding StockTransAction instance by example");
        try {
            List results = getSession().createCriteria(
                    "com.viettel.im.database.BO.StockTransAction").add(
                    Example.create(instance)).list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    }

    public List findByProperty(String propertyName, Object value) {
        log.debug("finding StockTransAction instance with property: " + propertyName + ", value: " + value);
        try {
            String queryString = "from StockTransAction as model where model." + propertyName + "= ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }

    public List findByStockTransId(Object stockTransId) {
        return findByProperty(STOCK_TRANS_ID, stockTransId);
    }

//    public StockTransAction findByActionCode1(Object actionCode) {
//        log.debug("finding StockTransAction instance with ACTION_CODE , value: " + actionCode);
//        try {
//            String queryString = "from StockTransAction as model where lower(model.actionCode)= ?";
//            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setParameter(0, actionCode.toString().toLowerCase());
//            List lst = queryObject.list();
//            if (lst.size() > 0) {
//                return (StockTransAction) lst.get(0);
//            }
//        } catch (RuntimeException re) {
//            log.error("find by ACTION_CODE failed", re);
//            throw re;
//        }
//
//        return null;
//    }

    public List findByActionType(Object actionType) {
        return findByProperty(ACTION_TYPE, actionType);
    }

    public List findByNote(Object note) {
        return findByProperty(NOTE, note);
    }

    public List findByUsername(Object username) {
        return findByProperty(USERNAME, username);
    }

    public List findByActionStaffId(Object actionStaffId) {
        return findByProperty(ACTION_STAFF_ID, actionStaffId);
    }

    public List findByApproveStaffId(Object approveStaffId) {
        return findByProperty(APPROVE_STAFF_ID, approveStaffId);
    }

    public List findAll() {
        log.debug("finding all StockTransAction instances");
        try {
            String queryString = "from StockTransAction";
            Query queryObject = getSession().createQuery(queryString);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }
    }

    public StockTransAction merge(StockTransAction detachedInstance) {
        log.debug("merging StockTransAction instance");
        try {
            StockTransAction result = (StockTransAction) getSession().merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(StockTransAction instance) {
        log.debug("attaching dirty StockTransAction instance");
        try {
            getSession().saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }

    public void attachClean(StockTransAction instance) {
        log.debug("attaching clean StockTransAction instance");
        try {
            getSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
}