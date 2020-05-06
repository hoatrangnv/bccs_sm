<%-- 
    Document   : ImportStock.tag
    Created on : Feb 24, 2009, 10:57:01 AM
    Author     : ThanhNC
--%>

<%@tag description="Cac thong tin nhap kho" pageEncoding="UTF-8"%>
<%--
<%@taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
--%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>

<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://ajaxtags.org/tags/ajax" prefix="ajax" %>

<%@attribute name="shopRecType"%>

<%@attribute name="readOnly"%>
<%@attribute name="disabled"%>
<%@attribute name="viewOnly"%>
<%@attribute name="type"%>
<%@attribute name="subForm"%>
<%@attribute name="height"%>


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
        String title = "MSG.GOD.126";
        String code = baseDAOAction.getText("MSG.GOD.127");
        String dateCreate = baseDAOAction.getText("MSG.GOD.128");
        String errorAction = baseDAOAction.getText("MSG.GOD.129");
        String errorAction2 = baseDAOAction.getText("MSG.GOD.130");

        if ("note".equals(type)) {
            title = "MSG.GOD.131";
            code = baseDAOAction.getText("MSG.GOD.132");
            dateCreate = baseDAOAction.getText("MSG.GOD.133");
            errorAction = baseDAOAction.getText("MSG.GOD.134");
            errorAction2 = baseDAOAction.getText("MSG.GOD.135");
        }
        request.setAttribute("require", require);
        request.setAttribute("title", title);
        request.setAttribute("code", code);
        request.setAttribute("dateCreate", dateCreate);
        String height1 = height == null ? "auto" : height;
        String style = "height:" + height1;
        request.setAttribute("style", style);
%>


<tags:imPanel title="${title}">
    <div style="${style}">
        <s:hidden name="%{#attr.subForm+'.actionId'}"   id="%{#attr.subForm+'.actionId'}"  />
        <table class="inputTbl4Col" cellpadding="0" cellspacing="4">
            <tr>
                <td  class="label">
                    ${code}${require}
                </td>
                <td class="text">
                    <s:textfield maxlength="20" theme="simple" name="%{#attr.subForm+'.cmdImportCode'}" id="%{#attr.subForm+'.cmdImportCode'}" cssClass="txtInputFull" readonly="true"/>
                </td>
                <td  class="label">
                    <tags:label key="MSG.GOD.136"  required='${require}'/>
                </td>
                <td class="text">
                    <s:textfield  theme="simple" name="%{#attr.subForm+'.receiver'}"  id="%{#attr.subForm+'.receiver'}"  cssClass="txtInputFull" readonly="true"  />
                    <s:hidden name="%{#attr.subForm+'.receiverId'}"/>
                </td>
            </tr>
            <tr>

                <s:if test="shopRecType == 'staff'">

                    <td  class="label">
                        <tags:label key="MSG.GOD.137" required="${require}" />
<!--                        $_{require}-->
                    </td>
                    <td class="text" colspan="3">
                        <s:select  theme="simple"  name="%{#attr.subForm+'.shopExportId'}"  id="%{#attr.subForm+'.shopExportId'}"  cssClass="txtInputFull"
                                   list="#request.lstStaff" listKey="staffId" listValue="name" disabled="#attr.disabled">
                        </s:select>
                    </td>
                    <td colspan="2">&nbsp;</td>

                </s:if>
                <s:else>

                    <td  class="label">
                        <tags:label key="MSG.GOD.138" />
<!--                        Kho xuất hàng-->
                    </td>
                    <td class="text" colspan="3">
                        <s:hidden name="%{#attr.subForm+'.shopExportId'}"   id="%{#attr.subForm+'.shopExportId'}"  />
                        <table width="100%">
                            <tr>
                                <td width="30%">
                                    <s:textfield cssClass="txtInputFull" name="%{#attr.subForm+'.shopExportCode'}"  theme="simple"
                                                 id="%{#attr.subForm+'.shopExportCode'}" readonly="true"/>
                                </td>
                                <td width="70%">
                                    <s:textfield  theme="simple"  name="%{#attr.subForm+'.shopExportName'}" id="%{#attr.subForm+'.shopExportName'}"  cssClass="txtInputFull" readonly="true"/>
                                </td>
                            </tr>
                        </table>

                    </td>

                </s:else>
            </tr>
            <tr>
                <td  class="label">
                    <tags:label key="MSG.GOD.139" />
<!--                    Kho nhận hàng-->
                </td>
                <td class="text" colspan="3">
                    <s:hidden name="%{#attr.subForm+'.shopImportId'}"   id="%{#attr.subForm+'.shopImportId'}"  />
                    <table width="100%">
                        <tr>
                            <td width="30%">
                                <s:textfield cssClass="txtInputFull" name="%{#attr.subForm+'.shopImportCode'}"  theme="simple"
                                             id="%{#attr.subForm+'.shopImportCode'}" readonly="true"/>
                            </td>
                            <td width="70%">
                                <s:textfield  theme="simple"  name="%{#attr.subForm+'.shopImportName'}" id="%{#attr.subForm+'.shopImportName'}"  cssClass="txtInputFull" readonly="true"/>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td  class="label">
                    <tags:label key="MSG.GOD.140" required="${require}" />
<!--                    Lý do nhập-->
                </td>
                <td class="text">
                    <tags:imSelect list="lstReasonImp" theme="simple"
                                   name="${pageScope.subForm}.reasonId"
                                   id="${pageScope.subForm}.reasonId"
                                   cssClass="txtInputFull"
                                   disabled="${pageScope.disabled}" listKey="reasonId" listValue="reasonName"
                                   headerKey="" headerValue="MSG.GOD.147" />
                    
                    <%--s:select  theme="simple"  name="%{#attr.subForm+'.reasonId'}"  id="%{#attr.subForm+'.reasonId'}" cssClass="txtInputFull"
                               list="%{#request.lstReasonImp== null? #{}:#request.lstReasonImp}" disabled="%{#attr.disabled}" listKey="reasonId" listValue="reasonName"
                               headerKey="" headerValue="--Chọn lý do--" /--%>

                    <s:if test="#attr.disabled=='true'">
                        <s:hidden name="%{#attr.subForm+'.reasonId'}"/>
                    </s:if>
                </td>
                <td  class="label">
                    ${dateCreate}
                </td>
                <td class="text">
                    <!--tags:dateChooser property="${property}dateImported" id="${property}dateImported" styleClass="txtInput"/-->
                    <tags:dateChooser readOnly="true" property="${property}dateImported" id="${property}dateImported" styleClass="txtInputFull"/>
                </td>
            </tr>
            <tr>
                <td  class="label">
                    <tags:label key="MSG.GOD.141" />
<!--                    Ghi chú-->
                </td>
                <td colspan="3"  class="text">
                    <s:textfield theme="simple" name="%{#attr.subForm+'.note'}" maxlength="500"  id="%{#attr.subForm+'.note'}" cssClass="txtInputFull" readonly="%{#attr.readOnly}"/>
                </td>
            </tr>
        </table>
    </div>
</tags:imPanel>
<script>
    validateForm =function(target){
        var formName='<s:property value="#attr.subForm"/>';
        var cmdImportCode= document.getElementById(formName+".cmdImportCode");
        var receiver= document.getElementById(formName+".receiver");
        var shopExportId= document.getElementById(formName+".shopExportId");
        var reasonId= document.getElementById(formName+".reasonId");
        var dateImported= dojo.widget.byId(formName+".dateImported");
        if(cmdImportCode.value ==null || trim(cmdImportCode.value)==''){
            $(target).innerHTML='${errorAction}';
            cmdImportCode.focus();
            return false;
        }
        cmdImportCode.value=trim(cmdImportCode.value);
        if(!isFormalCharacter(cmdImportCode.value)){
            $(target).innerHTML='${errorAction2}';
            cmdImportCode.focus();
            return false;
        }
        //Truong hop xac nhan nhap tu nhan vien --> can chon kho nhan vien
        if(shopExportId!=null){
            if(shopExportId.value ==null || trim(shopExportId.value)==''){
                $(target).innerHTML='<s:text name="MSG.GOD.142"/>';
//                $(target).innerHTML='Chưa chọn kho xuất hàng';
                shopExportId.focus();
                return false;
            }
        }

        if(reasonId.value ==null || trim(reasonId.value)==''){
        $(target).innerHTML='<s:text name="MSG.GOD.143"/>';
//            $(target).innerHTML='Chưa chọn lý do nhập';
            reasonId.focus();
            return false;
        }
        if(dateImported.domNode.childNodes[1].value ==null || dateImported.domNode.childNodes[1].value==''){
            $(target).innerHTML='<s:text name="MSG.GOD.144"/>';
//            $(target).innerHTML='Chưa nhập ngày nhập kho';
            dateImported.domNode.childNodes[1].focus();
            return false;
        }
        if(!isDateFormat(dateImported.domNode.childNodes[1].value)){
            $(target).innerHTML='<s:text name="MSG.GOD.145"/>';
//            $(target).innerHTML='Ngày nhập không đúng định dạng.';
            dateImported.domNode.childNodes[1].focus();
            return false;
        }
        var note= document.getElementById(formName+".note");
        note.value=note.value.trim();
        if(isHtmlTagFormat(note.value)){
            $(target).innerHTML='<s:text name="MSG.GOD.146"/>';
//            $(target).innerHTML='Trường ghi chú không được nhập thẻ HTML';
            note.focus();
            return false;
        }
        return true;
    }
</script>

