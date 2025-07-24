package telran.java58.accounting.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegisterDto {
    @NotBlank(message = "login is required")
@Size(min = 3,max = 20, message = "login should be between 3 and 20 symbols")
    private String login;
    @NotBlank(message = "password is required")
    @Size(min = 4, max = 20, message = "password should be between 4 and 20 symbols")
    private String password;
    @NotBlank(message = "firstName is required")
    @Size(min = 2, max = 20, message = "firstName should be between 2 and 20 symbols")
    private String firstName;
    @NotBlank(message = "lastName is required")
    @Size(min = 2, max = 20, message = "lastName should be between 2 and 20 symbols")
    private String lastName;
}
