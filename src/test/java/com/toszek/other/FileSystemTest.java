package com.toszek.other;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class FileSystemTest {

    @Test
    void getTotalSizeOfFilesInSystem() {
        FileSystem fileSystem = new FileSystem();
        fileSystem.addFile("test2", 20, Set.of("a"));
        fileSystem.addFile("test", 10, Set.of("a"));
        fileSystem.addFile("test3", 30, Set.of("a"));

        assertThat(fileSystem.getTotalSizeOfFilesInSystem())
                .isEqualTo(60);
    }

    @Test
    void getFirstBiggestTags() {
        FileSystem fileSystem = new FileSystem();
        fileSystem.addFile("test", 10, Set.of("a"));
        fileSystem.addFile("test4", 30, null);
        fileSystem.addFile("test3", 30, Set.of("c"));
        fileSystem.addFile("test2", 20, Set.of("b"));

        assertThat(fileSystem.getFirstBiggestTags(1))
                .containsExactly("c");

        assertThat(fileSystem.getFirstBiggestTags(2))
                .containsExactly("c", "b");

        assertThat(fileSystem.getFirstBiggestTags(3))
                .containsExactly("c", "b", "a");
    }

    @Test
    void getFirstBiggestTagsOverLimit() {
        FileSystem fileSystem = new FileSystem();
        fileSystem.addFile("test", 10, Set.of("a"));
        fileSystem.addFile("test4", 30, null);
        fileSystem.addFile("test3", 30, Set.of("c"));
        fileSystem.addFile("test2", 20, Set.of("b"));

        assertThat(fileSystem.getFirstBiggestTags(5))
                .containsExactly("c", "b", "a");
    }
}
