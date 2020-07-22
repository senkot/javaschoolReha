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
    <c:if test="${empty prescription.id}">
        <title>Add new Patient</title>
    </c:if>
    <c:if test="${!empty prescription.id}">
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
            <c:if test="${empty prescription.id}">
                <h5 class="my-0 mr-md-auto font-weight-normal">Add New Prescription for Patient <c:out value="${patient.lastName}"/> <c:out value="${patient.firstName}"/></h5>
            </c:if>
            <c:if test="${!empty prescription.id}">
                <h5 class="my-0 mr-md-auto font-weight-normal">Edit Prescription </h5>
            </c:if>

            <nav class="my-2 my-md-0 mr-md-3">
                <c:if test="${empty prescription.id}">
                    <a class="p-2 text-dark" href="<c:url value="/prescription-list?id=${patient.id}"/>">Back to Prescription LIST</a>
                </c:if>
                <c:if test="${!empty prescription.id}">
                    <a class="p-2 text-dark" href="<c:url value="/prescription?id=${prescription.id}"/>">Back to Prescription</a>
                </c:if>
            </nav>

            <a class="btn btn-outline-primary" href="<c:url value='/'/>">Back to Authorization Page</a>
        </div>
    </header>

    <form method="post">

        <div class="form-group">


            <c:if test="${!empty prescription.id}">
                <input type="hidden" name="prescriptionId" class="form-control" value="<c:out value="${prescription.id}"/>" >
            </c:if>

            <input type="hidden" name="patientId" class="form-control" value="${patient.id}">


            <label for="remedyName">Remedy</label>
            <c:if test="${empty prescription.id}">
                <input type="text" id="remedyName" name="remedyName" class="form-control" placeholder="Enter name of remedy">
            </c:if>
            <c:if test="${!empty prescription.id}">
                <input type="text" id="remedyName" name="remedyName" class="form-control" value="<c:out value="${prescription.remedyName}"/>" >
            </c:if>
        </div>

        <div class="form-group">
            <label for="remedyType">Type of remedy</label>
            <c:if test="${empty prescription.id}">
                <input type="text" id="remedyType" name="remedyType" class="form-control" placeholder="Choose type of remedy">
            </c:if>
            <c:if test="${!empty prescription.id}">
                <input type="text" id="remedyType" name="remedyType" class="form-control" value="<c:out value="${prescription.remedyType}"/>">
            </c:if>
        </div>

        <div class="form-group">
            <label for="dateOfStart">Date of start</label>
            <c:if test="${empty prescription.id}">
                <input type="date" id="dateOfStart" name="dateOfStart" class="form-control">
            </c:if>
            <c:if test="${!empty prescription.id}">
                <input type="date" id="dateOfStart" name="dateOfStart" class="form-control" value="<c:out value="${prescription.dateStart}"/>">
            </c:if>
        </div>

        <div class="form-group">
            <label for="dateOfEnd">Date of end</label>
            <c:if test="${empty prescription.id}">
                <input type="date" id="dateOfEnd" name="dateOfEnd" class="form-control">
            </c:if>
            <c:if test="${!empty prescription.id}">
                <input type="date" id="dateOfEnd" name="dateOfEnd" class="form-control" value="<c:out value="${prescription.dateEnd}"/>">
            </c:if>
        </div>

        <div class="form-group">
            <label for="iteration">Repeat</label>
            <c:if test="${empty prescription.id}">
                <input type="text" id="iteration" name="iteration" class="form-control" placeholder="Choose days for repeat remedy">
            </c:if>
            <c:if test="${!empty prescription.id}">
                <input type="text" id="iteration" name="iteration" class="form-control" value="<c:out value="${prescription.iteration}"/>">
            </c:if>
        </div>

        <div class="form-group">
            <label for="quantity">Quantity</label>
            <c:if test="${empty prescription.id}">
                <input type="number" id="quantity" name="quantity" class="form-control" placeholder="Enter the quantity for single use">
            </c:if>
            <c:if test="${!empty prescription.id}">
                <input type="number" id="quantity" name="quantity" class="form-control" value="<c:out value="${prescription.quantity}"/>">
            </c:if>
        </div>

        <c:if test="${empty prescription.id}">
            <button class="btn btn-primary" type="submit">ADD</button>
        </c:if>
        <c:if test="${!empty prescription.id}">
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
