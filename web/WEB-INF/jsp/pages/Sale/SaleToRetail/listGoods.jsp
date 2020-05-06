<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listGoods
    Created on : Feb 24, 2011, 1:58:54 PM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            String pageId = request.getParameter("pageId");
            request.setAttribute("lstGoods", request.getSession().getAttribute("lstGoods" + pageId));
%>

<fieldset class="imFieldset">
    <legend><s:text name="MSG.list.stock.model"/></legend>
    <div style="width:100%;overflow:auto; height:320px; vertical-align:top" >
        <display:table targets="goodList" id="goods" name="lstGoods" class="dataTable" cellpadding="1" cellspacing="1" requestURI="destroySaleInvoiceAction!pageNavigator.do" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" headerClass="tct">
                <div align="center" style="vertical-align:middle">${fn:escapeXml(goods_rowNum)}</div>
            </display:column>
            <display:column escapeXml="true" property="stockModelCode" title="Goods Code" sortable="false" style="text-center:20px; padding-right:20px" headerClass="tct"/>
            <display:column escapeXml="true" property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.036'))}" sortable="false" style="text-align:left; padding-right:20px" headerClass="tct"/>
            <display:column escapeXml="true" property="stockModelUnit" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.037'))}" sortable="false" style="text-align:right; padding-center:20px" headerClass="tct"/>
            <display:column escapeXml="true" property="quantity" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.038'))}" sortable="false" style="text-align:right" headerClass="tct"/>
            <display:column escapeXml="true" property="priceDisplay" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.039'))}" sortable="false" style="text-align:right" headerClass="tct"/>
            <display:column escapeXml="true" property="amountBeforeTaxDisplay" title="Before Tax" sortable="false" style="text-align:right" headerClass="tct"/>
            <display:column escapeXml="true" property="amountTaxDisplay" title="Tax" sortable="false" style="text-align:right" headerClass="tct"/>
            <display:column escapeXml="true" property="amountAfterTaxDisplay" title="After Tax" sortable="false" style="text-align:right" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.041'))}" sortable="false" headerClass="tct">
                <s:if test="#attr.goods.checkSerial==1">
                    <s:if test="#attr.goods.lstSerial != null && #attr.goods.lstSerial.size()>0">
                        <div align="center">
                            <s:url action="InputSerialAction!prepareInputSerial" id="URLView" encode="true" escapeAmp="false">
                                <s:param name="totalReq" value="#attr.goods.quantity"/>
                                <s:param name="stockModelId" value="#attr.goods.stockModelId"/>
                                <s:param name="ownerType" value="2"/>
                                <s:param name="ownerId" value="#session.userToken.getUserID()"/>
                                <s:param name="stateId" value="1"/>
                                <s:param name="isView" value="true"/>
                                <s:param name="purposeInputSerial" value="1"/>
                                <s:param name="impChannelTypeId" value="1000321"/>
                                <s:param name="impOwnerId" value="#session.userToken.getUserID()"/>
                                <s:param name="impOwnerType" value="2"/>
                                <s:param name="reasonId" value="%{#attr.saleTransForm.reasonId}"/>
                            </s:url>
                            <a href="javascript: void(0);" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URLView"/>',800,600)">
                                <s:text name = "detail"/>
                            </a>
                        </div>
                    </s:if>
                    <s:else>
                        <div align="center">
                            <s:url action="InputSerialAction!prepareInputSerial.do" id="URL" encode="true" escapeAmp="false">
                                <s:param name="stockModelId" value="#attr.goods.stockModelId"/>
                                <s:param name="ownerType" value="2"/>
                                <s:param name="totalReq" value="#attr.goods.quantity"/>
                                <s:param name="stateId" value="1"/>
                                <s:param name="ownerId" value="#session.userToken.getUserID()"/>
                                <s:param name="purposeInputSerial" value="1"/>
                                <s:param name="impChannelTypeId" value="1000321"/>
                                <s:param name="impOwnerId" value="#session.userToken.getUserID()"/>
                                <s:param name="impOwnerType" value="2"/>
                                <s:param name="reasonId" value="%{#attr.saleTransForm.reasonId}"/>
                            </s:url>                            
                            <a href="javascript:void(0)" onclick="newOpenDialog('<s:property escapeJavaScript="true"  value="#URL"/>')">
                                <s:text name = "input"/>
                            </a>                            
                        </div>
                    </s:else>
                </s:if><s:else>-</s:else>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.033'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                <s:if test="#attr.goods.lstSerial != null && #attr.goods.lstSerial.size()>0">
                    -
                </s:if>
                <s:else>
                    <a onclick="prepareEditGoods('<s:property escapeJavaScript="true"  value="#attr.goods.stockModelId"/>')">
                        <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name = "MSG.SAE.033"/>" alt="<s:text name = "MSG.SAE.033"/>" />
                    </a>
                </s:else>
            </display:column>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                <a onclick="deleteGoods('<s:property escapeJavaScript="true"  value="#attr.goods.stockModelId"/>')">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name = "MSG.SAE.042"/>" alt="<s:text name = "MSG.SAE.042"/>"/>
                </a>
            </display:column>

        </display:table>
    </div>
    <table class="inputTbl2Col">
        <tr>
            <td class="label"> <tags:label key="MSG.total.amount.before.tax"/></td>
            <td class="text">
                <s:textfield name="saleTransForm.amountBeforeTaxDisplay"  theme="simple" cssStyle="text-align:right" id="saleTransForm.amountNotTaxMoney" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="MSG.money.tax"/></td>
            <td  class="text">
                <s:textfield name="saleTransForm.amountTaxDisplay"  theme="simple" cssStyle="text-align:right" id="saleTransForm.taxMoney" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
            </td>
        </tr>
        <tr id="tr.saleTransForm.amountDiscountDisplay">
            <td class="label"><tags:label key="MSG.amount.total.discount"/></td>
            <td  class="text">
                <s:textfield name="saleTransForm.amountDiscountDisplay"  theme="simple" cssStyle="text-align:right" id="saleTransForm.amountDiscountDisplay" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="MSG.amount.total.payments"/></td>
            <td  class="text">
                <s:textfield name="saleTransForm.amountAfterTaxDisplay"  theme="simple" cssStyle="text-align:right" id="saleTransForm.amountTaxMoney" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
            </td>
        </tr>
        <tr id="tr.saleTransForm.custOTP">
            <td class="label"><tags:label key="MSG.SAE.214"/></td>
            <td  class="text">
                <s:textfield name="saleTransForm.custOTP"  theme="simple" cssStyle="text-align:right" id="saleTransForm.custOTP" maxlength="6" cssClass="txtInputFull"/>
            </td>
        </tr>
    </table>
</fieldset>

<script type="text/javascript" language="javascript">

    prepareEditGoods = function(stockModelId){
        var reasonId = document.getElementById('saleTransForm.reasonId').value;
        gotoAction("saleToRetailDetailDiv",'${contextPath}/saleToRetailV2Action!prepareEditGoods.do?stockModelId='+ stockModelId + "&reasonId="+ reasonId + "&" + token.getTokenParamString());
    }
    
    deleteGoods = function(stockModelId){
        var reasonId = document.getElementById('saleTransForm.reasonId').value;
        gotoAction("saleToRetailDetailDiv",'${contextPath}/saleToRetailV2Action!deleteGoods.do?stockModelId='+ stockModelId + "&reasonId="+ reasonId + "&" + token.getTokenParamString());
    }

    updateSerial = function() {
        gotoAction("saleToRetailDetailDiv",'${contextPath}/saleToRetailV2Action!updateListGoods.do?' + token.getTokenParamString());
    }
    
    newOpenDialog = function(url) {
        try {
            url = url + "&otpVipCustomer=" + document.getElementById("saleTransForm.custOTP").value;
            openDialog(url,800,600);
        } catch(e) {
        };
    }
</script>
