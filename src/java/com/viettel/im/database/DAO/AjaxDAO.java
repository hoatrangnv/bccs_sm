/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import java.util.List;
import org.hibernate.Session;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ComboListBean;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.QueryCryptUtils;
import com.viettel.im.database.BO.BookType;
import com.viettel.im.database.BO.SaleServices;
import com.viettel.im.database.BO.SaleServicesDetail;
import com.viettel.im.database.BO.SaleServicesModel;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockModel;
import com.viettel.im.database.BO.StockType;
import com.viettel.im.database.BO.Switches;
import com.viettel.im.database.BO.UserToken;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.ajaxtags.helpers.AjaxTreeXmlBuilder;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author thanhnv
 */
public class AjaxDAO extends BaseDAOAction {

    public static String SELECT_RES = "--Chọn tài nguyên--";
    public static String SELECT_DEPT = "--Chọn đơn vị--";
    private String vtAjaxValue;
    public static final Long SHOP_CATEGORY = 1L;
    public static final Long STAFF_CATEGORY = 2L;
    public static final String EMPTY_STRING = "";
    List listItems = new ArrayList();
    Map listItemsCombo = new HashMap();

    public String getResource() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            //Chon hang hoa --> lay don vi tinh
            String stockModelId = httpServletRequest.getParameter("stockModelId");
            if (stockModelId != null) {
                if ("".equals(stockModelId)) {
                    return "success";
                }
                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(this.getSession());
                StockModel model = stockModelDAO.findById(Long.parseLong(stockModelId));
                if (model != null) {
                    listItemsCombo.put(model.getUnit(), model.getUnit());
                    //listItems.add(new Node(model.getUnit(), model.getUnit()));
                    //builder.addItem(model.getUnit(), model.getUnit());
                    return "success";
                }
            }

            //Chon hang hoa tu loai hang hoa
            List lstRes = null;
            String stockTypeId = httpServletRequest.getParameter("stockTypeId");

            if (stockTypeId != null && !"".equals(stockTypeId.trim())) {
                Long id = Long.parseLong(stockTypeId.trim());
                StockModelDAO stockModelDAO = new StockModelDAO();
                stockModelDAO.setSession(this.getSession());
                lstRes = stockModelDAO.findByProperty(StockModelDAO.STOCK_TYPE_ID, id);


                listItemsCombo.put("", SELECT_RES);
                //listItems.add(new Node(SELECT_RES, ""));
                for (Object object : lstRes) {
                    StockModel sm = (StockModel) object;
                    //listItemsCombo.put(sm.getName(), sm.getStockModelId());
                    listItemsCombo.put(sm.getStockModelId(), sm.getName());
                //listItems.add(new Node(sm.getStockModelId().toString(), sm.getName()));
                }
            }

            /*NamNX add chon Equipment  --> Switch
             * */

            String sEquipmentId = httpServletRequest.getParameter("equipmentId");

            if (sEquipmentId != null && !"".equals(sEquipmentId.trim())) {
                Long id = Long.parseLong(sEquipmentId.trim());
                ChassicDAO ChassicDAO = new ChassicDAO();
                ChassicDAO.setSession(this.getSession());
                lstRes = ChassicDAO.findSwitchByEquipmentId(id);


                listItemsCombo.put("", SELECT_RES);
                //listItems.add(new Node(SELECT_RES, ""));
                for (Object object : lstRes) {
                    Switches sm = (Switches) object;
                    listItemsCombo.put(sm.getName(), sm.getSwitchId());
                //listItems.add(new Node(sm.getStockModelId().toString(), sm.getName()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return "success";
    }

    public String getBillDeparmentCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            String ShopType = req.getParameter("billDepartmentKind");
            String ShopCode = req.getParameter("formBill.billDepartmentName");

            if (ShopCode != null && ShopType != null) {
                if ("".equals(ShopCode)) {
                    return "success";
                }
                List parameterList = new ArrayList();
                String queryString = "from " + ((ShopType.equals("2")) ? " Staff " : " Shop ");
                queryString +=
                        " where status = ? ";
                parameterList.add(Constant.STATUS_USE);
                queryString += " and " + ((ShopType.equals("2")) ? " lower(staffCode) " : " lower(shopCode) ") + " like ? ";
                parameterList.add("%" + ShopCode.toLowerCase() + "%");

                if (!ShopType.equals("1")) {
                    queryString += " and shopId = ? ";
                    parameterList.add(userToken.getShopId());
                } else {
                    queryString += " and parentShopId = ? ";
                    parameterList.add(userToken.getShopId());
                }

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i <
                        parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }

                if (!queryObject.list().isEmpty()) {
                    if (ShopType.equals("2")) {
                        List<Staff> ListShopCode = new ArrayList();
                        ListShopCode =
                                queryObject.list();
                        for (int i = 0; i <
                                ListShopCode.size(); i++) {
                            listItemsCombo.put(ListShopCode.get(i).getStaffId(), ListShopCode.get(i).getStaffCode());
                        }

                    } else {
                        List<Shop> ListShopCode = new ArrayList();
                        ListShopCode =
                                queryObject.list();
                        for (int i = 0; i <
                                ListShopCode.size(); i++) {
                            listItemsCombo.put(ListShopCode.get(i).getShopId(), ListShopCode.get(i).getShopCode());
                        }
                    }
                    return "success";
                }
            } else {
                listItemsCombo.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    //MrSun
    public String getShopChildCode() throws Exception {
        try {
            HttpServletRequest req = getRequest();
            HttpSession session = req.getSession();
            UserToken userToken = (UserToken) session.getAttribute(Constant.USER_TOKEN);
            String ShopType = req.getParameter("billDepartmentKind");
            String ShopCode = req.getParameter("formBill.billDepartmentName");
            
            listItemsCombo.clear();
            CommonDAO commonDAO = new CommonDAO();
            commonDAO.setSession(this.getSession());
            List<ComboListBean> list = commonDAO.getShopAndStaffList(userToken.getShopId(), null, ShopCode, ShopType, 0,true,false);

            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    listItemsCombo.put(list.get(i).getObjId(), list.get(i).getObjCode());
                }
                return "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public List<Long> getListShopChild(Long shopId) {
        try {
            List<Long> listShopId = new ArrayList<Long>() ;
            String shopPath = null;
            List parameterList = new ArrayList();
            String queryString = " from Shop ";
            queryString += " where status = ? ";
            parameterList.add(Constant.STATUS_USE);
            queryString += " and shopId = ?";
            parameterList.add(shopId);
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i < parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            if (queryObject.list() != null && queryObject.list().size() != 0) {
                Shop shop = (Shop) queryObject.list().get(0);
                shopPath = shop.getShopPath();
            }

            List parameterList2 = new ArrayList();
            String queryString2 = "select a.shopId  from Shop a ";
            queryString2 += " where a.status = ? ";
            parameterList2.add(Constant.STATUS_USE);
            queryString2 += " and a.shopPath like ?";
            parameterList2.add(shopPath + "%");
            Query queryObject2 = getSession().createQuery(queryString2);
            for (int i = 0; i < parameterList2.size(); i++) {
                queryObject2.setParameter(i, parameterList2.get(i));
            }
            listShopId = queryObject2.list();
            return listShopId;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
    Map billDepartmentName = new HashMap();

    //MrSun
    public String getBillDeparmentName() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String ShopId = httpServletRequest.getParameter("billDepartmentId");
            String ShopType = httpServletRequest.getParameter("billDepartmentKind");
            System.out.println("ShopId: " + ShopId);
            UserToken userToken = (UserToken) httpServletRequest.getSession().getAttribute(Constant.USER_TOKEN);
            CommonDAO commonDAO = new CommonDAO();
            commonDAO.setSession(this.getSession());
            List<ComboListBean> list = commonDAO.getShopAndStaffList(userToken.getShopId(),Long.valueOf(ShopId), "", ShopType, 0, false, false);
            if (list!= null && list.size()>0){
                billDepartmentName.put("billDepartmentNameB", list.get(0).getObjName());
            }
            else
                billDepartmentName.put("billDepartmentNameB", "");
            return "success";

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    
    Map billCategoryName = new HashMap();

    public String getBillCategory() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            Long billSerialId = Long.parseLong(httpServletRequest.getParameter("billSerialId"));
            List parameterList = new ArrayList();
            String queryString = " from BookType ";
            queryString += " where status = ? ";
            parameterList.add(Constant.STATUS_USE);
            queryString += " and bookTypeId = ? ";
            parameterList.add(billSerialId);
            Query queryObject = getSession().createQuery(queryString);
            for (int i = 0; i <
                    parameterList.size(); i++) {
                queryObject.setParameter(i, parameterList.get(i));
            }
            BookType crrBookType = (BookType) queryObject.list().get(0);
            billCategoryName.put("billCategory", crrBookType.getBlockName());
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
    Map listBillSerial = new HashMap();

    public String getListBillSerials() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            String billSerial = httpServletRequest.getParameter("form.billSerial");
            if (billSerial != null) {
                List parameterList = new ArrayList();
                String queryString = " from BookType ";
                queryString += " where status = ? ";
                parameterList.add(Constant.STATUS_USE);
                queryString += " and lower(serialCode) like ? ";
                parameterList.add("%" + billSerial.toLowerCase() + "%");
                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i <
                        parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }
                List<BookType> crrBookType = queryObject.list();
                for (int i = 0; i < crrBookType.size(); i++) {
                    listBillSerial.put(crrBookType.get(i).getBookTypeId(), crrBookType.get(i).getSerialCode());
                }
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Shop> findShopChildByShopId(Long shopId) {

        try {
            String queryString = "select new Shop(a.shopId, a.name) " +
                    "from Shop a " +
                    "where a.parentShopId = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopId);
            return queryObject.list();
        } catch (RuntimeException re) {

            throw re;
        }
    }

    public List<Staff> findAllStaffByShopId(Long shopId) {

        try {
            String queryString = "select new Staff(a.staffId, a.name) " +
                    "from Staff a " +
                    "where a.shopId = ?";
            Session session = getSession();
            Query queryObject = session.createQuery(queryString);
            queryObject.setParameter(0, shopId);
            return queryObject.list();
        } catch (RuntimeException re) {

            throw re;
        }
    }

    public String getChild() throws Exception {
        try {
            this.listItems = new ArrayList();
            Session hbnSession = getSession();

            HttpServletRequest httpServletRequest = getRequest();

            String node = QueryCryptUtils.getParameter(httpServletRequest, "nodeId");
            if (node.indexOf("0_") == 0) {
                //neu la lan lay cay du lieu muc 0 (goc)
                String nodeId = "1_";
                getListItems().add(new Node("Khoi tao Item dau tien", "true", nodeId, "1_a"));
            } else if (node.indexOf("1_") == 0) {
                //neu la lan lay cay du lieu muc 1, tra ve danh sach cac saleServices
                List listSaleServices = hbnSession.createCriteria(SaleServices.class).
                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("name")).
                        list();
                Iterator iterSaleServices = listSaleServices.iterator();
                while (iterSaleServices.hasNext()) {
                    SaleServices saleServices = (SaleServices) iterSaleServices.next();
                    String nodeId = "2_" + saleServices.getSaleServicesId().toString(); //them prefix 2_ de xac dinh la node level
                    getListItems().add(new Node(saleServices.getName(), "true", nodeId, httpServletRequest.getContextPath() + "/saleServicesModelAction.do?selectedSaleServicesId=" + saleServices.getSaleServicesId().toString()));
                }

            } else if (node.indexOf("2_") == 0) {
                //neu la lan lay cay du lieu muc 2, tra ve danh sach cac saleServicesModel tuong ung voi saleServicesId
                node = node.substring(2); //bo phan prefix danh dau level cua cay (2_)
                List listSaleServicesModel = hbnSession.createCriteria(SaleServicesModel.class).
                        add(Restrictions.eq("saleServicesId", Long.parseLong(node))).
                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("stockTypeId")).
                        list();

                Iterator iterSaleServicesModel = listSaleServicesModel.iterator();
                while (iterSaleServicesModel.hasNext()) {
                    SaleServicesModel saleServicesModel = (SaleServicesModel) iterSaleServicesModel.next();
                    //ung voi moi saleServicesModel, tim stockType tuong ung
                    List listStockTypes = hbnSession.createCriteria(StockType.class).
                            add(Restrictions.eq("stockTypeId", saleServicesModel.getStockTypeId())).
                            add(Restrictions.eq("status", 1L)).
                            list();
                    StockType stockType = (StockType) listStockTypes.get(0);
                    String nodeId = "3_" + node + "_" + saleServicesModel.getSaleServicesModelId().toString(); //them prefix 3_ de xac dinh node level
                    getListItems().add(new Node(stockType.getName(), "true", nodeId, httpServletRequest.getContextPath() + "/saleServicesDetailAction.do?selectedSaleServicesModelId=" + saleServicesModel.getSaleServicesModelId().toString()));
                }
            } else if (node.indexOf("3_") == 0) {
                //neu la lan lay cay du lieu muc 3, tra ve danh sach cac saleServiceDetail tuong ung voi saleServicesModel
                node = node.substring(2); //bo phan prefix danh dau level cua cay (3_)
                String saleServicesModelId = node.substring(node.lastIndexOf("_") + 1); //lay id, vi cac id phan cach nhau boi dau "_"
                List listSaleServicesDetail = hbnSession.createCriteria(SaleServicesDetail.class).
                        add(Restrictions.eq("saleServicesModelId", Long.parseLong(saleServicesModelId))).
                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("id")).
                        list();

                Iterator iterSaleServicesDetail = listSaleServicesDetail.iterator();
                while (iterSaleServicesDetail.hasNext()) {
                    SaleServicesDetail saleServicesDetail = (SaleServicesDetail) iterSaleServicesDetail.next();
                    //ung voi moi saleServicesDetail, tim stockModel tuong ung
                    List listStockModels = hbnSession.createCriteria(StockModel.class).
                            add(Restrictions.eq("stockModelId", saleServicesDetail.getStockModelId())).
                            add(Restrictions.eq("status", 1L)).
                            list();
                    StockModel stockModel = (StockModel) listStockModels.get(0);
                    String nodeId = "L_" + node + "_" + saleServicesDetail.getId().toString(); //them prefix L_ de xac dinh node level (nut la')
                    getListItems().add(new Node(stockModel.getName(), "false", nodeId, "#"));
                }
            }
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


    }

    public String getSaleServicesTree() throws Exception {
        try {
            Session hbnSession = getSession();

            HttpServletRequest httpServletRequest = getRequest();

            String action = httpServletRequest.getParameter("action");
            String node = QueryCryptUtils.getParameter(httpServletRequest, "node");
//            node = "2_1";
            if (action != null && action.equalsIgnoreCase("info")) {
                return "<b>" + node + "</b> at " + new Date(); //
            }

            AjaxTreeXmlBuilder treeBuilder = new AjaxTreeXmlBuilder();
            if (node.indexOf("0_") == 0) {
                //neu la lan lay cay du lieu muc 0 (goc)
                String nodeId = "1_";
                treeBuilder.addItem("Tất cả các dịch vụ", nodeId, false, "javascript:rootNodeClicked()");
            } else if (node.indexOf("1_") == 0) {
                //neu la lan lay cay du lieu muc 1, tra ve danh sach cac saleServices
                List listSaleServices = hbnSession.createCriteria(SaleServices.class).
                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("name")).
                        list();
                Iterator iterSaleServices = listSaleServices.iterator();
                while (iterSaleServices.hasNext()) {
                    SaleServices saleServices = (SaleServices) iterSaleServices.next();
                    String nodeId = "2_" + saleServices.getSaleServicesId().toString(); //them prefix 2_ de xac dinh la node level
                    treeBuilder.addItem(saleServices.getName(), nodeId, true, "javascript:saleServicesNodeClicked('" + saleServices.getSaleServicesId().toString() + "')");
                }

            } else if (node.indexOf("2_") == 0) {
                //neu la lan lay cay du lieu muc 2, tra ve danh sach cac saleServicesModel tuong ung voi saleServicesId
                node = node.substring(2); //bo phan prefix danh dau level cua cay (2_)
                List listSaleServicesModel = hbnSession.createCriteria(SaleServicesModel.class).
                        add(Restrictions.eq("saleServicesId", Long.parseLong(node))).
                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("stockTypeId")).
                        list();

                Iterator iterSaleServicesModel = listSaleServicesModel.iterator();
                while (iterSaleServicesModel.hasNext()) {
                    SaleServicesModel saleServicesModel = (SaleServicesModel) iterSaleServicesModel.next();
                    //ung voi moi saleServicesModel, tim stockType tuong ung
                    List listStockTypes = hbnSession.createCriteria(StockType.class).
                            add(Restrictions.eq("stockTypeId", saleServicesModel.getStockTypeId())).
                            add(Restrictions.eq("status", 1L)).
                            list();
                    StockType stockType = (StockType) listStockTypes.get(0);
                    String nodeId = "3_" + node + "_" + saleServicesModel.getSaleServicesModelId().toString(); //them prefix 3_ de xac dinh node level
                    treeBuilder.addItem(stockType.getName(), nodeId, true, "javascript:saleServicesModelNodeClicked('" + saleServicesModel.getSaleServicesModelId().toString() + "')");
                }
            } else if (node.indexOf("3_") == 0) {
                //neu la lan lay cay du lieu muc 3, tra ve danh sach cac saleServiceDetail tuong ung voi saleServicesModel
                node = node.substring(2); //bo phan prefix danh dau level cua cay (3_)
                String saleServicesModelId = node.substring(node.lastIndexOf("_") + 1); //lay id, vi cac id phan cach nhau boi dau "_"
                List listSaleServicesDetail = hbnSession.createCriteria(SaleServicesDetail.class).
                        add(Restrictions.eq("saleServicesModelId", Long.parseLong(saleServicesModelId))).
                        add(Restrictions.eq("status", 1L)).
                        addOrder(Order.asc("id")).
                        list();

                Iterator iterSaleServicesDetail = listSaleServicesDetail.iterator();
                while (iterSaleServicesDetail.hasNext()) {
                    SaleServicesDetail saleServicesDetail = (SaleServicesDetail) iterSaleServicesDetail.next();
                    //ung voi moi saleServicesDetail, tim stockModel tuong ung
                    List listStockModels = hbnSession.createCriteria(StockModel.class).
                            add(Restrictions.eq("stockModelId", saleServicesDetail.getStockModelId())).
                            add(Restrictions.eq("status", 1L)).
                            list();
                    StockModel stockModel = (StockModel) listStockModels.get(0);
                    String nodeId = "L_" + node + "_" + saleServicesDetail.getId().toString(); //them prefix L_ de xac dinh node level (nut la')
                    treeBuilder.addItem(stockModel.getName(), nodeId, true, "javascript:void(0)");
                }
            }
            setVtAjaxValue(treeBuilder.toString());
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Map getListBillSerial() {
        return listBillSerial;
    }

    public void setListBillSerial(Map listBillSerial) {
        this.listBillSerial = listBillSerial;
    }

    public Map getBillCategoryName() {
        return billCategoryName;
    }

    public void setBillCategoryName(Map billCategoryName) {
        this.billCategoryName = billCategoryName;
    }

    public Map getBillDepartmentName() {
        return billDepartmentName;
    }

    public void setBillDepartmentName(Map billDepartmentName) {
        this.billDepartmentName = billDepartmentName;
    }

    public String getVtAjaxValue() {
        return vtAjaxValue;
    }

    public void setVtAjaxValue(String vtAjaxValue) {
        this.vtAjaxValue = vtAjaxValue;
    }

    public List getListItems() {
        return listItems;
    }

    public void setListItems(List listItems) {
        this.listItems = listItems;
    }

    public Map getListItemsCombo() {
        return listItemsCombo;
    }

    public void setListItemsCombo(Map listItemsCombo) {
        this.listItemsCombo = listItemsCombo;
    }
}
