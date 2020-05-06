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
<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<div style="width:100%;" >
    <tags:imPanel title="">   
        <div align="center" style="width:100%">
            <tags:submit  id="searchButton"
                          cssStyle="width:120px;"
                          formId="saleManagmentForm"
                          showLoadingText="true"
                          targets="searchResultArea"
                          value="MSG.SAE.138"
                          preAction="orderManagementAction!searchBankDocumentTransferInfo.do"/>
        </div>
        <div align="center" style="width:100%">
            <s:if test="saleManagmentForm.exportUrl !=null && saleManagmentForm.exportUrl!=''">
                <script>
                    window.open('${contextPath}<s:property escapeJavaScript="true"  value="saleManagmentForm.exportUrl"/>', '', 'toolbar=yes,scrollbars=yes');
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
            <!--        ham xoa bank tranfer info chi duy nhat user it_test duoc su dung -->
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
                        <s:if test="#attr.trans.status==4"><font color="red">Waitting</font></s:if>

                    </display:column>
                    <display:column property="amount" format="{0,number,#,###.00}" title="Amount" sortable="false" headerClass="sortable"/>
                    <display:column escapeXml="true" property="bankName" title="Bank Name" sortable="false" headerClass="sortable"/>
                    <display:column escapeXml="true" property="description" title="Description" sortable="false" headerClass="sortable"/>
                    <display:column escapeXml="true" property="createUser" title="Create User" sortable="false" headerClass="sortable"/>
                    <display:column title="Detail Child Bank" sortable="false" headerClass="tct" style="width:50px">
                        <div align="center">
                            <s:url action="orderManagementAction!preparePagePopupDocument" id="URLViewStock" encode="true" escapeAmp="false">
                                <s:param name="tranferId" value="#attr.trans.bankTranferInfoId"/>
                                <s:param name="tranferCode" value="#attr.trans.tranferCode"/>
                            </s:url>
                            <a href="javascript:void(0)" onclick="viewStockDetail('<s:property escapeJavaScript="true"  value="#URLViewStock"/>', 1000, 500)">
                                Detail
                            </a>
                        </div>
                    </display:column>
                    <s:if test="#request.roleDivideBank == 'TRUE'">
                        <display:column title="Divide" sortable="false" headerClass="tct" style="width:50px">
                            <s:if test="#attr.trans.status==1">
                                <div align="center">
                                    <s:url action="orderManagementAction!preparePagePopupDetailDocument" id="URLViewStock" encode="true" escapeAmp="false">
                                        <s:param name="tranferId" value="#attr.trans.bankTranferInfoId"/>
                                        <s:param name="tranferCode" value="#attr.trans.tranferCode"/>
                                    </s:url>
                                    <input type="button" id="hrefViewStockDetail" onclick="viewStockDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewStock"/>')" value="Divide"/>

                                </div>
                            </s:if>
                        </display:column>
                    </s:if>
                    <s:if test="#request.roleApproveRejectBank == 'TRUE'">
                        <display:column title="Accept" sortable="false" headerClass="tct" style="width:50px">
                            <div align="center">
                                <s:if test="#attr.trans.status==4">
                                    <s:url action="orderManagementAction!acceptDetailBankDocument" id="URL1">
                                        <s:param name="tranferId" value="#attr.trans.bankTranferInfoId"/>
                                        <s:param name="tranferCode" value="#attr.trans.tranferCode"/>
                                    </s:url>
                                    <sx:a targets="searchResultArea" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                        <img src="${contextPath}/share/img/accept_1.png" title="<s:property escapeJavaScript="true"  value="getError(MSG.SIK.145)"/>"  alt="<s:property escapeJavaScript="true"  value="getError(MSG.SIK.146)"/>"/>
                                    </sx:a>
                                </s:if>
                            </div>
                        </display:column>
                        <display:column title="Reject" sortable="false" headerClass="tct" style="width:50px">
                            <div align="center">
                                <s:if test="#attr.trans.status==4">
                                    <s:url action="orderManagementAction!rejectDetailBankDocument" id="URL1">
                                        <s:param name="tranferId" value="#attr.trans.bankTranferInfoId"/>
                                        <s:param name="tranferCode" value="#attr.trans.tranferCode"/>
                                    </s:url>
                                    <sx:a targets="searchResultArea" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                                        <img src="${contextPath}/share/img/accept_1.png" title="<s:property escapeJavaScript="true"  value="getError(MSG.SIK.145)"/>"  alt="<s:property escapeJavaScript="true"  value="getError(MSG.SIK.146)"/>"/>
                                    </sx:a>
                                </s:if>
                            </div>
                        </display:column>
                    </s:if>
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
        <jsp:include page="saleTransManagmentDetailOrder.jsp"/>
    </sx:div>
    <script>
                    viewSerial = function(ownerType, ownerId, stockModelId, stateId, quantity, maxView) {
                        $('returnMsgClient').innerHTML = "";
                        if (quantity * 1 > maxView * 1) {
                            $('returnMsgClient').innerHTML = '<s:text name="stock.viewDetailSerial.maxResult"/>';
                            return false;
                        }
                        openDialog("${contextPath}/ViewStockDetailAction!viewDetailSerial.do?ownerType=" + ownerType + "&ownerId=" + ownerId + "&stockModelId=" + stockModelId + "&stateId=" + stateId, 800, 700);
                    }
                    function addFields() {
                        var number = document.getElementById("member").value;
                        var container = document.getElementById("container2");
                        while (container.hasChildNodes()) {
                            container.removeChild(container.lastChild);
                        }
                        for (i = 0; i < number; i++) {
                            container.appendChild(document.createTextNode("Bank Document " + (i + 1) + " have amount: "));
                            var input = document.createElement("input");
                            input.type = "text";
                            container.appendChild(input);
                            container.appendChild(document.createElement("br"));
                        }
                    }

    </script>

    <%--/s:if--%>
</div>
