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
   <h5>  <c:out value="${quest.genre}"/>
   </h5>
   <hr>
   <img id = "singleQuest" class="img-fluid rounded" src="uploadFiles/${quest.image}" alt="">
   <hr>
   <p class="lead">
      <c:out value="${quest.description}"/>

   <hr>

   </p>

</div>
<div class="col-md-4">
   <div class="card my-4">
      <h5 class="card-header"><a class="btn btn-secondary" data-toggle="modal" data-target="#exampleModal">Edit Quest ${error} ${success} </a></h5>
</h5>
</div>
  <div class="card my-4">
            <form action="${_contextPath}/frontController" method="POST" >
            <input type="hidden" name="command" value="deleteQuest"/>
             <div class="form-group">
             </div>
              <h5 class="card-header"><button class="btn btn-secondary" type="submit" class="btn btn-primary">DELETE</button></h5>
              </form>

            </div>
      <div class="card my-4">
      <form action="${_contextPath}/upload" method="POST" enctype="multipart/form-data">
       <div class="form-group">
       <input accept="image/*" style="padding:10px"  id="files" type="file" class="form-control-file" name="fileName" oninvalid="this.setCustomValidity('Image is a must')"  onchange="this.setCustomValidity('')" required >
       </div>
        <h5 class="card-header"><button class="btn btn-secondary" type="submit" class="btn btn-primary" value="Upload">Upload</button></h5>
        </form>

      </div>

</div>
 <h3 class="jumbotron-heading" > ${sessionScope.duplicatedQuest}</h3>
         <c:remove var="duplicatedQuest" scope="session" />
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
<input id="add_form" type="hidden" name="command" value="editQuest"/>
<div class="form-group">
   <label for="recipient-name" class="col-form-label">Name:</label>
   <c:if test="${not empty quest.name}">
      <input type="text" class="form-control" name="name"  value="<c:out value='${quest.name}'/>" oninvalid="this.setCustomValidity('Quest Name is a must')"  onchange="this.setCustomValidity('')" required />
   </c:if>
   <c:if test="${empty quest.name}">
      <input type="text" class="form-control" name="name" oninvalid="this.setCustomValidity('Quest Name is a must')"  onchange="this.setCustomValidity('')" required  />
   </c:if>
</div>
<div class="form-group">
   <label for="recipient-name" class="col-form-label">Genre:</label>
   <c:if test="${not empty quest.genre}">
      <input type="text" class="form-control" name="genre"  value="<c:out value='${quest.genre}'/>" oninvalid="this.setCustomValidity('Quest Genre is a must')"  onchange="this.setCustomValidity('')" required  />
   </c:if>
   <c:if test="${empty quest.genre}">
      <input type="text" class="form-control" name="genre" oninvalid="this.setCustomValidity('Quest Genre is a must')"  onchange="this.setCustomValidity('')" required />
   </c:if>
</div>
<div class="form-group">
   <label for="recipient-name" class="col-form-label">Description:</label>
   <c:if test="${not empty quest.description}">
      <input type="text" class="form-control" name="description"  value="<c:out value='${quest.description}'/>" oninvalid="this.setCustomValidity('Quest Description is a must')"  onchange="this.setCustomValidity('')" required />
   </c:if>
   <c:if test="${empty quest.description}">
      <input type="text" class="form-control" name="description" oninvalid="this.setCustomValidity('Quest Description is a must')"  onchange="this.setCustomValidity('')" required   />
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