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

<tags:imPanel title="title.importStockCardLost.page">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>
    <!-- phan hien thi thong tin can tra cuu -->
        <s:form action="importStockCardLostAction" theme="simple" method="post" id="reqCardNotSaleFrom">
            <table class="inputTbl4Col">
                <tr>
                    <td><tags:label key="MSG.import.type" required="true"/></td>
                    <td colspan="1">
                        <s:radio name="reqCardNotSaleFrom.impType"
                                 id="reqCardNotSaleFrom.impType"
                                 list="#{'1':'Input by number range',
                                         '2':'Input from file'}"
                                 onchange="radioClick(this.value)"/>
                    </td>
                    <td colspan="1" align="center">
                        <%--<a href="${contextPath}/share/pattern/AddStaffByFile.xls">Download template thêm mã nhân viên theo File.</a>--%>
                        <%--a href="${contextPath}/share/pattern/AddStaffByFile.xls">${fn:escapeXml(imDef:imGetText('MES.CHL.012'))}</a--%>
                        <a href="${contextPath}/share/pattern/importStockCardLost.xls">${fn:escapeXml(imDef:imGetText('MSG.TT.01'))}</a>
                    </td>
                </tr>
                
                <tr>
                    <%--<td>Mã nhân viên (<font color="red">*</font>)</td>--%>
                    <td ><tags:label key="MES.CHL.105" required="true"/></td>
                    <td colspan="1">
                        <s:textfield name="reqCardNotSaleFrom.userCodeCreate" id="reqCardNotSaleFrom.userCodeCreate" maxlength="50" cssClass="txtInputFull" readonly="true"/>
                    </td>
                    
                    <td class="label"><tags:label key="MSG.GOD.240" required="true" /><%--Loại Ly Do--%></td>
                    <td colspan="2">
                            <tags:imSelect name="reqCardNotSaleFrom.reasonId" id="reqCardNotSaleFrom.reasonId" headerKey="" 
                                           headerValue="label.option" 
                                           cssClass="txtInputFull"
                                           list="listReasonLostCard"
                                           listKey="reasonId" 
                                           listValue="reasonName"/>
                    </td>
                </tr>
                
                <tr>
                    <td class="label"><tags:label key="lbl.file_cong_van_dinh_kem" required="true"/><font color="red">(select file .pdf)</font></td>
                    <td>
                         <tags:imFileUpload name="reqCardNotSaleFrom.clientFileNameApp" id="reqCardNotSaleFrom.clientFileNameApp" serverFileName="reqCardNotSaleFrom.serverFileNameApp" extension="pdf" impBankReceipt = "true"/>
                    </td>
                    
                    <td class="label" ><tags:label key="MES.CHL.005" required="true" /><font color="red">(select file .xls)</font></td>
                    <td class="text" >
                        <tags:imFileUpload name="reqCardNotSaleFrom.clientFileName" id="reqCardNotSaleFrom.clientFileName" serverFileName="reqCardNotSaleFrom.serverFileName" extension="xls"/>
                    </td>
                </tr>
                
                <tr>
                    <td style="width:10%; "><tags:label key="MSG.range.serial" required="true"/></td>
                    <td class="oddColumn" style="width:23%; ">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:textfield name="reqCardNotSaleFrom.serial" id="reqCardNotSaleFrom.serial" cssClass="txtInputFull" maxlength="15"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:textfield name="reqCardNotSaleFrom.toSerial" id="reqCardNotSaleFrom.toSerial" cssClass="txtInputFull" maxlength="15"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
                     <!-- phan nut tac vu -->
            <div align="center" class="button">
                <tags:submit formId="reqCardNotSaleFrom"
                             showLoadingText="true"
                             id="btnImportFile"
                             cssStyle="width:120px;"
                             targets="bodyContent"
                             value="Button.Input.File"
                             validateFunction="checkValidFields()"
                             preAction="importStockCardLostAction!importCardLost.do"/>
            </div>
            
            <div align="center" class="button">
                <tags:submit formId="reqCardNotSaleFrom"
                             showLoadingText="true"
                             id="btnInputRange"
                             cssStyle="width:120px;"
                             targets="bodyContent"
                             value="Button.Input.Range"
                             validateFunction="checkValidInputRange()"
                             preAction="importStockCardLostAction!inputRange.do"/>
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
            
            //ten file ko duoc de trong
            var clientFileNameApp = document.getElementById("reqCardNotSaleFrom.clientFileNameApp");
            if (trim(clientFileNameApp.value).length ==0){
                <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa chọn file cần tạo"--%>
                $('message').innerHTML = '<s:property value="getText('ERR.GOD.067')"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
                clientFileNameApp.focus();
                return false;
            }
            
            var reasonId = document.getElementById("reqCardNotSaleFrom.reasonId");
            if (reasonId.value == ''){
                <%--"Bạn chưa nhap ly do"--%>
                $('message').innerHTML = '<s:property value="getText('err.input.reason')"/>';
                reasonId.focus();
                return false;
            }
            
            return true;
        }
        
     checkValidInputRange = function() {
           
            //ten file ko duoc de trong
            var clientFileNameApp = document.getElementById("reqCardNotSaleFrom.clientFileNameApp");
            var serial = document.getElementById("reqCardNotSaleFrom.serial");
            var toSerial = document.getElementById("reqCardNotSaleFrom.toSerial");
            if (trim(clientFileNameApp.value).length ==0){
                <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa chọn file cần tạo"--%>
                $('message').innerHTML = '<s:property value="getText('ERR.GOD.067')"/>';
                //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
                clientFileNameApp.focus();
                return false;
            }
            
            var reasonId = document.getElementById("reqCardNotSaleFrom.reasonId");
            if (reasonId.value == ''){
                <%--"Bạn chưa nhap ly do"--%>
                $('message').innerHTML = '<s:property value="getText('err.input.reason')"/>';
                reasonId.focus();
                return false;
            }
            
            if (trim(serial.value).length == 0){
                <%--"Bạn chưa nhap serial"--%>
                $('message').innerHTML = '<s:property value="getText('MSG.GOD.173')"/>';
                serial.focus();
                return false;
            }
            if (trim(toSerial.value).length == 0){
                    <%--"Bạn chưa nhap serial"--%>
                    $('message').innerHTML = '<s:property value="getText('MSG.GOD.173')"/>';
                    toSerial.focus();
                    return false;
            }
            
            if (toSerial.value < serial.value) {
                $('message').innerHTML = '<s:property value="getText('ToSerial.Greater.FromSerial')"/>';
                serial.focus();
                return false;
            }

            if(!isNumberFormat(serial.value)){
                $('message').innerHTML = '<s:property value="getText('MSG.generates.number.type')"/>';
                serial.focus();
                return false;
            }

            if(!isNumberFormat(toSerial.value)){
                $('message').innerHTML = '<s:property value="getText('MSG.generates.number.type')"/>';
                toSerial.focus();
                return false;
            }
            
            return true;
        }   
        
      //xu ly su kien khi thay doi kieu nhap hang
    radioClick = function(value){
        if(value == 1) {
            //disable cac component phuc vu viec nhap so theo dai
            $('reqCardNotSaleFrom.reasonId').disabled = false;
            $('reqCardNotSaleFrom.clientFileNameApp').disabled = false;
            $('reqCardNotSaleFrom.serial').disabled = false;
            $('reqCardNotSaleFrom.toSerial').disabled = false;
            document.getElementById("btnImportFile").disabled = true;
            document.getElementById("btnInputRange").disabled = false;
            disableImFileUpload('reqCardNotSaleFrom.clientFileName', true);

        } else if(value == 2) {
            $('reqCardNotSaleFrom.reasonId').disabled = false;
            $('reqCardNotSaleFrom.clientFileNameApp').disabled = false;
            $('reqCardNotSaleFrom.clientFileName').disabled = false;
            $('reqCardNotSaleFrom.serial').disabled = true;
            $('reqCardNotSaleFrom.toSerial').disabled = true;
            document.getElementById("btnImportFile").disabled = false;
            document.getElementById("btnInputRange").disabled = true;
            disableImFileUpload('reqCardNotSaleFrom.clientFileName', false);
        } else {
            document.getElementById("btnImportFile").disabled = true;
            document.getElementById("btnInputRange").disabled = true;
        }
    }
   
</script>
