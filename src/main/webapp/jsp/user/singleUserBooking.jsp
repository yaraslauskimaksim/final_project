<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "../part/header.jsp" %>
<div class="container marketing">
<hr>
<div class="row">
  <c:forEach items="${booking}" var="booking">
          <div class="col-lg-4">
            <img class="rounded-circle" src="../static/img/booking.png" width="140" height="140">

            <ul>
             <h2><c:out value="${booking.quest.name}" /> - <c:out value="${booking.quest.genre}" /></h2>
            <h4>Status: <c:out value="${booking.status}" /></h4>
            <p>Date: <c:out value="${booking.timestamp}" /></p>
            <p>Number Of Guests: <c:out value="${booking.numberOfGuests}"/> </p>
            </ul>
            </div>


      </c:forEach>
</div>

</hr>


<ul class="pagination justify-content-center">
   <c:if test="${page != 1}">
      <li class="page-item">
         <a class="page-link" href="${_contextPath}/frontController?command=showSingleUserBooking&page=${page - 1}" aria-label="Previous">
         <span aria-hidden="true">&laquo;</span>
         <span class="sr-only">Previous</span>
         </a>
      </li>
   </c:if>
   <li class="page-item">
      <c:forEach begin="1" end="${numberOfPages}" var="i">
         <c:choose>
            <c:when test="${page eq i}">
               <a class="page-link" href="${_contextPath}/frontController?command=showSingleUserBooking&page=${i}" > ${i}</a>
            </c:when>
         </c:choose>
      </c:forEach>
   </li>
   <li class="page-item">
      <c:if test="${page lt numberOfPages}">
         <a class="page-link" href="${_contextPath}/frontController?command=showSingleUserBooking&page=${page + 1}" aria-label="Next">
         <span aria-hidden="true">&raquo;</span>
         <span class="sr-only">Next</span>
         </a>
      </c:if>
   </li>
</ul>

<%@ include file = "../part/footer.jsp" %>
