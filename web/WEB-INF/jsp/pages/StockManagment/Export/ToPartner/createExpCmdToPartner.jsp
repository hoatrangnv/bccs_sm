<%-- 
    Document   : createExpCmdToPartner
    Created on : Sep 2, 2010, 9:01:59 AM
    Author     : Doan Thanh 8
    Desc       : tao lenh xuat kho cho doi tac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
            String pageId = request.getParameter("pageId");
            request.setAttribute("listReason", request.getSession().getAttribute("listReason" + pageId));
%>

<tags:imPanel title="title.createExpCmdToPartner.page">
    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
    </div>

    <!-- phan thong tin ve lenh xuat -->
    <div class="divHasBorder">
        <s:form action="createExpCmdToPartnerAction" theme="simple" enctype="multipart/form-data"  method="POST" id="exportStockForm">
<s:token/>

            <table class="inputTbl6Col">
                <tr>
                    <!-- ma lenh xuat -->
                    <td><tags:label key="MSG.GOD.150" required="true"/></td>
                    <td class="oddColumn">
                        <s:textfield theme="simple" maxlength="20" name="exportStockForm.cmdExportCode" readonly="true" id="exportStockForm.cmdExportCode" cssClass="txtInputFull"/>
                    </td>
                    <!-- nguoi xuat -->
                    <td><tags:label key="MSG.GOD.155"/></td>
                    <td class="oddColumn">
                        <s:textfield theme="simple" name="exportStockForm.sender" id="exportStockForm.sender" cssClass="txtInputFull" readonly="true"  />
                    </td>
                    <!-- ngay xuat -->
                    <td><tags:label key="MSG.GOD.159"/></td>
                    <td>
                        <tags:dateChooser readOnly="true" property="exportStockForm.dateExported" id="exportStockForm.dateExported" styleClass="txtInputFull" />
                    </td>
                </tr>
                <tr>
                    <!-- kho xuat hang -->
                    <td><tags:label key="MSG.GOD.138" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="exportStockForm.shopExportCode" nameVariable="exportStockForm.shopExportName"
                                       codeLabel="MSG.RET.066" nameLabel="MSG.RET.067"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getNameShop"
                                       readOnly="true"/>
                    </td>
                    <!-- doi tac xuat hang -->
                    <td><tags:label key="MSG.partner" required="true"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="exportStockForm.shopImportedCode" nameVariable="exportStockForm.shopImportedName"
                                       codeLabel="MSG.partner.code" nameLabel="MSG.partner.name"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListPartner"
                                       getNameMethod="getPartnerName"/>
                    </td>
                    <!-- ly do xuat -->
                    <td><tags:label key="MSG.GOD.158" required="true"/></td>
                    <td>
                        <tags:imSelect name="exportStockForm.reasonId"
                                       id="exportStockForm.reasonId"
                                       cssClass="txtInputFull"
                                       theme="simple"
                                       headerKey="" headerValue="MSG.GOD.147"
                                       list="listReason"
                                       listKey="reasonId" listValue="reasonName"/>
                    </td>
                </tr>
                <tr>
                    <!-- ghi chu -->
                    <td><tags:label key="MSG.GOD.031"/></td>
                    <td class="oddColumn">
                        <s:textfield theme="simple" name="exportStockForm.note"  id="exportStockForm.note" maxlength="500" cssClass="txtInputFull"/>
                    </td>
                </tr>
            </table>
        </s:form>
    </div>

    <!-- phan thong tin ve danh sach hang hoa trong lenh xuat -->
    <div>
        <jsp:include page="listGoodsInExpCmdToPartner.jsp"/>
    </div>

    <!-- phan nut tac vu -->
    <div style="width:100%; margin-top: 5px;" align="center">
        <tags:submit id="btnCreateExpCmdToPartner"
                     formId="exportStockForm"
                     confirm="true"
                     confirmText="MSG.confirm.create.cmd.exp.store"
                     showLoadingText="true"
                     targets="bodyContent"
                     value="MSG.createExportCmd"
                     validateFunction="checkValidExportStockForm()"
                     cssStyle="width: 100px;"
                     preAction="createExpCmdToPartnerAction!createExpCmdToPartner.do"/>
    </div>

</tags:imPanel>

<script>
    $('exportStockForm.cmdExportCode').select();
    $('exportStockForm.cmdExportCode').focus();


    checkValidExportStockForm =function(){
        //ma lenh xuat khong duoc de trong
        if(trim($('exportStockForm.cmdExportCode').value) == "") {
            //$('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.STK.093')"/>';
            $('message').innerHTML = '<s:text name="ERR.STK.093"/>';
            $('exportStockForm.cmdExportCode').select();
            $('exportStockForm.cmdExportCode').focus();
            return false;
        }

        //ma lenh xuat ko duoc chua cac ky tu dac biet
        if(!isValidInput($('exportStockForm.cmdExportCode').value, false, false)) {
            //            $('message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.STK.094')"/>';
            $('message').innerHTML = '<s:text name="ERR.STK.094"/>';
            $('exportStockForm.cmdExportCode').select();
            $('exportStockForm.cmdExportCode').focus();
            return false;
        }

        //kho xuat hang khong duoc de trong
        if(trim($('exportStockForm.shopExportCode').value) == "") {
            //            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.STK.121')"/>';
            $('message').innerHTML = '<s:text name="ERR.STK.121"/>';
            $('exportStockForm.shopExportCode').select();
            $('exportStockForm.shopExportCode').focus();
            return false;
        }

        //kho xuat hang khong duoc chua cac ky tu dac biet
        if(!isValidInput($('exportStockForm.shopExportCode').value, false, false)) {
            //            $('message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.STK.122')"/>';
            $('message').innerHTML = '<s:text name="ERR.STK.122"/>';
            $('exportStockForm.shopExportCode').select();
            $('exportStockForm.shopExportCode').focus();
            return false;
        }

        //ma doi tac khong duoc de trong
        if(trim($('exportStockForm.shopImportedCode').value) == "") {
            //            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.STK.123')"/>';
            $('message').innerHTML = '<s:text name="ERR.STK.123"/>';
            $('exportStockForm.shopImportedCode').select();
            $('exportStockForm.shopImportedCode').focus();
            return false;
        }

        //ma doi tac khogn duoc chua cac ky tu dac biet
        if(!isValidInput($('exportStockForm.shopImportedCode').value, false, false)) {
            //$('message' ).innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.STK.124')"/>';
            $('message').innerHTML = '<s:text name="ERR.STK.124"/>';
            $('exportStockForm.shopImportedCode').select();
            $('exportStockForm.shopImportedCode').focus();
            return false;
        }

        //chon ly do xuat kho
        if(trim($('exportStockForm.reasonId').value) == "") {
            //            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.STK.015')"/>';
            $('message').innerHTML = '<s:text name="ERR.STK.015"/>';
            $('exportStockForm.reasonId').focus();
            return false;
        }

        //truong ghi chu khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('exportStockForm.note').value))) {
            //            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getError('ERR.GOD.006')"/>';
            $('message').innerHTML = '<s:text name="ERR.GOD.006"/>';
            $('exportStockForm.note').select();
            $('exportStockForm.note').focus();
            return false;
        }

        return true;

    }


</script>
