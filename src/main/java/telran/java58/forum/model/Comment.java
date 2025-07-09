package telran.java58.forum.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@NoArgsConstructor
public class Comment {
    @Setter
    private String user;
    private String message;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private int likes = 0;

    public Comment(String user, String message) {
        this.user = user;
        this.message = message;
    }
}
