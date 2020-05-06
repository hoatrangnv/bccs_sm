<%-- 
    Document   : addItem
    Created on : May 25, 2009, 11:23:57 AM
    Author     : nguyentuan
--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
<%@page contentType="text/html" pageEncoding="UTF-8"%>
"http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ page import="com.guhesan.querycrypt.QueryCrypt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("status", request.getSession().getAttribute("status"));
        request.setAttribute("add", request.getSession().getAttribute("add"));

%>
<sx:div id="ListItemFees">   
    <s:if test="#request.status != null && #request.status!='new'">
        <fieldset style="width:95%; padding:10px 10px 10px 10px">
            <legend class="transparent">Điều chỉnh khoản mục</legend>
            <table class="inputTbl">
                <tr>
                    <td>Tên khoản mục(<font color="red">*</font>)</td>
                    <td>
                        <!--req.setAttribute("lstItem", getItemList());-->
                        <s:select name="updFeesForm.actionCode"  id="actionCode" theme="simple"

                                  cssClass="txtInput"
                                  list="%{#request.lstItem != null?#request.lstItem:#{}}"
                                  onchange="changeChannel()"
                                  listKey="itemId" listValue="itemName" headerKey="" headerValue="-- Danh sách khoản mục --"
                                  onchange="selectItem()"/>
                        <input type="hidden" name="feeID" id="tempfeeId">
                    </td>
                    <td>Số lượng(<font color="red">*</font>)</td>
                    <td>
                        <s:textfield name="updFeesForm.count" id="count" maxlength="80" theme="simple" cssClass="txtInput" disabled="#request.valueQty"/>
                    </td>
                </tr>
                <tr>
                    <td> Ngày thực hiện(<font color="red">*</font>)</td>
                    <td>
                        <tags:dateChooser property="updFeesForm.date" styleClass="txtInput" readOnly="#request.valueQty"/>

                    </td>


                    <td>Thành tiền(<font color="red">*</font>)</td>
                    <td>
                        <s:textfield name="updFeesForm.money" id="money" maxlength="80"theme="simple" cssClass="txtInput" disabled="#request.valueMoney"/>
                    </td>
                </tr>
                <!--tr>
                     <td>Đơn giá(<font color="red">*</font>)</td>
                    <td>
                        <!--s:textfield name="updFeesForm.price" id="price" maxlength="80" theme="simple" cssClass="txtInput" disabled="true"/>
                        <input type="hidden" name="fee" id="tempfee">
                    </td>
                    <td> Ngày thực hiện(<font color="red">*</font>)</td>
                    <td>
                        <!--tags:dateChooser property="updFeesForm.date" styleClass="txtInput" readOnly="#request.valueQty"/>

                    </td>
                </tr-->

                <tr>
                    <td colspan="4" align="center">
                        <br />                       
                        <s:if test="#request.status=='add'">
                            <sx:submit  parseContent="true" executeScripts="true"
                                        formId="updFeesForm" loadingText="Đang thực hiện..."
                                        showLoadingText="true" targets="bodyContent"
                                        value="Cập nhật khoản mục"  beforeNotifyTopics="commUpdFeesAction/addCOMM"/>
                            &nbsp;&nbsp;&nbsp;
                        </s:if><s:elseif test="#request.status!=''">
                            <s:hidden name="updFeesForm.commtransid" id="updFeesForm.commtransid"/>
                            <sx:submit  parseContent="true" executeScripts="true"
                                        formId="updFeesForm" loadingText="Đang thực hiện..."
                                        showLoadingText="true" targets="bodyContent"
                                        value="Cập nhật khoản mục"  beforeNotifyTopics="commUpdFeesAction/updateCOMM"/>
                            <sx:submit  parseContent="true" executeScripts="true"
                                        formId="updFeesForm" loadingText="Đang thực hiện..."
                                        showLoadingText="true" targets="bodyContent"
                                        value="Bỏ qua"  beforeNotifyTopics="commUpdFeesAction/resetCOMM"/>
                            &nbsp;&nbsp;&nbsp;
                        </s:elseif>

                    </td>
                </tr>
            </table>
        </fieldset>
    </s:if>
</sx:div>

<script type="text/javascript">

//Nghi van khong dung
    dojo.event.topic.subscribe("commUpdFeesAction/DelFees", function(event, widget){
//        widget.href = "commUpdFeesAction!DelFees.do?"+ token.getTokenParamString();
    });

    changeChannel = function() {
        var comboId = document.getElementById("actionCode").value;
        var comboChannel = document.getElementById("payTypeCode").value;
        if(comboChannel==null)
        {
            alert("Bạn phải chọn đối tượng trước khi chọn khoản mục.")
        }
        else{
            jQuery.getJSON("AjaxGetInputType.do?itemID="  + jQuery('#actionCode').val()+ "&channelTypeID="+ jQuery('#payTypeCode').val(), function(data) {
                toDisable(data);
            });
        }
    }
</script>
