package telran.java58.forum.service;

import telran.java58.forum.dto.CommentDto;
import telran.java58.forum.dto.PostAddUpdateDto;
import telran.java58.forum.dto.PostDto;
import telran.java58.forum.model.Post;

import java.time.LocalDate;
import java.util.List;

public interface ForumService {

    Post addPost(String author, PostAddUpdateDto postAddUpdateDto);

    PostDto findPostById(String id);

    void addLike(String id);

    List<PostDto> findPostsByAuthor(String author);

    PostDto addComment(String id, String user, CommentDto message);

    PostDto deletePost(String id);

    List<PostDto> findPostsByTags(String tags);

    List<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo);

    PostDto updatePost(String id, PostAddUpdateDto postAddUpdateDto);

}
