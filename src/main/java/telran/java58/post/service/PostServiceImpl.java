package telran.java58.post.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import telran.java58.post.dao.PostRepository;
import telran.java58.post.dto.NewCommentDto;
import telran.java58.post.dto.PostAddUpdateDto;
import telran.java58.post.dto.PostDto;
import telran.java58.post.dto.exceptions.NotFoundException;
import telran.java58.post.model.Comment;
import telran.java58.post.model.Post;
import telran.java58.post.service.logging.PostLogger;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDto addPost(String author, PostAddUpdateDto postAddUpdateDto) {
        Post post = new Post(postAddUpdateDto.getTitle(), postAddUpdateDto.getContent(), author, postAddUpdateDto.getTags());
        postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto findPostById(String id) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(post, PostDto.class);
    }

    @PostLogger
    @Override
    public void addLike(String id) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundException::new);
        post.addLike();
        postRepository.save(post);
    }

    @Override
    public List<PostDto> findPostsByAuthor(String author) {
        return postRepository.findByAuthorIgnoreCase(author)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }

    @Override
    public PostDto addComment(String id, String user, NewCommentDto newCommentDto) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundException::new);
        Comment comment = new Comment(user, newCommentDto.getMessage());
        post.addComment(comment);
        postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto deletePost(String id) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundException::new);
        postRepository.delete(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public List<PostDto> findPostsByTags(List<String> tags) {
        return postRepository.findPostsByTagsContainingIgnoreCase(tags)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }

    @Override
    public List<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        return postRepository.findByDateCreatedBetween(dateFrom, dateTo)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }

    @PostLogger
    @Override
    public PostDto updatePost(String id, PostAddUpdateDto postAddUpdateDto) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundException::new);
        if (postAddUpdateDto.getTitle() != null) {
            post.setTitle(postAddUpdateDto.getTitle());
        }
        if (postAddUpdateDto.getContent() != null) {
            post.setContent(postAddUpdateDto.getContent());
        }
        if (postAddUpdateDto.getTags() != null) {
            post.setTags(postAddUpdateDto.getTags());
        }
        postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }
}
