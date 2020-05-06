package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import static com.viettel.database.DAO.BaseDAOAction.logEndCall;
import com.viettel.im.client.bean.ActionLogsObj;
import com.viettel.im.client.bean.AutoCompleteSearchBean;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.bean.SaleTransDetailBean;
import com.viettel.im.client.bean.SearchStockTransBean;
import com.viettel.im.client.bean.SaleTransDetailV2Bean;
import com.viettel.im.client.form.ExportStockForm;
import com.viettel.im.client.form.GoodsForm;
import com.viettel.im.client.form.ImportStockForm;
import com.viettel.im.client.form.SerialGoods;
import com.viettel.im.common.ConfigParam;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.NumberUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.common.util.StringUtils;
import com.viettel.im.database.BO.AppParams;
import com.viettel.im.database.BO.ChannelType;
import com.viettel.im.database.BO.GenResult;
import com.viettel.im.database.BO.Partner;
import com.viettel.im.database.BO.Price;
import com.viettel.im.database.BO.Reason;
import com.viettel.im.database.BO.SaleTrans;
import com.viettel.im.database.BO.SaleTransDetail;
import com.viettel.im.database.BO.SaleTransSerial;
import com.viettel.im.database.BO.SearchStockTrans;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.ShowMessage;
import com.viettel.im.database.BO.Staff;

import com.viettel.im.database.BO.StockDeposit;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTotalFull;
import com.viettel.im.database.BO.StockTotalId;
import com.viettel.im.database.BO.StockTrans;
import com.viettel.im.database.BO.StockTransAction;
import com.viettel.im.database.BO.StockTransDetail;
import com.viettel.im.database.BO.StockTransFull;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.VShopStaff;
import java.io.File;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import com.viettel.im.database.BO.DebitStock;
import com.viettel.im.database.BO.SaleTransSerialOrder;
import com.viettel.security.util.DbProcessor;
import com.viettel.security.util.Provisioning;
import com.viettel.vas.util.ExchangeClientChannel;
import java.util.ResourceBundle;
import org.apache.log4j.PropertyConfigurator;

/**
 *
 * @author ThanhNC
 */
public class StockCommonDAO extends BaseDAOAction {

    public static String SELECT_RES = "MSG.STK.017";
    private List listItemsSelectBox = new ArrayList();
    private ExportStockForm exportStockForm = new ExportStockForm();
    private GoodsForm goodsForm = new GoodsForm();
    private SerialGoods serialGoods = new SerialGoods();
    private Map<Long, String> autoSelectOnwer = new HashMap<Long, String>();
    private Map selectOnwer = new HashMap();
    private Map ownerName = new HashMap();
//    private static final Logger log = Logger.getLogger(StockCommonDAO.class);
    Logger log = Logger.getLogger(StockCommonDAO.class);
    private static final String PRICE_TYPE_CTV = "7";
    public static final String CHANEL_TYPE_OBJECT_TYPE = "1";
    public static final String CHANEL_TYPE_IS_VT_UNIT = "2";
    private final String REQUEST_PROCESS_MODE = "processMode";
    private final String REQUEST_MESSAGE = "returnMsg";
    private final String REQUEST_LST_SERIAL = "lstSerial";
    private final String REQUEST_LIST_CHANNEL_TYPE = "listChannelType";
    private final String VIEW_SERIAL_GROUP_BY_CHANNEL_TYPE = "viewSerialGroupByChannelType";
    //LeVT1 srart R499
    public static final Long EXP_REASON_ID = 4456L;
    //LeVT1 end R499
//    TrongLV 11-11-07
    //fix khong quan ly hang hoa theo kenh
//    private boolean IS_STOCK_CHANNEL = false;
    private boolean IS_STOCK_STAFF_OWNER = true;

    public void StockCommonDAO() {
        //Them ghi Log4j
        String log4JPath = ResourceBundle.getBundle("config").getString("logfilepath");
        PropertyConfigurator.configure(log4JPath);
    }

    public Map<Long, String> getAutoSelectOnwer() {
        return autoSelectOnwer;
    }

    public Map getSelectOnwer() {
        return selectOnwer;
    }

    public void setSelectOnwer(Map selectOnwer) {
        this.selectOnwer = selectOnwer;
    }

    public Map getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(Map ownerName) {
        this.ownerName = ownerName;
    }

    public void setAutoSelectOnwer(Map<Long, String> autoSelectOnwer) {
        this.autoSelectOnwer = autoSelectOnwer;
    }

    public List getListItemsSelectBox() {
        return listItemsSelectBox;
    }

    public void setListItemsSelectBox(List listItemsSelectBox) {
        this.listItemsSelectBox = listItemsSelectBox;
    }

    /*
     * Author: ThanhNC Date created: 10/03/2009 Purpose: Autocomplete chon danh
     * sach owner (shop, staff) khi xem kho
     */
    public String autoSelectOwner() throws Exception {
        HttpServletRequest req = getRequest();
        String pageFoward = "autoSelectOwner";
        try {


            String ownerType = req.getParameter("ownerType");
            String ownerCode = req.getParameter("goodsForm.ownerCode");
            if (ownerType == null || ownerCode == null || "".equals(ownerType.trim()) || "".equals(ownerCode.trim())) {
                return pageFoward;
            }
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (ownerType != null) {
                Long ownerTypeId = Long.parseLong(ownerType);
                if (ownerTypeId.equals(Constant.OWNER_TYPE_SHOP)) {
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    if (userToken != null && userToken.getShopId() != null) {
                        String SQL_SELECT_SHOP = " from Shop where status = ? and shopPath like  ?   and  lower(shopCode) like ? ";
                        Query q = getSession().createQuery(SQL_SELECT_SHOP);
                        q.setParameter(0, Constant.STATUS_USE);
                        q.setParameter(1, "%_" + userToken.getShopId().toString() + "%");
                        //q.setParameter(2, userToken.getShopId());
                        q.setParameter(2, "%" + ownerCode.toLowerCase() + "%");

                        List<Shop> lstShop = q.list();
                        int min = lstShop.size() <= 8 ? lstShop.size() : 8;
                        for (int idx = 0; idx < min; idx++) {
                            Shop tmp = lstShop.get(idx);
                            autoSelectOnwer.put(tmp.getShopId(), tmp.getShopCode());
                        }
                    }
                }
                if (ownerTypeId.equals(Constant.OWNER_TYPE_STAFF)) {
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(this.getSession());
                    if (userToken != null && userToken.getShopId() != null) {
                        List<Staff> lstStaff = staffDAO.findAllStaffAndCollborOfShop(userToken.getShopId());
                        int min = lstStaff.size() <= 8 ? lstStaff.size() : 8;
                        for (int idx = 0; idx < min; idx++) {
                            Staff tmp = lstStaff.get(idx);
                            autoSelectOnwer.put(tmp.getStaffId(), tmp.getStaffCode());
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return pageFoward;
    }

    public String getShopNameTextDIS() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String ownerType = httpServletRequest.getParameter("ownerType");
            String ownerId = httpServletRequest.getParameter("ownerId");
            String target = httpServletRequest.getParameter("target");
            if (ownerType != null && ownerType.equals(Constant.OWNER_TYPE_SHOP.toString())) {
                if (ownerId != null && ownerId.trim().length() > 0) {
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    Shop shop = shopDAO.findById(Long.parseLong(ownerId));
                    if (shop != null) {
                        ownerName.put(target, shop.getName());
                    }
                    return "selectOwnerName";
                }
            }
            if (ownerType != null && ownerType.equals(Constant.OWNER_TYPE_STAFF.toString())) {
                if (ownerId != null && ownerId.trim().length() > 0) {
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(this.getSession());
                    Staff staff = staffDAO.findById(Long.parseLong(ownerId));
                    if (staff != null) {
                        ownerName.put(target, staff.getName());
                    }
                    return "selectOwnerName";
                }
            }

        } catch (Exception ex) {
            throw ex;
        }



        return "selectOwnerName";
    }

    /*
     * Author: ThanhNC Date created: 10/03/2009 Purpose: Chon loai mat hang -->
     * mat hang (con trong kho)
     */
    public String getStockModel() throws Exception {
        try {

            HttpServletRequest httpServletRequest = getRequest();

            //Chon hang hoa tu loai hang hoa
            String stockTypeId = httpServletRequest.getParameter("stockTypeId");
            String[] header = {"", getText(SELECT_RES)};
            listItemsSelectBox.add(header);
            if (stockTypeId != null && !"".equals(stockTypeId.trim())) {
                Long id = Long.parseLong(stockTypeId.trim());
                Query q = null;

                String SQL_SELECT_STOCK_MODEL = "select stockModelId, name from StockModel where stockTypeId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";

                q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                q.setParameter(0, id);
                q.setParameter(1, Constant.STATUS_USE);

                Long ownerType = goodsForm.getOwnerType();
                Long ownerId = goodsForm.getOwnerId();
                if (ownerType != null && ownerId != null) {
                    SQL_SELECT_STOCK_MODEL = "select a.stockModelId, a.name from StockModel a where a.stockTypeId= ? and a.status = ? and exists ( select b.id.stockModelId from StockTotal b where b.id.stockModelId =a.stockModelId and b.status = ? and b.id.ownerId = ? and b.id.ownerType = ? and  (quantityIssue >0 or quantityDial>0) ) " + " order by a.name";
                    q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                    q.setParameter(0, id);
                    q.setParameter(1, Constant.STATUS_USE);

                    q.setParameter(2, Constant.STATUS_USE);
                    q.setParameter(3, ownerId);
                    q.setParameter(4, ownerType);
                }
                List lstStockModel = q.list();


                listItemsSelectBox.addAll(lstStockModel);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return "success";
    }

    public List<StockModel> getListStockModelToRecover(Long stockTypeId, Long ownerId) throws Exception {
        Long ownerType = 1L;

        Query q = null;

        String SQL_SELECT_STOCK_MODEL = "select stockModelId, name from StockModel where stockTypeId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";

        q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
        q.setParameter(0, stockTypeId);
        q.setParameter(1, Constant.STATUS_USE);

        if (ownerType != null && ownerId != null) {
            SQL_SELECT_STOCK_MODEL = "select a.stockModelId, a.name from StockModel a where a.stockTypeId= ? and a.status = ?  " + " and exists " + " ( select b.id.stockModelId from StockTotal b where b.id.stockModelId =a.stockModelId and b.status = ? and b.id.ownerId = ? and b.id.ownerType = ? and  (quantityIssue >0 or quantityDial>0) ) " + " order by a.name";
            q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
            q.setParameter(0, stockTypeId);
            q.setParameter(1, Constant.STATUS_USE);

            q.setParameter(2, Constant.STATUS_USE);
            q.setParameter(3, ownerId);
            q.setParameter(4, ownerType);

        }

        List lstStockModel = q.list();
        List<StockModel> listStockModel = new ArrayList();
        Iterator iter = lstStockModel.iterator();
        while (iter.hasNext()) {
            Object[] temp = (Object[]) iter.next();
            StockModel stockModel = new StockModel();
            stockModel.setStockModelId(new Long(temp[0].toString()));
            stockModel.setName(temp[1].toString());
            listStockModel.add(stockModel);
        }
        return listStockModel;


    }

    public String getShopIdbyShopCode(String ownerCode) throws Exception {
        HttpServletRequest httpServletRequest = getRequest();
        UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
        List listParameter2 = new ArrayList();
        StringBuffer strQuery2 = new StringBuffer("select new com.viettel.im.database.BO.Shop(a.shopId, a.shopCode) ");
        strQuery2.append("from Shop a, ");
        strQuery2.append(" ChannelType chty ");
        strQuery2.append("where 1 = 1 ");
        strQuery2.append(" AND chty.channelTypeId = a.channelTypeId ");
        strQuery2.append(" AND ");
        strQuery2.append(" chty.objectType = ? ");
        listParameter2.add(CHANEL_TYPE_OBJECT_TYPE);
        strQuery2.append(" AND ");
        strQuery2.append(" chty.isVtUnit = ? ");
        listParameter2.add(CHANEL_TYPE_IS_VT_UNIT);
        if (ownerCode != null && !ownerCode.trim().equals("")) {
            strQuery2.append("and lower(a.shopCode) = ? ");
            listParameter2.add(ownerCode.trim().toLowerCase());
        }
        strQuery2.append(" and a.parentShopId = ? ");
        listParameter2.add(userToken.getShopId());
        strQuery2.append("and rownum < ? ");
        listParameter2.add(300L);

        strQuery2.append("order by nlssort(a.shopCode, 'nls_sort=Vietnamese') asc ");

        Query query2 = getSession().createQuery(strQuery2.toString());
        for (int i = 0; i < listParameter2.size(); i++) {
            query2.setParameter(i, listParameter2.get(i));
        }

        List<Shop> tmpList2 = query2.list();
        if (tmpList2 != null && tmpList2.size() > 0 && ownerCode != null && !ownerCode.trim().equals("")) {
            return tmpList2.get(0).getShopId().toString();
        } else {
            return "0";
        }

    }
    /*
     * Author: Vunt Date created: 25/09/2009 Purpose: Chon loai mat hang --> mat
     * hang (con trong kho)
     */

    public String getStockModelToRecover() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
            //Chon hang hoa tu loai hang hoa
            String ownerId;
            String stockTypeId = httpServletRequest.getParameter("stockTypeId");
            String ownerCode = httpServletRequest.getParameter("ownerCode").toString();
            ownerId = getShopIdbyShopCode(ownerCode);
            Long ownerType = 1L;
            String[] header = {"", getText(SELECT_RES)};
            listItemsSelectBox.add(header);
            if (stockTypeId != null && !"".equals(stockTypeId.trim())) {
                Long id = Long.parseLong(stockTypeId.trim());
                Query q = null;

                String SQL_SELECT_STOCK_MODEL = "select stockModelId, name from StockModel where stockTypeId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";

                q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                q.setParameter(0, id);
                q.setParameter(1, Constant.STATUS_USE);

                if (ownerType != null && ownerId != null) {
                    SQL_SELECT_STOCK_MODEL = "select a.stockModelId, a.name from StockModel a where a.stockTypeId= ? and a.status = ?  " + " and exists " + " ( select b.id.stockModelId from StockTotal b,StockDeposit sd, Shop sh where b.id.stockModelId =a.stockModelId and b.status = ?  and b.id.ownerId = sh.shopId    and sh.channelTypeId = sd.chanelTypeId    and b.id.stockModelId = sd.stockModelId    and sd.status = 1" + " and b.id.ownerId = ? and b.id.ownerType = ? and  (b.quantityIssue >0 or b.quantityDial>0) ) " + " order by a.name";

                    q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                    q.setParameter(0, id);
                    q.setParameter(1, Constant.STATUS_USE);

                    q.setParameter(2, Constant.STATUS_USE);
                    q.setParameter(3, ownerId);
                    q.setParameter(4, ownerType);
                }

                List lstStockModel = q.list();
                listItemsSelectBox.addAll(lstStockModel);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return "success";
    }

    /*
     * Author: ThanhNC Date created: 10/03/2009 Purpose: chọn telecomservice -->
     * mat hang (con trong kho)
     */
    public String getStockModelByTelecomService() throws Exception {
        try {

            HttpServletRequest httpServletRequest = getRequest();
            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute("userToken");
            //Chon hang hoa tu loai hang hoa
            String telecomServiceId = httpServletRequest.getParameter("telecomServiceId");
            String staffCode = httpServletRequest.getParameter("staffCode");
            String[] header = {"", getText(SELECT_RES)};
            listItemsSelectBox.add(header);
            if (telecomServiceId != null && !"".equals(telecomServiceId.trim())) {
                Long id = Long.parseLong(telecomServiceId.trim());
                if (staffCode == null || "".equals(staffCode)) {
                    return "success";
                }

                Query q = null;
                String SQL_SELECT_STOCK_MODEL = "select stockModelId, name from StockModel where telecomServiceId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                ChannelDAO channelDAO = new ChannelDAO();
                channelDAO.setSession(getSession());
                Staff staff = channelDAO.getStaff(staffCode);
                if (staff == null) {
                    return "success";
                }
                Long ownerId = userToken.getUserID();
                Long ownerType = 2L;

                q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                q.setParameter(0, id);
                q.setParameter(1, Constant.STATUS_USE);

                if (ownerType != null && ownerId != null) {
                    //SQL_SELECT_STOCK_MODEL = "select a.stockModelId, a.name from StockModel a, StockDeposit c where a.telecomServiceId= ? and a.status = ?  " +
                    SQL_SELECT_STOCK_MODEL = "select a.stockModelId, a.name from StockModel a, StockDeposit c where c.status = 1 and a.telecomServiceId= ? and a.status = ?  "
                            + " and (trunc(c.dateFrom) <= trunc(sysdate) and (c.dateTo is null or trunc(c.dateTo) >= trunc(sysdate))) "
                            + " and c.stockModelId = a.stockModelId and c.chanelTypeId = ? and c.transType = 1"
                            + " and exists " + " ( select b.id.stockModelId from StockTotal b where b.id.stockModelId =a.stockModelId and b.status = ? and b.id.ownerId = ? and b.id.ownerType = ? and  (quantityIssue >0 or quantityDial>0) ) "
                            + " order by a.name";
                    q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                    q.setParameter(0, id);
                    q.setParameter(1, Constant.STATUS_USE);
                    q.setParameter(2, staff.getChannelTypeId());

                    q.setParameter(3, Constant.STATUS_USE);
                    q.setParameter(4, ownerId);
                    q.setParameter(5, ownerType);
                }
                List lstStockModel = q.list();


                listItemsSelectBox.addAll(lstStockModel);

            }

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return "success";
    }

    /*
     * Author: ThanhNC Date created: 10/03/2009 Purpose: Chon mat hang xuat kho
     * --> check mat hang co the boc tham khong
     */
    public String selectStockModel() throws Exception {
        log.info("# Begin method selectStockModel");
        HttpServletRequest req = getRequest();
        String pageForward = "stockGoods";

        /*
         * TrongLV ADD START
         */
        String revokeAgent = (String) req.getParameter("revokeAgent");
        if (revokeAgent != null && revokeAgent.equals("true")) {
            req.setAttribute("revokeAgent", "true");
            pageForward = "editGoodsRecover";
        }
        /*
         * TrongLV ADD STOP
         */




        //----------------------------------------------------------------------
        //tamdt1, 16/09/2010, start bo sung chon mat hang bang F9
        //--------kiem tra ma mat hang co ton tai khong
        String stockModelCodeF9 = this.goodsForm.getStockModelCode();
        if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return pageForward;
        }

        //trim cac truong can thiet
        stockModelCodeF9 = stockModelCodeF9.trim();
        this.goodsForm.setStockModelCode(stockModelCodeF9);
        //
        StockModelDAO stockModelDAOF9 = new StockModelDAO();
        stockModelDAOF9.setSession(this.getSession());
        StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9);
        if (stockModelF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        } else {
            this.goodsForm.setStockTypeId(stockModelF9.getStockTypeId());
            this.goodsForm.setStockModelId(stockModelF9.getStockModelId());
            this.goodsForm.setStockModelCode(stockModelF9.getStockModelCode());
            this.goodsForm.setStockModelName(stockModelF9.getName());
        }
        //tamdt1, 16/09/2010, end
        //----------------------------------------------------------------------

        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");
        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        }
        /*
         * LamNV ADD STOP
         */

        //Mac dinh set trang thai boc tham la ko phai boc tham
        goodsForm.setCanDial(Constant.EXP_NON_DIAL);

//        StockTypeDAO stockTypeDAO = new StockTypeDAO();
//        stockTypeDAO.setSession(this.getSession());
//        List lstStockType = stockTypeDAO.findAllAvailable();
//        req.setAttribute("lstStockType", lstStockType);

//
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
//        if (goodsForm.getStockTypeId() != null) {
//            List lstStockModel = getStockModelInShop(goodsForm.getOwnerType(), goodsForm.getOwnerId(), goodsForm.getStockTypeId(), Constant.STATUS_USE);
//            //List lstStockModel = stockModelDAO.findByProperty(StockModelDAO.STOCK_TYPE_ID, goodsForm.getStockTypeId());
//            req.setAttribute("lstStockModel", lstStockModel);
//        }

        if (goodsForm.getStockModelId() != null) {

            StockModel model = stockModelDAO.findById(goodsForm.getStockModelId());
            if (model != null) {
                Long checkDial = model.getCheckDial();
                if (checkDial != null && checkDial.equals(Constant.MUST_DRAW)) {
                    goodsForm.setCanDial(Constant.EXP_CAN_DIAL);
                }

            }

        }


        log.info("# End method selectStockModel");
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 10/03/2009 Purpose: Chon mat hang xuat kho
     * --> check mat hang co the boc tham khong
     *
     *
     * Modified by TrongLV Modify date 2011/04/18 Purpose select goods to expot
     * to collaborator
     */
    public String selectStockModelColl() throws Exception {
        log.info("# Begin method selectStockModel");
        HttpServletRequest req = getRequest();

        String pageForward = "stockGoods";
        req.setAttribute("collaborator", "coll");


        //----------------------------------------------------------------------
        //tamdt1, 16/09/2010, start bo sung chon mat hang bang F9
        //--------kiem tra ma mat hang co ton tai khong
        String stockModelCodeF9 = this.goodsForm.getStockModelCode();
        if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return pageForward;
        }

        //trim cac truong can thiet
        stockModelCodeF9 = stockModelCodeF9.trim();
        this.goodsForm.setStockModelCode(stockModelCodeF9);
        //
        StockModelDAO stockModelDAOF9 = new StockModelDAO();
        stockModelDAOF9.setSession(this.getSession());
        StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9);
        if (stockModelF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        } else {
            this.goodsForm.setStockTypeId(stockModelF9.getStockTypeId());
            this.goodsForm.setStockModelId(stockModelF9.getStockModelId());
            this.goodsForm.setStockModelCode(stockModelF9.getStockModelCode());
            this.goodsForm.setStockModelName(stockModelF9.getName());
        }
        //tamdt1, 16/09/2010, end
        //----------------------------------------------------------------------

        log.info("# End method selectStockModel");
        return pageForward;
    }

    private List getStockModelInShopColl() throws Exception {
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        Long telecomServiceId = goodsForm.getTelecomServiceId();
        if (telecomServiceId != null && telecomServiceId.compareTo(0L) != 0) {
            Query q = null;
            String SQL_SELECT_STOCK_MODEL = "select stockModelId, name from StockModel where telecomServiceId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
            Long ownerType = goodsForm.getOwnerType();
            Long ownerId = goodsForm.getOwnerId();

            q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
            q.setParameter(0, telecomServiceId);
            q.setParameter(1, Constant.STATUS_USE);

            if (ownerType != null && ownerId != null) {
                SQL_SELECT_STOCK_MODEL = " select a.stockModelId, a.name from StockModel a, StockDeposit c where a.telecomServiceId= ? and a.status = ?  "
                        + " and exists ( select b.id.stockModelId from StockTotal b where b.id.stockModelId =a.stockModelId and b.status = ? and b.id.ownerId = ? and b.id.ownerType= ? and  (quantityIssue >0 or quantityDial>0) ) "
                        + " and c.stockModelId = a.stockModelId and c.chanelTypeId = ? "
                        + " order by a.name";

                q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                q.setParameter(0, telecomServiceId);
                q.setParameter(1, Constant.STATUS_USE);
                q.setParameter(2, Constant.STATUS_USE);
                q.setParameter(3, ownerId);
                q.setParameter(4, ownerType);
                q.setParameter(5, 10L);
            }

            List listStock = q.list();
            List<StockModel> listStockModel = new ArrayList();
            Iterator iter = listStock.iterator();
            while (iter.hasNext()) {

                Object[] temp = (Object[]) iter.next();
                StockModel stockModel = new StockModel();
                stockModel.setStockModelId(new Long(temp[0].toString()));
                stockModel.setName(temp[1].toString());
                listStockModel.add(stockModel);
            }
            return listStockModel;
        }
        return null;

    }

    private List getStockModelInShopCollByTelecomService(Long telecomServiceId) throws Exception {
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        if (telecomServiceId != null && telecomServiceId.compareTo(0L) != 0) {
            Query q = null;

            String SQL_SELECT_STOCK_MODEL = "select stockModelId, name from StockModel where telecomServiceId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
            Long ownerType = goodsForm.getOwnerType();
            Long ownerId = goodsForm.getOwnerId();

            q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
            q.setParameter(0, telecomServiceId);
            q.setParameter(1, Constant.STATUS_USE);

            if (ownerType != null && ownerId != null) {
                SQL_SELECT_STOCK_MODEL = " select a.stockModelId, a.name from StockModel a, StockDeposit c where a.telecomServiceId= ? and a.status = ?  "
                        + " and exists ( select b.id.stockModelId from StockTotal b where b.id.stockModelId =a.stockModelId and b.status = ? and b.id.ownerId = ? and b.id.ownerType = ? and  (quantityIssue >0 or quantityDial>0) ) "
                        + " and c.stockModelId = a.stockModelId and c.chanelTypeId = ?"
                        + " order by a.name";

                q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                q.setParameter(0, telecomServiceId);
                q.setParameter(1, Constant.STATUS_USE);
                q.setParameter(2, Constant.STATUS_USE);
                q.setParameter(3, ownerId);
                q.setParameter(4, ownerType);
                q.setParameter(5, 10L);
            }

            List listStock = q.list();
            List<StockModel> listStockModel = new ArrayList();
            Iterator iter = listStock.iterator();
            while (iter.hasNext()) {

                Object[] temp = (Object[]) iter.next();
                StockModel stockModel = new StockModel();
                stockModel.setStockModelId(new Long(temp[0].toString()));
                stockModel.setName(temp[1].toString());
                listStockModel.add(stockModel);
            }
            return listStockModel;
        }
        return null;

    }

    private List getStockModelInShop(
            Long ownerType, Long ownerId, Long stockTypeId, Long status) throws Exception {
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        if (stockTypeId != null) {
            Query q = null;

            String SQL_SELECT_STOCK_MODEL = " from StockModel where stockTypeId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc ";

            q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
            q.setParameter(0, stockTypeId);
            q.setParameter(1, status);
            if (ownerType != null && ownerId != null) {
                SQL_SELECT_STOCK_MODEL = " from StockModel a where a.stockTypeId= ? and a.status = ?  " + " and exists " + " ( select b.id.stockModelId from StockTotal b where b.id.stockModelId =a.stockModelId and b.status = ? and b.id.ownerId = ? and b.id.ownerType= ? and  (quantityIssue >0 or quantityDial>0) ) " + " order by nlssort(a.name,'nls_sort=Vietnamese') asc";

                q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                q.setParameter(0, stockTypeId);
                q.setParameter(1, status);

                q.setParameter(2, Constant.STATUS_USE);
                q.setParameter(3, ownerId);
                q.setParameter(4, ownerType);

            }
            return q.list();
        }

        return new ArrayList();

    }
    /*
     * Author: ThanhNC Date created: 10/03/2009 Purpose: Khoi tao trang khi vao
     * man hinh nhap chi tiet serial
     */

    public String prepareInputSerial() throws Exception {
        log.info("# Begin method prepareInputSerial");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstSerial" + pageId);
        req.getSession().removeAttribute("editable" + pageId);
        String pageForward = "inputSerial";
        serialGoods.setTotal(0L);
        SerialGoods serialGood = getSerialGoods();


        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        Long ownerTypeId = 0L;
        Long ownerId = 0L;

        Long impOwnerType = 0L;
        Long impOwnerId = 0L;

        Long stockModelId = 0L;
        Long stateId = 0L;
        Long stockTransId = null;
        //R8388
        Long reasonId = 0L;
        Long saleTransDetailId = 0L;
        String otpVipCustomer = "";


        //TrongLV
        Long parentId;
        Long purposeInputSerial = 0L;//1 : ban le + no bo ; 2 : ban khuyen mai ; 3 : ban khac ; 4 : xuat tra doi tac ; 5 : xuat cho kho khac
        if (QueryCryptUtils.getParameter(req, "purposeInputSerial") != null) {
            purposeInputSerial = Long.valueOf(QueryCryptUtils.getParameter(req, "purposeInputSerial"));
            serialGood.setPurposeInputSerial(purposeInputSerial);
        }
//        tann20180111 them truong lay saleTransDetailId cho chuc năng  account trans
        if (QueryCryptUtils.getParameter(req, "saleTransDetailId") != null) {
            saleTransDetailId = Long.valueOf(QueryCryptUtils.getParameter(req, "saleTransDetailId"));
            serialGood.setSaleTransDetailId(saleTransDetailId);
        }

        if (QueryCryptUtils.getParameter(req, "reasonId") != null) {
            reasonId = Long.valueOf(QueryCryptUtils.getParameter(req, "reasonId"));
            serialGood.setReasonId(reasonId);
        }

        if (QueryCryptUtils.getParameter(req, "otpVipCustomer") != null) {
            otpVipCustomer = QueryCryptUtils.getParameter(req, "otpVipCustomer");
            serialGood.setOtp(otpVipCustomer);
        }

        if (QueryCryptUtils.getParameter(req, "parentId") != null) {
            String parentIdTmp = QueryCryptUtils.getParameter(req, "parentId");
            parentId =
                    Long.parseLong(parentIdTmp);
            serialGood.setParentId(parentId);
        }

        if (userToken.getShopId() != null) {
            ownerTypeId = Constant.OWNER_TYPE_SHOP;
            ownerId =
                    userToken.getShopId();
            serialGood.setOwnerType(ownerTypeId);
            serialGood.setOwnerId(ownerId);
        }

        if (QueryCryptUtils.getParameter(req, "ownerType") != null) {
            String ownerType = QueryCryptUtils.getParameter(req, "ownerType");
            ownerTypeId =
                    Long.parseLong(ownerType);
            serialGood.setOwnerType(ownerTypeId);
        }

        if (QueryCryptUtils.getParameter(req, "ownerId") != null) {
            String owner = QueryCryptUtils.getParameter(req, "ownerId");
            ownerId =
                    Long.parseLong(owner);
            serialGood.setOwnerId(ownerId);
        }



        if (QueryCryptUtils.getParameter(req, "totalReq") != null) {
            String strTotalReq = QueryCryptUtils.getParameter(req, "totalReq");
            Long totalReq = Long.parseLong(strTotalReq);
            serialGood.setTotalReq(totalReq);
        }

        if (QueryCryptUtils.getParameter(req, "stockModelId") != null) {
            String strStockModelId = QueryCryptUtils.getParameter(req, "stockModelId");
            stockModelId =
                    Long.parseLong(strStockModelId);
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel model = stockModelDAO.findById(stockModelId);
            if (model != null) {
                serialGood.setStockTypeId(model.getStockTypeId());
                serialGood.setStockModelId(stockModelId);
                serialGood.setStockModelName(model.getName());
            }

        }
        if (QueryCryptUtils.getParameter(req, "stateId") != null) {
            String strStateId = QueryCryptUtils.getParameter(req, "stateId");
            if (strStateId != null && !strStateId.equals("")) {
                stateId = Long.parseLong(strStateId);
                serialGood.setStateId(stateId);
            }

        }
        if (QueryCryptUtils.getParameter(req, "stockTransId") != null) {
            String strStockTransId = QueryCryptUtils.getParameter(req, "stockTransId");
            if (strStockTransId != null && !strStockTransId.equals("")) {
                stockTransId = Long.parseLong(strStockTransId);
                serialGood.setStockTransId(stockTransId);
            }

        }
        String editable = "false";
        if (QueryCryptUtils.getParameter(req, "editable") != null) {
            editable = QueryCryptUtils.getParameter(req, "editable");
            serialGood.setEditable(editable);
        }

        if (ownerTypeId != 0L && ownerId != 0L) {
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            Object obj = baseStockDAO.getStockByTypeAndId(ownerTypeId, ownerId);
            if (obj instanceof Shop) {
                Shop shop = (Shop) obj;
                serialGood.setOwnerCode(shop.getShopCode());
                serialGood.setOwnerName(shop.getName());
            }

            if (obj instanceof Staff) {
                Staff staff = (Staff) obj;
                serialGood.setOwnerCode(staff.getStaffCode());
                serialGood.setOwnerName(staff.getName());
            }

        }
        String revokeColl = QueryCryptUtils.getParameter(req, "revokeColl");
        if (revokeColl != null && revokeColl.equals("true")) {
            serialGood.setIsRevokeGoodsAction(true);
        }

        //tamdt1, 10/09/2010, start, them vao phuc vu viec ban hang theo kenh
        String strImpChannelTypeId = QueryCryptUtils.getParameter(req, "impChannelTypeId");
        if (strImpChannelTypeId != null && !strImpChannelTypeId.trim().equals("")) {
            serialGood.setImpChannelTypeId(strImpChannelTypeId.trim());
        }
        if (QueryCryptUtils.getParameter(req, "impOwnerId") != null) {
            String impOwnerIdTmp = QueryCryptUtils.getParameter(req, "impOwnerId");
            impOwnerId =
                    Long.parseLong(impOwnerIdTmp);
            serialGood.setImpOwnerId(impOwnerId);
        }

        if (QueryCryptUtils.getParameter(req, "impOwnerType") != null) {
            String impOwnerTypeTmp = QueryCryptUtils.getParameter(req, "impOwnerType");
            impOwnerType =
                    Long.parseLong(impOwnerTypeTmp);
            serialGood.setImpOwnerType(impOwnerType);
        }

        if (QueryCryptUtils.getParameter(req, "staffExpToShop") != null) {
            String staffExpToShopTmp = QueryCryptUtils.getParameter(req, "staffExpToShop");
            Boolean staffExpToShop = Boolean.parseBoolean(staffExpToShopTmp);
            if (staffExpToShop != null && staffExpToShop) {
                serialGood.setImpOwnerId(userToken.getShopId());
                serialGood.setImpOwnerType(Constant.OWNER_TYPE_SHOP);
                //Bat dau - tim kenh can gan
                //Neu la NVQL, chi quan ly 1 kenh thi gan kenh = kenh ma nv ql; neu quan ly nhieu kenh thi gan kenh = null
                //Neu la GDV, ko quan ly kenh thi gan kenh = kenh ban le
                CommonDAO commonDAO = new CommonDAO();
                List<ChannelType> listChannelType = commonDAO.getChannelTypeByStaffOwnerId(this.getSession(), userToken.getUserID());
                if (listChannelType == null || listChannelType.isEmpty()) {
                    serialGood.setImpChannelTypeId(Constant.CHANNEL_TYPE_RETAIL.toString());//ban le
                } else if (listChannelType.size() == 1) {
                    serialGood.setImpChannelTypeId(listChannelType.get(0).getChannelTypeId().toString());
                } else {
                    serialGood.setImpChannelTypeId("-1");
                }
                //Ket thuc - tim can kenh gan
            }

        }
        //tamdt1, 10/09/2010, end

        //Kiem tra truong hop xem chi tiet serial
        if (QueryCryptUtils.getParameter(req, "isView") != null) {
            String strIsView = QueryCryptUtils.getParameter(req, "isView");
            if (strIsView != null && strIsView.trim().equals("true")) {
                System.out.println("view chi tiet giao dich");
                if (purposeInputSerial != null && (purposeInputSerial.equals(Constant.PURPOSE_INPUT_SERIAL_SALE_TO_RETAIL) || purposeInputSerial.equals(Constant.PURPOSE_INPUT_SERIAL_SALE_TO_PUNISH_STAFF))) {
                    List<SaleTransDetailV2Bean> lstGoods = (List<SaleTransDetailV2Bean>) req.getSession().getAttribute("lstGoods" + pageId);
                    List<StockTransSerial> lstSerial;
                    if (lstGoods != null && lstGoods.size() > 0) {
                        for (SaleTransDetailV2Bean bean : lstGoods) {
                            if (bean.getStockModelId().equals(stockModelId)) {
                                lstSerial = bean.getLstSerial();
                                req.getSession().setAttribute("lstSerial" + pageId, lstSerial);
                                break;
                            }
                        }
                    }
                    System.out.println("view chi tiet giao dich : input serial");
                    return pageForward;
                }

                /*
                 * TRONGLV : XU LY TRUONG HOP VIEW CHI TIET GIAO DICH KHO
                 */
                String isViewTransDetailString = QueryCryptUtils.getParameter(req, "isViewTransDetail");
                boolean isViewTransDetail = false;
                if (isViewTransDetailString != null && !isViewTransDetailString.trim().equals("")) {
                    isViewTransDetail = Boolean.valueOf(isViewTransDetailString.trim());
                }
                /*
                 * TRONGLV : XU LY TRUONG HOP VIEW CHI TIET GIAO DICH KHO
                 */

                List<StockTransFull> lstGoods = (List<StockTransFull>) req.getSession().getAttribute("lstGoods" + pageId);
                List<StockTransSerial> lstSerial;
                StockTransSerial stockSerial = new StockTransSerial();
                if (lstGoods != null && lstGoods.size() > 0) {
                    for (StockTransFull stockTransFull : lstGoods) {
                        if (stockTransFull.getStockModelId().equals(stockModelId) && stockTransFull.getStateId().equals(stateId)) {
                            if (isViewTransDetail) {
                                System.out.println("view chi tiet giao dich : view trans detail ");
                                /*
                                 * TRONGLV : XU LY TRUONG HOP VIEW CHI TIET GIAO
                                 * DICH KHO
                                 */
                                stockSerial = new StockTransSerial();
                                stockSerial.setStockModelId(stockTransFull.getStockModelId());
                                stockSerial.setStockTransId(stockTransFull.getStockTransId());
                                stockSerial.setStateId(stockTransFull.getStateId());
                                lstSerial = (new StockTransSerialDAO()).findByExample(stockSerial);
                                if (lstSerial != null && lstSerial.size() >= Constant.SEARCH_MAX_RESULT) {
                                    req.setAttribute("returnMsg", "Warning! This transaction has >= " + String.valueOf(Constant.SEARCH_MAX_RESULT) + " records, you can request IT.Dept support to get all records ");
                                }
                                req.setAttribute("lstSerial", lstSerial);
                                /*
                                 * TRONGLV : XU LY TRUONG HOP VIEW CHI TIET GIAO
                                 * DICH KHO
                                 */
                            } else {
                                System.out.println("view chi tiet giao dich : not view trans detail ");
                                lstSerial = stockTransFull.getLstSerial();
                                req.getSession().setAttribute("lstSerial" + pageId, lstSerial);
                            }
                            return pageForward;
                        }
                    }
                }
            }
        }
        log.info("# End method prepareInputSerial");
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 09/06/2009 Purpose: Thay doi cach nhap chi
     * tiet serial (theo file hoac theo dai)
     */
    public String changeMethodInputSerial() throws Exception {
        log.info("# Begin method StockCommonDAO.changeMethodInputSerial");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstSerial" + pageId);
        req.getSession().removeAttribute("editable" + pageId);
        serialGoods.setTotal(0L);
        String pageForward = "inputSerial";
        log.info("# End method StockCommonDAO.changeMethodInputSerial");
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: Them serial vao danh
     * sach serial hang xuat di
     */
    public String searchListSerial() throws Exception {
        log.info("# Begin method searchListSerial");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        //tamdt1, 10/09, start them vao phuc vu viec ban hang theo kenh
        String impChannelTypeId = serialGoods.getImpChannelTypeId();
        //tamdt1, 10/09, end
        String pageFoward = "inputSerial";
        try {
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            List<StockTransSerial> listStockTransSerials = (List<StockTransSerial>) req.getSession().getAttribute("lstSerial" + pageId);
            if (listStockTransSerials == null) {
                listStockTransSerials = new ArrayList<StockTransSerial>();
                req.getSession().setAttribute("lstSerial" + pageId, listStockTransSerials);
            }
//Nhap theo file
            if (serialGoods.getImpType() != null && serialGoods.getImpType().equals(Constant.STATUS_IMPORT_BY_FILE)) {
                File clientFile = serialGoods.getImpFile();
                if (clientFile == null) {
                    req.setAttribute("returnMsg", "ERR.STK.056");
//                    Không được được file dữ liệu đầu vào.
                    return pageFoward;
                }

                List lst = new CommonDAO().readExcelFile(clientFile, "Sheet1", 0, 0, 1);
                if (lst == null) {
                    req.setAttribute("returnMsg", "ERR.STK.057");
//                    File đầu vào không đúng định dạng.
                    return pageFoward;
                }

                Long quantityInput = serialGoods.getTotal();
//                Huynq13 20170405 start checking same data in rows
                ArrayList<BigInteger> arFromSerial = new ArrayList<BigInteger>();
                ArrayList<BigInteger> arToSerial = new ArrayList<BigInteger>();
                for (int i = 0; i < lst.size(); i++) {
                    Object[] obj = (Object[]) lst.get(i);
                    if (obj != null && obj.length > 1) {
                        BigInteger frSerial = new BigInteger(obj[0].toString().trim());
                        BigInteger toSerial = new BigInteger(obj[1].toString().trim());
                        if (arFromSerial.contains(frSerial)) {
                            req.setAttribute("returnMsg", "Found same serial " + frSerial + " in rows of your excel file, let correct it");
                            serialGoods.setTotal(quantityInput);
                            return pageFoward;
                        } else {
                            arFromSerial.add(frSerial);
                        }
                        if (arToSerial.contains(toSerial)) {
                            req.setAttribute("returnMsg", "Found same serial " + toSerial + " in rows of your excel file, let correct it");
                            serialGoods.setTotal(quantityInput);
                            return pageFoward;
                        } else {
                            arToSerial.add(toSerial);
                        }
                    }
                }
//                Huynq13 20170405 end checking same data in rows         
                ArrayList<BigInteger> listOnlyOneSerial = new ArrayList<BigInteger>();
                DbProcessor db = new DbProcessor();
                for (int i = 0; i < lst.size(); i++) {
                    BigInteger lFromSerial = null;
                    BigInteger lToSerial = null;
                    Object[] obj = (Object[]) lst.get(i);
                    boolean onlyOneSerial = false;
                    if (obj != null && obj.length > 1) {
                        lFromSerial = new BigInteger(obj[0].toString().trim());
                        lToSerial = new BigInteger(obj[1].toString().trim());
//  Huynq13 start add to check toSerial - fromSerial = quantity input and quantity input must less than 50k
                        if ((lToSerial.subtract(lFromSerial).intValue() + 1) > 50001) {
                            log.info("Quantity input greater 50000 fromSerial" + lFromSerial + " toSerial " + lToSerial);
                            System.out.println("Quantity input greater 50000 fromSerial" + lFromSerial + " toSerial " + lToSerial);
                            req.setAttribute("returnMsg", "Each row in excel file must have toSerial - fromSerial < 50.000, now your file has a row invalid "
                                    + " fromSerial " + lFromSerial
                                    + " toSerial " + lToSerial
                                    + " substraction = " + (lToSerial.subtract(lFromSerial).intValue() + 1)); //File đầu vào không đúng định dạng.
                            serialGoods.setTotal(quantityInput);
                            return pageFoward;
                        }
//  Huynq13 end add to check toSerial - fromSerial = quantity input and quantity input must less than 50k
                        if (obj[0].toString().trim().equals(obj[1].toString().trim())) {
                            onlyOneSerial = true;
                            listOnlyOneSerial.add(lFromSerial);
                        }
                        StockModelDAO stockModelDAO = new StockModelDAO();
                        stockModelDAO.setSession(this.getSession());
                        StockModel model = stockModelDAO.findById(serialGoods.getStockModelId());
                        if (model == null) {
                            req.setAttribute("returnMsg", "ERR.STK.058");
//                            Không tìm thấy mặt hàng trên hệ thống
                            serialGoods.setTotal(quantityInput);
                            return pageFoward;
                        }
//tamdt1 - start
//phan xu ly trong truong hop submit lan thu 2 tro di

                        for (int idx = 0; idx < listStockTransSerials.size(); idx++) {
                            StockTransSerial tmpStockTransSerial = listStockTransSerials.get(idx);
                            BigInteger beginSerial = new BigInteger(tmpStockTransSerial.getFromSerial());
                            BigInteger endSerial = new BigInteger(tmpStockTransSerial.getToSerial());
                            if ((beginSerial.compareTo(lFromSerial) >= 0 && beginSerial.compareTo(lToSerial) <= 0) || endSerial.compareTo(lFromSerial) >= 0 && endSerial.compareTo(lToSerial) <= 0) {
//                                req.setAttribute("returnMsg", "Dải serial từ " + lFromSerial.toString() + " đến " + lToSerial.toString() + " bị trùng.");
                                req.setAttribute("returnMsg", getText("MSG.STK.010") + " " + lFromSerial.toString() + " " + getText("MSG.STK.011") + " " + lToSerial.toString() + " " + getText("MSG.STK.012"));
                                serialGoods.setTotal(quantityInput);
                                return pageFoward;
                            }

                        }
                        //tamdt1 - end
                        ShopDAO shopDAO = new ShopDAO();
                        shopDAO.setSession(this.getSession());
                        Shop fromShop = new Shop();
                        Shop toShop = new Shop();
                        StaffDAO staffDAO = new StaffDAO();
                        staffDAO.setSession(this.getSession());
                        Staff staff = new Staff();

                        if (serialGoods.getOwnerType() != null && serialGoods.getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                            fromShop = shopDAO.findById(serialGoods.getOwnerId());
                            if (fromShop == null || fromShop.getShopId() == null) {
                                req.setAttribute("returnMsg", "Error. From shop is not exist!");
                                return pageFoward;
                            }
                        }
                        if (serialGoods.getImpOwnerType() != null && serialGoods.getImpOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                            toShop = shopDAO.findById(serialGoods.getImpOwnerId());
                            if (toShop == null || toShop.getShopId() == null) {
                                req.setAttribute("returnMsg", "Error. To shop is not exist!");
                                return pageFoward;
                            }
                        }
                        boolean checkRoleAssignChannelToGoodsOfSupper = false;
                        boolean checkRoleAssignChannelToGoodsOfUnder = false;
//Huynq13 20170406 start add to validate fromSerial and toSerial in stock and not exported                    
                        String tableNameNormal = baseStockDAO.getTableNameByStockType(model.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
                        if (onlyOneSerial) {
                            if (i == 0 || i == lst.size() - 1) {
                                if (db.checkSerialExported(lToSerial.toString(), serialGoods.getOwnerId())) {
                                    req.setAttribute("returnMsg", "Can not export because serial from " + lFromSerial + " to " + lToSerial
                                            + " is exporting before, let take picture and contact IT");
                                    listStockTransSerials.clear();
                                    serialGoods.setTotal(0l);
                                    return pageFoward;
                                }
                            }
                        } else {
                            if (!db.checkSerialInStock(lFromSerial, tableNameNormal, Constant.STATUS_USE, serialGoods.getStateId(), serialGoods.getOwnerId(), serialGoods.getStockModelId())
                                    || !db.checkSerialInStock(lToSerial, tableNameNormal, Constant.STATUS_USE, serialGoods.getStateId(), serialGoods.getOwnerId(), serialGoods.getStockModelId())) {
                                req.setAttribute("returnMsg", "Can not export because serial from " + lFromSerial + " to " + lToSerial
                                        + " is not in stock, let check your serial again");
                                serialGoods.setTotal(quantityInput);
                                return pageFoward;
                            }
                            if (db.checkSerialExported(lFromSerial.toString(), serialGoods.getOwnerId())
                                    || db.checkSerialExported(lToSerial.toString(), serialGoods.getOwnerId())) {
                                req.setAttribute("returnMsg", "Can not export because serial from " + lFromSerial + " to " + lToSerial
                                        + " is exporting before, let take picture and contact IT");
                                serialGoods.setTotal(quantityInput);
                                return pageFoward;
                            }
                        }
//                        if ((i == lst.size() - 1) && listOnlyOneSerial.size() > 0) {
//                            if (!db.checkSerialInStockForOnlyOneSerial(listOnlyOneSerial, tableNameNormal, Constant.STATUS_USE, serialGoods.getStateId(), serialGoods.getOwnerId(), serialGoods.getStockModelId())) {
//                                String stateName = serialGoods.getStateId().equals(Constant.STATE_NEW) ? getText("MSG.STK.013") : serialGoods.getStateId().equals(Constant.STATE_OLD) ? getText("MSG.STK.014") : getText("MSG.STK.015");
//                                req.setAttribute("returnMsg", "The file is not enough "
//                                        + listOnlyOneSerial.size() + " goods with status " + stateName + " in stock");
//                                listStockTransSerials.clear();
//                                serialGoods.setTotal(0l);
//                                return pageFoward;
//                            }
//                        }
//Huynq13 20170406 end add to validate fromSerial and toSerial in stock and not exported             
                        if (fromShop != null && toShop != null && fromShop.getShopId() != null && toShop.getShopId() != null) {
                            //Truong hop xuat/nhap cho cap duoi hoac cap tren
                            if (toShop.getParentShopId() != null && !toShop.getParentShopId().equals(fromShop.getShopId())) {
                                //neu la ko phai cap tren xuat cho cap duoi
                                checkRoleAssignChannelToGoodsOfSupper = CommonDAO.checkRoleAssignChannelToGoods(this.getSession(), fromShop.getShopId(), true);
                            } else {
                                if (toShop.getParentShopId() != null && toShop.getParentShopId().equals(fromShop.getShopId())) {
                                    //neu la cap tren xuat cho cap duoi
                                    checkRoleAssignChannelToGoodsOfSupper = CommonDAO.checkRoleAssignChannelToGoods(this.getSession(), fromShop.getShopId(), true);
                                    //DINHDC ADD check the cao loaded .Neu la xuat the cao va la cong ty xuat cho chi nhanh.
                                    if (model.getStockTypeId().equals(Constant.STOCK_TYPE_CARD) && fromShop.getShopId().equals(Constant.VT_SHOP)
                                            && toShop.getParentShopId().equals(Constant.VT_SHOP)) {
                                        //                                        Huynq13 change solution to call Pro                                                                                    
                                        Provisioning pro = new Provisioning(ExchangeClientChannel.getInstance("../etc/exchange_client.cfg").getInstanceChannel(), log);
//                                    Provisioning pro = new Provisioning(ExchangeClientChannel.getInstance("D:\\2.Working\\20180115SM\\Web\\Web\\etc\\exchange_client.cfg").getInstanceChannel(), log);

                                        if (!pro.viewCard(lFromSerial.toString())) {
                                            String errorLoadedCard = getText("Error.Batch.ScratchCard.have.not.been.loaded");
                                            errorLoadedCard = errorLoadedCard.replace("{0}", lFromSerial.toString());
                                            req.setAttribute("returnMsg", errorLoadedCard);
                                            return pageFoward;
                                        }
                                    }
                                } else {
                                    if (impChannelTypeId == null || impChannelTypeId.trim().equals("")) {
                                        checkRoleAssignChannelToGoodsOfSupper = CommonDAO.checkRoleAssignChannelToGoods(this.getSession(), fromShop.getShopId(), true);
                                    }
                                }
                            }
                        } else {
                            //Truong hop xuat/nhap voi nhan vien/bat buoc phai chon kenh/neu khong chon kenh => gan = -1
                            if (impChannelTypeId == null || impChannelTypeId.trim().equals("")) {
                                impChannelTypeId = "-1";
                            }
                            if (fromShop != null && fromShop.getShopId() != null && serialGoods.getImpOwnerType() != null && serialGoods.getImpOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                                //Truong hop cua hang xuat cho nhan vien
                                staff = staffDAO.findById(serialGoods.getImpOwnerId());
                                if (staff == null || staff.getStaffId() == null) {
                                    req.setAttribute("returnMsg", "Error. To staff is not exist!");
                                    return pageFoward;
                                }
                                checkRoleAssignChannelToGoodsOfSupper = CommonDAO.checkRoleAssignChannelToGoods(this.getSession(), fromShop.getShopId(), true);
                            }
                        }

                        if (!checkRoleAssignChannelToGoodsOfSupper) {
                            //neu don vi xuat khong co quyen gan kenh
                            if (impChannelTypeId == null || impChannelTypeId.equals("")) {
                                impChannelTypeId = "-1";
                            }
                        } else {
                            //neu don vi xuat co quyen gan kenh
                            if (fromShop != null && fromShop.getShopId() != null && toShop != null && toShop.getShopId() != null) {
                                //neu xuat nhap giua cac cap 
                                checkRoleAssignChannelToGoodsOfUnder = CommonDAO.checkRoleAssignChannelToGoods(this.getSession(), toShop.getShopId(), true);
                            }

                            if (!checkRoleAssignChannelToGoodsOfUnder) {
                                if (impChannelTypeId == null || impChannelTypeId.equals("")) {
                                    impChannelTypeId = "-1";
                                }
                            }
                        }
//                      Huynq13 20170204 start add to count serial in stock_card                         
                        if (!onlyOneSerial) {
                            String sql = "select count(*) as result from " + tableNameNormal + " "
                                    + "where    status = ? "
                                    + "         and serial >= to_number(?) "
                                    + "         and serial <= to_number(?) "
                                    + "         and owner_Id = ? "
                                    + "         and stock_Model_Id = ? "
                                    + "         and state_Id = ? ";
                            long countSerial = db.countSerial(sql, Constant.STATUS_USE, lFromSerial, lToSerial, serialGoods.getOwnerId(),
                                    serialGoods.getStockModelId(), serialGoods.getStateId());
//                        if (lstResult.size() != (lToSerial.subtract(lFromSerial).intValue() + 1)) { Huynq13 20170204 modify use other function to count serial
                            if (countSerial != (lToSerial.subtract(lFromSerial).intValue() + 1)) {
//                      Huynq13 20170204 end add to count serial in stock_card                             
//                        List<String> lstResult = q.list(); //Huynq ignore to use new way count serial
//                        if (lstResult.size() != (lToSerial.subtract(lFromSerial).intValue() + 1)) { //Huynq ignore to use new way count serial
                                String stateName = serialGoods.getStateId().equals(Constant.STATE_NEW) ? getText("MSG.STK.013") : serialGoods.getStateId().equals(Constant.STATE_OLD) ? getText("MSG.STK.014") : getText("MSG.STK.015");
                                req.setAttribute("returnMsg", getText("MSG.STK.010") + " " + lFromSerial.toString() + " " + getText("MSG.STK.011") + " " + lToSerial.toString()
                                        + " with status " + stateName + " is not enough " + (lToSerial.subtract(lFromSerial).intValue() + 1) + " in stock");
                                serialGoods.setTotal(quantityInput);
                                return pageFoward;
                            }
                        }
                        StockTransSerial stockTransSerial = new StockTransSerial();
                        stockTransSerial.setFromSerial(lFromSerial.toString());
                        stockTransSerial.setToSerial(lToSerial.toString());
                        Long total = lToSerial.subtract(lFromSerial).longValue() + 1L;
                        quantityInput += total;
                        stockTransSerial.setQuantity(total);
                        //tao id gia, muc dich de remove
                        Long maxId = 0L;
                        for (int j = 0; j < listStockTransSerials.size(); j++) {
                            if (listStockTransSerials.get(j).getStockTransSerialId().compareTo(maxId) > 0) {
                                maxId = listStockTransSerials.get(j).getStockTransSerialId();
                            }
                        }
                        stockTransSerial.setStockTransSerialId(++maxId);
                        listStockTransSerials.add(stockTransSerial);
                    }
                }
                serialGoods.setTotal(quantityInput);
                if (!serialGoods.getTotal().equals(serialGoods.getTotalReq())) {
                    req.setAttribute("returnMsg", "ERR.STK.066");
                    return pageFoward;
//                    req.setAttribute("returnMsg", "Số lượng serial nhập vào không bằng số lượng yêu cầu.");
                }

            } //Nhap theo dai
            else {
                //Tu serial den serial co du lieu
                if (serialGoods.getFromSerial() != null && !"".equals(serialGoods.getFromSerial().trim())
                        && serialGoods.getToSerial() != null && !"".equals(serialGoods.getToSerial().trim()) && serialGoods.getStateId() != null) {
                    BigInteger lFromSerial = new BigInteger(serialGoods.getFromSerial().trim());
                    BigInteger lToSerial = new BigInteger(serialGoods.getToSerial().trim());
                    Long quantity = serialGoods.getTotalReq();
                    //Long total = serialGoods.getTotal();
                    Long total = getListSize(listStockTransSerials);
                    StockModelDAO stockModelDAO = new StockModelDAO();
                    stockModelDAO.setSession(this.getSession());
                    StockModel model = stockModelDAO.findById(serialGoods.getStockModelId());
                    if (model == null) {
//                        req.setAttribute("returnMsg", "Không tìm thấy mặt hàng trên hệ thống");
                        req.setAttribute("returnMsg", "ERR.STK.058");
                        return pageFoward;
                    }

//tamdt1 - start
//phan xu ly trong truong hop submit lan thu 2 tro di                    
                    for (int i = 0; i < listStockTransSerials.size(); i++) {
                        StockTransSerial tmpStockTransSerial = listStockTransSerials.get(i);
                        BigInteger beginSerial = new BigInteger(tmpStockTransSerial.getFromSerial());
                        BigInteger endSerial = new BigInteger(tmpStockTransSerial.getToSerial());
                        if ((beginSerial.compareTo(lFromSerial) >= 0 && beginSerial.compareTo(lToSerial) <= 0) || (endSerial.compareTo(lFromSerial) >= 0 && endSerial.compareTo(lToSerial) <= 0) || (beginSerial.compareTo(lFromSerial) <= 0 && endSerial.compareTo(lToSerial) >= 0)) {
//                            req.setAttribute("returnMsg", "Dải serial từ " + lFromSerial.toString() + " đến " + lToSerial.toString() + " bị trùng.");
                            req.setAttribute("returnMsg", getText("ERR.STK.072") + " " + getText("MSG.STK.010") + " " + lFromSerial.toString() + getText("MSG.STK.011") + " " + lToSerial.toString() + " " + getText("MSG.STK.012"));
                            return pageFoward;
                        }
                    }
                    //tamdt1 - end
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    Shop fromShop = new Shop();
                    Shop toShop = new Shop();

                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(this.getSession());
//                    Staff staff = new Staff();
                    Staff fromStaff = new Staff();
                    Staff toStaff = new Staff();

                    if (serialGoods.getOwnerType() != null && serialGoods.getOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                        fromShop = shopDAO.findById(serialGoods.getOwnerId());
                        if (fromShop == null || fromShop.getShopId() == null) {
                            req.setAttribute("returnMsg", "Error. From shop is not exist!");
                            return pageFoward;
                        }
                    } else if (serialGoods.getOwnerId() != null && serialGoods.getOwnerType() != null && serialGoods.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                        fromStaff = staffDAO.findById(serialGoods.getOwnerId());
                        if (fromStaff == null || fromStaff.getShopId() == null) {
                            req.setAttribute("returnMsg", "Error. To staff is not exist!");
                            return "viewDetailSerial";
                        }
                    }

                    if (serialGoods.getImpOwnerType() != null && serialGoods.getImpOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
                        toShop = shopDAO.findById(serialGoods.getImpOwnerId());
                        if (toShop == null || toShop.getShopId() == null) {
                            req.setAttribute("returnMsg", "Error. To shop is not exist!");
                            return pageFoward;
                        }
                    } else if (serialGoods.getImpOwnerId() != null && serialGoods.getImpOwnerType() != null && serialGoods.getImpOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                        toStaff = staffDAO.findById(serialGoods.getImpOwnerId());
                        if (toStaff == null || toStaff.getShopId() == null) {
                            req.setAttribute("returnMsg", "Error. To staff is not exist!");
                            return "viewDetailSerial";
                        }
                    }

                    boolean checkRoleAssignChannelToGoodsOfSupper = false;
                    boolean checkRoleAssignChannelToGoodsOfUnder = false;
//Huynq13 20170406 start add to validate fromSerial and toSerial in stock and not exported                    
                    String tableNameNormal = baseStockDAO.getTableNameByStockType(model.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
                    DbProcessor db = new DbProcessor();
                    if (!db.checkSerialInStock(lFromSerial, tableNameNormal, Constant.STATUS_USE, serialGoods.getStateId(), serialGoods.getOwnerId(), serialGoods.getStockModelId())) {
                        req.setAttribute("returnMsg", "Can not export because serial from " + lFromSerial + " to " + lToSerial + " is not in stock, let check your serial again");
                        return pageFoward;
                    }
                    if (db.checkSerialExported(lFromSerial.toString(), serialGoods.getOwnerId())) {
                        req.setAttribute("returnMsg", "Can not export because serial from " + lFromSerial + " to " + lToSerial + " is exporting before, let take picture and send to IT");
                        return pageFoward;
                    }
//Huynq13 20170406 end add to validate fromSerial and toSerial in stock and not exported   
                    if (fromShop != null && toShop != null && fromShop.getShopId() != null && toShop.getShopId() != null) {
                        //Truong hop xuat/nhap cho cap duoi hoac cap tren
                        if (toShop.getParentShopId() != null && !toShop.getParentShopId().equals(fromShop.getShopId())) {
                            //neu la ko phai cap tren xuat cho cap duoi
                            checkRoleAssignChannelToGoodsOfSupper = CommonDAO.checkRoleAssignChannelToGoods(this.getSession(), fromShop.getShopId(), true);
                        } else {
                            if (toShop.getParentShopId() != null && toShop.getParentShopId().equals(fromShop.getShopId())) {
                                //neu la cap tren xuat cho cap duoi
                                checkRoleAssignChannelToGoodsOfSupper = CommonDAO.checkRoleAssignChannelToGoods(this.getSession(), fromShop.getShopId(), true);
                                //DINHDC ADD check the cao loaded .Neu la xuat the cao va la cong ty xuat cho chi nhanh.
                                if (model.getStockTypeId().equals(Constant.STOCK_TYPE_CARD) && fromShop.getShopId().equals(Constant.VT_SHOP)
                                        && toShop.getParentShopId().equals(Constant.VT_SHOP)) {
                                    //                                        Huynq13 change solution to call Pro                                                                                    
                                    Provisioning pro = new Provisioning(ExchangeClientChannel.getInstance("../etc/exchange_client.cfg").getInstanceChannel(), log);
//                                    Provisioning pro = new Provisioning(ExchangeClientChannel.getInstance("D:\\STUDY\\Project\\Movitel\\CC\\etc\\exchange_client.cfg").getInstanceChannel(), log);
                                    if (!pro.viewCard(lFromSerial.toString())) {
                                        String errorLoadedCard = getText("Error.Batch.ScratchCard.have.not.been.loaded");
                                        errorLoadedCard = errorLoadedCard.replace("{0}", lFromSerial.toString());
                                        req.setAttribute("returnMsg", errorLoadedCard);
                                        return pageFoward;
                                    }
                                }
                            } else {
                                if (impChannelTypeId == null || impChannelTypeId.trim().equals("")) {
                                    checkRoleAssignChannelToGoodsOfSupper = CommonDAO.checkRoleAssignChannelToGoods(this.getSession(), fromShop.getShopId(), true);
                                }
                            }
                        }
                    } else {
                        //Truong hop xuat/nhap voi nhan vien/bat buoc phai chon kenh/neu khong chon kenh => gan = -1
                        if (impChannelTypeId == null || impChannelTypeId.trim().equals("")) {
                            impChannelTypeId = "-1";
                        }
                        if (fromShop != null && fromShop.getShopId() != null && serialGoods.getImpOwnerType() != null && serialGoods.getImpOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                            //Truong hop cua hang xuat cho nhan vien
                            toStaff = staffDAO.findById(serialGoods.getImpOwnerId());
                            if (toStaff == null || toStaff.getStaffId() == null) {
                                req.setAttribute("returnMsg", "Error. To staff is not exist!");
                                return pageFoward;
                            }
                            checkRoleAssignChannelToGoodsOfSupper = CommonDAO.checkRoleAssignChannelToGoods(this.getSession(), fromShop.getShopId(), true);
                        }
                    }

                    if (!checkRoleAssignChannelToGoodsOfSupper) {
                        //neu don vi xuat khong co quyen gan kenh
                        if (impChannelTypeId == null || impChannelTypeId.equals("")) {
                            impChannelTypeId = "-1";
                        }
                    } else {
                        //neu don vi xuat co quyen gan kenh
                        if (fromShop != null && fromShop.getShopId() != null && toShop != null && toShop.getShopId() != null) {
                            //neu xuat nhap giua cac cap
                            checkRoleAssignChannelToGoodsOfUnder = CommonDAO.checkRoleAssignChannelToGoods(this.getSession(), toShop.getShopId(), true);
                        }

                        if (!checkRoleAssignChannelToGoodsOfUnder) {
                            if (impChannelTypeId == null || impChannelTypeId.equals("")) {
                                impChannelTypeId = "-1";
                            }
                        }
                    }

                    String tableName = baseStockDAO.getTableNameByStockType(model.getStockTypeId(), BaseStockDAO.NAME_TYPE_HIBERNATE);
//                    String SQL_SELECT = "select serial from " + tableName + " where  status = " + Constant.STATUS_USE + " and to_number(serial) >= ? " + "and to_number(serial) <= ? and ownerType= ? and ownerId =?  and stockModelId= ? and stateId= ? and (checkDial= ? or checkDial is null) order by to_number(serial) asc";
                    //tamdt1, 10/09, start them vao phuc vu viec ban hang theo kenh
                    //thay the cau lenh o tren
                    String SQL_SELECT = "select serial from " + tableName + " "
                            + "where    status = ? "
                            + "         and to_number(serial) >= to_number(?) "
                            + "         and to_number(serial) <= to_number(?) "
                            + "         and ownerType = ? "
                            + "         and ownerId = ? "
                            + "         and stockModelId = ? "
                            + "         and stateId = ? "
                            + "         and (checkDial= ? or checkDial is null) ";


                    /* R2265_TrongLV_check khong cho chon the cao co trang thai cho huy */
                    if (ConfigParam.CHECK_CANCEL_SALE_TRANS_CARD) {
                        if (Constant.STOCK_CARD.equals(model.getStockTypeId())) {
                            if (serialGoods.getPurposeInputSerial() != null
                                    && (serialGoods.getPurposeInputSerial().equals(Constant.PURPOSE_INPUT_SERIAL_SALE_TO_RETAIL)
                                    || serialGoods.getPurposeInputSerial().equals(Constant.PURPOSE_INPUT_SERIAL_SALE_TO_PROMOTION)
                                    || serialGoods.getPurposeInputSerial().equals(Constant.PURPOSE_INPUT_SERIAL_EXP_TO_STOCK_TO_CHANNEL)
                                    || serialGoods.getPurposeInputSerial().equals(Constant.PURPOSE_INPUT_SERIAL_SALE))) {
                                System.out.println("nhap serial de ban hang");
//                                tannh20181508: bo dieu kien activestatus != 5 . Vi khi huy giao dich se cap nhat activeStatus =5
                                SQL_SELECT += " and (activeStatus  is null or (activeStatus != " + Constant.ACTIVE_STATUS_CARD_DEACTIVE_FAIL + ")) ";
                            } else {
                                System.out.println("nhap serial de xuat kho");
                                System.out.println(serialGoods.getStockTransId());
                                /* 
                                 * neu xuat cho dai ly dang shop thi bo sung check active_status 
                                 * dua vao stock_trans_id, kiem tra owner_id+owner_type
                                 */
                                boolean isInVt = true;//mac dinh la xuat nhap voi kenh
                                if (serialGoods.getStockTransId() != null) {
                                    StockTransDAO stockTransDAO = new StockTransDAO();
                                    StockTrans stockTrans = stockTransDAO.findById(serialGoods.getStockTransId());
                                    if (stockTrans != null) {
                                        if (!isInVT(stockTrans.getToOwnerId(), stockTrans.getToOwnerType())) {
                                            isInVt = false;
                                        }
                                    }
                                }
                                if (!isInVt) {//neu xuat nhap voi kenh 
//                                     tannh20181508: bo dieu kien activestatus != 5 . Vi khi huy giao dich se cap nhat activeStatus =5
                                    SQL_SELECT += " and (activeStatus  is null or (activeStatus != " + Constant.ACTIVE_STATUS_CARD_DEACTIVE_FAIL + ")) ";
                                }
                            }
                        }
                    }

                    //Kiem tra hang hoa co quan ly theo kenh hay khong
                    boolean IS_STOCK_CHANNEL = this.checkStockChannel(model.getStockModelId());


                    if (fromShop != null && toShop != null && fromShop.getShopId() != null && toShop.getShopId() != null) {
                        //Truong hop xuat tra cap tren
                        if (fromShop.getParentShopId() != null && fromShop.getParentShopId().equals(toShop.getShopId())) {
                            IS_STOCK_CHANNEL = false;
                        }
                    } else if (fromStaff != null && toShop != null && fromStaff.getShopId() != null && toShop.getShopId() != null) {
                        //nv xuat tra hang
                        IS_STOCK_CHANNEL = false;
                    }

                    if (IS_STOCK_CHANNEL) {
                        if (impChannelTypeId != null && !impChannelTypeId.equals("-5")) {//fix = -5 : tra doi tac
                            if (serialGoods.getPurposeInputSerial() == null || !serialGoods.getPurposeInputSerial().equals(Constant.PURPOSE_INPUT_SERIAL_SALE_TO_PROMOTION)) {

                                if (!checkRoleAssignChannelToGoodsOfSupper) {
                                    SQL_SELECT += "and channelTypeId in (:impChannelTypeId) ";
                                }
//                                if (checkRoleAssignChannelToGoodsOfSupper) {
//                                    if (impChannelTypeId != null && !impChannelTypeId.trim().equals("")) {
//                                        SQL_SELECT += "and (channelTypeId in (" + impChannelTypeId + ") or channelTypeId is null) ";
//                                    }
//                                } else {
//                                    SQL_SELECT += "and channelTypeId in (" + impChannelTypeId + ") ";
//                                }
                            }
                        }
                    }

                    SQL_SELECT += "order by to_number(serial) asc";
                    //tamdt1, 10/09, end
                    // tutm1 12/03/2012 chuyet tu setParameter sang setLong, setString
                    Query q = getSession().createQuery(SQL_SELECT);
                    q.setParameter(0, Constant.STATUS_USE);
                    q.setParameter(1, lFromSerial);
                    q.setParameter(2, lToSerial);
                    q.setParameter(3, serialGoods.getOwnerType());
                    q.setParameter(4, serialGoods.getOwnerId());
                    q.setParameter(5, serialGoods.getStockModelId());
                    q.setParameter(6, serialGoods.getStateId());
                    //Mac dinh chi tim nhung serial khong can boc tham
                    q.setParameter(7, Constant.UN_CHECK_DIAL);

                    if (IS_STOCK_CHANNEL) {
                        if (impChannelTypeId != null && !impChannelTypeId.equals("-5")) {//fix = -5 : tra doi tac
                            if (serialGoods.getPurposeInputSerial() == null || !serialGoods.getPurposeInputSerial().equals(Constant.PURPOSE_INPUT_SERIAL_SALE_TO_PROMOTION)) {
                                // tutm1 12/03/2012 doi mang string thanh mang long
                                String[] channelTypes = impChannelTypeId.split(",");
                                int len = channelTypes.length;
                                Long[] channelTypesL = new Long[len];
                                for (int j = 0; j < len; j++) {
                                    channelTypesL[j] = Long.valueOf(channelTypes[j]);
                                }

                                if (!checkRoleAssignChannelToGoodsOfSupper) {
                                    q.setParameterList("impChannelTypeId", channelTypesL);
                                }
                            }
                        }
                    }

                    int maxResult = quantity.intValue() - total.intValue();
                    q.setMaxResults(maxResult);
                    //q.setMaxResults(quantity.intValue() - total.intValue());
                    //q.setMaxResults(11);
                    List<String> lstResult = q.list();
                    if (lstResult.size() == 0) {
//                        req.setAttribute("returnMsg", "Không tìm thấy serial phù hợp");
                        req.setAttribute("returnMsg", "ERR.STK.067");
                        return pageFoward;
                    }
//tao id gia, muc dich de remove

                    Long maxId = 0L;
                    for (int i = 0; i < listStockTransSerials.size(); i++) {
                        if (listStockTransSerials.get(i).getStockTransSerialId().compareTo(maxId) > 0) {
                            maxId = listStockTransSerials.get(i).getStockTransSerialId();
                        }

                    }
                    List<StockTransSerial> tmpList = getListStockTransSerials(lstResult);
                    for (int i = 0; i < tmpList.size(); i++) {
                        StockTransSerial tmpStockTransSerial = tmpList.get(i);
                        tmpStockTransSerial.setStockTransSerialId(++maxId);
                        listStockTransSerials.add(tmpStockTransSerial);
                    }

                    serialGoods.setTotal(getListSize(listStockTransSerials));

                } else {
//                    req.setAttribute("returnMsg", "Chưa nhập serial");
                    req.setAttribute("returnMsg", "ERR.STK.068");
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("returnMsg", "ERR.STK.069");
        }
        log.info("# End method searchListSerial");
        return pageFoward;
    }

    private Long getListSize(List<StockTransSerial> listStockTransSerials) {
        Long count = 0L;
        if (listStockTransSerials == null) {
            return 0L;
        }
        for (StockTransSerial stockTransSerial : listStockTransSerials) {
            count += stockTransSerial.getQuantity();
        }
        return count;
    }
    /*
     * s
     * Author: ThanhNC Date created:05/06/2009 Purpose: Xuat chi tiet serial
     * xuat kho ra file excel
     */

    public String exportSerial() throws Exception {
        log.info("# Begin method StockCommonDAO.exportSerial");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        String pageFoward = "inputSerial";
        String isView = req.getParameter("isView");
        req.setAttribute("isView", isView);

        //Truong hop xuat chi tiet serial tu form xem kho (chi tiet serial)
        String exportViewDetail = req.getParameter("exportViewDetail");

        if (exportViewDetail != null && !"".equals(exportViewDetail.trim())) {
            pageFoward = "viewDetailSerial";
        }

        try {
            List<StockTransSerial> listStockTransSerials = new ArrayList<StockTransSerial>();
            if (isView != null && isView.equals("true")) {
                Long stockTransId = serialGoods.getStockTransId();
                if (stockTransId != null) {
                    StockTransSerialDAO stockTransSerialDAO = new StockTransSerialDAO();
                    stockTransSerialDAO.setSession(this.getSession());
                    listStockTransSerials = stockTransSerialDAO.findByStockTransAndStockModel(stockTransId, serialGoods.getStockModelId());
                }

            } else {
                listStockTransSerials = getRangeSerial(serialGoods.getOwnerType(), serialGoods.getOwnerId(),
                        serialGoods.getStockTypeId(), serialGoods.getStockModelId(), serialGoods.getStateId(), Constant.STATUS_USE);
            } //Truong hop view detail serial sau khi da nhap chi tiet serial --> lay gia tri tren session
            if (listStockTransSerials == null || listStockTransSerials.size() == 0) {
                listStockTransSerials = (List<StockTransSerial>) req.getSession().getAttribute("lstSerial" + pageId);
            }
            if (listStockTransSerials == null || listStockTransSerials.size() == 0) {
//                req.setAttribute("returnMsg", "Danh sách serial rỗng");
                req.setAttribute("returnMsg", "ERR.STK.070");
                return pageFoward;
            }
            req.setAttribute("lstSerial", listStockTransSerials);

            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

            String templatePath = "";
            //Giao dich xuat

            templatePath =
                    tmp + "Detail_serial.xls";
            filePath +=
                    "Detail_serial_" + userToken.getLoginName() + "_" + date + ".xls";

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            //set nguoi tao
            beans.put("userCreate", userToken.getFullName());

            beans.put("ownerName", serialGoods.getOwnerName());
            beans.put("stockModelName", serialGoods.getStockModelName());
            if (Constant.STATE_NEW.equals(serialGoods.getStateId())) {
//                beans.put("state", "Hàng mới");
                beans.put("state", getText("MSG.STK.007"));
            }

            if (Constant.STATE_OLD.equals(serialGoods.getStateId())) {
//                beans.put("state", "Hàng cũ");
                beans.put("state", getText("MSG.STK.008"));
            }

            if (Constant.STATE_DAMAGE.equals(serialGoods.getStateId())) {
//                beans.put("state", "Hàng hỏng");
                beans.put("state", getText("MSG.STK.009"));
            }

            int total = 0;
            Long stt = 1L;

            for (StockTransSerial stockTransSerial : listStockTransSerials) {
                total += stockTransSerial.getQuantity();
                stockTransSerial.setStt(stt++);
            }

            beans.put("totalReq", total);

            beans.put("listStockTransSerials", listStockTransSerials);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            serialGoods.setExportUrl(filePath);
        } catch (Exception ex) {
            ex.printStackTrace();
//            req.setAttribute("returnMsg", "Có lỗi khi export dữ liệu! File mẫu không tồn tại hoặc đường dẫn file không đúng.");
            req.setAttribute("returnMsg", "ERR.STK.071");
        }

        log.info("# End method StockCommonDAO.exportSerial");
        return pageFoward;
    }

    /**
     *
     * author tamdt1 date: 23/04/2009 tach dai list serial dau vao: 1 tap cac so
     * serrial dau ra : list cac cap diem dau - diem cuoi lien dai
     *
     */
    public List<StockTransSerial> getListStockTransSerials(
            List<String> listSerials) {
        List<StockTransSerial> listStockTransSerials = new ArrayList<StockTransSerial>();
        if (listSerials == null || listSerials.size() == 0) {
            return listStockTransSerials;
        }
        StockTransSerial stockTransSerial = new StockTransSerial();
        int listSize = listSerials.size();
        BigInteger fromSerial = new BigInteger(listSerials.get(0));
        BigInteger toSerial = new BigInteger(listSerials.get(listSize - 1));
        BigInteger num = toSerial.subtract(fromSerial);
        if (num.intValue() == (listSize - 1)) {
            stockTransSerial.setFromSerial(listSerials.get(0));
            stockTransSerial.setToSerial(listSerials.get(listSize - 1));
            stockTransSerial.setQuantity((long) listSize);
            listStockTransSerials.add(stockTransSerial);
            return listStockTransSerials;
        }
        BigInteger beforeSerial;
        BigInteger currentSerial;
        currentSerial = new BigInteger(listSerials.get(0));
        beforeSerial = currentSerial;
        stockTransSerial.setFromSerial(String.valueOf(currentSerial));
        for (int i = 1; i < listSerials.size(); i++) {
            currentSerial = new BigInteger(listSerials.get(i));
            if (!currentSerial.equals(beforeSerial.add(BigInteger.ONE))) {
                //neu khong lien ke nhau, luu tru doi tuong hien co
                stockTransSerial.setToSerial(String.valueOf(beforeSerial));
                BigInteger from = new BigInteger(stockTransSerial.getFromSerial());
                BigInteger to = new BigInteger(stockTransSerial.getToSerial());
                long quantity = to.subtract(from).longValue() + 1;
                stockTransSerial.setQuantity(quantity);
                listStockTransSerials.add(stockTransSerial);
                //tao ra doi tuong stockTransSerial moi de luu tru diem dau + diem cuoi
                stockTransSerial =
                        new StockTransSerial();
                stockTransSerial.setFromSerial(String.valueOf(currentSerial));
            }
            beforeSerial = currentSerial;
        }
        stockTransSerial.setToSerial(String.valueOf(beforeSerial));
        BigInteger from = new BigInteger(stockTransSerial.getFromSerial());
        BigInteger to = new BigInteger(stockTransSerial.getToSerial());
        long quantity = to.subtract(from).longValue() + 1;
        //long quantity = Long.parseLong(stockTransSerial.getToSerial()) - Long.parseLong(stockTransSerial.getFromSerial()) + 1;
        stockTransSerial.setQuantity(quantity);
        listStockTransSerials.add(stockTransSerial);
        return listStockTransSerials;
    }

    /**
     *
     * author tamdt1 date: 23/04/2009 xoa dai serial trong cac dai da chon (xoa
     * tren bien session)
     *
     */
    public String deleteSerialRange() throws Exception {
        log.info("# Begin method deleteSerialRange");
        HttpServletRequest req = getRequest();
        String pageFoward = "inputSerial";
        String pageId = req.getParameter("pageId");
        String strSelectedRangeId = req.getParameter("selectedRangeId");




        if (strSelectedRangeId != null) {
            Long total = this.serialGoods.getTotal();

            Long rangeId = Long.valueOf(strSelectedRangeId);
            List<StockTransSerial> listStockTransSerials = (List<StockTransSerial>) req.getSession().getAttribute("lstSerial" + pageId);




            for (int i = 0; i
                    < listStockTransSerials.size(); i++) {
                StockTransSerial tmpStockTransSerial = listStockTransSerials.get(i);




                if (tmpStockTransSerial.getStockTransSerialId().equals(rangeId)) {
                    BigInteger begin = new BigInteger(tmpStockTransSerial.getFromSerial());
                    BigInteger end = new BigInteger(tmpStockTransSerial.getToSerial());
                    total =
                            total - (end.subtract(begin).longValue() + 1);




                    this.serialGoods.setTotal(total);
                    listStockTransSerials.remove(i);




                    break;





                }


            }
        }

        log.info("# End method deleteSerialRange");




        return pageFoward;




    }


    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: Nhap serial cho mat
     * hang can xuat kho
     */
    public String addSerial() throws Exception {
        Session session = getSession();
        HttpServletRequest req = getRequest();
        String pageFoward = "addSerial";
        SerialGoods frm = getSerialGoods();
        String pageId = req.getParameter("pageId");

        Long stockModelId = frm.getStockModelId();
        Long stockTypeId = frm.getStockTypeId();
        Long stateId = frm.getStateId();
        Long parentId = frm.getParentId();
        if (stockModelId == null || stateId == null || stockTypeId == null) {
//            req.setAttribute("returnMsg", "Hàng hoá không tồn tại trên hệ thống");
            req.setAttribute("returnMsg", "ERR.STK.059");
            pageFoward = "inputSerial";
            return pageFoward;
        }
        try {
            List<StockTransSerial> listStockTransSerials = (List<StockTransSerial>) req.getSession().getAttribute("lstSerial" + pageId);
            if (listStockTransSerials == null || listStockTransSerials.size() == 0) {
//                req.setAttribute("returnMsg", "Chưa nhập chi tiết serial");
                req.setAttribute("returnMsg", "ERR.STK.060");
                pageFoward = "inputSerial";
                return pageFoward;
            }

            if (frm.getPurposeInputSerial() != null
                    && (frm.getPurposeInputSerial().equals(Constant.PURPOSE_INPUT_SERIAL_SALE_TO_RETAIL)
                    || frm.getPurposeInputSerial().equals(Constant.PURPOSE_INPUT_SERIAL_SALE_TO_PUNISH_STAFF))) {
                List<SaleTransDetailV2Bean> lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
                if (stockModelId != null) {
                    for (SaleTransDetailV2Bean bean : lstGoods) {
                        if (bean.getStockModelId().equals(stockModelId)) {
                            Long quantityInList = getListSize(listStockTransSerials);
                            if (!bean.getQuantity().equals(quantityInList)) {
                                req.setAttribute("returnMsg", "ERR.STK.061");
                                pageFoward = "inputSerial";
                                return pageFoward;
                            }
                            bean.setLstSerial(listStockTransSerials);
                            break;
                        }
                    }
                } else {
                    req.setAttribute("returnMsg", "ERR.STK.062");
                    req.getSession().removeAttribute("lstSerial" + pageId);
                    log.info("# End method addSerial");
                    return pageFoward;
                }

                req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
                req.getSession().setAttribute("editable" + pageId, serialGoods.getEditable());
                req.getSession().setAttribute("reasonIdRetail", serialGoods.getReasonId());
                req.getSession().setAttribute("otpVipCustomer", serialGoods.getOtp());
                req.getSession().removeAttribute("lstSerial" + pageId);
                req.setAttribute("returnMsg", "ERR.STK.063");
                return pageFoward;
            }

            List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
            if (stockModelId != null) {
                StockTransFull stockTransFull = null;
                //tannh20180111 ham luu serial vao bang trans serial order
                if (lstGoods != null && lstGoods.size() > 0) {
                    for (int i = 0; i < lstGoods.size(); i++) {
                        stockTransFull = (StockTransFull) lstGoods.get(i);
                        if (stockTransFull.getStockTransId() != null && stockTransFull.getStockTransId().intValue() > 0) {
                            if (stockTransFull.getStockTypeId().equals(stockTypeId) && stockTransFull.getStockModelId().equals(stockModelId) && stockTransFull.getStateId().equals(stateId)) {
                                Long quantityInList = getListSize(listStockTransSerials);
                                if (!stockTransFull.getQuantity().equals(quantityInList)) {
//                                req.setAttribute("returnMsg", "Số lượng serial nhập không đủ số lượng yêu cầu");
                                    req.setAttribute("returnMsg", "ERR.STK.061");
                                    pageFoward = "inputSerial";
                                    return pageFoward;
                                }
                                stockTransFull.setLstSerial(listStockTransSerials);
                            }
                        } else {
                            if (parentId != null && parentId.intValue() > 0) {
                                if (stockTransFull.getStockModelId().equals(parentId)) {
                                    List lstBean = stockTransFull.getLstSaleServiceDetail();
                                    if (null != lstBean) {
                                        for (int index = 0; index
                                                < lstBean.size(); index++) {
                                            SaleTransDetailBean stockTransFull_1 = (SaleTransDetailBean) lstBean.get(index);
                                            if (stockTransFull_1.getStockModelId().equals(stockModelId)) {
                                                Long quantityInList = getListSize(listStockTransSerials);
                                                if (!stockTransFull_1.getQuantity().equals(quantityInList)) {
//                                                req.setAttribute("returnMsg", "Số lượng serial nhập không đủ số lượng yêu cầu");
                                                    req.setAttribute("returnMsg", "ERR.STK.061");
                                                    pageFoward = "inputSerial";
                                                    return pageFoward;
                                                }
                                                stockTransFull_1.setLstSerial(listStockTransSerials);
                                                break;
                                            }
                                        }
                                    }
                                    break;
                                }
                            } else if (stockTransFull.getStockModelId().equals(stockModelId) && stockTransFull.getStateId().equals(stateId)) {
                                Long quantityInList = getListSize(listStockTransSerials);
                                if (!stockTransFull.getQuantity().equals(quantityInList)) {
//                                req.setAttribute("returnMsg", "Số lượng serial nhập không đủ số lượng yêu cầu");
                                    req.setAttribute("returnMsg", "ERR.STK.061");
                                    pageFoward = "inputSerial";
                                    return pageFoward;
                                }
                                stockTransFull.setLstSerial(listStockTransSerials);
                                if (serialGoods.getIsRevokeGoodsAction()) {
                                    String tmpTableName = new BaseStockDAO().getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
                                    Double tmpTotalDepositPrice = 0.0;
                                    for (StockTransSerial tmpStockTransSerial : listStockTransSerials) {
                                        String tmpFromSerial = tmpStockTransSerial.getFromSerial();
                                        String tmpToSerial = tmpStockTransSerial.getToSerial();

                                        StringBuilder tmpQueryTotalDepositPrice = new StringBuilder("");
                                        tmpQueryTotalDepositPrice.append("select    sum(nvl(deposit_price, 0)) ");
                                        tmpQueryTotalDepositPrice.append("from      ").append(tmpTableName).append(" ");
                                        tmpQueryTotalDepositPrice.append("where     to_number(serial) >= to_number(?) ");
                                        tmpQueryTotalDepositPrice.append("          and to_number(serial) <= to_number(?) ");
                                        tmpQueryTotalDepositPrice.append("          and stock_model_id = ? ");

                                        Query tmpQuery = getSession().createSQLQuery(tmpQueryTotalDepositPrice.toString());
                                        tmpQuery.setParameter(0, tmpFromSerial);
                                        tmpQuery.setParameter(1, tmpToSerial);
                                        tmpQuery.setParameter(2, stockModelId);

                                        Double tmpResult = Double.valueOf(tmpQuery.list().get(0).toString());
                                        tmpTotalDepositPrice += tmpResult;
                                    }
                                    stockTransFull.setTotalDepositPrice(tmpTotalDepositPrice);
                                    Double amount = 0.0;
                                    try {
                                        amount = Double.valueOf(req.getSession().getAttribute("amountDisplay" + pageId).toString());
                                    } catch (Exception ex) {
                                        amount = 0.0;
                                    }
                                    amount += tmpTotalDepositPrice;
                                    req.getSession().setAttribute("amountDisplay" + pageId, amount.toString());
                                }
                                break;
                            }
                        }
                    }


                } else {
                    //tannh2018011  ham luu serial vao bang trans serial order
//                   check da thuc su cap nhat serial hay chua 
                    if (checkAlreadlyHaveSerial(serialGoods.getSaleTransDetailId())) {
                        req.setAttribute("returnMsg", "ERR.STK.146");
                        pageFoward = "inputSerial";
                        return pageFoward;
                    }


                    if (listStockTransSerials != null && listStockTransSerials.size() > 0) {
                        StockTransSerial stockSerial = null;
                        for (int idx = 0; idx < listStockTransSerials.size(); idx++) {
                            stockSerial = (StockTransSerial) listStockTransSerials.get(idx);
                            long longfromSerial = Long.parseLong(stockSerial.getFromSerial().toString());
                            long longtoSerial = Long.parseLong(stockSerial.getToSerial().toString());
//                            for (long i = longfromSerial; i <= longtoSerial; i++) {
                            if (checkAlreadlyInputOtherTrans(longfromSerial + "")) {
                                req.setAttribute("returnMsg", "This from serial " + longfromSerial + " is in other order transaction.");
                                pageFoward = "inputSerial";
                                return pageFoward;
                            }
//                            }
                            if (checkAlreadlyInputOtherTrans(longtoSerial + "")) {
                                req.setAttribute("returnMsg", "This to serial " + longtoSerial + " is in other order transaction.");
                                pageFoward = "inputSerial";
                                return pageFoward;
                            }
                        }
                    }


                    saveSaleTransSerialOrder(listStockTransSerials, stockModelId, serialGoods.getSaleTransDetailId());
                    req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
                    req.getSession().setAttribute("editable" + pageId, serialGoods.getEditable());
                    req.getSession().setAttribute("reasonIdRetail", serialGoods.getReasonId());
                    req.getSession().setAttribute("otpVipCustomer", serialGoods.getOtp());
                    req.getSession().removeAttribute("lstSerial" + pageId);
//                  req.setAttribute("returnMsg", "Cập nhật serial thành công");
//                    req.setAttribute("resultViewSaleDetail", "ERR.STK.063");
//                    return "saleManagmentDetailOrder";
                    req.setAttribute("returnMsg", "ERR.STK.063");
                    pageFoward = "inputSerial";
                    return pageFoward;
                }


            } else {
//                req.setAttribute("returnMsg", "Không tìm thấy loại hàng cần cập nhật serial");
                req.setAttribute("returnMsg", "ERR.STK.062");
                req.getSession().removeAttribute("lstSerial" + pageId);
                log.info("# End method addSerial");
                return pageFoward;
            }

            req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
            req.getSession().setAttribute("editable" + pageId, serialGoods.getEditable());
            req.getSession().setAttribute("reasonIdRetail", serialGoods.getReasonId());
            req.getSession().setAttribute("otpVipCustomer", serialGoods.getOtp());
            req.getSession().removeAttribute("lstSerial" + pageId);
//            req.setAttribute("returnMsg", "Cập nhật serial thành công");
            req.setAttribute("returnMsg", "ERR.STK.063");
        } catch (Exception ex) {
            ex.printStackTrace();
            //getSession().clear();
            //getSession().getTransaction().rollback();
            //getSession().beginTransaction();
            pageFoward = "inputSerial";
//            req.setAttribute("returnMsg", "Có lỗi khi nhập dữ liệu");
            req.setAttribute("returnMsg", "ERR.STK.069");
        }
        return pageFoward;
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: Nhap serial cho mat
     * hang can xuat kho
     */
    public String refreshListGoods() throws Exception {
        HttpServletRequest req = getRequest();
        String pageFoward = "listGoods";
        String pageId = req.getParameter("pageId");
        req.setAttribute("inputSerial", "true");
        String editable = (String) req.getSession().getAttribute("editable" + pageId);
        goodsForm.setEditable(editable);
        //exportStockForm.setAmountTax(10L);
        req.getSession().removeAttribute("editable" + pageId);


        return pageFoward;




    }

    /*
     * Author: Vunt Date created: 25/09/2009 Purpose:
     */
    public String removeListGoods() throws Exception {
        HttpServletRequest req = getRequest();
        String pageFoward = "listGoodsRecover";
        String pageId = req.getParameter("pageId");
        String editable = (String) req.getSession().getAttribute("editable" + pageId);
        goodsForm.setEditable(editable);
        req.getSession().removeAttribute("lstGoods" + pageId);
        //exportStockForm.setAmountTax(10L);
        req.getSession().removeAttribute("editable" + pageId);




        return pageFoward;




    }
    /*
     * Author: Vunt Date created: 25/09/2009 Purpose: Nhap serial cho mat hang
     * thu hoi
     */

    public String refreshListGoodsRecover() throws Exception {
        HttpServletRequest req = getRequest();
        String pageFoward = "listGoodsRecover";
        //List lstGoods = (List) req.getSession().getAttribute("lstGoods");        
        //CalcSumDeposit(lstGoods);
        String pageId = req.getParameter("pageId");
        req.setAttribute("inputSerial" + pageId, "true");
        String editable = (String) req.getSession().getAttribute("editable" + pageId);
        goodsForm.setEditable(editable);
        //exportStockForm.setAmountTax(10L);
        req.getSession().removeAttribute("editable" + pageId);




        return pageFoward;




    }

    /*
     * Author: Vunt Date created: 26/02/2009 Purpose: Nhap serial cho mat hang
     * can xuat kho CTV
     */
    public String refreshListGoodsColl() throws Exception {
        HttpServletRequest req = getRequest();
        String pageFoward = "listGoods";
        String pageId = req.getParameter("pageId");
        //List lstGoods = (List) req.getSession().getAttribute("lstGoods");
        req.setAttribute("collaborator", "coll");
        //CalcSumDeposit(lstGoods);
        req.setAttribute("inputSerial", "true");
        String editable = (String) req.getSession().getAttribute("editable" + pageId);
        goodsForm.setEditable(editable);
        //exportStockForm.setAmountTax(10L);
        req.getSession().removeAttribute("editable" + pageId);





        return pageFoward;




    }

    /*
     * Author: LamNV Date created: 26/02/2009 Purpose: Nhap serial cho mat hang
     * can thu hoi tu kho CTV
     */
    public String refreshListGoodsRevokeColl() throws Exception {
        HttpServletRequest req = getRequest();
        String pageFoward = "listGoods";
        String pageId = req.getParameter("pageId");
        //List lstGoods = (List) req.getSession().getAttribute("lstGoods");
        req.setAttribute("revokeColl", "true");
        //CalcSumDeposit(lstGoods);
        req.setAttribute("inputSerial", "true");
        String editable = (String) req.getSession().getAttribute("editable" + pageId);
        goodsForm.setEditable(editable);
        //exportStockForm.setAmountTax(10L);
        req.getSession().removeAttribute("editable" + pageId);
        req.setAttribute("revokeColl", "true");
        return pageFoward;
    }

    /*
     * Author: ThanhNC DateCreate: 01/04/2009 Purpose: Prepare page xem kho
     */
    public String prepareViewStockDetail() throws Exception {
        log.info("# Begin method prepareViewStockDetail");
        String pageForward = "viewStockDetail";

        HttpServletRequest req = getRequest();
        //req.getSession().removeAttribute("lstStockGoods");
        try {
            if (goodsForm == null) {
                goodsForm = new GoodsForm();
            }

            String ownerType = (String) QueryCryptUtils.getParameter(req, "ownerType");
            String owner = (String) QueryCryptUtils.getParameter(req, "ownerId");
            String stockModel = (String) QueryCryptUtils.getParameter(req, "stockModelId");
            //TrongLV
            //Trong hop stockModelId la 1 String co dang: '0:stockModelId' hoac '1:SaleServiceId'
            if (null != stockModel && stockModel.split(":").length == 2) {
                if (stockModel.split(":")[0].trim().equals("0")) {
                    stockModel = stockModel.split(":")[1];
                } else {
                    stockModel = null;
                }
            }

            //Tim theo ma CTV
            String ownerCode = (String) QueryCryptUtils.getParameter(req, "ownerCode");
            if (ownerCode != null && !ownerCode.trim().equals("")) {
                if (ownerType != null && ownerType.trim().equals(Constant.OBJECT_TYPE_SHOP)) {
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());
                    List<Shop> shopList = shopDAO.findByPropertyWithStatus("shopCode", ownerCode.trim(), Constant.STATUS_USE);
                    if (shopList != null && !shopList.isEmpty()) {
                        owner = shopList.get(0).getShopId().toString();
                    }
                } else if (ownerType != null && ownerType.trim().equals(Constant.OBJECT_TYPE_STAFF)) {
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(this.getSession());
                    List<Staff> staffList = staffDAO.findByPropertyAndStatus("staffCode", ownerCode.trim(), Constant.STATUS_USE);
                    if (staffList != null && !staffList.isEmpty()) {
                        owner = staffList.get(0).getStaffId().toString();
                    }
                }
            }

            //Tim theo ma mat hang
            String stockModelCode = (String) QueryCryptUtils.getParameter(req, "stockModelCode");
            if (stockModelCode != null && !stockModelCode.trim().equals("")) {
                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(this.getSession());
                List<StockModel> stockModelList = stockModelDAO.findByPropertyWithStatus("stockModelCode", stockModelCode.trim(), Constant.STATUS_USE);
                if (stockModelList != null && !stockModelList.isEmpty()) {
                    stockModel = stockModelList.get(0).getStockModelId().toString();
                }
            }
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String viewType = (String) QueryCryptUtils.getParameter(req, "viewType");
            if (viewType != null && viewType.equals("normal")) {
                goodsForm.setViewType(viewType);
                pageForward = "viewStockDetailNormal";
            }
            Long ownerTypeId = 0L;
            Long ownerId = 0L;
            Long stockModelId = 0L;
            if (ownerType != null && !"".equals(ownerType)) {
                ownerTypeId = Long.parseLong(ownerType);
            }

            if (owner != null && !"".equals(owner)) {
                ownerId = Long.parseLong(owner);
            }

            if (stockModel != null && !"".equals(stockModel)) {
                stockModelId = Long.parseLong(stockModel);
                goodsForm.setStockModelId(stockModelId);
            } //Truong hop xem kho nhan vien

            if (ownerTypeId != null && ownerTypeId == Constant.OWNER_TYPE_STAFF && (ownerId == null || ownerId <= 0)) {
                ownerId = userToken.getUserID();
            } //Truong hop xem kho cua hang

            if (ownerTypeId != null && ownerTypeId == Constant.OWNER_TYPE_SHOP && (ownerId == null || ownerId <= 0)) {
                if (userToken.getShopId() != null) {
                    ownerId = userToken.getShopId();
                } else {
                    return pageForward;
                }
            }

            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            Object obj = baseStockDAO.getStockByTypeAndId(ownerTypeId, ownerId);
            if (obj instanceof Shop) {
                Shop shop = (Shop) obj;
                goodsForm.setOwnerId(ownerId);
                goodsForm.setOwnerType(ownerTypeId);
                goodsForm.setOwnerCode(shop.getShopCode());
                goodsForm.setOwnerName(shop.getName());
            }

            if (obj instanceof Staff) {
                Staff staff = (Staff) obj;
                goodsForm.setOwnerId(ownerId);
                goodsForm.setOwnerType(ownerTypeId);
                goodsForm.setOwnerCode(staff.getStaffCode());
                goodsForm.setOwnerName(staff.getName());
            }
            Query q = null;
            String SQL_SELECT_STOCK_TYPE = " from StockType where status= ? and stockTypeId in " + " ( select stockTypeId from StockModel where status= ? and stockModelId in " + " (select id.stockModelId from StockTotal where status = ? and id.ownerType= ? and id.ownerId =? )" + " ) ";
            if (stockModelId != null && !stockModelId.equals(0L)) {
                SQL_SELECT_STOCK_TYPE = " from StockType where status= ? and stockTypeId in " + " ( select stockTypeId from StockModel where status= ? and stockModelId = ?)";
                q = getSession().createQuery(SQL_SELECT_STOCK_TYPE);
                q.setParameter(0, Constant.STATUS_USE);
                q.setParameter(1, Constant.STATUS_USE);
                q.setParameter(2, stockModelId);
            }

            if (stockModelId == null || stockModelId.equals(0L)) {
                q = getSession().createQuery(SQL_SELECT_STOCK_TYPE);
                q.setParameter(0, Constant.STATUS_USE);
                q.setParameter(1, Constant.STATUS_USE);
                q.setParameter(2, Constant.STATUS_USE);
                q.setParameter(3, ownerTypeId);
                q.setParameter(4, ownerId);
            }

            List<StockType> lstStockType = q.list();
            List<StockTotalFull> lstStockGoods = null;
            for (StockType stockType : lstStockType) {
                if (stockModelId != null && !stockModelId.equals(0L)) {
                    lstStockGoods = findStockTotalFullByStockModel(ownerTypeId, ownerId, stockModelId);
                } else {
                    lstStockGoods = findStockTotalFull(ownerTypeId, ownerId, stockType.getStockTypeId());
                }
                stockType.setListStockDetail(lstStockGoods);
            } //req.getSession().setAttribute("lstStockGoods", lstStockType);
            req.setAttribute("lstStockGoods", lstStockType);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        req.setAttribute("collaborator", "coll");
        log.info("# End method prepareViewStockDetail");
        return pageForward;
    }

    /*
     * Author: Vunt DateCreate: 01/04/2009 Purpose: Prepare page xem thu hoi
     * hang dat coc dai ly
     */
    public String prepareViewStockDetailRecover() throws Exception {
        log.info("# Begin method prepareViewStockDetail");
        String pageForward = "viewStockDetail";

        HttpServletRequest req = getRequest();
        //req.getSession().removeAttribute("lstStockGoods");




        try {
            if (goodsForm == null) {
                goodsForm = new GoodsForm();




            }

            String ownerType = (String) QueryCryptUtils.getParameter(req, "ownerType");
            String owner;
            String ownerCode = (String) QueryCryptUtils.getParameter(req, "ownerCode");
            owner = getShopIdbyShopCode(ownerCode);

            String stockModel = (String) QueryCryptUtils.getParameter(req, "stockModelIdGet");
            //TrongLV
            //Trong hop stockModelId la 1 String co dang: '0:stockModelId' hoac '1:SaleServiceId'




            if (null != stockModel && stockModel.split(":").length == 2) {
                if (stockModel.split(":")[0].trim().equals("0")) {
                    stockModel = stockModel.split(":")[1];




                } else {
                    stockModel = null;




                }
            }


            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String viewType = (String) QueryCryptUtils.getParameter(req, "viewType");




            if (viewType != null && viewType.equals("normal")) {
                goodsForm.setViewType(viewType);
                pageForward = "viewStockDetailNormal";




            }

            Long ownerTypeId = 0L;
            Long ownerId = 0L;
            Long stockModelId = 0L;





            if (ownerType != null && !"".equals(ownerType)) {
                ownerTypeId = Long.parseLong(ownerType);




            }

            if (owner != null && !"".equals(owner)) {
                ownerId = Long.parseLong(owner);




            }

            if (stockModel != null && !"".equals(stockModel)) {
                stockModelId = Long.parseLong(stockModel);
                goodsForm.setStockModelId(stockModelId);




            } //Truong hop xem kho nhan vien

            if (ownerTypeId != null && ownerTypeId == Constant.OWNER_TYPE_STAFF && (ownerId == null || ownerId <= 0)) {
                ownerId = userToken.getUserID();




            } //Truong hop xem kho cua hang

            if (ownerTypeId != null && ownerTypeId == Constant.OWNER_TYPE_SHOP && (ownerId == null || ownerId <= 0)) {
                if (userToken.getShopId() != null) {
                    ownerId = userToken.getShopId();




                } else {
                    return pageForward;




                }

            }

            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            Object obj = baseStockDAO.getStockByTypeAndId(ownerTypeId, ownerId);




            if (obj instanceof Shop) {
                Shop shop = (Shop) obj;
                goodsForm.setOwnerId(ownerId);
                goodsForm.setOwnerType(ownerTypeId);
                goodsForm.setOwnerCode(shop.getShopCode());
                goodsForm.setOwnerName(shop.getName());




            }

            if (obj instanceof Staff) {
                Staff staff = (Staff) obj;
                goodsForm.setOwnerId(ownerId);
                goodsForm.setOwnerType(ownerTypeId);
                goodsForm.setOwnerCode(staff.getStaffCode());
                goodsForm.setOwnerName(staff.getName());




            }

            Query q = null;
            String SQL_SELECT_STOCK_TYPE = " from StockType where status = ? and stockTypeId in " + " ( select stockTypeId from StockModel where status= ? and stockModelId in " + " (select id.stockModelId from StockTotal where status = ? and id.ownerType= ? and id.ownerId =? )" + " ) ";

            if (stockModelId != null && !stockModelId.equals(0L)) {
                SQL_SELECT_STOCK_TYPE = " from StockType where status= ? and stockTypeId in " + " ( select stockTypeId from StockModel where status= ? and stockModelId = ?)";

                q = getSession().createQuery(SQL_SELECT_STOCK_TYPE);
                q.setParameter(0, Constant.STATUS_USE);
                q.setParameter(1, Constant.STATUS_USE);
                q.setParameter(2, stockModelId);

            }


            if (stockModelId == null || stockModelId.equals(0L)) {

                q = getSession().createQuery(SQL_SELECT_STOCK_TYPE);

                q.setParameter(0, Constant.STATUS_USE);
                q.setParameter(1, Constant.STATUS_USE);

                q.setParameter(2, Constant.STATUS_USE);
                q.setParameter(3, ownerTypeId);
                q.setParameter(4, ownerId);
            }

            List<StockType> lstStockType = q.list();


            List<StockTotalFull> lstStockGoods = null;




            for (StockType stockType : lstStockType) {
                if (stockModelId != null && !stockModelId.equals(0L)) {
                    lstStockGoods = findStockTotalFullByStockModel(ownerTypeId, ownerId, stockModelId);




                } else {
                    lstStockGoods = findStockTotalFull(ownerTypeId, ownerId, stockType.getStockTypeId());




                }

                stockType.setListStockDetail(lstStockGoods);




            } //req.getSession().setAttribute("lstStockGoods", lstStockType);
            req.setAttribute("lstStockGoods", lstStockType);




        } catch (Exception ex) {
            ex.printStackTrace();




            throw ex;




        }
        req.setAttribute("collaborator", "coll");
        log.info("# End method prepareViewStockDetail");




        return pageForward;




    }

    public List<ImSearchBean> getListStockModel(ImSearchBean imSearchBean) {
        try {
            String SELECT_STOCK_MODEL = "select new com.viettel.im.client.bean.ImSearchBean(stockModelCode, name) from StockModel where status= ? and stockModelId in " + " (select id.stockModelId from StockTotal where status = ? and id.ownerType= ? and id.ownerId =? )" + " and lower(stockModelCode) like ? ";


            Query qSelectStockModel = getSession().createQuery(SELECT_STOCK_MODEL);
            qSelectStockModel.setParameter(0, Constant.STATUS_USE);
            String param = imSearchBean.getOtherParamValue();
            String[] arrParam = param.split(";");
            if (arrParam != null && arrParam.length > 1) {
                Long ownerType = Long.valueOf(arrParam[0]);
                Long ownerId = Long.valueOf(arrParam[1]);

                qSelectStockModel.setParameter(1, Constant.STATUS_USE);

                qSelectStockModel.setParameter(2, ownerType);
                qSelectStockModel.setParameter(3, ownerId);
                qSelectStockModel.setParameter(4, "%" + imSearchBean.getCode().toLowerCase() + "%");
                return qSelectStockModel.list();
            }
        } catch (Exception ex) {
            ex.printStackTrace();




        }
        return null;




    }
    /*
     * Author: ThanhNC Date: 14/04/2010 Purpose: Lay danh sach stockModel trong
     * chuc nang xu ly kho hang
     */

    public List<ImSearchBean> getListStockModelProcessGoods(ImSearchBean imSearchBean) {
        try {
            String SELECT_STOCK_MODEL = "select new com.viettel.im.client.bean.ImSearchBean(stockModelCode, name) "
                    + "from StockModel where status= ? and lower(stockModelCode) like ?  and stockTypeId in "
                    + " (select stockTypeId from StockType where status= ? and checkExp = ? )";
            Query qSelectStockModel = getSession().createQuery(SELECT_STOCK_MODEL);
            qSelectStockModel.setParameter(0, Constant.STATUS_USE);
            qSelectStockModel.setParameter(1, "%" + imSearchBean.getCode().toLowerCase() + "%");
            qSelectStockModel.setParameter(2, Constant.STATUS_USE);
            qSelectStockModel.setParameter(3, Constant.STOCK_TYPE_EXP);




            return qSelectStockModel.list();





        } catch (Exception ex) {
            ex.printStackTrace();




        }
        return null;




    }

    public List<ImSearchBean> getListShopOrStaff(ImSearchBean imSearchBean) {
        try {
            Long ownerType = 0L;
            UserToken userToken = (UserToken) getRequest().getSession().getAttribute("userToken");
            String param = imSearchBean.getOtherParamValue();
            String[] arrParam = param.split(";");




            if (arrParam != null && arrParam.length >= 1) {
                ownerType = Long.valueOf(arrParam[0]);




            }
            if (ownerType.equals(0L)) {
                return null;




            }
            List parameterList = new ArrayList();

            String SELECT_SHOP_STAFF = "select new com.viettel.im.client.bean.ImSearchBean(ownerCode, ownerName) from VShopStaff" + " where status= ? and id.ownerType = ? ";
            parameterList.add(Constant.STATUS_USE);
            parameterList.add(ownerType);




            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                SELECT_SHOP_STAFF += " and lower(ownerCode) like ? ";
                parameterList.add("%" + imSearchBean.getCode().toLowerCase().trim() + "%");




            }
            if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                SELECT_SHOP_STAFF += " and lower(ownerName) like ? ";
                parameterList.add("%" + imSearchBean.getName().toLowerCase().trim() + "%");




            }


            if (ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
                SELECT_SHOP_STAFF += " and (shopPath like ? ESCAPE '$' or id.ownerId=? ) ";
                parameterList.add("%$_" + userToken.getShopId() + "$_%");
                parameterList.add(userToken.getShopId());




            }
            if (ownerType.equals(Constant.OWNER_TYPE_STAFF)) {
                SELECT_SHOP_STAFF += " and parentShopId = ? ";
                parameterList.add(userToken.getShopId());




            }
            SELECT_SHOP_STAFF += " order by nlssort(ownerName,'nls_sort=Vietnamese') asc ";

            Query qSelectShopStaff = getSession().createQuery(SELECT_SHOP_STAFF);




            for (int i = 0; i
                    < parameterList.size(); i++) {
                qSelectShopStaff.setParameter(i, parameterList.get(i));




            }
            qSelectShopStaff.setMaxResults(300);




            return qSelectShopStaff.list();





        } catch (Exception ex) {
            ex.printStackTrace();




        }
        return null;




    }

    public String viewStockDetail() throws Exception {
        log.info("# Begin method viewStockDetail");
        String pageForward = "viewStockDetail";

        if (goodsForm.getViewType() != null && goodsForm.getViewType().equals("normal")) {
            pageForward = "viewStockDetailNormal";
            if (goodsForm.getOwnerType() != null && goodsForm.getOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
                pageForward = "viewStockStaffDetail";
            }
        }
        HttpServletRequest req = getRequest();
        // req.getSession().removeAttribute("lstStockGoods");
        // req.getSession().removeAttribute("lstSerial");
        try {

            Long ownerTypeId = 0L;
            Long ownerId = 0L;
            String ownerCode = "";
            Long stockModelId = 0L;
            if (goodsForm.getOwnerType() == null && goodsForm.getOwnerId() == null) {
                req.setAttribute("returnMsg", "stock.viewDetail.noOwnerId");
                return pageForward;
            }
            ownerTypeId = goodsForm.getOwnerType();
            //ownerId = goodsForm.getOwnerId();
            ownerCode = goodsForm.getOwnerCode();

            if (ownerCode == null || ownerCode.trim().equals("")) {
                req.setAttribute("returnMsg", "stock.viewDetail.noOwnerId");
                return pageForward;
            }

            if (ownerTypeId.equals(Constant.OWNER_TYPE_SHOP)) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                Shop shop = shopDAO.findShopsAvailableByShopCode(ownerCode);
                if (shop == null) {
                    req.setAttribute("returnMsg", "stock.viewDetail.noOwnerId");
                    return pageForward;
                }
                ownerId = shop.getShopId();
            }

            if (ownerTypeId.equals(Constant.OWNER_TYPE_STAFF)) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = staffDAO.findStaffAvailableByStaffCode(ownerCode);
                if (staff == null) {
                    req.setAttribute("returnMsg", "stock.viewDetail.noOwnerId");
                    return pageForward;
                }
                ownerId = staff.getStaffId();
            }
            goodsForm.setOwnerId(ownerId);
            //stockModelId = goodsForm.getStockModelId();
            String stockModelCode = goodsForm.getStockModelCode();

            if (stockModelCode != null && !stockModelCode.trim().equals("")) {
                String SELECT_STOCK_MODEL_BY_CODE = "from StockModel where status= ?  and lower(stockModelCode)= ? ";
                Query qSelectStockModel = getSession().createQuery(SELECT_STOCK_MODEL_BY_CODE);
                qSelectStockModel.setParameter(0, Constant.STATUS_USE);
                qSelectStockModel.setParameter(1, stockModelCode.toLowerCase());
                List lst = qSelectStockModel.list();
                if (lst.size() > 0) {
                    StockModel stockModel = (StockModel) lst.get(0);
                    stockModelId = stockModel.getStockModelId();
                    goodsForm.setStockModelId(stockModel.getStockModelId());
                }
            }

            String SELECT_STOCK_MODEL = "from StockModel where status = ? and stockModelId in " + " (select id.stockModelId from StockTotal where status = ? and id.ownerType= ? and id.ownerId =? )";
            Query qSelectStockModel = getSession().createQuery(SELECT_STOCK_MODEL);

            qSelectStockModel.setParameter(0, Constant.STATUS_USE);
            qSelectStockModel.setParameter(1, Constant.STATUS_USE);
            qSelectStockModel.setParameter(2, ownerTypeId);
            qSelectStockModel.setParameter(3, ownerId);

            req.setAttribute("lstStockModel", qSelectStockModel.list());

            Query q = null;
            String SQL_SELECT_STOCK_TYPE = " from StockType where status= ? and stockTypeId in " + " ( select stockTypeId from StockModel where status= ? and stockModelId in " + " (select id.stockModelId from StockTotal where status = ? and id.ownerType= ? and id.ownerId =? )" + " ) ";

            if (stockModelId != null && !stockModelId.equals(0L)) {
                SQL_SELECT_STOCK_TYPE = " from StockType where status= ? and stockTypeId in " + " ( select stockTypeId from StockModel where status= ? and stockModelId= ?)";
                q = getSession().createQuery(SQL_SELECT_STOCK_TYPE);

                q.setParameter(0, Constant.STATUS_USE);
                q.setParameter(1, Constant.STATUS_USE);
                q.setParameter(2, stockModelId);
            }

            if (stockModelId == null || stockModelId.equals(0L)) {
                q = getSession().createQuery(SQL_SELECT_STOCK_TYPE);

                q.setParameter(0, Constant.STATUS_USE);
                q.setParameter(1, Constant.STATUS_USE);

                q.setParameter(2, Constant.STATUS_USE);
                q.setParameter(3, ownerTypeId);
                q.setParameter(4, ownerId);
            }
            List<StockType> lstStockType = q.list();
            List<StockTotalFull> lstStockGoods = null;

            for (StockType stockType : lstStockType) {
                if (stockModelId != null && !stockModelId.equals(0L)) {
                    lstStockGoods = findStockTotalFullByStockModel(ownerTypeId, ownerId, stockModelId);
                } else {
                    lstStockGoods = findStockTotalFull(ownerTypeId, ownerId, stockType.getStockTypeId());
                }
                stockType.setListStockDetail(lstStockGoods);

            } // req.getSession().setAttribute("lstStockGoods", lstStockType);
            req.setAttribute("lstStockGoods", lstStockType);
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        log.info("# End method viewStockDetail");
        return pageForward;
    }

    /*
     * Author: ThanhNC DateCreate: 01/04/2009 Purpose: Xuat bao cao chi tiet kho
     */
    public String exportStockDetail() throws Exception {
        log.info("# Begin method viewStockDetail");
        String pageForward = "exportStockDetailNormal";
        HttpServletRequest req = getRequest();
        try {
            Long ownerTypeId = 0L;
            Long ownerId = 0L;
            String ownerCode = "";
            Long stockModelId = null;
            String stockModelCode = goodsForm.getStockModelCode();
            if (stockModelCode != null && !stockModelCode.equals("")) {
                StockModel model = getStockModelByCode(stockModelCode);
                if (model != null) {
                    stockModelId = model.getStockModelId();
                } else {
                    req.setAttribute("returnMsg", "Mã hàng không đúng");
                    return pageForward;
                }
            }

            if (goodsForm.getOwnerType() == null && goodsForm.getOwnerId() == null) {
                req.setAttribute("returnMsg", "stock.viewDetail.noOwnerId");
                return pageForward;
            }

            ownerTypeId = goodsForm.getOwnerType();
            ownerCode = goodsForm.getOwnerCode();
            if (ownerCode == null || ownerCode.trim().equals("")) {
                req.setAttribute("returnMsg", "stock.viewDetail.noOwnerId");
                return pageForward;
            }

            if (ownerTypeId.equals(Constant.OWNER_TYPE_SHOP)) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                Shop shop = shopDAO.findShopsAvailableByShopCode(ownerCode);
                if (shop == null) {
                    req.setAttribute("returnMsg", "stock.viewDetail.noOwnerId");
                    return pageForward;
                }
                ownerId = shop.getShopId();
            }

            if (ownerTypeId.equals(Constant.OWNER_TYPE_STAFF)) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = staffDAO.findStaffAvailableByStaffCode(ownerCode);
                if (staff == null) {
                    req.setAttribute("returnMsg", "stock.viewDetail.noOwnerId");
                    return pageForward;
                }

                ownerId = staff.getStaffId();
            }
            String SELECT_STOCK_MODEL = "from StockModel where status= ? and stockModelId in " + " (select id.stockModelId from StockTotal where status = ? and id.ownerType= ? and id.ownerId =? )";
            Query qSelectStockModel = getSession().createQuery(SELECT_STOCK_MODEL);

            qSelectStockModel.setParameter(0, Constant.STATUS_USE);
            qSelectStockModel.setParameter(1, Constant.STATUS_USE);
            qSelectStockModel.setParameter(2, ownerTypeId);
            qSelectStockModel.setParameter(3, ownerId);

            req.setAttribute("lstStockModel", qSelectStockModel.list());

            List<StockTotalFull> lstStockGoods = new ArrayList<StockTotalFull>();

            if (stockModelId == null || stockModelId.equals(0L)) {
                lstStockGoods = findStockTotalFullWithSerial(ownerTypeId, ownerId);
            } else {
                lstStockGoods = findStockTotalFullWithSerial(ownerTypeId, ownerId, stockModelId);
            }

            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            filePath +=
                    "StockSerialReport_" + userToken.getLoginName() + "_" + date + ".xls";

            String templatePath = tmp + "StockSerialReport.xls";

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            //set nguoi tao
            beans.put("ownerName", goodsForm.getOwnerCode() + " --- " + goodsForm.getOwnerName());

            beans.put("lstStockGoods", lstStockGoods);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            req.setAttribute("lstStockGoods", lstStockGoods);
            goodsForm.setExportUrl(filePath);






        } catch (Exception ex) {
            ex.printStackTrace();




            throw ex;




        }

        log.info("# End method viewStockDetail");




        return pageForward;




    }

    private StockModel getStockModelByCode(String stockModelCode) {
        StockModel stockModel = null;




        if (stockModelCode != null || !stockModelCode.trim().equals("")) {

            String strQuery = "from StockModel where lower(stockModelCode) = ? and status = ?  ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, stockModelCode.trim().toLowerCase());
            query.setParameter(1, Constant.STATUS_USE);
            List<StockModel> listStockTypes = query.list();




            if (listStockTypes != null && listStockTypes.size() > 0) {
                stockModel = listStockTypes.get(0);





            }
        }

        return stockModel;




    }

    /*
     * Author: ThanhNC Date create: 27/03/2009 Purpose: Tim kiem hang hoa trong
     * kho bang ma kho (ownerId) va ma loai kho (ownerTypeId) va loai hang
     */
    public List findStockTotalFull(
            Long ownerType, Long ownerId, Long stockTypeId) {
        log.info("finding al Goods In Stock ");




        try {
            String queryString = "from StockTotalFull where id.ownerType = ? and id.ownerId= ? and stockTypeId = ? and status=?";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerType);
            queryObject.setParameter(1, ownerId);
            queryObject.setParameter(2, stockTypeId);
            queryObject.setParameter(3, Constant.STATUS_USE);




            return queryObject.list();




        } catch (RuntimeException re) {
            log.error("find all failed", re);




            throw re;




        }

    }
    /*
     * Author: ThanhNC Date create: 27/03/2009 Purpose: Tim kiem hang hoa trong
     * kho bang ma kho (ownerId) va ma loai kho (ownerTypeId) va loai hang co
     * chi tiet serial
     */

    public List findStockTotalFullWithSerial(
            Long ownerType, Long ownerId) throws Exception {
        log.info("finding al Goods In Stock ");




        try {
            String queryString = "from StockTotalFull where id.ownerType = ? and id.ownerId= ? and status=? and quantity >0";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerType);
            queryObject.setParameter(1, ownerId);
            queryObject.setParameter(2, Constant.STATUS_USE);
            List<StockTotalFull> lst = queryObject.list();
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());




            for (StockTotalFull stockTotalFull : lst) {
                StockModel model = stockModelDAO.findById(stockTotalFull.getId().getStockModelId());




                if (model != null && model.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                    List<StockTransSerial> tmpList = getRangeSerial(ownerType, ownerId, stockTotalFull.getStockTypeId(), stockTotalFull.getId().getStockModelId(), stockTotalFull.getId().getStateId(), Constant.STATUS_USE);
                    stockTotalFull.setListSerial(tmpList);




                } else {
                    stockTotalFull.setListSerial(new ArrayList<StockTransSerial>());




                }

            }
            return lst;




        } catch (RuntimeException re) {
            log.error("find all failed", re);




            throw re;




        }

    }

    public List findStockTotalFullWithSerial(
            Long ownerType, Long ownerId, Long stockModelId) throws Exception {
        log.info("finding al Goods In Stock ");




        try {
            String queryString = "from StockTotalFull where id.ownerType = ? and id.ownerId= ? and id.stockModelId= ? and status=? and quantity >0";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerType);
            queryObject.setParameter(1, ownerId);
            queryObject.setParameter(2, stockModelId);
            queryObject.setParameter(3, Constant.STATUS_USE);
            List<StockTotalFull> lst = queryObject.list();
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());




            for (StockTotalFull stockTotalFull : lst) {
                StockModel model = stockModelDAO.findById(stockTotalFull.getId().getStockModelId());




                if (model != null && model.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                    List<StockTransSerial> tmpList = getRangeSerial(ownerType, ownerId, stockTotalFull.getStockTypeId(), stockTotalFull.getId().getStockModelId(), stockTotalFull.getId().getStateId(), Constant.STATUS_USE);
                    stockTotalFull.setListSerial(tmpList);




                } else {
                    stockTotalFull.setListSerial(new ArrayList<StockTransSerial>());




                }

            }
            return lst;




        } catch (RuntimeException re) {
            log.error("find all failed", re);




            throw re;




        }

    }
    /*
     * Author: ThanhNC Date create: 27/03/2009 Purpose: Tim kiem hang hoa trong
     * kho bang ma kho (ownerId) va ma loai kho (ownerTypeId) va mat hang
     * stockModelId
     */

    public List findStockTotalFullByStockModel(
            Long ownerType, Long ownerId, Long stockModelId) {
        log.info("finding al Goods In Stock ");




        try {
            String queryString = "from StockTotalFull where id.ownerType = ? and id.ownerId= ? and id.stockModelId = ? and status=? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerType);
            queryObject.setParameter(1, ownerId);
            queryObject.setParameter(2, stockModelId);
            queryObject.setParameter(3, Constant.STATUS_USE);




            return queryObject.list();




        } catch (RuntimeException re) {
            log.error("find all failed", re);




            throw re;




        }

    }

    /*
     * Author: Vunt Date create: 27/10/2009 Purpose: Tim kiem hang hoa trong kho
     * bang ma kho (ownerId) va ma loai kho (ownerTypeId) - mat hang
     * stockModelId va state
     */
    public List findStockTotalFullByStockModelAndState(
            Long ownerType, Long ownerId, Long stockModelId, Long stateId) {
        log.info("finding al Goods In Stock ");




        try {
            String queryString = "from StockTotalFull where id.ownerType = ? and id.ownerId= ? and id.stockModelId = ? and status=? and id.stateId = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerType);
            queryObject.setParameter(1, ownerId);
            queryObject.setParameter(2, stockModelId);
            queryObject.setParameter(3, Constant.STATUS_USE);
            queryObject.setParameter(4, stateId);




            return queryObject.list();




        } catch (RuntimeException re) {
            log.error("find all failed", re);




            throw re;




        }

    }

    /*
     * Author: ThanhNC Date create: 28/03/2009 Purpose: Xem chi tiet serial 1
     * mat hang trong kho
     */
    public String viewDetailSerial() throws Exception {
        log.info("# Begin method viewSerial");
        HttpServletRequest req = getRequest();
        //req.getSession().removeAttribute("lstSerial");
        String ownerType = (String) QueryCryptUtils.getParameter(req, "ownerType");
        String owner = (String) QueryCryptUtils.getParameter(req, "ownerId");
        String stockModel = (String) QueryCryptUtils.getParameter(req, "stockModelId");
        String state = (String) QueryCryptUtils.getParameter(req, "stateId");
        String impChannelTypeId = (String) QueryCryptUtils.getParameter(req, "impChannelTypeId");

        String impOwnerIdString = (String) QueryCryptUtils.getParameter(req, "impOwnerId");
        String impOwnerTypeString = (String) QueryCryptUtils.getParameter(req, "impOwnerType");


        Long ownerTypeId = 0L;
        Long ownerId = 0L;
        Long stockModelId = 0L;
        Long stateId = 0L;

        Long impOwnerId = 0L;
        Long impOwnerType = 0L;

        if (impOwnerIdString != null && !"".equals(impOwnerIdString)) {
            impOwnerId = Long.parseLong(impOwnerIdString);
        }
        if (impOwnerTypeString != null && !"".equals(impOwnerTypeString)) {
            impOwnerType = Long.parseLong(impOwnerTypeString);
        }


        if (ownerType != null && !"".equals(ownerType)) {
            ownerTypeId = Long.parseLong(ownerType);
            serialGoods.setOwnerType(ownerTypeId);
        }

        if (owner != null && !"".equals(owner)) {
            ownerId = Long.parseLong(owner);
            serialGoods.setOwnerId(ownerId);
        }

        if (stockModel != null && !"".equals(stockModel)) {
            stockModelId = Long.parseLong(stockModel);
            serialGoods.setStockModelId(stockModelId);
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            StockModel model = stockModelDAO.findById(stockModelId);

            if (stockModel != null) {
                serialGoods.setStockModelName(model.getName());
                serialGoods.setStockTypeId(model.getStockTypeId());
            }
        }

        if (state != null && !"".equals(state)) {
            stateId = Long.parseLong(state);
            serialGoods.setStateId(stateId);
        }

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(getSession());
        StockModel model = stockModelDAO.findById(stockModelId);
        Long stockTypeId = 0L;

        if (model == null) {
            return "viewDetailSerial";
        } //        BaseStockDAO baseStockDAO = new BaseStockDAO();
        //        baseStockDAO.setSession(this.getSession());
        //        String tableName = baseStockDAO.getTableNameByStockType(model.getStockTypeId(), BaseStockDAO.NAME_TYPE_HIBERNATE);
        //        String SQL_SELECT = "select serial from " + tableName + " where ownerType= ? " +
        //                "and ownerId =?  and stockModelId = ? and stateId = ? and status = ? order by to_number(serial) asc";
        //        Query q = getSession().createQuery(SQL_SELECT);
        //        q.setParameter(0, ownerTypeId);
        //        q.setParameter(1, ownerId);
        //        q.setParameter(2, stockModelId);
        //        q.setParameter(3, stateId);
        //        q.setParameter(4, Constant.STATUS_USE);
        //
        //        List<String> lstResult = q.list();
        //        List<StockTransSerial> tmpList = getListStockTransSerials(lstResult);
        //tamdt1, 16/09/2010, start, bo sung them viec xem chi tiet serial theo kenh
        //thay the doan code nay
        //        List<StockTransSerial> tmpList = getRangeSerial(ownerTypeId, ownerId, model.getStockTypeId(), stockModelId, stateId, Constant.STATUS_USE);
        List<StockTransSerial> tmpList = new ArrayList<StockTransSerial>();


        //
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Shop fromShop = new Shop();
        Shop toShop = new Shop();

        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        Staff fromStaff = new Staff();
        Staff toStaff = new Staff();

        if (ownerId != null && ownerTypeId != null && ownerTypeId.equals(Constant.OWNER_TYPE_SHOP)) {
            fromShop = shopDAO.findById(ownerId);
            if (fromShop == null || fromShop.getShopId() == null) {
                req.setAttribute("returnMsg", "Error. From shop is not exist!");
                return "viewDetailSerial";
            }
        } else if (ownerId != null && ownerTypeId != null && ownerTypeId.equals(Constant.OWNER_TYPE_STAFF)) {
            fromStaff = staffDAO.findById(ownerId);
            if (fromStaff == null || fromStaff.getShopId() == null) {
                req.setAttribute("returnMsg", "Error. To staff is not exist!");
                return "viewDetailSerial";
            }
        }

        if (impOwnerId != null && impOwnerType != null && impOwnerType.equals(Constant.OWNER_TYPE_SHOP)) {
            toShop = shopDAO.findById(impOwnerId);
            if (toShop == null || toShop.getShopId() == null) {
                req.setAttribute("returnMsg", "Error. From shop is not exist!");
                return "viewDetailSerial";
            }
        } else if (impOwnerId != null && impOwnerType != null && impOwnerType.equals(Constant.OWNER_TYPE_STAFF)) {
            toStaff = staffDAO.findById(impOwnerId);
            if (toStaff == null || toStaff.getShopId() == null) {
                req.setAttribute("returnMsg", "Error. To staff is not exist!");
                return "viewDetailSerial";
            }
        }

        boolean IS_STOCK_CHANNEL = true;
        if (fromShop != null && toShop != null && fromShop.getShopId() != null && toShop.getShopId() != null) {
            //Truong hop xuat tra cap tren
            if (fromShop.getParentShopId() != null && fromShop.getParentShopId().equals(toShop.getShopId())) {
                IS_STOCK_CHANNEL = false;
            }
        } else if (fromStaff != null && toShop != null && fromStaff.getShopId() != null && toShop.getShopId() != null) {
            IS_STOCK_CHANNEL = false;
        }

        if (IS_STOCK_CHANNEL && impChannelTypeId != null && !impChannelTypeId.trim().equals("")) {
            tmpList = getRangeSerialHasChannelType(ownerTypeId, ownerId, model.getStockTypeId(), stockModelId, stateId, Constant.STATUS_USE, impChannelTypeId.trim());
        } else {
            tmpList = getRangeSerial(ownerTypeId, ownerId, model.getStockTypeId(), stockModelId, stateId, Constant.STATUS_USE);
        }


        //tamdt1, 16/09/2010, end

        //req.getSession().setAttribute("lstSerial", tmpList);
        req.setAttribute("lstSerial", tmpList);


//        List lstGoodsState = new StockTotalDAO().findByStockAndModel(ownerId, ownerTypeId, stockModelId);
//        req.setAttribute("lstGoodsState", lstGoodsState);

        String pageForward = "viewDetailSerial";
        log.info("# End method viewDetailQuantity");




        return pageForward;




    }

    /**
     * author : tamdt1 date : 21/09/2010 desc : Xem chi tiet serial 1 mat hang
     * trong kho theo kenh (modified ham viewDetailSerial)
     *
     */
    public String viewDetailSerialGroupByChannelType() throws Exception {
        log.info("Begin method viewDetailSerialGroupByChannelType of StockCommonDAO...");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);





        try {
            String ownerType = (String) QueryCryptUtils.getParameter(req, "ownerType");
            String owner = (String) QueryCryptUtils.getParameter(req, "ownerId");
            String stockModel = (String) QueryCryptUtils.getParameter(req, "stockModelId");
            String state = (String) QueryCryptUtils.getParameter(req, "stateId");
            Long ownerTypeId = 0L;
            Long ownerId = 0L;
            Long stockModelId = 0L;
            Long stateId = 0L;




            if (ownerType != null && !"".equals(ownerType)) {
                ownerTypeId = Long.parseLong(ownerType);
                serialGoods.setOwnerType(ownerTypeId);




            }

            if (owner != null && !"".equals(owner)) {
                ownerId = Long.parseLong(owner);
                serialGoods.setOwnerId(ownerId);




            }

            if (stockModel != null && !"".equals(stockModel)) {
                stockModelId = Long.parseLong(stockModel);
                serialGoods.setStockModelId(stockModelId);
                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(this.getSession());
                StockModel model = stockModelDAO.findById(stockModelId);




                if (stockModel != null) {
                    serialGoods.setStockModelName(model.getName());
                    serialGoods.setStockTypeId(model.getStockTypeId());




                }

            }

            if (state != null && !"".equals(state)) {
                stateId = Long.parseLong(state);
                serialGoods.setStateId(stateId);




            }

            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(getSession());
            StockModel model = stockModelDAO.findById(stockModelId);
            Long stockTypeId = 0L;




            if (model == null) {
                return VIEW_SERIAL_GROUP_BY_CHANNEL_TYPE;




            } else {
                serialGoods.setStockModelName(model.getName());
                serialGoods.setStockTypeId(model.getStockTypeId());
                stockTypeId = model.getStockTypeId();




            }

            //lay danh sach dai serial
            List<StockTransSerial> tmpList = new ArrayList<StockTransSerial>();
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            String tableName = baseStockDAO.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            String SQL_SELECT = " "
                    + "SELECT   channel_type_id as channelTypeId, "
                    + "         MIN (serial) as  fromSerial, "
                    + "         MAX (serial) as toSerial, "
                    + "         MAX(serial) -MIN(serial) + 1 as quantity "
                    + "FROM (   SELECT  channel_type_id, "
                    + "                 to_number(serial) serial, "
                    + "                 to_number(serial) - ROW_NUMBER () OVER (ORDER BY channel_type_id, to_number(serial)) rn "
                    + "         FROM " + tableName + " "
                    + "         WHERE   1 = 1"
                    + "                 and owner_type = ? "
                    + "                 and owner_id= ? "
                    + "                 and stock_model_id = ? "
                    + "                 and state_id = ? "
                    + "                 and status = ? "
                    + "     ) "
                    + "GROUP BY channel_type_id, rn "
                    + "ORDER BY fromSerial ";

            Query q = getSession().createSQLQuery(SQL_SELECT).
                    addScalar("channelTypeId", Hibernate.LONG).
                    addScalar("fromSerial", Hibernate.STRING).
                    addScalar("toSerial", Hibernate.STRING).
                    addScalar("quantity", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(StockTransSerial.class));

            q.setParameter(
                    0, ownerType);
            q.setParameter(
                    1, ownerId);
            q.setParameter(
                    2, stockModelId);
            q.setParameter(
                    3, stateId);
            q.setParameter(
                    4, Constant.STATUS_ACTIVE);
            tmpList = q.list();

//            req.setAttribute(REQUEST_LST_SERIAL, tmpList);
//
//            String strQuery = ""
//                    + "from ChannelType a "
//                    + "where a.status = ? "
//                    + "order by nlssort(a.name, 'nls_sort=Vietnamese') asc ";
//            Query query = getSession().createQuery(strQuery);
//            query.setParameter(0, Constant.STATUS_USE);
//            List<ChannelType> listChannelType = query.list();
//            req.setAttribute(REQUEST_LIST_CHANNEL_TYPE, listChannelType);



            String strQuery = ""
                    + "from ChannelType a "
                    + "where a.status = ? "
                    + "order by nlssort(a.name, 'nls_sort=Vietnamese') asc ";
            Query query = getSession().createQuery(strQuery);

            query.setParameter(0, Constant.STATUS_USE);
            List<ChannelType> listChannelType = query.list();

            //thiet lap ten kenh cho tung dai










            for (StockTransSerial tmpStockTransSerial : tmpList) {
                for (ChannelType tmpChannelType : listChannelType) {
                    if (tmpStockTransSerial.getChannelTypeId() == null) {
                        break;
                    } else if (tmpChannelType.getChannelTypeId().equals(tmpStockTransSerial.getChannelTypeId())) {
                        tmpStockTransSerial.setChannelTypeName(tmpChannelType.getName());
                        break;
                    }
                }
            }

            req.setAttribute(REQUEST_LST_SERIAL, tmpList);
        } catch (Exception ex) {
            ex.printStackTrace();




        }

        log.info("End method viewDetailSerialGroupByChannelType of StockCommonDAO");




        return VIEW_SERIAL_GROUP_BY_CHANNEL_TYPE;





    }

    /*
     * Author: ThanhNC Date create: 20/10/2009 Purpose: Tim kiem chi tiet serial
     * theo dai
     */
    private List<StockTransSerial> getRangeSerial(Long ownerType, Long ownerId, Long stockTypeId, Long stockModelId, Long stateId, Long status) throws Exception {
        try {
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            String tableName = baseStockDAO.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);

            String SQL_SELECT = " SELECT   MIN (to_number(serial)) as  fromSerial," + " MAX (to_number(serial)) as toSerial," + " MAX(to_number(serial)) -MIN(to_number(serial)) +1 as quantity " + " FROM (SELECT serial, serial - ROW_NUMBER () OVER (ORDER BY to_number(serial)) rn " + " FROM " + tableName + " where owner_type = ? and owner_id= ? and stock_model_id = ? and" + " state_id = ? and status = ? ) GROUP BY rn " + " ORDER BY to_number(fromSerial) ";

            Query q = getSession().createSQLQuery(SQL_SELECT).
                    addScalar("fromSerial", Hibernate.STRING).
                    addScalar("toSerial", Hibernate.STRING).
                    addScalar("quantity", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(StockTransSerial.class));
            q.setParameter(
                    0, ownerType);
            q.setParameter(
                    1, ownerId);
            q.setParameter(
                    2, stockModelId);
            q.setParameter(
                    3, stateId);
            q.setParameter(
                    4, status);










            return q.list();
//            List<String> lstResult = q.list();
//            List<StockTransSerial> tmpList = getListStockTransSerials(lstResult);
//            return tmpList;
        } catch (Exception ex) {
            ex.printStackTrace();




            throw ex;




        }

    }

    /**
     * author : tamdt1 date : 16/09/2010 desc : modified tu ham getRangeSerial
     * cua ThanhNC, bo sung them xem theo kenh
     *
     */
    private List<StockTransSerial> getRangeSerialHasChannelType(Long ownerType, Long ownerId, Long stockTypeId, Long stockModelId, Long stateId, Long status, String channelTypeId) throws Exception {
        try {
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            String tableName = baseStockDAO.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);

            /**
             * TrongLV 2011/04/16 Neu channelType != null & don vi xuat co quyen
             * gan kenh => cho phep view serial da gan kenh hoac chua gan kenh
             * Nguoc lai neu channelType != null nhung don vi xuat khong co
             * quyen gan kenh => chi cho phep view serial da gan kenh
             */
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());
            Shop fromShop = new Shop();

            boolean checkRoleAssignChannelToGoods = false;
            if (ownerType != null && ownerType.equals(Constant.OWNER_TYPE_SHOP)) {
                fromShop = shopDAO.findById(ownerId);
                if (fromShop == null || fromShop.getShopId() == null) {
                    return null;
                }
                checkRoleAssignChannelToGoods = CommonDAO.checkRoleAssignChannelToGoods(this.getSession(), fromShop.getShopId(), true);

            }

            if (!checkRoleAssignChannelToGoods) {
                //neu don vi xuat khong co quyen gan kenh
                if (channelTypeId == null || channelTypeId.equals("")) {
                    channelTypeId = "-1";
                }
            } else {
                //neu don vi xuat co quyen gan kenh
                if (fromShop != null && fromShop.getShopId() != null) {
                    if (channelTypeId == null || channelTypeId.equals("")) {
                        channelTypeId = "-1";
                    }
                }
            }

            String SQL_SELECT = " SELECT   MIN (to_number(serial)) as  fromSerial," + " MAX (to_number(serial)) as toSerial," + " MAX(to_number(serial)) -MIN(to_number(serial)) +1 as quantity " + " FROM (SELECT serial, serial - ROW_NUMBER () OVER (ORDER BY to_number(serial)) rn " + " FROM " + tableName
                    + " where owner_type = ? and owner_id= ? and stock_model_id = ? and" + " state_id = ? and status = ? "
                    + "  ) "
                    + " GROUP BY rn " + " ORDER BY to_number(fromSerial) ";

            boolean IS_STOCK_CHANNEL = this.checkStockChannel(stockModelId);

            if (!checkRoleAssignChannelToGoods && IS_STOCK_CHANNEL) {
                SQL_SELECT = " SELECT   MIN (to_number(serial)) as  fromSerial," + " MAX (to_number(serial)) as toSerial," + " MAX(to_number(serial)) -MIN(to_number(serial)) +1 as quantity " + " FROM (SELECT serial, serial - ROW_NUMBER () OVER (ORDER BY to_number(serial)) rn " + " FROM " + tableName
                        + " where owner_type = ? and owner_id= ? and stock_model_id = ? and" + " state_id = ? and status = ? "
                        + " and (channel_type_id in (?) or channel_type_id is null) ) "
                        + " GROUP BY rn " + " ORDER BY to_number(fromSerial) ";
            }

            Query q = getSession().createSQLQuery(SQL_SELECT).
                    addScalar("fromSerial", Hibernate.STRING).
                    addScalar("toSerial", Hibernate.STRING).
                    addScalar("quantity", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(StockTransSerial.class));
            q.setParameter(0, ownerType);
            q.setParameter(1, ownerId);
            q.setParameter(2, stockModelId);
            q.setParameter(3, stateId);
            q.setParameter(4, status);

            if (!checkRoleAssignChannelToGoods && IS_STOCK_CHANNEL) {
                q.setParameter(5, channelTypeId);
            }

            return q.list();

        } catch (Exception ex) {
            ex.printStackTrace();




            throw ex;




        }

    }
    /*
     * Author: ThanhNC Date create: 28/03/2009 Purpose: Them mat hang vao danh
     * sach hang xuat kho
     */

    public String addGoods() throws Exception {
        log.info("# Begin method addGoods");
        HttpServletRequest req = getRequest();
        String pageForward = "editGoods";
        String pageId = req.getParameter("pageId");

        //----------------------------------------------------------------------
        //tamdt1, 16/09/2010, start bo sung chon mat hang bang F9
        //--------kiem tra ma mat hang co ton tai khong
        String stockModelCodeF9 = this.goodsForm.getStockModelCode();
        Long quantityF9 = this.goodsForm.getQuantity();

        if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("") || quantityF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return pageForward;
        } //trim cac truong can thiet
        stockModelCodeF9 = stockModelCodeF9.trim();
        this.goodsForm.setStockModelCode(stockModelCodeF9);
        //
        StockModelDAO stockModelDAOF9 = new StockModelDAO();
        stockModelDAOF9.setSession(this.getSession());
        StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9);
        if (stockModelF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        } else {
            this.goodsForm.setStockTypeId(stockModelF9.getStockTypeId());
            this.goodsForm.setStockModelId(stockModelF9.getStockModelId());
            this.goodsForm.setStockModelCode(stockModelF9.getStockModelCode());
            this.goodsForm.setStockModelName(stockModelF9.getName());
        } //tamdt1, 16/09/2010, end
        //----------------------------------------------------------------------

        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        } /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");

        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        }
        /*
         * LamNV ADD STOP
         */
//Danh sach loai tai nguyen

        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);
        //Danh sach stockModel
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());

        if (goodsForm.getStockTypeId() == null || goodsForm.getStockModelId() == null || goodsForm.getStateId() == null || goodsForm.getQuantity() == null) {
//            req.setAttribute("returnMsg", "Chưa nhập các điều kiện bắt buộc");
            req.setAttribute("returnMsg", "ERR.STK.064");
            if (goodsForm.getStockTypeId() != null) {
                req.setAttribute("lstStockModel", getStockModelInShop(goodsForm.getOwnerType(), goodsForm.getOwnerId(), goodsForm.getStockTypeId(), Constant.STATUS_USE));
            }
            return pageForward;
        }

        if (goodsForm.getQuantity().equals(0L)) {
//            req.setAttribute("returnMsg", "Số lượng phải là số nguyên dương");
            req.setAttribute("returnMsg", "ERR.STK.011");
            if (goodsForm.getStockTypeId() != null) {
                req.setAttribute("lstStockModel", getStockModelInShop(goodsForm.getOwnerType(), goodsForm.getOwnerId(), goodsForm.getStockTypeId(), Constant.STATUS_USE));
            }

            return pageForward;
        }

        List lstStockModel = getStockModelInShop(goodsForm.getOwnerType(), goodsForm.getOwnerId(), goodsForm.getStockTypeId(), Constant.STATUS_USE);
        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
        if (lstGoods == null) {
            lstGoods = new ArrayList();
        }

        GoodsForm gF = getGoodsForm();
        if (gF.getQuantity() == null) {
//            req.setAttribute("returnMsg", "Chưa nhập số lượng hàng hoá cần xuất kho");
            req.setAttribute("returnMsg", "ERR.STK.073");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        } //Check trung lap

        StockTransFull tmp;
        for (int idx = 0; idx < lstGoods.size(); idx++) {
            tmp = (StockTransFull) lstGoods.get(idx);
            if (tmp.getStockTypeId().equals(gF.getStockTypeId()) && tmp.getStockModelId().equals(gF.getStockModelId()) && tmp.getStateId().equals(gF.getStateId())) {
//                req.setAttribute("returnMsg", "Hàng hoá thêm vào bị trùng lặp.");
                req.setAttribute("returnMsg", "ERR.STK.074");
                req.setAttribute("lstStockModel", lstStockModel);
                return pageForward;
            }
        }

        StockTransFull trans = new StockTransFull();
        //Lay thong tin loai hang hoa

        if (gF.getStockTypeId() == null) {
//            req.setAttribute("returnMsg", "Chưa chọn loại hàng hoá");
            req.setAttribute("returnMsg", "ERR.STK.075");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        StockType stockType = (StockType) stockTypeDAO.findById(gF.getStockTypeId());

        if (stockType == null) {
//            req.setAttribute("returnMsg", "Loại hàng hoá không tồn tại trên hệ thống");
            req.setAttribute("returnMsg", "ERR.STK.076");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        trans.setStockTypeId(gF.getStockTypeId());
        trans.setStockTypeName(stockType.getName());
        //Lay thong tin hang hoa

        if (gF.getStockModelId() == null) {
//            req.setAttribute("returnMsg", "Chưa chọn hàng hoá");
            req.setAttribute("returnMsg", "ERR.STK.025");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        StockModel stockModel = (StockModel) stockModelDAO.findById(gF.getStockModelId());

        if (stockModel == null) {
//            req.setAttribute("returnMsg", "Hàng hoá không tồn tại trên hệ thống.");
            req.setAttribute("returnMsg", "ERR.STK.059");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        //Check han muc
        //Chua chon don vi nhap => chua check duoc
//        Long priceBasic = priceDAO.findBasicPrice(stockModel.getStockModelId(), pricePolicy);
//        if (priceBasic == null) {
//            req.setAttribute("returnMsg", "ERR.SAE.143");
//            req.setAttribute("lstStockModel", lstStockModel);
//            return pageForward;
//        }
        //Check han muc

        //9969
        for (int idx = 0; idx < lstGoods.size(); idx++) {
            tmp = (StockTransFull) lstGoods.get(idx);
            if (tmp.getStockTransType() != null && !tmp.getStockTransType().equals(stockModel.getStockTypeGroup())) {
                req.setAttribute("returnMsg", "ERR.STK.074.1");
                req.setAttribute("lstStockModel", lstStockModel);
                return pageForward;
            }
        }
        trans.setStockTransType(stockModel.getStockTypeGroup());
        trans.setStockModelId(gF.getStockModelId());
        trans.setStockModelCode(stockModel.getStockModelCode());
        trans.setStockModelName(stockModel.getName());
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        AppParams appParams = appParamsDAO.findAppParams("STOCK_MODEL_UNIT", stockModel.getUnit());

        if (appParams != null) {
            trans.setUnit(appParams.getName());
        }

        trans.setCheckSerial(stockModel.getCheckSerial());
        Long checkDial = 0L;
        if (gF.getCheckDial() != null && gF.getCheckDial().equals("true")) {
            checkDial = 1L;
        }

        trans.setCheckDial(checkDial);
        trans.setFromOwnerType(gF.getOwnerType());
        trans.setFromOwnerId(gF.getOwnerId());
        //Check so luong yeu cau so voi so luong hang hoa thuc con trong kho

        if (!checkStockGoods(goodsForm.getOwnerId(), goodsForm.getOwnerType(), goodsForm.getStockModelId(), goodsForm.getStateId(), goodsForm.getQuantity(), checkDial, getSession())) {
//            req.setAttribute("returnMsg", "Số lượng hàng hoá trong kho nhỏ hơn số lượng yêu cầu xuất.");
            req.setAttribute("returnMsg", "ERR.STK.077");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;

        }
//        //Check truong hop xuat hang cho dai ly -->tat ca cac mat hang xuat di phai cung VAT
//        if (goodsForm.getExpType() != null && goodsForm.getExpType().equals("dial")) {
//            if (lstGoods != null && lstGoods.size() > 0) {
//                StockTransFull stockTransFull = (StockTransFull) lstGoods.get(0);
//                Long firstStockModelId = stockTransFull.getStockModelId();
//                String priceType = ResourceBundleUtils.getResource("PRICE_TYPE_AGENT");
//                Long firstVAT = getVATByStockModelIdAndType(firstStockModelId, priceType);
//                if (!checkVAT(firstStockModelId, firstVAT, priceType)) {
//                    req.setAttribute("returnMsg", "Các mặt hàng xuất bán cho đại lý phải khai báo giá và có cùng VAT");
//                    req.setAttribute("lstStockModel", lstStockModel);
//                    return pageForward;
//                }
//
//            }
//        }

//Lay so luong va trang thai hang
        trans.setQuantity(gF.getQuantity());

        trans.setStateId(gF.getStateId());
        trans.setNote(gF.getNote());

        /*
         * LamNV ADD START
         */
        if (revokeColl != null && revokeColl.equals("true")) {
            Double amount = 0.0;
            String amountAttr = "amount" + pageId;
            Double priceDeposit = getPriceDeposit(gF.getStockModelId());

            Double price = gF.getQuantity() * getPriceDeposit(gF.getStockModelId());
            trans.setPrice(priceDeposit);
            trans.setPriceDisplay(NumberUtils.rounđAndFormatAmount(priceDeposit));
            if (req.getSession().getAttribute(amountAttr) != null && req.getSession().getAttribute(amountAttr) != "") {
                amount = Double.parseDouble(req.getSession().getAttribute(amountAttr).toString());
            }
            amount += price;
            req.getSession().setAttribute(amountAttr, amount);
            System.out.println("-----------addgoods : begin");
            System.out.println("amountDisplay=" + NumberUtils.rounđAndFormatAmount(amount));
            System.out.println("-----------addgoods : end");

//            setTabSession("amountDisplay", NumberUtils.rounđAndFormatAmount(amount));

        }
        /*
         * LamNV ADD STOP
         */

        lstGoods.add(trans);
        /*
         * Kiem tra tong gia goc HH xuat cho NV + gia tri HH hien tai cua NV <=
         * han muc cua NV
         */
        /*
         * Double sourceAmount = sumAmountByGoodsList(lstGoods); if
         * (sourceAmount != null && sourceAmount >= 0) { if
         * (!checkCurrentDebitWhenImplementTrans(gF.getOwnerId(),
         * gF.getOwnerType(), sourceAmount)) { req.setAttribute("returnMsg",
         * "ERR.LIMIT.0012"); req.setAttribute("lstStockModel", lstStockModel);
         * return pageForward; } } else { req.setAttribute("returnMsg",
         * "ERR.LIMIT.0014"); req.setAttribute("lstStockModel", lstStockModel);
         * return pageForward; }
         */
        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
        goodsForm.resetForm();

        log.info("# End method addGoods");
        return pageForward;
    }

    /*
     * Author: Vunt Date create: 20/09/2009 Purpose: Them mat hang vao danh sach
     * thu hoi
     */
    public String addGoodsRecover() throws Exception {
        log.info("# Begin method addGoods");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        String pageForward = "editGoodsRecover";




        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");




        }

        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        //----------------------------------------------------------------------
        //tamdt1, 16/09/2010, start bo sung chon mat hang bang F9
        //--------kiem tra ma mat hang co ton tai khong
        String stockModelCodeF9 = this.goodsForm.getStockModelCode();
        Long quantityF9 = this.goodsForm.getQuantity();




        if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("") || quantityF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống




            return pageForward;




        } //trim cac truong can thiet
        stockModelCodeF9 = stockModelCodeF9.trim();




        this.goodsForm.setStockModelCode(stockModelCodeF9);
        //
        StockModelDAO stockModelDAOF9 = new StockModelDAO();
        stockModelDAOF9.setSession(this.getSession());
        StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9);




        if (stockModelF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại




            return pageForward;




        } else {
            this.goodsForm.setStockTypeId(stockModelF9.getStockTypeId());




            this.goodsForm.setStockModelId(stockModelF9.getStockModelId());




            this.goodsForm.setStockModelCode(stockModelF9.getStockModelCode());




            this.goodsForm.setStockModelName(stockModelF9.getName());




        }
        //tamdt1, 16/09/2010, end
        //----------------------------------------------------------------------

        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);

        Long importID;
        String importCode = req.getParameter("shopImportedCode");




        if (importCode == null || importCode.trim().equals("")) {
            req.setAttribute(REQUEST_MESSAGE, "Lỗi. Mã đại lý không hợp lệ");




            return pageForward;




        }
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Shop agent = shopDAO.findShopsAvailableByShopCodeNotStatus(importCode.trim());




        if (agent == null
                || agent.getShopId() == null
                || !agent.getParentShopId().equals(userToken.getShopId())
                || agent.getChannelTypeId() == null) {
            req.setAttribute(REQUEST_MESSAGE, "Lỗi. Mã đại lý không hợp lệ");




            return pageForward;




        }
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        ChannelType channelType = channelTypeDAO.findById(agent.getChannelTypeId());




        if (channelType == null
                || channelType.getChannelTypeId() == null
                || channelType.getObjectType() == null
                || channelType.getIsVtUnit() == null
                || !channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)
                || !channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT)) {
            req.setAttribute(REQUEST_MESSAGE, "Lỗi. Mã đại lý không hợp lệ");




            return pageForward;




        }


//        importID = Long.parseLong(getShopIdbyShopCode(importCode.trim()));
        importID = agent.getShopId();

        //Danh sach stockModel
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());





        if (goodsForm.getStockTypeId() == null || goodsForm.getStockTypeId() == null || goodsForm.getStateId() == null || goodsForm.getQuantity() == null) {
//            req.setAttribute("returnMsg", "Chưa nhập các điều kiện bắt buộc");
            req.setAttribute("returnMsg", "ERR.STK.064");




            if (goodsForm.getStockTypeId() != null) {
                req.setAttribute("lstStockModel", getListStockModelToRecover(goodsForm.getStockTypeId(), importID));




            }

            return pageForward;




        }

        if (goodsForm.getQuantity().equals(0L)) {
//            req.setAttribute("returnMsg", "Số lượng phải là số nguyên dương");
            req.setAttribute("returnMsg", "ERR.STK.011");




            if (goodsForm.getStockTypeId() != null) {
                req.setAttribute("lstStockModel", getListStockModelToRecover(goodsForm.getStockTypeId(), importID));




            }

            return pageForward;




        }

        List lstStockModel = getListStockModelToRecover(goodsForm.getStockTypeId(), importID);


        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);




        if (lstGoods == null) {
            lstGoods = new ArrayList();




        }

        GoodsForm gF = getGoodsForm();




        if (gF.getQuantity() == null) {
//            req.setAttribute("returnMsg", "Chưa nhập số lượng hàng hoá cần xuất kho");
            req.setAttribute("returnMsg", "ERR.STK.073");
            req.setAttribute("lstStockModel", lstStockModel);




            return pageForward;




        } //Check trung lap

        StockTransFull tmp;




        for (int idx = 0; idx
                < lstGoods.size(); idx++) {
            tmp = (StockTransFull) lstGoods.get(idx);




            if (tmp.getStockModelId().equals(gF.getStockModelId()) && tmp.getStateId().equals(gF.getStateId())) {
//                req.setAttribute("returnMsg", "Hàng hoá thêm vào bị trùng lặp.");
                req.setAttribute("returnMsg", "ERR.STK.074");
                req.setAttribute("lstStockModel", lstStockModel);




                return pageForward;




            }

        }

        StockTransFull trans = new StockTransFull();
        //Lay thong tin loai hang hoa




        if (gF.getStockTypeId() == null) {
//            req.setAttribute("returnMsg", "Chưa chọn loại hàng hoá");
            req.setAttribute("returnMsg", "ERR.STK.075");
            req.setAttribute("lstStockModel", lstStockModel);




            return pageForward;




        }

        StockType stockType = (StockType) stockTypeDAO.findById(gF.getStockTypeId());




        if (stockType == null) {
//            req.setAttribute("returnMsg", "Loại hàng hoá không tồn tại trên hệ thống");
            req.setAttribute("returnMsg", "ERR.STK.076");
            req.setAttribute("lstStockModel", lstStockModel);




            return pageForward;




        }

        trans.setStockTypeId(gF.getStockTypeId());
        trans.setStockTypeName(stockType.getName());
        //Lay thong tin hang hoa




        if (gF.getStockModelId() == null) {
//            req.setAttribute("returnMsg", "Chưa chọn hàng hoá");
            req.setAttribute("returnMsg", "ERR.STK.025");
            req.setAttribute("lstStockModel", lstStockModel);




            return pageForward;




        }

        StockModel stockModel = (StockModel) stockModelDAO.findById(gF.getStockModelId());




        if (stockModel == null) {
//            req.setAttribute("returnMsg", "Hàng hoá không tồn tại trên hệ thống.");
            req.setAttribute("returnMsg", "ERR.STK.059");
            req.setAttribute("lstStockModel", lstStockModel);




            return pageForward;




        }


        trans.setStockModelId(gF.getStockModelId());
        trans.setStockModelCode(stockModel.getStockModelCode());
        trans.setStockModelName(stockModel.getName());
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        AppParams appParams = appParamsDAO.findAppParams("STOCK_MODEL_UNIT", stockModel.getUnit());




        if (appParams != null) {
            trans.setUnit(appParams.getName());




        }

        trans.setCheckSerial(stockModel.getCheckSerial());
        Long checkDial = 0L;




        if (gF.getCheckDial() != null && gF.getCheckDial().equals("true")) {
            checkDial = 1L;




        }

        trans.setCheckDial(checkDial);
        trans.setFromOwnerType(importID);
        trans.setFromOwnerId(1L);

        //Check so luong yeu cau so voi so luong hang hoa thuc con trong kho




        if (!checkStockGoods(importID, 1L, goodsForm.getStockModelId(), goodsForm.getStateId(), goodsForm.getQuantity(), checkDial, getSession())) {
//            req.setAttribute("returnMsg", "Số lượng hàng hoá trong kho nhỏ hơn số lượng yêu cầu xuất.");
            req.setAttribute("returnMsg", "ERR.STK.077");
            req.setAttribute("lstStockModel", lstStockModel);




            return pageForward;




        }

//Lay so luong va trang thai hang
        trans.setQuantity(gF.getQuantity());
        trans.setStateId(gF.getStateId());
        trans.setNote(gF.getNote());
        trans.setTelecomServiceID(gF.getTelecomServiceId());
        lstGoods.add(trans);
        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
        goodsForm.resetForm();
        //CalcSumDeposit(lstGoods);
        log.info("# End method addGoods");




        return pageForward;




    }


    /*
     * Author: Vunt Date create: 09/09/2009 Purpose: Them mat hang vao danh sach
     * hang xuat kho cho CTV
     */
    public String addGoodsColl() throws Exception {
        log.info("# Begin method addGoods");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");

        String pageForward = "editGoods";
        req.setAttribute("collaborator", "coll");

        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        }

        //----------------------------------------------------------------------
        //tamdt1, 16/09/2010, start bo sung chon mat hang bang F9
        //--------kiem tra ma mat hang co ton tai khong
        String stockModelCodeF9 = this.goodsForm.getStockModelCode();
        Long quantityF9 = this.goodsForm.getQuantity();
        if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("") || quantityF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trường bắt buộc không được để trống
            return pageForward;
        } //trim cac truong can thiet
        stockModelCodeF9 = stockModelCodeF9.trim();
        this.goodsForm.setStockModelCode(stockModelCodeF9);
        //
        StockModelDAO stockModelDAOF9 = new StockModelDAO();
        stockModelDAOF9.setSession(this.getSession());
        StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9);
        if (stockModelF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        } else {
            this.goodsForm.setStockTypeId(stockModelF9.getStockTypeId());
            this.goodsForm.setStockModelId(stockModelF9.getStockModelId());
            this.goodsForm.setStockModelCode(stockModelF9.getStockModelCode());
            this.goodsForm.setStockModelName(stockModelF9.getName());
        }
        //tamdt1, 16/09/2010, end
        //----------------------------------------------------------------------


//Danh sach loai tai nguyen
//        StockTypeDAO stockTypeDAO = new StockTypeDAO();
//        stockTypeDAO.setSession(this.getSession());
//
//        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
//        telecomServiceDAO.setSession(this.getSession());
//        List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
//        req.setAttribute("lstStockType", lstTelecomService);
        req.setAttribute("collaborator", "coll");

        //Danh sach stockModel
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());

//        if (goodsForm.getTelecomServiceId() == null
//                || goodsForm.getStockModelId() == null
//                || goodsForm.getStateId() == null
//                || goodsForm.getQuantity() == null) {

        if (goodsForm.getStockModelId() == null
                || goodsForm.getStateId() == null
                || goodsForm.getQuantity() == null) {
//            req.setAttribute("returnMsg", "Chưa nhập các điều kiện bắt buộc");
            req.setAttribute("returnMsg", "ERR.STK.064");
//            if (goodsForm.getTelecomServiceId() != null) {
//                req.setAttribute("lstStockModel", getStockModelInShopColl());
//            }
            return pageForward;
        }

        if (goodsForm.getQuantity().equals(0L)) {
//            req.setAttribute("returnMsg", "Số lượng phải là số nguyên dương");
            req.setAttribute("returnMsg", "ERR.STK.011");

            if (goodsForm.getStockTypeId() != null) {
                req.setAttribute("lstStockModel", getStockModelInShopColl());
            }

            return pageForward;
        }

        List lstStockModel = getStockModelInShopColl();
        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);

        if (lstGoods == null) {
            lstGoods = new ArrayList();
        }

        GoodsForm gF = getGoodsForm();

        if (gF.getQuantity() == null) {
//            req.setAttribute("returnMsg", "Chưa nhập số lượng hàng hoá cần xuất kho");
            req.setAttribute("returnMsg", "ERR.STK.073");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;

        } //Check trung lap

        StockTransFull tmp;

        for (int idx = 0; idx < lstGoods.size(); idx++) {
            tmp = (StockTransFull) lstGoods.get(idx);
            if (tmp.getStockModelId().equals(gF.getStockModelId()) && tmp.getStateId().equals(gF.getStateId())) {
//                req.setAttribute("returnMsg", "Hàng hoá thêm vào bị trùng lặp.");
                req.setAttribute("returnMsg", "ERR.STK.074");
                req.setAttribute("lstStockModel", lstStockModel);
                return pageForward;
            }
        }

        StockTransFull trans = new StockTransFull();
        //Lay thong tin loai hang hoa

        if (gF.getStockTypeId() == null) {
//            req.setAttribute("returnMsg", "Chưa chọn loại hàng hoá");
            req.setAttribute("returnMsg", "ERR.STK.075");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        //StockType stockType = (StockType) stockTypeDAO.findById(gF.getStockTypeId());
        //if (stockType == null) {
        //    req.setAttribute("returnMsg", "Loại hàng hoá không tồn tại trên hệ thống");
        //    req.setAttribute("lstStockModel", lstStockModel);
        //    return pageForward;
        //}

        //trans.setStockTypeId(gF.getStockTypeId());
        //trans.setStockTypeName(stockType.getName());
        //Lay thong tin hang hoa
        if (gF.getStockModelId() == null) {
//            req.setAttribute("returnMsg", "Chưa chọn hàng hoá");
            req.setAttribute("returnMsg", "ERR.STK.025");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        StockModel stockModel = (StockModel) stockModelDAO.findById(gF.getStockModelId());
        if (stockModel == null) {
//            req.setAttribute("returnMsg", "Hàng hoá không tồn tại trên hệ thống.");
            req.setAttribute("returnMsg", "ERR.STK.059");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        /**
         * Get price list
         */
        String importCode = req.getParameter("shopImportedCode");

        if (importCode == null || importCode.trim().equals("")) {
            req.setAttribute("returnMsg", "ERR.STK.079");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        Staff staffImp = staffDAO.findStaffAvailableByStaffCode(importCode.trim());

        if (staffImp == null) {
//            req.setAttribute("returnMsg", "CTV không tồn tại.");
            req.setAttribute("returnMsg", "ERR.STK.080");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }
        Long importID = staffImp.getStaffId();


        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Long shopId = userToken.getShopId();
        PriceDAO priceDAO = new PriceDAO();
        priceDAO.setSession(getSession());
        priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_DEPOSIT);

//        ShopDAO shopDAO = new ShopDAO();
//        shopDAO.setSession(getSession());
//        Shop shop = shopDAO.findById(shopId);
//        String pricePolicy = shop.getPricePolicy();


        String pricePolicy = staffImp.getPricePolicy();
        priceDAO.setPricePolicyFilter(pricePolicy);

        priceDAO.setStockModelIdFilter(gF.getStockModelId());
        List priceList = priceDAO.findPriceForSaleRetail();
        Double price = 0.0;
        Long priceID = 0L;

        if (priceList != null && priceList.size() != 0) {
            Object[] temp = (Object[]) priceList.get(0);
            price = Double.parseDouble(temp[1].toString());
            priceID = Long.parseLong(temp[0].toString());

        } else {
//            req.setAttribute("returnMsg", "Hàng hoá chưa được định nghĩa giá.");
            req.setAttribute("returnMsg", "ERR.STK.078");
            req.setAttribute("lstStockModel", lstStockModel);

            return pageForward;
        }

        trans.setStockModelId(gF.getStockModelId());
        trans.setStockModelCode(stockModel.getStockModelCode());
        trans.setStockModelName(stockModel.getName());
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        AppParams appParams = appParamsDAO.findAppParams("STOCK_MODEL_UNIT", stockModel.getUnit());
        if (appParams != null) {
            trans.setUnit(appParams.getName());
        }

        trans.setCheckSerial(stockModel.getCheckSerial());
        Long checkDial = 0L;
        if (gF.getCheckDial() != null && gF.getCheckDial().equals("true")) {
            checkDial = 1L;
        }

        trans.setCheckDial(checkDial);
        trans.setFromOwnerType(gF.getOwnerType());
        trans.setFromOwnerId(gF.getOwnerId());



        for (int idx = 0; idx < lstGoods.size(); idx++) {
            tmp = (StockTransFull) lstGoods.get(idx);
            if (!tmp.getToOwnerId().equals(importID)) {
                req.setAttribute("returnMsg", "Error. Code of Channel is difference from last selected Channel");
                req.setAttribute("lstStockModel", lstStockModel);
                return pageForward;
            }
        }

        //Check TKTT cua CTV
        AccountBalanceDAO accountBalanceDAO = new AccountBalanceDAO();
        accountBalanceDAO.setSession(getSession());
        String sql_query = "";
        sql_query += " select real_Balance from account_agent ag, account_balance ab";
        sql_query += " where 1 = 1";
        sql_query += " and ag.status = 1";
        sql_query += " and ab.status = 1";
        sql_query += " and ag.account_id = ab.account_id";
        sql_query += " and ab.balance_type = 2";
        sql_query += " and ag.owner_id = ?";
        sql_query += " and ag.owner_type = 2";
        Query query = getSession().createSQLQuery(sql_query);
        query.setParameter(0, importID);
        List listAccount = query.list();
        Double realBalance = 0.0;

        if (listAccount != null) {
            Iterator iterator = listAccount.iterator();

            while (iterator.hasNext()) {
                Object object = (Object) iterator.next();
                if (object != null) {
                    realBalance = Double.parseDouble(object.toString());
                }
            }
        }

        if (listAccount != null && listAccount.size() != 0) {
        } else {
//            req.setAttribute("returnMsg", "CTV chưa có TKTT nên không thể xuất hàng");
            req.setAttribute("returnMsg", "ERR.STK.081");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }
        Double Amount = 0.0;
        if (req.getSession().getAttribute("amount") != null && req.getSession().getAttribute("amount") != "") {
            Amount = Double.parseDouble(req.getSession().getAttribute("amount").toString());
        }
        Amount += price * goodsForm.getQuantity();

        if (Amount.compareTo(realBalance) > 0) {
//            req.setAttribute("returnMsg", "Hàng hóa thêm vào vượt quá giới hạn đặt cọc của TKTT");
            req.setAttribute("returnMsg", "ERR.STK.082");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        } //Check so luong yeu cau ko > MaxDeposit

        // tutm1 : bo check maxdeposit do nghiep vu haiti ko can check nua
//        Long MaxDeposit = GetMaxDeposit(goodsForm.getStockModelId());
//        if (MaxDeposit - goodsForm.getQuantity() < 0) {
////            req.setAttribute("returnMsg", "Số lượng hàng hóa vượt quá số lượng cho phép xuất cho CTV");
//            req.setAttribute("returnMsg", "ERR.STK.083");
//            req.setAttribute("lstStockModel", lstStockModel);
//            return pageForward;
//        }

        //Check so luong yeu cau so voi so luong hang hoa thuc con trong kho
        if (!checkStockGoods(goodsForm.getOwnerId(), goodsForm.getOwnerType(), goodsForm.getStockModelId(), goodsForm.getStateId(), goodsForm.getQuantity(), checkDial, getSession())) {
//            req.setAttribute("returnMsg", "Số lượng hàng hoá trong kho nhỏ hơn số lượng yêu cầu xuất.");
            req.setAttribute("returnMsg", "ERR.STK.077");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }
        //Check tong so luong cua mat hang ko vuot qua so luong max cho phep - Vunt

        // tutm1 : bo check maxdeposit do nghiep vu haiti ko can check nua
//        if (MaxDeposit.compareTo(0L) <= 0) {
////            req.setAttribute("returnMsg", "!!!Lỗi. Số lượng hàng hóa của mặt hàng có thể xuất nhỏ hơn 0");
//            req.setAttribute("returnMsg", "ERR.STK.084");
//            req.setAttribute("lstStockModel", lstStockModel);
//            return pageForward;
//        } else if (!checkStockGoodsDeposit(importID, 2L, goodsForm.getStockModelId(),
//                goodsForm.getStateId(), MaxDeposit - goodsForm.getQuantity(), checkDial, getSession())) {
////            req.setAttribute("returnMsg", "Số lượng hàng hóa thêm vào vượt quá số lượng cho phép xuất cho CTV");
//            req.setAttribute("returnMsg", "ERR.STK.085");
//            req.setAttribute("lstStockModel", lstStockModel);
//            return pageForward;
//        }

//        //Check truong hop xuat hang cho dai ly -->tat ca cac mat hang xuat di phai cung VAT
//        if (goodsForm.getExpType() != null && goodsForm.getExpType().equals("dial")) {
//            if (lstGoods != null && lstGoods.size() > 0) {
//                StockTransFull stockTransFull = (StockTransFull) lstGoods.get(0);
//                Long firstStockModelId = stockTransFull.getStockModelId();
//                String priceType = ResourceBundleUtils.getResource("PRICE_TYPE_AGENT");
//                Long firstVAT = getVATByStockModelIdAndType(firstStockModelId, priceType);
//                if (!checkVAT(firstStockModelId, firstVAT, priceType)) {
//                    req.setAttribute("returnMsg", "Các mặt hàng xuất bán cho đại lý phải khai báo giá và có cùng VAT");
//                    req.setAttribute("lstStockModel", lstStockModel);
//                    return pageForward;
//                }
//
//            }
//        }

//Lay so luong va trang thai hang
        trans.setQuantity(gF.getQuantity());

        trans.setStateId(gF.getStateId());
        trans.setNote(gF.getNote());
//        trans.setTelecomServiceID(gF.getTelecomServiceId());
        trans.setTelecomServiceID(stockModel.getTelecomServiceId());

        trans.setPrice(price);
        trans.setPriceDisplay(NumberUtils.rounđAndFormatAmount(price));
        trans.setPriceId(priceID);

        trans.setToOwnerId(importID);
        trans.setToOwnerType(Constant.OWNER_TYPE_STAFF);
        trans.setChannelTypeId(staffImp.getChannelTypeId());

        lstGoods.add(trans);
        /*
         * Kiem tra tong gia goc HH xuat cho NV + gia tri HH hien tai cua NV <=
         * han muc cua NV
         */
        /*
         * Double sourceAmount = sumAmountByGoodsList(lstGoods); if
         * (sourceAmount != null && sourceAmount >= 0) { if
         * (!checkCurrentDebitWhenImplementTrans(gF.getOwnerId(),
         * gF.getOwnerType(), sourceAmount)) { req.setAttribute("returnMsg",
         * "ERR.LIMIT.0012"); req.setAttribute("lstStockModel", lstStockModel);
         * return pageForward; } } else { req.setAttribute("returnMsg",
         * "ERR.LIMIT.0014"); req.setAttribute("lstStockModel", lstStockModel);
         * return pageForward; }
         */

        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);

        goodsForm.resetForm();
        CalcSumDeposit(lstGoods);
        log.info("# End method addGoods");
        return pageForward;
    }

    //Lay so luong hang dat coc max
    private Long GetMaxDeposit(Long StockModelId) {
        Long MaxStock = -1L;
        String Sqlquery = "select * from stock_deposit where CHANEL_TYPE_ID = 10 and Stock_Model_id = ? and Status = 1";
        Query query = getSession().createSQLQuery(Sqlquery);
        query.setParameter(0, StockModelId);
        List listStock = query.list();




        if (listStock != null && listStock.size() != 0) {
            Object[] temp = (Object[]) listStock.get(0);
            MaxStock = Long.parseLong(temp[2].toString());




        }
        return MaxStock;




    }

    //Tinh tien thue
    private void CalcSumDeposit(List lstGoods) throws Exception {
        HttpServletRequest req = getRequest();
        Double Amount = 0.0;

        for (int i = 0; i < lstGoods.size(); i++) {
            StockTransFull stockTransFull = (StockTransFull) lstGoods.get(i);
            Amount += stockTransFull.getQuantity() * stockTransFull.getPrice();
        }

        String pageId = req.getParameter("pageId");
        req.getSession().setAttribute("amount" + pageId, Amount);
        req.getSession().setAttribute("amountDisplay" + pageId, NumberUtils.rounđAndFormatAmount(Amount));

    }

    /*
     * Author: ThanhNC Date Create: 29/06/2009 Purpose: Check cac mat hang xuat
     * cho dai ly phai cung VAT Parameter: + stockModelId can kiem tra + VAT:
     * VAT can check + priceType: Chinh sach gia cua VAT Return value: + True
     * neu mat hang them vao cung VAT + False neu mat hang them vao khong cung
     * VAT
     */
    public boolean checkVAT(Long stockModelId, Long VAT, String priceType) throws Exception {
        try {
            if (stockModelId == null) {
                return false;




            }
//Lay gia dang hieu luc

            String SQL_SELECT_PRICE = " from Price where stockModelId = ? and type = ? and staDate <= ? and (endDate >= ? or endDate is null) and status = ? ";
            Date currentDate = new Date();
            Query q = getSession().createQuery(SQL_SELECT_PRICE);
            q.setParameter(0, stockModelId);
            q.setParameter(1, priceType);
            q.setParameter(2, currentDate);
            q.setParameter(3, currentDate);
            q.setParameter(4, Constant.STATUS_USE);
            List lstPrice = q.list();




            if (lstPrice.size() == 0) {
                return false;




            }

            Price price = (Price) lstPrice.get(0);
            Long newVAT = price.getVat().longValue();




            if (newVAT == null && VAT == null) {
                return true;




            }

            if (newVAT != null && newVAT.equals(VAT)) {
                return true;




            }

            return false;




        } catch (Exception ex) {
            ex.printStackTrace();




            throw ex;




        }

    }

    /*
     * Author: ThanhNC Date Create: 29/06/2009 Purpose: Check cac mat hang xuat
     * cho dai ly phai cung VAT Parameter: + stockModelId can kiem tra + VAT:
     * VAT can check + priceType: Chinh sach gia cua VAT Return value: + True
     * neu mat hang them vao cung VAT + False neu mat hang them vao khong cung
     * VAT
     */
    public boolean checkVAT(Long stockModelId, Double VAT, String priceType, String pricePolicy) throws Exception {
        try {
            if (stockModelId == null) {
                return false;
            }
//Lay gia dang hieu luc

            String SQL_SELECT_PRICE = " from Price where stockModelId = ? and type = ? and staDate <= ? "
                    + " and (endDate >= ? or endDate is null) and status = ? "
                    + " and pricePolicy = ?  ";
            Date currentDate = getSysdate();
            Query q = getSession().createQuery(SQL_SELECT_PRICE);
            q.setParameter(0, stockModelId);
            q.setParameter(1, priceType);
            q.setParameter(2, currentDate);
            q.setParameter(3, currentDate);
            q.setParameter(4, Constant.STATUS_USE);
            q.setParameter(5, pricePolicy);
            List lstPrice = q.list();
            if (lstPrice.size() == 0) {
                return false;
            }

            Price price = (Price) lstPrice.get(0);
            Double newVAT = price.getVat();
            if (newVAT == null && VAT == null) {
                return true;
            }

            if (newVAT != null && newVAT.equals(VAT)) {
                return true;
            }

            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }
    /*
     * Author: ThanhNC Date Create: 29/06/2009 Purpose: Check cac mat hang xuat
     * cho dai ly phai cung telecom service Parameter: + firstStockModelId can
     * kiem tra + nextStockModelId Return value: + True neu mat hang them vao
     * cung telecom service + False neu mat hang them vao khong cung telecom
     * service
     */

    public boolean checkTelecomService(Long firstStockModelId, Long nextStockModelId) throws Exception {
        try {
            if (firstStockModelId == null || nextStockModelId == null) {
                return false;




            }
//Lay gia dang hieu luc

            String SQL_SELECT_PRICE = " SELECT * FROM stock_model a " + " WHERE a.stock_model_id= ? AND a.telecom_service_id=" + " (SELECT b.telecom_service_id FROM stock_model b WHERE b.stock_model_id = ?)";

            Query q = getSession().createSQLQuery(SQL_SELECT_PRICE);
            q.setParameter(0, firstStockModelId);
            q.setParameter(1, nextStockModelId);

            List lstPrice = q.list();




            if (lstPrice.size() == 0) {
                return false;




            }

            return true;




        } catch (Exception ex) {
            ex.printStackTrace();




            throw ex;




        }

    }

    /*
     * Author: ThanhNC Date create: 29/06/2009 Purpose: Lay VAT bang
     * stockModelId va loai gia priceType
     */
    public Long getVATByStockModelIdAndType(
            Long stockModelId, String priceType) throws Exception {
        try {
            if (stockModelId == null) {
                return null;




            }
//Lay gia dang hieu luc

            String SQL_SELECT_PRICE = " from Price where stockModelId = ? and type = ? and staDate <= ? and (endDate >= ? or endDate is null) and status = ? ";
            Date currentDate = new Date();
            Query q = getSession().createQuery(SQL_SELECT_PRICE);
            q.setParameter(0, stockModelId);
            q.setParameter(1, priceType);
            q.setParameter(2, currentDate);
            q.setParameter(3, currentDate);
            q.setParameter(4, Constant.STATUS_USE);
            List lstPrice = q.list();




            if (lstPrice.size() == 0) {
                return null;




            }

            Price price = (Price) lstPrice.get(0);




            return price.getVat().longValue();




        } catch (Exception ex) {
            ex.printStackTrace();




            throw ex;




        }

    }
    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: xoa hang hoa khoi danh
     * sach hang hoa
     */

    public String delGoods() throws Exception {
        log.info("# Begin method delGoods");
        String pageForward = "listGoods";

        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");



        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");




        } /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");




        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");




        } /*
         *
         */

        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);




        if (lstGoods == null) {
            req.setAttribute("returnMsg", "Danh sách hàng hoá rỗng!");




            return pageForward;




        }

        String stockModelId = (String) QueryCryptUtils.getParameter(req, "stockModelId");
        //String stockTypeId = (String) QueryCryptUtils.getParameter(req, "stockTypeId");
        String stateId = (String) QueryCryptUtils.getParameter(req, "stateId");





        if (stockModelId != null && !"".equals(stockModelId.trim()) && stateId != null && !"".equals(stateId.trim())) {
            Long lStockModelId = Long.parseLong(stockModelId.trim());
            Long lStateId = Long.parseLong(stateId.trim());
            StockTransFull trans;





            for (int i = 0; i
                    < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);




                if (trans.getStockModelId().equals(lStockModelId) && trans.getStateId().equals(lStateId)) {
                    lstGoods.remove(i);




                }

            }

        } else {
//            req.setAttribute("returnMsg", "Chưa chọn hàng hoá để xoá");
            req.setAttribute("returnMsg", "ERR.STK.086");




            return pageForward;




        }

        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);

        /*
         * LamNV add start
         */




        if (revokeColl != null && revokeColl.equals("true")) {
            Double amountTax = getAmountDisplay(lstGoods);
            req.getSession().setAttribute("amountDisplay" + pageId, amountTax.toString());
//            req.getSession().setAttribute("amountDisplay" + pageId, NumberUtils.rounđAndFormatAmount(amountTax).toString());




        }
        /*
         * LamNV add stop
         */

//        req.setAttribute("returnMsg", "Xoá thành công!");
        req.setAttribute("returnMsg", "ERR.STK.087");

        log.info("# End method delGoods");






        return pageForward;




    }

    /*
     * Author: Vunt Date created: 25/09/2009 Purpose: xoa hang hoa khoi danh
     * sach thu hoi
     */
    public String delGoodsRecover() throws Exception {
        log.info("# Begin method delGoods");
        String pageForward = "listGoodsRecover";
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");




        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");




        }

        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);




        if (lstGoods == null) {
//            req.setAttribute("returnMsg", "Danh sách hàng hoá rỗng!");
            req.setAttribute("returnMsg", "ERR.STK.088");




            return pageForward;




        }

        String stockModelId = (String) QueryCryptUtils.getParameter(req, "stockModelId");
        //String stockTypeId = (String) QueryCryptUtils.getParameter(req, "stockTypeId");
        String stateId = (String) QueryCryptUtils.getParameter(req, "stateId");





        if (stockModelId != null && !"".equals(stockModelId.trim()) && stateId != null && !"".equals(stateId.trim())) {
            Long lStockModelId = Long.parseLong(stockModelId.trim());
            //Long lStockTypeId = Long.parseLong(stockTypeId.trim());
            Long lStateId = Long.parseLong(stateId.trim());
            StockTransFull trans;





            for (int i = 0; i
                    < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);




                if (trans.getStockModelId().equals(lStockModelId) && trans.getStateId().equals(lStateId)) {
                    lstGoods.remove(i);




                }

            }

        } else {
//            req.setAttribute("returnMsg", "Chưa chọn hàng hoá để xoá");
            req.setAttribute("returnMsg", "ERR.STK.086");




            return pageForward;




        }

        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
        //CalcSumDeposit(lstGoods);
//        req.setAttribute("returnMsg", "Xoá thành công!");
        req.setAttribute("returnMsg", "ERR.STK.087");
        log.info("# End method delGoods");





        return pageForward;




    }

    /*
     * Author: Vunt Date created: 09/09/2009 Purpose: xoa hang hoa khoi danh
     * sach hang hoa ban cho CTV
     */
    public String delGoodsColl() throws Exception {
        log.info("# Begin method delGoods");
        String pageForward = "listGoods";
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        req.setAttribute("collaborator", "coll");

        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        }

        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
        if (lstGoods == null) {
//            req.setAttribute("returnMsg", "Danh sách hàng hoá rỗng!");
            req.setAttribute("returnMsg", "ERR.STK.088");
            return pageForward;
        }

        String stockModelId = (String) QueryCryptUtils.getParameter(req, "stockModelId");
        //String stockTypeId = (String) QueryCryptUtils.getParameter(req, "stockTypeId");
        String stateId = (String) QueryCryptUtils.getParameter(req, "stateId");

        if (stockModelId != null && !"".equals(stockModelId.trim()) && stateId != null && !"".equals(stateId.trim())) {
            Long lStockModelId = Long.parseLong(stockModelId.trim());
            //Long lStockTypeId = Long.parseLong(stockTypeId.trim());
            Long lStateId = Long.parseLong(stateId.trim());
            StockTransFull trans;

            for (int i = 0; i < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);
                if (trans.getStockModelId().equals(lStockModelId) && trans.getStateId().equals(lStateId)) {
                    lstGoods.remove(i);
                }
            }

        } else {
//            req.setAttribute("returnMsg", "Chưa chọn hàng hoá để xoá");
            req.setAttribute("returnMsg", "ERR.STK.086");
            return pageForward;
        }

        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
        CalcSumDeposit(lstGoods);
//        req.setAttribute("returnMsg", "Xoá thành công!");
        req.setAttribute("returnMsg", "ERR.STK.087");
        log.info("# End method delGoods");
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: prepare edit goods in
     * goods list
     */
    public String prepareEditGoods() throws Exception {
        log.info("# Begin method prepareEditGoods");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");


        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");




        } /*
         * LamNV ADD STOP
         */
        String pageForward = "editGoods";




        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");




        }
        String stockModelId = (String) req.getParameter("stockModelId");
        String stockTypeId = (String) req.getParameter("stockTypeId");
        String stateId = (String) QueryCryptUtils.getParameter(req, "stateId");
        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);




        if (stockModelId != null && !"".equals(stockModelId.trim()) && stockTypeId != null && !"".equals(stockTypeId.trim()) && stateId != null && !"".equals(stateId.trim())) {
            Long lStockModelId = Long.parseLong(stockModelId.trim());
            Long lStockTypeId = Long.parseLong(stockTypeId.trim());
            Long lStateId = Long.parseLong(stateId.trim());
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            List lstStockModel = stockModelDAO.findByProperty(StockModelDAO.STOCK_TYPE_ID, lStockTypeId);
            req.setAttribute("lstStockModel", lstStockModel);
            //req.setAttribute("listItemsCombo", lstStockModel);
            StockTransFull trans;





            for (int i = 0; i
                    < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);




                if (trans.getStockModelId().equals(lStockModelId) && trans.getStockTypeId().equals(lStockTypeId) && trans.getStateId().equals(lStateId)) {
                    goodsForm.setStockTypeId(lStockTypeId);
                    goodsForm.setStockModelId(lStockModelId);
                    goodsForm.setStockModelCode(trans.getStockModelCode());
                    goodsForm.setStockModelName(trans.getStockModelName());




                    if (trans.getCheckDial().equals(1L)) {
                        goodsForm.setCheckDial("true");




                    } else {
                        goodsForm.setCheckDial("false");





                    }

                    goodsForm.setStateId(trans.getStateId());
                    goodsForm.setQuantity(trans.getQuantity().longValue());
                    goodsForm.setNote(trans.getNote());




                }

                if (goodsForm.getStockModelId() != null) {

                    StockModel model = stockModelDAO.findById(goodsForm.getStockModelId());




                    if (model != null) {
                        Long checkDial = model.getCheckDial();




                        if (checkDial != null && checkDial.equals(Constant.MUST_DRAW)) {
                            goodsForm.setCanDial(Constant.EXP_CAN_DIAL);




                        } else {
                            goodsForm.setCanDial(Constant.EXP_NON_DIAL);




                        }

                    }

                }
                //Danh sach loai tai nguyen
                StockTypeDAO stockTypeDAO = new StockTypeDAO();
                stockTypeDAO.setSession(this.getSession());
                List lstStockType = stockTypeDAO.findAllAvailable();
                req.setAttribute("lstStockType", lstStockType);




            }

        } else {
//            req.setAttribute("returnMsg", "Chưa chọn hàng hoá để sửa.");
            req.setAttribute("returnMsg", "ERR.STK.089");




        }

        req.getSession().setAttribute("isEdit" + pageId, true);

        log.info("# End method prepareEditGoods");






        return pageForward;




    }

    /*
     * Author: Vunt Date created: 26/02/2009 Purpose: prepare edit goods in
     * goods list
     */
    public String prepareEditGoodsRecover() throws Exception {
        log.info("# Begin method prepareEditGoods");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        String pageForward = "editGoodsRecover";




        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");




        }
        String stockModelId = (String) req.getParameter("stockModelId");
        String stockTypeId = (String) req.getParameter("stockTypeId");
        String stateId = (String) QueryCryptUtils.getParameter(req, "stateId");
        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);




        if (stockModelId != null && !"".equals(stockModelId.trim()) && stockTypeId != null && !"".equals(stockTypeId.trim()) && stateId != null && !"".equals(stateId.trim())) {
            Long lStockModelId = Long.parseLong(stockModelId.trim());
            Long lStockTypeId = Long.parseLong(stockTypeId.trim());
            Long lStateId = Long.parseLong(stateId.trim());
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            List lstStockModel = stockModelDAO.findByProperty(StockModelDAO.STOCK_TYPE_ID, lStockTypeId);
            req.setAttribute("lstStockModel", lstStockModel);
            //req.setAttribute("listItemsCombo", lstStockModel);
            StockTransFull trans;





            for (int i = 0; i
                    < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);




                if (trans.getStockModelId().equals(lStockModelId) && trans.getStockTypeId().equals(lStockTypeId) && trans.getStateId().equals(lStateId)) {
                    goodsForm.setStockTypeId(lStockTypeId);
                    goodsForm.setStockModelId(lStockModelId);
                    goodsForm.setStockModelCode(trans.getStockModelCode());
                    goodsForm.setStockModelName(trans.getStockModelName());




                    if (trans.getCheckDial().equals(1L)) {
                        goodsForm.setCheckDial("true");




                    } else {
                        goodsForm.setCheckDial("false");





                    }

                    goodsForm.setStateId(trans.getStateId());
                    goodsForm.setQuantity(trans.getQuantity().longValue());
                    goodsForm.setNote(trans.getNote());




                }

                if (goodsForm.getStockModelId() != null) {

                    StockModel model = stockModelDAO.findById(goodsForm.getStockModelId());




                    if (model != null) {
                        Long checkDial = model.getCheckDial();




                        if (checkDial != null && checkDial.equals(Constant.MUST_DRAW)) {
                            goodsForm.setCanDial(Constant.EXP_CAN_DIAL);




                        } else {
                            goodsForm.setCanDial(Constant.EXP_NON_DIAL);




                        }

                    }

                }
                //Danh sach loai tai nguyen
                StockTypeDAO stockTypeDAO = new StockTypeDAO();
                stockTypeDAO.setSession(this.getSession());
                List lstStockType = stockTypeDAO.findAllAvailable();
                req.setAttribute("lstStockType", lstStockType);




            }

        } else {
//            req.setAttribute("returnMsg", "Chưa chọn hàng hoá để sửa.");
            req.setAttribute("returnMsg", "ERR.STK.089");




        }

        req.getSession().setAttribute("isEdit" + pageId, true);

        log.info("# End method prepareEditGoods");






        return pageForward;




    }

    /*
     * Author: Vunt Date created: 09/09/2009 Purpose: prepare edit goods in
     * goods list(CTV)
     */
    public String prepareEditGoodsColl() throws Exception {
        log.info("# Begin method prepareEditGoods");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        String pageForward = "editGoods";
        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        }
        req.setAttribute("collaborator", "coll");
        String stockModelId = (String) req.getParameter("stockModelId");
        //String stockTypeId = (String) req.getParameter("stockTypeId");
        String stateId = (String) QueryCryptUtils.getParameter(req, "stateId");
        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);

        if (stockModelId != null && !"".equals(stockModelId.trim()) && stateId != null && !"".equals(stateId.trim())) {
            Long lStockModelId = Long.parseLong(stockModelId.trim());
            //Long lStockTypeId = Long.parseLong(stockTypeId.trim());
            Long lStateId = Long.parseLong(stateId.trim());
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            //req.setAttribute("listItemsCombo", lstStockModel);
            StockTransFull trans;

            for (int i = 0; i < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);
                if (trans.getStockModelId().equals(lStockModelId) && trans.getStateId().equals(lStateId)) {
                    List lstStockModel = getStockModelInShopCollByTelecomService(trans.getTelecomServiceID());
                    req.setAttribute("lstStockModel", lstStockModel);
                    goodsForm.setStockModelId(lStockModelId);
                    goodsForm.setStockModelCode(trans.getStockModelCode());
                    goodsForm.setStockModelName(trans.getStockModelName());
                    if (trans.getCheckDial().equals(1L)) {
                        goodsForm.setCheckDial("true");
                    } else {
                        goodsForm.setCheckDial("false");
                    }
                    goodsForm.setStateId(trans.getStateId());
                    goodsForm.setQuantity(trans.getQuantity());
                    goodsForm.setNote(trans.getNote());
                    goodsForm.setTelecomServiceId(trans.getTelecomServiceID());


                    if (goodsForm.getStockModelId() != null) {
                        StockModel model = stockModelDAO.findById(goodsForm.getStockModelId());
                        if (model != null) {
                            Long checkDial = model.getCheckDial();
                            if (checkDial != null && checkDial.equals(Constant.MUST_DRAW)) {
                                goodsForm.setCanDial(Constant.EXP_CAN_DIAL);
                            } else {
                                goodsForm.setCanDial(Constant.EXP_NON_DIAL);
                            }
                        }
                    }
//                //Danh sach loai tai nguyen
//                TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
//                telecomServiceDAO.setSession(this.getSession());
//                List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
//                req.setAttribute("lstStockType", lstTelecomService);

                    break;
                }
            }
        } else {
//            req.setAttribute("returnMsg", "Chưa chọn hàng hoá để sửa.");
            req.setAttribute("returnMsg", "ERR.STK.089");
        }
        req.setAttribute("collaborator", "coll");
        req.getSession().setAttribute("isEdit" + pageId, true);
        log.info("# End method prepareEditGoods");
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: cacel sua hang hoa
     */
    public String cancelEditGoods() throws Exception {
        log.info("# Begin method cancelEditGoods");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        String pageForward = "editGoods";



        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");




        }
        goodsForm.resetForm();
        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");




        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");




        }
        /*
         * LamNV ADD STOP
         */
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);
        req.getSession().removeAttribute("isEdit" + pageId);

        log.info("# End method prepareEditGoods");






        return pageForward;




    }

    /*
     * Author: Vunt Date created: 25/09/2009 Purpose: cancel sua hang hoa da add
     * khi thu hoi
     */
    public String cancelEditGoodsRecover() throws Exception {
        log.info("# Begin method cancelEditGoods");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        String pageForward = "editGoodsRecover";
        goodsForm.resetForm();
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);
        req.getSession().removeAttribute("isEdit" + pageId);

        log.info("# End method prepareEditGoods");




        return pageForward;




    }

    /*
     * Author: Vunt Date created: 09/09/2009 Purpose: cancel sua hang hoa da add
     * vao list cho CTV
     */
    public String cancelEditGoodsColl() throws Exception {
        log.info("# Begin method cancelEditGoods");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        String pageForward = "editGoods";
        goodsForm.resetForm();
        //Danh sach loai tai nguyen
        //StockTypeDAO stockTypeDAO = new StockTypeDAO();
        //stockTypeDAO.setSession(this.getSession());
        //List lstStockType = stockTypeDAO.findAllAvailable();
        //req.setAttribute("lstStockType", lstStockType);
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
        req.setAttribute("lstStockType", lstTelecomService);
        req.getSession().removeAttribute("isEdit" + pageId);
        req.setAttribute("collaborator", "coll");
        log.info("# End method prepareEditGoods");




        return pageForward;




    }


    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: edit goods in goods
     * list
     */
    public String editGoods() throws Exception {
        log.info("# Begin method editGoods");
        String pageForward = "editGoods";
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");



        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");




        }

        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);

        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");




        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");




        }
        /*
         * LamNV ADD STOP
         */
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);

        //Danh sach stockModel
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        List lstStockModel = stockModelDAO.findByStockTypeIdAndStatus(goodsForm.getStockTypeId(), Constant.STATUS_USE);





        if (lstGoods != null) {
            Long lStockModelId = goodsForm.getStockModelId();
            Long lStockTypeId = goodsForm.getStockTypeId();
            Long lStateId = goodsForm.getStateId();

            StockTransFull trans = null;




            boolean isOk = false;
            for (int i = 0; i
                    < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);
                if (trans.getStockModelId().equals(lStockModelId) && trans.getStockTypeId().equals(lStockTypeId) && trans.getStateId().equals(lStateId)) {
                    // initFormExport(req);
                    Long checkDial = 0L;
                    if (goodsForm.getCheckDial() != null && goodsForm.getCheckDial().equals("true")) {
                        checkDial = 1L;
                    }

                    if (goodsForm.getQuantity().equals(0L)) {
//                        req.setAttribute("returnMsg", "Số lượng phải là số nguyên dương");
                        req.setAttribute("returnMsg", "ERR.STK.011");
                        req.setAttribute("lstStockModel", lstStockModel);
                        return pageForward;
                    }
//Check so luong yeu cau so voi so luong hang hoa thuc con trong kho

                    if (!checkStockGoods(goodsForm.getOwnerId(), goodsForm.getOwnerType(), goodsForm.getStockModelId(), goodsForm.getStateId(), goodsForm.getQuantity(), checkDial, getSession())) {
//                        req.setAttribute("returnMsg", "Số lượng hàng hoá trong kho nhỏ hơn số lượng yêu cầu xuất.");
                        req.setAttribute("returnMsg", "ERR.STK.077");
                        req.setAttribute("lstStockModel", lstStockModel);
                        return pageForward;
                    }

                    trans.setQuantity(goodsForm.getQuantity());
                    trans.setNote(goodsForm.getNote());
                    trans.setCheckDial(checkDial);
                    /*
                     * LamNV ADD START
                     */

                    if (revokeColl != null && revokeColl.equals("true")) {
                        Double priceDeposit = getPriceDeposit(trans.getStockModelId());
                        Double price = trans.getQuantity() * priceDeposit;
                        trans.setPrice(priceDeposit);
                        trans.setPriceDisplay(NumberUtils.rounđAndFormatAmount(priceDeposit));
                    } /*
                     * LamNV ADD STOP
                     */
                    isOk = true;
                }
            }
            /*
             * Kiem tra tong gia goc HH xuat cho NV + gia tri HH hien tai cua NV
             * <= han muc cua NV
             */
            /*
             * Double sourceAmount = sumAmountByGoodsList(lstGoods); if
             * (sourceAmount != null && sourceAmount >= 0) { if
             * (!checkCurrentDebitWhenImplementTrans(goodsForm.getOwnerId(),
             * goodsForm.getOwnerType(), sourceAmount)) {
             * req.setAttribute("returnMsg", "ERR.LIMIT.0012");
             * req.setAttribute("lstStockModel", lstStockModel); return
             * pageForward; } } else { req.setAttribute("returnMsg",
             * "ERR.LIMIT.0014"); req.setAttribute("lstStockModel",
             * lstStockModel); return pageForward; }
             */
            if (!isOk) {
//                req.setAttribute("returnMsg", "Sửa không thành công. Hàng hoá không tồn tại trong danh sách.");
                req.setAttribute("returnMsg", "ERR.STK.090");
                req.getSession().removeAttribute("isEdit" + pageId);
                goodsForm.resetForm();
                return pageForward;
            }

            req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
            req.getSession().removeAttribute("isEdit" + pageId);
            //expForm.setGoodsForm(new GoodsForm());
//            req.setAttribute("returnMsg", "Sửa thành công.");
            req.setAttribute("returnMsg", "ERR.STK.091");
        } else {
//            req.setAttribute("returnMsg", "Danh sách hàng hoá rỗng. Mặt hàng đã bị xoá khỏi danh sách");
            req.setAttribute("returnMsg", "ERR.STK.092");
        }

        /*
         * LamNV add start
         */
        if (revokeColl != null && revokeColl.equals("true")) {
            Double amountTax = getAmountTax(lstGoods);
            req.getSession().setAttribute("amount" + pageId, amountTax);
            req.getSession().setAttribute("amountDisplay" + pageId, NumberUtils.rounđAndFormatAmount(amountTax));

        } /*
         * LamNV add stop
         */

        goodsForm.resetForm();
        return pageForward;
    }

    /*
     * Author: Vunt Date created: 09/09/2009 Purpose: edit goods in goods list
     * (CTV)
     */
    public String editGoodsRecover() throws Exception {
        log.info("# Begin method editGoods");
        String pageForward = "editGoodsRecover";
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);




        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");




        }

        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);

        //Danh sach stockModel
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        Long importID;
        String importCode = req.getParameter("shopImportedCode");
        importID = Long.parseLong(getShopIdbyShopCode(importCode));

        List lstStockModel = getListStockModelToRecover(goodsForm.getStockTypeId(), importID);





        if (lstGoods != null) {
            Long lStockModelId = goodsForm.getStockModelId();
            //Long lStockTypeId = goodsForm.getStockTypeId();
            Long lStateId = goodsForm.getStateId();

            StockTransFull trans = null;




            boolean isOk = false;




            for (int i = 0; i
                    < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);




                if (trans.getStockModelId().equals(lStockModelId) && trans.getStateId().equals(lStateId)) {
                    // initFormExport(req);
                    Long checkDial = 0L;




                    if (goodsForm.getCheckDial() != null && goodsForm.getCheckDial().equals("true")) {
                        checkDial = 1L;




                    }

                    if (goodsForm.getQuantity().equals(0L)) {
//                        req.setAttribute("returnMsg", "Số lượng phải là số nguyên dương");
                        req.setAttribute("returnMsg", "ERR.STK.011");
                        req.setAttribute("lstStockModel", lstStockModel);




                        return pageForward;




                    }

                    //Check so luong yeu cau so voi so luong hang hoa thuc con trong kho

                    if (!checkStockGoods(importID, 1L, goodsForm.getStockModelId(), goodsForm.getStateId(), goodsForm.getQuantity(), checkDial, getSession())) {
//                        req.setAttribute("returnMsg", "Số lượng hàng hoá trong kho nhỏ hơn số lượng yêu cầu xuất.");
                        req.setAttribute("returnMsg", "ERR.STK.077");
                        req.setAttribute("lstStockModel", lstStockModel);




                        return pageForward;




                    }
                    trans.setQuantity(goodsForm.getQuantity());
                    trans.setNote(goodsForm.getNote());
                    trans.setCheckDial(checkDial);
                    isOk =
                            true;




                }

            }
            if (!isOk) {
//                req.setAttribute("returnMsg", "Sửa không thành công. Hàng hoá không tồn tại trong danh sách.");
                req.setAttribute("returnMsg", "ERR.STK.090");
                req.getSession().removeAttribute("isEdit" + pageId);
                goodsForm.resetForm();




                return pageForward;




            }

            req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
            //CalcSumDeposit(lstGoods);
            req.getSession().removeAttribute("isEdit" + pageId);
            //expForm.setGoodsForm(new GoodsForm());
//            req.setAttribute("returnMsg", "Sửa thành công.");
            req.setAttribute("returnMsg", "ERR.STK.091");




        } else {
//            req.setAttribute("returnMsg", "Danh sách hàng hoá rỗng. Mặt hàng đã bị xoá khỏi danh sách");
            req.setAttribute("returnMsg", "ERR.STK.092");




        }
        goodsForm.resetForm();




        return pageForward;




    }

    /*
     * Author: Vunt Date created: 09/09/2009 Purpose: edit goods in goods list
     * (CTV)
     */
    public String editGoodsColl() throws Exception {
        log.info("# Begin method editGoods");
        String pageForward = "editGoods";
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        //TelecomService
        TelecomServiceDAO telecomServiceDAO = new TelecomServiceDAO();
        telecomServiceDAO.setSession(this.getSession());
        List lstTelecomService = telecomServiceDAO.findByStatus(Constant.STATUS_USE);
        req.setAttribute("collaborator", "coll");
        req.setAttribute("lstStockType", lstTelecomService);




        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");




        }

        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);

        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());


        //Danh sach stockModel
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        List lstStockModel = getStockModelInShopCollByTelecomService(goodsForm.getTelecomServiceId());





        if (lstGoods != null) {
            Long lStockModelId = goodsForm.getStockModelId();
            //Long lStockTypeId = goodsForm.getStockTypeId();
            Long lStateId = goodsForm.getStateId();

            StockTransFull trans = null;




            boolean isOk = false;




            for (int i = 0; i
                    < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);




                if (trans.getStockModelId().equals(lStockModelId) && trans.getStateId().equals(lStateId)) {
                    // initFormExport(req);
                    Long checkDial = 0L;




                    if (goodsForm.getCheckDial() != null && goodsForm.getCheckDial().equals("true")) {
                        checkDial = 1L;




                    }

                    if (goodsForm.getQuantity().equals(0L)) {
//                        req.setAttribute("returnMsg", "Số lượng phải là số nguyên dương");
                        req.setAttribute("returnMsg", "ERR.STK.011");
                        req.setAttribute("lstStockModel", lstStockModel);




                        return pageForward;




                    }
                    Long importID;
                    String importCode = req.getParameter("shopImportedCode");
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(this.getSession());
                    Staff staffImp = staffDAO.findStaffAvailableByStaffCode(importCode.trim());
                    importID = staffImp.getStaffId();
                    // tutm1 : bo check maxdeposit do nghiep vu haiti ko can check nua
//                    Long MaxDeposit = GetMaxDeposit(goodsForm.getStockModelId());
//                    //Check so luong yeu cau ko > MaxDeposit
//
//
//
//
//                    if (MaxDeposit - goodsForm.getQuantity() < 0) {
////                        req.setAttribute("returnMsg", "Số lượng hàng hóa vượt quá số lượng cho phép xuất cho CTV");
//                        req.setAttribute("returnMsg", "ERR.STK.083");
//                        req.setAttribute("lstStockModel", lstStockModel);
//                        return pageForward;
//                    }

                    //Check so luong yeu cau so voi so luong hang hoa thuc con trong kho

                    if (!checkStockGoods(goodsForm.getOwnerId(), goodsForm.getOwnerType(), goodsForm.getStockModelId(), goodsForm.getStateId(), goodsForm.getQuantity(), checkDial, getSession())) {
//                        req.setAttribute("returnMsg", "Số lượng hàng hoá trong kho nhỏ hơn số lượng yêu cầu xuất.");
                        req.setAttribute("returnMsg", "ERR.STK.077");
                        req.setAttribute("lstStockModel", lstStockModel);
                        return pageForward;
                    }

                    //Check tong so luong cua mat hang ko vuot qua so luong max cho phep - Vunt

//                    // tutm1 : bo check maxdeposit do nghiep vu haiti ko can check nua
//                    if (MaxDeposit.compareTo(0L) <= 0) {
////                        req.setAttribute("returnMsg", "!!!Lỗi. Số lượng hàng hóa của mặt hàng có thể xuất nhỏ hơn 0");
//                        req.setAttribute("returnMsg", "ERR.STK.084");
//                        req.setAttribute("lstStockModel", lstStockModel);
//                        return pageForward;
//                    } else if (!checkStockGoodsDeposit(importID, 2L, goodsForm.getStockModelId(),
//                            goodsForm.getStateId(), MaxDeposit - goodsForm.getQuantity(), checkDial, getSession())) {
//                    }



                    trans.setQuantity(goodsForm.getQuantity());
                    trans.setNote(goodsForm.getNote());
                    trans.setCheckDial(checkDial);
                    isOk =
                            true;
                }

            }
            /*
             * Kiem tra tong gia goc HH xuat cho NV + gia tri HH hien tai cua NV
             * <= han muc cua NV
             */
            /*
             * Double sourceAmount = sumAmountByGoodsList(lstGoods); if
             * (sourceAmount != null && sourceAmount >= 0) { if
             * (!checkCurrentDebitWhenImplementTrans(goodsForm.getOwnerId(),
             * goodsForm.getOwnerType(), sourceAmount)) {
             * req.setAttribute("returnMsg", "ERR.LIMIT.0012");
             * req.setAttribute("lstStockModel", lstStockModel); return
             * pageForward; } } else { req.setAttribute("returnMsg",
             * "ERR.LIMIT.0014"); req.setAttribute("lstStockModel",
             * lstStockModel); return pageForward; }
             */
            if (!isOk) {
//                req.setAttribute("returnMsg", "Sửa không thành công. Hàng hoá không tồn tại trong danh sách.");
                req.setAttribute("returnMsg", "ERR.STK.090");
                req.getSession().removeAttribute("isEdit" + pageId);
                goodsForm.resetForm();




                return pageForward;




            }

            req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
            CalcSumDeposit(
                    lstGoods);
            req.getSession().removeAttribute("isEdit" + pageId);
            //expForm.setGoodsForm(new GoodsForm());
//            req.setAttribute("returnMsg", "Sửa thành công.");
            req.setAttribute("returnMsg", "ERR.STK.091");




        } else {
//            req.setAttribute("returnMsg", "Danh sách hàng hoá rỗng. Mặt hàng đã bị xoá khỏi danh sách");
            req.setAttribute("returnMsg", "ERR.STK.092");




        }
        goodsForm.resetForm();




        return pageForward;




    }

    /*
     * ThanhNC Check trung ma lenh nhap
     */
    public boolean checkExpReqCode(String expCode) {
        if ("".equals(expCode.trim())) {
            return false;




        }

        String SQL_SELECT = "from ExpReq where expReqCode= ?";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, expCode);




        if (q.list().size() > 0) {
            return false;




        }

        return true;





    }
    /*
     * ThanhNC Check trung ma phieu nhap
     */

    public boolean checkExpStateCode(String expCode) {
        if ("".equals(expCode.trim())) {
            return false;




        }

        String SQL_SELECT = "from ExpStatetament where expStaCode= ?";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, expCode);




        if (q.list().size() > 0) {
            return false;




        }

        return true;





    }

    /*
     * Author: ThanhNC Date created: 25/03/2009 Purpose: cap nhap so luong hang
     * hoa trong bang stock_total ,neu chua co thi insert thong tin moi khi nhap
     * kho
     */
    public void impStockTotal(Long ownerType, Long ownerId, Long stateId, Long stockModelId, Long quantity) {
        StockTotalId stockTotalId = new StockTotalId();
        StockTotalDAO stockTotalDAO = new StockTotalDAO();
        stockTotalDAO.setSession(this.getSession());
        StockTotal stockTotal = null;
        stockTotalId.setOwnerType(ownerType);
        stockTotalId.setOwnerId(ownerId);
        stockTotalId.setStateId(stateId);
        stockTotalId.setStockModelId(stockModelId);
        stockTotal =
                stockTotalDAO.findById(stockTotalId);
        //Chua co ban ghi nao trong bang stock_total




        if (stockTotal == null) {
            stockTotal = new StockTotal();
            stockTotal.setId(stockTotalId);
            stockTotal.setModifiedDate(new Date());
            stockTotal.setQuantity(quantity);
            stockTotal.setQuantityIssue(quantity);
            //SO luong phai boc tham mac dinh bang 0
            stockTotal.setQuantityDial(0L);
            stockTotal.setStatus(Constant.STATUS_USE);




            this.getSession().save(stockTotal);




        } else {
            stockTotal.setModifiedDate(new Date());
            stockTotal.setQuantity(stockTotal.getQuantity() + quantity);
            stockTotal.setQuantityIssue(stockTotal.getQuantityIssue() + quantity);




            this.getSession().update(stockTotal);




        }

    }

    public boolean impStockTotalDebit(Long ownerType, Long ownerId, Long stateId, Long stockModelId, Long quantity) {
        try {
            StockTotalId stockTotalId = new StockTotalId();
            StockTotalDAO stockTotalDAO = new StockTotalDAO();
            stockTotalDAO.setSession(this.getSession());
            StockTotal stockTotal = null;
            stockTotalId.setOwnerType(ownerType);
            stockTotalId.setOwnerId(ownerId);
            stockTotalId.setStateId(stateId);
            stockTotalId.setStockModelId(stockModelId);
            stockTotal = stockTotalDAO.findById(stockTotalId);
            //Chua co ban ghi nao trong bang stock_total
            if (stockTotal == null) {
                stockTotal = new StockTotal();
                stockTotal.setId(stockTotalId);
                stockTotal.setModifiedDate(new Date());
                stockTotal.setQuantity(quantity);
                stockTotal.setQuantityIssue(quantity);
                //SO luong phai boc tham mac dinh bang 0
                stockTotal.setQuantityDial(0L);
                stockTotal.setStatus(Constant.STATUS_USE);
                this.getSession().save(stockTotal);
            } else {
                String SQL_UPDATE_STOCK_TOTAL = " update Stock_total set quantity =quantity + ?, quantity_issue = quantity_issue + ?, modified_date= sysdate" + " where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? ";
                Query q = getSession().createSQLQuery(SQL_UPDATE_STOCK_TOTAL);
                q.setParameter(0, quantity);
                q.setParameter(1, quantity);
                q.setParameter(2, ownerId);
                q.setParameter(3, ownerType);
                q.setParameter(4, stockModelId);
                q.setParameter(5, stateId);
                int result = q.executeUpdate();
                if (result <= 0) {
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().getTransaction().begin();
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().getTransaction().begin();
        }
        return true;
    }

    /*
     * Author: ThanhNC Date created: 25/03/2009 Purpose: cap nhap so luong hang
     * hoa trong bang stock_total ,neu chua co thi insert thong tin moi khi nhap
     * kho
     */
    public void impStockTotal_collaborator(Long ownerType, Long ownerId, Long stateId, Long stockModelId, Long quantity) {
        StockTotalId stockTotalId = new StockTotalId();
        StockTotalDAO stockTotalDAO = new StockTotalDAO();
        stockTotalDAO.setSession(this.getSession());
        StockTotal stockTotal = null;
        stockTotalId.setOwnerType(ownerType);
        stockTotalId.setOwnerId(ownerId);
        stockTotalId.setStateId(stateId);
        stockTotalId.setStockModelId(stockModelId);
        stockTotal =
                stockTotalDAO.findById(stockTotalId);
        //Chua co ban ghi nao trong bang stock_total




        if (stockTotal == null) {
            stockTotal = new StockTotal();
            stockTotal.setId(stockTotalId);
            stockTotal.setModifiedDate(new Date());
//            stockTotal.setQuantity(quantity);
//            stockTotal.setQuantityIssue(quantity);
            //SO luong phai boc tham mac dinh bang 0
            stockTotal.setQuantityDial(0L);
            stockTotal.setStatus(Constant.STATUS_USE);




            this.getSession().save(stockTotal);




        } else {
            stockTotal.setModifiedDate(new Date());
//            stockTotal.setQuantity(stockTotal.getQuantity() + quantity);
//            stockTotal.setQuantityIssue(stockTotal.getQuantityIssue() + quantity);




            this.getSession().update(stockTotal);




        }

    }

    /*
     * ThanhNC Check trung ma giao dich
     */
    public static boolean checkDuplicateActionCode(String actionCode, Session session) {
//        if ("".equals(actionCode.trim())) {
//            return false;
//        }
//
//        String SQL_SELECT = "from StockTransAction where actionCode= ?";
//        Query q = session.createQuery(SQL_SELECT);
//        q.setParameter(0, actionCode);
//        if (q.list().size() > 0) {
//            return false;
//        }

        return true;





    }

    /*
     * Author: Vunt Date created: 16/09/2009 Purpose: Kiem tra so luong hang hoa
     * trong kho so voi so luong yeu cau
     */
    public static boolean checkStockGoodsDeposit(Long ownerId, Long onwerType, Long stockModelId, Long stateId, Long quantityReq, Long checkDial, Session session) throws Exception {
        try {
            StockTotalId stockTotalId = new StockTotalId();

            stockTotalId.setOwnerId(ownerId);
            stockTotalId.setOwnerType(onwerType);
            stockTotalId.setStockModelId(stockModelId);
            stockTotalId.setStateId(stateId);
            StockTotalDAO stockTotalDAO = new StockTotalDAO();
            stockTotalDAO.setSession(session);
            StockTotal stockTotal = stockTotalDAO.findById(stockTotalId);




            if (stockTotal == null) {
                return true;




            }
//So luong yeu cau lon hon so luong thuc co trong kho
//Neu checkDial =1 --> check so luong hang can boc tham
//Nguoc lai check so luong dap ung khong can boc tham

            Long quantityDial = stockTotal.getQuantityDial();
            Long quantityIssue = stockTotal.getQuantityIssue();




            if (checkDial != null && checkDial.equals(Constant.CHECK_DIAL)) {
                if (quantityDial > quantityReq) {
                    return false;




                }

            } else {
                if (quantityIssue > quantityReq) {
                    return false;




                }

            }

        } catch (Exception ex) {
            ex.printStackTrace();




            throw ex;




        }

        return true;




    }


    /*
     * Author: ThanhNC Date created: 24/03/2009 Purpose: Kiem tra so luong hang
     * hoa trong kho so voi so luong yeu cau
     */
    public static boolean checkStockGoods(Long ownerId, Long onwerType, Long stockModelId, Long stateId, Long quantityReq, Long checkDial, Session session) throws Exception {
        try {
            System.out.println("--> param");
            System.out.println("ownerId=" + ownerId);
            System.out.println("onwerType=" + onwerType);
            System.out.println("stockModelId=" + stockModelId);
            System.out.println("stateId=" + stateId);
            System.out.println("quantityReq=" + quantityReq);
            System.out.println("checkDial=" + checkDial);

            StockTotalId stockTotalId = new StockTotalId();

            stockTotalId.setOwnerId(ownerId);
            stockTotalId.setOwnerType(onwerType);
            stockTotalId.setStockModelId(stockModelId);
            stockTotalId.setStateId(stateId);
            StockTotalDAO stockTotalDAO = new StockTotalDAO();
            stockTotalDAO.setSession(session);
            StockTotal stockTotal = stockTotalDAO.findById(stockTotalId);
            if (stockTotal == null) {
                System.out.println("--> khong tim thay stock total");
                return false;
            }
//So luong yeu cau lon hon so luong thuc co trong kho
//Neu checkDial =1 --> check so luong hang can boc tham
//Nguoc lai check so luong dap ung khong can boc tham

            Long quantityDial = stockTotal.getQuantityDial();
            Long quantityIssue = stockTotal.getQuantityIssue();
            if (checkDial != null && checkDial.equals(Constant.CHECK_DIAL)) {
                if (quantityDial - quantityReq < 0) {
                    System.out.println("--> quantityDial - quantityReq < 0 ");
                    System.out.println("quantityDial=" + quantityDial);
                    System.out.println("quantityReq=" + quantityReq);
                    return false;
                }

            } else {
                if (quantityIssue - quantityReq < 0) {
                    System.out.println("--> quantityIssue - quantityReq < 0 ");
                    System.out.println("quantityIssue=" + quantityDial);
                    System.out.println("quantityReq=" + quantityReq);
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
        return true;
    }

    /*
     * Author: ThanhNC Date created: 24/03/2009 Purpose: Kiem tra so luong hang
     * hoa trong kho so voi so luong yeu cau va cap nhap lai so luong dap ung
     * (quantity_issue) khi xuat kho
     */
    public static boolean checkStockAndLockGoods(Long ownerId, Long onwerType, Long stockModelId, Long stateId, Long quantityReq, Session session) throws Exception {
        try {
            String SQL_UPDATE_STOCK_TOTAL = " update Stock_total set quantity_issue =quantity_issue - ?, modified_date= sysdate" + " where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? and  quantity_issue >= ? and quantity >=  ?";

            Query q = session.createSQLQuery(SQL_UPDATE_STOCK_TOTAL);

            q.setParameter(0, quantityReq);
            q.setParameter(1, ownerId);
            q.setParameter(2, onwerType);
            q.setParameter(3, stockModelId);
            q.setParameter(4, stateId);
            q.setParameter(5, quantityReq);
            q.setParameter(6, quantityReq);
            int result = q.executeUpdate();




            if (result <= 0) {
                session.clear();
                session.getTransaction().rollback();
                session.getTransaction().begin();




                return false;




            }
        } catch (Exception ex) {
            ex.printStackTrace();
            session.clear();
            session.getTransaction().rollback();
            session.getTransaction().begin();




            return false;




        }

        return true;
    }

    /*
     * Author: ThanhNC Date created: 24/03/2009 Param: ownerId: ma kho
     * ownerType: Kieu kho stockModelId: id mat hang stateId: trang thai hang
     * (moi, cu , hong) quantityReq: So luong yeu cau checkDial: Kiem tra tren
     * so luong phai boc tham hay so luong khong phai boc tham session:
     * Hibernate session Purpose: Kiem tra so luong hang hoa trong kho so voi so
     * luong yeu cau va cap nhap lai so luong dap ung (quantity_issue) khi xuat
     * kho
     */
    public static boolean checkStockAndLockGoods(Session sessionInput, Long ownerId, Long onwerType, Long stockModelId,
            Long stateId, Long quantityReq, Long checkDial, Session session) throws Exception {
        /**
         * START log.
         */
        String function = "com.viettel.im.database.DAO.StockCommonDAO.checkStockAndLockGoods";
        Long callId = 111111L;
        Date startDate = new Date();
        logStartCall(startDate, function, callId, "onwerType", onwerType, "ownerId", ownerId,
                "stockModelId", stockModelId, "stateId", stateId, "quantityReq", quantityReq, "checkDial", checkDial);
        /**
         * END log.
         */
        HttpServletRequest req = new BaseDAOAction().getRequest();
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        try {
            String SQL_UPDATE_STOCK_TOTAL = " update Stock_total set quantity_issue =quantity_issue - ?, modified_date= sysdate" + " where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? and  quantity_issue >= ? and quantity >=  ?";
            if (checkDial != null && checkDial.equals(Constant.CHECK_DIAL)) {
                SQL_UPDATE_STOCK_TOTAL = " update Stock_total set quantity_dial =quantity_dial - ?, modified_date= sysdate" + " where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? and quantity_dial >= ? and quantity >=  ?";
            }

            Query q = session.createSQLQuery(SQL_UPDATE_STOCK_TOTAL);
            q.setParameter(0, quantityReq);
            q.setParameter(1, ownerId);
            q.setParameter(2, onwerType);
            q.setParameter(3, stockModelId);
            q.setParameter(4, stateId);
            q.setParameter(5, quantityReq);
            q.setParameter(6, quantityReq);
            int result = q.executeUpdate();
            if (result <= 0) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(quantityReq));
                lstError.add(new ShowMessage("stock.error.quantity.notEnough", listParams));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.getTransaction().begin();
                logEndCall(startDate, new Date(), function, callId);
                return false;
            }
        } catch (Exception ex) {
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute("lstError", lstError);
            ex.printStackTrace();
            session.clear();
            session.getTransaction().rollback();
            session.getTransaction().begin();
            logEndCall(startDate, new Date(), function, callId);
            return false;
        }
        logEndCall(startDate, new Date(), function, callId);
        return true;

    }

    /*
     * Author: vunt Date created: 17/09/2010 Purpose: cong lai so luong dap ung
     */
    public static boolean addQuantityIssue(Long ownerId, Long onwerType, Long stockModelId, Long stateId, Long quantityReq, Session session) throws Exception {
        try {
            String SQL_UPDATE_STOCK_TOTAL = " update stock_total set quantity_issue =quantity_issue + ?, modified_date= sysdate"
                    + " where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? ";

            Query q = session.createSQLQuery(SQL_UPDATE_STOCK_TOTAL);
            q.setParameter(0, quantityReq);
            q.setParameter(1, ownerId);
            q.setParameter(2, onwerType);
            q.setParameter(3, stockModelId);
            q.setParameter(4, stateId);
            int result = q.executeUpdate();
            if (result <= 0) {
                session.clear();
                session.getTransaction().rollback();
                session.getTransaction().begin();
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            session.clear();
            session.getTransaction().rollback();
            session.getTransaction().begin();
            return false;
        }

        return true;
    }
    /*
     * Author: ThanhNC Date created: 24/03/2009 Param: ownerId: ma kho
     * ownerType: Kieu kho stockModelId: id mat hang stateId: trang thai hang
     * (moi, cu , hong) quantityReq: So luong yeu cau checkDial: Kiem tra tren
     * so luong phai boc tham hay so luong khong phai boc tham session:
     * Hibernate session Purpose: Kiem tra so luong hang hoa trong kho so voi so
     * luong yeu cau va cap nhap lai so luong dap ung (quantity_issue) khi xuat
     * kho
     */

    public static boolean checkStockAndLockGoods(Long ownerId, Long onwerType, Long stockModelId,
            Long stateId, Long quantityReq, Long checkDial, Session session) throws Exception {
        HttpServletRequest req = new BaseDAOAction().getRequest();
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        try {
            String SQL_UPDATE_STOCK_TOTAL = " update Stock_total set quantity_issue =quantity_issue - ?, modified_date= sysdate" + " where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? and  quantity_issue >= ? and quantity >=  ?";
            if (checkDial != null && checkDial.equals(Constant.CHECK_DIAL)) {
                SQL_UPDATE_STOCK_TOTAL = " update Stock_total set quantity_dial =quantity_dial - ?, modified_date= sysdate" + " where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? and quantity_dial >= ? and quantity >=  ?";
            }
            Query q = session.createSQLQuery(SQL_UPDATE_STOCK_TOTAL);
            q.setParameter(0, quantityReq);
            q.setParameter(1, ownerId);
            q.setParameter(2, onwerType);
            q.setParameter(3, stockModelId);
            q.setParameter(4, stateId);
            q.setParameter(5, quantityReq);
            q.setParameter(6, quantityReq);
            int result = q.executeUpdate();
            if (result <= 0) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(quantityReq));
                lstError.add(new ShowMessage("stock.error.quantity.notEnough", listParams));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.getTransaction().begin();
                return false;
            }
        } catch (Exception ex) {
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute("lstError", lstError);
            ex.printStackTrace();
            session.clear();
            session.getTransaction().rollback();
            session.getTransaction().begin();
            return false;
        }

        return true;
    }


    /*
     * Author: ThanhNC Date created: 25/03/2009 Purpose: cap nhap so quantity
     * stock_total khi thuc xuat kho
     */
    public boolean expStockTotal(Long ownerType, Long ownerId, Long stateId, Long stockModelId, Long quantity, boolean subtractQuantityIssue) {
        req = getRequest();
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        try {
            String SQL_UPDATE_STOCK_TOTAL = " update Stock_total set quantity =quantity - ?, modified_date= sysdate" + " where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? and  quantity >= ?";
            if (subtractQuantityIssue) {
                SQL_UPDATE_STOCK_TOTAL = " update Stock_total set quantity =quantity - ?, "
                        + " quantity_issue = quantity_issue - ?, modified_date= sysdate "
                        + " where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? and quantity >= ? and quantity_issue >= ? ";
            }
            Query q = getSession().createSQLQuery(SQL_UPDATE_STOCK_TOTAL);
            if (subtractQuantityIssue) {
                q.setParameter(0, quantity);
                q.setParameter(1, quantity);
                q.setParameter(2, ownerId);
                q.setParameter(3, ownerType);
                q.setParameter(4, stockModelId);
                q.setParameter(5, stateId);
                q.setParameter(6, quantity);
                q.setParameter(7, quantity);
            } else {
                q.setParameter(0, quantity);
                q.setParameter(1, ownerId);
                q.setParameter(2, ownerType);
                q.setParameter(3, stockModelId);
                q.setParameter(4, stateId);
                q.setParameter(5, quantity);
            }

            int result = q.executeUpdate();
            if (result <= 0) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(quantity));
                lstError.add(new ShowMessage("stock.error.quantity.notEnough", listParams));
                req.setAttribute("lstError", lstError);
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().getTransaction().begin();
                return false;
            }
        } catch (Exception ex) {
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute("lstError", lstError);
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().getTransaction().begin();
            return false;
        }
        return true;
    }

    public ExportStockForm getExportStockForm() {
        return exportStockForm;




    }

    public void setExportStockForm(ExportStockForm exportStockForm) {
        this.exportStockForm = exportStockForm;




    }

    public GoodsForm getGoodsForm() {
        return goodsForm;




    }

    public void setGoodsForm(GoodsForm goodsForm) {
        this.goodsForm = goodsForm;




    }

    public SerialGoods getSerialGoods() {
        return serialGoods;




    }

    public void setSerialGoods(SerialGoods serialGoods) {
        this.serialGoods = serialGoods;




    }

    /*
     * Author: ThanhNC Date created: 25/03/2009 Purpose: cap nhap so quantity
     * stock_total khi xuat kho cho nhan vien
     */
    public boolean expStockStaffTotal(Long ownerType, Long ownerId, Long stateId, Long stockModelId, Long quantity) {
        try {
            String SQL_UPDATE_STOCK_TOTAL = " update Stock_total set quantity =quantity - ?, "
                    + " quantity_issue = quantity_issue - ?, modified_date= sysdate" + " where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? and quantity >= ? "
                    + " and quantity_issue >= ? ";
            Query q = getSession().createSQLQuery(SQL_UPDATE_STOCK_TOTAL);
            q.setParameter(0, quantity);
            q.setParameter(1, quantity);
            q.setParameter(2, ownerId);
            q.setParameter(3, ownerType);
            q.setParameter(4, stockModelId);
            q.setParameter(5, stateId);
            q.setParameter(6, quantity);
            q.setParameter(7, quantity);
            int result = q.executeUpdate();
            if (result <= 0) {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().getTransaction().begin();
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().getTransaction().begin();
            return false;
        }
        return true;
    }

    //chi tru quantity
    public boolean expStockStaffTotalOnly(Long ownerType, Long ownerId, Long stateId, Long stockModelId, Long quantity) {
        try {
            String SQL_UPDATE_STOCK_TOTAL = " update Stock_total set quantity =quantity - ?, " + ", modified_date= sysdate" + " where owner_id = ? and owner_type = ? and stock_model_id = ? and state_id = ? and quantity >= ? " + " and quantity_issue >= ? ";
            Query q = getSession().createSQLQuery(SQL_UPDATE_STOCK_TOTAL);
            q.setParameter(0, quantity);
            q.setParameter(1, ownerId);
            q.setParameter(2, ownerType);
            q.setParameter(3, stockModelId);
            q.setParameter(4, stateId);
            q.setParameter(5, quantity);
            q.setParameter(6, quantity);
            int result = q.executeUpdate();
            if (result <= 0) {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().getTransaction().begin();
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().getTransaction().begin();
            return false;
        }
        return true;
    }


    /*
     * Author: Vunt Date created: 11/03/2009 Purpose: cap nhap so issue
     * stock_total khi thu hoi hang
     */
    public boolean expStockIssue(Long ownerType, Long ownerId, Long stateId, Long stockModelId, Long quantity) {
        try {
            String SQL_UPDATE_STOCK_TOTAL = " update Stock_total set quantity_issue =quantity_issue - ?, "
                    + "modified_date= sysdate" + " where owner_id = ? and owner_type = ? and stock_model_id = ? "
                    + " and state_id = ? and  quantity_issue >= ? and quantity >=  ?";

            Query q = getSession().createSQLQuery(SQL_UPDATE_STOCK_TOTAL);
            q.setParameter(0, quantity);
            q.setParameter(1, ownerId);
            q.setParameter(2, ownerType);
            q.setParameter(3, stockModelId);
            q.setParameter(4, stateId);
            q.setParameter(5, quantity);
            q.setParameter(6, quantity);




            int result = q.executeUpdate();




            if (result <= 0) {
                getSession().clear();
                getSession().getTransaction().rollback();
                getSession().getTransaction().begin();




                return false;




            }
        } catch (Exception ex) {
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().getTransaction().begin();




            return false;




        }
        return true;





    }

    /*
     * Author: LamNV Date created: 14/03/2010 Purpose: cap nhap so issue
     * stock_total khi thu hoi hang
     */
    public boolean injectStockIssue(Long ownerType, Long ownerId, Long stateId, Long stockModelId, Long quantity) {
        StockTotalId stockTotalId = new StockTotalId();
        StockTotalDAO stockTotalDAO = new StockTotalDAO();
        stockTotalDAO.setSession(this.getSession());
        StockTotal stockTotal = null;
        stockTotalId.setOwnerType(ownerType);
        stockTotalId.setOwnerId(ownerId);
        stockTotalId.setStateId(stateId);
        stockTotalId.setStockModelId(stockModelId);
        stockTotal = stockTotalDAO.findById(stockTotalId);
        //Chua co ban ghi nao trong bang stock_total




        if (stockTotal == null) {
            return false;




        }

        stockTotal.setModifiedDate(new Date());




        if ((stockTotal.getQuantity() - quantity < 0) || (stockTotal.getQuantityIssue() - quantity < 0)) {
            return false;




        }
        stockTotal.setQuantityIssue(stockTotal.getQuantityIssue() - quantity);
        getSession().update(stockTotal);




        return true;





    }

    /*
     * Author: LamNV Date created: 07/11/2009 Purpose: cap nhap so issue
     * stock_total khi thu hoi hang
     */
    public boolean rejectExpStockIssue(Long ownerType, Long ownerId, Long stateId, Long stockModelId, Long quantity) {
        StockTotalId stockTotalId = new StockTotalId();
        StockTotalDAO stockTotalDAO = new StockTotalDAO();
        stockTotalDAO.setSession(this.getSession());
        StockTotal stockTotal = null;
        stockTotalId.setOwnerType(ownerType);
        stockTotalId.setOwnerId(ownerId);
        stockTotalId.setStateId(stateId);
        stockTotalId.setStockModelId(stockModelId);
        stockTotal = stockTotalDAO.findById(stockTotalId);
        //Chua co ban ghi nao trong bang stock_total




        if (stockTotal == null) {
            return false;




        }

        stockTotal.setModifiedDate(new Date());
        stockTotal.setQuantityIssue(stockTotal.getQuantityIssue() + quantity);
        getSession().update(stockTotal);




        return true;





    }
    /*
     * Author: ThanhNC Date created: 03/06/2009 Purpose: Prepare page quan ly
     * giao dich xuat nhap kho
     */

    public String prepareStockManagment() throws Exception {
        log.info("# Begin method StockCommonDAO.prepareStockManagment");
        HttpServletRequest req = this.getRequest();
        String pageId = req.getParameter("pageId");
        //req.getSession().removeAttribute("lstSearchStockTrans"+pageId);
        String pageForward = "stockManagment";
        String DATE_FORMAT = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        Calendar cal = Calendar.getInstance();
        exportStockForm.setToDate(sdf.format(cal.getTime()));
        //cal.add(Calendar.DAY_OF_MONTH, -10); // roll down, substract 10 day
        exportStockForm.setFromDate(sdf.format(cal.getTime()));

        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());




        if (shop != null) {
            exportStockForm.setShopCode(shop.getShopCode());
            exportStockForm.setShopName(shop.getName());




        }
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        Staff staff = staffDAO.findById(userToken.getUserID());




        if (staff != null) {
            exportStockForm.setStaffCode(staff.getStaffCode());
            exportStockForm.setStaffName(staff.getName());
            exportStockForm.setUserCode(staff.getStaffCode());
            exportStockForm.setUserName(staff.getName());
        }
        searchTrans();
        pageForward = "stockManagment";
        log.info("# End method StockCommonDAO.prepareStockManagment");
        return pageForward;




    }
    /*
     * Author: ThanhNC Date created: 04/06/2009 Purpose: Phan trang danh sach
     * giao dich tim dc trong chuc nang quan ly giao dich
     */

    public String pageNavigator() throws Exception {
        log.info("# Begin method StockCommonDAO.pageNavigator");
        String pageForward = "stockManagmentSearchResult";
        log.info("# End method StockCommonDAO.pageNavigator");




        return pageForward;




    }
    /*
     * Author: ThanhNC Date created: 03/06/2009 Purpose: Tim kiem giao dich xuat
     * nhap khoc Chi tim nhung giao dich xuat nhap voi kho cua hang cua user
     * dang nhap (hoac kho cua nhan vien dang nhap)
     */

    public String searchTrans() throws Exception {
        log.info("# Begin method StockCommonDAO.searchTrans");
        HttpServletRequest req = this.getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        exportStockForm.setCmdExportCode("");
        req.getSession().removeAttribute("lstSearchStockTrans" + pageId);
        Session session = getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        String SQL_SELECT_TRANS_DETAIL = "from SearchStockTrans where 1=1 ";// ((stockTransType = ? and fromOwnerId = ? ) or(stockTransType=? and toOwnerId = ?) )";
        List lstParameter = new ArrayList();
        if (exportStockForm.getTransType() != null) {
            SQL_SELECT_TRANS_DETAIL += " and stockTransType= ? ";
            lstParameter.add(exportStockForm.getTransType());
        }
        if (exportStockForm.getActionCode() != null && !"".equals(exportStockForm.getActionCode())) {
            SQL_SELECT_TRANS_DETAIL += " and lower(id.actionCode)= ? ";
            lstParameter.add(exportStockForm.getActionCode().trim().toLowerCase());
        }

        if (exportStockForm.getActionType() != null && !exportStockForm.getActionType().equals(0L)) {
            SQL_SELECT_TRANS_DETAIL += " and actionType= ? ";
            lstParameter.add(exportStockForm.getActionType());
        }

        if (exportStockForm.getTransStatus() != null && !exportStockForm.getTransStatus().equals(0L)) {
            SQL_SELECT_TRANS_DETAIL += " and stockTransStatus= ? ";
            lstParameter.add(exportStockForm.getTransStatus());
        }
        Date dfromDate = new Date();
        if (exportStockForm.getFromDate() != null && !"".equals(exportStockForm.getFromDate())) {
            dfromDate = DateTimeUtils.convertStringToDate(exportStockForm.getFromDate());
            if (dfromDate != null) {
                SQL_SELECT_TRANS_DETAIL += " and createDatetime >= ? ";
                lstParameter.add(dfromDate);
            }

        }
        Date dtoDate = new Date();
        if (exportStockForm.getToDate() != null && !"".equals(exportStockForm.getToDate())) {
            String stoDate = exportStockForm.getToDate().substring(0, 10) + " 23:59:59";
            dtoDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");
            if (dtoDate != null) {
                SQL_SELECT_TRANS_DETAIL += " and createDatetime <= ? ";
                lstParameter.add(dtoDate);
            }

        }
        int distance = DateTimeUtils.distanceBeetwen2Date(dfromDate, dtoDate);

        if (distance > Constant.SEARCH_MAX_DISTANCE_DAYS) {
            req.setAttribute("searchMsg", "Warning! FROM_DATE and TO_DATE must less than " + String.valueOf(Constant.SEARCH_MAX_DISTANCE_DAYS) + " days");
            String pageForward = "stockManagmentSearchResult";
            log.info("# End method StockCommonDAO.searchTrans");
            return pageForward;
        }

        if (exportStockForm.getTransStatus() != null) {
            SQL_SELECT_TRANS_DETAIL += " and stockTransStatus = ? ";
            lstParameter.add(exportStockForm.getTransStatus());
        }

        /*
         * Neu truong doi tuong nhan va kho nhan deu khong nhap Tim tat ca cac
         * giao dich xuat, nhap cho kho (cua hang) cua nhan vien dang nhap va
         * xuat cho chinh nhan vien do) fromOwnerId hoac toOwnerId la kho cua
         * hang cua user dang nhap hoac kho cua nhan vien dang nhap
         */
        if (exportStockForm.getFromOwnerType() == null && exportStockForm.getFromOwnerId() == null) {
            SQL_SELECT_TRANS_DETAIL += " and  (  (fromOwnerType = ? and fromOwnerId = ?)"
                    + " or (toOwnerType = ? and toOwnerId = ?) or (fromOwnerType = ? and fromOwnerId = ?)"
                    + " or (toOwnerType = ? and toOwnerId = ?)  )";

            lstParameter.add(Constant.OWNER_TYPE_SHOP);
            lstParameter.add(userToken.getShopId());

            lstParameter.add(Constant.OWNER_TYPE_SHOP);
            lstParameter.add(userToken.getShopId());

            lstParameter.add(Constant.OWNER_TYPE_STAFF);
            lstParameter.add(userToken.getUserID());

            lstParameter.add(Constant.OWNER_TYPE_STAFF);
            lstParameter.add(userToken.getUserID());
        }

        if (exportStockForm.getFromOwnerType() != null && exportStockForm.getFromOwnerId() == null) {
            SQL_SELECT_TRANS_DETAIL += " and  (  ( ((fromOwnerType = ? and fromOwnerId = ?)"
                    + " or (fromOwnerType = ? and fromOwnerId = ?))" + " and toOwnerType = ? ) "
                    + " or ( ( (toOwnerType = ? and toOwnerId = ?)"
                    + " or (toOwnerType = ? and toOwnerId = ?) )and fromOwnerType= ?) )";

            // lstParameter.add(Constant.TRANS_EXPORT);
            lstParameter.add(Constant.OWNER_TYPE_SHOP);
            lstParameter.add(userToken.getShopId());
            lstParameter.add(Constant.OWNER_TYPE_STAFF);
            lstParameter.add(userToken.getUserID());
            lstParameter.add(exportStockForm.getFromOwnerType());

            // lstParameter.add(Constant.TRANS_IMPORT);
            lstParameter.add(Constant.OWNER_TYPE_SHOP);
            lstParameter.add(userToken.getShopId());
            lstParameter.add(Constant.OWNER_TYPE_STAFF);
            lstParameter.add(userToken.getUserID());
            lstParameter.add(exportStockForm.getFromOwnerType());
        }

        if (exportStockForm.getFromOwnerType() != null && exportStockForm.getFromOwnerId() != null) {
            SQL_SELECT_TRANS_DETAIL +=
                    " and (( ( (fromOwnerType = ? and fromOwnerId = ?) "
                    + "          or (fromOwnerType = ? and fromOwnerId = ?) "
                    + "        )  and toOwnerType =? and toOwnerId=? "
                    + "      )  "
                    + "      or  ("
                    + "           (   (toOwnerType = ? and toOwnerId = ?) "
                    + "           or (toOwnerType = ? and toOwnerId = ?)"
                    + "           ) and fromOwnerType= ? and fromOwnerId= ? "
                    + "          ) "
                    + "      )";

            // lstParameter.add(Constant.TRANS_EXPORT);
            lstParameter.add(Constant.OWNER_TYPE_SHOP);
            lstParameter.add(userToken.getShopId());
            lstParameter.add(Constant.OWNER_TYPE_STAFF);
            lstParameter.add(userToken.getUserID());
            lstParameter.add(exportStockForm.getFromOwnerType());
            lstParameter.add(exportStockForm.getFromOwnerId());

            //  lstParameter.add(Constant.TRANS_IMPORT);
            lstParameter.add(Constant.OWNER_TYPE_SHOP);
            lstParameter.add(userToken.getShopId());
            lstParameter.add(Constant.OWNER_TYPE_STAFF);
            lstParameter.add(userToken.getUserID());
            lstParameter.add(exportStockForm.getFromOwnerType());
            lstParameter.add(exportStockForm.getFromOwnerId());
        }

        SQL_SELECT_TRANS_DETAIL += " order by createDatetime desc ";
        Query q = session.createQuery(SQL_SELECT_TRANS_DETAIL);
        q.setMaxResults(Constant.SEARCH_MAX_RESULT);
        for (int idx = 0; idx < lstParameter.size(); idx++) {
            q.setParameter(idx, lstParameter.get(idx));
        }

        List lstSearchStockTrans = q.list();
        //req.getSession().setAttribute("lstSearchStockTrans"+pageId, lstSearchStockTrans);
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);
        String pageForward = "stockManagmentSearchResult";
        log.info("# End method StockCommonDAO.searchTrans");
        return pageForward;
    }


    /*
     * Author: TrongLV Date created: 15/09/2010 Purpose: Tim kiem giao dich xuat
     * nhap kho Copy tu method searchTrans Cho phep tim theo ma don vi xuất/nhập
     * & mã nhân viên xuất/nhập
     */
    public String searchTransHaiti() throws Exception {
        log.info("# Begin method StockCommonDAO.searchTrans");
        String pageForward = "stockManagmentSearchResult";

        HttpServletRequest req = this.getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);
        exportStockForm.setCmdExportCode("");
        req.getSession().removeAttribute("lstSearchStockTrans" + pageId);
        Session session = getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        String SQL_SELECT_TRANS_DETAIL = "from SearchStockTrans where 1=1 ";// ((stockTransType = ? and fromOwnerId = ? ) or(stockTransType=? and toOwnerId = ?) )";
        List lstParameter = new ArrayList();

        if (exportStockForm.getTransType() != null) {
            SQL_SELECT_TRANS_DETAIL += " and stockTransType= ? ";
            lstParameter.add(exportStockForm.getTransType());

        }

        if (exportStockForm.getActionCode() != null && !"".equals(exportStockForm.getActionCode())) {
            SQL_SELECT_TRANS_DETAIL += " and lower(id.actionCode)= ? ";
            lstParameter.add(exportStockForm.getActionCode().trim().toLowerCase());




        }

        if (exportStockForm.getActionType() != null && !exportStockForm.getActionType().equals(0L)) {
            SQL_SELECT_TRANS_DETAIL += " and actionType= ? ";
            lstParameter.add(exportStockForm.getActionType());




        }

        if (exportStockForm.getTransStatus() != null && !exportStockForm.getTransStatus().equals(0L)) {
            SQL_SELECT_TRANS_DETAIL += " and stockTransStatus= ? ";
            lstParameter.add(exportStockForm.getTransStatus());




        }

        if (exportStockForm.getFromDate() != null && !"".equals(exportStockForm.getFromDate())) {

            Date dfromDate = DateTimeUtils.convertStringToDate(exportStockForm.getFromDate());




            if (dfromDate != null) {
                SQL_SELECT_TRANS_DETAIL += " and createDatetime >= ? ";
                lstParameter.add(dfromDate);




            }

        }
        if (exportStockForm.getToDate() != null && !"".equals(exportStockForm.getToDate())) {
            String stoDate = exportStockForm.getToDate().substring(0, 10) + " 23:59:59";
            Date dtoDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");





            if (dtoDate != null) {
                SQL_SELECT_TRANS_DETAIL += " and createDatetime <= ? ";
                lstParameter.add(dtoDate);




            }

        }
        if (exportStockForm.getTransStatus() != null) {
            SQL_SELECT_TRANS_DETAIL += " and stockTransStatus = ? ";
            lstParameter.add(exportStockForm.getTransStatus());




        }

        /*
         * Neu truong doi tuong nhan va kho nhan deu khong nhap Tim tat ca cac
         * giao dich xuat, nhap cho kho (cua hang) cua nhan vien dang nhap va
         * xuat cho chinh nhan vien do) fromOwnerId hoac toOwnerId la kho cua
         * hang cua user dang nhap hoac kho cua nhan vien dang nhap
         */

        /*
         * Bo cach tim kiem cu
         *
         * if (exportStockForm.getFromOwnerType() == null &&
         * exportStockForm.getFromOwnerId() == null) { SQL_SELECT_TRANS_DETAIL
         * += " and ( (fromOwnerType = ? and fromOwnerId = ?)" + " or
         * (toOwnerType = ? and toOwnerId = ?) or (fromOwnerType = ? and
         * fromOwnerId = ?)" + " or (toOwnerType = ? and toOwnerId = ?) )";
         *
         * lstParameter.add(Constant.OWNER_TYPE_SHOP);
         * lstParameter.add(userToken.getShopId());
         *
         * lstParameter.add(Constant.OWNER_TYPE_SHOP);
         * lstParameter.add(userToken.getShopId());
         *
         * lstParameter.add(Constant.OWNER_TYPE_STAFF);
         * lstParameter.add(userToken.getUserID());
         *
         * lstParameter.add(Constant.OWNER_TYPE_STAFF);
         * lstParameter.add(userToken.getUserID());
         *
         *
         *
         * }
         *
         *
         * if (exportStockForm.getFromOwnerType() != null &&
         * exportStockForm.getFromOwnerId() == null) { SQL_SELECT_TRANS_DETAIL
         * += " and ( ( ((fromOwnerType = ? and fromOwnerId = ?) or
         * (fromOwnerType = ? and fromOwnerId = ?))" + " and toOwnerType = ? ) "
         * + " or ( ( (toOwnerType = ? and toOwnerId = ?)" + " or (toOwnerType =
         * ? and toOwnerId = ?) )and fromOwnerType= ?) )";
         *
         * // lstParameter.add(Constant.TRANS_EXPORT);
         * lstParameter.add(Constant.OWNER_TYPE_SHOP);
         * lstParameter.add(userToken.getShopId());
         * lstParameter.add(Constant.OWNER_TYPE_STAFF);
         * lstParameter.add(userToken.getUserID());
         * lstParameter.add(exportStockForm.getFromOwnerType());
         *
         * // lstParameter.add(Constant.TRANS_IMPORT);
         * lstParameter.add(Constant.OWNER_TYPE_SHOP);
         * lstParameter.add(userToken.getShopId());
         * lstParameter.add(Constant.OWNER_TYPE_STAFF);
         * lstParameter.add(userToken.getUserID());
         * lstParameter.add(exportStockForm.getFromOwnerType());
         *
         *
         * }
         *
         * if (exportStockForm.getFromOwnerType() != null &&
         * exportStockForm.getFromOwnerId() != null) { SQL_SELECT_TRANS_DETAIL
         * += " and (( ( (fromOwnerType = ? and fromOwnerId = ?) " + " or
         * (fromOwnerType = ? and fromOwnerId = ?) " + " ) and toOwnerType =?
         * and toOwnerId=? " + " ) " + " or (" + " ( (toOwnerType = ? and
         * toOwnerId = ?) " + " or (toOwnerType = ? and toOwnerId = ?)" + " )
         * and fromOwnerType= ? and fromOwnerId= ? " + " ) " + " )";
         *
         * // lstParameter.add(Constant.TRANS_EXPORT);
         * lstParameter.add(Constant.OWNER_TYPE_SHOP);
         * lstParameter.add(userToken.getShopId());
         * lstParameter.add(Constant.OWNER_TYPE_STAFF);
         * lstParameter.add(userToken.getUserID());
         * lstParameter.add(exportStockForm.getFromOwnerType());
         * lstParameter.add(exportStockForm.getFromOwnerId());
         *
         * // lstParameter.add(Constant.TRANS_IMPORT);
         * lstParameter.add(Constant.OWNER_TYPE_SHOP);
         * lstParameter.add(userToken.getShopId());
         * lstParameter.add(Constant.OWNER_TYPE_STAFF);
         * lstParameter.add(userToken.getUserID());
         * lstParameter.add(exportStockForm.getFromOwnerType());
         * lstParameter.add(exportStockForm.getFromOwnerId());
         *
         *
         * }
         *
         */


        /*
         * Thay the bang cach tim kiem moi
         */
        if (exportStockForm.getShopCode() != null && !exportStockForm.getShopCode().equals("")) {
            Long ownerIdS = 0L;
            Long ownerTypeS = 0L;
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(getSession());
            List<Shop> listShop = shopDAO.findByPropertyWithStatus("shopCode", exportStockForm.getShopCode().trim(), 1L);




            if (listShop == null || listShop.isEmpty()) {
                req.setAttribute("resultUpdateSale", "Lỗi! Mã đơn vị không chính xác");




                return pageForward;




            }

            String sql = " select shopId from Shop where status = ? and (shopId = ? or shopPath like ? escape '$' ) and shopId = ? ";
            Query q = getSession().createQuery(sql);
            q.setParameter(0, Constant.STATUS_USE);
            q.setParameter(1, userToken.getShopId());
            q.setParameter(2, "%$_" + userToken.getShopId() + "$_%");
            q.setParameter(3, userToken.getShopId());
            List listTemp = q.list();




            if (listTemp == null || listTemp.isEmpty()) {
                req.setAttribute("resultUpdateSale", "Lỗi! Đơn vị không thuộc cấp dưới của đơn vị hiện tại");




                return pageForward;




            }
            //Tim cac giao dich lien quan den don vi: xuat nhap voi cap tren + cap duoi + nhan vien
            ownerIdS = listShop.get(0).getShopId();
            ownerTypeS = Constant.OWNER_TYPE_SHOP;





            if (exportStockForm.getStaffCode() != null && !exportStockForm.getStaffCode().trim().equals("")) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(getSession());
                List<Staff> listStaff = staffDAO.findByPropertyAndStatus("staffCode", exportStockForm.getStaffCode().trim(), 1L);




                if (listStaff == null || listStaff.isEmpty()) {
                    req.setAttribute("resultUpdateSale", "Lỗi! Mã nhân viên không chính xác");




                    return pageForward;




                }
                if (listStaff.get(0).getShopId().compareTo(ownerIdS) != 0) {
                    req.setAttribute("resultUpdateSale", "Lỗi! Nhân viên không thuộc đơn vị đã chọn");




                    return pageForward;




                }
                //Tim cac giao dich lien quan den nhan vien thuoc don vi: xuat/nhap voi cua hang + CTV; doi hang hong
                ownerIdS = listStaff.get(0).getStaffId();
                ownerTypeS = Constant.OWNER_TYPE_STAFF;





            }
            SQL_SELECT_TRANS_DETAIL += " and ("
                    + "(fromOwnerId = ? and fromOwnerType = ? and stockTransType = ? ) or (toOwnerId = ? and toOwnerType = ? and stockTransType = ? )"
                    + ") ";
            lstParameter.add(ownerIdS);
            lstParameter.add(ownerTypeS);
            lstParameter.add(1L);
            lstParameter.add(ownerIdS);
            lstParameter.add(ownerTypeS);
            lstParameter.add(2L);




        }
        //Tim theo user tao giao dich
        if (exportStockForm.getUserCode() != null && !exportStockForm.getUserCode().trim().equals("")) {
            StaffDAO staffDAO = new StaffDAO();
            staffDAO.setSession(getSession());
            List<Staff> listStaff = staffDAO.findByPropertyAndStatus("staffCode", exportStockForm.getUserCode().trim(), 1L);




            if (listStaff == null || listStaff.isEmpty()) {
                req.setAttribute("resultUpdateSale", "Lỗi! Mã nhân viên không chính xác");




                return pageForward;




            }
            SQL_SELECT_TRANS_DETAIL += " and lower(userCode) = ? ";
            lstParameter.add(listStaff.get(0).getStaffCode().trim().toLowerCase());




        }


        SQL_SELECT_TRANS_DETAIL += " order by createDatetime desc ";
        Query q = session.createQuery(SQL_SELECT_TRANS_DETAIL);




        for (int idx = 0; idx
                < lstParameter.size(); idx++) {
            q.setParameter(idx, lstParameter.get(idx));




        }

        List lstSearchStockTrans = q.list();
        //req.getSession().setAttribute("lstSearchStockTrans"+pageId, lstSearchStockTrans);
        req.setAttribute("lstSearchStockTrans", lstSearchStockTrans);

        log.info("# End method StockCommonDAO.searchTrans");




        return pageForward;




    }

    public String expStockTransToExcel() throws Exception {
        log.info("# Begin method StockCommonDAO.searchTrans");
        HttpServletRequest req = this.getRequest();
        exportStockForm.setExportUrl("");
        String pageForward = searchTrans();
        List<SearchStockTrans> lstSearchStockTrans = (List<SearchStockTrans>) req.getAttribute("lstSearchStockTrans");
        if (lstSearchStockTrans != null) {
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

            SimpleDateFormat hoursFormat = new SimpleDateFormat("yyyyMMddhh24mmss");
            String dateTime = hoursFormat.format(new Date());

            String templatePath = ResourceBundleUtils.getResource("TEMPLATE_PATH") + "list_stock_trans.xls";
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE") + "list_stock_trans_" + userToken.getLoginName().toLowerCase() + "_" + dateTime + ".xls";

            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            String realPath = req.getSession().getServletContext().getRealPath(filePath);

            Map beans = new HashMap();
            beans.put("list", lstSearchStockTrans);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            exportStockForm.setExportUrl(filePath);
        }

        log.info("# End method StockCommonDAO.searchTrans");
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 03/06/2009 Purpose: Xem chi tiet giao dich
     * kho
     */
    public String viewStockTransDetail() throws Exception {
        log.info("# Begin method StockCommonDAO.viewStockTransDetail");
        HttpServletRequest req = this.getRequest();
        String pageId = req.getParameter("pageId");
        req.getSession().removeAttribute("lstGoods" + pageId);

        String pageForward = "stockManagmentViewDetail";
        //String actionCode = req.getParameter("actionCode");
        String strActionId = req.getParameter("actionId");
        Long actionId = 0L;
        if (strActionId == null || strActionId.equals("")) {
            actionId = Long.parseLong(strActionId);
            pageForward =
                    "stockManagmentSearchResult";
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }

        actionId = Long.parseLong(strActionId);
        StockTransActionDAO stockTransActionDAO = new StockTransActionDAO();
        stockTransActionDAO.setSession(this.getSession());
        StockTransAction stockTransAction = stockTransActionDAO.findById(actionId);

        if (stockTransAction == null) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
            return pageForward;
        }

        stockTransActionDAO.copyBOToExpForm(stockTransAction, exportStockForm);
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(this.getSession());
        /*
         * TRONGLV : XU LY TRUONG HOP VIEW CHI TIET GIAO DICH
         */
//        List lstGoods = stockTransFullDAO.findByActionId(actionId);
        List lstGoods = stockTransFullDAO.findByActionIdForViewStockTransDetail(actionId);

        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
        req.setAttribute("inputSerial", "true");
        log.info("# End method StockCommonDAO.viewStockTransDetail");
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 03/06/2009 Purpose: Chon ownerType
     */
    public String selectOwnerType() throws Exception {
        try {

            HttpServletRequest req = getRequest();

            String ownerType = req.getParameter("fromOwnerType");
            String[] header = {"", "----chọn kho----"};
            listItemsSelectBox.add(header);
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");




            if (ownerType != null && !"".equals(ownerType)) {
                Long ownerTypeId = Long.parseLong(ownerType);




                if (ownerTypeId.equals(Constant.OWNER_TYPE_SHOP)) {
                    ShopDAO shopDAO = new ShopDAO();
                    shopDAO.setSession(this.getSession());




                    if (userToken != null && userToken.getShopId() != null) {
                        Shop curentShop = shopDAO.findById(userToken.getShopId());




                        if (curentShop == null) {
                            return "selectOwner";




                        }

                        String SQL_SELECT_SHOP = "select shopId as id ,name from Shop where status = ? and (parentShopId = ? or shopId = ? ) order by nlssort(name,'nls_sort=Vietnamese') asc ";
                        Query q = getSession().createQuery(SQL_SELECT_SHOP);
                        q.setParameter(0, Constant.STATUS_USE);
                        q.setParameter(1, userToken.getShopId());




                        if (curentShop.getParentShopId() != null) {
                            q.setParameter(2, curentShop.getParentShopId());




                        } else {
                            q.setParameter(2, userToken.getShopId());




                        }

                        List<Shop> lstShop = q.list();

                        listItemsSelectBox.addAll(lstShop);




                    }

                }
                if (ownerTypeId.equals(Constant.OWNER_TYPE_STAFF)) {
                    StaffDAO staffDAO = new StaffDAO();
                    staffDAO.setSession(this.getSession());




                    if (userToken != null && userToken.getShopId() != null) {
                        String queryString = "select staffId as id,name from Staff a where a.status = ? and a.shopId= ? order by nlssort(name,'nls_sort=Vietnamese') asc ";

                        Query queryObject = getSession().createQuery(queryString);
                        queryObject.setParameter(0, Constant.STATUS_USE);
                        queryObject.setParameter(1, userToken.getShopId());

                        List<Staff> lstStaff = queryObject.list();
                        listItemsSelectBox.addAll(lstStaff);




                    }

                }
            }

        } catch (Exception e) {
            e.printStackTrace();




            throw e;




        }

        return "selectOwner";




    }
    /*
     * Author: ThanhNC Date created: 03/06/2009 Purpose: In chi tiet giao dich
     * kho
     */

    public String printStockTrans() throws Exception {
        log.info("# Begin method StockCommonDAO.printStockTrans");
        HttpServletRequest req = this.getRequest();


        String pageForward = "stockManagmentViewDetail";
        String actionCode = exportStockForm.getCmdExportCode();




        if (actionCode == null || actionCode.equals("")) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");




            return pageForward;




        }

        actionCode = actionCode.trim();
        Long actionId = exportStockForm.getActionId();




        if (actionId == null) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");




            return pageForward;




        }

        String SQL_SELECT = " from SearchStockTrans where id.actionId= ?";
        Query q = getSession().createQuery(SQL_SELECT);
        q.setParameter(0, actionId);
        List lst = q.list();
        SearchStockTrans searchStockTrans = null;




        if (lst.size() > 0) {
            searchStockTrans = (SearchStockTrans) lst.get(0);




        }

        if (searchStockTrans == null) {
            req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");




            return pageForward;




        }

        String prefixTemplatePath = "LN";
        String prefixFileOutName = "LN_" + actionCode;

        String prefix1 = "";
        String prefix2 = "";
        //Loai action 1:lenh 2: phieu
        Long actionType = searchStockTrans.getActionType();





        if (actionType != null && actionType.equals(Constant.ACTION_TYPE_CMD)) {
            prefix1 = "L";




        } else {
            prefix1 = "P";




        } //Loai gia dich 1: xuat 2: nhap

        Long stockTransType = searchStockTrans.getStockTransType();




        if (stockTransType != null && stockTransType.equals(Constant.TRANS_EXPORT)) {
            prefix2 = "X";




        } else {
            prefix2 = "N";




        }

        prefixTemplatePath = prefix1 + prefix2;
        prefixFileOutName =
                prefixTemplatePath + "_" + actionCode;

        String pathOut = printTransAction(actionId, prefixTemplatePath, prefixFileOutName, "resultCreateExp");

        exportStockForm.setExportUrl(pathOut);

        log.info("# End method StockCommonDAO.printStockTrans");




        return pageForward;




    }


    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: Tim kiem giao dich xuat
     * kho
     */
    public List searchExpTrans(
            Object form, Long transType) throws Exception {
        try {
            Session session = this.getSession();




            if (form == null || session == null) {
                return null;




            }

            String actionCode = null;
            Long actionType = null;
            Long transStatus = null;
            Long fromOwnerId = null;
            Long fromOwnerType = null;
            Long toOwnerId = null;
            Long toOwnerType = null;
            String fromDate = null;
            String toDate = null;
            Long maxTransStatus = null;
            //Neu loai giao dich la xuat kho




            if (form instanceof ExportStockForm) {
                ExportStockForm expFrm = (ExportStockForm) form;
                actionCode = expFrm.getActionCode() == null ? "" : expFrm.getActionCode().trim();
                actionType = expFrm.getActionType();
                transStatus = expFrm.getTransStatus();
                fromOwnerId = expFrm.getFromOwnerId();
                fromOwnerType = expFrm.getFromOwnerType();
                toOwnerId = expFrm.getToOwnerId();
                toOwnerType = expFrm.getToOwnerType();
                fromDate = expFrm.getFromDate();
                toDate = expFrm.getToDate();




            }
//Neu loai giao dich la nhap kho

            if (form instanceof ImportStockForm) {
                ImportStockForm impFrm = (ImportStockForm) form;
                actionCode = impFrm.getActionCode() == null ? "" : impFrm.getActionCode().trim();
                actionType = impFrm.getActionType();
                transStatus = impFrm.getTransStatus();
                fromOwnerId = impFrm.getFromOwnerId();
                fromOwnerType = impFrm.getFromOwnerType();
                toOwnerId = impFrm.getToOwnerId();
                toOwnerType = impFrm.getToOwnerType();
                fromDate = impFrm.getFromDate();
                toDate = impFrm.getToDate();
                //Neu la tim kiem phieu xuat de nhap hang --> chi tim nhung phieu xuat co trang thai da xuat hang
                //TruongNQ5 R6037
                if (fromOwnerType != null && fromOwnerType.equals(Constant.OWNER_TYPE_PARTNER)) {
                    if (impFrm.getPartnerId() != null) {
                        fromOwnerId = impFrm.getPartnerId();
                    }
                }



                if (impFrm.getActionType().equals(Constant.ACTION_TYPE_NOTE) && impFrm.getTransType().equals(Constant.TRANS_EXPORT)) {
                    maxTransStatus = Constant.TRANS_DONE;




                }

            }

            String SQL_SELECT_TRANS_DETAIL = "from SearchStockTrans where  1=1 ";
            List lstParameter = new ArrayList();




            if (transType != null) {
                SQL_SELECT_TRANS_DETAIL += " and stockTransType= ? ";
                lstParameter.add(transType);




            }

            if (actionCode != null && !"".equals(actionCode)) {
                SQL_SELECT_TRANS_DETAIL += " and id.actionCode= ? ";
                lstParameter.add(actionCode);




            }

            if (actionType != null && !actionType.equals(0L)) {
                SQL_SELECT_TRANS_DETAIL += " and actionType= ? ";
                lstParameter.add(actionType);




            }

            if (transStatus != null && !transStatus.equals(0L)) {
                SQL_SELECT_TRANS_DETAIL += " and stockTransStatus= ? ";
                lstParameter.add(transStatus);




            }

            if (fromOwnerId != null) {
                SQL_SELECT_TRANS_DETAIL += " and fromOwnerId= ? ";
                lstParameter.add(fromOwnerId);




            }

            if (fromOwnerType != null) {
                SQL_SELECT_TRANS_DETAIL += " and fromOwnerType= ? ";
                lstParameter.add(fromOwnerType);
                //Neu fromOwnerId==null va ownerType = shop (tim nhung lenh xuat cua kho cap duoi tra hang)
                // --> Chi lay nhung phieu xuat cua kho cap duoi xuat cho minh
                // TruongNQ5 02/07/2014 them nhap tu doi tac
                if (fromOwnerType.equals(Constant.OWNER_TYPE_PARTNER)) {
                    SQL_SELECT_TRANS_DETAIL += " and toOwnerType = ? ";
                    lstParameter.add(Constant.OWNER_TYPE_SHOP);
                }



                if (fromOwnerId == null && fromOwnerType.equals(Constant.OWNER_TYPE_SHOP) && toOwnerId != null) {
                    SQL_SELECT_TRANS_DETAIL += " and fromOwnerId in (select shopId from Shop where status = ? and parentShopId = ? ) ";
                    lstParameter.add(Constant.STATUS_USE);
                    lstParameter.add(toOwnerId);




                } else if (fromOwnerId == null && fromOwnerType.equals(Constant.OWNER_TYPE_STAFF) && toOwnerId != null) {
                    //tamdt1, 14/07/2010 bo sung them truogn hop nhap kho tu nhan vien
                    //-> chi lay nhung phieu xuat cua kho nhan vien xuat cho minh
                    SQL_SELECT_TRANS_DETAIL += " and fromOwnerId in (select staffId from Staff where status = ? and shopId = ? ) ";
                    lstParameter.add(Constant.STATUS_USE);
                    lstParameter.add(toOwnerId);




                }

            }


            if (toOwnerId != null) {
                SQL_SELECT_TRANS_DETAIL += " and toOwnerId= ? ";
                lstParameter.add(toOwnerId);




            }

            if (toOwnerType != null) {
                SQL_SELECT_TRANS_DETAIL += " and toOwnerType= ? ";
                lstParameter.add(toOwnerType);
                //Neu toOwnerId ==null va toOwnerType!=null -->chi tim nhung phieu xuat kho cho cap duoi




                if (toOwnerId == null && toOwnerType.equals(Constant.OWNER_TYPE_SHOP) && fromOwnerId != null) {
                    SQL_SELECT_TRANS_DETAIL += " and toOwnerId in (select shopId from Shop where status = ? and parentShopId = ? ) ";
                    lstParameter.add(Constant.STATUS_USE);
                    lstParameter.add(fromOwnerId);




                }

            }

            if (fromDate != null && !"".equals(fromDate)) {

                Date dfromDate = DateTimeUtils.convertStringToDate(fromDate);




                if (dfromDate != null) {
                    SQL_SELECT_TRANS_DETAIL += " and createDatetime >= ? ";
                    lstParameter.add(dfromDate);




                }

            }
            if (toDate != null && !"".equals(toDate)) {
                String stoDate = toDate.substring(0, 10) + " 23:59:59";
                Date dtoDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");





                if (dtoDate != null) {
                    SQL_SELECT_TRANS_DETAIL += " and createDatetime <= ? ";
                    lstParameter.add(dtoDate);




                }

            }
            if (maxTransStatus != null) {
                SQL_SELECT_TRANS_DETAIL += " and stockTransStatus >= ? and stockTransStatus <> ? ";
                lstParameter.add(maxTransStatus);
                lstParameter.add(Constant.TRANS_CANCEL);




            }

            SQL_SELECT_TRANS_DETAIL += " order by createDatetime desc ";
            Query q = session.createQuery(SQL_SELECT_TRANS_DETAIL);




            for (int idx = 0; idx
                    < lstParameter.size(); idx++) {
                q.setParameter(idx, lstParameter.get(idx));




            }

            return q.list();





        } catch (Exception ex) {
            ex.printStackTrace();




        }

        return null;




    }

    /*
     * @Author: tutm1
     * @Date created: 21/08/2013
     * @Purpose: Tim kiem giao dich 
     * kho
     */
    public List searchTrans(
            Object form, Long transType) throws Exception {
        try {
            Session session = this.getSession();

            if (form == null || session == null) {
                return null;
            }

            String actionCode = null;
            Long actionType = null;
            Long transStatus = null;
            Long fromOwnerId = null;
            Long fromOwnerType = null;
            Long toOwnerId = null;
            Long toOwnerType = null;
            String fromDate = null;
            String toDate = null;
            Long maxTransStatus = null;
            Long reasonId = null;
            //Neu loai giao dich la xuat kho

            if (form instanceof ExportStockForm) {
                ExportStockForm expFrm = (ExportStockForm) form;
                actionCode = expFrm.getActionCode() == null ? "" : expFrm.getActionCode().trim().toUpperCase();
                actionType = expFrm.getActionType();
                transStatus = expFrm.getTransStatus();
                fromOwnerId = expFrm.getFromOwnerId();
                fromOwnerType = expFrm.getFromOwnerType();
                toOwnerId = expFrm.getToOwnerId();
                toOwnerType = expFrm.getToOwnerType();
                fromDate = expFrm.getFromDate();
                toDate = expFrm.getToDate();
                reasonId = expFrm.getReasonId() != null ? Long.parseLong(expFrm.getReasonId()) : null;
            }
            //Neu loai giao dich la nhap kho

            if (form instanceof ImportStockForm) {
                ImportStockForm impFrm = (ImportStockForm) form;
                actionCode = impFrm.getActionCode() == null ? "" : impFrm.getActionCode().trim().toUpperCase();
                actionType = impFrm.getActionType();
                transStatus = impFrm.getTransStatus();
                fromOwnerId = impFrm.getFromOwnerId();
                fromOwnerType = impFrm.getFromOwnerType();
                toOwnerId = impFrm.getToOwnerId();
                toOwnerType = impFrm.getToOwnerType();
                fromDate = impFrm.getFromDate();
                toDate = impFrm.getToDate();
                reasonId = impFrm.getReasonId() != null ? Long.parseLong(impFrm.getReasonId()) : null;
                //Neu la tim kiem phieu xuat de nhap hang --> chi tim nhung phieu xuat co trang thai da xuat hang
                if (impFrm.getActionType().equals(Constant.ACTION_TYPE_NOTE) && impFrm.getTransType().equals(Constant.TRANS_EXPORT)) {
                    maxTransStatus = Constant.TRANS_DONE;
                }
            }

            String SQL_SELECT_TRANS_DETAIL = "select a.stock_trans_id stockTransId, a.stock_trans_type stockTransType, "
                    + " a.action_type actionType, a.create_datetime createDatetime, a.user_code userCode, "
                    + " a.user_create userCreate, a.from_owner_id fromOwnerId, "
                    + " a.from_owner_type fromOwnerType, a.from_owner_name fromOwnerName, "
                    + " a.to_owner_id toOwnerId, a.to_owner_type toOwnerType, a.to_owner_name toOwnerName,  "
                    + " a.action_code actionCode, a.action_id actionId, a.note, a.reason_id reasonId,"
                    + " a.reason_name reasonName, a.stock_trans_status stockTransStatus, a.status_name statusName,  "
                    + " a.his_action hisAction, a.deposit_status depositStatus, b.action_code otherActionCode, "
                    + " b.action_id otherActionId, b.user_code otherUserCode "
                    + " from Search_Stock_Trans a, Search_Stock_Trans  b "
                    + " where a.stock_trans_id = b.stock_trans_id(+) ";
            List lstParameter = new ArrayList();

            if (actionType != null && !actionType.equals(0L)) {
                SQL_SELECT_TRANS_DETAIL += " and a.action_Type= ? ";
                lstParameter.add(actionType);

                // neu la lenh, con lai se la phieu
                if (actionType.equals(Constant.ACTION_TYPE_CMD)) {
                    // vi lenh lap truoc phieu nen se co truong hop phieu chua duoc lap.
                    SQL_SELECT_TRANS_DETAIL += " and b.action_Type(+)= ? ";
                    lstParameter.add(Constant.ACTION_TYPE_NOTE);
                } else {// nguoc lai
                    SQL_SELECT_TRANS_DETAIL += " and b.action_Type= ? ";
                    lstParameter.add(Constant.ACTION_TYPE_CMD);
                }
            }

            if (transType != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.stock_Trans_Type= ? ";
                lstParameter.add(transType);

            }

            if (actionCode != null && !"".equals(actionCode)) {
                SQL_SELECT_TRANS_DETAIL += " and a.action_Code= ? ";
                lstParameter.add(actionCode);
            }

            if (transStatus != null && !transStatus.equals(0L)) {
                SQL_SELECT_TRANS_DETAIL += " and a.stock_Trans_Status= ? ";
                lstParameter.add(transStatus);

            }

            if (fromOwnerId != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.from_Owner_Id= ? ";
                lstParameter.add(fromOwnerId);
            }

            if (reasonId != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.reason_Id = ? ";
                lstParameter.add(reasonId);
            }

            if (fromOwnerType != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.from_Owner_Type= ? ";
                lstParameter.add(fromOwnerType);
                //Neu fromOwnerId==null va ownerType = shop (tim nhung lenh xuat cua kho cap duoi tra hang)
                // --> Chi lay nhung phieu xuat cua kho cap duoi xuat cho minh

                if (fromOwnerId == null && fromOwnerType.equals(Constant.OWNER_TYPE_SHOP) && toOwnerId != null) {
                    SQL_SELECT_TRANS_DETAIL += " and a.from_Owner_Id in (select shop_Id from Shop where status = ? and parent_Shop_Id = ? ) ";
                    lstParameter.add(Constant.STATUS_USE);
                    lstParameter.add(toOwnerId);
                } else if (fromOwnerId == null && fromOwnerType.equals(Constant.OWNER_TYPE_STAFF) && toOwnerId != null) {
                    //tamdt1, 14/07/2010 bo sung them truogn hop nhap kho tu nhan vien
                    //-> chi lay nhung phieu xuat cua kho nhan vien xuat cho minh
                    SQL_SELECT_TRANS_DETAIL += " and a.from_Owner_Id in (select staff_Id from Staff where status = ? and shop_Id = ? ) ";
                    lstParameter.add(Constant.STATUS_USE);
                    lstParameter.add(toOwnerId);
                }

            }
            if (toOwnerId != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.to_Owner_Id= ? ";
                lstParameter.add(toOwnerId);

            }

            if (toOwnerType != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.to_Owner_Type= ? ";
                lstParameter.add(toOwnerType);
                //Neu toOwnerId ==null va toOwnerType!=null -->chi tim nhung phieu xuat kho cho cap duoi

                if (toOwnerId == null && toOwnerType.equals(Constant.OWNER_TYPE_SHOP) && fromOwnerId != null && fromOwnerType.equals(Constant.OWNER_TYPE_SHOP)) {
                    SQL_SELECT_TRANS_DETAIL += " and a.to_Owner_Id in (select shop_Id from Shop where status = ? and parent_Shop_Id = ? ) ";
                    lstParameter.add(Constant.STATUS_USE);
                    lstParameter.add(fromOwnerId);

                }
            }

            if (fromDate != null && !"".equals(fromDate)) {

                Date dfromDate = DateTimeUtils.convertStringToDate(fromDate);
                if (dfromDate != null) {
                    SQL_SELECT_TRANS_DETAIL += " and a.create_Datetime >= ? ";
                    lstParameter.add(dfromDate);

                }

            }
            if (toDate != null && !"".equals(toDate)) {
                String stoDate = toDate.substring(0, 10) + " 23:59:59";
                Date dtoDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");

                if (dtoDate != null) {
                    SQL_SELECT_TRANS_DETAIL += " and a.create_Datetime <= ? ";
                    lstParameter.add(dtoDate);

                }
            }
            if (maxTransStatus != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.stock_Trans_Status >= ? and a.stock_Trans_Status <> ? ";
                lstParameter.add(maxTransStatus);
                lstParameter.add(Constant.TRANS_CANCEL);
            }

            SQL_SELECT_TRANS_DETAIL += " order by a.create_Datetime desc ";
            Query q = session.createSQLQuery(SQL_SELECT_TRANS_DETAIL).
                    addScalar("stockTransId", Hibernate.LONG).
                    addScalar("actionCode", Hibernate.STRING).
                    addScalar("actionId", Hibernate.LONG).
                    addScalar("otherActionCode", Hibernate.STRING).
                    addScalar("otherActionId", Hibernate.LONG).
                    addScalar("otherUserCode", Hibernate.STRING).
                    addScalar("stockTransType", Hibernate.LONG).
                    addScalar("actionType", Hibernate.LONG).
                    addScalar("createDatetime", Hibernate.DATE).
                    addScalar("userCreate", Hibernate.STRING).
                    addScalar("userCode", Hibernate.STRING).
                    addScalar("fromOwnerId", Hibernate.LONG).
                    addScalar("fromOwnerType", Hibernate.LONG).
                    addScalar("fromOwnerName", Hibernate.STRING).
                    addScalar("toOwnerId", Hibernate.LONG).
                    addScalar("toOwnerType", Hibernate.LONG).
                    addScalar("toOwnerName", Hibernate.STRING).
                    addScalar("note", Hibernate.STRING).
                    addScalar("reasonId", Hibernate.LONG).
                    addScalar("reasonName", Hibernate.STRING).
                    addScalar("stockTransStatus", Hibernate.LONG).
                    addScalar("statusName", Hibernate.STRING).
                    addScalar("depositStatus", Hibernate.LONG).
                    addScalar("hisAction", Hibernate.STRING).
                    setResultTransformer(Transformers.aliasToBean(SearchStockTransBean.class));

            for (int idx = 0; idx
                    < lstParameter.size(); idx++) {
                q.setParameter(idx, lstParameter.get(idx));
            }

            return q.list();


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /*
     * Author: ThanhNC Modified by tutm1 23/12/2011 Date created: 26/02/2009
     * Purpose: Tim kiem giao dich xuat kho cho cua hang
     */
    public List searchExpTransShop(
            Object form, Long transType) throws Exception {
        try {
            Session session = this.getSession();

            if (form == null || session == null) {
                return null;
            }
            String actionCode = null;
            Long actionType = null;
            Long transStatus = null;
            Long fromOwnerId = null;
            Long fromOwnerType = null;
            Long toOwnerId = null;
            Long toOwnerType = null;
            String fromDate = null;
            String toDate = null;
            Long maxTransStatus = null;
            //Neu loai giao dich la xuat kho

            if (form instanceof ExportStockForm) {
                ExportStockForm expFrm = (ExportStockForm) form;
                actionCode = expFrm.getActionCode();
                actionType = expFrm.getActionType();
                transStatus = expFrm.getTransStatus();
                fromOwnerId = expFrm.getFromOwnerId();
                fromOwnerType = expFrm.getFromOwnerType();
                toOwnerId = expFrm.getToOwnerId();
                toOwnerType = expFrm.getToOwnerType();
                fromDate = expFrm.getFromDate();
                toDate = expFrm.getToDate();

            }
//Neu loai giao dich la nhap kho
            if (form instanceof ImportStockForm) {
                ImportStockForm impFrm = (ImportStockForm) form;
                actionCode = impFrm.getActionCode();
                actionType = impFrm.getActionType();
                transStatus = impFrm.getTransStatus();
                fromOwnerId = impFrm.getFromOwnerId();
                fromOwnerType = impFrm.getFromOwnerType();
                toOwnerId = impFrm.getToOwnerId();
                toOwnerType = impFrm.getToOwnerType();
                fromDate = impFrm.getFromDate();
                toDate = impFrm.getToDate();
                //Neu la tim kiem phieu xuat de nhap hang --> chi tim nhung phieu xuat co trang thai da xuat hang

                if (impFrm.getActionType().equals(Constant.ACTION_TYPE_NOTE) && impFrm.getTransType().equals(Constant.TRANS_EXPORT)) {
                    maxTransStatus = Constant.TRANS_DONE;
                }
            }

            String SQL_SELECT_TRANS_DETAIL = "from SearchStockTrans a where  1=1 ";
            List lstParameter = new ArrayList();
            // chi tim kiem nhung giao dich cua cua hang
            SQL_SELECT_TRANS_DETAIL += " and exists (select sh.channelTypeId from Shop sh, ChannelType ct where sh.channelTypeId = ct.channelTypeId "
                    + " and ct.isVtUnit = ? and ct.objectType = ? and sh.shopId = a.toOwnerId ) ";
            lstParameter.add(Constant.IS_VT_UNIT);
            lstParameter.add(Constant.OBJECT_TYPE_SHOP);
            if (transType != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.stockTransType= ? ";
                lstParameter.add(transType);

            }

            if (actionCode != null && !"".equals(actionCode)) {
                SQL_SELECT_TRANS_DETAIL += " and a.id.actionCode= ? ";
                lstParameter.add(actionCode);
            }

            if (actionType != null && !actionType.equals(0L)) {
                SQL_SELECT_TRANS_DETAIL += " and a.actionType= ? ";
                lstParameter.add(actionType);

            }

            if (transStatus != null && !transStatus.equals(0L)) {
                SQL_SELECT_TRANS_DETAIL += " and a.stockTransStatus= ? ";
                lstParameter.add(transStatus);
            }

            if (fromOwnerId != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.fromOwnerId= ? ";
                lstParameter.add(fromOwnerId);
            }

            if (fromOwnerType != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.fromOwnerType= ? ";
                lstParameter.add(fromOwnerType);
                //Neu fromOwnerId==null va ownerType = shop (tim nhung lenh xuat cua kho cap duoi tra hang)
                // --> Chi lay nhung phieu xuat cua kho cap duoi xuat cho minh


                if (fromOwnerId == null && fromOwnerType.equals(Constant.OWNER_TYPE_SHOP) && toOwnerId != null) {
                    SQL_SELECT_TRANS_DETAIL += " and a.fromOwnerId in (select shopId from Shop where status = ? and parentShopId = ? ) ";
                    lstParameter.add(Constant.STATUS_USE);
                    lstParameter.add(toOwnerId);
                } else if (fromOwnerId == null && fromOwnerType.equals(Constant.OWNER_TYPE_STAFF) && toOwnerId != null) {
                    //tamdt1, 14/07/2010 bo sung them truogn hop nhap kho tu nhan vien
                    //-> chi lay nhung phieu xuat cua kho nhan vien xuat cho minh
                    SQL_SELECT_TRANS_DETAIL += " and a.fromOwnerId in (select staffId from Staff where status = ? and shopId = ? ) ";
                    lstParameter.add(Constant.STATUS_USE);
                    lstParameter.add(toOwnerId);

                }
            }

            if (toOwnerId != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.toOwnerId= ? ";
                lstParameter.add(toOwnerId);

            }

            if (toOwnerType != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.toOwnerType= ? ";
                lstParameter.add(toOwnerType);
                //Neu toOwnerId ==null va toOwnerType!=null -->chi tim nhung phieu xuat kho cho cap duoi


                if (toOwnerId == null && toOwnerType.equals(Constant.OWNER_TYPE_SHOP) && fromOwnerId != null) {
                    SQL_SELECT_TRANS_DETAIL += " and a.toOwnerId in (select shopId from Shop where status = ? and parentShopId = ? ) ";
                    lstParameter.add(Constant.STATUS_USE);
                    lstParameter.add(fromOwnerId);
                }

            }

            if (fromDate != null && !"".equals(fromDate)) {

                Date dfromDate = DateTimeUtils.convertStringToDate(fromDate);


                if (dfromDate != null) {
                    SQL_SELECT_TRANS_DETAIL += " and a.createDatetime >= ? ";
                    lstParameter.add(dfromDate);
                }

            }
            if (toDate != null && !"".equals(toDate)) {
                String stoDate = toDate.substring(0, 10) + " 23:59:59";
                Date dtoDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");

                if (dtoDate != null) {
                    SQL_SELECT_TRANS_DETAIL += " and a.createDatetime <= ? ";
                    lstParameter.add(dtoDate);
                }

            }
            if (maxTransStatus != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.stockTransStatus >= ? and stockTransStatus <> ? ";
                lstParameter.add(maxTransStatus);
                lstParameter.add(Constant.TRANS_CANCEL);

            }

            SQL_SELECT_TRANS_DETAIL += " order by a.createDatetime desc ";
            Query q = session.createQuery(SQL_SELECT_TRANS_DETAIL);

            for (int idx = 0; idx
                    < lstParameter.size(); idx++) {
                q.setParameter(idx, lstParameter.get(idx));
            }
            return q.list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    /*
     * Author: ThanhNC Date created: 20/04/2009 Purpose: Thuc hien xuat kho
     */

    public boolean expStock(ExportStockForm expFrm, HttpServletRequest req) throws Exception {
        log.info("# Begin method expStock");
        Session session = this.getSession();
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }

        //Check han muc        
        Long amountDebit = 0L;
        Long amountDebitDeposit = 0L;
        String[] arrMess = new String[3];
        boolean isSale = false;//Neu la ban dut cho Dai ly thi tru han muc ban dut, nguoc lai tru han muc dat coc
        //Check han muc
        try {
            String pageId = req.getParameter("pageId");
            //LeVT1 start R499
            List lstGoodsTemp = (List) req.getSession().getAttribute("lstGoods" + pageId);
            List lstGoods = lstGoodsTemp;
            //LeVT1 end R499

            if (expFrm.getActionId() == null) {
                lstError.add(new ShowMessage("stock.error.fromStockTransId.notFound"));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            //Lay danh sach hang hoa
//            StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
//            stockTransFullDAO.setSession(this.getSession());
//            List lstGoods = stockTransFullDAO.findByActionId(expFrm.getActionId());

            //Ghi file log loi
            boolean noError = true;
            //LeVT1 start R499
            boolean isChkExport = false;
            if ("4456".equals(expFrm.getReasonId())) {
                isChkExport = true;
                StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
                stockTransFullDAO.setSession(this.getSession());
                lstGoods = stockTransFullDAO.findByActionId(expFrm.getActionId());
            }
            //LeVT1 end R499

            //Trang thai giao dich  mac dinh la da xuat kho neu kho nhan la dai ly --> trang thai la kho nhan da confirm nhap
            Long transStatus = Constant.TRANS_DONE;

            //Neu kho nhan la kho dai ly -->đẩy hàng vào kho đại lý không cần confirm nhập
            //Nếu là không phải là kho đại lý -->  chi update trang thai serial la dang cho confirm nhap kho
            Long toOwnerId = expFrm.getShopImportedId();

            if (toOwnerId == null) {
                lstError.add(new ShowMessage("stock.error.noOwnerId"));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(session);
            Shop shop = shopDAO.findById(toOwnerId);
            if (shop == null || shop.getChannelTypeId() == null) {
                lstError.add(new ShowMessage("stock.error.noOwnerId"));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }

            Long channelTypeId = shop.getChannelTypeId();
            StockCommonDAO stockCommonDAO = new StockCommonDAO();
            stockCommonDAO.setSession(session);

            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            channelTypeDAO.setSession(session);
            ChannelType channelType = channelTypeDAO.findById(channelTypeId);

            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(session);
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(session);

            StockDepositDAO stockDepositDAO = new StockDepositDAO();
            stockDepositDAO.setSession(session);

            //Tim kiem giao dich xuat kho
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(session);
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (userToken == null) {
                lstError.add(new ShowMessage("common.error.sessionTimeout"));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            String userLogin = userToken.getLoginName();

            StockTrans stockTrans = stockTransDAO.findByActionId(expFrm.getActionId());
            if (stockTrans == null || !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                lstError.add(new ShowMessage("stock.error.transNonNoted"));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
//            session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT

            //Lay danh sach mat hang
            List<StockModel> lstStockModel = new ArrayList<StockModel>();
            for (int i = 0; i < lstGoods.size(); i++) {
                StockModel stk = new StockModel();
                StockTransFull stockTransFull = (StockTransFull) lstGoods.get(i);
                stk.setStockModelId(stockTransFull.getStockModelId());
                stk.setQuantity(stockTransFull.getQuantity());

                lstStockModel.add(stk);
            }
            //Check han muc kho don vi theo tung loai mat hang
            if (shop.getShopId().compareTo(Constant.SHOP_NOT_CHECK_DEBIT_ID) != 0) {
                //tinh gia tri don hang -> lay ra danh sach SL & TT cac LOAI mat hang trong don hang
                List<DebitStock> lstTotalOrderDebit = getTotalOrderDebit(lstStockModel, shop.getPricePolicy());
                if (lstTotalOrderDebit != null && lstTotalOrderDebit.size() > 0) {
                    //tinh gia tri ton kho SL & TT online doi voi tung loai mat hang co trong don hang
                    List<DebitStock> lstTotalStockDebit = getTotalStockDebit(session, lstTotalOrderDebit, shop.getPricePolicy(), shop.getShopId());
                    //tinh tong gia tri don hang + gia tri ton kho
                    List<DebitStock> lstTotalDebitAmountChange = getTotalDebitAmountChange(lstTotalOrderDebit, lstTotalStockDebit);
                    String[] checkHanMuc = new String[3];
                    checkHanMuc = checkDebitForShop(session, stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), lstTotalDebitAmountChange, getSysdate(), stockTrans.getStockTransId());

                    if (!checkHanMuc[0].equals("")) {
                        req.setAttribute("resultCreateExp", checkHanMuc[0]);
                        List listParam = new ArrayList();
                        listParam.add(checkHanMuc[1]);
                        req.setAttribute("resultCreateExpParams", listParam);
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        return false;
                    }
                }
            }
            StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
            stockTransDetailDAO.setSession(session);
            List lstStockTransDetail = stockTransDetailDAO.findByStockTransId(stockTrans.getStockTransId());
            if (lstStockTransDetail == null || lstStockTransDetail.isEmpty()) {
                lstError.add(new ShowMessage("stock.error.transDetail.null"));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            if (lstGoods == null || lstGoods.isEmpty()) {
                lstError.add(new ShowMessage("stock.error.transDetail.null"));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            if (lstGoods.size() != lstStockTransDetail.size()) {
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(lstGoods.size()));
                listParams.add(String.valueOf(lstStockTransDetail.size()));
                lstError.add(new ShowMessage("stock.error.transDetail.notValid", listParams));
                req.setAttribute("lstError", lstError);
                session.clear();
                //--
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }

            //Neu kho nhan la dai ly tim sale_trans_id cua giao dich bh
            Long saleTransId = null;
            SaleTrans saleTrans = null;

            if (channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                String SQL_SELECT = " from SaleTrans where stockTransId = ? and status <> ? and saleTransDate >= trunc(?)";
                Query qSelectSaleTrans = session.createQuery(SQL_SELECT);
                qSelectSaleTrans.setParameter(0, stockTrans.getStockTransId());
                qSelectSaleTrans.setParameter(1, Constant.SALE_TRANS_STATUS_CANCEL);
                qSelectSaleTrans.setParameter(2, stockTrans.getCreateDatetime());
                List lstResult = qSelectSaleTrans.list();
                if (lstResult != null && !lstResult.isEmpty()) {
                    saleTrans = (SaleTrans) lstResult.get(0);
                    saleTransId = saleTrans.getSaleTransId();
                }
            }
            for (int i = 0; i
                    < lstGoods.size(); i++) {
                //Check han muc
                isSale = false;//Mac dinh la tru han muc dat coc
                //Check han muc

                StockTransFull exp = (StockTransFull) lstGoods.get(i);
                StockTransFull expTemp = (StockTransFull) lstGoodsTemp.get(i);
                Long quantity = exp.getQuantity();
                List lstSerial = expTemp.getLstSerial();
                //Check mat hang quan ly theo serial ma chua nhap serial khi xuat kho
                if (exp.getCheckSerial() != null && exp.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL) && (lstSerial == null || lstSerial.size() == 0)) {
                    lstError.add(new ShowMessage("stock.error.noDetailSerial"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return false;
                }

                //Cap nhap lai ma kho va trang thai cua cac serial duoc xuat kho
                //Neu kho nhan la dai ly
                //Kiem tra la giao dich xuat dat coc || giao dich ban dut ==> update status cho dung
                if (channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                    if (exp.getCheckSerial() != null && exp.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                        if ((stockTrans.getPayStatus() == null || stockTrans.getPayStatus().equals(Constant.NOT_PAY))
                                && stockTrans.getDepositStatus() != null && stockTrans.getDepositStatus().equals(Constant.DEPOSIT_HAVE)) {
//OLD
//                        }                            
//                        Long stockModelId = exp.getStockModelId();
//                        stockDeposit = stockDepositDAO.findByStockModelAndChannelType(stockModelId, channelType.getChannelTypeId());//                        
//                        if (stockDeposit != null) {
                            //LeVT1 start R499
                            if (!isChkExport) {
                                noError = baseStockDAO.updateSeialByList(lstSerial, exp.getStockModelId(),
                                        Constant.OWNER_TYPE_SHOP, expFrm.getShopExportId(), Constant.OWNER_TYPE_SHOP, toOwnerId, Constant.STATUS_USE, Constant.STATUS_USE, quantity, stockTrans.getChannelTypeId());
                            }
                            //LeVT1 end R499
                            if (!noError) {
                                lstError.add(new ShowMessage("stock.exp.error.undefine"));
                                req.setAttribute("lstError", lstError);
                                session.clear();
                                session.getTransaction().rollback();
                                session.beginTransaction();
                                return false;
                            } //Neu la mat hang dat coc -->Cap nhap so luong hang hoa trong kho dai ly
                            //trung dh3
                            noError = StockTotalAuditDAO.changeStockTotal(getSession(), toOwnerId, Constant.OWNER_TYPE_SHOP, exp.getStockModelId(), exp.getStateId(), exp.getQuantity(), exp.getQuantity(), 0L, userToken.getUserID(),
                                    stockTrans.getReasonId(), stockTrans.getStockTransId(), expFrm.getCmdExportCode(), Constant.TRANS_IMPORT.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess();
                            if (!noError) {
                                session.clear();
                                session.getTransaction().rollback();
                                session.beginTransaction();
                                return false;
                            }
                        } //Neu la mat hang ban dut --> ban thang ra khoi kho khong cap nhap so luong hang hoa trong kho dai ly
                        else {
                            if ((stockTrans.getDepositStatus() == null || stockTrans.getDepositStatus().equals(Constant.NOT_DEPOSIT))
                                    && stockTrans.getPayStatus() != null && stockTrans.getPayStatus().equals(Constant.PAY_HAVE)) {

                                //LeVT1 start R499
                                if (!isChkExport) {
                                    noError = baseStockDAO.updateSeialByList(lstSerial, exp.getStockModelId(),
                                            Constant.OWNER_TYPE_SHOP, expFrm.getShopExportId(), Constant.OWNER_TYPE_SHOP, toOwnerId, Constant.STATUS_USE, Constant.STATUS_SALED, quantity, stockTrans.getChannelTypeId());
                                }
                                //LeVT1 end R499
                                if (!noError) {
                                    lstError.add(new ShowMessage("stock.exp.error.undefine"));
                                    req.setAttribute("lstError", lstError);
                                    session.clear();
                                    session.getTransaction().rollback();
                                    session.beginTransaction();
                                    return false;
                                }
                            } else {
                                return false;//Chua lap hoa don voi truong hop ban dut || chua lap phieu thu voi truong hop ban dat coc
                            }

                            isSale = true;
                        }

                    }
                    transStatus = Constant.TRANS_EXP_IMPED;
                } else {
                    //Kho nhan khong phai dai ly
                    if (exp.getCheckSerial() != null && exp.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                        //TrongLV
                        //Bo sung gan kenh cho serial hang hoa
                        //LeVT1 start R499
                        if (!isChkExport) {
                            noError = baseStockDAO.updateSeialExpNewWay(lstSerial, exp.getStockModelId(), Constant.OWNER_TYPE_SHOP, expFrm.getShopExportId(),
                                    Constant.STATUS_USE, Constant.STATUS_IMPORT_NOT_EXECUTE, quantity, stockTrans.getChannelTypeId(), userLogin, stockTrans.getStockTransId());
                        }
//            lamnt update cho kho giam tru
                        if (expFrm.getReason() != null && !"".equals(expFrm.getFileUpload())) {
//                            noError = baseStockDAO.updateStockTransAction(expFrm.getReason(), expFrm.getFileUpload(), expFrm.getCmdExportCode());
                            DbProcessor db = new DbProcessor();
                            int a = db.updateStockTransAction(expFrm.getReason(), expFrm.getFileUpload(), expFrm.getCmdExportCode());
                            if (a != 0) {
                                noError = true;
                            } else {
                                noError = false;
                            }
                        }
//            lamnt end
                        //LeVT1 end R499
                        if (!noError) {
                            lstError.add(new ShowMessage("stock.exp.error.undefine"));
                            req.setAttribute("lstError", lstError);
                            session.clear();
                            session.getTransaction().rollback();
                            session.beginTransaction();
                            return false;
                        }
                    }
                    transStatus = Constant.TRANS_DONE;
                }
                //Luu chi tiet serial vao bang stock_trans_serial

                if (!noError) {
                    lstError.add(new ShowMessage("stock.exp.error.undefine"));
                    req.setAttribute("lstError", lstError);
                    session.clear();
                    session.getTransaction().rollback();
                    session.beginTransaction();
                    return false;
                }

                StockTransSerial stockSerial = null;
                for (int idx = 0; idx
                        < lstSerial.size(); idx++) {
                    stockSerial = (StockTransSerial) lstSerial.get(idx);
                    stockSerial.setStockModelId(exp.getStockModelId());
                    stockSerial.setStateId(exp.getStateId());
                    stockSerial.setCreateDatetime(new Date());
                    stockSerial.setStockTransId(exp.getStockTransId());
                    session.save(stockSerial);
                    //Neu kho nhan la dai ly va mat hang  xuat ban dut -->luu chi tiet serial vao bang sale_trans_serial

//                    if (stockDeposit == null && channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                    if (isSale && channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                        if (saleTransId == null) {
                            lstError.add(new ShowMessage("stock.error.saleTransId.notFound"));
                            req.setAttribute("lstError", lstError);
                            session.clear();
                            session.getTransaction().rollback();
                            session.beginTransaction();
                            log.info("# End method expStock");
                            return false;
                        }
                        //Lay saleTransDetailId
                        Long saleTransDetailId = 0L;
                        String SQL_SELECT_SALE_TRANS_DETAIL = " from SaleTransDetail where saleTransId = ?  and stockModelId = ? and saleTransDate >= trunc(?) ";
                        Query q = session.createQuery(SQL_SELECT_SALE_TRANS_DETAIL);
                        q.setParameter(0, saleTransId);
                        q.setParameter(1, exp.getStockModelId());
                        q.setParameter(2, stockTrans.getCreateDatetime());
                        List lst = q.list();

                        if (lst.size() > 0) {
                            SaleTransDetail saleTransDetail = (SaleTransDetail) lst.get(0);
                            saleTransDetailId = saleTransDetail.getSaleTransDetailId();
                            SaleTransSerial saleTransSerial = new SaleTransSerial();
                            saleTransSerial.setSaleTransSerialId(getSequence("SALE_TRANS_SERIAL_SEQ"));
                            saleTransSerial.setSaleTransDetailId(saleTransDetailId);
                            saleTransSerial.setFromSerial(stockSerial.getFromSerial());
                            saleTransSerial.setToSerial(stockSerial.getToSerial());
                            saleTransSerial.setQuantity(stockSerial.getQuantity());
                            saleTransSerial.setStockModelId(stockSerial.getStockModelId());
//                            saleTransSerial.setSaleTransDate(stockSerial.getCreateDatetime());
                            saleTransSerial.setSaleTransDate(saleTransDetail.getSaleTransDate());
                            saleTransSerial.setUserDeliver(userToken.getUserID());
                            saleTransSerial.setUserUpdate(userToken.getUserID());
                            saleTransSerial.setDateDeliver(stockSerial.getCreateDatetime());
                            session.save(saleTransSerial);
                            //Cap nhat trang thai giao hang trong bang sale_trans_detail la da giao hang
                            saleTransDetail.setTransferGood(Constant.SALE_TRANSFER_GOOD);
                            session.save(saleTransDetail);

                            //luu thong tin vao bang VcRequets                            
                            StockModel stockModel = stockModelDAO.findById(saleTransDetail.getStockModelId());

                            //??? Kich hoat luon ke ca ke ban dut hay xuat dat coc
                            if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_TYPE_CARD)) {
                                VcRequestDAO vcRequestDAO = new VcRequestDAO();
                                vcRequestDAO.setSession(session);
                                vcRequestDAO.saveVcRequest(DateTimeUtils.getSysDate(), stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.REQUEST_TYPE_SALE_AGENTS, saleTransDetail.getSaleTransId());
                            } else if (stockModel != null && stockModel.getStockTypeId().equals(Constant.STOCK_KIT)) {
                                ReqActiveKitDAO reqActiveKitDAO = new ReqActiveKitDAO();
                                reqActiveKitDAO.setSession(getSession());
                                reqActiveKitDAO.saveReqActiveKit(stockSerial.getFromSerial(), stockSerial.getToSerial(), Constant.SALE_TYPE, saleTransDetail.getSaleTransId(),
                                        Long.parseLong(Constant.SALE_TRANS_TYPE_AGENT), getSysdate());
                            }
                        }
                    }
                }
                if (expFrm.getExpBigAngent() == null) {
                    expFrm.setExpBigAngent(0L);
                }

                //Tru so luong thuc te hang trong kho xuat
                // trung dh3
                if (expFrm.getExpBigAngent().equals(1L)) {
                    if (!StockTotalAuditDAO.changeStockTotal(getSession(), exp.getFromOwnerId(), exp.getFromOwnerType(), exp.getStockModelId(), exp.getStateId(), -exp.getQuantity(), 0L, 0L, userToken.getUserID(),
                            stockTrans.getReasonId(), stockTrans.getStockTransId(), exp.getActionCode(), stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess()) {
                        noError = false;
                    }
                } else {
                    if (!StockTotalAuditDAO.changeStockTotal(getSession(), exp.getFromOwnerId(), exp.getFromOwnerType(), exp.getStockModelId(), exp.getStateId(), -exp.getQuantity(), 0L, exp.getQuantity(), userToken.getUserID(),
                            stockTrans.getReasonId(), stockTrans.getStockTransId(), exp.getActionCode(), stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS).isSuccess()) {
                        noError = false;
                    }
                }
                if (noError == false) {
                    break;
                }
            }

            //Check han muc
            //Tru tong tien ban dut
            //Tru thang xuat
            arrMess = new String[3];
            Shop fromShop = shopDAO.findById(stockTrans.getFromOwnerId());
            arrMess = reduceDebit(fromShop.getShopId(), Constant.OWNER_TYPE_SHOP, amountDebit + amountDebitDeposit, false, null);

            if (!arrMess[0].equals("")) {
                lstError.add(new ShowMessage(arrMess[0]));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            //Cong thang nhan
            arrMess = new String[3];
            arrMess = addDebit(shop.getShopId(), Constant.OWNER_TYPE_SHOP, amountDebit, false, null);

            if (!arrMess[0].equals("")) {
                lstError.add(new ShowMessage(arrMess[0]));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }

            //Cong thang nhan
            arrMess = new String[3];
            arrMess = addDebitDeposit(shop.getShopId(), Constant.OWNER_TYPE_SHOP, amountDebitDeposit, false, null);
            if (!arrMess[0].equals("")) {
                lstError.add(new ShowMessage(arrMess[0]));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            //Check han muc

            //Neu kho nhan la dai ly va co mat hang xuat ban dut cap nhat trang thai giao hang trong bang sale_trans la da giao hang
            if (saleTrans != null && channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                saleTrans.setTransferGoods(Constant.SALE_TRANSFER_GOOD);
                session.save(saleTrans);
            }
            //Cap nhat lai trang thai phieu xuat la da xuat kho
            stockTrans.setStockTransStatus(transStatus);
            //add on 17/08/2009
            //Cap nhap lai user xuat kho, ngay xuat kho
            stockTrans.setRealTransDate(new Date());
            stockTrans.setRealTransUserId(userToken.getUserID());
            session.save(stockTrans);
            //Co loi xay ra khi export hang hoa
            if (!noError) {
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                log.info("# End method addSerial");
                return false;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute("lstError", lstError);
            session.clear();
            session.getTransaction().rollback();
            session.beginTransaction();
            return false;
        }
        return true;
    }

    /*
     * Author: ThanhNC Date created: 02/03/2009 Purporse: Tu choi nhap kho
     */
    public boolean rejectExpTrans(ExportStockForm exportForm, HttpServletRequest req) throws Exception {
        boolean noError = true;
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        if (userToken == null) {
            lstError.add(new ShowMessage("common.error.sessionTimeout"));
            req.setAttribute("lstError", lstError);
            return false;
        }
        String userLogin = userToken.getLoginName();
        //Check han muc
        Double amountDebit = 0.0;
//        Long amountDebit = 0L;
//        String[] arrMess = new String[3];
        boolean isSale = false;//Neu la ban dut cho Dai ly thi tru han muc ban dut, nguoc lai tru han muc dat coc
        //Check han muc
        try {
            Session session = getSession();
            //String actionCode = exportForm.getActionCode();
            //actionCode =actionCode.trim();
//            if (actionCode == null || "".equals(actionCode)) {
//                req.setAttribute("resultCreateExp", "stock.error.notHaveCondition");
//                return false;
//            }
            Long actionId = exportForm.getActionId();
            if (actionId == null) {
                lstError.add(new ShowMessage("stock.error.notHaveCondition"));
                req.setAttribute("lstError", lstError);
                return false;
            }

//Thay doi trang thai giao dich
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(this.getSession());
            StockTrans stockTrans = stockTransDAO.findByActionId(actionId);
            if (stockTrans == null) {
                lstError.add(new ShowMessage("stock.exp.error.notHaveNoteCode"));
                req.setAttribute("lstError", lstError);
                return false;
            }

            //Check han muc            
            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(session);

            Shop shop = shopDAO.findById(stockTrans.getToOwnerId());
            if (shop == null || shop.getChannelTypeId() == null) {
                lstError.add(new ShowMessage("stock.error.noOwnerId"));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            Shop shopImport = shopDAO.findById(stockTrans.getFromOwnerId());
            if (shopImport == null) {
                lstError.add(new ShowMessage("stock.error.noOwnerId"));
                req.setAttribute("lstError", lstError);
                session.clear();
                session.getTransaction().rollback();
                session.beginTransaction();
                return false;
            }
            ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
            channelTypeDAO.setSession(session);
            ChannelType channelType = channelTypeDAO.findById(shop.getChannelTypeId());

            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(session);

            StockDepositDAO stockDepositDAO = new StockDepositDAO();
            stockDepositDAO.setSession(session);
            //Check han muc

//            session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            stockTrans.setStockTransStatus(Constant.TRANS_REJECT); //giao dịch tu choi

            //Check han muc
            // tutm1 : xuat kho tra hang cap tren => gia tri hang hoa kho cap tren ko thay doi, cap duoi tang
            // => cong kho cap duoi trong bang STOCK_OWNER_TMP
            amountDebit = 0D;
            amountDebit = sumAmountByStockTransId(stockTrans.getStockTransId());
            if (amountDebit != null && (amountDebit.compareTo(0.0) > 0) && !checkCurrentDebitWhenImplementTrans(stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), amountDebit)) {
                req.setAttribute("resultCreateExp", "stock.underlying.error.exp");
                lstError.add(new ShowMessage("ERR.LIMIT.0013"));
                req.setAttribute("lstError", lstError);
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                return false;
            }

//Cong kho don vi xuat
            boolean addResult = addCurrentDebit(stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), amountDebit);
            if (!addResult) {
                req.setAttribute("resultCreateExp", "stock.underlying.error.exp");
                lstError.add(new ShowMessage("ERR.LIMIT.0014"));
                req.setAttribute("lstError", lstError);
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                return false;
            }

            //Tru kho don vi nhap
            addResult = false;
            addResult = addCurrentDebit(stockTrans.getToOwnerId(), stockTrans.getToOwnerType(), -1 * amountDebit);
            if (!addResult) {
                req.setAttribute("resultCreateExp", "stock.underlying.error.exp");
                lstError.add(new ShowMessage("ERR.LIMIT.0014"));
                req.setAttribute("lstError", lstError);
                this.getSession().clear();
                this.getSession().getTransaction().rollback();
                this.getSession().beginTransaction();
                return false;
            }


            session.update(stockTrans);
            //Cong lai so luong dap ung trong bang stock_total
            //Lay danh sach hang hoa trong giao dich
            StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
            stockTransDetailDAO.setSession(this.getSession());
            List lstGoodsDetail = stockTransDetailDAO.findByStockTransId(stockTrans.getStockTransId());
            StockTransDetail stockTransDetail = null;
            StockTotal stockTotal;

            StockTotalDAO stockTotalDAO = new StockTotalDAO();
            stockTotalDAO.setSession(this.getSession());
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            StockTransSerialDAO stockTransSerialDAO = new StockTransSerialDAO();
            stockTransSerialDAO.setSession(this.getSession());
            StockTransSerial stockTransSerial = new StockTransSerial();

            List lstSerial = null;

            //Lay danh sach mat hang
            List<StockModel> lstStockModel = new ArrayList<StockModel>();
            for (int i = 0; i < lstGoodsDetail.size(); i++) {
                StockModel stk = new StockModel();
                StockTransDetail stockTransFull = (StockTransDetail) lstGoodsDetail.get(i);
                stk.setStockModelId(stockTransFull.getStockModelId());
                stk.setQuantity(stockTransFull.getQuantityRes());

                lstStockModel.add(stk);
            }


            //Check han muc kho don vi theo tung loai mat hang
            if (shopImport.getShopId().compareTo(Constant.SHOP_NOT_CHECK_DEBIT_ID) != 0) {
                List<DebitStock> lstTotalOrderDebit = getTotalOrderDebit(lstStockModel, shopImport.getPricePolicy());
                if (lstTotalOrderDebit != null && lstTotalOrderDebit.size() > 0) {
                    List<DebitStock> lstTotalStockDebit = getTotalStockDebit(session, lstTotalOrderDebit, shopImport.getPricePolicy(), shopImport.getShopId());
                    List<DebitStock> lstTotalDebitAmountChange = getTotalDebitAmountChange(lstTotalOrderDebit, lstTotalStockDebit);
                    String[] checkHanMuc = new String[3];
                    checkHanMuc = checkDebitForShop(session, stockTrans.getFromOwnerId(), stockTrans.getToOwnerType(), lstTotalDebitAmountChange, getSysdate(), stockTrans.getStockTransId());

                    if (!checkHanMuc[0].equals("")) {
                        req.setAttribute("resultCreateExp", checkHanMuc[0]);
                        List listParam = new ArrayList();
                        listParam.add(checkHanMuc[1]);
                        req.setAttribute("resultCreateExpParams", listParam);
                        session.clear();
                        session.getTransaction().rollback();
                        session.beginTransaction();
                        return false;
                    }
                }
            }

            for (int idx = 0; idx
                    < lstGoodsDetail.size(); idx++) {
                //Check han muc
                isSale = false;
                //Check han muc

                stockTransDetail = (StockTransDetail) lstGoodsDetail.get(idx);
                stockTotal = stockTotalDAO.findById(new StockTotalId(stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), stockTransDetail.getStockModelId(), stockTransDetail.getStateId()));

                if (stockTotal != null) {
//                    stockTotal.setQuantity(stockTotal.getQuantity() + stockTransDetail.getQuantityRes());
//                    stockTotal.setQuantityIssue(stockTotal.getQuantityIssue() + stockTransDetail.getQuantityRes());
//                    getSession().update(stockTotal);

                    if (req.getAttribute("CHANGE_STOCK") != null) {

                        GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), stockTransDetail.getStockModelId(),
                                stockTransDetail.getStateId(), stockTransDetail.getQuantityRes(), stockTransDetail.getQuantityRes(), -stockTransDetail.getQuantityRes(),
                                userToken.getUserID(), stockTrans.getReasonId(), stockTrans.getStockTransId(), "", Constant.STOCK_IMP_NOTE.toString(), StockTotalAuditDAO.SourceType.STOCK_TRANS);
                    } else {
                        GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), stockTransDetail.getStockModelId(),
                                stockTransDetail.getStateId(), null, stockTransDetail.getQuantityRes(), null,
                                userToken.getUserID(), stockTrans.getReasonId(), stockTrans.getStockTransId(), "", stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.CMD_TRANS);
                    }
                    //Cap lai trang thai kho cua cac serial da xuat kho
                }
                stockTransSerial.setStateId(stockTransDetail.getStateId());
                stockTransSerial.setStockModelId(stockTransDetail.getStockModelId());
                stockTransSerial.setStockTransId(stockTransDetail.getStockTransId());
                lstSerial = stockTransSerialDAO.findByExample(stockTransSerial);

                if (stockTrans.getChannelTypeId() == null) {
                    noError = baseStockDAO.updateSeialExpNewWay(lstSerial, stockTransDetail.getStockModelId(),
                            stockTrans.getFromOwnerType(), stockTrans.getFromOwnerId(), Constant.STATUS_IMPORT_NOT_EXECUTE,
                            Constant.STATUS_USE, stockTransDetail.getQuantityRes(), null, userLogin, stockTrans.getStockTransId());
                } else {
                    noError = baseStockDAO.updateSeialExpNewWay(lstSerial, stockTransDetail.getStockModelId(),
                            stockTrans.getFromOwnerType(), stockTrans.getFromOwnerId(), Constant.STATUS_IMPORT_NOT_EXECUTE,
                            Constant.STATUS_USE, stockTransDetail.getQuantityRes(), stockTrans.getChannelTypeId(), userLogin, stockTrans.getStockTransId());
                }
                if (!noError) {
                    lstError.add(new ShowMessage("stock.exp.error"));
                    req.setAttribute("lstError", lstError);
                    getSession().clear();
                    getSession().getTransaction().rollback();
                    getSession().beginTransaction();
                    return false;
                }

                //Check han muc
                if (channelType != null && channelType.getIsVtUnit().equals(Constant.NOT_IS_VT_UNIT) && channelType.getObjectType().equals(Constant.OBJECT_TYPE_SHOP)) {
                    if (stockTransDetail.getCheckSerial() != null && stockTransDetail.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                        Long stockModelId = stockTransDetail.getStockModelId();
                        StockDeposit stockDeposit = stockDepositDAO.findByStockModelAndChannelType(stockModelId, channelType.getChannelTypeId());
                        if (stockDeposit != null) {
                            isSale = true;
                        }
                    }
                }


                /*
                 * PriceDAO priceDAO = new PriceDAO();
                 * priceDAO.setSession(getSession()); Long price =
                 * priceDAO.findBasicPrice(stockTransDetail.getStockModelId(),
                 * shop.getPricePolicy());
                 *
                 *
                 *
                 *
                 * if (price == null &&
                 * (checkStockOwnerTmpDebit(stockTrans.getFromOwnerId(),
                 * stockTrans.getFromOwnerType()) ||
                 * checkStockOwnerTmpDebit(stockTrans.getToOwnerId(),
                 * stockTrans.getToOwnerType())) &&
                 * stockTransDetail.getStateId().compareTo(Constant.STATE_NEW)
                 * == 0) { session.clear(); session.getTransaction().rollback();
                 * session.beginTransaction(); String errMSg =
                 * getText("ERR.SAE.143");
                 *
                 * StockModel stockModel =
                 * stockModelDAO.findById(stockTransDetail.getStockModelId());
                 *
                 *
                 *
                 *
                 * if (stockModel != null) { errMSg += " (" +
                 * stockModel.getStockModelCode() + " - " + stockModel.getName()
                 * + ")";
                 *
                 *
                 *
                 *
                 * }
                 * lstError.add(new ShowMessage(errMSg));
                 * req.setAttribute("lstError", lstError); session.clear();
                 * session.getTransaction().rollback();
                 * session.beginTransaction();
                 *
                 *
                 *
                 *
                 * return false;
                 *
                 *
                 *
                 *
                 * }
                 * if (price == null) { price = 0L;
                 *
                 *
                 *
                 *
                 * }
                 * if
                 * (stockTransDetail.getStateId().compareTo(Constant.STATE_NEW)
                 * == 0) { amountDebit += price *
                 * stockTransDetail.getQuantityRes();
                 *
                 *
                 *
                 *
                 * }
                 *
                 *
                 * //Cong thang xuat (nguoc lai) arrMess = new String[3];
                 * arrMess = addDebitTotal(stockTrans.getFromOwnerId(),
                 * stockTrans.getFromOwnerType(),
                 * stockTransDetail.getStockModelId(),
                 * stockTransDetail.getStateId(), Constant.STATUS_DEBIT_DEPOSIT,
                 * stockTransDetail.getQuantityRes(), false, null);
                 *
                 *
                 *
                 *
                 * // tutm1 : xuat kho cho cap duoi => gia tri hang hoa kho cap
                 * tren ko thay doi, cap duoi tang // => cong kho cap duoi trong
                 * bang STOCK_OWNER_TMP Double amount =
                 * Double.parseDouble(amountDebit.toString()); boolean addResult
                 * = addCurrentDebit(stockTrans.getFromOwnerId(),
                 * Constant.OWNER_TYPE_SHOP, amount); if (!addResult) {
                 * req.setAttribute("resultCreateExp",
                 * "stock.underlying.error.exp"); }
                 *
                 *
                 * if (!arrMess[0].equals("")) { lstError.add(new
                 * ShowMessage(arrMess[0])); req.setAttribute("lstError",
                 * lstError); session.clear();
                 * session.getTransaction().rollback();
                 * session.beginTransaction();
                 *
                 *
                 *
                 *
                 * return false;
                 *
                 *
                 *
                 *
                 * }
                 */




                //Tru thang nhan (nguoc lai)
                //Neu ban dut cho DL thi ...
                Long debitType = Constant.STATUS_DEBIT_DEPOSIT;




                if (isSale) {
                    debitType = Constant.STATUS_SALE_DEBIT;
                }

                /*
                 * arrMess = new String[3]; arrMess =
                 * reduceDebitTotal(stockTrans.getToOwnerId(),
                 * stockTrans.getToOwnerType(),
                 * stockTransDetail.getStockModelId(),
                 * stockTransDetail.getStateId(), debitType,
                 * stockTransDetail.getQuantityRes(), false, null);
                 *
                 *
                 *
                 *
                 * if (!arrMess[0].equals("")) { lstError.add(new
                 * ShowMessage(arrMess[0])); req.setAttribute("lstError",
                 * lstError); session.clear();
                 * session.getTransaction().rollback();
                 * session.beginTransaction();
                 *
                 *
                 *
                 *
                 * return false;
                 *
                 *
                 *
                 *
                 * }
                 */
                //Check han muc

            }

            //Check han muc
            //Cong thang xuat (nguoc lai)
            /*
             * arrMess = new String[3]; Shop fromShop =
             * shopDAO.findById(stockTrans.getFromOwnerId());
             *
             *
             *
             *
             * if (fromShop == null) { lstError.add(new ShowMessage("Lỗi! Không
             * tồn tại thông tin đơn vị xuất")); req.setAttribute("lstError",
             * lstError); session.clear(); session.getTransaction().rollback();
             * session.beginTransaction();
             *
             *
             *
             *
             * return false;
             *
             *
             *
             *
             * }
             * arrMess = addDebit(fromShop.getShopId(),
             * Constant.OWNER_TYPE_SHOP, amountDebit, false, null);
             *
             *
             *
             *
             * if (!arrMess[0].equals("")) { lstError.add(new
             * ShowMessage(arrMess[0])); req.setAttribute("lstError", lstError);
             * session.clear(); session.getTransaction().rollback();
             * session.beginTransaction();
             *
             *
             *
             *
             * return false;
             *
             *
             *
             *
             * }
             */


            //Tru thang nhan (nguoc lai)
            /*
             * arrMess = new String[3]; arrMess = reduceDebit(shop.getShopId(),
             * Constant.OWNER_TYPE_SHOP, amountDebit, false, null);
             *
             *
             *
             *
             * if (!arrMess[0].equals("")) { lstError.add(new
             * ShowMessage(arrMess[0])); req.setAttribute("lstError", lstError);
             * session.clear(); session.getTransaction().rollback();
             * session.beginTransaction();
             *
             *
             *
             *
             * return false;
             *
             *
             *
             *
             * }
             */
            //Check han muc


        } catch (Exception ex) {
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute("lstError", lstError);
            ex.printStackTrace();
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return false;
        }
        return noError;
    }
    /*
     * Author: ThanhNC Date created: 02/03/2009 Purporse: Huỷ lệnh/Phieu xuất
     */

    public boolean cancelExpTrans(ExportStockForm exportForm, HttpServletRequest req) throws Exception {
        Session session = getSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        List<ShowMessage> lstError = (List<ShowMessage>) req.getAttribute("lstError");
        StockTrans stockTrans = null;
        if (lstError == null) {
            lstError = new ArrayList<ShowMessage>();
        }
        String pageId = req.getParameter("pageId");
        try {
            String strActionId = req.getParameter("actionId");
            if (strActionId == null || "".equals(strActionId.trim())) {
                lstError.add(new ShowMessage("stock.error.notHaveCondition"));
                req.setAttribute("lstError", lstError);
                return false;
            }

            strActionId = strActionId.trim();
            Long actionId = Long.parseLong(strActionId);
            //Thay doi trang thai giao dich
            StockTransDAO stockTransDAO = new StockTransDAO();
            stockTransDAO.setSession(this.getSession());
            stockTrans = stockTransDAO.findByActionId(actionId);
            //trung dh3
            Long sourceType = stockTrans.getStockTransStatus();
            if (stockTrans == null) {
                lstError.add(new ShowMessage("stock.exp.error.notHaveNoteCode"));
                req.setAttribute("lstError", lstError);
                return false;
            }
//            session.refresh(stockTrans, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
            //Giao dich co status khac 1,2 --> khong cho huy
            if (!stockTrans.getStockTransStatus().equals(Constant.TRANS_NON_NOTE) && !stockTrans.getStockTransStatus().equals(Constant.TRANS_NOTED)) {
                lstError.add(new ShowMessage("stock.exp.error.notAllowCancel"));
                req.setAttribute("lstError", lstError);
                return false;
            }
//Khong cho huy nhung lenh xuat da thanh toan

            if (stockTrans.getPayStatus() != null && stockTrans.getPayStatus().equals(Constant.PAY_HAVE)) {
                lstError.add(new ShowMessage("stock.exp.error.notAllowCancelPayHave"));
                req.setAttribute("lstError", lstError);
                return false;
            }
//Khong cho phep huy nhung lenh xuat da dat coc

            if (stockTrans.getDepositStatus() != null && stockTrans.getDepositStatus().equals(Constant.DEPOSIT_HAVE)) {
                lstError.add(new ShowMessage("stock.exp.error.notAllowCancelDepositHave"));
                req.setAttribute("lstError", lstError);
                return false;
            }

            stockTrans.setStockTransStatus(Constant.TRANS_CANCEL); //giao dịch đã huỷ
            session.update(stockTrans);
            //Cong lai so luong dap ung trong bang stock_total
            //Lay danh sach hang hoa trong giao dich
            StockTransDetailDAO stockTransDetailDAO = new StockTransDetailDAO();
            stockTransDetailDAO.setSession(this.getSession());
            List lstGoodsDetail = stockTransDetailDAO.findByStockTransId(stockTrans.getStockTransId());
            StockTransDetail stockTransDetail = null;
            StockTotal stockTotal;

            StockTotalDAO stockTotalDAO = new StockTotalDAO();
            stockTotalDAO.setSession(session);

            for (int idx = 0; idx
                    < lstGoodsDetail.size(); idx++) {
                stockTransDetail = (StockTransDetail) lstGoodsDetail.get(idx);
                stockTotal =
                        stockTotalDAO.findById(new StockTotalId(stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), stockTransDetail.getStockModelId(), stockTransDetail.getStateId()));

                if (stockTotal != null) {
//                    session.refresh(stockTotal, LockMode.UPGRADE_NOWAIT); //Huynq13 2016/12/22 change from UPGRADE to UPGRADE_NOWAIT
                    //Neu mat hang boc tham --> cong so luong phai boc tham
                    if (stockTransDetail.getCheckDial() != null && stockTransDetail.getCheckDial().equals(Constant.DRAW_HAVE)) {
                        session.refresh(stockTotal, LockMode.UPGRADE); //Huynq13 2016/12/22 get new record to update
                        stockTotal.setQuantityDial(stockTotal.getQuantityDial() + stockTransDetail.getQuantityRes());
                        getSession().update(stockTotal);
                    } else {
                        if (sourceType == 1L) {
                            GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), stockTransDetail.getStockModelId(),
                                    stockTransDetail.getStateId(), null, stockTransDetail.getQuantityRes(), null,
                                    userToken.getUserID(), stockTrans.getReasonId(), stockTrans.getStockTransId(), "", stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.CMD_TRANS);
                        } else {

                            GenResult genResult = StockTotalAuditDAO.changeStockTotal(getSession(), stockTrans.getFromOwnerId(), stockTrans.getFromOwnerType(), stockTransDetail.getStockModelId(),
                                    stockTransDetail.getStateId(), null, stockTransDetail.getQuantityRes(), null,
                                    userToken.getUserID(), stockTrans.getReasonId(), stockTrans.getStockTransId(), "", stockTrans.getStockTransType().toString(), StockTotalAuditDAO.SourceType.STICK_TRANS);

                            //   stockTotal.setQuantityIssue(stockTotal.getQuantityIssue() + stockTransDetail.getQuantityRes());
                        }
                    }
                }
            }
            //Voi nhung lenh xuat da boc tham --> chuyen cac block da boc tham ve trang thai chua boc tham
            if (stockTrans.getDrawStatus() != null && stockTrans.getDrawStatus().equals(Constant.DRAW_HAVE)) {
                String SQL_UPDATE = "update BLOCK set STATUS = ? , DIAL_ID = null, STOCK_TRANS_ID = null, PICK_DATE = null where STOCK_TRANS_ID = ? ";
                Query q = getSession().createSQLQuery(SQL_UPDATE);
                q.setParameter(0, Constant.NOT_DRAW);
                q.setParameter(1, stockTrans.getStockTransId());
                q.executeUpdate();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            List listParams = new ArrayList<String>();
            listParams.add(ex.toString());
            lstError.add(new ShowMessage("stock.error.exception", listParams));
            req.setAttribute("lstError", lstError);
            getSession().clear();
            getSession().getTransaction().rollback();
            getSession().beginTransaction();
            return false;
        }

        req.getSession().removeAttribute("lstGoods" + pageId);
        //exportForm.resetForm();
        req.setAttribute("resultCreateExp", "stock.deleteNoteSuccess");
//        Huynq13 20170410 start add to rollback for stock_trans_id has exported
        try {
            if (userToken != null && stockTrans != null) {
                DbProcessor db = new DbProcessor();
                db.rollbackExport(userToken.getLoginName(), stockTrans.getStockTransId());
            } else {
                log.warn("userToken or stockTrans was not null " + userToken + " StockTransId " + stockTrans.getStockTransId());
                DbProcessor db = new DbProcessor();
                db.rollbackExport("NoName", stockTrans.getStockTransId());
            }
        } catch (Exception e) {
            log.debug("Had error when try to rollback export " + e.toString());
            e.printStackTrace();
        }
//        Huynq13 20170410 start add to rollback for stock_trans_id has exported
        return true;
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: Copy from
     * searchStockTrans to ExportStockForm
     */
    public boolean copySearchStockTransToExpForm(SearchStockTrans bo,
            ExportStockForm form) throws Exception {
        if (bo == null) {
            return false;




        }

        form.setCmdExportCode(bo.getId().getActionCode());
        form.setShopExportId(bo.getFromOwnerId());
        form.setShopExportName(bo.getFromOwnerName());
        form.setSender(bo.getUserCreate());
        form.setShopImportedId(bo.getToOwnerId());
        form.setReasonId(bo.getReasonId().toString());
        form.setDateExported(DateTimeUtils.convertDateTimeToString(bo.getCreateDatetime()));
        form.setNote(bo.getNote());




        return true;




    }

    /**
     *
     * @param actionId
     * @param prefixTemplatePath
     * @param prefixFileOutName
     * @param propertyError
     * @return
     * @throws Exception
     */
    public String printTransAction(
            Long actionId, String prefixTemplatePath, String prefixFileOutName, String propertyError) throws Exception {
        Session session = getSession();
        HttpServletRequest req = getRequest();
        //TruongNQ5 R6037
        Double priceCost = null;
        if (actionId == null) {
            req.setAttribute(propertyError, "stock.error.notHaveCondition");
            return null;
        }

        String actionCode = "";
        StockTransFullDAO stockTransFullDAO = new StockTransFullDAO();
        stockTransFullDAO.setSession(session);
        List<StockTransFull> lstStockTransFull = stockTransFullDAO.findByActionId(actionId);
        if (lstStockTransFull == null || lstStockTransFull.size() == 0) {
            req.setAttribute(propertyError, "stock.exp.error.notHaveNoteCode");
            return null;
        }

        StockTransFull stockTransFull = lstStockTransFull.get(0);
        actionCode =
                stockTransFull.getActionCode();
        // TruongNQ5 08/08/2014 Lay gia goc cua mat hang
        Query query = session.createQuery("from Price where stockModelId = ? and status = 1 and type = 23 and endDate is null or (staDate <= sysdate and endDate >= sysdate) ");
        query.setParameter(0, stockTransFull.getStockModelId());
        List<Price> lstPrice = query.list();
        if (lstPrice != null && !lstPrice.isEmpty()) {
            priceCost = lstPrice.get(0).getPrice();
            stockTransFull.setPrice(priceCost);
        }
        // End Thuannx
        Long actionStaffId = stockTransFull.getActionStaffId();
        StockTransDAO stockTransDAO = new StockTransDAO();
        stockTransDAO.setSession(this.getSession());
        StockTrans stockTrans = stockTransDAO.findByActionId(actionId);
        if (stockTrans == null) {
            req.setAttribute(propertyError, "stock.exp.error.notHaveNoteCode");
            return null;
        }

        Long fromShopId = 0L;
        Long toShopId = 0L;
        ChannelTypeDAO channelTypeDAO = new ChannelTypeDAO();
        channelTypeDAO.setSession(this.getSession());
        ChannelType channelTypeImp = null;
        ChannelType channelTypeExp = null;
        String fromOwnerName = "";
        String fromOwnerAddress = "";
        String toOwnerName = "";
        String toOwnerAddress = "";
        String reasonName = "";
        if (stockTrans.getReasonId() != null) {
            ReasonDAO reasonDAO = new ReasonDAO();
            reasonDAO.setSession(this.getSession());
            Reason reason = reasonDAO.findById(stockTrans.getReasonId());
            if (reason != null) {
                reasonName = reason.getReasonName();
            }

        }

        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        if (stockTrans.getFromOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
            Shop shopExp = shopDAO.findById(stockTrans.getFromOwnerId());
            if (shopExp == null) {
                return null;
            }

            fromOwnerName = shopExp.getName();
            fromOwnerAddress = shopExp.getAddress();
            channelTypeExp = channelTypeDAO.findById(shopExp.getChannelTypeId());
            if (channelTypeExp != null && !channelTypeExp.getStatus().equals(Constant.STATUS_USE)) {
                channelTypeExp = null;
            }

        } else if (stockTrans.getFromOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
            Staff staffExp = staffDAO.findById(stockTrans.getFromOwnerId());
            if (staffExp == null) {
                return null;
            }

            fromOwnerName = staffExp.getName();
            fromOwnerAddress = staffExp.getAddress();
            channelTypeExp = channelTypeDAO.findById(staffExp.getChannelTypeId());
            if (!channelTypeExp.getStatus().equals(Constant.STATUS_USE)) {
                channelTypeExp = null;
            }

        } else if (stockTrans.getFromOwnerType().equals(Constant.OWNER_TYPE_PARTNER)) {
            // TruongNQ5 nhap tu doi tac
            Partner partner = new PartnerDAO().findById(stockTrans.getFromOwnerId());
            fromOwnerName = partner.getPartnerName();
            fromOwnerAddress = partner.getAddress();
        }
        //Neu la in gd xuat ma khong tim thay loai kho xuat --> thong bao loi
        if (stockTrans.getStockTransType().equals(Constant.TRANS_EXPORT) && channelTypeExp == null) {
            req.setAttribute(propertyError, "stock.exp.error.notHaveNoteCode");
            return null;
        } //Lay thong tin kho nhap hang
        if (stockTrans.getToOwnerType().equals(Constant.OWNER_TYPE_SHOP)) {
            Shop shopImp = shopDAO.findById(stockTrans.getToOwnerId());
            if (shopImp == null) {
                return null;
            }

            toOwnerName = shopImp.getName();
            toOwnerAddress = shopImp.getAddress();
            channelTypeImp = channelTypeDAO.findById(shopImp.getChannelTypeId());
            if (!channelTypeImp.getStatus().equals(Constant.STATUS_USE)) {
                channelTypeImp = null;
            }

        } else if (stockTrans.getToOwnerType().equals(Constant.OWNER_TYPE_STAFF)) {
            Staff staffImp = staffDAO.findById(stockTrans.getToOwnerId());
            if (staffImp == null) {
                return null;
            }

            toOwnerName = staffImp.getName();
            toOwnerAddress = staffImp.getAddress();
            channelTypeImp = channelTypeDAO.findById(staffImp.getChannelTypeId());
            if (!channelTypeImp.getStatus().equals(Constant.STATUS_USE)) {
                channelTypeImp = null;
            }

        }

        //Neu la in gd nhap ma khong tim thay loai kho xuat --> thong bao loi
        if (stockTrans.getStockTransType().equals(Constant.TRANS_IMPORT) && channelTypeImp == null) {
            req.setAttribute(propertyError, "stock.exp.error.notHaveNoteCode");
            return null;
        }

        String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

        String templatePath = "";
        prefixTemplatePath = prefixTemplatePath == null ? "" : prefixTemplatePath.trim();
        String suffixTemp = "";
        //Giao dich xuat
        if (stockTrans.getStockTransType().equals(Constant.TRANS_EXPORT)) {
            if (prefixTemplatePath.equals("BBBGCT")) {
                templatePath = tmp + prefixTemplatePath + ".xls";
            } else {
                suffixTemp = getTemplate(session, fromShopId);
                if (!suffixTemp.equals("")) {
                    templatePath = tmp + prefixTemplatePath + "_" + suffixTemp + ".xls";
                } else {
                    templatePath = tmp + prefixTemplatePath + "_" + channelTypeExp.getStockReportTemplate() + ".xls";
                }
            }
            if (!suffixTemp.equals("")) {
                filePath +=
                        prefixFileOutName + "_" + suffixTemp + ".xls";
            } else {
                filePath +=
                        prefixFileOutName + "_" + channelTypeExp.getStockReportTemplate() + ".xls";
            }

        }
        if (stockTrans.getStockTransType().equals(Constant.TRANS_IMPORT)) {
            suffixTemp = getTemplate(session, toShopId);
            if (!suffixTemp.equals("")) {
                templatePath = tmp + prefixTemplatePath + "_" + suffixTemp + ".xls";
                filePath +=
                        prefixFileOutName + "_" + suffixTemp + ".xls";
            } //TruongNQ5 08/08/2014 template nhap hang tu doi tac
            else if (prefixTemplatePath != null && (prefixTemplatePath.equals("PN_FROM_PARTNER") || prefixTemplatePath.equals("LN_FROM_PARTNER"))) {
                templatePath = tmp + prefixTemplatePath + ".xls";
                filePath += prefixFileOutName + ".xls";
            } else {
                templatePath = tmp + prefixTemplatePath + "_" + channelTypeImp.getStockReportTemplate() + ".xls";
                filePath +=
                        prefixFileOutName + "_" + channelTypeImp.getStockReportTemplate() + ".xls";
            }
        }

        if (stockTrans.getStockTransType().equals(Constant.TRANS_RECOVER)) {
            templatePath = tmp + prefixTemplatePath + "_" + channelTypeExp.getStockReportTemplate() + ".xls";
            filePath +=
                    prefixFileOutName + "_" + channelTypeExp.getStockReportTemplate() + ".xls";
        }

        String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
        String actionStaffName = "";
        if (actionStaffId != null) {
            Staff staff = staffDAO.findById(actionStaffId);
            if (staff != null) {
                actionStaffName = staff.getName();
            }
        }
        Map beans = new HashMap();
        //set ngay tao
        beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
        //set nguoi tao
        //UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        beans.put("userCreate", actionStaffName);
        beans.put("actionCode", actionCode.toUpperCase());
        beans.put("fromOwnerName", fromOwnerName);
        beans.put("fromOwnerAddress", fromOwnerAddress);
        beans.put("toOwnerName", toOwnerName);
        beans.put("toOwnerAddress", toOwnerAddress);
        beans.put("reasonName", reasonName);
        beans.put("reasonNameBBBG", reasonName);
        beans.put("priceCost", (priceCost == null) ? "" : priceCost.toString());
        beans.put("lstStockTransFull", lstStockTransFull);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(templateRealPath, beans, realPath);
        return filePath;
    }
    AutoCompleteSearchBean autoCompleteSearchBean = new AutoCompleteSearchBean();
    private Map listShop = new HashMap();
    private Map listStaff = new HashMap();

    public AutoCompleteSearchBean getAutoCompleteSearchBean() {
        return autoCompleteSearchBean;




    }

    public void setAutoCompleteSearchBean(AutoCompleteSearchBean autoCompleteSearchBean) {
        this.autoCompleteSearchBean = autoCompleteSearchBean;




    }

    public Map getListShop() {
        return listShop;




    }

    public void setListShop(Map listShop) {
        this.listShop = listShop;




    }

    public Map getListStaff() {
        return listStaff;




    }

    public void setListStaff(Map listStaff) {
        this.listStaff = listStaff;




    }

    public String popupSearchShop() throws Exception {
        log.info("# Begin method ReportStockImpExpDAO.popupSearchShop");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "popupSearchShop";
        //pageForward = "reportStockExpImp";
        //initData();
        String shopCode = req.getParameter("shopCode");
        String shopName = req.getParameter("shopName");
        //String notAllChild = "false";




        if (req.getParameter("notAllChild") != null && req.getParameter("notAllChild").trim().equals("true")) {
            //notAllChild = "true";
            autoCompleteSearchBean.setNotAllChild("true");




        }
        if (shopCode != null && !shopCode.equals("")) {
            autoCompleteSearchBean.setCode(shopCode.trim());




        }
        if (shopName != null && !shopName.equals("")) {
            autoCompleteSearchBean.setName(shopName.trim());




        }

        StringBuffer SQL_SELECT_SHOP = new StringBuffer("select shop_code as code, name as name from Shop where status= ? ");
        List parameterList = new ArrayList();
        parameterList.add(Constant.STATUS_USE);




        if (autoCompleteSearchBean.getNotAllChild() != null && autoCompleteSearchBean.getNotAllChild().equals("true")) {
            SQL_SELECT_SHOP.append(" and PARENT_SHOP_ID= ? ");
            parameterList.add(userToken.getShopId());




        } else {
            SQL_SELECT_SHOP.append(" and (shop_path like ? ESCAPE '$' or shop_id=?) ");
            parameterList.add("%$_" + userToken.getShopId() + "$_%");
            parameterList.add(userToken.getShopId());




        }

        if (autoCompleteSearchBean.getCode() != null && !"".equals(autoCompleteSearchBean.getCode())) {
            SQL_SELECT_SHOP.append(" and lower(shop_code) like ? ");
            parameterList.add("%" + autoCompleteSearchBean.getCode().trim().toLowerCase() + "%");




        }
        if (autoCompleteSearchBean.getName() != null && !"".equals(autoCompleteSearchBean.getName())) {
            SQL_SELECT_SHOP.append(" and lower(name) like ? ");
            parameterList.add("%" + autoCompleteSearchBean.getName().trim().toLowerCase() + "%");




        }
        SQL_SELECT_SHOP.append("  order by nlssort(name,'nls_sort=Vietnamese') asc ");
        Query q = getSession().createSQLQuery(SQL_SELECT_SHOP.toString()).addScalar("code", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AutoCompleteSearchBean.class));


        for (int i = 0;
                i < parameterList.size();
                i++) {
            q.setParameter(i, parameterList.get(i));
        }

        q.setMaxResults(
                300);

        List lst = q.list();
        req.setAttribute(
                "lstResult", lst);
        req.setAttribute(
                "returnType", "shop");




        if (req.getParameter(
                "functionName") != null && !req.getParameter("functionName").equals("")) {
            autoCompleteSearchBean.setFunctionName(req.getParameter("functionName"));
        }
        return pageForward;
    }

    public String popupSearchStaff() throws Exception {
        log.info("# Begin method ReportStockImpExpDAO.popupSearchStaff");
        HttpServletRequest req = getRequest();
        String pageForward = "popupSearchShop";
        //initData();

        String shopCode = req.getParameter("shopCode");
        String staffCode = req.getParameter("staffCode");
        String staffName = req.getParameter("staffName");





        if (shopCode != null && !shopCode.equals("")) {
            autoCompleteSearchBean.setParentCode(shopCode.trim());




        }
        if (staffCode != null && !staffCode.equals("")) {
            autoCompleteSearchBean.setCode(staffCode.trim());




        }
        if (staffName != null && !staffName.equals("")) {
            autoCompleteSearchBean.setName(staffName.trim());




        }
        if (autoCompleteSearchBean.getParentCode() != null && !autoCompleteSearchBean.getParentCode().trim().equals("")) {
            StringBuffer SQL_SELECT_STAFF = new StringBuffer("select staff_code as code, name as name from staff where status= ? and shop_id = " + " (select shop_id from shop where lower(shop_code)= ? and status = ? )" + " and channel_Type_Id in (select channel_type_id from channel_type where object_type = ? and is_vt_unit = ? and status = ? ) ");
            //StringBuffer SQL_SELECT_STAFF = new StringBuffer("select staff_code as code, name as name from staff where status= ? and shop_id = " + " (select shop_id from shop where lower(shop_code)= ? and status = ? )" + " and channel_Type_Id in (select channel_type_id from channel_type where object_type = ?  and status = ? ) ");
            List parameterList = new ArrayList();
            parameterList.add(Constant.STATUS_USE);
            parameterList.add(autoCompleteSearchBean.getParentCode().toLowerCase());
            parameterList.add(Constant.STATUS_USE);
            parameterList.add(Constant.OBJECT_TYPE_STAFF);
            parameterList.add(Constant.IS_VT_UNIT);
            parameterList.add(Constant.STATUS_USE);




            if (autoCompleteSearchBean.getCode() != null && !"".equals(autoCompleteSearchBean.getCode())) {
                SQL_SELECT_STAFF.append(" and lower(staff_code) like ? ");
                parameterList.add("%" + autoCompleteSearchBean.getCode().trim().toLowerCase() + "%");




            }
            if (autoCompleteSearchBean.getName() != null && !"".equals(autoCompleteSearchBean.getName())) {
                SQL_SELECT_STAFF.append(" and lower(name) like ? ");
                parameterList.add("%" + autoCompleteSearchBean.getName().trim().toLowerCase() + "%");




            }
            SQL_SELECT_STAFF.append(" order by nlssort(name,'nls_sort=Vietnamese') asc ");

            Query q = getSession().createSQLQuery(SQL_SELECT_STAFF.toString()).addScalar("code", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AutoCompleteSearchBean.class));


            for (int i = 0;
                    i < parameterList.size();
                    i++) {
                q.setParameter(i, parameterList.get(i));
            }

            q.setMaxResults(
                    300);
            List lst = q.list();
            req.setAttribute(
                    "lstResult", lst);
            req.setAttribute(
                    "returnType", "staff");




            if (req.getParameter(
                    "functionName") != null && !req.getParameter("functionName").equals("")) {
                autoCompleteSearchBean.setFunctionName(req.getParameter("functionName"));
            }
        }

        return pageForward;




    }

    public String popupSearchStaffWidthColl() throws Exception {
        log.info("# Begin method ReportStockImpExpDAO.popupSearchStaff");
        HttpServletRequest req = getRequest();
        String pageForward = "popupSearchShop";
        //initData();

        String shopCode = req.getParameter("shopCode");
        String staffCode = req.getParameter("staffCode");
        String staffName = req.getParameter("staffName");





        if (shopCode != null && !shopCode.equals("")) {
            autoCompleteSearchBean.setParentCode(shopCode.trim());




        }
        if (staffCode != null && !staffCode.equals("")) {
            autoCompleteSearchBean.setCode(staffCode.trim());




        }
        if (staffName != null && !staffName.equals("")) {
            autoCompleteSearchBean.setName(staffName.trim());




        }
        if (autoCompleteSearchBean.getParentCode() != null && !autoCompleteSearchBean.getParentCode().trim().equals("")) {
            //StringBuffer SQL_SELECT_STAFF = new StringBuffer("select staff_code as code, name as name from staff where status= ? and shop_id = " + " (select shop_id from shop where lower(shop_code)= ? and status = ? )" + " and channel_Type_Id in (select channel_type_id from channel_type where object_type = ? and is_vt_unit = ? and status = ? ) ");
            StringBuffer SQL_SELECT_STAFF = new StringBuffer(
                    " select staff_code as code, name as name from staff where status= ?"
                    + " and shop_id = " + " (select shop_id from shop where lower(shop_code)= ? and status = ? )"
                    + " and channel_Type_Id in (select channel_type_id from channel_type where object_type = ?  and status = ? ) ");
            List parameterList = new ArrayList();
            parameterList.add(Constant.STATUS_USE);
            parameterList.add(autoCompleteSearchBean.getParentCode().toLowerCase());
            parameterList.add(Constant.STATUS_USE);
            parameterList.add(Constant.OBJECT_TYPE_STAFF);
            parameterList.add(Constant.STATUS_USE);




            if (autoCompleteSearchBean.getCode() != null && !"".equals(autoCompleteSearchBean.getCode())) {
                SQL_SELECT_STAFF.append(" and lower(staff_code) like ? ");
                parameterList.add("%" + autoCompleteSearchBean.getCode().trim().toLowerCase() + "%");




            }
            if (autoCompleteSearchBean.getName() != null && !"".equals(autoCompleteSearchBean.getName())) {
                SQL_SELECT_STAFF.append(" and lower(name) like ? ");
                parameterList.add("%" + autoCompleteSearchBean.getName().trim().toLowerCase() + "%");




            }
            SQL_SELECT_STAFF.append(" order by nlssort(name,'nls_sort=Vietnamese') asc ");

            Query q = getSession().createSQLQuery(SQL_SELECT_STAFF.toString()).addScalar("code", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AutoCompleteSearchBean.class));


            for (int i = 0;
                    i < parameterList.size();
                    i++) {
                q.setParameter(i, parameterList.get(i));
            }

            q.setMaxResults(
                    300);
            List lst = q.list();
            req.setAttribute(
                    "lstResult", lst);
            req.setAttribute(
                    "returnType", "staff");




            if (req.getParameter(
                    "functionName") != null && !req.getParameter("functionName").equals("")) {
                autoCompleteSearchBean.setFunctionName(req.getParameter("functionName"));
            }
        }

        return pageForward;




    }
    /*
     * Tim kiem danh sach CTV de xuat hang dat coc
     */

    public String popupSearchColl() throws Exception {
        log.info("# Begin method ReportStockImpExpDAO.popupSearchColl");
        HttpServletRequest req = getRequest();
        String pageForward = "popupSearchShop";
        //initData();

        String shopCode = req.getParameter("shopCode");
        String staffCode = req.getParameter("staffCode");
        String staffName = req.getParameter("staffName");


        String staffOwnerId = req.getParameter("staffOwnerId");
        if (!this.IS_STOCK_STAFF_OWNER) {//khong check nvql
            staffOwnerId = null;
        }



        if (shopCode != null && !shopCode.equals("")) {
            autoCompleteSearchBean.setParentCode(shopCode.trim());




        }
        if (staffCode != null && !staffCode.equals("")) {
            autoCompleteSearchBean.setCode(staffCode.trim());




        }
        if (staffName != null && !staffName.equals("")) {
            autoCompleteSearchBean.setName(staffName.trim());




        }

        if (autoCompleteSearchBean.getParentCode() != null && !autoCompleteSearchBean.getParentCode().trim().equals("")) {
            StringBuffer SQL_SELECT_STAFF = new StringBuffer("select staff_code as code, name as name from staff where status= ? and shop_id = " + " (select shop_id from shop where lower(shop_code)= ? and status = ? )" + " and channel_Type_Id in " + " (select channel_type_id from channel_type where object_type = ? and is_vt_unit = ? and status = ? ) ");
            List parameterList = new ArrayList();
            parameterList.add(Constant.STATUS_USE);
            parameterList.add(autoCompleteSearchBean.getParentCode().toLowerCase());
            parameterList.add(Constant.STATUS_USE);
            parameterList.add(Constant.OBJECT_TYPE_STAFF);
            parameterList.add(Constant.IS_NOT_VT_UNIT);
            parameterList.add(Constant.STATUS_USE);




            if (autoCompleteSearchBean.getCode() != null && !"".equals(autoCompleteSearchBean.getCode())) {
                SQL_SELECT_STAFF.append(" and lower(staff_code) like ? ");
                parameterList.add("%" + autoCompleteSearchBean.getCode().trim().toLowerCase() + "%");




            }
            if (autoCompleteSearchBean.getName() != null && !"".equals(autoCompleteSearchBean.getName())) {
                SQL_SELECT_STAFF.append(" and lower(name) like ? ");
                parameterList.add("%" + autoCompleteSearchBean.getName().trim().toLowerCase() + "%");




            }
            if (staffOwnerId != null) {
                SQL_SELECT_STAFF.append(" and STAFF_OWNER_ID = ? ");
                parameterList.add(Long.parseLong(staffOwnerId));
            }
            SQL_SELECT_STAFF.append(" order by nlssort(name,'nls_sort=Vietnamese') asc ");

            Query q = getSession().createSQLQuery(SQL_SELECT_STAFF.toString()).addScalar("code", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AutoCompleteSearchBean.class));


            for (int i = 0;
                    i < parameterList.size();
                    i++) {
                q.setParameter(i, parameterList.get(i));
            }

            q.setMaxResults(
                    300);
            List lst = q.list();
            req.setAttribute(
                    "lstResult", lst);
            req.setAttribute(
                    "returnType", "Collaborator");




            if (req.getParameter(
                    "functionName") != null && !req.getParameter("functionName").equals("")) {
                autoCompleteSearchBean.setFunctionName(req.getParameter("functionName"));
            }
        }

        return pageForward;




    }

    /*
     * Tim kiem danh sach CTV de thu hoi hang dat coc
     */
    public String popupSearchRevokeColl() throws Exception {
        log.info("# Begin method ReportStockImpExpDAO.popupSearchColl");
        HttpServletRequest req = getRequest();
        String pageForward = "popupSearchShop";
        //initData();

        String shopCode = req.getParameter("shopCode");
        String staffCode = req.getParameter("staffCode");
        String staffName = req.getParameter("staffName");
        String staffOwnerId = req.getParameter("staffOwnerId");
        if (!this.IS_STOCK_STAFF_OWNER) {//khong check nvql
            staffOwnerId = null;
        }





        if (shopCode != null && !shopCode.equals("")) {
            autoCompleteSearchBean.setParentCode(shopCode.trim());




        }
        if (staffCode != null && !staffCode.equals("")) {
            autoCompleteSearchBean.setCode(staffCode.trim());




        }
        if (staffName != null && !staffName.equals("")) {
            autoCompleteSearchBean.setName(staffName.trim());




        }

        if (autoCompleteSearchBean.getParentCode() != null && !autoCompleteSearchBean.getParentCode().trim().equals("")) {
            StringBuffer SQL_SELECT_STAFF = new StringBuffer("select staff_code as code, name as name from staff where status= ? and shop_id = " + " (select shop_id from shop where lower(shop_code)= ? and status = ? )" + " and channel_Type_Id in " + " (select channel_type_id from channel_type where object_type = ? and is_vt_unit = ? and status = ? ) ");
            List parameterList = new ArrayList();
            parameterList.add(Constant.STATUS_USE);
            parameterList.add(autoCompleteSearchBean.getParentCode().toLowerCase());
            parameterList.add(Constant.STATUS_USE);
            parameterList.add(Constant.OBJECT_TYPE_STAFF);
            parameterList.add(Constant.IS_NOT_VT_UNIT);
            parameterList.add(Constant.STATUS_USE);




            if (autoCompleteSearchBean.getCode() != null && !"".equals(autoCompleteSearchBean.getCode())) {
                SQL_SELECT_STAFF.append(" and lower(staff_code) like ? ");
                parameterList.add("%" + autoCompleteSearchBean.getCode().trim().toLowerCase() + "%");




            }
            if (autoCompleteSearchBean.getName() != null && !"".equals(autoCompleteSearchBean.getName())) {
                SQL_SELECT_STAFF.append(" and lower(name) like ? ");
                parameterList.add("%" + autoCompleteSearchBean.getName().trim().toLowerCase() + "%");




            }
            if (staffOwnerId != null) {
                SQL_SELECT_STAFF.append(" and STAFF_OWNER_ID = ? ");
                parameterList.add(Long.parseLong(staffOwnerId));




            }
            SQL_SELECT_STAFF.append(" order by nlssort(name,'nls_sort=Vietnamese') asc ");

            Query q = getSession().createSQLQuery(SQL_SELECT_STAFF.toString()).addScalar("code", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AutoCompleteSearchBean.class));


            for (int i = 0;
                    i < parameterList.size();
                    i++) {
                q.setParameter(i, parameterList.get(i));
            }

            q.setMaxResults(
                    300);
            List lst = q.list();
            req.setAttribute(
                    "lstResult", lst);
            req.setAttribute(
                    "returnType", "RevokeColl");




            if (req.getParameter(
                    "functionName") != null && !req.getParameter("functionName").equals("")) {
                autoCompleteSearchBean.setFunctionName(req.getParameter("functionName"));
            }
        }

        return pageForward;




    }

    /*
     * @Author: ThanhNC @Date created: 03/08/2009 @Description: Get shop name
     */
    public String getShopName() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String shopCode = req.getParameter("shopCode");
            String target = req.getParameter("target");
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");




            if (shopCode != null && shopCode.trim().length() > 0) {

                String queryString = "from Shop where lower(shopCode) = ? and status= ? and (shopPath like ? or shopId= ?)";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, shopCode.trim().toLowerCase());
                queryObject.setParameter(1, Constant.STATUS_USE);
                queryObject.setParameter(2, "%_" + userToken.getShopId() + "_%");
                queryObject.setParameter(3, userToken.getShopId());

                List<Shop> lstShop = queryObject.list();




                if (lstShop != null && lstShop.size() > 0) {
                    this.listShop.put(target, lstShop.get(0).getName());




                }
            }
        } catch (Exception ex) {
            throw ex;




        }

        return "autoSelectShop";




    }
    /*
     * @Author: ThanhNC @Date created: 03/08/2009 @Description: Get Staff name
     */

    public String getStaffName() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String staffCode = req.getParameter("staffCode");
            String target = req.getParameter("target");
            String shopCode = req.getParameter("shopCode");




            if (staffCode != null && !staffCode.trim().equals("") && shopCode != null && !shopCode.trim().equals("")) {
                String queryString = "from Staff where lower(staffCode) = ? and status = ? and " + " shopId = (select shopId from Shop where lower(shopCode)= ? and status = ? )  ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, staffCode.trim().toLowerCase());
                queryObject.setParameter(1, Constant.STATUS_USE);
                queryObject.setParameter(2, shopCode.trim().toLowerCase());
                queryObject.setParameter(3, Constant.STATUS_USE);
                List<Staff> lstStaff = queryObject.list();




                if (lstStaff != null && lstStaff.size() > 0) {
                    this.listStaff.put(target, lstStaff.get(0).getName());




                }
            }
        } catch (Exception ex) {
            throw ex;




        }

        return "autoSelectStaff";




    }

    private Double getAmountDisplay(List lstGoods) throws Exception {
        StockTransFull stockTransFull = null;
        Long stockModelId = 0L;
        Long quantity = 0L;
        Double amountDisplay = 0.0;
        Double price = 0.0;
        for (int i = 0; i < lstGoods.size(); i++) {
            if (lstGoods.get(i) instanceof GoodsForm) {
                goodsForm = (GoodsForm) lstGoods.get(i);
                stockModelId = goodsForm.getStockModelId();
                quantity = goodsForm.getQuantity();
                price = getPriceDeposit(stockModelId);
                //Cong tong tien cho moi mat hang
                amountDisplay += price * quantity;
            }
            if (lstGoods.get(i) instanceof StockTransFull) {
                stockTransFull = (StockTransFull) lstGoods.get(i);
                stockModelId = stockTransFull.getStockModelId();
                StockModelDAO stockModelDAO = new StockModelDAO();
                StockModel stockModel = stockModelDAO.findById(stockModelId);
                if (stockModel == null) {
                    return 0.0;
                }
                BaseStockDAO baseStockDAO = new BaseStockDAO();
                Long Id = 815711L;
                StockHandsetDAO stockHandsetDAO = new StockHandsetDAO();
                String tableName = baseStockDAO.getTableNameByStockType(stockModel.getStockTypeId(), BaseStockDAO.NAME_TYPE_NORMAL);
                String querySql = " Select sum(nvl(deposit_price,0)) from " + tableName + " where 1=1 "
                        + " and stock_model_id = ? "
                        //                                + " and owner_type = ? "
                        //                                + " and owner_id = ? "
                        + " and  to_number(serial) >= ? and to_number(serial) <= ? ";
                int j = 0;
                List lstSerial = stockTransFull.getLstSerial();
                StockTransSerial stockSerial = null;
                stockSerial = (StockTransSerial) lstSerial.get(0);
                Query query = getSession().createSQLQuery(querySql);
                query.setParameter(j++, stockModelId);
                query.setParameter(j++, stockSerial.getFromSerial());
                query.setParameter(j++, stockSerial.getToSerial());
                Object obj = query.uniqueResult();
                System.out.println("obj=" + obj);
                amountDisplay += Double.parseDouble(obj.toString());
            }
        }
        return amountDisplay;
    }

    /**
     *
     * @author LamNV
     * @param lstGoods
     * @return
     * @throws java.lang.Exception
     */
    private Double getAmountTax(List lstGoods) throws Exception {
        StockTransFull stockTransFull = null;
        Long stockModelId = 0L;
        Long quantity = 0L;
        Double amount = 0.0;
        Double price = 0.0;

        for (int i = 0; i
                < lstGoods.size(); i++) {
            //Neu la list cac goodsform
            if (lstGoods.get(i) instanceof GoodsForm) {
                goodsForm = (GoodsForm) lstGoods.get(i);
                stockModelId = goodsForm.getStockModelId();
                quantity = goodsForm.getQuantity();
            }
            //Neu la list cac stockTransFull
            if (lstGoods.get(i) instanceof StockTransFull) {
                stockTransFull = (StockTransFull) lstGoods.get(i);
                stockModelId = stockTransFull.getStockModelId();
                quantity = stockTransFull.getQuantity().longValue();
            }

            //Tinh tien cho mat hang
            price = getPriceDeposit(stockModelId);
            //Cong tong tien cho moi mat hang
            amount += price * quantity;
        }

        return amount;
    }

    /**
     * @author: LamNV
     * @purpose: Lay gia dat coc cho mat hang
     * @param stockModelId
     * @return
     */
    private Double getPriceDeposit(Long stockModelId) throws Exception {
        //Tinh tien cho mat hang
        Double price = 0.0;
        PriceDAO priceDAO = new PriceDAO();
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        priceDAO.setSession(getSession());

        Staff staff = staffDAO.findByStaffId(goodsForm.getOwnerId());





        if (staff != null) {
            priceDAO.setPricePolicyFilter(staff.getPricePolicy());
            priceDAO.setStockModelIdFilter(stockModelId);
            priceDAO.setPriceTypeFilter(Constant.PRICE_TYPE_DEPOSIT);
            List priceList = priceDAO.findPriceForSaleRetail();
            //Long priceID = 0L;





            if (priceList != null && priceList.size() != 0) {
                Object[] temp = (Object[]) priceList.get(0);
                price = Double.parseDouble(temp[1].toString());
                //priceID = Long.parseLong(temp[0].toString());




            } else {
                price = 0.0;




            }
        }

        return price;




    }

    /*
     * Author: NamNX DateCreate: 23/02/2010 Purpose: Prepare page xem kho nhan
     * vien
     */
    public String prepareViewStockStaffDetail() throws Exception {
        log.info("# Begin method prepareViewStockStaffDetail");
        String pageForward = "viewStockStaffDetail";

        HttpServletRequest req = getRequest();
        //req.getSession().removeAttribute("lstStockGoods");




        try {
            if (goodsForm == null) {
                goodsForm = new GoodsForm();




            }

            String ownerType = (String) QueryCryptUtils.getParameter(req, "ownerType");
            String owner = (String) QueryCryptUtils.getParameter(req, "ownerId");
            String stockModel = (String) QueryCryptUtils.getParameter(req, "stockModelId");








            if (null != stockModel && stockModel.split(":").length == 2) {
                if (stockModel.split(":")[0].trim().equals("0")) {
                    stockModel = stockModel.split(":")[1];




                } else {
                    stockModel = null;




                }
            }


            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
//Lay ten ShopName và ShopCode
            Long shopId = userToken.getShopId();
            String strShopQuery = " from Shop a where a.shopId = ? and status = ? ";
            Query shopQuery = getSession().createQuery(strShopQuery);
            shopQuery.setParameter(0, shopId);
            shopQuery.setParameter(1, Constant.STATUS_ACTIVE);
            List<Shop> listShop = shopQuery.list();

            goodsForm.setShopName(listShop.get(0).getName());
            goodsForm.setShopCode(listShop.get(0).getShopCode());


            String viewType = (String) QueryCryptUtils.getParameter(req, "viewType");




            if (viewType != null && viewType.equals("normal")) {
                goodsForm.setViewType(viewType);
                pageForward = "viewStockStaffDetail";




            }

            Long ownerTypeId = 0L;
            Long ownerId = 0L;
            Long stockModelId = 0L;





            if (ownerType != null && !"".equals(ownerType)) {
                ownerTypeId = Long.parseLong(ownerType);




            }

            if (owner != null && !"".equals(owner)) {
                ownerId = Long.parseLong(owner);




            }

            if (stockModel != null && !"".equals(stockModel)) {
                stockModelId = Long.parseLong(stockModel);
                goodsForm.setStockModelId(stockModelId);




            } //Truong hop xem kho nhan vien

            if (ownerTypeId != null && ownerTypeId == Constant.OWNER_TYPE_STAFF && (ownerId == null || ownerId <= 0)) {
                ownerId = userToken.getUserID();




            } //Truong hop xem kho cua hang

            if (ownerTypeId != null && ownerTypeId == Constant.OWNER_TYPE_SHOP && (ownerId == null || ownerId <= 0)) {
                if (userToken.getShopId() != null) {
                    ownerId = userToken.getShopId();




                } else {
                    return pageForward;




                }

            }

            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            Object obj = baseStockDAO.getStockByTypeAndId(ownerTypeId, ownerId);




            if (obj instanceof Shop) {
                Shop shop = (Shop) obj;
                goodsForm.setOwnerId(ownerId);
                goodsForm.setOwnerType(ownerTypeId);
                goodsForm.setOwnerCode(shop.getShopCode());
                goodsForm.setOwnerName(shop.getName());




            }

            if (obj instanceof Staff) {
                Staff staff = (Staff) obj;
                goodsForm.setOwnerId(ownerId);
                goodsForm.setOwnerType(ownerTypeId);
                goodsForm.setOwnerCode(staff.getStaffCode());
                goodsForm.setOwnerName(staff.getName());




            }




            String SQL_SELECT_STOCK_TYPE = " from StockType where status= " + Constant.STATUS_USE + " and stockTypeId in " + " ( select stockTypeId from StockModel where status= " + Constant.STATUS_USE + " and stockModelId in " + " (select id.stockModelId from StockTotal where status = ? and id.ownerType= ? and id.ownerId =? )" + " ) ";




            if (stockModelId != null && !stockModelId.equals(0L)) {
                SQL_SELECT_STOCK_TYPE = " from StockType where status= " + Constant.STATUS_USE + " and stockTypeId in " + " ( select stockTypeId from StockModel where status= " + Constant.STATUS_USE + " and stockModelId=" + stockModelId + ")";




            }

            Query q = getSession().createQuery(SQL_SELECT_STOCK_TYPE);




            if (stockModelId == null || stockModelId.equals(0L)) {
                q.setParameter(0, Constant.STATUS_USE);
                q.setParameter(1, ownerTypeId);
                q.setParameter(2, ownerId);




            }

            List<StockType> lstStockType = q.list();


            List<StockTotalFull> lstStockGoods = null;




            for (StockType stockType : lstStockType) {
                if (stockModelId != null && !stockModelId.equals(0L)) {
                    lstStockGoods = findStockTotalFullByStockModel(ownerTypeId, ownerId, stockModelId);




                } else {
                    lstStockGoods = findStockTotalFull(ownerTypeId, ownerId, stockType.getStockTypeId());




                }

                stockType.setListStockDetail(lstStockGoods);




            } //req.getSession().setAttribute("lstStockGoods", lstStockType);
            req.setAttribute("lstStockGoods", lstStockType);




        } catch (Exception ex) {
            ex.printStackTrace();




            throw ex;




        }
        req.setAttribute("collaborator", "coll");
        log.info("# End method prepareViewStockDetail");




        return pageForward;




    }

    /**
     * Author TheTM
     *
     * Chức năng Xử lý kho hàng
     */
    public String prepareProcessStockGoods() throws Exception {
        log.info("# Begin method prepareProcessStockGoods");
        String pageForward = "processStockGoods";

        HttpServletRequest req = getRequest();
        //req.getSession().removeAttribute("lstStockGoods");




        try {
            if (goodsForm == null) {
                goodsForm = new GoodsForm();




            }
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");




            if (userToken == null) {
                pageForward = Constant.SESSION_TIME_OUT;




                return pageForward;




            }
            String shopCode = goodsForm.getShopCode();
            String staffCode = goodsForm.getStaffCode();
            String stockModelCode = goodsForm.getStockModelCode();





            if (shopCode == null || shopCode.trim().equals("")) {
                shopCode = userToken.getShopCode();
                goodsForm.setShopCode(shopCode);
                goodsForm.setShopName(userToken.getShopName());




            } //            if (staffCode == null || staffCode.trim().equals("")) {
            //                staffCode = userToken.getLoginName();
            //                goodsForm.setStaffCode(staffCode);
            //            }



            List lstStockType = searchListStockTotal(stockModelCode, shopCode, staffCode, goodsForm.getStateId());




            if (lstStockType != null) {
                req.setAttribute("lstStockGoods", lstStockType);




            }
            req.setAttribute("lstStockGoods", lstStockType);





        } catch (Exception ex) {
            ex.printStackTrace();




            throw ex;




        }
        log.info("# End method prepareProcessStockGoods");




        return pageForward;




    }

    /**
     * Author TheTM Date Create 25/2/2010 Can Kho hang
     */
    public String balanceStockGoods() throws Exception {
        log.info("# Begin method balanceStockGoods");
        HttpServletRequest req = getRequest();
        String pageForward = "balanceStockGoods";

        Long ownerType = serialGoods.getOwnerType();
        Long ownerId = serialGoods.getOwnerId();
        Long stockModelId = serialGoods.getStockModelId();
        Long stateId = serialGoods.getStateId();





        if (ownerType == null || ownerId == null || stockModelId == null || stateId == null) {
            pageForward = "viewSerialProcess";
            req.setAttribute("returnMsg", "stock.balance.noInfo");




            return pageForward;




        }


        Long quantity = 0L;

        //List<StockTransSerial> listSerial = (List<StockTransSerial>) req.getSession().getAttribute("listSerial");
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);




        if (stockModel == null) {
            pageForward = "viewSerialProcess";
            req.setAttribute("returnMsg", "stock.balance.stockModelNotValid");




            return pageForward;




        }
        //Tim kiem giao dich con treo
        String SQL_SELECT_PEDING_EXP = " from StockTrans a, StockTransDetail b where "
                + " a.stockTransId = b.stockTransId "
                + " and b.stockModelId = ? "
                + " and a.stockTransType = ? and a.stockTransStatus in (?,?) "
                + " and a.fromOwnerType = ? and a.fromOwnerId = ? ";
        Query qSelectPending = getSession().createQuery(SQL_SELECT_PEDING_EXP);
        qSelectPending.setParameter(0, stockModelId);
        qSelectPending.setParameter(1, Constant.TRANS_EXPORT);
        qSelectPending.setParameter(2, Constant.TRANS_NON_NOTE);
        qSelectPending.setParameter(3, Constant.TRANS_NOTED);
        qSelectPending.setParameter(4, ownerType);
        qSelectPending.setParameter(5, ownerId);
        List lstResult = qSelectPending.list();




        if (lstResult.size() > 0) {
            pageForward = "viewSerialProcess";
            req.setAttribute("returnMsg", "stock.balance.havePendingExp");




            return pageForward;




        }

        List<StockTransSerial> listSerial = getRangeSerial(ownerType, ownerId, stockModel.getStockTypeId(), stockModelId, stateId, Constant.STATUS_USE);




        for (int i = 0; i
                < listSerial.size(); i++) {
            quantity += listSerial.get(i).getQuantity();




        }

        //trung dh3
        long oldQuantity = 0L;
        long oldQuantityIss = 0L;
        long changeQty = 0L;
        long changeQtyIss = 0L;
        List<StockTotal> lstStockTotal = new ArrayList();
        lstStockTotal = findStockTotalByStockModelAndState(ownerType, ownerId, stockModelId, stateId);
        StockTotal stockTotal = new StockTotal();




        if (lstStockTotal == null || lstStockTotal.size() == 0) {
            stockTotal.setId(new StockTotalId(ownerId, ownerType, stockModelId, stateId));
            stockTotal.setModifiedDate(getSysdate());
            stockTotal.setStatus(Constant.STATUS_USE);




        } else {
            stockTotal = lstStockTotal.get(0);
            //getSession().lock(stockTotal, LockMode.UPGRADE);
            getSession().refresh(stockTotal, LockMode.UPGRADE); //Huynq13 2016/12/22 change to use refresh function
            oldQuantity = stockTotal.getQuantity();
            oldQuantityIss = stockTotal.getQuantityIssue();

        }
        changeQty = quantity - oldQuantity;
        changeQtyIss = quantity - oldQuantityIss;

        StockTotal oldStockTotal = new StockTotal();
        BeanUtils.copyProperties(oldStockTotal, stockTotal);
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        StockTotalAuditDAO.changeStockTotal(this.getSession(), ownerId, ownerType, stockModelId, stateId, changeQty, changeQtyIss, 0L, userToken.getUserID(),
                0L, 0L, "", "0", StockTotalAuditDAO.SourceType.OTHER).isSuccess();
//        stockTotal.setQuantity(quantity);
//        stockTotal.setQuantityIssue(quantity);
//        getSession().update(stockTotal);
        getSession().flush();
        getSession().getTransaction().commit();
        getSession().getTransaction().begin();

        List<ActionLogsObj> lstLogObj = new ArrayList<ActionLogsObj>();
        lstLogObj.add(new ActionLogsObj(oldStockTotal, stockTotal));
        saveLog(
                Constant.ACTION_BALANCE_STOCK_TOTAL, "Cân kho hàng owner_type = " + stockTotal.getId().getOwnerType() + " ,owner_id = "
                + stockTotal.getId().getOwnerId() + " ,stock_model_id = " + stockTotal.getId().getStockModelId() + " ,state_id = "
                + stockTotal.getId().getStateId(), lstLogObj, null);

        req.setAttribute(REQUEST_PROCESS_MODE, "closePopup");
//        req.setAttribute("lstStockGoods", listStockGoods);
        log.info("# End method balanceStockGoods");




        return pageForward;




    }

    public List findStockTotalByStockModelAndState(
            Long ownerType, Long ownerId, Long stockModelId, Long stateId) {
        log.info("finding al Goods In Stock ");




        try {
            String queryString = "from StockTotal where id.ownerType = ? and id.ownerId= ? and id.stockModelId = ? and status=? and id.stateId = ? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerType);
            queryObject.setParameter(1, ownerId);
            queryObject.setParameter(2, stockModelId);
            queryObject.setParameter(3, Constant.STATUS_USE);
            queryObject.setParameter(4, stateId);




            return queryObject.list();




        } catch (RuntimeException re) {
            log.error("find all failed", re);




            throw re;




        }

    }

    public String viewDetailSerialProcess() throws Exception {
        log.info("# Begin method viewSerial");
        HttpServletRequest req = getRequest();
        //req.getSession().removeAttribute("lstSerial");
        String ownerType = (String) QueryCryptUtils.getParameter(req, "ownerType");
        // req.getSession().setAttribute("ownerType", ownerType);

        String owner = (String) QueryCryptUtils.getParameter(req, "ownerId");
        // req.getSession().setAttribute("ownerId", owner);

        String stockModel = (String) QueryCryptUtils.getParameter(req, "stockModelId");
        // req.getSession().setAttribute("stockModelId", stockModel);

        String state = (String) QueryCryptUtils.getParameter(req, "stateId");
        //req.getSession().setAttribute("stateId", state);
        String strQuantity = (String) QueryCryptUtils.getParameter(req, "total");
        String strQuantityReq = (String) QueryCryptUtils.getParameter(req, "totalReq");





        if (ownerType == null || ownerType.trim().equals("")
                || owner == null || owner.trim().equals("")
                || stockModel == null || stockModel.trim().equals("")
                || state == null || state.trim().equals("")
                || strQuantity == null || strQuantity.trim().equals("")
                || strQuantityReq == null || strQuantityReq.trim().equals("")) {
            req.setAttribute("returnMsg", "stock.balance.noInfo");




            return "viewSerialProcess";




        }
        Long ownerTypeId = 0L;
        Long ownerId = 0L;
        Long stockModelId = 0L;
        Long stateId = 0L;

        ownerTypeId = Long.parseLong(ownerType);
        serialGoods.setOwnerType(ownerTypeId);
        ownerId = Long.parseLong(owner);
        serialGoods.setOwnerId(ownerId);
        stockModelId = Long.parseLong(stockModel);
        serialGoods.setStockModelId(stockModelId);
        String SQL_SELECT_OWNER = " from VShopStaff where id.ownerType = ? and id.ownerId = ? ";
        Query q = getSession().createQuery(SQL_SELECT_OWNER);
        q.setParameter(0, ownerTypeId);
        q.setParameter(1, ownerId);
        List lst = q.list();




        if (lst == null || lst.size() == 0) {
            req.setAttribute("returnMsg", "stock.balance.ownerNotExists");




            return "viewSerialProcess";




        }
        VShopStaff vShopStaff = (VShopStaff) lst.get(0);
        serialGoods.setOwnerCode(vShopStaff.getOwnerCode());
        serialGoods.setOwnerName(vShopStaff.getOwnerName());
        serialGoods.setTotal(Long.parseLong(strQuantity));
        serialGoods.setTotalReq(Long.parseLong(strQuantityReq));

        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        StockModel model = stockModelDAO.findById(stockModelId);




        if (model == null) {
            req.setAttribute("returnMsg", "stock.balance.stockModelNotExists");




            return "viewSerialProcess";




        }

        serialGoods.setStockModelName(model.getName());
        serialGoods.setStockTypeId(model.getStockTypeId());
        stateId = Long.parseLong(state);
        serialGoods.setStateId(stateId);




        List<StockTransSerial> tmpList = getRangeSerial(ownerTypeId, ownerId, model.getStockTypeId(), stockModelId, stateId, Constant.STATUS_USE);
        //setTabSession("listSerial", tmpList);
        req.setAttribute("lstSerial", tmpList);

        // Lay thong tin hang vua chon
//        List<StockTotal> lstStockTotal = new ArrayList();
//        Long ownerTypeLong = Long.parseLong(ownerType);
//        Long ownerIdLong = Long.parseLong(owner);
//        Long stockModelIdLong = Long.parseLong(stockModel);
//        Long stateIdLong = Long.parseLong(state);

//        lstStockTotal = findStockTotalFullByStockModelAndState(ownerTypeLong, ownerIdLong, stockModelIdLong, stateIdLong);
//        req.getSession().setAttribute("lstSerialDetail", lstStockTotal);

        String pageForward = "viewSerialProcess";
        log.info("# End method viewDetailQuantity");




        return pageForward;




    }

    /*
     * Author: TheTM DateCreate: 18/03/2010 Purpose: xem kho cua chuc nang xu ly
     * kho hang
     */
    public String viewStockDetailProcess() throws Exception {
        log.info("# Begin method viewStockDetailProcess");
        String pageForward = "processStockGoods";

        HttpServletRequest req = getRequest();





        try {

            List lstStockType = searchListStockTotal(goodsForm.getStockModelCode(), goodsForm.getShopCode(), goodsForm.getStaffCode(), goodsForm.getStateId());

            // req.getSession().setAttribute("lstStockGoods", lstStockType);




            if (lstStockType != null) {
                req.setAttribute("lstStockGoods", lstStockType);




            }
        } catch (Exception ex) {
            ex.printStackTrace();




            throw ex;




        }

        log.info("# End method viewStockDetail");




        return pageForward;




    }

    private List searchListStockTotal(String stockModelCode, String shopCode, String staffCode, Long stateId) throws Exception {
        HttpServletRequest req = getRequest();
        Long ownerTypeId = 0L;
        Long ownerId = 0L;




        if (staffCode != null && !staffCode.trim().equals("")) {
            //Check staff in shop
            String SQL_SELECT_STAFF = " from Staff where lower(staffCode) = ? and shopId = (select shopId from Shop where lower(shopCode)= ? )";
            Query q = getSession().createQuery(SQL_SELECT_STAFF);
            q.setParameter(0, staffCode.toLowerCase().trim());
            q.setParameter(1, shopCode.toLowerCase().trim());
            List lst = q.list();




            if (lst == null || lst.size() == 0) {
                req.setAttribute("returnMsg", "stock.balance.staffNotInShop");




                return null;




            }
            Staff staff = (Staff) lst.get(0);
            ownerTypeId = Constant.OWNER_TYPE_STAFF;
            ownerId = staff.getStaffId();




        } else {
            ownerTypeId = Constant.OWNER_TYPE_SHOP;
            String SQL_SELECT_SHOP = " from Shop where lower(shopCode) = ? and status = ? ";
            Query q = getSession().createQuery(SQL_SELECT_SHOP);
            q.setParameter(0, shopCode.toLowerCase().trim());
            q.setParameter(1, Constant.STATUS_USE);
            List lst = q.list();




            if (lst == null || lst.size() == 0) {
                req.setAttribute("returnMsg", "stock.balance.shopCodeNotValid");




                return null;




            }
            Shop shop = (Shop) lst.get(0);
            ownerId = shop.getShopId();




        }

        Long stockModelId = null;




        if (stockModelCode != null && !stockModelCode.trim().equals("")) {
            String SQL_SELECT_STOCK_MODEL = " from StockModel where status = ? and lower(stockModelCode) = ? ";
            Query q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
            q.setParameter(0, Constant.STATUS_USE);
            q.setParameter(1, stockModelCode.toLowerCase().trim());
            List lst = q.list();




            if (lst != null && lst.size() > 0) {
                StockModel stockModel = (StockModel) lst.get(0);
                stockModelId = stockModel.getStockModelId();




            } else {
                req.setAttribute("returnMsg", "stock.balance.stockModelCodeNotExists");




                return null;




            }
        }
        String SQL_SELECT_STOCK_TYPE = " from StockType where status= "
                + Constant.STATUS_USE
                + " and stockTypeId in " + " ( select stockTypeId from StockModel where status= "
                + Constant.STATUS_USE + " and stockModelId in "
                + " (select id.stockModelId from StockTotal where status = ? and id.ownerType= ? and id.ownerId =? )" + " ) ";




        if (stockModelId != null && !stockModelId.equals(0L)) {
            SQL_SELECT_STOCK_TYPE = " from StockType where status= "
                    + Constant.STATUS_USE + " and stockTypeId in "
                    + " ( select stockTypeId from StockModel where status= "
                    + Constant.STATUS_USE + " and stockModelId=" + stockModelId + ")";




        }

        Query q = getSession().createQuery(SQL_SELECT_STOCK_TYPE);




        if (stockModelId == null || stockModelId.equals(0L)) {
            q.setParameter(0, Constant.STATUS_USE);
            q.setParameter(1, ownerTypeId);
            q.setParameter(2, ownerId);




        }

        List<StockType> lstStockType = q.list();


        List<StockTotalFull> lstStockGoods = null;
        //String statusGoods = goodsForm.getStatusGoods();




        for (StockType stockType : lstStockType) {
            if (stateId == null || stateId.equals(0L)) {
                if (stockModelId != null && !stockModelId.equals(0L)) {
                    lstStockGoods = findStockTotalFullByStockModel(ownerTypeId, ownerId, stockModelId);




                } else {
                    lstStockGoods = findStockTotalFull(ownerTypeId, ownerId, stockType.getStockTypeId());




                }

                stockType.setListStockDetail(lstStockGoods);




            } else {
                //Long stateId = Long.parseLong(statusGoods);
                if (stockModelId != null && !stockModelId.equals(0L)) {
                    lstStockGoods = findStockTotalFullByStockModelAndStateId(ownerTypeId, ownerId, stockModelId, stateId);




                } else {
                    lstStockGoods = findStockTotalFulAndStateId(ownerTypeId, ownerId, stockType.getStockTypeId(), stateId);




                }

                stockType.setListStockDetail(lstStockGoods);




            }
        }
        return lstStockType;





    }

    public List findStockTotalFullByStockModelAndStateId(
            Long ownerType, Long ownerId, Long stockModelId, Long stateId) {
        log.info("finding al Goods In Stock ");




        try {
            String queryString = "from StockTotalFull where id.ownerType = ? and id.ownerId= ? and id.stockModelId = ? and status=? and id.stateId =?  ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerType);
            queryObject.setParameter(1, ownerId);
            queryObject.setParameter(2, stockModelId);
            queryObject.setParameter(3, Constant.STATUS_USE);
            queryObject.setParameter(4, stateId);




            return queryObject.list();




        } catch (RuntimeException re) {
            log.error("find all failed", re);




            throw re;




        }

    }

    public List findStockTotalFulAndStateId(
            Long ownerType, Long ownerId, Long stockTypeId, Long stateId) {
        log.info("finding al Goods In Stock ");




        try {
            String queryString = "from StockTotalFull where id.ownerType = ? and id.ownerId= ? and stockTypeId = ? and status=? and id.stateId =? ";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerType);
            queryObject.setParameter(1, ownerId);
            queryObject.setParameter(2, stockTypeId);
            queryObject.setParameter(3, Constant.STATUS_USE);
            queryObject.setParameter(4, stateId);




            return queryObject.list();




        } catch (RuntimeException re) {
            log.error("find all failed", re);




            throw re;




        }

    }

    public List findStockTotalFullWithSerialAndStateId(
            Long ownerType, Long ownerId, Long stateId) throws Exception {
        log.info("finding al Goods In Stock ");




        try {
            String queryString = "from StockTotalFull where id.ownerType = ? and id.ownerId= ? and status=? and id.stateId =? and quantity >0";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerType);
            queryObject.setParameter(1, ownerId);
            queryObject.setParameter(2, Constant.STATUS_USE);
            queryObject.setParameter(3, stateId);
            List<StockTotalFull> lst = queryObject.list();
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());




            for (StockTotalFull stockTotalFull : lst) {
                StockModel model = stockModelDAO.findById(stockTotalFull.getId().getStockModelId());




                if (model != null && model.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                    List<StockTransSerial> tmpList = getRangeSerial(ownerType, ownerId, stockTotalFull.getStockTypeId(), stockTotalFull.getId().getStockModelId(), stockTotalFull.getId().getStateId(), Constant.STATUS_USE);
                    stockTotalFull.setListSerial(tmpList);




                } else {
                    stockTotalFull.setListSerial(new ArrayList<StockTransSerial>());




                }

            }
            return lst;




        } catch (RuntimeException re) {
            log.error("find all failed", re);




            throw re;




        }

    }

    public List findStockTotalFullWithSerialAndStateId(
            Long ownerType, Long ownerId, Long stockModelId, Long stateId) throws Exception {
        log.info("finding al Goods In Stock ");




        try {
            String queryString = "from StockTotalFull where id.ownerType = ? and id.ownerId= ? and id.stockModelId= ? and status=? and id.stateId =? and quantity >0";
            Query queryObject = getSession().createQuery(queryString);
            queryObject.setParameter(0, ownerType);
            queryObject.setParameter(1, ownerId);
            queryObject.setParameter(2, stockModelId);
            queryObject.setParameter(3, Constant.STATUS_USE);
            queryObject.setParameter(4, stateId);
            List<StockTotalFull> lst = queryObject.list();
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());




            for (StockTotalFull stockTotalFull : lst) {
                StockModel model = stockModelDAO.findById(stockTotalFull.getId().getStockModelId());




                if (model != null && model.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                    List<StockTransSerial> tmpList = getRangeSerial(ownerType, ownerId, stockTotalFull.getStockTypeId(), stockTotalFull.getId().getStockModelId(), stockTotalFull.getId().getStateId(), Constant.STATUS_USE);
                    stockTotalFull.setListSerial(tmpList);




                } else {
                    stockTotalFull.setListSerial(new ArrayList<StockTransSerial>());




                }

            }
            return lst;




        } catch (RuntimeException re) {
            log.error("find all failed", re);




            throw re;




        }

    }

    /**
     *
     * author : ThanhNC date : 09/07/2010 purpose : Lay danh sach nhan vien
     * thuoc cua hang (F9)
     *
     */
    public List<ImSearchBean> getListStaffF9(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        String param = imSearchBean.getOtherParamValue();
        String[] arrParam = param.split(";");
        String shopCode = "";
        if (arrParam != null && arrParam.length >= 1) {
            shopCode = arrParam[0];
        }
        if (shopCode == null || shopCode.trim().equals("")) {
            return null;
        }
        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(staffCode, name) ");
        strQuery1.append("from Staff ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_USE);

        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append("and channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);

        strQuery1.append("and shopId  = (select shopId from Shop where lower(shopCode) = ? and status = ? ) ");
        listParameter1.add(shopCode.trim().toLowerCase());
        listParameter1.add(Constant.STATUS_USE);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum <= ? ");
        listParameter1.add(100L);
        strQuery1.append("order by nlssort(staffCode, 'nls_sort=Vietnamese') asc ");
        Query query1 = getSession().createQuery(strQuery1.toString());

        for (int i = 0; i
                < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

    public Long getListStaffF9Size(ImSearchBean imSearchBean) {
        req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String param = imSearchBean.getOtherParamValue();
        String[] arrParam = param.split(";");
        String shopCode = "";
        if (arrParam != null && arrParam.length >= 1) {
            shopCode = arrParam[0];
        }
        if (shopCode == null || shopCode.trim().equals("")) {
            return null;
        }
        //lay danh sach cua hang hien tai + cua hang cap duoi
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select count(*) ");
        strQuery1.append("from Staff ");
        strQuery1.append("where 1 = 1 ");

        strQuery1.append("and status = ? ");
        listParameter1.add(Constant.STATUS_USE);

        //Modified by : TrongLV
        //Modified date : 2011-08-16
        strQuery1.append("and channelTypeId in (select channelTypeId from ChannelType where objectType=? and isVtUnit = ?) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);

        strQuery1.append("and shopId  = (select shopId from Shop where lower(shopCode) = ? and status = ? ) ");
        listParameter1.add(shopCode.trim().toLowerCase());
        listParameter1.add(Constant.STATUS_USE);

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }
        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }
        List<Long> tmpList = query1.list();
        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);
        } else {
            return 0L;
        }
    }


    /*
     * Author: LamNV Date created: 12/02/2009 Purpose: cap nhap so quantity
     * stock_total khi xuat kho cho nhan vien
     */
    public void expStockStaffTotalIgnoreIssue(Long ownerType, Long ownerId,
            Long stateId, Long stockModelId, Long quantity) throws Exception {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        StringBuilder strBuilder = null;




        try {
            strBuilder = new StringBuilder();
            strBuilder.append("UPDATE stock_total ");
            strBuilder.append("SET modified_date = sysdate, ");
            strBuilder.append("    quantity = quantity - ? ");
            strBuilder.append("WHERE owner_type = ? ");
            strBuilder.append("  AND owner_id = ? ");
            strBuilder.append("  AND state_id = ? ");
            strBuilder.append("  AND stock_model_id = ? ");
            stmt = getSession().connection().prepareStatement(strBuilder.toString());

            stmt.setLong(1, quantity);
            stmt.setLong(2, ownerType);
            stmt.setLong(3, ownerId);
            stmt.setLong(4, stateId);
            stmt.setLong(5, stockModelId);
            stmt.executeUpdate();
        } catch (Exception re) {
            throw re;
        } finally {
            stmt.close();
            rs.close();
        }
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: Tim kiem giao dich xuat
     * kho cho CH
     */
    public List searchExpTransAgent(
            Object form, Long transType) throws Exception {
        /**
         * THANHNC_20110215_START log.
         */
        String function = "com.viettel.im.database.DAO.StockCommonDAO.searchExpTransAgent";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartCall(startDate, function, callId, "transType", transType);
        /**
         * End log
         */
        try {
            Session session = this.getSession();
            if (form == null || session == null) {
                logEndCall(startDate, new Date(), function, callId);
                return null;
            }

            String actionCode = null;
            Long actionType = null;
            Long transStatus = null;
            Long fromOwnerId = null;
            Long fromOwnerType = null;
            Long toOwnerId = null;
            Long toOwnerType = null;
            String fromDate = null;
            String toDate = null;
            Long maxTransStatus = null;
            //Neu loai giao dich la xuat kho
            if (form instanceof ExportStockForm) {
                ExportStockForm expFrm = (ExportStockForm) form;
                actionCode = expFrm.getActionCode();
                actionType = expFrm.getActionType();
                transStatus = expFrm.getTransStatus();
                fromOwnerId = expFrm.getFromOwnerId();
                fromOwnerType = expFrm.getFromOwnerType();
                toOwnerId = expFrm.getToOwnerId();
                toOwnerType = expFrm.getToOwnerType();
                fromDate = expFrm.getFromDate();
                toDate = expFrm.getToDate();
            }
//Neu loai giao dich la nhap kho

            if (form instanceof ImportStockForm) {
                ImportStockForm impFrm = (ImportStockForm) form;
                actionCode = impFrm.getActionCode();
                actionType = impFrm.getActionType();
                transStatus = impFrm.getTransStatus();
                fromOwnerId = impFrm.getFromOwnerId();
                fromOwnerType = impFrm.getFromOwnerType();
                toOwnerId = impFrm.getToOwnerId();
                toOwnerType = impFrm.getToOwnerType();
                fromDate = impFrm.getFromDate();
                toDate = impFrm.getToDate();
                //Neu la tim kiem phieu xuat de nhap hang --> chi tim nhung phieu xuat co trang thai da xuat hang
                if (impFrm.getActionType().equals(Constant.ACTION_TYPE_NOTE) && impFrm.getTransType().equals(Constant.TRANS_EXPORT)) {
                    maxTransStatus = Constant.TRANS_DONE;
                }

            }

            String SQL_SELECT_TRANS_DETAIL = "from SearchStockTrans a where  1=1 ";
            SQL_SELECT_TRANS_DETAIL += " and exists (select sh.channelTypeId from Shop sh, ChannelType ct where sh.channelTypeId = ct.channelTypeId "
                    + " and ct.isVtUnit = ? and ct.objectType = ? and sh.shopId = a.toOwnerId ) ";
            List lstParameter = new ArrayList();
            lstParameter.add(Constant.IS_NOT_VT_UNIT);
            lstParameter.add(Constant.OBJECT_TYPE_SHOP);
            if (transType != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.stockTransType= ? ";
                lstParameter.add(transType);
            }

            if (actionCode != null && !"".equals(actionCode)) {
                SQL_SELECT_TRANS_DETAIL += " and a.id.actionCode= ? ";
                lstParameter.add(actionCode.trim());
            }

            if (actionType != null && !actionType.equals(0L)) {
                SQL_SELECT_TRANS_DETAIL += " and a.actionType= ? ";
                lstParameter.add(actionType);
            }

            if (transStatus != null && !transStatus.equals(0L)) {
                SQL_SELECT_TRANS_DETAIL += " and a.stockTransStatus= ? ";
                lstParameter.add(transStatus);
            }

            if (fromOwnerId != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.fromOwnerId= ? ";
                lstParameter.add(fromOwnerId);
            }

            if (fromOwnerType != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.fromOwnerType= ? ";
                lstParameter.add(fromOwnerType);
                //Neu fromOwnerId==null va ownerType = shop (tim nhung lenh xuat cua kho cap duoi tra hang)
                // --> Chi lay nhung phieu xuat cua kho cap duoi xuat cho minh
                if (fromOwnerId == null && fromOwnerType.equals(Constant.OWNER_TYPE_SHOP) && toOwnerId != null) {
                    SQL_SELECT_TRANS_DETAIL += " and a.fromOwnerId in (select shopId from Shop where status = ? and parentShopId = ? ) ";
                    lstParameter.add(Constant.STATUS_USE);
                    lstParameter.add(toOwnerId);
                } else if (fromOwnerId == null && fromOwnerType.equals(Constant.OWNER_TYPE_STAFF) && toOwnerId != null) {
                    //tamdt1, 14/07/2010 bo sung them truogn hop nhap kho tu nhan vien
                    //-> chi lay nhung phieu xuat cua kho nhan vien xuat cho minh
                    SQL_SELECT_TRANS_DETAIL += " and a.fromOwnerId in (select staffId from Staff where status = ? and shopId = ? ) ";
                    lstParameter.add(Constant.STATUS_USE);
                    lstParameter.add(toOwnerId);
                }

            }


            if (toOwnerId != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.toOwnerId= ? ";
                lstParameter.add(toOwnerId);
            }

            if (toOwnerType != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.toOwnerType= ? ";
                lstParameter.add(toOwnerType);
                //Neu toOwnerId ==null va toOwnerType!=null -->chi tim nhung phieu xuat kho cho cap duoi
                if (toOwnerId == null && toOwnerType.equals(Constant.OWNER_TYPE_SHOP) && fromOwnerId != null) {
                    SQL_SELECT_TRANS_DETAIL += " and a.toOwnerId in (select shopId from Shop where status = ? and parentShopId = ? ) ";
                    lstParameter.add(Constant.STATUS_USE);
                    lstParameter.add(fromOwnerId);
                }

            }

            if (fromDate != null && !"".equals(fromDate)) {

                Date dfromDate = DateTimeUtils.convertStringToDate(fromDate);
                if (dfromDate != null) {
                    SQL_SELECT_TRANS_DETAIL += " and a.createDatetime >= ? ";
                    lstParameter.add(dfromDate);
                }

            }
            if (toDate != null && !"".equals(toDate)) {
                String stoDate = toDate.substring(0, 10) + " 23:59:59";
                Date dtoDate = DateTimeUtils.convertStringToTime(stoDate, "yyyy-MM-dd HH:mm:ss");

                if (dtoDate != null) {
                    SQL_SELECT_TRANS_DETAIL += " and a.createDatetime <= ? ";
                    lstParameter.add(dtoDate);
                }

            }
            if (maxTransStatus != null) {
                SQL_SELECT_TRANS_DETAIL += " and a.stockTransStatus >= ? and a.stockTransStatus <> ? ";
                lstParameter.add(maxTransStatus);
                lstParameter.add(Constant.TRANS_CANCEL);
            }

            SQL_SELECT_TRANS_DETAIL += " order by a.createDatetime desc ";
            Query q = session.createQuery(SQL_SELECT_TRANS_DETAIL);
            for (int idx = 0; idx < lstParameter.size(); idx++) {
                q.setParameter(idx, lstParameter.get(idx));
            }

            Date sqlTime = new Date();
            List lst = q.list();
            logSQL(callId, SQL_SELECT_TRANS_DETAIL, sqlTime, new Date());
            logEndCall(startDate, new Date(), function, callId);
            return lst;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        logEndCall(startDate, new Date(), function, callId);
        return null;
    }

    /*
     * Author NamLT chi lay DL khong lay CH
     *
     */
    public String popupSearchShopUnderlyingAgent() throws Exception {
        log.info("# Begin method ReportStockImpExpDAO.popupSearchShop");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "popupSearchShopAgent";
        //pageForward = "reportStockExpImp";
        //initData();
        String shopCode = req.getParameter("shopCode");
        String shopName = req.getParameter("shopName");
        //String notAllChild = "false";
        if (req.getParameter("notAllChild") != null && req.getParameter("notAllChild").trim().equals("true")) {
            //notAllChild = "true";
            autoCompleteSearchBean.setNotAllChild("true");
        }
        if (shopCode != null && !shopCode.equals("")) {
            autoCompleteSearchBean.setCode(shopCode.trim());
        }
        if (shopName != null && !shopName.equals("")) {
            autoCompleteSearchBean.setName(shopName.trim());
        }

        StringBuffer SQL_SELECT_SHOP = new StringBuffer("select shop_code as code, shop_name as name from tbl_shop_tree a,Channel_Type b where a.shop_status= ? ");
        List parameterList = new ArrayList();
        parameterList.add(Constant.STATUS_USE);

        SQL_SELECT_SHOP.append(" and a.root_id= ? ");
        //  parameterList.add("%$_" + userToken.getShopId() + "$_%");
        parameterList.add(userToken.getShopId());

        SQL_SELECT_SHOP.append(" and a.parent_shop_id = ? ");
        //  parameterList.add("%$_" + userToken.getShopId() + "$_%");
        parameterList.add(userToken.getShopId());


        if (autoCompleteSearchBean.getCode() != null && !"".equals(autoCompleteSearchBean.getCode())) {
            SQL_SELECT_SHOP.append(" and lower(a.shop_code) like ? ");
            parameterList.add("%" + autoCompleteSearchBean.getCode().trim().toLowerCase() + "%");
        }
        if (autoCompleteSearchBean.getName() != null && !"".equals(autoCompleteSearchBean.getName())) {
            SQL_SELECT_SHOP.append(" and lower(a.shop_name) like ? ");
            parameterList.add("%" + autoCompleteSearchBean.getName().trim().toLowerCase() + "%");
        }
        SQL_SELECT_SHOP.append(" and a.channel_type_id=b.channel_type_id");
        SQL_SELECT_SHOP.append(" and b.object_type = ? and b.is_vt_unit = ?");
        parameterList.add(Constant.OBJECT_TYPE_SHOP);
        parameterList.add(Constant.IS_NOT_VT_UNIT);

        SQL_SELECT_SHOP.append("  order by a.shop_level asc,b.is_vt_unit asc, nlssort(a.shop_name,'nls_sort=Vietnamese') asc ");
        Query q = getSession().createSQLQuery(SQL_SELECT_SHOP.toString()).addScalar("code", Hibernate.STRING).addScalar("name", Hibernate.STRING).setResultTransformer(Transformers.aliasToBean(AutoCompleteSearchBean.class));
        for (int i = 0; i < parameterList.size(); i++) {
            q.setParameter(i, parameterList.get(i));
        }

        q.setMaxResults(300);

        List lst = q.list();
        req.setAttribute("lstResult", lst);
        req.setAttribute("returnType", "shop");
        if (req.getParameter("functionName") != null && !req.getParameter("functionName").equals("")) {
            autoCompleteSearchBean.setFunctionName(req.getParameter("functionName"));
        }
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 10/03/2009 Purpose: Chon loai mat hang -->
     * mat hang (con trong kho) duoc khai bao ban dut
     */
    public String getStockModelAgentSale() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            //Chon hang hoa tu loai hang hoa
            String stockTypeId = httpServletRequest.getParameter("stockTypeId");
            String ownerCode = httpServletRequest.getParameter("ownerCode").toString();
            Long channelId = getChannelByShopCode(ownerCode);
            String[] header = {"", getText(SELECT_RES)};
            listItemsSelectBox.add(header);
            if (channelId == null) {
                return "success";
            }
            if (stockTypeId != null && !"".equals(stockTypeId.trim())) {
                Long id = Long.parseLong(stockTypeId.trim());
                String SQL_SELECT_STOCK_MODEL = "select stockModelId, name from StockModel where stockTypeId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Long ownerType = goodsForm.getOwnerType();
                Long ownerId = goodsForm.getOwnerId();
                if (ownerType != null && ownerId != null) {
                    SQL_SELECT_STOCK_MODEL = "select a.stockModelId, a.name from StockModel a where a.stockTypeId= ? and a.status = ?  "
                            + " and exists( select sd.stockModelId from StockDeposit sd where sd.status = 1 and sd.chanelTypeId = ? and sd.transType = 2 and sd.stockModelId = a.stockModelId and "
                            + " (trunc(sd.dateFrom) <= trunc(sysdate) and (sd.dateTo is null or trunc(sd.dateTo) >= trunc(sysdate)))) "
                            + " and exists " + " ( select b.id.stockModelId from StockTotal b where b.id.stockModelId =a.stockModelId and b.status = "
                            + Constant.STATUS_USE + " and b.id.ownerId = " + ownerId + " and b.id.ownerType=" + ownerType + " and  (quantityIssue >0 or quantityDial>0) ) "
                            + " order by a.name";
                }
                Query q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                q.setParameter(0, id);
                q.setParameter(1, Constant.STATUS_USE);
                q.setParameter(2, channelId);
                List lstStockModel = q.list();
                listItemsSelectBox.addAll(lstStockModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "success";
    }

    /*
     * Author: ThanhNC Date created: 10/03/2009 Purpose: Chon loai mat hang -->
     * mat hang (con trong kho) duoc khai bao ban dut
     */
    public String getStockModelAgentDeposit() throws Exception {

        try {
            HttpServletRequest httpServletRequest = getRequest();
            //Chon hang hoa tu loai hang hoa
            String stockTypeId = httpServletRequest.getParameter("stockTypeId");
            String ownerCode = httpServletRequest.getParameter("ownerCode").toString();
            Long channelId = getChannelByShopCode(ownerCode);
            String[] header = {"", getText(SELECT_RES)};
            listItemsSelectBox.add(header);
            if (channelId == null) {
                return "success";
            }
            if (stockTypeId != null && !"".equals(stockTypeId.trim())) {
                Long id = Long.parseLong(stockTypeId.trim());
                String SQL_SELECT_STOCK_MODEL = "select stockModelId, name from StockModel where stockTypeId= ? and status = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
                Long ownerType = goodsForm.getOwnerType();
                Long ownerId = goodsForm.getOwnerId();
                if (ownerType != null && ownerId != null) {
                    SQL_SELECT_STOCK_MODEL = "select a.stockModelId, a.name from StockModel a where a.stockTypeId= ? and a.status = ?  "
                            + " and exists( select sd.stockModelId from StockDeposit sd where sd.status = 1 and sd.chanelTypeId = ? and sd.transType = 1 and sd.stockModelId = a.stockModelId and "
                            + " (trunc(sd.dateFrom) <= trunc(sysdate) and (sd.dateTo is null or trunc(sd.dateTo) >= trunc(sysdate)))) "
                            + " and exists " + " ( select b.id.stockModelId from StockTotal b where b.id.stockModelId =a.stockModelId and b.status = "
                            + Constant.STATUS_USE + " and b.id.ownerId = " + ownerId + " and b.id.ownerType=" + ownerType + " and  (quantityIssue >0 or quantityDial>0) ) "
                            + " order by a.name";
                }
                Query q = getSession().createQuery(SQL_SELECT_STOCK_MODEL);
                q.setParameter(0, id);
                q.setParameter(1, Constant.STATUS_USE);
                q.setParameter(2, channelId);
                List lstStockModel = q.list();
                listItemsSelectBox.addAll(lstStockModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "success";
    }

    public Long getChannelByShopCode(String shopCode) throws Exception {
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        List<Shop> list = shopDAO.findByShopCode(shopCode);
        if (list != null && list.size() > 0) {
            return list.get(0).getChannelTypeId();
        } else {
            return null;
        }
    }


    /*
     * Author: ThanhNC Date created: 10/03/2009 Purpose: Chon mat hang xuat kho
     * --> check mat hang co the boc tham khong
     */
    public String selectStockModelAgentSale() throws Exception {
        log.info("# Begin method selectStockModel");
        HttpServletRequest req = getRequest();
        String pageForward = "stockGoodsAgentSale";

        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");
        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        }
        /*
         * LamNV ADD STOP
         */

        //Mac dinh set trang thai boc tham la ko phai boc tham
        goodsForm.setCanDial(Constant.EXP_NON_DIAL);

        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);


        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        if (goodsForm.getStockTypeId() != null) {
            List lstStockModel = getStockModelInShop(goodsForm.getOwnerType(), goodsForm.getOwnerId(), goodsForm.getStockTypeId(), Constant.STATUS_USE);
            //List lstStockModel = stockModelDAO.findByProperty(StockModelDAO.STOCK_TYPE_ID, goodsForm.getStockTypeId());
            req.setAttribute("lstStockModel", lstStockModel);
        }

        if (goodsForm.getStockModelId() != null) {

            StockModel model = stockModelDAO.findById(goodsForm.getStockModelId());
            if (model != null) {
                Long checkDial = model.getCheckDial();
                if (checkDial != null && checkDial.equals(Constant.MUST_DRAW)) {
                    goodsForm.setCanDial(Constant.EXP_CAN_DIAL);
                }

            }

        }


        log.info("# End method selectStockModel");
        return pageForward;
    }

    public String selectStockModelAgentDeposit() throws Exception {
        log.info("# Begin method selectStockModel");
        HttpServletRequest req = getRequest();
        String pageForward = "stockGoodsAgentDeposit";

        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");
        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        }
        /*
         * LamNV ADD STOP
         */

        //Mac dinh set trang thai boc tham la ko phai boc tham
        goodsForm.setCanDial(Constant.EXP_NON_DIAL);

        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);


        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        if (goodsForm.getStockTypeId() != null) {
            List lstStockModel = getStockModelInShop(goodsForm.getOwnerType(), goodsForm.getOwnerId(), goodsForm.getStockTypeId(), Constant.STATUS_USE);
            //List lstStockModel = stockModelDAO.findByProperty(StockModelDAO.STOCK_TYPE_ID, goodsForm.getStockTypeId());
            req.setAttribute("lstStockModel", lstStockModel);
        }

        if (goodsForm.getStockModelId() != null) {

            StockModel model = stockModelDAO.findById(goodsForm.getStockModelId());
            if (model != null) {
                Long checkDial = model.getCheckDial();
                if (checkDial != null && checkDial.equals(Constant.MUST_DRAW)) {
                    goodsForm.setCanDial(Constant.EXP_CAN_DIAL);
                }

            }

        }

        log.info("# End method selectStockModel");
        return pageForward;
    }

    /*
     * @Desciption : Source from Inventory @Desciption : get shop name agent
     * @Author :add by hieptd @Date : 02122011
     */
    /*
     * @Author: ThanhNC @Date created: 03/08/2009 @Description: Get shop name
     */
    public String getShopNameAgent() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            String shopCode = req.getParameter("shopCode");
            String target = req.getParameter("target");
            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            if (shopCode != null && shopCode.trim().length() > 0) {

                String queryString = "from Shop where lower(shopCode) = ? and status= ? and (shopPath like ? or shopId= ?)"
                        + " and channelTypeId in (select channelTypeId from ChannelType where objectType = ? and isVtUnit = ?) ";
                Query queryObject = getSession().createQuery(queryString);
                queryObject.setParameter(0, shopCode.trim().toLowerCase());
                queryObject.setParameter(1, Constant.STATUS_USE);
                queryObject.setParameter(2, "%_" + userToken.getShopId() + "_%");
                queryObject.setParameter(3, userToken.getShopId());
                queryObject.setParameter(4, Constant.OBJECT_TYPE_SHOP);
                queryObject.setParameter(5, Constant.NOT_IS_VT_UNIT);

                List<Shop> lstShop = queryObject.list();
                if (lstShop != null && lstShop.size() > 0) {
                    this.listShop.put(target, lstShop.get(0).getName());
                }
            }

        } catch (Exception ex) {
            throw ex;
        }

        return "autoSelectShop";
    }

    /*
     * Author: ThanhNC Date create: 28/03/2009 Purpose: Them mat hang vao danh
     * sach hang lap lenh xuat kho cho dai ly
     */
    public String addGoodsAgentSale() throws Exception {
        log.info("# Begin method addGoodsAgentSale");
        HttpServletRequest req = getRequest();
        String pageForward = "editGoodsAgentSale";
        String pageId = req.getParameter("pageId");

        //----------------------------------------------------------------------
        //tamdt1, 16/09/2010, start bo sung chon mat hang bang F9
        //--------kiem tra ma mat hang co ton tai khong
        String stockModelCodeF9 = this.goodsForm.getStockModelCode();
        Long quantityF9 = this.goodsForm.getQuantity();

        if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("") || quantityF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trư�?ng bắt buộc không được để trống
            return pageForward;
        } //trim cac truong can thiet
        stockModelCodeF9 = stockModelCodeF9.trim();
        this.goodsForm.setStockModelCode(stockModelCodeF9);
        //
        StockModelDAO stockModelDAOF9 = new StockModelDAO();
        stockModelDAOF9.setSession(this.getSession());
        StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9);
        if (stockModelF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        } else {
            this.goodsForm.setStockTypeId(stockModelF9.getStockTypeId());
            this.goodsForm.setStockModelId(stockModelF9.getStockModelId());
            this.goodsForm.setStockModelCode(stockModelF9.getStockModelCode());
            this.goodsForm.setStockModelName(stockModelF9.getName());
        } //tamdt1, 16/09/2010, end
        //----------------------------------------------------------------------

        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        } /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");

        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        }
        /*
         * LamNV ADD STOP
         */
//Danh sach loai tai nguyen

        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);
        //Danh sach stockModel
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());

        if (goodsForm.getStockTypeId() == null || goodsForm.getStockModelId() == null || goodsForm.getStateId() == null || goodsForm.getQuantity() == null) {
//            req.setAttribute("returnMsg", "Chưa nhập các đi�?u kiện bắt buộc");
            req.setAttribute("returnMsg", "ERR.STK.064");
            if (goodsForm.getStockTypeId() != null) {
                req.setAttribute("lstStockModel", getStockModelInShop(goodsForm.getOwnerType(), goodsForm.getOwnerId(), goodsForm.getStockTypeId(), Constant.STATUS_USE));
            }
            return pageForward;
        }

        if (goodsForm.getQuantity().equals(0L)) {
//            req.setAttribute("returnMsg", "Số lượng phải là số nguyên dương");
            req.setAttribute("returnMsg", "ERR.STK.011");
            if (goodsForm.getStockTypeId() != null) {
                req.setAttribute("lstStockModel", getStockModelInShop(goodsForm.getOwnerType(), goodsForm.getOwnerId(), goodsForm.getStockTypeId(), Constant.STATUS_USE));
            }

            return pageForward;
        }

        List lstStockModel = getStockModelInShop(goodsForm.getOwnerType(), goodsForm.getOwnerId(), goodsForm.getStockTypeId(), Constant.STATUS_USE);
        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
        if (lstGoods == null) {
            lstGoods = new ArrayList();
        }

        GoodsForm gF = getGoodsForm();
        if (gF.getQuantity() == null) {
//            req.setAttribute("returnMsg", "Chưa nhập số lượng hàng hoá cần xuất kho");
            req.setAttribute("returnMsg", "ERR.STK.073");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        } //Check trung lap

        StockTransFull tmp;
        for (int idx = 0; idx < lstGoods.size(); idx++) {
            tmp = (StockTransFull) lstGoods.get(idx);
            if (tmp.getStockTypeId().equals(gF.getStockTypeId()) && tmp.getStockModelId().equals(gF.getStockModelId()) && tmp.getStateId().equals(gF.getStateId())) {
//                req.setAttribute("returnMsg", "Hàng hoá thêm vào bị trùng lặp.");
                req.setAttribute("returnMsg", "ERR.STK.074");
                req.setAttribute("lstStockModel", lstStockModel);
                return pageForward;
            }
        }

        StockTransFull trans = new StockTransFull();
        //Lay thong tin loai hang hoa

        if (gF.getStockTypeId() == null) {
//            req.setAttribute("returnMsg", "Chưa ch�?n loại hàng hoá");
            req.setAttribute("returnMsg", "ERR.STK.075");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        StockType stockType = (StockType) stockTypeDAO.findById(gF.getStockTypeId());

        if (stockType == null) {
//            req.setAttribute("returnMsg", "Loại hàng hoá không tồn tại trên hệ thống");
            req.setAttribute("returnMsg", "ERR.STK.076");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        trans.setStockTypeId(gF.getStockTypeId());
        trans.setStockTypeName(stockType.getName());
        //Lay thong tin hang hoa

        if (gF.getStockModelId() == null) {
//            req.setAttribute("returnMsg", "Chưa ch�?n hàng hoá");
            req.setAttribute("returnMsg", "ERR.STK.025");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        StockModel stockModel = (StockModel) stockModelDAO.findById(gF.getStockModelId());

        if (stockModel == null) {
//            req.setAttribute("returnMsg", "Hàng hoá không tồn tại trên hệ thống.");
            req.setAttribute("returnMsg", "ERR.STK.059");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        //Check han muc
        //Chua chon don vi nhap => chua check duoc
//        Long priceBasic = priceDAO.findBasicPrice(stockModel.getStockModelId(), pricePolicy);
//        if (priceBasic == null) {
//            req.setAttribute("returnMsg", "ERR.SAE.143");
//            req.setAttribute("lstStockModel", lstStockModel);
//            return pageForward;
//        }
        //Check han muc

        trans.setStockModelId(gF.getStockModelId());
        trans.setStockModelCode(stockModel.getStockModelCode());
        trans.setStockModelName(stockModel.getName());
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        AppParams appParams = appParamsDAO.findAppParams("STOCK_MODEL_UNIT", stockModel.getUnit());

        if (appParams != null) {
            trans.setUnit(appParams.getName());
        }

        trans.setCheckSerial(stockModel.getCheckSerial());
        Long checkDial = 0L;
        if (gF.getCheckDial() != null && gF.getCheckDial().equals("true")) {
            checkDial = 1L;
        }

        trans.setCheckDial(checkDial);
        trans.setFromOwnerType(gF.getOwnerType());
        trans.setFromOwnerId(gF.getOwnerId());
        //Check so luong yeu cau so voi so luong hang hoa thuc con trong kho

        if (!checkStockGoods(goodsForm.getOwnerId(), goodsForm.getOwnerType(), goodsForm.getStockModelId(), goodsForm.getStateId(), goodsForm.getQuantity(), checkDial, getSession())) {
//            req.setAttribute("returnMsg", "Số lượng hàng hoá trong kho nh�? hơn số lượng yêu cầu xuất.");
            req.setAttribute("returnMsg", "ERR.STK.077");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;

        }
//        //Check truong hop xuat hang cho dai ly -->tat ca cac mat hang xuat di phai cung VAT
//        if (goodsForm.getExpType() != null && goodsForm.getExpType().equals("dial")) {
//            if (lstGoods != null && lstGoods.size() > 0) {
//                StockTransFull stockTransFull = (StockTransFull) lstGoods.get(0);
//                Long firstStockModelId = stockTransFull.getStockModelId();
//                String priceType = ResourceBundleUtils.getResource("PRICE_TYPE_AGENT");
//                Long firstVAT = getVATByStockModelIdAndType(firstStockModelId, priceType);
//                if (!checkVAT(firstStockModelId, firstVAT, priceType)) {
//                    req.setAttribute("returnMsg", "Các mặt hàng xuất bán cho đại lý phải khai báo giá và có cùng VAT");
//                    req.setAttribute("lstStockModel", lstStockModel);
//                    return pageForward;
//                }
//
//            }
//        }

//Lay so luong va trang thai hang
        trans.setQuantity(gF.getQuantity());

        trans.setStateId(gF.getStateId());
        trans.setNote(gF.getNote());

        /*
         * LamNV ADD START
         */

        if (revokeColl != null && revokeColl.equals("true")) {
            Double amount = 0.0;
            String amountAttr = "amount" + pageId;
            Double price = gF.getQuantity() * getPriceDeposit(gF.getStockModelId());
            trans.setPrice(price);

            trans.setPriceDisplay(NumberUtils.rounđAndFormatAmount(price));

            if (req.getSession().getAttribute(amountAttr) != null && req.getSession().getAttribute(amountAttr) != "") {
                amount = Double.parseDouble(req.getSession().getAttribute(amountAttr).toString());
            }

            amount += price;
            req.getSession().setAttribute(amountAttr, amount);
            setTabSession("amountDisplay", NumberUtils.rounđAndFormatAmount(amount));

            //Tinh lai tien
            //Long amountTax = getAmountTax(lstGoods);

        }
        /*
         * LamNV ADD STOP
         */

        lstGoods.add(trans);
        /*
         * Kiem tra tong gia goc HH xuat cho NV + gia tri HH hien tai cua NV <=
         * han muc cua NV
         */
        /*
         * Double sourceAmount = sumAmountByGoodsList(lstGoods); if
         * (sourceAmount != null && sourceAmount >= 0) { if
         * (!checkCurrentDebitWhenImplementTrans(gF.getOwnerId(),
         * gF.getOwnerType(), sourceAmount)) { req.setAttribute("returnMsg",
         * "ERR.LIMIT.0012"); req.setAttribute("lstStockModel", lstStockModel);
         * return pageForward; } } else { req.setAttribute("returnMsg",
         * "ERR.LIMIT.0014"); req.setAttribute("lstStockModel", lstStockModel);
         * return pageForward; }
         */
        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
        goodsForm.resetForm();

        log.info("# End method addGoodsAgentSale");
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: prepare edit goods in
     * goods list
     */
    public String prepareEditGoodsAgentSale() throws Exception {
        log.info("# Begin method prepareEditGoodsAgentSale");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");


        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        } /*
         * LamNV ADD STOP
         */
        String pageForward = "editGoodsAgentSale";

        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        }
        String stockModelId = (String) req.getParameter("stockModelId");
        String stockTypeId = (String) req.getParameter("stockTypeId");
        String stateId = (String) QueryCryptUtils.getParameter(req, "stateId");
        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);


        if (stockModelId != null && !"".equals(stockModelId.trim()) && stockTypeId != null && !"".equals(stockTypeId.trim()) && stateId != null && !"".equals(stateId.trim())) {
            Long lStockModelId = Long.parseLong(stockModelId.trim());
            Long lStockTypeId = Long.parseLong(stockTypeId.trim());
            Long lStateId = Long.parseLong(stateId.trim());
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            List lstStockModel = stockModelDAO.findByProperty(StockModelDAO.STOCK_TYPE_ID, lStockTypeId);
            req.setAttribute("lstStockModel", lstStockModel);
            //req.setAttribute("listItemsCombo", lstStockModel);
            StockTransFull trans;
            for (int i = 0; i
                    < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);
                if (trans.getStockModelId().equals(lStockModelId) && trans.getStockTypeId().equals(lStockTypeId) && trans.getStateId().equals(lStateId)) {
                    goodsForm.setStockTypeId(lStockTypeId);
                    goodsForm.setStockModelId(lStockModelId);
                    goodsForm.setStockModelCode(trans.getStockModelCode());
                    goodsForm.setStockModelName(trans.getStockModelName());
                    if (trans.getCheckDial().equals(1L)) {
                        goodsForm.setCheckDial("true");
                    } else {
                        goodsForm.setCheckDial("false");
                    }

                    goodsForm.setStateId(trans.getStateId());
                    goodsForm.setQuantity(trans.getQuantity().longValue());
                    goodsForm.setNote(trans.getNote());
                }

                if (goodsForm.getStockModelId() != null) {

                    StockModel model = stockModelDAO.findById(goodsForm.getStockModelId());
                    if (model != null) {
                        Long checkDial = model.getCheckDial();
                        if (checkDial != null && checkDial.equals(Constant.MUST_DRAW)) {
                            goodsForm.setCanDial(Constant.EXP_CAN_DIAL);
                        } else {
                            goodsForm.setCanDial(Constant.EXP_NON_DIAL);
                        }

                    }

                }
                //Danh sach loai tai nguyen
                StockTypeDAO stockTypeDAO = new StockTypeDAO();
                stockTypeDAO.setSession(this.getSession());
                List lstStockType = stockTypeDAO.findAllAvailable();
                req.setAttribute("lstStockType", lstStockType);
            }

        } else {
//            req.setAttribute("returnMsg", "Chưa ch�?n hàng hoá để sửa.");
            req.setAttribute("returnMsg", "ERR.STK.089");
        }

        req.getSession().setAttribute("isEdit" + pageId, true);

        log.info("# End method prepareEditGoods");
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: edit goods in goods
     * list
     */
    public String editGoodsAgentSale() throws Exception {
        log.info("# Begin method editGoodsAgentSale");
        String pageForward = "editGoodsAgentSale";
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        }

        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);

        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");
        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        }
        /*
         * LamNV ADD STOP
         */
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);

        //Danh sach stockModel
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        List lstStockModel = stockModelDAO.findByStockTypeIdAndStatus(goodsForm.getStockTypeId(), Constant.STATUS_USE);

        if (lstGoods != null) {
            Long lStockModelId = goodsForm.getStockModelId();
            Long lStockTypeId = goodsForm.getStockTypeId();
            Long lStateId = goodsForm.getStateId();

            StockTransFull trans = null;
            boolean isOk = false;

            for (int i = 0; i
                    < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);

                if (trans.getStockModelId().equals(lStockModelId) && trans.getStockTypeId().equals(lStockTypeId) && trans.getStateId().equals(lStateId)) {
                    // initFormExport(req);
                    Long checkDial = 0L;
                    if (goodsForm.getCheckDial() != null && goodsForm.getCheckDial().equals("true")) {
                        checkDial = 1L;
                    }

                    if (goodsForm.getQuantity().equals(0L)) {
//                        req.setAttribute("returnMsg", "Số lượng phải là số nguyên dương");
                        req.setAttribute("returnMsg", "ERR.STK.011");
                        req.setAttribute("lstStockModel", lstStockModel);
                        return pageForward;
                    }
                    //Check so luong yeu cau so voi so luong hang hoa thuc con trong kho

                    if (!checkStockGoods(goodsForm.getOwnerId(), goodsForm.getOwnerType(), goodsForm.getStockModelId(), goodsForm.getStateId(), goodsForm.getQuantity(), checkDial, getSession())) {
//                        req.setAttribute("returnMsg", "Số lượng hàng hoá trong kho nh�? hơn số lượng yêu cầu xuất.");
                        req.setAttribute("returnMsg", "ERR.STK.077");
                        req.setAttribute("lstStockModel", lstStockModel);
                    }
                    trans.setQuantity(goodsForm.getQuantity());
                    trans.setNote(goodsForm.getNote());
                    trans.setCheckDial(checkDial);
                    /*
                     * LamNV ADD START
                     */

                    if (revokeColl != null && revokeColl.equals("true")) {
                        Double price = 1.0 * trans.getQuantity() * getPriceDeposit(trans.getStockModelId());
                        trans.setPrice(price);
                    } /*
                     * LamNV ADD STOP
                     */
                    isOk = true;
                }

            }
            if (!isOk) {
//                req.setAttribute("returnMsg", "Sửa không thành công. Hàng hoá không tồn tại trong danh sách.");
                req.setAttribute("returnMsg", "ERR.STK.090");
                req.getSession().removeAttribute("isEdit" + pageId);
                goodsForm.resetForm();
                return pageForward;
            }

            req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
            req.getSession().removeAttribute("isEdit" + pageId);
            req.setAttribute("returnMsg", "ERR.STK.091");
        } else {
//            req.setAttribute("returnMsg", "Danh sách hàng hoá rỗng. Mặt hàng đã bị xoá kh�?i danh sách");
            req.setAttribute("returnMsg", "ERR.STK.092");
        }

        /*
         * LamNV add start
         */
        if (revokeColl != null && revokeColl.equals("true")) {
            Double amountTax = getAmountTax(lstGoods);
            req.getSession().setAttribute("amount" + pageId, amountTax);
            req.getSession().setAttribute("amountDisplay" + pageId, NumberUtils.rounđAndFormatAmount(amountTax));
        } /*
         * LamNV add stop
         */

        log.info("# End method editGoodsAgentSale");

        goodsForm.resetForm();
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: cacel sua hang hoa
     */
    public String cancelEditGoodsAgentSale() throws Exception {
        log.info("# Begin method cancelEditGoodsAgentSale");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        String pageForward = "editGoodsAgentSale";

        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        }
        goodsForm.resetForm();
        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");
        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        }
        /*
         * LamNV ADD STOP
         */
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);
        req.getSession().removeAttribute("isEdit" + pageId);

        log.info("# End method cancelEditGoodsAgentSale");
        return pageForward;
    }

    /*
     * @Description : add goods agent @Author : add by hieptd @Source : from
     * Inventory @Date : 02122011
     */
    /*
     * Author: ThanhNC Date create: 28/03/2009 Purpose: Them mat hang vao danh
     * sach hang xuat kho
     */
    public String addGoodsAgentDeposit() throws Exception {
        log.info("# Begin method addGoodsAgentSale");
        HttpServletRequest req = getRequest();
        String pageForward = "editGoodsAgentDeposit";
        String pageId = req.getParameter("pageId");

        //----------------------------------------------------------------------
        //tamdt1, 16/09/2010, start bo sung chon mat hang bang F9
        //--------kiem tra ma mat hang co ton tai khong
        String stockModelCodeF9 = this.goodsForm.getStockModelCode();
        Long quantityF9 = this.goodsForm.getQuantity();

        if (stockModelCodeF9 == null || stockModelCodeF9.trim().equals("") || quantityF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.114"); //!!!Lỗi. Các trư�?ng bắt buộc không được để trống
            return pageForward;
        } //trim cac truong can thiet
        stockModelCodeF9 = stockModelCodeF9.trim();
        this.goodsForm.setStockModelCode(stockModelCodeF9);
        //
        StockModelDAO stockModelDAOF9 = new StockModelDAO();
        stockModelDAOF9.setSession(this.getSession());
        StockModel stockModelF9 = stockModelDAOF9.getStockModelByCode(stockModelCodeF9);
        if (stockModelF9 == null) {
            req.setAttribute(REQUEST_MESSAGE, "ERR.STK.115"); //!!!Lỗi. Mã mặt hàng không tồn tại
            return pageForward;
        } else {
            this.goodsForm.setStockTypeId(stockModelF9.getStockTypeId());
            this.goodsForm.setStockModelId(stockModelF9.getStockModelId());
            this.goodsForm.setStockModelCode(stockModelF9.getStockModelCode());
            this.goodsForm.setStockModelName(stockModelF9.getName());
        } //tamdt1, 16/09/2010, end
        //----------------------------------------------------------------------

        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        } /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");

        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        }
        /*
         * LamNV ADD STOP
         */
        //Danh sach loai tai nguyen

        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);
        //Danh sach stockModel
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());

        if (goodsForm.getStockTypeId() == null || goodsForm.getStockModelId() == null || goodsForm.getStateId() == null || goodsForm.getQuantity() == null) {
            //req.setAttribute("returnMsg", "Chưa nhập các đi�?u kiện bắt buộc");
            req.setAttribute("returnMsg", "ERR.STK.064");
            if (goodsForm.getStockTypeId() != null) {
                req.setAttribute("lstStockModel", getStockModelInShop(goodsForm.getOwnerType(), goodsForm.getOwnerId(), goodsForm.getStockTypeId(), Constant.STATUS_USE));
            }
            return pageForward;
        }

        if (goodsForm.getQuantity().equals(0L)) {
            // req.setAttribute("returnMsg", "Số lượng phải là số nguyên dương");
            req.setAttribute("returnMsg", "ERR.STK.011");
            if (goodsForm.getStockTypeId() != null) {
                req.setAttribute("lstStockModel", getStockModelInShop(goodsForm.getOwnerType(), goodsForm.getOwnerId(), goodsForm.getStockTypeId(), Constant.STATUS_USE));
            }

            return pageForward;
        }

        List lstStockModel = getStockModelInShop(goodsForm.getOwnerType(), goodsForm.getOwnerId(), goodsForm.getStockTypeId(), Constant.STATUS_USE);
        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
        if (lstGoods == null) {
            lstGoods = new ArrayList();
        }

        GoodsForm gF = getGoodsForm();
        if (gF.getQuantity() == null) {
//            req.setAttribute("returnMsg", "Chưa nhập số lượng hàng hoá cần xuất kho");
            req.setAttribute("returnMsg", "ERR.STK.073");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        } //Check trung lap

        StockTransFull tmp;
        for (int idx = 0; idx < lstGoods.size(); idx++) {
            tmp = (StockTransFull) lstGoods.get(idx);
            if (tmp.getStockTypeId().equals(gF.getStockTypeId()) && tmp.getStockModelId().equals(gF.getStockModelId()) && tmp.getStateId().equals(gF.getStateId())) {
//                req.setAttribute("returnMsg", "Hàng hoá thêm vào bị trùng lặp.");
                req.setAttribute("returnMsg", "ERR.STK.074");
                req.setAttribute("lstStockModel", lstStockModel);
                return pageForward;
            }
        }

        StockTransFull trans = new StockTransFull();
        //Lay thong tin loai hang hoa

        if (gF.getStockTypeId() == null) {
//            req.setAttribute("returnMsg", "Chưa ch�?n loại hàng hoá");
            req.setAttribute("returnMsg", "ERR.STK.075");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        StockType stockType = (StockType) stockTypeDAO.findById(gF.getStockTypeId());

        if (stockType == null) {
//            req.setAttribute("returnMsg", "Loại hàng hoá không tồn tại trên hệ thống");
            req.setAttribute("returnMsg", "ERR.STK.076");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        trans.setStockTypeId(gF.getStockTypeId());
        trans.setStockTypeName(stockType.getName());
        //Lay thong tin hang hoa

        if (gF.getStockModelId() == null) {
//            req.setAttribute("returnMsg", "Chưa ch�?n hàng hoá");
            req.setAttribute("returnMsg", "ERR.STK.025");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        StockModel stockModel = (StockModel) stockModelDAO.findById(gF.getStockModelId());

        if (stockModel == null) {
//            req.setAttribute("returnMsg", "Hàng hoá không tồn tại trên hệ thống.");
            req.setAttribute("returnMsg", "ERR.STK.059");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;
        }

        //Check han muc
        //Chua chon don vi nhap => chua check duoc
//        Long priceBasic = priceDAO.findBasicPrice(stockModel.getStockModelId(), pricePolicy);
//        if (priceBasic == null) {
//            req.setAttribute("returnMsg", "ERR.SAE.143");
//            req.setAttribute("lstStockModel", lstStockModel);
//            return pageForward;
//        }
        //Check han muc

        trans.setStockModelId(gF.getStockModelId());
        trans.setStockModelCode(stockModel.getStockModelCode());
        trans.setStockModelName(stockModel.getName());
        AppParamsDAO appParamsDAO = new AppParamsDAO();
        appParamsDAO.setSession(getSession());
        AppParams appParams = appParamsDAO.findAppParams("STOCK_MODEL_UNIT", stockModel.getUnit());

        if (appParams != null) {
            trans.setUnit(appParams.getName());
        }

        trans.setCheckSerial(stockModel.getCheckSerial());
        Long checkDial = 0L;
        if (gF.getCheckDial() != null && gF.getCheckDial().equals("true")) {
            checkDial = 1L;
        }

        trans.setCheckDial(checkDial);
        trans.setFromOwnerType(gF.getOwnerType());
        trans.setFromOwnerId(gF.getOwnerId());
        //Check so luong yeu cau so voi so luong hang hoa thuc con trong kho

        if (!checkStockGoods(goodsForm.getOwnerId(), goodsForm.getOwnerType(), goodsForm.getStockModelId(), goodsForm.getStateId(), goodsForm.getQuantity(), checkDial, getSession())) {
//            req.setAttribute("returnMsg", "Số lượng hàng hoá trong kho nh�? hơn số lượng yêu cầu xuất.");
            req.setAttribute("returnMsg", "ERR.STK.077");
            req.setAttribute("lstStockModel", lstStockModel);
            return pageForward;

        }
//        //Check truong hop xuat hang cho dai ly -->tat ca cac mat hang xuat di phai cung VAT
//        if (goodsForm.getExpType() != null && goodsForm.getExpType().equals("dial")) {
//            if (lstGoods != null && lstGoods.size() > 0) {
//                StockTransFull stockTransFull = (StockTransFull) lstGoods.get(0);
//                Long firstStockModelId = stockTransFull.getStockModelId();
//                String priceType = ResourceBundleUtils.getResource("PRICE_TYPE_AGENT");
//                Long firstVAT = getVATByStockModelIdAndType(firstStockModelId, priceType);
//                if (!checkVAT(firstStockModelId, firstVAT, priceType)) {
//                    req.setAttribute("returnMsg", "Các mặt hàng xuất bán cho đại lý phải khai báo giá và có cùng VAT");
//                    req.setAttribute("lstStockModel", lstStockModel);
//                    return pageForward;
//                }
//
//            }
//        }

//Lay so luong va trang thai hang
        trans.setQuantity(gF.getQuantity());

        trans.setStateId(gF.getStateId());
        trans.setNote(gF.getNote());

        /*
         * LamNV ADD START
         */

        if (revokeColl != null && revokeColl.equals("true")) {
            Double amount = 0.0;
            String amountAttr = "amount" + pageId;
            Double price = gF.getQuantity() * getPriceDeposit(gF.getStockModelId());
            trans.setPrice(price);

            trans.setPriceDisplay(NumberUtils.rounđAndFormatAmount(price));

            if (req.getSession().getAttribute(amountAttr) != null && req.getSession().getAttribute(amountAttr) != "") {
                amount = Double.parseDouble(req.getSession().getAttribute(amountAttr).toString());
            }

            amount += price;
            req.getSession().setAttribute(amountAttr, amount);
            setTabSession("amountDisplay", NumberUtils.rounđAndFormatAmount(amount));

            //Tinh lai tien
            //Long amountTax = getAmountTax(lstGoods);

        }
        /*
         * LamNV ADD STOP
         */

        lstGoods.add(trans);
        /*
         * Kiem tra tong gia goc HH xuat cho NV + gia tri HH hien tai cua NV <=
         * han muc cua NV
         */
        /*
         * Double sourceAmount = sumAmountByGoodsList(lstGoods); if
         * (sourceAmount != null && sourceAmount >= 0) { if
         * (!checkCurrentDebitWhenImplementTrans(gF.getOwnerId(),
         * gF.getOwnerType(), sourceAmount)) { req.setAttribute("returnMsg",
         * "ERR.LIMIT.0012"); req.setAttribute("lstStockModel", lstStockModel);
         * return pageForward; } } else { req.setAttribute("returnMsg",
         * "ERR.LIMIT.0014"); req.setAttribute("lstStockModel", lstStockModel);
         * return pageForward; }
         */
        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
        goodsForm.resetForm();

        log.info("# End method addGoodsAgentDeposit");
        return pageForward;
    }


    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: prepare edit goods in
     * goods list
     */
    public String prepareEditGoodsAgentDeposit() throws Exception {
        log.info("# Begin method prepareEditGoodsAgentDeposit");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");


        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        } /*
         * LamNV ADD STOP
         */
        String pageForward = "editGoodsAgentDeposit";

        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        }
        String stockModelId = (String) req.getParameter("stockModelId");
        String stockTypeId = (String) req.getParameter("stockTypeId");
        String stateId = (String) QueryCryptUtils.getParameter(req, "stateId");
        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);


        if (stockModelId != null && !"".equals(stockModelId.trim()) && stockTypeId != null && !"".equals(stockTypeId.trim()) && stateId != null && !"".equals(stateId.trim())) {
            Long lStockModelId = Long.parseLong(stockModelId.trim());
            Long lStockTypeId = Long.parseLong(stockTypeId.trim());
            Long lStateId = Long.parseLong(stateId.trim());
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());
            List lstStockModel = stockModelDAO.findByProperty(StockModelDAO.STOCK_TYPE_ID, lStockTypeId);
            req.setAttribute("lstStockModel", lstStockModel);
            //req.setAttribute("listItemsCombo", lstStockModel);
            StockTransFull trans;
            for (int i = 0; i
                    < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);
                if (trans.getStockModelId().equals(lStockModelId) && trans.getStockTypeId().equals(lStockTypeId) && trans.getStateId().equals(lStateId)) {
                    goodsForm.setStockTypeId(lStockTypeId);
                    goodsForm.setStockModelId(lStockModelId);
                    goodsForm.setStockModelCode(trans.getStockModelCode());
                    goodsForm.setStockModelName(trans.getStockModelName());
                    if (trans.getCheckDial().equals(1L)) {
                        goodsForm.setCheckDial("true");
                    } else {
                        goodsForm.setCheckDial("false");
                    }

                    goodsForm.setStateId(trans.getStateId());
                    goodsForm.setQuantity(trans.getQuantity().longValue());
                    goodsForm.setNote(trans.getNote());
                }

                if (goodsForm.getStockModelId() != null) {

                    StockModel model = stockModelDAO.findById(goodsForm.getStockModelId());
                    if (model != null) {
                        Long checkDial = model.getCheckDial();
                        if (checkDial != null && checkDial.equals(Constant.MUST_DRAW)) {
                            goodsForm.setCanDial(Constant.EXP_CAN_DIAL);
                        } else {
                            goodsForm.setCanDial(Constant.EXP_NON_DIAL);
                        }

                    }

                }
                //Danh sach loai tai nguyen
                StockTypeDAO stockTypeDAO = new StockTypeDAO();
                stockTypeDAO.setSession(this.getSession());
                List lstStockType = stockTypeDAO.findAllAvailable();
                req.setAttribute("lstStockType", lstStockType);
            }

        } else {
//            req.setAttribute("returnMsg", "Chưa ch�?n hàng hoá để sửa.");
            req.setAttribute("returnMsg", "ERR.STK.089");
        }

        req.getSession().setAttribute("isEdit" + pageId, true);

        log.info("# End method prepareEditGoods");
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: edit goods in goods
     * list
     */
    public String editGoodsAgentDeposit() throws Exception {
        log.info("# Begin method editGoodsAgentDeposit");
        String pageForward = "editGoodsAgentDeposit";
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        }

        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);

        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");
        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        }
        /*
         * LamNV ADD STOP
         */
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);

        //Danh sach stockModel
        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(this.getSession());
        List lstStockModel = stockModelDAO.findByStockTypeIdAndStatus(goodsForm.getStockTypeId(), Constant.STATUS_USE);

        if (lstGoods != null) {
            Long lStockModelId = goodsForm.getStockModelId();
            Long lStockTypeId = goodsForm.getStockTypeId();
            Long lStateId = goodsForm.getStateId();

            StockTransFull trans = null;
            boolean isOk = false;

            for (int i = 0; i
                    < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);

                if (trans.getStockModelId().equals(lStockModelId) && trans.getStockTypeId().equals(lStockTypeId) && trans.getStateId().equals(lStateId)) {
                    // initFormExport(req);
                    Long checkDial = 0L;
                    if (goodsForm.getCheckDial() != null && goodsForm.getCheckDial().equals("true")) {
                        checkDial = 1L;
                    }

                    if (goodsForm.getQuantity().equals(0L)) {
//                        req.setAttribute("returnMsg", "Số lượng phải là số nguyên dương");
                        req.setAttribute("returnMsg", "ERR.STK.011");
                        req.setAttribute("lstStockModel", lstStockModel);
                        return pageForward;
                    }
                    //Check so luong yeu cau so voi so luong hang hoa thuc con trong kho

                    if (!checkStockGoods(goodsForm.getOwnerId(), goodsForm.getOwnerType(), goodsForm.getStockModelId(), goodsForm.getStateId(), goodsForm.getQuantity(), checkDial, getSession())) {
//                        req.setAttribute("returnMsg", "Số lượng hàng hoá trong kho nh�? hơn số lượng yêu cầu xuất.");
                        req.setAttribute("returnMsg", "ERR.STK.077");
                        req.setAttribute("lstStockModel", lstStockModel);
                        return pageForward;
                    }
                    trans.setQuantity(goodsForm.getQuantity());
                    trans.setNote(goodsForm.getNote());
                    trans.setCheckDial(checkDial);
                    /*
                     * LamNV ADD START
                     */

                    if (revokeColl != null && revokeColl.equals("true")) {
                        Double price = 1.0 * trans.getQuantity() * getPriceDeposit(trans.getStockModelId());
                        trans.setPrice(price);
                    } /*
                     * LamNV ADD STOP
                     */
                    isOk = true;
                }

            }
            if (!isOk) {
//                req.setAttribute("returnMsg", "Sửa không thành công. Hàng hoá không tồn tại trong danh sách.");
                req.setAttribute("returnMsg", "ERR.STK.090");
                req.getSession().removeAttribute("isEdit" + pageId);
                goodsForm.resetForm();
                return pageForward;
            }

            req.getSession().setAttribute("lstGoods" + pageId, lstGoods);
            req.getSession().removeAttribute("isEdit" + pageId);
            req.setAttribute("returnMsg", "ERR.STK.091");
        } else {
//            req.setAttribute("returnMsg", "Danh sách hàng hoá rỗng. Mặt hàng đã bị xoá kh�?i danh sách");
            req.setAttribute("returnMsg", "ERR.STK.092");
        }

        /*
         * LamNV add start
         */
        if (revokeColl != null && revokeColl.equals("true")) {
            Double amountTax = getAmountTax(lstGoods);
            req.getSession().setAttribute("amount" + pageId, amountTax);
            req.getSession().setAttribute("amountDisplay" + pageId, NumberUtils.rounđAndFormatAmount(amountTax));
        } /*
         * LamNV add stop
         */

        log.info("# End method editGoodsAgentDeposit");

        goodsForm.resetForm();
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: cacel sua hang hoa
     */
    public String cancelEditGoodsAgentDeposit() throws Exception {
        log.info("# Begin method cancelEditGoodsAgentDeposit");
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        String pageForward = "editGoodsAgentDeposit";

        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        }
        goodsForm.resetForm();
        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");
        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        }
        /*
         * LamNV ADD STOP
         */
        //Danh sach loai tai nguyen
        StockTypeDAO stockTypeDAO = new StockTypeDAO();
        stockTypeDAO.setSession(this.getSession());
        List lstStockType = stockTypeDAO.findAllAvailable();
        req.setAttribute("lstStockType", lstStockType);
        req.getSession().removeAttribute("isEdit" + pageId);

        log.info("# End method cancelEditGoodsAgentDeposit");
        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: xoa hang hoa khoi danh
     * sach hang hoa
     */
    public String delGoodsAgentSale() throws Exception {
        log.info("# Begin method delGoods");
        String pageForward = "listGoodsAgentSale";

        HttpServletRequest req = getRequest();
        /**
         * THANHNC_20110215_START log.
         */
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String function = "com.viettel.im.database.DAO.StockCommonDAO.delGoodsAgentSale";
        Long callId = getApCallId();
        Date startDate = new Date();
        logStartUser(startDate, function, callId, userToken.getLoginName());
        /**
         * End log
         */
        String pageId = req.getParameter("pageId");
        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        }

        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");
        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        }
        /*
         *
         */

        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
        if (lstGoods == null) {
            req.setAttribute("returnMsg", "Danh sách hàng hoá rỗng!");
            logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "LIST_GOODS_NULL");
            return pageForward;
        }



        String stockModelId = (String) QueryCryptUtils.getParameter(req, "stockModelId");
        //String stockTypeId = (String) QueryCryptUtils.getParameter(req, "stockTypeId");
        String stateId = (String) QueryCryptUtils.getParameter(req, "stateId");

        if (stockModelId != null && !"".equals(stockModelId.trim()) && stateId != null && !"".equals(stateId.trim())) {
            Long lStockModelId = Long.parseLong(stockModelId.trim());
            Long lStateId = Long.parseLong(stateId.trim());
            StockTransFull trans;

            for (int i = 0; i < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);
                if (trans.getStockModelId().equals(lStockModelId) && trans.getStateId().equals(lStateId)) {
                    lstGoods.remove(i);
                }

            }

        } else {
//            req.setAttribute("returnMsg", "Chưa chọn hàng hoá để xoá");
            req.setAttribute("returnMsg", "ERR.STK.086");
            logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "ERR.STK.086");
            return pageForward;
        }

        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);

        /*
         * LamNV add start
         */
        if (revokeColl != null && revokeColl.equals("true")) {
            Double amountTax = getAmountTax(lstGoods);
            req.getSession().setAttribute("amount" + pageId, amountTax);
        }
        /*
         * LamNV add stop
         */

//        req.setAttribute("returnMsg", "Xoá thành công!");
        req.setAttribute("returnMsg", "ERR.STK.087");
        logEndUser(startDate, new Date(), function, callId, userToken.getLoginName(), "OK");
        log.info("# End method delGoods");


        return pageForward;
    }

    /*
     * Author: ThanhNC Date created: 26/02/2009 Purpose: xoa hang hoa khoi danh
     * sach hang hoa
     */
    public String delGoodsAgentDeposit() throws Exception {
        log.info("# Begin method delGoods");
        String pageForward = "listGoodsAgentDeposit";
        HttpServletRequest req = getRequest();
        String pageId = req.getParameter("pageId");
        if (goodsForm.getInputSerial() != null && goodsForm.getInputSerial().equals("true")) {
            req.setAttribute("inputSerial", "true");
        }

        /*
         * LamNV ADD START
         */
        String revokeColl = (String) req.getParameter("revokeColl");
        if (revokeColl != null && revokeColl.equals("true")) {
            req.setAttribute("revokeColl", "true");
        }
        /*
         *
         */

        List lstGoods = (List) req.getSession().getAttribute("lstGoods" + pageId);
        if (lstGoods == null) {
            req.setAttribute("returnMsg", "Danh sách hàng hoá rỗng!");
            return pageForward;
        }

        String stockModelId = (String) QueryCryptUtils.getParameter(req, "stockModelId");
        //String stockTypeId = (String) QueryCryptUtils.getParameter(req, "stockTypeId");
        String stateId = (String) QueryCryptUtils.getParameter(req, "stateId");

        if (stockModelId != null && !"".equals(stockModelId.trim()) && stateId != null && !"".equals(stateId.trim())) {
            Long lStockModelId = Long.parseLong(stockModelId.trim());
            Long lStateId = Long.parseLong(stateId.trim());
            StockTransFull trans;

            for (int i = 0; i < lstGoods.size(); i++) {
                trans = (StockTransFull) lstGoods.get(i);
                if (trans.getStockModelId().equals(lStockModelId) && trans.getStateId().equals(lStateId)) {
                    lstGoods.remove(i);
                }

            }

        } else {
//            req.setAttribute("returnMsg", "Chưa chọn hàng hoá để xoá");
            req.setAttribute("returnMsg", "ERR.STK.086");
            return pageForward;
        }

        req.getSession().setAttribute("lstGoods" + pageId, lstGoods);

        /*
         * LamNV add start
         */
        if (revokeColl != null && revokeColl.equals("true")) {
            Double amountTax = getAmountTax(lstGoods);
            req.getSession().setAttribute("amount" + pageId, amountTax);
        }
        /*
         * LamNV add stop
         */

//        req.setAttribute("returnMsg", "Xoá thành công!");
        req.setAttribute("returnMsg", "ERR.STK.087");

        log.info("# End method delGoods");


        return pageForward;
    }
    //LeVT1 start R499

    public static String getTemplate(Session session, Long shopId) {
        String template = "";
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(session);
        Shop shop = shopDAO.findById(shopId);
        if (shop != null) {
            String shopPath = shop.getShopPath().substring(1);
            String arr[] = shopPath.split("_");
            int length = arr.length;
            if (length == 1) {
                return "VT";
            }
            if (length == 2) {
                return "CN";
            }
            if (length == 3) {
                return "TTH";
            }
            if (length == 4) {
                return "CH";
            }
            if (length == 5) {
                return "NV";
            }
        }
        return template;
    }
    //LeVT1 end R499
    // ThinDM R6762

    public String genTransactionCode(Long shopId, String shopCode, Long staffId, String commandExportType) throws Exception {
        //Khai bao cac bien
        String prefixActionCode = "";
        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(getSession());
        //lay shop level
        Long shopLevel = CommonDAO.getShopLevel(getSession(), shopId);
        //Tao prefixActionCode
        // lay 2 ky tu dau & 1 ky tu cuoi shop_code
        // tutm1 06/06/2014 sua thanh lay 3 ky tu dau & 1 ky tu cuoi shop_code
        String subShopCode = shopCode;
        if (shopCode.length() > 4) {
            subShopCode = shopCode.substring(0, 3) + shopCode.substring(shopCode.length() - 1, shopCode.length());
        } else if (shopCode.length() > 3) {// neu = 4 ky tu lay 2 ky tu dau & 1 ky tu cuoi shop_code
            subShopCode = shopCode.substring(0, 2) + shopCode.substring(shopCode.length() - 1, shopCode.length());
        }
        // cap cong ty
        if (shopLevel.equals(Constant.SHOP_LEVEL_ROOT)) {
            prefixActionCode = "C" + commandExportType;
        }
        // cap chi nhanh
        if (shopLevel.equals(Constant.SHOP_LEVEL_BRANCH)) {
            prefixActionCode = "B" + subShopCode + commandExportType;
        }
        // TTKD
        if (shopLevel.equals(Constant.SHOP_LEVEL_CENTER)) {
            //Thuc hien lay ma tinh cua cua hang
            Shop shop = shopDAO.findById(shopId);
            // tim chi nhanh :
            Shop branchShop = shopDAO.findById(shop.getParentShopId());
            // lay 2 ky tu dau & 1 ky tu cuoi shop_code
            String shopBranchCode = branchShop.getShopCode();
            String subShopBranchCode = shopBranchCode;
            if (shopBranchCode.length() > 4) {
                subShopBranchCode = shopBranchCode.substring(0, 3) + shopBranchCode.substring(subShopBranchCode.length() - 1,
                        shopBranchCode.length());
            } else if (shopBranchCode.length() > 3) {
                subShopBranchCode = shopBranchCode.substring(0, 2) + shopBranchCode.substring(subShopBranchCode.length() - 1,
                        shopBranchCode.length());
            }
            if (shop != null) {
                prefixActionCode = "B" + subShopBranchCode + subShopCode + commandExportType;
            }
        }
        // cua hang
        if (shopLevel.equals(Constant.SHOP_LEVEL_SHOWROOM)) {
            //Thuc hien lay ma tinh cua cua hang
            Shop shop = shopDAO.findById(shopId);
            // tim TTKD
            Shop centerShop = shopDAO.findById(shop.getParentShopId());
            // tim chi nhanh
            Shop branchShop = shopDAO.findById(centerShop.getParentShopId());
            // lay 2 ky tu dau & 1 ky tu cuoi shop_code
            String shopCenterCode = centerShop.getShopCode();
            String subShopCenterCode = shopCenterCode;
            if (shopCenterCode.length() > 4) {
                subShopCenterCode = shopCenterCode.substring(0, 3) + shopCenterCode.substring(shopCenterCode.length() - 1,
                        shopCenterCode.length());
            } else if (shopCenterCode.length() > 3) {
                subShopCenterCode = shopCenterCode.substring(0, 2) + shopCenterCode.substring(shopCenterCode.length() - 1,
                        shopCenterCode.length());
            }
            // lay 2 ky tu dau & 1 ky tu cuoi shop_code
            String branchShopCode = branchShop.getShopCode();
            String subShopBranchCode = branchShopCode;
            if (branchShopCode.length() > 4) {
                subShopBranchCode = branchShopCode.substring(0, 3) + branchShopCode.substring(branchShopCode.length() - 1,
                        branchShopCode.length());
            } else if (branchShopCode.length() > 3) {
                subShopBranchCode = branchShopCode.substring(0, 2) + branchShopCode.substring(branchShopCode.length() - 1,
                        branchShopCode.length());
            }
            if (shop != null) {
                prefixActionCode = "B" + subShopBranchCode + subShopCenterCode + subShopCode + commandExportType;
            }
        }
        //Lay so giao dich trong ngay
        Date curDate = new Date();
        String strDateCurrent = DateTimeUtils.convertDateToString(curDate);
        Long numberTransaction = null;
        // lenh xuat
        if (commandExportType.equals(Constant.TRANS_CODE_LX)) {
            numberTransaction = getNumberTransactionExportCommand(shopId, strDateCurrent);
        } else if (commandExportType.equals(Constant.TRANS_CODE_LN)) {
            numberTransaction = getNumberTransactionCommandImport(shopId, strDateCurrent);
        } else if (commandExportType.equals(Constant.TRANS_CODE_PX)) {
            numberTransaction = getNumberTransactionExportNote(shopId, staffId, strDateCurrent);
        } else if (commandExportType.equals(Constant.TRANS_CODE_PN)) {
            numberTransaction = getNumberTransactionImportNote(shopId, staffId, strDateCurrent);
        } else if (commandExportType.equals(Constant.TRANS_CODE_LXS)) {
            numberTransaction = getNumberTransactionExportCommandIsdn(shopId, strDateCurrent);
        } else if (commandExportType.equals(Constant.TRANS_CODE_LNS)) {
            numberTransaction = getNumberTransactionCommandImportIsdn(shopId, strDateCurrent);
        } else if (commandExportType.equals(Constant.TRANS_CODE_PXS)) {
            numberTransaction = getNumberTransactionExportNoteIsdn(shopId, staffId, strDateCurrent);
        } else if (commandExportType.equals(Constant.TRANS_CODE_PNS)) {
            numberTransaction = getNumberTransactionImportNoteIsdn(shopId, staffId, strDateCurrent);
        }
//        prefixActionCode += DateTimeUtils.date2yyyyMMddStringNoSlash(curDate);
        prefixActionCode += DateTimeUtils.getYearOfDate(strDateCurrent, "yyyy-MM-dd");
        numberTransaction += 1;
        if (numberTransaction != null) {
            String numberWithZero = StringUtils.padLeftZero(String.valueOf(numberTransaction), Constant.LENGTH_OF_TRANS_CODE_NUMBER);
            prefixActionCode += "_" + numberWithZero;
        }
        return prefixActionCode;
    }
    //tao ham lay so luong cua lenh nhap trong ngay

    public Long getNumberTransactionCommandImport(Long shopId, String date) {
        String strQuery = "select count(*) countNumber from Stock_Trans a where exists (select 1 from Stock_Trans_Action b where "
                + " a.stock_Trans_Id = b.stock_Trans_Id and  (a.to_Owner_Id = ? or (a.from_owner_id = ? and a.to_owner_type <> ?)) "
                + " and b.create_Datetime >= trunc(to_date(?,'yyyy-mm-dd')) "
                + " and b.create_Datetime < trunc(to_date(?,'yyyy-mm-dd')) +1 and (a.stock_Trans_Type = ? "
                + " or a.stock_trans_type = ? ) and b.action_Type = ?)";
//                + " and a.from_Owner_id in (select shop_id from shop a,channel_type b where a.channel_type_id = b.channel_type_id"
//                + " and b.is_vt_unit = ? and b.object_type = ? and a.to_owner_id <> a.from_owner_id) ";
        SQLQuery query = getSession().createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        int i = 0;
        query.setParameter(i++, shopId);
        query.setParameter(i++, shopId); // truong hop nhan vien nhap hang tu cua hang, phai lay theo kho xuat
        query.setParameter(i++, Constant.OWNER_TYPE_SHOP);
        query.setParameter(i++, DateTimeUtils.getFirstDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(i++, DateTimeUtils.getLastDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(i++, Constant.STOCK_TRANS_TYPE_IMPORT); // giao dich nhap
        query.setParameter(i++, Constant.TRANS_RECOVER); // hoac giao dich thu hoi
        query.setParameter(i++, Constant.STOCK_TRANS_TYPE_COMMAND);
//        query.setParameter(5, Constant.IS_VT_UNIT);
//        query.setParameter(6, Constant.OBJECT_TYPE_SHOP);
        Long count = (Long) query.list().get(0);
        return count;
    }
    //tao ham lay so luong lenh xuat trong ngay

    public Long getNumberTransactionExportCommand(Long shopId, String date) {
        String strQuery = "select count(*) countNumber from Stock_Trans a where exists (select 1 from Stock_Trans_Action b where a.stock_Trans_Id = b.stock_Trans_Id and b.create_Datetime >= trunc(to_date(?,'yyyy-mm-dd')) "
                + " and b.create_Datetime < trunc(to_date(?,'yyyy-mm-dd')) +1 and a.stock_Trans_Type = ? and b.action_Type = ? ";
//                + " and a.to_Owner_id in (select shop_id from shop a,channel_type b where a.channel_type_id = b.channel_type_id"
//                + " and b.is_vt_unit = ? and b.object_type = ? and a.to_owner_id <> a.from_owner_id) ";
        strQuery += " and  (a.from_Owner_Id = ? or (a.to_owner_id = ? and a.from_owner_type <> ? ))) ";
        SQLQuery query = getSession().createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        query.setParameter(0, DateTimeUtils.getFirstDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(1, DateTimeUtils.getLastDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(2, Constant.STOCK_TRANS_TYPE_EXPORT);
        query.setParameter(3, Constant.STOCK_TRANS_TYPE_COMMAND);
        query.setParameter(4, shopId); // mac dinh lay theo kho xuat
        query.setParameter(5, shopId); // truong hop nhan vien xuat tra hang, phai lay theo kho nhan
        query.setParameter(6, Constant.OWNER_TYPE_SHOP);
//        query.setParameter(5, Constant.IS_VT_UNIT);
//        query.setParameter(6, Constant.OBJECT_TYPE_SHOP);
        Long count = (Long) query.list().get(0);
        return count;
    }
    //tao ham lay so luong phieu xuat trong ngay

    public Long getNumberTransactionExportNote(Long shopId, Long staffId, String date) {
        String strQuery = "select count(*) countNumber from Stock_Trans a where exists (select 1 from Stock_Trans_Action b "
                + " where a.stock_Trans_Id = b.stock_Trans_Id and b.create_Datetime >= trunc(to_date(?,'yyyy-mm-dd')) "
                + " and b.create_Datetime < trunc(to_date(?,'yyyy-mm-dd')) +1 and a.stock_Trans_Type = ? and b.action_Type = ? ";
//                + " and a.to_Owner_id in (select shop_id from shop a,channel_type b where a.channel_type_id = b.channel_type_id"
//                + " and b.is_vt_unit = ? and b.object_type = ? and a.to_owner_id <> a.from_owner_id) ";
        strQuery += " and  (a.from_Owner_Id = ? or (a.to_owner_id = ? and a.from_owner_type <> ? ) "
                // truong hop xuat dat coc CTV
                + " or (a.from_owner_id = ? and a.from_owner_type = ? and a.to_owner_type = ?) )) ";
        SQLQuery query = getSession().createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        query.setParameter(0, DateTimeUtils.getFirstDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(1, DateTimeUtils.getLastDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(2, Constant.STOCK_TRANS_TYPE_EXPORT);
        query.setParameter(3, Constant.STOCK_TRANS_TYPE_NOTE);
        query.setParameter(4, shopId);
        query.setParameter(5, shopId); // truong hop nhan vien xuat tra hang, phai lay theo kho nhan
        query.setParameter(6, Constant.OWNER_TYPE_SHOP);
        query.setParameter(7, staffId);
        query.setParameter(8, Constant.OWNER_TYPE_STAFF);
        query.setParameter(9, Constant.OWNER_TYPE_STAFF);
//        query.setParameter(5, Constant.IS_VT_UNIT);
//        query.setParameter(6, Constant.OBJECT_TYPE_SHOP);
        Long count = (Long) query.list().get(0);
        return count;
    }
    //end
    //thanhnv59 17.7.2013--them ham sinh ma phieu xuat cho dai ly vi do tu truoc da dung chung nen h tach ra
    //tao ham lay so luong cua lenh/phieu so

    public Long getNumberTransactionCommandImportIsdn(Long shopId, String date) {
        String strQuery = "select count(*) countNumber from Stock_Trans a where exists (select 1 from Stock_Trans_Action b where "
                + " a.stock_Trans_Id = b.stock_Trans_Id and  (a.to_Owner_Id = ? or (a.from_owner_id = ? and a.to_owner_type <> ?)) "
                + " and b.create_Datetime >= trunc(to_date(?,'yyyy-mm-dd')) "
                + " and b.create_Datetime < trunc(to_date(?,'yyyy-mm-dd')) +1 and a.stock_Trans_Type = ? "
                + " and b.action_Type = ? )";
        SQLQuery query = getSession().createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        int i = 0;
        query.setParameter(i++, shopId);
        query.setParameter(i++, shopId); // truong hop nhan vien nhap hang tu cua hang, phai lay theo kho xuat
        query.setParameter(i++, Constant.OWNER_TYPE_SHOP);
        query.setParameter(i++, DateTimeUtils.getFirstDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(i++, DateTimeUtils.getLastDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(i++, Constant.TRANS_RECOVER_ISDN); // giao dich nhap so
        query.setParameter(i++, Constant.STOCK_TRANS_TYPE_COMMAND);
        Long count = (Long) query.list().get(0);
        return count;
    }
    //tao ham lay phieu nhap trong ngay

    public Long getNumberTransactionImportNoteIsdn(Long shopId, Long staffId, String date) {
        String strQuery = "select count(*) countNumber from Stock_Trans a where exists (select 1 from Stock_Trans_Action b "
                + " where a.stock_Trans_Id = b.stock_Trans_Id  "
                + " and b.create_Datetime >= trunc(to_date(?,'yyyy-mm-dd')) "
                + " and b.create_Datetime < trunc(to_date(?,'yyyy-mm-dd')) +1 "
                + " and a.stock_Trans_Type = ?  and b.action_Type = ? "
                + " and (a.to_Owner_Id = ? or (a.from_owner_id = ? and a.to_owner_type <> ?)"
                + " or (a.to_owner_id = ? and a.from_owner_type = ? and a.to_owner_type = ?) )) ";
        SQLQuery query = getSession().createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        int i = 0;
        query.setParameter(i++, DateTimeUtils.getFirstDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(i++, DateTimeUtils.getLastDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(i++, Constant.TRANS_RECOVER_ISDN); // giao dich nhap
        query.setParameter(i++, Constant.STOCK_TRANS_TYPE_NOTE);
        query.setParameter(i++, shopId);
        query.setParameter(i++, shopId); // truong hop nhan vien nhap hang tu cua hang, phai lay theo kho xuat
        query.setParameter(i++, Constant.OWNER_TYPE_SHOP);
        query.setParameter(i++, staffId);
        query.setParameter(i++, Constant.OWNER_TYPE_STAFF);
        query.setParameter(i++, Constant.OWNER_TYPE_STAFF);
        Long count = (Long) query.list().get(0);
        return count;
    }
    //tao ham lay so luong lenh xuat trong ngay

    public Long getNumberTransactionExportCommandIsdn(Long shopId, String date) {
        String strQuery = "select count(*) countNumber from Stock_Trans a where exists (select 1 from Stock_Trans_Action b where a.stock_Trans_Id = b.stock_Trans_Id and b.create_Datetime >= trunc(to_date(?,'yyyy-mm-dd')) "
                + " and b.create_Datetime < trunc(to_date(?,'yyyy-mm-dd')) +1 and a.stock_Trans_Type = ? and b.action_Type = ? ";
        strQuery += " and  (a.from_Owner_Id = ? or (a.to_owner_id = ? and a.from_owner_type <> ? ))) ";
        SQLQuery query = getSession().createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        query.setParameter(0, DateTimeUtils.getFirstDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(1, DateTimeUtils.getLastDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(2, Constant.TRANS_EXPORT_ISDN);
        query.setParameter(3, Constant.STOCK_TRANS_TYPE_COMMAND);
        query.setParameter(4, shopId); // mac dinh lay theo kho xuat
        query.setParameter(5, shopId); // truong hop nhan vien xuat tra hang, phai lay theo kho nhan
        query.setParameter(6, Constant.OWNER_TYPE_SHOP);
        Long count = (Long) query.list().get(0);
        return count;
    }
    //tao ham lay so luong phieu xuat trong ngay

    public Long getNumberTransactionExportNoteIsdn(Long shopId, Long staffId, String date) {
        String strQuery = "select count(*) countNumber from Stock_Trans a where exists (select 1 from Stock_Trans_Action b where a.stock_Trans_Id = b.stock_Trans_Id and b.create_Datetime >= trunc(to_date(?,'yyyy-mm-dd')) "
                + " and b.create_Datetime < trunc(to_date(?,'yyyy-mm-dd')) +1 and a.stock_Trans_Type = ? and b.action_Type = ? ";
        strQuery += " and  (a.from_Owner_Id = ? or (a.to_owner_id = ? and a.from_owner_type <> ? ) "
                + " or (a.from_owner_id = ? and a.from_owner_type = ? and a.to_owner_type = ?) )";
        SQLQuery query = getSession().createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        query.setParameter(0, DateTimeUtils.getFirstDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(1, DateTimeUtils.getLastDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(2, Constant.TRANS_EXPORT_ISDN);
        query.setParameter(3, Constant.STOCK_TRANS_TYPE_NOTE);
        query.setParameter(4, shopId);
        query.setParameter(5, shopId); // truong hop nhan vien xuat tra hang, phai lay theo kho nhan
        query.setParameter(6, Constant.OWNER_TYPE_SHOP);
        query.setParameter(7, staffId);
        query.setParameter(8, Constant.OWNER_TYPE_STAFF);
        query.setParameter(9, Constant.OWNER_TYPE_STAFF);
        Long count = (Long) query.list().get(0);
        return count;
    }
    //end
    //thanhnv59 17.7.2013--them ham sinh ma phieu xuat cho dai ly vi do tu truoc da dung chung nen h tach ra
    //tao ham lay phieu nhap trong ngay

    public Long getNumberTransactionImportNote(Long shopId, Long staffId, String date) {
        String strQuery = "select count(*) countNumber from Stock_Trans a where exists (select 1 from Stock_Trans_Action b "
                + " where a.stock_Trans_Id = b.stock_Trans_Id  "
                + " and b.create_Datetime >= trunc(to_date(?,'yyyy-mm-dd')) "
                + " and b.create_Datetime < trunc(to_date(?,'yyyy-mm-dd')) +1 "
                + " and (a.stock_Trans_Type = ? or a.stock_trans_type = ?) and b.action_Type = ? "
                + " and (a.to_Owner_Id = ? or (a.from_owner_id = ? and a.to_owner_type <> ?)"
                // thu hoi hang hoa tu CTV.
                + " or (a.to_owner_id = ? and a.from_owner_type = ? and a.to_owner_type = ?) )) ";
        SQLQuery query = getSession().createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        int i = 0;
        query.setParameter(i++, DateTimeUtils.getFirstDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(i++, DateTimeUtils.getLastDayOfYear(date, "yyyy-MM-dd"));
        query.setParameter(i++, Constant.STOCK_TRANS_TYPE_IMPORT); // giao dich nhap
        query.setParameter(i++, Constant.TRANS_RECOVER); // hoac giao dich thu hoi
        query.setParameter(i++, Constant.STOCK_TRANS_TYPE_NOTE);
        query.setParameter(i++, shopId);
        query.setParameter(i++, shopId); // truong hop nhan vien nhap hang tu cua hang, phai lay theo kho xuat
        query.setParameter(i++, Constant.OWNER_TYPE_SHOP);
        query.setParameter(i++, staffId);
        query.setParameter(i++, Constant.OWNER_TYPE_STAFF);
        query.setParameter(i++, Constant.OWNER_TYPE_STAFF);
//        query.setParameter(5, Constant.IS_VT_UNIT);
//        query.setParameter(6, Constant.OBJECT_TYPE_SHOP);
        Long count = (Long) query.list().get(0);
        return count;
    }
    //End ThinDM

    /**
     * @Author: Tannh
     * @Date created: 11/01/2018
     * @Purpose: save sale trans serial order
     */
    private boolean saveSaleTransSerialOrder(List lstSerial, Long stockModelId, Long SaleTransDetailId) throws Exception {

        boolean noError = true;


        StockModelDAO stockModelDAO = new StockModelDAO();
        stockModelDAO.setSession(getSession());
        StockModel stockModel = stockModelDAO.findById(stockModelId);
        if (stockModel == null) {
            return false;
        }
        BaseStockDAO baseStockDAO = new BaseStockDAO();
        baseStockDAO.setSession(this.getSession());
        if (lstSerial != null && lstSerial.size() > 0) {
            StockTransSerial stockSerial = null;
            for (int idx = 0; idx < lstSerial.size(); idx++) {
                stockSerial = (StockTransSerial) lstSerial.get(idx);
                BigInteger startSerialTemp = new BigInteger(stockSerial.getFromSerial());
                BigInteger endSerialTemp = new BigInteger(stockSerial.getToSerial());
                Long totals = endSerialTemp.subtract(startSerialTemp).longValue() + 1;
                Long saleTransSerialId = getSequence("SALE_TRANS_SERIAL_ORDER_SEQ");
                SaleTransSerialOrder saleTransSerial = new SaleTransSerialOrder();
                saleTransSerial.setSaleTransSerialId(saleTransSerialId);
                saleTransSerial.setSaleTransDetailId(SaleTransDetailId);
                saleTransSerial.setStockModelId(stockModelId);
                saleTransSerial.setSaleTransDate(new Date());
                saleTransSerial.setFromSerial(stockSerial.getFromSerial());
                saleTransSerial.setToSerial(stockSerial.getToSerial());
                saleTransSerial.setQuantity(totals);
                this.getSession().save(saleTransSerial);

            }
        }
        return noError;
    }

    public boolean checkAlreadlyHaveSerial(Long SaleTransDetailId) throws Exception {
        log.debug("# Begin method checkAlreadlyHaveSerial");
        List<String> list = new ArrayList();
        boolean alreadlySerial = false;
        try {
            String queryString = " from SaleTransSerialOrder where saleTransDetailId = ? ";

            Query queryObject = getSession().createQuery(queryString);

            queryObject.setParameter(0, SaleTransDetailId);
            list = queryObject.list();
            if (list == null || list.isEmpty() || list.size() <= 0) {
                alreadlySerial = false;
            } else {
                alreadlySerial = true;
            }
        } catch (Exception ex) {
            log.error(" Begin method checkAlreadlyHaveSerial ; Exception : " + ex);
        }
        return alreadlySerial;
    }

    public boolean checkAlreadlyInputOtherTrans(String serial) throws Exception {
        log.debug("# Begin method checkAlreadlyInputOtherTrans");
        boolean alreadlySerial = false;
        try {

            String Sqlquery = " select * from sale_trans_order where status!=4 and sale_trans_order_id in (select sale_trans_order_id from sale_trans_detail_order where sale_trans_detail_id in (select sale_trans_detail_id from sale_trans_serial_order where from_serial <= ? and ? <= to_serial))  ";
            Query query = getSession().createSQLQuery(Sqlquery);
            query.setParameter(0, serial);
            query.setParameter(1, serial);
            List listStock = query.list();
            if (listStock != null && listStock.size() != 0) {
                alreadlySerial = true;
            }
        } catch (Exception ex) {
            log.error(" Begin method checkAlreadlyInputOtherTrans ; Exception : " + ex);
        }
        return alreadlySerial;
    }
}
