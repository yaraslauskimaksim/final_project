<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "part/header.jsp" %>
<div class="container">
<div class="row">
<div class="col-lg-8">
<h1 class="mt-4">
   <c:out value="${quest.name}"/> -  <c:out value="${quest.genre}"/>
   <p class="lead">
      by
      <a href="#">Start Bootstrap</a>
   </p>
   </h1>
   <p>
   <hr>
    <c:if test="${ not empty userId}">
     <form action="frontController" method="POST">
        <input type="hidden" name="command" value="rateQuest"/>
        <h3><button type="submit" class="btn btn-dark" onclick = rating() style="padding-left:15px">Rate It! </button><c:if test="${empty userId}"> Please, Sign in to book the quest. </c:if>
 <c:choose>
             <c:when test="${empty userId}">Please, Sign in to book the quest.</c:when>
               <c:otherwise>
            <a class="btn btn-secondary" data-toggle="modal" data-target="#bookingModal">Like it? Book it! ${error} ${success} </a></h5>
            </c:otherwise>
      </c:choose></h3>
           <ul class="rate-area">
             <input type="radio" id="5-star" name="score" value="5" /><label for="5-star" title="Amazing">5 stars</label>
             <input type="radio" id="4-star" name="score" value="4" /><label for="4-star" title="Good">4 stars</label>
             <input type="radio" id="3-star" name="score" value="3" /><label for="3-star" title="Average">3 stars</label>
             <input type="radio" id="2-star" name="score" value="2" /><label for="2-star" title="Not Good">2 stars</label>
             <input type="radio" id="1-star" name="score" value="1" /><label for="1-star" title="Bad">1 star</label>
           </ul>
           <h5><span> ${sessionScope.noRating} </span>
           <c:remove var="noRating" scope="session" /></h5>
</c:if>
</form>
</p>



<img id = "singleQuest" class="img-fluid rounded" src="${quest.image}" alt="">

<p class="lead">
   <c:out value="${quest.description}"/>


</p>

   <div class="card my-4">
   <c:if test="${not empty user.email}">
      <h5 class="card-header">Leave a Comment:</h5>
      <div class="card-body">
         <form action="frontController" method="POST">
            <input type="hidden" name="command" value="comment"/>
            <div class="form-group">
               <textarea class="form-control" rows="3" name="description"></textarea>
            </div>
            <button type="submit" class="btn btn-dark">Submit</button>
         </form>
      </div>
     </c:if>
   </div>
    <c:forEach items="${comment}" var="comments">
       <div class="media mb-4">
            <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
            <div class="media-body">
              <h5 class="mt-0"> <c:out value="${comments.user.firstName}" />   <c:out value="${comments.user.lastName}" /></h5>
             <c:out value="${comments.description}" />

            </div>
          </div>
            </c:forEach>


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
</div>
<%@ include file = "part/footer.jsp" %>