<%-- 
    Document   : createCmdAnypayPartner.jsp
    Created on : 09/08/2013
    Author     : tutm1
    Desc       : tao lenh nhap anypay tu doi tac
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

<s:if test="#request.success == 'true'">
    <script>
        window.opener.refreshAfterRequest();
        window.close();
    </script>
</s:if>

<tags:imPanel title="partner.anypay.create.command">
    <!-- phan hien thi thong tin message -->
    <div>
        <!-- phan thong tin ve lenh xuat -->
        <div class="divHasBorder">
            <s:form action="anypayPartnerAction" theme="simple" enctype="multipart/form-data"  method="POST" id="importStockForm">
                <s:hidden name="importStockForm.actionId" id="importStockForm.actionId"/>
                <table class="inputTbl6Col">
                    <tr>
                        <!-- ma lenh nhap -->
                        <td><tags:label key="MSG.GOD.127" required="true"/></td>
                        <td class="oddColumn">
                            <s:textfield theme="simple" maxlength="20" name="importStockForm.cmdImportCode" readonly="true" id="importStockForm.cmdImportCode" cssClass="txtInputFull"/>
                        </td>
                        <!-- nguoi lap -->
                        <td><tags:label key="MSG.importStaff"/></td>
                        <td class="oddColumn">
                            <s:textfield theme="simple" name="importStockForm.sender" id="importStockForm.sender" cssClass="txtInputFull" readonly="true"  />
                        </td>
                        <!-- ngay lap -->
                        <td><tags:label key="MSG.GOD.128"/></td>
                        <td>
                            <tags:dateChooser readOnly="true" property="importStockForm.dateImported" id="importStockForm.dateImported" styleClass="txtInputFull" />
                        </td>
                    </tr>
                    <tr>
                        <!-- kho nhan hang -->
                        <td><tags:label key="MSG.GOD.139" required="true"/></td>
                        <td class="oddColumn">
                            <tags:imSearch codeVariable="importStockForm.shopImportCode" nameVariable="importStockForm.shopImportName"
                                           codeLabel="MSG.RET.066" nameLabel="MSG.RET.067"
                                           searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                           searchMethodName="getListShop"
                                           getNameMethod="getNameShop"
                                           readOnly="true"/>
                        </td>
                        <!-- doi tac xuat hang -->
                        <td><tags:label key="MSG.partner" required="true"/></td>
                        <td class="oddColumn">
                            <tags:imSearch codeVariable="importStockForm.shopExportCode" nameVariable="importStockForm.shopExportName"
                                           codeLabel="MSG.partner.code" nameLabel="MSG.partner.name"
                                           searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                           searchMethodName="getListPartner"
                                           getNameMethod="getPartnerName"/>
                        </td>
                        <!-- ly do xuat -->
                        <td><tags:label key="MSG.reasonImport" required="true"/></td>
                        <td>
                            <tags:imSelect name="importStockForm.reasonId"
                                           id="importStockForm.reasonId"
                                           cssClass="txtInputFull"
                                           theme="simple"
                                           headerKey="" headerValue="MSG.GOD.147"
                                           list="listReason"
                                           listKey="reasonId" listValue="reasonName"
                                           disabled="true"
                                           />
                        </td>
                    </tr>
                    <tr>
                        <!-- ghi chu -->
                        <td><tags:label key="MSG.GOD.031"/></td>
                        <td class="oddColumn">
                            <s:textfield theme="simple" name="importStockForm.note"  id="importStockForm.note" maxlength="250" cssClass="txtInputFull"/>
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>
        

        <!-- phan thong tin ve danh sach hang hoa trong lenh nhap -->
        <div>
            <jsp:include page="listGoodsInCmdAnypayPartner.jsp"/>
        </div>

        <!-- phan nut tac vu -->
        <div style="width:100%; margin-top: 5px;" align="center">
            <s:if test="importStockForm.actionId !=null">
                <input type="button" value="${imDef:imGetText('MSG.createExportCmd')}" disabled style="width: 150px;"/>
            </s:if>
            <s:else>
                <tags:submit id="btnCreateImpCmdToPartner"
                             formId="importStockForm"
                             confirm="true"
                             confirmText="MSG.confirm.create.cmd.import.store"
                             showLoadingText="false"
                             targets="popupBody"
                             value="MSG.createImportCmd"
                             validateFunction="checkValidImportStockForm()"
                             cssStyle="width: 100px;"
                             preAction="anypayPartnerAction!createCmdAnypayPartner.do"/>
            </s:else>
        </div>
    </tags:imPanel>

    <script>
        $('importStockForm.cmdImportCode').select();
        $('importStockForm.cmdImportCode').focus();


        checkValidImportStockForm = function() {
            //ma lenh xuat khong duoc de trong
            if (trim($('importStockForm.cmdImportCode').value) == "") {
                //$('message').innerHTML = '<s:property value="getError('ERR.STK.093')"/>';
                $('message').innerHTML = '<s:text name="ERR.STK.093"/>';
                $('importStockForm.cmdImportCode').select();
                $('importStockForm.cmdImportCode').focus();
                return false;
            }

            //ma lenh xuat ko duoc chua cac ky tu dac biet
            if (!isValidInput($('importStockForm.cmdImportCode').value, false, false)) {
                //            $('message' ).innerHTML = '<s:property value="getError('ERR.STK.094')"/>';
                $('message').innerHTML = '<s:text name="ERR.STK.094"/>';
                $('importStockForm.cmdImportCode').select();
                $('importStockForm.cmdImportCode').focus();
                return false;
            }

            //kho nhap hang khong duoc de trong
            if (trim($('importStockForm.shopImportCode').value) == "") {
                //            $('message').innerHTML = '<s:property value="getError('stock.import.not.empty')"/>';
                $('message').innerHTML = '<s:text name="stock.import.not.empty"/>';
                $('importStockForm.shopImportCode').select();
                $('importStockForm.shopImportCode').focus();
                return false;
            }

            //kho nhap hang khong duoc chua cac ky tu dac biet
            if (!isValidInput($('importStockForm.shopImportCode').value, false, false)) {
                //            $('message' ).innerHTML = '<s:property value="getError('stock.import.not.special')"/>';
                $('message').innerHTML = '<s:text name="stock.import.not.special"/>';
                $('importStockForm.shopImportCode').select();
                $('importStockForm.shopImportCode').focus();
                return false;
            }

            //ma doi tac khong duoc de trong
            if (trim($('importStockForm.shopExportCode').value) == "") {
                //            $('message').innerHTML = '<s:property value="getError('ERR.STK.123')"/>';
                $('message').innerHTML = '<s:text name="ERR.STK.123"/>';
                $('importStockForm.shopExportCode').select();
                $('importStockForm.shopExportCode').focus();
                return false;
            }

            //ma doi tac khogn duoc chua cac ky tu dac biet
            if (!isValidInput($('importStockForm.shopExportCode').value, false, false)) {
                //$('message' ).innerHTML = '<s:property value="getError('ERR.STK.124')"/>';
                $('message').innerHTML = '<s:text name="ERR.STK.124"/>';
                $('importStockForm.shopExportCode').select();
                $('importStockForm.shopExportCode').focus();
                return false;
            }

            //chon ly do nhap kho
            if (trim($('importStockForm.reasonId').value) == "") {
                //            $('message').innerHTML = '<s:property value="getError('stock.import.reason.not.choice')"/>';
                $('message').innerHTML = '<s:text name="stock.import.reason.not.choice"/>';
                $('importStockForm.reasonId').focus();
                return false;
            }

            //truong ghi chu khong duoc chua cac the HTML
            if (isHtmlTagFormat(trim($('importStockForm.note').value))) {
                //            $('message').innerHTML = '<s:property value="getError('ERR.GOD.006')"/>';
                $('message').innerHTML = '<s:text name="ERR.GOD.006"/>';
                $('importStockForm.note').select();
                $('importStockForm.note').focus();
                return false;
            }

            return true;

        }
    </script>
