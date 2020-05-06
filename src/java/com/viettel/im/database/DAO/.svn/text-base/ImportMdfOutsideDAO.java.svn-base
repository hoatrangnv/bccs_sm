/**
 *  ImportMdfOutside
 *  @author: HaiNT41
 *  @version: 1.0
 *  @since: 1.0
 */
package com.viettel.im.database.DAO;

import com.viettel.im.database.DAO.CommonDAO;
import com.viettel.im.common.util.ResourceBundleUtils;
import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.database.BO.UserToken;
import com.viettel.im.database.BO.Area;
import com.viettel.im.database.BO.Boards;
import com.viettel.im.database.BO.CableBox;
import com.viettel.im.database.BO.Dslam;
import com.viettel.im.database.BO.PortOutside;
import com.viettel.im.common.Constant;
import com.viettel.im.common.util.DateTimeUtils;
import com.viettel.im.client.form.ImportMDFOutsideForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Query;
import jxl.Sheet;
import jxl.Workbook;
import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;


/**
 * @author haint41
 * @date 5/10/2011
 * @version    : 1.0
 * @since      : 1.0
 * @desc : Import thong tin ha tang mang ngoai vi
 */
public class ImportMdfOutsideDAO extends BaseDAOAction{
    
    private static final Log log = LogFactory.getLog(ImportMdfOutsideDAO.class);    
    private final String SESSION_LIST_MDF_OUTSIDE = "listMDFOutside";
    private final String REQUEST_MESSAGE = "message";
    private final String REQUEST_MESSAGE_PARAM = "messageParam";
    private final String IMPORT_MDF_OUTSIDE = "importMDFOutside";    
    private final String REQUEST_ERROR_FILE_PATH = "errorFilePath";    
    private int NUMBER_CMD_IN_BATCH = 100; //so luong ban ghi se commit 1 lan
    private String pageForward;
    private ImportMDFOutsideForm importMDFOutsideForm = new ImportMDFOutsideForm();
    
    public ImportMDFOutsideForm getImportMDFOutsideForm() {
        return importMDFOutsideForm;
    }

    public void setImportMDFOutsideForm(ImportMDFOutsideForm importMDFOutsideForm) {
        this.importMDFOutsideForm = importMDFOutsideForm;
    }
    
    /**
     * @author haint41
     * @date 05/10/2011
     * @desc man hinh mac dinh khi vao chuc nang 
     * @return pageForward
     * @throws Exception 
     */
    public String preparePage() throws Exception {
        log.info("begin method preparePage of ImportMdfOutsideDAO");

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {   
            this.importMDFOutsideForm.resetForm();
            removeTabSession(SESSION_LIST_MDF_OUTSIDE);
            List<String> listMessage = new ArrayList<String>();
            String userSessionId = req.getSession().getId();
            ImportMdfOutsideDAO.listProgressMessage.put(userSessionId, listMessage);
        } catch (Exception ex) {
//            session.clear();
//            session.getTransaction().rollback();
//            session.beginTransaction();
            //ghi log
            log.error("Loi ham preparePage : " + ex);            
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception. " + ex.toString());
        }

        pageForward = IMPORT_MDF_OUTSIDE;
        log.info("end method preparePage of ImportMdfOutsideDAO");
        return pageForward;
    }
    
    /**
     * @author haint41
     * @date 05/10/2011
     * @desc Ham xem truoc noi dung file excel
     * @return pageForward
     * @throws Exception 
     */
    public String filePreview() throws Exception {
        log.info("Begin filePreview of ImportMdfOutsideDAO");
        pageForward = IMPORT_MDF_OUTSIDE;        

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {            
            String serverFileName = this.importMDFOutsideForm.getServerFileName();            
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + com.viettel.security.util.StringEscapeUtils.getSafeFileName(serverFileName));
            File impFile = new File(serverFilePath);
            List lstStockOwnerTmp = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 4);

            if (lstStockOwnerTmp == null || lstStockOwnerTmp.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.ISN.132");
                return pageForward;
            }
            Workbook workbook = Workbook.getWorkbook(impFile);
            Sheet sheet = workbook.getSheet(0);
            int numberRow = sheet.getRows();
            numberRow = numberRow  - 1; //tru di dong dau tien
            List<ImportMDFOutsideForm> listRowInFile = new ArrayList<ImportMDFOutsideForm>();

            //numberRow = numberRow > 1000 ? 1000 : numberRow; //truong hop file > 1000 row -> chi hien thi 1000 row dau tien
            int rowNum = 0;
            //doc du lieu tu file -> day du lieu vao list
            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet
                if (rowNum >= 1000){
                    break;
                }
                String tmpStrProvince = sheet.getCell(0, rowIndex).getContents(); //
                String tmpStrDslamCode = sheet.getCell(1, rowIndex).getContents(); //
                String tmpStrBoards = sheet.getCell(2, rowIndex).getContents();
                String tmpStrCableBox = sheet.getCell(3, rowIndex).getContents();
                String tmpStrCableNo = sheet.getCell(4, rowIndex).getContents();
                
                if (((tmpStrProvince == null || tmpStrProvince.trim().equals(""))
                        && (tmpStrDslamCode == null || tmpStrDslamCode.trim().equals(""))
                        && (tmpStrBoards == null || tmpStrBoards.trim().equals(""))
                        && (tmpStrCableBox == null || tmpStrCableBox.trim().equals(""))
                        && (tmpStrCableNo == null || tmpStrCableNo.trim().equals("")))) {                    
                    continue;
                }
                
                rowNum ++;
                
                ImportMDFOutsideForm tmpImportMDFOutsideForm = new ImportMDFOutsideForm();
                tmpImportMDFOutsideForm.setProvince(tmpStrProvince.trim());
                tmpImportMDFOutsideForm.setDslamCode(tmpStrDslamCode.trim());
                tmpImportMDFOutsideForm.setBoardsCode(tmpStrBoards.trim());
                tmpImportMDFOutsideForm.setCableBoxCode(tmpStrCableBox.trim());
                tmpImportMDFOutsideForm.setCableNo(tmpStrCableNo.trim());

                listRowInFile.add(tmpImportMDFOutsideForm);
            }
            if (rowNum >= 999) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.139");
            } else {
                req.setAttribute(REQUEST_MESSAGE, "ERR.STK.140");
                List listParams = new ArrayList<String>();
                listParams.add(String.valueOf(rowNum));
                req.setAttribute(REQUEST_MESSAGE_PARAM, listParams);
            }
            //day len bien session
            setTabSession(SESSION_LIST_MDF_OUTSIDE, listRowInFile);           

        } catch (Exception ex) {
            //ex.printStackTrace();            
            log.error("Loi khi preview file : " + ex);
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }

        
        log.info("End filePreview of ImportMdfOutsideDAO");
        return pageForward;
    }  
    
    /**
     * @author haint41
     * @date 05/10/2011
     * @desc Ham import file excel vao DB
     * @return pageForward
     * @throws Exception 
     */
    public String importMDFOutside() throws Exception {
        log.info("Begin filePreview of ImportMdfOutsideDAO");
        pageForward = IMPORT_MDF_OUTSIDE;

        HttpServletRequest req = getRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);
        Session session = getSession();

        try {
            String serverFileName = this.importMDFOutsideForm.getServerFileName();            
            String tempPath = ResourceBundleUtils.getResource("IMPORT_STOCK_FROM_PARTNER_TEMP_PATH");
            String serverFilePath = req.getSession().getServletContext().getRealPath(tempPath + com.viettel.security.util.StringEscapeUtils.getSafeFileName(serverFileName));
            File impFile = new File(serverFilePath);
            List lstStockOwnerTmp = new CommonDAO().readExcelFile(impFile, "Sheet1", 1, 0, 4);
            if (lstStockOwnerTmp == null || lstStockOwnerTmp.isEmpty()) {
                req.setAttribute(REQUEST_MESSAGE, "ERR.ISN.132");
                return pageForward;
            }
            
            Workbook workbook = Workbook.getWorkbook(impFile);
            Sheet sheet = workbook.getSheet(0);
            int numberRow = sheet.getRows();
            numberRow = numberRow  - 1; //tru di dong dau tien
            List<ImportMDFOutsideForm> listError = new ArrayList<ImportMDFOutsideForm>();

            //lay cac message thong bao loi
            String message = "";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            String userSessionId = req.getSession().getId();
            List<String> listMessage = ImportMdfOutsideDAO.listProgressMessage.get(userSessionId);
            HashMap<String, String> hashMapError = new HashMap<String, String>();
            hashMapError.put("ERR.STK.134", getText("ERR.STK.134")); //!!!Lỗi. Lỗi. province không tồn tại
            hashMapError.put("ERR.STK.135", getText("ERR.STK.135")); //!!!Lỗi. Lỗi. site code không đúng với province
            hashMapError.put("ERR.STK.136", getText("ERR.STK.136"));
            hashMapError.put("ERR.STK.137", getText("ERR.STK.137"));
            hashMapError.put("ERR.STK.138", getText("ERR.STK.138"));
            hashMapError.put("ERR.STK.141", getText("ERR.STK.141"));
            hashMapError.put("ERR.STK.142", getText("ERR.STK.142"));
            hashMapError.put("ERR.STK.143", getText("ERR.STK.143"));
            hashMapError.put("ERR.STK.144", getText("ERR.STK.144"));
            hashMapError.put("ERR.STK.145", getText("ERR.STK.145"));

            HashMap<String, String> hashMapMessage = new HashMap<String, String>();
            hashMapMessage.put("MSG.ISN.048", getText("MSG.ISN.048")); //đang xử lý từ dòng
            hashMapMessage.put("MSG.ISN.049", getText("MSG.ISN.049")); //đến
            hashMapMessage.put("MSG.ISN.050", getText("MSG.ISN.050")); //dòng trong file

            if (numberRow > 0) {
                int toRow = NUMBER_CMD_IN_BATCH;
                toRow = toRow < numberRow ? toRow : numberRow;
                message = simpleDateFormat.format(DateTimeUtils.getSysDate()) + " " + hashMapMessage.get("MSG.ISN.048") + " 1 " + hashMapMessage.get("MSG.ISN.049") + " " + toRow + "/ " + numberRow + " " + hashMapMessage.get("MSG.ISN.050");                
                listMessage.add(message);
            }
            
            AreaDAO areaDAO = new AreaDAO();
            areaDAO.setSession(this.getSession());            
            
            DslamDAO dslamDAO = new DslamDAO();
            dslamDAO.setSession(this.getSession());           
                    
            BoardsDAO boardsDAO = new BoardsDAO();
            boardsDAO.setSession(this.getSession());            
            
            CableBoxDAO cableBoxDAO = new CableBoxDAO();
            cableBoxDAO.setSession(this.getSession());

            int rowNum = 0;
            //doc du lieu tu file -> day du lieu vao list
            for (int rowIndex = 1; rowIndex <= numberRow; rowIndex++) {
                //doc tat ca cac dong trong sheet                
                String tmpStrProvince = sheet.getCell(0, rowIndex).getContents().trim(); //
                String tmpStrDslamCode = sheet.getCell(1, rowIndex).getContents().trim(); //
                String tmpStrBoards = sheet.getCell(2, rowIndex).getContents().trim();
                String tmpStrBoardsCode = tmpStrDslamCode + "_" + tmpStrBoards;
                String tmpStrCableBox = sheet.getCell(3, rowIndex).getContents().trim();
                String tmpStrCableBoxCode = tmpStrBoardsCode + "_" + tmpStrCableBox;
                String tmpStrCableNo = sheet.getCell(4, rowIndex).getContents().trim();
                Long cableNo = null;
                
                if (((tmpStrProvince == null || tmpStrProvince.trim().equals(""))
                        && (tmpStrDslamCode == null || tmpStrDslamCode.trim().equals(""))
                        && (tmpStrBoards == null || tmpStrBoards.trim().equals(""))
                        && (tmpStrCableBox == null || tmpStrCableBox.trim().equals(""))
                        && (tmpStrCableNo == null || tmpStrCableNo.trim().equals("")))) {
                    continue;
                }
                
                rowNum ++;
                
                if (tmpStrProvince == null || tmpStrProvince.trim().equals("")) {
                    ImportMDFOutsideForm errImportMDFOutsideForm = new ImportMDFOutsideForm();
                    errImportMDFOutsideForm.setProvince(tmpStrProvince);
                    errImportMDFOutsideForm.setDslamCode(tmpStrDslamCode);
                    errImportMDFOutsideForm.setBoardsCode(tmpStrBoards);
                    errImportMDFOutsideForm.setCableBoxCode(tmpStrCableBox);
                    errImportMDFOutsideForm.setCableNo(tmpStrCableNo);
                    errImportMDFOutsideForm.setErrorMessage(hashMapError.get("ERR.STK.141")); //!!!Lỗi. Cable_no phải là dạng số
                    listError.add(errImportMDFOutsideForm);   
                    continue;
                }
                
                if (tmpStrDslamCode == null || tmpStrDslamCode.trim().equals("")) {
                    ImportMDFOutsideForm errImportMDFOutsideForm = new ImportMDFOutsideForm();
                    errImportMDFOutsideForm.setProvince(tmpStrProvince);
                    errImportMDFOutsideForm.setDslamCode(tmpStrDslamCode);
                    errImportMDFOutsideForm.setBoardsCode(tmpStrBoards);
                    errImportMDFOutsideForm.setCableBoxCode(tmpStrCableBox);
                    errImportMDFOutsideForm.setCableNo(tmpStrCableNo);
                    errImportMDFOutsideForm.setErrorMessage(hashMapError.get("ERR.STK.142")); //!!!Lỗi. Cable_no phải là dạng số
                    listError.add(errImportMDFOutsideForm);   
                    continue;
                }
                
                if (tmpStrBoards == null || tmpStrBoards.trim().equals("")) {
                    ImportMDFOutsideForm errImportMDFOutsideForm = new ImportMDFOutsideForm();
                    errImportMDFOutsideForm.setProvince(tmpStrProvince);
                    errImportMDFOutsideForm.setDslamCode(tmpStrDslamCode);
                    errImportMDFOutsideForm.setBoardsCode(tmpStrBoards);
                    errImportMDFOutsideForm.setCableBoxCode(tmpStrCableBox);
                    errImportMDFOutsideForm.setCableNo(tmpStrCableNo);
                    errImportMDFOutsideForm.setErrorMessage(hashMapError.get("ERR.STK.143")); //!!!Lỗi. Cable_no phải là dạng số
                    listError.add(errImportMDFOutsideForm);  
                    continue;
                }
                
                if (tmpStrCableBox == null || tmpStrCableBox.trim().equals("")) {
                    ImportMDFOutsideForm errImportMDFOutsideForm = new ImportMDFOutsideForm();
                    errImportMDFOutsideForm.setProvince(tmpStrProvince);
                    errImportMDFOutsideForm.setDslamCode(tmpStrDslamCode);
                    errImportMDFOutsideForm.setBoardsCode(tmpStrBoards);
                    errImportMDFOutsideForm.setCableBoxCode(tmpStrCableBox);
                    errImportMDFOutsideForm.setCableNo(tmpStrCableNo);
                    errImportMDFOutsideForm.setErrorMessage(hashMapError.get("ERR.STK.144")); //!!!Lỗi. Cable_no phải là dạng số
                    listError.add(errImportMDFOutsideForm);   
                    continue;
                }
                
                if (tmpStrCableNo == null || tmpStrCableNo.trim().equals("")) {
                    ImportMDFOutsideForm errImportMDFOutsideForm = new ImportMDFOutsideForm();
                    errImportMDFOutsideForm.setProvince(tmpStrProvince);
                    errImportMDFOutsideForm.setDslamCode(tmpStrDslamCode);
                    errImportMDFOutsideForm.setBoardsCode(tmpStrBoards);
                    errImportMDFOutsideForm.setCableBoxCode(tmpStrCableBox);
                    errImportMDFOutsideForm.setCableNo(tmpStrCableNo);
                    errImportMDFOutsideForm.setErrorMessage(hashMapError.get("ERR.STK.145")); //!!!Lỗi. Cable_no phải là dạng số
                    listError.add(errImportMDFOutsideForm);   
                    continue;
                }
                
                try {
                    cableNo = Long.valueOf(tmpStrCableNo);                    
                } catch (Exception e) {
                    ImportMDFOutsideForm errImportMDFOutsideForm = new ImportMDFOutsideForm();
                    errImportMDFOutsideForm.setProvince(tmpStrProvince);
                    errImportMDFOutsideForm.setDslamCode(tmpStrDslamCode);
                    errImportMDFOutsideForm.setBoardsCode(tmpStrBoards);
                    errImportMDFOutsideForm.setCableBoxCode(tmpStrCableBox);
                    errImportMDFOutsideForm.setCableNo(tmpStrCableNo);
                    errImportMDFOutsideForm.setErrorMessage(hashMapError.get("ERR.STK.137")); //!!!Lỗi. Cable_no phải là dạng số
                    listError.add(errImportMDFOutsideForm);                                                     
                    continue;
                }
                
                //check xem province co ton tai hay khong
                Area area = new Area();
                area = areaDAO.findProvinceByProvinceReference(tmpStrProvince);
                if (area == null) {
                    ImportMDFOutsideForm errImportMDFOutsideForm = new ImportMDFOutsideForm();
                    errImportMDFOutsideForm.setProvince(tmpStrProvince);
                    errImportMDFOutsideForm.setDslamCode(tmpStrDslamCode);
                    errImportMDFOutsideForm.setBoardsCode(tmpStrBoards);
                    errImportMDFOutsideForm.setCableBoxCode(tmpStrCableBox);
                    errImportMDFOutsideForm.setCableNo(tmpStrCableNo);
                    errImportMDFOutsideForm.setErrorMessage(hashMapError.get("ERR.STK.134")); //!!!Lỗi. province không tồn tại
                    listError.add(errImportMDFOutsideForm);                                                     
                    continue;
                }                
                //check xem dslam_code co ton tai hay khong
                Dslam dslam = new Dslam();
                dslam = dslamDAO.getDslamByCode(tmpStrDslamCode);                
                if (dslam == null){
                    ImportMDFOutsideForm errImportMDFOutsideForm = new ImportMDFOutsideForm();
                    errImportMDFOutsideForm.setProvince(tmpStrProvince);
                    errImportMDFOutsideForm.setDslamCode(tmpStrDslamCode);
                    errImportMDFOutsideForm.setBoardsCode(tmpStrBoards);
                    errImportMDFOutsideForm.setCableBoxCode(tmpStrCableBox);
                    errImportMDFOutsideForm.setCableNo(tmpStrCableNo);
                    errImportMDFOutsideForm.setErrorMessage(hashMapError.get("ERR.STK.135")); //!!!Lỗi. site code không tồn tại
                    listError.add(errImportMDFOutsideForm);                                                     
                    continue;
                }
                // check su ton tai cua dslam_code, kiem tra xem co dung voi province khong
                String province = area.getProvince();
                if (!dslam.getProvince().equals(province)){
                    ImportMDFOutsideForm errImportMDFOutsideForm = new ImportMDFOutsideForm();
                    errImportMDFOutsideForm.setProvince(tmpStrProvince);
                    errImportMDFOutsideForm.setDslamCode(tmpStrDslamCode);
                    errImportMDFOutsideForm.setBoardsCode(tmpStrBoards);
                    errImportMDFOutsideForm.setCableBoxCode(tmpStrCableBox);
                    errImportMDFOutsideForm.setCableNo(tmpStrCableNo);
                    errImportMDFOutsideForm.setErrorMessage(hashMapError.get("ERR.STK.136")); //!!!Lỗi. site code không đúng với province
                    listError.add(errImportMDFOutsideForm);                                                     
                    continue;
                }                               
                //check su ton tai cua boards_code
                Boards boards = new Boards();
                Long boardsId = null;
                List lstBoards = boardsDAO.findByCode(tmpStrBoardsCode.toUpperCase());
                if ( lstBoards != null && lstBoards.size()>0L){
                    boards = (Boards) lstBoards.get(0);
                    boardsId = boards.getBoardId();
                } else {
                    boardsId = getSequence("BOARD_SEQ");
                    boards.setBoardId(boardsId);
                    boards.setDslamId(dslam.getDslamId());                    
                    boards.setCode(tmpStrBoardsCode.toUpperCase());
                    boards.setName(tmpStrBoardsCode.toUpperCase());
                    boards.setStatus(Constant.BOARDS_STATUS_ACTIVE);
                    boardsDAO.save(boards);
                }
                //check su ton tai cua cable_box                                
                CableBox cableBox = new CableBox();                        
                Long cableBoxId = null;
                List lstCableBox = cableBoxDAO.findByCode(tmpStrCableBoxCode.toUpperCase());
                if (lstCableBox != null && lstCableBox.size() > 0L) {
                    cableBox = (CableBox) lstCableBox.get(0);
                    cableBoxId = cableBox.getCableBoxId();
                } else {
                    cableBoxId = getSequence("CABLE_BOX_SEQ");
                    cableBox.setCableBoxId(cableBoxId);
                    cableBox.setBoardId(boardsId);
                    cableBox.setCode(tmpStrCableBoxCode.toUpperCase());
                    cableBox.setName(tmpStrCableBoxCode.toUpperCase());
                    cableBox.setDslamId(dslam.getDslamId());
                    cableBox.setStatus(Constant.CABLE_BOX_STATUS_ACTIVE);
                    cableBoxDAO.save(cableBox);
                }
                //check su ton tai cua cable_no                
                boolean checkExistCableNo = checkExistCableNo(cableBoxId, cableNo);
                if (checkExistCableNo == true){
                    ImportMDFOutsideForm errImportMDFOutsideForm = new ImportMDFOutsideForm();
                    errImportMDFOutsideForm.setProvince(tmpStrProvince);
                    errImportMDFOutsideForm.setDslamCode(tmpStrDslamCode);
                    errImportMDFOutsideForm.setBoardsCode(tmpStrBoards);
                    errImportMDFOutsideForm.setCableBoxCode(tmpStrCableBox);
                    errImportMDFOutsideForm.setCableNo(tmpStrCableNo);
                    errImportMDFOutsideForm.setErrorMessage(hashMapError.get("ERR.STK.138")); //!!!Lỗi. Cable_no đã tồn tại
                    listError.add(errImportMDFOutsideForm);
                    continue;
                }
                //insert cable_no vao DB
                PortOutside portOutside = new PortOutside();
                portOutside.setPortOutsideId(getSequence("PORT_OUTSIDE_SEQ"));
                portOutside.setCableBoxId(cableBoxId);
                portOutside.setPortPosition(cableNo);
                portOutside.setCreateDate(getSysdate());
                portOutside.setCreateUser(userToken.getLoginName());
                portOutside.setCreateUserId(userToken.getUserID());                
                portOutside.setStatus(Constant.PORT_OUTSIDE_STATUS_FREE);                
                portOutside.setCableBoxCode(cableBox.getCode());
                portOutside.setBoardCode(boards.getCode());
                portOutside.setDslamCode(dslam.getCode());
                getSession().save(portOutside);
                          
                
                if (rowIndex % NUMBER_CMD_IN_BATCH == 0) {
                    session.flush();
                    session.beginTransaction();
                    session.getTransaction().commit();
                    session.getTransaction().begin();
                }
            }
            //ket xuat ket qua ra file excel trong truong hop co loi
            if (listError != null && listError.size() > 0) {
                StringBuffer strMessage = new StringBuffer("");

                try {
                    String DATE_FORMAT = "yyyyMMddHHmmss";
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    String filePath = ResourceBundleUtils.getResource("LINK_TO_DOWNLOAD_FILE");
                    filePath = filePath != null ? filePath : "/";
                    filePath += com.viettel.security.util.StringEscapeUtils.getSafeFileName("importMDFOutsideErr_" + userToken.getLoginName() + "_" + sdf.format(new Date()) + ".xls");
                    String realPath = req.getSession().getServletContext().getRealPath(filePath);

                    String templatePath = ResourceBundleUtils.getResource("IMPORT_MDF_OUTSIDE_TMP_PATH_ERR");
                    if (templatePath == null || templatePath.trim().equals("")) {
                        //khong tim thay duong dan den file template de xuat ket qua
                        strMessage.append("importMDFOutside.error.errTemplateNotExist");
                    } else {
                        String realTemplatePath = req.getSession().getServletContext().getRealPath(templatePath);
                        File fTemplateFile = new File(realTemplatePath);
                        if (!fTemplateFile.exists() || !fTemplateFile.isFile()) {
                            //file ko ton tai
                            strMessage.append("importMDFOutside.error.errTemplateNotExist");
                        } else {
                            Map beans = new HashMap();
                            beans.put("listError", listError);

                            XLSTransformer transformer = new XLSTransformer();
                            transformer.transformXLS(realTemplatePath, beans, realPath);

                            strMessage.append(filePath);
                        }
                    }

                } catch (Exception ex) {
                    //ex.printStackTrace();
                    strMessage.append("!!!Exception. ").append(ex.toString());
                }

                if (strMessage != null && !strMessage.toString().equals("")) {
                    req.setAttribute(REQUEST_ERROR_FILE_PATH, strMessage);
                }                
            }
            req.setAttribute(REQUEST_MESSAGE, getText("importMDFOutside.successfull") + " " + (rowNum - listError.size()) + "/" + rowNum );

        } catch (Exception ex) {
            //ex.printStackTrace();
            req.setAttribute(REQUEST_MESSAGE, "!!!Exception - " + ex.toString());
        }
     
        
        log.info("End filePreview of ImportMdfOutsideDAO");
        return pageForward;
    }
    
    //bien phuc vu viec hien thi progress
    private static HashMap<String, List<String>> listProgressMessage = new HashMap<String, List<String>>(); 
    
    /**
     * desc     : tra ve du lieu cap nhat cho divProgress     
     */
    public String updateProgressDiv(HttpServletRequest req) {
        try {
            String userSessionId = req.getSession().getId();
            List<String> listMessage = ImportMdfOutsideDAO.listProgressMessage.get(userSessionId);
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
    
    /**
     * @author haint41
     * @date 06/10/2011
     * @desc Ham kiem tra DSLAM co ton tai hay khong
     * @param province : na tinh
     * @param Code : ma Dslam
     * @return true neu ton tai
     */     
    private boolean checkDslamValidate(String province, String Code) {
        String strQuery = "select count(*) from Dslam where code = ? and province = ?";
        Query q = getSession().createQuery(strQuery);
        q.setParameter(0, Code);
        q.setParameter(1, province);
        Long count = (Long) q.list().get(0);
        if ((count != null) && (count.compareTo(0L) > 0)) {            
            return true;
        }
        return false;
    }
    
    /**
     * @author haint41
     * @date 06/10/2011 
     * @desc Ham kiem tra Cable no co ton tai hay khong
     * @param cableBoxId
     * @param cableNo
     * @return true neu co ton tai
     */
    private boolean checkExistCableNo(Long cableBoxId, Long cableNo){
        try {
            String strQuery = "from PortOutside where cableBoxId = ? and portPosition = ?";
            Query q = getSession().createQuery(strQuery);
            q.setParameter(0, cableBoxId);
            q.setParameter(1, cableNo);
            List lst = q.list();
            if (lst != null && lst.size() > 0){
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Loi khi kiem tra cable_no : " + e);
            return false;
        }              
    }
    
}
