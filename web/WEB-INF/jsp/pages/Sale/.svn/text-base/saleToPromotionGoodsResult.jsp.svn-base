<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : saleToPromotionGoodsResult
    Created on : Jul 25, 2009, 10:21:18 PM
    Author     : Vunt
    Desc       : Quản lý thông tin hàng hóa được thêm vao giao dịch bán hàng KM
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
            String pageId = request.getParameter("pageId");
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("lstGoods", request.getSession().getAttribute("lstGoods" + pageId));
%>

<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.035'))}</legend>
    <div id="goodList" style="width:100%; vertical-align:top;height:280px " >

        <tags:displayResult id="displayResultMsg" property="returnMsg" propertyValue="returnMsgValue" type="key"/>

        <div style="height:135px;overflow:auto">
            <display:table targets="goodList" id="good" name="lstGoods" class="dataTable" cellpadding="1" cellspacing="1" requestURI="SaleToPromotionAction!updateGoodList.do" >
                <display:column escapeXml="false" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" headerClass="tct">
                    <div align="center" style="vertical-align:middle">${fn:escapeXml(good_rowNum)}</div>
                </display:column>
                <display:column escapeXml="true" property="stockModelName" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.036'))}" sortable="false" style="text-align:right; padding-right:20px" headerClass="tct"/>
                <display:column escapeXml="true" property="unit" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.037'))}" sortable="false" style="text-align:right; padding-right:20px" headerClass="tct"/>
                <display:column escapeXml="true" property="quantity" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.038'))}" sortable="false" style="text-align:right" headerClass="tct"/>
                <display:column escapeXml="true" property="priceDisplay" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.039'))}" sortable="false" style="text-align:right" headerClass="tct"/>
                <display:column escapeXml="true" property="amountTaxDisplay" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.040'))}" sortable="false" style="text-align:right" headerClass="tct"/>


                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.041'))}" sortable="false" headerClass="tct">
                    <s:if test="#attr.good.checkSerial==1">
                        <s:if test="#attr.good.lstSerial != null && #attr.good.lstSerial.size()>0">
                            <div align="center">
                                <s:url action="InputSerialAction!prepareInputSerial" id="URLView" encode="true" escapeAmp="false">
                                    <s:param name="totalReq" value="#attr.good.quantity"/>
                                    <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                                    <s:param name="ownerType" value="2"/>
                                    <s:param name="ownerId" value="#session.userToken.getUserID()"/>
                                    <s:param name="stateId" value="1"/>
                                    <s:param name="purposeInputSerial" value="2"/>
                                    <s:param name="isView" value="true"/>
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
                                    <s:param name="purposeInputSerial" value="2"/>
                                    <s:param name="ownerId" value="#session.userToken.getUserID()"/>
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
                        <%--a onclick="prepareEditGoods('<s:property escapeJavaScript="true"  value="#attr.good.stockModelId"/>')">
                            <img src="${contextPath}/share/img/edit_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}"/>
                        </a--%>
                        <s:url action="SaleToPromotionAction!prepareEditGoods" id="URL2">
                            <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                        </s:url>
                        <sx:a targets="addGood" href="%{#URL2}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                            <img src="${contextPath}/share/img/edit_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.033'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.SAE.033'))}"/>
                        </sx:a>
                    </s:else>
                </display:column>

                <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                    <a onclick="delGoods('<s:property escapeJavaScript="true"  value="#attr.good.stockModelId"/>')">
                        <img src="${contextPath}/share/img/delete_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}"/>
                    </a>
                </display:column>

                <%--display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}" sortable="false" style="text-align:center; width:50px" headerClass="tct">
                    <s:url action="SaleToPromotionAction!delGoods" id="URL3">
                        <s:param name="stockModelId" value="#attr.good.stockModelId"/>
                    </s:url>
                    <sx:a targets="inputUpdateGood" href="%{#URL3}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                        <img src="${contextPath}/share/img/delete_icon.jpg" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}" alt="${fn:escapeXml(imDef:imGetText('MSG.SAE.042'))}"/>
                    </sx:a>
                </display:column--%>
            </display:table>
        </div>

        <table class="inputTbl2Col">
            <tr>
                <td class="label"><tags:label key="MSG.SAE.044"/></td>
                <td class="text">
                    <s:textfield  name="saleToCollaboratorForm.amountNotTaxMoney" theme="simple" cssStyle="text-align:right" id="saleToCollaboratorForm.amountNotTax" readonly="true"  maxlength="1000" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.SAE.045"/></td>
                <td class="text">
                    <s:textfield name="saleToCollaboratorForm.taxMoney" theme="simple" cssStyle="text-align:right" id="saleToCollaboratorForm.taxMoney" readonly="true"  maxlength="1000" cssClass="txtInputFull"/>
                </td>
            </tr>            
            <tr>
                <td class="label"><tags:label key="MSG.SAE.080"/></td>
                <td class="text">
                    <s:textfield name="saleToCollaboratorForm.promotionAmountMoney" theme="simple" cssStyle="text-align:right" id="saleToCollaboratorForm.promotionAmountMoney" readonly="true"  maxlength="1000" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.SAE.081"/></td>
                <td class="text">
                    <s:textfield name="saleToCollaboratorForm.discoutMoney" theme="simple" cssStyle="text-align:right" id="saleToCollaboratorForm.discoutMoney" readonly="true"  maxlength="1000" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.SAE.046"/></td>
                <td class="text">
                    <s:textfield name="saleToCollaboratorForm.amountTaxMoney"  theme="simple" cssStyle="text-align:right" id="saleToCollaboratorForm.amountTax" readonly="true"  maxlength="1000" cssClass="txtInputFull"/>
                </td>
            </tr>
            
        </table>
    </div>

</fieldset>

<script type="text/javascript" language="javascript">

    prepareEditGoods = function(stockModelId){
        gotoAction("addGood",'${contextPath}/SaleToPromotionAction!prepareEditGoods.do?stockModelId='+ stockModelId + "&" + token.getTokenParamString());
    }
    delGoods = function(stockModelId){
        gotoAction("inputUpdateGood",'${contextPath}/SaleToPromotionAction!delGoods.do?stockModelId='+ stockModelId + "&" + token.getTokenParamString());
    }

</script>
