package com.ensak.connect.blog_post.service;

import com.ensak.connect.auth.AuthenticationService;
import com.ensak.connect.auth.enums.Role;
import com.ensak.connect.auth.model.User;
import com.ensak.connect.backoffice.dto.DashboardResponseDTO;
import com.ensak.connect.blog_post.dto.BlogPostRequestDTO;
import com.ensak.connect.blog_post.dto.BlogPostResponseDTO;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.repository.BlogPostRepository;
import com.ensak.connect.config.exception.ForbiddenException;
import com.ensak.connect.config.exception.NotFoundException;
import com.ensak.connect.job_post.dto.JobPostRequestDTO;
import com.ensak.connect.job_post.model.JobPost;
import com.ensak.connect.job_post.repository.JobPostRepository;
import com.ensak.connect.resource.ResourceService;
import com.ensak.connect.resource.model.Resource;
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
    private final ResourceService resourceService;

    public BlogPost getBlogPostById(Integer id) {
        return blogPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find blog post with id "+ id + ".")
        );

    }

    public BlogPostResponseDTO getBlogPostByIdMapped(Integer id) {
        User author = authenticationService.getAuthenticatedUser();
        var blog = blogPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find blog post with id "+ id + ".")
        );
        return BlogPostResponseDTO.map(blog, author.getId());
    }

    public boolean existsBlogPost(Integer authorId, Integer blogPostId){
        return blogPostRepository.existsBlogPostByIdAndAuthorId(blogPostId, authorId);
    }

    public List<BlogPostResponseDTO> getBlogPosts() {
        User author = authenticationService.getAuthenticatedUser();
        return BlogPostResponseDTO.map(blogPostRepository.findAll(), author.getId());
    }

    @SneakyThrows
    public BlogPostResponseDTO createBlogPost(BlogPostRequestDTO request) {
        User author = authenticationService.getAuthenticatedUser();
        return BlogPostResponseDTO.map(blogPostRepository.save(
                BlogPost.builder()
                        .content(request.getContent())
                        .author(author)
                        .tags(request.getTags())
                        .resources(resourceService.useResources(request.getResources(),author))
                        .build()
        ), author.getId());
    }

    @SneakyThrows
    @Transactional
    public BlogPostResponseDTO updateBlogPostById(Integer id, BlogPostRequestDTO request) {
        User author = authenticationService.getAuthenticatedUser();
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find blog post with id " + id + ".")
        );

        blogPost.setContent(request.getContent());
        blogPost.setTags(request.getTags());
        blogPost.setResources(resourceService.updateUsedResource(
                blogPost.getResources().stream().map(Resource::getId).toList(),
                request.getResources(),
                author
                )
        );

        return BlogPostResponseDTO.map(blogPostRepository.save(blogPost), author.getId());
    }

    @SneakyThrows
    public void deleteBlogPostById(Integer id) {

        User author = authenticationService.getAuthenticatedUser();
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Could not find blog post with id " + id + ".")
        );

        if (!author.getId().equals(blogPost.getAuthor().getId()) && !author.getRole().equals(Role.ROLE_ADMIN)) {
            throw new ForbiddenException("Cannot delete posts made by other users");
        }
        resourceService.unuseResources(
                blogPost.getResources().stream().map(Resource::getId).toList(),
                author
                );

        blogPostRepository.deleteById(id);
    }

    public List<BlogPost> retrieveByTags(List<String> tags) {
        return blogPostRepository.retrieveByTags(tags);
    }

    public DashboardResponseDTO getCountPostsMonthly () {
        return DashboardResponseDTO.mapO(blogPostRepository.countByMonthOfCurrentYear());
    }
}
