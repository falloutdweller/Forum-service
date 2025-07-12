package telran.java58.forum.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class CommentDto {
    private String user;
    private String message;
    private LocalDateTime dateCreated;
    private int likes;
}

