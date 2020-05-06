<%-- 
    Document   : assignPricePolicyAndDiscountPolicy
    Created on : Sep 14, 2009, 10:29:30 AM
    Author     : AnDV
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%
            request.setAttribute("contextPath", request.getContextPath());
%>
<s:form action="assignPricePolicyAndDiscountPolicyAction" theme="simple" enctype="multipart/form-data"  method="post" id="assignPolicyForm">
    <s:token/>

    <tags:imPanel title="MSG.sale.channel.info">
        <div>
            <tags:displayResult id="displayResultMsgClient" property="message" propertyValue="messageParam" type="key" />
        </div>
        <table class="inputTbl4Col">
            <tr>
                <td class="label"><tags:label key="MSG.CFPPDPC" required="true"/></td>
                <td colspan="3">
                    <fieldset style="width:100%;">
                        <tags:imRadio name="assignPolicyForm.configType" id="configType"
                                      list="1:MSG.CFPP,2:MSG.CFDP" onchange="onChangeConfigType();"/>
                    </fieldset>
                </td>
            </tr>

            <tr>
                <td class="label"><tags:label key="MSG.generates.province" required="false"/></td>
                <td class="text">
                    <tags:imSelect name="assignPolicyForm.province"
                                   id="province"
                                   cssClass="txtInputFull"
                                   disabled="#request.readonly"
                                   headerKey="" headerValue="MSG.FAC.AssignPrice.ChooseProvince"
                                   list="listProvince"
                                   listKey="areaCode" listValue="name"/>
                </td>
                <td class="label" style="width:10%" ><tags:label key="MES.CHL.015" required="false" /></td>
                <td style="width:50%; " >
                    <tags:imSearch codeVariable="assignPolicyForm.shopCodeImSearch" nameVariable="assignPolicyForm.shopNameImSearch"
                                   codeLabel="MES.CHL.015" nameLabel="MES.CHL.016"
                                   searchClassName="com.viettel.im.database.DAO.CommonDAO"
                                   elementNeedClearContent="addStaffCodeCTVDBForm.staffMagCodeSearch;addStaffCodeCTVDBForm.staffMagNameSearch"
                                   searchMethodName="getListShopIsVTUnit"
                                   roleList="f9ShopChangeInfoStaff"/>
                </td>
            </tr>            
            <tr>
                <td class="label">
                    <tags:label key="MSG.chanel.type" required="false"/>
                </td>
                <td class="text">
                    <tags:imSelect name="assignPolicyForm.channelTypeId"
                                   id="channelTypeId"
                                   cssClass="txtInputFull"
                                   disabled="#request.readonly"
                                   headerKey=""
                                   headerValue="MSG.FAC.AssignPrice.ChooseChannel"
                                   list="listChannelType"
                                   listKey="channelTypeId" listValue="name"/>
                </td>                
                <s:if test="assignPolicyForm.configType == 1">
                    <td class="label">
                        <tags:label key="MSG.FAC.policy.price" />
                    </td>
                    <td class="text">
                        <tags:imSelect name="assignPolicyForm.discountPolicy"
                                       id="discountPolicy"
                                       cssClass="txtInputFull"
                                       headerKey=""
                                       headerValue="MSG.FAC.AssignPrice.ChoosePP"
                                       list="listDiscountPolicy"
                                       listKey="code" listValue="name"/>
                    </td>
                </s:if>
                <s:else>
                    <td class="label">
                        <tags:label key="MSG.FAC.policy.discount" />
                    </td>
                    <td class="text">
                        <tags:imSelect name="assignPolicyForm.discountPolicy"
                                       id="discountPolicy"
                                       cssClass="txtInputFull"
                                       headerKey=""
                                       headerValue="MSG.FAC.AssignPrice.ChooseCKPolicy"
                                       list="listDiscountPolicy"
                                       listKey="code" listValue="name"/>
                    </td>
                </s:else>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.channel.code"/></td>
                <td class="text">
                    <s:textfield name="assignPolicyForm.shopCode" id="shopCode"  maxlength="1000" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <td class="label">
                    <tags:label key="MSG.channel.name" />
                </td>
                <td class="text">
                    <s:textfield name="assignPolicyForm.name" id="name" maxlength="1000"  cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
            </tr>

        </table>
        <div align="center" style="padding-bottom:5px;">
            <sx:submit  parseContent="true" executeScripts="true"
                        formId="assignPolicyForm" loadingText="Đang thực hiện..."
                        showLoadingText="true" targets="bodyContent"
                        cssStyle="width:80"
                        key="MSG.generates.find"  beforeNotifyTopics="assignPricePolicyAndDiscountPolicyAction/searchShop"/>
        </div>
    </tags:imPanel>
    <br/>

    <tags:imPanel title="MSG.sale.channel.list">
        <div id="lstShops" style="width:100%; height:400px; overflow:auto;">
            <jsp:include page="listPolicy.jsp"/>
        </div>
    </tags:imPanel>
    <br/>
    <div id="assignPolicy">
        <tags:imPanel title="MSG.policy.price.discount">
            <table class="inputTbl4Col">
                <tr>
                    <s:if test="assignPolicyForm.configType == 1">
                        <td width="20%">
                            <tags:label key="MSG.policy.price.new" required="true"/>
                        </td>
                        <td colspan="2">
                            <tags:imSelect name="assignPolicyForm.newDiscountPolicy" theme="simple"
                                           id="newDiscountPolicy"
                                           cssClass="txtInputFull"
                                           headerKey=""
                                           headerValue="MSG.FAC.AssignPrice.ChoosePP"
                                           list="listDiscountPolicy"
                                           listKey="code" listValue="name"/>
                        </td>
                    </s:if>
                    <s:else>
                        <td width="20%">
                            <tags:label key="MSG.policy.discount.new" required="true"/>
                        </td>
                        <td colspan="2">
                            <tags:imSelect name="assignPolicyForm.newDiscountPolicy" theme="simple"
                                           id="newDiscountPolicy"
                                           cssClass="txtInputFull"
                                           headerKey=""
                                           headerValue="MSG.FAC.AssignPrice.ChooseCKPolicy"
                                           list="listDiscountPolicy"
                                           listKey="code" listValue="name"/>
                        </td>
                    </s:else>
                    <td align="right" width="100px;">
                        <sx:submit  parseContent="true" executeScripts="true"
                                    formId="assignPolicyForm" loadingText="Đang thực hiện..."
                                    showLoadingText="true" targets="bodyContent"
                                    cssStyle="width:80"
                                    key="MSG.assign"  beforeNotifyTopics="assignPricePolicyAndDiscountPolicyAction/assignPolicy"/>
                    </td>
                </tr>
            </table>            
        </tags:imPanel>
    </div>
</s:form>

<script type="text/javascript">
    var _myWidget2 = $('province');
    if (_myWidget2 != null)
        _myWidget2.focus();
    //lang nghe, xu ly su kien onclick tren nut tim kiem
    dojo.event.topic.subscribe("assignPricePolicyAndDiscountPolicyAction/searchShop", function(event, widget){

        /*if (trim($('province').value) == ""){
            $( 'displayResultMsgClient' ).innerHTML ='<s:property escapeJavaScript="true"  value="getError('ERROR.PROVINCE.MUST.CHOICE')"/>';
            $('province').focus();
            event.cancel = true;
            return;
        }
        
        if (trim($('channelTypeId').value) == ""){
            $( 'displayResultMsgClient' ).innerHTML ='<s:property escapeJavaScript="true"  value="getError('ERR.STAFF.0009')"/>';
            $('channelTypeId').focus();
            event.cancel = true;
            return;
        }*/
        widget.href = "assignPricePolicyAndDiscountPolicyAction!searchShop.do";
    });

    //lang nghe, xu ly su kien onclik tren nut lap ho so
    dojo.event.topic.subscribe("assignPricePolicyAndDiscountPolicyAction/assignPolicy", function(event, widget){
        if (checkRequiredFields()) {

            if(!confirm(getUnicodeMsg('<s:property escapeJavaScript="true"  value="getError('CONFIRM.ASSIGN.POLICY.DISCOUNT')"/>'))){
                event.cancel = true;
                return;
            }
     
            widget.href = "assignPricePolicyAndDiscountPolicyAction!assignPolicy.do?" + token.getTokenParamString();
        
        } else {
            event.cancel = true;
        }
    });
    checkRequiredFields = function() {

        if(!isCheckBoxChecked()){
            $('displayResultMsgClient').innerHTML= '<s:property escapeJavaScript="true"  value="getError('assignPolicy.error.notSelectShop')"/>';
            return false;
        }

        if($('newDiscountPolicy').value ==null || $('newDiscountPolicy').value.trim() == "") {
            $( 'displayResultMsgClient' ).innerHTML ='<s:property escapeJavaScript="true"  value="getError('ERR.FAC.ISDN.004')"/>' ;
            $('newDiscountPolicy').focus();
            return false;
        }
        return true;
    }

    onChangeConfigType = function() {
        gotoAction("bodyContent", "assignPricePolicyAndDiscountPolicyAction.do", "assignPolicyForm");
    }
    isCheckBoxChecked = function(){
        if(document.getElementById('tblShop') == null){
            return false;
        }
        var i = 0;
        var tbl = $( 'tblShop' );
        var inputs = [];
        //var chkNum = 0;
        inputs = tbl.getElementsByTagName( "input" );

        for( i = 0; i < inputs.length; i++ ) {
            if(inputs[i].type == "text" && inputs[i+3].type == "checkbox"){
                $(inputs[i].id + 'span').style.display = 'none';
            }
        }
        for( i = 0; i < inputs.length; i++ ) {
            //if( inputs[i].type == "checkbox" ) chkNum++;
            if( inputs[i].type == "checkbox" && inputs[i].checked == true ) {
                return true;
            }
        }
        return false;
    }

</script>
