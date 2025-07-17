package telran.java58.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import telran.java58.accounting.dao.AccountingRepository;
import telran.java58.accounting.model.Role;
import telran.java58.accounting.model.User;
import telran.java58.security.model.UserSecurity;

import java.io.IOException;
import java.security.Principal;
import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@Order(10)
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {
    private final AccountingRepository repository;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (checkEndPoint(request.getMethod(), request.getServletPath())){
            try {
                String[] credentials = getCredentials(request.getHeader("Authorization"));
                System.out.println("Attempting to authenticate user: " + credentials[0]);
                User user = repository.findById(credentials[0]).orElseThrow(RuntimeException::new);
                if (!BCrypt.checkpw(credentials[1], user.getPassword())) {
                    throw new RuntimeException();
                }
                Set<String> roles = user.getRoles().stream()
                        .map(Role::name)
                        .collect(Collectors.toSet());
                request = new WrappedRequest(request, credentials[0], roles);
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
    }
        filterChain.doFilter(request, response);
    }

    private boolean checkEndPoint(String method, String path){
        return !(HttpMethod.POST.matches(method) && path.matches("/account/register"))
                || (HttpMethod.GET.matches(method) && path.matches("/forum/posts.*"));
    }
    private String[] getCredentials(String header) {
        String token = header.split(" ")[1];
        token = new String(Base64.getDecoder().decode(token));
        return token.split(":");
    }

    private static class WrappedRequest extends HttpServletRequestWrapper {
        private final String login;
        private final Set<String> roles;

        public WrappedRequest(HttpServletRequest request, String login, Set<String> roles) {
            super(request);
            this.login = login;
            this.roles = roles;
        }

        @Override
        public Principal getUserPrincipal() {
            return new UserSecurity(login, roles);
        }

    }
}
