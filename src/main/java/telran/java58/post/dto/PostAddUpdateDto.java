package telran.java58.post.dto;

import lombok.Getter;

import java.util.Set;
@Getter
public class PostAddUpdateDto {
    private String title;
    private String content;
    private Set<String> tags;

}
