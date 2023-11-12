package com.audition.web;

import com.audition.TestHelper;
import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import com.audition.service.AuditionService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AuditionControllerTest {

    private static final String INVALID_NUMBER = "3.5";

    private transient AuditionController auditionController;

    @MockBean
    private transient AuditionService auditionService;

    @BeforeEach
    public void setup() {
        final List<AuditionPost> auditionPosts = new ArrayList<>();
        auditionPosts.add(TestHelper.getAuditionPost(1, 1, "Title1", "Body1"));
        auditionPosts.add(TestHelper.getAuditionPost(2, 2, "Title2", "Body2"));
        auditionPosts.add(TestHelper.getAuditionPost(3, 3, "Title3", "Body3"));
        auditionPosts.add(TestHelper.getAuditionPost(4, 4, "Title4", "Body4"));

        final List<AuditionComment> auditionCommentsForPost = new ArrayList<>();
        auditionCommentsForPost.add(TestHelper.getAuditionComment(1, 1, "Name1", "name1@email.com", "Body1"));
        auditionCommentsForPost.add(TestHelper.getAuditionComment(1, 2, "Name2", "name2@email.com", "Body2"));

        Mockito.when(auditionService.getPosts()).thenReturn(auditionPosts);
        Mockito.when(auditionService.getComments("1")).thenReturn(auditionCommentsForPost);
        Mockito.when(auditionService.getCommentsByPostId("1")).thenReturn(auditionCommentsForPost);

        auditionController = new AuditionController(this.auditionService);
    }

    @Test
    void testGetPostsRange() {
        List<AuditionPost> auditionPosts = auditionController.getPosts(null, null);
        Assertions.assertEquals(4, auditionPosts.size());
        auditionPosts = auditionController.getPosts("2", null);
        Assertions.assertEquals(2, auditionPosts.get(0).getId());
        Assertions.assertEquals(3, auditionPosts.size());
        auditionPosts = auditionController.getPosts(null, "2");
        Assertions.assertEquals(1, auditionPosts.get(0).getId());
        Assertions.assertEquals(2, auditionPosts.size());
    }

    @Test
    void testGetPostsWithStartAndEndIndex() {
        final List<AuditionPost> auditionPosts = auditionController.getPosts("2", "3");
        Assertions.assertEquals(2, auditionPosts.get(0).getId());
        Assertions.assertEquals(2, auditionPosts.size());
    }

    @Test
    void testGetPostsWithNonNumeric() {
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            auditionController.getPosts("2.5", "3");
        });
        Assertions.assertEquals("Invalid parameter values", exception.getMessage());
         exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            auditionController.getPosts("2", "3.5");
        });
        Assertions.assertEquals("Invalid parameter values", exception.getMessage());
    }

    @Test
    void testGetPostsById() {
        Mockito.when(auditionService.getPostById("2")).thenReturn(TestHelper.getAuditionPost(2, 2, "Title2", "Body2"));
        final AuditionPost auditionPost = auditionController.getPosts("2");
        Assertions.assertEquals(2, auditionPost.getId());
    }

    @Test
    void testGetPostsByInvalidId() {
        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            auditionController.getPosts(INVALID_NUMBER);
        });
        Assertions.assertEquals("Invalid path variable", exception.getMessage());
    }

    @Test
    void testGetComments() {
        final List<AuditionComment> auditionComments = auditionController.getComments("1");
        Assertions.assertEquals(1, auditionComments.get(0).getId());
    }

    @Test
    void testGetCommentsUsingInvalidPostId() {
        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            auditionController.getComments(INVALID_NUMBER);
        });
        Assertions.assertEquals("Invalid path variable", exception.getMessage());
    }

    @Test
    void testGetCommentsOfPost() {
        final List<AuditionComment> auditionComments = auditionController.getCommentsOfPost("1");
        Assertions.assertEquals(1, auditionComments.get(0).getId());
    }

    @Test
    void testGetCommentsOfPostUsingInvalidPostId() {
        final Exception exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            auditionController.getCommentsOfPost(INVALID_NUMBER);
        });
        Assertions.assertEquals("Invalid path variable", exception.getMessage());
    }

    @Test
    void handleInvalidInputArguments() {
        final ResponseEntity<String> responseEntity = auditionController.handleInvalidInputArguments(new IllegalArgumentException("Invalid argument"));
        Assertions.assertEquals(400, responseEntity.getStatusCode().value());
        Assertions.assertEquals("Invalid argument", responseEntity.getBody());
    }
}
