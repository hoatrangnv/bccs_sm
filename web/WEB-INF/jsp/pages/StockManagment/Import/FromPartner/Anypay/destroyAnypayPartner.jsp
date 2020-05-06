<%-- 
    Document   : createExpNoteToPartner
    Created on : Sep 2, 2010, 9:02:40 AM
    Author     : Doan Thanh 8
    Desc       : tao phieu xuat kho cho doi tac
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="display" uri="VTdisplaytaglib" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%@page import="com.guhesan.querycrypt.QueryCrypt" %>

<tags:imPanel title="anypay.cards.destroy.info">

    <!-- phan tim kiem lenh xuat de tao phieu xuat -->
    <div class="divHasBorder">
        <s:form action="anypayPartnerAction" theme="simple" enctype="multipart/form-data"  method="POST" id="searchExpStockForm">
            <s:hidden name="searchExpStockForm.actionType" />
            <s:hidden name="searchExpStockForm.transType" />
            <s:hidden name="searchExpStockForm.fromOwnerType" id="fromOwnerType"/>
            <s:hidden name="searchExpStockForm.fromOwnerId" id="fromOwnerId"/>
            <s:hidden name="searchExpStockForm.toOwnerType" id="toOwnerType"/>
            <s:hidden name="searchExpStockForm.toOwnerId" id="toOwnerId"/>
            <s:hidden name="searchExpStockForm.shopExpType" value="1"/>
            <s:hidden name="searchExpStockForm.reasonId" id="reasonId"/>

            <table class="inputTbl6Col" >
                <tr>
                    <!-- ma lenh -->
                    <td><tags:label key="MSG.GOD.205"/></td>
                    <td class="oddColumn">
                        <s:textfield name="searchExpStockForm.actionCode" id="searchExpStockForm.actionCode" cssClass="txtInputFull"/>
                    </td>
                    <!-- trang thai giao dich -->
                    <td></td>
                    <td class="oddColumn">
                    </td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <!-- doi tac xuat hang -->
                    <td><tags:label key="MSG.partner"/></td>
                    <td class="oddColumn">
                        <tags:imSearch codeVariable="searchExpStockForm.toOwnerCode" nameVariable="searchExpStockForm.toOwnerName"
                                       codeLabel="MSG.partner.code" nameLabel="MSG.partner.name"
                                       searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                       searchMethodName="getListPartner"
                                       getNameMethod="getPartnerName"/>
                    </td>
                    <!-- tu ngay -->
                    <td><tags:label key="MSG.GOD.117"/></td>
                    <td class="oddColumn">
                        <tags:dateChooser property="searchExpStockForm.fromDate" id="searchExpStockForm.fromDate" styleClass="txtInputFull"/>
                    </td>
                    <td><tags:label key="MSG.GOD.118"/></td>
                    <td>
                        <tags:dateChooser property="searchExpStockForm.toDate" id="searchExpStockForm.toDate" styleClass="txtInputFull"/>
                    </td>
                </tr>
            </table>

            <div style="width:100%; margin-top: 5px; margin-bottom: 5px;" align="center">
                <tags:submit formId="searchExpStockForm"
                             showLoadingText="true"
                             targets="divCreateImpCmdFromPartner"
                             cssStyle="width: 80px;"
                             value="MSG.GOD.009"
                             preAction="anypayPartnerAction!searchDestroyCmdAnypayPartner.do"/>
                <input type="button" style="width:120px" onclick="prepareDestroyAnypayPartner()" value='<s:property value="getText('btn.anypay.destroy')"/>'/>
            </div>
</s:form>

    <!-- phan hien thi ket qua tim kiem + lap phieu xuat kho cho doi tac -->
    <sx:div id="divCreateImpCmdFromPartner">
        <jsp:include page="destroyListCmdAnypayPartner.jsp"/>
    </sx:div>
            
    </div>

</tags:imPanel>
    <script type="text/javascript" language="javascript">
        prepareDestroyAnypayPartner=function(){
            openDialog("${contextPath}/anypayPartnerAction!preparePopupDestroyAnypayPartner.do?" 
                    + token.getTokenParamString() , screen.width,screen.height,false);
        };    
        refreshAfterRequest = function(){
        gotoAction("divCreateImpCmdFromPartner", "anypayPartnerAction!refreshAfterDestroy.do?" + token.getTokenParamString());
    }
    </script>