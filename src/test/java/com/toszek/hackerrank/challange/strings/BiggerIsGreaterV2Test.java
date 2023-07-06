package com.toszek.hackerrank.challange.strings;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BiggerIsGreaterV2Test {

    @Test
    void biggerIsGreater_lmno() {
        assertThat(BiggerIsGreaterV2.biggerIsGreater("lmno")).isEqualTo("lmon");
    }

    @Test
    void biggerIsGreater_dcba() {
        assertThat(BiggerIsGreaterV2.biggerIsGreater("dcba")).isEqualTo("no answer");
    }

    @Test
    void biggerIsGreater_dcbb() {
        assertThat(BiggerIsGreaterV2.biggerIsGreater("dcbb")).isEqualTo("no answer");
    }

    @Test
    void biggerIsGreater_abdc() {
        assertThat(BiggerIsGreaterV2.biggerIsGreater("abdc")).isEqualTo("acbd");
    }

    @Test
    void biggerIsGreater_abcd() {
        assertThat(BiggerIsGreaterV2.biggerIsGreater("abcd")).isEqualTo("abdc");
    }

    @Test
    void biggerIsGreater_fedcbabcd() {
        assertThat(BiggerIsGreaterV2.biggerIsGreater("fedcbabcd")).isEqualTo("fedcbabdc");
    }
}
