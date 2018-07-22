<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "../part/header.jsp" %>

<section class="jumbotron text-center">
        <div class="container">
          <h1 class="jumbotron-heading">401</h1>
          <p class="lead text-muted">Opps! Not Found, please try once again</p>
          <p>
          <c:choose>
          <c:when test="${'QUEST_OWNER' == sessionScope.role}">
         <a href="/questOwner" class="btn btn-primary my-2">Home</a>
          </c:when>
          <c:when test="${'ADMINISTRATOR' == sessionScope.role}">
            <a href="/administrator/home" class="btn btn-primary my-2">Home</a>
           </c:when>
          <c:otherwise>
          <a href="/home" class="btn btn-primary my-2">Home</a>
          </c:otherwise>
       </c:choose>
          </p>
        </div>
      </section>

<%@ include file = "../part/footer.jsp" %>