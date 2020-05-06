package com.viettel.im.database.DAO;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.StockTransBean;
import com.viettel.im.database.BO.StockTrans;
import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockTrans entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockTrans
 * @author MyEclipse Persistence Tools
 */

public class StockTransDAO extends BaseDAOAction {
//        /* LamNV ADD START */
//        private Session session;
//
//        @Override
//        public void setSession(Session session) {
//            this.session = session;
//        }
//
//        @Override
//        public Session getSession() {
//            if (session == null) {
//                return BaseHibernateDAO.getSession();
//            }
//            return this.session;
//        }
//        /* LamNV ADD STOP */    
//        
	private static final Log log = LogFactory.getLog(StockTransDAO.class);
	// property constants
	public static final String FROM_OWNER_ID = "fromOwnerId";
	public static final String FROM_OWNER_TYPE = "fromOwnerType";
	public static final String TO_OWNER_ID = "toOwnerId";
	public static final String TO_OWNER_TYPE = "toOwnerType";
	public static final String STOCK_TRANS_TYPE = "stockTransType";
	public static final String REASON_ID = "reasonId";
	public static final String STOCK_TRANS_STATUS = "stockTransStatus";
	public static final String PAY_STATUS = "payStatus";
	public static final String DEPOSIT_STATUS = "depositStatus";
	public static final String NOTE = "note";


     /*
     * author: ThanhNC
     * Purpose: Tim kiem theo property va trang thai
     */
    public List findByPropertyAndStatus(String propertyName, Object value, Long status) {
        log.debug("finding  StockTrans instance with property: " + propertyName + ", value: " + value + " and status: " + status);
        try {
            String queryString = "from  StockTrans as model where model." + propertyName + "= ? and model.status = ?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, value);
            queryObject.setParameter(1, status);
            return queryObject.list();
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
    /*
     * author: ThanhNC
     * Purpose: Tim kiem theo actionCode
     */

//    public StockTrans findByActionCode1(String actionCode) {
//        log.debug("finding StockTrans instance with actionCode:  " + actionCode );
//        try {
//            String queryString = "from StockTrans as model where model.stockTransId= " +
//                    " (select distinct(stockTransId) from StockTransAction where actionCode = ?) ";
//            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setParameter(0, actionCode);
//            List lst = queryObject.list();
//            if (lst.size() > 0) {
//                return (StockTrans) lst.get(0);
//            }
//            return null;
//        } catch (RuntimeException re) {
//            log.error("find by property name failed", re);
//            throw re;
//        }
//    }
    /*
     * author: ThanhNC
     * Purpose: Tim kiem theo actionCode
     */

    public StockTrans findByActionId(Long actionId) {
        log.debug("finding StockTrans instance with actionId:  " + actionId );
        try {
            String queryString = "from StockTrans as model where model.stockTransId= " +
                    " (select stockTransId from StockTransAction where actionId = ?) ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, actionId);
            List lst = queryObject.list();
            if (lst.size() > 0) {
                return (StockTrans) lst.get(0);
            }
            return null;
        } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
        }
    }
    /*
     * author: ThanhNC
     * Purpose: Tim kiem theo id va trang thai
     */

//    public StockTrans findByActionCodeAndStatus(String actionCode, Long status) {
//        log.debug("finding StockTrans instance with actionCode:  " + actionCode + " and status: " + status);
//        try {
//            String queryString = "from StockTrans as model where model.stockTransId= " +
//                    " (select distinct(stockTransId) from StockTransAction where actionCode = ?) and model.stockTransStatus = ?";
//            Query queryObject = getSession().createQuery(queryString);
//            queryObject.setParameter(0, actionCode);
//            queryObject.setParameter(1, status);
//            List lst = queryObject.list();
//            if (lst.size() > 0) {
//                return (StockTrans) lst.get(0);
//            }
//            return null;
//        } catch (RuntimeException re) {
//            log.error("find by property name failed", re);
//            throw re;
//        }
//    }


	public StockTrans findById(java.lang.Long id) {
		log.debug("getting StockTrans instance with id: " + id);
		try {
			StockTrans instance = (StockTrans) getSession().get(
					"com.viettel.im.database.BO.StockTrans", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

        /**
         * Tim StockTrans theo id
         * Session lay tu DAO khac
         * @param session
         * @param id
         * @return
         */
        public static StockTrans findById(Session session, Long id) {
    	    log.debug("getting StockTrans instance with id: " + id);
            try {
                    StockTrans instance = (StockTrans) session.get(
                                    "com.viettel.im.database.BO.StockTrans", id);
                    return instance;
            } catch (RuntimeException re) {
                    log.error("get failed", re);
                    throw re;
            }        
        }
	public List findByExample(StockTrans instance) {
		log.debug("finding StockTrans instance by example");
		try {
			List results = getSession().createCriteria(
					"com.viettel.im.database.BO.StockTrans").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding StockTrans instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from StockTrans as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByFromOwnerId(Object fromOwnerId) {
		return findByProperty(FROM_OWNER_ID, fromOwnerId);
	}

	public List findByFromOwnerType(Object fromOwnerType) {
		return findByProperty(FROM_OWNER_TYPE, fromOwnerType);
	}

	public List findByToOwnerId(Object toOwnerId) {
		return findByProperty(TO_OWNER_ID, toOwnerId);
	}

	public List findByToOwnerType(Object toOwnerType) {
		return findByProperty(TO_OWNER_TYPE, toOwnerType);
	}

	public List findByStockTransType(Object stockTransType) {
		return findByProperty(STOCK_TRANS_TYPE, stockTransType);
	}

	public List findByReasonId(Object reasonId) {
		return findByProperty(REASON_ID, reasonId);
	}

	public List findByStockTransStatus(Object stockTransStatus) {
		return findByProperty(STOCK_TRANS_STATUS, stockTransStatus);
	}

	public List findByPayStatus(Object payStatus) {
		return findByProperty(PAY_STATUS, payStatus);
	}

	public List findByDepositStatus(Object depositStatus) {
		return findByProperty(DEPOSIT_STATUS, depositStatus);
	}

	public List findByNote(Object note) {
		return findByProperty(NOTE, note);
	}

	public List findAll() {
		log.debug("finding all StockTrans instances");
		try {
			String queryString = "from StockTrans";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}  


    // from_owner_type = 2 , action_type = 1
    public List<StockTransBean> findStockTrainsJoinStockTransDetailByFomOwnerType(Long payStatus,Long ownerType,
            String shopId, Long stockTransType,Long actionType,Long depositStatus,Long toOwnerType,String toShopCode,
            String startDate,String endDate,Long parentShopId,boolean drawStatus,Long stockTransStatus) throws Exception{
        log.info("Start findStockTrainsJoinStockTransDetailByFomOwnerType");
        try {

            String whereDate = null,whereShop = null,whereShopCode = null,whereParentShop = null,whereDrawStatus = null,whereStockTransStatus = null;

            if((startDate != null && startDate.trim().length() > 0) && (endDate != null && endDate.trim().length() > 0)){
                   // whereDate = " and std.create_datetime > = to_date('2009/3/1', 'yyyy/mm/dd') and std.create_datetime <= to_date('2009/4/4', 'yyyy/mm/dd') ";
                    whereDate = " and st.create_datetime > = to_date(:startDate, 'yyyy/mm/dd') and st.create_datetime < to_date(:endDate, 'yyyy/mm/dd')+1 ";
            }else{
                if(startDate != null && startDate.trim().length() > 0){
                    whereDate = " and st.create_datetime >= to_date(:startDate,'yyyy/mm/dd')";
                }
                if(endDate != null && endDate.trim().length() > 0){
                    whereDate = " and st.create_datetime < to_date(:endDate,'yyyy/mm/dd') +1 ";
                }
            }

          

            if(shopId != null && shopId.length() > 0){
                whereShop = " and st.TO_OWNER_ID = :shopId ";
            }


            if(toShopCode != null && toShopCode.length() > 0){
              whereShopCode = " and lower(sh.shop_code) = :shopCode " ;
            }

            if(parentShopId != null ){
                whereParentShop = " and sh.parent_shop_id = :parentShopId " ;
            }

            if(drawStatus){
                whereDrawStatus = " and (st.draw_status is null or st.draw_status = 1) ";
            }

            if(stockTransStatus != null){
                whereStockTransStatus = "  and st.STOCK_TRANS_STATUS = :stockTransStatus "; //1                

            }



            String sql = " select st.stock_trans_id as stockTransId," +
                         "        st.DEPOSIT_STATUS as depositStatus, " +
                         "        (select sh.name from Shop sh where sh.shop_id=st.FROM_OWNER_ID) as nameShopExport," +
                         "        (select sh1.name from Shop sh1 where sh1.shop_id=st.TO_OWNER_ID) as nameShopImport," +
                         "        (select sh1.address from Shop sh1 where sh1.shop_id=st.TO_OWNER_ID) as addressShopImport, " +
                         "         st.TO_OWNER_ID as shopIdImport, st.pay_status as payStatus," +
                         "        ( select sm.shop_code from shop sm where sm.shop_id = st.TO_OWNER_ID ) as  shopCodeIdImport, " +
                         "         temp.userAction as userAction ,temp.create_datetime as createDatetime, temp.action_code as actionCode " +
                         " from stock_trans st " +
                         " inner join ( " +
                         "              select std.stock_trans_id , std.action_code," +
                         "                     std.create_datetime," +
                         "                     (select St1.name from Staff st1 where st1.staff_id = std.action_staff_id) as userAction" +
                         "              from Stock_trans_action std " +
                         //"              where std.action_type = :action_type " +
                         //               (whereDate == null ? "" : whereDate)            + "" +
                         "            ) " +
                         " temp " +
                         " on st.stock_trans_id = temp.stock_trans_id " +
                         //" where 1=1 " + (payStatus == null ? "" : " and st.pay_status = :payStatus" )
                         " where 1=1 " + (payStatus == null ? "" : " and st.pay_status = :payStatus" )
                         + " and st.STOCK_TRANS_TYPE = :STOCK_TRANS_TYPE " +
                           (depositStatus == null || depositStatus == 10 ? "" : " and st.DEPOSIT_STATUS = :DEPOSIT_STATUS ")
                           + (depositStatus != null && depositStatus == 10  ? " and st.DEPOSIT_STATUS IN (0, 1) " : "")
//                         + (ownerType == null ? "" : " and st.TO_OWNER_TYPE = :ownerType ")
                         + (ownerType == null ? "" : " and st.FROM_OWNER_TYPE = :ownerType ")
                         + (toOwnerType == null ? "" : " and st.TO_OWNER_TYPE = :TO_OWNER_TYPE ")
                         + (whereShop == null ? "" : whereShop)
                         + (whereDrawStatus == null ? "" : whereDrawStatus)
                         + (whereStockTransStatus == null ? "" : whereStockTransStatus)+
                         " and st.TO_OWNER_ID in (select sh.shop_id  from Shop sh inner join " +
                         "                       (select channel_type_id from Channel_Type ct where ct.object_type = 1 " +
                         " and ct.IS_VT_UNIT = 2 ) temp on sh.channel_type_id = temp.channel_type_id )"
                         + (whereDate == null ? "" : whereDate)  ;


            sql = "select temp2.stockTransId as stockTransId , " +
                               "     nameShopExport as nameShopExport, nameShopImport as nameShopImport," +
                               "     addressShopImport as addressShopImport, shopIdImport as shopIdImport , payStatus as payStatus," +
                               "     shopCodeIdImport as shopCodeIdImport,userAction as userAction , createDatetime as createDatetime," +
                               "     actionCode as actionCode,sh.shop_code as shopCode, depositStatus AS depositStatus from (" + sql.trim() + ") temp2 " +
                               " inner join Shop sh on temp2.shopIdImport = sh.shop_id where 1=1 " + (whereParentShop == null ? "" : whereParentShop)
                                    +  (whereShopCode == null ? "" : whereShopCode);


            sql += " order by createDatetime desc ";

            log.info("SQL findStockTrainsJoinStockTransDetailByFomOwnerType : " + sql);
            System.out.println("SQL findStockTrainsJoinStockTransDetailByFomOwnerType : " + sql);

            Query query = getSession().createSQLQuery(sql).
                            addScalar("stockTransId", Hibernate.STRING).
                            addScalar("nameShopExport", Hibernate.STRING).
                            addScalar("nameShopImport", Hibernate.STRING).
                            addScalar("addressShopImport", Hibernate.STRING).
                            addScalar("shopIdImport", Hibernate.STRING).
                            addScalar("payStatus",Hibernate.STRING).
                            addScalar("shopCodeIdImport", Hibernate.STRING).
                            addScalar("userAction",Hibernate.STRING).
                            addScalar("createDatetime", Hibernate.DATE).
                            addScalar("actionCode",Hibernate.STRING).
                            addScalar("shopCode",Hibernate.STRING).
                            addScalar("depositStatus", Hibernate.STRING);


            if((startDate != null && startDate.trim().length() > 0) && (endDate != null && endDate.trim().length() > 0)){
                Date sDate = DateTimeUtils.convertStringToDate(startDate);
                String strStartDate = DateTimeUtils.convertDateToString(sDate);
                Date eDate = DateTimeUtils.convertStringToDate(endDate);
                String strEndDate = DateTimeUtils.convertDateToString(eDate);
                query.setString("startDate", strStartDate);
                query.setString("endDate", strEndDate);
            }else{
                if(startDate != null && startDate.trim().length() > 0){
                   Date sDate = DateTimeUtils.convertStringToDate(startDate);
                   String strStartDate = DateTimeUtils.convertDateToString(sDate);
                   query.setString("startDate", strStartDate);
                }
                if(endDate != null && endDate.trim().length() > 0){
                   Date eDate = DateTimeUtils.convertStringToDate(endDate);
                   String strStartDate = DateTimeUtils.convertDateToString(eDate);
                   query.setString("endDate", strStartDate);
                }
            }

            //query.setLong("action_type", actionType);

            if(payStatus != null){                
                query.setLong("payStatus", payStatus);
            }

            query.setLong("STOCK_TRANS_TYPE", stockTransType);

            if(ownerType != null){
                query.setLong("ownerType", ownerType);
            }

            if(toOwnerType != null){
                query.setLong("TO_OWNER_TYPE", toOwnerType);
            }

            if(depositStatus != null && !depositStatus.equals(10L)){
                query.setLong("DEPOSIT_STATUS", depositStatus);
            }



            if(whereShop != null && whereShop.length() > 0){
                query.setLong("shopId", Long.parseLong(shopId));
            }


            if(toShopCode != null && toShopCode.length() > 0){
                query.setString("shopCode", toShopCode.trim().toLowerCase());
            }

            if(parentShopId != null ){
                query.setLong("parentShopId", parentShopId);
            }

            if(stockTransStatus != null){
                query.setLong("stockTransStatus", stockTransStatus);
            }

            query.setResultTransformer(Transformers.aliasToBean(StockTransBean.class));

            return query.list();
        } catch (HibernateException e) {
            log.error("Error at HibernateException : " + e.getMessage());
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public void update(StockTrans stockTrans) throws Exception{
        try {
            getSession().update(stockTrans);
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }


    //MrSun
    // from_owner_type = 2 , action_type = 1
    public List<StockTransBean> findStockTrainsJoinStockTransDetailByFomOwnerType(Long payStatus,Long ownerType,
            String shopId, Long stockTransType,Long actionType,Long depositStatus,Long toOwnerType,String toShopCode,
            String startDate,String endDate,Long parentShopId,boolean drawStatus,Long[] stockTransStatus, String stockTransCode) throws Exception{
        log.info("Start findStockTrainsJoinStockTransDetailByFomOwnerType");
        try {

            String whereDate = null,whereShop = null,whereShopCode = null,whereParentShop = null,whereDrawStatus = null,whereStockTransStatus = null;
            String whereStockTransCode = "";

            if((startDate != null && startDate.trim().length() > 0) && (endDate != null && endDate.trim().length() > 0)){
                   // whereDate = " and std.create_datetime > = to_date('2009/3/1', 'yyyy/mm/dd') and std.create_datetime <= to_date('2009/4/4', 'yyyy/mm/dd') ";
                    whereDate = " and st.create_datetime > = to_date(:startDate, 'yyyy/mm/dd') and st.create_datetime < to_date(:endDate, 'yyyy/mm/dd')+1 ";
            }else{
                if(startDate != null && startDate.trim().length() > 0){
                    whereDate = " and st.create_datetime >= to_date(:startDate,'yyyy/mm/dd')";
                }
                if(endDate != null && endDate.trim().length() > 0){
                    whereDate = " and st.create_datetime < to_date(:endDate,'yyyy/mm/dd')+1";
                }
            }



            if(shopId != null && shopId.length() > 0){
                whereShop = " and st.TO_OWNER_ID = :shopId ";
            }


            if(toShopCode != null && toShopCode.length() > 0){
              whereShopCode = " and lower(sh.shop_code) = :shopCode " ;
            }

            if(parentShopId != null ){
                whereParentShop = " and sh.parent_shop_id = :parentShopId " ;
            }

            if(drawStatus){
                whereDrawStatus = " and (st.draw_status is null or st.draw_status = 1) ";
            }

            if(stockTransStatus != null){
                if (stockTransStatus.length>0){
                    String tmpStatus = "";
                    for (int i = 0; i < stockTransStatus.length; i++){
                        if (stockTransStatus[i] == null) continue;
                        if (!"".equals(tmpStatus)) tmpStatus += ",";
                        tmpStatus += stockTransStatus[i];
                    }
                    if (!"".equals(tmpStatus)){
                        whereStockTransStatus = "  and st.STOCK_TRANS_STATUS IN (" + tmpStatus + ") ";
                    }
                }
            }

            if(stockTransCode != null)
                if(!"".equals(stockTransCode.trim()))
                {
                    whereStockTransCode = " and st.stock_trans_id IN ";
                    whereStockTransCode += " ( ";
                    whereStockTransCode += "        SELECT stac.stock_trans_id  FROM stock_trans_action stac WHERE lower(stac.action_code) = '"+stockTransCode.trim().toLowerCase()+"' and stac.action_type = 1 ";
                    whereStockTransCode += " ) ";
                }


            //Loai phieu = LENH
            //Action_Type = 1
//            String whereActionType = "";
//            whereActionType = " and st.stock_trans_id IN ";
//            whereActionType += " ( ";
//            whereActionType += "        SELECT stac.stock_trans_id  FROM stock_trans_action stac WHERE stac.ACTION_TYPE = 1 ";
//            whereActionType += " ) ";

            
            String sql = " select st.stock_trans_id as stockTransId," +
                         "        st.DEPOSIT_STATUS as depositStatus, " +
                         "        (select sh.name from Shop sh where sh.shop_id=st.FROM_OWNER_ID) as nameShopExport," +
                         "        (select sh1.name from Shop sh1 where sh1.shop_id=st.TO_OWNER_ID) as nameShopImport," +
                         "        (select sh1.address from Shop sh1 where sh1.shop_id=st.TO_OWNER_ID) as addressShopImport, " +
                         "         st.TO_OWNER_ID as shopIdImport, st.pay_status as payStatus," +
                         "        ( select sm.shop_code from shop sm where sm.shop_id = st.TO_OWNER_ID ) as  shopCodeIdImport, " +
                         "         temp.userAction as userAction ,temp.create_datetime as createDatetime, temp.action_code as actionCode " +
                         //MrSun
                         "         ,temp.action_type as actionType " +
                         " from stock_trans st " +
                         " inner join ( " +
                         "              select std.stock_trans_id , std.action_code," +
                         "                     std.create_datetime," +
                         "                     (select St1.name from Staff st1 where st1.staff_id = std.action_staff_id) as userAction" +
                         //MrSun
                         "                      , std.action_type " +
                         "              from Stock_trans_action std " +
                         //"              where std.action_type = :action_type " +
                         //               (whereDate == null ? "" : whereDate)            + "" +
                         "            ) " +
                         " temp " +
                         " on st.stock_trans_id = temp.stock_trans_id " +
                         //" where 1=1 " + (payStatus == null ? "" : " and st.pay_status = :payStatus" )
                         " where 1=1 " + (payStatus == null ? "" : " and st.pay_status = :payStatus" )
                         + " and st.STOCK_TRANS_TYPE = :STOCK_TRANS_TYPE " +
                           (depositStatus == null || depositStatus == 10 ? "" : " and st.DEPOSIT_STATUS = :DEPOSIT_STATUS ")
                           + (depositStatus != null && depositStatus == 10  ? " and st.DEPOSIT_STATUS IN (0, 1) " : "")
//                         + (ownerType == null ? "" : " and st.TO_OWNER_TYPE = :ownerType ")
                         + (ownerType == null ? "" : " and st.FROM_OWNER_TYPE = :ownerType ")
                         + (toOwnerType == null ? "" : " and st.TO_OWNER_TYPE = :TO_OWNER_TYPE ")
                         + (whereShop == null ? "" : whereShop)
                         + (whereDrawStatus == null ? "" : whereDrawStatus)
                         //MrSun
                         //+ (whereActionType == null ? "" : whereActionType)
                         + (whereStockTransStatus == null ? "" : whereStockTransStatus)
                         + (whereStockTransCode == null ? "" : whereStockTransCode)+

                         " and st.TO_OWNER_ID in (select sh.shop_id  from Shop sh inner join " +
                         "                       (select channel_type_id from Channel_Type ct where ct.object_type = 1 " +
                         " and ct.IS_VT_UNIT = 2 ) temp on sh.channel_type_id = temp.channel_type_id )"
                         + (whereDate == null ? "" : whereDate)  ;


            sql = "select temp2.stockTransId as stockTransId , " +
                               "     nameShopExport as nameShopExport, nameShopImport as nameShopImport," +
                               "     addressShopImport as addressShopImport, shopIdImport as shopIdImport , payStatus as payStatus," +
                               "     shopCodeIdImport as shopCodeIdImport,userAction as userAction , createDatetime as createDatetime," +
                               "     actionCode as actionCode,sh.shop_code as shopCode, depositStatus AS depositStatus from (" + sql.trim() + ") temp2 " +
                               " inner join Shop sh on temp2.shopIdImport = sh.shop_id where 1=1 and actionType = 1 " + (whereParentShop == null ? "" : whereParentShop)
                                    +  (whereShopCode == null ? "" : whereShopCode);
            



            log.info("SQL findStockTrainsJoinStockTransDetailByFomOwnerType : " + sql);
            System.out.println("SQL findStockTrainsJoinStockTransDetailByFomOwnerType : " + sql);

            sql += " order by createDatetime desc ";
            System.out.println("SQL findStockTrainsJoinStockTransDetailByFomOwnerType : " + sql);

            Query query = getSession().createSQLQuery(sql).
                            addScalar("stockTransId", Hibernate.STRING).
                            addScalar("nameShopExport", Hibernate.STRING).
                            addScalar("nameShopImport", Hibernate.STRING).
                            addScalar("addressShopImport", Hibernate.STRING).
                            addScalar("shopIdImport", Hibernate.STRING).
                            addScalar("payStatus",Hibernate.STRING).
                            addScalar("shopCodeIdImport", Hibernate.STRING).
                            addScalar("userAction",Hibernate.STRING).
                            addScalar("createDatetime", Hibernate.DATE).
                            addScalar("actionCode",Hibernate.STRING).
                            addScalar("shopCode",Hibernate.STRING).
                            addScalar("depositStatus", Hibernate.STRING);


            if((startDate != null && startDate.trim().length() > 0) && (endDate != null && endDate.trim().length() > 0)){
                Date sDate = DateTimeUtils.convertStringToDate(startDate);
                String strStartDate = DateTimeUtils.convertDateToString(sDate);
                Date eDate = DateTimeUtils.convertStringToDate(endDate);
                String strEndDate = DateTimeUtils.convertDateToString(eDate);
                query.setString("startDate", strStartDate);
                query.setString("endDate", strEndDate);
            }else{
                if(startDate != null && startDate.trim().length() > 0){
                   Date sDate = DateTimeUtils.convertStringToDate(startDate);
                   String strStartDate = DateTimeUtils.convertDateToString(sDate);
                   query.setString("startDate", strStartDate);
                }
                if(endDate != null && endDate.trim().length() > 0){
                   Date eDate = DateTimeUtils.convertStringToDate(endDate);
                   String strStartDate = DateTimeUtils.convertDateToString(eDate);
                   query.setString("endDate", strStartDate);
                }
            }

            //query.setLong("action_type", actionType);

            if(payStatus != null){
                query.setLong("payStatus", payStatus);
            }

            query.setLong("STOCK_TRANS_TYPE", stockTransType);

            if(ownerType != null){
                query.setLong("ownerType", ownerType);
            }

            if(toOwnerType != null){
                query.setLong("TO_OWNER_TYPE", toOwnerType);
            }

            if(depositStatus != null && !depositStatus.equals(10L)){
                query.setLong("DEPOSIT_STATUS", depositStatus);
            }



            if(whereShop != null && whereShop.length() > 0){
                query.setLong("shopId", Long.parseLong(shopId));
            }


            if(toShopCode != null && toShopCode.length() > 0){
                query.setString("shopCode", toShopCode.trim().toLowerCase());
            }

            if(parentShopId != null ){
                query.setLong("parentShopId", parentShopId);
            }

            query.setResultTransformer(Transformers.aliasToBean(StockTransBean.class));

            return query.list();
        } catch (HibernateException e) {
            log.error("Error at HibernateException : " + e.getMessage());
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    
     


}