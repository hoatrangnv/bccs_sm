<%-- 
    Document   : ImportQuotasUnitUnderTheFile
    Created on : May 12, 2015, 9:16:26 AM
    Author     : thindm
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="title.ImportQuotasUnitFile.page">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>
    <!-- phan hien thi thong tin can tra cuu  importDebitUnitTypeAction-->
    <div class="divHasBorder">
        <s:form action="importDebitUnitTypeAction" theme="simple" method="post" id="quotasAssignedForm">
            <table class="inputTbl4Col">
                
                <tr id="trImpByFile">
                    <td class="label"><tags:label key="MSG.generates.file_data" required="true"/></td>
                    <td class="text">
                        <tags:imFileUpload name="quotasAssignedForm.clientFileName" 
                                           id="quotasAssignedForm.clientFileName" 
                                           serverFileName="quotasAssignedForm.serverFileName" 
                                           cssStyle="width:500px;" 
                                           extension="xls;xlsx"/>
                    </td>
                    <td>
                        <a href="${contextPath}/share/pattern/ImpQuotasUnitFile.xls"><tags:label key="MSG.TT.01" /></a>
                    </td>
                </tr>
            </table>
        </s:form>
        <div>
            <s:if test="#request.reportAccountPath != null">
                <script>
                    window.open('${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}', '', 'toolbar=yes,scrollbars=yes');
                </script>

                <a href="${contextPath}/download.do?${fn:escapeXml(reportAccountPath)}">
                    <tags:displayResult id="filePathMessage" property="filePathMessage" type="key"/>
                </a>
            </s:if>
        </div>
        <!-- phan nut tac vu -->
        <div align="center" class="button">
            <tags:submit formId="quotasAssignedForm" id="btnRequestType"
                         showLoadingText="true"
                         cssStyle="width:120px;"
                         targets="bodyContent"
                         value="MSG.SMS.005"
                         validateFunction="checkValidFields();"
                         preAction="importDebitUnitTypeAction!importFile.do"/>
        </div>
    </div>
</tags:imPanel>
<script type="text/javascript">
  
    checkValidFields = function() {
           
            //nhap hang theo file -> ten file ko duoc de trong
            var clientFileName = document.getElementById("quotasAssignedForm.clientFileName");
            if (trim(clientFileName.value).length ==0){
                <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa chọn file cần tạo"--%>
                $('message').innerHTML = '<s:property value="getText('ERR.GOD.067')"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
                clientFileName.focus();
                return false;
            }
            return true;
        }
</script>


