package telran.java58.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import telran.java58.forum.dao.ForumRepository;
import telran.java58.forum.model.Post;

@RequiredArgsConstructor
@Service
public class CustomWebSecurity {
    private final ForumRepository forumRepository;

    public boolean isPostAuthor(String login, String postId) {
        Post post = forumRepository.findById(postId).orElse(null);
        return post != null && post.getAuthor().equalsIgnoreCase(login);
    }
}
