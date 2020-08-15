<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Event</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body class="text-center">

<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">

    <header>
        <div style="background-color: #e6e6fe" class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3  border-bottom shadow-sm">
            <h5 class="my-0 mr-md-auto font-weight-normal">Event</h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <sec:authorize access="hasRole('NURSE')">
                    <a class="p-2 text-dark" href="<c:url value="/event-list" />">Back to Event List</a>
                </sec:authorize>
                <sec:authorize access="hasRole('DOCTOR')">
                    <a class="p-2 text-dark" href="<c:url value="/prescription-show?id=${event.prescription.id}" />">Back to Prescription</a>
                </sec:authorize>
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
                <th>Date - Time</th>
                <td><c:out value="${event.date}"/> - <c:out value="${event.time}"/></td>
            </tr>
            <tr>
                <th>Remedy</th>
                <td><c:out value="${event.remedyName}"/></td>
            </tr>
            <tr>
                <th>Type</th>
                <td><c:out value="${event.remedyType}"/></td>
            </tr>
            <tr>
                <th>Quantity</th>
                <td><c:out value="${event.quantity}"/></td>
            </tr>
            <tr>
                <th>Status</th>
                <td><c:out value="${event.status}"/></td>
            </tr>
        </table>
    </div>

    <form method="post">
        <div>
            <label class="w-100">Change the status :</label>

            <input type="hidden" name="eventId" value="<c:out value="${event.id}"/>" >

            <div class="custom-control custom-radio">
                <c:if test="${event.status == 'planed'}">
                    <input type="radio" value="planed" class="custom-control-input" id="planed" name="status" onChange="Selected(this)" checked>
                </c:if>
                <c:if test="${event.status != 'planed'}">
                    <input type="radio" value="planed" class="custom-control-input" id="planed" name="status" onChange="Selected(this)" disabled>
                </c:if>
                <label class="custom-control-label" for="planed">Planed</label>
            </div>
            <div class="custom-control custom-radio">
                <c:if test="${event.status == 'done'}">
                    <input type="radio" value="done" class="custom-control-input" id="done" name="status" onChange="Selected(this)" checked>
                </c:if>
                <c:if test="${event.status == 'planed'}">
                    <input type="radio" value="done" class="custom-control-input" id="done" name="status" onChange="Selected(this)">
                </c:if>
                <c:if test="${event.status == 'canceled'}">
                    <input type="radio" value="done" class="custom-control-input" id="done" name="status" onChange="Selected(this)" disabled>
                </c:if>
                <label class="custom-control-label" for="done">Done</label>
            </div>
            <div class="custom-control custom-radio">
                <c:if test="${event.status == 'planed'}">
                    <input type="radio" value="canceled" class="custom-control-input" id="canceled" name="status" onChange="Selected(this)">
                </c:if>
                <c:if test="${event.status == 'done'}">
                    <input type="radio" value="canceled" class="custom-control-input" id="canceled" name="status" onChange="Selected(this)" disabled>
                </c:if>
                <c:if test="${event.status == 'canceled'}">
                    <input type="radio" value="canceled" class="custom-control-input" id="canceled" name="status" onChange="Selected(this)" checked>
                    <p id="cause">The cause is : <c:out value="${event.cause}"/></p>
                </c:if>
                <label class="custom-control-label" for="canceled">Canceled</label>
            </div>
            <div id='Block1' style='display: none;'>
                <label for="cause">Cause</label>
                <c:if test="${!empty event.cause}"> <p id="cause">value="<c:out value="${event.cause}"/>"</p> </c:if>
                <c:if test="${empty event.cause}"> <input id="causeInput" type="text" name="cause"></c:if>

            </div>

            <c:if test="${event.status == 'planed'}">
                <form:form action="${pageContext.request.contextPath}/event" method="post">
                    <button class="btn btn-primary" type="submit">EDIT</button>
                </form:form>
            </c:if>
        </div>
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
        if (label=="canceled") {
            document.getElementById("Block1").style.display='block';
            document.getElementById("causeInput").required=true;
        } else {
            document.getElementById("Block1").style.display='none';
            document.getElementById("causeInput").required=false;
        }
    }
</script>

</body>
</html>
