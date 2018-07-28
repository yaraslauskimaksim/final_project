package by.corporation.quest_fire.controller.filter;

import by.corporation.quest_fire.controller.util.Constants;
import by.corporation.quest_fire.entity.Role;
import by.corporation.quest_fire.entity.Status;
import by.corporation.quest_fire.util.BundleResourceManager;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig)  {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();

        Role role = (Role) session.getAttribute(Constants.ROLE);
        Status status = (Status) session.getAttribute(Constants.STATUS);

        if (role == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + BundleResourceManager.getConfigProperty(Constants.PATH_HOME));
            return;
        }

        switch (role) {
            case ADMINISTRATOR:
                chain.doFilter(request, response);
                break;
            case QUEST_OWNER:
                chain.doFilter(request, response);
                break;
            case CLIENT:
                if(status.equals(Status.ACTIVE)) {
                    httpResponse.sendRedirect(httpRequest.getContextPath() + BundleResourceManager.getConfigProperty(Constants.PATH_HOME));
                    break;
                }else {
                    chain.doFilter(request, response);
                    break;
                }

        }
    }


    @Override
    public void destroy() {

    }
}