<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : assignDebitResult
    Created on : Sep 6, 2010, 11:59:09 AM
    Author     : NamLT
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.viettel.im.common.util.ResourceBundleUtils" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="imDef" uri="imDef" %>
<div>
    <tags:displayResult id="messageList" property="messageList" type="key"/>
</div>

<%
            request.setAttribute("contextPath", request.getContextPath());





%>

<div style="height:300px;overflow:auto" >
    <display:table name="listStockOwnerTmp" id="listStockOwnerTmp"
                   pagesize="20" class="dataTable"
                   cellpadding="1" cellspacing="1"
                   requestURI="${contextPath}/assignDebitAction!pageNavigatorForList.do"
                   targets="divListRange">
        <display:column title=" ${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
            ${fn:escapeXml(listStockOwnerTmp_rowNum)}
        </display:column>
        <display:column escapeXml="true"  property="code" title="${fn:escapeXml(imDef:imGetText('MSG.object.code'))}" sortable="false" headerClass="sortable"/>
       <%-- <display:column title="${fn:escapeXml(imDef:imGetText('MSG.chanel.type'))}" sortable="false" headerClass="sortable">
            <s:if test = "#attr.listStockOwnerTmp.ownerType == 1">
                ${fn:escapeXml(imDef:imGetText('MSG.channel.shop'))}
            </s:if>
            <s:elseif test = "#attr.listStockOwnerTmp.ownerType == 2">
                ${fn:escapeXml(imDef:imGetText('MES.CHL.103'))}
            </s:elseif>

        </display:column>--%>
          <display:column escapeXml="true"  property="name" title="${fn:escapeXml(imDef:imGetText('MSG.staffShopCode'))}" sortable="false" headerClass="sortable"/>
        <display:column escapeXml="true"  property="channelCode" title="${fn:escapeXml(imDef:imGetText('MSG.chanel.type'))}" sortable="false" headerClass="sortable"/>
        <display:column escapeXml="true"  property="maxDebit" title="${fn:escapeXml(imDef:imGetText('MSG.maxDebit'))}" sortable="false" headerClass="sortable"/>
        <display:column escapeXml="true"  property="currentDebit" title="${fn:escapeXml(imDef:imGetText('MSG.currentDebit'))}" sortable="false" headerClass="sortable"/>
        <display:column escapeXml="true"  property="dateReset" title="${fn:escapeXml(imDef:imGetText('MSG.reset.date'))}" sortable="false" headerClass="sortable"/>

        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">

            <s:a href="" onclick="assignDebit('%{#attr.listStockOwnerTmp.stockId}')">
                <img src="${contextPath}/share/img/accept.png" title="<s:text name="MSG.assignDebit"/>" alt="<s:text name="MSG.assignDebit"/>"/>
            </s:a>

        </display:column>

        <display:column title="<input id = 'checkAllId' type='checkbox' onclick=\"selectCbAll()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center">
           
                <s:checkbox  name="debitForm.selectedItems" id="checkBoxId%{#attr.listStockOwnerTmp.stockId}"
                             theme="simple" fieldValue="%{#attr.listStockOwnerTmp.stockId}"
                             onclick="checkSelectCbAll();"/>
        
        </display:column>

    </display:table>
</div>

<script language="javascript">

    
    
    assignDebit = function(stockId) {
     
    <%--//  if(confirm("'<s:property escapeJavaScript="true"  value="getError('MSG.ISN.043')"/>'")) {--%>
              gotoAction('bodyContent', "${contextPath}/assignDebitAction!prepareAssignDebitPage.do?stockId=" + stockId+ '&' +  token.getTokenParamString(),"debitForm");

              $('debitForm.channelTypeId').readOnly=true;
              $('debitForm.ChannelParent').readOnly=true;
     
              


    <%-- //$('messageList').innerHTML= '<s:property escapeJavaScript="true"  value="getError('MSG.ISN.044')"/>';--%>
    <%--$("messageList").innerHTML="Gán thành công";--%>
        
                }

     selectCbAll = function(){
        selectAll("checkAllId", "debitForm.selectedItems", "checkBoxId");
    }

    checkSelectCbAll = function(){
        checkSelectAll("checkAllId", "debitForm.selectedItems", "checkBoxId");
    }

    checkSelected =function(number) {
        var rowId = document.getElementsByName('debitForm.selectedItems');
        var cbId="checkBoxId";
        var i=0;
        var count=0;

        for(i = 0; i < rowId.length; i++){
            if (document.getElementById(cbId+rowId[i].value).checked==true) {
                count++;
            }
        }
        //alert(rowId);
        if (count==0 && number==1) {
//           alert(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.ISN.060')"/>'));
            alert(getUnicodeMsg('<s:text name="MSG.ISN.060"/>'));
            //alert ("Chưa chọn bản ghi để xóa");
            return false;
        }

        if (count==0 && number==2) { 
            alert('Chưa chọn bản ghi để gán');
            //alert ("Chưa chọn bản ghi để xóa");
            return false;
        }
        return true;
    }


</script>

