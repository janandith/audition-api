package com.audition.web;

import com.audition.model.AuditionComment;
import com.audition.model.AuditionPost;
import com.audition.service.AuditionService;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuditionController {

    private transient AuditionService auditionService;

    @Autowired
    public AuditionController(final AuditionService auditionService) {
        this.auditionService = auditionService;
    }

    // Add a query param that allows data filtering. The intent of the filter is at developers discretion.
    @RequestMapping(value = "/posts", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<AuditionPost> getPosts(@RequestParam(name = "start", required = false) final String start,
        @RequestParam(name = "end", required = false) final String end) {
        // Add logic that filters response data based on the query param
        return filterPosts(start, end, auditionService.getPosts());
    }

    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    AuditionPost getPosts(@PathVariable("id") final String postId) {
        // Add input validation
        if (!StringUtils.isNumeric(postId)) {
            throw new IllegalArgumentException("Invalid path variable");
        }

        return auditionService.getPostById(postId);
    }

    // Add additional methods to return comments for each post. Hint: Check https://jsonplaceholder.typicode.com/
    @RequestMapping(value = "/posts/{id}/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<AuditionComment> getComments(@PathVariable("id") final String postId) {
        if (!StringUtils.isNumeric(postId)) {
            throw new IllegalArgumentException("Invalid path variable");
        }

        return auditionService.getComments(postId);
    }

    @RequestMapping(value = "/comments/{postId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<AuditionComment> getCommentsOfPost(@PathVariable("postId") final String postId) {
        // Add input validation
        if (!StringUtils.isNumeric(postId)) {
            throw new IllegalArgumentException("Invalid path variable");
        }

        return auditionService.getCommentsByPostId(postId);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleInvalidInputArguments(final IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    private List<AuditionPost> filterPosts(final String start, final String end, final List<AuditionPost> posts) {
        if (!StringUtils.isEmpty(start) && !StringUtils.isNumeric(start) || !StringUtils.isEmpty(end) && !StringUtils
            .isNumeric(end)) {
            throw new IllegalArgumentException("Invalid parameter values");
        }

        if (start != null) {
            if (end != null) {
                return posts.stream()
                    .filter(post -> post.getId() >= Integer.valueOf(start) && post.getId() <= Integer.valueOf(end))
                    .collect(Collectors.toList());
            } else {
                return posts.stream().filter(post -> post.getId() >= Integer.valueOf(start))
                    .collect(Collectors.toList());
            }
        } else {
            if (end != null) {
                return posts.stream().filter(post -> post.getId() <= Integer.valueOf(end)).collect(Collectors.toList());
            }
        }
        return posts;
    }
}
