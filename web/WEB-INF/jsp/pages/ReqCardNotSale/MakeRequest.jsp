<%-- 
    Document   : MakeRequest
    Created on : Dec 9, 2015, 10:38:28 AM
    Author     : mov_itbl_dinhdc
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

<tags:imPanel title="title.importCardNotSaleFile.page">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>
    <!-- phan hien thi thong tin can tra cuu -->
    <div class="divHasBorder">
        <s:form action="importCardNotSaleAction" theme="simple" method="post" id="reqCardNotSaleFrom">
            <table class="inputTbl4Col">
                <tr>
                    <td colspan="3" align="center">
                        <%--<a href="${contextPath}/share/pattern/AddStaffByFile.xls">Download template thêm mã nhân viên theo File.</a>--%>
                        <%--a href="${contextPath}/share/pattern/AddStaffByFile.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.012'))}</a--%>
                        <a href="${contextPath}/share/pattern/importStockCardNotSale.xls">${fn:escapeXml(imDef:imGetText('MSG.TT.01'))}</a>
                    </td>
                </tr>
                
                <tr>
                    <%--<td>Mã nhân viên (<font color="red">*</font>)</td>--%>
                    <td ><tags:label key="MES.CHL.105" required="true"/></td>
                    <td colspan="1">
                        <s:textfield name="reqCardNotSaleFrom.userCodeCreate" id="reqCardNotSaleFrom.userCodeCreate" maxlength="50" cssClass="txtInputFull" readonly="true" />
                    </td>
                    <td class="label" ><tags:label key="MES.CHL.005" required="true" /><font color="red">(select file .xls)</td>
                    <td class="text" >
                        <tags:imFileUpload name="reqCardNotSaleFrom.clientFileName" id="reqCardNotSaleFrom.clientFileName" serverFileName="reqCardNotSaleFrom.serverFileName" extension="xls"/>
                    </td>
                </tr>
                
            </table>
            <!-- phan nut tac vu -->
           <div align="center" class="button">
               <tags:submit formId="reqCardNotSaleFrom" id="btnRequestType"
                            showLoadingText="true"
                            cssStyle="width:120px;"
                            targets="bodyContent"
                            value="MSG.SMS.005"
                            validateFunction="checkValidFields();"
                            preAction="importCardNotSaleAction!importFile.do"/>
           </div>
       
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
       
    </div>
</tags:imPanel>
<script type="text/javascript">
  
    checkValidFields = function() {
           
            //ten file ko duoc de trong
            var clientFileName = document.getElementById("reqCardNotSaleFrom.clientFileName");
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



