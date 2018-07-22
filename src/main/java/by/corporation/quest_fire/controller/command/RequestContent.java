package by.corporation.quest_fire.controller.command;

import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.util.BundleResourceManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.util.*;

public class RequestContent {

    private String requestURL;
    private String referer;
    private Map<String, String[]> requestParameters;
    private Map<String, Object> sessionAttributes;
    private Collection<Part> parts;

    public RequestContent(HttpServletRequest request) {
        sessionAttributes = new HashMap<>();
        requestParameters = new HashMap<>(request.getParameterMap());
        requestURL = request.getRequestURL().append("?").append(request.getQueryString() == null ? "" : request.getQueryString()).toString();
        if (request.getHeader("Referer") != null) {
            referer = request.getHeader("Referer").equals("http://localhost:8080/controller") ? BundleResourceManager.getConfigProperty(Constants.PATH_HOME) : request.getHeader("Referer");
        } else {
            referer = null;
        }
        HttpSession currentSession = request.getSession(false);
        if (currentSession != null) {
            Enumeration<String> sessionAttributeNames = currentSession.getAttributeNames();
            while (sessionAttributeNames.hasMoreElements()) {
                String attributeName = sessionAttributeNames.nextElement();
                sessionAttributes.put(attributeName, currentSession.getAttribute(attributeName));
            }
        }
    }

    public String[] getRequestParameterValues(String parameter) {
        return requestParameters.get(parameter);
    }

    public String getRequestParameter(String parameter) {
        return requestParameters.get(parameter) == null ? null : requestParameters.get(parameter)[0];
    }

    public Object getSessionAttribute(String attribute) {
        return sessionAttributes.get(attribute);
    }

    public String getRequestURL() {
        return requestURL;
    }

    public String getReferer() {
        return referer;
    }

}