/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ReqCardNotSaleBean;
import com.viettel.im.client.bean.ReqCardNotSaleErrorBean;
import com.viettel.im.client.form.QuotasAssignedSingleForm;
import com.viettel.im.client.form.ReqCardNotSaleFrom;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.ReqCardDetailNotSale;
import com.viettel.im.database.BO.ReqCardNotSale;
import com.viettel.im.database.BO.RoleTrueChief;
import com.viettel.im.database.BO.Shop;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.StockCard;
import com.viettel.im.database.BO.UserToken;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import viettel.passport.client.RoleToken;

/**
 *
 * @author dinhdc
 */
public class ImportCardNotSaleFileDAO extends BaseDAOAction {

    private static final Logger log = Logger.getLogger(ImportQuotasUnitUnderTheFileDAO.class);
    private String pageForward;
    private final String IMP_CARD_NOT_SALE_FILE = "importCardNotSaleFile";
    private final String REQUEST_REPORT_ACCOUNT_PATH = "reportAccountPath";
    private final String REQUEST_REPORT_ACCOUNT_MESSAGE = "reportAccountMessage";
    ReqCardNotSaleFrom reqCardNotSaleFrom = new ReqCardNotSaleFrom();

    public ReqCardNotSaleFrom getReqCardNotSaleFrom() {
        return reqCardNotSaleFrom;
    }

    public void setReqCardNotSaleFrom(ReqCardNotSaleFrom reqCardNotSaleFrom) {
        this.reqCardNotSaleFrom = reqCardNotSaleFrom;
    }

    public String preparePage() throws Exception {
        log.info("Begin method preparePage");
        try {
            HttpServletRequest req = getRequest();
            UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
            viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
            if (userToken == null) {
                pageForward = Constant.ERROR_PAGE;
                return pageForward;
            }
            reqCardNotSaleFrom.setUserCodeCreate(vsaUserToken.getUserName().toUpperCase());
            pageForward = IMP_CARD_NOT_SALE_FILE;
        } catch (Exception e) {
            log.debug("load failed", e);
            return pageForward;
        }
        return pageForward;
    }

    public String importFile() throws Exception {
        log.info("Begin method importFile of ImportCardNotSaleFileDAO ...");
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Session imSession = this.getSession();
        viettel.passport.client.UserToken vsaUserToken = (viettel.passport.client.UserToken) req.getSession().getAttribute("vsaUserToken");
        Staff staff = null;
        if (vsaUserToken != null) {
            StaffDAO staffDAO = new StaffDAO();
            staff = staffDAO.findStaffAvailableByStaffCode(vsaUserToken.getUserName());
        }
        pageForward = IMP_CARD_NOT_SALE_FILE;
        String serverFileName = com.viettel.security.util.StringEscapeUtils.getSafeFileName(reqCardNotSaleFrom.getServerFileName());
        String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
        String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + serverFileName);
        File impFile = new File(serverFilePath);
        List<ReqCardNotSaleErrorBean> listError = new ArrayList<ReqCardNotSaleErrorBean>();
        List<ReqCardNotSaleBean> listcaCardNotSale = new ArrayList<ReqCardNotSaleBean>();
        List<Staff> listStaff = new ArrayList<Staff>();
        HashMap<String, Long> listReq = new HashMap<String, Long>();
        List<Object> list = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 3);
        if (list == null) {
            return pageForward;
        }
        Long count = 0L;
        Long total = 0L;
        Long reqId = 0L;
        Long reqDetailId = 0L;
        Long ownerType = 0L;
        Long ownerId = 0L;
        Long stockModelId = 0L;
        for (int i = 0; i < list.size(); i++) {
            count++;
            Object[] object = (Object[]) list.get(i);
            String reqCode = object[0].toString().trim();
            String cusName = object[1].toString().trim();
            String serial = object[2].toString().trim();
            String error = "";
            if (reqCode == null || reqCode.trim().equals("")) {
                error += ";" + getText("Error. REQ_CODE IS NOT NULL!");
            }
            if (cusName == null || cusName.trim().equals("")) {
                error += ";" + getText("Error. CUS_NAME IS NOT NULL!");
            }
            if (serial == null || serial.trim().equals("")) {
                error += ";" + getText("Error. SERIAL IS NOT NULL!");
            } else {
                StockCardDAO stockCard = new StockCardDAO();
                List<StockCard> listStockCard = new ArrayList<StockCard>();
                listStockCard = getInfoStockCardBySerial(Long.valueOf(serial));
                ownerType = listStockCard.get(0).getOwnerType();
                ownerId = listStockCard.get(0).getOwnerId();
                stockModelId = listStockCard.get(0).getStockModelId();

                Long statusSold = listStockCard.get(0).getStatus();
                // insert REQ_CARD_NOT_SALE
                ReqCardNotSale reqCardNotSaleReport = new ReqCardNotSale();
                reqId = getSequence("REQ_CARD_NOT_SALE_SEQ");
                reqCardNotSaleReport.setReqId(reqId);
                reqCardNotSaleReport.setReqCode(reqCode + "_" + reqId + "_" + "LOST");
                if (staff != null) {
                    reqCardNotSaleReport.setShopId(staff.getShopId());
                    reqCardNotSaleReport.setCreateStaffId(staff.getStaffId());
                }
                reqCardNotSaleReport.setCreateReqDate(getSysdate());
                reqCardNotSaleReport.setStatus(1L);
                reqCardNotSaleReport.setFilePath(serverFilePath);
                if (vsaUserToken != null && vsaUserToken.getUserName() != null) {
                    reqCardNotSaleReport.setStaffCode(vsaUserToken.getUserName().toUpperCase());
                }
                getSession().save(reqCardNotSaleReport);

//                if (statusSold == 0) {
                // Chi imort nhung serial voi status = 0 , nghia là da ban , con lại khong import
                importFile2(serial, ownerType, ownerId, reqId, reqCode, cusName, stockModelId);
//                }
                if (stockCard.checkSerialLost(Long.valueOf(serial))) {
                    error += ";" + getText("Error. SERIAL IS LOST!");
                    if (listStockCard.size() > 0) {
                        ownerType = listStockCard.get(0).getOwnerType();
                        ownerId = listStockCard.get(0).getOwnerId();
                        stockModelId = listStockCard.get(0).getStockModelId();
                        if (ownerType == 1) {
                            listStaff = getInfoStaff(ownerId);
                            if (listStaff == null || listStaff.size() <= 0) {
                                listStaff = getInfoStaffOfBranch(ownerId);
                                if (listStaff == null || listStaff.size() <= 0) {
                                    List<Shop> listShop = getParentShop(ownerId);
                                    if (listShop.get(0) != null && listShop.size() > 0) {
                                        listStaff = getInfoStaff(listShop.get(0).getParentShopId());
                                    }
                                }
                            }

                        } else if (ownerType == 2) {
                            listStaff = getInfoStaffWithShop(ownerId);
                            if (listStaff == null || listStaff.size() <= 0) {
                                listStaff = getInfoStaffWithShopOfBranch(ownerId);
                                if (listStaff == null || listStaff.size() <= 0) {
                                    ShopDAO shopDao = new ShopDAO();
                                    Long shopIdByStaff = shopDao.getShopIDByStaffID(ownerId);
                                    List<Shop> listShop = getParentShop(shopIdByStaff);
                                    if (listShop.get(0) != null && listShop.size() > 0) {
                                        listStaff = getInfoStaff(listShop.get(0).getParentShopId());
                                    }
                                }
                            }
                        }

                        ReqCardNotSale reqCardNotSale = new ReqCardNotSale();
                        reqId = getSequence("REQ_CARD_NOT_SALE_SEQ");
                        reqCardNotSale.setReqId(reqId);
                        reqCardNotSale.setReqCode(reqCode + "_" + reqId + "_" + "LOST");
                        if (staff != null) {
                            reqCardNotSale.setShopId(staff.getShopId());
                            reqCardNotSale.setCreateStaffId(staff.getStaffId());
                        }
                        reqCardNotSale.setCreateReqDate(getSysdate());
                        reqCardNotSale.setStatus(1L);
                        reqCardNotSale.setFilePath(serverFilePath);
                        if (vsaUserToken != null && vsaUserToken.getUserName() != null) {
                            reqCardNotSale.setStaffCode(vsaUserToken.getUserName().toUpperCase());
                        }
                        getSession().save(reqCardNotSale);

                        for (int j = 0; j < listStaff.size(); j++) {
                            ReqCardDetailNotSale reqCardDetailNotSale = new ReqCardDetailNotSale();
                            List<Shop> listShop = getParentShop(listStaff.get(j).getShopId());
                            Long parentShopId = 0L;
                            Long shopId = listStaff.get(j).getShopId();
                            if (listShop.get(0) != null && listShop.size() > 0) {
                                parentShopId = listShop.get(0).getParentShopId();
                                if (parentShopId != null && (parentShopId.equals(Constant.VT_SHOP) || shopId.equals(Constant.VT_SHOP))) {
                                    parentShopId = shopId;
                                }
                            }
                            reqDetailId = getSequence("REQ_CARD_DETAIL_NOT_SALE_SEQ");
                            reqCardDetailNotSale.setReqDetailId(reqDetailId);
                            reqCardDetailNotSale.setReqId(reqId);
                            reqCardDetailNotSale.setSerial(serial);
                            reqCardDetailNotSale.setCustName(cusName);
                            reqCardDetailNotSale.setReqNo(reqCode + "_" + reqId + "_" + "LOST");
                            reqCardDetailNotSale.setStockModelId(stockModelId);
                            reqCardDetailNotSale.setOwnerId(ownerId);
                            reqCardDetailNotSale.setOwnerType(ownerType.toString());
                            reqCardDetailNotSale.setLockStaffId(listStaff.get(j).getStaffId());
                            reqCardDetailNotSale.setShopBranchId(parentShopId);
                            reqCardDetailNotSale.setShopId(shopId);
                            reqCardDetailNotSale.setStatus(2L);
                            getSession().save(reqCardDetailNotSale);
                        }
                    }
                }
                if (checkSerialImport(getSession(), Long.valueOf(serial))) {
                    error += ";" + getText("Error. SERIAL IS Imported");
                }

                if (listStockCard.size() <= 0) {
                    error += ";" + getText("Error. SERIAL IS NOT Exist!");
                } else {
                    Long status = listStockCard.get(0).getStatus();
                    if (status == 0) {
                        error += ";" + getText("Error. SERIAL IS SOLD!");

                        ownerType = listStockCard.get(0).getOwnerType();
                        ownerId = listStockCard.get(0).getOwnerId();
                        stockModelId = listStockCard.get(0).getStockModelId();
                        if (ownerType == 1) {
                            listStaff = getInfoStaff(ownerId);
                            if (listStaff == null || listStaff.size() <= 0) {
                                listStaff = getInfoStaffOfBranch(ownerId);
                                if (listStaff == null || listStaff.size() <= 0) {
                                    List<Shop> listShop = getParentShop(ownerId);
                                    if (listShop.get(0) != null && listShop.size() > 0) {
                                        listStaff = getInfoStaff(listShop.get(0).getParentShopId());
                                    }
                                }
                            }
                        } else if (ownerType == 2) {
                            listStaff = getInfoStaffWithShop(ownerId);
                            if (listStaff == null || listStaff.size() <= 0) {
                                listStaff = getInfoStaffWithShopOfBranch(ownerId);
                                if (listStaff == null || listStaff.size() <= 0) {
                                    ShopDAO shopDao = new ShopDAO();
                                    Long shopIdByStaff = shopDao.getShopIDByStaffID(ownerId);
                                    List<Shop> listShop = getParentShop(shopIdByStaff);
                                    if (listShop.get(0) != null && listShop.size() > 0) {
                                        listStaff = getInfoStaff(listShop.get(0).getParentShopId());
                                    }
                                }
                            }
                        }
                        reqId = getSequence("REQ_CARD_NOT_SALE_SEQ");
                        ReqCardNotSale reqCardNotSale = new ReqCardNotSale();
                        reqCardNotSale.setReqId(reqId);
                        reqCardNotSale.setReqCode(reqCode + "_" + reqId + "_" + "SOLD");
                        if (staff != null) {
                            reqCardNotSale.setShopId(staff.getShopId());
                            reqCardNotSale.setCreateStaffId(staff.getStaffId());
                        }
                        reqCardNotSale.setCreateReqDate(getSysdate());
                        reqCardNotSale.setStatus(1L);
                        reqCardNotSale.setFilePath(serverFilePath);
                        if (vsaUserToken != null && vsaUserToken.getUserName() != null) {
                            reqCardNotSale.setStaffCode(vsaUserToken.getUserName().toUpperCase());
                        }
                        getSession().save(reqCardNotSale);

                        for (int j = 0; j < listStaff.size(); j++) {
                            ReqCardDetailNotSale reqCardDetailNotSale = new ReqCardDetailNotSale();
                            List<Shop> listShop = getParentShop(listStaff.get(j).getShopId());
                            Long parentShopId = 0L;
                            Long shopId = listStaff.get(j).getShopId();
                            if (listShop.get(0) != null && listShop.size() > 0) {
                                parentShopId = listShop.get(0).getParentShopId();
                                if (parentShopId != null && (parentShopId.equals(Constant.VT_SHOP) || shopId.equals(Constant.VT_SHOP))) {
                                    parentShopId = shopId;
                                }
                            }
                            reqDetailId = getSequence("REQ_CARD_DETAIL_NOT_SALE_SEQ");
                            reqCardDetailNotSale.setReqDetailId(reqDetailId);
                            reqCardDetailNotSale.setReqId(reqId);
                            reqCardDetailNotSale.setSerial(serial);
                            reqCardDetailNotSale.setCustName(cusName);
                            reqCardDetailNotSale.setReqNo(reqCode + "_" + reqId + "_" + "SOLD");
                            reqCardDetailNotSale.setStockModelId(stockModelId);
                            reqCardDetailNotSale.setOwnerId(ownerId);
                            reqCardDetailNotSale.setOwnerType(ownerType.toString());
                            reqCardDetailNotSale.setLockStaffId(listStaff.get(j).getStaffId());
                            reqCardDetailNotSale.setShopBranchId(parentShopId);
                            reqCardDetailNotSale.setShopId(shopId);
                            reqCardDetailNotSale.setStatus(4L);
                            getSession().save(reqCardDetailNotSale);
                        }
                    } else {
                        ownerType = listStockCard.get(0).getOwnerType();
                        ownerId = listStockCard.get(0).getOwnerId();
                        stockModelId = listStockCard.get(0).getStockModelId();
                        if (ownerType == 1) {
                            listStaff = getInfoStaff(ownerId);
                            if (listStaff == null || listStaff.size() <= 0) {
                                listStaff = getInfoStaffOfBranch(ownerId);
                                if (listStaff == null || listStaff.size() <= 0) {
                                    List<Shop> listShop = getParentShop(ownerId);
                                    if (listShop.get(0) != null && listShop.size() > 0) {
                                        listStaff = getInfoStaff(listShop.get(0).getParentShopId());
                                    }
                                }
                            }
                        } else if (ownerType == 2) {
                            listStaff = getInfoStaffWithShop(ownerId);
                            if (listStaff == null || listStaff.size() <= 0) {
                                listStaff = getInfoStaffWithShopOfBranch(ownerId);
                                if (listStaff == null || listStaff.size() <= 0) {
                                    ShopDAO shopDao = new ShopDAO();
                                    Long shopIdByStaff = shopDao.getShopIDByStaffID(ownerId);
                                    List<Shop> listShop = getParentShop(shopIdByStaff);
                                    if (listShop.get(0) != null && listShop.size() > 0) {
                                        listStaff = getInfoStaff(listShop.get(0).getParentShopId());
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!error.equals("")) {
                ReqCardNotSaleErrorBean errorBean = new ReqCardNotSaleErrorBean();
                errorBean.setReqCode(reqCode);
                errorBean.setCusName(cusName);
                errorBean.setSerial(serial);
                errorBean.setError(error);
                listError.add(errorBean);
            } else {
                total++;
                if (listReq.containsKey(reqCode)) {
                    reqId = listReq.get(reqCode);
                    for (int j = 0; j < listStaff.size(); j++) {
                        ReqCardDetailNotSale reqCardDetailNotSale = new ReqCardDetailNotSale();
                        List<Shop> listShop = getParentShop(listStaff.get(j).getShopId());
                        Long parentShopId = 0L;
                        Long shopId = listStaff.get(j).getShopId();
                        if (listShop.get(0) != null && listShop.size() > 0) {
                            parentShopId = listShop.get(0).getParentShopId();
                            if (parentShopId != null && parentShopId.equals(Constant.VT_SHOP) || shopId.equals(Constant.VT_SHOP)) {
                                parentShopId = shopId;
                            }
                        }
                        reqDetailId = getSequence("REQ_CARD_DETAIL_NOT_SALE_SEQ");
                        reqCardDetailNotSale.setReqDetailId(reqDetailId);
                        reqCardDetailNotSale.setReqId(reqId);
                        reqCardDetailNotSale.setSerial(serial);
                        reqCardDetailNotSale.setCustName(cusName);
                        reqCardDetailNotSale.setReqNo(reqCode + "_" + reqId);
                        reqCardDetailNotSale.setStockModelId(stockModelId);
                        reqCardDetailNotSale.setOwnerId(ownerId);
                        reqCardDetailNotSale.setOwnerType(ownerType.toString());
                        reqCardDetailNotSale.setLockStaffId(listStaff.get(j).getStaffId());
                        reqCardDetailNotSale.setShopBranchId(parentShopId);
                        reqCardDetailNotSale.setShopId(shopId);
                        reqCardDetailNotSale.setStatus(0L);
                        getSession().save(reqCardDetailNotSale);
                    }
                } else {
                    reqId = getSequence("REQ_CARD_NOT_SALE_SEQ");
                    ReqCardNotSale reqCardNotSale = new ReqCardNotSale();
                    reqCardNotSale.setReqId(reqId);
                    reqCardNotSale.setReqCode(reqCode + "_" + reqId);
                    if (staff != null) {
                        reqCardNotSale.setShopId(staff.getShopId());
                        reqCardNotSale.setCreateStaffId(staff.getStaffId());
                    }
                    reqCardNotSale.setCreateReqDate(getSysdate());
                    reqCardNotSale.setStatus(0L);
                    reqCardNotSale.setFilePath(serverFilePath);
                    if (vsaUserToken != null && vsaUserToken.getUserName() != null) {
                        reqCardNotSale.setStaffCode(vsaUserToken.getUserName().toUpperCase());
                    }

                    getSession().save(reqCardNotSale);
                    listReq.put(reqCode, reqId);

                    for (int j = 0; j < listStaff.size(); j++) {
                        ReqCardDetailNotSale reqCardDetailNotSale = new ReqCardDetailNotSale();
                        List<Shop> listShop = getParentShop(listStaff.get(j).getShopId());
                        Long parentShopId = 0L;
                        Long shopId = listStaff.get(j).getShopId();
                        if (listShop.get(0) != null && listShop.size() > 0) {
                            parentShopId = listShop.get(0).getParentShopId();
                            if (parentShopId != null && (parentShopId.equals(Constant.VT_SHOP) || shopId.equals(Constant.VT_SHOP))) {
                                parentShopId = shopId;
                            }
                        }
                        reqDetailId = getSequence("REQ_CARD_DETAIL_NOT_SALE_SEQ");
                        reqCardDetailNotSale.setReqDetailId(reqDetailId);
                        reqCardDetailNotSale.setReqId(reqId);
                        reqCardDetailNotSale.setSerial(serial);
                        reqCardDetailNotSale.setCustName(cusName);
                        reqCardDetailNotSale.setReqNo(reqCode + "_" + reqId);
                        reqCardDetailNotSale.setStockModelId(stockModelId);
                        reqCardDetailNotSale.setOwnerId(ownerId);
                        reqCardDetailNotSale.setOwnerType(ownerType.toString());
                        reqCardDetailNotSale.setLockStaffId(listStaff.get(j).getStaffId());
                        reqCardDetailNotSale.setShopBranchId(parentShopId);
                        reqCardDetailNotSale.setShopId(shopId);
                        reqCardDetailNotSale.setStatus(0L);
                        getSession().save(reqCardDetailNotSale);
                    }
                }
                //Danh sach thanh cong
                ReqCardNotSaleErrorBean errorBean = new ReqCardNotSaleErrorBean();
                errorBean.setReqCode(reqCode);
                errorBean.setCusName(cusName);
                errorBean.setSerial(serial);
                errorBean.setError("Success");
                listError.add(errorBean);
            }

        }
        //Hien thi toan bo
        downloadFile("errorImportCardNotSaleFile", listError);

        reqCardNotSaleFrom.setClientFileName(null);
        reqCardNotSaleFrom.setServerFileName(null);

        return pageForward;
    }
 //tannh20180316 ham import de lay bao cao the cao khieu bai
    public void importFile2(String serial, Long ownerType, Long ownerId, Long reqId, String reqCode,
            String cusName, Long stockModelId) throws Exception {
        List<Staff> listStaff = new ArrayList<Staff>();
        Long reqDetailId = 0L;
        Long staffId = 0L;
        Long shopId = 0L;
        //test
        List<RoleTrueChief> listRoleTrue = getTrueRoleList();
        List<Staff> listInfoRoleVsaAll = getInfoRoleVsaAll(ownerId);
        ReqCardDetailNotSale reqCardDetailNotSale = new ReqCardDetailNotSale();
        StockCardDAO stockCard = new StockCardDAO();

        //tannh20180316 luu ban ghi con lai de lay theo thong tin bao cao moi
        if (ownerType == 2) {
            listStaff = getInfoStaffByStaffId(ownerId);
            if (listStaff.get(0).getChannelTypeId() == 204068 || listStaff.get(0).getChannelTypeId() == 99
                    || listStaff.get(0).getChannelTypeId() == 14 || listStaff.get(0).getChannelTypeId() == 5
                    || listStaff.get(0).getChannelTypeId() == 3 || listStaff.get(0).getChannelTypeId() == 1000381
                    || listStaff.get(0).getChannelTypeId() == 1000382 || listStaff.get(0).getChannelTypeId() == 1000383
                    || listStaff.get(0).getChannelTypeId() == 1000384 || listStaff.get(0).getChannelTypeId() == 1000380
                    || listStaff.get(0).getChannelTypeId() == 2) {
                if (listStaff.get(0).getStaffCode() != null) {
                    reqCardDetailNotSale.setStaffCodePunish(listStaff.get(0).getStaffCode());
                    reqCardDetailNotSale.setTrueStaffCode(listStaff.get(0).getStaffCode());
                }
                if (listStaff.get(0).getName() != null) {
                    reqCardDetailNotSale.setStaffNamePunish(listStaff.get(0).getName());
                    reqCardDetailNotSale.setTrueStaffName(listStaff.get(0).getName());
                }
            }
            if (listStaff.get(0).getShopId() != null) {
                String shopCode = getParentShopCode(listStaff.get(0).getShopId()).get(0).getShopCode();
                reqCardDetailNotSale.setUnitCodePunish(shopCode);
            }
            if (listStaff.get(0).getStaffId() != null) {
                staffId = listStaff.get(0).getStaffId();
            }
            if (listStaff.get(0).getShopId() != null) {
                shopId = listStaff.get(0).getShopId();
            }
        } else if (ownerType == 1) {
            String bossStaffCodeAll = "";
            String bossStaffNameAll = "";

            String bossStaffCodeAllTrue = "";
            String bossStaffNameAllTrue = "";
            String shopCodeTrue = "";
            long chanelTypeId = 0L;
            String bossIsdnAll = "";
            String bossAddressAll = "";
            String shopCode = "";

            shopCodeTrue = getShopCodeByShopId(ownerId).get(0).getShopCode();
            chanelTypeId = getShopCodeByShopId(ownerId).get(0).getChannelTypeId();

//          2   Center Channel CE
            if (chanelTypeId == 2) {
                for (RoleTrueChief r : listRoleTrue) {
                    if (r.getShowroom() != null && r.getShowroom().equalsIgnoreCase(shopCodeTrue)
                            && r.getDescription() != null && r.getDescription().equalsIgnoreCase("CE")) {

                        if (r.getUserName() == null) {
                            bossStaffCodeAllTrue = bossStaffCodeAllTrue + " | " + "Not";
                        } else {
                            bossStaffCodeAllTrue = bossStaffCodeAllTrue + " | " + r.getUserName();
                        }
                        if (r.getName() == null) {
                            bossStaffNameAllTrue = bossStaffNameAllTrue + " | " + "Not";
                        } else {
                            bossStaffNameAllTrue = bossStaffNameAllTrue + " | " + r.getName();
                        }

                    }
                }
            }
//          3   Branch Channel BR
            if (chanelTypeId == 3) {
                for (RoleTrueChief r : listRoleTrue) {
                    if (r.getShowroom() != null && r.getShowroom().equalsIgnoreCase(shopCodeTrue)
                            && r.getDescription() != null && r.getDescription().equalsIgnoreCase("BR")) {
                        if (r.getUserName() == null) {
                            bossStaffCodeAllTrue = bossStaffCodeAllTrue + " | " + "Not";
                        } else {
                            bossStaffCodeAllTrue = bossStaffCodeAllTrue + " | " + r.getUserName();
                        }
                        if (r.getName() == null) {
                            bossStaffNameAllTrue = bossStaffNameAllTrue + " | " + "Not";
                        } else {
                            bossStaffNameAllTrue = bossStaffNameAllTrue + " | " + r.getName();
                        }

                    }
                }
            }
//          5   Showroom Channel SH
            if (chanelTypeId == 5) {
                for (RoleTrueChief r : listRoleTrue) {
                    if (r.getShowroom() != null && r.getShowroom().equalsIgnoreCase(shopCodeTrue)
                            && r.getDescription() != null && r.getDescription().equalsIgnoreCase("SH")) {
                        if (r.getUserName() == null) {
                            bossStaffCodeAllTrue = bossStaffCodeAllTrue + " | " + "Not";
                        } else {
                            bossStaffCodeAllTrue = bossStaffCodeAllTrue + " | " + r.getUserName();
                        }
                        if (r.getName() == null) {
                            bossStaffNameAllTrue = bossStaffNameAllTrue + " | " + "Not";
                        } else {
                            bossStaffNameAllTrue = bossStaffNameAllTrue + " | " + r.getName();
                        }

                    }
                }
            }

            if (listInfoRoleVsaAll != null && listInfoRoleVsaAll.size() > 0) {

                for (int l = 0; l < listInfoRoleVsaAll.size(); l++) {
                    Staff s = listInfoRoleVsaAll.get(l);
                    if (s.getStaffCode() == null) {
                        bossStaffCodeAll = bossStaffCodeAll + " | " + "Not";
                    } else {
                        bossStaffCodeAll = bossStaffCodeAll + " | " + s.getStaffCode();
                    }

                    if (s.getName() == null) {
                        bossStaffNameAll = bossStaffNameAll + " | " + "Not";
                    } else {
                        bossStaffNameAll = bossStaffNameAll + " | " + s.getName();
                    }

                    if (s.getIsdn() == null) {
                        bossIsdnAll = bossIsdnAll + " | " + "Not";
                    } else {
                        bossIsdnAll = bossIsdnAll + " | " + s.getIsdn();
                    }

                    if (s.getAddress() == null) {
                        bossAddressAll = bossAddressAll + " | " + "Not";
                    } else {
                        bossAddressAll = bossAddressAll + " | " + s.getAddress();
                    }
                    if (s.getStaffId() != null) {
                        staffId = s.getStaffId();
                    }
                    if (s.getShopId() != null) {
                        shopId = s.getShopId();
                        shopCode = getParentShopCode(shopId).get(0).getShopCode();
                    }
                }
            }

//            if (listStaff != null && listStaff.size() > 0 && listStaff.get(0).getStaffCode() != null) {
            reqCardDetailNotSale.setStaffCodePunish(bossStaffCodeAll);
//            }
//            if (listStaff != null && listStaff.size() > 0 && listStaff.get(0).getName() != null) {
            reqCardDetailNotSale.setStaffNamePunish(bossStaffNameAll);
//            }
//            if (listStaff != null && listStaff.size() > 0 && listStaff.get(0).getAddress() != null) {
            reqCardDetailNotSale.setUnitCodePunish(shopCode);
//            }
            reqCardDetailNotSale.setTrueStaffCode(bossStaffCodeAllTrue);
            reqCardDetailNotSale.setTrueStaffName(bossStaffNameAllTrue);
        }

        reqDetailId = getSequence("REQ_CARD_DETAIL_NOT_SALE_SEQ");
        reqCardDetailNotSale.setReqDetailId(reqDetailId);
        reqCardDetailNotSale.setReqId(reqId);
        reqCardDetailNotSale.setSerial(serial);
        reqCardDetailNotSale.setCustName(cusName);
        reqCardDetailNotSale.setReqNo(reqCode + "_" + reqId);
        reqCardDetailNotSale.setStockModelId(stockModelId);
        reqCardDetailNotSale.setOwnerId(ownerId);
        reqCardDetailNotSale.setOwnerType(ownerType.toString());
        reqCardDetailNotSale.setLockStaffId(staffId);
        reqCardDetailNotSale.setShopBranchId(0L);
        reqCardDetailNotSale.setShopId(shopId);
        if (stockCard.checkSerialLost(Long.valueOf(serial))) {
            reqCardDetailNotSale.setStatus(2L);
        } else {
            reqCardDetailNotSale.setStatus(3L);
        }


        // lay thong tin cua cap tren 
        List<Shop> listBoss = null;
        if (staffId != null && ownerType == 1) {
            listBoss = getParentShopId(staffId);
        }
        // tannh
//        long trueBossShopId = 0L;
//        if (listBoss != null && listBoss.size() > 0 && listBoss.get(0).getParentShopId() != null
//                && listBoss.get(0).getParentShopId() != 7282) {
//
//            trueBossShopId = listBoss.get(0).getParentShopId();
//
//        }
        long chanelTypeIdBoss = 0L;
        long ownerTypeBoss = 0L;
        if (ownerType == 2) {
            // nhan vien (de)
            List<Shop> listBossTrue = null;
            listBossTrue = getParentShopId(ownerId);
            if (listBossTrue != null && listBossTrue.size() > 0 && listBossTrue.get(0).getParentShopId() != null
                    && listBossTrue.get(0).getParentShopId() != 7282) {
                ownerTypeBoss = listBossTrue.get(0).getShopId();
                chanelTypeIdBoss = listBossTrue.get(0).getChannelTypeId();
            }
        } else if (ownerType == 1) {
            // shop (kho)
            List<Shop> listBossTrue = null;
            if (ownerId != null && ownerId != 7282) {
                listBossTrue = getShopCodeByShopId(ownerId);
                ownerTypeBoss = listBossTrue.get(0).getParentShopId();
                chanelTypeIdBoss = listBossTrue.get(0).getChannelTypeId();

            }
        }

        //tannh test tiep
        String shopCodeBossTrue = "";
        String bossStaffCodeAllTruePlus = "";
        String bossStaffNameAllTruePlus = "";
        String bossISDNAllTruePlus = "";
        long chanelTypeIdTrue = 0L;

        shopCodeBossTrue = getShopCodeByShopId(ownerTypeBoss).get(0).getShopCode();

        chanelTypeIdTrue = getShopCodeByShopId(ownerTypeBoss).get(0).getChannelTypeId();

        //          2   Center Channel CE
        if (chanelTypeIdTrue == 2) {
            for (RoleTrueChief r : listRoleTrue) {
                if (r.getShowroom() != null && r.getShowroom().equalsIgnoreCase(shopCodeBossTrue)
                        && r.getDescription() != null && r.getDescription().equalsIgnoreCase("CE")) {

                    if (r.getUserName() == null) {
                        bossStaffCodeAllTruePlus = bossStaffCodeAllTruePlus + " | " + "Not";
                    } else {
                        bossStaffCodeAllTruePlus = bossStaffCodeAllTruePlus + " | " + r.getUserName();
                    }
                    if (r.getName() == null) {
                        bossStaffNameAllTruePlus = bossStaffNameAllTruePlus + " | " + "Not";
                    } else {
                        bossStaffNameAllTruePlus = bossStaffNameAllTruePlus + " | " + r.getName();
                    }
                    if (r.getIsdn() == null) {
                        bossISDNAllTruePlus = bossISDNAllTruePlus + " | " + "Not";
                    } else {
                        bossISDNAllTruePlus = bossISDNAllTruePlus + " | " + r.getIsdn();
                    }

                }
            }
        }
//          3   Branch Channel BR
        if (chanelTypeIdTrue == 3) {
            for (RoleTrueChief r : listRoleTrue) {
                if (r.getShowroom() != null && r.getShowroom().equalsIgnoreCase(shopCodeBossTrue)
                        && r.getDescription() != null && r.getDescription().equalsIgnoreCase("BR")) {
                    if (r.getUserName() == null) {
                        bossStaffCodeAllTruePlus = bossStaffCodeAllTruePlus + " | " + "Not";
                    } else {
                        bossStaffCodeAllTruePlus = bossStaffCodeAllTruePlus + " | " + r.getUserName();
                    }
                    if (r.getName() == null) {
                        bossStaffNameAllTruePlus = bossStaffNameAllTruePlus + " | " + "Not";
                    } else {
                        bossStaffNameAllTruePlus = bossStaffNameAllTruePlus + " | " + r.getName();
                    }
                    if (r.getIsdn() == null) {
                        bossISDNAllTruePlus = bossISDNAllTruePlus + " | " + "Not";
                    } else {
                        bossISDNAllTruePlus = bossISDNAllTruePlus + " | " + r.getIsdn();
                    }

                }
            }
        }
//          5   Showroom Channel SH
        if (chanelTypeIdTrue == 5) {
            for (RoleTrueChief r : listRoleTrue) {
                if (r.getShowroom() != null && r.getShowroom().equalsIgnoreCase(shopCodeBossTrue)
                        && r.getDescription() != null && r.getDescription().equalsIgnoreCase("SH")) {
                    if (r.getUserName() == null) {
                        bossStaffCodeAllTruePlus = bossStaffCodeAllTruePlus + " | " + "Not";
                    } else {
                        bossStaffCodeAllTruePlus = bossStaffCodeAllTruePlus + " | " + r.getUserName();
                    }
                    if (r.getName() == null) {
                        bossStaffNameAllTruePlus = bossStaffNameAllTruePlus + " | " + "Not";
                    } else {
                        bossStaffNameAllTruePlus = bossStaffNameAllTruePlus + " | " + r.getName();
                    }
                    if (r.getIsdn() == null) {
                        bossISDNAllTruePlus = bossISDNAllTruePlus + " | " + "Not";
                    } else {
                        bossISDNAllTruePlus = bossISDNAllTruePlus + " | " + r.getIsdn();
                    }

                }
            }
        }
        reqCardDetailNotSale.setTrueUnitChiefCode(bossStaffCodeAllTruePlus);
        reqCardDetailNotSale.setTrueUnitChiefName(bossStaffNameAllTruePlus);
        reqCardDetailNotSale.setTrueUnitChiefPhone(bossISDNAllTruePlus);


        //end
        if (listBoss != null && listBoss.size() > 0 && listBoss.get(0).getParentShopId() != null
                && listBoss.get(0).getParentShopId() != 7282) {
            listInfoRoleVsaAll = getInfoRoleVsaAll(listBoss.get(0).getParentShopId());
        } else if (ownerType == 2) {
            ShopDAO shopDao = new ShopDAO();
            Long shopIdByStaff = shopDao.getShopIDByStaffID(ownerId);
            listInfoRoleVsaAll = getInfoRoleVsaAll(shopIdByStaff);
        } else {
            listInfoRoleVsaAll = new ArrayList<Staff>();
        }

        String bossStaffCodeAll = "";
        String bossStaffNameAll = "";
        String bossIsdnAll = "";
        String bossAddressAll = "";

        if (listInfoRoleVsaAll != null && listInfoRoleVsaAll.size() > 0) {
            for (int l = 0; l < listInfoRoleVsaAll.size(); l++) {
                Staff s = listInfoRoleVsaAll.get(l);
                if (s.getStaffCode() == null) {
                    bossStaffCodeAll = bossStaffCodeAll + " | " + "Not";
                } else {
                    bossStaffCodeAll = bossStaffCodeAll + " | " + s.getStaffCode();
                }

                if (s.getName() == null) {
                    bossStaffNameAll = bossStaffNameAll + " | " + "Not";
                } else {
                    bossStaffNameAll = bossStaffNameAll + " | " + s.getName();
                }

                if (s.getIsdn() == null) {
                    bossIsdnAll = bossIsdnAll + " | " + "Not";
                } else {
                    bossIsdnAll = bossIsdnAll + " | " + s.getIsdn();
                }

                if (s.getAddress() == null) {
                    bossAddressAll = bossAddressAll + " | " + "Not";
                } else {
                    bossAddressAll = bossAddressAll + " | " + s.getAddress();
                }
            }
        }

        if (listInfoRoleVsaAll == null || listInfoRoleVsaAll.size() <= 0) {
            reqCardDetailNotSale.setUnitChiefCodePunish("Not");
            reqCardDetailNotSale.setUnitChiefNamePunish("Not");
            reqCardDetailNotSale.setUnitChiefPhonePunish("Not");

        } else {
            reqCardDetailNotSale.setUnitChiefCodePunish(bossStaffCodeAll);
            reqCardDetailNotSale.setUnitChiefNamePunish(bossStaffNameAll);
            reqCardDetailNotSale.setUnitChiefPhonePunish(bossIsdnAll);

        }
        String staffSale = "";
        if (getSaleInfoStaffByStaffSerial(Long.valueOf(serial)) != null
                && getSaleInfoStaffByStaffSerial(Long.valueOf(serial)).size() > 0
                && getSaleInfoStaffByStaffSerial(Long.valueOf(serial)).get(0).getStaffCode() != null) {
            staffSale = getSaleInfoStaffByStaffSerial(Long.valueOf(serial)).get(0).getStaffCode();
        }
        reqCardDetailNotSale.setSaleStaffCodePunish(staffSale);
        if (listStaff != null && listStaff.size() > 0
                && (listStaff.get(0).getChannelTypeId() != 204068 && listStaff.get(0).getChannelTypeId() != 99
                && listStaff.get(0).getChannelTypeId() != 14 && listStaff.get(0).getChannelTypeId() != 5
                && listStaff.get(0).getChannelTypeId() != 3 && listStaff.get(0).getChannelTypeId() != 1000381
                && listStaff.get(0).getChannelTypeId() != 1000382 && listStaff.get(0).getChannelTypeId() != 1000383
                && listStaff.get(0).getChannelTypeId() != 1000384 && listStaff.get(0).getChannelTypeId() != 1000380
                && listStaff.get(0).getChannelTypeId() != 2)) {
            if (listStaff.get(0).getStaffCode() != null) {
                reqCardDetailNotSale.setStaffCodePunish(listStaff.get(0).getStaffCode());
                reqCardDetailNotSale.setTrueStaffCode(staffSale);
            }
            if (listStaff.get(0).getName() != null) {
                reqCardDetailNotSale.setStaffNamePunish(listStaff.get(0).getName());
                List<Staff> listStaffa = getInfoStaffByStaffCode(staffSale);
                if (listStaffa != null && listStaffa.size() > 0) {
                    reqCardDetailNotSale.setTrueStaffName(listStaffa.get(0).getName());
                } else if (reqCardDetailNotSale.getTrueStaffCode() == null || "".equalsIgnoreCase(reqCardDetailNotSale.getTrueStaffCode()) || reqCardDetailNotSale.getTrueStaffCode().length() <= 0) {
                    reqCardDetailNotSale.setTrueStaffName("");
                }
            }
        }
        getSession().save(reqCardDetailNotSale);

    }

    public String checkValidate(QuotasAssignedSingleForm tmp) {
        try {
            String returnMsg = "";
//            if (tmp.getImportType() == null) {
//                returnMsg = "ERR.TT.24"; //Lỗi, bạn chưa chọn loại import!
//                return returnMsg;
//            }
            if (tmp.getServerFileName() == null || tmp.getServerFileName().trim().equals("")) {
                returnMsg = "ERR.GOD.067"; //Lỗi, bạn chưa nhap file dau vao!
                return returnMsg;
            }
            return returnMsg;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private ReqCardNotSaleFrom getDataListfromFile(ReqCardNotSaleFrom tmp) {
        reqCardNotSaleFrom = tmp;
        try {
        } catch (Exception ex) {
            String str = CommonDAO.readStackTrace(ex);
            log.info(str);
        }
        return reqCardNotSaleFrom;
    }

    public List getInfoStaff(Long owner_id) {

        List<Staff> listStaff = new ArrayList<Staff>();
        Session imSession = this.getSession();
        try {
            String queryString = " select st.staff_id as staffId, st.shop_id as shopId from staff st WHERE shop_id = ?"
                    + " AND EXISTS (SELECT 'x' FROM vsa_v3.users a"
                    + " WHERE Status = 1 AND a.user_name = LOWER (st.staff_code) AND EXISTS"
                    + " (SELECT 'x' FROM vsa_v3.role_user WHERE user_id = a.user_id"
                    + " AND role_id  IN (SELECT role_id FROM vsa_v3.roles WHERE Role_Code IN ('CE_DIRECTOR_IM','CE_NEW_DIRECTOR_IM'))))";

            Query queryObject = imSession.createSQLQuery(queryString).addScalar("staffId", Hibernate.LONG)
                    .addScalar("shopId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, owner_id);
            listStaff = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff;
    }

    public List getInfoStaffWithShop(Long owner_id) {

        List<Staff> listStaff = new ArrayList<Staff>();

        try {
            String queryString = " select st.staff_id as staffId, st.shop_id as shopId from staff st WHERE shop_id IN (SELECT shop_id FROM Staff WHERE staff_id = ?) "
                    + " AND EXISTS (SELECT 'x' FROM vsa_v3.users a "
                    + " WHERE Status = 1 AND a.user_name = LOWER (st.staff_code) AND EXISTS "
                    + " (SELECT 'x' FROM vsa_v3.role_user WHERE user_id = a.user_id "
                    + " AND role_id  IN (SELECT role_id FROM vsa_v3.roles WHERE Role_Code IN ('CE_DIRECTOR_IM','CE_NEW_DIRECTOR_IM'))))";

            Query queryObject = getSession().createSQLQuery(queryString).addScalar("staffId", Hibernate.LONG)
                    .addScalar("shopId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, owner_id);
            listStaff = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff;
    }

    //download danh sach file loi ve
    public void downloadFile(String templatePathResource, List listReport) throws Exception {
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        String DATE_FORMAT = "yyyyMMddHHmmss";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE_2");
        filePath = filePath != null ? filePath : "/";
        filePath += templatePathResource + "_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls";
        //String realPath = req.getSession().getServletContext().getRealPath(filePath);
        String realPath = filePath;
        String templatePath = ResourceBundleUtils.getResource(templatePathResource);
        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
        Map beans = new HashMap();
        beans.put("listReport", listReport);
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(realTemplatePath, beans, realPath);
        DownloadDAO downloadDAO = new DownloadDAO();
        req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, "download.do?" + downloadDAO.getFileNameEncNew(realPath, req.getSession()));
        //req.setAttribute(REQUEST_REPORT_ACCOUNT_PATH, filePath);
//        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "Nếu hệ thống không tự download. Click vào đây để tải File lưu thông tin không cập nhật được");
        req.setAttribute(REQUEST_REPORT_ACCOUNT_MESSAGE, "ERR.CHL.102");

    }

    public List getInfoStockCardBySerial(Long serial) {

        List<StockCard> listStockCard = new ArrayList<StockCard>();

        try {
            String queryString = " SELECT Owner_Type AS ownerType, Owner_Id AS ownerId, Stock_Model_Id AS stockModelId, Status AS status FROM Stock_Card WHERE serial = ? ";

            Query queryObject = getSession().createSQLQuery(queryString)
                    .addScalar("ownerType", Hibernate.LONG)
                    .addScalar("ownerId", Hibernate.LONG)
                    .addScalar("stockModelId", Hibernate.LONG)
                    .addScalar("status", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(StockCard.class));
            queryObject.setParameter(0, serial);
            listStockCard = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStockCard;
    }

    public boolean checkSerialImport(Session session, Long serial) throws Exception {

        String strQuery = " SELECT count(*) countNumber FROM   REQ_CARD_DETAIL_NOT_SALE WHERE  serial = ? AND status != 3 ";
        SQLQuery query = session.createSQLQuery(strQuery).addScalar("countNumber", Hibernate.LONG);
        query.setParameter(0, serial);
        Long count = (Long) query.list().get(0);
        if (count <= 0) {
            return false;
        }
        return true;
    }

    public List getParentShop(Long shopId) {

        List<Shop> listShop = new ArrayList<Shop>();

        try {
            String queryString = " SELECT Shop_Id AS shopId, parent_shop_id AS parentShopId FROM Shop WHERE Shop_Id = ? AND Rownum <= 1 ";

            Query queryObject = getSession().createSQLQuery(queryString)
                    .addScalar("shopId", Hibernate.LONG)
                    .addScalar("parentShopId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(Shop.class));
            queryObject.setParameter(0, shopId);
            listShop = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listShop;
    }

    public List<Shop> getParentShopId(Long staffId) {

        List<Shop> listShop = new ArrayList<Shop>();

        try {
            String queryString = " SELECT Shop_Id AS shopId, parent_shop_id AS parentShopId ,channel_type_id as channelTypeId FROM shop where shop_id = (select shop_id from staff where staff_id = ? AND Rownum <= 1 ) ";
            Query queryObject = getSession().createSQLQuery(queryString)
                    .addScalar("shopId", Hibernate.LONG)
                    .addScalar("parentShopId", Hibernate.LONG)
                    .addScalar("channelTypeId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(Shop.class));
            queryObject.setParameter(0, staffId);
            listShop = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listShop;
    }

    public List getInfoStaffOfBranch(Long owner_id) {

        List<Staff> listStaff = new ArrayList<Staff>();
        Session imSession = this.getSession();
        try {
            String queryString = " select st.staff_id as staffId, st.shop_id as shopId from staff st WHERE shop_id = ?"
                    + " AND EXISTS (SELECT 'x' FROM vsa_v3.users a"
                    + " WHERE Status = 1 AND a.user_name = LOWER (st.staff_code) AND EXISTS"
                    + " (SELECT 'x' FROM vsa_v3.role_user WHERE user_id = a.user_id"
                    + " AND role_id  IN (SELECT role_id FROM vsa_v3.roles WHERE Role_Code IN ('BR_DIRECTOR_IM'))))";

            Query queryObject = imSession.createSQLQuery(queryString).addScalar("staffId", Hibernate.LONG)
                    .addScalar("shopId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, owner_id);
            listStaff = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff;
    }

    public List getInfoStaffWithShopOfBranch(Long owner_id) {

        List<Staff> listStaff = new ArrayList<Staff>();

        try {
            String queryString = " select st.staff_id as staffId, st.shop_id as shopId from staff st WHERE shop_id IN (SELECT shop_id FROM Staff WHERE staff_id = ?) "
                    + " AND EXISTS (SELECT 'x' FROM vsa_v3.users a "
                    + " WHERE Status = 1 AND a.user_name = LOWER (st.staff_code) AND EXISTS "
                    + " (SELECT 'x' FROM vsa_v3.role_user WHERE user_id = a.user_id "
                    + " AND role_id  IN (SELECT role_id FROM vsa_v3.roles WHERE Role_Code IN ('BR_DIRECTOR_IM'))))";

            Query queryObject = getSession().createSQLQuery(queryString).addScalar("staffId", Hibernate.LONG)
                    .addScalar("shopId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, owner_id);
            listStaff = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff;
    }

    public List<Shop> getInfoShopByShopID(Long shopID) {

        List<Shop> list = new ArrayList<Shop>();

        try {
            String queryString = "SELECT  shop_code as shopCode FROM shop WHERE shop_id = ?";
            Query queryObject = getSession().createSQLQuery(queryString)
                    .addScalar("staffCode", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(Shop.class));
            queryObject.setParameter(0, shopID);
            list = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return list;
    }

    public List<Shop> getParentShopCode(Long shopId) {

        List<Shop> listShop = new ArrayList<Shop>();

        try {
            String queryString = " SELECT Shop_code AS shopCode FROM Shop WHERE Shop_Id = ? AND Rownum <= 1 ";

            Query queryObject = getSession().createSQLQuery(queryString)
                    .addScalar("shopCode", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(Shop.class));
            queryObject.setParameter(0, shopId);
            listShop = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listShop;
    }

    public List<Shop> getShopCodeByShopId(Long shopId) {

        List<Shop> listShop = new ArrayList<Shop>();

        try {
            String queryString = " SELECT Shop_code AS shopCode ,channel_type_id as channelTypeId ,parent_shop_id AS parentShopId  FROM Shop WHERE Shop_Id = ? AND Rownum <= 1 ";

            Query queryObject = getSession().createSQLQuery(queryString)
                    .addScalar("shopCode", Hibernate.STRING)
                    .addScalar("channelTypeId", Hibernate.LONG)
                    .addScalar("parentShopId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(Shop.class));
            queryObject.setParameter(0, shopId);
            listShop = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listShop;
    }

    public List<Staff> getInfoStaffByStaffId(Long owner_id) {

        List<Staff> listStaff = new ArrayList<Staff>();

        try {
            String queryString = "SELECT staff_code as staffCode , name as name, address as address  ,shop_id as shopId, staff_id as staffId, channel_type_id as channelTypeId   FROM staff WHERE staff_id = ?";
            Query queryObject = getSession().createSQLQuery(queryString).addScalar("staffCode", Hibernate.STRING)
                    .addScalar("shopId", Hibernate.LONG)
                    .addScalar("staffId", Hibernate.LONG)
                    .addScalar("name", Hibernate.STRING)
                    .addScalar("address", Hibernate.STRING)
                    .addScalar("channelTypeId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, owner_id);
            listStaff = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff;
    }

//    
    public List<RoleTrueChief> getTrueRoleList() {

        List<RoleTrueChief> list = new ArrayList<RoleTrueChief>();

        try {
            String queryString = "select user_name as userName, isdn as isdn , showroom as showroom ,name as name, note as note, general as general ,plus as plus , description as description  from Role_True_Chief_cc";
            Query queryObject = getSession().createSQLQuery(queryString)
                    .addScalar("userName", Hibernate.STRING)
                    .addScalar("isdn", Hibernate.STRING)
                    .addScalar("showroom", Hibernate.STRING)
                    .addScalar("name", Hibernate.STRING)
                    .addScalar("note", Hibernate.STRING)
                    .addScalar("general", Hibernate.STRING)
                    .addScalar("plus", Hibernate.STRING)
                    .addScalar("description", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(RoleTrueChief.class));
            list = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return list;
    }

    public List<Staff> getSaleInfoStaffByStaffSerial(Long serial) {

        List<Staff> listStaff = new ArrayList<Staff>();

        try {
            String queryString =
                    "select staff_code as staffCode from staff where  staff_id = "
                    + " (select staff_id from sale_trans where sale_trans_id = "
                    + " (select sale_trans_id from  sale_trans_detail  where sale_trans_detail_id = "
                    + " (select sale_trans_detail_id from sale_trans_serial where "
                    + " from_serial <= ? and to_serial >=  ?  ) ))";
            Query queryObject = getSession().createSQLQuery(queryString).addScalar("staffCode", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, String.valueOf(serial));
            queryObject.setParameter(1, String.valueOf(serial));
            listStaff = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff;
    }

    public List<Staff> getInfoStaffByStaffCode(String code) {

        List<Staff> listStaff = new ArrayList<Staff>();

        try {
            String queryString = "SELECT staff_code as staffCode ,shop_id as shopId , name as name, address as address  , staff_id as staffId FROM staff WHERE staff_code = ?";
            Query queryObject = getSession().createSQLQuery(queryString).addScalar("staffCode", Hibernate.STRING)
                    .addScalar("shopId", Hibernate.LONG)
                    .addScalar("staffId", Hibernate.LONG)
                    .addScalar("name", Hibernate.STRING)
                    .addScalar("address", Hibernate.STRING)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, code);
            listStaff = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff;
    }

//          reqCardDetailNotSale.setStaffNamePunish(listStaff.get(j).getName());
//          reqCardDetailNotSale.setUnitCodePunish(listStaff.get(j).getAddress());
    public List getUsernameSuperiorShowroom(Long ownerId) throws Exception {
        List<Staff> listStaff = new ArrayList<Staff>();
        List listStaff1;
        try {
            String queryString = "select user_name ,cellphone ,full_name  from vsa_v3.role_user a,  vsa_v3.users b where a.is_active = 1 "
                    + " and a.role_id in (select role_id from vsa_v3.roles where role_code in ('SH_MANAGER')) "
                    + " and  a.user_id in (select user_id from vsa_v3.users where UPPER(user_name) in "
                    + " (select staff_code from staff where shop_id = ? and status = 1) )  and a.user_id = b.user_id ";
            Query queryObject = getSession().createSQLQuery(queryString);
            queryObject.setParameter(0, ownerId);
            listStaff1 = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff1;
    }

    public List getUsernameSuperiorBranch(Long ownerId) throws Exception {
        List<Staff> o = new ArrayList<Staff>();
        List listStaff1;
        try {
            String queryString = "select user_name ,cellphone ,full_name  from vsa_v3.role_user a,  vsa_v3.users b where a.is_active = 1 "
                    + " and a.role_id in (select role_id from vsa_v3.roles where role_code in ('BR_DIRECTOR')) "
                    + " and  a.user_id in (select user_id from vsa_v3.users where UPPER(user_name) in "
                    + " (select staff_code from staff where shop_id = ? and status = 1) )  and a.user_id = b.user_id ";
            Query queryObject = getSession().createSQLQuery(queryString);
            queryObject.setParameter(0, ownerId);
            listStaff1 = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff1;
    }

    public List getUsernameSuperiorBusiness(Long ownerId) throws Exception {
        List<Staff> listStaff = new ArrayList<Staff>();
        List listStaff1;
        try {
            String queryString = "select user_name ,cellphone ,full_name  from vsa_v3.role_user a,  vsa_v3.users b where a.is_active = 1 "
                    + " and a.role_id in (select role_id from vsa_v3.roles where role_code in ('CE_DIRECTOR')) "
                    + " and  a.user_id in (select user_id from vsa_v3.users where UPPER(user_name) in "
                    + " (select staff_code from staff where shop_id = ? and status = 1) )  and a.user_id = b.user_id ";
            Query queryObject = getSession().createSQLQuery(queryString);
            queryObject.setParameter(0, ownerId);
            listStaff1 = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff1;
    }

    public List<Staff> getInfoRoleVsaAll(Long ownerId) throws Exception {
        List<Staff> listStaff = new ArrayList<Staff>();
        try {
            String queryString = "select staff_code as staffCode ,name as name, isdn as isdn, address as address, staff_id as staffId , shop_id as shopId from staff where LOWER(staff_code) in "
                    + " (select user_name  from vsa_v3.role_user a,  vsa_v3.users b where a.is_active = 1 "
                    + " and a.role_id in (select role_id from vsa_v3.roles where role_code in "
                    + " ('SH_MANAGER' , 'BR_DIRECTOR', 'CE_DIRECTOR')) and  a.user_id in (select user_id from "
                    + " vsa_v3.users where UPPER(user_name) in (select staff_code from staff where shop_id = ? and status = 1) ) "
                    + " and a.user_id = b.user_id) ";

            Query queryObject = getSession().createSQLQuery(queryString).addScalar("staffCode", Hibernate.STRING)
                    .addScalar("name", Hibernate.STRING)
                    .addScalar("isdn", Hibernate.STRING)
                    .addScalar("address", Hibernate.STRING)
                    .addScalar("staffId", Hibernate.LONG)
                    .addScalar("shopId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, ownerId);
            listStaff = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff;
    }

    public List<Staff> getInfoRoleVsaSH(Long ownerId) throws Exception {
        List<Staff> listStaff = new ArrayList<Staff>();
        try {
            String queryString = "select staff_code as staffCode ,name as name, isdn as isdn, address as address, staff_id as staffId , shop_id as shopId from staff where LOWER(staff_code) in "
                    + " (select user_name  from vsa_v3.role_user a,  vsa_v3.users b where a.is_active = 1 "
                    + " and a.role_id in (select role_id from vsa_v3.roles where role_code in "
                    + " ('SH_MANAGER')) and  a.user_id in (select user_id from "
                    + " vsa_v3.users where UPPER(user_name) in (select staff_code from staff where shop_id = ? and status = 1) ) "
                    + " and a.user_id = b.user_id) ";

            Query queryObject = getSession().createSQLQuery(queryString).addScalar("staffCode", Hibernate.STRING)
                    .addScalar("name", Hibernate.STRING)
                    .addScalar("isdn", Hibernate.STRING)
                    .addScalar("address", Hibernate.STRING)
                    .addScalar("staffId", Hibernate.LONG)
                    .addScalar("shopId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, ownerId);
            listStaff = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff;
    }

    public List<Staff> getInfoRoleVsaCE(Long ownerId) throws Exception {
        List<Staff> listStaff = new ArrayList<Staff>();
        try {
            String queryString = "select staff_code as staffCode ,name as name, isdn as isdn, address as address, staff_id as staffId , shop_id as shopId from staff where LOWER(staff_code) in "
                    + " (select user_name  from vsa_v3.role_user a,  vsa_v3.users b where a.is_active = 1 "
                    + " and a.role_id in (select role_id from vsa_v3.roles where role_code in "
                    + " ('CE_DIRECTOR')) and  a.user_id in (select user_id from "
                    + " vsa_v3.users where UPPER(user_name) in (select staff_code from staff where shop_id = ? and status = 1) ) "
                    + " and a.user_id = b.user_id) ";

            Query queryObject = getSession().createSQLQuery(queryString).addScalar("staffCode", Hibernate.STRING)
                    .addScalar("name", Hibernate.STRING)
                    .addScalar("isdn", Hibernate.STRING)
                    .addScalar("address", Hibernate.STRING)
                    .addScalar("staffId", Hibernate.LONG)
                    .addScalar("shopId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, ownerId);
            listStaff = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff;
    }

    public List<Staff> getInfoRoleVsaBR(Long ownerId) throws Exception {
        List<Staff> listStaff = new ArrayList<Staff>();
        try {
            String queryString = "select staff_code as staffCode ,name as name, isdn as isdn, address as address, staff_id as staffId , shop_id as shopId from staff where LOWER(staff_code) in "
                    + " (select user_name  from vsa_v3.role_user a,  vsa_v3.users b where a.is_active = 1 "
                    + " and a.role_id in (select role_id from vsa_v3.roles where role_code in "
                    + " ('BR_DIRECTOR')) and  a.user_id in (select user_id from "
                    + " vsa_v3.users where UPPER(user_name) in (select staff_code from staff where shop_id = ? and status = 1) ) "
                    + " and a.user_id = b.user_id) ";

            Query queryObject = getSession().createSQLQuery(queryString).addScalar("staffCode", Hibernate.STRING)
                    .addScalar("name", Hibernate.STRING)
                    .addScalar("isdn", Hibernate.STRING)
                    .addScalar("address", Hibernate.STRING)
                    .addScalar("staffId", Hibernate.LONG)
                    .addScalar("shopId", Hibernate.LONG)
                    .setResultTransformer(Transformers.aliasToBean(Staff.class));
            queryObject.setParameter(0, ownerId);
            listStaff = queryObject.list();

        } catch (RuntimeException re) {
            log.error("find all failed", re);
            throw re;
        }

        return listStaff;
    }
}
