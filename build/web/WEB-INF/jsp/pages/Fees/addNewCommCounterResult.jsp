<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : addNewCommCounterResult
    Created on : May 14, 2009, 3:49:56 PM
    Author     : nguyentuan
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("listCommCounters", request.getSession().getAttribute("listCommCounters"));
%>

<sx:div id ="displayTagFrame">
    <display:table id="tbllistCommCounters" name="listCommCounters"
                   targets="displayTagFrame" pagesize="10"
                   class="dataTable" requestURI="CommCountersAction!pageNavigator.do" cellpadding="1" cellspacing="1">
        <display:column title="STT" headerClass="tct" sortable="false" style="text-align:center; width:50px; ">
        ${fn:escapeXml(tbllistCommCounters_rowNum)}
        </display:column>
        <display:column escapeXml="true"  title="Mã bộ đếm" property="counterCode"  headerClass="tct" style="text-align:left;width:100px;" />
        <display:column title="Tên bộ đếm" headerClass="tct" sortable="false" style="text-align:left;width:400px;">
            <s:property escapeJavaScript="true"  value="#attr.tbllistCommCounters.counterName"/>
        </display:column>
        <display:column title="Tên thủ tục" property="functionName" headerClass="tct" style="text-align:left; width:200px;" >
            <s:property escapeJavaScript="true"  value="#attr.tbllistCommCounters.functionName"/>
        </display:column>
        <!--display:column title="Chi tiết thủ tục" property="detailFunctionName" headerClass="tct" style="text-align:left;width:100px;">
            <!--s:property value="#attr.tbllistCommCounters.detailFunctionName"/>
        <!--/display:column-->
        <display:column title="Template BC" property="reportTemplate" headerClass="tct" style="text-align:left;width:100px;">
            <s:property escapeJavaScript="true"  value="#attr.tbllistCommCounters.reportTemplate"/>
        </display:column>
        <display:column title="Ngày tạo" property="createDate" format="{0,date,dd/MM/yyyy}" style="text-align:center;width:300px;" headerClass="tct" />
        <display:column title="Trạng thái" headerClass="tct" sortable="false" style="text-align:center;width:120px;">
            <s:if test="#attr.tbllistCommCounters.status == 1">Hiệu lực</s:if>
            <s:elseif test="#attr.tbllistCommCounters.status == 2">Không hiệu lực</s:elseif>
        </display:column>
        <display:column title="Tham số" sortable="false" headerClass="tct" style="text-align:center; width:100px; ">
            <sx:a onclick="updateCounterParam('%{#attr.tbllistCommCounters.counterId}')" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="Cập nhật tham số" alt="Cập nhật tham số"/>
            </sx:a>
        </display:column>
        <display:column title="Sửa" sortable="false" headerClass="tct" style="text-align:center; width:50px; ">
            <s:url action="CommCountersAction!prepareEditCommCounters" id="URL1">
                <s:param name="counterId" value="#attr.tbllistCommCounters.counterId"/>
            </s:url>
            <sx:a targets="bodyContent" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="Sửa" alt="Sửa"/>
            </sx:a>
        </display:column>
        <display:column title="Xoá" sortable="false" headerClass="tct" style="text-align:center; width:50px; ">
            <sx:a onclick="deleteCommCounter('%{#attr.tbllistCommCounters.counterId}')" cssClass="cursorHand" executeScripts="true" parseContent="true">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa" alt="Xóa"/>
            </sx:a>
        </display:column>
    </display:table>
</sx:div>

<script language="javascript">
    updateCounterParam = function(counterId) {
        openDialog("${contextPath}/CommCountersAction!actionPrepareCountersParamDialog.do?counterId="+ counterId, 750, 700, true);
    }

    deleteCommCounter = function(counterId) {
        if(confirm("Bạn có chắc chắn muốn xóa bộ đếm này?")) {
            gotoAction("bodyContent", "${contextPath}/CommCountersAction!deleteCommCounters.do?counterId="+ counterId+"&"+token.getTokenParamString());
        }
    }

</script>


