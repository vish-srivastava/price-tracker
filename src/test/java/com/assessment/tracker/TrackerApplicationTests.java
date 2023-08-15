package com.assessment.tracker;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.openfeign.EnableFeignClients;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class TrackerApplicationTests {

    @Mock
    private CacheManager cacheManager;

    private TrackerApplication trackerApplication;

    @Test
    void contextLoads() {
        TrackerApplication.main(new String[]{"Hello", "Hi"});
        assertNotNull(cacheManager);

    }
}