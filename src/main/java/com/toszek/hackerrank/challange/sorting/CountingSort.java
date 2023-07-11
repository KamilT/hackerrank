package com.toszek.hackerrank.challange.sorting;

import java.util.ArrayList;
import java.util.List;

/**
 * Challenge
 * Given a list of integers, count and return the number of times each value appears as an array of integers.
 * <p>
 * https://www.hackerrank.com/challenges/countingsort1/problem
 */
public class CountingSort {
    public static List<Integer> countingSort(List<Integer> arr) {
        List<Integer> countedResult = new ArrayList<>();
        for (Integer value : arr) {
            expandCounterListIfNeeded(countedResult, value);
            countedResult.set(value, countedResult.get(value) + 1);
        }
        expandCounterListIfNeeded(countedResult, 99);
        return countedResult;
    }

    private static void expandCounterListIfNeeded(final List<Integer> countedResult, final Integer value) {
        while (countedResult.size() <= value) {
            countedResult.add(0);
        }
    }
}
