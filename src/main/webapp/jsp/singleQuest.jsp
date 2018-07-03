<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "part/head.jsp" %>

  <div class="container">
      <div class="row">
        <div class="col-lg-8">
          <h1 class="mt-4"><c:out value="${quest.name}"/></h1>
          <p class="lead">
            by
            <a href="#">Start Bootstrap</a>
          </p>
          <hr>
          <p><c:out value="${quest.genre}"/></p>
          <hr>
          <img id = "singleQuest" class="img-fluid rounded" src="${quest.image}" alt="">
          <hr>
          <p class="lead"><c:out value="${quest.description}"/>
          <blockquote class="blockquote">
            <p class="mb-0">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>
            <footer class="blockquote-footer">Someone famous in
              <cite title="Source Title">Source Title</cite>
            </footer>
          </blockquote>
          <hr>
          </p>
          <c:if test="${not empty user.email}">
          <div class="card my-4">
            <h5 class="card-header">Leave a Comment:</h5>
            <div class="card-body">
              <form action="frontController" method="POST">
              <input type="hidden" name="command" value="comment"/>
                <div class="form-group">
                  <textarea class="form-control" rows="3" name="description"></textarea>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
              </form>
            </div>
          </div>


          <c:forEach items="${comment}" var="comments">
          <c:if test="${comments.status == 'APPROVED'}">
          <div class="media mb-4">
            <img class="d-flex mr-3 rounded-circle" src="http://placehold.it/50x50" alt="">
            <div class="media-body">
              <h5 class="mt-0">
              <span><c:out value="${comments.user.firstName}" /></span>
               <span><c:out value="${comments.user.lastName}" /></span>
               <span><c:out value="${comments.quest.name}" /></span>
              </h5>
             <p><c:out value="${comments.description}" />
            </div>
          </div>
          </c:if>
     </c:forEach>
     </c:if>



        </div>

        <div class="col-md-4">
          <div class="card my-4">
            <h5 class="card-header">Rate It!</h5>
            <div class="card-body">
              <div class="input-group">

                 <a  onclick="disableButton()" type="button" id = "rating" class="btn btn-danger" href ="frontController?command=rateQuest&questId=${quest.questId}&score=1">1</a>
                  <a onclick="disableButton()" type="button" id = "rating" class="btn btn-danger" href ="frontController?command=rateQuest&questId=${quest.questId}&score=2">2</a>
                  <a onclick="disable();" type="button" id = "rating" class="btn btn-danger" href ="frontController?command=rateQuest&questId=${quest.questId}&score=3">3</a>
              </div>
                          </div>
                        </div>


   <div class="card my-4">
             <h5 class="card-header"  style="color: #dc3545;">Like it? Book it!</h5>
             <div class="card-body">
             <div class="accordion" id="accordionExample">
               <div class="card">
                 <div class="card-header" id="headingOne">
                   <h5 class="mb-0">
                     <button  style="color: #dc3545;" class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
                      Select Day:
                     </button>
                   </h5>
                 </div>

                 <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordionExample">
                   <div class="card-body">
<div class="cal">
  <div class="cal__header">
    <button class="btn btn-action btn-link btn-lg" data-calendar-toggle="previous"><svg height="24" version="1.1" viewbox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg">
    <path d="M20,11V13H8L13.5,18.5L12.08,19.92L4.16,12L12.08,4.08L13.5,5.5L8,11H20Z"></path></svg></button>
    <div class="cal__header__label" data-calendar-label="month">
      March 2017
    </div><button class="btn btn-action btn-link btn-lg" data-calendar-toggle="next"> <svg height="24" version="1.1" viewbox="0 0 24 24" width="24" xmlns="http://www.w3.org/2000/svg">
    <path d="M4,11V13H16L10.5,18.5L11.92,19.92L19.84,12L11.92,4.08L10.5,5.5L16,11H4Z"></path></svg></button>
  </div>
  <div class="cal__week">
    <span>Mon</span> <span>Tue</span><span>Wed</span> <span>Thu</span> <span>Fri</span> <span>Sat</span> <span>Sun</span>
  </div>
  <div class="cal__body" data-calendar-area="month"></div>
</div>
         </div>
         </div>
         </div>
               <div class="card">
                 <div class="card-header" id="headingTwo">
                   <h5 class="mb-0">
                     <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                      Select number of people:
                     </button>
                   </h5>
                 </div>
                 <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionExample">
                   <div class="card-body">
                   <div class="card-body">
                                  <div class="form-check radio-green">
                                     <input class="form-check-input" name="group101" type="radio" id="radio103" value="2">
                                     <label class="form-check-label" for="radio103">2</label>
                                 </div>

                                 <div class="form-check radio-green">
                                     <input class="form-check-input" name="group101" type="radio" id="radio103" checked value="3">
                                     <label class="form-check-label" for="radio103">3</label>
                                 </div>

                                 <div class="form-check radio-green">
                                           <input class="form-check-input" name="group101" type="radio" id="radio103" checked value="4">
                                          <label class="form-check-label" for="radio103">4</label>
                                 </div>
                                    </div>
                   </div>
                 </div>
               </div>
               <div class="card">
                 <div class="card-header" id="headingThree">
                   <h5 class="mb-0">
                     <button class="btn btn-link collapsed" type="button" data-toggle="collapse" data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                      Approve booking:
                     </button>
                   </h5>
                 </div>
                 <div id="collapseThree" class="collapse" aria-labelledby="headingThree" data-parent="#accordionExample">
                   <div class="card-body">
                   <p>Please, enter your email. </p>
                   <input class="form-control" type="text" placeholder="email">
                    <div class="card-header" id="headingOne">
                   <p><button type="button" class="btn btn-danger">Approve it!</button><p>
                   </div>
                   </div>
                 </div>
               </div>
             </div>
             </div>
           </div>

         </div>

       </div>

 <%@ include file = "part/footer.jsp" %>