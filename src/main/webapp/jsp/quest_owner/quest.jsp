<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "../part/header.jsp" %>
<div class="album py-5 bg-light">
<div class="container">
<div class="row">

<c:forEach items="${allQuests}" var="quests">
   <div class="col-md-4">
      <div class="card mb-4 box-shadow">
         <c:if test="${not empty quests.image}">
            <img class="card-img-top" src="uploadFiles/${quests.image}" alt="" width="200" height="200">
         </c:if>
         <div class="card-body">
            <p class="card-text">
            <h5>
               <c:out value="${quests.name}" />
               -
               <c:out value="${quests.genre}"/>
            </h5>
            </p>
            <p class="card-text">
            <h5>
               <c:out value="${quests.description}" />
            </h5>
            </p>
            <div class="d-flex justify-content-between align-items-center">
               <div class="btn-group">
                   <c:if test="${not empty quests.name}">
                 <a type="button" class="btn btn-dark" href="${_contextPath}/frontController?command=showSingleQuest&questId=${quests.questId}">View / Edit / Delete</a>

                </c:if>
            </div>
        <small class="text-muted"><c:out value="${quests.score}" /></small>
               </div>
            </div>
         </div>
      </div>
</c:forEach>
</div>

<ul class="pagination justify-content-center">
   <c:if test="${page != 1}">
      <li class="page-item">
         <a class="page-link" href="${_contextPath}/frontController?command=showQuestsByRoomName&page=${page - 1}" aria-label="Previous">
         <span aria-hidden="true">&laquo;</span>
         <span class="sr-only">Previous</span>
         </a>
      </li>
   </c:if>
   <li class="page-item">
      <c:forEach begin="1" end="${numberOfPages}" var="i">
         <c:choose>
            <c:when test="${page eq i}">
               <a class="page-link" href="${_contextPath}/frontController?command=showQuestsByRoomName&page=${i}" > ${i}</a>
            </c:when>
         </c:choose>
      </c:forEach>
   </li>
   <li class="page-item">
      <c:if test="${page lt rnumberOfPages}">
         <a class="page-link" href="${_contextPath}/frontController?command=showQuestsByRoomName&page=${page + 1}" aria-label="Next">
         <span aria-hidden="true">&raquo;</span>
         <span class="sr-only">Next</span>
         </a>
      </c:if>
   </li>
</ul>
<%@ include file = "../part/footer.jsp" %>