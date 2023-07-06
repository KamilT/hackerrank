package com.toszek.other;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryDb {
    private Map<String, Integer> database = new HashMap<>();
    private LinkedList<Map<String, Integer>> transactions = new LinkedList<>();

    public void set(String key, int value) {
        if (!transactions.isEmpty()) {
            Map<String, Integer> currentTransactionData = transactions.getLast();
            currentTransactionData.put(key, value);
        } else {
            database.put(key, value);
        }
    }

    public Optional<Integer> get(String key) {
        final Iterator<Map<String, Integer>> transactionsDataIterator = transactions.descendingIterator();
        while (transactionsDataIterator.hasNext()) {
            final Map<String, Integer> transactionsData = transactionsDataIterator.next();
            if (transactionsData.containsKey(key)) {
                return Optional.of(transactionsData.get(key));
            }
        }
        return Optional.ofNullable(database.get(key));
    }

    public void delete(String key) {
        if (!transactions.isEmpty()) {
            Map<String, Integer> currentTransactionData = transactions.getLast();
            currentTransactionData.put(key, null);
        } else {
            database.remove(key);
        }
    }

    public int count(int value) {
        final AtomicInteger count = new AtomicInteger();
        Set<String> countedKeys = new HashSet<>();
        if (!transactions.isEmpty()) {
            final Iterator<Map<String, Integer>> transactionsDataIterator = transactions.descendingIterator();
            while (transactionsDataIterator.hasNext()) {
                final Map<String, Integer> transactionsData = transactionsDataIterator.next();
                transactionsData.forEach((key, keyValue) -> {
                    if (!countedKeys.contains(key) && keyValue == value) {
                        count.addAndGet(1);
                    }
                    countedKeys.add(key);
                });
            }
        }

        database.forEach((key, keyValue) -> {
            if (!countedKeys.contains(key) && keyValue == value) {
                count.addAndGet(1);
            }
        });
        return count.get();
    }

    public void begin() {
        transactions.add(new HashMap<>());
    }

    public boolean rollback() {
        if (transactions.isEmpty()) {
            System.out.println("No transaction");
            return false;
        }
        transactions.removeLast();
        return true;
    }

    public boolean commit() {
        if (transactions.isEmpty()) {
            System.out.println("No transaction");
            return false;
        }
        for (Map<String, Integer> transactionData : transactions) {
            transactionData.forEach((key, value) -> {
                if (value == null) {
                    database.remove(key);
                } else {
                    database.put(key, value);
                }
            });
        }
        transactions.clear();
        return true;
    }
}
