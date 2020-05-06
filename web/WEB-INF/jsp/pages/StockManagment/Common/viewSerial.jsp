<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : viewSerial
    Created on : Apr 1, 2009, 9:57:20 AM
    Author     : tamdt1
    Desc       : hien thi thong tin danh sach serial
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>


<%
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("message", request.getSession().getAttribute("message"));
        request.getSession().removeAttribute("message");
        request.setAttribute("currentDiscountDetailId", request.getSession().getAttribute("currentDiscountDetailId"));
        request.setAttribute("listDiscountDetails", request.getSession().getAttribute("listDiscountDetails"));
//request.setAttribute("lstSerial", request.getSession().getAttribute("lstSerial"));
%>
<s:form action="ViewStockDetailAction" id="serialGoods">
<s:token/>

    <sx:div id="detailSerial">
        <input type="hidden"  name="pageId" id="pageId"/>
        <s:hidden name="serialGoods.stockModelId" id="stockModelId"/>
        <s:hidden name="serialGoods.stockModelName" id="stockModelName"/>
        <s:hidden name="serialGoods.stockTypeId" id="stockTypeId"/>
        <s:hidden name="serialGoods.ownerId" id="ownerId"/>
        <s:hidden name="serialGoods.ownerType" id="ownerType"/>
        <s:hidden name="serialGoods.ownerName" id="ownerName"/>
        <s:hidden name="serialGoods.stateId" id="stateId"/>
        <s:hidden name="serialGoods.actionId" id="actionId"/>
        <!-- phan hien thi danh sach cac dai serial-->
        <div style="width:100%">
            <input type="button" onclick="exportSerial();"  value="${fn:escapeXml(imDef:imGetText('MSG.SAE.055'))}"/>
            <br/>
            <s:url action="ViewStockDetailAction" method="viewDetailSerial" id="viewDetailUrl" escapeAmp="false">
                <s:param name="ownerType" value="%{serialGoods.ownerType}"/>
                <s:param name="ownerId" value="%{serialGoods.ownerId}"/>
                <s:param name="stockModelId" value="%{serialGoods.stockModelId}"/>
                <s:param name="stateId" value="%{serialGoods.stateId}"/>
            </s:url>
           
            <display:table id="tblLstSerial" name="lstSerial" class="dataTable" cellpadding="1" cellspacing="1" targets="popupBody" pagesize="100" requestURI="${fn:escapeXml(viewDetailUrl)}" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                ${fn:escapeXml(tblLstSerial_rowNum)}
                </display:column>
                <display:column escapeXml="true" property="fromSerial" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.049'))}" sortable="false"  headerClass="tct"/>
                <display:column escapeXml="true" property="toSerial" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.050'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="quantity" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.051'))}" sortable="false" headerClass="tct"/>
            </display:table>


        </div>
        <br/>
        <div style="width:100%" align="center">
            <s:if test="serialGoods.exportUrl!=null && serialGoods.exportUrl!=''">
                <script>
                    window.open('${contextPath}<s:property escapeJavaScript="true"  value="serialGoods.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                </script>
                <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="serialGoods.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>

            </s:if>
        </div>
    </sx:div>
</s:form>
<script>
    exportSerial= function(){
        <%--document.getElementById("serialGoods").action="ViewStockDetailAction!exportSerial.do?exportViewDetail=true";
        document.getElementById("serialGoods").submit();--%>
        setAction("serialGoods","ViewStockDetailAction!exportSerial.do?exportViewDetail=true");
    }
</script>


