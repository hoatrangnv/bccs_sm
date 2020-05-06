<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<% request.setAttribute("contextPath", request.getContextPath());%>

<%
    response.sendRedirect(request.getContextPath() + "/Authentication.do");
%>
<%--
<SCRIPT LANGUAGE="JavaScript">

    function closeWindow()
    {
        //Firefox
        if (navigator.appName == 'Netscape')
        {            
            window.open('', '_self', '');
            
            window.open("${contextPath}/Authentication.do", "screen.width", "toolbar=no");
            window.close();
        }
        else //Internet Explorer
            setTimeout("WB.ExecWB(45,2)",0);
    }

</SCRIPT>

<OBJECT ID="WB" WIDTH="0" HEIGHT="0" CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2">
</OBJECT>
<html>
    <title>Trang chu</title>
    <head></head>
    <body onload="self.focus(); closeWindow()"></body> 
</html>
--%>
