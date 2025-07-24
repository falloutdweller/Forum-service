package telran.java58.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
@Getter
public class PostAddUpdateDto {
    @NotBlank(message = "title is required")
    @Size(min = 3, max = 255, message = "title should be between 3 and 255 symbols")
    private String title;
    @NotBlank(message = "content is required")
    @Size(min = 10, message = "content should be at least 10 symbols")
    private String content;
    private Set<String> tags =  new HashSet<>();

}
