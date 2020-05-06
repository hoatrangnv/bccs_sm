<%--
    Document   : accountAnyPayManagement
    Created on : Oct 13, 2009, 11:58:50 AM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<s:hidden name="CollAccountManagmentForm.pForm.pBalanceId" id="pBalanceId"/>
<s:hidden name="CollAccountManagmentForm.pForm.pAccountId" id="pAccountId"/>


<table class="inputTbl6Col">
        <tr>
            <td colspan="6">
                <s:hidden name="CollAccountManagmentForm.pForm.pAgentType" id="pAgentType" />
                <s:hidden name="CollAccountManagmentForm.pForm.pPassword" id="pPassword" />
                <s:hidden name="CollAccountManagmentForm.pForm.pServiceType" id="pServiceType" />
                <s:hidden name="CollAccountManagmentForm.pForm.pIsdn" id="pIsdn" />
                <s:hidden name="CollAccountManagmentForm.pForm.pSerial" id="pSerial" />
                
                <tags:displayResult id="returnMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key"/>
            </td>
        </tr>
        <tr>
            <td class="label">Người tạo</td>
            <td class="text">
                <s:textfield name="CollAccountManagmentForm.pForm.pUserCodeCreate" id="pUserCodeCreate" theme="simple" maxlength="100" cssClass="txtInput"   
                                    readonly="true"/>                
            </td>
            <td class="label">Ngày tạo</td>
            <td class="text">
                <tags:dateChooser property="CollAccountManagmentForm.pForm.pDateCreate"  id="pDateCreate"  styleClass="txtInput"  insertFrame="false"
                                  readOnly="true"/>
            </td>
            <td class="label">Mã đối tượng</td>
            <td class="text">
                <s:textfield name="CollAccountManagmentForm.pForm.pOwnerCode" id="pOwnerCode" theme="simple" maxlength="100" cssClass="txtInput"   
                                    readonly="true"/>                
            </td>
        </tr>        
        <tr>
            <td class="label">Trạng thái <font color="red">(*)</font> </td>
            <td class="text">
                <s:select name="CollAccountManagmentForm.pForm.pInStatus" id="pInStatus" cssClass="txtInput"
                          list="#{'1':'Hiệu lực','2':'Không hiệu lực'}" 
                          headerKey="" headerValue="--Chọn trạng thái--" disabled="false" theme="simple" 
                          />
            </td>
            <td class="label">Từ ngày <font color="red">(*)</font> </td>
            <td class="text">
                <tags:dateChooser property="CollAccountManagmentForm.pForm.pStartDate"  id="pStartDate"  styleClass="txtInput"  insertFrame="false"
                                  readOnly="false"/>
            </td>
            <td class="label">Đến ngày</td>
            <td class="text">
                <tags:dateChooser property="CollAccountManagmentForm.pForm.pEndDate"  id="pEndDate"  styleClass="txtInput"  insertFrame="false"
                                  readOnly="false"/>
            </td>
            <td>&nbsp;</td>
        </tr>
    </table>
<br/>

    <div align="center">
    <s:if test="collAccountManagmentForm.pForm.pBalanceId != null && collAccountManagmentForm.pForm.pBalanceId * 1 != 0 ">
            <tags:submit targets="anyPay" formId="collAccountManagmentForm"
                         showLoadingText="true" cssStyle="width:80px;"
                         confirm="true" confirmText="Bạn có chắc chắn muốn sửa thông tin tài khoản?"
                         value="Sửa TK"
                         validateFunction="validateUpdateAccountAnyPayInfo()"
                         preAction="CollAccountManagmentAction!editAccountAnyPay.do"
                       />
        </s:if>
        <s:else>
            <tags:submit targets="anyPay" formId="collAccountManagmentForm"
                         showLoadingText="true" cssStyle="width:80px;"
                         confirm="true" confirmText="Bạn có chắc chắn muốn tạo mới tài khoản?"
                         value="Tạo mới"
                         validateFunction="validateUpdateAccountAnyPayInfo()"
                         preAction="CollAccountManagmentAction!addAccountAnyPay.do"
                       />
        </s:else>
        <tags:submit targets="anyPay" formId="collAccountManagmentForm"
                     showLoadingText="true" cssStyle="width:80px;"
                     value="Bỏ qua"
                     preAction="CollAccountManagmentAction!prepareAccountAnyPay.do"
                   />        
    </div>
    <br/>

<script>
    
    $('pInStatus').focus();
    
</script>