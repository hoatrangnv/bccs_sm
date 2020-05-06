<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : saleCollToRetailGoodsList
    Created on : Aug 31, 2010, 1:50:20 PM
    Author     : tronglv
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%@page import="com.guhesan.querycrypt.QueryCrypt" %>

<%
    String pageId = request.getParameter("pageId");
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("lstGoods", request.getSession().getAttribute("lstGoods" + pageId));
    String editable = (String) request.getAttribute("editable");
    request.setAttribute("editable", editable);
    String inputSerial = (String) request.getParameter("inputSerial");
    if (request.getAttribute("inputSerial") != null) {
        inputSerial = request.getAttribute("inputSerial").toString();
    }
    request.setAttribute("inputSerial", inputSerial);

%>

<tags:displayResult id="displayResultMsg" property="returnMsg" propertyValue = "returnMsgValue" type="key" />

<fieldset class="imFieldset">
    <legend><s:text name="MSG.list.stock.model"/></legend>
    <div style="width:100%;overflow:auto; height:295px; vertical-align:top" >
        <display:table id="good" name="lstGoods" class="dataTable" requestURI="javascript: void(0)" cellpadding="1" cellspacing="1">
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" headerClass="tct" style="text-align:center; ">
                <s:property escapeJavaScript="true"  value="%{#attr.good_rowNum}"/>
            </display:column>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.074'))}" property="stockModelCode" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.036'))}" property="stockModelName" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.075'))}" property="telecomServiceName" headerClass="tct"/>
            <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.037'))}" property="unit" sortable="false" headerClass="tct"/>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.038'))}" sortable="false" headerClass="tct">
                <s:div align="right">
                    <s:property escapeJavaScript="true"  value="#attr.good.quantityMoney"/>
                </s:div>
            </display:column>
            <!--            <.display:column title="{imDef:imGetText('MSG.SAE.038')}" property="quantity" sortable="false" style="text-align:right" headerClass="tct"/>-->

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.039'))}" sortable="false" headerClass="tct">
                <s:div align="right">
                    <s:property escapeJavaScript="true"  value="#attr.good.priceMoney"/>
                </s:div>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.040'))}" sortable="false" headerClass="tct">
                <s:div align="right">
                    <s:property escapeJavaScript="true"  value="#attr.good.amountMoney"/>
                </s:div>
            </display:column>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.041'))}" sortable="false" headerClass="tct">
                <!--Chi nhung mat hang quan ly theo serial moi cho phep nhap chi tiet serial-->
                <s:if test="#attr.good.checkSerial==1">
                    <s:if test="#attr.good.lstSerial != null && #attr.good.lstSerial.size()>0">
                        <div align="center">
                            <s:url action="InputSerialAction!prepareInputSerial" id="URLView" encode="true" escapeAmp="false">
                                <s:param name="totalReq" value="#attr.good.quantity"/>
                                <s:param name="stockTypeId" value="#attr.good.stockTypeId"/>
                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                <s:param name="ownerType" value="#attr.good.fromOwnerType"/>
                                <s:param name="ownerId" value="#attr.good.fromOwnerId"/>
                                <s:param name="stateId" value="#attr.good.stateId"/>
                                <s:param name="impChannelTypeId" value="#attr.good.channelTypeId"/>
                                <s:param name="isView" value="true"/>
                                <s:param name="purposeInputSerial" value="3"/>
                            </s:url>
                            <a href="javascript: void(0);" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URLView"/>',800,700)">
                                ${fn:escapeXml(imDef:imGetText('MSG.SAE.041'))}
                            </a>
                        </div>
                    </s:if>
                    <s:else>
                        <div align="center">
                            <s:url action="InputSerialAction!prepareInputSerial.do" id="URL" encode="true" escapeAmp="false">
                                <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                <s:param name="ownerType" value="2"/>
                                <s:param name="totalReq" value="#attr.good.quantity"/>
                                <s:param name="stateId" value="1"/>
                                <s:param name="impChannelTypeId" value="#attr.good.channelTypeId"/>
                                <s:param name="ownerId" value="#attr.good.fromOwnerId"/>
                                <s:param name="purposeInputSerial" value="3"/>
                            </s:url>
                            <a href="javascript:void(0)" onclick="openDialog('<s:property escapeJavaScript="true"  value="#URL"/>',800,700)">
                                ${fn:escapeXml(imDef:imGetText('MSG.SAE.043'))}
                            </a>
                        </div>
                    </s:else>
                </s:if>
            </display:column>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.033'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                <s:if test="#attr.good.lstSerial != null && #attr.good.lstSerial.size()>0">
                    -
                </s:if>
                <s:else>
                    <a onclick="prepareEditGoods('<s:property escapeJavaScript="true"  value="#attr.good.stockModelId"/>')">
                        <img src="${contextPath}/share/img/edit_icon.jpg" title="<s:text name = "MSG.SAE.033"/>" alt="<s:text name = "MSG.SAE.033"/>"/>
                    </a>
                </s:else>
            </display:column>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                <a onclick="deleteGoods('<s:property escapeJavaScript="true"  value="#attr.good.stockModelId"/>')">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="<s:text name = "MSG.SAE.042"/>" alt="<s:text name = "MSG.SAE.042"/>"/>
                </a>
            </display:column>


            <%--s:if test="#attr.good.lstSerial != null && #attr.good.lstSerial.size()>0">
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.033'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                </display:column>
            </s:if>
            <s:else>
                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.033'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                    <s:url action="saleCollToRetailAction!prepareEditGoods" id="URL2">
                        <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                    </s:url>
                    <sx:a targets="bodyContent" href="%{#URL2}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/edit_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.033'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.SAE.033'))}"/>
                    </sx:a>
                </display:column>
            </s:else--%>

            <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                <s:url action="saleCollToRetailAction!delGoods" id="URL3">
                    <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                </s:url>
                <sx:a targets="listGoods" href="%{#URL3}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/delete_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}"/>
                </sx:a>
            </display:column--%>

        </display:table>
    </div>

    <table class="inputTbl2Col">
        <tr>
            <td class="label"> <tags:label key="MSG.total.amount.before.tax"/></td>
            <td class="text">
                <s:textfield name="saleToCollaboratorForm.amountNotTaxMoney"  theme="simple" cssStyle="text-align:right" id="saleToCollaboratorForm.amountNotTaxMoney" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                <!--                <.s:hidden name = "saleToCollaboratorForm.amountNotTax" />-->
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="MSG.money.tax"/></td>
            <td  class="text">
                <s:textfield name="saleToCollaboratorForm.taxMoney"  theme="simple" cssStyle="text-align:right" id="saleToCollaboratorForm.taxMoney" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                <!--                <.s:hidden name = "saleToCollaboratorForm.tax" />-->
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="MSG.amount.total.discount"/></td>
            <td  class="text">
                <s:textfield name="saleToCollaboratorForm.discoutMoney"  theme="simple" cssStyle="text-align:right" id="saleToCollaboratorForm.discoutMoney" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                <!--                <.s:hidden name = "saleToCollaboratorForm.discout" />-->
            </td>
        </tr>
        <tr>
            <td class="label"><tags:label key="MSG.amount.total.payments"/></td>
            <td  class="text">
                <s:textfield name="saleToCollaboratorForm.amountTaxMoney"  theme="simple" cssStyle="text-align:right" id="saleToCollaboratorForm.amountTaxMoney" readonly="true" maxlength="1000" cssClass="txtInputFull"/>
                <!--                <.s:hidden name = "saleToCollaboratorForm.amountTax" />-->
            </td>
        </tr>
    </table>

</fieldset>


<script type="text/javascript" language="javascript">   
    
    prepareEditGoods = function(stockModelId){
//        alert(stockModelId);//
        gotoAction("bodyContent",'${contextPath}/saleCollToRetailAction!prepareEditGoods.do?stockModelId='+ stockModelId + "&" + token.getTokenParamString());
    }
    deleteGoods = function(stockModelId){
//        alert(stockModelId);
        gotoAction("listGoods",'${contextPath}/saleCollToRetailAction!delGoods.do?stockModelId='+ stockModelId + "&" + token.getTokenParamString());
    }

    //    textFieldNF($('saleToCollaboratorForm.amountNotTax'));
    //    textFieldNF($('saleToCollaboratorForm.tax'));
    //    textFieldNF($('saleToCollaboratorForm.discout'));
    //    textFieldNF($('saleToCollaboratorForm.amountTax'));


    //
    updateSerial=function(){
        var amountNotTax = document.getElementById("saleToCollaboratorForm.amountNotTaxMoney").value;
        var tax = document.getElementById("saleToCollaboratorForm.taxMoney").value;
        var discout = document.getElementById("saleToCollaboratorForm.discoutMoney").value;
        var amountTax = document.getElementById("saleToCollaboratorForm.amountTaxMoney").value;

        gotoAction("listGoods", "saleCollToRetailAction!refreshListGoods.do?amountNotTax="+ amountNotTax +"&amountTax=" +amountTax+"&discout=" +discout+"&tax=" +tax);

    }
    
</script>


