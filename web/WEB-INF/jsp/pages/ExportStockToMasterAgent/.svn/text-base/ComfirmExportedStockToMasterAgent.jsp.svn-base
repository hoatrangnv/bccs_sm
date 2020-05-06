<%-- 
    Document   : ComfirmExportedStockToMasterAgent
    Created on : Sep 21, 2016, 8:50:37 AM
    Author     : mov_itbl_dinhdc
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
%>
<s:form method="POST" id="exportStockForm" action="StockTransMasterAgentAction" theme="simple">
<s:token/>

    <s:hidden name="exportStockForm.actionType" id = "exportStockForm.actionType"/>
    <tags:imPanel title="MSG.search.trans">
        <!-- phan hien thi message -->
        <div>
            <tags:displayResult id="message" property="message" propertyValue="messageParam" type="key"/>
        </div>
        <table class="inputTbl4Col" >
            <tr>
                <s:hidden name="exportStockForm.stockTransId" id="stockTransId"/>
                <td class="label"> <tags:label key="MSG.transaction.code"/></td>
                <td class="text">
                    <s:textfield name="exportStockForm.actionCode" id="actionCode" cssClass="txtInputFull"/>
                    <script>$('actionCode').focus();</script>
                </td>
                <td  class="label"><tags:label key="MSG.status"/></td>
                <td  class="text">
                    <tags:imSelect name="exportStockForm.transStatus"
                              id="transStatus"
                              cssClass="txtInputFull"
                              headerKey="" headerValue="MSG.GOD.189"
                              list="1:MSG.GOD.190,2:MSG.GOD.191,3:MSG.GOD.194,4:MSG.GOD.195,5:MSG.GOD.196"/>
                </td>
            </tr>
            <tr>
                <td  class="label"> <tags:label key="MSG.fromStore"/></td>
                <td  class="text">
                    <s:textfield name="exportStockForm.fromOwnerName" id="fromOwnerName" readonly="true" cssClass="txtInputFull"/>
                    <s:hidden name="exportStockForm.fromOwnerCode" id="fromOwnerCode"/>
                    <s:hidden name="exportStockForm.fromOwnerId" id="fromOwnerId"/>
                    <s:hidden name="exportStockForm.fromOwnerType" id="fromOwnerType"/>
                </td>
                <td  class="label"><tags:label key="MSG.toStore"/></td>
                <td  class="text" >
                    <s:hidden name="exportStockForm.toOwnerType" id="toOwnerType"/>
                    <tags:imSearch codeVariable="exportStockForm.toOwnerCode" nameVariable="exportStockForm.toOwnerName"
                                   codeLabel="MSG.store.receive.code" nameLabel="MSG.store.receive.name"
                                   searchClassName="com.viettel.im.database.DAO.SaleToMasterAgentDAO"
                                   searchMethodName="getListObjectByParentIdOrOwenerId"
                                   getNameMethod="getListObjectByParentIdOrOwenerId"
                                   otherParam="fromOwnerCode"/>
                </td>
            </tr>
            <tr>
                <td  class="label">
                     <tags:label key="MSG.fromDate"/>
                </td>
                <td  class="text">

                    <tags:dateChooser property="exportStockForm.fromDateSearch" id="fromDate" styleClass="txtInputFull"/>
                </td>
                <td  class="label">
                     <tags:label key="MSG.consign.date.to"/>
                </td>
                <td  class="text">

                    <tags:dateChooser property="exportStockForm.toDateSearch" id="toDate" styleClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center" >

                    <tags:submit formId="exportStockForm" confirm="false"
                                 showLoadingText="true" targets="bodyContent"
                                 value="MSG.find" preAction="StockTransMasterAgentAction!searchExportedNoteMasterAgent.do"
                                 validateFunction="checkValidSearchExportedStock()"/>
                </td>
            </tr>
           
        </table>

    </tags:imPanel>
    <div>
        <s:if test="#request.fileDownLoadConfirmPath != null">
            <script>
                window.open('${contextPath}${fn:escapeXml(fileDownLoadConfirmPath)}','','toolbar=yes,scrollbars=yes');
            </script>
            <a href="${contextPath}${fn:escapeXml(fileDownLoadConfirmPath)}">
                <tags:displayResult id="printExpMessage" property="printExpMessage" type="key"/>
            </a>
        </s:if>
    </div>
    <sx:div id="searchArea" theme="simple">        
        <jsp:include page="ListExportedNoteMasterAgent.jsp"/>
    </sx:div>
    <s:if test="#request.displayFileConfirm == 1">
        <table>
            <tr>
                <td class="label"><tags:label key="lbl.file_cong_van_dinh_kem" /><font color="red">(select file .pdf)</td>
                <td colspan="2">
                    <tags:imFileUpload name="exportStockForm.clientFileName" id="exportStockForm.clientFileName" 
                                       serverFileName="exportStockForm.serverFileName" extension="pdf" impBankReceipt = "true"
                                       />
                </td> 
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td colspan="4" align="center" >

                    <tags:submit formId="exportStockForm" confirm="false"
                                 showLoadingText="true" targets="bodyContent"
                                 confirm="true" confirmText="cf.duyet_yeu_cau"
                                 value="MSG.Confirm.Exp" preAction="StockTransMasterAgentAction!ConfirmExportedNoteMasterAgent.do"
                                 validateFunction="checkValidFieldsConfirm()"/>
                </td>
            </tr>
        </table>
     </s:if>
</s:form>
<script>
     checkValidFieldsConfirm = function() {
        //ten file ko duoc de trong
         var clientFileName = document.getElementById("exportStockForm.clientFileName");
         if (trim(clientFileName.value).length ==0){
 <%--$('resultViewChangeStaffInShopClient').innerHTML="Bạn chưa chọn file cần tạo"--%>
             $('message').innerHTML = '<s:property value="getText('ERR.GOD.067')"/>';
             //$('message').innerHTML = "!!!Lỗi. Bạn chưa chọn file dữ liệu";
             clientFileName.focus();
             return false;
         }
         
         return true;
     }
     checkValidSearchExportedStock = function() {
        
        var dateExported= dojo.widget.byId("fromDate");
            if(dateExported.domNode.childNodes[1].value.trim() == ""){
             <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo từ ngày';--%>
                $('message').innerHTML = '<s:property value="getText('ERR.RET.002')"/>';
                $('fromDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
             <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ ngày không hợp lệ';--%>
                $('message').innerHTML='<s:property value="getText('ERR.RET.003')"/>';
                $('fromDate').focus();
                return false;
            }        
          var toDateExported = dojo.widget.byId("toDate");
            if(toDateExported.domNode.childNodes[1].value.trim() == ""){
             <%--$( 'displayResultMsgClient' ).innerHTML='Chưa nhập ngày báo cáo đến ngày';--%>
                $('message').innerHTML='<s:property value="getText('ERR.RET.004')"/>';
                $('toDate').focus();
                return false;
            }
            if(!isDateFormat(toDateExported.domNode.childNodes[1].value)){
             <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo đến ngày không hợp lệ';--%>
                $('message').innerHTML='<s:property value="getText('ERR.RET.005')"/>/>';
                $('toDate').focus();
                return false;
            }
            if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),toDateExported.domNode.childNodes[1].value.trim(),"VN")){
             <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ phải nhỏ hơn Ngày báo cáo đến ngày';--%>
                $('message').innerHTML='<s:property value="getText('ERR.RET.002')"/>';
                $('fromDate').focus();
                return false;
            }  
             if(days_between(dateExported.domNode.childNodes[1].value.trim(),toDateExported.domNode.childNodes[1].value.trim()) > 31){
             <%--$( 'displayResultMsgClient' ).innerHTML='Ngày báo cáo từ phải nhỏ hơn Ngày báo cáo đến ngày';--%>
                $('message').innerHTML='<s:property value="getText('E.200078')"/>';
                $('toDate').focus();
                return false;
            } 
        return true;
     }
</script>
    