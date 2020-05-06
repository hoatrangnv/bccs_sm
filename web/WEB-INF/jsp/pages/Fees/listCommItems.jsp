<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listCommItems
    Created on : Mar 31, 2009, 5:27:26 PM
    Author     : DatPV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listCommItems", request.getSession().getAttribute("listCommItems"));
%>

<tags:imPanel title="Danh sách khoản mục">
    <s:if test="#request.listCommItems != null && #request.listCommItems.size() > 0">
        <display:table id="tblCommItems" name="listCommItems"
                       targets="listCommItems" pagesize="10"
                       requestURI="${contextPath}/commItemsAction!pageNavigatorCommItem.do"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="STT" sortable="false" style="width:40px; text-align:center" headerClass="tct">${fn:escapeXml(tblCommItems_rowNum)}</display:column>
            <display:column escapeXml="true"  property="itemName" title="Tên khoản mục" sortable="false" headerClass="tct" />
            <display:column property="startDate" title="Từ ngày" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct" style="width:80px; text-align:center"/>
            <display:column property="endDate" title="Đến ngày" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct" style="width:80px; text-align:center"/>
            <display:column title="Trạng thái" sortable="false" headerClass="tct" style="width:80px;text-align:center;">
                <s:if test = "#attr.tblCommItems.status == 1">
                    Hiệu lực
                </s:if>
                <s:else>
                    Không hiệu lực
                </s:else>
            </display:column>
            <display:column escapeXml="true"  property="description" title="Mô tả" sortable="false" headerClass="tct" style="text-align:center;"/>
            <display:column title="Chi tiết" sortable="false" style="width:40px; text-align:center;" headerClass="tct">
                <s:url action="commItemsAction!displayCommItems" id="URL1">
                    <s:token/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>
                    <s:param name="selectedCommItemsId" value="#attr.tblCommItems.itemId"/>
                </s:url>
                <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/accept.png" title="Chi tiết" alt="Chi tiết"/>
                </sx:a>
            </display:column>
        </display:table>
    </s:if>
    <s:else>
        <table class="dataTable">
            <tr>
                <th class="tct">STT</th>
                <th class="tct">Tên khoản mục</th>
                <th class="tct">Từ ngày</th>
                <th class="tct">Đến ngày</th>
                <th class="tct">Trạng thái</th>
                <th class="tct">Mô tả</th>
                <th class="tct">Chi tiết</th>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
                <td></td>
            </tr>
        </table>
    </s:else>

</tags:imPanel>

