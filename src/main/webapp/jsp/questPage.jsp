
<%@ taglib uri="/WEB-INF/taglib/paginator.tld" prefix="paginator" %>
<%@ include file = "part/header.jsp" %>


<div class="album py-5 bg-light">
<div class="container">
<p> ${requestScope.emptyList}</p>
<div class="row">
<c:forEach items="${allQuests}" var="quest">
   <div class="col-md-4">
      <div class="card mb-4 box-shadow">
         <c:if test="${not empty quest.image}">
            <img class="card-img-top" src="uploadFiles/${quest.image}" alt="" width="200" height="200">
         </c:if>
         <div class="card-body">
            <p class="card-text">
            <h5>
               <c:out value="${quest.name}" />
               -
               <c:out value="${quest.genre}"/>
            </h5>
            </p>
            <p class="card-text">
            <h5>
               <c:out value="${quest.description}" />
            </h5>
            </p>
            <div class="d-flex justify-content-between align-items-center">
               <div class="btn-group">
                  <a class="btn btn-dark" href="frontController?command=singleQuest&questId=${quest.questId}">View</a>
                  </div
                  <small class="text-muted"><i class="far fa-smile">  <c:out value="${quest.score}"/></i></small>
               </div>
            </div>
         </div>
      </div>
</c:forEach>
</div>
<ul class="pagination justify-content-center">

         <paginator:display currentPage="${requestScope.currentPage}" numberOfPages="${requestScope.numberOfPages}" viewPageCount="1" url="frontController?command=quest&" />

        </ul>


  </div>
  </div>
  <%@ include file = "part/footer.jsp" %>