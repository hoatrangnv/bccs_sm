<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : UpdFeesResult
    Created on : May 15, 2009, 8:30:43 AM
    Author     : ThanhNC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("lstCalulate", request.getSession().getAttribute("lstCalulate"));
%>

<sx:div id="displayTagFrame">
    <display:table pagesize="10" targets="displayTagFrame"
                   id="tblCal" name="lstCalulate"
                   requestURI="commUpdFeesAction!pageNavigator.do" class="dataTable"
                   cellpadding="1" cellspacing="1" >
        <display:column title="STT" headerClass="tct" sortable="false" style="width:50px; text-align:center">
            ${fn:escapeXml(tblCal_rowNum)}
        </display:column>
        <display:column title="Khoản mục" headerClass="tct">
            <s:property escapeJavaScript="true"  value="#attr.tblCal.itemName"/>
        </display:column>
        <display:column  title="Kiểu DL nhập" headerClass="tct">
            <s:if test="#attr.tblCal.inputType == 2">Nhập tay số lượng</s:if>
            <s:if test="#attr.tblCal.inputType == 3">Nhập tay số tiền</s:if>
        </display:column>
        <display:column property="quantity" title="Số lượng"  headerClass="tct" format="{0,number,#,###}" style="text-align:right"/>
        <display:column property="totalMoney" title="Thành tiền"  headerClass="tct" format="{0,number,#,###}" style="text-align:right"/>
        <display:column property="createDate" title="Ngày cập nhật"  headerClass="tct" format="{0,date,dd/MM/yyyy}" style="text-align:center; width:100px; "/>
        <display:column title="Trạng thái"  headerClass="tct" style="width:100px; ">
            <s:if test="#attr.tblCal.approved == 0">Chưa duyệt</s:if>
            <s:elseif test="#attr.tblCal.approved == 1">Đã duyệt</s:elseif>
        </display:column>
        <display:column title="Sửa" sortable="false" headerClass="tct" style="width:50px; text-align:center">
            <s:if test="#attr.tblCal.approved == 0">
                <s:url action="commUpdFeesAction!preUpdateFee" id="URL2">
                    <s:token/>
                    <s:param name="commTransId" value="#attr.tblCal.commTransId"/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>


                </s:url>
                <sx:a targets="bodyContent" href="%{#URL2}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/edit_icon.jpg" title="Điều chỉnh khoản mục" alt="Sửa"/>
                </sx:a>
            </s:if>
            <s:elseif test="#attr.tblCal.approved == 1">
                <img src="${contextPath}/share/img/edit_icon_1.jpg" title="" alt=""/>
            </s:elseif>
        </display:column>
        <display:column title="Xóa" sortable="false" headerClass="tct" style="width:50px; text-align:center">
            <s:if test="#attr.tblCal.approved == 0">
                <s:url action="commUpdFeesAction!deleteCOMM" id="URL3">
                    <s:token/>
                    <s:param name="commTransId" value="#attr.tblCal.commTransId"/>
                    <s:param name="struts.token.name" value="'struts.token'"/>
                    <s:param name="struts.token" value="struts.token"/>


                </s:url>

                <sx:a onclick="delcommTran('%{#attr.tblCal.commTransId}')" >
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="Xóa khoản mục" alt="Xóa"/>
                </sx:a>
            </s:if>
            <s:elseif test="#attr.tblCal.approved == 1">
                <img src="${contextPath}/share/img/delete_icon_1.jpg" title="" alt=""/>
            </s:elseif>
        </display:column>
    </display:table>
</sx:div>
<script>
    delcommTran = function(commTransId) {
        if(confirm("Bạn có thực sự muốn xóa khoản mục này?")){
            gotoAction('bodyContent','commUpdFeesAction!deleteCOMM.do?commTransId=' + commTransId+"&"+ token.getTokenParamString());
        }
    }

</script>


