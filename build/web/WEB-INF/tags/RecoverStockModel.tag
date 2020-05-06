<%-- 
    Document   : RecoverStockModel
    Created on : Sep 23, 2009, 5:23:51 PM
    Author     : Vunt
--%>

<%@tag description="Cac thong tin lenh xuat kho" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib  prefix="sx" uri="/struts-dojo-tags" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@attribute name="shopRecType"%>

<%@attribute name="readOnly"%>
<%@attribute name="disabled"%>
<%@attribute name="viewOnly"%>
<%@attribute name="type"%>
<%@attribute name="subForm"%>
<%@taglib prefix="imDef" uri="imDef" %>

<%
            request.setAttribute("contextPath", request.getContextPath());
%>


<%
            String property = "";
            if (subForm != null && !"".equals(subForm.trim())) {
                property = subForm.trim() + ".";
                request.setAttribute("property", property);
            }
%>

<%String require = "&nbsp;(<font color='red'>*</font>)";%>

<%
            com.viettel.database.DAO.BaseDAOAction baseDAOAction = new com.viettel.database.DAO.BaseDAOAction();

            if (viewOnly != null && "true".equals(viewOnly)) {
                require = "";
            }
            //String title = "Thông tin lệnh xuất";            
            //String code = "Mã lệnh xuất";
            //String errorAction = "Chưa nhập mã lệnh xuất";
            //String errorAction2 = "Mã lệnh không được chứa các ký tự đặc biệt";
            String title = baseDAOAction.getText("MSG.GOD.149");
            String code = baseDAOAction.getText("MSG.GOD.150");
            String errorAction = baseDAOAction.getText("ERR.STK.093");
            String errorAction2 = baseDAOAction.getText("ERR.STK.094");

            if ("note".equals(type)) {
                //title = "Thông tin phiếu nhập";
                //code = "Mã phiếu nhập";
                //errorAction = "Chưa nhập mã phiếu xuất";
                //errorAction2 = "Mã phiếu không được chứa các ký tự đặc biệt";
                title = baseDAOAction.getText("MSG.GOD.131");
                code = baseDAOAction.getText("MSG.GOD.132");
                errorAction = baseDAOAction.getText("ERR.STK.095");
                errorAction2 = baseDAOAction.getText("ERR.STK.096");
            }
%>

<%--<tags:imPanel title="Thông tin phiếu nhập">--%>
<tags:imPanel title="MSG.GOD.131">
    <s:hidden name="%{#attr.subForm+'.status'}" id="%{#attr.subForm+'.status'}"/>
    <s:hidden name="%{#attr.subForm+'.actionId'}" id="%{#attr.subForm+'.actionId'}"/>
    <table class="inputTbl4Col">
        <tr>
            <td style="width:15%; ">
                <tags:label key="MSG.GOD.132" required="true"/>
                <!--                Mã phiếu nhập (<font color="red">*</font>)-->
            </td>
            <td style="width:35%; " class="oddColumn">
                <s:textfield theme="simple" maxlength="20"   name="%{#attr.subForm+'.cmdExportCode'}" id="%{#attr.subForm+'.cmdExportCode'}" cssClass="txtInputFull" readonly="true"/>
            </td>
            <td style="width:15%; "></td>
            <td></td>
        </tr>
        <tr>
            <td>
                <tags:label key="MSG.recoverStore"/>
                <!--                Kho thu hồi-->
            </td>
            <td class="oddColumn">
                <s:hidden theme="simple" name="%{#attr.subForm+'.shopExportId'}" id="%{#attr.subForm+'.shopExportId'}"/>
                <s:textfield theme="simple" name="%{#attr.subForm+'.shopExportName'}" id="%{#attr.subForm+'.shopExportName'}" cssClass="txtInputFull" readonly="true"/>
            </td>
            <td>
                <tags:label key="MSG.recoverPerson"/>
                <!--                Người thu hồi-->
            </td>
            <td>
                <s:textfield theme="simple" name="%{#attr.subForm+'.sender'}" id="%{#attr.subForm+'.sender'}" cssClass="txtInputFull" readonly="true"  />
                <s:hidden theme="simple" name="%{#attr.subForm+'.senderId'}"/>
            </td>
        </tr>
        <%--<logic:equal name="shopRecType" value="staff">--%>
        <tr>
            <%--
            <td style="width:10%; " >
                Thu hồi từ đại lý (<font color="red">*</font>)
            </td>
            <td>
                <s:select theme="simple"  name="%{#attr.subForm+'.shopImportedId'}"
                          id="%{#attr.subForm+'.shopImportedId'}"
                          cssClass="txtInput"
                          disabled="%{#attr.disabled}"
                          headerKey="" headerValue="---Chọn kho thu hồi---"
                          list="%{#request.lstStaff != null?#request.lstStaff:#{}}"
                          listKey="shopId" listValue="name"
                          onchange="resetStockTypeCombobox()"
                          />
                          </td>
            --%>
            <td>
                <tags:label key="MSG.recoverFromShop" required="true"/>
                <!--                Thu hồi từ đại lý (<font color="red">*</font>)-->
            </td>
            <td class="oddColumn">
                <tags:imSearch codeVariable="exportStockForm.shopCode" nameVariable="exportStockForm.shopName"
                               codeLabel="MSG.SAE.085" nameLabel="MSG.SAE.086"
                               searchClassName="com.viettel.im.database.DAO.SaleDAO"
                               searchMethodName="getListShop"
                               getNameMethod="getNameShop"/>     

            </td>         
            <s:if test="#attr.disabled =='true'">
                <s:hidden  theme="simple"  name="%{#attr.subForm+'.shopImportedId'}" id="%{#attr.subForm+'.shopImportedId'}"/>
            </s:if>
            <s:hidden  theme="simple"  name="shopSelect" id="shopSelect" value=""/>        

        </tr>

        <tr>
            <td>
                <tags:label key="MSG.reasonRecover" required="true" />
                <!--                Lý do thu hồi (<font color="red">*</font>)-->
            </td >
            <td class="oddColumn">
                <tags:imSelect theme="simple"  name="${subForm}.reasonId"
                               id="${subForm}.reasonId"
                               cssClass="txtInputFull"
                               disabled="${disabled}"
                               headerKey="" headerValue="MSG.SAE.013"
                               list="lstReasonExp"
                               listKey="reasonId" listValue="reasonName"/>

                <s:if test="#attr.disabled =='true'">
                    <s:hidden theme="simple"  name="%{#attr.subForm+'.reasonId'}" id="%{#attr.subForm+'.reasonId'}"/>
                </s:if>


            </td>
            <td>
                <tags:label key="MSG.recoverDate" required="true" />
                <!--                Ngày thu hồi (<font color="red">*</font>)-->
            </td>
            <td>

                <tags:dateChooser readOnly="true"  property="${property}dateExported" id="${property}dateExported"  styleClass="txtInput" />

            </td>
        </tr>
        <tr>
            <td>
                <tags:label key="MSG.SAE.020" required="false" />
                <!--                Ghi chú-->
            </td>
            <td colspan="3">
                <s:textfield theme="simple"  name="%{#attr.subForm+'.note'}" maxlength="500" id="%{#attr.subForm+'.note'}" cssClass="txtInputFull" readonly="%{#attr.readOnly}"/>
            </td>
        </tr>
    </table>
</tags:imPanel>
<script>
    validateForm =function(target){
        var formName='<s:property value="#attr.subForm"/>';
        var cmdExportCode= document.getElementById(formName+".cmdExportCode");
        //var shopImportedId= document.getElementById(formName+".shopImportedId");
        var shopImportedCode= document.getElementById(formName+".shopCode");
        var reasonId= document.getElementById(formName+".reasonId");
        //var dateExported= document.getElementById(formName+".dateExported");
        var dateExported= dojo.widget.byId(formName+".dateExported");
        //var dateValue =dateExported.domNode.childNodes[1].value;
        //alert(dateExported.domNode.childNodes[1].value);
        
        if(cmdExportCode.value ==null || trim(cmdExportCode.value)==''){
            $(target).innerHTML='<%=errorAction%>';
            cmdExportCode.focus();
            return false;
        }
        cmdExportCode.value=trim(cmdExportCode.value);
        if(!isFormalCharacter(cmdExportCode.value)){
            $(target).innerHTML='<%=errorAction2%>';
            cmdExportCode.focus();
            return false;
        }
        if(shopImportedCode.value ==null || trim(shopImportedCode.value)==''){
            //            $(target).innerHTML='Chưa chọn kho nhận hàng';
            $(target).innerHTML='<s:text name="ERR.STK.005"/>';
            shopImportedCode.focus();
            return false;
        }
        if(reasonId.value ==null || trim(reasonId.value)==''){
            //            $(target).innerHTML='Chưa chọn lý do xuất';
            $(target).innerHTML='<s:text name="ERR.STK.015"/>';
            reasonId.focus();
            return false;
        }
        
        if(dateExported.domNode.childNodes[1].value ==null || dateExported.domNode.childNodes[1].value==''){
            //            $(target).innerHTML='Chưa nhập ngày xuất kho';
            $(target).innerHTML='<s:text name="ERR.STK.097"/>';
            dateExported.domNode.childNodes[1].focus();
            return false;
        }
        if(!isDateFormat(dateExported.domNode.childNodes[1].value)){
            //            $(target).innerHTML='Ngày xuất không đúng định dạng.';
            $(target).innerHTML='<s:text name="ERR.STK.098"/>';
            dateExported.domNode.childNodes[1].focus();
            return false;
        }
        if(compareDates(GetDateSys(),dateExported.domNode.childNodes[1].value)){
            //            $(target).innerHTML='Ngày xuất không được lớn hơn ngày hiện tại';
            $(target).innerHTML='<s:text name="ERR.SAE.004"/>';
            dateExported.domNode.childNodes[1].focus();
            return false;
        }
        var note= document.getElementById(formName+".note");
        note.value=note.value.trim();
        return true;
    }
    
    resetStockTypeCombobox = function(){        
        var formName='<s:property value="#attr.subForm"/>';
        var shopImportedId= document.getElementById(formName+".shopImportedId");
        //var shopImportedCode= document.getElementById(formName+".shopCode");
        var shopSelect = document.getElementById("shopSelect");        
        if (shopSelect.value != ''){
            //            if (confirm(getUnicodeMsg("Nếu chọn lại đại lý thì sẽ reset lại những mặt hàng đã chọn"))) {
            if (confirm(getUnicodeMsg("ERR.STK.099"))) {
                $('stockTypeId').value ='';
                $('stateId').value ='';
                $('stockModelId').value ='';
                $('quantity').value ='';
                $('note').value ='';
                gotoAction("listGoods", "CommonStockAction!removeListGoods.do");
            }
            else{
                shopImportedId.value = shopSelect.value;
                $('stockTypeId').value ='';
            }
        }
        else{            
            shopSelect.value = shopImportedId.value;
            $('stockTypeId').value ='';
        }
    }
    
    
    function GetDateSys() {
        var time = new Date();
        var dd=time.getDate();
        var mm=time.getMonth()+1;
        var yy=time.getFullYear() ;
        var temp = '';
        if (dd < 10) dd = '0' + dd;
        if (mm < 10) mm = '0' + mm;
        temp = dd + "/" + mm + "/" + yy ;
        return(temp);
    }
</script>