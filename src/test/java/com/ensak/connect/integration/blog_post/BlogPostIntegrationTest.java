package com.ensak.connect.integration.blog_post;

import com.ensak.connect.auth.model.User;
import com.ensak.connect.blog_post.dto.BlogPostRequestDTO;
import com.ensak.connect.blog_post.dto.BlogPostResponseDTO;
import com.ensak.connect.blog_post.model.BlogPost;
import com.ensak.connect.blog_post.repository.BlogPostRepository;
import com.ensak.connect.config.exception.dto.HttpResponse;
import com.ensak.connect.integration.AuthenticatedBaseIntegrationTest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogPostIntegrationTest extends AuthenticatedBaseIntegrationTest {



    public static final String API_BLOG_POSTS = "/api/v1/blog-posts";
    @Autowired
    private MockMvc api;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BlogPostRepository blogPostRepository;


    @BeforeEach
    void tearDown() {
        blogPostRepository.deleteAll();
    }
    BlogPostRequestDTO blogPostTest = BlogPostRequestDTO.builder()
            .content("This is a blog content test")
            .tags(List.of(new String[]{"test1", "test2"}))
            .build();


    @Test
    public void itShouldCreateBlogPostWhenAuthenticated() throws Exception {
        this.authenticateAsUser();
        String requestJSON = objectMapper.writeValueAsString(blogPostTest);
        String response = api.perform(
                        post(API_BLOG_POSTS)
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }


    @Test
    public void itShouldNotCreateBlogPostWhenNotAuthenticated() throws Exception {
        String requestJSON = objectMapper.writeValueAsString(blogPostTest);
        String response = api.perform(
                        post(API_BLOG_POSTS)
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    public void itShouldNotCreateBlogPostWhenTagsAreMoreThanAllowed() throws Exception {
        this.authenticateAsUser();
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tags.add("tag " + i);
        }
        var request = blogPostTest;
        request.setTags(tags);
        String requestJSON = objectMapper.writeValueAsString(request);
        String response = api.perform(
                        post(API_BLOG_POSTS)
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().is4xxClientError())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldGetBlogPostWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var post = blogPostRepository.save(
                BlogPost.builder()
                        .content(blogPostTest.getContent())
                        .tags(blogPostTest.getTags())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        get(API_BLOG_POSTS + "/" + post.getId())
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BlogPostResponseDTO blogPostResponseDTO = objectMapper.readValue(response, BlogPostResponseDTO.class);

        Assertions.assertEquals(post.getId(), blogPostResponseDTO.getId());
        Assertions.assertEquals(post.getContent(), blogPostResponseDTO.getContent());
        Assertions.assertArrayEquals(post.getTags().toArray(), blogPostResponseDTO.getTags().toArray());
        Assertions.assertEquals(post.getAuthor().getId(), blogPostResponseDTO.getAuthor().getUserId());

    }

    @Test
    public void isShouldNotGetBlogPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var post = blogPostRepository.save(
                BlogPost.builder()
                        .content(blogPostTest.getContent())
                        .tags(blogPostTest.getTags())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        get(API_BLOG_POSTS + "/" + post.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldGetListBlogPostWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var post1 = blogPostRepository.save(
                BlogPost.builder()
                        .content(blogPostTest.getContent() + "1")
                        .tags(blogPostTest.getTags())
                        .author(user)
                        .build()
        );
        var post2 = blogPostRepository.save(
                BlogPost.builder()
                        .content(blogPostTest.getContent() + "2")
                        .tags(blogPostTest.getTags())
                        .author(user)
                        .build()
        );
        var post3 = blogPostRepository.save(
                BlogPost.builder()
                        .content(blogPostTest.getContent() + "3")
                        .tags(blogPostTest.getTags())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        get(API_BLOG_POSTS)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<BlogPostResponseDTO> blogPosts = objectMapper.readValue(response, new TypeReference<List<BlogPostResponseDTO>>(){});
        Assertions.assertNotNull(blogPosts);
        BlogPostResponseDTO postTest1 = blogPosts.get(0);
        BlogPostResponseDTO postTest2 = blogPosts.get(1);
        BlogPostResponseDTO postTest3 = blogPosts.get(2);

        Assertions.assertEquals(post1.getId(), postTest1.getId());
        Assertions.assertEquals(post1.getContent(), postTest1.getContent());
        Assertions.assertArrayEquals(post1.getTags().toArray(), postTest1.getTags().toArray());
        Assertions.assertEquals(post1.getAuthor().getId(), postTest1.getAuthor().getUserId());

        Assertions.assertEquals(post2.getId(), postTest2.getId());
        Assertions.assertEquals(post2.getContent(), postTest2.getContent());
        Assertions.assertArrayEquals(post2.getTags().toArray(), postTest2.getTags().toArray());
        Assertions.assertEquals(post2.getAuthor().getId(), postTest2.getAuthor().getUserId());

        Assertions.assertEquals(post3.getId(), postTest3.getId());
        Assertions.assertEquals(post3.getContent(), postTest3.getContent());
        Assertions.assertArrayEquals(post3.getTags().toArray(), postTest3.getTags().toArray());
        Assertions.assertEquals(post3.getAuthor().getId(), postTest3.getAuthor().getUserId());

    }

    @Test
    public void isShouldNotGetListBlogPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var post = blogPostRepository.save(
                BlogPost.builder()
                        .content(blogPostTest.getContent())
                        .tags(blogPostTest.getTags())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        get(API_BLOG_POSTS)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldUpdateBlogPostWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var post = blogPostRepository.save(
                BlogPost.builder()
                        .content(blogPostTest.getContent())
                        .tags(blogPostTest.getTags())
                        .author(user)
                        .build()
        );

        var postUpdated = BlogPostRequestDTO.builder()
                .content(blogPostTest.getContent() + " updated")
                .tags(blogPostTest.getTags())
                .build();

        String requestJSON = objectMapper.writeValueAsString(postUpdated);

        String response = api.perform(
                        put(API_BLOG_POSTS + "/" + post.getId())
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isAccepted())
                .andReturn()
                .getResponse()
                .getContentAsString();
        BlogPostResponseDTO blogPostResponseDTO = objectMapper.readValue(response, BlogPostResponseDTO.class);

        Assertions.assertEquals(post.getId(), blogPostResponseDTO.getId());
        Assertions.assertNotEquals(post.getContent(), blogPostResponseDTO.getContent());
        Assertions.assertNotEquals(post.getTags(), blogPostResponseDTO.getTags());
        Assertions.assertEquals(post.getAuthor().getId(), blogPostResponseDTO.getAuthor().getUserId());
        Assertions.assertEquals(postUpdated.getContent(), blogPostResponseDTO.getContent());
        Assertions.assertEquals(postUpdated.getTags(), blogPostResponseDTO.getTags());

    }

    @Test
    public void isShouldNotUpdateBlogPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var post = blogPostRepository.save(
                BlogPost.builder()
                        .content(blogPostTest.getContent())
                        .tags(blogPostTest.getTags())
                        .author(user)
                        .build()
        );

        var postUpdated = BlogPostRequestDTO.builder()
                .content(blogPostTest.getContent() + " updated")
                .tags(blogPostTest.getTags())
                .build();

        String requestJSON = objectMapper.writeValueAsString(postUpdated);

        String response = api.perform(
                        put(API_BLOG_POSTS + "/" + post.getId())
                                .contentType(APPLICATION_JSON)
                                .content(requestJSON)
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldAllowUserToDeleteHisOwnBlogPostWhenAuthenticated() throws Exception {
        User user = this.authenticateAsUser();
        var post = blogPostRepository.save(
                BlogPost.builder()
                        .content(blogPostTest.getContent())
                        .tags(blogPostTest.getTags())
                        .author(user)
                        .build()
        );

        api.perform(
                        delete(API_BLOG_POSTS + "/" + post.getId())
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldNotAllowUserToDeleteHisOwnBlogPostWhenNotAuthenticated() throws Exception {
        User user = this.createDummyUser();
        var post = blogPostRepository.save(
                BlogPost.builder()
                        .content(blogPostTest.getContent())
                        .tags(blogPostTest.getTags())
                        .author(user)
                        .build()
        );

        api.perform(
                        delete(API_BLOG_POSTS + "/" + post.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    public void isShouldNotAllowUserToDeleteBlogPostMadeByOtherUsers() throws Exception {
        this.authenticateAsStudent();
        User user = this.createDummyUser();
        var post = blogPostRepository.save(
                BlogPost.builder()
                        .content(blogPostTest.getContent())
                        .tags(blogPostTest.getTags())
                        .author(user)
                        .build()
        );

        String response = api.perform(
                        delete(API_BLOG_POSTS + "/" + post.getId())
                )
                .andExpect(status().isForbidden())
                .andReturn()
                .getResponse()
                .getContentAsString();
        HttpResponse apiResponse = objectMapper.readValue(response, HttpResponse.class);

        Assertions.assertEquals("Cannot delete posts made by other users", apiResponse.getMessage());
    }
}
