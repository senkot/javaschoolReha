<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: KOTLIS
  Date: 11.07.2020
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <c:if test="${empty patient.id}">
        <title>Add new Patient</title>
    </c:if>
    <c:if test="${!empty patient.id}">
        <title>Edit Patient</title>
    </c:if>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

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

            <a class="btn btn-outline-primary" href="<c:url value='/'/>">Back to Authorization Page</a>
        </div>
    </header>

    <form method="post">

        <div class="form-group">
            <label for="lastName">Surname</label>
            <c:if test="${empty patient.id}">
                <input type="text" id="lastName" name="lastName" class="form-control" placeholder="Enter surname">
            </c:if>
            <c:if test="${!empty patient.id}">
                <input type="text" id="lastName" name="lastName" class="form-control" value="<c:out value="${patient.lastName}"/>" >
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

        <c:if test="${empty patient.id}">
            <button class="btn btn-primary" type="submit">ADD</button>
        </c:if>
        <c:if test="${!empty patient.id}">
            <button class="btn btn-primary" type="submit">EDIT</button>
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
