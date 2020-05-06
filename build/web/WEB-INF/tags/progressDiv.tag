<%-- 
    Document   : progressDiv
    Created on : Nov 14, 2009, 8:49:59 AM
    Author     : Doan Thanh 8
    Purpose    : update lai noi dung 1 the div sau 1 khoang thoi gian nhat dinh
--%>

<%@tag description="hien thi tien trinh thuc hien" pageEncoding="UTF-8"%>

<%@taglib prefix="s" uri="/struts-tags"%>
<%@taglib prefix="sx" uri="/struts-dojo-tags"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="updateUrl" rtexprvalue="true" required="true"%>
<%@attribute name="intervalTime"%>

<%-- any content can be specified here e.g.: --%>
<%
    request.setAttribute("contextPath", request.getContextPath());
%>

<div id="imProgressDiv" style="display:none; height:360px; width:540px; overflow:auto; position:absolute; left:0px; top:0px; background:#DFE8F6; text-align:left; font-size:13px; padding:5px; border: 2px #000000 solid;">
    Đang thực hiện ...
</div>

<script type="text/javascript">

    var intervalTime = '${pageScope.intervalTime}';
    if(intervalTime == null || intervalTime == '' || intervalTime * 1 <= 0) {
        intervalTime = 10000; //mac dinh delay 10s
    }

    //bat dau thuc hien lap thanh progress bar
    startProgressDiv = function() {
        if(g_progressDivInterval != null) {
            clearInterval(g_progressDivInterval);
        }
        $('imProgressDiv').innerHTML = "Đang thực hiện ...";
        $('imProgressDiv').style.display = "block";
        
        //
        g_progressDivInterval = window.setInterval('updateDataForProgressDiv()', intervalTime * 1);
    }

    //dung thanh progress bar
    stopProgressDiv = function() {
        if(g_progressDivInterval != undefined) {
            $('imProgressDiv').innerHTML = "Đang thực hiện ...";
            $('imProgressDiv').style.display = "none";
            clearInterval(g_progressDivInterval);
        }
    }

    updateDataForProgressDiv = function() {
        //
        updateProgressDiv('${pageScope.updateUrl}'); //
    }

    updateProgressDiv = function(url){
        dojo.io.bind({
            url:url ,
            error: function(type, data, evt){
                alert("tamdt1 - error");
            },
            handler: function(type, data, e) {
                if(dojo.lang.isObject(data)) {
                    //update data vao the div
                    var divInnerHTML = $('imProgressDiv').innerHTML;
                    var progressDivData = data['progressDivData'];
                    if(progressDivData != undefined && progressDivData != "") {
                        $('imProgressDiv').innerHTML = progressDivData + "<br />" + divInnerHTML;
                    }
                }
            },
            mimetype: "text/json"
        });
    }

    getHighestZIndex = function() {
        var allElems = document.getElementsByTagName ? document.getElementsByTagName("*") : document.all;
        var maxZIndex = 0;

        for(var i=0;i<allElems.length;i++) {
            var elem = allElems[i];
            var cStyle = null;

            if (elem.currentStyle) {
                cStyle = elem.currentStyle;
            }
            else if (document.defaultView && document.defaultView.getComputedStyle) {
                cStyle = document.defaultView.getComputedStyle(elem,"");
            }

            var sNum;
            if (cStyle) {
                sNum = Number(cStyle.zIndex);
            } else {
                sNum = Number(elem.style.zIndex);
            }
            if (!isNaN(sNum)) {
                maxZIndex = Math.max(maxZIndex,sNum);
            }
        }

        return maxZIndex;
    }


    showProgressDivOnTop = function() {
        //hien thi the div process noi len tren tat ca cac the khac
        
        var scrOfX = 0, scrOfY = 0;
        if(typeof(window.pageYOffset) == 'number') {
            //Netscape compliant
            scrOfY = window.pageYOffset;
            scrOfX = window.pageXOffset;
        } else if(document.body && (document.body.scrollLeft || document.body.scrollTop)) {
            //DOM compliant
            scrOfY = document.body.scrollTop;
            scrOfX = document.body.scrollLeft;
        } else if(document.documentElement && (document.documentElement.scrollLeft || document.documentElement.scrollTop)) {
            //IE6 standards compliant mode
            scrOfY = document.documentElement.scrollTop;
            scrOfX = document.documentElement.scrollLeft;
        }

        var pageW = (window.innerWidth) ? window.innerWidth : (document.documentElement.offsetWidth - 5);
        var pageH = (window.innerHeight) ? window.innerHeight : (document.documentElement.offsetHeight - 5);

        var left = parseInt(scrOfX + (pageW - $('imProgressDiv').offsetWidth ) / 2 );
        var top = parseInt(scrOfY + (pageH - $('imProgressDiv').offsetHeight ) / 2 );
        
        var imProgressDivZIndex = $('imProgressDiv').style.zIndex;
        var highestZIndex = getHighestZIndex();
        if(imProgressDivZIndex < highestZIndex) {
            imProgressDivZIndex = highestZIndex + 1;
            $('imProgressDiv').style.zIndex = imProgressDivZIndex;
        }

        $('imProgressDiv').style.left = left + 'px';
        $('imProgressDiv').style.top = top + 'px';
    }

</script>