/**
 * Created by Hollow on 19.03.2015.
 */

function checkForm(form) {
    var ret = true;

    if (form.Benutzername.value.length < 4 || form.Benutzername.value.length > 8) {
        //alert("Error: Benutzername muss zwischen 4 und 8 Zeichen lang sein!");
        form.Benutzername.focus();

        var para = document.createElement("p");
        var node = document.createTextNode("Error: Benutzername muss zwischen 4 und 8 Zeichen lang sein!");
        para.appendChild(node);

        var element = document.getElementById("error-username");
        element.appendChild(para);

        ret = false;
    }

    if (form.Passwort.value.length < 4 || form.Passwort.value.length > 8) {
        //alert("Error: Passwort muss zwischen 4 und 8 Zeichen lang sein!");

        var para = document.createElement("p");
        var node = document.createTextNode("Error: Passwort muss zwischen 4 und 8 Zeichen lang sein!");
        para.appendChild(node);

        var element = document.getElementById("error-password");
        element.appendChild(para);

        form.Passwort.focus();

        ret = false;
    }

    return ret;
}

function checkField(input,other,registerButton) {
    var ret = true;
    if(document.getElementById("error-message") != null) {
        var tmp = document.getElementById("error-message");
        tmp.parentNode.removeChild(tmp);
    }

    if (input.value.length < 4 || input.value.length > 8) {
        //alert("Error: Benutzername muss zwischen 4 und 8 Zeichen lang sein!");
        input.focus();

        input.className = "site-textfield-error";
        /*
        var para = document.createElement("p");
        para.id = "error-message";
        var node = document.createTextNode("Bitte zwischen 4 und 8 Zeichen eingeben!");
        para.appendChild(node);

        //var element = document.getElementById("input_error");
        element = input.parentNode;
        element.appendChild(para);

        para.style.color="red";
        */
        ret = false;
    }else{
        input.className = "site-textfield";
    }
    
    if (ret && (other.value.length > 3 && other.value.length < 9)) {
        registerButton.className = "registrieren_enabled";
    } else {
        registerButton.className = "registrieren_disabled";
    }

    return ret;
}