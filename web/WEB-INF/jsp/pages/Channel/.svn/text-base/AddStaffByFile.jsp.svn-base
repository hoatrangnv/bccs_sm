<%-- 
    Document   : AddStaffByFile
    Created on : Jul 16, 2010, 9:42:44 AM
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

    <tags:imPanel title="MES.CHL.162">
        <div>
            <table class="inputTbl4Col">
                <tr>
                    <td colspan="3" align="center">
                        <%--<a href="${contextPath}/share/pattern/AddStaffByFile.xls">Download template thêm mã nhân viên theo File.</a>--%>
                        <%--a href="${contextPath}/share/pattern/AddStaffByFile.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.012'))}</a--%>
                        <a href="${contextPath}/share/pattern/importStaff.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.012'))}</a>
                    </td>
                </tr>
                 <tr>
                    <td class="label"><tags:label key="label.channel.type" required="true" /><%--Loại kênh--%></td>
                    <td colspan="2">
                        <div class="text">
                            <tags:imSelect name="channelForm.changeType" id="channelForm.changeType" headerKey="" headerValue="label.option" cssClass="txtInputFull"
                                           list="14:Staff Channel"/>
                            
                            <%--tags:imSelect name="channelForm.changeType" id="channelForm.changeType" headerKey="" headerValue="label.option" cssClass="txtInputFull"
                                           list="14:Staff at Department Channel, 1000381:Staff manages Agent Channel,1000382:Staff manages D2D Channel,1000383:Staff manages Commune Channel,1000383:Staff manages Collector Channel,1000380:Staff at Showroom Channel"
                                           /--%>
                        </div>
                    </td>
                </tr>
                
                <tr id="trImpByFile">
                    <%--<td  class="label" >File dữ liệu (<font color="red">*</font>)</td>--%>
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
                         preAction="channelChangeStaffAction!viewFile.do"/>
                    </td>
                </tr>
            </table>
        </div>
        <div id="listStaff">
            <jsp:include page="ListStaffImport.jsp"/>
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
                     confirmText="MES.CHL.008"
                     validateFunction="checkValidFile()"
                     preAction="channelChangeStaffAction!addStaffByFile.do"/>
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
            $( 'resultViewChangeStaffInShopClient' ).innerHTML='<s:text name="ERR.STAFF.0009"/>';//Chua chon loai doi tuong
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
