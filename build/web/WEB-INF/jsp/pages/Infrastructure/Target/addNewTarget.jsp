<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : showLogChangeAccountFPT
    Created on : Jan 29, 2010, 10:37:27 AM
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

<%
            if (request.getAttribute("listTargetSearch") == null) {
                request.setAttribute("listTargetSearch", request.getSession().getAttribute("listTargetSearch"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<br/>
<tags:imPanel title="TARGET.ASSIGN.EDIT">
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="message" property="returnMsg" propertyValue="returnMsgParam" type="key"/>
    </div>
    <s:form action="manageBTSGroupAction" theme="simple" enctype="multipart/form-data"  method="post" id="targetForm" name="targetForm">
        <div>

            <table class="inputTbl6Col" style="width:100%" align="center" >
                <tr>
                    <td class="label">
                        <tags:label key="Target.shopCode" required="false"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="targetForm.shopCode" nameVariable="targetForm.shopName"
                                       codeLabel="targetForm.shopCode" nameLabel="targetForm.shopName"
                                       searchClassName="com.viettel.im.database.DAO.TargetStaffBtsDAO"
                                       searchMethodName="getListShop" elementNeedClearContent="targetForm.staffCode;targetForm.staffName"
                                       getNameMethod="getShopName"/>
                    </td>
                    <td class="label">
                        <tags:label key="Target.StaffCode" required="true"/>
                    </td>
                    <td class="text">
                        <tags:imSearch codeVariable="targetForm.staffCode" nameVariable="targetForm.staffName"
                                       codeLabel="MSG.stock.staff.code" nameLabel="MSG.staffName"
                                       searchClassName="com.viettel.im.database.DAO.TargetStaffBtsDAO"
                                       searchMethodName="getListStaff"
                                       otherParam="targetForm.shopCode"
                                       getNameMethod="getStaffName"/>
                    </td>
                    <td class="label"><tags:label key="BTS.Status" required="true"/> </td>
                    <td class="text">
                        <tags:imSelect name="targetForm.status" id="targetForm.status"
                                       cssClass="txtInputFull"
                                       list="1:BTS.active"
                                       />
                    </td>
                </tr>
                <tr>
                    <td class="label"><tags:label key="TARGET.YEAR" required="true"/></td>
                    <td class="text">
                        <table style="width:100%; border-spacing:0px; ">
                            <tr>
                                <td style="width:50%;">
                                    <s:select name="targetForm.yearSelect"
                                              id="targetForm.yearSelect"
                                              cssClass="txtInputFull"
                                              list="%{#request.listYear != null ? #request.listYear :#{}}"
                                              listKey="code" listValue="name"/>
                                </td>
                                <td>-</td>
                                <td style="width:50%;">
                                    <s:select name="targetForm.monthSelect"
                                              id="targetForm.monthSelect"
                                              cssClass="txtInputFull"
                                              list="%{#request.listMonth != null ? #request.listMonth :#{}}"
                                              listKey="code" listValue="name"/>
                                </td>
                            </tr>
                        </table>
                    </td>

                </tr>
            </table>
            <s:token/>
        </div>


        <sx:div id="displayTagFrame">
            <display:table pagesize="20" id="tbllistTargetSearch"  name="listTargetSearch" class="dataTable" targets="displayTagFrame" requestURI="manageBTSGroupAction!pageNavigatorListStaff.do" cellpadding="1" cellspacing="1" >
                <display:column escapeXml="true" title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" sortable="false" style="width:50px; text-align:center" headerClass="tct">
                    ${fn:escapeXml(tbllistTargetSearch_rowNum)}
                </display:column>
                <display:column property="targetCode" title="${fn:escapeXml(imDef:imGetText('TARGET_CODE'))}" sortable="false" headerClass="sortable" escapeXml="true"/>
                <display:column escapeXml="true"  property="targetName" title="${fn:escapeXml(imDef:imGetText('TARGET_NAME'))}" sortable="false" headerClass="sortable"/>

                <display:column title="${fn:escapeXml(imDef:imGetText('TARGET_AMOUNT'))}" style="width:180px; text-align:left">
                    <s:textfield size="20" name="amountMap['%{#attr.tbllistTargetSearch.targetId}']"
                                 id="amountMap['%{#attr.tbllistTargetSearch.targetId}']"
                                 value="%{#attr.tbllistTargetSearch.amountTarget}"
                                 theme="simple" style="text-align:right;"
                                 maxLength="15"
                                 >
                    </s:textfield>
                </display:column>
            </display:table>
        </sx:div>

    </s:form>
    <tags:submit
        targets="editTarget" formId="targetForm"
        showLoadingText="true"
        value="BTS.btn.AddNewTarGet"
        validateFunction="checkValidateUpdateBTSGroup()"
        preAction="manageBTSGroupAction!addTargetToStaff.do"
        />


</tags:imPanel>

<script type="text/javascript" language="javascript">

    checkValidateUpdateBTSGroup = function() {        
        return true;
    
        $('btsGroupForm1.btsGroupName').value=trim($('btsGroupForm1.btsGroupName').value);


        // Kiem tra ma tram BTS-------------------------------------------------
        //
        // -  Ma tram BTS khong duoc chua cac the HTML
        if(trim($('btsGroupForm1.btsGroupCode').value) == ""){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00001')" />';
            $('btsGroupForm1.btsGroupCode').select();
            $('btsGroupForm1.btsGroupCode').focus();
            return false;
        }

        if(isHtmlTagFormat(trim($('btsGroupForm1.btsGroupCode').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00005')" />';
            $('btsGroupForm1.btsGroupCode').select();
            $('btsGroupForm1.btsGroupCode').focus();
            return false;
        }
        // - Ma tram BTS khong duoc chua cac ky tu dac biet

        if(trim($('btsGroupForm1.btsGroupCode').value) != "" &&
            !isValidInput(trim($('btsGroupForm1.btsGroupCode').value), false, false)){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00006')" />';
            $('btsGroupForm1.btsGroupCode').select();
            $('btsGroupForm1.btsGroupCode').focus();
            return false;
        }
        // - Ma tram BTS khong duoc chua cac ky tu trong
        if (trim($('btsGroupForm1.btsGroupCode').value).match(" ")){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00007')" />';
            $('btsGroupForm1.btsGroupCode').select();
            $('btsGroupForm1.btsGroupCode').focus();
            return false;
        }
        // - Ma tram BTS khong duoc qua 40 ky tu
        if ( trim($('btsGroupForm1.btsGroupCode').value).length  > 40){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00008')" />';
            $('btsGroupForm1.btsGroupCode').select();
            $('btsGroupForm1.btsGroupCode').focus();
            return false;
        }

        // Kiem tra ten tram BTS------------------------------------------------
        //

        if(trim($('btsGroupForm1.btsGroupName').value) == ""){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00002')" />';
            $('btsGroupForm1.btsGroupName').select();
            $('btsGroupForm1.btsGroupName').focus();
            return false;
        }
        // -  Ten tram BTS khong duoc chua cac the HTML
        if(isHtmlTagFormat(trim($('btsGroupForm1.btsGroupName').value))) {
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00009')" />';
            $('btsGroupForm1.btsGroupName').select();
            $('btsGroupForm1.btsGroupName').focus();
            return false;
        }
        // - Ten tram BTS khong duoc qua 250 ky tu
        if ( trim($('btsGroupForm1.btsGroupName').value).length  > 250){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.000011')" />';
            $('btsGroupForm1.btsGroupName').select();
            $('btsGroupForm1.btsGroupName').focus();
            return false;
        }

        if(trim($('btsGroupForm1.status').value) == ""){
            $('message').innerHTML = '<s:property escapeJavaScript="true"  value="getText('ERR.BTSGROUP.00003')" />';
            $('btsGroupForm1.status').select();
            $('btsGroupForm1.status').focus();
            return false;
        }
        return true;
    }
  

</script>
