
<%-- 
    Document   : ChangeCTVDBInShop
    Created on : Jul 13, 2010, 3:31:02 PM
    Author     : User
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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            UserToken userToken = (UserToken) request.getSession().getAttribute("userToken");
            String shopName = userToken.getShopName();
            Long shopId = userToken.getShopId();
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("shopName", shopName);

%>
<s:form action="channelChangeStaffAction" theme="simple" method="post" id="channelForm">
<s:token/>

    <tags:imPanel title="MES.CHL.036">
        <table class="inputTbl4Col">
            <tr>
                <td colspan="4" align="center">
                    <%--<a href="${contextPath}/share/pattern/ChangeCTVDBInShop.xls">Download template chuyển cửa hàng cho ĐB/NVĐB theo File.</a>--%>
                    <%--a href="${contextPath}/share/pattern/ChangeCTVDBInShop.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.022'))}</a--%>
                    <a href="${contextPath}/share/pattern/changeShopForChannel.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.022'))}</a>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <div class="divHasBorder" style="margin-top:10px; padding:3px;">
                        <%--<s:radio name="channelForm.changeType" id ="changeType"
                                 list="#{'1':'Chuyển một NV', '2':'Chuyển theo File'}" onchange="clickChange(this.value)"/>--%>
                        <tags:imRadio name="channelForm.changeType" id="changeType"
                                      list="1:MES.CHL.023,2:MES.CHL.024" onchange="clickChange(this.value)"/>
                    </div>
                </td>
            </tr>
            <tr>
                <%--<td>Mã cửa hàng cũ<font color="red">(*)</font></td>--%>
                <td class="label"><tags:label key="MES.CHL.025" required="true" /></td>
                <td >
                    <tags:imSearch codeVariable="channelForm.shopCode" nameVariable="channelForm.shopName"
                                   codeLabel="MES.CHL.025" nameLabel="MES.CHL.026"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent="channelForm.ownerCode;channelForm.ownerName"
                                   getNameMethod="getNameShop"
                                   roleList="rolef9ShopChangeCTVDBInShop"/>
                </td>
                <%--<td>Mã ĐB/NVĐB<font color="red">(*)</font></td>--%>
                <td class="label"><tags:label key="MES.CHL.027" required="true" /></td>
                <td >
                    <tags:imSearch codeVariable="channelForm.ownerCode" nameVariable="channelForm.ownerName"
                                   codeLabel="MES.CHL.027" nameLabel="MES.CHL.028"
                                   searchClassName="com.viettel.im.database.DAO.ChannelDAO"
                                   searchMethodName="getListStaffCTVDB"
                                   otherParam="channelForm.shopCode"
                                   getNameMethod="getNameStaff"
                                   roleList=""/>
                </td>
            </tr>
            <tr>
                <%--<td>Mã CH chuyển đến<font color="red">(*)</font></td>--%>
                <td class="label"><tags:label key="MES.CHL.029" required="true" /></td>
                <td >
                    <tags:imSearch codeVariable="channelForm.newShopCode" nameVariable="channelForm.newShopName"
                                   codeLabel="MES.CHL.029" nameLabel="MES.CHL.030"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent=""
                                   getNameMethod="getNameShop"
                                   roleList=""/>
                </td>
                <%--<td>Mã NVQL mới<font color="red">(*)</font></td>--%>
                <td class="label"><tags:label key="MES.CHL.031" required="true" /></td>
                <td >
                    <tags:imSearch codeVariable="channelForm.staffManageCode" nameVariable="channelForm.staffManageName"
                                   codeLabel="MES.CHL.032" nameLabel="MES.CHL.033"
                                   searchClassName="com.viettel.im.database.DAO.ChannelDAO"
                                   searchMethodName="getListStaffManagement"
                                   otherParam="channelForm.newShopCode"
                                   elementNeedClearContent=""
                                   getNameMethod="getNameStaffManagement"
                                   roleList=""/>
                </td>
            </tr>
            <tr id="trImpByFile">
                <%--<td  class="label" >File dữ liệu (<font color="red">*</font>)</td>--%>
                <td class="label" ><tags:label key="MES.CHL.005" required="true" /></td>
                <td class="text" >
                    <tags:imFileUpload name="channelForm.clientFileName" id="channelForm.clientFileName" serverFileName="channelForm.serverFileName" extension="xls"/>
                </td>
            </tr>
        </table>
    </tags:imPanel>
    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="channelForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MES.CHL.034"
                     confirm="true"
                     confirmText="MES.CHL.035"
                     validateFunction="checkValidFields()"
                     preAction="channelChangeStaffAction!changeCTVDBIinShop.do"/>
        <%--<tags:submit formId="channelForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="Theo File"
                     confirm="true"
                     confirmText="Bạn có chắc chắn chuyển cửa hàng cho ĐB/NVĐB theo file không?"
                     validateFunction="checkValidFile()"
                     preAction="channelChangeStaffAction!changeCTVDBIinShopByFile.do"/>--%>
    </div>

    <tags:displayResult id="resultViewChangeStaffInShopClient" property="resultViewChangeStaffInShop" type="key"/>
    <div>
        <s:if test="#request.reportAccountPath != null">
            <script>
                window.open('${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}','','toolbar=yes,scrollbars=yes');
            </script>
            <a href="${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}">
                <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
            </a>
        </s:if>
    </div>

</s:form>
<script type="text/javascript" language="javascript">
    clickChange = function(value) {
        if (value*1 == 1){
            $('channelForm.newShopCode').disabled = false;
            $('channelForm.ownerCode').disabled = false;
            $('channelForm.staffManageCode').disabled = false;
        }
        else{
            $('channelForm.newShopCode').disabled = true;
            $('channelForm.ownerCode').disabled = true;
            $('channelForm.staffManageCode').disabled = true;
        }
    }
    checkValidFields = function(){
        var i = 0;
        for(i = 1; i < 2; i=i+1) {
            var radioId = "changeType" + i;
            if($(radioId).checked == true) {
                break;
            }
        }
        if(i == 2){
            var clientFileName = document.getElementById("channelForm.clientFileName");
            if (trim(clientFileName.value).length ==0){
    <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa chọn file cần điều chuyển"--%>
                    $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.006')"/>';
                    clientFileName.focus();
                    return false;
                }
                return true;
            }
            else{
                var shopCode = document.getElementById("channelForm.shopCode");
                if (trim(shopCode.value).length ==0){
    <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa nhập mã cửa hàng cũ"--%>
                    $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.007')"/>';
                    shopCode.focus();
                    return false;
                }
                var ownerCode = document.getElementById("channelForm.ownerCode");
                if (trim(ownerCode.value).length ==0){
    <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa nhập mã ĐB/NVĐB"--%>
                    $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.008')"/>';
                    ownerCode.focus();
                    return false;
                }
                var newShopCode = document.getElementById("channelForm.newShopCode");
                if (trim(newShopCode.value).length ==0){
    <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa nhập mã cửa hàng chuyển đến"--%>
                    $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.009')"/>';
                    newShopCode.focus();
                    return false;
                }
                var staffManageCode = document.getElementById("channelForm.staffManageCode");
                if (trim(staffManageCode.value).length ==0){
    <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa nhập mã nhân viên quản lý mới"--%>
                    $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.010')"/>';
                    staffManageCode.focus();
                    return false;
                }
                return true;
            }
        }
        checkValidFile = function(){
            return true;
        }

</script>
