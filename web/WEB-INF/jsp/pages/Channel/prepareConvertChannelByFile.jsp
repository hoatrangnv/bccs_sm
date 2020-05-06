<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : OnOffShopfByFile
    Created on : Sep 24, 2010, 8:26:25 AM
    Author     : Lamnt
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

    <tags:imPanel title="title.prepareConvertChannelByFile.page">
        <div>
            <table class="inputTbl4Col">
                <tr>
                    <td colspan="4" align="center">                        
                        <%--a href="${contextPath}/share/pattern/OnOffStaffByFile.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.164'))}</a--%>
                        <a href="${contextPath}/share/pattern/convertChannel.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.164'))}</a>
                    </td>
                </tr>
                <tr id="trImpByFile">
                    <%--<td  class="label" >File dữ liệu (<font color="red">*</font>)</td>--%>
                    <td class="label" ><tags:label key="MES.CHL.005" required="true" /></td>
                    <td  class="text" >
                        <!-- Edit by hieptd -->
                        <tags:imFileUpload
                            name="channelForm.clientFileName"
                            id="channelForm.clientFileName" serverFileName="channelForm.serverFileName" extension="xls"/>
                    </td>
                </tr>
            </table>
        </div>
        <div align="center" style="padding-top:5px; padding-bottom:5px;">
            <tags:submit formId="channelForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MES.CHL.006"
                         validateFunction="checkValidFile()"
                         preAction="channelChangeStaffAction!viewChannelCodeFile.do"/>
        </div>
        <div id="listStaff">
            <jsp:include page="ListChannelConvert.jsp"/>
        </div>
    </tags:imPanel>


    <!-- phan nut tac vu -->
    <div align="center" style="padding-top:5px; padding-bottom:5px;">
        <tags:submit formId="channelForm"
                     showLoadingText="true"
                     cssStyle="width:100px;"
                     targets="bodyContent"
                     value="MES.CHL.179"
                     confirm="true"
                     confirmText="MES.CHL.167"
                     validateFunction="checkValidFile()"
                     preAction="channelChangeStaffAction!convertChannelByFile.do"/>
    </div>
    <tags:displayResult id="resultViewConvertChannelByFile" property="resultViewConvertChannel" propertyValue="messageParam" type="key"/>
    <div>
        <s:if test="#request.reportAccountPath != null">
            <script>
                window.open('${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}', '', 'toolbar=yes,scrollbars=yes');
            </script>
            <a href="${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}">
                <tags:displayResult id="reportAccountMessage" property="reportAccountMessage" type="key"/>
            </a>
        </s:if>
    </div>

</s:form>
<script type="text/javascript" language="javascript">
    checkValidFile = function() {
        var clientFileName = document.getElementById("channelForm.clientFileName");
        if (trim(clientFileName.value).length == 0) {
    <%--$('resultViewConvertChannelByFile').innerHTML="Bạn chưa chọn file cần tạo"--%>
                $('resultViewConvertChannelByFile').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.CHL.001')"/>';
                clientFileName.focus();
                return false;
            }
            return true;
        }
</script>
