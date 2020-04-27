package exercicesCombinatoire;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class to iterate over all combinations of integers.
 * <p>
 * It can be used to provide different form of combinations:
 * <ul>
 * <li>permutations of n ({@code length}) objects
 * <li>k-permutations (aka arrangements) of k ({@code length}) objects among n
 * ({@code nbValues})
 * <li>combinations (subsets) of k ({@code length}) objects among n
 * ({@code nbValues})
 * </ul>
 * <p>
 * If {@code subset=true} this class provides subsets else (k-)permutations.
 * <p>
 * Repetitions of elements can be allowed with the {@code nbOccurrences}
 * parameter.
 * <p>
 * The iterator of this class returns a combination as a {@code List<Integer>}.
 * Values are in [0;{@code nbValues}[ but can be shifted with an {@code offset}
 * providing values in [{@code offset};{@code nbValues+offset}[. Integers of a
 * subset are provided in ascending order.
 *
 * @author Daniel Diaz {@code <daniel.diaz@univ-paris1.fr>}
 */
public class Combinator implements Iterable<List<Integer>> {

    protected final int length;
    protected final int nbValues;
    protected final int offset;
    protected final int nbOccurrences;
    protected final boolean subset;

    /**
     * Initializes a newly created {@code exercicesCombinatoire.Combinator} object (permutations)
     * Values are in [{@code 0};{@code length}[
     *
     * @param length the length of the permutations
     */
    public Combinator(int length) {
        this(length, length);
    }

    /**
     * Initializes a newly created {@code exercicesCombinatoire.Combinator} object (permutations)
     * Values are in [{@code offset};{@code length+offset}[
     *
     * @param length the length of the permutations
     * @param offset the offset to add to 0-based values
     */
    public Combinator(int length, int offset) {
        this(length, length, offset);
    }

    /**
     * Initializes a newly created {@code exercicesCombinatoire.Combinator} object (k-permutations)
     * Values are in [{@code offset};{@code offset+nbValues}[
     *
     * @param length the length of the permutations
     * @param nbValues the number of available values
     * @param offset the offset to add to 0-based values
     */
    public Combinator(int length, int nbValues, int offset) {
        this(length, nbValues, offset, 1, false);
    }

    /**
     * Initializes a newly created {@code exercicesCombinatoire.Combinator} object (general
     * combinations). Values are in [{@code offset};{@code offset+nbValues}[
     *
     * @param length the length of the combinations (number of elements)
     * @param nbValues the number of available values
     * @param offset the offset to add to 0-based values
     * @param nbOccurrences the number of occurrences of each value (allow for
     * repetitions)
     * @param subset if {@code true} generates subsets else (k-)permutations.
     */
    public Combinator(int length, int nbValues, int offset, int nbOccurrences, boolean subset) {
        if (nbValues * nbOccurrences < length) {
            throw new IllegalArgumentException("too few values or occurrences for this length");
        }
        this.length = length;
        this.nbValues = nbValues;
        this.offset = offset;
        this.nbOccurrences = nbOccurrences;
        this.subset = subset;
    }

    @Override
    public Iterator<List<Integer>> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<List<Integer>> {

        private final int[] t; // values of the (next) combination
        private final int[] t_end; // greatest possible value for each position in t[] if subset
        private final int[] used; // count used occurrences for each value
        private boolean solAvailable;

        Itr() {
            t = new int[length];
            used = new int[nbValues];
            int v = 0;
            for (int i = 0; i < length; i++) {
                t[i] = v;
                if (++used[v] == nbOccurrences) {
                    v++;
                }
            }

            if (!subset) {
                t_end = null;
            } else {
                t_end = new int[length];
                v = nbValues - 1;
                int remain = nbOccurrences;
                for (int i = length - 1; i >= 0; i--) {
                    t_end[i] = v;
                    if (--remain == 0) {
                        v--;
                        remain = nbOccurrences;
                    }
                }
            }
            solAvailable = (length > 0);
        }

        @Override
        public boolean hasNext() {
            return solAvailable;
        }

        @Override
        public List<Integer> next() {
            if (!solAvailable) {
                return null;
            }
            List<Integer> ret = new ArrayList<>(length);
            for (int x : t) {
                ret.add(offset + x);
            }
            solAvailable = nextPermutation();
            return ret;
        }

        private boolean nextPermutation() {
            int i = length, vEnd;
            int v;

            do {
                v = t[--i];
                vEnd = (subset) ? t_end[i] : nbValues - 1;
                used[v]--;
                do {
                    v++;
                } while (v <= vEnd && used[v] == nbOccurrences);
            } while (i > 0 && v > vEnd);

            if (v > vEnd) {
                return false;
            }

            do {
                if (used[v] < nbOccurrences) {
                    t[i++] = v;
                    used[v]++;
                    if (!subset) {
                        v = 0;
                    }
                } else {
                    v++;
//                    if (v >= nbValues) { // check - should never occurs
//                        System.out.println("ERR : OCCURS !!!");
//                        return false;
//                    }
                }
            } while (i < length);
            return true;
        }
    }

    /* ------- MAIN -------- */
    private static void exhaustiveTest() {
        int errors = 0;
        for (int length = 1; length <= 7; length++) {
            for (int nbOccurrences = 1; nbOccurrences <= 6; nbOccurrences++) {
                int min = (length + nbOccurrences - 1) / nbOccurrences;
                for (int nbValues = min; nbValues <= min + 6; nbValues++) {
                    int offset = (int) (Math.random() * 100);
                    for (int i = 0; i < 2; i++) {
                        boolean subset = i == 1;
                        System.out.printf("************ check(%d,%d,%d,%d,%s) = ", length, nbValues, offset, nbOccurrences, subset);
                        Combinator comb = new Combinator(length, nbValues, offset, nbOccurrences, subset);
                        int sols = check(comb);
                        if (sols < 0) {
                            errors++;
                            System.err.println("ERROR");
                        } else {
                            System.out.println(sols);
                        }
                    }
                }
            }
        }
        if (errors > 0) {
            System.err.println("TOTAL errors : " + errors);
            System.exit(1);
        }
        System.out.println("All tests succeeded");
        System.exit(0);
    }

    private static int check(Combinator comb) {
        int length = comb.length;
        int nbValues = comb.nbValues;
        int offset = comb.offset;
        int nbOccurrences = comb.nbOccurrences;
        boolean subset = comb.subset;

        List<List<Integer>> l1 = new ArrayList<>();
        for (List<Integer> c : comb) {
            l1.add(c);
        }

        boolean checkOk = true;
        int count2 = 0;

        if (length > 0) {
            int[] t = new int[length];
            int i = 0;
            t[0] = -1;
            do {
                t[i]++;
                while (++i < length) {
                    t[i] = 0;
                }
                //System.out.println("+++ " + Arrays.toString(t));
                List<Integer> d = listIfOK(t, length, nbValues, offset, nbOccurrences, subset);
                if (d != null) {
                    if (count2 >= l1.size() || !l1.get(count2).equals(d)) {
                        System.out.println("ERROR -> missing " + d);
                        checkOk = false;
                    }
                    count2++;
                }
                while (--i >= 0 && t[i] == nbValues - 1) {
                }
            } while (i >= 0);
        }
        if (checkOk && count2 != l1.size()) {
            System.err.println("ERROR - too many combinations remaining");
            checkOk = false;
        }
        return checkOk ? count2 : -1;
    }

    private static List<Integer> listIfOK(int[] t, int length, int nbValues, int offset, int nbOccurrences, boolean subset) {
        int[] occ = new int[nbValues];
        if (subset && !isSorted(t)) {
            return null;
        }
        List<Integer> d = new ArrayList<>(length);
        for (int x : t) {
            if (x >= nbValues) {
                System.err.println("OCCURS ERR : " + x);
                return null;
            }
            if (++occ[x] > nbOccurrences) {
                return null;
            }
            d.add(x + offset);
        }
        return d;
    }

    private static boolean isSorted(int[] t) {
        for (int i = 1; i < t.length; i++) {
            if (t[i - 1] > t[i]) {
                return false;
            }
        }
        return true;
    }

    public static void main(String... args) {
        //exhaustiveTest();

        Combinator comb = new Combinator(4, 3, 0, 3, false);
        check(comb);
        int count = 0;
        for (List<Integer> c : comb) {
            System.out.println(c);
            count++;
        }
        System.out.println("#comb : " + count);
    }
}
