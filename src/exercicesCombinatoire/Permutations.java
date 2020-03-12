package exercicesCombinatoire;

/**
 * A class to iterate over all permutations of integers.
 *
 * See {@link Combinator} for more details
 *
 * @author Daniel Diaz {@code <daniel.diaz@univ-paris1.fr>}
 */
public class Permutations extends Combinator {

    public Permutations(int length) {
        this(length, 0);
    }

    public Permutations(int length, int offset) {
        this(length, offset, 1);
    }

    public Permutations(int length, int offset, int nbOccurrences) {
        super(length, length, offset, nbOccurrences, false);
    }
}
