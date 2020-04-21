package exercicesCombinatoire.magicsquare;

import exercicesCombinatoire.Permutations;

import java.util.List;

/**
 * <p>
 * Simple implementation of Magic Square.
 * <p>
 *
 * @author Nicolas sibaud
 */
public class MagicSquare extends MagicSquareAbstract
{

    public static void main(final String... args)
    {
        // Try with a magic quare of size 3
        MagicSquareAbstract m = new MagicSquare(4);
        // Call implementation
        if (m.solveMagicSquare())
        {
            // Solution found so print them
            m.printMagicSquare();
        }
    }

    /**
     * Constructor.
     *
     * @param n size of square
     */
    public MagicSquare(final int n)
    {
        super(n);
    }

    /**
     * Implementation of magic square.
     * Create a new permutation of with a lenght equal to n² and with offset of 1.
     * Check for every permutation it is a solution.
     *
     * @return true if solved else false
     */
    @Override
    public boolean solveMagicSquare()
    {
        // new permutation
        Permutations permutations = new Permutations(super.n2, 1);

        // iterate trought all permutations
        for (List<Integer> perm : permutations)
        {
            // Check solution for the current permutation
            if (isSolution(perm))
            {
                // it is a solution so true
                return true;
            }
        }
        // No solutions found
        return false;
    }

    /**
     * Helper method in charge of determining if current list is solution.
     *
     * @param p List of integer from permutation
     * @return true if solution else false
     */
    private boolean isSolution(final List<Integer> p)
    {
        // from 0 to super.n
        for (int i = 0 ; i < super.n ; i++)
        {
            // from 0 to super.n
            for (int j = 0; j < super.n ; j++)
            {
                // Select super.t with i and j range and store value
                super.t[i][j] = p.get(i * super.n + j);
            }
        }

        // from 0 to super.n -1
        for (int k = 0; k < super.n; k++)
        {
            // Check if sum from lines and column are equal to super.sum
            if (super.sumLine(k) != super.sum || sumColumn(k) != super.sum)
            {
                return false;
            }
        }

        // Determine if diagonal are equal to super.sum
        return sumDiagonal1() == super.sum && sumDiagonal2() == super.sum;

    }
}
