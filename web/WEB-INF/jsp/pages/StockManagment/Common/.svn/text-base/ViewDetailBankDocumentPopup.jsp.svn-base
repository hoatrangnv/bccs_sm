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
    <tags:displayResult id="resultUpdateSaleClient" property="resultUpdateSale" type="key"/>
    <fieldset class="imFieldset" >
        <legend><s:property escapeJavaScript="true"  value="getText('MSG.SAE.139')"/>
            <div style="width:100%; height:320px; overflow:auto;">
                <display:table name="lstSaleTrans" targets="searchResultArea" id="trans" pagesize="800" 
                               class="dataTable" cellpadding="1" cellspacing="1" 
                               requestURI="orderManagementAction!pageNavigator.do">

                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" headerClass="tct">
                        <div align="center">
                            <s:property escapeJavaScript="true"  value="%{#attr.trans_rowNum}"/>
                        </div>
                    </display:column>
                    <display:column escapeXml="true" property="tranferCode" title="Bank Document No" sortable="false" headerClass="sortable"/>
                    <display:column escapeXml="false" property="createTime" title="Date Created " format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="false" headerClass="sortable" />
                    <display:column escapeXml="false" title="Status" sortable="false" headerClass="tct">
                        <s:if test="#attr.trans.status==0"><font color="red">Used</font></s:if>
                        <s:if test="#attr.trans.status==1"><font color="red">Not Used</font></s:if>
                        <s:if test="#attr.trans.status==2"><font color="red">Cancel</font></s:if>

                    </display:column>
                    <display:column property="amount" format="{0,number,#,###.00}" title="Amount" sortable="false" headerClass="sortable"/>
                    <display:column escapeXml="true" property="bankName" title="Bank Name" sortable="false" headerClass="sortable"/>
                    <display:column escapeXml="true" property="description" title="Description" sortable="false" headerClass="sortable"/>
                    <display:column escapeXml="true" property="createUser" title="Create User" sortable="false" headerClass="sortable"/>
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


</div>
