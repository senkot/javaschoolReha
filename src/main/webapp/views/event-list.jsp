<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Event List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

    <script src="js/jquery-3.5.1.min.js" type="text/javascript"></script>
    <script src="/js/jquery.tablesorter.js" type="text/javascript"></script>
</head>
<body class="text-center">
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
    <header>
        <div style="background-color: #e6e6fe" id="my-header" class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3  border-bottom shadow-sm">
            <h5 class="my-0 mr-md-auto font-weight-normal">Event List</h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <sec:authorize access="hasRole('DOCTOR')">
                    <a class="p-2 text-dark" href="<c:url value="/patient-list"/>">Back to Patient List</a>
                </sec:authorize>
            </nav>
            <a class="btn btn-outline-primary" href="/">Back to Srart Page</a>
        </div>
    </header>

    <div class="table-responsive container">
        <form method="post">
            <div class="form-group">
                <label for="patients">Patient</label>
                <select class="form-control" id="patients" name="patientId">
                    <option value="0">All</option>
                    <c:forEach var="patient" items="${patients}">
                        <option value="${patient.id}"
                        <c:if test="${filter.patientId == patient.id}"> selected
                        </c:if>
                        ><c:out value="${patient.lastName}"/> <c:out value="${patient.firstName}"/></option>
                    </c:forEach>
                </select>
            </div>
            <div class="form-group">
                <label for="dateToFilter">Date</label>
                <fmt:formatDate value="${filter.dateToFilter}" type="date" var="datka" pattern="YYYY-MM-DD" />
                <input class="form-control" pattern="dd.MM.yyyy" type="date" id="dateToFilter" name="dateToFilter" value="${datka}">
            </div>
            <div class="form-group">
                <label for="dayTime">Time of the day</label>
                <select class="form-control" id="dayTime" name="dayTime">
                    <option value="all"<c:if test="${filter.dayTime == 'all'}"> selected
                    </c:if>>All</option>
                    <option value="morning"<c:if test="${filter.dayTime == 'morning'}"> selected
                    </c:if>>Morning</option>
                    <option value="afternoon"<c:if test="${filter.dayTime == 'afternoon'}"> selected
                    </c:if>>Afternoon</option>
                    <option value="evening"<c:if test="${filter.dayTime == 'evening'}"> selected
                    </c:if>>Evening</option>
                    <option value="night"<c:if test="${filter.dayTime == 'night'}"> selected
                    </c:if>>Night</option>
                </select>
            </div>
            <div class="form-group">
                <label for="status">Event status</label>
                <select class="form-control" id="status" name="status">
                    <option value="all"<c:if test="${filter.status == 'all'}"> selected
                    </c:if>>All</option>
                    <option value="planed"<c:if test="${filter.status == 'planed'}"> selected
                    </c:if>>Planed</option>
                    <option value="done"<c:if test="${filter.status == 'done'}"> selected
                    </c:if>>Done</option>
                    <option value="canceled"<c:if test="${filter.status == 'canceled'}"> selected
                    </c:if>>Canceled</option>
                </select>
            </div>

            <form:form action="${pageContext.request.contextPath}/event-list" method="post">
                <button class="btn btn-primary" type="submit">FILTER</button>
            </form:form>
        </form>


        <table  class="table table-bordered table-hover" id="user_list">
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
                    <td style="text-align: center"><c:out value="${event.quantity}"/></td>
                    <td>
                        <a href="<c:url value="/event?id=${event.id}"/>">
                            <c:out value="${event.status}"/>
                        </a>
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

<script>

    function setDayToday() {
        document.getElementById("dateToFilter").valueAsDate = new Date();
    }

    setDayToday();

    $(document).ready(function() {
        $("#user_list").tablesorter();
    });

</script>

</body>
</html>
