package telran.java58.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import telran.java58.post.dao.PostRepository;
import telran.java58.post.model.Post;

@RequiredArgsConstructor
@Service
public class CustomWebSecurity {
    private final PostRepository postRepository;

    public boolean isPostAuthor(String login, String postId) {
        Post post = postRepository.findById(postId).orElse(null);
        return post != null && post.getAuthor().equalsIgnoreCase(login);
    }
}
