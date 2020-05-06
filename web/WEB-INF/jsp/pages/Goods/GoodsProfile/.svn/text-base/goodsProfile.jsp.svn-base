<%-- 
    Document   : goodsProfile
    Created on : Apr 2, 2009, 8:11:17 AM
    Author     : tamdt1
    Desc       : hien thi thong tin ve goodsProfile cua 1 stockType
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<tags:imPanel title = "MSG.GOD.060">
    <%--<tags:imPanel title = "Thông tin profile">--%>
    <!-- phan hien thi message (neu co) -->
    <div>
        <tags:displayResult id="displayResultMsgClient" property="message"  propertyValue="paramMsg" type="key" />
    </div>

    <!-- phan hien thi thong tin ve profile hang hoa -->
    <s:form action="profileAction" theme="simple" enctype="multipart/form-data"  method="post" id="profileForm">
<s:token/>

        <s:hidden name="profileForm.profileId" id="profileForm.profileId"/>

        <!-- trong truong hop cac combobox bi disable phai them cac bien hidden de luu tru cac gia tri nay -->
        <s:if test="!(#request.isAddMode || #request.isEditMode)">
            <s:hidden name="profileForm.separatedChar" id="profileForm.separatedChar"/>
        </s:if>

        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.027"/>
                        <!--                        Loại mặt hàng-->
                    </td>
                    <td class="oddColumn">
                        <tags:imSelect name="profileForm.stockTypeId"
                                       id="profileForm.stockTypeId"
                                       cssClass="txtInputFull"
                                       disabled="true"
                                       list="listStockTypes"
                                       listKey="stockTypeId" listValue="name"
                                       headerKey="" headerValue="MSG.GOD.216"/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.061"/>
                        <!--                        Tên profile-->
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="profileForm.profileName" id="profileForm.profileName" maxlength="1000" cssClass="txtInputFull" readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.062"/>
                        <!--                        Ký tự phân cách-->
                    </td>
                    <td>
                        <tags:imSelect name="profileForm.separatedChar"
                                       id="profileForm.separatedChar"
                                       cssClass="txtInputFull"
                                       disabled="${!(fn:escapeXml(isAddMode) || fn:escapeXml(isEditMode))}"
                                       onchange="updateLinePattern($('profileForm.arrSelectedField'), $('profileForm.separatedChar'), $('profileForm.linePattern'))"
                                       list="listProfileSeperators"
                                       listKey="value" listValue="name"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.063"/>
                        <!--                        Dòng bắt đầu DL-->
                    </td>
                    <td class="oddColumn">
                        <s:textfield name="profileForm.startLine" id="profileForm.startLine" maxlength="10" cssClass="txtInputFull" readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                    <td>
                        <tags:label key="MSG.GOD.045"/>
                        <!--                        Mô tả-->
                    </td>
                    <td colspan="3">
                        <s:textfield name="profileForm.description" id="profileForm.description" maxlength="1000" cssClass="txtInputFull" readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <tags:label key="MSG.GOD.064"/>
                        <!--                        Mẫu dữ liệu-->
                    </td>
                    <td colspan="5">
                        <s:textfield name="profileForm.linePattern" id="profileForm.linePattern" maxlength="1000" cssClass="txtInputFull" readonly="true"/>
                    </td>
                </tr>
                <tr>
                    <td style="vertical-align:top">
                        <!--                        Các trường<br />trong file dữ liệu-->
                        <tags:label key="MSG.GOD.065"/>
                    </td>
                    <td colspan="5">
                        <table style="width:100%">
                            <tr>
                                <td style="width:40%">
                                    <tags:imSelect name="profileForm.arrAvailbleField"
                                                   id="profileForm.arrAvailbleField"
                                                   cssStyle="width:100%; height:130px;"
                                                   multiple="true"
                                                   size="8"
                                                   disabled="${!(fn:escapeXml(isAddMode) || fn:escapeXml(isEditMode))}"
                                                   list="listAvailableFields"
                                                   listKey="columnName" listValue="columnName"
                                                   ondblclick="addField($('profileForm.arrSelectedField'), $('profileForm.separatedChar'), $('profileForm.linePattern'))"
                                                   />
                                </td>
                                <td align="center" style="width:15%; vertical-align:middle;">
                                    <s:if test="#request.isAddMode || #request.isEditMode">
                                        <input type="button" value=">>" onclick="addAllField($('profileForm.arrSelectedField'), $('profileForm.separatedChar'), $('profileForm.linePattern'))" style="width:60px">
                                        <br /><br />
                                        <input type="button" value=">" onclick="addField($('profileForm.arrSelectedField'), $('profileForm.separatedChar'), $('profileForm.linePattern'))" style="width:60px">
                                        <br />
                                        <input type="button" value="<" onclick="delField($('profileForm.arrSelectedField'), $('profileForm.separatedChar'), $('profileForm.linePattern'))" style="width:60px">
                                        <br /><br />
                                        <input type="button" value="<<" onclick="delAllField($('profileForm.arrSelectedField'), $('profileForm.separatedChar'), $('profileForm.linePattern'))" style="width:60px">
                                    </s:if>
                                    <s:else>
                                        <input type="button" value=">>" disabled style="width:60px">
                                        <br /><br />
                                        <input type="button" value=">" disabled style="width:60px">
                                        <br />
                                        <input type="button" value="<" disabled style="width:60px">
                                        <br /><br />
                                        <input type="button" value="<<" disabled style="width:60px">
                                    </s:else>
                                </td>
                                <td style="width:40%">
                                    <tags:imSelect name="profileForm.arrSelectedField"
                                                   id="profileForm.arrSelectedField"
                                                   cssStyle="width:100%; height:130px;"
                                                   multiple="true"
                                                   size="8"
                                                   disabled="${!(fn:escapeXml(isAddMode) || fn:escapeXml(isEditMode))}"
                                                   list="listSelectedFields"
                                                   listKey="columnName" listValue="columnName"
                                                   ondblclick="delField($('profileForm.arrSelectedField'), $('profileForm.separatedChar'), $('profileForm.linePattern'))"
                                                   />
                                </td>
                                <td align="right" style="vertical-align:middle;">
                                    <s:if test="#request.isAddMode || #request.isEditMode">
                                        <input type="button" value="^" onclick="upOptionInList($('profileForm.arrSelectedField'), $('profileForm.separatedChar'), $('profileForm.linePattern'))" style="width:20px; height:50px;">
                                        <br /><br />
                                        <input type="button" value="V" onclick="dowOptionInList($('profileForm.arrSelectedField'), $('profileForm.separatedChar'), $('profileForm.linePattern'))" style="width:20px; height:50px;">
                                    </s:if>
                                    <s:else>
                                        <input type="button" value="^" disabled style="width:20px; height:50px;">
                                        <br /> <br />
                                        <input type="button" value="V" disabled style="width:20px; height:50px;">
                                    </s:else>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>

        <!-- danh sach tat ca cac profile hang hoa -->
        <div>
            <fieldset class="imFieldset">
                <legend>${fn:escapeXml(imDef:imGetText('MSG.GOD.066'))}
                    <!--                    Danh sách các mặt hàng áp dụng profile-->
                </legend>
                <div style="overflow:auto; height:260px;">
                    <display:table id="tblListStockModels" name="listStockModels" class="dataTable" cellpadding="1" cellspacing="1" >
                        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblListStockModels_rowNum)}</display:column>
                        <display:column escapeXml="true" property="stockModelCode" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.007'))}" sortable="false" headerClass="tct"/>
                        <display:column escapeXml="true" property="name" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.008'))}" sortable="false" headerClass="tct"/>
                        <display:column escapeXml="true" property="stockTypeName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.027'))}" sortable="false" headerClass="tct"/>
                        <display:column escapeXml="true" property="telecomServiceName" title="${fn:escapeXml(imDef:imGetText('MSG.GOD.270'))}" sortable="false" headerClass="tct"/>
                        <display:column title="<input id = 'checkAllId' type='checkbox' onclick=\"selectCbAll()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center">
                            <s:if test="#attr.tblListStockModels.profileId != null">
                                <s:checkbox name="profileForm.arrStockModel" id="checkBoxId%{#attr.tblListStockModels.stockModelId}"
                                            theme="simple" fieldValue="%{#attr.tblListStockModels.stockModelId}" value="1"
                                            onclick="checkSelectCbAll();" disabled="%{!(#request.isAddMode || #request.isEditMode)}"/>
                            </s:if>
                            <s:else>
                                <s:checkbox name="profileForm.arrStockModel" id="checkBoxId%{#attr.tblListStockModels.stockModelId}"
                                            theme="simple" fieldValue="%{#attr.tblListStockModels.stockModelId}" value="0"
                                            onclick="checkSelectCbAll();" disabled="%{!(#request.isAddMode || #request.isEditMode)}"/>
                            </s:else>
                        </display:column>
                    </display:table>
                </div>
            </fieldset>
        </div>
    </s:form>

    <!-- phan nut tac vu -->
    <s:if test="#request.isAddMode || #request.isEditMode">
        <div align="center" style="padding-top:5px;">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="profileForm" loadingText="Processing..."
                       cssStyle="width:80px;"
                       showLoadingText="true" targets="divDisplayInfo"
                       key="MSG.GOD.016"  beforeNotifyTopics="profileAction/addOrEditProfile"/>
            <sx:submit parseContent="true" executeScripts="true"
                       formId="profileForm" loadingText="Processing..."
                       cssStyle="width:80px;"
                       showLoadingText="true" targets="divDisplayInfo"
                       key="MSG.GOD.018"  beforeNotifyTopics="profileAction/displayProfile"/>
        </div>
    </s:if>
    <s:else>
        <div align="center" style="padding-top:5px;">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="profileForm" loadingText="Processing..."
                       cssStyle="width:80px;"
                       showLoadingText="true" targets="divDisplayInfo"
                       key="MSG.GOD.019"  beforeNotifyTopics="profileAction/prepareAddProfile"/>

            <s:if test="#attr.profileForm.profileId.compareTo(0L) > 0">
                <sx:submit parseContent="true" executeScripts="true"
                           formId="profileForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divDisplayInfo"
                           key="MSG.GOD.020"  beforeNotifyTopics="profileAction/prepareEditProfile"/>
                <sx:submit parseContent="true" executeScripts="true"
                           formId="profileForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divDisplayInfo"
                           key="MSG.GOD.021"  beforeNotifyTopics="profileAction/delProfile"/>
            </s:if>
            <s:else>
                <sx:submit parseContent="true" executeScripts="true" disabled="true"
                           formId="profileForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divDisplayInfo"
                           key="MSG.GOD.020"  beforeNotifyTopics="profileAction/prepareEditProfile"/>
                <sx:submit parseContent="true" executeScripts="true" disabled="true"
                           formId="profileForm" loadingText="Processing..."
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divDisplayInfo"
                           key="MSG.GOD.021"  beforeNotifyTopics="profileAction/delProfile"/>

                <!--                <input type="button" value="Sửa" disabled style="width:80px;">
                                <input type="button" value="Xóa" disabled style="width:80px;">-->
            </s:else>
            <script language="javascript">
                //
                $('checkAllId').disabled = true;
            </script>

        </div>
    </s:else>




</tags:imPanel>

<script language="javascript">
    
    //lang nghe, xu ly su kien khi click nut "Chap nhan"
    dojo.event.topic.subscribe("profileAction/addOrEditProfile", function(event, widget){
        //kiem tra tinh hop le truoc khi
        if(checkValidFields()) {
            selectAllOption($('profileForm.arrSelectedField'), true); //chon tat ca cac phan tu
            widget.href = "profileAction!addOrEditProfile.do?" + token.getTokenParamString();
        } else {
            event.cancel = true;
        }
    });

    validDataBeforeDisplay = function() {
        $('profileForm.startLine').value = "";
    }


    //lang nghe, xu ly su kien khi click nut "Bo qua"
    dojo.event.topic.subscribe("profileAction/displayProfile", function(event, widget){
        validDataBeforeDisplay();
        widget.href = "profileAction!displayProfile.do";
    });

    //lang nghe, xu ly su kien khi click nut "Them"
    dojo.event.topic.subscribe("profileAction/prepareAddProfile", function(event, widget){
        widget.href = "profileAction!prepareAddProfile.do";
    });

    //lang nghe, xu ly su kien khi click nut "Sua"
    dojo.event.topic.subscribe("profileAction/prepareEditProfile", function(event, widget){
        widget.href = "profileAction!prepareEditProfile.do";
    });

    //lang nghe, xu ly su kien khi click nut "Xoa"
    dojo.event.topic.subscribe("profileAction/delProfile", function(event, widget){
        //if (confirm('Bạn có chắc chắn muốn xóa profile mặt hàng này không?'))
//        var strComfirm = getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('MSG.GOD.298')"/>');
        var strComfirm = getUnicodeMsg('<s:text name="MSG.GOD.298"/>');
        if(confirm(strComfirm)){
            widget.href = "profileAction!delProfile.do?" + token.getTokenParamString();
        } else {
        event.cancel = true;
    }

    //event: set event.cancel = true, to cancel request
});


selectCbAll = function(){
    selectAll("checkAllId", "profileForm.arrStockModel", "checkBoxId");
}

checkSelectCbAll = function(){
    checkSelectAll("checkAllId", "profileForm.arrStockModel", "checkBoxId");
}

//--------------------------------------------------------------------------------
//cac ham xu ly su kien cua trang goodsProfle
//
//cap nhat lai mau du lieu
updateLinePattern = function(cbArrSelectedField, cbSplitString, txtLinePattern) {
    var str = "";
    for(var i = 0; i < cbArrSelectedField.length - 1; i++ ) {
        str += cbArrSelectedField.options[i].value + cbSplitString.value;
    }
    if(cbArrSelectedField.length > 0) {
        str += cbArrSelectedField.options[cbArrSelectedField.length - 1].value;
    }
    txtLinePattern.value = str;
}

//di chuyen 1 option trong list len tren
upOptionInList = function(cbArrSelectedField, cbSplitString, txtLinePattern) {
    var curPos, prevPos, prevOp, curOp, newOp;

    curPos = -1;
    for(var i = 0; i < cbArrSelectedField.length; i++) {
        if(cbArrSelectedField.options[i].selected) {
            curOp = cbArrSelectedField.options[i];
            curPos = i;
            if(curPos == 0) return;
            prevPos = curPos - 1;
            prevOp = cbArrSelectedField.options[prevPos];
            break;
        }
    }

    if(curPos == -1) return;

    newOp = document.createElement('option');
    newOp.value = curOp.value;
    newOp.text = curOp.text;

    cbArrSelectedField.remove(curPos);
    cbArrSelectedField.insertBefore(newOp, prevOp);

    selectAllOption(cbArrSelectedField, false);
    newOp.selected = true;

    updateLinePattern(cbArrSelectedField, cbSplitString, txtLinePattern);
}

//di chuyen 1 option trong list xuong duoi
dowOptionInList = function(cbArrSelectedField, cbSplitString, txtLinePattern) {
    var curPos, postPos, postOp, curOp, newOp;

    curPos = -1;
    for(var i = 0; i < cbArrSelectedField.length; i++) {
        if(cbArrSelectedField.options[i].selected) {
            curOp = cbArrSelectedField.options[i];
            curPos = i;
            if( curPos == cbArrSelectedField.length - 1 ) return;
            postPos = curPos + 1;
            postOp = cbArrSelectedField.options[postPos];
            break;
        }
    }

    if(curPos == -1) return;

    newOp = document.createElement('option');
    newOp.value = postOp.value;
    newOp.text = postOp.text;

    cbArrSelectedField.remove(postPos);
    cbArrSelectedField.insertBefore(newOp, curOp);

    selectAllOption(cbArrSelectedField, false );
    curOp.selected = true;

    updateLinePattern(cbArrSelectedField, cbSplitString, txtLinePattern);
}

//ham chon tat ca cac truong
addAllField = function(cbArrSelectedField, cbSplitString, txtLinePattern) {
    addAll();
    updateLinePattern(cbArrSelectedField, cbSplitString, txtLinePattern);
}

//ham them truong
addField = function(cbArrSelectedField, cbSplitString, txtLinePattern) {
    addAttribute();
    updateLinePattern(cbArrSelectedField, cbSplitString, txtLinePattern);
}

//
delField = function(cbArrSelectedField, cbSplitString, txtLinePattern) {
    delAttribute();
    updateLinePattern(cbArrSelectedField, cbSplitString, txtLinePattern);
}

//
delAllField = function(cbArrSelectedField, cbSplitString, txtLinePattern) {
    delAll();
    updateLinePattern(cbArrSelectedField, cbSplitString, txtLinePattern);
}

//ham select tat ca cac phan tu trong list, hoac unselect tat cac cac phan tu trong list
selectAllOption = function(cbArrSelectedField, selectOrNot) {
    for(var i = 0; i < cbArrSelectedField.length; i++ ) {
        cbArrSelectedField.options[i].selected = selectOrNot;
    }
}

//ham kiem tra du lieu cac truong co hop le hay ko
    checkValidFields = function() {
        var startLine = $('profileForm.startLine').value;
        if(!isInteger(trim(startLine))) {
            //dong bat dau p la so khong am
//            $('displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.024')"/>';
            $('displayResultMsgClient' ).innerHTML = '<s:text name="ERR.GOD.024"/>';
            $('profileForm.startLine').select();
            $('profileForm.startLine').focus();
            return false;
        }
        //ten profile khong duoc chua cac the HTML
        var profileName = $('profileForm.profileName').value;
        if (containsHtmlTag(profileName)) {
//            $('displayResultMsgClient' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.034')"/>';
            $('displayResultMsgClient' ).innerHTML = '<s:text name="ERR.GOD.034"/>';
            $('profileForm.profileName').select();
            $('profileForm.profileName').focus();
            return false;
        }

        //trim cac truogn can thiet
        $('profileForm.startLine').value = trim($('profileForm.startLine').value);
        $('profileForm.profileName').value = trim($('profileForm.profileName').value);

        return true;
    }

//
selectAllOption($('profileForm.arrAvailbleField'), false);
selectAllOption($('profileForm.arrSelectedField'), false);
createListObjects('profileForm.arrAvailbleField', 'profileForm.arrSelectedField');
updateLinePattern($('profileForm.arrSelectedField'), $('profileForm.separatedChar'), $('profileForm.linePattern'));


</script>
