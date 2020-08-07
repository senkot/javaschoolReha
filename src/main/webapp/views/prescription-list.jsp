<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Prescription List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <style>
        #my-header {background-color: #e6e6fe}
    </style>
</head>
<body class="text-center">
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
    <header>
        <div id="my-header" class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3  border-bottom shadow-sm">
            <h5 class="my-0 mr-md-auto font-weight-normal">Prescription List</h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <a class="p-2 text-dark" href="<c:url value="/add-prescription?id=${patient.id}"/>">Add new Prescription</a>
                <a class="p-2 text-dark" href="<c:url value="/patient?id=${patient.id}"/>">Back to The Patient</a>
            </nav>
            <a class="btn btn-outline-primary" href="/">Back to Authorization Page</a>
        </div>
    </header>

    <div class="table-responsive container">
        <table class="table table-bordered table-hover">
            <tr class="table-active">
                <th>Remedy</th>
                <th>Type</th>
                <th>Date of Start</th>
                <th>Date of End</th>
                <th>Quantity</th>
                <th>Status</th>
                <th>Edit</th>
            </tr>

            <c:forEach var="prescription" items="${prescriptions}">
                <tr>
                    <td>
                        <a href='<c:url value="/prescription-show?id=${prescription.id}" />'>
                        <c:out value="${prescription.remedyName}" />
                        </a>
                    </td>
                    <td><c:out value="${prescription.remedyType}" /></td>
                    <td><c:out value="${prescription.dateStart}" /></td>
                    <td><c:out value="${prescription.dateEnd}" /></td>
                    <td><c:out value="${prescription.quantity}"/></td>
                    <td>
                        <a href='<c:url value="/prescription?id=${prescription.id}" />'>
                            <c:out value="${prescription.status}"/>
                        </a>
                    </td>
                    <td>
                        <a href='<c:url value="/edit-prescription?id=${prescription.id}" />'>EDIT</a>
                    </td>
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
