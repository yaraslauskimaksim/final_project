<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="local" var="loc"/>
<fmt:message bundle="${loc}" key="local.login" var="login"/>
<fmt:message bundle="${loc}" key="local.signup" var="signup"/>


<!DOCTYPE html>
<html>
     <head>
     <title>QuestFire</title>
     <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
     <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
    </head>
     <body>
         <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">

           <a class="navbar-brand" href="#">Quest Fire</a>
           <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExampleDefault" aria-controls="navbarsExampleDefault" aria-expanded="false" aria-label="Toggle navigation">
             <span class="navbar-toggler-icon"></span>
           </button>

           <div class="collapse navbar-collapse" id="navbarsExampleDefault">
             <ul class="navbar-nav mr-auto">
               <li class="nav-item active">
                 <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
               </li>
               <li class="nav-item">
                 <a class="nav-link" href="#">Link</a>
               </li>
               <li class="nav-item">
                 <a class="nav-link disabled" href="#">Disabled</a>
               </li>
                        <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Rating</a>
                    <div class="dropdown-menu" aria-labelledby="dropdown01">
                      <a class="dropdown-item" href="#">Quest Rating</a>
                      <a class="dropdown-item" href="#">Top Players</a>
                    </div>
                  </li>
             </ul>

                              <ul class="nav navbar-nav navbar-right">
                              <a class="btn btn-outline-danger" href="frontController?command=local&adr=${pageContext.request.requestURI}&lang=ru">Рус</a>
                              <a class="btn btn-outline-danger" href="frontController?command=local&adr=${pageContext.request.requestURI}&lang=en">En</a>
                              </ul>

                               <ul class="nav navbar-nav navbar-right">
                                <c:choose>
                                  <c:when test="${empty user.email}">
                                  <li><a  href="/login">${login}</a></li>
                                  <li><a href="/register">${signup}</a></li>
                                  </c:when>
                                  <c:when test="${not empty user.email}">
                                  <c:out value="${user.email}"/>
                                  <a class="btn btn-outline-danger" href="frontController?command=logout">Logout</a>
                                  </c:when>
                                  </c:choose>
                                  </ul>

         </nav>
