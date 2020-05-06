<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : invoiceSaleTransDetail
    Created on : 22/04/2009, 16:43:14 PM
    Author     : tungtv
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%            //request.setAttribute("saleTransDetailList", request.getSession().getAttribute("saleTransDetailList"));
            //request.setAttribute("lstSaleTransDetail", request.getSession().getAttribute("lstSaleTransDetail"));
%>

<s:if test="#request.lstSaleTransDetail != null && #request.lstSaleTransDetail.size() != 0">
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.181'))}</legend>
        <display:table id="saleTransDetail" name="lstSaleTransDetail" pagesize="50" class="dataTable" cellpadding="1" cellspacing="1" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" headerClass="tct" style="width:40px; text-align:center">
                ${fn:escapeXml(saleTransDetail_rowNum)}
            </display:column>
            <display:column escapeXml="true" property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.074'))}" sortable="false" style="text-align:left; " headerClass="tct"/>
            <display:column escapeXml="true" property="name" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.036'))}" sortable="false" headerClass="sortable"/>
            <display:column property="price" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.039'))}"   format="{0,number,#,###.00}"  style="text-align:right" sortable="false" headerClass="sortable"/>
            <display:column escapeXml="true" property="quantity" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.051'))}"  format="{0}" style="text-align:right"  sortable="false" headerClass="sortable"/>
            <display:column property="amount" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.145'))}"  format="{0,number,#,###.00}"  style="text-align:right" sortable="false" headerClass="sortable"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.156'))}" sortable="false" headerClass="tct" style="width:70px">
                <s:if test="#attr.saleTransDetail.checkSerial==1">
                    <div align="center">
                        <s:url action="saleManagmentAction!viewTransDetailSerial" id="URL" encode="true" escapeAmp="false">
                            <s:param name="saleTransDetailId" value="#attr.saleTransDetail.saleTransDetailId"/>
                        </s:url>
                        <a href="javascript:void(0)" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URL"/>',800,700)">
                            ${fn:escapeXml(imDef:imGetText('MSG.SAE.156'))}
                        </a>
                    </div>
                </s:if>
            </display:column>

            <display:footer> <tr> <td colspan="7" style="color:green">${fn:escapeXml(imDef:imGetText('MSG.SAE.147'))}:<s:property escapeJavaScript="true"  value="%{#request.lstSaleTransDetail.size()}" /></td> <tr> </display:footer>
            </display:table>

    </fieldset>

</s:if>
<s:else>
    <script>
        $( 'returnMsgClient' ).innerHTML='     ';
    </script>
    <br>
    <font color='red' size="2px">
        ${fn:escapeXml(imDef:imGetText('MSG.SAE.183'))}
    </font>    
</s:else>

