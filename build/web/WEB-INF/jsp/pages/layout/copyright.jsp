<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="imDef" uri="imDef" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<!--<p class="copyright">-->
<div align="center" class="copyright">
    <!--        Phát triển bởi Trung tâm IT - Công ty Viễn thông Viettel-->
    <%--s:property value="getText('MSG.HEADER.COPYRIGHT')"></s:property--%>
    <div>
<!--        <s_:property value="getText('MSG.HEADER.COPYRIGHT')"/>-->
        <s:text name ="MSG.HEADER.COPYRIGHT"/>
    </div>
    <div>
<!--        $_{imDef:imGetText('MSG.HEADER.VERSION')}-->
<s:text name ="MSG.HEADER.VERSION"/>
    </div>
</div>
<!--</p>-->