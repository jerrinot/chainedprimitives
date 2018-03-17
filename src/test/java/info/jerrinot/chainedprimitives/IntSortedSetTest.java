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
    public void unlinkLastNode() {
        IntSortedSet set = new IntSortedSet();
        set.add(0);
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        set.remove(2);
        set.remove(3);
        set.remove(4);


        assertTrue(set.contains(0));
        assertTrue(set.contains(1));
        assertFalse(set.contains(2));
        assertFalse(set.contains(3));
        assertFalse(set.contains(4));
    }

    @Test
    public void unlinkFirstNode() {
        IntSortedSet set = new IntSortedSet();
        set.add(0);
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(4);

        set.remove(0);
        set.remove(1);
        set.remove(2);
        set.remove(3);

        assertFalse(set.contains(0));
        assertFalse(set.contains(1));
        assertFalse(set.contains(2));
        assertFalse(set.contains(3));
        assertTrue(set.contains(4));
    }

    @Test
    public void containsMaxInt_whenContainsMaxInt() {
        IntSortedSet set = new IntSortedSet();
        set.add(Integer.MAX_VALUE);
        assertTrue(set.contains(Integer.MAX_VALUE));
    }

    @Test
    public void containsMaxInt_whenContainsZero() {
        IntSortedSet set = new IntSortedSet();
        set.add(0);
        assertFalse(set.contains(Integer.MAX_VALUE));
    }

    @Test
    public void containsMaxInt_whenEmpty() {
        IntSortedSet set = new IntSortedSet();
        assertFalse(set.contains(Integer.MAX_VALUE));
    }

    @Test
    public void containsZero_whenEmpty() {
        IntSortedSet set = new IntSortedSet();
        assertFalse(set.contains(0));
    }

    @Test
    public void toArray_withMaxIntAndZero() {
        IntSortedSet set = new IntSortedSet();
        set.add(Integer.MAX_VALUE);
        set.add(0);
        int[] ints = set.toArray();
        assertEquals(2, ints.length);
        assertEquals(0, ints[0]);
        assertEquals(Integer.MAX_VALUE, ints[1]);
    }

    @Test
    public void toArray_withMaxInt() {
        IntSortedSet set = new IntSortedSet();
        set.add(Integer.MAX_VALUE);
        int[] ints = set.toArray();
        assertEquals(1, ints.length);
        assertEquals(Integer.MAX_VALUE, ints[0]);
    }

    @Test
    public void toArray_empty() {
        IntSortedSet set = new IntSortedSet();
        int[] ints = set.toArray();
        assertEquals(0, ints.length);
    }

    @Test
    public void addRemoveAndGetMaxInt() {
        IntSortedSet set = new IntSortedSet();
        set.remove(Integer.MAX_VALUE);
        assertFalse(set.contains(Integer.MAX_VALUE));
    }

    @Test
    public void addAndDoubleRemoveMaxInt() {
        IntSortedSet set = new IntSortedSet();
        set.remove(Integer.MAX_VALUE);
        assertFalse(set.remove(Integer.MAX_VALUE));
    }

    @Test
    public void addAndRemoveMaxInt() {
        IntSortedSet set = new IntSortedSet();
        set.add(Integer.MAX_VALUE);
        assertTrue(set.remove(Integer.MAX_VALUE));
    }

    @Test
    public void getMaxInt() {
        IntSortedSet set = new IntSortedSet();
        assertFalse(set.contains(Integer.MAX_VALUE));
    }

    @Test
    public void addAndGetMaxInt() {
        IntSortedSet set = new IntSortedSet();
        set.add(Integer.MAX_VALUE);
        assertTrue(set.contains(Integer.MAX_VALUE));
    }


    @Test
    public void testRemovalNonExisting() {
        IntSortedSet set = new IntSortedSet();
        assertFalse(set.remove(0));
    }

    @Test
    public void testRemovalExisting() {
        IntSortedSet set = new IntSortedSet();
        set.add(0);

        assertTrue(set.remove(0));
    }

    @Test
    public void testDoubleRemovalExisting() {
        IntSortedSet set = new IntSortedSet();
        set.add(0);

        set.remove(0);
        assertFalse(set.remove(0));
    }

    @Test
    public void testRemovalFromHead() {
        IntSortedSet set = new IntSortedSet();
        set.add(0);
        set.add(1);
        set.add(2);
        set.add(3);

        assertTrue(set.remove(0));
        assertTrue(set.remove(1));
        assertTrue(set.remove(2));
        assertTrue(set.remove(3));
    }

    @Test
    public void testRemovalFromTail() {
        IntSortedSet set = new IntSortedSet();
        set.add(0);
        set.add(1);
        set.add(2);
        set.add(3);

        assertTrue(set.remove(3));
        assertTrue(set.remove(2));
        assertTrue(set.remove(1));
        assertTrue(set.remove(0));
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