<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sign In</title>
    <style>
        #my-header {background-color: #e6e6fe}
    </style>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
</head>
<body class="text-center">
<div class="cover-container d-flex w-100 h-100 p-3 mx-auto flex-column">
    <header>
        <div id="my-header" class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3  border-bottom shadow-sm">
            <h5 class="my-0 mr-md-auto font-weight-normal">Chandra Clinic</h5>
            <a class="btn btn-outline-primary" href="/">Back to Start Page</a>
        </div>
    </header>

    <main role="main" class="inner cover">
        <h1 class="cover-heading">Login</h1>

            <form method="post">
                <div class="form-group">
                    <label for="name">Name: </label>
                    <input id="name" name="name" type="text">
                </div>
                <div class="form-group">
                    <label for="password">Password: </label>
                    <input id="password" name="password" type="password">
                </div>
                <input type="submit">
            </form>
            <c:if test="${error == true}">
                <p>Bad credentials!</p>
            </c:if>
    </main>

    <footer class="mastfoot mt-auto">
        <div class="inner">
            <p>Chandra Clinic &copy; 2020 by Konstantin Senko</p>
        </div>
    </footer>
</div>
</body>
</html>
