package com.ensak.connect.blog_post.service;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.blog_post.dto.BlogPostRequestDTO;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.blog_post.dto.BlogPostResponseDTO;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.repository.BlogPostRepository;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.question_post.model.QuestionPost;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BlogPostService {

    private final BlogPostRepository blogPostRepository;
    private final AuthenticationService authenticationService;

    public BlogPost getBlogPostById(Integer id){
        return blogPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find blog post with id" + id +".")

        );
    }

    public boolean existBlogPost(Integer authorId,Integer blogPostId){
        return blogPostRepository.existsBlogPostByIdAndAuthorId(authorId,blogPostId);
    }

    public  List<BlogPost> getBlogPosts(){
        return  blogPostRepository.findAll();
    }

    public  BlogPost createBlogPost(BlogPostRequestDTO request){
        User author = authenticationService.getAuthenticatedUser();
        return blogPostRepository.save(
                BlogPost.builder()
                        .title(request.getTitle())
                        .content(request.getContent())
                        .author(author)
                        .tags(request.getTags())
                        .build()

        );
    }

    @Transactional
    public BlogPost updateBlogPostById(Integer id,BlogPostRequestDTO request){
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find blog post with id :" + id +".")
        );

        blogPost.setTitle(request.getTitle());
        blogPost.setContent(request.getContent());
        blogPost.setTags(request.getTags());

        return blogPostRepository.save(blogPost);
    }

    @SneakyThrows
    public void deleteBlogPostById(Integer id){
        User author = authenticationService.getAuthenticatedUser();
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException("could not find blog post with id :"+ id + ".")
        );

        if (!author.getId().equals(blogPost.getAuthor().getId())){
            throw  new ForbiddenException("Connot delete posts made by others users");

        }

        blogPostRepository.deleteById(id);

    }

    public List<QuestionPost> retriveByTags(List<String> tags){
        return blogPostRepository.retrieveByTags(tags);
    }


}
