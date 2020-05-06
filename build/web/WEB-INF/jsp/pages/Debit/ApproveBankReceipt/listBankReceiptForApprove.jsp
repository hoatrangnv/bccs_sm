<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : listBankReceipt
    Created on : Oct 30, 2010, 10:53:37 AM
    Author     : Doan Thanh 8
    Desc       : danh sach giay nop tien
--%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("listBankReceipt", request.getSession().getAttribute("listBankReceipt"));
%>


<fieldset class="imFieldset">
    <legend>${fn:escapeXml(imDef:imGetText('MSG.DET.077'))}</legend>

    <!-- phan hien thi danh sach cac lan dieu chinh -->
    <div class="divHasBorder" style="height: 250px; overflow:auto;">
        <table class="dataTable">
            <thead>
                <tr>
                    <th rowspan="2">${fn:escapeXml(imDef:imGetText('MSG.DET.078'))}</th>
                    <th colspan="5" style="border-right: 1px solid #999999">${fn:escapeXml(imDef:imGetText('MSG.DET.154'))}</th>
                    <th colspan="5">${fn:escapeXml(imDef:imGetText('MSG.DET.155'))}</th>
                    <th rowspan="2">
                        <input id='cbCheckAll' type='checkbox' onclick='checkAllCheckbox()' />
                    </th>
                </tr>
                <tr>
                    <th>${fn:escapeXml(imDef:imGetText('MSG.DET.119'))}</th>
                    <th>${fn:escapeXml(imDef:imGetText('MSG.DET.120'))}</th>
                    <th>${fn:escapeXml(imDef:imGetText('MSG.DET.070'))}</th>
                    <th>${fn:escapeXml(imDef:imGetText('MSG.DET.073'))}</th>
                    <th style="border-right: 1px solid #999999">${fn:escapeXml(imDef:imGetText('MSG.DET.121'))}</th>
                    <th>${fn:escapeXml(imDef:imGetText('MSG.DET.119'))}</th>
                    <th>${fn:escapeXml(imDef:imGetText('MSG.DET.120'))}</th>
                    <th>${fn:escapeXml(imDef:imGetText('MSG.DET.070'))}</th>
                    <th>${fn:escapeXml(imDef:imGetText('MSG.DET.073'))}</th>
                    <th>${fn:escapeXml(imDef:imGetText('MSG.DET.121'))}</th>
                </tr>
            </thead>
                <tbody>
                <s:if test="#request.listBankReceipt != null && #request.listBankReceipt.size() > 0">
                    <s:iterator id="bankReceipt" value="#request.listBankReceipt" status="bankReceiptStatus">
                        <tr>
                            <td style="text-align:center; "><s:property escapeJavaScript="true"  value="%{#bankReceiptStatus.index + 1}" /></td>
                            <td><s:property escapeJavaScript="true"  value="#attr.bankReceipt.shopCode" /></td>
                            <td><s:property escapeJavaScript="true"  value="#attr.bankReceipt.accountNo" /></td>
                            <td>
                                <s:if test="#attr.bankReceipt.bankReceiptDate != null">
                                    <s:text name="format.date">
                                        <s:param value="#attr.bankReceipt.bankReceiptDate" />
                                    </s:text>
                                </s:if>
                            </td>
                            <td><s:property escapeJavaScript="true"  value="#attr.bankReceipt.bankReceiptCode" /></td>
                            <td style="text-align: right;">
                                <s:if test="#attr.bankReceipt.amountAfterAdjustment != null">
                                    <s:text name="format.number">
                                        <s:param value="#attr.bankReceipt.amountAfterAdjustment" />
                                    </s:text>
                                </s:if>
                            </td>
                            <td><s:property escapeJavaScript="true"  value="#attr.bankReceipt.shopCodeInternal" /></td>
                            <td><s:property escapeJavaScript="true"  value="#attr.bankReceipt.accountNoInternal" /></td>
                            <td>
                                <s:if test="#attr.bankReceipt.bankReceiptDateInternal != null">
                                    <s:text name="format.date">
                                        <s:param value="#attr.bankReceipt.bankReceiptDateInternal" />
                                    </s:text>
                                </s:if>
                                
                            </td>
                            <td><s:property escapeJavaScript="true"  value="#attr.bankReceipt.bankReceiptCodeInternal" /></td>
                            <td style="text-align: right;">
                                <s:if test="#attr.bankReceipt.amountInternal != null">
                                    <s:text name="format.number">
                                        <s:param value="#attr.bankReceipt.amountInternal" />
                                    </s:text>
                                </s:if>
                                
                            </td>
                            <td style="text-align: center;">
                                <s:if test="#attr.bankReceipt.bankReceiptExternalId != null && #attr.bankReceipt.bankReceiptExternalId.compareTo(0L) > 0 && #attr.bankReceipt.fromInternalId != null && #attr.bankReceipt.fromInternalId.compareTo(0L) > 0">
                                    <!-- chi cho duyet trong truong hop GNT da khop -->
                                    <s:checkbox name="searchBankReceiptExternalForm.selectedFormId"
                                                id="arrSelectedFormId_%{#attr.bankReceipt.formId}"
                                                onclick="cbOnclick();"
                                                fieldValue="%{#attr.bankReceipt.formId}"
                                                theme="simple"/>
                                </s:if>
                                <s:else>
                                    -
                                </s:else>
                            </td>
                        </tr>
                    </s:iterator>
                </s:if>
                <s:else>
                    <tr>
                        <td colspan="12"></td>
                    </tr>
                </s:else>
            </tbody>
        </table>
        
        <%--
        <display:table id="tblBankReceipt" name="listBankReceipt"
                       class="dataTable" cellpadding="1" cellspacing="1" >
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.DET.078'))}" style="text-align:center" headerClass="tct" sortable="false">
                ${fn:escapeXml(tblBankReceipt_rowNum)}
            </display:column>
            <display:column escapeXml="true"  property="shopCode" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.119'))}"/>
            <display:column escapeXml="true"  property="accountNo" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.120'))}"/>
            <display:column property="bankReceiptDate" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.070'))}" format="{0,date,dd/MM/yyyy}"/>
            <display:column escapeXml="true"  property="bankReceiptCode" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.073'))}"/>
            <display:column property="amountAfterAdjustment" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.121'))}" format="{0,number,#,###.00}" style="text-align:right; "/>
            <display:column headerClass="tct" sortable="false" title="><" style="text-align: center; width: 50px;">
                ><
            </display:column>
            <display:column escapeXml="true"  property="shopCodeInternal" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.119'))}"/>
            <display:column escapeXml="true"  property="accountNoInternal" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.120'))}"/>
            <display:column property="bankReceiptDateInternal" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.070'))}" format="{0,date,dd/MM/yyyy}"/>
            <display:column escapeXml="true"  property="bankReceiptCodeInternal" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.073'))}"/>
            <display:column property="amountInternal" headerClass="tct" sortable="false" title="${fn:escapeXml(imDef:imGetText('MSG.DET.121'))}" format="{0,number,#,###.00}" style="text-align:right; "/>
            <display:column title="<input id='cbCheckAll' type='checkbox' onclick=\"checkAllCheckbox()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center;">
                <s:if test="#attr.tblBankReceipt.bankReceiptExternalId != null && #attr.tblBankReceipt.bankReceiptExternalId.compareTo(0L) > 0 && #attr.tblBankReceipt.fromInternalId != null && #attr.tblBankReceipt.fromInternalId.compareTo(0L) > 0">
                    <!-- chi cho duyet trong truong hop GNT da khop -->
                    <s:checkbox name="searchBankReceiptExternalForm.selectedFormId"
                            id="arrSelectedFormId_%{#attr.tblBankReceipt.formId}"
                            onclick="cbOnclick();"
                            fieldValue="%{#attr.tblBankReceipt.formId}"
                            theme="simple"/>
                </s:if>
                <s:else>
                    -
                </s:else>
            </display:column>
        </display:table>
        --%>
    </div>

    <!-- phan thong tin dieu chinh cho giay nop tien -->
    <div class="divHasBorder" style="margin-top: 5px; text-align: center;">
        <s:if test="#request.listBankReceipt != null && #request.listBankReceipt.size() > 0">
            <tags:submit formId="searchBankReceiptExternalForm"
                         showLoadingText="true"
                         cssStyle="width:90px;"
                         targets="bodyContent"
                         value="MSG.DET.122"
                         preAction="approveBankReceiptAction!approveBankReceipt.do"
                         confirm="true"
                         confirmText="MSG.DET.123"/>

            <tags:submit formId="searchBankReceiptExternalForm"
                         showLoadingText="true"
                         cssStyle="width:90px;"
                         targets="bodyContent"
                         value="MSG.DET.150"
                         preAction="approveBankReceiptAction!exportDataListToExcel.do"/>
        </s:if>
        <s:else>
            <input type="button" style="width: 90px;" value="${fn:escapeXml(imDef:imGetText('MSG.DET.122'))}" disabled>
            <input type="button" style="width: 90px;" value="${fn:escapeXml(imDef:imGetText('MSG.DET.150'))}" disabled>
        </s:else>
    </div>

</fieldset>

<script language="javascript">
    //
    checkAllCheckbox = function(){
        selectAll("cbCheckAll", "searchBankReceiptExternalForm.selectedFormId", "arrSelectedFormId_");
    }
    //
    cbOnclick = function(){
        checkSelectAll("cbCheckAll", "searchBankReceiptExternalForm.selectedFormId", "arrSelectedFormId_");
    }

</script>

