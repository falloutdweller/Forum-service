package telran.java58.security.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

import java.security.Principal;
import java.util.Set;

@Getter
@AllArgsConstructor
@Builder
public class UserSecurity implements Principal {
    private String name;
    @Singular
    private Set<String> roles;
}
