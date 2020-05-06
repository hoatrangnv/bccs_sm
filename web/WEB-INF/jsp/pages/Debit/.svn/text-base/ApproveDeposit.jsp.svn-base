<%-- 
    Document   : ApproveDeposit
    Created on : Feb 18, 2009, 11:43:14 AM
    Author     : tuannv1
--%>

<%--
    Note: Phe duyet giay nop tien
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display"%>

<%
            if (request.getAttribute("listBankReceipt") == null) {
                request.setAttribute("listBankReceipt", request.getSession().getAttribute("listBankReceipt"));
            }
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:form action="approveDepositAction" theme="simple" id="approveDepositForm" method="post">
<s:token/>

    <tags:imPanel title="MSG.find.doc.deposit">
        <table class="inputTbl4Col">
            <tr>
                <td class="label"><tags:label key="MSG.object.type" required="false"/></td>
                <td class="text">
                    <tags:imSelect name="approveDepositForm.channelTypeIdB"
                                   id="channelTypeIdB"
                                   cssClass="txtInput"
                                   headerKey="" headerValue="MSG.SIK.264"
                                   list="listChannelType"
                                   listKey="objId" listValue="objName"/>

                    <%--<s:select name="approveDepositForm.channelTypeIdB"
                              id="channelTypeIdB"
                              cssClass="txtInput"
                              headerKey="" headerValue="--Chọn loại đối tượng--"
                              list="%{#request.listChannelType != null? #request.listChannelType :#{}}"
                              listKey="objId" listValue="objName"
                              onchange="notify('channelTypeIdB','shopCodeB','select1','%{#request.contextPath}/getListShopCodeBA.do');"/>--%>
                </td>

            </tr>
            <tr>
                <s:url action="getListShopCodeBA" var="listShopCodeBDefault" >
                    <s:param name="channelTypeIdB" value="approveDepositForm.channelTypeIdB"></s:param>
                </s:url>
                <td class="label"> <tags:label key="MSG.object.code"/></td>
                <td class="text">
                    <%-- shopeCode lay tu viec join voi bang Shop theo ShopId --%>
                    <sx:autocompleter name="approveDepositForm.shopCodeB"
                                      id="shopCodeB"
                                      listenTopics="select1"
                                      href="%{listShopCodeBDefault}"
                                      cssClass="txtInput"
                                      loadOnTextChange="true"
                                      loadMinimumCount="1"
                                      valueNotifyTopics="approveDepositAction/displayShopNameB"/>
                </td>
                <td class="label"><tags:label key="MSG.shop.agent.staff.name" /></td>
                <td>
                    <s:textfield name="approveDepositForm.shopNameB" id="shopNameB" maxlength="80" cssClass="txtInput" readonly="true"/>
                </td>
            </tr>
            <tr>
                <td class="label"> <tags:label key="MSG.account.number" /></td>
                <td class="text">
                    <%-- accountNo lay tu viec join voi bang Bank_Account theo bankAccountId --%>
                    <sx:autocompleter name="approveDepositForm.accountNoB"
                                      id = "accountNoB"
                                      cssClass="txtInput"
                                      href="getBankAccountNoBA.do"
                                      loadOnTextChange="true"
                                      loadMinimumCount="2"
                                      valueNotifyTopics="approveDepositAction/displayBankNameB"/>
                </td>
                <td class="label"><tags:label key="MSG.bank"/></td>
                <td>
                    <s:textfield name="approveDepositForm.bankNameB" id="bankNameB" maxlength="80" cssClass="txtInput" readonly="true"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.date.paid.from"/></td>
                <td class="text">
                    <tags:dateChooser  property="approveDepositForm.fromDateB" id="fromDateB" readOnly="false" styleClass="txtInputFull"/>
                </td>
                <td class="label"><tags:label key="MSG.date.paid.to"/></td>
                <td class="text">
                    <tags:dateChooser property="approveDepositForm.toDateB" id="toDateB" readOnly="false" styleClass="txtInputFull"/>
                </td>

            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.money.paid.from"/></td>
                <td>
                    <s:textfield name="approveDepositForm.fromAmountB" id="fromAmountB"  onkeyup="textFieldNF(this)" maxlength="20" cssClass="txtInput"/>
                </td>
                <td class="label"><tags:label key="MSG.money.paid.to"/></td>
                <td>
                    <s:textfield name="approveDepositForm.toAmountB" id="toAmountB"  onkeyup="textFieldNF(this)" maxlength="20" cssClass="txtInput"/>
                </td>
            </tr>
            <tr>
                <td class="label"><tags:label key="MSG.telecom.service"/></td>
                <td class="text">
                    <tags:imSelect name="approveDepositForm.telecomServiceIdB"
                                   id="telecomServiceIdB"
                                   cssClass="txtInput"
                                   headerKey="" headerValue="MSG.SIK.263"
                                   list="listTelecomService"
                                   listKey="telecomServiceId" listValue="telServiceName"/>

                    <%--<s:select name="approveDepositForm.telecomServiceIdB"
                              id="telecomServiceIdB"
                              cssClass="txtInput"
                              headerKey="" headerValue="--Chọn dịch vụ viễn thông--"
                              list="%{#request.listTelecomService != null? #request.listTelecomService :#{}}"
                              listKey="telecomServiceId" listValue="telServiceName"/>--%>
                </td>
                <td class="label"> <tags:label key="MSG.status"/></td>
                <td class="text">
                    <tags:imSelect name="approveDepositForm.statusB" id="status"
                                   cssClass="txtInput" disabled="false"
                                   list="1:MSG.not.approved, 0: MSG.approved ,4:MSG.deny "
                                   headerKey="" headerValue="MSG.SIK.262"/>

                    <%--<s:select name="approveDepositForm.statusB" id="status" cssClass="txtInput"
                              list="#{'1':'Chưa duyệt','2':'Đã duyệt','4':'Từ chối'}"
                              headerKey="" headerValue="--Trạng thái--"/>--%>
                </td>
            </tr>
            <tr>
                <td colspan="4">
                    <tags:displayResult id="displayResultMsgClient" property="returnMsg" propertyValue="returnMsgValue" type="key" />
                </td>
            </tr>
        </table>
    </tags:imPanel>
    <%--</fieldset>--%>
    <br />

    <div align="center">
        <%--<sx:submit parseContent="true" executeScripts="true"
                   formId="approveDepositForm"
                   targets="bodyContent"
                   cssStyle="width:80px;"
                   value="MSG.find"  beforeNotifyTopics="approveDepositAction/searchBankReceipt"/>--%>
        <tags:submit targets="bodyContent" formId="approveDepositForm"
                     cssStyle="width:80px;" value="MSG.GOD.009"
                     preAction="approveDepositAction!searchBankReceipt.do"
                     />
        &nbsp;&nbsp;&nbsp;
        <sx:submit parseContent="true" executeScripts="true"
                   formId="reasonForm" loadingText="Processing..."
                   showLoadingText="true" targets="bodyContent"
                   cssStyle="width:80px;"
                   key="MSG.GOD.018"  beforeNotifyTopics="approveDepositAction/resetBankReceipt"/>

        <%--tags:submit targets="bodyContent" formId="approveDepositForm"
                     cssStyle="width:80px;" value="MSG.GOD.018"
                     preAction="approveDepositAction!acceptApprove.do?type=1"
                     /--%>
    </div>
    <br/>
    <script type="text/javascript">

        var _myWidget1 = dojo.widget.byId("shopCodeB");
        _myWidget1.textInputNode.focus();


        
        //lang nghe, xu ly su kien khi click nut "Tim kiem"
        dojo.event.topic.subscribe("approveDepositAction/getListShopCodeB", function(event, widget){
            widget.href = "approveDepositAction!getListShopCodeB.do" + "?" + token.getTokenParamString();
        });

        dojo.event.topic.subscribe("approveDepositAction/displayBankNameB", function(value, key, text, widget){
            if(key != null){
                getInputText("getBankNameBA.do?bankAccountId="+key);
            }
        });

        dojo.event.topic.subscribe("approveDepositAction/displayShopNameB", function(value, key, text, widget){
            if(key != null){
                getInputText("getShopNameBA.do?shopIdB="+key+"&channelTypeIdB="+document.getElementById("channelTypeIdB").value);
            }
        });

        dojo.event.topic.subscribe("approveDepositAction/searchBankReceipt", function(event, widget){
            var _myWidget = dojo.widget.byId("shopCodeB");
            var textShopCode = _myWidget.textInputNode.value.trim();
            if(textShopCode != "" && isHtmlTagFormat(textShopCode)){
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.001"/>';
                //$( 'displayResultMsgClient' ).innerHTML = "Không nên nhập thẻ html vào mã cửa hàng/đại lý/CTV";
                _myWidget.textInputNode.select();
                event.cancel = true;
                return;
            }
            var _myWidget1 = dojo.widget.byId("accountNoB");
            var textAccountNo = _myWidget1.textInputNode.value.trim();
            if(textAccountNo != "" && isHtmlTagFormat(textAccountNo)){
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.002"/>';
                //$( 'displayResultMsgClient' ).innerHTML = "Không nên nhập thẻ html vào số tài khoản";
                _myWidget1.textInputNode.select();
                event.cancel = true;
                return;
            }            
            var bankNameB= document.getElementById('bankNameB');            
            if (textAccountNo != "" && bankNameB.value ==""){
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.003"/>';
                //$( 'displayResultMsgClient' ).innerHTML = "!!!Lỗi. Số tài khoản không tồn tại";
                _myWidget1.textInputNode.select();
                event.cancel = true;
                return;
            }

            
            //MrSun
            var checkDate = true;
            var dateExported= dojo.widget.byId("fromDateB");
            if(dateExported.domNode.childNodes[1].value.trim() != "" &&
                !isDateFormat(dateExported.domNode.childNodes[1].value)){
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.004"/>';
                //$( 'displayResultMsgClient' ).innerHTML='Ngày nộp từ không hợp lệ';
                dateExported.domNode.childNodes[1].focus();
                event.cancel = true;
                return;
            }
            if (dateExported.domNode.childNodes[1].value.trim() == "")
                checkDate = false;

            var dateExported1 = dojo.widget.byId("toDateB");
            if(dateExported1.domNode.childNodes[1].value.trim() != "" &&
                !isDateFormat(dateExported1.domNode.childNodes[1].value)){
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.005"/>';
                //$( 'displayResultMsgClient' ).innerHTML='Ngày nộp đến không hợp lệ';
                dateExported1.domNode.childNodes[1].focus();
                event.cancel = true;
                return;
            }
            if (dateExported1.domNode.childNodes[1].value.trim() == "")
                checkDate = false;

            if (checkDate)
                if(!isCompareDate(dateExported.domNode.childNodes[1].value.trim(),dateExported1.domNode.childNodes[1].value.trim(),"VN")){
                    $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.006"/>';
                    //$( 'displayResultMsgClient' ).innerHTML='Ngày nộp từ phải nhỏ hơn Ngày nộp đến';
                    dateExported.domNode.childNodes[1].focus();
                    dateExported.domNode.childNodes[1].select();
                    event.cancel = true;
                    return;
                }
            //MrSun


                        
            if($( 'fromAmountB' ).value.trim() != "" && !isMoneyFormat($( 'fromAmountB' ).value.trim())){
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.007"/>';
                //$( 'displayResultMsgClient' ).innerHTML = "Số tiền nộp từ không hợp lệ";
                $( 'fromAmountB' ).select();
                event.cancel = true;
                return;
            }
            if($( 'toAmountB' ).value.trim() != "" && !isMoneyFormat($( 'toAmountB' ).value.trim())){
                $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.008"/>';
                //$( 'displayResultMsgClient' ).innerHTML = "Số tiền nộp đến không hợp lệ";
                $( 'toAmountB' ).select();
                event.cancel = true;
                return false;
            }

            widget.href = "approveDepositAction!searchBankReceipt.do" + "?" + token.getTokenParamString();
        });

        //lang nghe, xu ly su kien khi click nut "Reset"
        dojo.event.topic.subscribe("approveDepositAction/resetBankReceipt", function(event, widget){
            //document.getElementById('sChannelTypeId').value="";
            document.getElementById('shopNameB').value="";
            document.getElementById('bankNameB').value="";
            document.getElementById('telecomServiceIdB').value="";
            document.getElementById('status').value="";
            document.getElementById('fromAmountB').value="";
            document.getElementById('toAmountB').value="";
            dojo.widget.byId("shopCodeB").textInputNode.value = "";
            dojo.widget.byId("accountNoB").textInputNode.value = "";
        <%--dojo.widget.byId("fromDateB").domNode.childNodes[1].value = "";
        dojo.widget.byId("toDateB").domNode.childNodes[1].value = "";--%>
                event.cancel = true;
            });


            //lang nghe, xu ly su kien khi click nut "Phê duyệt"
            dojo.event.topic.subscribe("approveDepositAction/acceptApprove", function(event, widget){
                var result = isCheckBoxChecked('_1');
                if(result >0){
                    if (result == 3)
                        $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.009"/>';
                    //$( 'displayResultMsgClient' ).innerHTML = "Bạn phải chọn giấy nộp tiền chưa được phê duyệt để duyệt";
                    event.cancel = true;
                    return false;
                }

                //if (!confirm("Bạn có chắc chắn muốn phê duyệt các giấy nộp tiền đã chọn?"))
                if (!confirm(getUnicodeMsg("<s:text name="MSG.DET.001"/>"))){
                    event.cancel = true;
                    return false;
                }

                widget.href = "approveDepositAction!acceptApprove.do?type=2" + "&" + token.getTokenParamString();
            });
            //lang nghe, xu ly su kien khi click nut "Hủy phê duyệt"
            dojo.event.topic.subscribe("approveDepositAction/cancelApprove", function(event, widget){
                var result = isCheckBoxChecked('_2');
                if(result >0){
                    if (result == 3)
                        $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.010"/>';
                    //$( 'displayResultMsgClient' ).innerHTML = "Bạn phải chọn giấy nộp tiền đã được phê duyệt để huỷ";
                    event.cancel = true;
                    return false;
                }

                //if (!confirm("Bạn có chắc chắn muốn huỷ phê duyệt các giấy nộp tiền đã chọn?"))
                if (!confirm(getUnicodeMsg("<s:text name="MSG.DET.002"/>"))){
                    event.cancel = true;
                    return false;
                }

                widget.href = "approveDepositAction!acceptApprove.do?type=1" + "&" + token.getTokenParamString();
            });
            //lang nghe, xu ly su kien khi click nut "Từ chối"
            dojo.event.topic.subscribe("approveDepositAction/denyApprove", function(event, widget){
                var result = isCheckBoxChecked('_1');
                if(result >0){
                    if (result == 3)
                        $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.011"/>';
                    //$( 'displayResultMsgClient' ).innerHTML = "Bạn phải chọn giấy nộp tiền chưa được phê duyệt để từ chối";
                    event.cancel = true;
                    return;
                }

                //if (!confirm("Bạn có chắc chắn muốn từ chối các giấy nộp tiền đã chọn?"))
                if (!confirm(getUnicodeMsg("<s:text name="MSG.DET.003"/>"))){
                    event.cancel = true;
                    return false;
                }

                widget.href = "approveDepositAction!acceptApprove.do?type=4" + "&" + token.getTokenParamString();
            });

            //ham phuc vu viec phan trang
            pageNavigator = function (ajaxTagId, pageNavigator, pageNumber){
                dojo.widget.byId("updateContent").href = 'approveDepositAction!pageNavigator.do?' + pageNavigator + "=" + pageNumber;
                dojo.event.topic.publish('updateContent');
            }

            textFieldNF($('fromAmountB'));
            textFieldNF($('toAmountB'));

            //Kiem tra co ban ghi nao duoc chon hay khong?
        <%--
        1: chua chon
        2: chon khac loai
        3: ok
        --%>
            isCheckBoxChecked = function(funcType){
        
        

                if(document.getElementById('tblListBankReceipt') == null)
                {
                    $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.012"/>';
                    //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn giấy nộp tiền";
                    return 1;
                }
                var i = 0;
                var tbl = $( 'tblListBankReceipt' );
                var inputs = [];
            
                var isChecked = false;
                var isDiff = false;
                var subType = "";
                var isDiffFunc = false;

                inputs = tbl.getElementsByTagName( "input" );
                
                for( i = 1; i < inputs.length; i++ ) {
                    if( inputs[i].type == "checkbox" && inputs[i].checked == true ) {
                        //Co phan tu duoc chon
                        isChecked = true;
                    
                        if (inputs[i-1].type == "hidden" && inputs[i-1].value.indexOf('_')>=0){

                            //Co cac phan tu khac loai cung duoc chon
                            if (isDiff)
                                continue;
                            if (subType != "" && subType != inputs[i-1].value){
                                isDiff = true;
                                continue;
                            }
                            subType = inputs[i-1].value;

                            //Co phan tu khong thuoc chuc nang dang xu ly
                            if (isDiffFunc)
                                continue;
                            if (funcType != inputs[i-1].value){
                                isDiffFunc = true;
                            }
                        }
                    }
                }
                if (!isChecked){
                    $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.012"/>';
                    //$( 'displayResultMsgClient' ).innerHTML = "Bạn chưa chọn giấy nộp tiền!";
                    return 1;
                }
                else{
                    if (isDiff){
                        $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.013"/>';
                        //$( 'displayResultMsgClient' ).innerHTML = "Bạn đã chọn giấy nộp tiền không cùng loại!";
                        return 2;
                    }
                    else if (isDiffFunc){
                        $('displayResultMsgClient').innerHTML= '<s:text name="ERR.DET.014"/>';
                        //$( 'displayResultMsgClient' ).innerHTML = "Giấy nộp tiền đã được thực hiện!";
                        return 3;
                    }
                    else{
                        $( 'displayResultMsgClient' ).innerHTML = "";
                        return 0;
                    }
                }
            }
            //Ductm_sx:submit--->tags:submit cần có hàm validate
            //            validateForm = function(){
            //                return true;
            //            }
    </script>

    <%-- Danh mục giấy nộp tiền --%>
    <sx:bind id="updateContent" indicator="overlay" events="onclick" listenTopics="updateContent" targets="bodyContent" separateScripts="true" executeScripts="true"/>
    <div id="listBankReceipt">
        <%--<fieldset style="width:95%; padding:10px 10px 10px 10px">
            <legend class="transparent">Danh sách giấy nộp tiền </legend>--%>
        <tags:imPanel title="MSG.list.doc.deposit">
            <jsp:include page="listApproveDeposit.jsp"/>
        </tags:imPanel>
        <%--</fieldset>--%>
    </div>
    <br>
    <s:if test="#request.listBankReceipt != null && #request.listBankReceipt.size() > 0">
        <div align="center">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="approveDepositForm" loadingText="Processing..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.approve"  beforeNotifyTopics="approveDepositAction/acceptApprove"/>
            <!--           Phê duyệt-->
            <%--tags:submit targets="bodyContent" formId="approveDepositForm"
                         cssStyle="width:80px;" value="button.insert"
                         preAction="approveDepositAction!acceptApprove.do?type=2"
                         /--%>
            &nbsp;&nbsp;&nbsp;
            <sx:submit parseContent="true" executeScripts="true"
                       formId="approveDepositForm" loadingText="Processing..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:90px"
                       key="MSG.reject.approve"  beforeNotifyTopics="approveDepositAction/cancelApprove"/>
            <!--            Hủy phê duyệt-->
            <%--tags:submit targets="bodyContent" formId="approveDepositForm"
                         cssStyle="width:80px;" value="MSG.reject.approve"
                         preAction="approveDepositAction!acceptApprove.do?type=1"
                         /--%>
            &nbsp;&nbsp;&nbsp;
            <sx:submit parseContent="true" executeScripts="true"
                       formId="approveDepositForm" loadingText="Processing..."
                       showLoadingText="true" targets="bodyContent"
                       cssStyle="width:80px"
                       key="MSG.deny"  beforeNotifyTopics="approveDepositAction/denyApprove"/>
            <!--            Từ chối-->
            <%--tags:submit targets="bodyContent" formId="approveDepositForm"
                         cssStyle="width:80px;" value="MSG.deny"
                         preAction="approveDepositAction!acceptApprove.do?type=4"
                         /--%>
        </div>
    </s:if>
</s:form>
