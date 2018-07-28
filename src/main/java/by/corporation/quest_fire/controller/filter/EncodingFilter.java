package by.corporation.quest_fire.controller.filter;



import javax.servlet.*;
import java.io.IOException;



public class EncodingFilter implements Filter {



    private static final String ENCODING_PARAMETER = "encoding";
    private static final String DEFAULT_ENCODING = "UTF-8";

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getServletContext().getInitParameter(ENCODING_PARAMETER);
        if (encoding == null) {
            encoding = DEFAULT_ENCODING;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
