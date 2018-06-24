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
          <link rel="stylesheet" href="../../styles/carousel.css">
          <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
          <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
          <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
          <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
    </head>
  <body>

   <header>
      <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <a class="navbar-brand" href="/home">QuestFire</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Link</a>
            </li>
           <li class="nav-item dropdown">
                   <a class="nav-link dropdown-toggle" href="http://example.com" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Quests</a>
                   <div class="dropdown-menu" aria-labelledby="dropdown01">
                   <a class="dropdown-item" href="frontController?command=quest">All Quests</a>
                   <a class="dropdown-item" href="#">Top Quest</a>
                </div>
           </li>
          </ul>
          <form class="form-inline mt-2 mt-md-2">
              <a class="btn btn-danger" type="button" data-toggle="collapse" data-target=".multi-collapse" aria-expanded="false" aria-controls="multiCollapseExample1 multiCollapseExample2">Come Closer!</a>
            </form>
            <div class="row">
              <div class="col">
               <div class="collapse multi-collapse" id="multiCollapseExample1">
               <div class="card card-body">
               <c:choose>
                <c:when test="${empty user.email}">
                <a  type="button" class="btn btn-outline-danger" href="/login">${login}</a>
                <a  type="button" class="btn btn-outline-danger" href="/register">${signup}</a>
                </c:when>
                 <c:when test="${not empty user.email}">
                 <c:out value="${user.email}"/>
                  <a class="btn btn-outline-danger" href="frontController?command=logout">Logout</a>
                  </c:when>
                </c:choose>
                </ul>
                </div>
      </div>
    </div>
  </div>
</div>
          <form class="form-inline mt-2 mt-md-2">
            <a type="button" class="btn btn-danger" href="frontController?command=local&adr=${pageContext.request.requestURI}&lang=ru">RU</a>
            <a type="button" class="btn btn-danger" href="frontController?command=local&adr=${pageContext.request.requestURI}&lang=en">EN</a>
          </form>
        </div>
      </nav>
    </header>