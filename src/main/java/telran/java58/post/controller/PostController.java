package telran.java58.post.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import telran.java58.post.dto.NewCommentDto;
import telran.java58.post.dto.PostAddUpdateDto;
import telran.java58.post.dto.PostDto;
import telran.java58.post.service.PostService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/forum")
@RequiredArgsConstructor

public class PostController {
    private final PostService postService;

    @PostMapping("/post/{author}")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto addPost(@PathVariable String author, @RequestBody @Valid PostAddUpdateDto postAddUpdateDto) {
        return postService.addPost(author, postAddUpdateDto);
    }

    @GetMapping("/post/{id}")
    public PostDto findPostById(@PathVariable String id) {
        return postService.findPostById(id);
    }

    @PatchMapping("/post/{id}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(@PathVariable String id) {
        postService.addLike(id);
    }

    @GetMapping("/posts/author/{author}")
    public List<PostDto> findPostsByAuthor(@PathVariable String author) {
        return postService.findPostsByAuthor(author);
    }

    @PatchMapping("/post/{id}/comment/{user}")
    public PostDto addComment(@PathVariable String id, @PathVariable String user, @RequestBody @Valid NewCommentDto newCommentDto) {
        return postService.addComment(id, user, newCommentDto);
    }

    @DeleteMapping("/post/{id}")
    public PostDto deletePost(@PathVariable String id) {
        return postService.deletePost(id);
    }

    @GetMapping("/posts/tags")
    public List<PostDto> findPostsByTags(@RequestParam List<String> values) {
        return postService.findPostsByTags(values);
    }

    @GetMapping("/posts/period")
    public List<PostDto> findPostsByPeriod(@RequestParam LocalDate dateFrom, @RequestParam LocalDate dateTo) {
        return postService.findPostsByPeriod(dateFrom, dateTo);
    }

    @PatchMapping("/post/{id}")
    public PostDto updatePost(@PathVariable String id, @RequestBody PostAddUpdateDto postAddUpdateDto) {
        return postService.updatePost(id, postAddUpdateDto);
    }
}
