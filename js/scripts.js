/**
 * Created by Hollow on 19.03.2015.
 */

function checkField(input, other, registerButton) {
    var ret = true;

    if (input.value.length < 4 || input.value.length > 8) {
        input.focus();
        input.className = "site-textfield-error";
        ret = false;
    }else{
        input.className = "site-textfield";
    }
    
    if (ret && (other.value.length > 3 && other.value.length < 9)) {
        registerButton.className = "registrieren_enabled";
        $('#Registrieren').attr('disabled','enabled');
    } else {
        registerButton.className = "registrieren_disabled";
        $('#Registrieren').attr('disabled','disabled');
    }

    return ret;
}

function checkDate(input) {
    if(!hasNativeDateInput()){
    date = /^(((0[1-9]|[12]\d|3[01])\/(0[13578]|1[02])\/((19|[2-9]\d)\d{2}))|((0[1-9]|[12]\d|30)\/(0[13456789]|1[012])\/((19|[2-9]\d)\d{2}))|((0[1-9]|1\d|2[0-8])\/02\/((19|[2-9]\d)\d{2}))|(29\/02\/((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))))$/;
    if (input.value.match(date)) {
        input.className = "site-textfield";
    }else{
            input.className = "site-textfield-error";
        }
    }
}

    