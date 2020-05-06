<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : discountGroup
    Created on : Mar 24, 2009, 9:29:16 AM
    Author     : tamdt1
    Desc       : hien thi thong tin ve discountGroup
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="sdivDiscountGroup" cssStyle="padding:3px; ">
    <!-- hien thi message -->
    <div>
        <tags:displayResult property="message" id="message" type="key"/>
    </div>

    <!-- phan hien thi thong tin ve chiet khau -->
    <div class="divHasBorder">
        <s:form action="discountGroupAction" theme="simple" enctype="multipart/form-data"  method="post" id="discountGroupForm">
<s:token/>

            <s:hidden name="discountGroupForm.discountGroupId" id="discountGroupForm.discountGroupId"/>

            <!-- trong truong hop cac combobox bi disable phai them cac bien hidden de luu tru cac gia tri nay -->
            <%--<s:if test="!(#request.isAddMode || #request.isEditMode)">
                <s:hidden name="discountGroupForm.discountPolicy" id="discountGroupForm.discountPolicy"/>
                <s:hidden name="discountGroupForm.telecomServiceId" id="discountGroupForm.telecomServiceId"/>
                <s:hidden name="discountGroupForm.status" id="discountGroupForm.status"/>
            </s:if>--%>

            <table class="inputTbl4Col">
                <tr>                    
                    <td style="width:15%; "><tags:label key="MSG.GOD.312" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td class="oddColumn" style="width:35%; ">
                        <s:textfield name="discountGroupForm.name" id="discountGroupForm.name" maxlength="40" cssClass="txtInputFull" readonly="%{!(#request.isAddMode || #request.isEditMode)}"/>
                    </td>
                    <td style="width:15%; "><tags:label key="MSG.GOD.313" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td>
                        <tags:imSelect name="discountGroupForm.discountPolicy"
                                       id="discountGroupForm.discountPolicy"
                                       disabled="${fn:escapeXml(!(requestScope.isAddMode || requestScope.isEditMode))}"
                                       cssClass="txtInputFull"
                                       list="listDiscountPolicy"
                                       listKey="code" listValue="name"
                                       headerKey="" headerValue="MSG.GOD.317"/>
                    </td>
                </tr>
                <tr>
                    <td><tags:label key="MSG.GOD.093" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="discountGroupForm.telecomServiceId"
                                       id="discountGroupForm.telecomServiceId"
                                       disabled="${fn:escapeXml(!(requestScope.isAddMode || requestScope.isEditMode))}"
                                       cssClass="txtInputFull"
                                       list="listTelecomServices"
                                       listKey="telecomServiceId" listValue="telServiceName"
                                       headerKey="" headerValue="MSG.GOD.302"/>
                    </td>
                    <td><tags:label key="MSG.GOD.013" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td>
                        <tags:imSelect name="discountGroupForm.status"
                                       id="discountGroupForm.status"
                                       disabled="${fn:escapeXml(!(requestScope.isAddMode || requestScope.isEditMode))}"
                                       cssClass="txtInputFull"
                                       list="1:MSG.GOD.002, 0:MSG.GOD.003"
                                       headerKey="" headerValue="MSG.GOD.189"/>
                    </td>
                </tr>
                <tr>
                    <!-- phuong thuc CK -->
                    <td><tags:label key="MSG.LST.028" required="${fn:escapeXml((requestScope.isAddMode || requestScope.isEditMode))}"/></td>
                    <td class="oddColumn">
                        <tags:imSelect name="discountGroupForm.discountMethod"
                                       id="discountGroupForm.discountMethod"
                                       disabled="${fn:escapeXml(!(requestScope.isAddMode || requestScope.isEditMode))}"
                                       cssClass="txtInputFull"
                                       headerKey="" headerValue="--Select discount method--"
                                       list="1:MSG.LST.029,2:MSG.LST.030"/>
                    </td>
                    <td><tags:label key="MSG.GOD.031"/></td>
                    <td>
                        <s:textfield name="discountGroupForm.notes" id="discountGroupForm.notes" maxlength="50" readonly="%{!(#request.isAddMode || #request.isEditMode)}" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </s:form>

        <!-- phan nut tac vu -->
        <s:if test="#request.isAddMode || #request.isEditMode">
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnAddDiscountGroup"
                             targets="sdivDiscountGroup"
                             formId="discountGroupForm"
                             cssStyle="width:80px;"
                             confirm="true" 
                             confirmText="MSG.GOD.017"
                             value="MSG.GOD.016"
                             preAction="discountGroupAction!addOrEditDiscountGroup.do"
                             validateFunction="checkValidDataToAddOrEditDiscountGroup()"
                             showLoadingText="true"/>

                <tags:submit id="btnDisplayDiscountGroup"
                             targets="divDisplayInfo"
                             formId="discountGroupForm"
                             cssStyle="width:80px;"
                             value="MSG.GOD.018"
                             preAction="discountGroupAction!displayDiscountGroup.do"
                             showLoadingText="true"/>
            </div>
            <script language="javascript">
                disableTab('sxdivDiscountDetail');
                disableTab('sxdivDiscountGoods');
            </script>
        </s:if>
        <s:else>
            <div align="center" style="vertical-align:top; ">
                <tags:submit id="btnPrepareAddDiscountGroup"
                             formId="discountGroupForm"
                             showLoadingText="true"
                             cssStyle="width:80px;"
                             targets="sdivDiscountGroup"
                             value="MSG.GOD.019"
                             preAction="discountGroupAction!prepareAddDiscountGroup.do"/>

                <!-- chi hien thi sua, xoa trong truong hop da co it nhat 1 phan tu -->
                <s:if test="#attr.discountGroupForm.discountGroupId.compareTo(0L) > 0">
                    <tags:submit id="btnPrepareEditDiscountGroup"
                                 formId="discountGroupForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="sdivDiscountGroup"
                                 value="MSG.GOD.020"
                                 preAction="discountGroupAction!prepareEditDiscountGroup.do"/>

                    <tags:submit id="btnDeleteDiscountGroup"
                                 formId="discountGroupForm"
                                 showLoadingText="true"
                                 cssStyle="width:80px;"
                                 targets="sdivDiscountGroup"
                                 confirm="true"
                                 confirmText="MSG.GOD.314"
                                 value="MSG.GOD.021"
                                 preAction="discountGroupAction!delDiscountGroup.do"/>
                    <script language="javascript">
                        enableTab('sxdivDiscountDetail');
                        enableTab('sxdivDiscountGoods');
                    </script>
                </s:if>
                <s:else>
                    <input type="button" value=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.020'))}" disabled style="width:80px;">
                    <input type="button" value=" ${fn:escapeXml(imDef:imGetText('MSG.GOD.021'))}" disabled style="width:80px;">
                    <script language="javascript">
                        disableTab('sxdivDiscountDetail');
                        disableTab('sxdivDiscountGoods');
                    </script>
                </s:else>
            </div>

        </s:else>
    </div>

    <!-- danh sach nhom CK -->
    <div>
        <fieldset class="imFieldset">
            <legend> ${fn:escapeXml(imDef:imGetText('MSG.GOD.315'))}</legend>
            <br /><br /><br /><br /><br />
        </fieldset>
    </div>

</sx:div>

<script language="javascript">

    //
    checkValidDataToAddOrEditDiscountGroup = function() {
        if(checkRequiredFields() && checkValidFields()) {
            return true;
        } else {
            return false;
        }
    }


    //ham kiem tra cac truong bat buoc
    checkRequiredFields = function() {
        if(trim($('discountGroupForm.name').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.022')"/>';
            //$('message').innerHTML = '!!!Lỗi. Trường tên chiết khấu không được để trống';
            $('discountGroupForm.name').select();
            $('discountGroupForm.name').focus();
            return false;
        }
        if(trim($('discountGroupForm.discountPolicy').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.023')"/>';
            //$('message').innerHTML = '!!!Lỗi. Chọn chính sách chiết khấu';
            $('discountGroupForm.discountPolicy').focus();
            return false;
        }
        if(trim($('discountGroupForm.telecomServiceId').value) == "") {
            $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.003')"/>';
            //$('message').innerHTML= '!!!Lỗi. Chọn dịch vụ viễn thông';
            $('discountGroupForm.telecomServiceId').focus();
            return false;
        }

        return true;
    }

    //kiem tra ma khong duoc chua cac ky tu dac biet
    checkValidFields = function() {
        if(isHtmlTagFormat(trim($('discountGroupForm.name').value))) {
            //ten chiet khau khong duoc chua cac the HTML
            $('message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.037')"/>';
            $('discountGroupForm.name').select();
            $('discountGroupForm.name').focus();
            return false;
        }

        if(isHtmlTagFormat(trim($('discountGroupForm.notes').value))) {
            //ten chiet khau khong duoc chua cac the HTML
            $('message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.006')"/>';
            $('discountGroupForm.notes').select();
            $('discountGroupForm.notes').focus();
            return false;
        }


        //trim cac truong can thiet
        $('discountGroupForm.name').value = trim($('discountGroupForm.name').value);
        $('discountGroupForm.discountPolicy').value = trim($('discountGroupForm.discountPolicy').value);
        $('discountGroupForm.telecomServiceId').value = trim($('discountGroupForm.telecomServiceId').value);
        $('discountGroupForm.notes').value = trim($('discountGroupForm.notes').value);


        return true;
    }

</script>


