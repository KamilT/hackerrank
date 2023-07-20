package com.toszek.other;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

record File(String name, long size, Set<String> tag) {
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
        return -1 * Long.compare((int) size, (int) o.size);
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

    public void addFile(String name, long size, Set<String> tags) {
        if (size <= 0) {
            throw new RuntimeException("Size cannot be below");
        }
        tags = tags.stream().filter(tag -> tag != null && !tag.isBlank()).collect(Collectors.toSet());

        files.add(new File(name, size, tags));
        sizeCounter += size;
        tags.forEach(tag -> {
            final TagSizeCounter tagSizeCounter = tagsInSystem.computeIfAbsent(tag, TagSizeCounter::new);
            tagsStatisticsSet.remove(tagSizeCounter);
            tagSizeCounter.addSize(size);
            tagsStatisticsSet.add(tagSizeCounter);
        });
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
