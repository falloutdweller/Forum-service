package telran.java58.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import telran.java58.accounting.dao.AccountingRepository;
import telran.java58.accounting.model.Role;
import telran.java58.accounting.model.User;
import telran.java58.forum.dao.ForumRepository;
import telran.java58.forum.model.Post;

import java.io.IOException;

@Order(60)
@Component
@RequiredArgsConstructor
public class PostUpdataDeleteFilter implements Filter {
    private final ForumRepository forumRepository;
    private final AccountingRepository accountingRepository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (checkEndPoint(request.getMethod(), request.getServletPath())) {
            try {
                String login = request.getUserPrincipal().getName();
                String idToCheck = request.getRequestURI().substring("/forum/post/".length());
                Post post = forumRepository.findById(idToCheck).orElseThrow(RuntimeException::new);
                if (!HttpMethod.GET.matches(request.getMethod())) {
                    User user = accountingRepository.findById(login).orElseThrow(RuntimeException::new);
                    if (!(HttpMethod.DELETE.matches(request.getMethod()) || user.getRoles().contains(Role.MODERATOR))) {
                        if (!post.getAuthor().equals(login)) {
                            throw new RuntimeException();
                        }
                    }
                }
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }


    private boolean checkEndPoint(String method, String path) {
        return HttpMethod.GET.matches(method) && path.matches("/forum/post/\\w+")
                || HttpMethod.PATCH.matches(method) && path.matches("/forum/post/\\w+")
                || HttpMethod.DELETE.matches(method) && path.matches("/forum/post/\\w+");
    }
}
