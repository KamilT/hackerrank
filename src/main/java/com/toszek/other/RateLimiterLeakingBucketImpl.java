package com.toszek.other;

import java.time.Duration;
import java.time.Instant;

class RateLimiterLeakingBucketImpl implements RateLimiterLeakingBucket {
    private final int maxCapacity;
    private final int drainRequestPerMilliseconds;
    private long bucket;
    private Instant lastCallTimestamp;

    public RateLimiterLeakingBucketImpl(final int maxCapacity, final int drainRequestPerMillisecond) {
        this.maxCapacity = maxCapacity;
        this.drainRequestPerMilliseconds = drainRequestPerMillisecond;
    }

    @Override
    public boolean shouldConsumeRequest() {
        return shouldConsumeRequest(Instant.now());
    }

    synchronized boolean shouldConsumeRequest(Instant now) {
        if (lastCallTimestamp != null) {
            long countToDelete = Duration.between(lastCallTimestamp, now).toMillis() * drainRequestPerMilliseconds;
            lastCallTimestamp = now;
            bucket -= countToDelete;
            if (bucket < 0) {
                bucket = 0;
            }
        } else {
            lastCallTimestamp = now;
            bucket = 0;
        }

        if (bucket < maxCapacity) {
            bucket += 1;
            return true;
        }
        return false;
    }
}
