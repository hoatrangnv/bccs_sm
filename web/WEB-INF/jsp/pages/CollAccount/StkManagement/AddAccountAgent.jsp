<%--  
    Document   : AddAccountAgent
    Created on : Nov 11, 2009, 7:59:02 PM
    Author     : Vunt
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="inventoryDisplay"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page  import="com.viettel.im.common.util.ResourceBundleUtils"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    //String flag = (String) request.getSession().getAttribute("flag");
    String pageId = request.getParameter("pageId");
    request.setAttribute("flag", request.getSession().getAttribute("flag" + pageId));
    request.setAttribute("roleActive", request.getSession().getAttribute("roleActive" + pageId));
    request.setAttribute("roleReActive", request.getSession().getAttribute("roleReActive" + pageId));
    request.setAttribute("roleChangePass", request.getSession().getAttribute("roleChangePass" + pageId));
    request.setAttribute("roleChangeStatus", request.getSession().getAttribute("roleChangeStatus" + pageId));
    request.setAttribute("roleChangeInfo", request.getSession().getAttribute("roleChangeInfo" + pageId));
    //request.setAttribute("roleChangeInfo", request.getSession().getAttribute("roleChangeInfo" + pageId));
    request.setAttribute("roleUnlock", request.getSession().getAttribute("roleUnlock" + pageId));
    request.setAttribute("roleUnlockBreach", request.getSession().getAttribute("roleUnlockBreach" + pageId));

    request.setAttribute("roleRepairSim", request.getSession().getAttribute("roleRepairSim" + pageId));
    request.setAttribute("changeInfo", request.getSession().getAttribute("changeInfo" + pageId));
    request.setAttribute("changePassword", request.getSession().getAttribute("changePassword" + pageId));
    request.setAttribute("changeStatus", request.getSession().getAttribute("changeStatus" + pageId));
    request.setAttribute("StatusDestroy", request.getSession().getAttribute("StatusDestroy" + pageId));
    request.setAttribute("showButton", request.getSession().getAttribute("showButton" + pageId));
    request.setAttribute("showEdit", request.getSession().getAttribute("showEdit" + pageId));
    request.setAttribute("checkSerial", request.getSession().getAttribute("checkSerial" + pageId));
    request.setAttribute("changeSerial", request.getSession().getAttribute("changeSerial" + pageId));

%>
<c:set var="roleActiveAccountAgent" scope="page" value="${fn:escapeXml(imDef:checkAuthority('roleActiveAccountAgent', request))}" />
<c:set var="roleChangePassAccountAgent" scope="page" value="${fn:escapeXml(imDef:checkAuthority('roleChangePassAccountAgent', request))}" />
<c:set var="roleChangeStatusAccountAgent" scope="page" value="${fn:escapeXml(imDef:checkAuthority('roleChangeStatusAccountAgent', request))}" />
<c:set var="roleChangeInfoAccountAgent" scope="page" value="${fn:escapeXml(imDef:checkAuthority('roleChangeInfoAccountAgent', request))}" />
<c:set var="roleRepairSimAccountAgent" scope="page" value="${fn:escapeXml(imDef:checkAuthority('roleRepairSimAccountAgent', request))}" />
<c:set var="roleActive" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleActive, request))}" />
<c:set var="roleReActive" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleReActive, request))}" />
<c:set var="roleChangePass" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleChangePass, request))}" />
<c:set var="roleChangeStatus" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleChangeStatus, request))}" />
<c:set var="roleChangeInfo" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleChangeInfo, request))}" />
<c:set var="roleRepairSim" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleRepairSim, request))}" />
<c:set var="roleUnlock" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleUnlock, request))}" />
<c:set var="roleUnlockBreach" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleUnlockBreach, request))}" />


<s:hidden name="collAccountManagmentForm.accountIdAgent" id="collAccountManagmentForm.accountIdAgent"/>
<s:hidden name="collAccountManagmentForm.collId" id="collAccountManagmentForm.collId"/>
<s:hidden name="collAccountManagmentForm.dateCreate" id="dateCreate"/>
<s:hidden name="collAccountManagmentForm.agent_id" id="agent_id"/>
<s:hidden name="collAccountManagmentForm.statusAcc" id="statusAcc"/>
<s:hidden name="collAccountManagmentForm.passAcc" id="passAcc"/>
<s:hidden name="collAccountManagmentForm.saveSerialOld" id="saveSerialOld"/>
<s:hidden name="collAccountManagmentForm.saveSerialNew" id="saveSerialNew"/>
<s:hidden name="CollAccountManagmentForm.checkShowViewAccount" id="checkShowViewAccount"/>

<div>
    <tags:displayResult id="displayResultMsgClient" property="messageParam" propertyValue="paramMsg" type="key" />
</div>    
<div id="abcsd_09333" style="color: red;font-size: 12pt" align="center" >
</div>    

<sx:div id="showViewAccountAgent" theme="simple">
    <table>

    </table>
    <table class="inputTbl8Col">
        <tr>
            <%--<td class="label">Loại tài khoản </td>--%>
            <td class="label"><tags:label key="MSG.SIK.015" /></td>
            <td class="text">
                <s:textfield name="collAccountManagmentForm.accountType" theme="simple" id="accountType" maxlength="14" cssClass="txtInputFull" readonly="true"/>
            </td>
            <td class="text" colspan="2">
                <s:textfield name="collAccountManagmentForm.accountName" theme="simple" id="accountName" maxlength="50" cssClass="txtInputFull" readonly="true"/>
            </td>
        </tr>
        <tr>
            <%--<td class="label">CH quản lý</td>--%>
            <td class="label"><tags:label key="MSG.SIK.268" /></td>
            <td class="text">
                <s:textfield theme="simple" name="collAccountManagmentForm.shopParentcode" id="shopParentcode" maxlength="100" cssClass="txtInputFull" readonly="true"/>
            </td>
            <td class="text" colspan="2">
                <s:textfield theme="simple" name="collAccountManagmentForm.shopParentName" id="shopParentName" maxlength="200" cssClass="txtInputFull" readonly="true"/>
            </td>
            <%--<td class="label">NV quản lý </td>--%>
            <td class="label"><tags:label key="MSG.SIK.269" /></td>
            <td class="text">
                <s:textfield name="collAccountManagmentForm.staffManagementCode" id="staffManagementCode" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="true"/>
            </td>
            <td class="text" colspan="2">
                <s:textfield name="collAccountManagmentForm.staffManagementName" id="staffManagementName" theme="simple" maxlength="200" cssClass="txtInputFull" readonly="true"/>
            </td>
        </tr>

        <tr>
            <%--<td class="label">CH/ĐL </td>--%>
            <td class="label"><tags:label key="MSG.SIK.016" /></td>
            <td class="text">
                <s:textfield theme="simple" name="collAccountManagmentForm.shopCodeAgent" id="shopCode" maxlength="100" cssClass="txtInputFull" readonly="true"/>
            </td>
            <td class="text" colspan="2">   
                <s:textfield theme="simple" name="collAccountManagmentForm.shopNameAgent" id="shopName" maxlength="200" cssClass="txtInputFull" readonly="true"/>
            </td>
            <%--<td class="label">NV/CTV </td>--%>
            <td class="label"><tags:label key="MSG.SIK.017" /></td>
            <td class="text">
                <s:textfield name="collAccountManagmentForm.staffCode" id="staffCode" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="true"/>
            </td>
            <td class="text" colspan="2">
                <s:textfield name="collAccountManagmentForm.staffName" id="staffName" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="true"/>
            </td>
        </tr>       
        <tr>
            <%--<td class="label">Số ISDN (<font color="red">*</font>)</td>--%>
            <s:if test="(#request.showButton*1 == 1 || #request.showEdit*1 == 3) ">
                <td class="label"><tags:label key="MSG.SIK.018" required="true"/></td>
                <td class="text">

                    <s:if test="#request.changeSerial != null && #request.changeSerial == false && #request.showEdit*1 == 3 #request.showEdit*1 != 5">
                        <s:textfield name="collAccountManagmentForm.isdn" id="isdn" theme="simple" maxlength="100" cssClass="txtInputFull" onblur="getSerial()"/>
                    </s:if>
                    <s:else>
                        <s:textfield name="collAccountManagmentForm.isdn" id="isdn" theme="simple" maxlength="100" cssClass="txtInputFull" onblur="getSerial()" readonly="#request.changeSerial"/>
                    </s:else>
                </td>
            </s:if>
            <s:else>
                <td class="label"><tags:label key="MSG.SIK.018" required="false"/></td>
                <td class="text">
                    <s:textfield name="collAccountManagmentForm.isdn" id="isdn" theme="simple" maxlength="100" cssClass="txtInputFull" readonly="true" onblur="getSerial()"
                                 />
                </td>
            </s:else>
            <%--<td class="label">Số Serial</td>--%>
            <td class="label"><tags:label key="MSG.SIK.019"/></td>
            <td class="text">
                <s:textfield name="collAccountManagmentForm.serial" id="serial" theme="simple" maxlength="100" cssClass="txtInputFull"
                             readonly="true"/>
            </td>

            <td class="label"><tags:label key="L.100003"/></td>
            <td class="text" colspan="3">
                <s:if test="(#request.showButton*1 == 1 || #request.showEdit*1 == 3) ">
                    <s:if test="(#request.changeSerial != null && #request.changeSerial == false) || (#request.showEdit*1 == 1)">
                        <tags:imSelect name="collAccountManagmentForm.checkIsdn"
                                       id="checkIsdn" 
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       headerKey="1" headerValue="L.100004"
                                       list="0:L.100005"
                                       onchange="changeCheckIsdn(this)"
                                       />
                    </s:if>
                    <s:else>
                        <tags:imSelect name="collAccountManagmentForm.checkIsdn" 
                                       id="checkIsdn" disabled="${fn:escapeXml(changeSerial)}"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       headerKey="1" headerValue="L.100004"
                                       list="0:L.100005"
                                       onchange="changeCheckIsdn(this)"
                                       />
                    </s:else>
                </s:if>
                <s:else>
                    <tags:imSelect name="collAccountManagmentForm.checkIsdn"
                                   id="checkIsdn"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   headerKey="1" headerValue="L.100004"
                                   list="0:L.100005"
                                   onchange="changeCheckIsdn(this)" disabled="true"
                                   />
                </s:else>
            </td>
        </tr>

        <tr>
            <%--<td>Tỉnh/TP (<font color="red">*</font>)</td>--%>
            <td class="label"><tags:label key="MSG.SIK.020" required="true"/></td>
            <td colspan="3">
                <tags:imSearch codeVariable="collAccountManagmentForm.provinceCode" nameVariable="collAccountManagmentForm.provinceName"
                               codeLabel="MSG.SIK.021" nameLabel="MSG.SIK.022"
                               searchClassName="com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO"
                               elementNeedClearContent="collAccountManagmentForm.districtCode,collAccountManagmentForm.districtName,collAccountManagmentForm.wardCode,collAccountManagmentForm.wardName"
                               searchMethodName="getProvinceList"
                               readOnly="true"
                               getNameMethod="getProvinceName"
                               roleList=""/>
            </td>
            <%--<td>Quận/Huyện (<font color="red">*</font>)</td>--%>
            <td class="label"><tags:label key="MSG.SIK.023" required="true"/></td>
            <td colspan="3">
                <tags:imSearch codeVariable="collAccountManagmentForm.districtCode" nameVariable="collAccountManagmentForm.districtName"
                               codeLabel="MSG.SIK.024" nameLabel="MSG.SIK.025"
                               searchClassName="com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO"
                               searchMethodName="getDistrictList"
                               readOnly="true"
                               elementNeedClearContent="collAccountManagmentForm.wardCode,collAccountManagmentForm.wardName"
                               otherParam="collAccountManagmentForm.provinceCode"
                               getNameMethod="getDistrictName"
                               roleList=""/>
            </td>
        </tr>
        <tr>
            <%--<td>Phường/Xã (<font color="red">*</font>)</td>--%>
            <td class="label"><tags:label key="MSG.SIK.026" required="true"/></td>
            <td colspan="3">
                <tags:imSearch codeVariable="collAccountManagmentForm.wardCode" nameVariable="collAccountManagmentForm.wardName"
                               codeLabel="MSG.SIK.027" nameLabel="MSG.SIK.028"
                               searchClassName="com.viettel.im.database.DAO.AccountAnyPayFPTManagementDAO"
                               searchMethodName="getWardList"
                               readOnly="true"
                               otherParam="collAccountManagmentForm.districtCode;collAccountManagmentForm.provinceCode"
                               getNameMethod="getWardName"
                               roleList=""/>
            </td>
            <%--<td class="label">Tên (<font color="red">*</font>)</td>--%>
            <td class="label"><tags:label key="MSG.SIK.029" required="true"/></td>
            <td class="text" colspan="3">
                <s:textfield name="collAccountManagmentForm.nameAccount" theme="simple" id="nameAccount" maxlength="100" cssClass="txtInputFull" readonly="true"/>
            </td>

        </tr>
        <tr>
            <%--<td class="label">Đại diện </td>--%>
            <td class="label"><tags:label key="MSG.SIK.030"/></td>
            <td class="text" colspan="3">
                <s:textfield name="collAccountManagmentForm.namerepresentative" theme="simple" id="namerepresentative" maxlength="100" cssClass="txtInputFull" readonly="true"/>
            </td>
            <%--<td class="label">Địa chỉ (<font color="red">*</font>)</td>--%>
            <td class="label"><tags:label key="MSG.SIK.031" required="true"/></td>
            <td class="text" colspan="3">
                <s:textfield name="collAccountManagmentForm.address" theme="simple" id="address" maxlength="100" cssClass="txtInputFull" readonly="true"/>
            </td>
        </tr>
        <tr>
            <%--<td class="label">Ngày SN/TL (<font color="red">*</font>)</td>--%>
            <td class="label"><tags:label key="MSG.SIK.032" required="true"/></td>
            <td class="text">
                <tags:dateChooser property="collAccountManagmentForm.birthDate"  id="birthDate"  styleClass="txtInput" readOnly="true"  insertFrame="false"/>
            </td>
            <%--<td class="label">CMT/GPKD (<font color="red">*</font>)</td>--%>
            <td class="label"><tags:label key="MSG.SIK.033" required="true"/></td>
            <td class="text">
                <s:textfield name="collAccountManagmentForm.idNo" theme="simple" id="idNo" maxlength="14" cssClass="txtInputFull" readonly="true"/>
            </td>
            <%--<td class="label">Ngày Cấp (<font color="red">*</font>)</td>--%>
            <td class="label"><tags:label key="MSG.SIK.034" required="true"/></td>
            <td class="text">
                <tags:dateChooser property="collAccountManagmentForm.makeDate"  id="makeDate"  styleClass="txtInput" readOnly="true"  insertFrame="false"/>
            </td>
            <%--<td class="label">Nơi cấp (<font color="red">*</font>)</td>--%>
            <td class="label"><tags:label key="MSG.SIK.035" required="true"/></td>
            <td class="text">
                <s:textfield name="collAccountManagmentForm.makeAddress" theme="simple" id="makeAddress" maxlength="100" cssClass="txtInputFull" readonly="true"/>
            </td>
        </tr>

        <%--tr>
            <td class="label"><tags:label key="MSG.SIK.036"/></td>
            <td class="text">
                <s:textfield name="collAccountManagmentForm.phoneNumber" theme="simple" id="phoneNumber" maxlength="14" cssClass="txtInputFull" readonly="true"/>
            </td>
            <td class="label"><tags:label key="MSG.SIK.037"/></td>
            <td class="text">
                <s:textfield name="collAccountManagmentForm.fax" theme="simple" id="fax" maxlength="20" cssClass="txtInputFull" readonly="true"/>
            </td>
            <td class="label"><tags:label key="MSG.SIK.038"/></td>
            <td class="text" colspan="3">
                <s:textfield name="collAccountManagmentForm.email" theme="simple" id="email" maxlength="50" cssClass="txtInputFull" readonly="true"/>
            </td>
        </tr--%>

        <%--tr>
            <td class="label"><tags:label key="MSG.SIK.039" required="true"/></td>
            <td class="text" colspan="3">
                <s:textfield name="collAccountManagmentForm.secretQuestion" theme="simple" id="secretQuestion" maxlength="100" cssClass="txtInputFull" readonly="#request.changeInfo"/>
            </td>
            <td class="label"><tags:label key="MSG.SIK.040" required="true"/></td>
            <td class="text">
                <tags:dateChooser property="collAccountManagmentForm.datePassword"  id="datePassword"  styleClass="txtInput" readOnly="${fn:escapeXml(requestScope.changeInfo)}" insertFrame="false"/>
            </td>
            
            <s:if test="#request.showButton*1 == 1">
                <s:if test="collAccountManagmentForm.accountType != null && collAccountManagmentForm.accountType * 1 == 14 ">
                    <td class="label"><tags:label key="MSG.SIK.041" required="true"/></td>
                    <td class="text">                        
                        <tags:imSelect name="collAccountManagmentForm.checkVat" id="checkVat"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       list="1:MSG.SIK.042,0:MSG.SIK.043"
                                       />
                    </td>
                </s:if>
                <s:else>
                    <s:if test="collAccountManagmentForm.accountType != null && collAccountManagmentForm.accountType * 1 == 80043 ">
                        <td class="label"><tags:label key="MSG.SIK.041" required="true"/></td>
                        <td class="text">
                            <tags:imSelect name="f.checkVat" id="checkVat"
                                           cssClass="txtInputFull"
                                           theme="simple"
                                           list="1:MSG.SIK.044,0:MSG.SIK.045"
                                           />
                        </td>

                    </s:if>                  
                </s:else>
            </s:if>
            <s:else>
                <s:if test="collAccountManagmentForm.accountType != null && collAccountManagmentForm.accountType * 1 == 14 ">
                    <td class="label"><tags:label key="MSG.SIK.041" required="true"/></td>
                    <td class="text">                        
                        <tags:imSelect name="collAccountManagmentForm.checkVat" id="checkVat"
                                       cssClass="txtInputFull" 
                                       theme="simple"
                                       disabled="true"
                                       list="1:MSG.SIK.042,0:MSG.SIK.043"
                                       />
                    </td>
                </s:if>
                <s:else>
                    <s:if test="collAccountManagmentForm.accountType != null && collAccountManagmentForm.accountType * 1 == 80043 ">
                        <td class="label"><tags:label key="MSG.SIK.041" required="true"/></td>
                        <td class="text">                            
                            <tags:imSelect name="collAccountManagmentForm.checkVat" id="checkVat"
                                           cssClass="txtInputFull"
                                           theme="simple"
                                           disabled="true"
                                           list="1:MSG.SIK.044,0:MSG.SIK.045"
                                           />
                        </td>
                    </s:if>
                </s:else>
            </s:else>
        </tr--%>
        <tr>
            <%--<td class="label">Mật khẩu (<font color="red">*</font>)</td>--%>
            <td class="label"><tags:label key="MSG.SIK.046" required="true"/></td>
            <td class="text">
                <s:password name="collAccountManagmentForm.password" theme="simple" id="password" maxlength="50" cssClass="txtInputFull" readonly="#request.changePassword" showPassword="true"/>
            </td>
            <%--<td class="label">Nhập lại MK (<font color="red">*</font>)</td>--%>
            <td class="label"><tags:label key="MSG.SIK.047" required="true"/></td>
            <td class="text">
                <s:password name="collAccountManagmentForm.rePassword" theme="simple" id="rePassword" maxlength="50" cssClass="txtInputFull" readonly="#request.changePassword" showPassword="true"/>
            </td>
        </tr>
        <%--tr>
            <s:if test="#request.StatusDestroy*1 != 1">
                <s:if test="collAccountManagmentForm.statusAcc != null && (collAccountManagmentForm.statusAcc * 1 == 5
                      || collAccountManagmentForm.statusAcc * 1 == 8)">
                    <s:if test="collAccountManagmentForm.statusAcc * 1 == 5">
                        <td class="label"><tags:label key="MSG.SIK.002" required="true"/></td>
                        <td class="text">
                            <tags:imSelect name="collAccountManagmentForm.status" id="status"
                                           cssClass="txtInputFull"
                                           disabled="${fn:escapeXml(requestScope.changeStatus)}"
                                           theme="simple"
                                           list="5:Locked because of wrong MPIN"
                                           />
                        </td>
                    </s:if>
                    <s:else>
                        <td class="label"><tags:label key="MSG.SIK.002" required="true"/></td>
                        <td class="text">
                            <tags:imSelect name="collAccountManagmentForm.status" id="status"
                                           cssClass="txtInputFull"
                                           disabled="${fn:escapeXml(requestScope.changeStatus)}"
                                           theme="simple"
                                           list="8:Lock because of wrong registing information"
                                           />
                        </td>
                    </s:else>

                </s:if>
                <s:else>
                    <td class="label"><tags:label key="MSG.SIK.002" required="true"/></td>
                    <td class="text">
                        <tags:imSelect name="collAccountManagmentForm.status" id="status"
                                       cssClass="txtInputFull"
                                       disabled="${fn:escapeXml(requestScope.changeStatus)}"
                                       theme="simple"
                                       list="1:Active,0:Locked"
                                       />
                    </td>
                </s:else>
            </s:if>
            <s:else>
                <td class="label"><tags:label key="MSG.SIK.002" required="true"/></td>
                <td class="text">
                    <tags:imSelect name="collAccountManagmentForm.status" id="status"
                                   cssClass="txtInputFull"
                                   disabled="${fn:escapeXml(requestScope.changeStatus)}"
                                   theme="simple"
                                   list="2:Cancelled,1:Active,0:Locked"/>
                </td>
            </s:else>
            <td class="label"><tags:label key="MSG.SIK.049" required="true"/></td>
            <td class="text">                
                <tags:imSelect name="collAccountManagmentForm.reasonId" id="reasonId"
                               cssClass="txtInputFull"                               
                               disabled="${fn:escapeXml(requestScope.changeStatus)}"
                               theme="simple"
                               list="1:MSG.SIK.007,2:MSG.SIK.008"/>
            </td>
            <td class="label"><tags:label key="MSG.SIK.050" /></td>
            <td class="text">
                <s:textfield name="collAccountManagmentForm.serialNew" theme="simple" id="serialNew" maxlength="100" cssClass="txtInputFull" readonly="true"/>
            </td>
        </tr--%>

        <tr>
            <%--tutm1  28/09/2011 : Cho phép gạch nợ--%>
            <td class="label"><tags:label key="Cho phép gạch nợ" /></td>
            <td class="text">
                <s:if test="#request.changeInfo">
                    <s:checkbox name="collAccountManagmentForm.checkPayment" id="collAccountManagmentForm.checkPayment" 
                                onchange='checkPayment(this);' disabled="true"/>
                </s:if>
                <s:else>
                    <s:checkbox name="collAccountManagmentForm.checkPayment" id="collAccountManagmentForm.checkPayment" 
                                onchange='checkPayment(this);' disabled="false"/>                    
                </s:else>
            </td>
            <td class="label"><tags:label key="Số Imei" required="true"/></td>
            <td class="text">
                <s:if test="collAccountManagmentForm.checkPayment">
                    <s:textfield name="collAccountManagmentForm.imei" theme="simple" id="imei" maxlength="15" cssClass="txtInputFull" 
                                 readonly="#request.changeInfo" />
                </s:if>
                <s:else><s:textfield name="collAccountManagmentForm.imei" theme="simple" id="imei" maxlength="20" cssClass="txtInputFull" 
                                     readonly="true"/>
                </s:else>
            </td>
            <td class="label"><tags:label key="Giá trị gạch nợ hiện tại" required="true"/></td>
            <td class="text">
                <s:textfield name="collAccountManagmentForm.currentDebitStr" theme="simple" id="currentDebit" 
                             maxlength="17" cssClass="txtInputFull" readonly="true" cssStyle="text-align:left"/>
            </td>
            <td class="label"><tags:label key="Hạn mức gạch nợ" required="true"/></td>
            <td class="text">
                <s:if test="collAccountManagmentForm.checkPayment">
                    <s:textfield name="collAccountManagmentForm.limitDebitStr" theme="simple" id="limitDebit" maxlength="17" cssClass="txtInputFull" 
                                 readonly="#request.changeInfo" />
                </s:if>
                <s:else><s:textfield name="collAccountManagmentForm.limitDebitStr" theme="simple" id="limitDebit" maxlength="17" cssClass="txtInputFull" 
                                     readonly="true"/>
                </s:else>                
            </td>
        </tr>
    </table>
</sx:div>

<div style="height:10px">
</div>
<s:if test="#request.showButton*1 == 1 && #request.showEdit*1 !=5">
    <div class="divHasBorder" style="margin-top:10px; padding:3px;">
        <s:checkbox name="collAccountManagmentForm.createTKTT" id="collAccountManagmentForm.createTKTT"/>
        <%--<label>Tạo tài khoản TT</label>--%>
        <label>${fn:escapeXml(imDef:imGetText('MSG.SIK.051'))}</label>
        <s:checkbox name="collAccountManagmentForm.createAnyPay" id="collAccountManagmentForm.createAnyPay"/>
        <%--<label>Tạo tài khoản AnyPay</label>--%>
        <label>${fn:escapeXml(imDef:imGetText('MSG.SIK.052'))}</label>

    </div>
</s:if>
<s:else>
    <s:hidden name="collAccountManagmentForm.createTKTT" id="collAccountManagmentForm.createTKTT" value="true"/>
    <s:hidden name="collAccountManagmentForm.createAnyPay" id="collAccountManagmentForm.createAnyPay" value="true"/>
</s:else>

<div align="center" style="padding-bottom:5px;">   
    <s:if test="#request.showButton*1 == 1 && #request.showEdit*1 !=5">
        <td class="text" colspan="8" align="center" theme="simple">
            <tags:submit id="activeAnyPay" confirm="false" formId="collAccountManagmentForm"  cssStyle="width:120px;"
                         showLoadingText="true" targets="showView" value="MSG.SIK.053"
                         disabled="${fn:escapeXml(!pageScope.roleActive)}"
                         validateFunction="checkValidFieldsActive()"
                         preAction="CollAccountManagmentAction!activeAnyPay.do"
                         />
        </td>
    </s:if>

    <s:if test="#request.showEdit*1 == -1">
        <td class="text" theme="simple">
            <input type="button" style="width:120px;" onclick=""  value="${fn:escapeXml(imDef:imGetText('MSG.SIK.056'))}" disabled/>
        </td>
    </s:if>
    <s:else>
        <s:if test="#request.StatusDestroy*1 != 1">
            <s:if test="collAccountManagmentForm.statusAcc != null && (collAccountManagmentForm.statusAcc * 1 == 5
                  || collAccountManagmentForm.statusAcc * 1 == 8)">
                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.061'))}" style="width:120px;" onclick="viewLog()">
            </s:if>
            <s:else>
                <s:if test="#request.showEdit*1 == 0">
                    <td class="text" colspan="8" align="center" theme="simple">                        
                        <tags:submit id="changePassword" confirm="false" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                     showLoadingText="true" targets="showView" value="MSG.SIK.056"
                                     validateFunction="checkStatus()"
                                     disabled="${fn:escapeXml(!pageScope.roleChangePass)}"
                                     preAction="CollAccountManagmentAction!changePassword.do"
                                     />
                    </td>
                    <s:if test="collAccountManagmentForm.checkShowViewAccount != null && collAccountManagmentForm.checkShowViewAccount * 1 == 1 ">
                        <td class="text" colspan="8" align="center" theme="simple">
                            <tags:submit targets="accountDetail" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                         showLoadingText="true" 
                                         confirm="false"
                                         value="MSG.SIK.062"
                                         preAction="CollAccountManagmentAction!showAccountDetail.do"
                                         />
                        </td>
                    </s:if>
                    <s:else>
                        <td class="text" colspan="8" align="center" theme="simple">
                            <tags:submit targets="accountDetail" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                         showLoadingText="true" 
                                         confirm="false"
                                         value="MSG.SIK.062"
                                         preAction="CollAccountManagmentAction!showAccountDetail.do"
                                         />
                        </td>
                    </s:else>
                    <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.061'))}" style="width:100px;" onclick="viewLog()">
                </s:if>
                <s:else>
                    <s:if test="#request.showEdit*1 != 4">
                        <s:if test="#request.showEdit*1 == 2">
                            <td class="text" colspan="8" align="center" theme="simple">
                                <tags:submit id="save" confirm="true" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                             showLoadingText="true" targets="showView" value="MSG.SIK.063"
                                             validateFunction="checkValidChangePass()"
                                             confirmText="MSG.SIK.064"
                                             preAction="CollAccountManagmentAction!save.do"
                                             />
                            </td>
                            <td class="text" colspan="8" align="center" theme="simple">
                                <tags:submit id="cancel" confirm="false" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                             showLoadingText="true" targets="showView" value="MSG.SIK.065"
                                             preAction="CollAccountManagmentAction!cancel.do"
                                             />
                            </td>
                        </s:if>
                        <s:else>
                            <s:if test="#request.showEdit*1 == 1">
                                <td class="text" colspan="8" align="center" theme="simple">
                                    <tags:submit id="save" confirm="true" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                                 showLoadingText="true" targets="showView" value="MSG.SIK.063"
                                                 confirmText="MSG.SIK.066"
                                                 preAction="CollAccountManagmentAction!save.do"
                                                 />
                                </td>
                                <td class="text" colspan="8" align="center" theme="simple">
                                    <tags:submit id="cancel" confirm="false" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                                 showLoadingText="true" targets="showView" value="MSG.SIK.065"
                                                 preAction="CollAccountManagmentAction!cancel.do"
                                                 />
                                </td>
                            </s:if>
                            <s:else>
                                <s:if test="#request.showEdit*1 == 3">
                                    <td class="text" colspan="8" align="center" theme="simple">
                                        <tags:submit id="save" confirm="true" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                                     showLoadingText="true" targets="showView" value="MSG.SIK.063"
                                                     validateFunction="checkValidFieldsChangeInfo()"
                                                     confirmText="MSG.SIK.067"
                                                     preAction="CollAccountManagmentAction!save.do"
                                                     />
                                    </td>
                                    <td class="text" colspan="8" align="center" theme="simple">
                                        <tags:submit id="cancel" confirm="false" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                                     showLoadingText="true" targets="showView" value="MSG.SIK.065"
                                                     preAction="CollAccountManagmentAction!cancel.do"
                                                     />
                                    </td>
                                </s:if>
                                <s:else>
                                    <s:if test="#request.showEdit*1 == 5">
                                        <td class="text" colspan="8" align="center" theme="simple">                                            
                                            <tags:submit confirm="true" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                                         showLoadingText="true" targets="showView" value="MSG.SIK.053"
                                                         validateFunction="checkValidFieldsActive()"
                                                         preAction="CollAccountManagmentAction!reActiveAnyPay.do"
                                                         />
                                        </td>
                                        <td class="text" colspan="8" align="center" theme="simple">                                            
                                            <tags:submit confirm="false" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                                         showLoadingText="true" targets="showView" value="MSG.SIK.065"
                                                         preAction="CollAccountManagmentAction!cancelReactive.do"
                                                         />
                                        </td>
                                    </s:if>
                                </s:else>
                            </s:else>
                        </s:else>
                    </s:if>
                    <s:else>
                        <td class="text" colspan="8" align="center" theme="simple">
                            <tags:submit id="save" confirm="false" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                         showLoadingText="true" targets="showView" value="MSG.SIK.068"
                                         validateFunction="checkValidFields()"
                                         preAction="CollAccountManagmentAction!save.do"
                                         />
                        </td>
                        <td class="text" colspan="8" align="center" theme="simple">
                            <tags:submit id="cancel" confirm="false" formId="collAccountManagmentForm" cssStyle="width:120px;"
                                         showLoadingText="true" targets="showView" value="MSG.SIK.065"
                                         preAction="CollAccountManagmentAction!cancel.do"
                                         />
                        </td>
                    </s:else>
                </s:else>
            </s:else>
        </s:if>
        <s:else>
            <td class="text" colspan="8" align="center" theme="simple">
                <tags:submit id="reactiveAnyPay" confirm="false" formId="collAccountManagmentForm" cssStyle="width:120px;"
                             showLoadingText="true" targets="showView" value="MSG.SIK.270"
                             disabled="${fn:escapeXml(!pageScope.roleReActive)}"
                             preAction="CollAccountManagmentAction!showReactiveAnyPay.do"
                             />

            </td>
            <td class="text" colspan="8" align="center" theme="simple">
                <tags:submit targets="accountDetail" formId="collAccountManagmentForm" cssStyle="width:120px;"
                             showLoadingText="true" 
                             confirm="false"
                             value="MSG.SIK.062"
                             preAction="CollAccountManagmentAction!showAccountDetail.do"
                             />
            </td>
            <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.061'))}" style="width:120px;" onclick="viewLog()"
            </s:else>
        </s:else>

</div>


<script type="text/javascript" language="javascript">   
    
    //textFieldNF($('moneyAmount'));
    //textFieldNF($('realDebit'));
    validateform = function(){
        $('displayResultMsgClient').innerHTML="";
        var statusAgent=document.getElementById('statusAgent');
        if (statusAgent.value ==""){
    <%--$('displayResultMsgClient' ).innerHTML='Chưa chọn trạng thái tài khoản';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.001')"/>';
                $('statusAgent').focus();
                return false;
            }
            var serial=document.getElementById('serial');
            var isdn = document.getElementById('isdn');
            if (isdn.value ==""){

                if (${fn:escapeXml('checkIsdn')}.value == '1'){
    <%--$('displayResultMsgClient' ).innerHTML='Chưa nhập số ISDN';--%>
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.002')"/>';
                    $('isdn').focus();
                    return false;
                }


            }
            if (serial.value ==""){
    <%--$('displayResultMsgClient' ).innerHTML='Số ISDN chưa chính xác';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.003')"/>';
                $('isdn').focus();
                return false;
            }
            var password=document.getElementById('password');
            if (statusAgent.value ==""){
    <%--$('displayResultMsgClient' ).innerHTML='Chưa nhập mật khẩu tài khoản';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.004')"/>';
                $('password').focus();
                return false;
            }
            return true;
        }
        validateUpdateform = function(){
            var statusAgent=document.getElementById('statusAgent');
            if (statusAgent.value ==""){
    <%--$('displayResultMsgClient' ).innerHTML='Chưa chọn trạng thái tài khoản';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.001')"/>';
                $('statusAgent').focus();
                return false;
            }
            $('displayResultMsgClient').innerHTML="";
            var serial=document.getElementById('serial');
            var isdn = document.getElementById('isdn');
            if (isdn.value ==""){
                if (${fn:escapeXml('checkIsdn')}.value == '1'){
    <%--$('displayResultMsgClient' ).innerHTML='Chưa nhập số ISDN';--%>
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.002')"/>';
                    $('isdn').focus();
                    return false;
                }}
            if (serial.value ==""){
                if (${fn:escapeXml('checkIsdn')}.value == '1'){
    <%--$('displayResultMsgClient' ).innerHTML='Số ISDN chưa chính xác';--%>
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.003')"/>';
                    $('isdn').focus();
                    return false;
                }}
        
            var password=document.getElementById('password');
            if (statusAgent.value ==""){
    <%--$('displayResultMsgClient' ).innerHTML='Chưa nhập mật khẩu tài khoản';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.004')"/>';
                $('password').focus();
                return false;
            }
            return true;
        }

        checkStatus = function(){
            var statusAcc = document.getElementById("statusAcc");
            if (statusAcc.value*1 != 1){
    <%--$('displayResultMsgClient').innerHTML='Trạng thái tài khoản không phải là hoạt động nên không thể thực hiện được';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.005')"/>';
                return false;
            }
            return true

        }

        updateData_VuNT = function (url){
            dojo.io.bind({
                url:url ,
                error: function(type, data, evt){
                    alert("error");
                },
                handler: function(type, data, e) {
                    if(dojo.lang.isObject(data)) {
                        // Duyet qua data tra ve de tim cac truong can update
                        for(var keyValue in data){
                            var obj = document.getElementById(keyValue);
                            if(obj != null){
                                updateElement_VuNT(obj,data[keyValue]);
                            }
                        }
                    }
                },
                mimetype: "text/json"
            });
        }
        updateElement_VuNT = function(obj,data){
            var ogCbW;

            switch (obj.tagName) {
                case 'SELECT':
                    ogCbW = obj.offsetWidth;
                    clearAllChilds(obj);
                    updateChildToCombo(obj, data, ogCbW );
                    break;
                case 'TEXTAREA':
                    obj.value = data;
                    break;
                case 'INPUT':
                    if (obj.type=='checkbox'){
                        alert('Checkbox');
                        obj.checked = true;
                    }
                    if (obj.type=='text'){
                        obj.value = data;
                    }
                    break;
                case 'DIV':
                    obj.innerHTML = data;
                    break;
                default:
                    break;
            }

        }



        getSerial = function(){
            var check = '${fn:escapeXml(requestScope.checkSerial)}';
            if (check == '1'){

                if ($('checkIsdn').value != '1'){
                    return;
                }

                $("serial").value="";
                var isdn=$("isdn").value;

                var ownerId = $("collAccountManagmentForm.collId").value;
                updateData_VuNT("CollAccountManagmentAction!getSerial.do?target=serial&targetError=abcsd_09333&isdn=" + isdn +"&ownerId="+ownerId+"&pageId="+pageId);
            }
        }

        checkValidFieldsActive = function() {


            var serial=document.getElementById('serial');
            var isdn = document.getElementById('isdn');

            if ($('checkIsdn').value == '1'){
                if (isdn.value ==""){
    <%--$('displayResultMsgClient' ).innerHTML='Chưa nhập số ISDN';--%>
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.002')"/>';
                    $('isdn').focus();
                    return false;
                }
                if (serial.value ==""){
    <%--$('displayResultMsgClient' ).innerHTML='Số ISDN chưa chính xác';--%>
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.003')"/>';
                    $('isdn').focus();
                    return false;
                }
            }
   

            if ($('checkIsdn').value == '1'){
    <%--
        var datePassword = dojo.widget.byId("datePassword");
        if(datePassword.domNode.childNodes[1].value.trim() == ""){
            $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.017')"/>';
            $('datePassword').focus();
            return false;
        }
        if(!(datePassword.domNode.childNodes[1].value)){
            $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.018')"/>';
            $('datePassword').focus();
            return false;
        }
        var secretQuestion = document.getElementById("secretQuestion");
        if (secretQuestion.value ==null || secretQuestion.value ==""){
            $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.019')"/>';
            $('secretQuestion').focus();
            return false;
        }

                var password = document.getElementById("password");
                var rePassword = document.getElementById("rePassword");
                if (password.value ==null || password.value ==""){
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.020')"/>';
                    $('password').focus();
                    return false;
                }
                if (password.value.length <6){
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.021')"/>';
                    $('password').focus();
                    return false;
                }
                if(!isInteger(trim(password.value))){
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.022')"/>';
                    password.focus();
                    return false;
                }
                if (password.value ==null || password.value ==""){
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.021')"/>';
                    $('password').focus();
                    return false;
                }
        
                if (password.value != rePassword.value){
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.023')"/>';
                    $('password').focus();
                    return false;
                }
    --%>

            }else{
                $('isdn').value = '';
                $('serial').value = '';
                $('password').value = '';
                $('rePassword').value = '';
            }
            
            // tutm1 - 29/09/2011 kiem tra imei va hạn mức gạch nợ
            checkPaymentObj = $('collAccountManagmentForm.checkPayment');
            if (checkPaymentObj != null && checkPaymentObj.checked == true) {
                if ($('imei').value.trim() == null || $('imei').value.trim() == ""){
                    $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.STK.131')"/>';
                    $('imei').focus();
                    return false;
                }

                if (!isInteger($('imei').value.trim())){
                    $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.STK.132')"/>';
                    $('imei').focus();
                    return false;
                }

                if ($('limitDebit').value.trim() == null || $('limitDebit').value.trim() == ""){
                    $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.STK.130')"/>';
                    $('limitDebit').focus();
                    return false;
                }
                if (!isDouble($('limitDebit').value.trim())){
                    $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.STK.129')"/>';
                    $('limitDebit').focus();
                    return false;
                }
            }
                
            return true;
        }
        
        checkValidChangePass = function() {
            var password = document.getElementById("password");
            var rePassword = document.getElementById("rePassword");
            if (password.value ==null || password.value ==""){
    <%--$('displayResultMsgClient').innerHTML='Chưa nhập mật khẩu';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.020')"/>';
                $('password').focus();
                return false;
            }
            if(!isInteger(trim(password.value))){
    <%--$('displayResultMsgClient').innerHTML="Mật khẩu phải là số"--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.022')"/>';
                password.focus();
                return false;
            }
            if (password.value.length <6){
    <%--$('displayResultMsgClient').innerHTML='Độ dài mật khẩu chưa đủ 6 ký tự';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.021')"/>';
                $('password').focus();
                return false;
            }

            if (password.value != rePassword.value){
    <%--$('displayResultMsgClient').innerHTML='Mật khẩu nhập lại không chính xác';--%>
                $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.023')"/>';
                $('password').focus();
                return false;
            }
            return true;
        }

        checkValidFieldsChangeInfo = function() {
        
            if ($('checkIsdn').value == '1'){
            
                var datePassword = dojo.widget.byId("datePassword");
                if(datePassword.domNode.childNodes[1].value.trim() == ""){
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.017')"/>';
                    $('datePassword').focus();
                    return false;
                }
                if(!(datePassword.domNode.childNodes[1].value)){
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.018')"/>';
                    $('datePassword').focus();
                    return false;
                }
                var secretQuestion = document.getElementById("secretQuestion");
                if (secretQuestion.value ==null || secretQuestion.value ==""){
                    $('displayResultMsgClient').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.019')"/>';
                    $('secretQuestion').focus();
                    return false;
                }

            }else{
                $('isdn').value = '';
                $('serial').value = '';
            }
            
            
            // tutm1 - 29/09/2011 kiem tra imei va hạn mức gạch nợ
            checkPaymentObj = $('collAccountManagmentForm.checkPayment');
            if (checkPaymentObj != null && checkPaymentObj.checked == true) {
                if ($('imei').value.trim() == null || $('imei').value.trim() == ""){
                    $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.STK.131')"/>';
                    $('imei').focus();
                    return false;
                }

                if (!isInteger($('imei').value.trim())){
                    $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.STK.132')"/>';
                    $('imei').focus();
                    return false;
                }

                if ($('limitDebit').value.trim() == null || $('limitDebit').value.trim() == ""){
                    $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.STK.130')"/>';
                    $('limitDebit').focus();
                    return false;
                }
                if (!isDouble($('limitDebit').value.trim())){
                    $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.STK.129')"/>';
                    $('limitDebit').focus();
                    return false;
                }
            }
            
            return true;
        }

        viewLog = function() {
            var accountId = document.getElementById("collAccountManagmentForm.accountIdAgent");
            openDialog('CollAccountManagmentAction!showLogAccountAgent.do?accountId=' + accountId.value,screen.width,screen.height,false);
        }
        
    
        changeCheckIsdn = function(checkHaveSim){
       
            $('isdn').value = '';
            $('serial').value = '';
            $('secretQuestion').value = '';
            $('password').value = '';
            $('rePassword').value = '';
            var datePassword = dojo.widget.byId("datePassword");
            datePassword.domNode.childNodes[1].value = '';


            //            alert($('collAccountManagmentForm.createAnyPay').value);



            if (checkHaveSim.value == '1'){
                $('isdn').disabled = false;
                $('serial').value = '';

                //                $('isdn').style.display = 'none';
                //                $('collAccountManagmentForm.createAnyPay').value= 'false';

            }else{
                $('serial').value = '';
                
                $('isdn').disabled = true;
                //                $('isdn').style.display = 'block';
            }

        
        }
        // tutm1 : 29/09/11 
        // khi check cho phep CTV gạch nợ
        checkPayment = function(checkPaymentObj){
            if (checkPaymentObj.checked == true) {
                $('imei').readOnly = false;
                //                $('currentDebit').readOnly = false;
                $('limitDebit').readOnly = false;
            }
            else {
                $('imei').readOnly = true;
                //                $('currentDebit').readOnly = true;
                $('limitDebit').readOnly = true;
            }
        }
        
        checkShowIsdnInfo();
        
        checkShowIsdnInfo = function(){
            if ($('checkIsdn').value == '0'){
                $('isdn').disabled = true;
            }else{
                $('isdn').disabled = false;
            }
        }
        
        
</script>
