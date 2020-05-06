/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.viettel.im.database.DAO;

import com.viettel.database.DAO.BaseDAOAction;
import com.viettel.im.client.bean.ImSearchBean;
import com.viettel.im.client.form.ManagementCTVAndPointForm;
import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Staff;
import com.viettel.im.database.BO.UserToken;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;

/**
 *
 * @author mov_itbl_dinhdc
 */
public class ViewStockCTVAndPointDAO extends BaseDAOAction{
    
    private static final Log log = LogFactory.getLog(ViewStockCTVAndPointDAO.class);
    private ManagementCTVAndPointForm managementCTVAndPointForm = new ManagementCTVAndPointForm();

    public ManagementCTVAndPointForm getManagementCTVAndPointForm() {
        return managementCTVAndPointForm;
    }

    public void setManagementCTVAndPointForm(ManagementCTVAndPointForm managementCTVAndPointForm) {
        this.managementCTVAndPointForm = managementCTVAndPointForm;
    }
    
    public String preparePage() {
        log.info(" Start preparePage ViewStockCTVAndPointDAO .......................");
        HttpServletRequest req = getRequest();
        ChannelTypeDAO ctDao = new ChannelTypeDAO(getSession());
        List lstChannelTypeCol = ctDao.findByObjectTypeAndIsVtUnitAndIsPrivate("2", "2", 0L);
        req.setAttribute("listChannelType", lstChannelTypeCol);
        log.info(" End preparePage ViewStockCTVAndPointDAO .......................");
        return "viewStockCTVAndPoint";
    }
    
    public String viewStockCTVAndPoint() throws Exception {
        log.info("# Begin method viewStockCTVAndPoint");
        
        log.info("# End method viewStockCTVAndPoint");
        return "viewStockCTVAndPoint";
    }
     //Lay nhan vien  theo ma   
     public Long getStaffId(String staffCode) throws Exception {
        StaffDAO staffDAO = new StaffDAO();
        staffDAO.setSession(getSession());
        Staff staff = staffDAO.findAvailableByStaffCode(staffCode.trim().toLowerCase());
        if (staff != null) {
            return staff.getStaffId();
        }
        return 0L;

    }
    //Lay danh sach nhan vien thuoc cong ty theo Shop Code
    public List<ImSearchBean> getListStaff(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.status = 1 and b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);



        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }
    //Lay danh sach nhan vien thuoc cong ty theo Shop Code
    public List<ImSearchBean> getStaffName(ImSearchBean imSearchBean) {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();

        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);

        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
            listParameter1.add(imSearchBean.getOtherParamValue().trim().toLowerCase());
        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase());
        } else {
            return listImSearchBean;
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }


        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }
    //Lay danh sach nhan vien la kenh theo Shop Code va Staff management
    public List<ImSearchBean> getListOwner(ImSearchBean imSearchBean) throws Exception {
        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");
        strQuery1.append("and a.staffOwnerId is not null ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_NOT_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);


        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String[] arrOtherParamValue = imSearchBean.getOtherParamValue().trim().split(";");

            /* 120503 - TrongLV - NEU KHONG CHON MA NVQL THI TIM THEO SHOP_CODE */
            if (arrOtherParamValue != null && arrOtherParamValue.length == 2
                    && arrOtherParamValue[0] != null && !arrOtherParamValue[0].trim().equals("") //                    && arrOtherParamValue[1] != null && !arrOtherParamValue[1].trim().equals("")
                    ) {
                String shopCode = arrOtherParamValue[0].toLowerCase();
                String staffManageCode = arrOtherParamValue[1].toLowerCase();
                strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
                listParameter1.add(shopCode.trim().toLowerCase());
                try {
                    if (staffManageCode != null && !staffManageCode.equals("")) {
                        Long staffId = getStaffId(staffManageCode);
                        strQuery1.append("and a.staffOwnerId = ? ");
                        listParameter1.add(staffId);
                    }
                } catch (Exception ex) {
                }


            } else {
                return listImSearchBean;
            }

        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) like ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "%");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }
     //Lay danh sach nhan vien la kenh theo Shop Code va Staff management
    public List<ImSearchBean> getOwnerName(ImSearchBean imSearchBean) {

        HttpServletRequest req = imSearchBean.getHttpServletRequest();
        UserToken userToken = (UserToken) req.getSession().getAttribute(Constant.USER_TOKEN);

        List<ImSearchBean> listImSearchBean = new ArrayList<ImSearchBean>();
        //lay danh sach nhan vien
        List listParameter1 = new ArrayList();
        StringBuffer strQuery1 = new StringBuffer("select new com.viettel.im.client.bean.ImSearchBean(a.staffCode, a.name) ");
        strQuery1.append("from Staff a ");
        strQuery1.append("where 1 = 1 and a.status = 1 ");
        strQuery1.append("and a.staffOwnerId is not null ");

        strQuery1.append(" and a.channelTypeId in (select channelTypeId from ChannelType b where b.objectType = ? and b.isVtUnit = ? and b.isPrivate = ? ) ");
        listParameter1.add(Constant.OBJECT_TYPE_STAFF);
        listParameter1.add(Constant.IS_NOT_VT_UNIT);
        listParameter1.add(Constant.CHANNEL_TYPE_IS_NOT_PRIVATE);


        if (imSearchBean.getOtherParamValue() != null && !imSearchBean.getOtherParamValue().trim().equals("")) {
            String[] arrOtherParamValue = imSearchBean.getOtherParamValue().trim().split(";");

            /* 120503 - TrongLV - NEU KHONG CHON MA NVQL THI TIM THEO SHOP_CODE */
            if (arrOtherParamValue != null && arrOtherParamValue.length == 2
                    && arrOtherParamValue[0] != null && !arrOtherParamValue[0].trim().equals("") //                    && arrOtherParamValue[1] != null && !arrOtherParamValue[1].trim().equals("")
                    ) {
                String shopCode = arrOtherParamValue[0].toLowerCase();
                String staffManageCode = arrOtherParamValue[1].toLowerCase();
                strQuery1.append("and a.shopId in (select shopId from Shop where lower(shopCode) = ? ) ");
                listParameter1.add(shopCode.trim().toLowerCase());
                try {
                    if (staffManageCode != null && !staffManageCode.equals("")) {
                        Long staffId = getStaffId(staffManageCode);
                        strQuery1.append("and a.staffOwnerId = ? ");
                        listParameter1.add(staffId);
                    }
                } catch (Exception ex) {
                }

            } else {
                return listImSearchBean;
            }

        } else {
            //truong hop chua co shop -> tra ve chuoi rong
            return listImSearchBean;
        }

        if (imSearchBean.getCode() != null && !imSearchBean.getCode().trim().equals("")) {
            strQuery1.append("and lower(a.staffCode) = ? ");
            listParameter1.add(imSearchBean.getCode().trim().toLowerCase() + "");
        }

        if (imSearchBean.getName() != null && !imSearchBean.getName().trim().equals("")) {
            strQuery1.append("and lower(a.name) like ? ");
            listParameter1.add("%" + imSearchBean.getName().trim().toLowerCase() + "%");
        }

        strQuery1.append("and rownum < ? ");
        listParameter1.add(300L);

        strQuery1.append("order by nlssort(a.staffCode, 'nls_sort=Vietnamese') asc ");

        Query query1 = getSession().createQuery(strQuery1.toString());
        for (int i = 0; i < listParameter1.size(); i++) {
            query1.setParameter(i, listParameter1.get(i));
        }

        List<ImSearchBean> tmpList1 = query1.list();
        if (tmpList1 != null && tmpList1.size() > 0) {
            listImSearchBean.addAll(tmpList1);
        }

        return listImSearchBean;
    }

}
