package telran.java58.post.service;

import telran.java58.post.dto.NewCommentDto;
import telran.java58.post.dto.PostAddUpdateDto;
import telran.java58.post.dto.PostDto;

import java.time.LocalDate;
import java.util.List;

public interface PostService {

    PostDto addPost(String author, PostAddUpdateDto postAddUpdateDto);

    PostDto findPostById(String id);

    void addLike(String id);

    List<PostDto> findPostsByAuthor(String author);

    PostDto addComment(String id, String user, NewCommentDto newCommentDto);

    PostDto deletePost(String id);

    List<PostDto> findPostsByTags(List<String> tags);

    List<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo);

    PostDto updatePost(String id, PostAddUpdateDto postAddUpdateDto);

}
