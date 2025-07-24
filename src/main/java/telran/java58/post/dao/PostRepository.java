package telran.java58.post.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import telran.java58.post.model.Post;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface PostRepository extends MongoRepository<Post, String> {
    Stream<Post> findByAuthorIgnoreCase(String author);
    //Works fine, but gives warning
    Stream<Post> findPostsByTagsContainingIgnoreCase(List<String> tags);
    //Works fine, but gives warning
    Stream<Post> findByDateCreatedBetween(LocalDate dateFrom, LocalDate dateTo);
}
