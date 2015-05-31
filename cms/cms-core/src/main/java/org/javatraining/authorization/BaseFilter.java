package org.javatraining.authorization;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by olga on 29.05.15.
 */
public abstract class BaseFilter implements Filter {
    protected String token;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        token = ((HttpServletRequest) request).getHeader(Config.REQUEST_HEADER_TOKEN);
    }

    @Override
    public void destroy() {
    }
}
