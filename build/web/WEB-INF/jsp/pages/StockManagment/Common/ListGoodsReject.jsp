<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListGoodsReject
    Created on : Sep 21, 2010, 8:36:16 AM
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
            Object object;
            boolean revoke = false;

            if (request.getSession().getAttribute("lstGoods" + pageId) != null) {
                request.setAttribute("lstGoods", request.getSession().getAttribute("lstGoods" + pageId));
            }

            String inputSerial = (String) request.getParameter("inputSerial");
            if (request.getAttribute("inputSerial") != null) {
                inputSerial = request.getAttribute("inputSerial").toString();
            } else {
                object = request.getSession().getAttribute("inputSerial" + pageId);
                if (object != null) {
                    inputSerial = object.toString();
                }
            }

            request.setAttribute("inputSerial", inputSerial);
            request.setAttribute("notInputSerial", request.getParameter("notInputSerial"));
            String collaborator = (String) request.getAttribute("collaborator");
            request.setAttribute("collaborator", collaborator);
            Long amount = (Long) request.getSession().getAttribute("amount" + pageId);
            request.setAttribute("amount", amount);

%>
<sx:div id="localListGoods">
    <tags:imPanel title="MSG.detail.goods">
        <div style="height:210px; overflow:auto;">
            <tags:displayResult id="returnMsgGoodsClient" property="returnMsg"/>
            <display:table id="good" name="lstGoods" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
                <display:column
                    title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}"
                    sortable="false" headerClass="tct">
                    <s:property escapeJavaScript="true"  value="%{#attr.good_rowNum}"/>
                </display:column>
                <s:if test="#attr.collaborator =='coll'">
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.175'))}" property="stockModelName" sortable="false" headerClass="tct"/>
                </s:if> <s:else>
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.176'))}" property="stockTypeName"/>
                    <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.175'))}" property="stockModelName" sortable="false" headerClass="tct"/>
                </s:else>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.013'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.good.stateId ==1"><tags:label key="MSG.GOD.169"/></s:if>
                    <s:if test="#attr.good.stateId ==3"><tags:label key="MSG.GOD.170"/></s:if>
                </display:column>
                <display:column escapeXml="true" property="unit" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.177'))}" sortable="false" headerClass="tct"/>
                <s:if test="#attr.collaborator =='coll'">
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.178'))}" property="price" style="text-align:right" sortable="false" headerClass="tct" format="{0,number,#,###}"/>
                </s:if>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.179'))}" property="quantity" format="{0}"  style="text-align:right" sortable="false" headerClass="tct"/>
                <s:if test="#attr.collaborator =='coll'">
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.180'))}" sortable="false" headerClass="tct" style="text-align:right" format="{0,number,#,###}">
                        <s:text name="format.money">
                            <s:param name="value" value="#attr.good.price * #attr.good.quantity * 1"/>
                            <%--<s:property escapeJavaScript="true"  value="#attr.good.price * #attr.good.quantity * 1" ></s:property>--%>
                        </s:text>
                    </display:column>
                </s:if>
                <s:if test="#request.revokeColl =='true'">
                    <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.178'))}" property="price" style="text-align:right" sortable="false" headerClass="tct" format="{0,number,#,###}"/>
                </s:if>                
            </display:table>
            <s:if test="#attr.collaborator =='coll'">
                <table class="inputTbl">
                    <tr>
                        <td><tags:label key="MSG.sum.money.deposit"/></td>
                        <td>
                            <s:textfield name="exportStockForm.amountTax" value = "%{#request.amount}"  theme="simple" cssStyle="text-align:right" id="exportStockForm.amountTax" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                        </td>
                    </tr>
                </table>
            </s:if>
            <s:if test="#request.revokeColl == 'true'">
                <table class="inputTbl">
                    <tr>
                        <td><tags:label key="MSG.sum.money.deposit"/></td>
                        <td>
                            <s:textfield name="importStockForm.amountTax" value = "%{#request.amount}"  theme="simple" cssStyle="text-align:right" id="importStockForm.amountTax" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                        </td>
                    </tr>
                </table>
            </s:if>

        </div>
    </tags:imPanel>
</sx:div>
<script>
    textFieldNF($('exportStockForm.amountTax'));
    textFieldNF($('importStockForm.amountTax'));

    deleteGoods=function(target, action){
        //if(confirm("Bạn có thực sự muốn xoá hàng hoá khỏi danh sách?"))
        if(confirm(getUnicodeMsg('<s:text name="MSG.STK.001"/>'))){
    <s:if test="#request.revokeColl=='true'">
                action += "&revokeColl=true";
    </s:if>

                gotoAction(target, action,"goodsForm");
            }
        }

        updateSerial=function() {
    <s:if test="#attr.collaborator =='coll'">
            gotoAction("localListGoods", "InputSerialAction!refreshListGoodsColl.do?" + token.getTokenParamString());
    </s:if>
    <s:elseif test="#request.revokeColl=='true'">
            gotoAction("localListGoods", "InputSerialAction!refreshListGoodsRevokeColl.do?" + token.getTokenParamString());
    </s:elseif>
    <s:else>
            gotoAction("localListGoods", "InputSerialAction!refreshListGoods.do?" + token.getTokenParamString());
    </s:else>
    <%--
    <s:if test="#request.amount!=null">
        gotoAction("localListGoods", "InputSerialAction!refreshListGoodsColl.do");
    </s:if>
    <s:else>
        gotoAction("localListGoods", "InputSerialAction!refreshListGoods.do");
    </s:else>
    --%>
        }
        function checkAndOpenPopup(path, w, h){
            if(document.getElementById("shopExportId").value ==0)
            {
                alert(getUnicodeMsg( '<s:text name="MSG.GOD.174"/>'));
                //                alert("Chưa chọn nhân viên trả hàng");
                document.getElementById("shopExportId").focus();
                return false;
            }
            else
                openDialog(path,w,h,true);
        }
</script>
