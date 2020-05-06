<%-- 
    Document   : searchDebitOfStaff
    Created on : May 29, 2013, 7:38:08 PM
    Author     : thinhph2_s
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<tags:imPanel title="title.look.up.debit.shop">
    <s:form id="inputForm" action="searchDebitOfStaffAction" theme="simple" method="post">
        <fieldset class="imFieldset">
            <legend>${imDef:imGetText('MSG.find.info')}</legend>
            <table class="inputTbl6Col">
                <tr>
                    <td class="label"><tags:label key="lbl.cua_hang_nhan_vien"/></td>
                    <td class="text">
                        <s:textfield name="inputForm.staffCode" id="staffCode" theme="simple" cssClass="txtInputFull" maxLength="40" cssStyle="witd:200px"/>
                    </td>
                    <td class="label"><tags:label key="MSG.object.type" required="true"/></td>
                    <td class="text">
                        <tags:imSelect name="inputForm.requestObjectType" id="requestObjectType"
                                       cssClass="txtInputFull" disabled="false"
                                       list="1: lbl.han.muc.kho.don.vi, 2: lbl.han.muc.kho.nhan.vien"
                                       headerKey="" headerValue="COM.CHL.001"/>
                    </td>
                </tr>
            </table>
            <div align="center">
                <tags:submit targets="listSearchDebitOfStaffDIV"
                             formId="inputForm"
                             cssStyle="width:100px;"
                             value="MSG.SAE.138"
                             showLoadingText="true"
                             preAction="searchDebitOfStaffAction!searchDebitStaff.do"
                             validateFunction="validate()"/>
            </div>
            <div id="listSearchDebitOfStaffDIV">
                <jsp:include page="listSearchDebitOfStaff.jsp"/>
            </div>
        </fieldset>
    </s:form>
</tags:imPanel>
<script type="text/javascript">
    validate = function(){
        if(jQuery("#requestObjectType").val() == ""){
            jQuery("#displayResultMsg").html('<s:property value="getText('lbl.chua_chon_loai_doi_tuong')"/>');
            jQuery("#requestObjectType").focus();
            return false;
        }
        return true;
    }
</script>