package telran.java58.security;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import telran.java58.accounting.dao.AccountingRepository;
import telran.java58.accounting.model.Role;
import telran.java58.security.model.UserSecurity;

import java.io.IOException;

@Component
@Order(20)
@RequiredArgsConstructor
public class AdminManagingRolesFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if (checkEndPoint(request.getServletPath())) {
            UserSecurity user = (UserSecurity) request.getUserPrincipal();
            if (!user.getRoles().contains(Role.ADMINISTRATOR.name())){
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean checkEndPoint(String path) {
        return path.matches("/account/user/\\w+/role/\\w+");
    }
}
