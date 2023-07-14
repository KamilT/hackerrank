package com.toszek.other;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Rate limiting
 * 5 req/10 sec
 * <p>
 * 2000 requests credit per hour
 * <p>
 * Perform rate limiting logic for provided customer ID. Return true if the
 * request is allowed, and false if it is not.
 * boolean rateLimit(int customerId)
 * constaints:
 * up to 10k users
 */


record CreditInfo(Instant creation, int credits) {
}

final class CustomerInfo {
    private final List<Instant> calls;
    private int credits;

    /**
     *
     */
    CustomerInfo(List<Instant> calls, CreditInfo credits) {
        this.calls = calls;
        this.credits = credits;
    }

    public void decreaseCredits() {
        credits -= 1;
        if (credits < 0) {
            throw new RuntimeException("Not allowed state");
        }
    }

    public List<Instant> calls() {
        return calls;
    }

    public int credits() {
        return credits;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CustomerInfo) obj;
        return Objects.equals(this.calls, that.calls) &&
                this.credits == that.credits;
    }

    @Override
    public int hashCode() {
        return Objects.hash(calls, credits);
    }

    @Override
    public String toString() {
        return "CustomerInfo[" +
                "calls=" + calls + ", " +
                "credits=" + credits + ']';
    }

}

public class RateLimiter {
    private final Map<Integer, CustomerInfo> rateLimiterMap = new ConcurrentHashMap<>();
    private final int allowedRequestsCount;
    private final int timeIntervalInMilliseconds;
    private final int newCreditsForClient;

    public RateLimiter(final int allowedRequestsCount, final int timeIntervalInMilliseconds, final int newCreditsForClient) {
        this.allowedRequestsCount = allowedRequestsCount;
        this.timeIntervalInMilliseconds = timeIntervalInMilliseconds;
        this.newCreditsForClient = newCreditsForClient;
    }

    public boolean rateLimit(int customerId) {
        Instant now = Instant.now();
        Instant removeThreshold = now.minusMillis(timeIntervalInMilliseconds);
        final CustomerInfo customerInfo = rateLimiterMap.computeIfAbsent(customerId, key -> new CustomerInfo(new ArrayList<>(), newCreditsForClient));

        // clean old requests
        // there is a better approach to do it manually and stop on not before element
        customerInfo.calls().removeIf(timestamp -> timestamp.isBefore(removeThreshold));

        customerInfo.calls().add(now);

        // access allow decision
        final boolean decision = customerInfo.calls().size() <= allowedRequestsCount;
        if (!decision) {
            if (customerInfo.credits() > 0) {
                customerInfo.decreaseCredits();
                return true;
            }
            return false;
        }
        return true;
    }
}
