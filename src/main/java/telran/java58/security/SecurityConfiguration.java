package telran.java58.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import telran.java58.accounting.model.Role;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final CustomWebSecurity webSecurity;

    @Bean
    SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic(Customizer.withDefaults());
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/account/register", "/forum/posts/**")
                        .permitAll()
                        .requestMatchers("/account/user/{login}/role/{role}")
                        .hasRole(Role.ADMINISTRATOR.name())
                        .requestMatchers(HttpMethod.PATCH, "/account/user/{login}")
                        .access(new WebExpressionAuthorizationManager("#login == authentication.name"))
                        .requestMatchers(HttpMethod.DELETE, "/account/user/{login}")
                        .access(new WebExpressionAuthorizationManager("#login == authentication.name or hasRole('ADMINISTRATOR')"))
                        .requestMatchers(HttpMethod.POST, "/forum/post/{author}")
                        .access(new WebExpressionAuthorizationManager("#author == authentication.name"))
                        .requestMatchers(HttpMethod.PATCH, "/forum/post/{id}/comment/{author}")
                        .access(new WebExpressionAuthorizationManager("#author == authentication.name"))
                        .requestMatchers(HttpMethod.PATCH, "/forum/post/{id}")
                        .access((auth, context) ->
                                new AuthorizationDecision(webSecurity.isPostAuthor(auth.get().getName(), context.getVariables().get("id"))))
                        .requestMatchers(HttpMethod.DELETE, "/forum/post/{id}")
                        .access((auth, context) -> {
                            boolean isAuthor = webSecurity.isPostAuthor(auth.get().getName(), context.getVariables().get("id"));
//                            boolean isMod =
//                                    auth.get().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals(Role.MODERATOR.name()));
                            boolean isMod = context.getRequest().isUserInRole("MODERATOR");
                            return new AuthorizationDecision(isMod || isAuthor);
                        })
                        .anyRequest()
                        .authenticated()
        );
        return http.build();
    }

}
