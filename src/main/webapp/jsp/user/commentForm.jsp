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
          <link rel="stylesheet" href="../../styles/style.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
            <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
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
                <h4 class="text-white"><ul class="text-white">
                <a class="nav-link dropdown-toggle" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Language</a>
                 <div class="dropdown-menu" aria-labelledby="dropdown01">
                  <a class="dropdown-item" href="frontController?command=local&adr=${pageContext.request.requestURI}&lang=ru">RU</a>
                 <a class="dropdown-item" href="frontController?command=local&adr=${pageContext.request.requestURI}&lang=en">EN</a>
                 </div>
                   </ul></h4>

              </div>
            </div>
          </div>
        </div>

         <div class="navbar navbar-dark bg-dark box-shadow">
                <div class="container d-flex justify-content-between">

                 <ul><a class="btn btn-outline-danger" href="frontController?command=local&adr=${pageContext.request.requestURI}&lang=ru">RU</a>
                   <a class="btn btn-outline-danger" href="frontController?command=local&adr=${pageContext.request.requestURI}&lang=en">EN</a></ul>
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="mr-2"><path d="M23 19a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2V8a2 2 0 0 1 2-2h4l2-3h6l2 3h4a2 2 0 0 1 2 2z"></path><circle cx="12" cy="13" r="4"></circle></svg>
                    <ul><button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span><ul>
                  </button>
                     <p><div class="sticku">
                                    <c:choose>
                                       <c:when test="${empty user.email}">
                                        <a  class="btn btn-outline-danger" href="/login">${login}</a>
                                       <a class="btn btn-outline-danger" href="/register">${signup}</a>
                                       </c:when>
                                      <c:when test="${not empty user.email}">
                                      <c:out value="${user.email}"/>
                                       <a class="btn btn-outline-danger"c>Logout</a>
                                       </c:when>
                  </c:choose></ul></div>
                </div>
              </div>
            </header>


<body class="text-center">
    <div class="container">
     <div class="row">
    <h1 style="text-align:center;">Add New Comment to</h1>
    <div style="width:30%; margin:30px auto;">
    <form action="frontController" method="POST">
    <input type="hidden" name="command" value="comment"/>
    <div class = "form-group">
     <input class="form-control" type="text" name="description" placeholder="Text">
      </div>
      <div class = "form-group">
      <button class="btn btn-lg btn-primary btn-block">Send Comment</button>
      </div>
     </form>
    <a href="/questPaget">Go Back</a>
    </div>

    </body>
    </html>