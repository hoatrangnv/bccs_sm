<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listStockTypes
    Created on : Apr 6, 2009, 1:50:40 PM
    Author     : tamdt1
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<!-- danh sach tat ca cac loai mat hang -->
<%--<tags:imPanel title= "Danh sách loại mặt hàng">--%>
<tags:imPanel title= "MSG.GOD.071">
    <div style="width:100%; height: 580px; overflow:auto; margin-top:5px;">
        <s:if test="#request.listStockTypes != null && #request.listStockTypes.size() > 0">
            <display:table id="tblListStockTypes" name="listStockTypes" class="dataTable" cellpadding="1" cellspacing="1" >
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListStockTypes_rowNum)}</display:column>
                <display:column escapeXml="true" property="name" title="${fn:escapeXml(imDef:imGetText('MSG.stock.stock.type'))}" sortable="false" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.tblListStockTypes.status == 1">
                        <tags:label key="MSG.GOD.002"/>
<!--                        Hiệu lực-->
                    </s:if>
                    <s:else>
                        <tags:label key="MSG.GOD.003"/>
<!--                        Không hiệu lực-->
                    </s:else>
                </display:column>
                <display:column escapeXml="true" property="notes" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.020'))}" sortable="false" headerClass="tct"/>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.068'))}" sortable="false" style="width:80px; text-align:center" headerClass="tct">
                    <s:url action="profileAction!listProfiles" id="URL1">
                        <s:param name="selectedStockTypeId" value="#attr.tblListStockTypes.stockTypeId"/>
                    </s:url>
                    <sx:a targets="divDisplayInfo" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/accept.png"
                             title="<s:text name="MSG.GOD.072"/>" alt="<s:text name="MSG.GOD.068"/>"/>
<!--                             title="Danh sách các profile của mặt hàng" alt="Danh sách profile"/>-->
                    </sx:a>
                </display:column>
            </display:table>
        </s:if>
        <s:else>
            <table class="dataTable">
                <tr>
                    <th class="tct"><tags:label key="MSG.GOD.073"/></th>
                    <th class="tct"><tags:label key="MSG.GOD.027"/></th>
                    <th class="tct"><tags:label key="MSG.GOD.062"/></th>
                    <th class="tct"><tags:label key="MSG.GOD.013"/></th>
                    <th class="tct"><tags:label key="MSG.GOD.031"/></th>
                    <th class="tct"><tags:label key="MSG.GOD.068"/></th>
<!--                    <th class="tct">STT</th>
                    <th class="tct">Loại mặt hàng</th>
                    <th class="tct">Ký tự phân cách</th>
                    <th class="tct">Trạng thái</th>
                    <th class="tct">Ghi chú</th>
                    <th class="tct">Danh sách profile</th>-->
                </tr>
                <tr>
                    <td>&nbsp;</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </table>
        </s:else>
    </div>
</tags:imPanel>



