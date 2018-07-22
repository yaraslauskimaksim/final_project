<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.login" var="login"/>
<fmt:message bundle="${loc}" key="local.signup" var="signup"/>



<!doctype html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Quest Fire</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css">
    <link rel="stylesheet" href="../vendor/font-awesome/css/font-awesome.min.css">

    <!-- Custom styles for this template -->
    <link href="../../styles/album.css" rel="stylesheet">
    <link href="../../styles/form.css" rel="stylesheet">

</head>

  <body>
 <header>
      <div class="collapse bg-dark" id="navbarHeader">
        <div class="container">
          <div class="row">
            <div class="col-sm-8 col-md-7 py-4">
              <h4 class="text-white">About</h4>
              <p class="text-muted">Add some information about the album below, the author, or any other background context. Make it a few sentences long so folks can pick up some informative tidbits. Then, link them off to some social networking sites or contact information.</p>
            </div>
            <div class="col-sm-4 offset-md-1 py-4">
            <c:if test="${not empty user.email}">
            <h4 class="text-white"><c:out value="${user.email}"/></h4>
             <a class="text-white" href="${_contextPath}/frontController?command=logout">Logout</a>
           </c:if>


              <ul class="navbar-nav mr-auto">
                 <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="http://example.com" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Languages</a>
                    <div class="dropdown-menu" aria-labelledby="dropdown01">
                       <a class="dropdown-item" href="${_contextPath}/frontController?command=local&lang=ru">RU</a>
                       <a class="dropdown-item" href="${_contextPath}/frontController?command=local&lang=en">EN</a>
                    </div>
                 </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
      <div class="navbar navbar-dark bg-dark box-shadow">
        <div class="container d-flex justify-content-between">
         <a href="/user/frozen" class="navbar-brand d-flex align-items-center">
            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDdee8Y-JW5FV4-h1U-lOFYvGXDUuhFHY5THAEAeoibanI49rBww" width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-2"/>
            <strong>QuestFire</strong>
          </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
          </button>
        </div>
      </div>
    </header>