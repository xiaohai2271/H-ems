package cn.celess.j2ee.filter;

import cn.celess.j2ee.entity.db.User;
import cn.celess.j2ee.util.UserContext;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 2021/10/28
 * TODO:
 *
 * @author 禾几海
 */
@Component
@WebFilter(filterName = "authFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) servletRequest).getSession(true);
        User user = (User) session.getAttribute("user");
        if (user != null)
            UserContext.set(user);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
