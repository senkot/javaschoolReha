
var input = document.getElementById("firstName");

function checkButton(){
    try {
        if (validateName() === true
            && validateLastName() === true
            && validateSecondName() === true
            && validateDateOfBirth() === true
            && validateInsurance() === true) {

            document.getElementById("buttonAdd").removeAttribute("disabled");

        } else {
            document.getElementById("buttonAdd").disabled = "true";
        }
    } catch (e) {
        if (validateName() === true
            && validateLastName() === true
            && validateSecondName() === true
            && validateDateOfBirth() === true) {

            document.getElementById("buttonEdit").removeAttribute("disabled");

        } else {
            document.getElementById("buttonEdit").disabled = "true";
        }
    }

}
function validateOnBlurName(){

    if (validateName() === true) {
        document.querySelector(".form-name > .input-requirements").style.maxHeight="0px";
        document.getElementById("firstName").classList.remove("errorInput");

    } else {
        document.getElementById("firstName").classList.add("errorInput");
    }

    checkButton();

}
function validateOnBlurLastName(){

    if (validateLastName() === true) {

        document.querySelector(".form-lastName > .input-requirements").style.maxHeight="0px";
        document.getElementById("lastName").classList.remove("errorInput");

    } else {
        document.getElementById("lastName").classList.add("errorInput");
    }
    checkButton();
}

function validateOnBlurSecondName(){

    if (validateSecondName() === true) {

        document.querySelector(".form-secondName > .input-requirements").style.maxHeight="0px";
        document.getElementById("secondName").classList.remove("errorInput");

    } else {
        document.getElementById("secondName").classList.add("errorInput");
    }
    checkButton();
}

function validateOnBlurDateOfBirth(){

    if(validateDateOfBirth() === true)
    {
        document.getElementById("dateOfBirth").classList.remove("errorInput");
        document.querySelector(".form-dateOfBirth > .input-requirements").style.maxHeight="0px";
    } else {document.getElementById("dateOfBirth").classList.add("errorInput");
        document.querySelector(".form-dateOfBirth > .input-requirements").style.maxHeight="1000px";
    }
    checkButton();
}

function validateOnBlurInsurance() {
    if(validateInsurance() === true) {
        document.querySelector(".form-insurance > .input-requirements").style.maxHeight="0px";
        document.getElementById("insurance").classList.remove("errorInput");

    } else {
        document.getElementById("insurance").classList.add("errorInput");
    }
    checkButton();
    }


function validateOnChangeName(){
    if (validateName() === false) {
        document.querySelector(".form-name > .input-requirements").style.maxHeight="1000px";
    }
}

function validateOnChangeLastName(){
    if (validateLastName() === false) {
        document.querySelector(".form-lastName > .input-requirements").style.maxHeight="1000px";
    }
}
function validateOnChangeSecondName(){
    if (validateSecondName() === false) {

        document.querySelector(".form-secondName > .input-requirements").style.maxHeight="1000px";
    }
}
function validateOnChangeInsurance(){
    if(validateInsurance() === false){
        document.querySelector(".form-insurance > .input-requirements").style.maxHeight="1000px";
    }
}

function validateName(){

    document.forms.mainForm.firstName.value = document.forms.mainForm.firstName.value.trim();

    var x=document.forms.mainForm.firstName.value;

    var li1 = document.querySelector(".form-name > .input-requirements li:nth-child(1)");
    var li2 = document.querySelector(".form-name > .input-requirements li:nth-child(2)");
    var li3 = document.querySelector(".form-name > .input-requirements li:nth-child(3)");

    if (/[^a-zA-Zа-яА-я/]+/.test(x)){
        li1.classList.add('invalid');
        li1.classList.remove('valid');
    } else{
        li1.classList.add('valid');
        li1.classList.remove('invalid');
    }

    if (x.length == 0) {
        li2.classList.add('invalid');
        li2.classList.remove('valid');
    } else{
        li2.classList.add('valid');
        li2.classList.remove('invalid');
    }

    if (x.length < 2 || x.length > 30) {
        li3.classList.add('invalid');
        li3.classList.remove('valid');
    } else{
        li3.classList.add('valid');
        li3.classList.remove('invalid');
    }
    if (li1.classList.contains('valid') && li2.classList.contains('valid') && li3.classList.contains('valid')){
        return true;
    } else return false;

}

function validateLastName(){

    document.forms.mainForm.lastName.value = document.forms.mainForm.lastName.value.trim();

    var x=document.forms.mainForm.lastName.value;

    var li1 = document.querySelector(".form-lastName > .input-requirements li:nth-child(1)");
    var li2 = document.querySelector(".form-lastName > .input-requirements li:nth-child(2)");
    var li3 = document.querySelector(".form-lastName > .input-requirements li:nth-child(3)");

    if (/[^a-zA-Zа-яА-я/]+/.test(x)){
        li1.classList.add('invalid');
        li1.classList.remove('valid');
    } else{
        li1.classList.add('valid');
        li1.classList.remove('invalid');
    }

    if (x.length == 0) {
        li2.classList.add('invalid');
        li2.classList.remove('valid');
    } else{
        li2.classList.add('valid');
        li2.classList.remove('invalid');
    }

    if (x.length < 2 || x.length > 30) {
        li3.classList.add('invalid');
        li3.classList.remove('valid');
    } else{
        li3.classList.add('valid');
        li3.classList.remove('invalid');
    }
    if (li1.classList.contains('valid') && li2.classList.contains('valid') && li3.classList.contains('valid')){
        return true;
    } else return false;
}

function validateSecondName(){

    document.forms.mainForm.secondName.value = document.forms.mainForm.secondName.value.trim();

    var x=document.forms.mainForm.secondName.value;

    var li1 = document.querySelector(".form-secondName > .input-requirements li:nth-child(1)");

    if (/[^a-zA-Zа-яА-я/]+/.test(x)){
        li1.classList.add('invalid');
        li1.classList.remove('valid');
    } else{
        li1.classList.add('valid');
        li1.classList.remove('invalid');
    }

    if (li1.classList.contains('valid')){
        return true;
    } else return false;
}

function validateDateOfBirth(){
    var x = document.forms.mainForm.dateOfBirth.value;
    var diffYears = (new Date() - new Date(x))/1000/60/24/60/365.25;

    var li1 = document.querySelector(".form-dateOfBirth > .input-requirements li:nth-child(1)");
    var li2 = document.querySelector(".form-dateOfBirth > .input-requirements li:nth-child(2)");

    if(diffYears > 130){
        li1.classList.add('invalid');
        li1.classList.remove('valid');
    } else {
        li1.classList.remove('invalid');
        li1.classList.add('valid');
    }

    if (diffYears < 18){
        li2.classList.add('invalid');
        li2.classList.remove('valid');
    } else {
        li2.classList.remove('invalid');
        li2.classList.add('valid');
    }


    if (li1.classList.contains('valid') && li2.classList.contains('valid')){
        return true;
    } else return false;
}
function validateInsurance(){
    document.forms.mainForm.insurance.value = document.forms.mainForm.insurance.value.trim();

    var x=document.forms.mainForm.insurance.value;
    var li1 = document.querySelector(".form-insurance > .input-requirements li:nth-child(1)");


    if (x.length == 0) {
        li1.classList.add('invalid');
        li1.classList.remove('valid');
        return false;
    } else{
        li1.classList.add('valid');
        li1.classList.remove('invalid');
        return true;
    }

}

var inputName = document.getElementById("firstName");

inputName.addEventListener('keyup', validateOnChangeName);
inputName.addEventListener('blur', validateOnBlurName);

var inputLastName = document.getElementById("lastName");

inputLastName.addEventListener('keyup', validateOnChangeLastName);
inputLastName.addEventListener('blur', validateOnBlurLastName);

var inputSecondName = document.getElementById("secondName");

inputSecondName.addEventListener('keyup', validateOnChangeSecondName);
inputSecondName.addEventListener('blur', validateOnBlurSecondName);

var inputDateOfBirth = document.getElementById("dateOfBirth");
inputDateOfBirth.addEventListener('blur', validateOnBlurDateOfBirth);

var inputInsurance = document.getElementById("insurance");

inputInsurance.addEventListener('keyup', validateOnChangeInsurance);
inputInsurance.addEventListener('blur', validateOnBlurInsurance);