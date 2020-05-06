<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : PayFeesResult
    Created on : May 15, 2009, 9:38:55 AM
    Author     : ThanhNC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
    request.setAttribute("lstCalulate", request.getSession().getAttribute("lstCalulate"));
%>


<sx:div id="displayTagFrame">
    <display:table id="tblCal" name="lstCalulate" 
    pagesize="10" targets="displayTagFrame" 
    requestURI="commPayFeesAction!pageNavigator.do"
    class="dataTable" cellpadding="1" cellspacing="1" >
        <display:column title="STT" headerClass="tct" sortable="false" style="width:50px; text-align:center">
            ${fn:escapeXml(tblCal_rowNum)}
        </display:column>
        <display:column escapeXml="true"  property="itemName" title="Tên khoản mục" headerClass="tct"/>
        <display:column  title="Loại khoản mục"  headerClass="tct">
            <s:if test="#attr.tblCal.inputType == 1">Tự động</s:if>
            <s:if test="#attr.tblCal.inputType == 2">Nhập tay số lượng</s:if>
            <s:if test="#attr.tblCal.inputType == 3">Nhập tay số tiền</s:if>
        </display:column>
        <display:column property="quantity" title="Số lượng" format="{0,number,#,###}" style="text-align:right"/>
        <display:column property="fee" title="Phí bán hàng" headerClass="tct" format="{0,number,#,###}" style="text-align:right"/>
        <display:column property="totalMoney" title="Thành tiền" headerClass="tct" format="{0,number,#,###}" style="text-align:right"/>
        <display:column title="Trạng thái duyệt"  headerClass="tct" style="width:100px; ">
            <s:if test="#attr.tblCal.approved == 0">Chưa duyệt</s:if>
            <s:if test="#attr.tblCal.approved == 1">Đã duyệt</s:if>
        </display:column>
        <display:column title="Trạng thái TT"  headerClass="tct" style="width:100px; ">
            <s:if test="#attr.tblCal.payStatus == 0">Chưa thanh toán</s:if>
            <s:if test="#attr.tblCal.payStatus == 1">Đã thanh toán</s:if>
        </display:column>
        <display:column property="createDate" title="Ngày cập nhật"  headerClass="tct" format="{0,date,dd/MM/yyyy}" style="text-align:center; width:100px; "/>
    </display:table>
</sx:div>



