package telran.java58.forum.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class PostAddUpdateDto {
    private String title;
    private String content;
    private Set<String> tags;

}
