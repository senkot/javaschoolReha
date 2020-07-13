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
    <style>
        table, th, td {
            border: 1px solid black;
            margin: 3px;
        }
    </style>
</head>
<body>
    <div>
        <h2>The List of Patients</h2>
    </div>

    <div>
        <button style="margin: 3px" onclick="location.href='<c:url value="/patient-form"/>'">ADD</button>
    </div>

    <div>
        <table>
            <tr>
                <th>Last Name</th>
                <th>Name</th>
                <th>Second Name</th>
                <th>Date of Birth</th>
                <th>Number of Insurance</th>
                <th>Additional Insurance</th>
                <th>Show</th>
                <th>Edit</th>
            </tr>

            <c:forEach var="patient" items="${patients}">
                <tr>
                    <td><c:out value="${patient.lastName}" /></td>
                    <td><c:out value="${patient.firstName}" /></td>
                    <td><c:out value="${patient.secondName}" /></td>
                    <td><c:out value="${patient.dateOfBirth}" /></td>
                    <td><c:out value="${patient.insurance}" /></td>
                    <td><c:out value="${patient.additionalInsurance}" />Insert check-mark with condition true/false</td>
                    <td>
                        <a href='<c:url value="/patient?id=${patient.id}" />'>SHOW</a>
                    </td>
                    <td>
                        <a href='<c:url value="/edit?id=${patient.id}" />'>EDIT</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <div>
            <button style="margin: 3px" onclick="location.href='<c:url value="/" />' ">Back to List of Patients</button>
        </div>

    </div>
</body>
</html>
