<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : countersParam
    Created on : Apr 17, 2009, 10:28:30 AM
    Author     : NamNX
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("lstcountersParam", request.getSession().getAttribute("lstcountersParam"));
%>

<display:table id="countersParam" name="lstcountersParam" 
               pagesize="10" targets="lstcountersParam" class="dataTable" requestURI="CommCountersAction!pageNavigatorForCounterParam.do" cellpadding="1" cellspacing="1" >
    <display:column title="STT" headerClass="tct" sortable="false" style="text-align:center; width:50px; ">
        ${fn:escapeXml(countersParam_rowNum)}
    </display:column>
    <display:column title="Tên tham số" property="paramName" sortable="false" headerClass="sortable">
        <s:property escapeJavaScript="true"  value="#attr.countersParam.paramName"/>
    </display:column>
    <display:column escapeXml="true"  title="Kiểu dữ liệu" property="dataType" sortable="false" headerClass="tct"/>
    <%--display:column title="Sửa" sortable="false" headerClass="tct" style="text-align:center; width:50px; ">
        <s:url action="CommCountersAction!prepareEditCommCountersParam" id="URLEdit">
            <s:param name="counterParamId" value="#attr.countersParam.counterParamId"/>
        </s:url>
        <sx:a targets="popupBody" href="%{#URLEdit}" cssClass="cursorHand" executeScripts="true" parseContent="true">
            <img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa" alt="Sửa"/>
        </sx:a>
    </display:column--%>
    <%--display:column title="Xóa" sortable="false" headerClass="sortable" style="text-align:center; width:50px; ">
        <sx:a onclick="deleteCounterParam('%{#attr.countersParam.counterParamId}')" cssClass="cursorHand" executeScripts="true" parseContent="true">
            <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa" alt="Xóa"/>
        </sx:a>
    </display:column--%>
    <display:column title="Xóa" sortable="false" headerClass="sortable" style="text-align:center; width:50px; ">
        <s:url action="CommCountersAction!deleteCommCountersParam" id="URLDelete">
            <s:param name="counterParamId" value="#attr.countersParam.counterParamId"/>
        </s:url>
        <sx:a onclick="deleteCounterParam('%{#attr.countersParam.counterParamId}')">
            <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa" alt="Xóa"/>
        </sx:a>
    </display:column>
</display:table>

<script language="javascript">
    deleteCounterParam = function(counterParamId) {
        if(confirm("Bạn có thực sự muốn xóa hóa đơn này?")){
            document.getElementById("commCountersForm").action="CommCountersAction!deleteCommCountersParam.do?counterParamId=" + counterParamId+"&"+token.getTokenParamString();
            document.getElementById("commCountersForm").submit();
        }
    }
    /*

    deleteCounterParam = function(counterParamId) {
        if(confirm("Bạn có thực sự muốn xóa tham số này?")) {
            gotoAction("popupBody", "${contextPath}/CommCountersAction!deleteCommCountersParam.do?counterParamId="+ counterParamId);
        }
    }*/
</script>



