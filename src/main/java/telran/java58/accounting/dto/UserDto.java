package telran.java58.accounting.dto;

import lombok.*;

import java.util.Set;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDto {
    private String login;
    private String firstName;
    private String lastName;
    @Singular
    private Set<String> roles;
}
