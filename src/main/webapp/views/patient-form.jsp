<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: KOTLIS
  Date: 11.07.2020
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <c:if test="${empty patient.id}">
        <title>Add new Patient</title>
    </c:if>
    <c:if test="${!empty patient.id}">
        <title>Edit Patient</title>
    </c:if>
</head>
<body>
<div>
    <c:if test="${empty patient.id}">
        <h2>Add new Patient</h2>
    </c:if>
    <c:if test="${!empty patient.id}">
        <h2>Edit Patient</h2>
    </c:if>
</div>

<div>
    <div>
        <form method="post">
            <table>
                <tr>
                    <th></th>
                    <td>DATA</td>
                </tr>

                <tr>
                    <th>Surname : </th>
                    <c:if test="${empty patient.id}">
                        <td><input type="text" name="lastName"></td>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <td><input type="text" name="lastName" value="<c:out value="${patient.lastName}"/>"></td>
                    </c:if>
                </tr>

                <tr>
                    <th>Name : </th>
                    <c:if test="${empty patient.id}">
                        <td><label><input type="text" name="firstName"></label></td>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <td><label>
                            <input type="text" name="firstName" value="<c:out value="${patient.firstName}"/>">
                        </label></td>
                    </c:if>
                </tr>

                <tr>
                    <th>Second Name : </th>
                    <c:if test="${empty patient.id}">
                        <td><label>
                            <input type="text" name="secondName">
                        </label></td>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <td><label>
                            <input type="text" name="secondName" value="<c:out value="${patient.secondName}"/>">
                        </label></td>
                    </c:if>
                </tr>

                <tr>
                    <th>Date of birth : </th>
                    <c:if test="${empty patient.id}">
                        <td><label>
                            <input type="date" name="dateOfBirth">
                        </label></td>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <td><label>
                            <input type="date" name="dateOfBirth" value="<c:out value="${patient.dateOfBirth}"/>">
                        </label></td>
                    </c:if>
                </tr>

                <tr>
                    <th>Insurance : </th>
                    <c:if test="${empty patient.id}">
                        <td><label>
                            <input type="text" name="insurance">
                        </label></td>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <td><label>
                            <input type="text" name="insurance" value="<c:out value="${patient.insurance}"/>">
                        </label></td>
                    </c:if>
                </tr>

                <tr>
                    <th>Additional insurance : </th>
                    <c:if test="${empty patient.id}">
                        <td><label>
                            <input type="text" name="additionalInsurance">
                        </label></td>
                    </c:if>
                    <c:if test="${!empty patient.id}">
                        <td><label>
                            <input type="text" name="additionalInsurance" value="<c:out value="${patient.additionalInsurance}"/>">
                        </label></td>
                    </c:if>
                </tr>
            </table>
            <c:if test="${empty patient.id}">
                <button type="submit">ADD</button>
            </c:if>
            <c:if test="${!empty patient.id}">
                <button type="submit">EDIT</button>
            </c:if>
        </form>
    </div>
</div>

<div>
    <c:if test="${empty patient.id}">
        <button onclick="location.href='<c:url value="/patient-list"/>'">Back to Patient LIST</button>
    </c:if>
    <c:if test="${!empty patient.id}">
        <button onclick="location.href='<c:url value="/patient?id=${patient.id}"/>'">Back to Patient INFO</button>
    </c:if>
</div>
</body>
</html>
