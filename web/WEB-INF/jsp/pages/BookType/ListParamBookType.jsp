<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ListParamBookType
    Created on : Aug 17, 2009, 2:06:41 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>

<s:form action="GetBookTypeAction" theme="simple" enctype="multipart/form-data"  method="post" id="printerParamForm">
<s:token/>

    <tags:imPanel title="MSG.GOD.088">
        <s:hidden name="printerParamForm.printerParamId" id="printerParamForm.printerParamId"/>

        <div>
            <tags:displayResult id="displayResultMsgClient" property="messageParam" propertyValue="paramMsg" type="key" />
        </div>
        <table class="inputTbl8Col">
            <sx:div id="displayParamDetail">
                <tr>
                    <td class="label"><tags:label key="MSG.bill.code.need.print" required="true"/></td>
                    <td class="text">
                        <s:textfield name="printerParamForm.serialCode" id="serialCode" maxlength="100" cssClass="txtInputFull" readonly="1"/>
                    </td>
                    <td class="label"><tags:label key="MSG.field.name.need.print" required="true"/> </td>
                    <td class="text">
                        <s:textfield name="printerParamForm.fieldName" id="fieldName" maxlength="100" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.abscissa.left" /></td>
                    <td class="text">
                        <s:textfield name="printerParamForm.XCoordinates" id="XCoordinates" maxlength="10" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.abscissa.right" /></td>
                    <td class="text">
                        <s:textfield name="printerParamForm.YCoordinates" id="YCoordinates" maxlength="10" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.wide.space.need.print" /></td>
                    <td class="text">
                        <s:textfield name="printerParamForm.width" id="width" maxlength="10" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.hight.space.need.print" /></td>
                    <td class="text">
                        <s:textfield name="printerParamForm.height" id="height" maxlength="10" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.repeat.or.not" /></td>
                    <td class="text">
                        <tags:imSelect name="printerParamForm.isDetailField" id="isDetailField"
                                       cssClass="txtInputFull" disabled="false"
                                       list="1:MSG.LST.011, 0: MSG.LST.010 "
                                       listKey="abc" listValue=""
                                       headerKey="" headerValue="MSG.LST.009"/>

                    </td>
                    <td class="label"><tags:label key="MSG.font.name.need.print" /></td>
                    <%--<td>
                        <s:textfield name="printerParamForm.font" id="font" maxlength="80" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
                    </td>--%>
                    <td class="text">
                        <%--<s:select name="printerParamForm.font"
                                  id="font"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn kiểu font--"
                                  list="#{'arial.ttf':'arial.ttf'}"
                                  />--%>
                        <tags:imSelect name="printerParamForm.font" id="font"
                                       cssClass="txtInputFull" disabled="false"
                                       list="arial.ttf:arial.ttf, arialbd.ttf:arialbd.ttf"
                                       listKey="abc" listValue=""
                                       headerKey="" headerValue="MSG.LST.012"/>
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="MSG.font.size.need.print" /></td>
                    <td class="text">
                        <s:textfield name="printerParamForm.fontSize" id="fontSize" maxlength="10" readonly="%{#request.toInputData}" cssClass="txtInputFull"/>
                    </td>
                    <td class="label"><tags:label key="MSG.font.type.need.print" /></td>
                    <td class="text">
                        <%--<s:select name="printerParamForm.fontStyle"
                                  id="fontStyle"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn kiểu font--"
                                  list="#{'0':'regular','1':'bold','2':'italic','3':'bold italic'}"
                                  />--%>
                        <tags:imSelect name="printerParamForm.font" id="font"
                                       cssClass="txtInputFull" disabled="false"
                                       list="0:regular,1:bold,2:italic,3:bold italic"
                                       listKey="abc" listValue=""
                                       headerKey="" headerValue="MSG.LST.013"/>

                    </td>
                    <td class="label"><tags:label key="MSG.generates.status" /></td>
                    <td class="text">
                        <%--<s:select name="printerParamForm.status"
                                  id="status" disabled="false"
                                  cssClass="txtInputFull"
                                  headerKey="" headerValue="--Chọn trạng thái--"
                                  list="#{'1':'Hiệu lực','0':'Không hiệu lực'}"
                                  />--%>
                        <tags:imSelect name="printerParamForm.status" id="status"
                                       cssClass="txtInputFull" disabled="false"
                                       listKey="abc" listValue=""
                                       headerKey="" headerValue="MSG.GOD.189"
                                       list="1:MSG.GOD.002, 0:MSG.GOD.003"/>
                    </td>
                </tr>

            </sx:div>
        </table>
        <br/>
        <div align="center" style="padding-bottom:5px;">

            <s:if test="#session.toEditParamBookType != 1">
                <input type="button" onclick="addParamBookType();"  value="${fn:escapeXml(imDef:imGetText('MSG.GOD.010'))}"/>
            </s:if>
            <s:else>
                <input type="button" onclick="editParamBookType();"  value="${fn:escapeXml(imDef:imGetText('MSG.GOD.016'))}"/>
                <input type="button" onclick="cancel();"  value="${fn:escapeXml(imDef:imGetText('MSG.GOD.018'))}"/>
            </s:else>
        </div>
    </tags:imPanel>

</s:form>
<br>
<%
            if (request.getAttribute("listParamBookType") == null) {
                request.setAttribute("listParamBookType", request.getSession().getAttribute("listParamBookType"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>
<tags:imPanel title="MSG.LST.014">

    <sx:div id="displayParamBookType">


        <display:table pagesize="10" id="tbllistParamBookType"
                       targets="popupBody" name="listParamBookType"
                       class="dataTable"
                       requestURI="GetBookTypeAction!pageNavigatorParam.do"
                       cellpadding="1" cellspacing="1" >

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.GOD.073'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center">
                ${fn:escapeXml(tbllistParamBookType_rowNum)}
            </display:column>

            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.bill.code.need.print'))}" property="serialCode" headerClass="tct" style="text-align:left"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.field.name.need.print'))}"  headerClass="tct" style="text-align:left">
                <s:property escapeJavaScript="true"  value="#attr.tbllistParamBookType.fieldName"/>
            </display:column>

            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.abscissa.left'))}" property="XCoordinates" headerClass="tct" sortable="false" style="text-align:center"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.ordinate.right'))}" property="YCoordinates" headerClass="tct" style="text-align:center"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.wide.space.need.print'))}" property="width" style="text-align:right" headerClass="tct"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.hight.space.need.print'))}" property="height" style="text-align:right" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.repeat.have'))}" headerClass="tct" sortable="false" style="width:60px; text-align:center">
                <s:if test="#attr.tbllistParamBookType.isDetailField == 1">${fn:escapeXml(imDef:imGetText('MSG.LST.010'))}</s:if>
                <s:else>
                    <s:if test="#attr.tbllistParamBookType.isDetailField == 0">${fn:escapeXml(imDef:imGetText('MSG.LST.011'))}</s:if>
                </s:else>
                <s:else></s:else>
            </display:column>

            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.font.name.need.print'))}" property="font" style="text-align:center" headerClass="tct"/>
            <display:column escapeXml="true"  title="${fn:escapeXml(imDef:imGetText('MSG.font.size.need.print'))}" property="fontSize" style="text-align:center" headerClass="tct"/>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.font.type.need.print'))}" headerClass="tct" sortable="false" style="text-align:center">
                <s:if test="#attr.tbllistParamBookType.fontStyle == 0">regular</s:if>
                <s:else>
                    <s:if test="#attr.tbllistParamBookType.fontStyle == 1">bold</s:if>
                </s:else>
                <s:else>
                    <s:if test="#attr.tbllistParamBookType.fontStyle == 2">italic</s:if>
                </s:else>
                <s:if test="#attr.tbllistParamBookType.fontStyle == 3">bold italic</s:if>
                <s:else></s:else>
            </display:column>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.status'))}" headerClass="tct" sortable="false">
                <s:if test="#attr.tbllistParamBookType.status == 1">${fn:escapeXml(imDef:imGetText('MSG.active'))}</s:if>
                <s:if test="#attr.tbllistParamBookType.status == 0">${fn:escapeXml(imDef:imGetText('MSG.inactive'))}</s:if>
            </display:column>

            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generates.edit'))}"
                            sortable="false" headerClass="tct" style="width:50px;text-align:center">
                <s:url action="GetBookTypeAction!prepareEditParamBookType" id="URL1">
                    <s:param name="printerParamId" value="#attr.tbllistParamBookType.printerParamId"/>
                </s:url>
                <sx:a targets="popupBody" href="%{#URL1}" cssClass="cursorHand" executeScripts="true" parseContent="true">
                    <img src="${contextPath}/share/img/edit_icon.jpg"
                         title="<s:text name="MSG.generates.edit" />"
                         alt="<s:text name="MSG.generates.edit" />"/>
                </sx:a>
            </display:column>
            <display:column title="${fn:escapeXml(imDef:imGetText('MSG.delete'))}"
                            sortable="false" headerClass="tct" style="width:50px;text-align:center">
                    <s:url action="GetBookTypeAction!deleteParamBookType" id="URL2">
                        <s:token/>
                        <s:param name="printerParamId" value="#attr.tbllistParamBookType.printerParamId"/>
                        <s:param name="struts.token.name" value="'struts.token'"/>
                        <s:param name="struts.token" value="struts.token"/>
                    </s:url>
                <sx:a onclick="delParamBookType('%{#attr.tbllistParamBookType.printerParamId}')">
                    <img src="${contextPath}/share/img/delete_icon.jpg"
                         title="<s:text name="MSG.generates.delete" />"
                         alt="<s:text name="MSG.generates.delete" />"/>
                </sx:a>
            </display:column>
        </display:table>
    </sx:div>
</tags:imPanel>



<script type="text/javascript" language="javascript">
    var fieldName=document.getElementById('fieldName');
    var xCordinates=document.getElementById('XCoordinates');
    var yCoordinates=document.getElementById('YCoordinates');
    var width=document.getElementById('width');
    var height=document.getElementById('height');
    var font=document.getElementById('font');
    var fontSize=document.getElementById('fontSize');
    var fontStyle=document.getElementById('fontStyle');
    fieldName.focus();
    validate = function(){
        if(trim(fieldName.value) == "" ||trim(fieldName.value) ==null ){
    <%--$('displayResultMsgClient').innerHTML="Tên trường cần in không được để rỗng"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.001"/>';
                fieldName.focus();
                return false;
            }
            if(trim(fieldName.value) != null &&  trim(fieldName.value).length >100 ){
    <%--$('displayResultMsgClient').innerHTML="Tên trường cần in nhập vượt quá giới hạn"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.002"/>';
                fieldName.focus();
                return false;
            }
            if (isHtmlTagFormat(trim(fieldName.value))){
    <%--$('displayResultMsgClient').innerHTML="Tên trường cần in không được nhập định dạng thẻ HTML"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.003"/>';
                fieldName.focus();
                return false;
            }
            if(trim(xCordinates.value) == "0" ){
    <%--$('displayResultMsgClient').innerHTML="Hoành độ trái phải > 0"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.004"/>';
                xCordinates.focus();
                return false;
            }
            if(trim(xCordinates.value) != "" && !isInteger( trim(xCordinates.value) ) ){

    <%--$('displayResultMsgClient').innerHTML="Hoành độ trái phải là số nguyên dương"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.005"/>';
                xCordinates.focus();
                return false;
            }
            if(xCordinates.value != null &&  trim(xCordinates.value).length >10 ){
    <%--$('displayResultMsgClient').innerHTML="Hoành độ trái nhập vượt quá giới hạn"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.006"/>';
                xCordinates.focus();
                return false;
            }
            if(trim(yCoordinates.value) == "0" ){
    <%--$('displayResultMsgClient').innerHTML="Hoành độ phải phải > 0"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.007"/>';
                yCoordinates.focus();
                return false;
            }
            if(trim(yCoordinates.value) != "" && !isInteger( trim(yCoordinates.value) ) ){
    <%--$('displayResultMsgClient').innerHTML="Hoành độ phải phải là số nguyên dương"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.008"/>';
                YCoordinates.focus();
                return false;
            }
            if(trim(yCoordinates.value) != null &&  trim(yCoordinates.value).length >10 ){
    <%--$('displayResultMsgClient').innerHTML="Hoành độ phải nhập vượt quá giới hạn"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.009"/>';
                yCoordinates.focus();
                return false;
            }
            if(trim(width.value) == "0" ){
    <%--$('displayResultMsgClient').innerHTML="Chiều rộng phải > 0"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.010"/>';
                width.focus();
                return false;
            }
            if(trim(width.value) != "" && !isInteger( trim(width.value) ) ){
    <%--$('displayResultMsgClient').innerHTML="Chiều rộng phải là số nguyên dương"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.011"/>';
                width.focus();
                return false;
            }
            if(trim(width.value) != null &&  trim(width.value).length >10 ){
    <%--$('displayResultMsgClient').innerHTML="Chiều rộng nhập vượt quá giới hạn"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.012"/>';
                width.focus();
                return false;
            }
            if(trim(height.value) == "0" ){
                $('displayResultMsgClient').innerHTML="Chiều cao phải > 0"
                $('displayResultMsgClient').innerHTML= '<s:text name="ER.LI.001"/>';
                height.focus();
                return false;
            }
            if(trim(height.value) != "" && !isInteger( trim(height.value) ) ){
    <%--$('displayResultMsgClient').innerHTML="Chiều cao phải là số nguyên dương"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.013"/>';
                height.focus();
                return false;
            }
            if(trim(height.value) != null &&  trim(height.value).length >10 ){
    <%--$('displayResultMsgClient').innerHTML="Chiều cao nhập vượt quá giới hạn"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.014"/>';
                height.focus();
                return false;
            }
            if(trim(font.value) != null &&  trim(font.value).length >100 ){
    <%--$('displayResultMsgClient').innerHTML="Tên font nhập vượt quá giới hạn"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.015"/>';
                font.focus();
                return false;
            }
            if (isHtmlTagFormat(trim(font.value))){
    <%--$('displayResultMsgClient').innerHTML="Tên font không được nhập định dạng thẻ HTML"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.016"/>';
                font.focus();
                return false;
            }
            if(trim(fontSize.value) == "0" ){
    <%--$('displayResultMsgClient').innerHTML="Kích thước font phải > 0"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.017"/>';
                fontSize.focus();
                return false;
            }
            if(trim(fontSize.value) != "" && !isInteger( trim(fontSize.value) ) ){
    <%--$('displayResultMsgClient').innerHTML="Kích thước font phải là số nguyên dương"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.018"/>';
                fontSize.focus();
                return false;
            }
            if(trim(fontSize.value) != null &&  trim(fontSize.value).length >10 ){
    <%--$('displayResultMsgClient').innerHTML="Kích thước font nhập vượt quá giới hạn"--%>
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.LST.019"/>';
                fontSize.focus();
                return false;
            }
            return true;
        }
        delParamBookType = function(printerParamId) {
            var strConfirm = getUnicodeMsg('<s:text name="ERR.LST.021"/>');
            if (confirm(strConfirm)) {
                document.getElementById("printerParamForm").action="GetBookTypeAction!deleteParamBookType.do?printerParamId=" + printerParamId + "&" + token.getTokenParamString();
                document.getElementById("printerParamForm").submit();
            }
        }
        addParamBookType = function() {
            if (!validate()) {
                event.cancel = true;
            }
            document.getElementById("printerParamForm").action="GetBookTypeAction!addParamBookType.do?"+ token.getTokenParamString();
            document.getElementById("printerParamForm").submit();
        }
        editParamBookType = function() {
            if (!validate()) {
                event.cancel = true;
            }
            document.getElementById("printerParamForm").action="GetBookTypeAction!editParamBookType.do?"+ token.getTokenParamString();
            document.getElementById("printerParamForm").submit();
        }

        cancel = function() {

            document.getElementById("printerParamForm").action="GetBookTypeAction!prepareEditPrinterParamBookType.do";
            document.getElementById("printerParamForm").submit();
                }
</script>


