<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html lang="en">
<head>

    <title>Chandra Clinic Login Page</title>

    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
    <link rel="canonical" href="https://getbootstrap.com/docs/4.0/examples/sign-in/">

    <link href="/css/signin.css" rel="stylesheet">
</head>

<body class="text-center">

    <form:form action="${pageContext.request.contextPath}/authenticateTheUser"
               method="POST" class="form-horizontal">

        <label>Please sign in</label>

        <div>
            <input type="text" name="username"  class="form-control" placeholder="Username" required="">
        </div>

        <div class="pt-3">
            <input type="password"  name="password"  class="form-control" placeholder="Password" required="">
        </div>

        <div>
            <button class="btn btn-lg btn-primary btn-block mt-5" type="submit">Sign in</button>
        </div>

        <c:if test="${param.error != null}">

            <div class="mt-5" style="color: tomato">
                Invalid username and password.
            </div>

        </c:if>

    </form:form>

</body>
</html>