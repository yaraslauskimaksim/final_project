<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
          <link rel="stylesheet" href="styles/style.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB" crossorigin="anonymous">
            <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
            <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
     </head>
     <body>

            <div class="row">
             <div class="col-md-4">
               <div class="card mb-4 box-shadow">
                         <img class="card-img-top" src="static/img/gomer1.png" alt="">
                            <div class="card-body">
                                             <p class="card-text">${quest.description}</p>
                                              <small class="text-muted"> <c:out value="${quest.name}"/></small>
                                              <small class="text-muted"> <c:out value="${quest.name}"/></small>
                                              <small class="text-muted">${quest.genre}</small>
                                             <div class="d-flex justify-content-between align-items-center">
                                              <div class="btn-group">
                                                 <c:if test="${not empty user.email}">
                                                  <a type="button" class="btn btn-sm btn-outline-secondary" href ="/commentForm">Add New Comment</a>
                                                </c:if>
                                              </div>
                                         </div>
                                   </div>
               </div>
             </div>
     </main>

       <footer class="text-muted">
         <div class="container">
           <p class="float-right">
             <a href="#">Back to top</a>
           </p>
           <p>Album example is &copy; Bootstrap, but please download and customize it for yourself!</p>
           <p>New to Bootstrap? <a href="../../">Visit the homepage</a> or read our <a href="../../getting-started/">getting started guide</a>.</p>
         </div>
       </footer>

     </body>
   </html>
