<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Chandra Clinic</title>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
</head>
<body class="text-center">
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
    <header>
        <div style="background-color: #e6e6fe" id="my-header" class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3  border-bottom shadow-sm">
            <h5 class="my-0 mr-md-auto font-weight-normal">Chandra Clinic</h5>
            <sec:authorize access="hasAnyRole('DOCTOR', 'NURSE', 'ADMIN')">
                <p class="my-2 my-md-0 mr-md-3">Hello, <sec:authentication property="principal.username"/>!
                    You signed in as a <sec:authentication property="principal.authorities"/></p>
            </sec:authorize>
            <nav class="my-2 my-md-0 mr-md-3">
                <sec:authorize access="hasRole('DOCTOR')">
                    <a class="p-2 text-dark" href="<c:url value="/patient-list"/>">Patients</a>
                </sec:authorize>
                <sec:authorize access="hasRole('NURSE')">
                    <a class="p-2 text-dark" href="<c:url value="/event-list"/>">Events</a>
                </sec:authorize>
            </nav>
            <p class="my-2 my-md-0 mr-md-3">
                <sec:authorize access="hasAnyRole('DOCTOR', 'NURSE', 'ADMIN')">
                <form:form action="${pageContext.request.contextPath}/logout" method="post">
                    <input type="submit" class="btn btn-outline-primary" value="Logout" />
                </form:form>
                </sec:authorize>
                <sec:authorize access="!hasAnyRole('DOCTOR', 'NURSE', 'ADMIN')">
                    <a class="btn btn-outline-primary"  href='<c:url value="/login-page" />'>Login</a>
                </sec:authorize>
            </p>
        </div>
    </header>

    <main role="main" class="inner cover">
        <h1 class="cover-heading">Chandra Clinic Rehab Division System</h1>
        <p class="lead">Document management system of the Chandra clinic Rehab Resort</p>
        <p class="lead">
            <a class="btn btn-outline-primary"  href='<c:url value="/about" />'>More</a>
        </p>
    </main>

    <footer class="mastfoot mt-auto">
        <div class="inner">
            <p>Chandra Clinic &copy; 2020 by Konstantin Senko</p>
        </div>
    </footer>
</div>
</body>
</html>
