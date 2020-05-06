<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : ListGoodsRecover
    Created on : Sep 25, 2009, 8:52:05 AM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    String pageId = request.getParameter("pageId");
    if (request.getSession().getAttribute("lstGoods" + pageId) != null) {
        request.setAttribute("lstGoods", request.getSession().getAttribute("lstGoods" + pageId));
    }

    String inputSerial = (String) request.getParameter("inputSerial");
    if (request.getAttribute("inputSerial") != null) {
        inputSerial = request.getAttribute("inputSerial").toString();
    }
    request.setAttribute("inputSerial", inputSerial);
    request.setAttribute("notInputSerial", request.getParameter("notInputSerial"));
    String collaborator = (String) request.getAttribute("collaborator");
    request.setAttribute("collaborator", collaborator);

%>

<sx:div id="localListGoods">
    <tags:imPanel title="MSG.list.goods">
        <div style="height:210px; overflow:auto;">
            <tags:displayResult id="returnMsgGoodsClient" property="returnMsg"/>
            <s:if test="#request.lstGoods != null && #request.lstGoods.size()>0">
                <display:table id="good" name="lstGoods" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
                    <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" sortable="false" headerClass="tct">
                        <s:property escapeJavaScript="true"  value="%{#attr.good_rowNum}"/>
                    </display:column>
                    <s:if test="#attr.collaborator =='coll'">
                        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.175'))}" property="stockModelName" sortable="false" headerClass="tct"/>
                        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.178'))}" property="price" style="text-align:right" sortable="false" headerClass="tct"/>
                    </s:if> <s:else>
                        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.176'))}" property="stockTypeName"/>
                        <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.175'))}" property="stockModelName" sortable="false" headerClass="tct"/>
                    </s:else>
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" sortable="false" headerClass="tct">
                        <s:if test="#attr.good.stateId ==1"><tags:label key="MSG.GOD.169"/></s:if>
                        <s:if test="#attr.good.stateId ==3"><tags:label key="MSG.GOD.170"/></s:if>
                    </display:column>
                    <display:column  escapeXml="true" property="unit" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.177'))}" sortable="false" headerClass="tct"/>
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.179'))}" property="quantity" style="text-align:right" sortable="false" headerClass="tct"/>
                    <!--Neu cho phep sua, xoa hang hoa trong danh sach -->
                    <s:if test="goodsForm.editable != null && goodsForm.editable =='true'">
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.182'))}" sortable="false" headerClass="tct">
                            <s:url action="CommonStockAction!prepareEditGoodsRecover" id="URL2">
                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                <s:param name="stockTypeId" value="#attr.good.stockTypeId"/>
                                <s:param name="stateId" value="#attr.good.stateId"/>
                            </s:url>
                            <s:url action="CommonStockAction!delGoodsRecover" id="URL3" escapeAmp="false">
                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                <s:param name="stockTypeId" value="#attr.good.stockTypeId"/>
                                <s:param name="stateId" value="#attr.good.stateId"/>
                            </s:url>
                            <div align="center">
                                <!-- chi cho phep sua nhung hang hoa chua nhap chi tiet serial-->
                                <s:if test="#attr.good.lstSerial == null || #attr.good.lstSerial.size()==0">

                                    <sx:a targets="inputGoods" href="%{#URL2}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                        <img src="${contextPath}/share/img/edit_icon.jpg" title="'<s:text name="MSG.edit.goods"/>" alt="'<s:text name="MSG.edit.goods"/>"/>
                                    </sx:a>
                                </s:if>
                                <a onclick="deleteGoods('listGoods','<s:property escapeJavaScript="true"  value="#attr.URL3"/>')">
                                    <img src="${contextPath}/share/img/delete_icon.jpg" title="'<s:text name="MSG.delete.goods"/>" alt="'<s:text name="MSG.delete.goods"/>"/>
                                </a>
                            </div>
                        </display:column>
                    </s:if>
                </display:table>


            </s:if>
            <s:if test="#request.lstGoods == null || #request.lstGoods.size==0">

                <table class="dataTable">
                    <tr>
                        <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}</th>
                        <s:if test="#attr.collaborator =='coll'">
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.175'))}</th>
                            <th>${fn:escapeXml(imDef:imGetText('MSG.GOD.178'))}</th>
                        </s:if> <s:else>
                            <th>${fn:escapeXml(imDef:imGetText('MSG.GOD.176'))}</th>
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.175'))}</th>
                        </s:else>
                        <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}</th>
                        <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.177'))}</th>
                        <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.179'))}</th>
                        <s:if test="goodsForm.editable != null && goodsForm.editable =='true'">
                            <th class="tct">${fn:escapeXml(imDef:imGetText('MSG.GOD.182'))}</th>
                        </s:if>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>                         
                        <s:if test="#attr.inputSerial != null && #attr.inputSerial =='true'">
                            <td>&nbsp;</td>
                        </s:if>
                        <td>&nbsp;</td>
                    </tr>

                </table>
            </s:if>
        </div>
    </tags:imPanel>
</sx:div>


<script>

    viewStockDetailserial=function(path){
        var ownerType="1";
        var ownerId=$('exportStockForm.shopImportedId').value;
        path=path+"&ownerType=1&ownerId="+ownerId;
        openDialog(path, 900, 700,false);
    }

    deleteGoods=function(target, action){
        //        if(confirm("Bạn có thực sự muốn xoá hàng hoá khỏi danh sách?"))
        if (confirm(getUnicodeMsg('<s:text name="MSG.STK.0001"/>')))
        {
            gotoAction(target, action,"goodsForm");
        }
    }

    updateSerial=function(){
        var actionCode = document.getElementById("exportStockForm.cmdExportCode").value;
        gotoAction("localListGoods", "InputSerialAction!refreshListGoodsRecover.do?actionCode="+actionCode + "&" + token.getTokenParamString());
    }

</script>
