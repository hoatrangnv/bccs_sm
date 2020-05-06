<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : imSearchList
    Created on : Nov 17, 2009, 10:49:38 AM
    Author     : Doan Thanh 8
    Desc       : danh sach ket qua tim kiem
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<sx:div id="sxdivImListSearch">
    <fieldset class="imFieldset">
        <legend>${fn:escapeXml(imDef:imGetText('imSearchList.lblSearchResultList'))}</legend>
        <div id="divImListSearch" style="height:450px; overflow:auto;">
            <display:table id="tblListImSearchBean"
                           name="listImSearchBean"
                           class="dataTable"
                           cellpadding="1" cellspacing="1">
                <display:column title="${fn:escapeXml(imDef:imGetText('tbl.th.imSearchPopup.orderNumber'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListImSearchBean_rowNum)}</display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText(param['imSearchCodeLabel']))}" sortable="false" headerClass="tct">
                    <div class="cursorHand" ondblclick="selectIsdnBean('${fn:escapeXml(tblListImSearchBean.code)}', '${fn:escapeXml(tblListImSearchBean.name)}')">${fn:escapeXml(tblListImSearchBean.code)}</div>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText(param['imSearchNameLabel']))}" sortable="false" headerClass="tct">
                    <div class="cursorHand" ondblclick="selectIsdnBean('${fn:escapeXml(tblListImSearchBean.code)}', '${fn:escapeXml(tblListImSearchBean.name)}')">${fn:escapeXml(tblListImSearchBean.name)}</div>
                </display:column>
                <display:column title="${fn:escapeXml(imDef:imGetText('tbl.th.imSearchPopup.select'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                    <sx:a cssClass="cursorHand" executeScripts="true" onclick="selectIsdnBean('%{#attr.tblListImSearchBean.code}', '%{#attr.tblListImSearchBean.name}')" parseContent="true">
                        <img src="${contextPath}/share/img/accept.png" title="${fn:escapeXml(imDef:imGetText('tbl.th.imSearchPopup.select'))}" alt="${fn:escapeXml(imDef:imGetText('tbl.th.imSearchPopup.select'))}"/>
                    </sx:a>
                </display:column>
            </display:table>
        </div>
    </fieldset>
</sx:div>




