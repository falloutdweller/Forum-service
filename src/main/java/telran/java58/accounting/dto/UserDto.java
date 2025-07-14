package telran.java58.accounting.dto;

import lombok.Getter;
import lombok.Singular;

import java.util.Set;

@Getter
public class UserDto {

    private String login;
    private String firstName;
    private String lastName;
    @Singular
    private Set<String> roles;
}
