package telran.java58.accounting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class RoleUpdateDto {
    private String login;
    private Set<String> roles;
}
