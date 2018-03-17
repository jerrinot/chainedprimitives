package info.jerrinot.chainedprimitives;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class TestUtils {
    public static List<Integer> randomInts(int count, int upperBound) {
        List<Integer> res = new ArrayList<>(count);
        while (res.size() != count) {
            res.add(ThreadLocalRandom.current().nextInt(upperBound));
        }
        return res;
    }

    public static <T> List<T> unique(List<T> input) {
        Set<T> set = new HashSet<>();
        set.addAll(input);
        return new ArrayList<>(set);
    }

    public static <T extends Comparable<T>> List<T> sorted(List<T> input) {
        List<T> copy = new ArrayList<>(input);
        Collections.sort(copy);
        return copy;
    }
}
