<%-- 
    Document   : SaleAnypayToRetail
    Created on : Jun 7, 2012, 3:07:01 PM
    Author     : thaiph
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="java.util.*"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
    request.setAttribute("contextPath", request.getContextPath());
%>


<s:form action="saleRetailAnypayAction" theme="simple" method="post" id="saleRetailAnypayForm">
<s:token/>
    <tags:imPanel title="saleRetailAnypay">
            <!--LinhNBV start modified on May 24 2018: Check limit valid or invalid.-->
    <s:if test="#session.isOverLimit == 0">
        <div  align="center" class="txtError">
            <s:text name="ERR.SAE.158"></s:text>
        </div>
    </s:if>
    <s:else>
        <table class="inputTbl2Col">
            <tr>
                <td class ="label"><tags:label key="MSG.generates.unit_code" required="true" /> </td>
                <td colspan="1" class="text">
                    <tags:imSearch codeVariable="saleRetailAnypayForm.shopCode" nameVariable="saleRetailAnypayForm.shopName"
                                   codeLabel="MSG.RET.032" nameLabel="MSG.RET.033"
                                   readOnly="true"
                                   roleList=""/>
                </td>
                <s:hidden name="saleRetailAnypayForm.subId" />
            </tr>


            <tr>
                <td class="label"><tags:label key="MSG.RET.047" required="true"/></td>
                <td colspan="1" class="text">
                    <tags:imSearch codeVariable="saleRetailAnypayForm.staffCode" nameVariable="saleRetailAnypayForm.staffName"
                                   codeLabel="MSG.RET.032" nameLabel="MSG.RET.033"
                                   readOnly="true"
                                   roleList=""/>
                </td>
            </tr>

            <tr>
                <td  class="label"><tags:label key="MSG.isdn" required="true" /></td>
                <td class="text"><s:textfield maxlength="20" cssClass="txtInputFull" id="saleRetailAnypayForm.isdn" name="saleRetailAnypayForm.isdn" /></td>
            </tr>

            <tr>
                <td colspan="4" align="center">
                    <tags:submit formId="saleRetailAnypayForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="bodyContent"
                                 validateFunction = "checkValidFields()"
                                 value="MSG.detail"
                                 preAction="saleRetailAnypayAction!getDetail.do"/>
                </td>
            </tr>
            <tr>
                <td colspan="4" align="center">
                    <tags:displayResult id="displayResultMsgClient" property="displayResultMsgClient" propertyValue="returnMsgValue" type="key"/>
                </td>                
            </tr>
            <tr>

            </tr>
            <tr>
                <td colspan="4" align="center">
                    <s:if test="#request.filePath !=null && #request.filePath!=''">
                        <script>
                            <%--window.open('${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');--%>
                                window.open('<s:property escapeJavaScript="true"  value="#request.filePath"/>','','toolbar=yes,scrollbars=yes');
                        </script>
                        <div>
                            <%--<a href='${contextPath}<s:property escapeJavaScript="true"  value="#request.filePath"/>' >Bấm vào đây để download nếu trình duyệt không tự động download về.</a>--%>
                            <a href='<s:property escapeJavaScript="true"  value="#request.filePath"/>' ><tags:label key="MSG.clickhere.to.download" required="false" /></a>
                        </div>
                    </s:if>
                </td>
            </tr>
        </table>
        <div id="detailOfIsdn" style="width:80%;">
            <s:if test="saleRetailAnypayForm.subId != null">
                <!--jsp:include page="DetailOfISDN.jsp"/-->
                <table class="inputTbl2Col">
                    <tr>
                        <td align="center"><tags:label key="MSG.cusName" required="false" /> </td>
                        <td class="text"><s:textfield maxlength="50" cssClass="txtInputFull" id="saleRetailAnypayForm.cusName" 
                                     name="saleRetailAnypayForm.cusName" readonly="true"/></td>
                    </tr>
                    <tr>
                        <td align="center"><tags:label key="MSG.FAC.Card.NotvalidDate" required="false" /> </td>
                        <td class="text">
                            <tags:dateChooser property="saleRetailAnypayForm.expiryDate"  id="saleRetailAnypayForm.expiryDate" styleClass="txtInput"   readOnly="true" insertFrame="false"/>
                        </td>
                </table>
                <table class="inputTbl2Col">
                    <tr>
                        <td align="center">
                            <div style="width:100%;" >
                                <display:table name="listAccount" id="tbllistAccount"
                                               class="dataTable" cellpadding="1" cellspacing="1">
                                    <display:column escapeXml="true"  property="balanceType" title="${fn:escapeXml(imDef:imGetText('MSG.account.type'))}" style="text-align:center" sortable="false" headerClass="tct"/>
                                    <display:column property="balance" format="{0,number,#,###.00}" title="${fn:escapeXml(imDef:imGetText('MSG.SIK.079'))}" style="text-align:right" sortable="false" headerClass="tct"/>
                                </display:table>
                            </div>
                        </td>
                    </tr>
                </table>
                <table class="inputTbl4Col">
                    <tr>
                        <td  class="label"><tags:label key="MSG.SIK.134" required="true" /></td>

                        <td><tags:imSelect name="saleRetailAnypayForm.amountList"
                                       id="amountList"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="L.200015"
                                       list="listAmount"
                                       listKey="value" listValue="name"/>
                        </td>

                        <td  class="label"><%--tags:label key="L.200016"/--%>
                            <s:checkbox name="saleRetailAnypayForm.chk" id="saleRetailAnypayForm.chk" value="false"  onclick="selectAmountManual(this);"/>
                            <s:property escapeJavaScript="true"  value="getText('L.200016')"/>
                        </td>

                        <td class="text"><s:textfield maxlength="10" cssClass="txtInputFull" disabled="true"
                                     id="saleRetailAnypayForm.amountManual" name="saleRetailAnypayForm.amountManual" /></td>
                    </tr>

                    <tr>
                        <td colspan="4" align="center">
                            <s:if test="#attr.listButton == null">
                                <tags:submit formId="saleRetailAnypayForm"
                                             showLoadingText="true"
                                             cssStyle="width:120px;"
                                             targets="bodyContent"
                                             validateFunction = "checkValidFieldsToCreatTrans()"
                                             value="MSG.createTrans"
                                             confirm="true" confirmText="MSG.confirm.create.trans"
                                             preAction="saleRetailAnypayAction!createTrans.do"/>
                            </td>
                        </s:if><s:else>
                            <tags:submit formId="saleRetailAnypayForm"
                                         showLoadingText="true"
                                         cssStyle="width:120px;"
                                         disabled="true"
                                         targets="bodyContent"
                                         validateFunction = "checkValidFieldsToCreatTrans()"
                                         value="MSG.createTrans"
                                         confirm="true" confirmText="MSG.confirm.create.trans"
                                         preAction="saleRetailAnypayAction!createTrans.do"/>
                            </td>
                        </s:else>
                    </tr>
                </table>



            </s:if>
        </div>
      </s:else>          
    </tags:imPanel>
</s:form>
<script language="javascript">
    selectAmountManual = function(value){
        $('saleRetailAnypayForm.amountManual').disabled=!value.checked;
        $('amountList').disabled=value.checked;
    }
    
    checkValidFieldsToCreatTrans = function() {
        var chk= document.getElementById("saleRetailAnypayForm.chk");
        if (chk.checked){
            var amountManual= document.getElementById("saleRetailAnypayForm.amountManual");
            if(amountManual.value == "" ) {
                $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.200027')"/>';
                amountManual.focus();
                return false;
            }
            if (!isInteger(amountManual.value)){
                $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.200028')"/>';
                amountManual.focus();
                return false;
            }
            if (parseInt(amountManual.value)<=0){
                $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.SIK.058')"/>';
                amountManual.focus();
                return false;
            }
        }else{
            var amountManual= document.getElementById("amountList");
            if(amountManual.value == "" ) {
                $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.200027')"/>';
                amountManual.focus();
                return false;
            }
            if (!isInteger(amountManual.value)){
                $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('E.200028')"/>';
                amountManual.focus();
                return false;
            }
            
        }
        $( 'displayResultMsgClient' ).innerHTML = '';
        return true;
    }


    checkValidFields = function() {

        var isdn= document.getElementById("saleRetailAnypayForm.isdn");
        if(isdn.value==""){
            //chua chon KitMovitel
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.ISN.141')"/>';
            isdn.focus();
            return false;
        }
        if (!isInteger(isdn.value)){
            $( 'displayResultMsgClient' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.ISN.122')"/>';
            isdn.focus();
            return false;
        }
        $( 'displayResultMsgClient' ).innerHTML = '';
        return true;
    }
    
    isNumberKey = function (evt)
    {
        var charCode = (evt.which) ? evt.which : event.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57))
            return false;

        return true;
    }

        

</script>

