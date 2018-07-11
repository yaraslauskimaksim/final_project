<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "part/header.jsp" %>
<div class="container">
<div class="row">
<div class="col-lg-8">
<h1 class="mt-4">
   <c:out value="${quest.name}"/>
</h1>
<p class="lead">
   by
   <a href="#">Start Bootstrap</a>
</p>
<hr>
<p>
   <c:out value="${quest.genre}"/>
</p>
<hr>
<img id = "singleQuest" class="img-fluid rounded" src="${quest.image}" alt="">
<hr>
<p class="lead">
   <c:out value="${quest.description}"/>
<blockquote class="blockquote">
   <p class="mb-0">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>
   <footer class="blockquote-footer">Someone famous in
      <cite title="Source Title">Source Title</cite>
   </footer>
</blockquote>
<hr>
</p>
<c:if test="${not empty user.email}">
   <div class="card my-4">
      <h5 class="card-header">Leave a Comment:</h5>
      <div class="card-body">
         <form action="frontController" method="POST">
            <input type="hidden" name="command" value="comment"/>
            <div class="form-group">
               <textarea class="form-control" rows="3" name="description"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
         </form>
      </div>
   </div>
   <c:forEach items="${comment}" var="comments">
      <c:if test="${comments.status == 'APPROVED'}">
         <div class="media mb-4">
            <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
            <div class="media-body">
               <h5 class="mt-0">
                  <span>
                     <c:out value="${comments.user.firstName}" />
                  </span>
                  <span>
                     <c:out value="${comments.user.lastName}" />
                  </span>
                  <span>
                     <c:out value="${comments.quest.name}" />
                  </span>
               </h5>
               <p>
                  <c:out value="${comments.description}" />
            </div>
         </div>
      </c:if>
   </c:forEach>
</c:if>


</div>
<div class="col-md-4">
   <div class="card my-4">
      <p class="card-header"  style="background-color: #F75959; color: #FFFFFF">Rate It! <c:if test="${empty userId}"> Please, Sign in to book the quest. </c:if> </h5>
        <c:if test="${ not empty userId}">
         <div class="card-body">
         <div class="input-group" >
           <span style="right-padding: 7px"><a id="rating"  type="button" class="btn btn-danger btn-circle btn-xl" href ="frontController?command=rateQuest&questId=${quest.questId}&score=1"><i class="fa fa-heart"></i></a>
           <a id="rating"  type="button" class="btn btn-danger btn-circle btn-xl" href ="frontController?command=rateQuest&questId=${quest.questId}&score=2"><i class="fa fa-heart"></i></a>
           <a id="rating"  type="button" class="btn btn-danger btn-circle btn-xl" href ="frontController?command=rateQuest&questId=${quest.questId}&score=3"><i class="fa fa-heart"></i></a>
           <a id="rating" type="button" class="btn btn-danger btn-circle btn-xl" href ="frontController?command=rateQuest&questId=${quest.questId}&score=4"><i class="fa fa-heart"></i></a>
           <a id="rating" type="button" class="btn btn-danger btn-circle btn-xl" href ="frontController?command=rateQuest&questId=${quest.questId}&score=5"><i class="fa fa-heart"></i></a>
         </div>

      </div>
      </c:if>
   </div>

   <div class="card my-4">
      <p class="card-header" style="background-color:#F75959; color: #FFFFFF">
       <c:choose>
       <c:when test="${empty userId}">Please, Sign in to book the quest.</c:when>
         <c:otherwise>
      <a data-toggle="modal" data-target="#bookingModal">Like it? Book it! ${error} ${success} </a></h5>
      </c:otherwise>
              </c:choose>
   </div>

   <div class="modal fade" id="bookingModal">
    <div class="modal-dialog">
       <div class="modal-content">
          <div class="modal-header">
             <h5 class="modal-title" id="exampleModalLabel">Book it!</h5>
             <button type="button" class="close" data-dismiss="modal" aria-label="Close">
             <span aria-hidden="true">&times;</span>
             </button>
          </div>
          <div class="modal-body">
             <form action="frontController" method="POST">
                <input type="hidden" name="command" value="booking"/>
                  <div class="form-group">
 <label class="my-1 mr-2" for="inlineFormCustomSelectPref">Number of people:</label>
  <select class="custom-select my-1 mr-sm-2" id="inlineFormCustomSelectPref" name="numberOfGuests">
    <option selected>Choose...</option>
    <option value="1">One</option>
    <option value="2">Two</option>
    <option value="3">Three</option>
  </select>

                  <label class="my-1 mr-2" for="inlineFormCustomSelectPref">Time Slot:</label>
                   <select class="custom-select my-1 mr-sm-2" type="text" id="inlineFormCustomSelectPref" name="time">
                     <option selected>Choose...</option>
                     <option value="10:00">10:00</option>
                     <option value="12:00">12:00</option>
                     <option value="14:00">14:00</option>
                     <option value="16:00">16:00</option>
                     <option value="18:00">18:00</option>
                      <option value="20:00">20:00</option>
                   </select>

                 <label for="message-text" class="col-form-label">Date:</label>
                 <input  class="custom-select my-1 mr-sm-2" type="date" id="arrive" class="floatLabel" name="date" required >


         </div>
          <div class="modal-footer">
          <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
          <button type="submit" class="btn btn-danger">Confirm Booking!</button>
          <c:if test="${not empty error}">
          <p id="errorMessage">${error}</p>
           <c:remove var="error" scope="session" />
          </c:if>
          </div>
       </div>
       </form>
    </div>
</div>
<%@ include file = "part/footer.jsp" %>