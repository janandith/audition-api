package com.audition;

import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;

public class TestHelper {

    public static AuditionPost getAuditionPost(final int userId, final int id, final String title, final String body) {
        final AuditionPost auditionPost = new AuditionPost();
        auditionPost.setUserId(userId);
        auditionPost.setId(id);
        auditionPost.setTitle(title);
        auditionPost.setBody(body);
        return auditionPost;
    }

    public static AuditionComment getAuditionComment(final int postId, final int id, final String name,
        final String email, final String body) {
        final AuditionComment auditionComment = new AuditionComment();
        auditionComment.setPostId(postId);
        auditionComment.setId(id);
        auditionComment.setName(name);
        auditionComment.setEmail(email);
        auditionComment.setBody(body);
        return auditionComment;
    }
}
