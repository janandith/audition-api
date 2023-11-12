package com.audition.web.advice;

import com.audition.common.exception.SystemException;
import com.audition.common.logging.AuditionLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.client.HttpClientErrorException;

@ExtendWith(SpringExtension.class)
class ExceptionControllerAdviceTest {

    private transient ExceptionControllerAdvice exceptionControllerAdvice;

    @MockBean
    private transient AuditionLogger auditionLogger;

    @BeforeEach
    public void setup() {
        exceptionControllerAdvice = new ExceptionControllerAdvice(this.auditionLogger);
    }

    @Test
    void handleHttpClientException() {
        final ProblemDetail problemDetail = exceptionControllerAdvice
            .handleHttpClientException(new HttpClientErrorException(HttpStatusCode.valueOf(500)));
        Assertions.assertEquals(500, problemDetail.getStatus());
    }

    @Test
    void handleMainExceptionHttpClientError() {
        final ProblemDetail problemDetail = exceptionControllerAdvice
            .handleMainException(new HttpClientErrorException(HttpStatusCode.valueOf(500)));
        Assertions.assertEquals(500, problemDetail.getStatus());
    }

    @Test
    void handleMainExceptionMethodNotSupported() {
        final ProblemDetail problemDetail = exceptionControllerAdvice
            .handleMainException(new HttpRequestMethodNotSupportedException("PUT"));
        Assertions.assertEquals(405, problemDetail.getStatus());
        Assertions.assertEquals("Request method 'PUT' is not supported", problemDetail.getDetail());
    }

    @Test
    void handleMainExceptionGeneric() {
        final ProblemDetail problemDetail = exceptionControllerAdvice.handleMainException(new Exception());
        Assertions.assertEquals(500, problemDetail.getStatus());
        Assertions
            .assertEquals("API Error occurred. Please contact support or administrator.", problemDetail.getDetail());
    }

    @Test
    void handleSystemExceptionValidCode() {
        final ProblemDetail problemDetail = exceptionControllerAdvice
            .handleSystemException(new SystemException("ERROR", 500));
        Assertions.assertEquals(500, problemDetail.getStatus());
        Assertions.assertEquals("ERROR", problemDetail.getDetail());
    }

    @Test
    void handleSystemExceptionWithInvalidCode() {
        final ProblemDetail problemDetail = exceptionControllerAdvice
            .handleSystemException(new SystemException("Invalid Code", -1));
        Assertions.assertEquals(500, problemDetail.getStatus());
        Assertions.assertEquals("Invalid Code", problemDetail.getDetail());
    }
}
