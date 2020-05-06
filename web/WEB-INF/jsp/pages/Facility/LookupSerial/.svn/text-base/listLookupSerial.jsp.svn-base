<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listLookupSerial
    Created on : Jun 21, 2009, 11:18:08 AM
    Author     : Doan Thanh 8
    Desc       : danh sach cac serial
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<fieldset class="imFieldset">
    <tags:imPanel title="MSG.search.result">
        <div style="width:100%; height:350px; overflow:auto;">
            <display:table name="listLookupSerialBean" id="tblListLookupSerialBean"
                           class="dataTable" cellpadding="1" cellspacing="1">
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListLookupSerialBean_rowNum)}</display:column>
                <display:column escapeXml="true" property="serial" title="${fn:escapeXml(imDef:imGetText('MSG.serial.number'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.stockModelId'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MSG.stockModelName'))}" sortable="false" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.goods.state'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListLookupSerialBean.stateId == 1">
                        <s:property escapeJavaScript="true"  value="getText('MSG.GOD.new')"/>
                    </s:if>
                    <s:elseif test="#attr.tblListLookupSerialBean.stateId == 2">
                        <s:property escapeJavaScript="true"  value="getText('MSG.GOD.old')"/>
                    </s:elseif>
                    <s:elseif test="#attr.tblListLookupSerialBean.stateId == 3">
                        <s:property escapeJavaScript="true"  value="getText('MSG.GOD.error')"/>
                    </s:elseif>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.stock.type'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListLookupSerialBean.ownerType == 1">
                        <s:property escapeJavaScript="true"  value="getText('MSG.FAC.DepartShop')"/>
                    </s:if>

                    <s:elseif test="#attr.tblListLookupSerialBean.ownerType == 2">
                        <s:property escapeJavaScript="true"  value="getText('MSG.staff.stock')"/>
                    </s:elseif>
                </display:column>
                <display:column escapeXml="true" property="ownerCode" title="${fn:escapeXml(imDef:imGetText('MSG.store.code'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="ownerName" title="${fn:escapeXml(imDef:imGetText('MSG.storeName'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="contractCode" title="${fn:escapeXml(imDef:imGetText('lb.contract.code'))}" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true" property="batchCode" title="${fn:escapeXml(imDef:imGetText('lb.batch.code'))}" sortable="false" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListLookupSerialBean.status == 0">
                        <!--                        da ban-->
                        <s:property escapeJavaScript="true"  value="getText('L.200017')"/>
                    </s:if>
                    <s:elseif test="#attr.tblListLookupSerialBean.status == 1">
                        <!--                        trong kho-->
                        <s:property escapeJavaScript="true"  value="getText('L.200018')"/>
                    </s:elseif>                    
                    <s:elseif test="#attr.tblListLookupSerialBean.status == 2">
<!--                        -->
                        <s:property escapeJavaScript="true"  value="getText('MSG.used')"/>
                    </s:elseif>
                    <s:elseif test="#attr.tblListLookupSerialBean.status == 3">
                        <!--                        dang cho xác nhan nhap kho-->
                        <s:property escapeJavaScript="true"  value="getText('L.200020')"/>
                    </s:elseif>
                    <s:elseif test="#attr.tblListLookupSerialBean.status == 4">
<!--                        -->
                        <s:property escapeJavaScript="true"  value="getText('MSG.registed.KIT')"/>
                    </s:elseif>                    
                    <s:elseif test="#attr.tblListLookupSerialBean.status == 5">
<!--                        -->
                        <s:property escapeJavaScript="true"  value="getText('MSG.removed')"/>
                    </s:elseif>
                    <!--                    tam khoa (sim)-->
                    <s:elseif test="#attr.tblListLookupSerialBean.status == 6">
                        <s:property escapeJavaScript="true"  value="getText('L.200023')"/>
                    </s:elseif>
                    <s:elseif test="#attr.tblListLookupSerialBean.status == 14">
                        <!--                    cho deactive tren tong dai-->
                        <s:property escapeJavaScript="true"  value="getText('L.200031')"/>
                    </s:elseif>
                    <s:elseif test="#attr.tblListLookupSerialBean.status == 16">
                        <!--                    deactive tren tong dai that bai-->
                        <s:property escapeJavaScript="true"  value="getText('L.200033')"/>
                    </s:elseif>
                    <s:else>
                        <!--                        khong xac dinh-->
                        <s:property escapeJavaScript="true"  value="getText('L.200034')"/>
                    </s:else>
                </display:column>

                <%--display:column title="active status" sortable="false" headerClass="tct">
                    <s:property escapeJavaScript="true"  value="inactived fail"/>
                </display:column--%>

                <%--<display:column title="${fn:escapeXml(imDef:imGetText('MSG.history.view'))}" sortable="false" headerClass="tct" style="width:80px; text-align:center; ">
                    <s:a onclick="aOnclick('%{#attr.tblListLookupSerialBean.serial}','%{#attr.tblListLookupSerialBean.stockModelCode}','%{#attr.tblListLookupSerialBean.status}')" cssClass="cursorHand">
                        <img src="${contextPath}/share/img/accept.png" title="<s:property escapeJavaScript="true"  value="getText('MSG.history.view')"/>" alt="<s:property escapeJavaScript="true"  value="getText('MSG.history.view')"/>"/>
                    </s:a>
                </display:column>--%>
            </display:table>
        </div>
    </tags:imPanel>
</fieldset>

<script language="javascript">
    aOnclick = function(serial,stockModelCode,status) {
        //openDialog('lookupSerialAction!viewLookUpSerialHistory.do?serial='+serial+'&stockModelCode='+ stockModelCode, 800, 600, true);
        openDialog('lookupSerialAction!viewLookUpSerialHistory.do?lookupSerialForm.fromSerial='+serial+'&lookupSerialForm.stockModelCode='+ stockModelCode+'&lookupSerialForm.status='+status, 900, 700, true);
    }
</script>


