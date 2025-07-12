package telran.java58.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class PostDto {
    private String id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime dateCreated;
    private Set<String> tags;
    private Integer likes;
    private List<CommentDto> comments;
}
