<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listCommGroupItems
    Created on : Mar 31, 2009, 5:27:26 PM
    Author     : DatPV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listCommItemGroup", request.getSession().getAttribute("listCommItemGroup"));
%>

<tags:imPanel title="Danh sách nhóm khoản mục">
    <div align="left" style="width:100%; height:600px; overflow:scroll; margin-left:5px; margin-top:5px;">
        <s:if test="#request.listCommItemGroup != null && #request.listCommItemGroup.size() != 0">
            <display:table id="tblCommGroupItems" name="listCommItemGroup"
                           pagesize="20"
                           requestURI="${contextPath}/commItemGroupsAction!pageNavigatorCommItemGroup.do"
                           targets="divDisplayInfo"
                           class="dataTable" cellpadding="1" cellspacing="1">
                <display:column title="STT" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                    ${fn:escapeXml(tblCommGroupItems_rowNum)}
                </display:column>
                <display:column escapeXml="true"  property="groupName" title="Tên nhóm khoản mục" sortable="false" headerClass="tct"/>
                <display:column title="Loại báo cáo" sortable="false" headerClass="tct">
                    <s:if test = "#attr.tblCommGroupItems.reportType == 1">
                        Bảng tính phí bán hàng
                    </s:if>
                    <s:elseif test = "#attr.tblCommGroupItems.reportType == 2">
                        Bảng tính khoản giảm trừ
                    </s:elseif>
                    <s:elseif test = "#attr.tblCommGroupItems.reportType == 3">
                        Group mục 1 có cả 2 loại trên
                    </s:elseif>
                </display:column>
                <display:column title="Trạng thái" sortable="false" headerClass="tct">
                    <s:if test = "#attr.tblCommGroupItems.status == 1">
                        Hiệu lực
                    </s:if>
                    <s:elseif test = "#attr.tblCommGroupItems.status == 2">
                        Không hiệu lực
                    </s:elseif>
                </display:column>
                <display:column property="createDate" title="Ngày tạo" format="{0,date,dd/MM/yyyy}" sortable="false" headerClass="tct"/>
                <display:column title="Chi tiết" sortable="false" headerClass="tct" style="text-align:center;width:50px;">
                    <s:url action="commItemGroupsAction!displayCommItemGroups" id="URL">
                        <s:token/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>
                        <s:param name="selectedCommItemGroupsId" value="#attr.tblCommGroupItems.itemGroupId"/>
                    </s:url>
                    <sx:a targets="divDisplayInfo" href="%{#URL}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/accept.png" title="Thông tin chi tiết về nhóm khoản mục" alt="Chi tiết"/>
                    </sx:a>
                </display:column>
            </display:table>
        </s:if>
        <s:else>
            <table class="dataTable">
                <tr>
                    <th class="tct">STT</th>
                    <th class="tct">Tên nhóm khoản mục</th>
                    <th class="tct">Loại báo cáo</th>
                    <th class="tct">Trạng thái</th>
                    <th class="tct">Chi tiết</th>
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

        <!-- phan nut tac vu -->
        <s:if test="#session.toDetail != 1">
            <div align="center" style="padding-bottom:5px; padding-top:10px;">
                <input type="button" value="Thêm mới" style="width:80px;" onclick="prepareAddCommItemGroups()">
            </div>
        </s:if>

    </div>
</tags:imPanel>

<script language="javascript">

    //xu ly su kien onclick cua nut "Them" (them commItemGroups)
    prepareAddCommItemGroups = function() {
        gotoAction("divDisplayInfo", "${contextPath}/commItemGroupsAction!prepareAddCommItemGroups.do");
    }

</script>



