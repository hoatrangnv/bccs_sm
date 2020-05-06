<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : approveBankReceiptPrepage
    Created on : Oct 30, 2010, 10:52:58 AM
    Author     : Doan Thanh 8
    Desc       : man hinh chuan bi phe duyet giay nop tien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>


<s:form action="approveBankReceiptAction" theme="simple" method="post" id="searchBankReceiptExternalForm">
<s:token/>

    <tags:imPanel title="title.approveBankReceiptPrepage.page">
        <!-- phan hien thi thong tin message -->
        <div>
            <tags:displayResult property="message" id="message" propertyValue="messageParam" type="key"/>
        </div>

        <!-- phan link download bao cao -->
        <div>
            <s:if test="#request.excelFilePath != null">
                <script>
                    window.open('${contextPath}/${fn:escapeXml(excelFilePath)}','','toolbar=yes,scrollbars=yes');
                </script>
                <a href="${contextPath}/${fn:escapeXml(excelFilePath)}">
                    <tags:displayResult id="excelFilePathMessage" property="excelFilePathMessage" type="key"/>
                </a>
            </s:if>
        </div>

        <!-- phan tra cuu giay thong tin giay nop tien de duyet -->
        <div class="divHasBorder">
            <table class="inputTbl6Col">
                <tr>
                    <!-- ma don vi -->
                    <td style="width:13%;"><tags:label key="MSG.DET.069"/></td>
                    <td class="oddColumn" style="width:20%;">
                        <tags:imSearch codeVariable="searchBankReceiptExternalForm.shopCode"
                                       nameVariable="searchBankReceiptExternalForm.shopName"
                                       codeLabel="MSG.SAE.067" nameLabel="MSG.SAE.070"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListShop"
                                       getNameMethod="getShopName"/>
                    </td>
                    <!-- ngay nop tien -->
                    <td style="width:13%;"><tags:label key="MSG.DET.093"/></td>
                    <td class="oddColumn" style="width:20%;">
                        <tags:dateChooser property="searchBankReceiptExternalForm.bankReceiptDateFrom"
                                          id="searchBankReceiptExternalForm.bankReceiptDateFrom"
                                          styleClass="txtInputFull"/>
                    </td>
                    <td style="width:13%;"><tags:label key="MSG.DET.094"/></td>
                    <td>
                        <tags:dateChooser property="searchBankReceiptExternalForm.bankReceiptDateTo"
                                          id="searchBankReceiptExternalForm.bankReceiptDateTo"
                                          styleClass="txtInputFull"/>
                    </td>
                </tr>
                <tr>
                    <!-- so tai khoan -->
                    <td><tags:label key="MSG.DET.120"/></td>
                    <td class="oddColumn">
                        <s:textfield name="searchBankReceiptExternalForm.accountNo" id="searchBankReceiptExternalForm.accountNo" maxlength="25" cssClass="txtInputFull"/>
                    </td>
                    <!-- ma giay nop tien -->
                    <td><tags:label key="MSG.DET.073"/></td>
                    <td class="oddColumn">
                        <s:textfield name="searchBankReceiptExternalForm.bankReceiptCode" id="searchBankReceiptExternalForm.bankReceiptCode" maxlength="25" cssClass="txtInputFull"/>
                    </td>
                    <td>
                        <s:checkbox name="searchBankReceiptExternalForm.includeApproveRecord"/>
                        <label>${fn:escapeXml(imDef:imGetText('MSG.DET.152'))}</label>
                    </td>
                    <td>
                        <s:checkbox name="searchBankReceiptExternalForm.includeInapproveRecord"/>
                        <label>${fn:escapeXml(imDef:imGetText('MSG.DET.153'))}</label>
                    </td>
                </tr>
            </table>

            <!-- phan nut tac vu -->
            <div align="center" style="padding-top:3px; padding-bottom: 3px;">
                <tags:submit formId="searchBankReceiptExternalForm"
                             showLoadingText="true"
                             validateFunction="checkValidDataToSearch();"
                             cssStyle="width:80px;"
                             targets="bodyContent"
                             value="MSG.DET.082"
                             preAction="approveBankReceiptAction!searchBankReceipt.do"/>
            </div>
        </div>

        <!-- phan hien thi danh sach giay nop tien -->
        <div>
            <sx:div id="divApproveBankReceipt">
                <jsp:include page="listBankReceiptForApprove.jsp" />
            </sx:div>
        </div>

    </tags:imPanel>

</s:form>

<script>
    //kiem tra tinh hop le cua cac truong
    checkValidDataToSearch = function() {
        //trim cac truong can thiet
        $('searchBankReceiptExternalForm.shopCode').value = trim($('searchBankReceiptExternalForm.shopCode').value);
        $('searchBankReceiptExternalForm.accountNo').value = trim($('searchBankReceiptExternalForm.accountNo').value);
        $('searchBankReceiptExternalForm.bankReceiptCode').value = trim($('searchBankReceiptExternalForm.bankReceiptCode').value);


        //ma don vi khong duoc chua cac ky tu dac biet, chi duoc chua cac ky tu A-Z, a-z, 0-9, _
        if($('searchBankReceiptExternalForm.shopCode').value != "" && !isValidInput($('searchBankReceiptExternalForm.shopCode').value, false, false)) {
            $('message').innerHTML = '<s:text name="ERR.DET.079"/>';
            $('searchBankReceiptExternalForm.shopCode').select();
            $('searchBankReceiptExternalForm.shopCode').focus();
            return false;
        }
        
        return true;
    }
</script>
