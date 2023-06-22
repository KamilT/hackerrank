package com.toszek.hackerrank.challange.strings;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * https://www.hackerrank.com/challenges/gem-stones/problem
 * There is a collection of rocks where each rock has various minerals embeded in it. Each type of mineral is designated by a lowercase letter in the range Asci[a-z]. There may be multiple occurrences of a mineral in a rock. A mineral is called a gemstone if it occurs at least once in each of the rocks in the collection.
 * Given a list of minerals embedded in each of the rocks, display the number of types of gemstones in the collection.
 */
class GemstonesTest {

    @Test
    void finds2Gemstones() {
        assertThat(Gemstones.gemstones(List.of("abcdde", "baccd", "eeabg"))).isEqualTo(2);
    }
}
