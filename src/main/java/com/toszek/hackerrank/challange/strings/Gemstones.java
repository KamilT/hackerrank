package com.toszek.hackerrank.challange.strings;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * https://www.hackerrank.com/challenges/gem-stones/problem
 * There is a collection of rocks where each rock has various minerals embeded in it. Each type of mineral is designated by a lowercase letter in the range Asci[a-z]. There may be multiple occurrences of a mineral in a rock. A mineral is called a gemstone if it occurs at least once in each of the rocks in the collection.
 * Given a list of minerals embedded in each of the rocks, display the number of types of gemstones in the collection.
 */
public class Gemstones {
    public static int gemstones(List<String> arr) {
        List<String> rocks = new ArrayList<>(arr); // make a copy so we don't modify original array
        // find rock with the smallest size
        String sourceRock = null;
        int sourceRockSize = Integer.MAX_VALUE;
        for(String rock : rocks) {
            if(rock.length() < sourceRockSize) {
               sourceRockSize = rock.length();
               sourceRock = rock;
            }
        }

        Set<Character> gemSet = new HashSet<>();
        // add the smallest rock minerals to counter
        for(int mineralIndex = 0; mineralIndex < sourceRock.length(); mineralIndex +=1) {
            gemSet.add(sourceRock.charAt(mineralIndex));
        }

        // remove source rock from rocks to check
        rocks.remove(sourceRock);

        // check other rocks for gems
        for (int rockIndex = 0; rockIndex < rocks.size(); rockIndex++) {
            final String rock = rocks.get(rockIndex);
            final Iterator<Character> gemsIterator = gemSet.iterator();
            while (gemsIterator.hasNext()) {
                final Character gem = gemsIterator.next();
                if(!rock.contains(gem.toString())) {
                    gemsIterator.remove();
                }
            }
            if(gemSet.isEmpty()) {
                return 0;
            }
        }
        return gemSet.size();
    }
}
