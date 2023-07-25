package com.toszek.other;

import java.time.Duration;
import java.time.Instant;
import java.util.LinkedList;

class RateLimiterLeakingBucketImpl implements RateLimiterLeakingBucket {
    private final int maxCapacity;
    private final int drainRequestPerMilliseconds;

    private final LinkedList<Instant> requestsQueue = new LinkedList<>();

    public RateLimiterLeakingBucketImpl(final int maxCapacity, final int drainRequestPerMillisecond) {
        this.maxCapacity = maxCapacity;
        this.drainRequestPerMilliseconds = drainRequestPerMillisecond;
    }

    @Override
    public boolean shouldConsumeRequest() {
        return shouldConsumeRequest(Instant.now());
    }

    synchronized boolean shouldConsumeRequest(Instant now) {
        // clean capacity of my queue
        // take oldest from queue
        // calculate how many to delete based on elapsed time and drainRequestPerSecond
        // remove till countToDelete reached or queue is empty

        if (!requestsQueue.isEmpty()) {
            final Instant last = requestsQueue.getLast();
            long countToDelete = Duration.between(last, now).toMillis() * drainRequestPerMilliseconds;

            while (!requestsQueue.isEmpty() && countToDelete > 0) {
                requestsQueue.removeLast();
                countToDelete--;
            }
        }

        if (requestsQueue.size() < maxCapacity) {
            requestsQueue.add(now);
            return true;
        }
        return false;
    }
}
