<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ChangeParentShop
    Created on : Jul 13, 2010, 3:44:45 PM
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


<%
            UserToken userToken = (UserToken) request.getSession().getAttribute("userToken");
            String shopName = userToken.getShopName();
            Long shopId = userToken.getShopId();
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("shopName", shopName);

%>
<s:form action="channelChangeStaffAction" theme="simple" method="post" id="channelForm">
<s:token/>

    <tags:imPanel title="MES.CHL.053">
        <table class="inputTbl4Col">
            <tr>
                <td colspan="4" align="center">
                    <%--<a href="${contextPath}/share/pattern/ChangeParentShop.xls">Download template chuyển mã cha theo File.</a>--%>
                    <%--a href="${contextPath}/share/pattern/ChangeParentShop.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.040'))}</a--%>
                    <a href="${contextPath}/share/pattern/changeParentForShop.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.040'))}</a>
                </td>
            </tr>
            <tr>
                <%--<td>Mã CH cần thay đổi<font color="red">(*)</font></td>--%>
                <td class="label"><tags:label key="MES.CHL.041" required="true" /></td>
                <td >
                    <tags:imSearch codeVariable="channelForm.shopCode" nameVariable="channelForm.shopName"
                                   codeLabel="MES.CHL.025" nameLabel="MES.CHL.026"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent="channelForm.ownerCode;channelForm.ownerName"
                                   getNameMethod="getNameShop"
                                   roleList="rolef9ShopChangeParentShop"/>
                </td>                            
                <%--<td>Mã cha cần chuyển đến<font color="red">(*)</font></td>--%>
                <td class="label"><tags:label key="MES.CHL.042" required="true" /></td>
                <td >
                    <tags:imSearch codeVariable="channelForm.newShopCode" nameVariable="channelForm.newShopName"
                                   codeLabel="MES.CHL.043" nameLabel="MES.CHL.044"
                                   searchClassName="com.viettel.im.database.DAO.SaleManagmentDAO"
                                   searchMethodName="getListShop"
                                   elementNeedClearContent=""
                                   getNameMethod="getNameShop"
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
                     value="MES.CHL.045"
                     confirm="true"
                     confirmText="MES.CHL.046"
                     validateFunction="checkValidFields()"
                     preAction="channelChangeStaffAction!changeParentShop.do"/>
        <tags:submit formId="channelForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MES.CHL.047"
                     confirm="true"
                     confirmText="MES.CHL.048"
                     validateFunction="checkValidFile()"
                     preAction="channelChangeStaffAction!changeParentShopByFile.do"/>
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
    checkValidFields = function(){
        var shopCode = document.getElementById("channelForm.shopCode");
        if (trim(shopCode.value).length ==0){
    <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa nhập mã cửa hàng cần thay đổi"--%>
                $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.011')"/>';
                shopCode.focus();
                return false;
            }
            var newShopCode = document.getElementById("channelForm.newShopCode");
            if (trim(newShopCode.value).length ==0){
    <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa nhập mã cửa hàng chuyển đến"--%>
                $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.009')"/>';
                newShopCode.focus();
                return false;
            }
            return true;
        }
        checkValidFile = function(){
            var clientFileName = document.getElementById("channelForm.clientFileName");
            if (trim(clientFileName.value).length ==0){
    <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa chọn file cần điều chuyển"--%>
                $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.006')"/>';
                clientFileName.focus();
                return false;
            }
            return true;
        }

</script>
