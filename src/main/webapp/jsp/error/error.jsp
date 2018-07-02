<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.login" var="login"/>
<fmt:message bundle="${loc}" key="local.signup" var="signup"/>
<fmt:message bundle="${loc}" key="local.signupLogin" var="signupLogin"/>
<!DOCTYPE html>
<html lang="en">
  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>QuestFire</title>

    <link href="../../vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,700,300italic,400italic,700italic" rel="stylesheet" type="text/css">
    <link href="../../vendor/simple-line-icons/css/simple-line-icons.css" rel="stylesheet">
   	<link rel="icon" type="image/png" href="../../static/img/icons/favicon.ico"/>
   	<link rel="stylesheet" type="text/css" href="../../vendor/font-awesome/css/font-awesome.min.css">
   	<link rel="stylesheet" type="text/css" href="../../vendor/fonts/Linearicons-Free-v1.0.0/icon-font.min.css">
   	<link rel="stylesheet" type="text/css" href="../../vendor/fonts/iconic/css/material-design-iconic-font.min.css">
   	<link rel="stylesheet" type="text/css" href="../../vendor/animate/animate.css">
   	<link rel="stylesheet" type="text/css" href="../../vendor/css-hamburgers/hamburgers.min.css">
   	<link rel="stylesheet" type="text/css" href="../../vendor/animsition/css/animsition.min.css">
   	<link rel="stylesheet" type="text/css" href="../../vendor/select2/select2.min.css">
   	<link rel="stylesheet" type="text/css" href="../../vendor/daterangepicker/daterangepicker.css">
   	<link rel="stylesheet" type="text/css" href="../../styles/util.css">
   	<link rel="stylesheet" type="text/css" href="../../styles/main.css">
      <link rel="stylesheet" href="../../styles/stylish-portfolio.min.css">
      <link rel="stylesheet" type="text/css" href="../../../styles/error.css">
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
      <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
      <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
  </head>
  <body id="page-top">
    <a class="menu-toggle rounded" href="#">
      <i class="fa fa-bars"></i>
    </a>
    <nav id="sidebar-wrapper">
      <ul class="sidebar-nav">
        <li class="sidebar-brand">
          <a class="js-scroll-trigger" href="#page-top">Start Bootstrap</a>
        </li>
        <li class="sidebar-nav-item">
          <a class="js-scroll-trigger" href="#page-top">Home</a>
        </li>
        <li class="sidebar-nav-item">
          <a class="js-scroll-trigger" href="#about">About</a>
        </li>
        <li class="sidebar-nav-item">
          <a class="js-scroll-trigger" href="#services">Services</a>
        </li>

         <c:if test="${empty user.email}">
        <li class="sidebar-nav-item">
          <a class="js-scroll-trigger" href="/login">Sign In</a>
          <a class="js-scroll-trigger" href="/register">Sign Up</a>
        </li>
        </c:if>

        <li class="sidebar-nav-item">
          <a class="js-scroll-trigger" href="#contact">Contact</a>
        </li>
      </ul>
    </nav>

   <section id="not-found">
     <div id="title">Simple Pure CSS3 &bull; 404 Error Page</div>
     <div class="circles">
       <p><c:out value="${requestScope.error}"/><br> </p>
       <span class="circle big"></span>
       <span class="circle med"></span>
       <span class="circle small"></span>
     </div>
   </section>
   <script src="../../js/jquery.easing.min.js"></script>
   <script src="../../js/stylish-portfolio.min.js"></script>
   <script src="../../vendor/animsition/js/animsition.min.js"></script>
   <script src="../../vendor/bootstrap/js/popper.js"></script>
   <script src="../../vendor/select2/select2.min.js"></script>
   <script src="../../vendor/daterangepicker/moment.min.js"></script>
   <script src="../../vendor/daterangepicker/daterangepicker.js"></script>
   <script src="../../vendor/countdowntime/countdowntime.js"></script>
   <script src="../../js/main.js"></script>

    </body>
   </html>