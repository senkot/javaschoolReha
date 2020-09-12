<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <c:if test="${empty patient.id}">
        <title>Add new Patient</title>
    </c:if>
    <c:if test="${!empty patient.id}">
        <title>Edit Patient</title>
    </c:if>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">

    <style>
        #my-header {background-color: #e6e6fe}
        .error {color: tomato}
        input:invalid {
            border: 2px dashed red;
        }
        .errorButton {color: tomato}

        .errorInput {
            border: 2px solid tomato;
        }

        .input-requirements {
            font-style: italic;
            text-align: left;
            list-style: disc;
            list-style-position: inside;
            max-width: 400px;
            margin: 10px auto;
            color: rgb(150,150,150);
            max-Height: 0;
            overflow: hidden;
        }
        .input-requirements li.invalid {
            color: #e74c3c;
        }
        .input-requirements li.valid {
            color: #2ecc71;
        }
    </style>
</head>
<body class="text-center">
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
    <header>
        <div id="my-header" class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3  border-bottom shadow-sm">
            <c:if test="${empty patient.id}">
                <h5 class="my-0 mr-md-auto font-weight-normal">Add New Patient</h5>
            </c:if>
            <c:if test="${!empty patient.id}">
                <h5 class="my-0 mr-md-auto font-weight-normal">Edit Patient</h5>
            </c:if>

            <nav class="my-2 my-md-0 mr-md-3">
                <c:if test="${empty patient.id}">
                    <a class="p-2 text-dark" href="<c:url value="/patient-list"/>">Back to Patient LIST</a>
                </c:if>
                <c:if test="${!empty patient.id}">
                    <a class="p-2 text-dark" href="<c:url value="/patient?id=${patient.id}"/>">Back to Patient INFO</a>
                </c:if>
            </nav>

            <a class="btn btn-outline-primary" href="<c:url value='/'/>">Back to Start Page</a>
        </div>
    </header>

    <div class="container">
        <div class="row ">
            <form class="col" method="post" name="mainForm">

                <div class="form-group form-lastName">

                    <c:if test="${!empty errors}">
                        <c:forEach items="${errors}" var="error">
                            <c:if test="${error.defaultMessage.equals('insurance error')}">
                                <div class="error">There is a patient with the same insurance number!</div>
                                <div class="error"><a href="<c:url value="/patient?id=${existedPatientId}" />" target="_blank">Show</a></div>
                            </c:if>
                        </c:forEach>
                    </c:if>

                    <input type="hidden" name="doctorName" value="${user.fullName}">

                    <c:if test="${!empty patient.id}">
                        <input type="hidden" name="patientId" class="form-control" value="<c:out value="${patient.id}"/>" >
                    </c:if>

                    <c:if test="${empty patient.id}">
                        <input type="hidden" name="state" value="in">
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <input type="hidden" name="state" value="${patient.state}">
                    </c:if>


                    <label for="lastName">Surname</label>
                    <c:if test="${empty patient.id}">
                        <input type="text" id="lastName" name="lastName" class="form-control" placeholder="Enter surname"
                        <c:if test="${!empty patientDTO.lastName}"> value="${patientDTO.lastName}" </c:if>   >
                        <ul class="input-requirements">
                            <li>Can contain letters only!</li>
                            <li>Last name is required!</li>
                            <li>At least 2 symbols, and 30 as max</li>
                        </ul>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <input type="text" id="lastName" name="lastName" class="form-control" value="<c:out value="${patient.lastName}"/>"
                        <c:if test="${!empty patientDTO.lastName}"> value="${patientDTO.lastName}" </c:if>>
                        <ul class="input-requirements">
                            <li>Can contain letters only!</li>
                            <li>Last name is required!</li>
                            <li>At least 2 symbols, and 30 as max</li>
                        </ul>
                    </c:if>
                    <c:if test="${!empty errors}">
                        <c:forEach items="${errors}" var="error">
                            <c:if test="${error.defaultMessage.equals('lastName blank')}">
                                <div class="error" >Surname is required!</div>
                            </c:if>
                            <c:if test="${error.defaultMessage.equals('lastName size')}">
                                <div class="error">At least 2 symbols, and 30 as max</div>
                            </c:if>
                        </c:forEach>
                    </c:if>

                </div>

                <div class="form-group form-name">
                    <label for="firstName">Name</label>
                    <c:if test="${empty patient.id}">
                        <input type="text" id="firstName" name="firstName" class="form-control" placeholder="Enter name"
                        <c:if test="${!empty patientDTO.firstName}"> value="${patientDTO.firstName}" </c:if>>
                        <ul class="input-requirements">
                            <li>Can contain letters only!</li>
                            <li>Name is required!</li>
                            <li>At least 2 symbols, and 30 as max</li>
                        </ul>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <input type="text" id="firstName" name="firstName" class="form-control" value="<c:out value="${patient.firstName}"/>"
                        <c:if test="${!empty patientDTO.firstName}"> value="${patientDTO.firstName}" </c:if>>
                        <ul class="input-requirements">
                            <li>Can contain letters only!</li>
                            <li>Name is required!</li>
                            <li>At least 2 symbols, and 30 as max</li>
                        </ul>
                    </c:if>
                    <c:if test="${!empty errors}">
                        <c:forEach items="${errors}" var="error">
                            <c:if test="${error.defaultMessage.equals('firstName blank')}">
                                <div class="error">Name is required!</div>
                            </c:if>
                            <c:if test="${error.defaultMessage.equals('firstName size')}">
                                <div class="error">At least 2 symbols required, and 30 as max</div>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <div class="error" id="nameErrorRequired"></div>
                    <div class="error" id="nameErrorSize"></div>


                </div>

                <div class="form-group form-secondName">
                    <label for="secondName">Second name</label>
                    <c:if test="${empty patient.id}">
                        <input type="text" id="secondName" name="secondName" class="form-control" placeholder="Enter second name"
                        <c:if test="${!empty patientDTO.secondName}"> value="${patientDTO.secondName}" </c:if>>
                        <ul class="input-requirements">
                            <li>Can contain letters only!</li>
                        </ul>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <input type="text" id="secondName" name="secondName" class="form-control" value="<c:out value="${patient.secondName}"/>"
                        <c:if test="${!empty patientDTO.secondName}"> value="${patientDTO.secondName}" </c:if>>
                        <ul class="input-requirements">
                            <li>Can contain letters only!</li>
                        </ul>
                    </c:if>
                </div>

                <div class="form-group form-dateOfBirth">
                    <label for="dateOfBirth">Date of Birth</label>
                    <c:if test="${empty patient.id}">
                        <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control"
                        <c:if test="${!empty patientDTO.dateOfBirth}"> value="${patientDTO.dateOfBirth}" </c:if>>
                        <ul class="input-requirements">
                            <li>Maximum patient age 130 years!</li>
                            <li>Minimum age 18 years!</li>
                        </ul>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="<c:out value="${patient.dateOfBirth}"/>"
                        <c:if test="${!empty patientDTO.dateOfBirth}"> value="${patientDTO.dateOfBirth}" </c:if>>
                        <ul class="input-requirements">
                            <li>Maximum patient age 130 years!</li>
                            <li>Minimum age 18 years!</li>
                        </ul>
                    </c:if>
                    <c:if test="${!empty errors}">
                        <c:forEach items="${errors}" var="error">
                            <c:if test="${error.defaultMessage.equals('dateOfBirth blank')}">
                                <div class="error">Select the date of birth!</div>
                            </c:if>
                            <c:if test="${error.defaultMessage.equals('date future')}">
                                <div class="error">Check the date! It's in the future!</div>
                            </c:if>
                            <c:if test="${error.defaultMessage.equals('date young')}">
                                <div class="error">Patient must be older than 18 years!</div>
                            </c:if>
                            <c:if test="${error.defaultMessage.equals('date old')}">
                                <div class="error">Check the date! It's more than 150 years Patient!</div>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </div>

                <div class="form-group form-insurance">
                    <label for="insurance">Insurance</label>
                    <c:if test="${empty patient.id}">
                        <input type="text" id="insurance" name="insurance" class="form-control" placeholder="Enter number of main insurance"
                        <c:if test="${!empty patientDTO.insurance}"> value="${patientDTO.insurance}" </c:if>>
                        <ul class="input-requirements">
                            <li>Insurance is required!</li>
                        </ul>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <input type="text" id="insurance" name="insurance" class="form-control" value="<c:out value="${patient.insurance}"/>"
                        <c:if test="${!empty patientDTO.insurance}"> value="${patientDTO.insurance}" </c:if>>
                        <ul class="input-requirements">
                            <li>Insurance is required!</li>
                        </ul>
                    </c:if>
                    <c:if test="${!empty errors}">
                        <c:forEach items="${errors}" var="error">
                            <c:if test="${error.defaultMessage.equals('insurance blank')}">
                                <div class="error">Number of insurance is required!</div>
                            </c:if>
                            <c:if test="${error.defaultMessage.equals('insurance size')}">
                                <div class="error">At least 7 symbols required!</div>
                                <div class="error">For foreign insurance maximum size - 30 symbols</div>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="additionalInsurance">Additional Insurance</label>
                    <c:if test="${empty patient.id}">
                        <input type="text" id="additionalInsurance" name="additionalInsurance" class="form-control" placeholder="Enter number of additional insurance if there is"
                        <c:if test="${!empty patientDTO.additionalInsurance}"> value="${patientDTO.additionalInsurance}" </c:if>>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <input type="text" id="additionalInsurance" name="additionalInsurance" class="form-control" value="<c:out value="${patient.additionalInsurance}"/>"
                        <c:if test="${!empty patientDTO.additionalInsurance}"> value="${patientDTO.additionalInsurance}" </c:if>>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="diagnosis">Diagnosis</label>
                    <c:if test="${empty patient.id}">
                        <select class="form-control" id="diagnosis" name="diagnosis">
                            <option <c:if test="${!empty patientDTO.diagnosis &&
                            patientDTO.diagnosis.equals('tiredness')}"> selected </c:if>>tiredness</option>
                            <option <c:if test="${!empty patientDTO.diagnosis &&
                            patientDTO.diagnosis.equals('sadness')}"> selected </c:if>>sadness</option>
                            <option <c:if test="${!empty patientDTO.diagnosis &&
                            patientDTO.diagnosis.equals('stress')}"> selected </c:if>>stress</option>
                            <option <c:if test="${!empty patientDTO.diagnosis &&
                            patientDTO.diagnosis.equals('depression')}"> selected </c:if>>depression</option>
                            <option <c:if test="${!empty patientDTO.diagnosis &&
                            patientDTO.diagnosis.equals('weakness')}"> selected </c:if>>weakness</option>
                            <option <c:if test="${!empty patientDTO.diagnosis &&
                            patientDTO.diagnosis.equals('boredom')}"> selected </c:if>>boredom</option>
                            <option <c:if test="${!empty patientDTO.diagnosis &&
                            patientDTO.diagnosis.equals('insomnia')}"> selected </c:if>>insomnia</option>
                            <option <c:if test="${!empty patientDTO.diagnosis &&
                            patientDTO.diagnosis.equals('nervousness')}"> selected </c:if>>nervousness</option>
                        </select>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <select  class="form-control" id="diagnosis" name="diagnosis">
                            <c:if test="${patient.diagnosis == 'tiredness'}">
                                <option selected>tiredness</option>
                                <option>sadness</option>
                                <option>stress</option>
                                <option>depression</option>
                                <option>weakness</option>
                                <option>boredom</option>
                                <option>insomnia</option>
                                <option>nervousness</option>
                            </c:if>
                            <c:if test="${patient.diagnosis == 'sadness'}">
                                <option>tiredness</option>
                                <option selected>sadness</option>
                                <option>stress</option>
                                <option>depression</option>
                                <option>weakness</option>
                                <option>boredom</option>
                                <option>insomnia</option>
                                <option>nervousness</option>
                            </c:if>
                            <c:if test="${patient.diagnosis == 'stress'}">
                                <option>tiredness</option>
                                <option>sadness</option>
                                <option selected>stress</option>
                                <option>depression</option>
                                <option>weakness</option>
                                <option>boredom</option>
                                <option>insomnia</option>
                                <option>nervousness</option>
                            </c:if>
                            <c:if test="${patient.diagnosis == 'depression'}">
                                <option>tiredness</option>
                                <option>sadness</option>
                                <option>stress</option>
                                <option selected>depression</option>
                                <option>weakness</option>
                                <option>boredom</option>
                                <option>insomnia</option>
                                <option>nervousness</option>
                            </c:if>
                            <c:if test="${patient.diagnosis == 'weakness'}">
                                <option>tiredness</option>
                                <option>sadness</option>
                                <option>stress</option>
                                <option>depression</option>
                                <option selected>weakness</option>
                                <option>boredom</option>
                                <option>insomnia</option>
                                <option>nervousness</option>
                            </c:if>
                            <c:if test="${patient.diagnosis == 'boredom'}">
                                <option>tiredness</option>
                                <option>sadness</option>
                                <option>stress</option>
                                <option>depression</option>
                                <option>weakness</option>
                                <option selected>boredom</option>
                                <option>insomnia</option>
                                <option>nervousness</option>
                            </c:if>
                            <c:if test="${patient.diagnosis == 'insomnia'}">
                                <option>tiredness</option>
                                <option>sadness</option>
                                <option>stress</option>
                                <option>depression</option>
                                <option>weakness</option>
                                <option>boredom</option>
                                <option selected>insomnia</option>
                                <option>nervousness</option>
                            </c:if>
                            <c:if test="${patient.diagnosis == 'nervousness'}">
                                <option>tiredness</option>
                                <option>sadness</option>
                                <option>stress</option>
                                <option>depression</option>
                                <option>weakness</option>
                                <option>boredom</option>
                                <option>insomnia</option>
                                <option selected>nervousness</option>
                            </c:if>
                        </select>
                    </c:if>
                </div>

                <c:if test="${empty patient.id}">
                    <form:form action="${pageContext.request.contextPath}/add" method="post">
                        <button class="btn btn-primary" id="buttonAdd" type="submit">ADD</button>
                    </form:form>
                </c:if>
                <c:if test="${!empty patient.id}">
                    <form:form action="${pageContext.request.contextPath}/edit" method="post">
                        <button class="btn btn-primary" id="buttonEdit" type="submit">EDIT</button>
                    </form:form>
                </c:if>
            </form>

        </div>
    </div>
    <footer class="mastfoot mt-auto">
        <div class="inner">
            <p>Chandra Clinic &copy; 2020 by Konstantin Senko</p>
        </div>
    </footer>

</div>

<script>
    function birthdayToday() {
        document.getElementById("dateOfBirth").valueAsDate = new Date();
    }

    var patient = patient;
    if (patient == null) {
        startToday();
        endToday()
    }

</script>
<script src="/js/patient-form-validation.js" type="text/javascript"></script>

</body>
</html>
