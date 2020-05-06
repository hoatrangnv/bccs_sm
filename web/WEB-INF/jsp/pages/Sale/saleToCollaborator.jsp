<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document    : saleForCollaborator
    Created on  : Mar 28, 2009, 2:36:44 PM
    Author      : tuannv
    Desc        : ban hang cho cong tac vien
    Modified    : tamdt1, 28/10/2009
--%>

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
<script type="text/javascript" language="javascript">
    checkBeforeSave = function() {
        if ($('saleToCollaboratorForm.agentCodeSearch').value.trim() == "") {
            $('saleToCollaboratorForm.agentCodeSearch').focus();
            $('saleToCollaboratorForm.agentCodeSearch').select();
            $('displayResultMsg').innerHTML = '<s:text name="MSG.SAE.073"/>';
            return false;
        }
//        if ($('saleTransForm.orderCode').value.trim() == "" && $('payMethodId').value.trim() == 2 ){
//            $('saleTransForm.orderCode').focus();
//            $('saleTransForm.orderCode').select();
//            $( 'displayResultMsg' ).innerHTML = '<s:text name="saleRetail.warn.orderCode"/>';
//            return false;
//        }
//        
//        if ($('saleTransForm.orderCode').value.trim() != "" && $('saleTransForm.orderCode').value.trim().length != 8  && $('payMethodId').value.trim() == 2 ){
//            $('saleTransForm.orderCode').focus();
//            $('saleTransForm.orderCode').select();
//            $( 'displayResultMsg' ).innerHTML = '<s:text name="saleRetail.warn.orderCodeLenght"/>';
//            return false;
//        }

        if ($('reasonId').value.trim() == "") {
            $('reasonId').focus();
            $('reasonId').select();
            $('displayResultMsg').innerHTML = '<s:text name="MSG.SAE.060"/>';
            return false;
        }

        if ($('payMethodId').value.trim() == "") {
            $('payMethodId').focus();
            $('payMethodId').select();
            $('displayResultMsg').innerHTML = '<s:text name="MSG.SAE.061"/>';
            return false;
        } else if ($('payMethodId').value.trim() == '99') {
            if ($('saleToCollaboratorForm.isdnWallet').value.trim() == "") {
                $('displayResultMsg').innerHTML = '<s:text name="input.isdn.EMola"/>';
                $('saleToCollaboratorForm.isdnWallet').select();
                $('saleToCollaboratorForm.isdnWallet').focus();
                return false;
            }
        }
        return true;
    }

    checkHideShowAccTrans = function() {
        var payMethodId = document.getElementById('payMethodId').value;
        if (payMethodId != 2) {
            document.getElementById('trOrderCode').style.display = 'none';
            document.getElementById('btCreateOrder').style.display = 'none';
            document.getElementById('btCreateTrans').style.display = '';
        }
        else {
            document.getElementById('trOrderCode').style.display = '';
            document.getElementById('btCreateOrder').style.display = '';
            document.getElementById('btCreateTrans').style.display = 'none';
        }
    }

    var payMethodId = document.getElementById('payMethodId').value;
    if (payMethodId != 2) {
        document.getElementById('trOrderCode').style.display = 'none';
        document.getElementById('btCreateOrder').style.display = 'none';
        document.getElementById('btCreateTrans').style.display = '';
    } else {
        document.getElementById('trOrderCode').style.display = '';
        document.getElementById('btCreateOrder').style.display = '';
        document.getElementById('btCreateTrans').style.display = 'none';
    }

</script>
<tags:imPanel title="MSG.SAE.065">
    <!--LinhNBV start modified on May 24 2018: Check limit valid or invalid.-->
    <s:if test="#session.isOverLimit == 0">
        <div  align="center" class="txtError">
            <s:text name="ERR.SAE.158"></s:text>
            </div>
    </s:if>
    <s:else>
        <s:form action="saleToCollaboratorAction" theme="simple" method="post" id="saleToCollaboratorForm">
            <s:token/>

            <s:hidden name="staff_export_channel" id="staff_export_channel" value="staff_export_channel"/>

            <!-- phan nhap thong tin chi tiet ve giao dich -->
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.002'))}</legend>
                <table class="inputTbl6Col">
                    <tr>
                        <td class="label"><tags:label key="MSG.SAE.066" required="true"/></td>
                        <td class="text">

                            <tags:imSelect name="saleToCollaboratorForm.agentTypeIdSearch"
                                           id="agentTypeIdSearch"
                                           cssClass="txtInputFull"
                                           headerKey="" headerValue="--Select Channel Type--"
                                           list="listChannelType"
                                           listKey="channelTypeId" listValue="name"/>

                            <!--                        <tags_:imSelect name="saleToCollaboratorForm.agentTypeIdSearch"
                                                                   id="agentTypeIdSearch"
                                                                   cssClass="txtInputFull" disabled="false" theme="simple"
                                                                   list="80043:MSG.SAE.068"
                                                                   headerKey="10" headerValue="MSG.SAE.069"/>-->
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.067" required="true"/></td>
                        <td colspan="5" class="text">
                            <tags:imSearch codeVariable="saleToCollaboratorForm.agentCodeSearch" nameVariable="saleToCollaboratorForm.agentNameSearch"
                                           codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                           searchClassName="com.viettel.im.database.DAO.SaleTransInvoiceDAO"
                                           searchMethodName="getListObjectByParentIdOrOwenerId"
                                           otherParam="agentTypeIdSearch"
                                           getNameMethod="getObjectName"/>
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
                                           list="1:MSG.SAE.071 ,2:MSG.SAE.072" 
                                           onchange="checkHideShowAccTrans();"/>
                        </td>
                        <td class="label"><tags:label key="MSG.SAE.008" required="true"/></td>
                        <td class="text">
                            <tags:dateChooser property="saleToCollaboratorForm.saleDate" styleClass="txtInput" readOnly="true"/>
                        </td>
                    </tr>
                    <tbody style="display:none" id="trOrderCode">
                    </tbody>

                </table>
            </fieldset>

            <!-- phan chiu tiet danh sach hang hoa co trong giao dich -->
            <table style="width:100%; border-spacing:0; margin-top:3px;">
                <tr>
                    <td style="width:40%; vertical-align:top;">
                        <fieldset class="imFieldset">
                            <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.015'))}</legend>
                            <sx:div  id="inputGoods" cssStyle="width:100%; height:380px; vertical-align:top">
                                <jsp:include page="StockGoodsCollaborator.jsp"/>
                            </sx:div>
                        </fieldset>
                    </td>
                    <td style="width:2px; "></td>
                    <td style="vertical-align:top">
                        <fieldset class="imFieldset">
                            <legend>${fn:escapeXml(imDef:imGetText('MSG.SAE.035'))}</legend>
                            <sx:div id="listGoods" cssStyle="width:100%; height:380px; vertical-align:top">
                                <jsp:include page="ListCollaboratorModelResult.jsp">
                                    <jsp:param name="editable" value="true"/>
                                    <jsp:param name="payMethodId" value="2"/>
                                </jsp:include>
                            </sx:div>
                        </fieldset>
                    </td>
                </tr>
            </table>

            <div id="btCreateTrans" style="margin-top:3px;">
                <tags:submit confirm="true" confirmText="MSG.SAE.204"
                             formId="saleToCollaboratorForm"
                             targets="bodyContent"
                             validateFunction="checkBeforeSave()" showLoadingText="true"
                             value="MSG.SAE.305" preAction="saleToCollaboratorAction!saveSaleToCollaborator.do"/>
                <s:property escapeJavaScript="true"  value="saleToCollaboratorForm.reSult"/>

            </div>

            <div id="btCreateOrder" style="margin-top:3px;display: none">
                <%--LinhNBV start modified on May 22 2018: Add tag if to check create order success -> downloadOrder--%>

                <tags:submit confirm="true" confirmText="MSG.SAE.314"
                             formId="saleToCollaboratorForm"
                             targets="bodyContent"
                             validateFunction="checkBeforeSave()" showLoadingText="true"
                             value="MSG.SAE.313" preAction="saleToCollaboratorAction!saveSaleToCollaborator.do"/>
                <s:property escapeJavaScript="true"  value="saleToCollaboratorForm.reSult"/>

                <s:if test="#session.downloadOrderForChannel == 1">
                    <s:url id="fileguide" namespace="/" action="saleToCollaboratorAction!myDownload" ></s:url>
                    <s:a href="%{fileguide}">Download Order</s:a>  
                </s:if>

            </div>
        </s:form>
    </s:else>

</tags:imPanel>




