<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : PackageGoodsNew
    Created on : Sep 27, 2010, 10:42:27 AM
    Author     : User
--%>

<%--
    Note: hien thi thong tin ve saleService
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
        request.setAttribute("contextPath", request.getContextPath());
        String pageId = request.getParameter("pageId");
        request.setAttribute("showButton", request.getSession().getAttribute("showButton" + pageId));
%>
<sx:div id="divListPackageGoods"  cssStyle="padding:3px;">
    <s:if test="#request.packageGoodsMode == 'prepareAddOrEdit'">
        <s:set var="readonly" value="false" scope="request"/>
    </s:if>
    <s:else>
        <s:set var="readonly" value="true" scope="request"/>
    </s:else>

    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult id="message" property="message" type="key"/>
    </div>

    <!-- phan hien thi thong tin ve dich vu -->
    <s:form theme="simple" action="packageGoodsNewAction.do" id="packageGoodsForm" method="post">
<s:token/>

        <s:hidden name="packageGoodsForm.packageGoodsId" id="packageGoodsForm.packageGoodsId"/>

        <table class="inputTbl4Col">
            <tr>
                <td class="label"><tags:label key="MSG.GOD.code" required="true"/></td>
                <td class="text">
                    <s:textfield name="packageGoodsForm.packageCode" id="packageGoodsForm.packageCode" maxlength="50" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <td class="label"><tags:label key="MSG.package.goods.name" required="true"/></td>
                <td class="text">
                    <s:textfield name="packageGoodsForm.packageName" id="packageGoodsForm.packageName" maxlength="100" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
            </tr>
            <tr>
                <td class="label" style="width:10%; "><tags:label key="MSG.fromDate"/></td>
                <td class="label" style="width:20%; " class="oddColumn">
                    <tags:dateChooser property="packageGoodsForm.fromDate" id ="fromDate" readOnly="${fn:escapeXml(requestScope.readonly)}"/>
                </td>
                <td class="label" style="width:10%; "><tags:label key="MSG.generates.to_date" /></td>
                <td class="oddColumn">
                    <tags:dateChooser property="packageGoodsForm.toDate" id ="toDate" readOnly="${fn:escapeXml(requestScope.readonly)}"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MES.CHL.060" required="${fn:escapeXml(!readonly)}"/></td>
                <td class="text">
                    <tags:imSelect name="packageGoodsForm.status" id="packageGoodsForm.status"
                                   cssClass="txtInputFull"
                                   disabled="true"
                                   headerKey="" headerValue="COM.CHL.012"
                                   list="1:MES.CHL.129,0:MES.CHL.130"
                                   />
                </td>
                <td class="label"><tags:label key="MSG.GOD.031"/></td>
                <td   class="text">
                    <s:textfield name="packageGoodsForm.decriptions" id="packageGoodsForm.decriptions" maxlength="450" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
            </tr>
        </table>
    </s:form>

    <!-- phan nut tac vu -->
    <s:if test="#request.readonly == false">
        <s:if test="#request.showButton == null || #request.showButton*1 != 0">
            <div align="center" style="vertical-align:top">
                <tags:submit formId="packageGoodsForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="divDisplayInfo"
                             value="MSG.accept"
                             validateFunction="validateBeforeUpdate()"
                             preAction="packageGoodsNewAction!addOrEditPackageGoods.do"/>
                <tags:submit formId="packageGoodsForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="divListPackageGoods"
                             value="MSG.cancel2"
                             preAction="packageGoodsNewAction!displayPackageGoodsCancel.do"/>
            </div>
            <script language="javascript">
                enableTab('sxdivListPackageGoods');
                disableTab('sxdivPackageGoodsModelList');
            </script>
        </s:if>
        <s:else>
            <script language="javascript">
                enableTab('sxdivListPackageGoods');
                enableTab('sxdivPackageGoodsModelList');
            </script>
        </s:else>

        <script language="javascript">
            validateBeforeUpdate =function(){
                //return true;
                return checkRequiredFields();
            }
            //ham kiem tra cac truong bat buoc
            checkRequiredFields = function() {
                //truong ma dich vu
                if(trim($('packageGoodsForm.packageCode').value) == "") {
                    $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.043')"/>';
                    $('packageGoodsForm.packageCode').select();
                    $('packageGoodsForm.packageCode').focus();
                    return false;
                }
                if (isHtmlTagFormat(trim($('packageGoodsForm.packageCode').value))){
                    $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.GOD.049')"/>';
                    $('packageGoodsForm.packageCode').select();
                    $('packageGoodsForm.packageCode').focus();
                    return false;
                }
                if(trim($('packageGoodsForm.packageName').value) == "") {
                    $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.044')"/>';
                    $('packageGoodsForm.packageName').select();
                    $('packageGoodsForm.packageName').focus();
                    return false;
                }
                if (isHtmlTagFormat(trim($('packageGoodsForm.packageName').value))){
                    $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.GOD.050')"/>';
                    $('packageGoodsForm.packageName').select();
                    $('packageGoodsForm.packageName').focus();
                    return false;
                }
                if (isHtmlTagFormat(trim($('packageGoodsForm.decriptions').value))){
                    $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.GOD.051')"/>';
                    $('packageGoodsForm.decriptions').select();
                    $('packageGoodsForm.decriptions').focus();
                    return false;
                }
                var dateExported= dojo.widget.byId("fromDate");
                if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
                    $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.GOD.057')"/>';
                    dateExported.domNode.childNodes[1].select();
                    dateExported.domNode.childNodes[1].focus();
                    return false;
                }
                if(dateExported.domNode.childNodes[1].value.trim() != "" && !isDateFormat(dateExported.domNode.childNodes[1].value)){
                    $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.GOD.045')"/>';
                    dateExported.domNode.childNodes[1].focus();
                    return false;
                }

                var dateExported1 = dojo.widget.byId("toDate");
                if(dateExported1.domNode.childNodes[1].value.trim() != "" && !isDateFormat(dateExported1.domNode.childNodes[1].value)){
                    $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.GOD.046')"/>';
                    dateExported1.domNode.childNodes[1].focus();
                    return false;
                }
                if(dateExported.domNode.childNodes[1].value.trim() != "" && dateExported1.domNode.childNodes[1].value.trim() != "" &&
                    !isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
                    $( 'message' ).innerHTML='<s:property escapeJavaScript="true"  value="getText('ERR.GOD.047')"/>';
                    dateExported.domNode.childNodes[1].focus();
                    return false;
                }
                return true;
            }

            //kiem tra ma khong duoc chua cac ky tu dac biet
            checkValidFields = function() {
                return true;
            }
        </script>
    </s:if>
    <s:else>
        <s:if test="#request.showButton == null || #request.showButton*1 != 0">
            <div align="center" style="vertical-align:top">

                <tags:submit formId="packageGoodsForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="divListPackageGoods"
                             value="MSG.add"
                             preAction="packageGoodsNewAction!prepareAddPackageGoods1.do"/>

                <s:if test="#attr.packageGoodsForm.packageGoodsId.compareTo(0L) > 0">

                    <tags:submit formId="packageGoodsForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="divListPackageGoods"
                                 value="MSG.SAE.033"
                                 preAction="packageGoodsNewAction!prepareEditPackageGoods.do"/>
                    <tags:submit formId="packageGoodsForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="divDisplayInfo"
                                 confirm="true"
                                 confirmText="MSG.GOD.333"
                                 value="MSG.GOD.021"
                                 preAction="packageGoodsNewAction!delPackageGoods.do"/>
                    <script language="javscript">
                        enableTab('sxdivPackageGoodsModelList');
                    </script>
                </s:if>
                <s:else>
                    <tags:submit formId="packageGoodsForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="divListPackageGoods"
                                 value="MSG.SAE.033"
                                 disabled="true"
                                 preAction="packageGoodsNewAction!prepareEditPackageGoods.do"/>
                    <tags:submit formId="packageGoodsForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="divListPackageGoods"
                                 disabled="true"
                                 value="MSG.GOD.021"
                                 preAction="packageGoodsNewAction!delPackageGoods.do"/>
                    <script language="javscript">
                        disableTab('sxdivPackageGoodsModelList');
                    </script>
                </s:else>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <%--<input type="button" style="width:80px;" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.290'))} " onclick="prepareAddPackageGoodsPrice()">
                <input type="button" style="width:80px;" value="${fn:escapeXml(imDef:imGetText('MSG.add.goods'))}" onclick="prepareAddPackageGoodsDetail()">--%>
            </div>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top">
                <tags:submit formId="packageGoodsForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="divListPackageGoods"
                             value="MSG.add"
                             preAction="packageGoodsNewAction!prepareAddPackageGoods1.do"/>
            </div>
            <script language="javscript">
                enableTab('sxdivListPackageGoods');
                enableTab('sxdivPackageGoodsModelList');
            </script>
        </s:else>
        <script language="javascript">
        </script>
    </s:else>
</sx:div>
