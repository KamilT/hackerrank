package com.toszek.other;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 *
 * Change request:
 * add credits for each customer to allow burst functionality, credits are replenished each hour.
 */


final class CreditInfo {
    private final Instant creation;
    private int credits;

    /**
     *
     */
    public CreditInfo(Instant creation, int credits) {
        this.creation = creation;
        this.credits = credits;
    }

    public Instant creation() {
        return creation;
    }

    public int getCredits() {
        return credits;
    }

    public void decreaseCredits() {
        credits -= 1;
        if (credits < 0) {
            throw new RuntimeException("Not allowed state");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CreditInfo) obj;
        return Objects.equals(this.creation, that.creation) &&
                this.credits == that.credits;
    }

    @Override
    public int hashCode() {
        return Objects.hash(creation, credits);
    }

    @Override
    public String toString() {
        return "CreditInfo[" +
                "creation=" + creation + ", " +
                "credits=" + credits + ']';
    }

}

final class CustomerInfo {
    private final List<Instant> calls;
    private CreditInfo credits;
    private final int newCreditsPool;

    /**
     *
     */
    CustomerInfo(List<Instant> calls, CreditInfo credits, int newCreditsPool) {
        this.calls = calls;
        this.credits = credits;
        this.newCreditsPool = newCreditsPool;
    }

    public void decreaseCredits() {
        if (credits.creation().truncatedTo(ChronoUnit.HOURS).isBefore(Instant.now().truncatedTo(ChronoUnit.HOURS))) {
            credits = new CreditInfo(Instant.now(), newCreditsPool);
        }
        credits.decreaseCredits();
    }

    public List<Instant> calls() {
        return calls;
    }

    public int credits() {
        return credits.getCredits();
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
        final CustomerInfo customerInfo = rateLimiterMap.computeIfAbsent(customerId, key -> new CustomerInfo(new ArrayList<>(), new CreditInfo(Instant.now(), newCreditsForClient), newCreditsForClient));

        // clean old requests
        // there is a better approach to do it manually and stop on not before element
        customerInfo.calls().removeIf(timestamp -> timestamp.isBefore(removeThreshold));

        customerInfo.calls().add(now);

        // access allow decision
        final boolean decision = customerInfo.calls().size() <= allowedRequestsCount;
        if (!decision) {
            try {
                customerInfo.decreaseCredits();
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }
}
