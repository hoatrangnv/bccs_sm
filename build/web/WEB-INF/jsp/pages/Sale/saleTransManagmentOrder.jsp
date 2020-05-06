<%--
    Document   : saleTransManagment.jsp
    Quan ly giao dich ban hang
    Created on : 10/06/2009
    Author     : ThanhNC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@ taglib uri="VTdisplaytaglib" prefix="display"%>
<%@page  import="com.viettel.im.database.BO.UserToken" %>


<%
    UserToken userToken = (UserToken) request.getSession().getAttribute("userToken");
    String shopName = userToken.getShopName();
    Long shopId = userToken.getShopId();
    request.setAttribute("contextPath", request.getContextPath());
    request.setAttribute("shopName", shopName);
    request.setAttribute("lstPayMethod", request.getSession().getAttribute("lstPayMethod"));
    request.setAttribute("saleInvoiceTypeList", request.getSession().getAttribute("saleInvoiceTypeList"));

%>
<s:form action="orderManagementAction!searchSaleTrans" theme="simple"  enctype="multipart/form-data" method="post" id="saleManagmentForm">
    <s:token/>


    <tags:imPanel title="MSG.SAE.107">
        <div >
            <table class="inputTbl8Col">
                <s:if test="#request.clearDebitByHand != 'TRUE'">    
                    <s:if test="#request.roleBrOrderBank == 'BR_ORDER_BANK'">

                        <tr  id="trCreate">
                            <td class="label">File Import</td>
                            <td id="tagsImFileUpload" class="text" colspan="1"  >
                                <tags:imFileUpload name="saleManagmentForm.fileUrl" 
                                                   id="fileUrl" 
                                                   serverFileName="saleManagmentForm.fileUrl"
                                                   cssStyle="width:350px;"/>
                            </td>
                            <td class="text" colspan="1"  > 
                                <tags:submit formId="saleManagmentForm"
                                             showLoadingText="true"
                                             cssStyle="width:120px;"
                                             targets="searchResultArea"
                                             value="Create by file"
                                             preAction="orderManagementAction!createOrderBankByFile.do"/>
                            </td>
                            <td> <a href="/SM/share/pattern/TeamplateOrderBank.xls">Download template </a></td>
                        </tr>
                    </s:if>  
                    <s:else>
                        <tr>
                            <td class="label"> <tags:label key="MSG.SAE.315"/></td>
                            <td style="width:25%;" >
                                <s:textfield name="saleManagmentForm.sSaleTransCode" id="sSaleTransCode" cssClass="txtInputFull" maxlength="50"/>
                            </td>
                            <td></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td class="label"><tags:label key="MSG.SAE.126"/></td>
                            <td>
                                <tags:dateChooser id="sTransFromDate" property="saleManagmentForm.sTransFromDate" />
                            </td>
                            <td class="label"><tags:label key="MSG.SAE.127"/></td>
                            <td>
                                <tags:dateChooser id="sTransToDate" property="saleManagmentForm.sTransToDate" />
                            </td>
                        </tr>
                        <tr>
                            <td class="label">Order from</td>
                            <td>
                                <tags:imRadio name="saleManagmentForm.sSaleTransType" id="sSaleTransType"
                                              list="1:MSG.Order.From.WEB,2:MSG.Order.From.mBCCS,3:All"
                                              />

                            </td>
                        </tr>
                        <tr>
                            <td class="label"><tags:label key="MSG.SAE.128"/></td>
                            <td>
                                <tags:imSelect name="saleManagmentForm.transStatus"
                                               id="transStatus"
                                               cssClass="txtInputFull"
                                               headerKey="" headerValue="MSG.SAE.129"
                                               list="1|2:MSG.SAE.301, 1|5:MSG.SAE.302 ,1|4:MSG.SAE.311 ,1|55:MSG.SAE.312, 2|2:MSG.SAE.317, 2|6:MSG.SAE.318, 2|5:MSG.SAE.319"/>
                            </td>
                        </tr>
                    </s:else> 
                </s:if> 
                <s:else>
                    <tr>
                        <td class="label"> Bank document no </td>
                        <td style="width:15%; " >
                            <s:textfield name="saleManagmentForm.sSaleTransCode" id="sSaleTransCode" cssClass="txtInputFull" maxlength="20"/>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="label"><tags:label key="MSG.SAE.126"/></td>
                        <td>
                            <tags:dateChooser id="sTransFromDate" property="saleManagmentForm.sTransFromDate" />
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.127"/></td>
                        <td>
                            <tags:dateChooser id="sTransToDate" property="saleManagmentForm.sTransToDate" />
                        </td>
                    </tr>

                    <tr>
                        <td class="label"> Status </td>
                        <td>
                            <tags:imSelect name="saleManagmentForm.sTransStatus"
                                           id="sTransStatus"
                                           cssClass="txtInputFull"
                                           list="1:Not Used ,5:Pending"/>
                        </td>
                        <td></td>
                        <td></td>
                    </tr> 

                </s:else>    

            </table>
        </div>
        <sx:div id="searchResultArea">
            <jsp:include page="saleTransManagmentResultOrder.jsp"/>
        </sx:div>
    </tags:imPanel>
</s:form>
<script type="text/javascript" language="javascript">
    dojo.event.topic.subscribe("SaleTrans/search", function(event, widget) {
        widget.href = 'orderManagementAction!searchSaleTrans.do';
    });

</script>
