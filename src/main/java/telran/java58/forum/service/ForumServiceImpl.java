package telran.java58.forum.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import telran.java58.forum.dao.ForumRepository;
import telran.java58.forum.dto.CommentDto;
import telran.java58.forum.dto.NewCommentDto;
import telran.java58.forum.dto.PostAddUpdateDto;
import telran.java58.forum.dto.PostDto;
import telran.java58.forum.dto.exceptions.NotFoundException;
import telran.java58.forum.model.Comment;
import telran.java58.forum.model.Post;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ForumServiceImpl implements ForumService {
    private final ForumRepository forumRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDto addPost(String author, PostAddUpdateDto postAddUpdateDto) {
        Post post = new Post(postAddUpdateDto.getTitle(), postAddUpdateDto.getContent(), author, postAddUpdateDto.getTags());
        forumRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto findPostById(String id) {
        Post post = forumRepository.findById(id).orElseThrow(NotFoundException::new);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public void addLike(String id) {
        Post post = forumRepository.findById(id).orElseThrow(NotFoundException::new);
        post.addLike();
        forumRepository.save(post);
    }
    @Override
    public List<PostDto> findPostsByAuthor(String author) {
        return forumRepository.findByAuthorIgnoreCase(author)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }

    @Override
    public PostDto addComment(String id, String user, NewCommentDto newCommentDto) {
        Post post = forumRepository.findById(id).orElseThrow(NotFoundException::new);
        Comment comment = new Comment(user, newCommentDto.getMessage());
        post.addComment(comment);
        forumRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto deletePost(String id) {
        Post post = forumRepository.findById(id).orElseThrow(NotFoundException::new);
        forumRepository.delete(post);
        return modelMapper.map(post, PostDto.class);
    }
    @Override
    public List<PostDto> findPostsByTags(List<String> tags) {
        return forumRepository.findPostsByTagsContainingIgnoreCase(tags)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }
    @Override
    public List<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        return forumRepository.findByDateCreatedBetween(dateFrom, dateTo)
                .map(p -> modelMapper.map(p, PostDto.class))
                .toList();
    }

    @Override
    public PostDto updatePost(String id, PostAddUpdateDto postAddUpdateDto) {
        Post post = forumRepository.findById(id).orElseThrow(NotFoundException::new);
        if (postAddUpdateDto.getTitle() != null) {
            post.setTitle(postAddUpdateDto.getTitle());
        }
        if (postAddUpdateDto.getContent() != null) {
            post.setContent(postAddUpdateDto.getContent());
        }
        if (postAddUpdateDto.getTags() != null) {
            post.setTags(postAddUpdateDto.getTags());
        }
        forumRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }
}
