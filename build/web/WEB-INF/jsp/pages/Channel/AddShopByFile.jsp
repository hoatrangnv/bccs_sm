
<%-- 
    Document   : AddShopByFile
    Created on : Jul 16, 2010, 9:42:59 AM
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

            String pageId = request.getParameter("pageId");
            request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType" + pageId));

%>
<s:form action="channelChangeStaffAction" theme="simple" method="post" id="channelForm">
<s:token/>

    <tags:imPanel title="MES.CHL.009">
        <div>
            <table class="inputTbl4Col">
                <tr>
                    <td colspan="4" align="center">
                        <%--<a href="${contextPath}/share/pattern/AddShopByFile.xls">Download template thêm mã CH/ĐL theo File.</a>--%>
                        <%--a href="${contextPath}/share/pattern/AddShopByFile.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.010'))}</a--%>
                        <a href="${contextPath}/share/pattern/importShop.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.010'))}</a>
                    </td>
                </tr>

                <tr>
                    <td class="label">Channel Type</td>
                    <td colspan="3">
                        <div class="text">
                            <tags:imSelect name="channelForm.changeType"
                                           id="channelForm.changeType"
                                           cssClass="txtInputFull"
                                           theme="simple"
                                           headerKey="" headerValue="--Select channel type--"
                                           list="listChannelType"  listKey="channelTypeId" listValue="name"/>

                        </div>

                    </td>
                </tr>

                <tr id="trImpByFile">
                    <%--<td  class="label" >File dữ liệu (<font color="red">*</font>)</td>--%>
                    <td class="label" ><tags:label key="MES.CHL.005" required="true" /></td>
                    <td  class="text" >
                        <tags:imFileUpload name="channelForm.clientFileName" id="channelForm.clientFileName" serverFileName="channelForm.serverFileName" extension="xls"/>
                    </td>
                </tr>
            </table>
        </div>
        <div align="left" style="padding-top:5px; padding-bottom:5px;">
            <tags:submit formId="channelForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MES.CHL.006"
                         validateFunction="checkValidFile()"
                         preAction="channelChangeStaffAction!viewImportShopFile.do"/>
        </div>
        <div id="listStaff">
            <jsp:include page="ListShopImport.jsp"/>
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
                     confirmText="MES.CHL.011"
                     validateFunction="checkValidFile()"
                     preAction="channelChangeStaffAction!addShopByFile.do"/>
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
    checkValidFile = function(){

        var channelTypeId = document.getElementById("channelForm.changeType");
        if (trim(channelTypeId.value).length ==0){
            $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:text name="Error. Pls select channel type!"/>';//Chua chon loai doi tuong
            channelTypeId.focus();
            return false;
        }


        var clientFileName = document.getElementById("channelForm.clientFileName");
        if (trim(clientFileName.value).length ==0){
    <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa chọn file cần tạo"--%>
                $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.CHL.001')"/>';
                clientFileName.focus();
                return false;
            }
            return true;
        }
</script>
