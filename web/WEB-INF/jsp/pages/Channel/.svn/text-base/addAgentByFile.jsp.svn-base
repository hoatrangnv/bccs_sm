<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Copyright 2010 Viettel Telecom. All rights reserved.
    VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms. 

    Document   : addAgentByFile
    Created on : Nov 21, 2011, 1:41:01 PM
    Author     : haint
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

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

            String pageId = request.getParameter("pageId");
            request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType" + pageId));

%>
<s:form action="addAgentByFile" theme="simple" method="post" id="addAgentForm">
<s:token/>

    <tags:imPanel title="MES.CHL.009">
        <div>
            <table class="inputTbl4Col">
                <tr>
                    <td colspan="4" align="center">
                        <%--<a href="${contextPath}/share/pattern/AddShopByFile.xls">Download template thêm mã CH/ĐL theo File.</a>--%>
                        <%--a href="${contextPath}/share/pattern/AddShopByFile.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.010'))}</a--%>
                        <a href="${contextPath}/share/pattern/importAgent.xls">${fn:escapeXml(imDef:imGetText('MES.CHANGE.INFO.AGENT.025'))}</a>
                    </td>
                </tr>

                <tr>
                    <td class="label">Channel Type</td>
                    <td colspan="3">
                        <div class="text">
                            <tags:imSelect name="addAgentForm.changeType"
                                           id="addAgentForm.changeType"
                                           cssClass="txtInputFull"
                                           theme="simple"
                                           headerKey="" headerValue="label.option"
                                           list="listChannelType" listKey="channelTypeId" listValue="name"/>
                        </div>
                    </td>
                </tr>

                <tr id="trImpByFile">
                    <%--<td  class="label" >File dữ liệu (<font color="red">*</font>)</td>--%>
                    <td class="label" ><tags:label key="MES.CHL.005" required="true" /></td>
                    <td  class="text" >
                        <tags:imFileUpload name="addAgentForm.clientFileName" 
                                           id="addAgentForm.clientFileName" 
                                           serverFileName="addAgentForm.serverFileName"
                                           extension="xls"/>
                    </td>
                </tr>
            </table>
        </div>
        <div align="left" style="padding-top:5px; padding-bottom:5px;">
            <tags:submit formId="addAgentForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="listAgent"
                         value="MES.CHL.006"
                         validateFunction="checkValidatePreview()"
                         preAction="addAgentByFile!viewImportAgentFile.do"/>
        </div>
        <div id="listAgent">
            <jsp:include page="listAgentImport.jsp"/>
        </div>
    </tags:imPanel>
    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="addAgentForm"
                     showLoadingText="true"
                     cssStyle="width:80px;"
                     targets="bodyContent"
                     value="MES.CHL.007"
                     confirm="true"
                     confirmText="MES.CHL.011"
                     validateFunction="checkValidFile()"
                     preAction="addAgentByFile!addAgentByFile.do"/>
    </div>

    <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
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
    
    checkValidatePreview = function() {
        var clientFileName = document.getElementById("addAgentForm.clientFileName");
        if (trim(clientFileName.value).length ==0){
    <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa chọn file cần tạo"--%>
                $( 'message' ).innerHTML='<s:text name="ERR.CHL.001"/>';
                clientFileName.focus();
                return false;
            }
            return true;
        }
    
    checkValidFile = function(){

        var channelTypeId = document.getElementById("addAgentForm.changeType");
        if (trim(channelTypeId.value).length ==0){
            $( 'message' ).innerHTML='<s:text name="Error. Pls select channel type!"/>';//Chua chon loai doi tuong
            channelTypeId.focus();
            return false;
        }


        var clientFileName = document.getElementById("addAgentForm.clientFileName");
        if (trim(clientFileName.value).length ==0){
    <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa chọn file cần tạo"--%>
                $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.001')"/>';
                clientFileName.focus();
                return false;
            }
            return true;
        }
</script>
