package com.toszek.other;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryDbTest {

    private InMemoryDb inMemoryDb;

    @BeforeEach
    void setUp() {
        inMemoryDb = new InMemoryDb();
    }

    @Test
    public void adds_A_ToDbNoTransaction() {
        inMemoryDb.set("A", 10);
        assertThat(inMemoryDb.get("A"))
                .isNotNull()
                .hasValue(10);
    }

    @Test
    public void checksNotExistingNoTransaction() {
        assertThat(inMemoryDb.get("A"))
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void countsValuesNoTransaction() {
        inMemoryDb.set("A", 10);
        inMemoryDb.set("B", 10);
        assertThat(inMemoryDb.count(10))
                .isEqualTo(2);
    }

    @Test
    public void deleteValueNoTransaction() {
        inMemoryDb.set("A", 10);
        inMemoryDb.set("B", 10);
        inMemoryDb.delete("B");
        assertThat(inMemoryDb.count(10))
                .isEqualTo(1);
    }

    @Test
    public void getsValueInTransaction() {
        inMemoryDb.set("A", 10);
        inMemoryDb.begin();
        inMemoryDb.set("A", 20);
        assertThat(inMemoryDb.get("A"))
                .isNotNull()
                .hasValue(20);
    }

    @Test
    public void countValueInTransaction() {
        inMemoryDb.set("A", 10);
        inMemoryDb.begin();
        inMemoryDb.set("A", 20);
        assertThat(inMemoryDb.count(10))
                .isEqualTo(0);
        assertThat(inMemoryDb.count(20))
                .isEqualTo(1);
    }

    @Test
    public void rollbackValueInTransaction() {
        inMemoryDb.set("A", 10);
        inMemoryDb.begin();
        inMemoryDb.set("A", 20);
        inMemoryDb.set("B", 15);
        inMemoryDb.rollback();
        assertThat(inMemoryDb.get("A"))
                .isNotNull()
                .hasValue(10);
        assertThat(inMemoryDb.get("B"))
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void commitTransaction() {
        inMemoryDb.set("A", 10);
        inMemoryDb.begin();
        inMemoryDb.set("A", 20);
        inMemoryDb.set("B", 15);
        inMemoryDb.commit();
        assertThat(inMemoryDb.get("A"))
                .isNotNull()
                .hasValue(20);
        assertThat(inMemoryDb.get("B"))
                .isNotNull()
                .hasValue(15);
    }

    @Test
    public void commitTwoTransactions() {
        inMemoryDb.set("A", 10);
        inMemoryDb.begin();
        inMemoryDb.set("A", 20);
        inMemoryDb.set("B", 15);
        inMemoryDb.begin();
        inMemoryDb.set("A", 30);
        inMemoryDb.commit();
        assertThat(inMemoryDb.get("A"))
                .isNotNull()
                .hasValue(30);
        assertThat(inMemoryDb.get("B"))
                .isNotNull()
                .hasValue(15);
    }

    @Test
    public void commitDeleteInTransaction() {
        inMemoryDb.set("A", 10);
        inMemoryDb.begin();
        inMemoryDb.delete("A");
        inMemoryDb.commit();
        assertThat(inMemoryDb.get("A"))
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void commitNoTransaction() {
        assertThat(inMemoryDb.commit())
                .isFalse();
    }

    @Test
    public void rollbackNoTransaction() {
        assertThat(inMemoryDb.rollback())
                .isFalse();
    }
}
