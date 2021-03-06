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
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" >
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Quest Fire</title>

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery.bootstrapvalidator/0.5.0/css/bootstrapValidator.min.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous" />
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.0/css/all.css" integrity="sha384-lKuwvrZot6UHsBSfcMvOkWwlCMgc0TaWr+30HWe3a4ltaBwTZhyTEggF5tJv8tbt" crossorigin="anonymous">
    <!-- Custom styles for this template -->
    <link href="../styles/album.css" rel="stylesheet">
    <link href="../styles/carousel.css" rel="stylesheet">
     <link href="../styles/rating.css" rel="stylesheet">

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
                <c:choose>
                   <c:when test="${'QUEST_OWNER' == sessionScope.role}">
                      <li class="nav-item dropdown">
                         <a  class="nav-link dropdown-toggle" href="http://example.com" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Quests</a>
                         <div class="dropdown-menu" aria-labelledby="dropdown01">
                            <a class="dropdown-item" href="${_contextPath}/frontController?command=showQuestsByRoomName&page=1">Quests</a>

                         </div>
                      </li>
                   </c:when>
                   <c:when test="${'ADMINISTRATOR' == sessionScope.role}">
                      <li class="nav-item dropdown">
                         <a class="nav-link dropdown-toggle" href="http://example.com" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">My Items</a>
                         <div class="dropdown-menu" aria-labelledby="dropdown01">
                            <a class="dropdown-item" href="${_contextPath}/frontController?command=show&page=1">Comments</a>
                            <a class="dropdown-item" href="${_contextPath}/frontController?command=showUserByStatus&status=active&page=1">Active Users</a>
                            <a class="dropdown-item" href="${_contextPath}/frontController?command=showUserByStatus&status=frozen&page=1">Frozen Users</a>
                             <a class="dropdown-item" href="${_contextPath}/frontController?command=showUserMessages&page=1">Messages</a>
                         </div>
                      </li>
                       </c:when>
                      <c:when test="${'ADMINISTRATOR' == sessionScope.role}">
                                                 <li class="nav-item dropdown">
                                                    <a class="nav-link dropdown-toggle" href="http://example.com" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">My Items</a>
                                                    <div class="dropdown-menu" aria-labelledby="dropdown01">
                                                       <a class="dropdown-item" href="${_contextPath}/frontController?command=show&page=1">Comments</a>
                                                       <a class="dropdown-item" href="${_contextPath}/frontController?command=showUserByStatus&status=active&page=1">Active Users</a>
                                                       <a class="dropdown-item" href="${_contextPath}/frontController?command=showUserByStatus&status=frozen&page=1">Frozen Users</a>
                                                        <a class="dropdown-item" href="${_contextPath}/frontController?command=showUserMessages&page=1">Messages</a>
                                                    </div>
                                                 </li>
                                              </c:when>

                     <c:when test="${'CLIENT' == sessionScope.role}">
                                         <li class="nav-item dropdown">
                                            <a class="nav-link dropdown-toggle" href="http://example.com" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">My Items</a>
                                            <div class="dropdown-menu" aria-labelledby="dropdown01">
                                             <a class="dropdown-item" href="${_contextPath}/frontController?command=quest&page=1">All Quests</a>
                                                                         <a class="dropdown-item" href="${_contextPath}/frontController?command=showQuestByRating&page=1">Top Quest</a>
                                               <a class="dropdown-item" href="${_contextPath}/frontController?command=showSingleUserBooking&page=1">My Booking</a>
                                            </div>
                                         </li>
                                      </c:when>
                   <c:otherwise>
                      <li class="nav-item dropdown">
                         <a class="nav-link dropdown-toggle" href="http://example.com" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Quests <i class="fas fa-bomb"></i></a>
                         <div class="dropdown-menu" aria-labelledby="dropdown01">
                            <a class="dropdown-item" href="${_contextPath}/frontController?command=quest&page=1">All Quests</a>
                            <a class="dropdown-item" href="${_contextPath}/frontController?command=showQuestByRating&page=1">Top Quest</a>
                         </div>
                      </li>
                   </c:otherwise>
                </c:choose>
             </ul>
           <ul class="navbar-nav mr-auto">
              <c:if test="${empty user.email}">
                 <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="http://example.com" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Sign In / Sign Up <i class="far fa-address-book"></i></a>
                      <div class="dropdown-menu" aria-labelledby="dropdown01">
                       <a class="dropdown-item" href="/login">${login}</a>
                       <a class="dropdown-item" href="/register">${signup}</a>
                    </div>
                 </li>
              </c:if>

           </ul>
              <ul class="navbar-nav mr-auto">
                 <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="http://example.com" id="dropdown01" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Languages <i class="fas fa-globe"></i></a>
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
         <c:choose>
         <c:when test="${'QUEST_OWNER' == sessionScope.role}">
          <a href="/questOwner/home" class="navbar-brand d-flex align-items-center">
         </c:when>
         <c:when test="${'ADMINISTRATOR' == sessionScope.role}">
         <a href="/administrator/home" class="navbar-brand d-flex align-items-center">
         </c:when>
           <c:otherwise>
         <a href="/home" class="navbar-brand d-flex align-items-center">
         </c:otherwise>
          </c:choose>
            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDdee8Y-JW5FV4-h1U-lOFYvGXDUuhFHY5THAEAeoibanI49rBww" width="20" height="20" viewBox="0 0 24 24" fill="none" class="mr-2"/>
            <strong>QuestFire</strong>
          </a>
      <c:choose>
           <c:when test="${'ADMINISTRATOR' == sessionScope.role}">
             </c:when>
             <c:when test="${'QUEST_OWNER' == sessionScope.role}">
             </c:when>
           <c:otherwise>
                       <form class="form-inline mt-2 mt-md-0" style="padding-left:450px" action="frontController" method="POST">
                                 <input type="hidden" name="command" value="search"/>
                                  <input class="form-control mr-sm-2" type="text" placeholder="Search" aria-label="Search" name="name">
                                  <button class="btn btn-secondary" type="submit">Search</button>
                                </form>

                                      </c:otherwise>
                                                        </c:choose>
                                                          <button class="navbar-toggler" type="submit" data-toggle="collapse" data-target="#navbarHeader" aria-controls="navbarHeader" aria-expanded="false" aria-label="Toggle navigation">
                                                                                           <span class="navbar-toggler-icon"></span>
                                                                                         </button>
        </div>
      </div>
    </header>