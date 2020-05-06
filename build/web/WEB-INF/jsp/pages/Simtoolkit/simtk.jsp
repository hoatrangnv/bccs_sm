<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : simtk
    Created on : Jan 18, 2012, 11:51:08 AM
    Author     : TrongLV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    String pageId = request.getParameter("pageId");

    request.setAttribute("roleUpdateLinkStk", request.getSession().getAttribute("ROLE_UPDATE_LINK_STK" + pageId));

    request.setAttribute("roleActiveStk", request.getSession().getAttribute("ROLE_ACTIVE_STK" + pageId));
    request.setAttribute("roleReActiveStk", request.getSession().getAttribute("ROLE_REACTIVE_STK" + pageId));
    request.setAttribute("roleInActiveStk", request.getSession().getAttribute("ROLE_INACTIVE_STK" + pageId));
    request.setAttribute("roleChangePassStk", request.getSession().getAttribute("ROLE_CHANGE_PASS_STK" + pageId));
    request.setAttribute("roleUpdateStk", request.getSession().getAttribute("ROLE_UPDATE_STK" + pageId));

    request.setAttribute("roleReceiveDepositStk", request.getSession().getAttribute("ROLE_RECEIVE_DEPOSIT" + pageId));
    request.setAttribute("rolePayDepositStk", request.getSession().getAttribute("ROLE_PAY_DEPOSIT" + pageId));
    request.setAttribute("roleActiveBalance", request.getSession().getAttribute("ROLE_ACTIVE_ACCOUNT_BALANCE" + pageId));
    request.setAttribute("roleUpdateBalance", request.getSession().getAttribute("ROLE_UPDATE_ACCOUNT_BALANCE" + pageId));
    request.setAttribute("roleInActiveBalance", request.getSession().getAttribute("ROLE_INACTIVE_ACCOUNT_BALANCE" + pageId));

    request.setAttribute("roleActiveAnypay", request.getSession().getAttribute("ROLE_ACTIVE_ACCOUNT_ANYPAY" + pageId));
    request.setAttribute("roleUpdateAnypay", request.getSession().getAttribute("ROLE_UPDATE_ACCOUNT_ANYPAY" + pageId));
    request.setAttribute("roleInActiveAnypay", request.getSession().getAttribute("ROLE_INACTIVE_ACCOUNT_ANYPAY" + pageId));

    request.setAttribute("roleActivePayment", request.getSession().getAttribute("ROLE_ACTIVE_ACCOUNT_PAYMENT" + pageId));
    request.setAttribute("roleUpdatePayment", request.getSession().getAttribute("ROLE_UPDATE_ACCOUNT_PAYMENT" + pageId));
    request.setAttribute("roleInActivePayment", request.getSession().getAttribute("ROLE_INACTIVE_ACCOUNT_PAYMENT" + pageId));

    request.setAttribute("listStatusNotCanncel", request.getSession().getAttribute("LIST_CHANNEL_STATUS_NOT_CANCEL_STATUS"));
    request.setAttribute("listStatus", request.getSession().getAttribute("LIST_CHANNEL_STATUS_STATUS"));

%>

<s:hidden name="form.vfSimtk.shopId"/>
<s:hidden name="form.vfSimtk.staffId"/>
<s:hidden name="form.vfSimtk.ownerId" id="form.vfSimtk.ownerId"/>
<s:hidden name="form.vfSimtk.ownerType" id="form.vfSimtk.ownerType"/>
<s:hidden name="form.vfSimtk.channelTypeId"/>
<s:hidden name="form.vfSimtk.isVtUnit" id="form.vfSimtk.isVtUnit"/>
<s:hidden name="form.vfSimtk.accountId" id="form.vfSimtk.accountId"/>
<s:hidden name="form.vfSimtk.isViewAccountDetail"/>

<s:if test="form.vfSimtk.accountId != null && form.vfSimtk.accountStatus != 0 ">
    <c:set var="isUpdateAccount" scope="page" value="true" />
</s:if>
<s:else>
    <c:set var="isUpdateAccount" scope="page" value="false" />
</s:else>



<div>
    <tags:displayResult id="displayResultMsgSimtk" property="messageParam" propertyValue="paramMsg" type="key" />
</div> 


<%--
check show staff or channel or big agent

--%>

<table class="inputTbl4Col">
    <tr>
        <td class="label"><tags:label key="MSG.shop.code"/></td>
        <td class="text">
            <s:textfield id="form.vfSimtk.shopCode" name="form.vfSimtk.shopCode" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
        </td>
        <td class="label"><tags:label key="MSG.shop.name"/></td>
        <td class="text">
            <s:textfield id="form.vfSimtk.shopName" name="form.vfSimtk.shopName" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
        </td>
    </tr>

    <s:if test="#attr.form.objectTypeSearch == 2 "><!--    neu la staff; co the la nhan vien hay la kenh-->
        <tr>
            <td class="label"><tags:label key="MES.CHL.064"/></td>
            <td class="text">
                <s:textfield id="form.vfSimtk.staffCode" name="form.vfSimtk.staffCode" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
            </td>
            <td class="label"><tags:label key="MES.CHL.065"/></td>
            <td class="text">
                <s:textfield id="form.vfSimtk.staffName" name="form.vfSimtk.staffName" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
            </td>
        </tr>

        <s:if test="#attr.form.isVtUnitSearch == 2 "><!--        neu la kenh-->       
            <tr>
                <td class="label"><tags:label key="MSG.chanel.code"/></td>
                <td class="text">
                    <s:textfield id="form.vfSimtk.ownerCode" name="form.vfSimtk.ownerCode" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
                </td>
                <td class="label"><tags:label key="MSG.channel.name"/></td>
                <td class="text">
                    <s:textfield id="form.vfSimtk.ownerName" name="form.vfSimtk.ownerName" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
                </td>
            </tr>
        </s:if>
    </s:if>

    <s:else><!--neu la shop; la dai ly            -->
        <tr>
            <td class="label"><tags:label key="MSG.channel.code"/></td>
            <td class="text">
                <s:textfield id="form.vfSimtk.ownerCode" name="form.vfSimtk.ownerCode" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
            </td>
            <td class="label"><tags:label key="MSG.channel.name"/></td>
            <td class="text">
                <s:textfield id="form.vfSimtk.ownerName" name="form.vfSimtk.ownerName" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
            </td>
        </tr>
    </s:else>


    <tr>
        <td class="label"><tags:label key="MES.CHL.140"/></td>
        <td class="text">
            <s:textfield id="form.vfSimtk.channelTypeName" name="form.vfSimtk.channelTypeName" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
        </td>
        <td></td>
        <td></td>
    </tr>

    <tr>
        <td class="label"><tags:label key="MSG.province.code"/></td>
        <td class="text">
            <tags:imSearch codeVariable="form.vfSimtk.province" nameVariable="form.vfSimtk.provinceName"
                           codeLabel="MSG.SIK.024" nameLabel="MSG.SIK.025"
                           readOnly="true"
                           roleList=""/>            
        </td>
        <td class="label"><tags:label key="MSG.district.code"/></td>
        <td class="text">
            <tags:imSearch codeVariable="form.vfSimtk.district" nameVariable="form.vfSimtk.districtName"
                           codeLabel="MSG.SIK.024" nameLabel="MSG.SIK.025"
                           readOnly="true"
                           roleList=""/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.village.code"/></td>
        <td class="text">
            <tags:imSearch codeVariable="form.vfSimtk.precinct" nameVariable="form.vfSimtk.precinctName"
                           codeLabel="MSG.SIK.024" nameLabel="MSG.SIK.025"
                           readOnly="true"
                           roleList=""/>
        </td>

        <td class="label"><tags:label key="L.200003"/></td>
        <td class="text">
            <s:textfield id="form.vfSimtk.streetBlockName" name="form.vfSimtk.streetBlockName" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
        </td>        
    </tr>
    <tr>
        <td class="label"><tags:label key="L.200004"/></td>
        <td class="text">
            <s:textfield id="form.vfSimtk.streetName" name="form.vfSimtk.streetName" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
        </td>
        <td class="label"><tags:label key="L.200005"/></td>
        <td class="text">
            <s:textfield id="form.vfSimtk.home" name="form.vfSimtk.home" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MSG.address"/></td>
        <td class="text" colspan="3">
            <s:textfield id="form.vfSimtk.address" name="form.vfSimtk.address" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="L.100013"/></td>
        <td class="text">
            <s:textfield id="form.vfSimtk.tradeName" name="form.vfSimtk.tradeName" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
        </td>
        <td class="label"><tags:label key="L.100014"/></td>
        <td class="text">
            <s:textfield id="form.vfSimtk.contactName" name="form.vfSimtk.contactName" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MES.CHL.057"/></td>
        <td class="text">
            <s:textfield id="form.vfSimtk.idNo" name="form.vfSimtk.idNo" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true" />
        </td>
        <td class="label"><tags:label key="MSG.SIK.034"/></td>
        <td class="text">
            <tags:dateChooser property="form.vfSimtk.idIssueDate"  id="form.vfSimtk.idIssueDate" styleClass="txtInputFull"  insertFrame="false" readOnly="true"/>
        </td>
    </tr>
    <tr>
        <td class="label"><tags:label key="MES.CHL.066"/></td>
        <td class="text">
            <s:textfield id="form.vfSimtk.idIssuePlace" name="form.vfSimtk.idIssuePlace" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
        </td>
        <td class="label"><tags:label key="MES.CHL.082"/></td>
        <td class="text">
            <tags:dateChooser property="form.vfSimtk.birthday"  id="form.vfSimtk.birthday" styleClass="txtInputFull"  insertFrame="false" readOnly="true"/>
        </td>       
    </tr>

    <s:if test="form.vfSimtk.accountId == null || form.vfSimtk.accountStatus == 0 ">
        <tr>
            <td class="label"><tags:label key="L.100003" required="true"/></td>
            <%--<s:if test="form.vfSimtk.isVtUnit == 2">--%>
            <td class="text">
                <tags:imSelect name="form.vfSimtk.checkIsdn"
                               id="form.vfSimtk.checkIsdn"
                               cssClass="txtInputFull" 
                               theme="simple"
                               headerKey="1" headerValue="L.100004"
                               list="0:L.100005"
                               disabled="false"
                               />
            </td>
            <%--</s:if>--%>
            <%--<s:else>--%>
            <!--                <td class="text">
            <%--<tags:imSelect name="form.vfSimtk.checkIsdn"--%>
                           id="form.vfSimtk.checkIsdn"
                           cssClass="txtInputFull" 
                           theme="simple"
                           headerKey="1" headerValue="L.100004"
                           list="0:L.100005,2:L.10000100"
                           disabled="true"
                           />
        </td>-->
            <%--</s:else>--%>

            <td class="label"><tags:label key="IMEI"/></td>
            <td class="text">
                <s:textfield id="form.vfSimtk.imei" name="form.vfSimtk.imei" theme="simple" cssClass="txtInputFull" maxlength="15"/>
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="ISDN"/></td>
            <td class="text">
                <%--s:textfield readonly="true" id="form.vfSimtk.msisdn" name="form.vfSimtk.msisdn" theme="simple" cssClass="txtInputFull" maxlength="20" onblur="getSerial()"/--%>
                <s:textfield  id="form.vfSimtk.msisdn" name="form.vfSimtk.msisdn" theme="simple" cssClass="txtInputFull" maxlength="20"/>
            </td>
            <td class="label"><tags:label key="SERIAL"/></td>
            <td class="text">
                <s:textfield id="form.vfSimtk.iccid" name="form.vfSimtk.iccid" theme="simple" cssClass="txtInputFull" maxlength="20"/>
            </td>
        </tr>

        <!--    truong hop kich hoat lai && la kenh nvql  -->        
        <s:if test="form.vfSimtk.isVtUnit == 1 && form.vfSimtk.accountId != null && form.vfSimtk.accountStatus == 0 ">
            <tr>
                <td class="label"><tags:label key="MSG.SIK.074" required="true"/></td>
                <td class="text">
                    <%--s:hidden name="form.vfSimtk.accountStatus"/--%>

                    <tags:imSelect name="form.vfSimtk.accountStatus" id="form.vfSimtk.accountStatus"
                                   cssClass="txtInputFull"
                                   theme="simple"
                                   disabled="true"
                                   headerKey="" headerValue="MSG.SIK.252"
                                   list="listStatus" listKey="code" listValue="name"
                                   />
                </td>
                <td></td><td></td>
            </tr>    
        </s:if>
    </s:if>

    <s:else>
        <tr>
            <td class="label"><tags:label key="L.100003"/></td>
            <td class="text">
                <tags:imSelect name="form.vfSimtk.checkIsdn"
                               id="form.vfSimtk.checkIsdn"
                               cssClass="txtInputFull" 
                               theme="simple"
                               disabled="true"
                               headerKey="1" headerValue="L.100004"
                               list="0:L.100005"
                               onchange=""
                               />
            </td>
            <td class="label"><tags:label key="IMEI"/></td>
            <td class="text">
                <s:textfield id="form.vfSimtk.imei" name="form.vfSimtk.imei" theme="simple" cssClass="txtInputFull" maxlength="15" readonly="true"/>
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="ISDN"/></td>
            <td class="text">
                <s:textfield id="form.vfSimtk.msisdn" name="form.vfSimtk.msisdn" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true" />
            </td>
            <td class="label"><tags:label key="SERIAL"/></td>
            <td class="text">
                <s:textfield id="form.vfSimtk.iccid" name="form.vfSimtk.iccid" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
            </td>

        </tr>

        <!--    isdn & serial moi-->
        <s:if test="form.vfSimtk.newMsisdn != null && form.vfSimtk.newMsisdn != '' ">
            <tr><td></td><td></td><td></td><td></td></tr>
            <tr>
                <td class="label"><tags:label key="NEW ISDN"/></td>
                <td class="text">
                    <font color="red">
                        <s:textfield id="form.vfSimtk.newMsisdn" name="form.vfSimtk.newMsisdn" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
                    </font>
                </td>
                <td class="label"><tags:label key="NEW SERIAL"/></td>
                <td class="text">
                    <font color="red">
                        <s:textfield id="form.vfSimtk.newIccid" name="form.vfSimtk.newIccid" theme="simple" cssClass="txtInputFull" maxlength="20" readonly="true"/>
                    </font>
                </td>
            </tr>
        </s:if>    
        <!--    isdn & serial moi-->

        <s:if test="form.vfSimtk.isVtUnit == 1 ">
            <tr>
                <td class="label"><tags:label key="MSG.SIK.074" required="true"/></td>
                <td class="text">            
                    <s:if test="form.vfSimtk.accountId != null && form.vfSimtk.accountStatus != 0 && form.vfSimtk.accountStatus != 4 ">
                        <tags:imSelect name="form.vfSimtk.accountStatus" id="form.vfSimtk.accountStatus"
                                       cssClass="txtInputFull"
                                       theme="simple"                                   
                                       headerKey="" headerValue="MSG.SIK.252"
                                       list="listStatusNotCanncel" listKey="code" listValue="name"
                                       />
                    </s:if>
                    <s:else>
                        <%--s:hidden name="form.vfSimtk.accountStatus"/--%>
                        <tags:imSelect name="form.vfSimtk.accountStatus" id="form.vfSimtk.accountStatus"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       disabled="true"
                                       headerKey="" headerValue="MSG.SIK.252"
                                       list="listStatus" listKey="code" listValue="name"
                                       />
                    </s:else>
                </td>
                <td></td><td></td>
            </tr>
        </s:if>
    </s:else>
</table>
<!--nếu chưa có tài khoản thị chọn 3 option tài khoản-->
<%--<s:if test="form.vfSimtk.accountId == null && form.vfSimtk.isVtUnit == 2 ">--%>
<s:if test="form.vfSimtk.accountId == null">
    <div class="divHasBorder" style="margin-top:10px; padding:3px;">
        <s:checkbox name="form.vfSimtk.createAccountBalance" id="form.vfSimtk.createAccountBalance"/>
        <label><s:property escapeJavaScript="true"  value="getText('MSG.SIK.051')"/></label>
        <s:checkbox name="form.vfSimtk.createAccountAnypay" id="form.vfSimtk.createAccountAnypay"/>
        <label><s:property escapeJavaScript="true"  value="getText('MSG.SIK.052')"/></label>
        <s:checkbox name="form.vfSimtk.createAccountPayment" id="form.vfSimtk.createAccountPayment"/>
        <label><s:property escapeJavaScript="true"  value="getText('L.200002')"/></label>
    </div>
</s:if>
<%--</s:if>--%>

<div align="center" style="padding-bottom:5px;">    
    <!--    truong hop kich hoat moi hoac kich hoat lai-->
    <s:if test="form.vfSimtk.accountId == null || form.vfSimtk.accountStatus == 0 || form.vfSimtk.accountStatus == 4 ">
        <!--Nếu chưa có tài khoản thì hiển thị button active-->
        <s:if test="form.vfSimtk.accountId == null ">
            <tags:submit  formId="form"
                          cssStyle="width:120px;" validateFunction="checkValidateActiveStk();"
                          showLoadingText="true" confirm="true" confirmText="C.200002"
                          targets="simtkArea" 
                          value="MSG.SIK.053" 
                          disabled="${fn:escapeXml(!requestScope.roleActiveStk)}"
                          preAction="simtkManagmentAction!activeSimtk.do"
                          />

        </s:if>
        <!--Có tài khoản nhưng đang ở trạng tái khóa thì hiển thị button unlock-->
        <s:elseif test="form.vfSimtk.accountStatus == 4 ">
            <tags:submit  formId="form"
                          cssStyle="width:120px;"
                          showLoadingText="true" 
                          targets="simtkArea"  confirm="true" confirmText="C.200006"
                          value="Unlock" disabled="${fn:escapeXml(!requestScope.roleUpdateStk)}"
                          preAction="simtkManagmentAction!unlockSimtk.do"
                          />
            <!--Nếu là CTV thì hiển thị thêm button viewaccountDetail-->
            <s:if test="form.vfSimtk.isVtUnit == 2 ">
                <tags:submit targets="simtkArea" formId="form" cssStyle="width:120px;"
                             showLoadingText="true" 
                             confirm="false"
                             value="MSG.SIK.062"
                             preAction="simtkManagmentAction!viewAccountDetail.do"
                             />
            </s:if>
        </s:elseif>
        <!--Nếu accountStatus (trạng thái tài khoản đang bị hủy) hiển thị button reActive cho phép kích hoạt lại-->
        <s:elseif test="form.vfSimtk.accountStatus == 0 ">
            <tags:submit  formId="form"
                          cssStyle="width:120px;" validateFunction="checkValidateActiveStk();"
                          showLoadingText="true" 
                          targets="simtkArea"  confirm="true" confirmText="C.200003"
                          value="MSG.SIK.270" disabled="${fn:escapeXml(!requestScope.roleReActiveStk)}"
                          preAction="simtkManagmentAction!reActiveSimtk.do"
                          />
        </s:elseif>
        <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.061')"/>" style="width:120px;" onclick="viewLog()"
        </s:if>

        <!--truong hop dang hoat dong va la nhân viên quản lý -->
        <s:elseif test="form.vfSimtk.isVtUnit == 1 ">
            <!--truong hop dang hoat dong va la kenh-->
            <!--Minhtn7 R8452  ChangeSim-->
            <!--Đổi sim (SDN/Thường) sang Thương-->
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('L.200065')"/>" style="width:120px;" onclick="changeISDN()"
            <!--Minhtn7 R8452  ChangeSim-->
            <tags:submit targets="simtkArea" formId="form" cssStyle="width:120px;"
                         showLoadingText="true" 
                         confirm="false"
                         value="L.200035"
                         preAction="simtkManagmentAction!updateLinkStk.do"
                         disabled="${fn:escapeXml(!requestScope.roleUpdateLinkStk)}"
                         />
            <s:if test="#request.roleChangePassStk == true ">
                <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.056')"/>" style="width:120px;" onclick="changePassword()"
            </s:if>
            <s:else>
                <!--        getText('MSG.SIK.056')-->
                <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.056')"/>" style="width:120px;" disabled/>
        </s:else>
        <tags:submit  formId="form"
                      cssStyle="width:120px;" 
                      showLoadingText="true" confirm="true" confirmText="C.200004"
                      targets="simtkArea"
                      value="MSG.SIK.009"  disabled="${fn:escapeXml(!requestScope.roleUpdateStk)}"
                      preAction="simtkManagmentAction!updateSimtk.do"
                      />
        <tags:submit formId="form"
                     cssStyle="width:120px;"
                     showLoadingText="true" confirm="true" confirmText="C.200005"
                     targets="simtkArea"
                     value="MSG.SIK.010"  disabled="${fn:escapeXml(!requestScope.roleInActiveStk)}"
                     preAction="simtkManagmentAction!inActiveSimtk.do"
                     />
        <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.061')"/>" style="width:120px;" onclick="viewLog()"
        </s:elseif>
        <s:else>  
            <!--Minhtn7 R8452  ChangeSim-->
            <!--Đổi sim (SDN/Thường) sang Thương-->
            <input type="button" value="<s:property escapeJavaScript="true"  value="getText('L.200065')"/>" style="width:120px;" onclick="changeISDN()"
            <!--Minhtn7 R8452  ChangeSim-->
            <!--truong hop dang hoat dong va la kenh-->
            <tags:submit targets="simtkArea" formId="form" cssStyle="width:120px;"
                         showLoadingText="true" 
                         confirm="false"
                         value="L.200035"
                         preAction="simtkManagmentAction!updateLinkStk.do"
                         disabled="${fn:escapeXml(!requestScope.roleUpdateLinkStk)}"
                         />

            <s:if test="#request.roleChangePassStk == true ">
                <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.056')"/>" style="width:120px;" onclick="changePassword()"
            </s:if>
            <s:else>
                <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.056')"/>" style="width:120px;" disabled/>
        </s:else>
        <tags:submit targets="simtkArea" formId="form" cssStyle="width:120px;"
                     showLoadingText="true" 
                     confirm="false"
                     value="MSG.SIK.062"
                     preAction="simtkManagmentAction!viewAccountDetail.do"
                     />
        <input type="button" value="<s:property escapeJavaScript="true"  value="getText('MSG.SIK.061')"/>" style="width:120px;" onclick="viewLog()"
        </s:else>
</div>

<sx:div id="accountDetailArea" theme="simple">
    <s:if test="form.vfSimtk != null && form.vfSimtk.accountId != null && form.vfSimtk.isViewAccountDetail == true ">
        <sx:tabbedpanel id="tabContainer">
            <sx:div key="MSG.SIK.247" id="accountBalance">
                <tags:imPanel title="MSG.SIK.247">
                    <sx:div id="accountBalance1" theme="simple">
                        <jsp:include page="accountBalance.jsp"/>
                    </sx:div>
                </tags:imPanel>
            </sx:div>
            <sx:div key="MSG.SIK.248" id="accountAnypay">
                <tags:imPanel title="MSG.SIK.248">
                    <sx:div id="accountAnypay1" theme="simple">
                        <jsp:include page="accountAnypay.jsp"/>
                    </sx:div>
                </tags:imPanel>
            </sx:div>
            <sx:div key="L.200002" id="accountPayment">
                <tags:imPanel title="L.200002">
                    <sx:div id="accountPayment1"  theme="simple">
                        <jsp:include page="accountPayment.jsp"/>
                    </sx:div>
                </tags:imPanel>
            </sx:div>                
        </sx:tabbedpanel>
    </s:if>
</sx:div>
<script language="javascript">

            checkValidateActiveStk = function() {
                if (trim($("form.vfSimtk.checkIsdn").value) == '1') {

                    if (trim($("form.vfSimtk.msisdn").value) == '') {
                        $('form.vfSimtk.msisdn').select();
                        $('form.vfSimtk.msisdn').focus();
                        $('displayResultMsgSimtk').innerHTML = '<s:text name ="E.200017"/>';
                        return false;
                    }

//            if (trim($("form.vfSimtk.iccid").value) == ''){
//                $('form.vfSimtk.iccid').select();
//                $('form.vfSimtk.iccid').focus();
//                $('displayResultMsgSimtk').innerHTML='<s:text name ="E.200018"/>';
//                return false;
//            }

                    if ($("form.vfSimtk.isVtUnit").value == '2') {
                        if ($("form.vfSimtk.createAccountPayment").value == true) {
                            if (trim($("form.vfSimtk.imei").value) == '') {
                                $('form.vfSimtk.imei').select();
                                $('form.vfSimtk.imei').focus();
                                $('displayResultMsgSimtk').innerHTML = '<s:text name ="E.200029"/>';
                            }
                        }
                    }
                }

                if (trim($("form.vfSimtk.checkIsdn").value) == '0') {
                    $('form.vfSimtk.msisdn').value = '';
                    $('form.vfSimtk.iccid').value = '';
                    $('form.vfSimtk.imei').value = '';
                }
                return true;
            }



            selectCheckIsdn = function(checkIsdn) {
                if (checkIsdn.value == '1') {
                    $('form.vfSimtk.msisdn').disabled = false;
                    $('form.vfSimtk.msisdn').value = '';
                } else {
                    $('form.vfSimtk.msisdn').value = '';
                    $('form.vfSimtk.msisdn').disabled = true;
                }
            }

            viewLog = function() {
                var accountId = document.getElementById("form.vfSimtk.accountId");
                openDialog('simtkManagmentAction!showLogAccountAgent.do?accountId=' + accountId.value, screen.width, screen.height, false);
            }

            changePassword = function() {
                var accountId = document.getElementById("form.vfSimtk.accountId");
                openDialog('simtkManagmentAction!prepareChangePassword.do?accountId=' + accountId.value, screen.width - 500, screen.height - 450, true);
            }


            changeISDN = function() {
                var accountId = document.getElementById("form.vfSimtk.accountId");
                var idNo = document.getElementById("form.vfSimtk.idNo");
                var msisdn = document.getElementById("form.vfSimtk.msisdn")
                openDialog('simtkManagmentAction!prepareChangeISDN.do?accountId=' + accountId.value + '&msisdn=' + msisdn.value + '&idNo=' + idNo.value.toString(), screen.width - 500, screen.height - 450, true);
            }


            getSerial = function() {
                if ($('form.vfSimtk.checkIsdn').value != '1') {
                    return;
                }
                $("form.vfSimtk.iccid").value = '';
                var isdn = $("form.vfSimtk.msisdn").value;
                var ownerId = $("form.vfSimtk.ownerId").value;
                var ownerType = $("form.vfSimtk.ownerType").value;
                updateData_VuNT("simtkManagmentAction!getSerial.do?target=form.vfSimtk.iccid&targetError=displayResultMsgClient&isdn=" + isdn + "&ownerId=" + ownerId + "&ownerType=" + ownerType);
            }
            updateData_VuNT = function(url) {
                dojo.io.bind({
                    url: url,
                    error: function(type, data, evt) {
                        alert("error");
                    },
                    handler: function(type, data, e) {
                        if (dojo.lang.isObject(data)) {
                            // Duyet qua data tra ve de tim cac truong can update
                            for (var keyValue in data) {
                                var obj = document.getElementById(keyValue);
                                if (obj != null) {
                                    updateElement_VuNT(obj, data[keyValue]);
                                }
                            }
                        }
                    },
                    mimetype: "text/json"
                });
            }
            updateElement_VuNT = function(obj, data) {
                var ogCbW;

                switch (obj.tagName) {
                    case 'SELECT':
                        ogCbW = obj.offsetWidth;
                        clearAllChilds(obj);
                        updateChildToCombo(obj, data, ogCbW);
                        break;
                    case 'TEXTAREA':
                        obj.value = data;
                        break;
                    case 'INPUT':
                        if (obj.type == 'checkbox') {
                            alert('Checkbox');
                            obj.checked = true;
                        }
                        if (obj.type == 'text') {
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

//    onblurCheckIsdn = function(){
//        $( 'displayResultMsg' ).innerHTML='';
//        var checkIsdn = "";
//        var shopCode = "";
//        var staffCode = "";
//        if (document.getElementById("form.vfSimtk.msisdn") != null && document.getElementById("form.vfSimtk.msisdn").value != null){
//            checkIsdn = trim(document.getElementById("form.vfSimtk.msisdn").value);
//        }
//        if (checkIsdn == ""){
//            $( 'displayResultMsg' ).innerHTML='Error. ISDN incorrect!';
//            return;
//        }
//        gotoAction("bodyContent",'simtkManagmentAction!onblurCheckIsdn.do?checkIsdn=' + checkIsdn + "&" + token.getTokenParamString());
//    }


</script>
