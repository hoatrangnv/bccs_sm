<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : AddCollByFile
    Created on : Jul 30, 2010, 3:49:39 PM
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

    <tags:imPanel title="menu.channel.channel.import">
        <div>
            <table class="inputTbl4Col">
                <tr>
                    <td colspan="4" align="center">                        
                        <a href="${contextPath}/share/pattern/importChannel.xls"> Download template </a>
                    </td>
                </tr>

                <tr>
                    <td class="label"><tags:label key="label.channel.type" required="true" /><%--Loại kênh--%></td>
                    <td class="text">
                        <tags:imSelect name="channelForm.changeType" id="channelForm.changeType" headerKey="" headerValue="label.option" cssClass="txtInputFull"
                                       list="lstChannelTypeCol" listKey="channelTypeId" listValue="name" onchange="changeChannelType()"/>
                    </td>
                    <td class="label"> 
                        <s:checkbox id="chkWallet" name="channelForm.chkWallet" onchange="changeChkWallet()"/>
                        <tags:label key="lbl.is.wallet"/>
                    </td>
                    <td></td>
                </tr>     

                <tr id ="walletInfo">
                    <%--Chon loai kenh vi dien tu--%>
                    <s:hidden name="channelForm.channelWalletHide" id="channelForm.channelWalletHide"/>
                    <td class="label"><tags:label key="label.channel.wallet" required="true"/></td>
                    <td class="text">
                        <tags:imSelect name="channelForm.channelWallet"
                                       id="channelForm.channelWallet"
                                       cssClass="txtInputFull"
                                       theme="simple" headerKey="" headerValue="label.option"
                                       list="lstChannelTypeWallet" listKey="code" listValue="name" disabled="true"
                                       />
                    </td>
                </tr>

                <tr id="trImpByFile">
                    <td class="label" ><tags:label key="MES.CHL.005" required="true" /></td>
                    <td class="text" >
                        <tags:imFileUpload name="channelForm.clientFileName" id="channelForm.clientFileName" serverFileName="channelForm.serverFileName" extension="xls"/>
                    </td>
                    <td class="text">
                        <tags:submit formId="channelForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MES.CHL.006"
                         validateFunction="checkValidFile()"
                         preAction="channelChangeStaffAction!viewFileColl.do"/>
                    </td>
                    <td></td>
                </tr>
            </table>
        </div>
        <div id="listStaff">
            <jsp:include page="ListStaffImportAP.jsp"/>
        </div>
    </tags:imPanel>

    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="channelForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MES.CHL.007"
                     confirm="true"
                     confirmText="MES.CHL.013"
                     validateFunction="checkValidFile()"
                     preAction="channelChangeStaffAction!addStaffByFileAP.do"/>
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
    changeChkWallet = function() {
        if (jQuery('#chkWallet').is(':checked')) {
            $('walletInfo').style.display = '';
        } else {
            $('walletInfo').style.display = 'none';
        }
    }
    changeChkWallet();
    
    changeChannelType = function() {
        var channelTypeId = document.getElementById("channelForm.channelType").value;
        if (channelTypeId != null && channelTypeId == 1000522) {
            document.getElementById("channelForm.channelWallet").value = 'SA';
            document.getElementById("channelForm.channelWallet").value = 'SA';
        } else if (channelTypeId != null && channelTypeId == 1000485) {
            document.getElementById("channelForm.channelWallet").value = 'MA';
            document.getElementById("channelForm.channelWalletHide").value = 'MA';
        } else {
            document.getElementById("channelForm.channelWallet").value = 'AG';
            document.getElementById("channelForm.channelWalletHide").value = 'AG';
        }
    }
    checkValidFile = function() {

        var channelTypeId = document.getElementById("channelForm.changeType");
        if (trim(channelTypeId.value).length == 0) {
            $('resultViewChangeStaffInShopClient').innerHTML = '<s:text name="ERR.STAFF.0009"/>';//Chua chon loai doi tuong
            channelTypeId.focus();
            return false;
        }

        //Neu la kenh vi thi bat buoc chon cac thong tin vi
        if (jQuery('#chkWallet').is(':checked')) {
            var channelWallet = document.getElementById("channelForm.channelWallet");
            if (channelWallet.value == null || channelWallet.value == "") {
                $('resultViewChangeStaffInShopClient').innerHTML = '<s:property escapeJavaScript="true"  value="getText('err.input.channelWallet')"/>';
                channelWallet.focus();
                return false;
            }
        }

        var clientFileName = document.getElementById("channelForm.clientFileName");
        if (trim(clientFileName.value).length ==0){
                $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:text name="ERR.CHL.001"/>';
                clientFileName.focus();
                return false;
            }
            return true;
        }
</script>
