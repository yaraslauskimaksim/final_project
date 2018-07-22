<!-- Comments Form -->
<c:if test="${empty userId}">
   <h5> Please, Sign in to leave a comment. <i class="far fa-comments"></i></h5>
</c:if>
<div class="card my-4">
   <c:if test="${not empty user.email}">
      <h5 class="card-header">
         Leave a Comment: <span> ${sessionScope.commentSaved}</span>
         <c:remove var="commentSaved" scope="session" />
      </h5>
      <div class="card-body">
         <form id="comment_form" action="frontController" method="POST">
            <input type="hidden" name="command" value="comment"/>
            <div class="form-group">
               <textarea class="form-control" rows="3" name="description"></textarea>
            </div>
            <button type="submit" class="btn btn-dark">Submit</button>
         </form>
      </div>
   </c:if>
</div>
<!-- Single Comment -->
<c:forEach items="${comment}" var="comments">
   <div class="media mb-4">
      <img class="d-flex mr-3 rounded-circle"  width="50" height="50" src="static/img/commentAvatar.png" alt="">
      <div class="media-body">
         <h5 class="mt-0">
            <c:out value="${comments.user.firstName}" />
            <c:out value="${comments.user.lastName}" />
         </h5>
         <h5 class="mt-0">
            <c:out value="${comments.time}" />
         </h5>
         <c:out value="${comments.description}" />
      </div>
   </div>
</c:forEach>
</div>