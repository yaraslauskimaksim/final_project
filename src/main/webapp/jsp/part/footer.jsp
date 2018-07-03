
<script src="../js/stylish-portfolio.min.js"></script>
<script src="../vendor/animsition/js/animsition.min.js"></script>
<script src="../vendor/bootstrap/js/popper.js"></script>
<script src="../vendor/select2/select2.min.js"></script>
<script src="../vendor/daterangepicker/moment.min.js"></script>
<script src="../vendor/daterangepicker/daterangepicker.js"></script>
<script src="../vendor/countdowntime/countdowntime.js"></script>
<script src="../js/jquery.URI.js"></script>
<script src="../js/URI.js"></script>
<script src="../js/card.js"></script>
<div class="modal fade" id="loginModal">
<div class="modal-dialog">
   <div class="modal-content">
      <div class="modal-header">
         <h5 class="modal-title" id="exampleModalLabel">Sign In!</h5>
         <button type="button" class="close" data-dismiss="modal" aria-label="Close">
         <span aria-hidden="true">&times;</span>
         </button>
      </div>
      <div class="modal-body">
         <form action="frontController" method="POST">
            <input type="hidden" name="command" value="login"/>
            <div class="form-group">
               <label for="recipient-name" class="col-form-label">Email:</label>
               <input class="form-control" type="text" name="email" placeholder="Email...">
            </div>
            <div class="form-group">
               <label for="message-text" class="col-form-label">Password:</label>
               <input  class="form-control" type="password" name="password" placeholder="*************">
            </div>
      </div>
      <div class="modal-footer">
      <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#regModal">
      Have not account yet?
      </button>
      <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      <button type="submit" class="btn btn-danger">Sign In</button>
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
       <c:if test="${not empty error}">
       <p id="errorMessage">${error}</p>
        <c:remove var="error" scope="session" />
       </c:if>
       </div>
    </div>
    </form>
 </div>
 </body>
 </html>