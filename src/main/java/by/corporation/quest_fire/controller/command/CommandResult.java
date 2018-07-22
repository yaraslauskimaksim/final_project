package by.corporation.quest_fire.controller.command;


import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class CommandResult {

    private String page;
    private RoutingType routingType;
    private String errorMessage;
    private Integer errorCode;
    private boolean sessionInvalidated;
    private Map<String, Object> sessionAttributes = new HashMap<>();
    private Map<String, Object> requestAttributes = new HashMap<>();

    public CommandResult(String page) {
        routingType = RoutingType.FORWARD;
        this.page = page;
    }

    public CommandResult(RoutingType routingType, String page) {
        this.routingType = routingType;
        this.page = page;
    }

    public CommandResult(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public void setSessionInvalidated(boolean sessionInvalidated) {
        this.sessionInvalidated = sessionInvalidated;
    }

    public void updateRequest(HttpServletRequest request) {
        requestAttributes.forEach(request::setAttribute);
        sessionAttributes.forEach(request.getSession()::setAttribute);
        if (sessionInvalidated) {
            request.getSession().invalidate();
        }
    }

    public Object putRequestAttribute(String attribute, Object value) {
        return requestAttributes.put(attribute, value);
    }

    public Object putSessionAttribute(String attribute, Object value) {
        return sessionAttributes.put(attribute, value);
    }

    public RoutingType getRoutingType() {
        return routingType;
    }

    public enum RoutingType {
        FORWARD, REDIRECT
    }
}

