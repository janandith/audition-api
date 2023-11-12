package com.audition.integration;

import com.audition.TestHelper;
import com.audition.common.exception.SystemException;
import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
class AuditionIntegrationClientTest {

    private static final String GET_POSTS_ENDPOINT = "https://jsonplaceholder.typicode.com/posts";
    private static final String GET_POSTS_BY_ID_ENDPOINT_WITH_VALUES = "https://jsonplaceholder.typicode.com/posts?id=2";
    private static final String GET_COMMENTS_ENDPOINT_WITH_VALUES = "https://jsonplaceholder.typicode.com/posts/2/comments";
    private static final String GET_COMMENTS_BY_POST_ID_ENDPOINT_WITH_VALUES = "https://jsonplaceholder.typicode.com/comments?postId=2";

    private transient AuditionIntegrationClient auditionIntegrationClient;

    @MockBean
    private transient RestTemplate restTemplate;

    @BeforeEach
    public void setup() {
        this.auditionIntegrationClient = new AuditionIntegrationClient(restTemplate);
        final AuditionPost[] auditionPosts = getAuditionPosts();
        final AuditionComment[] auditionComments = getAuditionComments();
        Mockito.when(restTemplate.getForObject(GET_POSTS_ENDPOINT, AuditionPost[].class)).thenReturn(auditionPosts);
        Mockito.when(restTemplate.getForObject(GET_COMMENTS_ENDPOINT_WITH_VALUES, AuditionComment[].class))
            .thenReturn(auditionComments);
        Mockito.when(restTemplate.getForObject(GET_COMMENTS_BY_POST_ID_ENDPOINT_WITH_VALUES, AuditionComment[].class))
            .thenReturn(auditionComments);
    }

    @Test
    void testGetPosts() {
        final List<AuditionPost> auditionPosts = auditionIntegrationClient.getPosts();
        Assertions.assertEquals(3, auditionPosts.size());
    }

    @Test
    void testGetPostsById() {
        Mockito.when(restTemplate.getForObject(GET_POSTS_BY_ID_ENDPOINT_WITH_VALUES, AuditionPost.class))
            .thenReturn(TestHelper.getAuditionPost(2, 2, "Title2", "Body2"));
        final AuditionPost auditionPost = auditionIntegrationClient.getPostById("2");
        Assertions.assertEquals(2, auditionPost.getId());
    }

    @Test
    void testGetPostsByIdWhenClientErrorExceptionWithStatusCode404() {
        Mockito.when(restTemplate.getForObject(GET_POSTS_BY_ID_ENDPOINT_WITH_VALUES, AuditionPost.class))
            .thenThrow(new HttpClientErrorException(
                HttpStatusCode.valueOf(400)));
        final Exception exception = Assertions.assertThrows(SystemException.class, () -> {
            auditionIntegrationClient.getPostById("2");
        });
        Assertions.assertNotNull(exception);
    }

    @Test
    void testGetPostsByIdWhenClientErrorExceptionWithStatusCode405() {
        Mockito.when(restTemplate.getForObject(GET_POSTS_BY_ID_ENDPOINT_WITH_VALUES, AuditionPost.class))
            .thenThrow(new HttpClientErrorException(
                HttpStatusCode.valueOf(405)));
        final Exception exception = Assertions.assertThrows(SystemException.class, () -> {
            auditionIntegrationClient.getPostById("2");
        });
        Assertions.assertNotNull(exception);
    }

    @Test
    void testGetComments() {
        final List<AuditionComment> auditionComments = auditionIntegrationClient.getComments("2");
        Assertions.assertEquals(2, auditionComments.size());
    }

    @Test
    void testGetCommentsByPostId() {
        final List<AuditionComment> auditionComments = auditionIntegrationClient.getCommentsByPostId("2");
        Assertions.assertEquals(2, auditionComments.size());
    }

    private AuditionPost[] getAuditionPosts() {
        return new AuditionPost[]{
            TestHelper.getAuditionPost(1, 1, "Title1", "Body1"),
            TestHelper.getAuditionPost(2, 2, "Title2", "Body2"),
            TestHelper.getAuditionPost(3, 3, "Title3", "Body3")
        };
    }

    private AuditionComment[] getAuditionComments() {
        return new AuditionComment[]{
            TestHelper.getAuditionComment(2, 1, "Name1", "name1@email.com", "Body1"),
            TestHelper.getAuditionComment(2, 2, "Name2", "name2@email.com", "Body2")
        };
    }
}
