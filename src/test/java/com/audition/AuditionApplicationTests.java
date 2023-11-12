package com.audition;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AuditionApplicationTests {

    // Implement unit test. Note that an applicant should create additional unit tests as required.
    @Test
    void contextLoads(final ApplicationContext context) {
        Assertions.assertNotNull(context);
    }

}
