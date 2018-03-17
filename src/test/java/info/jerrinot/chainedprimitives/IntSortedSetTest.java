package info.jerrinot.chainedprimitives;

import org.junit.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import static info.jerrinot.chainedprimitives.TestUtils.randomInts;
import static info.jerrinot.chainedprimitives.TestUtils.sorted;
import static info.jerrinot.chainedprimitives.TestUtils.unique;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IntSortedSetTest {
    private static final int SIZE = 1000;
    private static final int UPPER_BOUND = 800;

    @Test
    public void testRemovalNonExisting() {
        IntSortedSet intSortedSet = new IntSortedSet();
        assertFalse(intSortedSet.remove(0));
    }

    @Test
    public void testRemovalExisting() {
        IntSortedSet intSortedSet = new IntSortedSet();
        intSortedSet.add(0);

        assertTrue(intSortedSet.remove(0));
    }

    @Test
    public void testDoubleRemovalExisting() {
        IntSortedSet intSortedSet = new IntSortedSet();
        intSortedSet.add(0);

        intSortedSet.remove(0);
        assertFalse(intSortedSet.remove(0));
    }

    @Test
    public void testRemovalFromHead() {
        IntSortedSet intSortedSet = new IntSortedSet();
        intSortedSet.add(0);
        intSortedSet.add(1);
        intSortedSet.add(2);
        intSortedSet.add(3);

        assertTrue(intSortedSet.remove(0));
        assertTrue(intSortedSet.remove(1));
        assertTrue(intSortedSet.remove(2));
        assertTrue(intSortedSet.remove(3));
    }

    @Test
    public void testRemovalFromTail() {
        IntSortedSet intSortedSet = new IntSortedSet();
        intSortedSet.add(0);
        intSortedSet.add(1);
        intSortedSet.add(2);
        intSortedSet.add(3);

        assertTrue(intSortedSet.remove(3));
        assertTrue(intSortedSet.remove(2));
        assertTrue(intSortedSet.remove(1));
        assertTrue(intSortedSet.remove(0));
    }

    @Test
    public void testRandomizedRemoves() {
        IntSortedSet intSortedSet = new IntSortedSet();
        for (int i = 0; i < SIZE; i++) {
            intSortedSet.add(i);
        }

        List<Integer> generatedInts = randomInts(SIZE, UPPER_BOUND * 2);
        List<Integer> controlList = IntStream.range(0, SIZE).boxed().collect(toList());
        for (Integer itemToRemove : generatedInts) {
            boolean expectedResult = controlList.remove(itemToRemove);
            boolean actualResult = intSortedSet.remove(itemToRemove);
            assertEquals("Failure when removing value " + itemToRemove + ".", expectedResult, actualResult);
        }
    }

    @Test
    public void testRandomizedContains() {
        IntSortedSet intSortedSet = new IntSortedSet();
        List<Integer> generatedInts = randomInts(SIZE, UPPER_BOUND);
        for (Integer i : generatedInts) {
            intSortedSet.add(i);
        }

        int itemsToTry = 5000;
        for (int i = 0; i < itemsToTry; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(UPPER_BOUND * 2);
            boolean shouldBeFound = generatedInts.contains(randomInt);
            boolean actuallyFound = intSortedSet.contains(randomInt);
            assertEquals(shouldBeFound, actuallyFound);
        }
    }

    @Test
    public void testRandomizedInsertion() {
        int iterationCount = 100;

        for (int iteration = 0; iteration < iterationCount; iteration++) {
            IntSortedSet intSortedSet = new IntSortedSet();
            List<Integer> generatedInts = randomInts(SIZE, UPPER_BOUND);
            for (Integer i : generatedInts) {
                intSortedSet.add(i);
            }
            int[] sortedInts = intSortedSet.toArray();
            List<Integer> expectedItems = unique(sorted(generatedInts));

            assertEquals(expectedItems.size(), sortedInts.length);
            for (int i = 0; i < sortedInts.length; i++) {
                assertEquals((int) expectedItems.get(i), sortedInts[i]);
            }
        }
    }



}