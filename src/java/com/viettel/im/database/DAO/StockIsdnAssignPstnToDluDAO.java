package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.PstnDistrictCodeViewHelper;
import com.viettel.im.client.form.ImportPSTNForm;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.im.database.BO.Dslam;
import com.viettel.im.database.BO.StockIsdnPstn;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.Area;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import net.sf.jxls.transformer.XLSTransformer;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.database.BO.Shop;

/**
 *
 * @author TungTV
 */
public class StockIsdnAssignPstnToDluDAO extends BaseDAOAction{

    private static final Logger log = Logger.getLogger(StockIsdnAssignPstnToDluDAO.class);
    //dinh nghia cac hang so ten bien request, session
    private final String REQUEST_MESSAGE = "message"; //
    private final String REQUEST_MESSAGE_PARAM = "messageParam"; //
    private final String REQUEST_MESSAGE_LIST = "messageList"; //
    private final String REQUEST_LIST_LOCATIONS = "listLocations"; //
    private final String REQUEST_LIST_DLUS = "listDlus"; //
    private final String SESSION_LIST_STOCK_ISDN_PSTN = "importPstnList"; //
    //
    private final Long MAX_RESULT = 1000L; //so ban ghi toi da trong 1 lan truy van
    //dinh nghia bien form
    private ImportPSTNForm form = new ImportPSTNForm();

    public ImportPSTNForm getForm() {
        return form;
    }

    public void setForm(ImportPSTNForm form) {
        this.form = form;
    }

    private boolean isNumber(String str) {
        try {
            Long value = Long.parseLong(str);
            if (value.compareTo(0L) >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Go to interface create new stock isdn
     * @param form
     * @param req
     * @return
     * @throws java.lang.Exception
     */
    public String preparePage() throws Exception {
        /** Action common object */
        log.info("Go to interface create new stock isdn");
        getReqSession();
//        String edit = req.getParameter("edit");
//        String forwardPage = "preparePage";
//        if (edit == null) {
//            forwardPage = "preparePage";
//        } else {
//            forwardPage = "gotoEditPstn";
//        }
//        AreaDAO areaDAO = new AreaDAO();
//        areaDAO.setSession(getSession());
//        StockIsdnPstnDAO stockIsdnPstnDAO = new StockIsdnPstnDAO();
//        stockIsdnPstnDAO.setSession(getSession());
//        List<Area> areaList = areaDAO.findAllProvince();
//        PstnDistrictCodeViewHelper viewHelper;
//
//        if (session.getAttribute(PSTN_DLU_DISTRICT_CODE_VIEWHELPER) == null) {
//            List<StockIsdnPstn> importPstnList = stockIsdnPstnDAO.findAll();
//            session.setAttribute("importPstnList", importPstnList);
//        }
//        if (edit == null) {
//            viewHelper = new PstnDistrictCodeViewHelper();
//            session.setAttribute(PSTN_DLU_DISTRICT_CODE_VIEWHELPER, viewHelper);
//        } else {
//            viewHelper = (PstnDistrictCodeViewHelper)session
//                    .getAttribute(PSTN_DLU_DISTRICT_CODE_VIEWHELPER);
//        }
//        ImportPSTNForm importPSTNForm = (ImportPSTNForm)form;
//        List dluList = new ArrayList();
//
//        if (importPSTNForm !=null && importPSTNForm.getLocation() != null
//                && !importPSTNForm.getLocation().equals("")) {
//            viewHelper.setLocation(importPSTNForm.getLocation());
//            List tempAreaList = areaDAO.findByProperty("areaCode", importPSTNForm.getLocation());
//            Area area = null;
//            if (tempAreaList != null && tempAreaList.size() != 0) {
//                area = (Area)tempAreaList.get(0);
//                viewHelper.setStockPstnDistrict(area.getPstnCode());
//            }
//            if (area != null) {
//                DslamDAO dslamDAO = new DslamDAO();
//                dslamDAO.setSession(getSession());
//                dluList = dslamDAO.findDluByProvince(area.getProvince());
//            }
//        } else {
//             viewHelper.setStockPstnDistrict(null);
//             viewHelper.setLocation("");
//             viewHelper.setDluId(null);
//        }


        String forwardPage = "preparePage";
        String provinceName = "";

        this.form.resetForm();

        //Phan quyen nguoi dung thuoc tinh nao-NamLT add 15/7/2010
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

//        Staff staff = new Staff();
//        String strStaff = "from Staff where lower(staffCode)=?";
//        Query staffQuery = getSession().createQuery(strStaff).setParameter(0, userToken.getLoginName().toLowerCase());
//        if (staffQuery.list() != null && !staffQuery.list().isEmpty()) {
//            staff = (Staff) staffQuery.list().get(0);
//        }


        ShopDAO shopDAO = new ShopDAO();
        shopDAO.setSession(this.getSession());
        Shop shop = shopDAO.findById(userToken.getShopId());


        if (shop != null) {

            System.out.println("shop: ");
            System.out.println(shop.getShopCode());
            System.out.println(" - ");
            System.out.println(shop.getName());

            if (shop.getProvince() != null && !"".equals(shop.getProvince())) {

                List parameterList = new ArrayList();
                String queryString = "Select name from Area where district IS NULL and precinct IS NULL ";
                queryString += " and lower(areaCode) = ? ";
                parameterList.add("" + shop.getProvince().toLowerCase() + "");

                Query queryObject = getSession().createQuery(queryString);
                for (int i = 0; i < parameterList.size(); i++) {
                    queryObject.setParameter(i, parameterList.get(i));
                }


                if (!queryObject.list().isEmpty()) {
                    provinceName = queryObject.list().get(0).toString();
                }
                this.form.setProvinceCode(shop.getProvince());
                this.form.setProvinceName(provinceName);


            } else {
                this.form.setProvinceCode("");
                this.form.setProvinceName("");
                getRequest().setAttribute(REQUEST_MESSAGE, "ERR.ISN.129");
            }

        }






        //xoa bien session
        getRequest().getSession().setAttribute(SESSION_LIST_STOCK_ISDN_PSTN, new ArrayList());

        //thiet lap du lieu cho combobox
        getRequest().setAttribute(REQUEST_LIST_LOCATIONS, getListProvinces());
        setTabSession("viewExcelPstntoDLU", ResourceBundleUtils.getResource("viewExcelPstntoDLU"));

        //End NamLT

        log.info("# End method StockIsdnAssignPstnToDluDAO.preparePage");
        return forwardPage;
    }

    //thiet lap thong bao loi
    private ImportPSTNForm setError(ImportPSTNForm importpstnAdd) {
        if (!isNumber(importpstnAdd.getPstnModule())) {
            importpstnAdd.setError(importpstnAdd.getError() + getText("ERR.ISN.080"));
        }
        if (!isNumber(importpstnAdd.getPstnRack())) {
            importpstnAdd.setError(importpstnAdd.getError() +  getText("ERR.ISN.081"));
        }
        if (!isNumber(importpstnAdd.getPstnShelf())) {
            importpstnAdd.setError(importpstnAdd.getError() + getText("ERR.ISN.082"));
        }
        if (!isNumber(importpstnAdd.getPstnSlot())) {
            importpstnAdd.setError(importpstnAdd.getError() + getText("ERR.ISN.083"));
        }
        if (!isNumber(importpstnAdd.getPort())) {
            importpstnAdd.setError(importpstnAdd.getError() + getText("ERR.ISN.130"));
        }
        if (!isNumber(importpstnAdd.getPstnDas())) {
            importpstnAdd.setError(importpstnAdd.getError() + getText("ERR.ISN.084"));
        }
        return importpstnAdd;
    }
    private static HashMap<String, List<String>> listProgressMessage = new HashMap<String, List<String>>();
    //Import du lieu thu file
    /*
     * Action gan so PSTN cho DLU tu file Excel
     * Author NamLT
     * Date 20/07/2010
     */

    public String assignPstnToDLU() throws Exception {
        log.debug("#Begin method StockIsdnAssignPstnToDluDAO.assignPstnToDslam");
        String forwardPage = "preparePage";
        getReqSession();
        ImportPSTNForm importPSTNForm = (ImportPSTNForm) form;
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        //ThanhNC add on 03/08/2010 bo xung thong bao thoi gian thuc hien
        List<String> listMessage = new ArrayList<String>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        this.listProgressMessage.put(req.getSession().getId(), listMessage);
        String startTime = DateTimeUtils.getSysDateTime();
        String message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + getText("ERR.ISN.131");
        listMessage.add(message);
        //End ThanhNC add


        int NUMBER_CMD_IN_BATCH = 100; //so luong ban ghi se commit 1 lan
        Connection conn = getSession().connection();
        StringBuffer strUpdateQuery = new StringBuffer();
        strUpdateQuery.append("update STOCK_ISDN_PSTN set ");
        strUpdateQuery.append("DSLAM_ID = ?, PORT = ?, MODULE = ?, RACK = ?, SHELF = ?, SLOT = ?, DAS = ?, DEVICE_TYPE = ? ,SERVICE_TYPE = ? ,LAST_UPDATE_USER=?,LAST_UPDATE_IP_ADDRESS=?,LAST_UPDATE_TIME=?,LAST_UPDATE_KEY=?");
        strUpdateQuery.append("where TO_NUMBER(ISDN) = ? ");
        strUpdateQuery.append("log errors reject limit unlimited"); //chuyen cac ban insert loi ra bang log
        PreparedStatement stmUpdate = conn.prepareStatement(strUpdateQuery.toString());

        //thiet lap du lieu cho combobox
//        getRequest().setAttribute(REQUEST_LIST_LOCATIONS, getListProvinces());
//        getRequest().setAttribute(REQUEST_LIST_DLUS, getListDluByProvince(importPSTNForm.getLocation()));
        try {

//            if (importPSTNForm.getImpFile() == null) {
//                req.setAttribute("resultAssignIsdnPrice", "Chưa chọn file");
//                return forwardPage;
//            }

//            File clientFile = importPSTNForm.getImpFile();

            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + CommonDAO.getSafeFileName(importPSTNForm.getServerFileName()));
            File clientFile = new File(serverFilePath);

            if (importPSTNForm.getServerFileName() == null) {
                req.setAttribute("resultAssignIsdnPrice", "ERR.ISN.138");
                return forwardPage;
            }

            List lstIsdn = new CommonDAO().readExcelFile(clientFile, "Sheet1", 0, 0, 9);
            if (lstIsdn == null || lstIsdn.size() == 0) {
                req.setAttribute("resultAssignIsdnPrice", "ERR.ISN.132");
                // lstIsdn = new CommonDAO().readExcelFile(clientFile, "Sheet1", 0, 1, 9);
                return forwardPage;
            }
            //List<ImportPSTNForm> listPstn = new ArrayList<ImportPSTNForm>();
            List<ImportPSTNForm> listError = new ArrayList<ImportPSTNForm>();
            Object[] obj = null;
            String dluId = "";
            String isdn = "";
            String module = "";
            String rack = "";
            String shelf = "";
            String slot = "";
            String port = "";
            String das = "";
            String telecomserviceId = "";
            String decide = "";
            int countInBatch = 0;
            for (int idx = 0; idx < lstIsdn.size(); idx++) {
                //Khoi tao lai bien
                dluId = "";
                isdn = "";
                module = "";
                rack = "";
                shelf = "";
                slot = "";
                port = "";
                das = "";
                telecomserviceId = "";
                decide = "";
                obj = (Object[]) lstIsdn.get(idx);
                if (obj != null && obj.length >= 10) {
                    dluId = obj[0] != null ? obj[0].toString().trim() : "";
                    isdn = obj[1] != null ? obj[1].toString().trim() : "";
                    module = obj[2] != null ? obj[2].toString().trim() : "";
                    rack = obj[3] != null ? obj[3].toString().trim() : "";
                    shelf = obj[4] != null ? obj[4].toString().trim() : "";
                    slot = obj[5] != null ? obj[5].toString().trim() : "";
                    port = obj[6] != null ? obj[6].toString().trim() : "";
                    das = obj[7] != null ? obj[7].toString().trim() : "";
                    telecomserviceId = obj[8] != null ? obj[8].toString().trim() : "";
                    decide = obj[9] != null ? obj[9].toString().trim() : "";
                }
                if (!isdn.equals("") && !dluId.equals("") && !("".equals(isdn.trim())) && isNumber(isdn)) {
                    ImportPSTNForm importpstnAdd = new ImportPSTNForm();
                    //ImportPSTNForm importpstnErr = new ImportPSTNForm();
                    //String strLocation = dluId;
//                    String strQuery = "select dslamId, name from Dslam where lower(code) = ? and productId = ? and status=? order by nlssort(name,'nls_sort=Vietnamese') asc";
//                    Query query = getSession().createQuery(strQuery);
//                    query.setParameter(0, strLocation.toLowerCase());
//                    query.setParameter(1, Constant.PRODUCT_ID_DLU);
//                    query.setParameter(2, Constant.STATUS_USE);
//                    List listDslams = query.list();
                    boolean Check = true;
                    String strQueryIsdn = "select isdn from StockIsdnPstn WHERE to_number(isdn) = ? ";
                    Query queryIsdn = getSession().createQuery(strQueryIsdn);
                    queryIsdn.setParameter(0, isdn);
                    List listIsdn = queryIsdn.list();
                    Dslam dslamBO = getDslamByCode(dluId);
                    Long dslamId = dslamBO != null ? dslamBO.getDslamId() : -1L;
                    String province = dslamBO != null ? dslamBO.getProvince() : "";
                    Area areaBO = getPstnCodeByProvince(province);
                    String pstnCode = areaBO != null ? areaBO.getPstnCode() : "";


                    //NamLT add :Check file import co bi trung cong da su dung khong 20/7/2010
                    String strCountQuery = "select count(*) from StockIsdnPstn "
                            + "where "
                            + "dslamId = ? and port = ? and module = ? and rack = ? and shelf = ? and slot = ? and isdn != ?";

                    Query countQuery =
                            getSession().createQuery(strCountQuery);
                    countQuery.setParameter(0, dslamId);
                    countQuery.setParameter(1, Long.valueOf(port));
                    //countQuery.setParameter(2, endPort);
                    try {
                        countQuery.setParameter(2, Long.valueOf(module));
                        countQuery.setParameter(3, Long.valueOf(rack));
                        countQuery.setParameter(4, Long.valueOf(shelf));
                        countQuery.setParameter(5, Long.valueOf(slot));
                        countQuery.setParameter(6, isdn);
                    } catch (Exception ex) {
                        importpstnAdd.setError(getText("ERR.ISN.133"));
                        Check = false;
                    }


                    List listCount = countQuery.list();
                    Long count = 0L;

                    if (listCount != null && listCount.size() > 0) {
                        count = Long.valueOf(listCount.get(0).toString());
                    }

                    if (count.compareTo(0L) > 0) {
                        importpstnAdd.setError(getText("ERR.ISN.134"));
                        Check = false;
                    }


                    if (dslamBO == null) {
                        importpstnAdd.setError(getText("ERR.ISN.135"));
                        Check = false;
                    }
                    if (listIsdn == null || listIsdn.size() == 0) {
                        importpstnAdd.setError(getText("ERR.ISN.136"));
                        Check = false;
                    }
                    if (dslamBO == null && (listIsdn == null || listIsdn.size() == 0)) {
                        importpstnAdd.setError(getText("ERR.ISN.137"));
                        Check = false;
                    }


                    if (isdn.substring(0, 3).indexOf(pstnCode, 0) != 0) {

                        importpstnAdd.setError(getText("ERR.ISN.139"));
                        Check = false;

                    }

                    //End NamLT

                    if (dslamBO != null) {
                        // Object[] dslam = (Object[]) listDslams.get(0);
                        importpstnAdd.setDluId(dslamBO.getDslamId().toString());
                    } else {
                        importpstnAdd.setDslamcod(dluId);
                    }
                    importpstnAdd.setIsdn(isdn);
                    importpstnAdd.setPstnModule(module);
                    importpstnAdd.setPstnRack(rack);
                    importpstnAdd.setPstnShelf(shelf);
                    importpstnAdd.setPstnSlot(slot);
                    importpstnAdd.setPort(port);
                    importpstnAdd.setPstnDas(das);
                    importpstnAdd.setPstnDeviceType(decide);
                    importpstnAdd.setServicesType(telecomserviceId);
                    importpstnAdd = setError(importpstnAdd);
                    if (Check) {
                        //listPstn.add(importpstnAdd);
                        stmUpdate.setLong(1, Long.parseLong(importpstnAdd.getDluId())); //thiet lap truong dslamId
                        stmUpdate.setLong(2, Long.parseLong(importpstnAdd.getPort())); //thiet lap truong port
                        stmUpdate.setLong(3, Long.parseLong(importpstnAdd.getPstnModule())); //thiet lap truong module
                        stmUpdate.setLong(4, Long.parseLong(importpstnAdd.getPstnRack())); //thiet lap truong rack
                        stmUpdate.setLong(5, Long.parseLong(importpstnAdd.getPstnShelf())); //thiet lap truong shelf
                        stmUpdate.setLong(6, Long.parseLong(importpstnAdd.getPstnSlot())); //thiet lap truong slot
                        stmUpdate.setLong(7, Long.parseLong(importpstnAdd.getPstnDas())); //thiet lap truong das
                        stmUpdate.setString(8, importpstnAdd.getPstnDeviceType()); //thiet lap truong deviceType
                        stmUpdate.setString(9, telecomserviceId);
                        stmUpdate.setString(10, userToken.getLoginName());
                        stmUpdate.setString(11, getRequest().getRemoteAddr());
                        stmUpdate.setDate(12, new java.sql.Date(DateTimeUtils.getSysDate().getTime()));
                        stmUpdate.setString(13, Constant.LAST_UPDATE_KEY);

                        //service_type
                        stmUpdate.setLong(14, Long.parseLong(importpstnAdd.getIsdn())); //isdn can update
                        stmUpdate.addBatch();
                        countInBatch += 1;
                        if ((countInBatch % NUMBER_CMD_IN_BATCH == 0) || (idx == lstIsdn.size() - 1)) {
                            //thuc hien batch, import du lieu vao DB, thuc hien insert 100 ban ghi 1 lan
                            int[] updateCount = stmUpdate.executeBatch();
                            conn.commit();
                            message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + getText("ERR.ISN.140") + countInBatch + " số.";
                            listMessage.add(message);
                        }
                    } else {
                        listError.add(importpstnAdd);
                    }

                } else {
                    ImportPSTNForm importpstnAdd = new ImportPSTNForm();
                    if (isdn.equals("")) {
                        importpstnAdd.setError(getText("ERR.ISN.141"));
                    }
                    if (dluId.equals("")) {
                        importpstnAdd.setError(getText("ERR.ISN.142"));
                    }
                    if (dluId.equals("") && isdn.equals("")) {
                        importpstnAdd.setError(getText("ERR.ISN.143"));
                    }
                    importpstnAdd.setIsdn(isdn);
                    importpstnAdd.setPstnModule(module);
                    importpstnAdd.setPstnRack(rack);
                    importpstnAdd.setPstnShelf(shelf);
                    importpstnAdd.setPstnSlot(slot);
                    importpstnAdd.setPort(port);
                    importpstnAdd.setPstnDas(das);
                    importpstnAdd.setPstnDeviceType(decide);
                    importpstnAdd = setError(importpstnAdd);
                    listError.add(importpstnAdd);
                }

            }
            message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + getText("ERR.ISN.144");
            listMessage.add(message);

            if (listError != null && listError.size() != 0) {
                message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + getText("ERR.ISN.145");
                listMessage.add(message);
                String DATE_FORMAT = "yyyyMMddhh24mmss";
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                Calendar cal = Calendar.getInstance();
                String date = sdf.format(cal.getTime());
                String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
                String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");

                String templatePath = "";
                //Giao dich xuat

                templatePath = tmp + "Log_Import_isdn_Dlu_.xls";
                filePath += "Log_Import_isdn_Dlu_" + userToken.getLoginName() + "_" + date + ".xls";
                importPSTNForm.setPathLogFile(filePath);
                req.setAttribute("pathLogFile", filePath);
                String realPath = req.getSession().getServletContext().getRealPath(filePath);
                String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

                Map beans = new HashMap();
                //set ngay tao
                //beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
                //set nguoi tao
                //beans.put("userCreate", userToken.getFullName());


                beans.put("listLogs", listError);
                XLSTransformer transformer = new XLSTransformer();
                transformer.transformXLS(templateRealPath, beans, realPath);
                message = simpleDateFormat.format(DateTimeUtils.getSysDate()) +  getText("ERR.ISN.146");
                listMessage.add(message);
                req.setAttribute("resultAssignIsdnPrice", "ERR.ISN.146");
            } else {
                req.setAttribute("resultAssignIsdnPrice", getText("ERR.ISN.140")+ " " + lstIsdn.size() + " số");
                return forwardPage;
            }

        } catch (Exception ex) {

            ex.printStackTrace();
            req.setAttribute("resultAssignIsdnPrice", "ERR.ISN.147");
            importPSTNForm.setPathLogFile("");
            getSession().clear();
            return forwardPage;
        }

        return forwardPage;
    }

    /**
     * Gan dai so PSTN vao DLU tren giao dien
     * Author NamLT
     * Date 21/7/2010
     * @throws Exception
     */
    public String assignPstnToDslam() throws Exception {
        log.debug("#Begin method StockIsdnAssignPstnToDluDAO.assignPstnToDslam");

        String forwardPage = "assignPstnToDslam";
        getReqSession();

        ImportPSTNForm importPSTNForm = (ImportPSTNForm) form;
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (!checkValidStockIsdnPstnToAdd()) {
            //thiet lap du lieu cho combobox
//            getRequest().setAttribute(REQUEST_LIST_LOCATIONS, getListProvinces());
//            getRequest().setAttribute(REQUEST_LIST_DLUS, getListDluByProvince(importPSTNForm.getLocation()));
            //getRequest().setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.isdnRangeInvalid");
            forwardPage =
                    "assignPstnToDslam";
            log.debug("# End method StockIsdnDAO.inputStockIsdn");
            return forwardPage;
        }

        String areaCode = importPSTNForm.getLocation();
        String pstnCode;

        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(getSession());
        List<Area> tempAreaList = areaDAO.findByProperty("areaCode", areaCode);
        pstnCode =
                tempAreaList.get(0).getPstnCode();

        String tempStockPstnHeader = importPSTNForm.getStockPstnHeader() != null ? importPSTNForm.getStockPstnHeader().trim() : "";
        String tempStartStockPstn = importPSTNForm.getStartStockPstn();
        String tempEndStockPstn = importPSTNForm.getEndStockPstn();

        String startStockPstn = tempStockPstnHeader + tempStartStockPstn;
        String endStockPstn = tempStockPstnHeader + tempEndStockPstn;

        Long startIsdn = Long.valueOf(pstnCode + startStockPstn);
        Long endIsdn = Long.valueOf(pstnCode + endStockPstn);
        Long startPort = Long.valueOf(importPSTNForm.getStartPortPstn());

//        if(!checkExistIsdnRange(startIsdn, endIsdn)) {
//            getRequest().setAttribute(REQUEST_LIST_LOCATIONS, getListProvinces());
//            getRequest().setAttribute(REQUEST_LIST_DLUS, getListDluByProvince(importPSTNForm.getLocation()));
//
//            forwardPage = "assignPstnToDslam";
//            log.debug("# End method StockIsdnDAO.inputStockIsdn");
//            return forwardPage;
//        }

        //bat dau thuc hien import du lieu vao bang stockIsdnPstn
        int numberRecord = 0;
        Long currentIsdn = startIsdn;
        Long currentPort = startPort;

        int NUMBER_CMD_IN_BATCH = 10000; //so luong ban ghi se commit 1 lan

        Connection conn;

        try {
            System.out.println("start update to DB - " + new java.util.Date());

            conn =
                    getSession().connection();
            //tao cau lenh update
            StringBuffer strUpdateQuery = new StringBuffer();
            strUpdateQuery.append("update STOCK_ISDN_PSTN set ");
            strUpdateQuery.append("DSLAM_ID = ?, PORT = ?, MODULE = ?, RACK = ?, SHELF = ?, SLOT = ?, DAS = ?, DEVICE_TYPE = ?,LAST_UPDATE_USER=?,LAST_UPDATE_IP_ADDRESS=?,LAST_UPDATE_TIME=?,LAST_UPDATE_KEY=? ");
            strUpdateQuery.append("where ISDN = ? ");
            strUpdateQuery.append("log errors reject limit unlimited"); //chuyen cac ban insert loi ra bang log
            PreparedStatement stmUpdate = conn.prepareStatement(strUpdateQuery.toString());

            //thuc hien lap qua dai isdn can thay doi thong tin DLU
            while (currentIsdn.compareTo(endIsdn) <= 0) {
                stmUpdate.setLong(1, Long.valueOf(importPSTNForm.getDluId())); //thiet lap truong dslamId
                stmUpdate.setLong(2, currentPort); //thiet lap truong port
                stmUpdate.setLong(3, Long.valueOf(importPSTNForm.getPstnModule())); //thiet lap truong module
                stmUpdate.setLong(4, Long.valueOf(importPSTNForm.getPstnRack())); //thiet lap truong rack
                stmUpdate.setLong(5, Long.valueOf(importPSTNForm.getPstnShelf())); //thiet lap truong shelf
                stmUpdate.setLong(6, Long.valueOf(importPSTNForm.getPstnSlot())); //thiet lap truong slot
                stmUpdate.setLong(7, Long.valueOf(importPSTNForm.getPstnDas())); //thiet lap truong das
                stmUpdate.setString(8, importPSTNForm.getPstnDeviceType()); //thiet lap truong deviceType
                stmUpdate.setString(9, userToken.getLoginName());
                stmUpdate.setString(10, getRequest().getRemoteAddr());
                stmUpdate.setDate(11, new java.sql.Date(DateTimeUtils.getSysDate().getTime()));
                stmUpdate.setString(12, Constant.LAST_UPDATE_KEY);
                stmUpdate.setLong(13, currentIsdn); //isdn can update

                stmUpdate.addBatch();
                currentIsdn++;

                currentPort++;

                numberRecord++;

                if (numberRecord % NUMBER_CMD_IN_BATCH == 0) {
                    //thuc hien batch, import du lieu vao DB, thuc hien insert 100 ban ghi 1 lan
                    int[] updateCount = stmUpdate.executeBatch();
                    conn.commit();

                    System.out.println("end update to DB - batch " + (numberRecord / NUMBER_CMD_IN_BATCH) + " - " + new java.util.Date());
                }

            }


            if (numberRecord % NUMBER_CMD_IN_BATCH != 0) { //insert so ban ghi con lai
                int[] updateCount = stmUpdate.executeBatch();
                conn.commit();

                System.out.println("end insert to DB - " + new java.util.Date());
            }

        } catch (SQLException ex2) {
            ex2.printStackTrace();
        }

//luu thong tin history
//        saveImportHistory(pstnCode + startStockPstn, pstnCode + endStockPstn);
        //  String strLocation = importPSTNForm.getLocation();
        // String strDslamId = importPSTNForm.getDluId();
        //  String strStockPstnDistrict = importPSTNForm.getStockPstnDistrict();

        getRequest().setAttribute(REQUEST_LIST_LOCATIONS, getListProvinces());
        getRequest().setAttribute(REQUEST_LIST_DLUS, getListDluByProvince(importPSTNForm.getLocation()));

        //  importPSTNForm.resetForm(); //
        // importPSTNForm.setLocation(strLocation);
        //  importPSTNForm.setDluId(strDslamId);
        //  importPSTNForm.setStockPstnDistrict(strStockPstnDistrict);

        getSession().clear();
        //  List<StockIsdnPstn> listStockIsdnPstns = getListStockIsdnPstnOfDlu(Long.valueOf(strDslamId));
        //  getRequest().getSession().setAttribute(SESSION_LIST_STOCK_ISDN_PSTN, listStockIsdnPstns);

        getRequest().setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.success");

        log.debug("# End method StockIsdnDAO.inputStockIsdn");
        this.listStockIsdnPstn();
        return forwardPage;
    }

    /**
     * Chuan bi du lieu de Edit
     * @param form
     * @param req
     * @return
     * @throws java.lang.Exception
     */
    public String gotoEditPstn() throws Exception {

        /** Action common object */
        log.info("Input Stock History Page Navigator ");
        log.debug("#Begin method StockIsdnAssignPstnToDluDAO.editPstn");

        String forwardPage = "gotoEditPstn";
        getReqSession();

        String strStockIsdnPstnId = getRequest().getParameter("stockIsdnPstnId");
        Long stockIsdnPstnId = -1L;
        try {
            stockIsdnPstnId = Long.valueOf(strStockIsdnPstnId);
        } catch (NumberFormatException ex) {
            stockIsdnPstnId = -1L;
        }

        StockIsdnPstn stockIsdnPstn = getStockIsdnPstnById(stockIsdnPstnId);
        if (stockIsdnPstn != null) {
            this.form.setStockPstnNumber(stockIsdnPstn.getIsdn());
            this.form.setDluId(stockIsdnPstn.getDslamId().toString());
            this.form.setStartPortPstn(stockIsdnPstn.getPort().toString());
            this.form.setPstnModule(stockIsdnPstn.getModule().toString());
            this.form.setPstnRack(stockIsdnPstn.getRack().toString());
            this.form.setPstnShelf(stockIsdnPstn.getShelf().toString());
            this.form.setPstnSlot(stockIsdnPstn.getSlot().toString());
            this.form.setPstnDas(stockIsdnPstn.getDas().toString());
            this.form.setPstnDeviceType(stockIsdnPstn.getDeviceType());


            //thiet lap du lieu cho cac combobox
            Dslam dslam = getDslamById(stockIsdnPstn.getDslamId());

            this.form.setDslamCode(dslam.getCode());
            this.form.setDslamName(dslam.getName());
            getRequest().setAttribute(REQUEST_LIST_LOCATIONS, getListProvinces());
            if (dslam != null) {
                this.form.setLocation(dslam.getProvince());
                List<Area> lstProvinceName = getProvinceByCode(dslam.getProvince());
                this.form.setProvinceName(lstProvinceName.get(0).getName());
                getRequest().setAttribute(REQUEST_LIST_DLUS, getListDluByProvince(dslam.getProvince()));
            }

        }

        log.debug("# End method StockIsdnAssignPstnToDluDAO.editPstn");
        return forwardPage;
    }

    /**
     * Ham Edit so PSTN gan cho DLU
     * @param form
     * @param req
     * @return
     * @throws java.lang.Exception
     */
    public String editPstnComplete() throws Exception {
        log.debug("#Begin method StockIsdnAssignPstnToDluDAO.editPstnComplete");

        String forwardPage = "gotoEditPstn";
        getReqSession();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");

        if (!checkValidStockIsdnPstnToEdit()) {
            String isdn = this.form.getStockPstnNumber();
            StockIsdnPstn stockIsdnPstn = getStockIsdnPstnByIsdn(isdn);
            if (stockIsdnPstn != null) {
                Dslam dslam = getDslamById(stockIsdnPstn.getDslamId());
                getRequest().setAttribute(REQUEST_LIST_LOCATIONS, getListProvinces());
                if (dslam != null) {
                    this.form.setLocation(dslam.getProvince());
                    getRequest().setAttribute(REQUEST_LIST_DLUS, getListDluByProvince(dslam.getProvince()));
                }

            }

            log.debug("# End method StockIsdnAssignPstnToDluDAO.editPstnComplete");
            return forwardPage;
        }

//        ImportPSTNForm importPSTNForm = (ImportPSTNForm)form;
//        ActionErrors errors = new ActionErrors();
//
//        String stockIsdnPstnId = (String)session.getAttribute("stockIsdnPstnId");
//
//        if (stockIsdnPstnId == null || stockIsdnPstnId.equals("")) {
//            forwardPage = "errorPage";
//            return forwardPage;
//        }
//
//        StockIsdnPstnDAO stockIsdnPstnDAO = new StockIsdnPstnDAO();
//        stockIsdnPstnDAO.setSession(getSession());
//        StockIsdnPstn stockIsdnPstn = stockIsdnPstnDAO.findById(new Long(stockIsdnPstnId));
//
//        PstnDistrictCodeViewHelper viewHelper = (PstnDistrictCodeViewHelper)session
//                .getAttribute(PSTN_DLU_DISTRICT_CODE_VIEWHELPER);
//
//        if (viewHelper == null) {
//            viewHelper = new PstnDistrictCodeViewHelper();
//            session.setAttribute(PSTN_DLU_DISTRICT_CODE_VIEWHELPER, viewHelper);
//        }
//        saveViewHelper(importPSTNForm, viewHelper);
//
//        String areaCode = importPSTNForm.getLocation();
//        String pstnCode;
//        AreaDAO areaDAO = new AreaDAO();
//        areaDAO.setSession(getSession());
//        List tempAreaList = areaDAO.findByProperty("areaCode", areaCode);
//        if (tempAreaList != null && tempAreaList.size() != 0) {
//                Area area = (Area)tempAreaList.get(0);
//                pstnCode = area.getPstnCode();
//        } else {
//            errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Thành phố bạn chọn không còn tồn tại", false));
//            saveErrors(req, errors);
//            forwardPage = "assignPstnToDslam";
//            log.debug("# End method StockIsdnDAO.inputStockIsdn");
//            return forwardPage;
//        }
//
//        String tempIsdn = pstnCode + importPSTNForm.getStockPstnNumber();
//
//        boolean valid = checkExist(new Long(tempIsdn), new Long(tempIsdn),
//                importPSTNForm, errors , true, viewHelper);
//
//        if (!valid) {
//            saveErrors(req, errors);
//            forwardPage = "gotoEditPstn";
//            log.debug("# End method StockIsdnDAO.inputStockIsdn");
//            return forwardPage;
//        }
        String isdn = this.form.getStockPstnNumber();
        StockIsdnPstn stockIsdnPstn = getStockIsdnPstnByIsdn(isdn);
        if (stockIsdnPstn != null) {
            stockIsdnPstn.setPort(new Long(this.form.getStartPortPstn()));
            stockIsdnPstn.setModule(new Long(this.form.getPstnModule()));
            stockIsdnPstn.setRack(new Long(this.form.getPstnRack()));
            stockIsdnPstn.setShelf(new Long(this.form.getPstnShelf()));
            stockIsdnPstn.setSlot(new Long(this.form.getPstnSlot()));
            stockIsdnPstn.setDeviceType(this.form.getPstnDeviceType());
            stockIsdnPstn.setDas(new Long(this.form.getPstnDas()));
            stockIsdnPstn.setLastUpdateIpAddress(getRequest().getRemoteAddr());
            stockIsdnPstn.setLastUpdateKey(Constant.LAST_UPDATE_KEY);
            stockIsdnPstn.setLastUpdateTime(new java.sql.Date(DateTimeUtils.getSysDate().getTime()));
            stockIsdnPstn.setLastUpdateUser(userToken.getLoginName());
            //NamLT add:Lay DsLamCode
            Dslam dslam = getDslamByCode(this.form.getDslamCode());

            stockIsdnPstn.setDslamId(dslam.getDslamId());
            getSession().update(stockIsdnPstn);

            //cap nhat lai gia tri trong bien session
            StockIsdnPstn tmpStockIsdnPstn = getStockIsdnPstnInListByIsdn(isdn);
            if (tmpStockIsdnPstn != null) {
                tmpStockIsdnPstn.setPort(new Long(this.form.getStartPortPstn()));
                tmpStockIsdnPstn.setModule(new Long(this.form.getPstnModule()));
                tmpStockIsdnPstn.setRack(new Long(this.form.getPstnRack()));
                tmpStockIsdnPstn.setShelf(new Long(this.form.getPstnShelf()));
                tmpStockIsdnPstn.setSlot(new Long(this.form.getPstnSlot()));
                tmpStockIsdnPstn.setDeviceType(this.form.getPstnDeviceType());
                tmpStockIsdnPstn.setDas(new Long(this.form.getPstnDas()));
                //Dslam dslam = getDslamByCode(this.form.getDslamCode());

                stockIsdnPstn.setDslamId(dslam.getDslamId());
            }

        } else {
            getRequest().setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.isdnNotExist");
        }

        getRequest().setAttribute("editStockIsdnPstnMode", "closePopup");
        //getRequest().setAttribute("stockIsdnNuber", isdn);


        log.debug("# End method StockIsdnAssignPstnToDluDAO.editPstnComplete");
        return forwardPage;
    }

    /**
     * Ham xoa so PSTN gan cho DLU
     * @param form
     * @param req
     * @return
     * @throws java.lang.Exception
     */
    public String deletePstn() throws Exception {
        log.debug("#Begin method StockIsdnAssignPstnToDluDAO.deletePstnComplete");

//        String forwardPage = "deletePstnComplete";
        String forwardPage = "pageNavigatorForList";
        UserToken userToken = (UserToken) getRequest().getSession().getAttribute("userToken");
        //getReqSession();

        String strStockIsdnPstnId = getRequest().getParameter("stockIsdnPstnId");
        Long stockIsdnPstnId = -1L;
        try {
            stockIsdnPstnId = Long.valueOf(strStockIsdnPstnId);
        } catch (NumberFormatException ex) {
            stockIsdnPstnId = -1L;
        }

        StockIsdnPstn stockIsdnPstn = getStockIsdnPstnById(stockIsdnPstnId);
        if (stockIsdnPstn != null) {
            stockIsdnPstn.setPort(null);
            stockIsdnPstn.setModule(null);
            stockIsdnPstn.setRack(null);
            stockIsdnPstn.setShelf(null);
            stockIsdnPstn.setSlot(null);
            stockIsdnPstn.setDeviceType(null);
            stockIsdnPstn.setDas(null);
            stockIsdnPstn.setDslamId(null);
            stockIsdnPstn.setLastUpdateIpAddress(getRequest().getRemoteAddr());
            stockIsdnPstn.setLastUpdateKey(Constant.LAST_UPDATE_KEY);
            stockIsdnPstn.setLastUpdateTime(new java.sql.Date(DateTimeUtils.getSysDate().getTime()));
            stockIsdnPstn.setLastUpdateUser(userToken.getLoginName());


            getSession().update(stockIsdnPstn);

            //
            //cap nhat lai gia tri trong bien session
//            List<StockIsdnPstn> listStockIsdnPstns = (List<StockIsdnPstn>) getRequest().getAttribute(SESSION_LIST_STOCK_ISDN_PSTN);
//            if (listStockIsdnPstns != null && listStockIsdnPstns.size() > 0) {
//                for (int i = 0; i
//                        < listStockIsdnPstns.size(); i++) {
//                    StockIsdnPstn tmpStockIsdnPstn = listStockIsdnPstns.get(i);
//                    if (tmpStockIsdnPstn.getId().equals(stockIsdnPstnId)) {
//                        listStockIsdnPstns.remove(i);
//                        break;
//
//                    }
//
//
//                }
//            }




//            StockIsdnPstn tmpStockIsdnPstn = getStockIsdnPstnInListById(stockIsdnPstnId);
//            if (tmpStockIsdnPstn != null) {
//                List<StockIsdnPstn> listStockIsdnPstn = (List<StockIsdnPstn>) getRequest().getSession().getAttribute(SESSION_LIST_STOCK_ISDN_PSTN);
//                listStockIsdnPstn.remove(tmpStockIsdnPstn);
//            }
            //session.setAttribute("importPstnList", listStockIsdnPstns);
            getRequest().setAttribute(REQUEST_MESSAGE_LIST, "assignPstnToDlu.success.delIsdnPstn");
        }

//
//        ActionErrors errors = new ActionErrors();
//
//        String stockIsdnPstnId = req.getParameter("stockIsdnPstnId");
//        if (stockIsdnPstnId == null || stockIsdnPstnId.equals("")) {
//            forwardPage = "errorPage";
//            return forwardPage;
//        }
//        PstnDistrictCodeViewHelper viewHelper = (PstnDistrictCodeViewHelper)session
//                .getAttribute(PSTN_DLU_DISTRICT_CODE_VIEWHELPER);
//
//        if (viewHelper == null) {
//            viewHelper = new PstnDistrictCodeViewHelper();
//            session.setAttribute(PSTN_DLU_DISTRICT_CODE_VIEWHELPER, viewHelper);
//        }
//
//        StockIsdnPstnDAO stockIsdnPstnDAO = new StockIsdnPstnDAO();
//        stockIsdnPstnDAO.setSession(getSession());
//        StockIsdnPstn stockIsdnPstn = stockIsdnPstnDAO.findById(new Long(stockIsdnPstnId));
//        stockIsdnPstnDAO.delete(stockIsdnPstn);
//
//        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Số PSTN đã xóa thành công", false));
//        saveErrors(req, errors);
//        saveViewHelper(stockIsdnPstn, viewHelper);
//
//        /** Renew interface */
//        List<StockIsdnPstn> importPstnList = stockIsdnPstnDAO.findAll();
//
//        session.setAttribute("importPstnList", importPstnList);
        log.debug("# End method StockIsdnAssignPstnToDluDAO.editPstn");
//        System.out.println("END in deletePSTN");
        return listStockIsdnPstn();
    }

    /*
     * Action Export file tim kiem
     * Author NamLT
     * 21/7/2010
     */
    public String actionExport2Excel() throws Exception {

        String pageForward = "pageNavigator";
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        Long dslamId = -1L;
        try {
            ImportPSTNForm importPSTNForm = (ImportPSTNForm) form;
            String strSelectedDslamId = null;
            // List<StockIsdnPstn> lstStockIsdnPstn=(List<StockIsdnPstn>) req.getSession().getAttribute(SESSION_LIST_STOCK_ISDN_PSTN);
            String dslamCode = importPSTNForm.getDslamCode();
//            if (!(dslamCode != null && !dslamCode.trim().equals(""))) {
//                httpServletRequest.setAttribute("messageList", "Chưa nhập mã DLU!");
//                return pageForward;
//            }
            DslamDAO dslamDAO = new DslamDAO();
            dslamDAO.setSession(this.getSession());
            List<Dslam> listDslam = dslamDAO.findByProperty("code", dslamCode.trim().toUpperCase());

//            if (dslamCode != null && !dslamCode.trim().equals("") && !(listDslam != null && listDslam.size() > 0)) {
//                httpServletRequest.setAttribute("messageList", "Mã DLU không chính xác!");
//                return pageForward;
//            }

            if (listDslam != null && listDslam.size() > 0) {
                strSelectedDslamId = listDslam.get(0).getDslamId().toString();
                dslamId = Long.valueOf(strSelectedDslamId);
            } else {

                dslamId = -1L;

            }


            String tempStockPstnHeader = importPSTNForm.getStockPstnHeader() != null ? importPSTNForm.getStockPstnHeader().trim() : "";
            String tempStartStockPstn = importPSTNForm.getStartStockPstn();
            String tempEndStockPstn = importPSTNForm.getEndStockPstn();
            if (tempStartStockPstn == null && tempEndStockPstn != null) {
                tempStartStockPstn = copyString(tempEndStockPstn, "0");
            }
            if (tempEndStockPstn == null && tempStartStockPstn != null) {
                tempStartStockPstn = copyString(tempEndStockPstn, "9");
            }

            String startStockPstn = tempStockPstnHeader + tempStartStockPstn;
            String endStockPstn = tempStockPstnHeader + tempEndStockPstn;
            String pstnCode = importPSTNForm.getStockPstnDistrict();
            Long startIsdn = Long.valueOf(pstnCode + startStockPstn);
            Long endIsdn = Long.valueOf(pstnCode + endStockPstn);
            //Long startPort = Long.valueOf(importPSTNForm.getStartPortPstn());
            //Long endPort = Long.valueOf(importPSTNForm.getEndPortPstn());



            List listParameter = new ArrayList();

            //StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.areaCode, a.fullName) ");

//Long id, String isdn, Long port, Long module, Long rack, Long shelf, Long slot, String deviceType, Long status, Date createDate, Long das, String dslamCode

            String strQuery = " select new com.viettel.im.database.BO.StockIsdnPstn(a.id, a.isdn, a.port, a.module, a.rack, a.shelf, a.slot, a.deviceType, a.status, a.createDate, a.das, (select b.code from Dslam b where b.dslamId = a.dslamId),a.isdnType ) from StockIsdnPstn a where 1=1";


            if (dslamId != null && dslamId != -1L) {
                strQuery += " and a.dslamId = ? ";
                listParameter.add(dslamId);
            } else {
                if (this.form.getProvinceCode() != null && !"".equals(this.form.getProvinceCode())) {
                    strQuery += " and exists(select 1 from Dslam d where d.dslamId = a.dslamId and lower(d.province)= ?) ";
                    listParameter.add(this.form.getProvinceCode().toLowerCase());
                }

            }
            if (importPSTNForm.getStartStockPstn() != null && !importPSTNForm.getStartStockPstn().trim().equals("")) {
                listParameter.add(startIsdn.toString());
                strQuery += " and a.isdn >= ? ";
            }
            if (importPSTNForm.getEndStockPstn() != null && !importPSTNForm.getEndStockPstn().trim().equals("")) {
                listParameter.add(endIsdn.toString());
                strQuery += " and a.isdn <= ? ";
            }
            if (importPSTNForm.getStartPortPstn() != null && !importPSTNForm.getStartPortPstn().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getStartPortPstn().trim()) < 0) {
                        req.setAttribute("message", "PSTN.error.StartPort");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getStartPortPstn().trim()));
                        strQuery += " and a.port >= ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "BookType.error.StartPortFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getEndPortPstn() != null && !importPSTNForm.getEndPortPstn().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getEndPortPstn().trim()) < 0) {
                        req.setAttribute("message", "PSTN.error.EndPort");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getEndPortPstn().trim()));
                        strQuery += " and a.port <= ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "BookType.error.EndPortFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getPstnModule() != null && !importPSTNForm.getPstnModule().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getPstnModule().trim()) < 0) {
                        req.setAttribute("message", "PSTN.error.Module");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getPstnModule().trim()));
                        strQuery += " and a.module = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "BookType.error.ModuleFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getPstnRack() != null && !importPSTNForm.getPstnRack().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getPstnRack().trim()) < 0) {
                        req.setAttribute("message", "PSTN.error.Rack");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getPstnRack().trim()));
                        strQuery += " and a.rack = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "BookType.error.RackFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getPstnShelf() != null && !importPSTNForm.getPstnShelf().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getPstnShelf().trim()) < 0) {
                        req.setAttribute("message", "PSTN.error.Shelf");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getPstnShelf().trim()));
                        strQuery += " and a.shelf = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "BookType.error.ShelfFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getPstnSlot() != null && !importPSTNForm.getPstnSlot().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getPstnSlot().trim()) < 0) {
                        req.setAttribute("message", "PSTN.error.Slot");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getPstnSlot().trim()));
                        strQuery += " and a.slot = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "BookType.error.SlotFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getPstnDas() != null && !importPSTNForm.getPstnDas().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getPstnDas().trim()) < 0) {
                        req.setAttribute("message", "PSTN.error.Das");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getPstnDas().trim()));
                        strQuery += " and a.das = ? ";
                    }
                } catch (NumberFormatException e) {
                    req.setAttribute("message", "BookType.error.DasFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getPstnDeviceType().trim() != null && !importPSTNForm.getPstnDeviceType().trim().equals("")) {
                listParameter.add(importPSTNForm.getPstnDeviceType().trim());
                strQuery += " and a.deviceType = ? ";
            }

            if (importPSTNForm.getStatus().trim() != null && !importPSTNForm.getStatus().trim().equals("")) {
                listParameter.add(Long.parseLong(importPSTNForm.getStatus().trim()));
                strQuery += " and a.status = ? ";
            }

             if (importPSTNForm.getIsdnType().trim() != null && !importPSTNForm.getIsdnType().trim().equals("")) {
                listParameter.add(importPSTNForm.getIsdnType().trim());
                strQuery += " and a.isdnType = ? ";
            }


            strQuery += " and rownum < ? ";
            listParameter.add(MAX_RESULT);

            strQuery += " order by a.dslamId, a.module, a.rack, a.shelf, a.slot, a.port ";

            Query query = getSession().createQuery(strQuery);
            for (int i = 0;
                    i < listParameter.size();
                    i++) {
                query.setParameter(i, listParameter.get(i));
            }
            List<StockIsdnPstn> listStockIsdnPstn =
                    (List<StockIsdnPstn>) query.list();

            if (null == listStockIsdnPstn || 0 == listStockIsdnPstn.size()) {
                throw new Exception("Danh sách số ISDN rỗng");
            }

            for (int i = 0; i < listStockIsdnPstn.size(); i++) {
                StockIsdnPstn lst = (StockIsdnPstn) listStockIsdnPstn.get(i);
                lst.setStrStatus(this.getStatus(lst.getStatus()));
                lst.setStrIsdnType(this.getIsdnType(lst.getIsdnType()));

            }

            String DATE_FORMAT = "yyyyMMddhh24mmss";
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
            Calendar cal = Calendar.getInstance();
            String date = sdf.format(cal.getTime());
            String tmp = ResourceBundleUtils.getResource("TEMPLATE_PATH");
            String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
            String templatePath = "";

            templatePath = tmp + "Isdn_Pstn_List.xls";
            filePath += "Isdn_Pstn_List_" + userToken.getLoginName() + "_" + date + ".xls";

            this.form.setPathExpFile(filePath);

            String realPath = req.getSession().getServletContext().getRealPath(filePath);
            String templateRealPath = req.getSession().getServletContext().getRealPath(templatePath);

            Map beans = new HashMap();
            //  beans.put("dateCreate", DateTimeUtils.convertStringToDate(DateTimeUtils.getSysdate()));
            // beans.put("userCreate", userToken.getFullName());
            beans.put("lstIsdn", listStockIsdnPstn);
            req.setAttribute("pathExpFile", realPath);
            XLSTransformer transformer = new XLSTransformer();
            transformer.transformXLS(templateRealPath, beans, realPath);
            req.setAttribute(REQUEST_MESSAGE, "MSG.FAC.Excel.Success");
        } catch (Exception e) {
            req.setAttribute(REQUEST_MESSAGE, e.getMessage());
            e.printStackTrace();
        }

        return pageForward;


    }

    /*
     * Ham tra ve trang thai dang Text
     * Author NamLT
     * Date 21.7.2010
     */
    public String getStatus(Long status) {
        if (status == 1L) {
            return ("NewISDN");
        } else if (status == 2L) {
            return ("UsingISDN");
        } else if (status == 3L) {
            return ("SuspendISDN");
        } else if (status == 5L) {
            return ("LockISDN");
        } else {
            return ("MSG.SAE.097");
        }
        //return "";

    }

    public String getIsdnType(String isdnType) {
        if ("1".equals(isdnType)) {
            return ("PrepaidISDN");
        } else if ("2".equals(isdnType)) {
            return ("PostPaidISDN");
        } else if ("3".equals(isdnType)) {
            return ("SpecialISDN");
        } else if ("4".equals(isdnType)) {
            return ("FakeISDN");
        } else {
            return ("MSG.SAE.097");
        }
    }

    /**
     * Input Stock History Page Navigator
     * @param form
     * @param req
     * @return
     * @throws java.lang.Exception
     */
    public String pageNavigator() throws Exception {

        /** Action common object */
        log.info("Input Stock History Page Navigator ");
        log.debug("#Begin method StockIsdnAssignPstnToDluDAO.pageNavigator");

        String forwardPage = "pageNavigator";
        getReqSession();

        StockIsdnPstnDAO stockIsdnPstnDAO = new StockIsdnPstnDAO();
        stockIsdnPstnDAO.setSession(getSession());
        List<StockIsdnPstn> importPstnList = stockIsdnPstnDAO.findAll();

        reqSession.setAttribute(SESSION_LIST_STOCK_ISDN_PSTN, importPstnList);

        log.debug("# End method StockIsdnAssignPstnToDluDAO.pageNavigator");
        return forwardPage;
    }

    private void saveViewHelper(StockIsdnPstn stockIsdnPstn, PstnDistrictCodeViewHelper viewHelper) {

        String tempIsdn = stockIsdnPstn.getIsdn();
        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(getSession());
        List<Area> areaList = areaDAO.findAllProvince();
        for (int i = 0; i
                < areaList.size(); i++) {
            Area area = areaList.get(i);
            int temp = tempIsdn.indexOf(area.getPstnCode());
            if (temp == 0) {
                viewHelper.setStockPstnDistrict(area.getPstnCode());
                viewHelper.setStockPstnNumber(tempIsdn.substring(area.getPstnCode().length()));
                viewHelper.setLocation(area.getAreaCode());
                break;

            }


        }
        viewHelper.setStartPortPstn(stockIsdnPstn.getPort().toString());
        viewHelper.setPstnDas(stockIsdnPstn.getDas().toString());
        viewHelper.setPstnDeviceType(stockIsdnPstn.getDeviceType());
        viewHelper.setPstnModule(stockIsdnPstn.getModule().toString());
        viewHelper.setPstnRack(stockIsdnPstn.getRack().toString());
        viewHelper.setPstnShelf(stockIsdnPstn.getShelf().toString());
        viewHelper.setPstnSlot(stockIsdnPstn.getSlot().toString());
        viewHelper.setDluId(stockIsdnPstn.getDslamId().toString());
        viewHelper.setStatus(stockIsdnPstn.getStatus().toString());
    }

    private void saveViewHelper(ImportPSTNForm importPSTNForm, PstnDistrictCodeViewHelper viewHelper) {

        viewHelper.setStockPreviousPstnNumber(viewHelper.getStockPstnNumber());
        viewHelper.setStockPstnNumber(importPSTNForm.getStockPstnNumber());

        viewHelper.setLocation(importPSTNForm.getLocation());
        viewHelper.setStartPortPstn(importPSTNForm.getStartPortPstn());
        viewHelper.setPstnDas(importPSTNForm.getPstnDas());
        viewHelper.setPstnDeviceType(importPSTNForm.getPstnDeviceType());
        viewHelper.setPstnModule(importPSTNForm.getPstnModule());
        viewHelper.setPstnRack(importPSTNForm.getPstnRack());
        viewHelper.setPstnShelf(importPSTNForm.getPstnShelf());
        viewHelper.setPstnSlot(importPSTNForm.getPstnSlot());

    }

//    private boolean saveStockIsdnPstn(StockIsdnPstn stockIsdnPstn, Long isdn,
//            Long port, ImportPSTNForm importPSTNForm) throws Exception {
//
//        try {
//            StockIsdnPstnDAO stockIsdnPstnDAO = new StockIsdnPstnDAO();
//            stockIsdnPstnDAO.setSession(getSession());
//            stockIsdnPstn.setId(getSequence("STOCK_ISDN_PSTN_SEQ"));
//            stockIsdnPstn.setIsdn(isdn.toString());
//            stockIsdnPstn.setPort(port);
//            stockIsdnPstn.setModule(new Long(importPSTNForm.getPstnModule()));
//            stockIsdnPstn.setRack(new Long(importPSTNForm.getPstnRack()));
//            stockIsdnPstn.setShelf(new Long(importPSTNForm.getPstnShelf()));
//            stockIsdnPstn.setSlot(new Long(importPSTNForm.getPstnSlot()));
//            stockIsdnPstn.setDeviceType(importPSTNForm.getPstnDeviceType());
//            stockIsdnPstn.setStatus(Constant.STATUS_ISDN_NEW);
//            stockIsdnPstn.setDas(new Long(importPSTNForm.getPstnDas()));
//            stockIsdnPstn.setIsdnStatus(Constant.STATUS_ACTIVE);
//            stockIsdnPstn.setCreateDate(new Date());
//            stockIsdnPstnDAO.save(stockIsdnPstn);
//
//            return true;
//        } catch (Exception ex) {
//            getRequest().setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Dữ liệu bị trùng");
//            return false;
//        }
//
//    }
//
//
//    private void saveImportHistory(String startIsdn, String endIsdn) throws Exception {
//        ImportHistoryDAO importHistoryDAO = new ImportHistoryDAO();
//        importHistoryDAO.setSession(getSession());
//        ImportHistory importHistory = new ImportHistory();
//        importHistory.setImportHistoryId(getSequence("IMPORT_HISTORY_SEQ"));
//        importHistory.setStockTypeId(Constant.STOCK_ISDN_PSTN);
//        importHistory.setStockStartIsdn(startIsdn);
//        importHistory.setStockEndIsdn(endIsdn);
//        importHistory.setDateCreated(new Date());
//        importHistoryDAO.save(importHistory);
//    }
//    private boolean checkExist(Long startPstnNumber, Long endPstnNumber,
//            ImportPSTNForm importPSTNForm, ActionErrors errors
//            , boolean edit, PstnDistrictCodeViewHelper viewHelper) {
//
//        StockIsdnPstnDAO stockIsdnPstnDAO = new StockIsdnPstnDAO();
//        stockIsdnPstnDAO.setSession(getSession());
//        boolean valid = true;
//
//        /** In case of edit, we first check, wherether or not PSTN already assign */
//        if (edit) {
//            boolean existPstn;
//            existPstn = stockIsdnPstnDAO.checkExistStockIsdnPstn(
//                        startPstnNumber, new Long(importPSTNForm.getStartPortPstn()),
//                        new Long(importPSTNForm.getPstnModule()), new Long(importPSTNForm.getPstnRack()),
//                        new Long(importPSTNForm.getPstnShelf()), new Long(importPSTNForm.getPstnSlot()),
//                        new Long(importPSTNForm.getPstnDas()), importPSTNForm.getPstnDeviceType());
//            if (existPstn) {
//                if(!viewHelper.getStockPstnNumber().equals(viewHelper.getStockPreviousPstnNumber())) {
//                    errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Số PSTN đã được đấu nối", false));
//                    getRequest().setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Số PSTN đã được đấu nối"); //tamdt1, 07/05/2009
//                    return false;
//                }
//            }
//            boolean existPstnPort = false;
//
//            /** Incase PSTN Number not change */
//            if (viewHelper.getStockPstnNumber().equals(viewHelper.getStockPreviousPstnNumber())) {
//                existPstnPort = stockIsdnPstnDAO.checkExistStockIsdnPstnPort(new Long(importPSTNForm.getDluId()),
//                        new Long(importPSTNForm.getStartPortPstn()), new Long(importPSTNForm.getStartPortPstn()),
//                        new Long(importPSTNForm.getPstnModule()), new Long(importPSTNForm.getPstnRack()),
//                        new Long(importPSTNForm.getPstnShelf()), new Long(importPSTNForm.getPstnSlot()),
//                        new Long(importPSTNForm.getPstnDas()), importPSTNForm.getPstnDeviceType(),
//                        new Long(importPSTNForm.getStatus()));
//            }
//            if (existPstnPort) {
//                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Cổng bạn chọn đã được đấu nối", false));
//                getRequest().setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Cổng bạn chọn đã được đấu nối"); //tamdt1, 07/05/2009
//                return false;
//            } else {
//
//                /** Incase we chang to other PSTN number */
//                if (!viewHelper.getStockPstnNumber().equals(viewHelper.getStockPreviousPstnNumber())) {
//                    boolean existPstnNumber = stockIsdnPstnDAO.checkExistStockIsdnPstnNumber(startPstnNumber, endPstnNumber);
//                    if (existPstnNumber) {
//                        errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Số bạn chọn đã được đấu nối", false));
//                        getRequest().setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Số bạn chọn đã được đấu nối"); //tamdt1, 07/05/2009
//                        return false;
//                    }
//                }
//            }
//        } else {
//            boolean existPstnNumber = stockIsdnPstnDAO.checkExistStockIsdnPstnNumber(startPstnNumber, endPstnNumber);
//            if (existPstnNumber) {
//                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Dải số bạn chọn đã tồn tại", false));
//                getRequest().setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Dải số bạn chọn đã tồn tại"); //tamdt1, 07/05/2009
//                valid = false;
//            }
//            boolean existPstnPort = false;
//            Long status = null;
//            if (importPSTNForm.getStatus() != null) {
//                status = new Long(importPSTNForm.getStatus());
//            } else {
//                status = PSTN_ACTIVE_STATUS;//Default status
//            }
//            existPstnPort = stockIsdnPstnDAO.checkExistStockIsdnPstnPort(new Long(importPSTNForm.getDluId()),
//                    new Long(importPSTNForm.getStartPortPstn()), new Long(importPSTNForm.getEndPortPstn()),
//                    new Long(importPSTNForm.getPstnModule()), new Long(importPSTNForm.getPstnRack()),
//                    new Long(importPSTNForm.getPstnShelf()), new Long(importPSTNForm.getPstnSlot()),
//                    new Long(importPSTNForm.getPstnDas()), importPSTNForm.getPstnDeviceType(),
//                    status);
//             if (existPstnPort && !valid) {
//                errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("Cổng bạn chọn đã được đấu nối", false));
//                getRequest().setAttribute(REQUEST_MESSAGE, "!!!Lỗi. Cổng bạn chọn đã được đấu nối"); //tamdt1, 07/05/2009
//                return false;
//            }
//        }
//
//        return true;
//    }
    /**
     *
     * author tamdt1
     * date: 07/05/2009
     * phuc vu viec phan trang
     *
     */
    public String pageNavigatorForList() throws Exception {
        log.info("Begin method pageNavigatorForList of StockIsdnAssignPstnToDluDAO ...");

        String pageForward = "pageNavigatorForList";

        //getRequest().getSession().getAttribute(SESSION_LIST_STOCK_ISDN_PSTN);

        listStockIsdnPstn();


        log.info("End method pageNavigatorForList of StockIsdnAssignPstnToDluDAO");

        return pageForward;
    }
    private HashMap hashMapResult = new HashMap();

    public HashMap getHashMapResult() {
        return hashMapResult;
    }

    public void setHashMapResult(HashMap hashMapResult) {
        this.hashMapResult = hashMapResult;
    }

    /**
     *
     * author tamdt1
     * date: 07/05/2009
     * lay danh sach cac tram DLU
     *
     */
    public String getDluCombobox() throws Exception {
        try {
            HttpServletRequest httpServletRequest = getRequest();

            //lay danh sach cac tram DLU doi voi 1 ma tinh
            String strLocation = httpServletRequest.getParameter("location");
            String strTarget = httpServletRequest.getParameter("target").trim();
            String[] arrTarget = strTarget.split(",");

            //Chi update dau so PSTN
            if (strLocation != null && !strLocation.trim().equals("")) {
                AreaDAO areaDAO = new AreaDAO();
                areaDAO.setSession(getSession());
                List<Area> tempAreaList = areaDAO.findByProperty("areaCode", strLocation.trim());
                String pstnCode = "";
                if (tempAreaList != null && tempAreaList.size() > 0) {
                    pstnCode = tempAreaList.get(0).getPstnCode();
                }
                hashMapResult.put(arrTarget[1], pstnCode);
            }

            /**if (strLocation != null && !strLocation.trim().equals("")) {
            String strQuery = "select dslamId, (code || ' - ' || name) as name from Dslam where province = ? and productId = ? and status = ? order by nlssort(code,'nls_sort=Vietnamese') asc";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, strLocation);
            query.setParameter(1, Constant.PRODUCT_ID_DLU);
            query.setParameter(2, Constant.STATUS_USE);
            List listDslams = query.list();

            String[] header = {"", "--Chọn DLU--"};
            List tmpList = new ArrayList();
            tmpList.add(header);
            tmpList.addAll(listDslams);

            AreaDAO areaDAO = new AreaDAO();
            areaDAO.setSession(getSession());
            List<Area> tempAreaList = areaDAO.findByProperty("areaCode", strLocation);
            String pstnCode = tempAreaList.get(0).getPstnCode();

            //them vao ket qua tra ve, cap nhat 2 vung, danh sach cac dlu va dau ma tinh
            hashMapResult.put(arrTarget[0], tmpList);
            hashMapResult.put(arrTarget[1], pstnCode);
            } else {
            String[] header = {"", "--Chọn DLU--"};
            List tmpList = new ArrayList();
            tmpList.add(header);

            //them vao ket qua tra ve, cap nhat 2 vung, danh sach cac dlu va dau ma tinh
            hashMapResult.put(arrTarget[0], tmpList);
            hashMapResult.put(arrTarget[1], "");
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return "success";
    }

    private String copyString(String tempEndStockPstn, String replace) {
        String output = "";
        for (int i = 0; i < tempEndStockPstn.length(); i++) {
            output += replace;
        }
        return output;
    }

    /**
     *
     * author tamdt1
     * date: 07/05/2009
     * lay danh sach cac so PSTN thuoc ve 1 DLU
     *
     */
    public String listStockIsdnPstn() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();
        getReqSession();
        List<StockIsdnPstn> listStockIsdnPstns = new ArrayList<StockIsdnPstn>();
        ImportPSTNForm importPSTNForm = (ImportPSTNForm) form;
        String pageForward = "pageNavigatorForList";
        Long dslamId = -1L;
        try {

            //String strSelectedDslamId = httpServletRequest.getParameter("selectedDslamId");
            String strSelectedDslamId = importPSTNForm.getDluId();

            String dslamCode = importPSTNForm.getDslamCode();
//            if (!(dslamCode != null && !dslamCode.trim().equals(""))) {
//                httpServletRequest.setAttribute("messageList", "Chưa nhập mã DLU!");
//                return pageForward;
//            }
            DslamDAO dslamDAO = new DslamDAO();
            dslamDAO.setSession(this.getSession());
            List<Dslam> listDslam = dslamDAO.findByProperty("code", dslamCode.trim().toUpperCase());

            if (dslamCode != null && !dslamCode.trim().equals("") && !(listDslam != null && listDslam.size() > 0)) {
                httpServletRequest.setAttribute("messageList", "ERR.ISN.148");
                return pageForward;
            }

            if (listDslam != null && listDslam.size() > 0) {
                strSelectedDslamId = listDslam.get(0).getDslamId().toString();
                dslamId = Long.valueOf(strSelectedDslamId);
            } else {

                dslamId = -1L;

            }


            String tempStockPstnHeader = importPSTNForm.getStockPstnHeader() != null ? importPSTNForm.getStockPstnHeader().trim() : "";
            String tempStartStockPstn = importPSTNForm.getStartStockPstn();
            String tempEndStockPstn = importPSTNForm.getEndStockPstn();
            if (tempStartStockPstn == null && tempEndStockPstn != null) {
                tempStartStockPstn = copyString(tempEndStockPstn, "0");
            }
            if (tempEndStockPstn == null && tempStartStockPstn != null) {
                tempStartStockPstn = copyString(tempEndStockPstn, "9");
            }

            String startStockPstn = tempStockPstnHeader + tempStartStockPstn;
            String endStockPstn = tempStockPstnHeader + tempEndStockPstn;
            String pstnCode = importPSTNForm.getStockPstnDistrict();
            Long startIsdn = Long.valueOf(pstnCode + startStockPstn);
            Long endIsdn = Long.valueOf(pstnCode + endStockPstn);
            //Long startPort = Long.valueOf(importPSTNForm.getStartPortPstn());
            //Long endPort = Long.valueOf(importPSTNForm.getEndPortPstn());



            List listParameter = new ArrayList();

            //StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.areaCode, a.fullName) ");

//Long id, String isdn, Long port, Long module, Long rack, Long shelf, Long slot, String deviceType, Long status, Date createDate, Long das, String dslamCode

            String strQuery = " select new com.viettel.im.database.BO.StockIsdnPstn(a.id, a.isdn, a.port, a.module, a.rack, a.shelf, a.slot, a.deviceType, a.status, a.createDate, a.das, (select b.code from Dslam b where b.dslamId = a.dslamId),a.isdnType ) from StockIsdnPstn a where 1=1";


            if (dslamId != null && dslamId != -1L) {
                strQuery += " and a.dslamId = ? ";
                listParameter.add(dslamId);
            } else {
                if (this.form.getProvinceCode() != null && !"".equals(this.form.getProvinceCode())) {
                    strQuery += " and exists(select 1 from Dslam d where d.dslamId = a.dslamId and lower(d.province)= ?) ";
                    listParameter.add(this.form.getProvinceCode().toLowerCase());
                }

            }
            if (importPSTNForm.getStartStockPstn() != null && !importPSTNForm.getStartStockPstn().trim().equals("")) {
                listParameter.add(startIsdn.toString());
                strQuery += " and a.isdn >= ? ";
            }
            if (importPSTNForm.getEndStockPstn() != null && !importPSTNForm.getEndStockPstn().trim().equals("")) {
                listParameter.add(endIsdn.toString());
                strQuery += " and a.isdn <= ? ";
            }
            if (importPSTNForm.getStartPortPstn() != null && !importPSTNForm.getStartPortPstn().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getStartPortPstn().trim()) < 0) {
                        httpServletRequest.setAttribute("message", "PSTN.error.StartPort");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getStartPortPstn().trim()));
                        strQuery += " and a.port >= ? ";
                    }
                } catch (NumberFormatException e) {
                    httpServletRequest.setAttribute("message", "BookType.error.StartPortFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getEndPortPstn() != null && !importPSTNForm.getEndPortPstn().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getEndPortPstn().trim()) < 0) {
                        httpServletRequest.setAttribute("message", "PSTN.error.EndPort");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getEndPortPstn().trim()));
                        strQuery += " and a.port <= ? ";
                    }
                } catch (NumberFormatException e) {
                    httpServletRequest.setAttribute("message", "BookType.error.EndPortFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getPstnModule() != null && !importPSTNForm.getPstnModule().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getPstnModule().trim()) < 0) {
                        httpServletRequest.setAttribute("message", "PSTN.error.Module");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getPstnModule().trim()));
                        strQuery += " and a.module = ? ";
                    }
                } catch (NumberFormatException e) {
                    httpServletRequest.setAttribute("message", "BookType.error.ModuleFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getPstnRack() != null && !importPSTNForm.getPstnRack().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getPstnRack().trim()) < 0) {
                        httpServletRequest.setAttribute("message", "PSTN.error.Rack");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getPstnRack().trim()));
                        strQuery += " and a.rack = ? ";
                    }
                } catch (NumberFormatException e) {
                    httpServletRequest.setAttribute("message", "BookType.error.RackFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getPstnShelf() != null && !importPSTNForm.getPstnShelf().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getPstnShelf().trim()) < 0) {
                        httpServletRequest.setAttribute("message", "PSTN.error.Shelf");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getPstnShelf().trim()));
                        strQuery += " and a.shelf = ? ";
                    }
                } catch (NumberFormatException e) {
                    httpServletRequest.setAttribute("message", "BookType.error.ShelfFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getPstnSlot() != null && !importPSTNForm.getPstnSlot().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getPstnSlot().trim()) < 0) {
                        httpServletRequest.setAttribute("message", "PSTN.error.Slot");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getPstnSlot().trim()));
                        strQuery += " and a.slot = ? ";
                    }
                } catch (NumberFormatException e) {
                    httpServletRequest.setAttribute("message", "BookType.error.SlotFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getPstnDas() != null && !importPSTNForm.getPstnDas().trim().equals("")) {
                try {
                    if (Long.parseLong(importPSTNForm.getPstnDas().trim()) < 0) {
                        httpServletRequest.setAttribute("message", "PSTN.error.Das");
                        return pageForward;
                    } else {
                        listParameter.add(Long.parseLong(importPSTNForm.getPstnDas().trim()));
                        strQuery += " and a.das = ? ";
                    }
                } catch (NumberFormatException e) {
                    httpServletRequest.setAttribute("message", "BookType.error.DasFormat");
                    return pageForward;
                }
            }
            if (importPSTNForm.getPstnDeviceType().trim() != null && !importPSTNForm.getPstnDeviceType().trim().equals("")) {
                listParameter.add(importPSTNForm.getPstnDeviceType().trim());
                strQuery += " and a.deviceType = ? ";
            }

            if (importPSTNForm.getStatus().trim() != null && !importPSTNForm.getStatus().trim().equals("")) {
                listParameter.add(Long.parseLong(importPSTNForm.getStatus().trim()));
                strQuery += " and a.status = ? ";
            }

             if (importPSTNForm.getIsdnType().trim() != null && !importPSTNForm.getIsdnType().trim().equals("")) {
                listParameter.add(importPSTNForm.getIsdnType().trim());
                strQuery += " and a.isdnType = ? ";
            }


            strQuery += " and rownum < ? ";
            listParameter.add(MAX_RESULT);

            strQuery += " order by a.dslamId, a.module, a.rack, a.shelf, a.slot, a.port ";

            Query query = getSession().createQuery(strQuery);
            for (int i = 0;
                    i < listParameter.size();
                    i++) {
                query.setParameter(i, listParameter.get(i));
            }
            listStockIsdnPstns =
                    query.setMaxResults(Constant.MAX_RESULT_PSTN).list();


        } catch (Exception e) {
            e.printStackTrace();
        }




        if (listStockIsdnPstns != null && listStockIsdnPstns.size() != 0) {
            req.setAttribute("messageList", getText("MSG.ISN.052")+ " "+ String.valueOf(listStockIsdnPstns.size())+ " " + getText("MSG.result"));
        } else {
            req.setAttribute("messageList", "MSG.doNotFindData");
        }
        //httpServletRequest.getSession().setAttribute(SESSION_LIST_STOCK_ISDN_PSTN, listStockIsdnPstns);

        httpServletRequest.setAttribute(SESSION_LIST_STOCK_ISDN_PSTN, listStockIsdnPstns);
        setTabSession("editAndDeletePstnToDLU", ResourceBundleUtils.getResource("editAndDeletePstnToDLU"));
        return pageForward;
    }

    /**
     *
     * author tamdt1
     * date: 07/05/2009
     * lay danh sach cac so PSTN thuoc ve 1 DLU
     *
     */
    private List<StockIsdnPstn> getListStockIsdnPstnOfDlu(Long dslamId) throws Exception {
        List<StockIsdnPstn> listStockIsdnPstns = new ArrayList<StockIsdnPstn>();


        if (dslamId == null) {
            return listStockIsdnPstns;


        }

        try {
            String strQuery = "from StockIsdnPstn where dslamId = ? and status = ? and rownum < ? order by to_number(isdn) ";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, dslamId);
            query.setParameter(1, Constant.STATUS_USE);
            query.setParameter(2, MAX_RESULT);
            listStockIsdnPstns =
                    query.list();


        } catch (Exception e) {
            e.printStackTrace();


        }

        return listStockIsdnPstns;


    }

    /**
     *Ham refresh lai trang
     * author tamdt1
     * date: 07/05/2009
     *
     */
    public String refreshListIsdnPstn() throws Exception {



        getRequest().setAttribute(REQUEST_MESSAGE_LIST, "assignPstnToDlu.success.editIsdnPstn");

        String pageForward = "pageNavigatorForList";
        getSession().clear();


        this.listStockIsdnPstn();


        return pageForward;



    }

    /*
     * Action Xoa nhieu so PSTN duoc gan
     * Author NamLT
     * Date 22/7/2010
     */
    public String actionDeletePstnDLU() throws Exception {

        String[] arrSelectedItem = this.form.getSelectedItems();
        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute("userToken");
        String pageForward = "pageNavigator";

        try {
            if (arrSelectedItem == null || arrSelectedItem.length <= 0
                    || (arrSelectedItem.length == 1 && arrSelectedItem[0].equals("false"))) {
                getRequest().setAttribute(REQUEST_MESSAGE, "Chưa chọn bản ghi nào để xóa");
                log.info("End contractCOMM of ContractFeesDAO");
            } else {
                for (int i = 0; i < arrSelectedItem.length; i++) {
                    String strId = arrSelectedItem[i];
                    StockIsdnPstn stockIsdnPstn = this.getStockIsdnPstnById(Long.parseLong(strId));

                    if (stockIsdnPstn != null) {
                        stockIsdnPstn.setPort(null);
                        stockIsdnPstn.setModule(null);
                        stockIsdnPstn.setRack(null);
                        stockIsdnPstn.setShelf(null);
                        stockIsdnPstn.setSlot(null);
                        stockIsdnPstn.setDeviceType(null);
                        stockIsdnPstn.setDas(null);
                        stockIsdnPstn.setDslamId(null);
                        stockIsdnPstn.setLastUpdateIpAddress(getRequest().getRemoteAddr());
                        stockIsdnPstn.setLastUpdateKey(Constant.LAST_UPDATE_KEY);
                        stockIsdnPstn.setLastUpdateTime(new java.sql.Date(DateTimeUtils.getSysDate().getTime()));
                        stockIsdnPstn.setLastUpdateUser(userToken.getLoginName());


                        getSession().update(stockIsdnPstn);
                    }



                }
                req.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.success.delIsdnPstn");

            }
            this.listStockIsdnPstn();
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }

        return pageForward;

    }

    /**
     *
     * author tamdt1
     * date: 08/05/2009
     * kiem tra tinh hop le cua cac thong tin nhap vao
     *
     */
    private boolean checkValidStockIsdnPstnToAdd() {
        try {
            HttpServletRequest httpServletRequest = getRequest();

            String location = this.form.getProvinceCode();
            String dslamCode = this.form.getDslamCode();
            String startStockPstn = this.form.getStartStockPstn();
            String endStockPstn = this.form.getEndStockPstn();
            String startPortPstn = this.form.getStartPortPstn();
            String endPortPstn = this.form.getEndPortPstn();

            String pstnModule = this.form.getPstnModule();
            String pstnRack = this.form.getPstnRack();
            String pstnSelf = this.form.getPstnShelf();
            String pstnSlot = this.form.getPstnSlot();

            String pstnDas = this.form.getPstnDas();
            String pstnDeviceType = this.form.getPstnDeviceType();


            //kiem tra dieu kien cua cac truong bat buoc


            if (location == null || location.trim().equals("")
                    || dslamCode == null || dslamCode.trim().equals("")
                    || startStockPstn == null || startStockPstn.trim().equals("")
                    || endStockPstn == null || endStockPstn.trim().equals("")
                    || startPortPstn == null || startPortPstn.trim().equals("")
                    || endPortPstn == null || endPortPstn.trim().equals("")
                    || pstnModule == null || pstnModule.trim().equals("")
                    || pstnRack == null || pstnRack.trim().equals("")
                    || pstnSelf == null || pstnSelf.trim().equals("")
                    || pstnSlot == null || pstnSlot.trim().equals("")
                    || pstnDas == null || pstnDas.trim().equals("")
                    ) {

                httpServletRequest.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.requiredFieldsEmpty");


                return false;


            }

            HashMap<String, Object> hashMap0 = new HashMap();
            hashMap0.put("areaCode", location.trim().toUpperCase());
            hashMap0.put("IS_NULL.parentCode", "");
            List<Dslam> tempAreaList = CommonDAO.findByProperty(getSession(), "Area", hashMap0);


            if (!(tempAreaList != null && tempAreaList.size() > 0)) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "ERR.ISN.138");


                return false;


            }

            HashMap<String, Object> hashMap1 = new HashMap();
            hashMap1.put("code", (dslamCode.trim()));
            hashMap1.put("status", Constant.STATUS_USE);
            hashMap1.put("productId", Constant.PRODUCT_ID_DLU);
            List<Dslam> tempDslamList = CommonDAO.findByProperty(getSession(), "Dslam", hashMap1);


            if (!(tempDslamList != null && tempDslamList.size() > 0)) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "ERR.ISN.135");


                return false;


            }







            //kiem tra chieu dai so dau dai va so cuoi dai
            if (startStockPstn.trim().length() != endStockPstn.trim().length()) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.startAndEndIsdnDifferent");


                return false;


            }

            String stockPstnDistrict = this.form.getStockPstnDistrict() != null ? this.form.getStockPstnDistrict().trim() : "";
            String strStockPstnHeader = this.form.getStockPstnHeader() != null ? this.form.getStockPstnHeader().trim() : "";

            Long startIsdn;

            Long endIsdn;

            Long startPort;

            Long endPort;



            try {
                startIsdn = Long.valueOf(stockPstnDistrict + strStockPstnHeader + startStockPstn.trim());
                endIsdn =
                        Long.valueOf(stockPstnDistrict + strStockPstnHeader + endStockPstn.trim());
                startPort =
                        Long.valueOf(startPortPstn.trim());
                endPort =
                        Long.valueOf(endPortPstn.trim());


            } catch (Exception ex) {
                ex.printStackTrace();
                startIsdn =
                        -1L;
                endIsdn =
                        -1L;
                startPort =
                        -1L;
                endPort =
                        -1L;


            } //kiem tra cac so phai la so duong, so cuoi > so dau
            if (startIsdn < 0 || endIsdn < 0 || startPort < 0 || endPort < 0) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.isdnOrPortNegative");


                return false;


            } //so cuoi dai phai lon hon so dau
            if (startIsdn.compareTo(endIsdn) > 0) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.startIsdnGreaterEndIsdn");


                return false;


            } //
            if (startPort.compareTo(endPort) > 0) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.startPortGreaterEndPort");


                return false;


            } //kiem tra so luong port va so luong isdn
            if ((endPort - startPort) != (endIsdn - startIsdn)) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.isdnAndPortDifferent");


                return false;


            } //kiem tra so luong so can gan trong dai co vuot qua so luong cho phep ko
            Long maxIsdnAssignToDlu = -1L;


            try {
                String strMaxIsdnAssignToDlu = ResourceBundleUtils.getResource("MAX_ISDN_ASSIGN_TO_DLU");
                maxIsdnAssignToDlu =
                        Long.valueOf(strMaxIsdnAssignToDlu);


            } catch (Exception ex) {
                ex.printStackTrace();
                maxIsdnAssignToDlu =
                        -1L;


            }

            if ((endIsdn - startIsdn + 1) > maxIsdnAssignToDlu) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.quantityOverMaximum");
                List listParam = new ArrayList();
                listParam.add(maxIsdnAssignToDlu);
                httpServletRequest.setAttribute(REQUEST_MESSAGE_PARAM, listParam);



                return false;


            }

//kiem tra khong cho sua thong tin cac so dang su dung
//            String strQuery = "select count(*) from StockIsdnPstn "
//                    + "where "
//                    + "to_number(isdn) >= ? and to_number(isdn) <= ? and (status=? or status=?) and dslamId is not null ";
//
//            Query query = getSession().createQuery(strQuery);
//            query.setParameter(0, startIsdn);
//            query.setParameter(1, endIsdn);
//            query.setParameter(2, Constant.STATUS_ISDN_USING);
//            query.setParameter(3, Constant.STATUS_ISDN_LOCK);
//
//
//
//            Long count = 0L;
//            List list = query.list();
//
//
//            if (list != null && list.size() > 0) {
//                count = (Long) list.get(0);
//
//
//            }
//
//            if (count.compareTo(0L) > 0) {
//                httpServletRequest.setAttribute(REQUEST_MESSAGE, "!!!.Lỗi số ISDN đang được sử dụng hoặc đang bị khóa");
//
//
//
//                return false;
//
//
//            }

            //Check so ISDN khong ton tai

            String strCheckIsdnQuery = "select count(*) from StockIsdnPstn "
                    + "where "
                    + "to_number(isdn) >= ? and to_number(isdn) <= ? ";

            Query checkIsdnQuery = getSession().createQuery(strCheckIsdnQuery);
            checkIsdnQuery.setParameter(0, startIsdn);
            checkIsdnQuery.setParameter(1, endIsdn);




            Long countIsdn = 0L;
            List listCheckIsdn = checkIsdnQuery.list();


            if (listCheckIsdn != null && listCheckIsdn.size() > 0) {
                countIsdn = (Long) listCheckIsdn.get(0);


            }

            if (countIsdn != (endIsdn - startIsdn + 1)) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, getText("ERR.ISN.152"));



                return false;


            }

            Dslam dslam = getDslamByCode(dslamCode);

            String province = dslam != null ? dslam.getProvince() : "";

            if (!province.equals(location)) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, getText("ERR.ISN.141") + location);
                return false;

            }

////kiem tra tinh hop le cua dai so
////      - dai so hop le la dai so dang o trang thai so moi hoac so ngung su dung
//            strQuery = "select count(*) from StockIsdnPstn "
//                    + "where "
//                    + "to_number(isdn) >= ? and to_number(isdn) <= ? and dslamId is null and status in (? , ? ) ";
//
//            //danh sach cac trang thai hop le
////        List<Long> listValidStatus = new ArrayList<Long>();
////        listValidStatus.add(Constant.STATUS_ISDN_NEW); //so moi
////        listValidStatus.add(Constant.STATUS_ISDN_SUSPEND); //so ngung su dung
//
//            query =
//                    getSession().createQuery(strQuery);
//            query.setParameter(0, startIsdn);
//            query.setParameter(1, endIsdn);
//            query.setParameter(2, Constant.STATUS_ISDN_NEW);//so moi
//            query.setParameter(3, Constant.STATUS_ISDN_SUSPEND);//so ngung su dung
//            //query.setParameterList("listValidStatus", listValidStatus);
//
//            count =
//                    0L;
//            list =
//                    query.list();
//            if (list != null && list.size() > 0) {
//                count = (Long) list.get(0);
//            }
//
//            Long numberIsdn = endIsdn - startIsdn + 1;
//
//            if (count.compareTo(numberIsdn) != 0) {
//
//                httpServletRequest.setAttribute(REQUEST_MESSAGE, "!!!.Lỗi dải số không hợp lệ,tồn tại số đã được sử dụng");
//
//                return false;
//            }


            //kiem tra port da duoc su dung hay chua
            String strQuery = "select count(*) from StockIsdnPstn "
                    + "where "
                    + "dslamId = ? and port >= ? and port <= ? and module = ? and rack = ? and shelf = ? and slot = ? ";

            Query query =
                    getSession().createQuery(strQuery);
            query.setParameter(0, tempDslamList.get(0).getDslamId());
            query.setParameter(1, startPort);
            query.setParameter(2, endPort);

//        String pstnModule = this.form.getPstnModule();
//        String pstnRack = this.form.getPstnRack();
//        String pstnSelf = this.form.getPstnShelf();
//        String pstnSlot = this.form.getPstnSlot();



            try {
                query.setParameter(3, Long.valueOf(pstnModule.trim()));
                query.setParameter(4, Long.valueOf(pstnRack.trim()));
                query.setParameter(5, Long.valueOf(pstnSelf.trim()));
                query.setParameter(6, Long.valueOf(pstnSlot.trim()));


            } catch (Exception ex) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "ERR.ISN.125");


                return false;


            }


            Long count =
                    0L;
            List list =
                    query.list();


            if (list != null && list.size() > 0) {
                count = (Long) list.get(0);


            }

            if (count.compareTo(0L) > 0) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.duplicatePort");


                return false;



            }

            this.form.setLocation(location.trim().toUpperCase());


            this.form.setDslamCode(dslamCode.trim());

            //GAN GIA TRI DSLAM_ID


            this.form.setDluId(tempDslamList.get(0).getDslamId().toString());



            this.form.setStartStockPstn(startStockPstn.trim());


            this.form.setEndStockPstn(endStockPstn.trim());


            this.form.setStartPortPstn(startPortPstn.trim());


            this.form.setEndPortPstn(endPortPstn.trim());


            this.form.setPstnModule(pstnModule.trim());


            this.form.setPstnRack(pstnRack.trim());


            this.form.setPstnShelf(pstnSelf.trim());


            this.form.setPstnSlot(pstnSlot.trim());


            this.form.setPstnDas(pstnDas.trim());


            this.form.setPstnDeviceType(pstnDeviceType.trim());



            return true;



        } catch (Exception ex) {
            return false;


        }
    }

    /**
     *
     * author tamdt1
     * date: 08/05/2009
     * kiem tra tinh hop le cua cac thong tin nhap vao truoc khi edit thong tin
     *
     */
    private boolean checkValidStockIsdnPstnToEdit() {
        try {
            HttpServletRequest httpServletRequest = getRequest();
            Dslam dslam = getDslamByCode(this.form.getDslamCode());


            Long dslamId = dslam.getDslamId();
            String stockPstnNumber = this.form.getStockPstnNumber();
            String startPortPstn = this.form.getStartPortPstn();
            // String endPortPstn = this.form.getEndPortPstn();
            String pstnModule = this.form.getPstnModule();
            String pstnRack = this.form.getPstnRack();
            String pstnSelf = this.form.getPstnShelf();
            String pstnSlot = this.form.getPstnSlot();
            String pstnDas = this.form.getPstnDas();
            String pstnDeviceType = this.form.getPstnDeviceType();

            StockIsdnPstn stockIsdnPstn = getStockIsdnPstnByIsdn(stockPstnNumber);


            //kiem tra dieu kien cua cac truong bat buoc


            if (dslamId == null
                    || stockPstnNumber == null || stockPstnNumber.trim().equals("")
                    || startPortPstn == null || startPortPstn.trim().equals("")
                    //|| endPortPstn == null || endPortPstn.trim().equals("")
                    || pstnModule == null || pstnModule.trim().equals("")
                    || pstnRack == null || pstnRack.trim().equals("")
                    || pstnSelf == null || pstnSelf.trim().equals("")
                    || pstnSlot == null || pstnSlot.trim().equals("")
                    || pstnDas == null || pstnDas.trim().equals("")
                    ) {

                httpServletRequest.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.requiredFieldsEmpty");


                return false;


            }

//        HashMap<String, Object> hashMap0 = new HashMap();
//        hashMap0.put("areaCode", location.trim().toUpperCase());
//        hashMap0.put("IS_NULL.parentCode","");
//        List<Dslam> tempAreaList = CommonDAO.findByProperty(getSession(), "Area", hashMap0);
//        if (tempAreaList != null && tempAreaList.size()>0){
//            httpServletRequest.setAttribute(REQUEST_MESSAGE, "Lỗi mã tỉnh sai");
//            return false;
//        }

            HashMap<String, Object> hashMap1 = new HashMap();
            hashMap1.put("dslamId", dslamId);
            hashMap1.put("status", Constant.STATUS_USE);
            hashMap1.put("productId", Constant.PRODUCT_ID_DLU);
            List<Dslam> tempDslamList = CommonDAO.findByProperty(getSession(), "Dslam", hashMap1);


            if (tempDslamList == null || tempDslamList.size() == 0) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "ERR.ISN.139");


                return false;


            }

            Long pstnNumber;
            Long startPort;
            Long endPort;



            try {
                pstnNumber = Long.valueOf(stockPstnNumber.trim());
                startPort =
                        Long.valueOf(startPortPstn.trim());
//                endPort =
//                        Long.valueOf(endPortPstn.trim());


            } catch (Exception ex) {
                ex.printStackTrace();
                pstnNumber =
                        -1L;
                startPort =
                        -1L;
//                endPort =
//                        -1L;


            } //kiem tra cac so phai la so duong, so cuoi > so dau
            if (pstnNumber <= 0 || startPort <= 0) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.isdnAndPortDifferent");


                return false;


            }

//kiem tra port da duoc su dung hay chua
            String strQuery = "from StockIsdnPstn "
                    + "where "
                    + "dslamId = ? and port = ? and module = ? and rack = ? and shelf = ? and slot = ? ";

            Query query =
                    getSession().createQuery(strQuery);
            query.setParameter(0, tempDslamList.get(0).getDslamId());
            query.setParameter(1, startPort);
            //  query.setParameter(2, endPort);

//        String pstnModule = this.form.getPstnModule();
//        String pstnRack = this.form.getPstnRack();
//        String pstnSelf = this.form.getPstnShelf();
//        String pstnSlot = this.form.getPstnSlot();

            query.setParameter(2, Long.parseLong(pstnModule.trim()));
            query.setParameter(3, Long.parseLong(pstnRack.trim()));
            query.setParameter(4, Long.parseLong(pstnSelf.trim()));
            query.setParameter(5, Long.parseLong(pstnSlot.trim()));

            // Long count = 0L;
            List<StockIsdnPstn> list = (List<StockIsdnPstn>) query.list();
//            if (list != null && list.size() > 0) {
//                count = (Long) list.get(0);
//            }




            if (!list.isEmpty() && (list.get(0).getId() != stockIsdnPstn.getId())) {
                httpServletRequest.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.duplicatePort");


                return false;


            } // this.form.setDslamCode(dslamCode.trim());

            this.form.setDluId(tempDslamList.get(0).getDslamId().toString());



            this.form.setStockPstnNumber(stockPstnNumber.trim());


            this.form.setStartPortPstn(startPortPstn.trim());


            this.form.setPstnModule(pstnModule.trim());


            this.form.setPstnRack(pstnRack.trim());


            this.form.setPstnShelf(pstnSelf.trim());


            this.form.setPstnDas(pstnDas.trim());


            this.form.setPstnDeviceType(pstnDeviceType.trim());



            return true;



        } catch (Exception ex) {
            return false;


        }
    }

//    private Boolean CheckExistIsdnPstn(List<StockIsdnPstn> list, Long dslamId, Long port, Long module, Long rack, Long shelf, Long slot) {
//
//        Boolean check=false;
//        for (int i = 0; i < list.size(); i++) {
//
//            if (list.get(i).getDslamId() != dslamId
//                    && list.get(i).getPort() != port
//                    && list.get(i).getModule() != module
//                    && list.get(i).getRack() != rack
//                    && list.get(i).getShelf() != shelf
//                    && list.get(i).getSlot() != slot) {
//                check= true;
//            } else {
//                check= false;
//            }
//        }
//        return check;
//    }
//    /**
//     *
//     * author tamdt1
//     * date: 08/05/2009
//     * kiem tra su ton tai cua dai so
//     *
//     */
//    private boolean checkExistIsdnRange(Long startIsdn, Long endIsdn) {
//        HttpServletRequest httpServletRequest = getRequest();
//        //kiem tra su ton tai cua dai so
//        Long quantity = endIsdn - startIsdn + 1; //so luong isdn trong dai so nguoi dung nhap vao
//        String strQuery = "from StockIsdnPstn where to_number(isdn) >= ? and to_number(isdn) <= ?";
//        Query query = getSession().createQuery(strQuery);
//        query.setParameter(0, startIsdn);
//        query.setParameter(1, endIsdn);
//        List<StockIsdnPstn> listStockIsdnPstns = query.list();
//        if(listStockIsdnPstns.size() != quantity.intValue()) {
//            //so luong isdn trong dai so nhap vao khong duoc tim thay trong CSDL
//            httpServletRequest.setAttribute(REQUEST_MESSAGE, "assignPstnToDlu.error.isdnRangeNotExist");
//            return false;
//        }
//
//        return true;
//    }
    /**
     *
     * author tamdt1
     * date: 07/05/2009
     * lay danh sach cac tinh/tp
     *
     */
    private List<Area> getListProvinces() throws Exception {
        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(getSession());
        List<Area> listAreas = areaDAO.findAllProvince();



        return listAreas;


    }

    private List<Area> getProvinceByCode(String provinceCode) {
        AreaDAO areaDAO = new AreaDAO();
        areaDAO.setSession(getSession());
        List<Area> listAreas = areaDAO.findByProperty("province", provinceCode);


        return listAreas;


    }

//    private List<Area> getPstnCodeByProvince(String province) {
//
//        AreaDAO areaDAO = new AreaDAO();
//        areaDAO.setSession(getSession());
//        List<Area> listPstnCode = areaDAO.findByProperty("province", provinceCode);
//
//
//        return listAreas;
//
//    }
    /**
     *
     * author tamdt1
     * date: 07/05/2009
     * lay danh sach cac tram DLU cua 1 tinh/tp
     *
     */
    private List<Dslam> getListDluByProvince(String strLocation) throws Exception {
        List<Dslam> listDslams = new ArrayList<Dslam>();


        if (strLocation == null || strLocation.trim().equals("")) {
            return listDslams;


        }

        try {
            String strQuery = "from Dslam where province = ? and productId = ? order by nlssort(name,'nls_sort=Vietnamese') asc";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, strLocation);
            query.setParameter(1, Constant.PRODUCT_ID_DLU);
            listDslams =
                    query.list();


        } catch (Exception e) {
            e.printStackTrace();


            throw e;


        }

        return listDslams;


    }

    /**
     *
     * author tamdt1
     * date: 08/05/2009
     * lay dslam co dslamId = id
     *
     */
    private Dslam getDslamById(Long dslamId) throws Exception {
        Dslam dslam = null;



        if (dslamId == null) {
            return dslam;


        }

        try {
            String strQuery = "from Dslam where dslamId = ? and status = ?";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, dslamId);
            query.setParameter(1, Constant.STATUS_USE);
            List<Dslam> listDslams = query.list();


            if (listDslams != null && listDslams.size() > 0) {
                dslam = listDslams.get(0);


            }

        } catch (Exception e) {
            e.printStackTrace();


            throw e;


        }

        return dslam;


    }

    private Dslam getDslamByCode(String dslamCode) throws Exception {
        Dslam dslam = null;



        if (dslamCode == null) {
            return dslam;


        }

        try {
            String strQuery = "from Dslam where code = ? and status = ? and productId = ?";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, dslamCode);
            query.setParameter(1, Constant.STATUS_USE);
            query.setParameter(2, Constant.PRODUCT_ID_DLU);
            List<Dslam> listDslams = (List<Dslam>) query.list();


            if (listDslams != null && listDslams.size() > 0) {
                dslam = listDslams.get(0);


            }

        } catch (Exception e) {
            e.printStackTrace();


            throw e;


        }

        return dslam;


    }

    private Area getPstnCodeByProvince(String province) throws Exception {
        Area area = null;



        if (province == null) {
            return area;


        }

        try {
            String strQuery = "from Area where district IS NULL and precinct IS NULL and lower(province) like ?  ";

            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, province.toLowerCase());
            //query.setParameter(1, Constant.STATUS_USE);
            List<Area> listPstnCode = query.list();


            if (listPstnCode != null && listPstnCode.size() > 0) {
                area = listPstnCode.get(0);


            }

        } catch (Exception e) {
            e.printStackTrace();


            throw e;


        }

        return area;


    }

    /**
     *
     * author tamdt1
     * date: 08/05/2009
     * lay stockIsdnPstn co isdn = isdn
     *
     */
    private StockIsdnPstn getStockIsdnPstnByIsdn(String isdn) {
        StockIsdnPstn stockIsdnPstn = null;



        if (isdn == null || isdn.trim().equals("")) {
            return stockIsdnPstn;


        }

        try {
            String strQuery = "from StockIsdnPstn where isdn = ?";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, isdn.trim());
            List<StockIsdnPstn> listStockIsdnPstns = query.list();


            if (listStockIsdnPstns != null && listStockIsdnPstns.size() > 0) {
                stockIsdnPstn = listStockIsdnPstns.get(0);


            }

        } catch (Exception e) {
            e.printStackTrace();


        }

        return stockIsdnPstn;


    }

    /**
     *
     * author tamdt1
     * date: 08/05/2009
     * lay stockIsdnPstn co id = stockIsdnPstnId
     *
     */
    private StockIsdnPstn getStockIsdnPstnById(Long stockIsdnPstnId) {
        StockIsdnPstn stockIsdnPstn = null;



        if (stockIsdnPstnId == null) {
            return stockIsdnPstn;


        }

        try {
            String strQuery = "from StockIsdnPstn where id = ?";
            Query query = getSession().createQuery(strQuery);
            query.setParameter(0, stockIsdnPstnId);
            List<StockIsdnPstn> listStockIsdnPstns = query.list();


            if (listStockIsdnPstns != null && listStockIsdnPstns.size() > 0) {
                stockIsdnPstn = listStockIsdnPstns.get(0);


            }

        } catch (Exception e) {
            e.printStackTrace();


        }

        return stockIsdnPstn;


    }

    /**
     *
     * author tamdt1
     * date: 08/05/2009
     * lay stockIsdnPstn co id = stockIsdnPstnId trong bien session
     *
     */
    private StockIsdnPstn getStockIsdnPstnInListByIsdn(String isdn) {
        StockIsdnPstn stockIsdnPstn = null;



        if (isdn == null || isdn.trim().equals("")) {
            return stockIsdnPstn;


        }

        List<StockIsdnPstn> listStockIsdnPstns = (List<StockIsdnPstn>) getRequest().getSession().getAttribute(SESSION_LIST_STOCK_ISDN_PSTN);


        if (listStockIsdnPstns != null && listStockIsdnPstns.size() > 0) {
            for (int i = 0; i
                    < listStockIsdnPstns.size(); i++) {
                StockIsdnPstn tmpStockIsdnPstn = listStockIsdnPstns.get(i);


                if (tmpStockIsdnPstn.getIsdn().equals(isdn.trim())) {
                    stockIsdnPstn = tmpStockIsdnPstn;


                    break;



                }






            }
        }

        return stockIsdnPstn;


    }

    /**
     *
     * author tamdt1
     * date: 08/05/2009
     * lay stockIsdnPstn co id = stockIsdnPstnId trong bien session
     *
     */
    private StockIsdnPstn getStockIsdnPstnInListById(Long id) {
        StockIsdnPstn stockIsdnPstn = null;



        if (id == null) {
            return stockIsdnPstn;


        }

        List<StockIsdnPstn> listStockIsdnPstns = (List<StockIsdnPstn>) getRequest().getSession().getAttribute(SESSION_LIST_STOCK_ISDN_PSTN);


        if (listStockIsdnPstns != null && listStockIsdnPstns.size() > 0) {
            for (int i = 0; i
                    < listStockIsdnPstns.size(); i++) {
                StockIsdnPstn tmpStockIsdnPstn = listStockIsdnPstns.get(i);


                if (tmpStockIsdnPstn.getId().equals(id)) {
                    stockIsdnPstn = tmpStockIsdnPstn;


                    break;



                }






            }
        }

        return stockIsdnPstn;


    }
    private HashMap hashMapPstnCode = new HashMap();

    public HashMap getHashMapPstnCode() {
        return hashMapPstnCode;


    }

    public void setHashMapPstnCode(HashMap hashMapPstnCode) {
        this.hashMapPstnCode = hashMapPstnCode;


    }

    /**
     *
     * author tamdt1
     * date: 09/05/2009
     * cap nhat dau so PSTN tinh
     *
     */
    public String updatePstnCode() throws Exception {
        HttpServletRequest httpServletRequest = getRequest();

        String strLocation = httpServletRequest.getParameter("location");


        if (strLocation != null && !strLocation.trim().equals("")) {
            AreaDAO areaDAO = new AreaDAO();
            areaDAO.setSession(getSession());
            List<Area> tempAreaList = areaDAO.findByProperty("areaCode", strLocation);
            String pstnCode = tempAreaList.get(0).getPstnCode();


            this.hashMapPstnCode.put("form.stockPstnDistrict", pstnCode);


        }

        return "updatePstnCodeSuccess";


    }

    public List<ImSearchBean> getListDslam(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        queryString.append("from Dslam a where 1=1 ");
        queryString.append("and a.status = ? ");
        listParameter.add(Constant.STATUS_USE);
        queryString.append("and a.productId = ? ");
        listParameter.add(Constant.PRODUCT_ID_DLU);


        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.code) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");


        }
        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            queryString.append("and upper(a.name) like ? ");
            listParameter.add("%" + imSearchBean.getName().trim().toUpperCase() + "%");


        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            queryString.append("and upper(a.province) = ? ");
            listParameter.add(imSearchBean.getOtherParamValue().trim().toUpperCase());


        }

        queryString.append("and rownum <= ? ");
        listParameter.add(AssignShopDslamDAO.MAX_SEARCH_RESULT);
        queryString.append("order by a.code ");
        Query queryObject = getSession().createQuery(queryString.toString());


        for (int i = 0; i
                < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));


        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<ImSearchBean> tmpList = queryObject.list();


        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);


        }
        return listImSearchBean;


    }

    public Long getListDslamSize(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select count(*) ");
        queryString.append("from Dslam a where 1=1 ");
        queryString.append("and a.status = ? ");
        listParameter.add(Constant.STATUS_USE);
        queryString.append("and a.productId = ? ");
        listParameter.add(Constant.PRODUCT_ID_DLU);


        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.code) like ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase() + "%");


        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            queryString.append("and upper(a.province) = ? ");
            listParameter.add(imSearchBean.getOtherParamValue().trim().toUpperCase());


        }

        Query queryObject = getSession().createQuery(queryString.toString());


        for (int i = 0; i
                < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));


        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<Long> tmpList = queryObject.list();


        if (tmpList != null && tmpList.size() > 0) {
            return tmpList.get(0);


        } else {
            return null;


        }
    }

    public List<ImSearchBean> getListDslamName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();

        List listParameter = new ArrayList();
        StringBuffer queryString = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.code, a.name) ");
        queryString.append("from Dslam a where 1=1 ");
        queryString.append("and rownum <= ? ");
        listParameter.add(AssignShopDslamDAO.MAX_SEARCH_RESULT);
        queryString.append("and a.status = ? ");
        listParameter.add(Constant.STATUS_USE);
        queryString.append("and a.productId = ? ");
        listParameter.add(Constant.PRODUCT_ID_DLU);


        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            queryString.append("and upper(a.code) = ? ");
            listParameter.add(imSearchBean.getCode().trim().toUpperCase());


        }
        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            queryString.append("and upper(a.province) = ? ");
            listParameter.add(imSearchBean.getOtherParamValue().trim().toUpperCase());


        }
        Query queryObject = getSession().createQuery(queryString.toString());


        for (int i = 0; i
                < listParameter.size(); i++) {
            queryObject.setParameter(i, listParameter.get(i));


        }
        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        List<ImSearchBean> tmpList = queryObject.list();


        if (tmpList != null && tmpList.size() > 0) {
            listImSearchBean.addAll(tmpList);


        }

        return listImSearchBean;

    }
    /* Author: ThanhNC
     * Created date: 03/08/2010
     * Purpose: Update progress tren man hinh khi thuc hien import so
     */

    public String updateProgressDiv(HttpServletRequest req) {
        log.info("Begin updateProgressDiv of StockIsdnDAO");

        try {
            String userSessionId = req.getSession().getId();
            List<String> listMessage = this.listProgressMessage.get(userSessionId);
            if (listMessage != null && listMessage.size() > 0) {
                String message = listMessage.get(0);
                listMessage.remove(0);
                return message;
            } else {
                return "";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}