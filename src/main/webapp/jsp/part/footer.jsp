<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js" integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js" integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T" crossorigin="anonymous"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.4.1/js/bootstrap-datepicker.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.8.0/js/bootstrap-datepicker.js" ></script>
  <script data-require="MomentJS@2.10.0" data-semver="2.10.0" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.10.6/moment.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>






<script src="../vendor/animsition/js/animsition.min.js"></script>
<script src="../vendor/bootstrap/js/popper.js"></script>
<script src="../vendor/select2/select2.min.js"></script>
<script src="../vendor/daterangepicker/moment.min.js"></script>
<script src="../vendor/daterangepicker/daterangepicker.js"></script>
<script src="../vendor/countdowntime/countdowntime.js"></script>
<script src="../js/card.js"></script>
<script src="../js/submit.js"></script>
<script src="../js/login.js"></script
<script src="../js/rating.js"></script>
<script src="../js/booking.js"></script>
<div class="modal fade" id="loginModal"  role="dialog" data-backdrop="static" data-keyboard="false">
<div class="modal-dialog">
   <div class="modal-content">
      <div class="modal-header">
      <p class="statusMsg"></p>
         <h5 class="modal-title" id="exampleModalLabel">Sign In!</h5>
         <button type="button" class="close" data-dismiss="modal" aria-label="Close">
         <span aria-hidden="true">&times;</span>
         </button>
      </div>
      <div class="modal-body">
         <form id="loginForm" name="loginForm" action="frontController" method="POST">
            <input type="hidden" name="command" value="login"/>
            <div class="form-group">
               <label for="recipient-name" class="col-form-label">Email:</label>
               <input class="form-control" type="text" name="email" id="email" placeholder="Email...">
            </div>
            <div class="form-group">
               <label for="message-text" class="col-form-label">Password:</label>
               <input  class="form-control" id="password" type="password" name="password" placeholder="*************">
            </div>
      </div>
      <div class="modal-footer">
      <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#regModal">
      Have not account yet?
      </button>
      <p id="ajaxGetUserServletResponse"></p>
      <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      <button id="submitBtn" type="submit" class="btn btn-danger" onclick="submitContactForm()">Sign In</button>

      </div>
   </div>
   </form>
</div>

 <div class="modal fade" id="regModal">
 <div class="modal-dialog">
    <div class="modal-content">
       <div class="modal-header">
          <h5 class="modal-title" id="exampleModalLabel">Sign Up!</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
          </button>
       </div>
       <div class="modal-body">
          <form action="frontController" method="POST">
             <input type="hidden" name="command" value="register"/>
             <div class="form-group">
                <label for="recipient-name" class="col-form-label">First Name:</label>
                <input class="form-control" type="text" name="firstname" placeholder="First Name..." required>
             </div>
             <div class="form-group">
                <label for="message-text" class="col-form-label">Last Name:</label>
                <input  class="form-control" type="text" name="lastname" placeholder="Last Name..." required>
             </div>
             <div class="form-group">
                <label for="message-text" class="col-form-label">Email:</label>
                <input  class="form-control" type="text" name="email" placeholder="ex@mail.com" required>
             </div>
             <div class="form-group">
                <label for="message-text" class="col-form-label">Password:</label>
                <input  class="form-control" type="password" name="password" placeholder="*************" required>
             </div>
       </div>
       <div class="modal-footer">
       <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
       <button type="submit" class="btn btn-danger">Sign Up</button>
       <c:if test="${not empty sessionScope.errorMessage}">
       <p id="errorMessage">{sessionScope.errorMessage}</p>
        <c:remove var="errorMessage" scope="session" />
        </c:if>
       </div>
    </div>
    </form>
 </div>
 </body>
 </html>