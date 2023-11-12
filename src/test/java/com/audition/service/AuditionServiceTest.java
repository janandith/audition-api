package com.audition.service;

import com.audition.integration.AuditionIntegrationClient;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
class AuditionServiceTest {

    private transient AuditionService auditionService;

    @MockBean
    private transient AuditionIntegrationClient auditionIntegrationClient;

    @BeforeEach
    public void setup() {
        auditionService = new AuditionService(this.auditionIntegrationClient);
    }

    @Test
    void verifyGetPosts() {
        auditionService.getPosts();
        Mockito.verify(auditionIntegrationClient, Mockito.atLeastOnce()).getPosts();
    }

    @Test
    void verifyGetPostsById() {
        auditionService.getPostById(StringUtils.EMPTY);
        Mockito.verify(auditionIntegrationClient, Mockito.atLeastOnce()).getPostById(Mockito.anyString());
    }

    @Test
    void verifyGetComments() {
        auditionService.getComments(StringUtils.EMPTY);
        Mockito.verify(auditionIntegrationClient, Mockito.atLeastOnce()).getComments(Mockito.anyString());
    }

    @Test
    void verifyGetCommentsByPostId() {
        auditionService.getCommentsByPostId(StringUtils.EMPTY);
        Mockito.verify(auditionIntegrationClient, Mockito.atLeastOnce()).getCommentsByPostId(Mockito.anyString());
    }
}
