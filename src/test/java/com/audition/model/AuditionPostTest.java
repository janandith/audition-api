package com.audition.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AuditionPostTest {

    @Test
    void checkGetters() {
        final AuditionPost auditionPost = new AuditionPost();
        Assertions.assertEquals(0, auditionPost.getId());
        Assertions.assertEquals(null, auditionPost.getTitle());
        Assertions.assertEquals(null, auditionPost.getBody());
        Assertions.assertEquals(0, auditionPost.getUserId());
    }

    @Test
    void checkSetters() {
        final AuditionPost auditionPost = new AuditionPost();
        auditionPost.setId(1);
        auditionPost.setTitle("title");
        auditionPost.setBody("body");
        auditionPost.setUserId(1);
        Assertions.assertEquals(1, auditionPost.getId());
        Assertions.assertEquals("title", auditionPost.getTitle());
        Assertions.assertEquals("body", auditionPost.getBody());
        Assertions.assertEquals(1, auditionPost.getUserId());
    }
}
