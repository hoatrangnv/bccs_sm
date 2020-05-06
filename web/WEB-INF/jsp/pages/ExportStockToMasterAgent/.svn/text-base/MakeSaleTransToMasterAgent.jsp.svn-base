<%-- 
    Document   : MakeSaleTransToMasterAgent
    Created on : Sep 9, 2016, 9:47:55 AM
    Author     : mov_itbl_dinhdc
--%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            request.setAttribute("listTelecomService", request.getSession().getAttribute("listTelecomService"));
            request.setAttribute("listReaSonSaleExpCollaborator", request.getSession().getAttribute("listReaSonSaleExpCollaborator"));
            request.setAttribute("listPriceCollaborator", request.getSession().getAttribute("listPriceCollaborator"));
            request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType"));
%>
<script>    
    checkBeforeSave = function(){
        if ($('saleToCollaboratorForm.agentCodeSearch').value.trim() == ""){
            $('saleToCollaboratorForm.agentCodeSearch').focus();
            $('saleToCollaboratorForm.agentCodeSearch').select();
            $( 'displayResultMsg' ).innerHTML = '<s:text name="MSG.SAE.073"/>';
            return false;
        }

        if ($('reasonId').value.trim() == ""){
            $('reasonId').focus();
            $('reasonId').select();
            $( 'displayResultMsg' ).innerHTML = '<s:text name="MSG.SAE.060"/>';
            return false;
        }

        if ($('payMethodId').value.trim() == ""){
            $('payMethodId').focus();
            $('payMethodId').select();
            $( 'displayResultMsg' ).innerHTML = '<s:text name="MSG.SAE.061"/>';
            return false;
        }
        
       if ($('saleToCollaboratorForm.staffCode').value.trim() == "") {
             $('saleToCollaboratorForm.staffCode').focus();
            $('saleToCollaboratorForm.staffCode').select();
            $( 'displayResultMsg' ).innerHTML = '<s:text name="StaffCode.Export.Manatory"/>';
            return false;
        }    
        return true;
    }
        
</script>

<tags:imPanel title="MSG.Sale.to.master.agent">
    <s:form action="saleToMasterAgentAction" theme="simple" method="post" id="saleToCollaboratorForm">
<s:token/>

        <s:hidden name="staff_export_channel" id="staff_export_channel" value="staff_export_channel"/>

        <!-- phan nhap thong tin chi tiet ve giao dich -->
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.002'))}</legend>
            <table class="inputTbl6Col">
                <s:hidden name="saleToCollaboratorForm.saleTransId" id="saleToCollaboratorForm.saleTransId"/>
                <tr>
                    <%--<td style="width:10%; ">Mã cửa hàng<font color="red">*</font></td>--%>
                    <td class="label" style="width:10%" ><tags:label key="MES.CHL.015" required="true" /></td>
                    <td colspan="2" class="text">
                        <tags:imSearch codeVariable="saleToCollaboratorForm.shopCode" nameVariable="saleToCollaboratorForm.shopName"
                                       codeLabel="MES.CHL.015" nameLabel="MES.CHL.016"
                                       searchClassName="com.viettel.im.database.DAO.SaleToMasterAgentDAO"
                                       elementNeedClearContent="saleToCollaboratorForm.staffCode;saleToCollaboratorForm.staffName"
                                       searchMethodName="getListShop"
                                       roleList="f9ShopChangeInfoStaff"/>
                    </td>
                    
                    <td class="label"><tags:label key="MES.CHL.087" required="true" /></td>
                    <td colspan="2" class="text">
                        <tags:imSearch codeVariable="saleToCollaboratorForm.staffCode" nameVariable="saleToCollaboratorForm.staffName"
                                       codeLabel="MES.CHL.088" nameLabel="MES.CHL.089"
                                       searchClassName="com.viettel.im.database.DAO.SaleToMasterAgentDAO"
                                       searchMethodName="getListStaffManage"
                                       otherParam="saleToCollaboratorForm.shopCode"
                                       getNameMethod="getNameStaff"
                                       roleList="f9StaffManagementChangeInfoStaff"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.PARENT.MASTER.AGENT"/></td>
                    <td colspan="2" class="text">
                        <tags:imSearch codeVariable="saleToCollaboratorForm.parentMasterAgentCode" nameVariable="saleToCollaboratorForm.parentMasterAgentName"
                                       codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                       searchClassName="com.viettel.im.database.DAO.SaleToMasterAgentDAO"
                                       searchMethodName="getListParentMasterAgentId"
                                       />
                    </td>
                    
                    <td class="label"><tags:label key="MSG.SAE.067" required="true"/></td>
                    <td colspan="2" class="text">
                        <tags:imSearch codeVariable="saleToCollaboratorForm.agentCodeSearch" nameVariable="saleToCollaboratorForm.agentNameSearch"
                                       codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                       searchClassName="com.viettel.im.database.DAO.SaleToMasterAgentDAO"
                                       searchMethodName="getListObjectByParentIdOrOwenerId"
                                       otherParam="saleToCollaboratorForm.staffCode"
                                       />
                    </td>
                </tr>
                
                <tr>
                    <td  class="label"><tags:label key="MSG.Payment.Papers" required="true"/></td>
                    <td  class="text" colspan="2">
                        <s:textfield name="saleToCollaboratorForm.paymentPapersCode" theme="simple" id="saleToCollaboratorForm.paymentPapersCode" maxlength="50" cssClass="txtInputFull"/>
                    </td>
                    <td  class="label"><tags:label key="MSG.Amount.Payment" required="true"/></td>
                    <td  class="text" colspan="2">
                        <s:textfield name="saleToCollaboratorForm.amountPayment" theme="simple" id="saleToCollaboratorForm.amountPayment" maxlength="20" cssClass="txtInputFull"/>
                    </td>
                </tr>
                
                <tr>
                    <td class="label"><tags:label key="MSG.SAE.009" required="true"/></td>
                    <td  class="text">
                        <tags:imSelect name="saleToCollaboratorForm.reasonId"
                                       id="reasonId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.013"
                                       list="listReaSonSaleExpCollaborator"
                                       listKey="reasonId" listValue="reasonName"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.010" required="true"/></td>
                    <td class="text">
                        <tags:imSelect name="saleToCollaboratorForm.payMethodId"
                                       id="payMethodId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.SAE.014"
                                       list="1:MSG.SAE.071, 2:MSG.SAE.072" value="2"/>
                    </td>
                    <td class="label"><tags:label key="MSG.SAE.008" required="true"/></td>
                    <td class="text">
                        <tags:dateChooser property="saleToCollaboratorForm.saleDate" styleClass="txtInput" readOnly="true"/>
                    </td>
                </tr>
            </table>
        </fieldset>

        <!-- phan chiu tiet danh sach hang hoa co trong giao dich -->
        <table style="width:100%; border-spacing:0; margin-top:3px;">
            <tr>
                <td style="width:40%; vertical-align:top;">
                    <fieldset class="imFieldset">
                        <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.015'))}</legend>
                        <sx:div  id="inputGoods" cssStyle="width:100%; height:380px; vertical-align:top">
                            <jsp:include page="StockGoodsSaleMasterAgent.jsp"/>
                        </sx:div>
                    </fieldset>
                </td>
                <td style="width:2px; "></td>
                <td style="vertical-align:top">
                    <fieldset class="imFieldset">
                        <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.035'))}</legend>
                        <sx:div id="listGoods" cssStyle="width:100%; height:380px; vertical-align:top">
                            <jsp:include page="ListSaleToMasterAgentModelResult.jsp">
                                <jsp:param name="editable" value="true"/>
                            </jsp:include>
                        </sx:div>
                    </fieldset>
                </td>
            </tr>
        </table>

        <div style="margin-top:3px;">
            <tags:submit confirm="true" confirmText="MSG.SAE.204"
                         formId="saleToCollaboratorForm"
                         targets="bodyContent"
                         validateFunction="checkBeforeSave()" showLoadingText="true"
                         value="MSG.SAE.064" preAction="saleToMasterAgentAction!saveSaleToMasterAgent.do"/>
            <s:if test="#request.PrinCmdExport==1">
                <tags:submit formId="saleToCollaboratorForm" showLoadingText="true" 
                             targets="bodyContent" value="MSG.printExportCmd"
                             preAction="saleToMasterAgentAction!printExpCmd.do" 
                             cssStyle="width: 150px;"/>
            </s:if>
            <s:property escapeJavaScript="true"  value="saleToCollaboratorForm.reSult"/>
        </div>
        <div>
            <s:if test="#request.printExpPath != null">
                <script>
                    window.open('${contextPath}/download.do?${fn:escapeXml(printExpPath)}','','toolbar=yes,scrollbars=yes');
                </script>
                <a href="${contextPath}/download.do?${fn:escapeXml(printExpPath)}">
                    <tags:displayResult id="printExpMessage" property="printExpMessage" type="key"/>
                </a>
            </s:if>
        </div>

    </s:form>
</tags:imPanel>

