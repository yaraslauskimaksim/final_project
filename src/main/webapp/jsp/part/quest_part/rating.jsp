<h5>
   <c:if test="${empty userId}"> Please, Sign in to rate the quest. <i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i><i class="fas fa-star"></i> </c:if>
</h5>
<p>
   <c:if test="${ not empty userId}">
<form id="rating_form" action="frontController" method="POST"  style="padding=115px">
<input type="hidden" name="command" value="rateQuest"/>
<h5 class="card-header"><button type="submit" class="btn btn-dark" id="rating" style="padding-left:15px">Rate It! </button>
<span> ${sessionScope.ratingSaved}</span>
<c:remove var="ratingSaved" scope="session" />
</h5>
<ul class="rate-area">
<input type="radio" id="5-star" name="score" value="5" required/><label for="5-star" title="Amazing">5 stars</label>
<input type="radio" id="4-star" name="score" value="4" required/><label for="4-star" title="Good">4 stars</label>
<input type="radio" id="3-star" name="score" value="3" required/><label for="3-star" title="Average">3 stars</label>
<input type="radio" id="2-star" name="score" value="2" required/><label for="2-star" title="Not Good">2 stars</label>
<input type="radio" id="1-star" name="score" value="1" required/><label for="1-star" title="Bad">1 star</label>
</ul>
<h5><span> ${sessionScope.noRating} </span>
<c:remove var="noRating" scope="session" /></h5>
</c:if>
</form></p>