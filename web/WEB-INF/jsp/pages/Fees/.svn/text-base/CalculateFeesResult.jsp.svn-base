<%@page import="com.viettel.security.util.StringEscapeUtils"%>
<%-- 
    Document   : CalculateFeesResult
    Created on : May 15, 2009, 9:25:02 AM
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
        request.setAttribute("lstCalulateUp", request.getSession().getAttribute("lstCalulateUp"));
    %>
    <fieldset class="gray" style="width:95% ; padding:10px 10px 10px 10px">
        <legend class="transparent">Tổng hợp chi phí hoa hồng</legend>
        <s:if test="#request.lstCalulateUp!=null && #request.lstCalulateUp.size()>0">

            <%int iIndex = 0;%>
            <display:table pagesize="10" targets="displayTagFrame" id="tblCal" name="lstCalulateUp" requestURI="commCalculateAction!pageNavigator.do" class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="STT" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                    <div align="center"><%=StringEscapeUtils.escapeHtml(++iIndex)%></div>
                </display:column>
                <!--display:column property="itemid" title="Mã khoản mục"  headerClass="tct" style="text-align:left"/-->
                <display:column escapeXml="true"  property="itemname" title="Tên khoản mục"  headerClass="tct" style="text-align:left"/>
                <display:column  title="Loại khoản mục"  headerClass="tct" style="text-align:left">
                    <s:if test="#attr.tblCal.inputType == 1">Tự động</s:if>
                    <s:if test="#attr.tblCal.inputType == 2">Nhập tay số lượng</s:if>
                    <s:if test="#attr.tblCal.inputType == 3">Nhập tay số tiền</s:if>
                </display:column>
                <display:column property="createDate" title="Ngày cập nhật"  headerClass="tct" format="{0,date,dd/MM/yyyy}" style="text-align:center"/>
                <display:column escapeXml="true"  property="quantity" title="Số lượng"  headerClass="tct" style="text-align:right"/>
                <display:column escapeXml="true"  property="fee" title="Đơn giá"  headerClass="tct" style="text-align:right"/>
                <display:column escapeXml="true"  property="totalmoney" title="Thành tiền"  headerClass="tct" style="text-align:right"/>
                <display:column title="Trạng thái"  headerClass="tct" style="text-align:center">
                    <s:if test="#attr.tblCal.approved == 0">Chưa duyệt</s:if>
                    <s:if test="#attr.tblCal.approved == 1">Đã duyệt</s:if>
                </display:column>
            </display:table>

        </s:if>
        <s:else>
            <font color="red">Không tìm thấy bản ghi nào</font>
        </s:else>
    </fieldset>
</sx:div>
