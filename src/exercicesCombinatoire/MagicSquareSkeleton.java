package exercicesCombinatoire;

import java.util.List;

/**
 * Magic Square Skeleton
 */
public class MagicSquareSkeleton extends MagicSquareAbstract {

    public static void main(String... args) {
        MagicSquareAbstract m = new MagicSquareSkeleton(3);
        if (m.solveMagicSquare())
        {
            m.printMagicSquare();
        }
    }

    public MagicSquareSkeleton(int n)
    {
        super(n);
    }

    /**
     * Implementation.
     *
     * @return true if solved else false
     */
    @Override
    public boolean solveMagicSquare() {
        // *** PUT YOUR CODE HERE ***
        // *** (compute a solution in the t[][] array) ***
        // *** (you can use the variables in the abstract class ***
        Permutations permutations = new Permutations(super.n2, 1);
        for (List<Integer> perm : permutations)
        {
            if (isSolution(perm))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if p is a solution.
     *
     * @param p List of integer from permutation
     * @return true if solution else false
     */
    private boolean isSolution(List<Integer> p)
    {
        for (int i = 0 ; i < super.n ; i++)
        {
            for (int j = 0; j < super.n ; j++)
            {
                super.t[i][j] = p.get(i * super.n + j);
            }
        }

        for (int k = 0; k < n; k++)
        {
            if (super.sumLine(k) != sum || sumColumn(k) != sum)
            {
                return false;
            }
        }

        return sumDiagonal1() == sum && sumDiagonal2() == sum;

    }
}
