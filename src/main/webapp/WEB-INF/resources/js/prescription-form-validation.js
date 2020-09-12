function checkButton(){
    try {
        if (validateRemedyName() === true
        && validateQuantity() === true
        && validateDateStart() === true
        && validateDateEnd() === true
        ) {
            document.getElementById("buttonAdd").removeAttribute("disabled");
        } else {
            document.getElementById("buttonAdd").disabled = "true";
        }
    } catch (e) {
        if (validateRemedyName() === true
        && validateQuantity() === true
        && validateDateStart() === true
        && validateDateEnd() === true
        ) {
            document.getElementById("buttonEdit").removeAttribute("disabled");
        } else {
            document.getElementById("buttonEdit").disabled = "true";
        }
    }
}

function validateOnBlurName() {
    if (validateRemedyName() === true) {
        document.querySelector(".form-remedyName > .input-requirements").style.maxHeight = "0px";
        document.getElementById("remedyName").classList.remove("errorInput");
    } else {
        document.getElementById("remedyName").classList.add("errorInput");
    }
    checkButton();
}

function validateQuantity() {
    var quantity = document.forms.mainForm.quantity.value;
    return quantity.length !== 0;
}

function validateOnBlurQuantity() {
    if (validateQuantity() === true) {
        document.querySelector(".form-quantity > .input-requirements").style.maxHeight = "0px";
        document.getElementById("quantity").classList.remove("errorInput");
    } else {
        document.getElementById("quantity").classList.add("errorInput");
    }
    checkButton();
}

function validateRemedyName(){
    document.forms.mainForm.remedyName.value = document.forms.mainForm.remedyName.value.trim();

    var remedyName = document.forms.mainForm.remedyName.value;

    var li1 = document.querySelector(".form-remedyName > .input-requirements li:nth-child(1)");
    var li2 = document.querySelector(".form-remedyName > .input-requirements li:nth-child(2)");

    if (remedyName.length === 0) {
        li1.classList.add('invalid');
        li1.classList.remove('valid');
    } else {
        li1.classList.add('valid');
        li1.classList.remove('invalid');
    }

    if (remedyName.length < 2 || remedyName.length > 30) {
        li2.classList.add('invalid');
        li2.classList.remove('valid');
    } else {
        li2.classList.add('valid');
        li2.classList.remove('invalid');
    }

    return (li1.classList.contains('valid') && li2.classList.contains('valid'));
}

function validateOnChangeRemedyName() {
    if (validateRemedyName() === false) {
        document.querySelector(".form-remedyName > .input-requirements").style.maxHeight = "1000px";
    }
}

function validateOnChangeQuantity() {
    var li = document.querySelector(".form-quantity > .input-requirements li:nth-child(1)");

    if (validateQuantity() === false) {
        li.classList.add('invalid');
        li.classList.remove('valid');

        document.querySelector(".form-quantity > .input-requirements").style.maxHeight = "1000px";
    } else {
        li.classList.add('valid');
        li.classList.remove('invalid');
    }
}

function validateDateStart() {
    var date = new Date();
    var dateNow = new Date(date.getFullYear(), date.getMonth(), date.getDate());
    var dateStart = new Date(document.forms.mainForm.dateOfStart.value);

    var li1 = document.querySelector(".form-dateOfStart > .input-requirements li:nth-child(1)");
    var li2 = document.querySelector(".form-dateOfStart > .input-requirements li:nth-child(2)");

    if (dateStart.length === 0) {
        li1.classList.add('invalid');
        li1.classList.remove('valid');
    } else {
        li1.classList.add('valid');
        li1.classList.remove('invalid');
    }

    if (dateStart < dateNow) {
        li2.classList.add('invalid');
        li2.classList.remove('valid');
    } else {
        li2.classList.add('valid');
        li2.classList.remove('invalid');
    }

    return (li1.classList.contains('valid') && li2.classList.contains('valid'));
}

function validateOnBlurStart() {
    if (validateDateStart() === true) {
        document.getElementById("dateOfStart").classList.remove("errorInput");
        document.querySelector(".form-dateOfStart > .input-requirements").style.maxHeight = "0px";
        document.getElementById("dateOfEnd").disabled=false;
    } else {
        document.getElementById("dateOfStart").classList.add("errorInput");
        document.querySelector(".form-dateOfStart > .input-requirements").style.maxHeight = "1000px";
        document.getElementById("dateOfEnd").disabled=true;
    }
    checkButton();
}

function validateDateEnd() {
    var dateStart = new Date(document.forms.mainForm.dateOfStart.value);
    var dateEnd = new Date(document.forms.mainForm.dateOfEnd.value);

    var li1 = document.querySelector(".form-dateOfEnd > .input-requirements li:nth-child(1)");
    var li2 = document.querySelector(".form-dateOfEnd > .input-requirements li:nth-child(2)");

    if (dateEnd.length === 0) {
        li1.classList.add('invalid');
        li1.classList.remove('valid');
    } else {
        li1.classList.add('valid');
        li1.classList.remove('invalid');
    }

    if (dateEnd < dateStart) {
        li2.classList.add('invalid');
        li2.classList.remove('valid');
    } else {
        li2.classList.add('valid');
        li2.classList.remove('invalid');
    }

    return (li1.classList.contains('valid') && li2.classList.contains('valid'));
}

function validateOnBlurEnd() {
    if (validateDateEnd() === true) {
        document.getElementById("dateOfEnd").classList.remove("errorInput");
        document.querySelector(".form-dateOfEnd > .input-requirements").style.maxHeight = "0px";
    } else {
        document.getElementById("dateOfEnd").classList.add("errorInput");
        document.querySelector(".form-dateOfEnd > .input-requirements").style.maxHeight = "1000px";
    }
    checkButton();
}

var inputRemedy = document.getElementById("remedyName");

inputRemedy.addEventListener('keyup', validateOnChangeRemedyName);
inputRemedy.addEventListener('blur', validateOnBlurName);

var inputQuantity = document.getElementById("quantity");

inputQuantity.addEventListener('keyup', validateOnChangeQuantity);
inputQuantity.addEventListener('blur', validateOnBlurQuantity);

var inputDateStart = document.getElementById("dateOfStart");
var inputDateEnd = document.getElementById("dateOfEnd");

inputDateStart.addEventListener('blur', validateOnBlurStart);
inputDateEnd.addEventListener('blur', validateOnBlurEnd);