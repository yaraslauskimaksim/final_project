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


<%@ include file = "../part/footer.jsp" %>
