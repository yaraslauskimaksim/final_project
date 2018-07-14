<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "../part/header.jsp" %>
<div class="row">
 <c:forEach items="${users}" var="user">
 <c:if test="${user.status eq 'ACTIVE'}">
<div class="col-lg-4 mb-4">

            <div class="card h-100">
            <h4 class="card-header"><c:out value="${user.status}" /></h4>
            <div class="card-body">
              <p class="card-text">Name: <c:out value="${user.firstName}" /> <c:out value="${user.lastName}" /></p>
             <p class="card-text">Email: <c:out value="${user.email}" /> </p>
            </div>
            <div class="card-footer">
            <p><a class="btn btn-secondary" href="${_contextPath}/frontController?command=frozeUser&id=${user.id}" role="button">Froze User!</a></p>
              <a href="#" class="btn btn-primary">Learn More</a>
            </div>
          </div>

        </div>
      </c:if>
      <c:if test="${user.status eq 'FROZEN'}">
      <div class="col-lg-4 mb-4">

                  <div class="card h-100">
                  <h4 class="card-header"><c:out value="${user.status}" /></h4>
                  <div class="card-body">
                    <p class="card-text">Name: <c:out value="${user.firstName}" /> <c:out value="${user.lastName}" /></p>
                   <p class="card-text">Email: <c:out value="${user.email}" /> </p>
                  </div>
                  <div class="card-footer">
                  <p><a class="btn btn-secondary" href="${_contextPath}/frontController?command=makeUserActive&id=${user.id}" role="button">Active User!</a></p>
                    <a href="#" class="btn btn-primary">Learn More</a>
                  </div>
                </div>

              </div>
            </c:if>
</c:forEach>
</div>


   <ul class="pagination justify-content-center">
   <c:if test="${page != 1}">
      <li class="page-item">
         <a class="page-link" href="${_contextPath}/frontController?command=showUserByStatus&status=${requestScope.status}&page=${page - 1}" aria-label="Previous">
         <span aria-hidden="true">&laquo;</span>
         <span class="sr-only">Previous</span>
         </a>
      </li>
   </c:if>
   <li class="page-item">
      <c:forEach begin="1" end="${numberOfPages}" var="i">
         <c:choose>
            <c:when test="${page eq i}">
               <a class="page-link" href="${_contextPath}/frontController?command=showUserByStatus&status=${requestScope.status}&page=${i}" > ${i}</a>
            </c:when>
         </c:choose>
      </c:forEach>
   </li>
   <li class="page-item">
      <c:if test="${page lt numberOfPages}">
         <a class="page-link" href="${_contextPath}/frontController?command=showUserByStatus&status=${requestScope.status}&page=${page + 1}" aria-label="Next">
         <span aria-hidden="true">&raquo;</span>
         <span class="sr-only">Next</span>
         </a>
      </c:if>
   </li>
</ul>


<%@ include file = "../part/footer.jsp" %>