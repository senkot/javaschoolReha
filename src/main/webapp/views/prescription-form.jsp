<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <c:if test="${empty prescription.id}">
        <title>Add new Prescription</title>
    </c:if>
    <c:if test="${!empty prescription.id}">
        <title>Edit Prescription</title>
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

            <a class="btn btn-outline-primary" href="<c:url value='/'/>">Back to Start Page</a>
        </div>
    </header>

    <form method="post">

        <div class="form-group">

            <c:if test="${!empty collisions}">
                <div>
                <ul>There is some collisions in the dates :
                <c:forEach items="${collisions}" var="collision">
                    <li><c:out value="${collision}" /></li>
                </c:forEach>
                </ul>
                </div>
            </c:if>

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
                <select  class="form-control" id="remedyType" name="remedyType"  onchange="Selected(this)">
                    <option>pill</option>
                    <option>procedure</option>
                </select>
            </c:if>
            <c:if test="${!empty prescription.id}">
                <select class="form-control" id="remedyType" name="remedyType" onchange="Selected(this)">
                    <c:if test="${prescription.remedyType == 'pill'}">
                        <option selected>pill</option>
                        <option>procedure</option>
                    </c:if>
                    <c:if test="${prescription.remedyType == 'procedure'}">
                        <option>pill</option>
                        <option selected>procedure</option>
                    </c:if>
                </select>
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
            <label class="w-100">Weekday for repeat</label>
            <div class="custom-control custom-checkbox custom-control-inline">
                <input type="checkbox" class="custom-control-input" id="monday" name="monday">
                <label class="custom-control-label" for="monday">Monday</label>
            </div>
            <div class="custom-control custom-checkbox custom-control-inline">
                <input type="checkbox" class="custom-control-input" id="tuesday" name="tuesday">
                <label class="custom-control-label" for="tuesday">Tuesday</label>
            </div>
            <div class="custom-control custom-checkbox custom-control-inline">
                <input type="checkbox" class="custom-control-input" id="wednesday" name="wednesday">
                <label class="custom-control-label" for="wednesday">Wednesday</label>
            </div>
            <div class="custom-control custom-checkbox custom-control-inline">
                <input type="checkbox" class="custom-control-input" id="thursday" name="thursday">
                <label class="custom-control-label" for="thursday">Thursday</label>
            </div>
            <div class="custom-control custom-checkbox custom-control-inline">
                <input type="checkbox" class="custom-control-input" id="friday" name="friday">
                <label class="custom-control-label" for="friday">Friday</label>
            </div>
            <div class="custom-control custom-checkbox custom-control-inline">
                <input type="checkbox" class="custom-control-input" id="saturday" name="saturday">
                <label class="custom-control-label" for="saturday">Saturday</label>
            </div>
            <div class="custom-control custom-checkbox custom-control-inline">
                <input type="checkbox" class="custom-control-input" id="sunday" name="sunday">
                <label class="custom-control-label" for="sunday">Sunday</label>
            </div>
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

        <div class="form-group">
            <label class="w-100">Time reception</label>
            <div class="custom-control custom-checkbox custom-control-inline">
                <input type="checkbox" class="custom-control-input" id="morning" name="morning">
                <label class="custom-control-label" for="morning">Morning</label>
            </div>
            <div class="custom-control custom-checkbox custom-control-inline">
                <input type="checkbox" class="custom-control-input" id="afternoon" name="afternoon">
                <label class="custom-control-label" for="afternoon">Afternoon</label>
            </div>
            <div class="custom-control custom-checkbox custom-control-inline">
                <input type="checkbox" class="custom-control-input" id="evening" name="evening">
                <label class="custom-control-label" for="evening">Evening</label>
            </div>
            <div class="custom-control custom-checkbox custom-control-inline">
                <input type="checkbox" class="custom-control-input" id="night" name="night">
                <label class="custom-control-label" for="night">Night</label>
            </div>
        </div>

        <c:if test="${empty prescription.id}">
            <form:form action="${pageContext.request.contextPath}/add-prescription" method="post">
                <button class="btn btn-primary" type="submit">ADD</button>
            </form:form>
        </c:if>
        <c:if test="${!empty prescription.id}">
            <form:form action="${pageContext.request.contextPath}/edit-prescription" method="post">
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

<script>
    function Selected(a) {
        var label = a.value;
        if (label=="procedure") {
            document.getElementById("quantity").value='1';
            document.getElementById("quantity").readOnly=true;
        } else {
            document.getElementById("quantity").readOnly=false;
        }
    }
</script>

</body>
</html>
