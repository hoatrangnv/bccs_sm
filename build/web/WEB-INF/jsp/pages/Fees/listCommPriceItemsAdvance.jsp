<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
    Document   : listCommPriceItemsAdvance
    Created on : Sep 29, 2009, 2:15:35 PM
    Author     : MrSun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("listChannelType", request.getSession().getAttribute("listChannelType"));
%>

<fieldset style="width:100%; background-color: #F5F5F5;">
    <legend>Danh sách đối tượng áp phí</legend>
    <s:if test="#session.listChannelType != null && #session.listChannelType.size() > 0">
        <display:table id="tblCommItems" name="listChannelType"
                       targets="listCommPriceItemsAdvanceArea" pagesize="20"
                       requestURI="${contextPath}/commItemsAction!pageNavigatorCommItemAdvance.do"
                       class="dataTable" cellpadding="1" cellspacing="1">
            <display:column title="STT" sortable="false" style="width:50px; text-align:center" headerClass="tct">${fn:escapeXml(tblCommItems_rowNum)}</display:column>
            <display:column escapeXml="true"  property="channelTypeName" title="Tên đối tượng" sortable="false" headerClass="tct"/>
            <%--<display:column title="Giá tiền" sortable="false" headerClass="tct" style="width:100px; text-align:right;">
                <s:hidden name="itemChannelForm.channelTypeIdList" id="hiddenID%{#attr.tblCommItems.channelTypeId}" value="%{#attr.tblCommItems.channelTypeId}"/>
                <s:textfield name="itemChannelForm.feeList" readonly="false" id="fee%{#attr.tblCommItems.channelTypeId}" maxlength="10" cssClass="txtInputFull" cssStyle="text-align:right;" theme="simple"  onkeyup="textFieldNF(this)" value="%{#attr.tblCommItems.feeString}"/>
            </display:column>
            <display:column title="VAT" sortable="false" headerClass="tct" style="width:50px; text-align:right;">
                <s:textfield name="itemChannelForm.vatList" readonly="false" id="vat%{#attr.tblCommItems.channelTypeId}" maxlength="2" cssClass="txtInputFull" cssStyle="text-align:right;" theme="simple" value="%{#attr.tblCommItems.vatString}" />
            </display:column>--%>
            <display:column title="Chọn <br/><input id = 'checkAllID' type='checkbox' onclick=\"selectCbAll()\">"
                            sortable="false" headerClass="tct" style="width: 50px; text-align:center">
                <s:checkbox name="itemChannelForm.channelTypeIdCheckedList" id="checkBoxID%{#attr.tblCommItems.channelTypeId}"
                            onclick="checkSelectCbAll();" value="%{#attr.tblCommItems.checked}"
                            fieldValue="%{#attr.tblCommItems.channelTypeId}" theme="simple"/>
            </display:column>
        </display:table>
    </s:if>
    <s:else>
        <table class="dataTable">
            <tr>
                <th class="tct">STT</th>
                <th class="tct">Tên đối tượng</th>
                <%--<th class="tct">Phí</th>--%>
                <%--<th class="tct">VAT</th>--%>
                <th class="tct">Chọn</th>
            </tr>
            <tr>
                <td>&nbsp;</td>
                <td></td>
                <%--<td></td>
                <td></td>--%>
                <td></td>
            </tr>
        </table>
    </s:else>

</fieldset>


<script type="text/javascript" language="javascript">
    selectCbAll = function(){
        selectAll("checkAllID", "itemChannelForm.channelTypeIdCheckedList", "checkBoxID");
    }
    checkSelectCbAll = function(){
        checkSelectAll("checkAllID", "itemChannelForm.channelTypeIdCheckedList", "checkBoxID");
    }

    isCheckBoxChecked = function(){
        if(document.getElementById('tblCommItems') == null){
            return false;
        }
        var i = 0;
        var tbl = $( 'tblCommItems' );
        var inputs = [];
        //var chkNum = 0;
        inputs = tbl.getElementsByTagName( "input" );
        for( i = 0; i < inputs.length; i++ ) {
            if( inputs[i].type == "checkbox" && inputs[i].checked == true ) {
                return true;
            }
        }
        return false;
    }

    validateRecevied = function(){
        if(isCheckBoxChecked() == false){
            $( 'message' ).innerHTML = "!!! Bạn chưa chọn đối tượng áp phí nào";
            return false;
        }
        return true;
    }
</script>
