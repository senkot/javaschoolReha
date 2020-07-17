<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: KOTLIS
  Date: 11.07.2020
  Time: 13:46
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <title>Patient List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <style>
        #my-header {background-color: #e6e6fe}
    </style>
</head>
<body class="text-center">
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
        <header>
            <div id="my-header" class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3  border-bottom shadow-sm">
                <h5 class="my-0 mr-md-auto font-weight-normal">Charite Clinic</h5>
                <nav class="my-2 my-md-0 mr-md-3">
                    <a class="p-2 text-dark" href="<c:url value="/add"/>">Add new Patient</a>
                </nav>
                <a class="btn btn-outline-primary" href="/">Back to Authorization Page</a>
            </div>
        </header>

        <div class="table-responsive container">
            <table class="table table-bordered table-hover">
                <tr class="table-active">
                    <th>Last Name</th>
                    <th>Name</th>
                    <th>Second Name</th>
                    <th>Date of Birth</th>
                    <th>Number of Insurance</th>
                    <th>Additional Insurance</th>
                    <th>Show</th>
                </tr>

                <c:forEach var="patient" items="${patients}">
                    <tr>
                        <td><c:out value="${patient.lastName}" /></td>
                        <td><c:out value="${patient.firstName}" /></td>
                        <td><c:out value="${patient.secondName}" /></td>
                        <td><c:out value="${patient.dateOfBirth}" /></td>
                        <td><c:out value="${patient.insurance}" /></td>
                        <c:if test="${patient.haveAdditionalInsurance() == true}" >
                            <td style="text-align: center">&#9745;</td>
                        </c:if>
                        <c:if test="${patient.haveAdditionalInsurance() == false}" >
                            <td style="text-align: center">&#9746;</td>
                        </c:if>
                        <td>
                            <a href='<c:url value="/patient?id=${patient.id}" />'>SHOW</a>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>

    <footer class="mastfoot mt-auto">
        <div class="inner">
            <p>Charite Clinic Information System. created by Konstantin Senko <a href="mailto:senko.kostya@ya.ru">SEND</a> a mail</p>
        </div>
    </footer>

</div>
</body>
</html>
