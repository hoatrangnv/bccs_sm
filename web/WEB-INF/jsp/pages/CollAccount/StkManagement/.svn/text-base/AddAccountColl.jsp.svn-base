<%-- 
    Document   : AddAccountColl
    Created on : Oct 10, 2009, 9:01:08 AM
    Author     : Vunt
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page  import="com.viettel.im.common.util.ResourceBundleUtils"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
            String pageId = request.getParameter("pageId");
            request.setAttribute("roleCreateAccountBalance", request.getSession().getAttribute("roleCreateAccountBalance" + pageId));
            request.setAttribute("roleUpdateAccountBalance", request.getSession().getAttribute("roleUpdateAccountBalance" + pageId));
            request.setAttribute("roleDestroyAccountBalance", request.getSession().getAttribute("roleDestroyAccountBalance" + pageId));
            request.setAttribute("roleAddMoneyAccount", request.getSession().getAttribute("roleAddMoneyAccount" + pageId));
            request.setAttribute("roleGetMoneyAccount", request.getSession().getAttribute("roleGetMoneyAccount" + pageId));
            request.setAttribute("roleBorrowCreditAccount", request.getSession().getAttribute("roleBorrowCreditAccount" + pageId));
            request.setAttribute("rolePaymentCreditAccount", request.getSession().getAttribute("rolePaymentCreditAccount" + pageId));
            request.setAttribute("Edit", request.getSession().getAttribute("Edit" + pageId));
            request.setAttribute("detroyAgent", request.getSession().getAttribute("detroyAgent" + pageId));
            request.setAttribute("status", request.getSession().getAttribute("status" + pageId));


%>
<c:set var="roleCreateAccountBalance" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleCreateAccountBalance, request))}" />
<c:set var="roleUpdateAccountBalance" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleUpdateAccountBalance, request))}" />
<c:set var="roleDestroyAccountBalance" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleDestroyAccountBalance, request))}" />

<c:set var="roleAddMoneyAccount" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleAddMoneyAccount, request))}" />
<c:set var="roleGetMoneyAccount" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleGetMoneyAccount, request))}" />

<c:set var="roleBorrowCreditAccount" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.roleBorrowCreditAccount, request))}" />
<c:set var="rolePaymentCreditAccount" scope="page" value="${fn:escapeXml(imDef:checkAuthority(requestScope.rolePaymentCreditAccount, request))}" />
<!--tags:imPanel-->
<tags:imPanel title="MSG.SIK.070">
    <s:hidden name="collAccountManagmentForm.accountId" id="collAccountManagmentForm.accountId"/>
    <div>
        <tags:displayResult id="displayResultMsgClientAccount" property="messageParam" propertyValue="paramMsg" type="key" />
    </div>
    <table class="inputTbl8Col" style="width:100%">
        <sx:div id="displayAccountCreate">
            <tr>
                <%--<td class="label">Người tạo</td>--%>
                <td class="label"><tags:label key="MSG.SIK.069"/></td>
                <td class="text">
                    <s:textfield name="collAccountManagmentForm.userNameCreate" id="userNameCreate" theme="simple" maxlength="100" cssClass="txtInputFull"   readonly="true"/>
                </td>
                <%--<td class="label">Ngày tạo</td>--%>
                <td class="label"><tags:label key="MSG.SIK.071"/></td>
                <td class="text">
                    <tags:dateChooser property="collAccountManagmentForm.createDate"  id="createDate"  styleClass="txtInput"  insertFrame="false"
                                      readOnly="true"/>
                </td>
                <%--<td class="label">Ngày bắt đầu (<font color="red">*</font>)</td>--%>
                <td class="label"><tags:label key="MSG.SIK.072" required="true"/></td>
                <td class="text">
                    <tags:dateChooser property="collAccountManagmentForm.startDate"  id="startDate" styleClass="txtInput"  insertFrame="false"
                                      readOnly="${fn:escapeXml(requestScope.Edit)}"  />
                </td>
                <%--<td class="label">Ngày kết thúc</td>--%>
                <td class="label"><tags:label key="MSG.SIK.073"/></td>
                <td class="text">
                    <tags:dateChooser property="collAccountManagmentForm.endDate"  id="endDate" styleClass="txtInput"  insertFrame="false"
                                      readOnly="${fn:escapeXml(requestScope.Edit)}"/>
                </td>

            </tr>
            <tr>
                <%--<td class="label">Trạng thái TK (<font color="red">*</font>)</td>--%>
                <td class="label"><tags:label key="MSG.SIK.074" required="true"/></td>
                <td class="text">
                    <s:if test="#request.detroyAgent == 1">
       
                        <tags:imSelect name="collAccountManagmentForm.accountStatusAdd" id="accountStatusAdd"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       disabled="true"
                                       headerKey="" headerValue="MSG.SIK.252"
                                       list="0:Not Activated,1:Active,2:Locked,3:Canncelled"
                                       />
                    </s:if>
                    <s:else>
                        <s:if test="#request.status == 3">

                            <tags:imSelect name="collAccountManagmentForm.accountStatusAdd" id="accountStatusAdd"
                                           cssClass="txtInputFull"
                                           theme="simple"
                                           disabled="${fn:escapeXml(requestScope.Edit)}"
                                           headerKey="" headerValue="-- Select status --"
                                           list="1:Active,2:Locked"
                                           />

                        </s:if>
                        <s:else>
       
                            <tags:imSelect name="collAccountManagmentForm.accountStatusAdd" id="accountStatusAdd"
                                           cssClass="txtInputFull"
                                           theme="simple"
                                           disabled="${fn:escapeXml(requestScope.Edit)}"
                                           headerKey="" headerValue="-- Select status --"
                                           list="0:Not Activated,1:Active"
                                           />
                        </s:else>
                    </s:else>
                </td>
                <%--<td class="label">Số dư tài khoản (<font color="red">*</font>)</td>--%>
                <td class="label"><tags:label key="MSG.SIK.079" required="true"/></td>
                <td class="text">
                    <s:if test="#request.status == 3" >
                        <s:textfield name="collAccountManagmentForm.moneyAmount" id="moneyAmount" cssStyle ="text-align:right" theme="simple" maxlength="10"  cssClass="txtInputFull"
                                     readonly = "true" />
                    </s:if>
                    <s:else>
                        <%--<s:textfield name="collAccountManagmentForm.moneyAmount" id="moneyAmount" onkeyup="textFieldNF(this)" cssStyle ="text-align:right" theme="simple" maxlength="10"  cssClass="txtInputFull"
                                     readonly = "#session.Edit" />--%>
                        <s:textfield name="collAccountManagmentForm.moneyAmount" id="moneyAmount"  cssStyle ="text-align:right" theme="simple" maxlength="10"  cssClass="txtInputFull"
                                     readonly = "true" />
                    </s:else>

                </td>
                <%--<td class="label">Số tiền tín dụng (<font color="red">*</font>)</td>--%>
                <td class="label"><tags:label key="MSG.SIK.080" required="true"/></td>
                <td class="text">
  
                    <s:textfield name="collAccountManagmentForm.realDebit" id="realDebit" onkeyup="textFieldNF(this)" cssStyle ="text-align:right" theme="simple" maxlength="10"  cssClass="txtInputFull"
                                 readonly = "true" />
                </td>
            </tr>

        </sx:div>
    </table>
    <div style="height:10px">
    </div>
    <div align="center" style="padding-bottom:5px;">
        <s:if test="#request.detroyAgent == 1">
        </s:if>
        <s:else>
            <s:if test="#request.status == 1">
    
                <tags:submit targets="accountColl" formId="collAccountManagmentForm"
                             showLoadingText="true" cssStyle="width:80px;"
                             confirm="true" confirmText="MSG.SIK.082"
                             value="MSG.SIK.081"
                             disabled="${fn:escapeXml(!pageScope.roleCreateAccountBalance)}"
                             validateFunction="validate()"
                             preAction="CollAccountManagmentAction!addAccountColl.do"
                             />
            </s:if>
            <s:else>
                <s:if test="#request.status == 2">
   
                    <tags:submit targets="accountColl" formId="collAccountManagmentForm"
                                 showLoadingText="true" cssStyle="width:80px;"
                                 confirm="true" confirmText="MSG.SIK.083"
                                 value="MSG.SIK.053"
                                 preAction="CollAccountManagmentAction!activeAccountColl.do"
                                 />
                </s:if>
            </s:else>
            <s:else>
                <s:if test="#request.status == 3">
     
                    <tags:submit targets="accountColl" formId="collAccountManagmentForm"
                                 showLoadingText="true" cssStyle="width:80px;"
                                 confirm="true" confirmText="MSG.SIK.084"
                                 value="MSG.SIK.009"
                                 disabled="${fn:escapeXml(!pageScope.roleUpdateAccountBalance)}"
                                 validateFunction="validateUpdate()"
                                 preAction="CollAccountManagmentAction!editAccountColl.do"
                                 />
       
                    <tags:submit targets="accountColl" formId="collAccountManagmentForm"
                                 showLoadingText="true" cssStyle="width:90px;"
                                 confirm="true" confirmText="MSG.SIK.085"
                                 disabled="${fn:escapeXml(!pageScope.roleDestroyAccountBalance)}"
                                 value="MSG.SIK.010"
                                 preAction="CollAccountManagmentAction!destroyAccountColl.do"
                                 />               

                    <%--<s:if test = "{#request.CollAccountManagmentForm.accountStatusAdd  == 2 || #request.CollAccountManagmentForm.accountStatusAdd  == 3}">--%>
                    <s:if test = "%{#request.CollAccountManagmentForm.accountStatusAdd  == 2 || #request.CollAccountManagmentForm.accountStatusAdd  == 3}">
                        <%--s:property value="%{CollAccountManagmentForm.realDebit}"/--%>
                        <input type="button" value="Nạp tiền" style="width:80px;" disabled >
                        <input type="button" value="Rút tiền" style="width:80px;" disabled >
                        <s:if test = "#attr.rolePaymentCreditAccount">                            
                            <s:if test="%{CollAccountManagmentForm.realDebit <= 0}">
                                <%--<input type="button" value="Giảm tín dụng" style="width:100px;" onclick="preparePaymentCredit()" disabled>--%>
                                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.086'))}" style="width:100px;" onclick="preparePaymentCredit()" disabled>
                            </s:if>
                            <s:else>
                                <%--<input type="button" value="Giảm tín dụng" style="width:100px;" onclick="preparePaymentCredit()" >--%>
                                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.086'))}" style="width:100px;" onclick="preparePaymentCredit()" >
                            </s:else>
                        </s:if>
                        <s:else>
                            <%--<input type="button" value="Giảm tín dụng" style="width:100px;" disabled >--%>
                            <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.086'))}" style="width:100px;" disabled >
                        </s:else>
                        <%--<input type="button" value="Khởi tạo tín dụng" style="width:100px;" disabled >--%>
                        <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.087'))}" style="width:100px;" disabled >
                    </s:if>
                    <s:else>
                        <s:if test = "#attr.roleAddMoneyAccount">
                            <%--<input type="button" value="Nạp tiền" style="width:80px;" onclick="prepareAddMoney()" >--%>
                            <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.088'))}" style="width:80px;" onclick="prepareAddMoney()" >
                        </s:if>
                        <s:else>
                            <%--<input type="button" value="Nạp tiền" style="width:80px;" disabled >--%>
                            <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.088'))}" style="width:80px;" disabled >
                        </s:else>
                        <s:if test = "#attr.roleGetMoneyAccount">
                            <%--<input type="button" value="Rút tiền" style="width:80px;" onclick="prepareGetMoney()" >--%>
                            <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.089'))}" style="width:80px;" onclick="prepareGetMoney()" >
                        </s:if>
                        <s:else>
                            <%--<input type="button" value="Rút tiền" style="width:80px;" disabled>--%>
                            <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.089'))}" style="width:80px;" disabled>
                        </s:else>

                        <s:if test = "#attr.rolePaymentCreditAccount">
                            <s:if test="%{CollAccountManagmentForm.realDebit != 'null' && CollAccountManagmentForm.realDebit > 0}">
                                <%--<input type="button" value="Giảm tín dụng" style="width:100px;" onclick="preparePaymentCredit()">--%>
                                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.086'))}" style="width:100px;" onclick="preparePaymentCredit()">
                            </s:if>
                            <s:else>
                                <%--<input type="button" value="Giảm tín dụng" style="width:100px;" onclick="preparePaymentCredit()" disabled >--%>
                                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.086'))}" style="width:100px;" onclick="preparePaymentCredit()" disabled >
                            </s:else>
                        </s:if>
                        <s:else>
                            <%--<input type="button" value="Giảm tín dụng" style="width:100px;" disabled >--%>
                            <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.086'))}" style="width:100px;" disabled >
                        </s:else>
                        <s:if test = "#attr.roleBorrowCreditAccount">
                            <s:if test = "%{CollAccountManagmentForm.maxCreditNum !=null && CollAccountManagmentForm.maxCreditNum >= 1}">
                                <%--<input type="button" value="Khởi tạo tín dụng" style="width:100px;" onclick="prepareBorrowCredit()" >--%>
                                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.087'))}" style="width:100px;" onclick="prepareBorrowCredit()" >
                            </s:if>
                            <s:else>
                                <%--<input type="button" value="Khởi tạo tín dụng" style="width:100px;" onclick="prepareBorrowCredit()" disabled>--%>
                                <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.087'))}" style="width:100px;" onclick="prepareBorrowCredit()" disabled>
                            </s:else>                            
                        </s:if>
                        <s:else>
                            <%--<input type="button" value="Khởi tạo tín dụng" style="width:100px;" disabled >--%>
                            <input type="button" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.087'))}" style="width:100px;" disabled >
                        </s:else>

                    </s:else>                        
                    <s:url action="CollAccountManagmentAction!prepareViewAccountDetail" id="URLViewAccount" encode="true" escapeAmp="false">
                        <s:param name="accountId" value="collAccountManagmentForm.accountIdAgent"/>
                    </s:url>
                    <%--<a id="hrefViewStockDetail" href="javascript:viewAccountDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewAccount"/>')"> Xem giao dịch</a>--%>
                    <input type="button" id="hrefViewStockDetail" onclick="viewAccountDetail('<s:property escapeJavaScript="true"  value="#attr.URLViewAccount"/>')" value="${fn:escapeXml(imDef:imGetText('MSG.SIK.090'))}"/>
                </s:if>
            </s:else>
        </s:else>

    </div>

    <!--/tags:imPanel-->
</tags:imPanel>

<script type="text/javascript" language="javascript">
    //var fieldName=document.getElementById('staffCreate');
    //fieldName.focus();
    textFieldNF($('moneyAmount'));
    textFieldNF($('realDebit'));
    validate = function(){
        $('displayResultMsgClientAccount').innerHTML="";
        var dateExported= dojo.widget.byId("startDate");
        if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClientAccount' ).innerHTML='Chưa nhập ngày bắt đầu';--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.024')"/>';
                $('startDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClientAccount' ).innerHTML='Ngày bắt đầu không hợp lệ';--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.025')"/>';
                $('startDate').focus();
                return false;
            }

            var dateExported1 = dojo.widget.byId("endDate");
            if(dateExported1.domNode.childNodes[1].value != "" && !isDateFormat(dateExported1.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClientAccount' ).innerHTML='Ngày báo cáo đến không hợp lệ';--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.026')"/>';
                $('endDate').focus();
                return false;
            }
            if(dateExported1.domNode.childNodes[1].value.trim() != "" && !isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClientAccount' ).innerHTML='Ngày bắt đầu phải nhỏ hơn ngày kết thúc';--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.027')"/>';
                $('startDate').focus();
                return false;
            }

            var accountStatusAdd=document.getElementById('accountStatusAdd');
            if (accountStatusAdd.value ==""){
    <%--$('displayResultMsgClientAccount' ).innerHTML='Chưa chọn trạng thái tài khoản';--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.001')"/>';
                $('accountStatusAdd').focus();
                return false;
            }
            //var moneyAmount=document.getElementById('moneyAmount');
            //var realDebit=document.getElementById('realDebit');
            var moneyAmount = $('moneyAmount').value.replace(/\,/g,""); //loai bo dau , trong chuoi
            var realDebit = $('realDebit').value.replace(/\,/g,""); //loai bo dau , trong chuoi
            if(trim(moneyAmount) == ""){
    <%--$('displayResultMsgClientAccount').innerHTML="Chưa nhập số tiền khởi tạo"--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.028')"/>';
                $('moneyAmount').focus();
                return false;
            }
            if( trim(moneyAmount) != "0" && !isInteger( trim(moneyAmount) ) ){
    <%--$('displayResultMsgClientAccount').innerHTML="Số tiền khởi tạo phải là số nguyên dương"--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.029')"/>';
                $('moneyAmount').focus();
                return false;
            }
            if(trim(realDebit) =="" ){
    <%--$('displayResultMsgClientAccount').innerHTML="Chưa nhập số tiền tín dụng"--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.030')"/>';
                $('realDebit').focus();
                return false;
            }
            if(trim(realDebit) != "0" && !isInteger( trim(realDebit) ) ){
    <%--$('displayResultMsgClientAccount').innerHTML="Số tiền tín dụng phải là số nguyên dương"--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.031')"/>';
                $('realDebit').focus();
                return false;
            }

              //
              $('moneyAmount').value = $('moneyAmount').value.replace(/\,/g,""); //loai bo dau , trong chuoi
              $('realDebit').value = $('realDebit').value.replace(/\,/g,""); //loai bo dau , trong chuoi

              return true;
          }
          validateUpdate = function(){
              $('displayResultMsgClientAccount').innerHTML="";
              var dateExported= dojo.widget.byId("startDate");
              if(dateExported.domNode.childNodes[1].value.trim() == ""){
    <%--$( 'displayResultMsgClientAccount' ).innerHTML='Chưa nhập ngày bắt đầu';--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.024')"/>';
                $('startDate').focus();
                return false;
            }
            if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClientAccount' ).innerHTML='Ngày bắt đầu không hợp lệ';--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.025')"/>';
                $('startDate').focus();
                return false;
            }

            var dateExported1 = dojo.widget.byId("endDate");
            if(dateExported1.domNode.childNodes[1].value != "" && !isDateFormat(dateExported1.domNode.childNodes[1].value)){
    <%--$( 'displayResultMsgClientAccount' ).innerHTML='Ngày báo cáo đến không hợp lệ';--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.026')"/>';
                $('endDate').focus();
                return false;
            }
            if(dateExported1.domNode.childNodes[1].value.trim() != "" && !isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
    <%--$( 'displayResultMsgClientAccount' ).innerHTML='Ngày bắt đầu phải nhỏ hơn ngày kết thúc';--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.027')"/>';
                $('startDate').focus();
                return false;
            }

            var accountStatusAdd=document.getElementById('accountStatusAdd');
            if (accountStatusAdd.value ==""){
    <%--$('displayResultMsgClientAccount' ).innerHTML='Chưa chọn trạng thái tài khoản';--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.001')"/>';
                $('accountStatusAdd').focus();
                return false;
            }
            //var moneyAmount=document.getElementById('moneyAmount');
            //var realDebit=document.getElementById('realDebit');
            var moneyAmount = $('moneyAmount').value.replace(/\,/g,""); //loai bo dau , trong chuoi
            var realDebit = $('realDebit').value.replace(/\,/g,""); //loai bo dau , trong chuoi
            if(trim(moneyAmount) == ""){
    <%--$('displayResultMsgClientAccount').innerHTML="Chưa nhập số tiền khởi tạo"--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.028')"/>';
                $('moneyAmount').focus();
                return false;
            }
            if(trim(moneyAmount) != "0" && !isInteger( trim(moneyAmount) ) ){
    <%--$('displayResultMsgClientAccount').innerHTML="Số tiền khởi tạo phải là số nguyên dương"--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.029')"/>';
                $('moneyAmount').focus();
                return false;
            }
            if(trim(realDebit) != "0" && !isInteger( trim(realDebit) ) ){
    <%--$('displayResultMsgClientAccount').innerHTML="Số tiền dư nợ phải là số nguyên dương"--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.030')"/>';
                $('realDebit').focus();
                return false;
            }
            if (moneyAmount*1 < realDebit*1 ){
    <%--$('displayResultMsgClientAccount').innerHTML="Số tiền khởi tạo phải lớn hơn số tiền dư nợ"--%>
                $('displayResultMsgClientAccount').innerHTML='<s:property escapeJavaScript="true"  value="getError('ERR.SIK.031')"/>';
                $('moneyAmount').focus();
                return false;
            }
            $('moneyAmount').value = $('moneyAmount').value.replace(/\,/g,""); //loai bo dau , trong chuoi
            $('realDebit').value = $('realDebit').value.replace(/\,/g,""); //loai bo dau , trong chuoi
            return true;
        }
        viewAccountDetail=function(path){
            openPopup(path, 900, 700);
        }

        dojo.event.topic.subscribe("CollAccountManagmentAction/addAccountColl", function(event, widget){
            if (!validate()) {
                event.cancel = true;
            }
            // TuTM1 04/03/2012 Fix ATTT CSRF
            widget.href = "CollAccountManagmentAction!addAccountColl.do" + token.getTokenParamString();

        });
        dojo.event.topic.subscribe("CollAccountManagmentAction/activeAccountColl", function(event, widget){
            if (!validate()) {
                event.cancel = true;
            }
            // TuTM1 04/03/2012 Fix ATTT CSRF
            widget.href = "CollAccountManagmentAction!activeAccountColl.do" + token.getTokenParamString();

        });
        dojo.event.topic.subscribe("CollAccountManagmentAction/editAccountColl", function(event, widget){
            if (!validateUpdate()) {
                event.cancel = true;
            }
            // TuTM1 04/03/2012 Fix ATTT CSRF
            widget.href = "CollAccountManagmentAction!editAccountColl.do" + token.getTokenParamString();

        });

        prepareAddMoney = function() {
            var accountId=document.getElementById('collAccountManagmentForm.accountIdAgent');
            var strForm = getFormAsString('collAccountManagmentForm');
            //alert(strForm);
            //+ "&" + strForm
            //alert(accountId.value);
            // TuTM1 04/03/2012 Fix ATTT CSRF
            openDialog("${contextPath}/CollAccountManagmentAction!showFormAddMoneyToAccountBalance.do?accountId=" 
                + accountId.value + "&" + token.getTokenParamString(), true, true, false);
            var myWindow;
            if(!myWindow || myWindow.closed){
                //alert("refresh");
                // TuTM1 04/03/2012 Fix ATTT CSRF
                gotoAction("accountColl",'CollAccountManagmentAction!refreshParent.do?accountId=' 
                    + accountId.value + "&" + token.getTokenParamString());
            }
        }


        // LamNV ADD START 02-04-2010
        preparePaymentCredit = function() {
            var accountId=document.getElementById('collAccountManagmentForm.accountIdAgent');
            var strForm = getFormAsString('collAccountManagmentForm');
            //alert(strForm);
            //+ "&" + strForm
            //alert(accountId.value);
            // TuTM1 04/03/2012 Fix ATTT CSRF
            openDialog("${contextPath}/CollAccountManagmentAction!showFormPaymentCredit.do?accountId=" 
                + accountId.value + "&" + token.getTokenParamString(), true, true, false);
            var myWindow;
            if(!myWindow || myWindow.closed){
                //alert("refresh");
                // TuTM1 04/03/2012 Fix ATTT CSRF
                gotoAction("accountColl",'CollAccountManagmentAction!refreshParent.do?accountId=' 
                    + accountId.value + "&" + token.getTokenParamString());
            }
        }
        // LamNV ADD STOP 02-04-2010

        // TuanNV ADD START 03-04-2010
        prepareBorrowCredit = function() {
            var accountId=document.getElementById('collAccountManagmentForm.accountIdAgent');
            var strForm = getFormAsString('collAccountManagmentForm');
            // TuTM1 04/03/2012 Fix ATTT CSRF
            openDialog("${contextPath}/CollAccountManagmentAction!showFormBorrowCredit.do?accountId=" + accountId.value
             + "&" + token.getTokenParamString(), true, true, false);
            var myWindow;
            if(!myWindow || myWindow.closed){
                //alert("refresh");
                // TuTM1 04/03/2012 Fix ATTT CSRF
                gotoAction("accountColl",'CollAccountManagmentAction!refreshParent.do?accountId=' 
                    + accountId.value + "&" + token.getTokenParamString());
            }
        }
        // TuanNV ADD STOP 03-04-2010


        prepareGetMoney = function() {
            var accountId=document.getElementById('collAccountManagmentForm.accountIdAgent');
            var strForm = getFormAsString('collAccountManagmentForm');
            // TuTM1 04/03/2012 Fix ATTT CSRF
            openDialog("${contextPath}/CollAccountManagmentAction!showFormGetMoneyToAccountBalance.do?accountId=" + accountId.value, true, true, false);
            var myWindow;
            if(!myWindow || myWindow.closed){
                //alert("refresh");
                // TuTM1 04/03/2012 Fix ATTT CSRF
                gotoAction("accountColl",'CollAccountManagmentAction!refreshParent.do?accountId=' 
                    + accountId.value + "&" + token.getTokenParamString());
            }
        }
        updateParent = function(){
            var accountId=document.getElementById('collAccountManagmentForm.accountIdAgent');
            // TuTM1 04/03/2012 Fix ATTT CSRF
            gotoAction("accountColl",'CollAccountManagmentAction!refreshParent.do?accountId=' 
                + accountId.value + "&" + token.getTokenParamString());
        }
        


</script>
