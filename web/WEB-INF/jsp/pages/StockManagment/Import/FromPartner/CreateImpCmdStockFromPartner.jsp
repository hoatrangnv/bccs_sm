<%-- 
    Document   : CreateImpCmdStockFromPartner
    Created on : Jun 25, 2014, 2:20:54 PM
    Author     : thuannx1
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<%
            request.setAttribute("contextPath", request.getContextPath());

%>

<tags:imPanel title="MSG.GOD.126">
    <!-- phan hien thi thong tin message -->
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>


    <!-- phan hien thi thong tin mat hang can nhap kho -->
    <s:form action="" theme="simple" method="post" id="importStockForm">
        <s:token/>

        <s:hidden name="importStockForm.actionId"/>
        <s:hidden name="importStockForm.expSimActionId"/>
        <s:hidden name="importStockForm.expSimActionCode"/>

        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td > <tags:label key="MSG.toStore" required="true"/></td>
                    <td class="oddColumn" style="width: 25%">
                        <table>
                            <tr>
                                <td style="width: 30%">
                                    <s:textfield name="importStockForm.toOwnerCode" maxLength="25" readOnly="true" id="importStockForm.toOwnerCode" cssClass="txtInputFull" readonly="true"/>
                                </td>
                                <td style="width: 70%">
                                    <s:textfield name="importStockForm.toOwnerName" maxLength="25" readOnly="true" id="importStockForm.toOwnerName" cssClass="txtInputFull" readonly="true"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td > <tags:label key="MSG.GOD.127" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield name="importStockForm.actionCode" maxLength="25" readOnly="true" id="actionCode" cssClass="txtInputFull"/>
                    </td>
                    <td><tags:label key="MSG.importDate" required="true"/></td>
                    <td>
                        <tags:dateChooser property="importStockForm.importDate" id="importStockForm.importDate" styleClass="txtInputFull" readOnly="true"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.partner" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="importStockForm.partnerId"
                                       id="partnerId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.215"
                                       list="listPartners"
                                       listKey="partnerId" listValue="partnerName"/>
                    </td>
                    <td><tags:label key="MSG.GOD.140" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect theme="simple"  name="importStockForm.reasonId"
                                       id="reasonId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.147"
                                       list="lstReasonExp"
                                       listKey="reasonId" listValue="reasonName"/>
                    </td>
                    <td><tags:label key="MSG.comment" required="false"/></td>
                    <td class="oddColumn" rowspan="2" colspan="2">
                        <s:textarea theme="simple"  name="importStockForm.note" maxlength="500" id="importStockForm.note" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.stock.stock.type" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="importStockForm.stockTypeId"
                                       id="stockTypeId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.216"
                                       list="listStockTypes"
                                       listKey="stockTypeId" listValue="name"
                                       onchange="updateCombo('stockTypeId','stockModelId','${fn:escapeXml(requestScope.contextPath)}/CreateImpCmdStockFromPartnerAction!selectStockType.do')"/>
                    </td>
                    <td><tags:label key="MSG.quanlity" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield theme="simple"  name="importStockForm.quantity" maxlength="10" id="importStockForm.quantity" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.generates.goods" required="true"/></td>
                    <td class="oddColumn">
                        <%--<tags:imSelect name="importStockForm.stockModelId"
                                       id="stockModelId"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="MSG.GOD.217"
                                       list="listStockModel"
                                       listKey="stockModelId" listValue="name"/>--%>
                        <s:select name="importStockForm.stockModelId" id="stockModelId"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="%{getText('MSG.GOD.217')}"
                                  listKey="stockModelId" listValue="name"
                                  list="%{#request.listStockModel != null ? #request.listStockModel : #{} }"/>
                    </td>
                    <td><tags:label key="MSG.stateId" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="importStockForm.stateId"
                                       id="stateId"
                                       cssClass="txtInputFull"
                                       list="1:MSG.GOD.new, 3:MSG.GOD.error"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="lb.contract.code" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield theme="simple"  name="importStockForm.contractCode" maxlength="100" id="contractCode" cssClass="txtInputFull"/>
                    </td>
                    <td><tags:label key="lb.batch.code" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield theme="simple"  name="importStockForm.batchCode" maxlength="100" id="batchCode" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </div>
        <tags:submit formId="importStockForm"
                     showLoadingText="true"
                     cssStyle="width:100px;"
                     targets="bodyContent"
                     value="MSG.createImportCmd"
                     validateFunction="checkValidate()"
                     preAction="CreateImpCmdStockFromPartnerAction!createImpCmdStockFromPartner.do"/>
        <s:if test="#session.disablePrintImpCmdStockFromPartner == true">
            <tags:submit formId="importStockForm"
                         showLoadingText="true"
                         cssStyle="width:100px;"
                         targets="bodyContent"
                         value="MSG.GOD.232"
                         preAction="CreateImpCmdStockFromPartnerAction!printImpCmdStockFromPartner.do"
                         disabled="true"/>
        </s:if>
        <s:else>
            <tags:submit formId="importStockForm"
                         showLoadingText="true"
                         cssStyle="width:100px;"
                         targets="bodyContent"
                         value="MSG.GOD.232"
                         preAction="CreateImpCmdStockFromPartnerAction!printImpCmdStockFromPartner.do"
                         disabled="false"/>
        </s:else>
        <tags:submit formId="importStockForm"
                     showLoadingText="true"
                     cssStyle="width:100px;"
                     targets="bodyContent"
                     value="Reset"
                     preAction="CreateImpCmdStockFromPartnerAction!preparePage.do?isReset=true"/>

    </s:form>
    <!-- phan hien thi link download phieu nhap kho -->
    <div align="center">
        <s:if test="#request.notePrintPath !=null && #request.notePrintPath != ''">
            <script>
                window.open('${contextPath}/${fn:escapeXml(notePrintPath)}','','toolbar=yes,scrollbars=yes');
            </script>
            <div><a href='${contextPath}/${fn:escapeXml(notePrintPath)}' ><tags:label key="MSG.download2.file.here"/></a></div>
        </s:if>
    </div>
</tags:imPanel>
<script type="text/javascript">
    checkValidate = function(){
        if(trim($('partnerId').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.038"/>';
            $('partnerId').focus();
            return false;
        }
        if(trim($('reasonId').value) == "") {
            $('message').innerHTML= '<s:text name="stock.import.reason.not.choice"/>';
            $('reasonId').focus();
            return false;
        }
        if(trim($('stockTypeId').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.041"/>';
            $('stockTypeId').focus();
            return false;
        }
        if(trim($('importStockForm.quantity').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.SAE.051"/>';
            $('importStockForm.quantity').select();
            $('importStockForm.quantity').focus();
            return false;
        }
        var quantity = document.getElementById("importStockForm.quantity");
        quantity.value=trim(quantity.value);
        if(quantity == null || trim(quantity.value).length <= 0 ){
            $('message').innerHTML='<s:text name="MSG.SAE.029"/>';
            quantity.select();
            quantity.focus();
            return false;
        }
        if(!isInteger(trim(quantity.value)) || quantity. value.trim()==0){

            $('message').innerHTML='<s:text name="MSG.SAE.030"/>';
            quantity.select();
            quantity.focus();
            return false;
        }
        if(trim(quantity.value).length >10 ){
            $('message').innerHTML='<s:text name="MSG.SAE.031"/>';
            quantity.select();
            quantity.focus();
            return false;
        }
        if(trim($('stockModelId').value) == "") {
            $('message').innerHTML= '<s:text name="ERR.STK.040"/>';
            $('stockModelId').focus();
            return false;
        }
        if(trim($('stateId').value) == "") {
            $('message').innerHTML= '<s:text name="E.100009"/>';
            $('stateId').focus();
            return false;
        }
        if(trim($('importStockForm.note').value) != "" && isHtmlTagFormat(trim($('importStockForm.note').value))) {
            $('message').innerHTML = '<s:property value="getText('ERR.LST.102')" />';
            $('importStockForm.note').select();
            $('importStockForm.note').focus();
            return false;
        }
        if(trim($('importStockForm.note').value).length >200 ){
            $('message').innerHTML='<s:text name="ERR.SAE.022"/>';
            $('importStockForm.note').select();
            $('importStockForm.note').focus();
            return false;
        }
        if (trim($('contractCode').value) == "") {
            $('message').innerHTML = '<s:text name="err.contractCode.not.input"/>';
            $('contractCode').focus();
            return false;
        }
        if (trim($('batchCode').value) == "") {
            $('message').innerHTML = '<s:text name="err.batchCode.not.input"/>';
            $('batchCode').focus();
            return false;
        }
        $('message').innerHTML= '';
        return true;
    }
</script>