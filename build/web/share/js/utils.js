function getFormAsString(formName){    var radioHash = new Hash();    //var checkboxHash = new Hash();    //Setup the return String    returnString ="";    //Get the form values    formElements=document.forms[formName].elements;    //loop through the array , building up the url    //in the form /strutsaction.do&name=value    if(navigator.userAgent.indexOf("Safari")!=-1){        for ( var i=formElements.length-1; i>=0; --i ){            //we escape (encode) each value            if (formElements[i].type == "radio") {                if (formElements[i].checked == true) {                    radioHash.set(formElements[i].name,formElements[i].value);                }            }            else {                if (formElements[i].type == "checkbox") {                    if (formElements[i].checked == true) {                        //checkboxHash.set(formElements[i].name,formElements[i].value);                        returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);                    }                }                //else returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);                else if(formElements[i] != undefined && formElements[i].type != "file" && formElements[i].value != null && formElements[i].name != null &&formElements[i].name != ""                    && formElements[i].value != "" && formElements[i].name != undefined && formElements[i].style.display != "none")                    returnString=returnString+"&"+formElements[i].name+"=" + formElements[i].value;            }        }        radioHash.each(function(pair) {            returnString=returnString+"&"+escape(pair.key)+"="+escape(pair.value);        });        /*checkboxHash.each(function(pair) {                returnString=returnString+"&"+escape(pair.key)+"="+escape(pair.value);            });*/        /*        for ( var i=formElements.length-1; i>=0; --i ){            returnString=returnString+"&"+formElements[i].name+"=" + formElements[i].value;        }*/    } else if(navigator.userAgent.indexOf("Gecko")!=-1) {        for ( var i=formElements.length-1; i>=0; --i ){            //we escape (encode) each value            if (formElements[i].type == "radio") {                if (formElements[i].checked == true) {                    radioHash.set(formElements[i].name,formElements[i].value);                }            }            else {                if (formElements[i].type == "checkbox") {                    if (formElements[i].checked == true) {                        //checkboxHash.set(formElements[i].name,formElements[i].value);                        returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);                    }                }                //else returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);                else if(formElements[i] != undefined && formElements[i].type != "file" && formElements[i].value != null && formElements[i].name != null &&formElements[i].name != ""                    && formElements[i].value != "" && formElements[i].name != undefined && formElements[i].style.display != "none")                    returnString=returnString+"&"+formElements[i].name+"=" + formElements[i].value;            }        }        radioHash.each(function(pair) {            returnString=returnString+"&"+escape(pair.key)+"="+escape(pair.value);        });        /*checkboxHash.each(function(pair) {                returnString=returnString+"&"+escape(pair.key)+"="+escape(pair.value);            });*/        /*        for ( var i=formElements.length-1; i>=0; --i ){            returnString=returnString+"&"+formElements[i].name+"=" + formElements[i].value;        }*/    }    else    {        if(navigator.userAgent.indexOf("MSIE 7.0")!=-1) {            for ( var i=formElements.length-1; i>=0; --i ){                //we escape (encode) each value                if (formElements[i].type == "radio") {                    if (formElements[i].checked == true) {                        radioHash.set(formElements[i].name,formElements[i].value);                    }                }                else {                    if (formElements[i].type == "checkbox") {                        if (formElements[i].checked == true) {                            //checkboxHash.set(formElements[i].name,formElements[i].value);                            returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);                        }                    }                    //else returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);                    else if(formElements[i] != undefined && formElements[i].type != "file" && formElements[i].value != null && formElements[i].name != null &&formElements[i].name != ""                        && formElements[i].value != "" && formElements[i].name != undefined && formElements[i].style.display != "none")                    	returnString=returnString+"&"+formElements[i].name+"="+escape(Utf8.encode(formElements[i].value));                }            }            radioHash.each(function(pair) {                returnString=returnString+"&"+escape(pair.key)+"="+escape(pair.value);            });            /*checkboxHash.each(function(pair) {                returnString=returnString+"&"+escape(pair.key)+"="+escape(pair.value);            });*/            /*            for ( var i=formElements.length-1; i>=0; --i ){                returnString=returnString+"&"+formElements[i].name+"="+escape(Utf8.encode(formElements[i].value));            }             */        }        else        {            for ( var i=formElements.length-1; i>=0; --i ){                //we escape (encode) each value                if (formElements[i].type == "radio") {                    if (formElements[i].checked == true) {                        radioHash.set(formElements[i].name,formElements[i].value);                    }                }                else {                    if (formElements[i].type == "checkbox") {                        if (formElements[i].checked == true) {                            //checkboxHash.set(formElements[i].name,formElements[i].value);                            returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(Utf8.encode(formElements[i].value));                        }                    }                    //else returnString=returnString+"&"+escape(formElements[i].name)+"="+escape(formElements[i].value);                    else if(formElements[i] != undefined && formElements[i].type != "file" && formElements[i].value != null && formElements[i].name != null &&formElements[i].name != ""                        && formElements[i].value != "" && formElements[i].name != undefined && formElements[i].style.display != "none")                        returnString=returnString+"&"+formElements[i].name+"="+escape(Utf8.encode(formElements[i].value));                }            }            radioHash.each(function(pair) {                returnString=returnString+"&"+escape(pair.key)+"="+escape(Utf8.encode(pair.value));            });            /*checkboxHash.each(function(pair) {                    returnString=returnString+"&"+escape(pair.key)+"="+escape(Utf8.encode(pair.value));                });*/            /*                for ( var i=formElements.length-1; i>=0; --i ){                    returnString=returnString+"&"+formElements[i].name+"="+escape(Utf8.encode(formElements[i].value));                }*/        }    }    //alert(returnString.length);    //alert(returnString);    //return the values    return returnString;}