<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "part/login_register_part/header.jsp" %>
    <body background="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQy5qddgAB5IVw1uPTuubGsDZoGMVdNYCqIdUPCqHxcP_VdTNnT">

        <!-- Top content -->
        <div class="top-content">

            <div class="inner-bg">
                <div class="container">
                    <div class="row">
                        <div class="col-sm-8 col-sm-offset-2 text">
                            <h1><strong>QuestFire</strong></h1>
                            <div class="description">

                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 form-box">
                        	<div class="form-top">
                        		<div class="form-top-left">
                        			<h3>Sign Up to our site</h3><span> ${sessionScope.invalidFields} </span>
                                    <c:remove var="error" scope="session" />
                                    <span> ${sessionScope.userExits} </span>
                                    <c:remove var="error" scope="session" />
                            		<p>Enter your personal data to register:</p>
                        		</div>
                        		<div class="form-top-right">
                        			<i class="fa fa-lock"></i>
                        		</div>
                            </div>
                            <div class="form-bottom">
                            <form role="form" class="login-form"  name="loginForm" action="frontController" method="POST">
                              <input type="hidden" name="command" value="register"/>
                              	<div class="form-group">
                              	<label class="sr-only" for="form-email">First Name</label>
                              	<input type="text" name="firstname" placeholder="First Name..." class="form-username form-control" id="form-username">
                              	</div>
                              		<div class="form-group">
                                    <label class="sr-only" for="form-email">Last Name</label>
                                    <input type="text" name="lastname" placeholder="Last Name..." class="form-username form-control" id="form-username">
                                    </div>
			                    	<div class="form-group">
			                    		<label class="sr-only" for="form-email">Email</label>
			                        	<input type="text" name="email" placeholder="Email..." class="form-username form-control" id="form-username">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="form-password">Password</label>
			                        	<input type="password" name="password" placeholder="Password..." class="form-password form-control" id="form-password">
			                        </div>
			                        <button type="submit" class="btn">Sign Up!</button>
			                    </form>
		                    </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 social-login">
                        	<h3>...or have an account:</h3>
                        	<div class="social-login-buttons">
	                        	<a class="btn btn-link-2" href="/login">
	                        		 Sign Up!
	                        	</a>
                               <a class="btn btn-link-2" href="/home">
	                        		 Go Back!
	                        	</a>
                        	</div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
<%@ include file = "part/login_register_part/footer.jsp" %>
