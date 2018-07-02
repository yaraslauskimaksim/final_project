<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "part/header.jsp" %>
  <body class="text-center">
    <form class="form-signin" action="frontController" method="POST" style="width:30%; margin:30px auto;">
    <input type="hidden" name="command" value="login"/>
      <img class="mb-4" src="https://getbootstrap.com/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
      <h1 class="h3 mb-3 font-weight-normal">Please Login</h1>
      <label for="inputEmail" class="sr-only">Email address</label>
      <input type="email" name="email" class="form-control" placeholder="Email address" required autofocus>
      <label for="inputPassword" class="sr-only" >Password</label>
      <input type="password" class="form-control" name="password" placeholder="Password" required>
      <div class="checkbox mb-3">
        <label>
          <input type="checkbox" value="remember-me" style="margin-top:30px auto;"> Remember me
        </label>
      </div>
      <button class="btn btn-danger" type="submit">Log in</button>
      <p class="mt-5 mb-3 text-muted" style="margin-top:30px auto;">&copy; 2017-2018</p>
       <a href="/">Go Back</a>
    </form>
  </body>
</html>

