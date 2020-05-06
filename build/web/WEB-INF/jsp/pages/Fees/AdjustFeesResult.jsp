<%-- 
    Document   : AdjustFeesResult
    Created on : May 15, 2009, 8:54:25 AM
    Author     : ThanhNC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>

<sx:div id="displayTagFrame">
    <%
        request.setAttribute("listfees", request.getSession().getAttribute("listfees"));
    %>
    <fieldset style="width:95%; padding:10px 10px 10px 10px">
        <legend class="transparent">Tổng hợp phí hoa hồng</legend>
        <s:if test="#request.listfees!=null && #request.listfees.size()>0">

            <display:table pagesize="10" targets="displayTagFrame" id="tbllistfees" name="listfees" class="dataTable" cellpadding="1" cellspacing="1" requestURI="commAdjustFeesAction!pageNavigator.do">
                <display:column escapeXml="true"  title="Mã khoản mục" property="itemid"  sortable="true" headerClass="sortable" style="text-align:center"/>
                <display:column escapeXml="true"  title="Tên khoản mục" property="itemname"  sortable="true" headerClass="sortable" style="text-align:center"/>
                <display:column escapeXml="true"  title="Số lượng" property="quantity"  sortable="true" headerClass="sortable" style="text-align:center"/>
                <display:column escapeXml="true"  title="Đơn giá" property="fee"  sortable="true" headerClass="sortable" style="text-align:center"/>
                <display:column escapeXml="true"  title="Thành tiền" property="totalmoney"  sortable="true" headerClass="sortable" style="text-align:center"/>
                <display:column escapeXml="true"  title="Tiền có thuế" property="taxMoney"  sortable="true" headerClass="sortable" style="text-align:center"/>
            </display:table>

        </s:if>
        <s:else>
            <font color='red'>
                Danh mục rỗng
            </font>
        </s:else>
    </fieldset>
</sx:div >
