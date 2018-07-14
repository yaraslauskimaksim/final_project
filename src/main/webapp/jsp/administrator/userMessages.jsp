<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "../part/header.jsp" %>

  <div class="container marketing">
  <hr>
  <div class="row">
    <c:forEach items="${messages}" var="message">
            <div class="col-lg-4">
              <img class="rounded-circle" src="../static/img/comment.png" width="140" height="140">
              <h2><c:out value="${message.user.email}" /></h2>
              <h6><c:out value="${message.user.firstName}" /> <c:out value="${message.user.lastName}" /></h6>
               <h6><c:out value="${message.message}" /></h6>
              <p><a class="btn btn-secondary" href="${_contextPath}/frontController?command=deleteMessage&id=${message.id}" role="button">Delete</a>
            </div>

        </c:forEach>
  </div>

</hr>
<ul class="pagination justify-content-center">
   <c:if test="${page != 1}">
      <li class="page-item">
         <a class="page-link" href="${_contextPath}/frontController?command=showUserMessages&page=${page - 1}" aria-label="Previous">
         <span aria-hidden="true">&laquo;</span>
         <span class="sr-only">Previous</span>
         </a>
      </li>
   </c:if>
   <li class="page-item">
      <c:forEach begin="1" end="${numberOfPages}" var="i">
         <c:choose>
            <c:when test="${page eq i}">
               <a class="page-link" href="${_contextPath}/frontController?command=showUserMessages&page=${i}" > ${i}</a>
            </c:when>
         </c:choose>
      </c:forEach>
   </li>
   <li class="page-item">
      <c:if test="${page lt numberOfPages}">
         <a class="page-link" href="${_contextPath}/frontController?command=showUserMessages&page=${page + 1}" aria-label="Next">
         <span aria-hidden="true">&raquo;</span>
         <span class="sr-only">Next</span>
         </a>
      </c:if>
   </li>
</ul>
<%@ include file = "../part/footer.jsp" %>