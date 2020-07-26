<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Event List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <style>
        #my-header {background-color: #e6e6fe}
    </style>
</head>
<body class="text-center">
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
    <header>
        <div id="my-header" class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3  border-bottom shadow-sm">
            <h5 class="my-0 mr-md-auto font-weight-normal">Event List</h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <%-- прописать роли входа и от этого условие появления кнопки перехода --%>

                <a class="p-2 text-dark" href="<c:url value="/patient-list"/>">Back to Patient List</a>
            </nav>
            <a class="btn btn-outline-primary" href="/">Back to Authorization Page</a>
        </div>
    </header>

    <div class="table-responsive container">
        <table class="table table-bordered table-hover">
            <tr class="table-active">
                <th>Date</th>
                <th>Time</th>
                <th>Patient</th>
                <th>Remedy</th>
                <th>Type</th>
                <th>Quantity</th>
                <th>Status</th>
            </tr>

            <c:forEach var="event" items="${events}">
                <tr>
                    <td><c:out value="${event.date}" /></td>
                    <td><c:out value="${event.time}" /></td>
                    <td><c:out value="${event.prescription.patient.lastName} ${event.prescription.patient.firstName}" /></td>
                    <td><c:out value="${event.remedyName}" /></td>
                    <td><c:out value="${event.remedyType}" /></td>
                    <c:if test="${event.quantity != 0}" >
                        <td style="text-align: center"><c:out value="${event.quantity}"/></td>
                    </c:if>
                    <c:if test="${prescription.quantity == 0}" >
                        <td style="text-align: center">-</td>
                    </c:if>
                    <td><c:out value="${event.status}" /></td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <footer class="mastfoot mt-auto">
        <div class="inner">
            <p>Chandra Clinic &copy; 2020 by Konstantin Senko</p>
        </div>
    </footer>

</div>
</body>
</html>
