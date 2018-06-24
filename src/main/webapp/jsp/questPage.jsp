<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "part/header.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


 <div class="album py-5 bg-light">
        <div class="container">

          <div class="row">
            <div class="col-md-4">
              <div class="card mb-4 box-shadow">
               <c:forEach items="${quest}" var="quest">
                  <c:if test="${fn:containsIgnoreCase(quest.name, 'rec')}">
                   <img class="card-img-top" src="static/img/gomer1.png" alt="">
                    </c:if>
                    <c:if test="${fn:containsIgnoreCase(quest.name, 'narnia')}">
                    <img class="card-img-top" src="static/img/harry.png" alt="">
                    </c:if>
                  <div class="card-body">
                  <p class="card-text"><c:out value="${quest.description}"/></p>
                   <div class="d-flex justify-content-between align-items-center">
                    <div class="btn-group">
                     <a type="button" class="btn btn-sm btn-outline-secondary" href="frontController?command=single_quest&questId=${quest.questId}">View</a>
                      <c:if test="${not empty user.email}">
                       <a type="button" class="btn btn-sm btn-outline-secondary" href ="frontController?command=go_to_comment&questId=${quest.questId}">Add New  Comment</a>
                        </c:if>
                       </div>
                       <small class="text-muted"><c:out value="${quest.name}"/></small>
                       </div>
                       </div>

                      </div>
                       </c:forEach >
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
