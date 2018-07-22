<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "../part/header.jsp" %>

<hr>
<div class="row" >
<div class="col-xl-3 col-sm-6 mb-3"  style="padding-left: 2rem; ">
   <div class="card text-white bg-primary o-hidden h-100">
      <div class="card-body">
         <div class="card-body-icon">
            <h1><i class="fa fa-fw fa-comments"></i></h1>
         </div>
         <div class="mr-5">Comments </div>
      </div>
      <a class="card-footer text-white clearfix small z-1" href="${_contextPath}/frontController?command=show&page=1">
      <span class="float-left">View</span>
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
            <h1><i class="far fa-envelope-open"></i></h1>
         </div>
         <div class="mr-5">Messages</div>
      </div>
     <a class="card-footer text-white clearfix small z-1" href="${_contextPath}/frontController?command=showUserMessages&page=1">
         <span class="float-left">View</span>
         <span class="float-right">
         <i class="fa fa-angle-right"></i>
         </span>
         </a>
   </div>
</div>




</div>

</div>
</hr>
<%@ include file = "../part/footer.jsp" %>