<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "part/login_register_part/header.jsp" %>



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
                        			<h3>Login to our site. <span> ${sessionScope.errorInLogin}</span>
                        			<c:remove var="errorInLogin" scope="session" />
                        			</h3>
                            		<p>Enter your email and password to log on: </p>
                        		</div>
                        		<div class="form-top-right">
                        			<i class="fa fa-lock"></i>
                        		</div>
                            </div>
                            <div class="form-bottom">
                            <form role="form" class="login-form"  name="loginForm" action="frontController" method="POST">
                              <input type="hidden" name="command" value="login"/>
			                    	<div class="form-group">
			                    		<label class="sr-only" for="form-email">Email</label>
			                        	<input type="text" name="email" placeholder="Email..." class="form-username form-control" id="form-username">
			                        </div>
			                        <div class="form-group">
			                        	<label class="sr-only" for="form-password">Password</label>
			                        	<input type="password" name="password" placeholder="Password..." class="form-password form-control" id="form-password">
			                        </div>
			                        <button type="submit" class="btn">Sign in!</button>
			                    </form>
		                    </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3 social-login">
                        	<h3>...or create an account:</h3>
                        	<div class="social-login-buttons">
	                        	<a class="btn btn-link-2" href="/register">
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
