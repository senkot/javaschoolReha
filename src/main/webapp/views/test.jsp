<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Test</title>
</head>
<body>
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
            <td><c:out value="${event.patient}" /></td>
            <td><c:out value="${event.remedy}" /></td>
            <td><c:out value="${event.type}" /></td>
            <td style="text-align: center"><c:out value="${event.quantity}"/></td>
            <td><c:out value="${event.status}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
