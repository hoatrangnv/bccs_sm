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
        <s:if test="#request.clearDebitByHand == 'TRUE'">    
            <div align="center" style="width:100%">
                <tags:submit  id="searchButton"
                              cssStyle="width:120px;"
                              formId="saleManagmentForm"
                              showLoadingText="true"
                              targets="searchResultArea"
                              value="MSG.SAE.138"
                              preAction="orderManagementAction!searchBankTransferInfo.do"/>
            </div>
        </s:if> 
        <s:else>
            <s:if test="#request.roleBrOrderBank != 'BR_ORDER_BANK'">
                <div align="center" style="width:100%">
                    <tags:submit  id="searchButton"
                                  cssStyle="width:120px;"
                                  formId="saleManagmentForm"
                                  showLoadingText="true"
                                  targets="searchResultArea"
                                  value="MSG.SAE.138"
                                  preAction="orderManagementAction!searchSaleTrans.do"/>
                </div>
            </s:if>
        </s:else>    
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
            <s:if test="#request.clearDebitByHand != 'TRUE'">    
                <div style="width:100%; height:320px; overflow:auto;">
                    <display:table name="lstSaleTrans" targets="searchResultArea" id="trans" pagesize="500" 
                                   class="dataTable" cellpadding="1" cellspacing="1" 
                                   requestURI="orderManagementAction!pageNavigator.do">

                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.048'))}" sortable="false" headerClass="tct">
                            <div align="center">
                                <s:property escapeJavaScript="true"  value="%{#attr.trans_rowNum}"/>
                            </div>
                        </display:column>
                        <display:column escapeXml="true" property="saleTransCode" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.140'))}" sortable="false" headerClass="sortable"/>
                        <%--<display:column escapeXml="true" property="staffName" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.141'))}" sortable="false" headerClass="sortable"/>--%>
                        <%--<display:column escapeXml="true" property="telServiceName" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.075'))}" sortable="false" headerClass="sortable"/>--%>
                        <%--<display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.142'))}" property="saleTransTypeName" sortable="false" headerClass="sortable"/>--%>
                        <display:column escapeXml="false" property="saleTransDate" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.143'))}" format="{0,date,dd/MM/yyyy HH:mm:ss}" sortable="false" headerClass="sortable" />
                        <display:column escapeXml="false" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.094'))}" sortable="false" headerClass="tct">
                            <%----%>
                            <%----%>
                            <%----%>
                            <%----%>
                            <s:if test="#attr.trans.orderFrom != 'mBCCS'">
                                <s:if test="#attr.trans.isCheck==0 && #attr.trans.status==2"><font color="red"><s:property escapeJavaScript="true"  value="getText('MSG.SAE.309')"/></font></s:if>
                                <s:if test="#attr.trans.isCheck==1 && #attr.trans.status==5"><font color="red"><s:property escapeJavaScript="true"  value="getText('MSG.SAE.310')"/></font></s:if>
                                <s:if test="#attr.trans.status==4"><font color="red"><s:property escapeJavaScript="true"  value="getText('MSG.SAE.311')"/></font></s:if>
                                <s:if test="#attr.trans.isCheck==3 && #attr.trans.status==5"><font color="red"><s:property escapeJavaScript="true"  value="getText('MSG.SAE.312')"/></font></s:if>
                                </s:if><s:else>  
                                    <s:if test="#attr.trans.status==2"><font color="red"><s:property escapeJavaScript="true"  value="getText('MSG.SAE.317')"/></font></s:if>
                                <s:if test="#attr.trans.status==6"><font color="red"><s:property escapeJavaScript="true"  value="getText('MSG.SAE.318')"/></font></s:if>
                                <s:if test="#attr.trans.status==5"><font color="red"><s:property escapeJavaScript="true"  value="getText('MSG.SAE.319')"/></font></s:if>
                                <s:if test="#attr.trans.status==4"><font color="red"><s:property escapeJavaScript="true"  value="getText('MSG.SAE.311')"/></font></s:if>
                                </s:else>

                        </display:column>
                        <%--<display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.144'))}" property="invoiceNo" sortable="false" headerClass="tct"/>--%>
                        <display:column property="amountTax" format="{0,number,#,###.00}" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.145'))}" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="true" property="custName" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.123'))}" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="true" property="isdn" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.124'))}" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="true" property="contractNo" title="${fn:escapeXml(imDef:imGetText('MSG.SAE.125'))}" sortable="false" headerClass="sortable"/>
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.SAE.146'))}" sortable="false" headerClass="tct" style="width:50px">
                            <div align="center">
                                <s:url action="orderManagementAction!viewTransDetail" id="URL" encode="true" escapeAmp="false">
                                    <%--<s:param name="saleTransId" value="#attr.trans.saleTransId"/>--%>
                                    <s:param name="saleTransOrderId" value="#attr.trans.saleTransOrderId"/>
                                </s:url>
                                <sx:a targets="detailArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                                    <s:property escapeJavaScript="true"  value="getText('MSG.SAE.146')"/>
                                </sx:a>
                            </div>
                        </display:column>
                        <display:column  title="${fn:escapeXml(imDef:imGetText('MSG.SAE.306'))}" sortable="false" headerClass="tct" style="width:50px">
                            <s:url id="fileguide" namespace="/" action="accounrTransDownload" >
                                <s:param name="scanPath" value="#attr.trans.scanPath"/>
                            </s:url>
                            <s:a href="%{fileguide}">Download</s:a>  

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
            </s:if > <s:else> 
                <!--        ham xoa bank tranfer info chi duy nhat user co role SM_CLEAR_DEBIT_BY_HAND duoc su dung -->
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
                        <display:column escapeXml="false" title="Status of Bank Document No" sortable="false" headerClass="tct">
                            <s:if test="#attr.trans.status==1"><font color="red">Not Used</font></s:if>
                            <s:if test="#attr.trans.status==5"><font color="red">Pending</font></s:if>

                        </display:column>
                        <display:column property="amount" format="{0,number,#,###.00}" title="Amount" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="true" property="bankName" title="Bank Name" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="true" property="description" title="Description" sortable="false" headerClass="sortable"/>
                        <display:column escapeXml="true" property="createUser" title="Create User" sortable="false" headerClass="sortable"/>
                        <s:if test="#attr.trans.status==5">
                            <display:column title="Revert Pending" sortable="false" headerClass="tct">
                                <div align="center">
                                    <s:url action="orderManagementAction!revertPendingBankTranfer" id="URL" encode="true" escapeAmp="false">
                                        <s:param name="tranferId" value="#attr.trans.bankTranferInfoId"/>
                                    </s:url>
                                    <sx:a targets="detailArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                                        <s:property escapeJavaScript="true"  value="getText('MSG.ORDER.REVERT')"/>
                                    </sx:a>
                                </div>
                            </display:column>
                        </s:if>
                        <s:if test="#attr.trans.status==1">
                            <display:column title="Delete Bank Document No" sortable="false" headerClass="tct">
                                <div align="center">
                                    <s:url action="orderManagementAction!deleteBankTranfer" id="URL" encode="true" escapeAmp="false">
                                        <s:param name="tranferId" value="#attr.trans.bankTranferInfoId"/>
                                    </s:url>
                                    <sx:a targets="detailArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                                        <s:property escapeJavaScript="true"  value="getText('MSG.generates.delete')"/>
                                    </sx:a>
                                </div>
                            </display:column>
                            <display:column title="Cancel Bank Document No" sortable="false" headerClass="tct">
                                <div align="center">
                                    <s:url action="orderManagementAction!cancelBankTranfer" id="URL" encode="true" escapeAmp="false">
                                        <s:param name="tranferId" value="#attr.trans.bankTranferInfoId"/>
                                    </s:url>
                                    <sx:a targets="detailArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                                        <s:property escapeJavaScript="true"  value="getText('MSG.ORDER.CANCEL')"/>
                                    </sx:a>
                                </div>
                            </display:column>
                            <display:column title="Clear sale transaction" sortable="false" headerClass="tct" >
                                <input  id="saleTransId<s:property  value="%{#attr.trans_rowNum}"/>" name="saleTransId<s:property  value="%{#attr.trans_rowNum}"/>" placeholder="Enter saleTransId" style="text-align: center"/>
                                <s:url action="orderManagementAction!acceptBankTranfer" id="URL" encode="true" escapeAmp="false">
                                    <s:param name="tranferId" value="#attr.trans.bankTranferInfoId"/>
                                    <s:param name="saleTransId" value="saleTransId"/>
                                    <s:param name="rowNum" value="%{#attr.trans_rowNum}"/>
                                </s:url>
                                <sx:a targets="detailArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                                    <s:property escapeJavaScript="true"  value="getText('MSG.ORDER.CLEAR')"/>
                                </sx:a>
                            </display:column>
                            <display:column title="Clear payment transaction" sortable="false" headerClass="tct" >
                                <input  id="paymentId<s:property  value="%{#attr.trans_rowNum}"/>" name="paymentId<s:property  value="%{#attr.trans_rowNum}"/>" placeholder="Enter paymentId" style="text-align: center" />
                                <s:url action="orderManagementAction!clearPaymentTransaction" id="URL" encode="true" escapeAmp="false">
                                    <s:param name="tranferId" value="#attr.trans.bankTranferInfoId"/>
                                    <s:param name="paymentId" value="paymentId"/>
                                    <s:param name="rowNum" value="%{#attr.trans_rowNum}"/>
                                </s:url>
                                <sx:a targets="detailArea" href="%{#URL}" executeScripts="true" parseContent="true" errorNotifyTopics="errorAction">
                                    <s:property escapeJavaScript="true"  value="getText('MSG.ORDER.CLEAR')"/>
                                </sx:a>
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
            </s:else>   

    </fieldset>

    <sx:div id="detailArea">
        <jsp:include page="saleTransManagmentDetailOrder.jsp"/>
    </sx:div>

    <%--/s:if--%>
</div>
