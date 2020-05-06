<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : assignDebit
    Created on : Sep 6, 2010, 10:09:56 AM
    Author     : NamLT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.viettel.im.common.util.ResourceBundleUtils" %>
<%--<%@page import="com.guhesan.querycrypt.QueryCrypt" %>--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="imDef" uri="imDef" %>



<%
            request.setAttribute("contextPath", request.getContextPath());
            String pageId = request.getParameter("pageId");
            request.setAttribute("viewAllShopDebit", request.getSession().getAttribute("viewAllShopDebit" + pageId));
%>

<c:set var="viewAllShopDebit" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.viewAllShopDebit, request))}" />
<s:form action="" method="POST" id="debitForm" theme="simple">
<s:token/>

    <tags:imPanel title="MSG.debit.info">

        <div id="divMesage">
            <tags:displayResult property="message" id="message" type="key" propertyValue="messageParam"/>
        </div>

        <table class="inputTbl4Col" style="width:100%" border="0" cellpadding="0" cellspacing="4">

            <tr>
                <td class="label">
                    <tags:label key="MSG.parentCode"/>
                </td>

                <td class="text" >

                    <s:if test="#request.toAssignDebit != 1">
                        <tags:imSearch codeVariable="debitForm.parentCode"
                                       nameVariable="debitForm.parentName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"


                                       />
                    </s:if>
                    <s:else>
                        <tags:imSearch codeVariable="debitForm.parentCode"
                                       nameVariable="debitForm.parentName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName" readOnly="true" />
                    </s:else>


                </td>
                <td class="label">
                    <tags:label key="MSG.SAE.109"/>
                </td>



                <td class="text" >

                    <s:if test="#request.toAssignDebit != 1">
                        <tags:imSearch codeVariable="debitForm.shopCode"
                                       nameVariable="debitForm.shopName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.DebitDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"
                                       otherParam="debitForm.parentCode"

                                       />
                    </s:if>

                    <s:else>
                        <tags:imSearch codeVariable="debitForm.shopCode"
                                       nameVariable="debitForm.shopName"
                                       codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                       searchClassName="com.viettel.im.database.DAO.DebitDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"
                                       otherParam="debitForm.parentCode"
                                       readOnly="true"
                                       />
                    </s:else>




                </td>

            </tr>




            <tr>


                <td class="label">
                    <tags:label key="MSG.object.type"/>
                </td>
                <td class="text"  >

                    <tags:imSelect name="debitForm.ChannelParent"
                                   id="debitForm.ChannelParent"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.SIK.264"
                                   list="1:MSG.AllGrade, 2:MSG.Grade1,3:MSG.allStaff,4:MSG.StaffOfShop"
                                   />

                </td>

                <td class="label">
                    <tags:label key="MSG.object.code"/>
                </td>

                <td class="text">
                    <s:if test="#request.toAssignDebit != 1">

                        <s:textfield name="debitForm.code" id="debitForm.code"   maxlength="100" cssClass="txtInputFull"/>
                    </s:if>
                    <s:else>
                        <s:textfield name="debitForm.code" id="debitForm.code" readOnly="true"  maxlength="100" cssClass="txtInputFull"/>
                    </s:else>


                </td>
            </tr><%--
        </tr>--%>
            <tr>

                 <td class="label">
                   <tags:label key="MSG.chanel.type"/>
               </td>



                <td class="text" >
                      <s:if test="#request.toAssignDebit != 1">

                    <tags:imSearch codeVariable="debitForm.channelType"
                                   nameVariable="debitForm.ownerType"
                                   codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                   searchClassName="com.viettel.im.database.DAO.DebitDAO"
                                   searchMethodName="getListChannel"
                                   getNameMethod="getListChannelName"


                                   />
                      </s:if>
                    <s:else>
                        <tags:imSearch codeVariable="debitForm.channelType"
                                   nameVariable="debitForm.ownerType"
                                   codeLabel="MSG.RET.028" nameLabel="MSG.RET.029"
                                   searchClassName="com.viettel.im.database.DAO.DebitDAO"
                                   searchMethodName="getListChannel"
                                   getNameMethod="getListChannelName"
                                   readOnly="true" />
                    </s:else>


                </td>
                <td class="label">
                    <tags:label key="MSG.status"/>
                </td>

                <td  class="text" >

                    <tags:imSelect name="debitForm.status"
                                   id="debitForm.status"
                                   cssClass="txtInputFull"
                                   headerKey="" headerValue="MSG.RET.162"
                                   list="1:MSG.Assign, 2:MSG.UnAssign"
                                   />

                </td>

            </tr>

            <tr>
                <td class="label">
                    <tags:label  key="MSG.maxDebit"   />
                </td>
                <td class="text" >
                    <s:textfield name="debitForm.maxDebit" id="debitForm.maxDebit"  maxlength="19" cssClass="txtInputFull"/>
                </td>

                <td class="label"><tags:label key="MSG.reset.date" /></td>
                <td class="text">
                    <s:textfield name="debitForm.dateReset" id="debitForm.dateReset" maxlength="9" cssClass="txtInputFull"/>
                </td>
            </tr>







        </table>
        <br />
        <div align="center" style="width:100%; padding-bottom:5px;">

            <input type="hidden" value="${fn:escapeXml(stockId)}" name="debitForm.stockId" />
            <input type="hidden" value="${fn:escapeXml(toAssignDebit)}" id="toAssignDebit" />


            <tags:submit formId="debitForm"
                         showLoadingText="true"
                         cssStyle="width:100px;"
                         targets="bodyContent"
                         value="MSG.assignDebit"
                         validateFunction="checkValidate();"
                         confirm="true" confirmText="MSG.LST.043"
                         preAction="assignDebitAction!assignDebit.do" />

           <s:if test="#request.toAssignDebit != 1">

            <tags:submit formId="debitForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="divListRange"
                         value="MSG.find"
                         validateFunction="checkValidateSearch();"
                         preAction="assignDebitAction!listDebit.do"/>

         <%--   <tags:submit  formId="debitForm"
                          showLoadingText="true"
                          cssStyle="width:80px;"
                          targets="bodyContent"
                          value="MSG.delete" confirm="true" confirmText="MSG.ISN.061"
                          validateFunction="checkSelected(1);"
                          preAction="assignDebitAction!actionDeleteDebit.do"/>--%>

            <tags:submit formId="debitForm"
                         showLoadingText="true"
                         cssStyle="width:80px;"
                         targets="bodyContent"
                         value="MSG.report.export"
                         confirm="true" confirmText="MSG.ISN.033"
                         validateFunction="checkValidateSearch();"
                         preAction="assignDebitAction!actionExport2Excel.do"/>

           </s:if>


                <tags:submit formId="debitForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.cancel2"


                             preAction="assignDebitAction!resetForm.do"/>

           
        </tags:imPanel>
    </div>

    <s:if test="#request.pathExpFile!=null">
        <script>
            window.open('${contextPath}<s:property escapeJavaScript="true"  value="debitForm.pathExpFile"/>','','toolbar=yes,scrollbars=yes');
        </script>
        <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="debitForm.pathExpFile"/>' ><s:text name="MSG.GOD.272"/></a></div>
    </s:if>

    <br />
    <tags:imPanel title="Kết quả tìm kiếm hạn mức">

        <sx:div id="divListRange">
            <jsp:include page="assignDebitResult.jsp"/>
        </sx:div>
    </tags:imPanel>

    <%--    <s:if test="#session.listLookupIsdn != null && #session.listLookupIsdn.size() > 0">--%>

    <%--</s:if> --%>

    <br />

    <s:if test = "#attr.viewAllShopDebit">

        <tags:imPanel title="MSG.import.info.from.excel">

            <table class="inputTbl4Col" style="text-align:left; width:100%;">
                <tr>
                    <td class="label" align="left"> <tags:label  key="MSG.attachmentFile" required="true"  /></td>

                    <td class="text" colspan="2" align="left">
                        <tags:imFileUpload cssStyle="width:100%" name="debitForm.clientFileName" id="debitForm.clientFileName" serverFileName="debitForm.serverFileName" extension="xls;txt"/>
                        <!--s:file id="form.impFile" name="form.impFile" size="60"/-->
                    </td>
                    <td class="text" class="label" align="left">

                        <tags:submit formId="debitForm"
                                     showLoadingText="true"
                                     cssStyle="width:100px;"
                                     targets="bodyContent"
                                     value="MSG.import"
                                     confirm="true" confirmText="MSG.ISN.062"
                                     preAction="assignDebitAction!assginDebitByExcel.do"
                                     showProgressDiv="true"
                                     showProgressClass="com.viettel.im.database.DAO.DebitDAO"
                                     showProgressMethod="updateProgressDiv"
                                     />
                        <a onclick=downloadPatternFile()><s:text name="MSG.download.file.here"/></a>
                    </td>
                    <%--<td class="text"  align="left">

                </td>--%>
                </tr>



                <tr>
                    <td colspan="5" align="center">
                        <tags:displayResult id="resultAssignIsdnPriceClient" property="resultAssignIsdnPrice" type="key"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="5" align="center">

                        <s:if test="debitForm.pathLogFile!=null && debitForm.pathLogFile!=''">
                            <script>
                                window.open('${contextPath}<s:property escapeJavaScript="true"  value="debitForm.pathLogFile"/>','','toolbar=yes,scrollbars=yes');
                            </script>
                            <div><a href='${contextPath}<s:property escapeJavaScript="true"  value="debitForm.pathLogFile"/>' ><s:text name="MSG.clickhere.to.download"/>.</a></div>
                        </s:if>
                    </td>
                </tr>
            </table>
        </tags:imPanel>
    </s:if>


</s:form>
<script language="javascript" >

    if ($('toAssignDebit').value==1) {
        document.getElementById('checkAllId').checked=true;
        selectAll("checkAllId", "debitForm.selectedItems", "checkBoxId");
    }


    dojo.event.topic.subscribe("assignDebitAction/updateObjName", function(value, key, text, widget){
        if (key != undefined) {
            getInputText('${contextPath}/assignDebitAction!updateObjName.do?target=debitForm.name&code=' + key + '&'+  token.getTokenParamString());
        } else {
            $('debitForm.name').value = "";
        }

    });

    downloadPatternFile = function() {
        window.open('${contextPath}/share/pattern/assignDebitPattern.xls','','toolbar=yes,scrollbars=yes');
    }

    checkValidateSearch = function() {
        var result = checkRequireFieldSearch() && checkValidFields();
        return result;
    }


    checkValidate = function() {
        var result = checkRequiredFields() && checkValidFields() && checkSelected(2);
        return result;
    }

    checkValidateEdit = function() {
        var result = checkRequiredFields() && checkValidFields() ;
        return result;
    }

    checkRequireFieldSearch= function() {
        if(trim($('debitForm.parentCode').value) == "") {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.CHL.128')"/>';
            $('message').innerHTML = '<s:text name="ERR.CHL.128"/>';
            $('debitForm.parentCode').select();
            $('debitForm.parentCode').focus();
            return false;
        }
        return true;
    }

    checkRequiredFields = function() {
        //
        if(trim($('debitForm.maxDebit').value) == "") {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.112')"/>';
            $('message').innerHTML = '<s:text name="ERR.LST.112"/>';
            $('debitForm.maxDebit').select();
            $('debitForm.maxDebit').focus();
            return false;
        }

        if(trim($('debitForm.dateReset').value) == "") {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.113')"/>';
            $('message').innerHTML = '<s:text name="ERR.LST.113"/>';
            $('debitForm.dateReset').select();
            $('debitForm.dateReset').focus();
            return false;
        }

        if(trim($('debitForm.parentCode').value) == "") {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.CHL.128')"/>';
            $('message').innerHTML = '<s:text name="ERR.CHL.128"/>';
            $('debitForm.parentCode').select();
            $('debitForm.parentCode').focus();
            return false;
        }
        return true;
    }

    checkValidFields = function() {
        if((trim($('debitForm.maxDebit').value) != "") && (!isInteger(trim($('debitForm.maxDebit').value)))) {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.110')"/>';
            $('message').innerHTML = '<s:text name="ERR.LST.110"/>';
            $('debitForm.maxDebit').select();
            $('debitForm.maxDebit').focus();
            return false;
        }

        if((trim($('debitForm.dateReset').value)!= "" )&& (!isInteger(trim($('debitForm.dateReset').value)))) {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.111')"/>';
            $('message').innerHTML = '<s:text name="ERR.LST.111"/>';
            $('debitForm.dateReset').select();
            $('debitForm.dateReset').focus();
            return false;
        }

         if(trim($('debitForm.maxDebit').value).length >20) {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.118')"/>';
            $('message').innerHTML = '<s:text name="ERR.LST.118"/>';
            $('debitForm.maxDebit').select();
            $('debitForm.maxDebit').focus();
            return false;
        }

         if(trim($('debitForm.dateReset').value).length >20) {
//            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.LST.119')"/>';
            $('message').innerHTML = '<s:text name="ERR.LST.119"/>';
            $('debitForm.dateReset').select();
            $('debitForm.dateReset').focus();
            return false;
        }
        return true;
    }







</script>
