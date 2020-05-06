<%--
    Document   : ExportStockCmd
    Created on : Feb 24, 2009, 10:57:01 AM
    Author     : ThanhNC
--%>
<%@tag description="Cac thong tin lenh xuat kho" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@attribute name="shopRecType"%>

<%@attribute name="readOnly"%>
<%@attribute name="disabled"%>
<%@attribute name="viewOnly"%>
<%@attribute name="type"%>
<%@attribute name="subForm"%>
<%@attribute name="height"%>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<%
           String property = "";
            if (subForm != null && !"".equals(subForm.trim())) {
                property = subForm.trim() + ".";
                request.setAttribute("property", property);
            }
%>

<%String require = "&nbsp;(<font color='red'>*</font>)";%>

<%
            com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();
            if (viewOnly != null && "true".equals(viewOnly)) {
                require = "";
            }

            String title = "MSG.GOD.149";

            String code = baseDAOAction.getText("MSG.GOD.150");
            String errorAction = baseDAOAction.getText("MSG.GOD.151");
            String errorAction2 = baseDAOAction.getText("MSG.GOD.130");
            if ("note".equals(type)) {
                title = "MSG.GOD.152";
                code = baseDAOAction.getText("MSG.GOD.153");
                errorAction = baseDAOAction.getText("MSG.GOD.154");
                errorAction2 = baseDAOAction.getText("MSG.GOD.135");
            }
            request.setAttribute("title", title);
            request.setAttribute("code", code);
            request.setAttribute("errorAction", errorAction);
            request.setAttribute("errorAction2", errorAction2);
            request.setAttribute("require", require);
            String height1 = height == null ? "auto" : height;
            String style = "height:" + height1;
            request.setAttribute("style", style);

            String combo_header_reason = baseDAOAction.getText("MSG.GOD.147");
            request.setAttribute("combo_header_reason", combo_header_reason);
%>


<tags:imPanel title="${fn:escapeXml(title)}">
    <div style="${fn:escapeXml(style)}">
        <s:hidden name="%{#attr.subForm+'.status'}" id="%{#attr.subForm+'.status'}"/>
        <s:hidden name="%{#attr.subForm+'.actionId'}" id="%{#attr.subForm+'.actionId'}"/>
        <table class="inputTbl4Col">
            <tr>
                <td class="label">
                    ${code}${require}
                </td>
                <td class="text">
                    <s:textfield theme="simple" maxlength="20"  name="%{#attr.subForm+'.cmdExportCode'}" id="%{#attr.subForm+'.cmdExportCode'}" cssClass="txtInputFull"  readonly="true"/>
                </td>
                <td class="label">
                    <tags:label key="MSG.GOD.155"/>
                    <!--                    Người xuất-->
                </td>
                <td  class="text">
                    <s:textfield theme="simple" name="%{#attr.subForm+'.sender'}" id="%{#attr.subForm+'.sender'}" cssClass="txtInputFull" readonly="true"  />
                    <s:hidden theme="simple" name="%{#attr.subForm+'.senderId'}"/>
                </td>
            </tr>
            <tr>
                <td class="label">
                    <tags:label key="MSG.GOD.138"/>
                    <!--                    Kho xuất hàng-->
                </td>
                <td  class="text" colspan="3">
                    <s:hidden theme="simple" name="%{#attr.subForm+'.shopExportId'}" id="%{#attr.subForm+'.shopExportId'}"/>
                    <table width="100%">
                        <tr>
                            <td width="30%">
                                <s:textfield theme="simple" name="%{#attr.subForm+'.shopExportCode'}" id="%{#attr.subForm+'.shopExportCode'}" cssClass="txtInputFull" readonly="true"/>
                            </td>
                            <td width="70%">
                                <s:textfield theme="simple" name="%{#attr.subForm+'.shopExportName'}" id="%{#attr.subForm+'.shopExportName'}" cssClass="txtInputFull" readonly="true"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <%--<logic:equal name="shopRecType" value="staff">--%>


            <tr>

                <td class="label">
                    <tags:label key="MSG.GOD.139"/>
                    <!--                                Kho nhận hàng-->
                    ${require}
                </td>
                <td  class="text" colspan="3">
                    <table width="100%">
                        <tr>
                            <td width="30%">
                                <s:hidden theme="simple" name="%{#attr.subForm+'.shopImportedId'}" id="%{#attr.subForm+'.shopImportedId'}"/>
                                <s:if test="#attr.readOnly!=null && #attr.readOnly=='true'">
                                    <s:textfield cssClass="txtInputFull" name="%{#attr.subForm+'.shopImportedCode'}"  theme="simple"
                                                 id="%{#attr.subForm+'.shopImportedCode'}" readonly="true"/>
                                </s:if>
                                <s:else>
                                    <s:textfield cssClass="txtInputFull" onblur="getShopName()"  theme="simple"
                                                 name="%{#attr.subForm+'.shopImportedCode'}" id="%{#attr.subForm+'.shopImportedCode'}" />
                                </s:else>
                            </td>
                            <td width="70%">
                                <s:textfield cssClass="txtInputFull" name="%{#attr.subForm+'.shopImportedName'}"  theme="simple"
                                             id="%{#attr.subForm+'.shopImportedName'}" readonly="%{#attr.readOnly}" />
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td class="label">
                    <tags:label key="MSG.GOD.158"/>
                    <!--                    Lý do xuất-->
                    ${require}
                </td>
                <td  class="text">
                    <tags:imSelect theme="simple"
                                   name="${pageScope.subForm}.reasonId"
                                   id="${pageScope.subForm}.reasonId"
                                   cssClass="txtInputFull"
                                   disabled="${pageScope.subForm}.disabled"
                                   headerKey="" headerValue="MSG.GOD.147"
                                   list="lstReasonExp"
                                   listKey="reasonId" listValue="reasonName"/>

                    <s:if test="#attr.disabled =='true'">
                        <s:hidden theme="simple"  name="%{#attr.subForm+'.reasonId'}" id="%{#attr.subForm+'.reasonId'}"/>
                    </s:if>


                </td>
                <td class="label">
                    <tags:label key="MSG.GOD.159"/>
                    <!--                    Ngày xuất-->
                    <%--=require--%>
                </td>
                <td  class="text">
                    <!--tags:dateChooser readOnly="${disabled}"  property="${property}dateExported" id="${property}dateExported"  styleClass="txtInputFull" /-->
                    <tags:dateChooser readOnly="true"  property="${property}dateExported" id="${property}dateExported"  styleClass="txtInputFull" />
                </td>
            </tr>
            <tr>
                <td  class="label">
                    <tags:label key="MSG.GOD.031"/>
                    <!--                    Ghi chú-->
                </td>
                <td colspan="3"  class="text">
                    <s:textfield theme="simple"  name="%{#attr.subForm+'.note'}" maxlength="500" id="%{#attr.subForm+'.note'}" cssClass="txtInputFull" readonly="%{#attr.readOnly}"/>
                </td>
            </tr>
        </table>
    </div>
</tags:imPanel>
<script>
    var formName='<s:property value="#attr.subForm"/>';
    getShopName=function(){
        $(formName+".shopImportedName").value="";
        var shopCode=$(formName+".shopImportedCode").value;
        getInputText("CommonStockAction!getShopNameAgent.do?target="+formName+".shopImportedName&shopCode=" + shopCode);
    }
    getStaffName=function(){
        $(formName+".shopImportedName").value="";
        var staffCode=$(formName+".shopImportedCode").value;
        var shopCode='<s:property value="#session.userToken.shopCode"/>'
        getInputText("CommonStockAction!getStaffName.do?target="+formName+".shopImportedName&staffCode=" + staffCode+"&shopCode="+shopCode);
    }

    setStaffValue= function(code,name){
        $(formName+'.shopImportedCode').value=code;
        $(formName+'.shopImportedName').value=name;
        $(formName+'.reasonId').focus();
    }
    setShopValue= function(code,name){
        $(formName+'.shopImportedCode').value=code;
        $(formName+'.shopImportedName').value=name;
        $(formName+'.reasonId').focus();
    }

    try{
        $(formName +".shopImportedCode").onkeyup = function( e ) {
            var evt = ( window.event ) ? window.event : e;
            var keyID = evt.keyCode;
            // F9
            if( keyID == 120 ) {
                try {
                    var code=$(formName+'.shopImportedCode').value;
                    var name=$(formName+'.shopImportedName').value; 
                    openDialog('CommonStockAction!popupSearchShopUnderlyingAgent.do?shopCode='+code+"&shopName="+name+"&notAllChild=true", 700, 750,false)
                }
                catch( e ) {
                    alert(e.message);
                }
            }
        }
    }
    catch(e){
    }

    validateForm =function(target){
        //var formName='<s:property value="#attr.subForm"/>';
        var cmdExportCode= document.getElementById(formName+".cmdExportCode");
        var shopImportedCode= document.getElementById(formName+".shopImportedCode");
        var reasonId= document.getElementById(formName+".reasonId");
        //var dateExported= document.getElementById(formName+".dateExported");
        var dateExported= dojo.widget.byId(formName+".dateExported");
        //var dateValue =dateExported.domNode.childNodes[1].value;
        //alert(dateExported.domNode.childNodes[1].value);
        if(cmdExportCode.value ==null || trim(cmdExportCode.value)==''){
            $(target).innerHTML='${errorAction}';
            cmdExportCode.focus();
            return false;
        }

        if(!isFormalCharacter(cmdExportCode.value)){
            $(target).innerHTML='${errorAction2}';
            cmdExportCode.focus();
            return false;
        }
        cmdExportCode.value=trim(cmdExportCode.value);
        if(shopImportedCode.value ==null || trim(shopImportedCode.value)==''){
            $(target).innerHTML='<s:property value="getText('MSG.GOD.160')"/>';
            //                    $(target).innerHTML='Chưa chọn kho nhận hàng';
            shopImportedCode.focus();
            return false;
        }
        if(reasonId.value ==null || trim(reasonId.value)==''){
            $(target).innerHTML='<s:property value="getText('MSG.GOD.161')"/>';
            //                    $(target).innerHTML='Chưa chọn lý do xuất';
            reasonId.focus();
            return false;
        }

        if(dateExported.domNode.childNodes[1].value ==null || dateExported.domNode.childNodes[1].value==''){
            $(target).innerHTML='<s:property value="getText('MSG.GOD.162')"/>';
            //                    $(target).innerHTML='Chưa nhập ngày xuất kho';
            dateExported.domNode.childNodes[1].focus();
            return false;
        }
        if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
            $(target).innerHTML='<s:property value="getText('MSG.GOD.163')"/>';
            //                    $(target).innerHTML='Ngày xuất không đúng định dạng.';
            dateExported.domNode.childNodes[1].focus();
            return false;
        }
        var note= document.getElementById(formName+".note");

        if(isHtmlTagFormat(note.value)){
            $(target).innerHTML='<s:property value="getText('MSG.GOD.164')"/>';
            //                    $(target).innerHTML='Trường ghi chú không được thẻ HTML';
            note.focus();
            return false;
        }
        note.value=note.value.trim();
        return true;
    }

    validateFormColl =function(target){
        //var formName='<s:property value="#attr.subForm"/>';
        var cmdExportCode= document.getElementById(formName+".cmdExportCode");
        var shopImportedCode= document.getElementById(formName+".shopImportedCode");
        var reasonId= document.getElementById(formName+".reasonId");
        //var dateExported= document.getElementById(formName+".dateExported");
        var dateExported= dojo.widget.byId(formName+".dateExported");
        //var dateValue =dateExported.domNode.childNodes[1].value;
        //alert(dateExported.domNode.childNodes[1].value);
        var payMethodeid = document.getElementById(formName+".payMethodeid");

        if(cmdExportCode.value ==null || trim(cmdExportCode.value)==''){
            $(target).innerHTML='${errorAction}';
            cmdExportCode.focus();
            return false;
        }

        if(!isFormalCharacter(cmdExportCode.value)){
            $(target).innerHTML='${errorAction2}';
            cmdExportCode.focus();
            return false;
        }
        cmdExportCode.value=trim(cmdExportCode.value);
        if(shopImportedCode.value ==null || trim(shopImportedCode.value)==''){
            //                    $(target).innerHTML='Chưa chọn kho nhận hàng';
            $(target).innerHTML='<s:property value="getText('MSG.GOD.160')"/>';
            shopImportedCode.focus();
            return false;
        }
        if(payMethodeid.value ==null || trim(payMethodeid.value)==''){
            //                    $(target).innerHTML='Chưa chọn hình thức thanh toán';
            $(target).innerHTML='<s:property value="getText('MSG.GOD.165')"/>';
            payMethodeid.focus();
            return false;
        }
        if(reasonId.value ==null || trim(reasonId.value)==''){
            //                    $(target).innerHTML='Chưa chọn lý do xuất';
            $(target).innerHTML='<s:property value="getText('MSG.GOD.161')"/>';
            reasonId.focus();
            return false;
        }

        if(dateExported.domNode.childNodes[1].value ==null || dateExported.domNode.childNodes[1].value==''){
            //                    $(target).innerHTML='Chưa nhập ngày xuất kho';
            $(target).innerHTML='<s:property value="getText('MSG.GOD.162')"/>';
            dateExported.domNode.childNodes[1].focus();
            return false;
        }
        if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
            //                    $(target).innerHTML='Ngày xuất không đúng định dạng.';
            $(target).innerHTML='<s:property value="getText('MSG.GOD.163')"/>';
            dateExported.domNode.childNodes[1].focus();
            return false;
        }

        var note= document.getElementById(formName+".note");

        if(isHtmlTagFormat(note.value)){
            //                    $(target).innerHTML='Trường ghi chú không được thẻ HTML';
            $(target).innerHTML='<s:property value="getText('MSG.GOD.164')"/>';
            note.focus();
            return false;
        }
        note.value=note.value.trim();
        return true;
    }

    function GetDateSys() {
        var time = new Date();
        var dd=time.getDate();
        var mm=time.getMonth()+1;
        var yy=time.getFullYear() ;
        var temp = '';
        if (dd < 10) dd = '0' + dd;
        if (mm < 10) mm = '0' + mm;
        temp = dd + "/" + mm + "/" + yy ;
        return(temp);
    }
</script>