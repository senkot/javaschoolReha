<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>
<body>
<h2>The Patient:
    <c:if test="${patient.lastName != null}">
        <c:out value="${patient.lastName}"/>
    </c:if>

    <c:if test="${patient.firstName != null}">
        <c:out value="${patient.firstName}"/>
    </c:if>
</h2>
<button onclick="location.href='<c:url value="/edit?id=${patient.id}" />'">EDIT</button>
<table>
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

</table>

<div>
    <button style="margin: 3px" onclick="location.href='<c:url value="/patient-list" />' ">Back to List of Patients</button>
</div>

</body>
</html>
