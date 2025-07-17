package telran.java58.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Order(30)
@Component
@RequiredArgsConstructor
public class UserUpdateFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (checkEndPoint(request.getMethod(),request.getServletPath())) {
            String login = request.getUserPrincipal().getName();
            String path = request.getRequestURI();
            if (!path.substring("/account/user/".length()).equals(login)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
    private boolean checkEndPoint(String method, String path) {
        return HttpMethod.PATCH.matches(method) && path.matches("/account/user/\\w+");
    }
}
