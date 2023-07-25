package com.toszek.other;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class RateLimiterLeakingBucketTest {

    @Test
    void shouldAllowRequest() {
        // prepare
        RateLimiterLeakingBucket rateLimiterLeakingBucket = new RateLimiterLeakingBucketImpl(1, 0);
        // act
        assertThat(rateLimiterLeakingBucket.shouldConsumeRequest())
                .isTrue();
    }

    @Test
    void shouldNotAllowRequest() {
        // prepare
        RateLimiterLeakingBucket rateLimiterLeakingBucket = new RateLimiterLeakingBucketImpl(1, 0);
        // act
        assertThat(rateLimiterLeakingBucket.shouldConsumeRequest())
                .isTrue();
        assertThat(rateLimiterLeakingBucket.shouldConsumeRequest())
                .isFalse();
    }

    @Test
    void shouldAllowDueToDrainRequest() {
        // prepare
        Instant now = Instant.now();
        RateLimiterLeakingBucketImpl rateLimiter = new RateLimiterLeakingBucketImpl(2, 1);

        // act
        assertThat(rateLimiter.shouldConsumeRequest(now))
                .isTrue();
        assertThat(rateLimiter.shouldConsumeRequest(now))
                .isTrue();
        assertThat(rateLimiter.shouldConsumeRequest(now))
                .isFalse();

        now = now.plusMillis(1);

        assertThat(rateLimiter.shouldConsumeRequest(now))
                .isTrue();

        assertThat(rateLimiter.shouldConsumeRequest(now))
                .isFalse();
    }
}
