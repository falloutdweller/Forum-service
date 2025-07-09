package telran.java58.forum.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    @Setter
    private String user;
    private String message;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private int likes;
}

