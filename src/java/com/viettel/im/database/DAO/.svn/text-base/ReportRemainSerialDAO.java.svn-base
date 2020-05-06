/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.common.OriginalViettelMsg;
import com.viettel.common.ViettelMsg;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.viettel.im.common.Constant;
import com.viettel.im.common.ReportConstant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.StockTotal;
import com.viettel.im.database.BO.StockTotalFull;
import com.viettel.im.database.BO.StockTotalId;
import com.viettel.im.database.BO.StockTransSerial;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.common.Constant;
import com.viettel.im.client.form.GoodsForm;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

/**
 *
 * @author NamLT
 */
public class ReportRemainSerialDAO extends BaseDAOAction {
//
//    private Session session;
//
//    @Override
//    public void setSession(Session session) {
//        this.session = session;
//    }
//
//    @Override
//    public Session getSession() {
//        if (session == null) {
//            return BaseHibernateDAO.getSession();
//        }
//        return this.session;
//    }
    private GoodsForm goodsForm = new GoodsForm();
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";
    private static final Logger log = Logger.getLogger(ReportRemainSerialDAO.class);

    public GoodsForm getGoodsForm() {
        return goodsForm;




    }

    public void setGoodsForm(GoodsForm goodsForm) {
        this.goodsForm = goodsForm;




    }

    public String prepareViewStockStaffDetail() throws Exception {

        log.debug("# Begin method prepareViewStockStaffDetail");
        String pageForward = "viewStockRemainSerial";

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
                pageForward = "viewStockRemainSerial";




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
                SQL_SELECT_STOCK_TYPE = " from StockType where status= " + Constant.STATUS_USE + " and stockTypeId in " + " ( select stockTypeId from StockModel where status= " + Constant.STATUS_USE + " and stockModelId=?)";




            }

            Query q = getSession().createQuery(SQL_SELECT_STOCK_TYPE);




            if (stockModelId == null || stockModelId.equals(0L)) {
                q.setParameter(0, Constant.STATUS_USE);
                q.setParameter(1, ownerTypeId);
                q.setParameter(2, ownerId);
            } else {
                q.setParameter(0, stockModelId);
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




            }
            req.setAttribute("lstStockGoods", lstStockType);




        } catch (Exception ex) {
            ex.printStackTrace();




            throw ex;




        }
        req.setAttribute("collaborator", "coll");
        log.debug("# End method prepareViewStockDetail");




        return pageForward;


    }

    public List findStockTotalFullByStockModel(
            Long ownerType, Long ownerId, Long stockModelId) {
        log.debug("finding al Goods In Stock ");


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

    public List findStockTotalFull(
            Long ownerType, Long ownerId, Long stockTypeId) {
        log.debug("finding al Goods In Stock ");
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

    public String exportStockStaffDetail() throws Exception {
        log.debug("# Begin method viewStockDetail");
        String pageForward = "viewStockRemainSerial";


        HttpServletRequest req = getRequest();

        try {

            Long ownerTypeId = 0L;
            Long ownerId = 0L;
            Long staffId = 0L;
            String ownerCode = "";
            String shopCode = "";
            String shopName = "";
            String staffCode = "";
            Long shopId = 0L;
            Long stockModelId = goodsForm.getStockModelId();
            String stockModelCode = "";
            Boolean isStaff = false;
            Boolean isStaffOwner = false;
            Boolean isShop = false;
            Boolean isShopOwner = false;
            if (goodsForm.getOwnerType() == null && goodsForm.getOwnerId() == null) {
                req.setAttribute("returnMsg", "stock.viewDetail.noOwnerId");
                return pageForward;
            }

            ownerTypeId = goodsForm.getOwnerType();
            //ownerId = goodsForm.getOwnerId();
            ownerCode =
                    goodsForm.getOwnerCode();
            shopCode = goodsForm.getShopCode();
            shopName = goodsForm.getShopName();
            if (shopCode == null || shopCode.trim().equals("")) {
                pageForward = "viewStockRemainSerial";
                req.setAttribute("returnMsg", "stock.viewDetail.noOwnerId");
                return pageForward;
            } else {

                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
                //  shopId = shop != null ? shop.getShopId() : -1L;
                if (shop == null) {
                    //  pageForward = "viewStockRemainSerial";
                    req.setAttribute("returnMsg", "stock.viewDetail.noOwnerId");
                    return pageForward;
                } else {
                    shopId = shop.getShopId();
                }
            }

            staffCode = goodsForm.getStaffCode();

//            if (staffCode == null || staffCode.trim().equals("")) {
//                pageForward = "viewStockRemainSerial";
//                req.setAttribute("returnMsg", "stock.viewDetail.noOwnerId");
//                return pageForward;
//            }

            isStaff = goodsForm.getIsStaff();
            isStaffOwner = goodsForm.getIsStaffOwner();
            isShop = goodsForm.getIsShop();
            isShopOwner = goodsForm.getIsShopOwner();

            if ((!isStaff && !isStaffOwner) && (isShop || isShopOwner)) {
                ownerTypeId = Constant.OWNER_TYPE_SHOP;
            } else if (isShop && (isStaff || isStaffOwner)) {
                ownerTypeId = -1L;
            }

            if (ownerCode != null && !"".equals(ownerCode) && ownerTypeId.equals(Constant.OWNER_TYPE_STAFF)) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = staffDAO.findStaffAvailableByStaffCode(ownerCode);
                if (staff == null) {
                    //    pageForward = "viewStockRemainSerial";
                    req.setAttribute("returnMsg", "stock.report.impExp.error.staffCodeNotValid");
                    return pageForward;
                } else {
                    ownerId = staff.getStaffId();
                }


            }

            if (ownerTypeId.equals(Constant.OWNER_TYPE_SHOP)) {
                ShopDAO shopDAO = new ShopDAO();
                shopDAO.setSession(this.getSession());
                Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
                ownerId = shop != null ? shop.getShopId() : -1L;
            }

            if (ownerCode != null && !"".equals(ownerCode) && ownerTypeId == -1L) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = staffDAO.findStaffAvailableByStaffCode(ownerCode);
                if (staff == null) {
                    //    pageForward = "viewStockRemainSerial";
                    req.setAttribute("returnMsg", "stock.report.impExp.error.staffCodeNotValid");
                    return pageForward;
                } else {
                    ownerId = staff.getStaffId();
                }


            }


            stockModelCode = goodsForm.getStockModelCode();



            if (staffCode != null && !"".equals(staffCode)) {
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = staffDAO.findStaffAvailableByStaffCode(staffCode);
                if (staff == null) {
                    //   pageForward = "viewStockRemainSerial";
                    req.setAttribute("returnMsg", "ERR.LST.117");
                    return pageForward;
                } else {
                    staffId = staff.getStaffId();
                }

            } else {
                staffId = -1L;
            }






            String SELECT_STOCK_MODEL = "from StockModel where status= " + Constant.STATUS_USE + " and stockModelId in " + " (select id.stockModelId from StockTotal where status = ? and id.ownerType= ? and id.ownerId =? )";
            Query qSelectStockModel = getSession().createQuery(SELECT_STOCK_MODEL);
            qSelectStockModel.setParameter(0, Constant.STATUS_USE);
            qSelectStockModel.setParameter(1, ownerTypeId);
            qSelectStockModel.setParameter(2, ownerId);

            req.setAttribute("lstStockModel", qSelectStockModel.list());


            List<StockTotalFull> lstStockGoods = new ArrayList<StockTotalFull>();
            if (stockModelCode == null || "".equals(stockModelCode)) {
                lstStockGoods = findStockTotalFullWithSerial(ownerTypeId, ownerId, staffId, isStaff, isStaffOwner, isShop, isShopOwner, shopId);
            } else {
                lstStockGoods = findStockTotalFullWithSerial(ownerTypeId, ownerId, staffId, stockModelCode, isStaff, isStaffOwner, isShop, isShopOwner, shopId);
            }


            UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();

            String date = sdf.format(cal.getTime());
            filePath +=
                    "StockSerialReport_" + userToken.getLoginName() + "_" + date + ".xls";

            String templatePath = tmp + "StockSerialReport1.xls";

            //String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String realPath  = filePath;
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);
            Map beans = new HashMap();
            //set ngay tao
            beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            //set nguoi tao
            beans.put("ownerName", shopCode + "_" + shopName);

            beans.put("lstStockGoods", lstStockGoods);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            req.setAttribute("lstStockGoods", lstStockGoods);
            DownloadDAO downloadDAO = new DownloadDAO();
        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, downloadDAO.getFileNameEncNew(realPath, req.getSession()));
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "N?u h? th?ng không t? download. Click vào dây d? t?i File luu thông tin không c?p nh?t du?c");
        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "ERR.CHL.102");
            goodsForm.setExportUrl(filePath);


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

        log.debug("# End method viewStockDetail");
        return pageForward;
    }

    public List findStockTotalFullWithSerial(
            Long ownerType, Long ownerId, Long staffId, Boolean isStaff, Boolean isStaffOwner, Boolean isShop, Boolean isShopOwner, Long shopId) throws Exception {
        log.debug("finding al Goods In Stock ");
        List listParameter = new ArrayList();

        try {
            String queryString = "from StockTotalFull where status=? and quantity >0 ";

            listParameter.add(Constant.STATUS_USE);
            if (ownerType == 2L) {

                queryString += "and id.ownerType = ? ";
                listParameter.add(ownerType);
                //Neu chon ma NV
                if (ownerId != null && ownerId != 0L) {
                    //Neu chon ma NV,khong chon CTV
                    if (isStaff && !isStaffOwner) {
                        queryString += " and id.ownerId =?";
                        listParameter.add(ownerId);

                    } //Neu chon ma CTV,khong chon ma NV
                    else if (!isStaff && isStaffOwner) {
                        if (staffId == -1L) {
                            queryString += " and id.ownerId in (select staffId from Staff where staffOwnerId=? and status =1 and shopId=? ) ";
                            listParameter.add(ownerId);
                            listParameter.add(shopId);
                        } else {
                            queryString += " and id.ownerId =?";
                            listParameter.add(staffId);
                        }
                    } //Neu chon ca 2
                    else if (isStaff && isStaffOwner) {
                        queryString += " and id.ownerId in (select staffId from Staff where status=1 and (staffId=? or staffOwnerId=? ) and shopId=? )   ";
                        listParameter.add(ownerId);
                        listParameter.add(ownerId);
                        listParameter.add(shopId);

                    }
                } //Neu khong chon ma NV
                else {
                    if (isStaff && !isStaffOwner) {
                        //Lay tat ca cac NV thuoc cua hang
                        queryString += " and id.ownerId in (select staffId from Staff where shopId =?  and staffOwnerId is null and status =1) ";
                        listParameter.add(shopId);

                    } //khong chon ma NV,lay tat ca cac CTV
                    else if (!isStaff && isStaffOwner) {
                        queryString += " and id.ownerId in (select staffId from Staff where shopId = ?  and staffOwnerId is not null and status =1) ";
                        listParameter.add(shopId);
                    } //neu chon ca 2,lay tat ca cac NV va CTV thuoc cua hang
                    else if (isStaff && isStaffOwner) {
                        queryString += " and id.ownerId in (select staffId from Staff where shopId = ? and status =1 ) ";
                        listParameter.add(shopId);

                    }


                }
            } else if (ownerType == 1L) {   //Voi BC cua hang
                queryString += "and id.ownerType = ? ";
                listParameter.add(ownerType);
                if (ownerId != null && ownerId != -1L) {
                    //Neu chon cua hang,khong chon dai ly
                    if (isShop && !isShopOwner) {
                        queryString += " and id.ownerId =?  ";
                        listParameter.add(ownerId);

                    } //Neu chon dai ly,khong chon cua hang
                    else if (!isShop && isShopOwner) {
                        queryString += " and id.ownerId in (select shopId from Shop where shopPath like ? and status =1 ) ";
                        listParameter.add("%_" + ownerId + "_%");
                    } //Neu chon ca 2
                    else if (isShop && isShopOwner) {
                        queryString += " and id.ownerId in (select shopId from Shop where ( shopPath like ? or shopId = ? ) and status =1 )   ";
                        listParameter.add("%_" + ownerId + "_%");
                        listParameter.add(ownerId);

                    }
                } else {
                    return null;
                }

            } else if (ownerType == -1L) {   //BC cua hang va nhan vien

                //Neu chon xuat theo cua hang va nhan vien
                if (isShop && isStaff && !isStaffOwner) {
                    if (ownerId != null && ownerId != 0L) {    //Nhap nhan vien,lay BC cua Shop va NV
                        queryString += "and (id.ownerId = ? and id.ownerType=1)";
                        queryString += " or (id.ownerId =? and id.ownerType=2 ) ";
                        listParameter.add(shopId);
                        listParameter.add(ownerId);
                    } else {   //khong nhap NV lay all nhan vien thuoc cua hang
                        queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                        queryString += " or (id.ownerId in (select staffId from Staff where shopId = ?  and staffOwnerId is null ) and id.ownerType=2))";
                        listParameter.add(shopId);
                        listParameter.add(shopId);

                    }
                } //Neu chon xuat cua hang va CTV
                else if (isShop && isStaffOwner && !isStaff) {
                    if (ownerId != null && ownerId != 0L) {

                        if (staffId == -1L) {    //Nhap nv khong nhap CTV,lay all CTV thuoc NV
                            queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                            queryString += " or (id.ownerId in (select staffId from Staff where shopId=? and staffOwnerId =? and status =1 ) and id.ownerType=2))";

                            listParameter.add(shopId);
                            listParameter.add(shopId);
                            listParameter.add(ownerId);
                        } else {   //Nhap ca NV va CTV
                            queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                            queryString += " or (id.ownerId =?))";
                            listParameter.add(shopId);
                            listParameter.add(staffId);
                        }


                    } else {    //khong nhap NV

                        if (staffId == -1L) {   //khong nhap NV va CTV,lay all CTV thuoc shop
                            queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                            queryString += " or (id.ownerId in (select staffId from Staff where shopId=? and staffOwnerId is not null and status =1) and id.ownerType=2))";

                            listParameter.add(shopId);
                            listParameter.add(shopId);
                            //listParameter.add(ownerId);
                        } else {   //Khong nhap NV nhung nhap CTV,lay BC shop va CTV
                            queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                            queryString += " or (id.ownerId =?))";
                            listParameter.add(shopId);
                            listParameter.add(staffId);
                        }
                    }
                } //Chon ca 3
                else if (isShop && isStaff && isStaffOwner) {
                    if (ownerId != null && ownerId != 0L) {   //Nhap ma NV

                        if (staffId == -1L) {   // nhap NV khong nhap CTV,lay all CTV thuoc NV
                            queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                            queryString += " or (id.ownerId = ? and id.ownerType=2)";
                            queryString += " or (id.ownerId in (select staffId from Staff where staffOwnerId=? and status =1 ) and id.ownerType=2))";
                            listParameter.add(shopId);
                            listParameter.add(ownerId);
                            listParameter.add(ownerId);


                        } //Nhap ma CTV
                        else {
                            queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                            queryString += " or (id.ownerId =? and id.ownerType=2)";
                            queryString += " or (id.ownerId =? and id.ownerType=2))";

                            listParameter.add(shopId);
                            listParameter.add(ownerId);
                            listParameter.add(staffId);


                        }

                    } //Khong nhap ma NV lay all Shop,NV va CTV
                    else {


                        queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                        queryString += " or (id.ownerId in (select staffId from Staff where shopId = ? and status =1 ) and id.ownerType=2))";

                        listParameter.add(shopId);
                        listParameter.add(shopId);





                    }

                }
            }
            queryString += " order by isStaff";

            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < listParameter.size(); i++) {
                queryObject.setParameter(i, listParameter.get(i));
            }


            List<StockTotalFull> lst = queryObject.list();
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(
                    this.getSession());






            for (StockTotalFull stockTotalFull : lst) {
                StockModel model = stockModelDAO.findById(stockTotalFull.getId().getStockModelId());
                if (model != null && model.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                    List<StockTransSerial> tmpList = getRangeSerialByOwner(ownerType, stockTotalFull.getId().getOwnerId(), stockTotalFull.getStockTypeId(), stockTotalFull.getId().getStockModelId(), stockTotalFull.getId().getStateId(), Constant.STATUS_USE, shopId, isStaff, isStaffOwner);
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
            Long ownerType, Long ownerId, Long staffId, String stockModelCode, Boolean isStaff, Boolean isStaffOwner, Boolean isShop, Boolean isShopOwner, Long shopId) throws Exception {
        log.debug("finding al Goods In Stock ");
        List listParameter = new ArrayList();

        try {
            String queryString = "from StockTotalFull where stockModelCode= ? and status=? and quantity >0";
            //  listParameter.add(ownerType);
            listParameter.add(stockModelCode);
            listParameter.add(Constant.STATUS_USE);

            if (ownerType == 2L) {

                queryString += "and id.ownerType = ? ";
                listParameter.add(ownerType);
                //Neu chon ma NV
                if (ownerId != null && ownerId != 0L) {
                    //Neu chon ma NV,khong chon CTV
                    if (isStaff && !isStaffOwner) {
                        queryString += " and id.ownerId =?  ";
                        listParameter.add(ownerId);

                    } //Neu chon ma CTV,khong chon ma NV
                    else if (!isStaff && isStaffOwner) {
                        if (staffId == -1L) {
                            queryString += " and id.ownerId in (select staffId from Staff where staffOwnerId=? and status =1 and shopId=? ) ";
                            listParameter.add(ownerId);
                            listParameter.add(shopId);
                        } else {
                            queryString += " and id.ownerId =?";
                            listParameter.add(staffId);
                        }
                    } //Neu chon ca 2
                    else if (isStaff && isStaffOwner) {
                        queryString += " and id.ownerId in (select staffId from Staff where status=1 and (staffId=? or staffOwnerId=? ) and shopId=? )   ";
                        listParameter.add(ownerId);
                        listParameter.add(ownerId);
                        listParameter.add(shopId);

                    }
                } //Neu khong chon ma NV
                else {
                    if (isStaff && !isStaffOwner) {
                        //Lay tat ca cac NV thuoc cua hang
                        queryString += " and id.ownerId in (select staffId from Staff where shopId =?  and staffOwnerId is null and status =1) ";
                        listParameter.add(shopId);

                    } //khong chon ma NV,lay tat ca cac CTV
                    else if (!isStaff && isStaffOwner) {
                        queryString += " and id.ownerId in (select staffId from Staff where shopId = ?  and staffOwnerId is not null and status =1) ";
                        listParameter.add(shopId);
                    } //neu chon ca 2,lay tat ca cac NV va CTV thuoc cua hang
                    else if (isStaff && isStaffOwner) {
                        queryString += " and id.ownerId in (select staffId from Staff where shopId = ? and status =1 ) ";
                        listParameter.add(shopId);

                    }


                }
            } else if (ownerType == 1L) {   //Voi BC cua hang
                queryString += "and id.ownerType = ? ";
                listParameter.add(ownerType);
                if (ownerId != null && ownerId != -1L) {
                    //Neu chon cua hang,khong chon dai ly
                    if (isShop && !isShopOwner) {
                        queryString += " and id.ownerId =?  ";
                        listParameter.add(ownerId);

                    } //Neu chon dai ly,khong chon cua hang
                    else if (!isShop && isShopOwner) {
                        queryString += " and id.ownerId in (select shopId from Shop where shopPath like ? and status =1 ) ";
                        listParameter.add("%_" + ownerId + "_%");
                    } //Neu chon ca 2
                    else if (isShop && isShopOwner) {
                        queryString += " and id.ownerId in (select shopId from Shop where ( shopPath like ? or shopId = ? ) and status =1 )   ";
                        listParameter.add("%_" + ownerId + "_%");
                        listParameter.add(ownerId);

                    }
                } else {
                    return null;
                }

            } else if (ownerType == -1L) {   //BC cua hang va nhan vien

                //Neu chon xuat theo cua hang va nhan vien
                if (isShop && isStaff && !isStaffOwner) {
                    if (ownerId != null && ownerId != 0L) {    //Nhap nhan vien,lay BC cua Shop va NV
                        queryString += "and (id.ownerId = ? and id.ownerType=1)";
                        queryString += " or (id.ownerId =? and id.ownerType=2 ) ";
                        listParameter.add(shopId);
                        listParameter.add(ownerId);
                    } else {   //khong nhap NV lay all nhan vien thuoc cua hang
                        queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                        queryString += " or (id.ownerId in (select staffId from Staff where shopId = ?  and staffOwnerId is null ) and id.ownerType=2))";
                        listParameter.add(shopId);
                        listParameter.add(shopId);

                    }
                } //Neu chon xuat cua hang va CTV
                else if (isShop && isStaffOwner && !isStaff) {
                    if (ownerId != null && ownerId != 0L) {

                        if (staffId == -1L) {    //Nhap nv khong nhap CTV,lay all CTV thuoc NV
                            queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                            queryString += " or (id.ownerId in (select staffId from Staff where shopId=? and staffOwnerId =? and status =1 ) and id.ownerType=2))";

                            listParameter.add(shopId);
                            listParameter.add(shopId);
                            listParameter.add(ownerId);
                        } else {   //Nhap ca NV va CTV
                            queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                            queryString += " or (id.ownerId =?))";
                            listParameter.add(shopId);
                            listParameter.add(staffId);
                        }


                    } else {    //khong nhap NV

                        if (staffId == -1L) {   //khong nhap NV va CTV,lay all CTV thuoc shop
                            queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                            queryString += " or (id.ownerId in (select staffId from Staff where shopId=? and staffOwnerId is not null and status =1) and id.ownerType=2))";

                            listParameter.add(shopId);
                            listParameter.add(shopId);
                            //listParameter.add(ownerId);
                        } else {   //Khong nhap NV nhung nhap CTV,lay BC shop va CTV
                            queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                            queryString += " or (id.ownerId =?))";
                            listParameter.add(shopId);
                            listParameter.add(staffId);
                        }
                    }
                } //Chon ca 3
                else if (isShop && isStaff && isStaffOwner) {
                    if (ownerId != null && ownerId != 0L) {   //Nhap ma NV

                        if (staffId == -1L) {   // nhap NV khong nhap CTV,lay all CTV thuoc NV
                            queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                            queryString += " or (id.ownerId = ? and id.ownerType=2)";
                            queryString += " or (id.ownerId in (select staffId from Staff where staffOwnerId=? and status =1 ) and id.ownerType=2))";
                            listParameter.add(shopId);
                            listParameter.add(ownerId);
                            listParameter.add(ownerId);


                        } //Nhap ma CTV
                        else {
                            queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                            queryString += " or (id.ownerId =? and id.ownerType=2)";
                            queryString += " or (id.ownerId =? and id.ownerType=2))";

                            listParameter.add(shopId);
                            listParameter.add(ownerId);
                            listParameter.add(staffId);


                        }

                    } //Khong nhap ma NV lay all Shop,NV va CTV
                    else {


                        queryString += "and ((id.ownerId = ? and id.ownerType=1)";
                        queryString += " or (id.ownerId in (select staffId from Staff where shopId = ? and status =1 ) and id.ownerType=2))";

                        listParameter.add(shopId);
                        listParameter.add(shopId);





                    }

                }
            }

            queryString += " order by isStaff";
            Query queryObject = getSession().createQuery(queryString);


            for (int i = 0; i
                    < listParameter.size(); i++) {
                queryObject.setParameter(i, listParameter.get(i));

            }
            List<StockTotalFull> lst = queryObject.list();
            StockModelDAO stockModelDAO = new StockModelDAO();
            stockModelDAO.setSession(this.getSession());


            for (StockTotalFull stockTotalFull : lst) {
                StockModel model = stockModelDAO.findById(stockTotalFull.getId().getStockModelId());

                if (model != null && model.getCheckSerial().equals(Constant.GOODS_HAVE_SERIAL)) {
                    List<StockTransSerial> tmpList = getRangeSerialByOwner(ownerType, stockTotalFull.getId().getOwnerId(), stockTotalFull.getStockTypeId(), stockTotalFull.getId().getStockModelId(), stockTotalFull.getId().getStateId(), Constant.STATUS_USE, shopId, isStaff, isStaffOwner);
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

    private List<StockTransSerial> getRangeSerialByOwner(Long ownerType, Long ownerId, Long stockTypeId, Long stockModelId, Long stateId, Long status, Long shopId, Boolean isStaff, Boolean isStaffOwner) throws Exception {
        try {
            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            String tableName = baseStockDAO.getTableNameByStockType(stockTypeId, BaseStockDAO.NAME_TYPE_NORMAL);
            List listParameter = new ArrayList();

            String SQL_SELECT = " SELECT MIN (to_number(serial)) as  fromSerial," + " MAX (to_number(serial)) as toSerial," + " MAX(to_number(serial)) -MIN(to_number(serial)) +1 as quantity " + " FROM (SELECT serial, serial - ROW_NUMBER () OVER (ORDER BY to_number(serial)) rn,OWNER_ID " + " FROM " + tableName + " where stock_model_id = ? and" + " state_id = ? and status = ?"
                    + " and owner_id=?";

            listParameter.add(stockModelId);
            listParameter.add(stateId);
            listParameter.add(status);
            listParameter.add(ownerId);
            if (ownerType != null && ownerType != -1L) {
                SQL_SELECT += " and owner_type= ?";
                listParameter.add(ownerType);








            }




            SQL_SELECT += " ) GROUP BY rn,OWNER_ID" + " ORDER BY to_number(fromSerial) ";

            Query q = getSession().createSQLQuery(SQL_SELECT).
                    addScalar("fromSerial", Hibernate.STRING).
                    addScalar("toSerial", Hibernate.STRING).
                    addScalar("quantity", Hibernate.LONG).
                    setResultTransformer(Transformers.aliasToBean(StockTransSerial.class));






            for (int i = 0;
                    i < listParameter.size();
                    i++) {
                q.setParameter(i, listParameter.get(i));
            }





















            return q.list();

        } catch (Exception ex) {
            ex.printStackTrace();










            throw ex;










        }

    }

    public List<ImSearchBean> getListStockModel(ImSearchBean imSearchBean) {
        try {
            String SELECT_STOCK_MODEL = "select new com.viettel.im.client.bean.ImSearchBean(stockModelCode, name) from StockModel where status= " + Constant.STATUS_USE + " and lower(stockModelCode) like ? " + " and stockModelId in " + " (select id.stockModelId from StockTotal where status = ? and id.ownerType= ? ";
            String param = imSearchBean.getOtherParamValue();

            String[] arrParam = param.split(";");








            if (arrParam != null && arrParam.length > 1) {
                Long ownerType = Long.valueOf(arrParam[0]);
                // String ownerCode = String.valueOf(arrParam[1]);
                // if (arrParam[1] != null && !"".equals(arrParam[1])) {
                String ownerCode = String.valueOf(arrParam[1]);
                SELECT_STOCK_MODEL += "and id.ownerId =? )";
                Query qSelectStockModel = getSession().createQuery(SELECT_STOCK_MODEL);
                StaffDAO staffDAO = new StaffDAO();
                staffDAO.setSession(this.getSession());
                Staff staff = staffDAO.findStaffByStaffCode(ownerCode);
                qSelectStockModel.setParameter(0, "%" + imSearchBean.getCode().toLowerCase() + "%");
                qSelectStockModel.setParameter(1, Constant.STATUS_USE);
                qSelectStockModel.setParameter(2, ownerType);
                qSelectStockModel.setParameter(3, staff.getStaffId());








                return qSelectStockModel.list();








            } else {
                Long ownerType = Long.valueOf(arrParam[0]);
                SELECT_STOCK_MODEL += ")";
                Query qSelectStockModel = getSession().createQuery(SELECT_STOCK_MODEL);
                qSelectStockModel.setParameter(0, "%" + imSearchBean.getCode().toLowerCase() + "%");
                qSelectStockModel.setParameter(1, Constant.STATUS_USE);
                qSelectStockModel.setParameter(2, ownerType);








                return qSelectStockModel.list();









            }
        } catch (Exception ex) {
            ex.printStackTrace();








        }



        return null;








    }

    public List<ImSearchBean> getListCTV(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        String ownerCode = imSearchBean.getOtherParamValue().trim().toLowerCase();
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(this.getSession());
        Staff staff = staffDAO.findStaffAvailableByStaffCode(ownerCode);








        if (staff == null) {
            return null;








        } else {


            //lay danh sach cua hang hien tai + cua hang cap duoi
            List listParameter1 = new ArrayList();
            StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
            strQuery1.append("from Staff a ");
            strQuery1.append("where 1 = 1 ");









            if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
                strQuery1.append("and a.staffOwnerId=?");
                listParameter1.add(staff.getStaffId());








            } else {
                //truong hop chua co shop -> tra ve chuoi rong
                return listImSearchBean;








            }


            //tuannv hieu chinh ngay 02/04/2010: cho phep tim kiem ca kho CTV/DB/DL
            strQuery1.append("and a.channelTypeId in (select channelTypeId from ChannelType where objectType=?) ");
            listParameter1.add(Constant.OWNER_TYPE_STAFF.toString());









            if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
                strQuery1.append("and lower(a.staffCode) like ? ");
                listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");








            }

            if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
                strQuery1.append("and lower(a.name) like ? ");
                listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");








            }
            strQuery1.append(" and status=1 ");
            strQuery1.append("and rownum <= ? ");
            listParameter1.add(100L);

            strQuery1.append("order by a.pointOfSale desc, nlssort( a.staffCode, 'nls_sort=Vietnamese') asc ");

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
    }
}
