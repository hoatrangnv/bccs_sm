<%--
    Document   : imSearch
    Created on : Nov 17, 2009, 11:11:51 AM
    Author     : Doan Thanh 8
    Purpose    : tim kiem thong tin
--%>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="searchClassName" rtexprvalue="true"%>
<%@attribute name="searchMethodName" rtexprvalue="true"%>
<%@attribute name="codeVariable" rtexprvalue="true"%>
<%@attribute name="nameVariable" rtexprvalue="true"%>
<%@attribute name="codeLabel" rtexprvalue="true"%>
<%@attribute name="nameLabel" rtexprvalue="true"%>
<%@attribute name="otherParam" rtexprvalue="true"%>
<%@attribute name="elementNeedClearContent" rtexprvalue="true"%>
<%@attribute name="getNameMethod" rtexprvalue="true"%>
<%@attribute name="roleList" rtexprvalue="true"%>
<%@attribute name="readOnly" rtexprvalue="true"%>
<%@attribute name="postAction" rtexprvalue="true"%>
<!-- ThinDM R6762 -->
<%@attribute name="maxLength" rtexprvalue="true"%>
<%@attribute name="updateDataAfterSelect" rtexprvalue="true"%>
<%@attribute name="updateDataAfterSelectTrungdh3" rtexprvalue="true"%>
<%@attribute name="updateDataAfterSelectGotoTrungdh3" rtexprvalue="true"%>
<%@attribute name="target" rtexprvalue="true"%>
<%@attribute name="formId" rtexprvalue="true"%>
<%@attribute name="priceCost" rtexprvalue="flase"%>
<!-- End ThinDM R6762 -->
<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<%-- any content can be specified here e.g.: --%>

<s:hidden value="%{#attr.searchClassName}" id="%{#attr.codeVariable}_searchClassName"/>
<s:hidden value="%{#attr.searchMethodName}" id="%{#attr.codeVariable}_searchMethodName"/>
<s:hidden value="%{#attr.codeVariable}" id="%{#attr.codeVariable}_codeVariable"/>
<s:hidden value="%{#attr.nameVariable}" id="%{#attr.codeVariable}_nameVariable"/>
<s:hidden value="%{#attr.codeLabel}" id="%{#attr.codeVariable}_codeLabel"/>
<s:hidden value="%{#attr.nameLabel}" id="%{#attr.codeVariable}_nameLabel"/>
<s:hidden value="%{#attr.otherParam}" id="%{#attr.codeVariable}_otherParam"/>
<s:hidden value="%{#attr.elementNeedClearContent}" id="%{#attr.codeVariable}_elementNeedClearContent"/>
<s:hidden value="%{#attr.getNameMethod}" id="%{#attr.codeVariable}_getNameMethod"/>
<s:hidden value="%{#attr.roleList}" id="%{#attr.codeVariable}_roleList"/>
<s:hidden value="%{#attr.readOnly}" id="%{#attr.codeVariable}_readOnly"/>
<s:hidden value="%{#attr.postAction}" id="%{#attr.codeVariable}_postAction"/>
<!-- ThinDM R6762 -->
<s:hidden value="%{#attr.updateDataAfterSelectGotoTrungdh3}" id="%{#attr.codeVariable}_updateDataAfterSelectGotoTrungdh3"/>
<s:hidden value="%{#attr.target}" id="%{#attr.codeVariable}_target"/>
<s:hidden value="%{#attr.formId}" id="%{#attr.codeVariable}_formId"/>
<!-- ThinDM R6762 -->
<c:set var="hasRole" scope="page" value="false" />
<c:if test="${(pageScope['roleList'] == null) || (pageScope['roleList'] == '')}">
    <c:set var="hasRole" scope="page" value="true" />
</c:if>
<c:forEach var="role" items="${fn:split(pageScope['roleList'], ';')}">
    <c:set var="tmpHasRole" scope="page" value="${imDef:checkAuthority(role, request)}" />
    <c:set var="hasRole" scope="page" value="${hasRole || tmpHasRole}" />
</c:forEach>

<table style="width:100%; border-spacing:0px; ">
    <tr>
        <td style="width:30%; ">
            <s:textfield theme="simple" name="%{#attr.codeVariable}" id="%{#attr.codeVariable}" maxlength="50" cssClass="txtInputFull" readonly="%{#attr.readOnly}" disabled="%{!#attr.hasRole}"/>
            <!-- trong truong hop bi disable -> them 1 bien hidden de giu lai gia tri cua bien -->
            <s:if test="%{!#attr.hasRole}">
                <s:hidden name="%{#attr.codeVariable}" id="%{#attr.codeVariable}_hidden"/>
            </s:if>
        </td>
        <td>
            <s:textfield theme="simple" name="%{#attr.nameVariable}" id="%{#attr.nameVariable}" maxlength="100" cssClass="txtInputFull" readonly="true"/>
        </td>
    </tr>
</table>

<script language="javascript" type="text/javascript">
    //xoa du lieu cua cac phan tu lien quan trong truong hop code thay doi
    $('${fn:escapeXml(pageScope["codeVariable"])}').onchange = function() {
        //xoa name do code thay doi
        var nameVariable = $('${fn:escapeXml(pageScope["codeVariable"])}_nameVariable').value;
        $(nameVariable).value = "";

        //xoa du lieu cac phan tu lien quan
        var elementNeedClearContent = $('${fn:escapeXml(pageScope["codeVariable"])}_elementNeedClearContent').value;
        if(elementNeedClearContent != null && elementNeedClearContent != "") {
            var arrElementNeedClearContent = elementNeedClearContent.split(";");
            for (i = 0; i < arrElementNeedClearContent.length; i = i + 1){
                if($(arrElementNeedClearContent[i]) != undefined) {
                    $(arrElementNeedClearContent[i]).value = "";
                }
            }
        }

        //goi ham thuc hien sau khi chon code
        var postAction = $('${fn:escapeXml(pageScope["codeVariable"])}_postAction').value;
        if(postAction != null && postAction != "") {
            eval(postAction);
        }
    }

    //
    updateTagValue = function(codeElementId, nameElementId, code, name) {
        $(codeElementId).value = code;
        $(nameElementId).value = name;
        $(nameElementId).select();
        $(nameElementId).focus();

        //goi ham thuc hien sau khi chon code
        var postAction = $('${pageScope["codeVariable"]}_postAction').value;
        if(postAction != null && postAction != "") {
            eval(postAction);
        }
    }

    //
    $('${fn:escapeXml(pageScope["codeVariable"])}').onkeyup = function(e) {
        var readOnly = '${pageScope["readOnly"]}';
        var hasRole = '${fn:escapeXml(hasRole)}';
        var evt = (window.event) ? window.event : e;
        var keyId = evt.keyCode;
        
        //xu ly su kien doi voi nut F9
        if(hasRole == 'true' && !(readOnly == 'true') && keyId == 120)
         //if( keyID == 120
        {
            //F9
            try {
                var searchClassName = $('${fn:escapeXml(pageScope["codeVariable"])}_searchClassName').value;
                var searchMethodName = $('${fn:escapeXml(pageScope["codeVariable"])}_searchMethodName').value;
                var getNameMethod = $('${fn:escapeXml(pageScope["codeVariable"])}_getNameMethod').value;
                var codeVariable = $('${fn:escapeXml(pageScope["codeVariable"])}_codeVariable').value;
                var nameVariable = $('${fn:escapeXml(pageScope["codeVariable"])}_nameVariable').value;
                var codeLabel = $('${fn:escapeXml(pageScope["codeVariable"])}_codeLabel').value;
                var nameLabel = $('${fn:escapeXml(pageScope["codeVariable"])}_nameLabel').value;
                var code = $('${fn:escapeXml(pageScope["codeVariable"])}').value;
                var name = $('${fn:escapeXml(pageScope["nameVariable"])}').value;
                var otherParam = $('${fn:escapeXml(pageScope["codeVariable"])}_otherParam').value;
                var otherParamValue = "";
                if(otherParam != null && otherParam != "") {
                    var arrOtherParam = otherParam.split(";");
                    for (i = 0; i < arrOtherParam.length; i = i + 1){
                        otherParamValue = otherParamValue + ";" + $(arrOtherParam[i]).value;
                    }
                    otherParamValue = otherParamValue.substr(1); //loai bo dau ";" dau tien
                }
                openDialog('${contextPath}/ImSearchPopupAction!.do' +
                    '?searchClassName=' + searchClassName +
                    '&searchMethodName=' + searchMethodName +
                    '&getNameMethod=' + getNameMethod +
                    '&codeVariable=' + codeVariable +
                    '&nameVariable=' + nameVariable +
                    '&codeLabel=' + codeLabel +
                    '&nameLabel=' + nameLabel +
                    '&code=' + code +
                    '&otherParamValue=' + otherParamValue, 700, 750, true);
            }
            catch( e ) {
                alert(e.message);
            }
        }
    }
        $('${fn:escapeXml(pageScope["nameVariable"])}').onfocus = function() {
        //alert('1');
        var target = $('${fn:escapeXml(pageScope["codeVariable"])}_target').value;
        var formId = $('${fn:escapeXml(pageScope["codeVariable"])}_formId').value;



        
        var elementNeedClearContent = $('${fn:escapeXml(pageScope["codeVariable"])}_elementNeedClearContent').value;
        if(elementNeedClearContent != null && elementNeedClearContent != "") {
            var arrElementNeedClearContent = elementNeedClearContent.split(";");
            for (i = 0; i < arrElementNeedClearContent.length; i = i + 1){
                if($(arrElementNeedClearContent[i]) != undefined) {
                    $(arrElementNeedClearContent[i]).value = "";
                }
            }
        }
        //trungdh3
        var updateDataAfterSelectGotoTrungdh3 = $('${fn:escapeXml(pageScope["codeVariable"])}_updateDataAfterSelectGotoTrungdh3').value;
        var codeVariable = $('${fn:escapeXml(pageScope["codeVariable"])}').value;
        if (updateDataAfterSelectGotoTrungdh3!=null && updateDataAfterSelectGotoTrungdh3 != "" && target!=null && target != "" )  {
                        //alert(codeVariable)
            gotoAction(target, updateDataAfterSelectGotoTrungdh3+"?param="+codeVariable, formId);
        }
        //endtrung dh3

    }
    $('${fn:escapeXml(pageScope["codeVariable"])}').onkeydown = function(e) {
        var readOnly = '${pageScope["readOnly"]}';
        var hasRole = '${fn:escapeXml(hasRole)}';
        var evt = (window.event) ? window.event : e;
        var keyId = evt.keyCode;
        var code = $('${fn:escapeXml(pageScope["codeVariable"])}').value;

        if(hasRole == 'true' && !(readOnly == 'true') && keyId == 9 && code != '') {
            //tab
            try {
                var searchClassName = $('${fn:escapeXml(pageScope["codeVariable"])}_searchClassName').value;
                var searchMethodName = $('${fn:escapeXml(pageScope["codeVariable"])}_searchMethodName').value;
                var getNameMethod = $('${fn:escapeXml(pageScope["codeVariable"])}_getNameMethod').value;
                var otherParam = $('${fn:escapeXml(pageScope["codeVariable"])}_otherParam').value;
                var otherParamValue = "";
                if(otherParam != null && otherParam != "") {
                    var arrOtherParam = otherParam.split(";");
                    for (i = 0; i < arrOtherParam.length; i = i + 1){
                        otherParamValue = otherParamValue + ";" + $(arrOtherParam[i]).value;
                    }
                    otherParamValue = otherParamValue.substr(1); //loai bo dau ";" dau tien
                }
                
                var strUrl = '${contextPath}/ImSearchPopupAction!getName.do' +
                    '?searchClassName=' + searchClassName +
                    '&searchMethodName=' + searchMethodName +
                    '&getNameMethod=' + getNameMethod +
                    '&code=' + code +
                    '&otherParamValue=' + otherParamValue;
                
                updateNameTextBox(strUrl, '${fn:escapeXml(pageScope["codeVariable"])}', '${fn:escapeXml(pageScope["nameVariable"])}');
                
            }
            catch( e ) {
                alert(e.message);
            }
        }
    }

    //cap nhat thong tin vao textbox name
    updateNameTextBox = function(url, codeVariable1, nameVariable1){
        dojo.io.bind({
            url:url ,
            error: function(type, data, evt){
                alert("error");
            },
            handler: function(type, data, e) {
                if(dojo.lang.isObject(data)) {
                    var codeValue = data['codeValue'];
                    var nameValue = data['nameValue'];
                    if(codeValue != null && codeValue == '') {
                        //neu khog co phan tu nao duoc tim thay hoac co nhieu hon 1 phan tu duoc tim thay -> bat popup
                        try {
                            var searchClassName = $(codeVariable1 + '_searchClassName').value;
                            var searchMethodName = $(codeVariable1 + '_searchMethodName').value;
                            var getNameMethod = $(codeVariable1 + '_getNameMethod').value;
                            var codeVariable = $(codeVariable1 + '_codeVariable').value;
                            var nameVariable = $(codeVariable1 + '_nameVariable').value;
                            var codeLabel = $(codeVariable1 + '_codeLabel').value;
                            var nameLabel = $(codeVariable1 + '_nameLabel').value;
                            var code = $(codeVariable).value;
                            var name = $(nameVariable).value;
                            var otherParam = $(codeVariable1 + '_otherParam').value;
                            var otherParamValue = "";
                            if(otherParam != null && otherParam != "") {
                                var arrOtherParam = otherParam.split(";");
                                for (i = 0; i < arrOtherParam.length; i = i + 1){
                                    otherParamValue = otherParamValue + ";" + $(arrOtherParam[i]).value;
                                }
                                otherParamValue = otherParamValue.substr(1); //loai bo dau ";" dau tien
                            }

                            openDialog('${contextPath}/ImSearchPopupAction!.do' +
                                '?searchClassName=' + searchClassName +
                                '&searchMethodName=' + searchMethodName +
                                '&getNameMethod=' + getNameMethod +
                                '&codeVariable=' + codeVariable +
                                '&nameVariable=' + nameVariable +
                                '&codeLabel=' + codeLabel +
                                '&nameLabel=' + nameLabel +
                                '&code=' + code +
                                '&otherParamValue=' + otherParamValue, 700, 750, true);
                        }
                        catch( e ) {
                            alert(e.message);
                        }
                    } else {
                        //neu chi co 1 phan tu -> update vao code va name
                        $(codeVariable1).value = codeValue;
                        $(nameVariable1).value = nameValue;
                    }
                }
            },
            mimetype: "text/json"
        });       
    }

</script>
