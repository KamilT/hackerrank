package com.toszek.other;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class RateLimiterTest {

    @Test
    void rateLimitDoesNotLimitCalls() {
        RateLimiter rateLimiter = new RateLimiter(10, 5000, 0);
        assertThatNoException().isThrownBy(() -> {
            for (int call = 0; call < 2; call++) {
                assertThat(rateLimiter.rateLimit(1))
                        .isTrue();
            }
        });
    }

    @Test
    void rateLimitDoesLimitCalls() {
        RateLimiter rateLimiter = new RateLimiter(1, 5000, 0);
        assertThatNoException().isThrownBy(() -> {
            rateLimiter.rateLimit(1);
            assertThat(rateLimiter.rateLimit(1))
                    .isFalse();
        });
    }

    @Test
    void rateLimitsOneUserAllowsSecond() {
        RateLimiter rateLimiter = new RateLimiter(1, 5000, newCreditsForClient);
        assertThatNoException().isThrownBy(() -> {
            rateLimiter.rateLimit(1);
            assertThat(rateLimiter.rateLimit(1))
                    .isFalse();

            assertThat(rateLimiter.rateLimit(2))
                    .isTrue();
        });
    }
}
