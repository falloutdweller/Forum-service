package telran.java58.forum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import telran.java58.forum.dto.CommentDto;
import telran.java58.forum.dto.PostAddUpdateDto;
import telran.java58.forum.dto.PostDto;
import telran.java58.forum.model.Post;
import telran.java58.forum.service.ForumService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/forum")
@RequiredArgsConstructor

public class ForumController {
    private final ForumService forumService;

    @PostMapping("/post/{user}")
    public Post addPost(@PathVariable("user") String author, @RequestBody PostAddUpdateDto postAddUpdateDto) {
        return forumService.addPost(author, postAddUpdateDto);
    }

    @GetMapping("/post/{postId}")
    public PostDto findPostById(@PathVariable("postId") String id) {
        return forumService.findPostById(id);
    }

    @PatchMapping("/post/{postId}/like")
    public void addLike(@PathVariable("postId") String id) {
        forumService.addLike(id);
    }

    @GetMapping("/posts/author/{user}")
    public List<PostDto> findPostsByAuthor(@PathVariable("user") String author) {
        return forumService.findPostsByAuthor(author);
    }

    @PatchMapping("/post/{postId}/comment/{commenter}")
    public PostDto addComment(@PathVariable("postId") String id, @PathVariable("commenter") String user, @RequestBody CommentDto message) {
        return forumService.addComment(id, user, message);
    }

    @DeleteMapping("/post/{postId}")
    public PostDto deletePost(@PathVariable("postId") String id) {
        return forumService.deletePost(id);
    }

    @GetMapping("/posts/tags")
    public List<PostDto> findPostsByTags(@RequestParam String values) {
        return forumService.findPostsByTags(values);
    }

    @GetMapping("/posts/period")
    public List<PostDto> findPostsByPeriod(@RequestParam LocalDate dateFrom, @RequestParam LocalDate dateTo) {
        return forumService.findPostsByPeriod(dateFrom, dateTo);
    }

    @PatchMapping("/post/{postId}")
    public PostDto updatePost(@PathVariable("postId") String id,@RequestBody PostAddUpdateDto postAddUpdateDto) {
        return forumService.updatePost(id, postAddUpdateDto);
    }
}
