<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%-- 
    Document   : GetBookType
    Created on : Aug 8, 2009, 2:05:16 PM
    Author     : Vunt
    Desc       : khai bao loai hoa don
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="sx" uri="/struts-dojo-tags" %>
<%@taglib uri="VTdisplaytaglib" prefix="display" %>
<%@taglib tagdir="/WEB-INF/tags/" prefix="tags" %>
<%@taglib prefix="imDef" uri="imDef" %>


<%
            request.setAttribute("contextPath", request.getContextPath());
%>

<s:if test="#request.bookTypeMode == 'prepareAddOrEdit'">
    <s:set var="readonly" value="0" scope="request"/>
</s:if>

<s:else>
    <s:if test="#request.bookTypeMode == 'copy'">
        <s:set var="readonly" value="1" scope="request"/>
    </s:if>
    <s:else>
        <s:set var="readonly" value="2" scope="request"/>
    </s:else>
</s:else>

<tags:imPanel title="title.GetBookTypePre.page">
    <!-- hien thi message -->
    <div>
        <tags:displayResult id="displayResultMsgClient" property="message" propertyValue="paramMsg" type="key" />
    </div>

    <!-- phan hien thi thong tin bookType -->
    <div class="divHasBorder">
        <s:form action="GetBookTypeAction" theme="simple" enctype="multipart/form-data"  method="post" id="BookTypeForm">
            <s:token/>

            <s:hidden name="BookTypeForm.booktypeid" id="BookTypeForm.booktypeid"/>
            <tags:BookType toInputData="true" property="BookTypeForm"/>
        </s:form>


        <!-- phan nut tac vu -->
        <div align="center" style="padding-bottom:5px;">
            <s:if test="#request.readonly == 0">
                <tags:submit targets="bodyContent" formId="BookTypeForm"
                             cssStyle="width:80px;" value="MSG.accept"
                             showLoadingText="true"
                             validateFunction="validate();"
                             preAction="GetBookTypeAction!editBookType.do"/>
                <tags:submit targets="bodyContent" formId="BookTypeForm"
                             cssStyle="width:80px;" value="MSG.cancel2"
                             showLoadingText="true"
                             preAction="GetBookTypeAction!preparePage.do"/>
            </s:if>
            <s:elseif test="#request.readonly == 2">
                <tags:submit targets="bodyContent" formId="BookTypeForm"
                             cssStyle="width:80px;" value="MSG.generates.addNew"
                             showLoadingText="true"
                             validateFunction="validate();"
                             preAction="GetBookTypeAction!addBookType.do"/>
                <tags:submit targets="bodyContent" formId="BookTypeForm"
                             cssStyle="width:80px;" value="MSG.find"
                             validateFunction="validatesearch();"
                             showLoadingText="true"
                             preAction="GetBookTypeAction!searchBookType.do"/>
            </s:elseif>
            <s:elseif test="#request.readonly == 1">
                <tags:submit targets="bodyContent" formId="BookTypeForm"
                             cssStyle="width:80px;" value="MSG.copy"
                             showLoadingText="true"
                             validateFunction="validate();"
                             preAction="GetBookTypeAction!copyBookType.do"/>
                <tags:submit targets="bodyContent" formId="BookTypeForm"
                             cssStyle="width:80px;" value="MSG.cancel2"
                             showLoadingText="true"
                             preAction="GetBookTypeAction!preparePage.do"/>
            </s:elseif>
        </div>
    </div>

    <!-- danh sach cac bookType -->
    <div>
        <fieldset class="imFieldset">
            <legend>${fn:escapeXml(imDef:imGetText('MSG.invoice.list'))}</legend>
            <jsp:include page="ListBookType.jsp"/>
        </fieldset>
    </div>

</tags:imPanel>

<script type="text/javascript" language="javascript">

    var booktypeid=document.getElementById('BookTypeForm.booktypeid');
    var blockname=document.getElementById('BookTypeForm.blockname');
    var lengthname=document.getElementById('BookTypeForm.lengthname');
    var lengthinvoice=document.getElementById('BookTypeForm.lengthinvoice');
    var type=document.getElementById('BookTypeForm.type');
    var book=document.getElementById('BookTypeForm.book');
    var serialreal=document.getElementById('BookTypeForm.serialreal');
    var serialcode=document.getElementById('BookTypeForm.serialcode');
    var decription=document.getElementById('BookTypeForm.decription');
    var pagewith=document.getElementById('BookTypeForm.pagewith');
    var pageheight=document.getElementById('BookTypeForm.pageheight');
    var rowspacing=document.getElementById('BookTypeForm.rowspacing');
    var maxrow=document.getElementById('BookTypeForm.maxrow');
    var numinvoice=document.getElementById('BookTypeForm.numinvoice');
    serialcode.focus();
    validate = function(){
        $('displayResultMsgClient' ).innerHTML = "";
        if(trim(serialcode.value) == "" ||trim(serialcode.value) ==null ){
            //$('displayResultMsgClient').innerHTML="Số serial hóa đơn không được để rỗng"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.066"/>';
            serialcode.focus();
            return false;
        }
        if(trim(serialcode.value).length >20 ){
            //$('displayResultMsgClient').innerHTML="Số serial hóa đơn nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.067"/>';
            serialcode.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(serialcode.value))){
            //$('displayResultMsgClient').innerHTML="Số serial hóa đơn không được nhập định dạng thẻ HTML"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.068"/>';
            serialcode.focus();
            return false;
        }
        if(trim(serialreal.value).length >20 ){
            //$('displayResultMsgClient').innerHTML="Số serial thực tế nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.069"/>';
            serialreal.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(serialreal.value))){
            //$('displayResultMsgClient').innerHTML="Số serial thực tế không được nhập định dạng thẻ HTML"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.070"/>';
            serialreal.focus();
            return false;
        }
        if(trim(blockname.value) == "" ||trim(blockname.value) ==null ){
            //$('displayResultMsgClient').innerHTML="Tên loại hóa đơn không được để rỗng"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.071"/>';
            blockname.focus();
            return false;
        }

        if(trim(blockname.value) != null &&  trim(blockname.value).length >50 ){
            //$('displayResultMsgClient').innerHTML="Tên loại hóa đơn nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.072"/>';
            blockname.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(blockname.value))){
            //$('displayResultMsgClient').innerHTML="Tên loại hóa đơn không được nhập định dạng thẻ HTML"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.073"/>';
            blockname.focus();
            return false;
        }
        if(type.value ==""){
            //$('displayResultMsgClient').innerHTML="Chưa ch�?n loại"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.074"/>';
            type.focus();
            return false;
        }
        if(trim(book.value) == "" ||trim(book.value) ==null ){
            //$('displayResultMsgClient').innerHTML="Bạn chưa nhập mẫu hóa đơn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.075"/>';
            book.focus();
            return false;
        }
        if(trim(book.value).length <= 0 ){
            //$('displayResultMsgClient').innerHTML="Bạn chưa nhập mẫu hóa đơn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.075"/>';
            book.focus();
            return false;
        }
        if(trim(book.value) == "0" ){
            //$('displayResultMsgClient').innerHTML="Mẫu hóa đơn phải > 0"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.076"/>';
            book.focus();
            return false;
        }
        if(!isInteger( trim(book.value) ) ){
            //$('displayResultMsgClient').innerHTML="Mẫu hóa đơn phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.077"/>';
            book.focus();
            return false;
        }
        if(trim(book.value).length >2 ){
            //$('displayResultMsgClient').innerHTML="�?ộ dài mẫu hóa đơn nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.078"/>';
            book.focus();
            return false;
        }
        if(trim(numinvoice.value) == "" ||trim(numinvoice.value) ==null ){
            //$('displayResultMsgClient').innerHTML="Số t�? trong quyển không được để rỗng"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.079"/>';
            numinvoice.focus();
            return false;
        }
        if(trim(numinvoice.value) == "0" ){
            //$('displayResultMsgClient').innerHTML="Số tờ trong quyển phải >0"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.080"/>';
            numinvoice.focus();
            return false;
        }

        if(!isInteger( trim(numinvoice.value) ) ){
            //$('displayResultMsgClient').innerHTML="Số tờ phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.081"/>';
            numinvoice.focus();
            return false;
        }
        if(trim(numinvoice.value).length >10 ){
            //$('displayResultMsgClient').innerHTML="Số tờ trong một quyển nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.082"/>';
            numinvoice.focus();
            return false;
        }
        if(trim(lengthname.value).length <= 0 ){
            //$('displayResultMsgClient').innerHTML="Bạn chưa nhập độ dài số quyển/thùng"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.083"/>';
            lengthname.focus();
            return false;
        }
        if(trim(lengthname.value) == "0" ){
            //$('displayResultMsgClient').innerHTML="Độ dài số quyển/thùng phải > 0"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.084"/>';
            lengthname.focus();
            return false;
        }
        if(!isInteger( trim(lengthname.value) ) ){

            //$('displayResultMsgClient').innerHTML="Độ dài số quyển/thùng phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.085"/>';
            lengthname.focus();
            return false;
        }
        if(trim(lengthname.value).length >2 ){
            //$('displayResultMsgClient').innerHTML="Độ dài số quyển/thùng nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.086"/>';
            lengthname.focus();
            return false;
        }
        if(trim(lengthinvoice.value).length <= 0 ){
            //$('displayResultMsgClient').innerHTML="Bạn chưa nhập độ dài số hóa đơn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.087"/>';
            lengthinvoice.focus();
            return false;
        }
        if(trim(lengthinvoice.value) == "0" ){
            //$('displayResultMsgClient').innerHTML="Độ dài số hóa đơn phải > 0"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.088"/>';
            lengthinvoice.focus();
            return false;
        }
        if(!isInteger( trim(lengthinvoice.value) ) ){

            //$('displayResultMsgClient').innerHTML="Độ dài số hóa đơn phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.089"/>';
            lengthinvoice.focus();
            return false;
        }
        if(trim(lengthinvoice.value).length >2 ){
            //$('displayResultMsgClient').innerHTML="Độ dài số hóa đơn nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.090"/>';
            lengthinvoice.focus();
            return false;
        }
        if(trim(pagewith.value) == "0" ){
            //$('displayResultMsgClient').innerHTML="Chiều rộng hóa phải > 0"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.091"/>';
            pagewith.focus();
            return false;
        }
        if(pagewith.value != "" && !isInteger( trim(pagewith.value) ) ){

            //$('displayResultMsgClient').innerHTML="Chiều rộng hóa phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.092"/>';
            pagewith.focus();
            return false;
        }
        if(trim(pageheight.value) == "0" ){
            //$('displayResultMsgClient').innerHTML="Chiều cao hóa phải > 0"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.093"/>';
            pageheight.focus();
            return false;
        }
        if((trim(pageheight.value) != "") && !isInteger( trim(pageheight.value))){
            //$('displayResultMsgClient').innerHTML="Chiều cao hóa phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.094"/>';
            pageheight.focus();
            return false;
        }
        if(trim(rowspacing.value) == "0" ){
            //$('displayResultMsgClient').innerHTML="Khoảng cách dòng phải > 0"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.095"/>';
            rowspacing.focus();
            return false;
        }
        if(trim(rowspacing.value) != "" && !isInteger( trim(rowspacing.value) ) ){

            //$('displayResultMsgClient').innerHTML="Khoảng cách dòng phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.096"/>';
            rowspacing.focus();
            return false;
        }
        if(rowspacing.value != null &&  trim(rowspacing.value).length >10 ){
            //$('displayResultMsgClient').innerHTML="Khoảng cách dòng nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.097"/>';
            rowspacing.focus();
            return false;
        }
        if(trim(maxrow.value) == "0" ){
            //$('displayResultMsgClient').innerHTML="Số dòng tối đã trong mẫu hóa đơn phải > 0"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.098"/>';
            maxrow.focus();
            return false;
        }
        if(trim(maxrow.value) != "" && !isInteger( trim(maxrow.value) ) ){

            //$('displayResultMsgClient').innerHTML="Số dòng tối đã trong mẫu hóa đơn phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.099"/>';
            maxrow.focus();
            return false;
        }
        if(trim(maxrow.value).length >10 ){
            //$('displayResultMsgClient').innerHTML="Số dòng tối đã trong mẫu hóa đơn nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.100"/>';
            maxrow.focus();
            return false;
        }
        if(trim(decription.value).length >500 ){
            //$('displayResultMsgClient').innerHTML="Ghi chú nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.101"/>';
            decription.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(decription.value))){
            //$('displayResultMsgClient').innerHTML="Ghi chú không được nhập định dạng thẻ HTML"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.102"/>';
            decription.focus();
            return false;
        }


        return true;
    }

    validatesearch = function(){
        $('displayResultMsgClient' ).innerHTML = "";
        if(trim(serialcode.value).length >20 ){
            //$('displayResultMsgClient').innerHTML="Số serial hóa đơn nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.067"/>';
            serialcode.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(serialcode.value))){
            //$('displayResultMsgClient').innerHTML="Số serial hóa đơn không được nhập định dạng thẻ HTML"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.068"/>';
            serialcode.focus();
            return false;
        }
        if(trim(serialreal.value).length >20 ){
            //$('displayResultMsgClient').innerHTML="Số serial thực tế nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.069"/>';
            serialreal.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(serialreal.value))){
            //$('displayResultMsgClient').innerHTML="Số serial thực tế không được nhập định dạng thẻ HTML"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.070"/>';
            serialreal.focus();
            return false;
        }
        if(trim(blockname.value) != "" &&  trim(blockname.value).length >50 ){
            //$('displayResultMsgClient').innerHTML="Tên loại hóa đơn nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.071"/>';
            blockname.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(blockname.value))){
            //$('displayResultMsgClient').innerHTML="Tên loại hóa đơn không được nhập định dạng thẻ HTML"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.073"/>';
            blockname.focus();
            return false;
        }
        if(trim(book.value).length >2 ){
            //$('displayResultMsgClient').innerHTML="�?ộ dài mẫu hóa đơn nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.078"/>';
            book.focus();
            return false;
        }
        if(trim(book.value) != "" && !isInteger( trim(book.value) ) ){
            //$('displayResultMsgClient').innerHTML="Mẫu hóa đơn phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.077"/>';
            book.focus();
            return false;
        }
        if(trim(numinvoice.value) != "" && !isInteger( trim(numinvoice.value) ) ){
            //$('displayResultMsgClient').innerHTML="Số t�? phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.081"/>';
            numinvoice.focus();
            return false;
        }
        if(trim(numinvoice.value).length >10 ){
            //$('displayResultMsgClient').innerHTML="Số t�? trong một quyển nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.082"/>';
            numinvoice.focus();
            return false;
        }
        if(trim(lengthname.value) == "0" ){
            //$('displayResultMsgClient').innerHTML="�?ộ dài số quyển/thùng phải > 0"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.084"/>';
            lengthname.focus();
            return false;
        }
        if( trim(lengthname.value) != "" && !isInteger( trim(lengthname.value) ) ){

            //$('displayResultMsgClient').innerHTML="�?ộ dài số quyển/thùng phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.085"/>';
            lengthname.focus();
            return false;
        }
        if(trim(lengthname.value).length >2 ){
            //$('displayResultMsgClient').innerHTML="�?ộ dài số quy�?n/thùng nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.086"/>';
            lengthname.focus();
            return false;
        }
        if(trim(lengthinvoice.value) != "" &&!isInteger( trim(lengthinvoice.value) ) ){
            //$('displayResultMsgClient').innerHTML="�?ộ dài số hóa đơn phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.089"/>';
            lengthinvoice.focus();
            return false;
        }
        if(trim(lengthinvoice.value).length >2 ){
            //$('displayResultMsgClient').innerHTML="�?ộ dài số hóa đơn nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.090"/>';
            lengthinvoice.focus();
            return false;
        }
        if(pagewith.value != "" && !isInteger( trim(pagewith.value) ) ){
            //$('displayResultMsgClient').innerHTML="Chi�?u rộng hóa phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.092"/>';
            pagewith.focus();
            return false;
        }
        if((trim(pageheight.value) != "") && !isInteger( trim(pageheight.value))){
            //$('displayResultMsgClient').innerHTML="Chi�?u cao hóa phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.094"/>';
            pageheight.focus();
            return false;
        }
        if(trim(rowspacing.value) != "" && !isInteger( trim(rowspacing.value) ) ){
            //$('displayResultMsgClient').innerHTML="Khoảng cách dòng phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.096"/>';
            rowspacing.focus();
            return false;
        }
        if(rowspacing.value != null &&  trim(rowspacing.value).length >10 ){
            //$('displayResultMsgClient').innerHTML="Khoảng cách dòng nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.097"/>';
            rowspacing.focus();
            return false;
        }
        if(trim(maxrow.value) != "" && !isInteger( trim(maxrow.value) ) ){
            //$('displayResultMsgClient').innerHTML="Số dòng tối đã trong mẫu hóa đơn phải là số nguyên dương"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.099"/>';
            maxrow.focus();
            return false;
        }
        if(trim(maxrow.value).length >10 ){
            //$('displayResultMsgClient').innerHTML="Số dòng tối đã trong mẫu hóa đơn nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.100"/>';
            maxrow.focus();
            return false;
        }
        if(trim(decription.value).length >500 ){
            //$('displayResultMsgClient').innerHTML="Ghi chú nhập vượt quá giới hạn"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.101"/>';
            decription.focus();
            return false;
        }
        if (isHtmlTagFormat(trim(decription.value))){
            //$('displayResultMsgClient').innerHTML="Ghi chú không được nhập định dạng thẻ HTML"
            $( 'displayResultMsgClient' ).innerHTML = '<s:text name="ERR.LST.102"/>';
            decription.focus();
            return false;
        }
       
        return true;
    }





    delBookType = function(bookTypeId) {
        var strConfirm = getUnicodeMsg('<s:text name="ERR.LST.021"/>');
        if (confirm(strConfirm)) {
        <%--if(confirm("Bạn có thực sự muốn xóa hóa đơn này?")){--%>
            gotoAction('bodyContent','GetBookTypeAction!deleteBookType.do?bookTypeId=' + bookTypeId+'&'+token.getTokenParamString());
        }
    }

    preparePagePrinterUser = function() {

        openDialog("${contextPath}/GetBookTypeAction!preparePagePrinterUser.do?"+ token.getTokenParamString(), 750, 700, true);
    }

</script>


