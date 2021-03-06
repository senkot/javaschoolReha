<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: KOTLIS
  Date: 11.07.2020
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Patient</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

</head>
<body class="text-center">

<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">

    <header>
        <div style="background-color: #e6e6fe" id="my-header" class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3  border-bottom shadow-sm">
            <h5 class="my-0 mr-md-auto font-weight-normal">Patient:
                <c:if test="${patient.lastName != null}">
                    <c:out value="${patient.lastName}"/>
                </c:if>

                <c:if test="${patient.firstName != null}">
                    <c:out value="${patient.firstName}"/>
                </c:if>
            </h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <a class="p-2 text-dark" href="<c:url value="/edit?id=${patient.id}"/>">EDIT</a>
                <a class="p-2 text-dark" href="<c:url value="/prescription-list?id=${patient.id}" />">Prescriptions</a>
                <a class="p-2 text-dark" href="<c:url value="/patient-list" />">Back to Patient List</a>
            </nav>
            <a class="btn btn-outline-primary" href="<c:url value="/"/>">Back to Start Page</a>
        </div>
    </header>

    <div class="table-responsive container">
        <table class="table table-striped table-sm">
            <tr>
                <th></th>
                <th>INFO</th>
            </tr>
            <tr>
                <th>Last Name</th>
                <td><c:out value="${patient.lastName}"/></td>
            </tr>
            <tr>
                <th>Name</th>
                <td><c:out value="${patient.firstName}"/></td>
            </tr>
            <tr>
                <th>Second Name</th>

                <c:if test="${patient.secondName != null}">
                    <td><c:out value="${patient.secondName}"/></td>
                </c:if>

                <c:if test="${patient.secondName == null}">
                    <td>-none-</td>
                </c:if>

            </tr>
            <tr>
                <th>Date of Birth</th>
                <td><c:out value="${patient.dateOfBirth}"/></td>
            </tr>
            <tr>
                <th>Number of Insurance</th>
                <td><c:out value="${patient.insurance}"/></td>
            </tr>
            <tr>
                <th>Additional Insurance</th>
                <c:if test="${patient.additionalInsurance != null}">
                    <td><c:out value="${patient.additionalInsurance}"/></td>
                </c:if>
                <c:if test="${patient.additionalInsurance == null}">
                    <td>-none-</td>
                </c:if>
            </tr>
            <tr>
                <th>Attending Doctor</th>
                <td><c:out value="${patient.doctorName}"/></td>
            </tr>
            <tr>
                <th>Diagnosis</th>
                <td><c:out value="${patient.diagnosis}"/></td>
            </tr>
            <tr>
                <th>Status</th>
                <td><c:out value="${patient.state}"/></td>
            </tr>

        </table>
    </div>

    <form method="post">
        <div>
            <label class="w-100">Change the status :</label>

            <input type="hidden" name="patientId" value="<c:out value="${patient.id}"/>" >

            <div class="custom-control custom-radio">
                <c:if test="${patient.state == 'in'}">
                    <input type="radio" value="in" class="custom-control-input" id="in" name="state" checked>
                </c:if>
                <c:if test="${patient.state != 'in'}">
                    <input type="radio" value="in" class="custom-control-input" id="in" name="state">
                </c:if>
                <label class="custom-control-label" for="in">In</label>
            </div>
            <div class="custom-control custom-radio">
                <c:if test="${patient.state == 'discharged'}">
                    <input type="radio" value="discharged" class="custom-control-input" id="discharged" name="state" checked>
                </c:if>
                <c:if test="${patient.state != 'discharged'}">
                    <input type="radio" value="discharged" class="custom-control-input" id="discharged" name="state" onclick="alert('This choice will cancel all unfinished prescriptions and events!')">
                </c:if>
                <label class="custom-control-label" for="discharged">Discharged</label>
            </div>

                <form:form action="${pageContext.request.contextPath}/patient" method="post">
                    <button class="btn btn-primary" type="submit">CHANGE</button>
                </form:form>
        </div>
    </form>

    <footer class="mastfoot mt-auto">
        <div class="inner">
            <p>Chandra Clinic &copy; 2020 by Konstantin Senko</p>
        </div>
    </footer>

</div>
</body>
</html>
