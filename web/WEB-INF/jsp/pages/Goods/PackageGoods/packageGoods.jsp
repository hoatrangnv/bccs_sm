<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : saleServices
    Created on : Mar 13, 2009, 4:42:09 PM
    Author     : tamdt1
--%>

<%--
    Note: hien thi thong tin ve saleService
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="http://displaytag.sf.net" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>
<%
        request.setAttribute("contextPath", request.getContextPath());
%>

<sx:div id="divListPackageGoods"  cssStyle="padding:3px;">



    <s:if test="#request.packageGoodsMode == 'prepareAddOrEdit'">
        <s:set var="readonly" value="false" scope="request"/>
    </s:if>
    <s:else>
        <s:set var="readonly" value="true" scope="request"/>
    </s:else>

    <!-- phan hien thi thong tin message -->
    <div>
        <tags:displayResult id="message" property="message" type="key"/>
    </div>

    <!-- phan hien thi thong tin ve dich vu -->
    <s:form theme="simple" action="packageGoodsAction.do" id="saleServicesForm" method="post">
<s:token/>

        <s:hidden name="saleServicesForm.saleServicesId" id="saleServicesForm.saleServicesId"/>

        <table class="inputTbl4Col">
            <tr>
                <td class="label"><tags:label key="MSG.GOD.code" required="true"/><s:if test="#request.readonly == false"></s:if></td>
                <td class="text">
                    <s:textfield name="saleServicesForm.code" id="saleServicesForm.code" maxlength="50" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
                <td class="label"><tags:label key="MSG.package.goods.name" required="true"/><s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <s:textfield name="saleServicesForm.name" id="saleServicesForm.name" maxlength="100" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
            </tr>
            <tr>
                <td  class="label"><tags:label key="MSG.GOD.093" required="true"/><s:if test="#request.readonly == false"></s:if></td>
                <td  class="text">
                    <%--<s:select name="saleServicesForm.telecomServicesId"
                              id="saleServicesForm.telecomServicesId"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              list="%{#request.listTelecomServices != null ? #request.listTelecomServices : #{}}"
                              listKey="telecomServiceId" listValue="telServiceName"
                              headerKey="" headerValue="--Chọn dịch vụ VT--"/>--%>
                    <%--tags:imSelect name="saleServicesForm.telecomServicesId" id="saleServicesForm.telecomServicesId"
                                   cssClass="txtInputFull" disabled="false"
                                   list="listTelecomServices"
                                   headerKey="" headerValue="MSG.GOD.274"/--%>
                    <tags:imSelect name="saleServicesForm.telecomServicesId" id="saleServicesForm.telecomServicesId"
                                   cssClass="txtInputFull" disabled="false"
                                   list="listTelecomServices"
                                   headerKey="" headerValue="MSG.RET.158"/>
                </td>
                <%--td>Trạng thái<s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                <td>
                    <s:select name="saleServicesForm.status"
                              id="saleServicesForm.status"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              list="#{'1':'Hoạt động', '0':'Không hoạt động'}"/>
                </td>
                </tr>
                <tr--%>
                <td class="label"><tags:label key="MSG.GOD.031"/></td>
                <td   class="text">
                    <s:textfield name="saleServicesForm.notes" id="saleServicesForm.notes" maxlength="450" cssClass="txtInputFull" readonly="#request.readonly"/>
                </td>
            </tr>
        </table>
    </s:form>

    <!-- phan nut tac vu -->
    <s:if test="#request.readonly == false">
        <div align="center" style="vertical-align:top">
            <%--sx:submit parseContent="true" executeScripts="true"
                           formId="saleServicesForm" loadingText="%{getText('MSG.loading')}"
                           cssStyle="width:80px;"
                           showLoadingText="true" targets="divDisplayInfo"
                           value="Chấp nhận"  beforeNotifyTopics="packageGoodsAction/addOrEditPackageGoods"/--%>
            <%--tags:submit formId="saleServicesForm" cssStyle="width:80px;"
                         showLoadingText="true" targets="divListPackageGoods" value="Chấp nhận"
            validateFunction="validateBeforeUpdate()" preAction="packageGoodsAction!addOrEditPackageGoods.do" /--%>

            <sx:submit parseContent="true" executeScripts="true"
                       formId="saleServicesForm" loadingText="Processing..."
                       cssStyle="width:80px;"
                       showLoadingText="true" targets="divListPackageGoods"
                       key="MSG.accept"  beforeNotifyTopics="packageGoodsAction/addOrEditPackageGoods"/>

            <sx:submit parseContent="true" executeScripts="true"
                       formId="saleServicesForm" loadingText="Processing..."
                       cssStyle="width:80px;"
                       showLoadingText="true" targets="divDisplayInfo"
                       key="MSG.cancel2"  beforeNotifyTopics="packageGoodsAction/cancelAddPackageGoods"/>
        </div>
        <script language="javscript">
            disableTab('sxdivListPackageGoodsPrice');
            disableTab('sxdivPackageGoodsModelList');
        </script>
        <script language="javascript">
            //lang nghe, xu ly su kien khi click nut "Chap nhan"
            /*
                dojo.event.topic.subscribe("packageGoodsAction/addOrEditPackageGoods", function(event, widget){
                    if(checkRequiredFields() && checkValidFields()) {
                        setAction(widget,'divDisplayInfo','packageGoodsAction!addOrEditPackageGoods.do');
                    } else {
                        event.cancel = true;
                    }
                });
             */

            dojo.event.topic.subscribe("packageGoodsAction/addOrEditPackageGoods", function(event, widget){
                if(validateBeforeUpdate()) {

                    widget.href = "packageGoodsAction!addOrEditPackageGoods.do" + '?' + token.getTokenParamString() ;
                } else {
                    event.cancel = true;
                }
            });
            validateBeforeUpdate =function(){
                return (checkRequiredFields() && checkValidFields());
            }
            //lang nghe, xu ly su kien khi click nut "Bo qua"
            dojo.event.topic.subscribe("packageGoodsAction/cancelAddPackageGoods", function(event, widget){
                setAction(widget,'divDisplayInfo','packageGoodsAction!displayPackageGoods.do')
            });

            //ham kiem tra cac truong bat buoc
            checkRequiredFields = function() {
                //truong ma dich vu
                if(trim($('saleServicesForm.code').value) == "") {
                    $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.001')"/>';
                    //$('message').innerHTML = '!!!Lỗi. Trường mã dịch vụ không được để trống';
                    $('saleServicesForm.code').select();
                    $('saleServicesForm.code').focus();
                    return false;
                }
                if(trim($('saleServicesForm.name').value) == "") {
                    $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.002')"/>';
                    //$('message').innerHTML = '!!!Lỗi. Trường tên dịch vụ không được để trống';
                    $('saleServicesForm.name').select();
                    $('saleServicesForm.name').focus();
                    return false;
                }
                if(trim($('saleServicesForm.telecomServicesId').value) == "") {
                    $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.003')"/>';
                    //$('message').innerHTML= '!!!Lỗi. Chọn dịch vụ viễn thông';
                    $('saleServicesForm.telecomServicesId').focus();
                    return false;
                }

                return true;
            }

            //kiem tra ma khong duoc chua cac ky tu dac biet
            checkValidFields = function() {
                if(!isValidInput($('saleServicesForm.code').value, false, false)) {
                    $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.004')"/>';
                    //$('message').innerHTML = '!!!Lỗi. Trường mã gói hàng chỉ được chứa các ký tự A-Z, a-z, 0-9, _';
                    $('saleServicesForm.code').select();
                    $('saleServicesForm.code').focus();
                    return false;
                }

                if(isHtmlTagFormat(trim($('saleServicesForm.name').value))) {
                    $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.005')"/>';
                    //$('message').innerHTML="!!!Lỗi. Tên gói hàng không được chứa các thẻ HTML";
                    $('saleServicesForm.name').select();
                    $('saleServicesForm.name').focus();
                    return false;
                }

                if(isHtmlTagFormat(trim($('saleServicesForm.notes').value))) {
                    $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.006')"/>';
                    //$('message').innerHTML="!!!Lỗi. Trường ghi chú không được chứa các thẻ HTML";
                    $('saleServicesForm.notes').select();
                    $('saleServicesForm.notes').focus();
                    return false;
                }

                if($('saleServicesForm.notes').value.length > 450) {
                    $('message').innerHTML= '<s:property escapeJavaScript="true"  value="getText('ERR.GOD.007')"/>';
                    //$('message').innerHTML="!!!Lỗi. Trường ghi chú quá dài";
                    $('saleServicesForm.notes').select();
                    $('saleServicesForm.notes').focus();
                    return false;
                }

                //trim cac truong can thiet
                $('saleServicesForm.code').value = trim($('saleServicesForm.code').value);
                $('saleServicesForm.name').value = trim($('saleServicesForm.name').value);
                $('saleServicesForm.notes').value = trim($('saleServicesForm.notes').value);


                return true;
            }

        </script>
    </s:if>
    <s:else>
        <div align="center" style="vertical-align:top">
            <sx:submit parseContent="true" executeScripts="true"
                       formId="saleServicesForm" loadingText="Processing..."
                       cssStyle="width:80px;"
                       showLoadingText="true" targets="divListPackageGoods"
                       key="MSG.add"  beforeNotifyTopics="packageGoodsAction/prepareAddPackageGoods"/>

            <sx:submit parseContent="true" executeScripts="true"
                       formId="saleServicesForm" loadingText="Processing..."
                       cssStyle="width:80px;"
                       showLoadingText="true" targets="divListPackageGoods"
                       key="MSG.SAE.033"  beforeNotifyTopics="packageGoodsAction/prepareEditPackageGoods"/>

            <sx:submit parseContent="true" executeScripts="true"
                       formId="saleServicesForm" loadingText="Processing..."
                       cssStyle="width:80px;"
                       showLoadingText="true" targets="divListPackageGoods"
                       key="MSG.GOD.021"  beforeNotifyTopics="packageGoodsAction/delPackageGoods"/>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <input type="button" style="width:80px;" value="${fn:escapeXml(imDef:imGetText('MSG.GOD.290'))} " onclick="prepareAddPackageGoodsPrice()">
            <input type="button" style="width:80px;" value="${fn:escapeXml(imDef:imGetText('MSG.add.goods'))}" onclick="prepareAddPackageGoodsDetail()">
        </div>
        <script language="javscript">
            enableTab('sxdivListPackageGoodsPrice');
            enableTab('sxdivPackageGoodsModelList');
        </script>
        <script language="javascript">



            //lang nghe, xu ly su kien khi click nut "Them"
            dojo.event.topic.subscribe("packageGoodsAction/prepareAddPackageGoods", function(event, widget){
                widget.href = "packageGoodsAction!prepareAddPackageGoods.do" + '?' + token.getTokenParamString();
                // setAction(widget,'divListPackageGoods','packageGoodsAction!prepareAddPackageGoods.do');
            });

            //lang nghe, xu ly su kien khi click nut "Sua"
            dojo.event.topic.subscribe("packageGoodsAction/prepareEditPackageGoods", function(event, widget){
                widget.href = "packageGoodsAction!prepareEditPackageGoods.do" + '?' + token.getTokenParamString();
                //setAction(widget,'divListPackageGoods','packageGoodsAction!prepareEditPackageGoods.do')
            });

            //lang nghe, xu ly su kien khi click nut "Xoa"
            dojo.event.topic.subscribe("packageGoodsAction/delPackageGoods", function(event, widget){
                //if (confirm('Bạn có chắc chắn muốn xóa gói hàng này không?'))
                if(confirm("'<s:property escapeJavaScript="true"  value="getText('MSG.GOD.286')"/>'")) {
                    setAction(widget,'divListPackageGoods','packageGoodsAction!delPackageGoods.do' + '?' + token.getTokenParamString());
                    //widget.href = "aleServicesAction!delSaleServices.do";
                } else {
                    event.cancel = true;
                }

            });

            //ham xu ly su kien onclick cua nut "Them" (them gia dich vu)
            prepareAddPackageGoodsPrice = function() {
                openDialog("${contextPath}/packageGoodsAction!prepareAddPackageGoodsPrice.do" + '?' + token.getTokenParamString(), 750, 700, true);
            }

            //ham xu ly su kien onclick cua nut "Them" (them loai mat hang vao dich vu)
            prepareAddPackageGoodsDetail = function() {
                openDialog("${contextPath}/packageGoodsAction!prepareAddPackageGoodsDetail.do" + '?' + token.getTokenParamString(), 750, 700, true);
            }

            //ham xu ly su kien khi dong cua so popup sau khi them saleServicesDetail
            refreshListPackageGoodsDetail = function() {
                gotoAction('packageGoodsDetail','packageGoodsAction!refreshListPackageGoodsDetail.do' + '?' + token.getTokenParamString(),'saleServicesForm');
            }

            //ham xu ly su kien khi dong cua so popup
            refreshPriceList = function() {
                gotoAction('packageGoodsPrices','packageGoodsAction!refreshPriceList.do' + '?' + token.getTokenParamString());
            }

            //xu ly su kien onchange cua combobox
            dojo.event.topic.subscribe("select/stockModelId",  function(value, key, text){
                notifyEvent('stockModelId',key,'priceId','select2','${contextPath}/packageGoodsAction!getComboboxSource.do' + '?' + token.getTokenParamString());
            });

            //lang nghe, xu ly su kien khi click nut "Them" (them saleServiceModel)
            dojo.event.topic.subscribe("packageGoodsAction/addPackageGoodsModel", function(event, widget){
                var stockTypeId = $('stockTypeId').value;
                var stockModelId = "";
                var priceId = "";
                if(dojo.widget.byId("stockModelId").selectedResult != null) {
                    stockModelId = dojo.widget.byId("stockModelId").selectedResult[1];
                }
                if(dojo.widget.byId("priceId").selectedResult != null) {
                    priceId = dojo.widget.byId("priceId").selectedResult[1];
                }
                if(stockTypeId != "" && stockModelId != "" && priceId != null) {
                    //setAction(widget,'divSaleServicesModelList','saleServicesModelAction!addSaleServicesDetail.do')
                    widget.href = "packageGoodsAction!addPackageGoodsDetail.do" + '?' + token.getTokenParamString();
                } else {
                    alert('<s:property escapeJavaScript="true"  value="getText('MSG.GOD.287')"/>');
                    //alert('Các trường bắt buộc không được để trống');
                    event.cancel = true;
                }

            });

        </script>
    </s:else>
</sx:div>
<%--table class="inputTbl">
<tr>
<td>
<!-- danh sach gia dich vu -->

<tags:imPanel title="Danh sách giá gói hàng">
<div style="width:100%; height:150px; overflow:auto">
<sx:div id="divListPackageGoodsPrice">
<jsp:include page="listPackageGoodsPrices.jsp"/>
</sx:div>
</div>
</tags:imPanel>
</td>
</tr>
<tr>
<td>
<!-- danh sach mat hang thuoc dich vu -->
<tags:imPanel title="Danh sách hàng hoá trong gói hàng">

<div style="width:100%; height:250px; overflow:auto">
<sx:div id="divPackageGoodsModelList">
<jsp:include page="listPackageGoodsModels.jsp"/>
</sx:div>
</div>
</tags:imPanel>
</td>
</tr>
</table--%>


