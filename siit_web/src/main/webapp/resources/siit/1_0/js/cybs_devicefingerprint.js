function cybs_dfprofiler(merchantID, environment) {
    merchantID = $('#formListaCarteraObligaciones\\:merchantId').val();
    if (environment.toLowerCase() == 'test') {
        var org_id = 'k8vif92e';
    } else {
        var org_id = '1snn5n9w';
    }
    var sessionID = new Date().getTime();
    // $("#imgDevice").remove();
    $("#thm_fp").remove();
    // $("#src_matrix").remove();
    $("#paragraphTMID").remove();

    //One-Pixel Image Code
    var paragraphTM = document.createElement("p");
    str = "";
    str = "background:url(https://h.online-metrix.net/fp/clear.png?org_id=" + org_id + "&session_id=" + merchantID + sessionID + "&m=1)";
    paragraphTM.styleSheets = str;
    paragraphTM.id = "paragraphTMID";
    document.body.appendChild(paragraphTM);

    if ($('#imgDevice').length) {
        $("#imgDevice").attr("src", "https://h.online-metrix.net/fp/clear.png?org_id=" + org_id + "&session_id=" + merchantID + sessionID + "&m=2");
    } else {
        var img = document.createElement("img");
        str = "https://h.online-metrix.net/fp/clear.png?org_id=" + org_id + "&session_id=" + merchantID + sessionID + "&m=2";
        img.src = str;
        img.alt = "";
        img.id = "imgDevice";
        document.body.appendChild(img);
    }

    //Flash Code
    var objectTM = document.createElement("object");
    objectTM.data = "https://h.online-metrix.net/fp/fp.swf?org_id=" + org_id + "&session_id=" + merchantID + sessionID;
    objectTM.type = "application/x-shockwave-flash";
    objectTM.width = "1";
    objectTM.height = "1";
    objectTM.id = "thm_fp";
    var param = document.createElement("param");
    param.name = "movie";
    param.value = "https://h.online-metrix.net/fp/fp.swf?org_id=" + org_id + "&session_id=" + merchantID + sessionID;
    objectTM.appendChild(param);
    document.body.appendChild(objectTM);

    //JavaScript Code
    if ($('#src_matrix').length) {
        $("#src_matrix").attr("src", "https://h.online-metrix.net/fp/tags.js?org_id=" + org_id + "&session_id=" + merchantID + sessionID);
    } else {
        var tmscript = document.createElement("script");
        tmscript.src = "https://h.online-metrix.net/fp/tags.js?org_id=" + org_id + "&session_id=" + merchantID + sessionID;
        tmscript.type = "text/javascript";
        tmscript.id = "src_matrix";
        document.body.appendChild(tmscript);
    }

    var inputform1 = jQuery('#formListaCarteraObligaciones\\:deviceFingerprint');
    var inputform2 = jQuery('#formCrearFormulario\\:deviceFingerprint');
    if (inputform1.length > 0) {
        inputform1.val(sessionID);
    } else if (inputform2.length > 0) {
        inputform2.val(sessionID);
    }

    // console.log('merchantID: ' + merchantID);
    // console.log('sessionID: ' + sessionID);
    // console.log('url: ' + "https://h.online-metrix.net/fp/tags.js?org_id=" + org_id + "&session_id=" + merchantID + sessionID);

    return sessionID;
}
