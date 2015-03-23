/**
 * Created by Hollow on 19.03.2015.
 */

function checkForm(form) {
    if (form.Benutzername.value.length < 4 || form.Benutzername.value.length > 8) {
        alert("Error: Benutzername muss zwischen 4 und 8 Zeichen lang sein!");
        form.Benutzername.focus();
        return false;
    }

    if (form.Passwort.value.length < 4 || form.Passwort.value.length > 8) {
        alert("Error: Passwort muss zwischen 4 und 8 Zeichen lang sein!");
        form.Passwort.focus();
        return false;
    }

    return true;
}

function checkField(input){
    if (input.value.length < 4 || input.value.length > 8) {
        input.focus();
        return false;
    }
    return true;
}