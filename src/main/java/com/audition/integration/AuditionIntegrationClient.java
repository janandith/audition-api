package com.audition.integration;

import com.audition.common.exception.SystemException;
import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AuditionIntegrationClient {

    private static final String GET_POSTS_ENDPOINT = "https://jsonplaceholder.typicode.com/posts";
    private static final String GET_COMMENTS_ENDPOINT = "https://jsonplaceholder.typicode.com/posts/{postId}/comments";
    private static final String GET_COMMENTS_BY_POST_ID_ENDPOINT = "https://jsonplaceholder.typicode.com/comments";

    private transient RestTemplate restTemplate;

    @Autowired
    public AuditionIntegrationClient(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<AuditionPost> getPosts() {
        // Make RestTemplate call to get Posts from https://jsonplaceholder.typicode.com/posts
        final AuditionPost[] auditionPosts = restTemplate.getForObject(GET_POSTS_ENDPOINT, AuditionPost[].class);
        return Arrays.stream(auditionPosts).collect(Collectors.toList());
    }

    public AuditionPost getPostById(final String id) {
        // Get post by post ID call from https://jsonplaceholder.typicode.com/posts/
        try {
            final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(GET_POSTS_ENDPOINT).queryParam("id", id);
            return restTemplate.getForObject(builder.build().toString(), AuditionPost.class);
        } catch (final HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new SystemException("Cannot find a Post with id " + id, "Resource Not Found",
                    404, e);
            } else {
                // Find a better way to handle the exception so that the original error message is not lost. Feel free to change this function.
                throw new SystemException(StringUtils.isEmpty(e.getMessage()) ? "Unknown Error message" : e.getMessage(), e);
            }
        }
    }

    // GET comments for a post from https://jsonplaceholder.typicode.com/posts/{postId}/comments - the comments must be returned as part of the post.
    public List<AuditionComment> getComments(final String postId)  {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(GET_COMMENTS_ENDPOINT);
        final Map<String, String> urlParams = new ConcurrentHashMap<>();
        urlParams.put("postId", postId);
        final AuditionComment[] auditionComments = restTemplate.getForObject(builder.buildAndExpand(urlParams).toUri().toString(), AuditionComment[].class);
        return Arrays.stream(auditionComments).collect(Collectors.toList());
    }

    // GET comments for a particular Post from https://jsonplaceholder.typicode.com/comments?postId={postId}.
    public List<AuditionComment> getCommentsByPostId(final String postId)  {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(GET_COMMENTS_BY_POST_ID_ENDPOINT).queryParam("postId", postId);
        final AuditionComment[] auditionComments = restTemplate.getForObject(builder.build().toString(), AuditionComment[].class);
        return Arrays.stream(auditionComments).collect(Collectors.toList());
    }
}