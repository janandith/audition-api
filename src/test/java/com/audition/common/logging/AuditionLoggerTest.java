package com.audition.common.logging;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

class AuditionLoggerTest {

    private transient AuditionLogger auditionLogger;

    @BeforeEach
    public void setup() {
        auditionLogger = new AuditionLogger();
    }

    @Test
    void verifyInfoErrorEnabled() {
        final Logger mockLogger = Mockito.mock(Logger.class);
        Mockito.when(mockLogger.isInfoEnabled()).thenReturn(true);
        auditionLogger.info(mockLogger, StringUtils.EMPTY);
        Mockito.verify(mockLogger, Mockito.atLeastOnce()).info(Mockito.anyString());
        auditionLogger.info(mockLogger, StringUtils.EMPTY, new Object());
        Mockito.verify(mockLogger, Mockito.atLeastOnce()).info(Mockito.anyString(), Mockito.any(Object.class));
    }

    @Test
    void verifyInfoErrorDisabled() {
        final Logger mockLogger = Mockito.mock(Logger.class);
        Mockito.when(mockLogger.isInfoEnabled()).thenReturn(false);
        auditionLogger.info(mockLogger, StringUtils.EMPTY);
        Mockito.verify(mockLogger, Mockito.never()).info(Mockito.anyString());
        auditionLogger.info(mockLogger, StringUtils.EMPTY, new Object());
        Mockito.verify(mockLogger, Mockito.never()).info(Mockito.anyString(), Mockito.any(Object.class));
    }

    @Test
    void verifyAllLogEnabled() {
        final Logger mockLogger = Mockito.mock(Logger.class);
        Mockito.when(mockLogger.isDebugEnabled()).thenReturn(true);
        Mockito.when(mockLogger.isWarnEnabled()).thenReturn(true);
        Mockito.when(mockLogger.isErrorEnabled()).thenReturn(true);
        auditionLogger.debug(mockLogger, StringUtils.EMPTY);
        Mockito.verify(mockLogger, Mockito.atLeastOnce()).debug(Mockito.anyString());
        auditionLogger.warn(mockLogger, StringUtils.EMPTY);
        Mockito.verify(mockLogger, Mockito.atLeastOnce()).warn(Mockito.anyString());
        auditionLogger.error(mockLogger, StringUtils.EMPTY);
        Mockito.verify(mockLogger, Mockito.atLeastOnce()).error(Mockito.anyString());
        auditionLogger.logErrorWithException(mockLogger, StringUtils.EMPTY, new Exception());
        Mockito.verify(mockLogger, Mockito.atLeastOnce()).error(Mockito.anyString(), Mockito.any(Exception.class));
    }

    @Test
    void verifyDebugErrorDisabled() {
        final Logger mockLogger = Mockito.mock(Logger.class);
        Mockito.when(mockLogger.isDebugEnabled()).thenReturn(false);
        auditionLogger.debug(mockLogger, StringUtils.EMPTY);
        Mockito.verify(mockLogger, Mockito.never()).debug(Mockito.anyString());
    }

    @Test
    void verifyWarnErrorDisabled() {
        final Logger mockLogger = Mockito.mock(Logger.class);
        Mockito.when(mockLogger.isWarnEnabled()).thenReturn(false);
        auditionLogger.warn(mockLogger, StringUtils.EMPTY);
        Mockito.verify(mockLogger, Mockito.never()).warn(Mockito.anyString());
    }

    @Test
    void verifyErrorErrorDisabled() {
        final Logger mockLogger = Mockito.mock(Logger.class);
        Mockito.when(mockLogger.isErrorEnabled()).thenReturn(false);
        auditionLogger.error(mockLogger, StringUtils.EMPTY);
        Mockito.verify(mockLogger, Mockito.never()).error(Mockito.anyString());
    }

    @Test
    void verifyLogErrorWithExceptionErrorDisabled() {
        final Logger mockLogger = Mockito.mock(Logger.class);
        Mockito.when(mockLogger.isErrorEnabled()).thenReturn(false);
        auditionLogger.logErrorWithException(mockLogger, StringUtils.EMPTY, new Exception());
        Mockito.verify(mockLogger, Mockito.never()).error(Mockito.anyString(), Mockito.any(Exception.class));
    }

    @Test
    void verifyLogStandardProblemDetailWhenErrorEnabled() {
        final Logger mockLogger = Mockito.mock(Logger.class);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), StringUtils.EMPTY);
        Mockito.when(mockLogger.isErrorEnabled()).thenReturn(true);
        auditionLogger.logStandardProblemDetail(mockLogger, problemDetail, new Exception());
        Mockito.verify(mockLogger, Mockito.atLeastOnce()).error(Mockito.anyString(), Mockito.any(Exception.class));
        auditionLogger.logStandardProblemDetail(mockLogger, null, new Exception());
        Mockito.verify(mockLogger, Mockito.atLeastOnce()).error(Mockito.anyString(), Mockito.any(Exception.class));
    }

    @Test
    void verifyLogStandardProblemDetailWhenErrorDisabled() {
        final Logger mockLogger = Mockito.mock(Logger.class);
        final ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(500), StringUtils.EMPTY);
        Mockito.when(mockLogger.isErrorEnabled()).thenReturn(false);
        auditionLogger.logStandardProblemDetail(mockLogger, problemDetail, new Exception());
        Mockito.verify(mockLogger, Mockito.never()).error(Mockito.anyString(), Mockito.any(Exception.class));
        auditionLogger.logHttpStatusCodeError(mockLogger, "Error message", 500);
        Mockito.verify(mockLogger, Mockito.never()).error(Mockito.anyString());
    }

    @Test
    void verifyLogHttpStatusCodeErrorWhenErrorEnabled() {
        final Logger mockLogger = Mockito.mock(Logger.class);
        Mockito.when(mockLogger.isErrorEnabled()).thenReturn(true);
        auditionLogger.logHttpStatusCodeError(mockLogger, "Error message", 500);
        Mockito.verify(mockLogger, Mockito.atLeastOnce()).error(Mockito.anyString());
        Mockito.when(mockLogger.isErrorEnabled()).thenReturn(true);
        auditionLogger.logHttpStatusCodeError(mockLogger, StringUtils.EMPTY, 500);
        Mockito.verify(mockLogger, Mockito.atLeastOnce()).error(Mockito.anyString());
        Mockito.when(mockLogger.isErrorEnabled()).thenReturn(true);
        auditionLogger.logHttpStatusCodeError(mockLogger, "Error message", null);
        Mockito.verify(mockLogger, Mockito.atLeastOnce()).error(Mockito.anyString());
    }
}
