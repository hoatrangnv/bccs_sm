<%-- 
    Document   : listPolicy
    Created on : Sep 14, 2009, 10:29:51 AM
    Author     : AnDV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>
<%@taglib prefix="imDef" uri="imDef" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
            request.setAttribute("listShops", request.getSession().getAttribute("listShops"));
%>

<sx:div id="displayTagFrame" >
    <display:table  id="tblShop" name="listShops"
                    targets="displayTagFrame" 
                    class="dataTable" cellpadding="1" cellspacing="1" >
        <display:column title="${fn:escapeXml(imDef:imGetText('MSG.generate.order_no'))}" headerClass="tct" sortable="false" style="width:50px; text-align:center" >
            ${fn:escapeXml(tblShop_rowNum)}
        </display:column>
        <display:column escapeXml="true"  property="code" title="${fn:escapeXml(imDef:imGetText('MSG.channel.code'))}" sortable="false" headerClass="tct" />
        <display:column escapeXml="true" property="name" title="${fn:escapeXml(imDef:imGetText('MSG.channel.name'))}" sortable="false" headerClass="tct" />
        <display:column escapeXml="true" property="channelTypeName" title="${fn:escapeXml(imDef:imGetText('MSG.chanel.type'))}" sortable="false" headerClass="tct"/>
        <%--display:column property="pricePolicyName" title="${fn:escapeXml(imDef:imGetText('MSG.policy.price'))}" sortable="false" headerClass="tct" /--%>
        <s:if test="assignPolicyForm.configType == 1">
            <display:column escapeXml="true" property="discountPolicyName" title="${fn:escapeXml(imDef:imGetText('MSG.policy.price'))}" sortable="false" headerClass="tct" />
        </s:if>
        <s:else>
            <display:column escapeXml="true" property="discountPolicyName" title="${fn:escapeXml(imDef:imGetText('MSG.policy.discount'))}" sortable="false" headerClass="tct" />
        </s:else>
        <display:column escapeXml="true" property="provinceName" title="${fn:escapeXml(imDef:imGetText('MSG.generates.province'))}" sortable="false" headerClass="tct" />

        <%--display:column title="Trạng thái"  headerClass="tct" style="width:100px; ">
            <s:if test="#attr.tblShop.status == 1">Có hiệu lực</s:if>
            <s:if test="#attr.tblShop.status == 2">Không hiệu lực</s:if>
        </display:column--%>
        <display:column title="<input id = 'checkAllId' type='checkbox' onclick=\"selectCbAll()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center">
            <s:checkbox name="assignPolicyForm.selectedItems" id="checkBoxId%{#attr.tblShop.id}"
                        theme="simple" fieldValue="%{#attr.tblShop.id}"
                        onclick="checkSelectCbAll();"/>
        </display:column>
    </display:table>
</sx:div>
<script>
    selectCbAll = function(){
        selectAll("checkAllId", "assignPolicyForm.selectedItems", "checkBoxId");
    }

    checkSelectCbAll = function(){
        checkSelectAll("checkAllId", "assignPolicyForm.selectedItems", "checkBoxId");
    }

    isTextBoxChecked = function(){
        if(document.getElementById('invoice') == null){
            return false;
        }
        var i = 0;
        var tbl = $( 'invoice' );
        var inputs = [];
        inputs = tbl.getElementsByTagName( "input" );
        for( i = 0; i < inputs.length; i++ ) {
            if(inputs[i].type == "text" && inputs[i+2].type == "checkbox"){
                if(inputs[i+2].checked == true){
                    var startNum = inputs[i-1].value *1;
                    var endNum = inputs[i+1].value *1;
                    var inputNum = inputs[i].value *1;
                    if(!isNumberFormat(inputs[i].value)){
                        $(inputs[i].id + 'span').style.display = 'inline';
                        inputs[i].select();
                        return false;
                    }
                    else if(inputNum < startNum || inputNum > endNum){
                        $(inputs[i].id + 'span').style.display = 'inline';
                        inputs[i].select();
                        return false;
                    }
                }
            }
        }
        return true;
    }

</script>



