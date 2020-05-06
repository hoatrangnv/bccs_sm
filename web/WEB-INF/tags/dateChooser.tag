
<%@tag display-name="Viettel's Simple Date Chooser" pageEncoding="UTF-8"%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>


<%@attribute name="property"%>
<%@attribute name="id"%>
<%@attribute name="showTime" %>
<%@attribute name="insertFrame" %>
<%@attribute name="styleClass" %>
<%@attribute name="readOnly" %>
<%@attribute name="tabIndex" %>
<%@attribute name="cssStyle" %>

<sx:datetimepicker id="%{#attr.id}" disabled="%{#attr.readOnly}" type="date" name="%{#attr.property}" cssClass="%{#attr.styleClass}" displayFormat="dd/MM/yyyy" toggleType="explode" />

<%--
    <s:if test="#attr.readOnly!=null && #attr.readOnly=='true'">    
    <s:textfield theme="simple" name="%{#attr.property}" id="%{#attr.id'}" cssClass="txtInput" readonly="true"/>
    </s:if>
    <s:else>        
        <sx:datetimepicker id="%{#attr.id}" type="date" name="%{#attr.property}" cssClass="%{#attr.styleClass}" displayFormat="dd/MM/yyyy" toggleType="explode" />
    </s:else>
--%>
<%--
<input type="text" name="${property}"   id="${id}" dojoType="dropdowndatepicker" displayFormat="dd/MM/yyyy" readonly="${readOnly}"  containerToggle="explode"/>
--%>

<%--
<%if (property != null) {%>
<table class="${styleClass}">
<td width="auto">
<s:textfield name="%{#attr.property}" id="%{#attr.property}" style="width:100% !important;"/>
<!--html:text property="${property}"  styleId="${property}" styleClass="${styleClass}" readonly="${readOnly}" style="width:100% !important;" tabindex="${tabIndex}" /-->
</td>

<%if (!"true".equals(readOnly)) {%>
<td width="16px">
<a href="javascript:void(0)" onclick="if($('${property}').disabled)return false; if(self.gfPop)gfPop.fPopCalendar(document.getElementById('${property}'));return false;" >
<img id="${property}_trigger" src="${contextPath}/share/img/calendar.gif"
width="16" height="16"
alt="Click để chọn ngày tháng" tabindex="999">
</a>
</td>
<%} else {%>
<td width="16px">

<img id="${property}_trigger" src="${contextPath}/share/img/calendar.gif"
width="16" height="16"
alt="Click để chọn ngày tháng" tabindex="999">

</td>
<%}%>
</table>
<iframe width=174 height=189 name="gToday:normal:agenda.js.${property}" id="gToday:normal:agenda.js.${property}" src="${contextPath}/share/js/date/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<%}%>
<script>

generateDate( $( '${property}' ) );
</script>
--%>

