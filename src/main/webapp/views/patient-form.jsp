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

    <form method="post">

        <div class="form-group">

            <c:if test="${!empty errors}">
                <c:forEach items="${errors}" var="error">
                    <c:if test="${error.defaultMessage.equals('PatientDTOValidator insurance error')}">
                        <div>There is a patient with the same insurance number!</div>
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
                <input type="text" id="lastName" name="lastName" class="form-control" placeholder="Enter surname">
            </c:if>
            <c:if test="${!empty patient.id}">
                <input type="text" id="lastName" name="lastName" class="form-control" value="<c:out value="${patient.lastName}"/>" >
            </c:if>
            <c:if test="${!empty errors}">
                <c:forEach items="${errors}" var="error">
                    <c:if test="${error.defaultMessage.equals('lastName blank')}">
                        <div>Surname is required!</div>
                    </c:if>
                </c:forEach>
            </c:if>

        </div>

        <div class="form-group">
            <label for="firstName">Name</label>
            <c:if test="${empty patient.id}">
                <input type="text" id="firstName" name="firstName" class="form-control" placeholder="Enter name">
            </c:if>
            <c:if test="${!empty patient.id}">
                <input type="text" id="firstName" name="firstName" class="form-control" value="<c:out value="${patient.firstName}"/>">
            </c:if>
        </div>

        <div class="form-group">
            <label for="secondName">Second name</label>
            <c:if test="${empty patient.id}">
                <input type="text" id="secondName" name="secondName" class="form-control" placeholder="Enter second name">
            </c:if>
            <c:if test="${!empty patient.id}">
                <input type="text" id="secondName" name="secondName" class="form-control" value="<c:out value="${patient.secondName}"/>">
            </c:if>
        </div>

        <div class="form-group">
            <label for="dateOfBirth">Date of Birth</label>
            <c:if test="${empty patient.id}">
                <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control">
            </c:if>
            <c:if test="${!empty patient.id}">
                <input type="date" id="dateOfBirth" name="dateOfBirth" class="form-control" value="<c:out value="${patient.dateOfBirth}"/>">
            </c:if>
        </div>

        <div class="form-group">
            <label for="insurance">Insurance</label>
            <c:if test="${empty patient.id}">
                <input type="text" id="insurance" name="insurance" class="form-control" placeholder="Enter number of main insurance">
            </c:if>
            <c:if test="${!empty patient.id}">
                <input type="text" id="insurance" name="insurance" class="form-control" value="<c:out value="${patient.insurance}"/>">
            </c:if>
        </div>

        <div class="form-group">
            <label for="additionalInsurance">Additional Insurance</label>
            <c:if test="${empty patient.id}">
                <input type="text" id="additionalInsurance" name="additionalInsurance" class="form-control" placeholder="Enter number of additional insurance if there is">
            </c:if>
            <c:if test="${!empty patient.id}">
                <input type="text" id="additionalInsurance" name="additionalInsurance" class="form-control" value="<c:out value="${patient.additionalInsurance}"/>">
            </c:if>
        </div>

        <div class="form-group">
            <label for="diagnosis">Diagnosis</label>
            <c:if test="${empty patient.id}">
                <select class="form-control" id="diagnosis" name="diagnosis">
                    <option>tiredness</option>
                    <option>sadness</option>
                    <option>stress</option>
                    <option>depression</option>
                    <option>weakness</option>
                    <option>boredom</option>
                    <option>insomnia</option>
                    <option>nervousness</option>
                </select>
            </c:if>
            <c:if test="${!empty patient.id}">
                <select  class="form-control" id="diagnosis" name="diagnosis">
                    <c:if test="${patient.diagnosis == 'tiredness'}">
                        <option selected>tiredness</option>
                    </c:if>
                    <c:if test="${patient.diagnosis == 'sadness'}">
                        <option selected>sadness</option>
                    </c:if>
                    <c:if test="${patient.diagnosis == 'stress'}">
                        <option selected>stress</option>
                    </c:if>
                    <c:if test="${patient.diagnosis == 'depression'}">
                        <option selected>depression</option>
                    </c:if>
                    <c:if test="${patient.diagnosis == 'weakness'}">
                        <option selected>weakness</option>
                    </c:if>
                    <c:if test="${patient.diagnosis == 'boredom'}">
                        <option selected>boredom</option>
                    </c:if>
                    <c:if test="${patient.diagnosis == 'insomnia'}">
                        <option selected>insomnia</option>
                    </c:if>
                    <c:if test="${patient.diagnosis == 'nervousness'}">
                        <option selected>nervousness</option>
                    </c:if>
                </select>
            </c:if>
        </div>

        <c:if test="${empty patient.id}">
            <form:form action="${pageContext.request.contextPath}/add" method="post">
                <button class="btn btn-primary" type="submit">ADD</button>
            </form:form>
        </c:if>
        <c:if test="${!empty patient.id}">
            <form:form action="${pageContext.request.contextPath}/edit" method="post">
                <button class="btn btn-primary" type="submit">EDIT</button>
            </form:form>
        </c:if>
    </form>

    <footer class="mastfoot mt-auto">
        <div class="inner">
            <p>Chandra Clinic &copy; 2020 by Konstantin Senko</p>
        </div>
    </footer>

</div>
</body>
</html>
