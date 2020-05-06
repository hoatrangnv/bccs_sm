<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : ContractFeesResult
    Created on : May 15, 2009, 9:38:55 AM
    Author     : Tuannv
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
    request.setAttribute("lstCalulate", request.getSession().getAttribute("lstCalulate"));
%>

<sx:div id="displayTagFrame">
    <display:table id="tblCal" name="lstCalulate" 
    pagesize="10" targets="displayTagFrame" requestURI="commcontractFeesAction!pageNavigator.do" 
    class="dataTable" cellpadding="1" cellspacing="1" >
        <display:column title="STT" headerClass="tct" sortable="false" style="width:50px; text-align:center">
            ${fn:escapeXml(tblCal_rowNum)}
        </display:column>
        <display:column escapeXml="true"  property="itemName" title="Tên khoản mục" sortable="false" headerClass="tct" />
        <display:column  title="Loại khoản mục"  headerClass="tct">
            <s:if test="#attr.tblCal.inputType == 1">Tự động</s:if>
            <s:if test="#attr.tblCal.inputType == 2">Nhập tay số lượng</s:if>
            <s:if test="#attr.tblCal.inputType == 3">Nhập tay số tiền</s:if>
        </display:column>
        <display:column property="quantity" title="Số lượng" sortable="false" headerClass="tct" format="{0,number,#,###}" style="text-align:right"/>
        <!--display:column property="fee" title="Đơn giá" sortable="false" headerClass="tct" format="{0,number,#,###}" style="text-align:right"/-->
        <display:column property="totalMoney" title="Thành tiền" sortable="false" headerClass="tct" format="{0,number,#,###}" style="text-align:right"/>
        <display:column title="Trạng thái"  headerClass="tct" style="width:100px; ">
            <s:if test="#attr.tblCal.approved == 0">Chưa duyệt</s:if>
            <s:if test="#attr.tblCal.approved == 1">Đã duyệt</s:if>
        </display:column>
        <display:column property="createDate" title="Ngày cập nhật"  headerClass="tct" format="{0,date,dd/MM/yyyy}" style="text-align:center; width:100px; "/>
        <display:column title="<input id = 'checkAllId' type='checkbox' onclick=\"selectCbAll()\">" sortable="false" headerClass="tct" style="width:50px; text-align:center">
            <s:checkbox name="contractFeesForm.selectedItems" id="checkBoxId%{#attr.tblCal.itemId}"
                        theme="simple" fieldValue="%{#attr.tblCal.itemId}" 
                        onclick="checkSelectCbAll();"/>
        </display:column>
    </display:table>
</sx:div>

<script>
    selectCbAll = function(){
        selectAll("checkAllId", "contractFeesForm.selectedItems", "checkBoxId");
    }

    checkSelectCbAll = function(){
        checkSelectAll("checkAllId", "contractFeesForm.selectedItems", "checkBoxId");
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


