<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : addCommItems
    Created on : Mar 10, 2009, 11:31:00 AM
    Author     : DatPV
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
%>

<s:if test="#request.commItemsMode == 'prepareAddOrEdit'">
    <s:set var="readonly" value="false" scope="request"/>
</s:if>
<s:else>
    <s:set var="readonly" value="true" scope="request"/>
</s:else>
<tags:imPanel title="Thông tin khoản mục">

    <!-- phan hien thi message -->
    <div>
        <tags:displayResult property="message" id="message" type="key"/>
    </div>
    
    
    <s:form action="commItemsAction" id="commItemsForm" method="post" theme="simple">
<s:token/>

        <s:hidden name="commItemsForm.itemId" id="commItemsForm.itemId"/>
        <s:hidden name="commItemsForm.itemGroupId" id="commItemsForm.itemGroupId"/>
        <table class="inputTbl6Col">
            <tr>
                <td class="label">Nhóm khoản mục</td>
                <td class="text">
                    <s:textfield name="commItemsForm.commItemGroupName" id="commItemsForm.commItemGroupName" theme="simple" readonly="true" maxlength="80" cssClass="txtInputFull"/>
                </td>
                <td class="label">Tên khoản mục <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <s:textfield name="commItemsForm.itemName" id="commItemsForm.itemName" readonly="#request.readonly" maxlength="150" cssClass="txtInputFull"/>
                </td>
                <td class="label">Mô tả</td>
                <td class="text">
                    <s:textfield name="commItemsForm.description" id="commItemsForm.description" cssClass="txtInputFull" readonly="#request.readonly" maxlength="500"/>
                </td>
            </tr>
            <tr>
                <td class="label">Ngày bắt đầu <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <tags:dateChooser id="commItemsForm.startDate" property="commItemsForm.startDate" styleClass="txtInputFull" readOnly="${fn:escapeXml(readonly)}" styleClass="txtInputFull"/>
                </td>
                <td class="label">Ngày kết thúc</td>
                <td class="text">
                    <tags:dateChooser id="commItemsForm.endDate" property="commItemsForm.endDate" styleClass="txtInputFull" readOnly="${fn:escapeXml(readonly)}" styleClass="txtInputFull" />
                </td>
                <td class="label">Trạng thái <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <s:select name="commItemsForm.status" id="commItemsForm.status"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              list="#{'1':'Hiệu lực','2':'Không hiệu lực'}"
                              headerKey="" headerValue="--Chọn trạng thái--"/>
                </td>
            </tr>
            <tr>
                <td class="label">Kiểu báo cáo <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <s:select name="commItemsForm.reportType" id="commItemsForm.reportType"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              list="#{'1':'Báo cáo hoa hồng','2':'Báo cáo chế tài'}"
                              headerKey="" headerValue="--Chọn kiểu báo cáo--"/>
                </td>
                <td class="label">Vị trí trong BC <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <s:textfield name="commItemsForm.itemOrder" id="commItemsForm.itemOrder" maxlength="3" readonly="#request.readonly" cssClass="txtInputFull" />
                </td>
                <td class="label">Đơn vị</td>
                <td class="text">
                    <s:textfield name="commItemsForm.unit" id="commItemsForm.unit" maxlength="80" readonly="#request.readonly" cssClass="txtInputFull"/>
                </td>
            </tr>
            <tr>                
                <td class="label">Kiểu DL nhập <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <s:select name="commItemsForm.inputType" id="commItemsForm.inputType"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              list="#{'1':'Hệ thống tự động','2':'Nhập tay số lượng','3':'Nhập tay số tiền'}"
                              headerKey="" headerValue="--Chọn kiểu DL nhập vào--"
                              onchange="changeInputType()"/>
                </td>
                <s:if test="#request.listCommCounters != null && #request.listCommCounters.size() > 0">
                    <td class="label">Bộ đếm <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                    <td class="text">
                        <s:select name="commItemsForm.counterId" id="commItemsForm.counterId"
                                  cssClass="txtInputFull"
                                  disabled="#request.readonly"
                                  headerKey="" headerValue="--Chọn bộ đếm--"
                                  list="%{#request.listCommCounters != null ? #request.listCommCounters : #{}}"
                                  listKey="counterId" listValue="counterName"
                                  onchange="changeCounter()"/>
                    </td>
                </s:if>
                <td class="label">Kiểm tra hồ sơ <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                <td class="text">
                    <s:select name="commItemsForm.checkedDoc" id="commItemsForm.checkedDoc"
                              cssClass="txtInputFull"
                              disabled="#request.readonly"
                              list="#{'1':'Có kiểm tra hồ sơ'}"
                              headerKey="0" headerValue="Không kiểm tra hồ sơ"/>
                </td>
            </tr>
            <s:if test="#session.listCounterParam != null && #session.listCounterParam.size() > 0">
                <tr>
                    <s:iterator value="#session.listCounterParam" id="counterParam" status="rowstatus">
                        <!-- lap qua list, thiet lap 3 phan tu/ hang -->
                        <s:if test="#rowstatus.index % 3 == 2">
                            <td class="label"><s:property escapeJavaScript="true"  value="%{#attr.counterParam.paramName}"/> <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                            <td class="text">
                                <s:textfield name="commItemsForm.arrCounterParam" value="%{commItemsForm.arrCounterParam[#rowstatus.index]}" readonly="#request.readonly" cssClass="txtInputFull"/>
                            </td>
                        </tr><tr>
                        </s:if>
                        <s:else>
                            <td class="label"><s:property escapeJavaScript="true"  value="%{#attr.counterParam.paramName}"/> <s:if test="#request.readonly == false">(<font color="red">*</font>)</s:if></td>
                            <td class="text">
                                <s:textfield name="commItemsForm.arrCounterParam" value="%{commItemsForm.arrCounterParam[#rowstatus.index]}" readonly="#request.readonly" cssClass="txtInputFull"/>
                            </td>
                        </s:else>
                    </s:iterator>
                </tr>
            </s:if>
        </table>
    </s:form>
    
    <!-- phan nut tac vu -->
    <s:if test="#request.readonly == false">
        <div align="center" style="width:100%; padding-bottom:5px; padding-top:15px;">
            <s:if test="#request.toEditCommItems == 'toEdit'">
                <sx:submit parseContent="true"
                           executeScripts="true"
                           targets="divDisplayInfo"
                           formId="commItemsForm"
                           value="Cập nhật"
                           cssStyle="width:80px;"
                           beforeNotifyTopics="commItemsAction/editCommItems"
                           errorNotifyTopics="errorAction" />
            </s:if>
            <s:else>
                <sx:submit parseContent="true"
                           executeScripts="true"
                           targets="divDisplayInfo"
                           formId="commItemsForm"
                           value="Thêm mới"
                           cssStyle="width:80px;"
                           beforeNotifyTopics="commItemsAction/addCommItems"
                           errorNotifyTopics="errorAction" />
            </s:else>
            
            <sx:submit parseContent="true"
                       executeScripts="true"
                       targets="divDisplayInfo"
                       formId="commItemsForm"
                       value="Bỏ qua"
                       cssStyle="width:80px;"
                       beforeNotifyTopics="commItemsAction/displayCommItems"
                       errorNotifyTopics="errorAction" />
        </div>
        
        <script language="javascript">
            
            //lang nghe xu ly su kien click cua nut "Dong y"
            dojo.event.topic.subscribe("commItemsAction/addCommItems", function(event, widget){
                if(checkRequiredFields() && checkValidFields()) {
                    if(confirm("Bạn có thực sự muốn thêm mới khoản mục?")){
                        widget.href = "commItemsAction!addOrEditCommItems.do?"+token.getTokenParamString();
                    } else {
                    event.cancel = true;
                }
            } else {
            event.cancel = true;
        }
    });
    dojo.event.topic.subscribe("commItemsAction/editCommItems", function(event, widget){
        if(checkRequiredFields() && checkValidFields()) {
            if(confirm("Bạn có thực sự muốn sửa khoản mục?")){
                widget.href = "commItemsAction!addOrEditCommItems.do?"+token.getTokenParamString();
            } else {
            event.cancel = true;
        }
    } else {
    event.cancel = true;
}
});
//lang nghe xu ly su kien click cua nut "Bo qua"
dojo.event.topic.subscribe("commItemsAction/displayCommItems", function(event, widget){
    widget.href = "commItemsAction!displayCommItems.do";
});

//xu ly su kien khi kieu du lieu nhap vao thay doi
changeInputType = function() {
    //phai truyen ngay, thang (trong datetime picker theo param do ko nhan trong form)
    var wgStartDate = dojo.widget.byId("commItemsForm.startDate");
    var startDate = wgStartDate.domNode.childNodes[1].value;
    var wgEndDate = dojo.widget.byId("commItemsForm.endDate");
    var endDate = wgEndDate.domNode.childNodes[1].value;
    var params = "commItemsForm.startDate=" + startDate;
    params = params + "&commItemsForm.endDate=" + endDate;
    
    gotoAction("divDisplayInfo", "${contextPath}/commItemsAction!changeInputType.do?" + params, 'commItemsForm');
    }
    
    //xu ly su kien khi bo dem thay doi
    changeCounter = function() {
        //phai truyen ngay, thang (trong datetime picker theo param do ko nhan trong form)
        var wgStartDate = dojo.widget.byId("commItemsForm.startDate");
        var startDate = wgStartDate.domNode.childNodes[1].value;
        var wgEndDate = dojo.widget.byId("commItemsForm.endDate");
        var endDate = wgEndDate.domNode.childNodes[1].value;
        var params = "commItemsForm.startDate=" + startDate;
        params = params + "&commItemsForm.endDate=" + endDate;
        
        gotoAction("divDisplayInfo", "${contextPath}/commItemsAction!changeCounter.do?" + params, 'commItemsForm');
        }
        
        //ham kiem tra cac truong bat buoc
        checkRequiredFields = function() {
            if(trim($('commItemsForm.itemName').value) == "") {
                $('message').innerHTML = "!!!Lỗi. Tên khoản mục không được để trống";
                $('commItemsForm.itemName').select();
                $('commItemsForm.itemName').focus();
                return false;
            }
            
            var wgStartDate= dojo.widget.byId("commItemsForm.startDate");
            if(trim(wgStartDate.domNode.childNodes[1].value) == "") {
                $('message').innerHTML = "!!!Lỗi. Ngày bắt đầu không được để trống";
                wgStartDate.domNode.childNodes[1].select();
                wgStartDate.domNode.childNodes[1].focus();
                return false;
            }
            
            if(trim($('commItemsForm.status').value) == "") {
                $('message').innerHTML = "!!!Lỗi. Chọn trạng thái";
                $('commItemsForm.status').focus();
                return false;
            }
            
            if(trim($('commItemsForm.reportType').value) == "") {
                $('message').innerHTML = "!!!Lỗi. Chọn kiểu báo cáo";
                $('commItemsForm.reportType').focus();
                return false;
            }
            
            if(trim($('commItemsForm.itemOrder').value) == "") {
                $('message').innerHTML = "!!!Lỗi. Vị trí trong báo cáo không được để trống";
                $('commItemsForm.itemOrder').select();
                $('commItemsForm.itemOrder').focus();
                return false;
            }
            
            if(trim($('commItemsForm.inputType').value) == "") {
                $('message').innerHTML = "!!!Lỗi. Chọn kiểu dữ liệu nhập";
                $('commItemsForm.inputType').focus();
                return false;
            }
            if ((trim($('commItemsForm.inputType').value) == "1") && (trim($('commItemsForm.counterId').value) == "")) {
                $('message').innerHTML = "!!!Lỗi. Chọn bộ đếm";
                $('commItemsForm.counterId').focus();
                return false;
            }
            
            return true;
        }
        
        //ham kiem tra du lieu cac truong co hop le hay ko
        checkValidFields = function() {
            if(isHtmlTagFormat(trim($('commItemsForm.itemName').value))) {
                $('message').innerHTML = "!!!Lỗi. Tên khoản mục không được chứa các thẻ HTML";
                $('commItemsForm.itemName').select();
                $('commItemsForm.itemName').focus();
                return false;
            }
            
            if(!isInteger(trim($('commItemsForm.itemOrder').value))) {
                $('message').innerHTML = "!!!Lỗi. Vị trí trong báo cáo phải là số không âm";
                $('commItemsForm.itemOrder').select();
                $('commItemsForm.itemOrder').focus();
                return false;
            }
            
            return true;
        }
        
        </script>
        
    </s:if>
    <s:else>
        <div align="center" style="width:100%; padding-bottom:5px; padding-top:15px;">
            <sx:submit parseContent="true"
                       executeScripts="true"
                       targets="divDisplayInfo"
                       formId="commItemsForm"
                       value="Thêm"
                       cssStyle="width:80px;"
                       beforeNotifyTopics="commItemsAction/prepareAddCommItems"
                       errorNotifyTopics="errorAction" />
            <sx:submit parseContent="true"
                       executeScripts="true"
                       targets="divDisplayInfo"
                       formId="commItemsForm"
                       value="Sửa"
                       cssStyle="width:80px;"
                       beforeNotifyTopics="commItemsAction/prepareEditCommItems"
                       errorNotifyTopics="errorAction" />
            <sx:submit parseContent="true"
                       executeScripts="true"
                       targets="divDisplayInfo"
                       formId="commItemsForm"
                       value="Xóa"
                       cssStyle="width:80px;"
                       beforeNotifyTopics="commItemsAction/deleteCommItems"
                       errorNotifyTopics="errorAction" />
            <%--<input type="button" value="Thêm phí KM" style="width:85px;" onclick="prepareAddCommFees()">--%>
            <input type="button" value="Thêm phí KM" style="width:85px;" onclick="prepareAddCommFeesAdvance()">
        </div>
        
        <script language="javascript">
            
            //lang nghe xu ly su kien click cua nut "Them" (them khoan muc)
            dojo.event.topic.subscribe("commItemsAction/prepareAddCommItems", function(event, widget){
                widget.href = "commItemsAction!prepareAddCommItems.do";
            });
            
            //lang nghe xu ly su kien click cua nut "Sua"
            dojo.event.topic.subscribe("commItemsAction/prepareEditCommItems", function(event, widget){
                widget.href = "commItemsAction!prepareEditCommItems.do";
            });
            
            //lang nghe xu ly su kien click cua nut "Xoa"
            dojo.event.topic.subscribe("commItemsAction/deleteCommItems", function(event, widget){
                if(confirm('Bạn chắc chắn muốn xóa khoản mục này?')) {
                    widget.href = "commItemsAction!deleteCommItems.do?"+token.getTokenParamString();
                } else {
                event.cancel = true;
            }
            
        });
        
        </script>
        
    </s:else>
    
    <!-- danh sach tat ca cac gia cua mat hang -->
    <table class="inputTbl">
        <tr>
            <td>
                <fieldset style="width:100%">
                    <legend>Danh sách phí khoản mục</legend>
                    <div style="width:100%;">
                        <sx:div id="divListCommFees">
                            <jsp:include page="listCommFees.jsp"/>
                        </sx:div>
                    </div>
                </fieldset>
            </td>
        </tr>
    </table>
    
    <script language="javascript">
        //bat popup them phi khoan muc moi
        prepareAddCommFees = function() {
            openDialog("${contextPath}/commItemsAction!prepareAddCommFees.do", 750, 500, true);
            }
            
            prepareAddCommFeesAdvance = function() {
                openDialog("${contextPath}/commItemsAction!prepareAddCommFeesAdvance.do", 750, 500, true);
                }
                
                //cap nhat lai danh sach gia
                refreshListCommFees = function() {
                    gotoAction('divListCommFees','commItemsAction!refreshListCommFees.do');
                }
                
                
    </script>
    
    
    </tags:imPanel>
    
    
    
    
