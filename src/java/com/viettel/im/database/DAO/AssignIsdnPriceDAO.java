package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.LogAssignIsdnPriceBean;
import com.viettel.im.client.form.AssignIsdnPriceForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.FilterType;
import com.viettel.im.database.BO.IsdnFilterRules;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.StockKit;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import com.viettel.im.common.util.DateTimeUtils;
import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import org.hibernate.Session;
import com.viettel.im.database.BO.IsdnTrans;
import com.viettel.im.database.BO.IsdnTransDetail;

/**
 * A data access object (DAO) providing persistence and search support for
 * StockKit entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.viettel.im.database.BO.StockKit
 * @author MyEclipse Persistence Tools
 */
public class AssignIsdnPriceDAO extends BaseDAOAction {

    private static final Log log = LogFactory.getLog(AssignIsdnPriceDAO.class);
    public String pageForward;
    private AssignIsdnPriceForm assignIsdnPriceForm = new AssignIsdnPriceForm();

    public AssignIsdnPriceForm getAssignIsdnPriceForm() {
        return assignIsdnPriceForm;
    }

    public void setAssignIsdnPriceForm(AssignIsdnPriceForm assignIsdnPriceForm) {
        this.assignIsdnPriceForm = assignIsdnPriceForm;
    }

    public String preparePage() throws Exception {
        /** Action common object */
        log.debug("# Begin method AssignIsdnPriceDAO.preparePage");
        HttpServletRequest req = getRequest();
        pageForward = "prepareAssignIsdnPrice";
        log.debug("# End method AssignIsdnPriceDAO.preparePage");
        return pageForward;
    }
    /*
     * Author: ThanhNC
     * Date created: 10/06/2009
     * Purpose: Gan gia cho so dep
     */

    public String assignIsdnPrice() throws Exception {


        log.debug("# Begin method AssignIsdnPriceDAO.assignIsdnPrice");
        HttpServletRequest req = getRequest();

        try {
            pageForward = "assignIsdnPrice";
            if (assignIsdnPriceForm.getStockTypeId() == null) {
                req.setAttribute("resultAssignIsdnPrice", "assignIsdnPrice.error.noStockType");
                return pageForward;
            }
            if (assignIsdnPriceForm.getImpFile() == null) {
                req.setAttribute("resultAssignIsdnPrice", "assignIsdnPrice.error.noFileAttachment");
                return pageForward;
            }
            File clientFile = assignIsdnPriceForm.getImpFile();
            List lstIsdn = new CommonDAO().readExcelFile(clientFile, "Sheet1", 0, 0, 4);
            if (lstIsdn == null || lstIsdn.size() == 0) {
                req.setAttribute("resultAssignIsdnPrice", "assignIsdnPrice.error.errorReadFile");
                return pageForward;
            }
            //Danh sach loi
            List<LogAssignIsdnPriceBean> lstError = new ArrayList<LogAssignIsdnPriceBean>();
            Object[] obj = null;
            LogAssignIsdnPriceBean logAssignIsdnPriceBean = null;
            String isdn = "";
            String strPrice = "";
            Long price = 0L;
            String strFilterType = "";
            Long filterTypeId = null;
            String strRule = "";
            Long ruleId = null;
            String shopCode = "";
            FilterTypeDAO filterTypeDAO = new FilterTypeDAO();
            filterTypeDAO.setSession(this.getSession());

            IsdnFilterRulesDAO isdnFilterRulesDAO = new IsdnFilterRulesDAO();
            isdnFilterRulesDAO.setSession(this.getSession());

            ShopDAO shopDAO = new ShopDAO();
            shopDAO.setSession(this.getSession());

            BaseStockDAO baseStockDAO = new BaseStockDAO();
            baseStockDAO.setSession(this.getSession());
            String stockModelName = baseStockDAO.getTableNameByStockType(assignIsdnPriceForm.getStockTypeId(), BaseStockDAO.NAME_TYPE_HIBERNATE);

            if (stockModelName == null || "".equals(stockModelName.trim())) {
                req.setAttribute("resultAssignIsdnPrice", "assignIsdnPrice.error.stockTypeNotExits");
                return pageForward;
            }
            CommonDAO commonDAO = new CommonDAO();
            Long icountError = 1L;
            for (int idx = 0; idx < lstIsdn.size(); idx++) {
                //Khoi tao lai bien
                isdn = "";
                strPrice = "";
                price = null;
                strFilterType = "";
                filterTypeId = null;
                strRule = "";
                ruleId = null;
                shopCode = "";
                obj = (Object[]) lstIsdn.get(idx);
                if (obj != null && obj.length >= 5) {
                    isdn = obj[0].toString();
                    //Gia
                    strPrice = obj[1].toString();
                    //Lay ID nhom luat
                    strFilterType = obj[2].toString();
                    //Lay ID bo luat
                    strRule = obj[3].toString();

                    //Lay ma cua hang
                    shopCode = obj[4].toString();
                    //Check dieu kien
                    boolean firstWarning = true;
                    if (isdn == null || "".equals(isdn.trim()) || !isNumber(isdn)) {
                        logAssignIsdnPriceBean = new LogAssignIsdnPriceBean();
                        logAssignIsdnPriceBean.setId(icountError++);
                        logAssignIsdnPriceBean.setIsdn(isdn);
                        logAssignIsdnPriceBean.setPrice(strPrice);
                        logAssignIsdnPriceBean.setFilterType(strFilterType);
                        logAssignIsdnPriceBean.setRule(strRule);
                        logAssignIsdnPriceBean.setShopCode(shopCode);

                        logAssignIsdnPriceBean.setDescription("Trường số thuê bao không phải là số");
                        logAssignIsdnPriceBean.setLogType(LogAssignIsdnPriceBean.ERROR);
                        lstError.add(logAssignIsdnPriceBean);
                        //Bo qua va thuc hien doc ban ghi tiep theo
                        continue;

                    }

                    try {
                        price = Long.parseLong(strPrice);
                        if (price == null || price.compareTo(0L) < 0) {
                            logAssignIsdnPriceBean = new LogAssignIsdnPriceBean();
                            logAssignIsdnPriceBean.setId(icountError++);
                            logAssignIsdnPriceBean.setIsdn(isdn);
                            logAssignIsdnPriceBean.setPrice(strPrice);
                            logAssignIsdnPriceBean.setFilterType(strFilterType);
                            logAssignIsdnPriceBean.setRule(strRule);
                            logAssignIsdnPriceBean.setShopCode(shopCode);
                            logAssignIsdnPriceBean.setDescription("Trường giá phải lớn hơn 0.");
                            logAssignIsdnPriceBean.setLogType(LogAssignIsdnPriceBean.ERROR);
                            lstError.add(logAssignIsdnPriceBean);
                            //Bo qua va doc ban ghi tiep thep
                            continue;
                        }

                    } catch (Exception ex) {
                        //Add loi vao  danh sach loi tra ve
                        logAssignIsdnPriceBean = new LogAssignIsdnPriceBean();
                        logAssignIsdnPriceBean.setId(icountError++);
                        logAssignIsdnPriceBean.setIsdn(isdn);
                        logAssignIsdnPriceBean.setPrice(strPrice);
                        logAssignIsdnPriceBean.setFilterType(strFilterType);
                        logAssignIsdnPriceBean.setRule(strRule);
                        logAssignIsdnPriceBean.setShopCode(shopCode);
                        logAssignIsdnPriceBean.setDescription("Trường giá không hợp lệ.");
                        logAssignIsdnPriceBean.setLogType(LogAssignIsdnPriceBean.ERROR);
                        lstError.add(logAssignIsdnPriceBean);
                        //Bo qua va doc ban ghi tiep thep
                        continue;
                    }


                    if (shopCode == null || "".equals(shopCode.trim())) {
                        logAssignIsdnPriceBean = new LogAssignIsdnPriceBean();
                        logAssignIsdnPriceBean.setId(icountError++);
                        logAssignIsdnPriceBean.setIsdn(isdn);
                        logAssignIsdnPriceBean.setPrice(strPrice);
                        logAssignIsdnPriceBean.setFilterType(strFilterType);
                        logAssignIsdnPriceBean.setRule(strRule);
                        logAssignIsdnPriceBean.setShopCode(shopCode);
                        logAssignIsdnPriceBean.setDescription("Trường mã cửa hàng không có dữ liệu");
                        logAssignIsdnPriceBean.setLogType(LogAssignIsdnPriceBean.ERROR);
                        lstError.add(logAssignIsdnPriceBean);
                        //Bo qua va thuc hien doc ban ghi tiep theo
                        continue;

                    }

                    Shop shop = shopDAO.findShopsAvailableByShopCode(shopCode);
                    if (shop == null) {
                        logAssignIsdnPriceBean = new LogAssignIsdnPriceBean();
                        logAssignIsdnPriceBean.setId(icountError++);
                        logAssignIsdnPriceBean.setIsdn(isdn);
                        logAssignIsdnPriceBean.setPrice(strPrice);
                        logAssignIsdnPriceBean.setFilterType(strFilterType);
                        logAssignIsdnPriceBean.setRule(strRule);
                        logAssignIsdnPriceBean.setShopCode(shopCode);

                        logAssignIsdnPriceBean.setDescription("Trường mã cửa hàng không tồn tại trên hệ thống");
                        logAssignIsdnPriceBean.setLogType(LogAssignIsdnPriceBean.ERROR);
                        lstError.add(logAssignIsdnPriceBean);
                        //Bo qua va thuc hien doc ban ghi tiep theo
                        continue;
                    }

                    //Thuc hien update gia so dep

                    StringBuffer queryString = new StringBuffer("from " + stockModelName + " where 1=1 ");

                    queryString.append(" and to_number(isdn) =  ? ");
                    queryString.append(" and ownerType =  ? ");
                    queryString.append(" and ownerId =  ? ");
                    queryString.append(" and status <>  ? ");

                    Query queryObject = getSession().createQuery(queryString.toString());
                    queryObject.setParameter(0, Long.parseLong(isdn));
                    queryObject.setParameter(1, Constant.OWNER_TYPE_SHOP);
                    queryObject.setParameter(2, shop.getShopId());
                    queryObject.setParameter(3, Constant.STATUS_ISDN_USING);

                    List lstStockIsdn = queryObject.list();
                    if (lstStockIsdn == null || lstStockIsdn.size() == 0) {
                        logAssignIsdnPriceBean = new LogAssignIsdnPriceBean();
                        logAssignIsdnPriceBean.setId(icountError++);
                        logAssignIsdnPriceBean.setIsdn(isdn);
                        logAssignIsdnPriceBean.setPrice(strPrice);
                        logAssignIsdnPriceBean.setFilterType(strFilterType);
                        logAssignIsdnPriceBean.setRule(strRule);
                        logAssignIsdnPriceBean.setShopCode(shopCode);
                        logAssignIsdnPriceBean.setDescription("Số thuê bao" + isdn + " không tồn tại trong cửa hàng " + shopCode);
                        logAssignIsdnPriceBean.setLogType(LogAssignIsdnPriceBean.ERROR);
                        lstError.add(logAssignIsdnPriceBean);
                        //Bo qua va thuc hien doc ban ghi tiep theo
                        continue;
                    }
                    Object stockIsdn = lstStockIsdn.get(0);




                    if (strFilterType != null && !"".equals(strFilterType.trim())) {
                        try {
                            filterTypeId = Long.parseLong(strFilterType);
                            FilterType filterType = filterTypeDAO.findById(filterTypeId);
                            if (filterType == null || !filterType.getStatus().equals(Constant.STATUS_USE)) {
                                logAssignIsdnPriceBean = new LogAssignIsdnPriceBean();
                                logAssignIsdnPriceBean.setId(icountError++);
                                logAssignIsdnPriceBean.setIsdn(isdn);
                                logAssignIsdnPriceBean.setPrice(strPrice);
                                logAssignIsdnPriceBean.setFilterType(strFilterType);
                                logAssignIsdnPriceBean.setRule(strRule);
                                logAssignIsdnPriceBean.setShopCode(shopCode);
                                logAssignIsdnPriceBean.setDescription("Trường ID loại nhóm luật không tồn tại trong danh sách loại nhóm luật");
                                logAssignIsdnPriceBean.setLogType(LogAssignIsdnPriceBean.WARINING);
                                lstError.add(logAssignIsdnPriceBean);
                                filterTypeId = null;
                                firstWarning = false;
                            }
                        } catch (Exception ex) {
                            //Add loi vao  danh sach loi tra ve
                            if (firstWarning) {
                                logAssignIsdnPriceBean = new LogAssignIsdnPriceBean();
                                logAssignIsdnPriceBean.setId(icountError++);
                                logAssignIsdnPriceBean.setIsdn(isdn);
                                logAssignIsdnPriceBean.setPrice(strPrice);
                                logAssignIsdnPriceBean.setFilterType(strFilterType);
                                logAssignIsdnPriceBean.setRule(strRule);
                                logAssignIsdnPriceBean.setShopCode(shopCode);
                                logAssignIsdnPriceBean.setDescription("Trường ID loại nhóm luật không hợp lệ");
                                logAssignIsdnPriceBean.setLogType(LogAssignIsdnPriceBean.WARINING);
                                lstError.add(logAssignIsdnPriceBean);
                                filterTypeId = null;
                                firstWarning = false;
                            }
                            // Van tiep tuc thuc hien

                        }
                    }

                    if (strRule != null && !"".equals(strRule.trim())) {
                        try {
                            ruleId = Long.parseLong(strRule);
                            IsdnFilterRules isdnFilterRules = isdnFilterRulesDAO.findById(ruleId);
                            if ((isdnFilterRules == null || !isdnFilterRules.getStatus().equals(Constant.STATUS_USE) ||
                                    !isdnFilterRules.getFilterTypeId().equals(filterTypeId)) && firstWarning) {
                                logAssignIsdnPriceBean = new LogAssignIsdnPriceBean();
                                logAssignIsdnPriceBean.setId(icountError++);
                                logAssignIsdnPriceBean.setIsdn(isdn);
                                logAssignIsdnPriceBean.setPrice(strPrice);
                                logAssignIsdnPriceBean.setFilterType(strFilterType);
                                logAssignIsdnPriceBean.setRule(strRule);
                                logAssignIsdnPriceBean.setShopCode(shopCode);
                                logAssignIsdnPriceBean.setDescription("Trường ID bộ luật không tồn tại trong danh sách bộ luật");
                                logAssignIsdnPriceBean.setLogType(LogAssignIsdnPriceBean.WARINING);
                                lstError.add(logAssignIsdnPriceBean);
                                ruleId = null;
                                filterTypeId = null;
                                firstWarning = false;
                            }
                            if (!isdnFilterRules.getFilterTypeId().equals(filterTypeId)) {
                                ruleId = null;
                                filterTypeId = null;
                            }
                        } catch (Exception ex) {
                            //Add loi vao  danh sach loi tra ve
                            if (firstWarning) {
                                logAssignIsdnPriceBean = new LogAssignIsdnPriceBean();
                                logAssignIsdnPriceBean.setId(icountError++);
                                logAssignIsdnPriceBean.setIsdn(isdn);
                                logAssignIsdnPriceBean.setPrice(strPrice);
                                logAssignIsdnPriceBean.setFilterType(strFilterType);
                                logAssignIsdnPriceBean.setRule(strRule);
                                logAssignIsdnPriceBean.setShopCode(shopCode);
                                logAssignIsdnPriceBean.setDescription("Trường ID bộ luật không hợp lệ");
                                logAssignIsdnPriceBean.setLogType(LogAssignIsdnPriceBean.WARINING);
                                lstError.add(logAssignIsdnPriceBean);
                                ruleId = null;
                                filterTypeId = null;
                                firstWarning = false;
                            }
                            // Van tiep tuc thuc hien

                        }
                    }
                    HashMap<String, Object> updateMap = new HashMap<String, Object>();
                    updateMap.put("setPrice", price);
                    updateMap.put("setIsdnType", Constant.ISDN_TYPE_SPEC);
                    if (filterTypeId != null) {
                        updateMap.put("setFilterTypeId", filterTypeId);
                    }
                    if (ruleId != null) {
                        updateMap.put("setRulesId", ruleId);
                    }
                    //he he de loi day cho ma nho
                    stockIsdn = commonDAO.setFieldsForObject(stockIsdn, updateMap);
                    getSession().save(stockIsdn);


                    //------------------------start----------------------------------------
                    //              Andv
                    //luu thong tin dai so can tao isdnTrans va isdnTransDetail
                    Session session = getSession();
                    Long stockTypeId = assignIsdnPriceForm.getStockTypeId();
                    UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                String message = "";
//                message = simpleDateFormat.format(DateTimeUtils.getSysdate()) + " bắt đầu lưu thông tin dải số cần tạo";
//                System.out.println(message);

                    IsdnTrans isdnTrans = new IsdnTrans();
                    Long isdnTransId = getSequence("ISDN_TRANS_SEQ");
                    isdnTrans.setIsdnTransId(isdnTransId);
                    isdnTrans.setStockTypeId(stockTypeId);
                    isdnTrans.setTransType(Constant.ISDN_TRANS_TYPE_UPDATE_PRICE);
                    isdnTrans.setLastUpdateUser(userToken.getLoginName() + "-" + req.getRemoteHost());
                    isdnTrans.setLastUpdateIpAddress(req.getRemoteAddr());
                    isdnTrans.setLastUpdateTime(DateTimeUtils.getSysDate());
                    session.save(isdnTrans);

                    IsdnTransDetail isdnTransDetail = new IsdnTransDetail();
                    isdnTransDetail.setIsdnTransId(isdnTransId);
                    isdnTransDetail.setFromIsdn(isdn);
                    isdnTransDetail.setToIsdn(isdn);
                    isdnTransDetail.setQuantity(1L);
                    Long isdnTransDetailId = getSequence("ISDN_TRANS_DETAIL_SEQ");
                    isdnTransDetail.setIsdnTransDetailId(isdnTransDetailId);
                    isdnTransDetail.setNewValue(strPrice);
                    session.save(isdnTransDetail);
                    session.flush();

//                message = simpleDateFormat.format(DateTimeUtils.getSysdate()) + " kết thúc lưu thông tin dải số cần gán";
//                System.out.println(message);
                    //end: andv


                } else {
                    req.setAttribute("resultAssignIsdnPrice", "assignIsdnPrice.error.errorReadFile");
                    return pageForward;
                }

            }
            if (lstError.size() > 0) {
                UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
                String DATE_FORMAT = "yyyyMMddhh24mmss";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();

                String date = sdf.format(cal.getTime());
                String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
                String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

                String templatePath = "";
                //Giao dich xuat

                templatePath = tmp + "Log_assing_isdn_price.xls";
                filePath += "Log_assing_isdn_price_" + userToken.getLoginName() + "_" + date + ".xls";
                assignIsdnPriceForm.setPathLogFile(filePath);
                req.setAttribute("pathLogFile", filePath);
                String realPath = req.getSession().getServletContext().getRealPath(filePath);
                String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

                Map beans = new HashMap();
                //set ngay tao
                beans.put("dateCreate", getSysdate());
                //set nguoi tao
                beans.put("userCreate", userToken.getFullName());


                beans.put("listLogs", lstError);
                XLSTransformer transformer = new XLSTransformer();
                transformer.transformXLS(templateRealPath, beans, realPath);
                req.setAttribute("resultAssignIsdnPrice", "assignIsdnPrice.successWithSomeError");
            } else {
                req.setAttribute("resultAssignIsdnPrice", "assignIsdnPrice.success");
                return pageForward;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("resultAssignIsdnPrice", "assignIsdnPrice.errorUndefine");
            assignIsdnPriceForm.setPathLogFile("");
            getSession().clear();
            return pageForward;
        }
        log.debug("# End method AssignIsdnPriceDAO.assignIsdnPrice");
        return pageForward;
    }

    private boolean isNumber(String str) {
        try {
            Long value = Long.parseLong(str);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String pageNavigator() throws Exception {
        log.debug("# Begin method AssignGoodsDrawDAO.pageNavigator");
        pageForward = "assignGoodsDrawResult";
        return pageForward;
    }
}
