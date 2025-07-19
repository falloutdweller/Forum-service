package telran.java58.security;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import telran.java58.accounting.model.Role;
import telran.java58.forum.dao.ForumRepository;
import telran.java58.forum.dto.exceptions.NotFoundException;
import telran.java58.forum.model.Post;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final ForumRepository forumRepository;

    @Bean
    SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/account/register", "/forum/posts/**")
                    .permitAll()
                .requestMatchers("/account/user/{login}/role/{role}")
                .hasRole(Role.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.PATCH, "/account/user/{login}")
                .access(new WebExpressionAuthorizationManager("#login == authentication.name"))
                .requestMatchers(HttpMethod.DELETE, "account/user/{login}")
                .access(new WebExpressionAuthorizationManager("#login == authentication.name or hasRole('ADMINISTRATOR')"))
                .requestMatchers(HttpMethod.POST, "/forum/post/{author}")
                .access(new WebExpressionAuthorizationManager("#author == authentication.name"))
                .requestMatchers(HttpMethod.PATCH, "/forum/post/{id}/comment/{author}")
                .access(new WebExpressionAuthorizationManager("#author == authentication.name"))
                .requestMatchers( "/forum/post/{id}")
                .access(((authentication, context) -> {
                    HttpServletRequest request = context.getRequest();

                    if (HttpMethod.DELETE.matches(request.getMethod()) && request.isUserInRole("MODERATOR")) {
                        return new AuthorizationDecision(true);
                    }
                    String idToCheck = request.getRequestURI().substring("/forum/post/".length());
                    Post post = forumRepository.findById(idToCheck).orElseThrow(NotFoundException::new);
                    boolean access = post.getAuthor().equals(authentication.get().getName());
                    return new AuthorizationDecision(access);
                }))
                .anyRequest()
                    .authenticated()
        );
        return http.build();
    }

}
