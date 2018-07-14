<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "../part/header.jsp" %>
<hr>
<div class="row" >
<div class="col-xl-3 col-sm-6 mb-3"  style="padding-left: 2rem; ">
   <div class="card text-white bg-primary o-hidden h-100">
      <div class="card-body">
         <div class="card-body-icon">
            <i class="fa fa-fw fa-comments"></i>
         </div>
         <div class="mr-5">View All Quest!</div>
      </div>
      <a class="card-footer text-white clearfix small z-1" href="${_contextPath}/frontController?command=showQuestByRoomName&page=1">
      <span class="float-left">View Details</span>
      <span class="float-right">
      <i class="fa fa-angle-right"></i>
      </span>
      </a>
   </div>
</div>
<div class="col-xl-3 col-sm-6 mb-3">
   <div class="card text-white bg-danger o-hidden h-100">
      <div class="card-body">
         <div class="card-body-icon">
            <i class="fa fa-fw fa-support"></i>
         </div>
         <div class="mr-5">Add new Quest right now?</div>
      </div>
      <button type="button" class="card-footer text-white clearfix small z-1" data-toggle="modal" data-target="#exampleModal">
         <span class="float-left">View Details</span>
         <span class="float-right">
         <i class="fa fa-angle-right"></i>
         </span>
         </a>
   </div>
</div>
<div class="col-xl-3 col-sm-6 mb-3">
   <div class="card text-white bg-success o-hidden h-100">
      <div class="card-body">
         <div class="card-body-icon">
            <i class="fa fa-fw fa-shopping-cart"></i>
         </div>
         <div class="mr-5">New Booking!</div>
      </div>
     <a class="card-footer text-white clearfix small z-1" href="${_contextPath}/frontController?command=showUserBooking&page=1">
      <span class="float-left">View Details</span>
      <span class="float-right">
      <i class="fa fa-angle-right"></i>
      </span>
      </a>
   </div>
</div>
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
<div class="modal-dialog" role="document">
<div class="modal-content">
<div class="modal-header">
   <h5 class="modal-title" id="exampleModalLabel">Add new amazing quest</h5>
   <button type="button" class="close" data-dismiss="modal" aria-label="Close">
   <span aria-hidden="true">&times;</span>
   </button>
</div>
<div class="modal-body">
<form action="${_contextPath}/frontController" method="POST" >
<input type="hidden" name="command" value="addQuest"/>
<div class="form-group">
   <label for="recipient-name" class="col-form-label">Name:</label>
   <input type="text" class="form-control" name="name">
</div>
<div class="form-group">
   <label for="recipient-name" class="col-form-label">Genre:</label>
   <input type="text" class="form-control" name="genre">
</div>
<div class="form-group">
   <label for="message-text" class="col-form-label">Description:</label>
   <textarea class="form-control" name="description"></textarea>
</div>
<div class="form-group">
   <div class="input-group mb-3">
     <div class="input-group-prepend">
       <span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
     </div>
     <div class="custom-file">
       <input type="file" class="custom-file-input" id="inputGroupFile01" aria-describedby="inputGroupFileAddon01">
       <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
     </div>
   </div>
</div>
<div class="form-group">
   <label for="message-text" class="col-form-label">Quest Room Name:</label>
   <input type="text" class="form-control" name="roomName">
</div>
</div>
<div class="modal-footer">
   <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
   <button type="submit" class="btn btn-primary">Add new quest!</button>
</div>
</div>
</form>
</div>
</div>
</hr>
<%@ include file = "../part/footer.jsp" %>