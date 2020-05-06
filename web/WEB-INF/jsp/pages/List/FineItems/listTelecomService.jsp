<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : listTelecomService
    Created on : Sep 14, 2009, 2:36:53 PM
    Author     : TheTM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">


<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title="Danh sách loại dịch vụ">
    <!--fieldset style="width:100%; background-color:#F5F5F5;">
    <legend>Danh sÃ¡ch loáº¡i dá»‹ch vá»¥</legend-->
    <div class="divHasBorder">
        <s:if test="#request.listTelecomService != null && #request.listTelecomService.size() > 0">
            <display:table id="tblListTelecomService" name="listTelecomService"
                           class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="STT" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListTelecomService_rowNum)}</display:column>
                <display:column escapeXml="true"  property="telServiceName" title="Loại dịch vụ" sortable="false" headerClass="tct"/>
                <display:column escapeXml="true"  property="description" title="Mô tả" sortable="false" style="width:50%" headerClass="tct"/>
                <display:column property="createDate" title="Ngày tạo" format="{0,date,dd/MM/yyyy}" sortable="false"  style="width:10%;text-align:center" headerClass="tct"/>
                <display:column title="Trạng thái" sortable="false" style="width:10%" headerClass="tct">
                    <s:if test="#attr.tblListTelecomService.status == 1">
                        Hiệu lực
                    </s:if>
                    <s:else>
                        Hết hiệu lực
                    </s:else>
                </display:column>
                <display:column title="Danh sách lý do phạt" sortable="false" style="width:80px; text-align:center" headerClass="tct">
                    <s:url action="finePriceAction!listFineItem" id="URL1">
                        <s:param name="selectedTelecomServiceId" value="#attr.tblListTelecomService.telecomServiceId"/>
                    </s:url>
                    <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/accept.png" title="Danh sách lý do phạt" alt="Danh sách lý do phạt"/>
                    </sx:a>
                </display:column>
            </display:table>
        </s:if>
        <s:else>
            <table class="dataTable">
                <tr>
                    <th class="tct">STT</th>
                    <th class="tct">Loại dịch vụ</th>
                    <th class="tct">Mô tả</th>
                    <th class="tct">Ngày tạo</th>
                    <th class="tct">Trạng thái</th>
                    <th class="tct">Danh sách lý do phạt</th>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </s:else>
    </div>
</tags:imPanel>
