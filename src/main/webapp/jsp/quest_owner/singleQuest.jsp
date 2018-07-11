<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "../part/header.jsp" %>
<div class="container">
<div class="row">
<div class="col-lg-8">
   <h1 class="mt-4">
      <c:out value="${quest.name}"/>
   </h1>
   <p class="lead">
      by
      <a href="#">
         <c:out value="${quest.questRoomName}"/>
      </a>
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
</div>
<div class="col-md-4">
   <div class="card my-4">
      <h5 class="card-header">Maybe you want to change something?</h5>
      <div class="card-body">
         <div class="input-group">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo">Edit</button>
         </div>
      </div>
   </div>
</div>
</div>
</div>
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
<div class="modal-dialog" role="document">
<div class="modal-content">
<div class="modal-header">
   <h5 class="modal-title" id="exampleModalLabel">Edit Amazing Quest</h5>
   <button type="button" class="close" data-dismiss="modal" aria-label="Close">
   <span aria-hidden="true">&times;</span>
   </button>
</div>
<div class="modal-body">
<form action="${_contextPath}/frontController" method="POST" >
<input type="hidden" name="command" value="editQuest"/>
<div class="form-group">
   <label for="recipient-name" class="col-form-label">Name:</label>
   <c:if test="${not empty quest.name}">
      <input type="text" class="form-control" name="name"  value="<c:out value='${quest.name}'/>" />
   </c:if>
   <c:if test="${empty quest.name}">
      <input type="text" class="form-control" name="name" />
   </c:if>
</div>
<div class="form-group">
   <label for="recipient-name" class="col-form-label">Genre:</label>
   <c:if test="${not empty quest.genre}">
      <input type="text" class="form-control" name="genre"  value="<c:out value='${quest.genre}'/>" />
   </c:if>
   <c:if test="${empty quest.genre}">
      <input type="text" class="form-control" name="genre" />
   </c:if>
</div>
<div class="form-group">
   <label for="recipient-name" class="col-form-label">Description:</label>
   <c:if test="${not empty quest.description}">
      <input type="text" class="form-control" name="description"  value="<c:out value='${quest.description}'/>" />
   </c:if>
   <c:if test="${empty quest.description}">
      <input type="text" class="form-control" name="description"  />
   </c:if>
</div>
<div class="form-group">
<label for="recipient-name" class="col-form-label">Description:</label>
<c:if test="${not empty quest.image}">
   <input type="text" class="form-control" name="image"  value="<c:out value='${quest.image}'/>" />
</c:if>
<c:if test="${ empty quest.image}">
   <input type="text" class="form-control" name="image"/>
</c:if>
 </div>
 <div class="form-group">
    <label for="recipient-name" class="col-form-label">Quest Room Name:</label>
    <c:if test="${not empty quest.questRoomName}">
       <input type="text" class="form-control" name="roomName" value="<c:out value='${quest.questRoomName}'/>" />
    </c:if>
    <c:if test="${ empty quest.questRoomName}">
       <input type="text" class="form-control" name="roomName"  />
    </c:if>
 </div>
 </div>
 <div class="modal-footer">
    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
    <button type="submit" class="btn btn-primary">Edit</button>
 </div>
 </div>
 </form>
 </div>
 </div>
 <%@ include file = "../part/footer.jsp" %>