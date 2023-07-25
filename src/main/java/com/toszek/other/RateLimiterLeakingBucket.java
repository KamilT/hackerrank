package com.toszek.other;

public interface RateLimiterLeakingBucket {
    /**
     * @return True if the request should be consumed.
     * False if the rate limit has been exceeded.
     */
    boolean shouldConsumeRequest();
}
