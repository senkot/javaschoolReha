<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html lang="en">
<head>

    <meta http-equiv="content-type" content="text/html; charset=UTF-8">

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">


    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">


    <link rel="canonical" href="https://getbootstrap.com/docs/4.0/examples/sign-in/">


    <!-- Custom styles for this template -->
    <link href="css/signin.css" rel="stylesheet">
</head>

<body class="text-center">

<form:form action="${pageContext.request.contextPath}/authenticateTheUser"
           method="POST" class="form-horizontal">

    <form class="form-signin">

        <div>
            <input type="text" name="username" id="inputEmail" class="form-control" placeholder="Username" required="" autofocus=""
        </div>

        <div>
            <input type="password"  name="password" id="inputPassword" class="form-control" placeholder="Password" required="">
        </div>

        <div>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
        </div>
    </form>

</form:form>

</body></html>