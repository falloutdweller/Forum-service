package telran.java58.forum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import telran.java58.forum.dto.NewCommentDto;
import telran.java58.forum.dto.PostAddUpdateDto;
import telran.java58.forum.dto.PostDto;
import telran.java58.forum.service.ForumService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/forum")
@RequiredArgsConstructor

public class ForumController {
    private final ForumService forumService;

    @PostMapping("/post/{author}")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto addPost(@PathVariable String author, @RequestBody PostAddUpdateDto postAddUpdateDto) {
        return forumService.addPost(author, postAddUpdateDto);
    }

    @GetMapping("/post/{id}")
    public PostDto findPostById(@PathVariable String id) {
        return forumService.findPostById(id);
    }

    @PatchMapping("/post/{id}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(@PathVariable String id) {
        forumService.addLike(id);
    }

    @GetMapping("/posts/author/{author}")
    public List<PostDto> findPostsByAuthor(@PathVariable String author) {
        return forumService.findPostsByAuthor(author);
    }

    @PatchMapping("/post/{id}/comment/{user}")
    public PostDto addComment(@PathVariable String id, @PathVariable String user, @RequestBody NewCommentDto newCommentDto) {
        return forumService.addComment(id, user, newCommentDto);
    }

    @DeleteMapping("/post/{id}")
    public PostDto deletePost(@PathVariable String id) {
        return forumService.deletePost(id);
    }

    @GetMapping("/posts/tags")
    public List<PostDto> findPostsByTags(@RequestParam List<String> values) {
        return forumService.findPostsByTags(values);
    }

    @GetMapping("/posts/period")
    public List<PostDto> findPostsByPeriod(@RequestParam LocalDate dateFrom, @RequestParam LocalDate dateTo) {
        return forumService.findPostsByPeriod(dateFrom, dateTo);
    }

    @PatchMapping("/post/{id}")
    public PostDto updatePost(@PathVariable String id, @RequestBody PostAddUpdateDto postAddUpdateDto) {
        return forumService.updatePost(id, postAddUpdateDto);
    }
}
