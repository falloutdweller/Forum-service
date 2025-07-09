package telran.java58.forum.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document(collection = "forum")
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor

public class Post {
    private String id;
    @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private String author;
    private LocalDateTime dateCreated = LocalDateTime.now();
    @Setter
    private Set<String> tags;
    private int likes;
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content, List<String> tags) {
        this.title = title;
        this.content = content;
        this.tags = new HashSet<>(tags);
        this.dateCreated = LocalDateTime.now();
    }

    public void addLike() {
        likes++;
    }
    public void addComment(Comment message) {
        comments.add(message);
    }
}
