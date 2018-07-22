<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "part/header.jsp" %>
<div class="container">
<div class="row">
<!-- Post Content Column -->
<div class="col-lg-8">
   <!-- Title -->
   <h1 class="mt-4">
      <c:out value="${quest.name}"/>
      -
      <c:out value="${quest.genre}"/>
   </h1>
   <!-- Author -->
   <p class="lead">
      by
      <a href="#">
         <c:out value="${quest.questRoomName}"/>
      </a>
   </p>
   <!-- Rating Form  -->
   <%@ include file = "part/quest_part/rating.jsp" %>
   <hr>
   <img class="img-fluid rounded"  src="uploadFiles/${quest.image}" alt="">
   <hr>
   <!-- Description  Content -->
   <h5 class="lead">
      <c:out value="${quest.description}"/>
   </h5>
   <hr>
   <!-- Comment Form and User Comments -->
   <%@ include file = "part/quest_part/comment.jsp" %>
   <!-- Booking Form -->
   <%@ include file = "part/quest_part/booking.jsp" %>
   <!-- /.row -->
</div>
<%@ include file = "part/footer.jsp" %>