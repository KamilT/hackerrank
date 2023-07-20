package com.toszek.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * - dodawanie plikow
 * Plik:
 * nazwa, rozmiar, kolekcja/tag (na potrzeby grupowania)
 * <p>
 * Constrains:
 * 1 tag na plik max
 * <p>
 * Zadanie:
 * - dodawanie plikow
 * <p>
 * wygenerowac raport:
 * - suma wszyskich rozmiarow plikow
 * - n najwiekszych kolekcji, zwraca liste nazw tagow
 */

record File(String name, long size, String tag) {
}

class TagSizeCounter implements Comparable<TagSizeCounter> {
    private String tag;
    private long size;

    public TagSizeCounter(final String tag) {
        this.tag = tag;
        this.size = 0;
    }

    public String getTag() {
        return tag;
    }

    public long getSize() {
        return size;
    }

    @Override
    public int compareTo(final TagSizeCounter o) {
        return Long.compare((int) size, (int) o.size);
    }

    public void addSize(final long size) {
        this.size += size;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TagSizeCounter that = (TagSizeCounter) o;
        return Objects.equals(tag, that.tag);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag);
    }
}

class FileSystem {
    private final List<File> files = new ArrayList<>();
    private final SortedSet<TagSizeCounter> tagsStatisticsSet = new TreeSet<>();
    private final Map<String, TagSizeCounter> tagsInSystem = new HashMap<>();
    private long sizeCounter = 0;

    public void addFile(String name, long size, String tag) {
        if (size <= 0) {
            throw new RuntimeException("Size cannot be below");
        }
        files.add(new File(name, size, tag));
        sizeCounter += size;
        if (tag != null && !tag.isBlank()) {
            final TagSizeCounter tagSizeCounter = tagsInSystem.computeIfAbsent(tag, tag1 -> {
                final TagSizeCounter counter = new TagSizeCounter(tag1);
                tagsStatisticsSet.add(counter);
                return counter;
            });
            tagSizeCounter.addSize(size);
        }
    }

    public long getTotalSizeOfFilesInSystem() {
        return sizeCounter;
    }

    public List<String> getFirstBiggestTags(int n) {
        return tagsStatisticsSet.stream()
                .map(TagSizeCounter::getTag)
                .limit(n)
                .toList();
    }
}
