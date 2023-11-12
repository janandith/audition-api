package com.audition.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AuditionCommentTest {
    @Test
    void checkGetters() {
        final AuditionComment auditionComment = new AuditionComment();
        Assertions.assertEquals(0, auditionComment.getId());
        Assertions.assertEquals(0, auditionComment.getPostId());
        Assertions.assertEquals(null, auditionComment.getBody());
        Assertions.assertEquals(null, auditionComment.getEmail());
        Assertions.assertEquals(null, auditionComment.getName());
    }
}
