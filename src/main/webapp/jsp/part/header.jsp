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
      <title>QuestFire</title>
      <link href="../styles/carousel.css" rel="stylesheet">
      <link href="../styles/3-col-portfolio.css" rel="stylesheet">
      <link href="../styles/sb-admin.css" rel="stylesheet">
      <link href="../styles/card.css" rel="stylesheet">
      <link href="../styles/rating.css" rel="stylesheet">
      <link href="../styles/submit.css" rel="stylesheet"
       <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/css/bootstrap-datepicker.css" >
      <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
      <link href="vendor/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
      <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
      <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/css/tempusdominus-bootstrap-4.min.css" />

     </head>
     <body>
        <header>
        <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
        <c:choose>
           <c:when test="${'QUEST_OWNER' == sessionScope.role}">
              <a class="navbar-brand" href="/questOwner">QuestFire</a>
           </c:when>
           <c:otherwise>
              <a class="navbar-brand" href="/home">QuestFire</a>
           </c:otherwise>
        </c:choose>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav mr-auto">
           <li class="nav-item active">
              <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
           </li>


                      <li class="nav-item dropdown">
                         <a class="nav-link dropdown-toggle" href="http://example.com" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Quests</a>
                         <div class="dropdown-menu" aria-labelledby="dropdown01">
                            <a class="dropdown-item" href="frontController?command=quest&page=1">All Quests</a>
                            <a class="dropdown-item" href="frontController?command=showQuestByRating&page=1">Top Quest</a>
                         </div>
                      </li>

           <c:choose>
              <c:when test="${empty user.email}">
                 <li class="nav-item active">
                    <a class="nav-link" href="/login">Sign In</a>
                 </li>
              </c:when>
              <c:when test="${not empty user.email}">
                 <li class="nav-item">
                    <a class="nav-link" href="#">
                       <c:out value="${user.email}"/>
                    </a>
                 </li>
                 <li class="nav-item">
                    <a class="btn btn-outline-danger" href="${_contextPath}/frontController?command=logout">Logout</a>
                 </li>
              </c:when>
           </c:choose>
        </ul>

          <form class="form-inline mt-2 mt-md-0"  style="padding-right: 1rem; " action="frontController" method="POST" >
          <input type="hidden" name="command" value="search"/>
          <input class="form-control mr-sm-2" type="text" placeholder="Quest Name..." aria-label="Search" name="name">
          <button class="btn btn-danger my-2 my-sm-0" type="submit">Search</button>
          </form>
          <form class="form-inline mt-2 mt-md-0">
          <a type="button" class="btn btn-danger" href="frontController?command=local&lang=ru">RU</a>
          <a type="button" class="btn btn-danger" href="frontController?command=local&lang=en">EN</a>
          </form>
          </div>
       </nav>
    </header>

