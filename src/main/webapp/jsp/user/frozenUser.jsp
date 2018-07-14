<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file = "../part/frozen_user_part/header.jsp" %>

        <div class="container">
            <div class="row">
                <div class="col-md-6 col-md-offset-3" id="form_container">
                    <h3>Contact Us for Activate Your Account!</h3>
                    <h3>${sessionScope.success}</h3>
                    <h3>${sessionScope.error}</h3>
                    <c:if test="${empty sessionScope.success}">
                    <p> Please send your message below. We will get back to you at the earliest! </p>
                    <form role="form" method="post" id="contact_form" action="${_contextPath}/frontController" method="POST">
                       <input type="hidden" name="command" value="contact"/>
                        <div class="row">
                            <div class="col-sm-12 form-group">
                                <label for="message"> Message:</label>
                                <textarea class="form-control" type="textarea" name="message" id="message" maxlength="6000" rows="7"></textarea>
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-sm-12 form-group">
                                <button type="submit" class="btn btn-lg btn-default pull-right" >Send &rarr;</button>
                            </div>
                        </div>
                    </form>
                    </c:if>
             </div>
            </div>
        </div>

<%@ include file = "../part/footer.jsp" %>