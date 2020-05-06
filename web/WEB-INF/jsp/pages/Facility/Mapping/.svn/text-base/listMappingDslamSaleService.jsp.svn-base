<%@page import="java.util.ResourceBundle"%>
<%@page import="com.viettel.security.util.StringEscapeUtils"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@page import="com.viettel.payment.common.Constant"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
    request.setAttribute("listMappingDslamSaleService", request.getSession().getAttribute("listMappingDslamSaleService"));
%>
<!-- phan hien thi thong tin message -->
<div style="width:100%">
    <tags:displayResult id="messageList" property="messageList" propertyValue="messageParam" type="key"/>
</div>
<!-- phan link download xuat ra excel -->
<div style="width:100%" align="center">
    <s:if test="#request.resultFile!=null && #request.resultFile!=''">
        <script>
            window.open('${fn:escapeXml(contextPath)}/download.do?<s:property value="#request.resultFile"/>', '', 'toolbar=yes,scrollbars=yes');
        </script>
        <div>
            <a href='${fn:escapeXml(contextPath)}/download.do?<s:property value="#request.resultFile"/>' ><s:property escapeJavaScript="true"  value="%{getText('title.common.download')}"/>
            </a>
        </div>
    </s:if>
</div>
<!-- phan link download xuat ra file log loi -->
<div style="width:100%" align="center">
    <s:if test="#request.resultFileError!=null && #request.resultFileError!=''">
        <div>
            <a href='${fn:escapeXml(contextPath)}/download.do?<s:property value="#request.resultFileError"/>' ><s:property escapeJavaScript="true"  value="%{getText('title.common.download')}"/>
            </a>
        </div>
    </s:if>
</div>
<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('title.common.user.mappingList'))}</legend>
    <div id="displayTagFrame" align="center" style="width:100%; height:300px; overflow:auto;" targets="divMapping">
        <s:if test="#request.listMappingDslamSaleService != null && #request.listMappingDslamSaleService.size() > 0">

            <display:table id="MappingDslamSaleService"
                           name="listMappingDslamSaleService"
                           class="dataTable"
                           cellpadding="1" cellspacing="1"
                           pagesize="20"
                           targets="displayTagFrame"
                           requestURI="mappingDslamSaleServiceAction!pageNavigator.do" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" headerClass="tct" style=" text-align: center ; width:30px;">
                    ${fn:escapeXml(MappingDslamSaleService_rowNum)}
                </display:column>
                <display:column escapeXml="true"  property="dslam" title="${fn:escapeXml(imDef:imGetText('MSG.Dslam'))}" sortable="false" headerClass="tct" style="width:100px;"/>
                <display:column escapeXml="true"  property="saleService" title="${fn:escapeXml(imDef:imGetText('MSG.SaleService'))}" sortable="false" headerClass="tct" style="width:100px;"/>
                <display:column escapeXml="true"  property="status" title="${fn:escapeXml(imDef:imGetText('MSG.status'))}" sortable="false" headerClass="tct" style="text-align: center ; width:100px;"/>

                <display:column escapeXml="false" title="${imDef:imGetText('MSG.generates.delete')}" sortable="false" style="width:80px; text-align:center" headerClass="sortable">
                    <s:if test="#attr.MappingDslamSaleService.status =='Activate'">
                        <sx:a onclick="confirmDelete('%{#attr.MappingDslamSaleService.dslamId}','%{#attr.MappingDslamSaleService.saleServiceId}')" >
                            <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:property value="getText(MSG.generates.delete)" />" alt="<s:property value="getText(MSG.generates.delete)" />"/>
                        </sx:a>
                    </s:if>
                    <s:if test="#attr.MappingDslamSaleService.status !='Activate' ">
                        <img src="${contextPath}/share/img/delete_icon_1.jpg" title="<s:property value="getText(MSG.generates.delete)" />" alt="<s:property value="getText(MSG.generates.delete)" />"/>
                    </s:if>
                </display:column>
            </display:table>

        </s:if>
        <s:else>
            <font color='red'>
                <s:property  value="%{getText('title.common.noResult')}"/>
            </font>
        </s:else>
    </div>

</fieldset>
<script type="text/javascript">
    confirmDelete = function(dslamId, saleServiceId) {
        if (confirm(getUnicodeMsg('<s:property value="getText('MSG.DET.005')" />'))) {
            gotoAction("bodyContent", "mappingDslamSaleServiceAction!delete.do?dslamId=" + dslamId + "&saleServiceId=" + saleServiceId + "&" + token.getTokenParamString());
        }
    }
</script>
