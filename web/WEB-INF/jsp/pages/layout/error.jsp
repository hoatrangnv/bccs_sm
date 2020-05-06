<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<script type="javascript">

 var win = window.open('', 100, config='height=800,width=900, toolbar=no, menubar=no, scrollbars=yes, resizable=yes,location=no, directories=no, status=no');
 win.document.write(<s:actionerror/>);
</script>
