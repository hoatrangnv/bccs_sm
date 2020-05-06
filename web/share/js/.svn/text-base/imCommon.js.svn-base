function setAction(form, action){
    var newAction = action + (action.indexOf("?") > -1 ? "&" : "?") + "pageId="+pageId;
    document.getElementById(form).action=newAction;
    document.getElementById(form).submit();
}
/*
 * add by tamdt1, 11/06/2009
 * source: http://www.mredkj.com/javascript/nfbasic.html
 * To use addSeparatorsNF, you need to pass it the following arguments:
 *      - nStr: The number to be formatted, as a string or number. No validation is done, so don't input a formatted number. If inD is something other than a period, then nStr must be passed in as a string.
 *      - inD: The decimal character for the input, such as '.' for the number 100.2
 *      - outD: The decimal character for the output, such as ',' for the number 100,2
 *      - sep: The separator character for the output, such as ',' for the number 1,000.2
 *
 */

function addSeparatorsNF(nStr, inD, outD, sep) {
    nStr += '';
    var dpos = nStr.indexOf(inD);
    var nStrEnd = '';
    if (dpos != -1) {
        nStrEnd = outD + nStr.substring(dpos + 1, nStr.length);
        nStr = nStr.substring(0, dpos);
    }
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(nStr)) {
        nStr = nStr.replace(rgx, '$1' + sep + '$2');
    }
    return nStr + nStrEnd;
}

/*
 * tamdt1, 11/06/2009
 * format chuoi nhap vao trong textfield, phan cach hang nghin = dau ','
 * dau vao: id cua doi tuong text can format
 * vd: 5000000 -> 5,000,000
 */
function textFieldNF(textFieldObject) {
    if(textFieldObject != null) {
        var tmp = textFieldObject.value;
        tmp = tmp.replace(/\,/g,""); //loai bo dau , trong chuoi
        var tmp1 = addSeparatorsNF(tmp, '.', '.', ',');
        textFieldObject.value = tmp1;
    }
}

/*
 * tamdt1, 13/06/2009
 * format chuoi nhap vao trong select combobox, phan cach hang nghin = dau ','
 * dau vao: id cua doi tuong selectbox can format
 * vd: 5000000 -> 5,000,000
 */
function selectNF(selectObject) {
    var ops = selectObject.options;
    var i;
    for(i = 0; i < ops.length; i++) {
        //not IE
        var tmp1 = ops[i].text;
        ops[i].text = addSeparatorsNF(tmp1, '.', '.', ',');
        //IE
        var tmp2 = ops[i].innerHTML;
        ops[i].innerHTML = addSeparatorsNF(tmp2, '.', '.', ',');

    }
}

/*
 * thanhnc, 11/09/2009
 * bat popup
 *
 */
/*
function openPopup(path, width, height) {
    my_window = window.open(path,'',"location=" + (screen.width) + ",status=1, scrollbars=1, titlebar=0, width=" +width + ",height=" + height);
    my_window.focus();
    return false;
}

function openDialog( url, _w, _h, _modal ) {
    var brwVer  = navigator.appVersion;
    var brwName = navigator.appName;
    var brwAgent = navigator.userAgent;

    var _t = 0, _l = 0; // top / left
    var modal;

    switch( arguments.length ) {
        case 6:
            _t = arguments[3];
            _l = arguments[4];
            modal = arguments[5];
            break;
        default:
            _t = 100;
            _l = ( screen.width - _w ) / 2;
            modal = arguments[3];
            break;
    }

    var t = _t;
    var l = _l;

    var w = _w;
    var h = _h;

    var sep = (brwVer.indexOf('MSIE') == -1 ) ? ";" : ",";
    if(modal) {
        if(brwVer.indexOf('MSIE') == -1 && brwAgent.indexOf('Firefox/3') == -1) {
            window.open(url, '_blank',
                "modal=yes" + sep + "location=no" + sep + "status=no" + sep + "scrollbars=yes" + sep + "width=" + w + "" + sep + "height=" + h + sep + "top=" + t + sep + "left=" + l );
        }
        else {
            window.showModalDialog(url, "",
                "dialogwidth:" + w + "px" + sep + "dialogheight:" + h + "px" + sep + "center:yes" + sep + "status:no" + sep + "resizable:no" + sep + "location:no" + sep + "toolbar:no" + sep + "menubar:no" + sep + "dialogleft:" + l + "px" + sep + "dialogtop:" + t );
        }
    }
    else {
        sep = ",";
        window.open(url, '_blank',
            "modal=no" + sep + "location=no" + sep + "status=no" + sep + "scrollbars=yes" + sep + "width=" + w + "" + sep + "height=" + h + "" + sep + "top=" + t + sep + "left=" + l );
    }
}
*/
/*
 * tamdt1, 11/09/2009
 * copy from dinhvv, xu ly table tree
 *
 */
function topUpNode(id, contextPath) {

    var treeTable = $('OfferSubTree');

    var trs = treeTable.getElementsByTagName("tr");

    var check = null;

    for( var i = 0; i < trs.length; i++ ) {

        if( trs[i].id  == id ) {

            check = ( trs[i].style.display == 'none' ) ? '' : 'none';

            trs[i].style.display = check;

            if(check == 'none') {

                $('expan_'+id).src = contextPath+ '/share/img/treeview/plusbottom.gif';

                $('folder_'+id).src = contextPath+ '/share/img/treeview/folder.gif';

            } else {

                $('expan_'+id).src = contextPath+ '/share/img/treeview/minusbottom.gif';

                $('folder_'+id).src = contextPath+ '/share/img/treeview/folderopen.gif';

            }

        }

    }
}

/*
 * DucDD
 * Check box select all
 * checkAllId = id cua check box trên title
 * cbName = name của check box
 * chbId = id phía trước của check box
 *
 */
selectAll = function(checkAllID, cbName, cbId) {    
    var checkAll = document.getElementById(checkAllID);    
    var rowId = document.getElementsByName(cbName);    
    var checkBoxID = cbId;
    var i = 0;
    if(checkAll.checked){
        for(i = 0; i < rowId.length; i++){
            document.getElementById(checkBoxID+rowId[i].value).checked=true;
        }
    }else{
        for(i = 0; i < rowId.length; i++){
            document.getElementById(checkBoxID+rowId[i].value).checked=false;
        }
    }
}

checkSelectAll = function(checkAllID, cbName, cbId) {
    var checkAll = document.getElementById(checkAllID);
    var rowId = document.getElementsByName(cbName);
    var checkBoxID = cbId;
    var checkedAll = true;
    for(var i = 0; i < rowId.length; i++){
        if(document.getElementById(checkBoxID+rowId[i].value).checked != true){
            checkedAll = false;
        }
    }
    if(checkedAll == true){
        checkAll.checked = true;
    }else{
        checkAll.checked = false;
    }
}


function getFormAsString(formName){
    var radioHash = new Hash();
    //var checkboxHash = new Hash();

    //Setup the return String
    returnString ="";

    //Get the form values
    formElements=document.forms[formName].elements;

    //loop through the array , building up the url
    //in the form /strutsaction.do&name=value
    if(navigator.userAgent.indexOf("Safari")!=-1){
        for ( var i=formElements.length-1; i>=0; --i ){
            //we escape (encode) each value
            if (formElements[i].type == "radio") {
                if (formElements[i].checked == true) {
                    radioHash.set(formElements[i].name,formElements[i].value);
                }
            }
            else {
                if (formElements[i].type == "checkbox") {
                    if (formElements[i].checked == true) {
                        //checkboxHash.set(formElements[i].name,formElements[i].value);
                        returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);
                    }
                }
                //else returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);
                else if(formElements[i] != undefined && formElements[i].type != "file" && formElements[i].value != null && formElements[i].name != null &&formElements[i].name != ""
                    && formElements[i].value != "" && formElements[i].name != undefined && formElements[i].style.display != "none")
                    returnString=returnString+"&"+formElements[i].name+"=" + formElements[i].value;
            }
        }

        radioHash.each(function(pair) {
            returnString=returnString+"&"+escape(pair.key)+"="+escape(pair.value);
        });

    /*checkboxHash.each(function(pair) {
                returnString=returnString+"&"+escape(pair.key)+"="+escape(pair.value);
            });*/
    /*
        for ( var i=formElements.length-1; i>=0; --i ){
            returnString=returnString+"&"+formElements[i].name+"=" + formElements[i].value;
        }*/
    } else if(navigator.userAgent.indexOf("Gecko")!=-1) {
        for ( var i=formElements.length-1; i>=0; --i ){
            //we escape (encode) each value
            if (formElements[i].type == "radio") {
                if (formElements[i].checked == true) {
                    radioHash.set(formElements[i].name,formElements[i].value);
                }
            }
            else {
                if (formElements[i].type == "checkbox") {
                    if (formElements[i].checked == true) {
                        //checkboxHash.set(formElements[i].name,formElements[i].value);
                        returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);
                    }
                }
                //else returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);
                else if(formElements[i] != undefined && formElements[i].type != "file" && formElements[i].value != null && formElements[i].name != null &&formElements[i].name != ""
                    && formElements[i].value != "" && formElements[i].name != undefined && formElements[i].style.display != "none")
                    returnString=returnString+"&"+formElements[i].name+"=" + formElements[i].value;
            }
        }

        radioHash.each(function(pair) {
            returnString=returnString+"&"+escape(pair.key)+"="+escape(pair.value);
        });

    /*checkboxHash.each(function(pair) {
                returnString=returnString+"&"+escape(pair.key)+"="+escape(pair.value);
            });*/

    /*
        for ( var i=formElements.length-1; i>=0; --i ){
            returnString=returnString+"&"+formElements[i].name+"=" + formElements[i].value;
        }*/
    }
    else
    {
        if(navigator.userAgent.indexOf("MSIE 7.0")!=-1) {
            for ( var i=formElements.length-1; i>=0; --i ){
                //we escape (encode) each value
                if (formElements[i].type == "radio") {
                    if (formElements[i].checked == true) {
                        radioHash.set(formElements[i].name,formElements[i].value);
                    }
                }
                else {
                    if (formElements[i].type == "checkbox") {
                        if (formElements[i].checked == true) {
                            //checkboxHash.set(formElements[i].name,formElements[i].value);
                            returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);
                        }
                    }
                    //else returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);
                    else if(formElements[i] != undefined && formElements[i].type != "file" && formElements[i].value != null && formElements[i].name != null &&formElements[i].name != ""
                        && formElements[i].value != "" && formElements[i].name != undefined && formElements[i].style.display != "none")
                        returnString=returnString+"&"+formElements[i].name+"="+escape(Utf8.encode(formElements[i].value));
                }
            }

            radioHash.each(function(pair) {
                returnString=returnString+"&"+escape(pair.key)+"="+escape(pair.value);
            });

        /*checkboxHash.each(function(pair) {
                returnString=returnString+"&"+escape(pair.key)+"="+escape(pair.value);
            });*/

        /*
            for ( var i=formElements.length-1; i>=0; --i ){
                returnString=returnString+"&"+formElements[i].name+"="+escape(Utf8.encode(formElements[i].value));
            }
             */
        }
        else
        {
            for ( var i=formElements.length-1; i>=0; --i ){
                //we escape (encode) each value
                if (formElements[i].type == "radio") {
                    if (formElements[i].checked == true) {
                        radioHash.set(formElements[i].name,formElements[i].value);
                    }
                }
                else {
                    if (formElements[i].type == "checkbox") {
                        if (formElements[i].checked == true) {
                            //checkboxHash.set(formElements[i].name,formElements[i].value);
                            returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(Utf8.encode(formElements[i].value));
                        }
                    }
                    //else returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);
                    else if(formElements[i] != undefined && formElements[i].type != "file" && formElements[i].value != null && formElements[i].name != null &&formElements[i].name != ""
                        && formElements[i].value != "" && formElements[i].name != undefined && formElements[i].style.display != "none")
                        returnString=returnString+"&"+formElements[i].name+"="+escape(Utf8.encode(formElements[i].value));
                }
            }

            radioHash.each(function(pair) {
                returnString=returnString+"&"+escape(pair.key)+"="+escape(Utf8.encode(pair.value));
            });

        /*checkboxHash.each(function(pair) {
                    returnString=returnString+"&"+escape(pair.key)+"="+escape(Utf8.encode(pair.value));
                });*/

        /*
                for ( var i=formElements.length-1; i>=0; --i ){
                    returnString=returnString+"&"+formElements[i].name+"="+escape(Utf8.encode(formElements[i].value));
                }*/
        }
    }

    //alert(returnString.length);
    //alert(returnString);
    //return the values

    return returnString;

}


/**
 * TrongLV
 * Fix font hien thi Tieng Viet
 */

getUnicodeMsg =function(str) {
    var ta=document.createElement("textarea");
    ta.innerHTML=str.replace(/</g,"&lt;").replace(/>/g,"&gt;");
    return ta.value;
}


/**
 * tamdt1, 20/08/2010
 * kiem tra 1 chuoi nhap vao co chua cac the HTML khong
 */

function containsHtmlTag(str) {
    if(str.match(/([\<])([^\>]{1,})*([\>])/i) == null) {
        return false;
    } else {
        return true;
    }
}


