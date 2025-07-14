package telran.java58.accounting.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class RoleUpdateDto {
    private String login;
    private Set<String> roles;
}
