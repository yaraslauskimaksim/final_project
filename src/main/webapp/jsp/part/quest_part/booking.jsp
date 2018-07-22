<div class="col-md-4">
   <!-- Search Widget -->
   <div class="card my-4">
   </div>
   <!-- Categories Widget -->
   <div class="card my-4">
      <h5 class="card-header">Book This Amazing Quest!</h5>
      <c:choose>
         <c:when test="${empty userId}">Please, Sign in to book the quest.</c:when>
         <c:otherwise>
            <a class="btn btn-secondary" data-toggle="modal" data-target="#bookingModal">Like it? Book it!</a></h5>
            <h5>${error} ${sessionScope.success} </h5>
            <c:remove var="success" scope="session" />
         </c:otherwise>
      </c:choose>
   </div>
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
               <input id="bookingForm" type="hidden" name="command" value="booking"/>
               <div class="form-group">
                  <label class="my-1 mr-2" for="inlineFormCustomSelectPref">Number of people:</label>
                  <select required="" class="custom-select my-1 mr-sm-2" name="numberOfGuests" >
                     <option selected>Choose...</option>
                     <option value="1">One</option>
                     <option value="2">Two</option>
                     <option value="3">Three</option>
                     <option value="4">Four</option>
                     <option value="5">Five</option>
                  </select>
                  <label class="my-1 mr-2" for="inlineFormCustomSelectPref">Time Slot:</label>
                  <select class="custom-select my-1 mr-sm-2" type="text" id="inlineFormCustomSelectPref" name="time" >
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
                  <label class="my-1 mr-2" for="inlineFormCustomSelectPref">Check Missing Date: </label>
                  <select class="custom-select my-1 mr-sm-2" type="text" id="inlineFormCustomSelectPref" >
                     <c:forEach items="${bookingDate}" var="bookingDate">
                        <option disabled>${bookingDate}</option>
                     </c:forEach>
                  </select>
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