<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listLookupSerial
    Created on : Jun 21, 2009, 11:18:08 AM
    Author     : Nguyen Van Lam
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
    <legend>
        <s:text name="MSG.search.result"/>
    </legend>
    <div style="width:100%; height:350px; overflow:auto;">
        <display:table name="tblListCheckCard" id="card"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(card_rowNum)}</display:column>
            <%--display:column property="serial" title="Serial" sortable="false" headerClass="tct"/--%>
            <display:column title="Trạng thái" sortable="false" headerClass="tct">
                <s:if test="cardStatus == 0"><s:text name="MSG.FAC.card.notActive"/> <%--Thẻ đã được tạo, chưa được activate--%></s:if>
                <s:elseif test="cardStatus == 1"><s:text name="MSG.FAC.card.Active"/> <%--Thẻ đã được activate, sẵn sàng cho việc nạp tiền--%> </s:elseif>
                <s:elseif test="cardStatus == 2"><s:text name="MSG.FAC.card.Actived"/> <%--Thẻ đã bị nạp tiền--%></s:elseif>
                <s:elseif test="cardStatus == 3"><s:text name="MSG.FAC.card.Lock"/> <%--Thẻ đang ở trạng thái khóa--%></s:elseif>
                <s:else><s:property escapeJavaScript="true"  value="cardStatus"/></s:else>
            </display:column>

            <display:column escapeXml="true" property="cardExpired" title="${fn:escapeXml(imDef:imGetText('MSG.FAC.Card.NotvalidDate'))}" sortable="false" headerClass="tct"/>
            <display:column escapeXml="true" property="cardValue" title="${fn:escapeXml(imDef:imGetText('MSG.FAC.Card.Value'))}" sortable="false" headerClass="tct"/>
        </display:table>
    </div>
</fieldset>

<script language="javascript">
    selectCbItemAll = function(){
        selectAll("checkAllItemId", "activeCardForm.lstSelectedItem", "checkBoxItemId");
    }

    checkSelectCbAll = function(){
        checkSelectAll("checkAllItemId", "activeCardForm.lstSelectedItem", "checkBoxItemId");
    }
</script>


