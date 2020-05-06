package com.viettel.im.client.form;

import com.viettel.im.common.Constant;
import com.viettel.im.database.BO.Staff;
import java.util.Date;
import java.util.ResourceBundle;

/**
 *
 * @author Doan Thanh 8
 */
public class StaffForm {

    private Long staffId;
    private Long shopId;
    private String staffCode;
    private String name;
    private Date birthday;
    private String idNo;
    private String idIssuePlace;
    private Date idIssueDate;
    private String tel;
    private Long type;
    private String serial;
    private String isdn;
    private String pin;
    private String address;
    private String province;
    private String staffOwnType;
    private Long staffOwnerId;
    private Long channelTypeId;
    private String pricePolicy;
    private String discountPolicy;
    private String staffManagementName;
    //Them Nhan vien quan ly cho Nhan Vien HO thuoc Cty
    private String shopCode;
    private String shopName;
    private String staffManagementCode;
    //lamnt 14032017
    private String isdnWallet;
    private String hrId;
    //tannh 20180502 them truong han muc gach no
    private String limitDay;
    private String limitMoney;
    private String limitCreateUser;
    private Date limitCreateTime;
    private Date limitEndTime;
    private String limitApproveUser;
    private Date limitApproveTime;
    private String noteLimit;
    private String clientFileName;
    private String serverFileName;
    private String ftpPath;

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public Date getLimitEndTime() {
        return limitEndTime;
    }

    public void setLimitEndTime(Date limitEndTime) {
        this.limitEndTime = limitEndTime;
    }

    public String getClientFileName() {
        return clientFileName;
    }

    public void setClientFileName(String clientFileName) {
        this.clientFileName = clientFileName;
    }

    public String getServerFileName() {
        return serverFileName;
    }

    public void setServerFileName(String serverFileName) {
        this.serverFileName = serverFileName;
    }

    public void copyDataFromBO(Staff staff) {
        this.setStaffId(staff.getStaffId());
        this.setShopId(staff.getShopId());
        this.setStaffCode(staff.getStaffCode());
        this.setName(staff.getName());
        this.setBirthday(staff.getBirthday());
        this.setIdNo(staff.getIdNo());
        this.setIdIssuePlace(staff.getIdIssuePlace());
        this.setIdIssueDate(staff.getIdIssueDate());
        this.setTel(staff.getTel());
        this.setType(staff.getType());
        this.setSerial(staff.getSerial());
        this.setIsdn(staff.getIsdn());
        this.setPin(staff.getPin());
        this.setAddress(staff.getAddress());
        this.setProvince(staff.getProvince());
        this.setStaffOwnType(staff.getStaffOwnType());
        this.setStaffOwnerId(staff.getStaffOwnerId());
        this.setChannelTypeId(staff.getChannelTypeId());
        this.setPricePolicy(staff.getPricePolicy());
        this.setDiscountPolicy(staff.getDiscountPolicy());
        //lamnt 15032017
        this.setIsdnWallet(staff.getIsdnWallet());
        this.setHrId(staff.getHrId());
        //end
        //tannh 20180502
        this.setLimitDay(staff.getLimitDay());
        this.setLimitMoney(staff.getLimitMoney());
        this.setLimitCreateUser(staff.getLimitCreateUser());
        this.setLimitCreateTime(staff.getLimitCreateTime());
        this.setLimitApproveTime(staff.getLimitApproveTime());
        this.setLimitApproveUser(staff.getLimitApproveUser());
        this.setNoteLimit(this.getLimitMoney());
        this.setLimitEndTime(staff.getLimitEndTime());
        //end
    }

    public void copyDataToBO(Staff staff) {
        staff.setStaffId(this.getStaffId());
        staff.setShopId(this.getShopId());
        staff.setStaffCode(this.getStaffCode());
        staff.setName(this.getName());
        staff.setBirthday(this.getBirthday());
        staff.setIdNo(this.getIdNo());
        staff.setIdIssuePlace(this.getIdIssuePlace());
        staff.setIdIssueDate(this.getIdIssueDate());
        staff.setTel(this.getTel());
        staff.setType(this.getType());
        staff.setSerial(this.getSerial());
        staff.setIsdn(this.getIsdn());
        staff.setPin(this.getPin());
        staff.setAddress(this.getAddress());
        staff.setProvince(this.getProvince());
        staff.setStaffOwnType(this.getStaffOwnType());
        staff.setStaffOwnerId(this.getStaffOwnerId());
        staff.setStaffOwnType(Constant.OWNER_TYPE_STAFF.toString());
        staff.setChannelTypeId(this.getChannelTypeId());
        staff.setPricePolicy(this.getPricePolicy());
        staff.setDiscountPolicy(this.getDiscountPolicy());

    }

    //HIEUNV31- 09/01/2017
    public void copyDataToBO09012017(Staff staff) {
        staff.setStaffId(this.getStaffId());
        staff.setShopId(this.getShopId());
        staff.setStaffCode(this.getStaffCode());
        staff.setName(this.getName());
        staff.setBirthday(this.getBirthday());
        staff.setIdNo(this.getIdNo());
        staff.setIdIssuePlace(this.getIdIssuePlace());
        staff.setIdIssueDate(this.getIdIssueDate());
        staff.setTel(this.getTel());
        staff.setType(this.getType());
        staff.setSerial(this.getSerial());
        staff.setIsdn(this.getIsdn());
        staff.setPin(this.getPin());
        staff.setAddress(this.getAddress());
        staff.setStaffOwnType(this.getStaffOwnType());
        staff.setStaffOwnerId(this.getStaffOwnerId());
        staff.setStaffOwnType(Constant.OWNER_TYPE_STAFF.toString());
        staff.setChannelTypeId(this.getChannelTypeId());
    }
    //END

    public void copyDataToBOUpdate(Staff staff) {
        staff.setStaffId(this.getStaffId());
        staff.setShopId(this.getShopId());
        staff.setStaffCode(this.getStaffCode());
        staff.setName(this.getName());
        //staff.setBirthday(this.getBirthday());
        staff.setIdNo(this.getIdNo());
        staff.setIdIssuePlace(this.getIdIssuePlace());
        staff.setIdIssueDate(this.getIdIssueDate());
        staff.setTel(this.getTel());
        staff.setType(this.getType());
        //staff.setSerial(this.getSerial());
        staff.setIsdn(this.getIsdn());
        //staff.setPin(this.getPin());
        staff.setAddress(this.getAddress());
        //staff.setProvince(this.getProvince());
        staff.setStaffOwnType(this.getStaffOwnType());
        staff.setStaffOwnerId(this.getStaffOwnerId());
        staff.setChannelTypeId(this.getChannelTypeId());
        if (staff.getChannelTypeId().equals(Constant.CHANNEL_TYPE_CTV)) {
            staff.setPointOfSale("2");
        } else {
            if (staff.getChannelTypeId().equals(Constant.CHANNEL_TYPE_DB)) {
                staff.setPointOfSale("1");
            }
        }
        //staff.setPricePolicy(this.getPricePolicy());
        //staff.setDiscountPolicy(this.getDiscountPolicy());

    }

    public void resetForm() {
        this.setStaffId(0L);
        this.setShopId(0L);
        this.setStaffCode("");
        this.setName("");
        this.setBirthday(null);
        this.setIdNo("");
        this.setIdIssuePlace("");
        this.setIdIssueDate(null);
        this.setTel("");
        this.setType(0L);
        this.setSerial("");
        this.setIsdn("");
        this.setPin("");
        this.setAddress("");
        this.setProvince("");
        this.setStaffOwnType("");
        this.setStaffOwnerId(0L);
        this.setChannelTypeId(0L);
        this.setPricePolicy("");
        this.setDiscountPolicy("");
    }

    public String getDiscountPolicy() {
        return discountPolicy;
    }

    public void setDiscountPolicy(String discountPolicy) {
        this.discountPolicy = discountPolicy;
    }

    public String getPricePolicy() {
        return pricePolicy;
    }

    public void setPricePolicy(String pricePolicy) {
        this.pricePolicy = pricePolicy;
    }

    public Long getChannelTypeId() {
        return channelTypeId;
    }

    public void setChannelTypeId(Long channelTypeId) {
        this.channelTypeId = channelTypeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getIdIssueDate() {
        return idIssueDate;
    }

    public void setIdIssueDate(Date idIssueDate) {
        this.idIssueDate = idIssueDate;
    }

    public String getIdIssuePlace() {
        return idIssuePlace;
    }

    public void setIdIssuePlace(String idIssuePlace) {
        this.idIssuePlace = idIssuePlace;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getIsdn() {
        return isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getStaffCode() {
        return staffCode;
    }

    public void setStaffCode(String staffCode) {
        this.staffCode = staffCode;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffOwnType() {
        return staffOwnType;
    }

    public void setStaffOwnType(String staffOwnType) {
        this.staffOwnType = staffOwnType;
    }

    public Long getStaffOwnerId() {
        return staffOwnerId;
    }

    public void setStaffOwnerId(Long staffOwnerId) {
        this.staffOwnerId = staffOwnerId;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getStaffManagementName() {
        return staffManagementName;
    }

    public void setStaffManagementName(String staffManagementName) {
        this.staffManagementName = staffManagementName;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStaffManagementCode() {
        return staffManagementCode;
    }

    public void setStaffManagementCode(String staffManagementCode) {
        this.staffManagementCode = staffManagementCode;
    }

    public String getIsdnWallet() {
        return isdnWallet;
    }

    public void setIsdnWallet(String isdnWallet) {
        this.isdnWallet = isdnWallet;
    }

    public String getHrId() {
        return hrId;
    }

    public void setHrId(String hrId) {
        this.hrId = hrId;
    }

    public String getLimitDay() {
        return limitDay;
    }

    public void setLimitDay(String limitDay) {
        this.limitDay = limitDay;
    }

    public String getLimitMoney() {
        return limitMoney;
    }

    public void setLimitMoney(String limitMoney) {
        this.limitMoney = limitMoney;
    }

    public String getLimitCreateUser() {
        return limitCreateUser;
    }

    public void setLimitCreateUser(String limitCreateUser) {
        this.limitCreateUser = limitCreateUser;
    }

    public Date getLimitCreateTime() {
        return limitCreateTime;
    }

    public void setLimitCreateTime(Date limitCreateTime) {
        this.limitCreateTime = limitCreateTime;
    }

    public String getLimitApproveUser() {
        return limitApproveUser;
    }

    public void setLimitApproveUser(String limitApproveUser) {
        this.limitApproveUser = limitApproveUser;
    }

    public Date getLimitApproveTime() {
        return limitApproveTime;
    }

    public void setLimitApproveTime(Date limitApproveTime) {
        this.limitApproveTime = limitApproveTime;
    }

    public String getNoteLimit() {
        return ResourceBundle.getBundle("config").getString("msgNoteLimitMoney");
    }

    public void setNoteLimit(String noteLimit) {
        this.noteLimit = ResourceBundle.getBundle("config").getString("msgNoteLimitMoney");
    }
}
