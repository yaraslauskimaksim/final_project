<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ include file = "../part/header.jsp" %>
<div class="container marketing">
<hr>
<div class="row">
<h1 class="jumbotron-heading" style="padding: 150px"> ${requestScope.emptyList}</h1>
  <c:forEach items="${booking}" var="booking">
          <div class="col-lg-4">
            <img class="rounded-circle" src="../static/img/booking.png" width="140" height="140">
            <h2><c:out value="${booking.quest.name}" /></h2>
            <ul>
            <p>Name: <c:out value="${booking.user.firstName}" /> <c:out value="${booking.user.lastName}" /></p>
            <p>Date: <c:out value="${booking.timestamp}" /></p>
            <p>Number Of Guests: <c:out value="${booking.numberOfGuests}"/> </p>
            </ul>
            <p><a class="btn btn-secondary" href="${_contextPath}/frontController?command=approveBooking&id=${booking.bookingId}" role="button">Approve Booking</a></p>
             <p><a class="btn btn-secondary" href="${_contextPath}/frontController?command=rejectBooking&id=${booking.bookingId}" role="button">Reject Booking</a></p>
          </div>

      </c:forEach>
</div>

</hr>

<ul class="pagination justify-content-center">

   <c:if test="${page == 1}">
      <li class="page-item">
         <a class="page-link" href="${_contextPath}/frontController?command=showUserBooking&page=${page - 1}" aria-label="Previous">
         <span aria-hidden="true">&laquo;</span>
         <span class="sr-only">Previous</span>
         </a>
      </li>
   </c:if>

   <li class="page-item">
      <c:forEach begin="1" end="${numberOfPages}" var="i">
         <c:choose>
            <c:when test="${page eq i}">
               <a class="page-link" href="${_contextPath}/frontController?command=showUserBooking&page=${i}" > ${i}</a>
            </c:when>
         </c:choose>
      </c:forEach>
   </li>
   <li class="page-item">
      <c:if test="${page lt rnumberOfPages}">
         <a class="page-link" href="${_contextPath}/frontController?command=showUserBooking&page=${page + 1}" aria-label="Next">
         <span aria-hidden="true">&raquo;</span>
         <span class="sr-only">Next</span>
         </a>
      </c:if>
   </li>
</ul>


<%@ include file = "../part/footer.jsp" %>