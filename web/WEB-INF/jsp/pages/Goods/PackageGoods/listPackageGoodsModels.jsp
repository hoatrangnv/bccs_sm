<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listSaleServicesModels
    Created on : Mar 15, 2009, 8:37:53 AM
    Author     : tamdt1
--%>

<%--
    Note: hien thi thong tin ve saleServicesModel cua saleService
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listPackageGoodsDetail", request.getSession().getAttribute("listPackageGoodsDetail"));
%>
<sx:div id="packageGoodsDetail" cssStyle="width:100%">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult id="listPackageGoodsModelMessage" property="listPackageGoodsModelMessage" type="key"/>
    </div>

    <display:table id="tblListPackageGoodsDetail" name="listPackageGoodsDetail" class="dataTable"
                   targets="packageGoodsDetail" cellpadding="1" cellspacing="1">

        <display:column  escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
            ${fn:escapeXml(tblListPackageGoodsDetail_rowNum)}
        </display:column>
        <display:column  escapeXml="true" property="stockTypeName" title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.027'))}" sortable="false" headerClass="tct"/>
        <display:column  escapeXml="true" property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.007'))}" sortable="false" headerClass="tct"/>
        <display:column  escapeXml="true" property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.008'))}" sortable="false" headerClass="tct"/>
        <display:column  escapeXml="true" property="notes" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.031'))}" sortable="false" headerClass="tct"/>
        <display:column  escapeXml="true" title=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.020'))}" style="with:10px; text-align:center">
            <s:a onclick="editPackageGoodsDetail('%{#attr.tblListPackageGoodsDetail.id}')">
                <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:property escapeJavaScript="true"  value="getText(MSG.GOD.020)"/>" alt="<s:property escapeJavaScript="true"  value="getText(MSG.GOD.020)"/>"/>
            </s:a>
        </display:column>
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))} " style="with:10px; text-align:center">
            <s:a onclick="delPackageGoodsDetail('%{#attr.tblListPackageGoodsDetail.id}')">
                <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property escapeJavaScript="true"  value="getText(MSG.GOD.021)"/>" alt="<s:property escapeJavaScript="true"  value="getText(MSG.GOD.021)"/>"/>
            </s:a>
        </display:column>
    </display:table>
</sx:div>

<script language="javascript">

    //
    editPackageGoodsDetail = function(id) {
        openDialog("${contextPath}/packageGoodsAction!prepareEditPackageGoodsDetail.do?id=" + id, 750, 700, true);
    }

    //
    delPackageGoodsDetail = function(saleServicesModelId) {
        //if (confirm('Bạn có chắc chắn muốn xóa mặt hàng này ra khỏi gói hàng này không?'))

        if(confirm("'<s:property escapeJavaScript="true"  value="getText('MSG.GOD.291')"/>'")){
            gotoAction('packageGoodsDetail', '${contextPath}/packageGoodsAction!delPackageGoodsDetail.do?id=' + saleServicesModelId + '&' + token.getTokenParamString() );
        }
}   
</script>

