package telran.java58.accounting.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserUpdateDto {
    @Size(min = 2, max = 20, message = "firstName should be between 2 and 20 symbols")
    private String firstName;
    @Size(min = 2, max = 20, message = "lastName should be between 2 and 20 symbols")
    private String lastName;
}
