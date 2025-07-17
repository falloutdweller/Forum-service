package telran.java58.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Order(70)
@RequiredArgsConstructor
public class PostCommentFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (checkEndPoint(request.getMethod(), request.getServletPath())) {
            String login = request.getUserPrincipal().getName();
            Pattern pattern = Pattern.compile("^/forum/post/(\\w+)/comment/([\\w.-]+)$");
            Matcher matcher = pattern.matcher(request.getRequestURI());
            if (matcher.matches()) {
                String commenter = matcher.group(2);
                if (!login.matches(commenter)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean checkEndPoint(String method, String path) {
        return HttpMethod.PATCH.matches(method) && path.matches("/forum/post/\\w+/comment/\\w+");
    }
}
