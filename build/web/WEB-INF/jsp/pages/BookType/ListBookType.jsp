<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : ListBookType
    Created on : May 15, 2009, 5:06:51 PM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            if (request.getAttribute("listBookType") == null) {
                request.setAttribute("listBookType", request.getSession().getAttribute("listBookType"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="displayTagFrame">
    <display:table pagesize="10" id="tbllistBookType"
                   targets="displayTagFrame" name="listBookType"
                   class="dataTable"
                   requestURI="GetBookTypeAction!pageNavigator.do"
                   cellpadding="1" cellspacing="1" >
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" headerClass="tct" sortable="false" style="width:40px;text-align:center">
            ${fn:escapeXml(tbllistBookType_rowNum)}
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.bill.serial.number'))}" property="serialCode" style="text-align:center" headerClass="tct"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.real.serial.number'))}" property="serialReal" style="text-align:center" headerClass="tct"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.bill.name'))}"  headerClass="tct" style="width:80px;text-align:center" >
            <s:property escapeJavaScript="true"  value="#attr.tbllistBookType.blockName"/>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.type'))}" headerClass="tct" sortable="false">
            <s:if test="#attr.tbllistBookType.type == 1"><tags:label key="MSG.book" /></s:if>
            <s:else><tags:label key="MSG.bin" /></s:else>
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.bill.template'))}" property="book" style="text-align:center" headerClass="tct"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.papers.book.number'))}" property="numInvoice" headerClass="tct" style="width:50px;text-align:center"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.book.bin.length'))}" property="lengthName" headerClass="tct" sortable="false" style="width:40px;text-align:center"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.bill.number.length'))}" property="lengthInvoice" headerClass="tct" style="width:40px;text-align:center"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.bill.number.wide'))}" property="pageWidth" style="text-align:center" headerClass="tct"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.bill.number.hight'))}" property="pageHeight" style="text-align:center" headerClass="tct"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.line.space'))}" property="rowSpacing" style="text-align:center" headerClass="tct"/>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.line.max'))}" property="maxRow" style="text-align:center" headerClass="tct"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" headerClass="tct" sortable="false" style="width:100px;text-align:center">
            <s:if test="#attr.tbllistBookType.status == 1"><tags:label key="MSG.active" /></s:if>
            <s:elseif test="#attr.tbllistBookType.status == 0"><tags:label key="MSG.inactive" /></s:elseif>
            <s:else>Undefined</s:else>
        </display:column>
        <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.comment'))}" property="description" style="width:200px;text-align:left" headerClass="tct"/>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.invoice.type'))}" headerClass="tct" sortable="false" style="width:100px;text-align:center">
            <s:if test="#attr.tbllistBookType.invoiceType == 1"><tags:label key="msg.invoiceTypeSale" /></s:if>
            <s:elseif test="#attr.tbllistBookType.invoiceType == 2"><tags:label key="msg.invoiceTypePayment" /></s:elseif>
            <s:elseif test="#attr.tbllistBookType.invoiceType == 3"><tags:label key="msg.receiptTypeSale" /></s:elseif>
            <s:elseif test="#attr.tbllistBookType.invoiceType == 4"><tags:label key="msg.receiptTypePayment" /></s:elseif>
            <s:elseif test="#attr.tbllistBookType.invoiceType == 5"><tags:label key="msg.expenseTypeSale" /></s:elseif>
            <s:elseif test="#attr.tbllistBookType.invoiceType == 6"><tags:label key="msg.expenseTypePayment" /></s:elseif>
            <s:elseif test="#attr.tbllistBookType.invoiceType == 7"><tags:label key="msg.voucherTypeSale" /></s:elseif>
            <s:elseif test="#attr.tbllistBookType.invoiceType == 8"><tags:label key="msg.voucherTypePayment" /></s:elseif>
            <s:elseif test="#attr.tbllistBookType.invoiceType == 9"><tags:label key="msg.invoiceTypeAdjustment" /></s:elseif>
            <s:else>Undefined</s:else>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.copy'))}" sortable="false" headerClass="sortable" style="text-align:center; width:50px;">
            <sx:a onclick="copyBookType('%{#attr.tbllistBookType.bookTypeId}')">
                <img src="${contextPath}/share/img/clone.jpg" title="<s:text name="MSG.copy"/>" alt="<s:text name="MSG.copy"/>"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
            <s:url action="GetBookTypeAction!prepareEditBookType" id="URL1">
                <s:token/>
                <s:param name="bookTypeId" value="#attr.tbllistBookType.bookTypeId"/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>


            </s:url>
            <%--
             <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa" alt="Sửa"/>
            </sx:a>
            --%>
            <sx:a href="javascript: void(0);" onclick="showEdit('%{#attr.tbllistBookType.bookTypeId}')">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.generates.edit" />"
                     alt="<s:text name="MSG.generates.edit" />"/>
            </sx:a>

        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
            <s:url action="GetBookTypeAction!deleteBookType" id="URL2">
                <s:token/>
                <s:param name="bookTypeId" value="#attr.tbllistBookType.bookTypeId"/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>


            </s:url>
            <sx:a onclick="delBookType('%{#attr.tbllistBookType.bookTypeId}')">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name="MSG.generates.delete" />"
                     alt="<s:text name="MSG.generates.delete" />"/>
            </sx:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.parameter.printer.update'))}" sortable="false" headerClass="tct" style="width:50px;text-align:center">
            <s:url action="GetBookTypeAction!prepareEditPrinterParamBookType" id="URL1">
                <s:token/>
                <s:param name="serialCode" value="#attr.tbllistBookType.serialCode"/>
                <s:param name="struts.token.name" value="'struts.token'"/>
                <s:param name="struts.token" value="struts.token"/>


            </s:url>
            <a href="javascript: void(0);" onclick="openPopup('<s:property escapeJavaScript="true"  value="#URL1"/>',true,true)">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name="MSG.print.param" />"
                     alt="<s:text name="MSG.print.param" />"/>
            </a>
        </display:column>
    </display:table>
</sx:div>

<script type="text/javascript" language="javascript">
    checkValidate = function(){
        return true;
    }
    viewParamDetail=function(path){
        //if(document.getElementById("stockModelId").value ==0)
        //{
        // alert("Chưa chọn mặt hàng");
        document.getElementById("bookTypeId").focus();
        //} else {
        openPopup(path, 700,900);
        //}
    }
    showEdit = function(bookTypeId)
    {
        var serialcode=document.getElementById('BookTypeForm.serialcode');
        serialcode.focus();        
        gotoAction('bodyContent','GetBookTypeAction!prepareEditBookType.do?bookTypeId=' + bookTypeId+"&isCopy=0"+"&"+ token.getTokenParamString());
    }
    copyBookType = function(rulesId) {
        gotoAction('bodyContent', "${contextPath}/GetBookTypeAction!prepareEditBookType.do?bookTypeId=" + rulesId+"&isCopy=1"+"&"+ token.getTokenParamString());
    }
    

</script>
