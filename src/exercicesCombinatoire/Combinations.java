package exercicesCombinatoire;

/**
 * A class to iterate over all combinations (subsets) of integers.
 *
 * See {@link Combinator} for more details
 *
 * @author Daniel Diaz {@code <daniel.diaz@univ-paris1.fr>}
 */
public class Combinations extends Combinator {

    public Combinations(int length, int nbValues) {
        this(length, nbValues, 0);
    }

    public Combinations(int length, int nbValues, int offset) {
        this(length, nbValues, offset, 1);
    }

    public Combinations(int length, int nbValues, int offset, int nbOccurrences) {
        super(length, nbValues, offset, nbOccurrences, true);
    }
}
