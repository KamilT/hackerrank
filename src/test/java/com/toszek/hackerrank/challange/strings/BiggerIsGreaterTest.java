package com.toszek.hackerrank.challange.strings;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BiggerIsGreaterTest {

    @Test
    void biggerIsGreater_lmno() {
        assertThat(BiggerIsGreater.biggerIsGreater("lmno")).isEqualTo("lmon");
    }

    @Test
    void biggerIsGreater_dcba() {
        assertThat(BiggerIsGreater.biggerIsGreater("dcba")).isEqualTo("no answer");
    }

    @Test
    void biggerIsGreater_dcbb() {
        assertThat(BiggerIsGreater.biggerIsGreater("dcbb")).isEqualTo("no answer");
    }

    @Test
    void biggerIsGreater_abdc() {
        assertThat(BiggerIsGreater.biggerIsGreater("abdc")).isEqualTo("acbd");
    }

    @Test
    void biggerIsGreater_abcd() {
        assertThat(BiggerIsGreater.biggerIsGreater("abcd")).isEqualTo("abdc");
    }

    @Test
    void biggerIsGreater_fedcbabcd() {
        assertThat(BiggerIsGreater.biggerIsGreater("fedcbabcd")).isEqualTo("fedcbabdc");
    }
}
