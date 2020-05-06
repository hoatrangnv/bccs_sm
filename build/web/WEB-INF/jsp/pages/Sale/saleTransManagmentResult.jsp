<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : saleTransManagmentResult.jsp
    Created on : 18/06/2009
    Author     : ThanhNC
    Desc       : danh sach giao dich ban hang
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib prefix="imDef" uri="imDef" %>

<div style="width:100%;" >
    <tags:imPanel title="">    
        <div align="center" style="width:100%">
            <tags:submit  id="searchButton"
                          cssStyle="width:120px;"
                          formId="saleManagmentForm"
                          showLoadingText="true"
                          targets="searchResultArea"
                          value="MSG.SAE.138"
                          preAction="saleManagmentAction!searchSaleTrans.do"/>
            <tags:submit id="exportExcel" confirm="false" formId="saleManagmentForm" showLoadingText="true" targets="searchResultArea" value="Excel..." preAction="saleManagmentAction!expSaleTransToExcel.do"
                         cssStyle="width:120px;"
                         />
        </div>
        <div align="center" style="width:100%">
        <s:if test="saleManagmentForm.exportUrl !=null && saleManagmentForm.exportUrl!=''">
                <script>
                    window.open('${contextPath}<s:property escapeJavaScript="true"  value="saleManagmentForm.exportUrl"/>','','toolbar=yes,scrollbars=yes');
                </script>
                <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="saleManagmentForm.exportUrl"/>' ><tags:label key="MSG.download2.file.here"/></a></div>

            </s:if>
        </div>
</tags:imPanel>                 

    <tags:displayResult id="resultUpdateSaleClient" property="resultUpdateSale" type="key"/>
    <%--s:if test="#session.lstSaleTrans != null && #session.lstSaleTrans.size() != 0">
        <%
            request.setAttribute("lstSaleTrans", request.getSession().getAttribute("lstSaleTrans"));
        %--%>
        <fieldset class="imFieldset" >
            <legend><s:property escapeJavaScript="true"  value="getText('MSG.SAE.139')"/>

                <div style="width:100%; height:320px; overflow:auto;">
                    <display:table name="lstSaleTrans" targets="searchResultArea" id="trans" pagesize="500" 
                                   class="dataTable" cellpadding="1" cellspacing="1" 
                                   requestURI="saleManagmentAction!pageNavigator.do">
                        
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" headerClass="tct">
                            <div align="center">
                                <s:property escapeJavaScript="true"  value="%{#attr.trans_rowNum}"/>
                            </div>
                        </display:column>
                        <display:column escapeXml="true" property="saleTransCode" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.140'))}" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="true" property="staffName" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.141'))}" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="true" property="telServiceName" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.075'))}" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.142'))}" property="saleTransTypeName" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="false" property="saleTransDate" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.143'))}" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="false" headerClass="sortable" />
                        <display:column escapeXml="false" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.094'))}" sortable="false" headerClass="tct">
                            <s:if test="#attr.trans.status==1"><s:property escapeJavaScript="true"  value="getText('MSG.SAE.130')"/></s:if>
                            <s:if test="#attr.trans.status==2"><s:property escapeJavaScript="true"  value="getText('MSG.SAE.131')"/></s:if>
                            <s:if test="#attr.trans.status==3"><s:property escapeJavaScript="true"  value="getText('MSG.SAE.132')"/></s:if>
                            <s:if test="#attr.trans.status==4"><font color="red"><s:property escapeJavaScript="true"  value="getText('MSG.SAE.133')"/></font></s:if>
                        </display:column>
                        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.144'))}" property="invoiceNo" sortable="false" headerClass="tct"/>
                        <display:column property="amountTax" format="{0,number,#,###.00}" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.145'))}" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="true" property="custName" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.123'))}" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="true" property="isdn" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.124'))}" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="true" property="contractNo" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.125'))}" sortable="false" headerClass="sortable"/>
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.146'))}" sortable="false" headerClass="tct" style="width:50px">
                            <div align="center">
                                <s:url action="saleManagmentAction!viewTransDetail" id="URL" encode="true" escapeAmp="false">
                                    <s:param name="saleTransId" value="#attr.trans.saleTransId"/>
                                </s:url>
                                <sx:a targets="detailArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                                    <s:property escapeJavaScript="true"  value="getText('MSG.SAE.146')"/>
                                </sx:a>
                            </div>
                        </display:column>
                        <display:footer> 
                            <tr> 
                                <td colspan="15" style="color:green">
                                    <s:property escapeJavaScript="true"  value="getText('MSG.SAE.147')"/> : <s:property escapeJavaScript="true"  value="%{#request.lstSaleTrans.size()}" />
                                </td> 
                            <tr> 
                        </display:footer>
                 </display:table>
                </div>
        </fieldset>
            
        <sx:div id="detailArea">
            <jsp:include page="saleTransManagmentDetail.jsp"/>
        </sx:div>
            
    <%--/s:if--%>
</div>
