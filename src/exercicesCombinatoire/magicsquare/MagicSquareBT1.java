package exercicesCombinatoire.magicsquare;

/**
 * <p>
 *  Implementation Back tracking of Magic Square.
 *
 *  variables line by line, values : ascending order.
 * <p>
 *
 * @author Nicolas sibaud
 */
public class MagicSquareBT1 extends MagicSquareAbstract
{
    public static void main(final String... args)
    {
        // Create new instance
        MagicSquareAbstract m = new MagicSquareBT1(3);

        // Try to solve magic square
        if (m.solveMagicSquare())
        {
            // Magic square is solved, print it
            m.printMagicSquare();
        }
    }

    /**
     * Array of taken values.
     */
    private final boolean[] taken; // taken[v] = true if and only if value v already taken

    /**
     * Constructor.
     *
     * @param n size of magic square
     */
    public MagicSquareBT1(final int n)
    {
        // super constructor
        super(n);

        // new array
        taken = new boolean[super.n2 +1]; // actual values in  1..n2
    }

    /**
     * Implementation.
     *
     * @return true if solved, else false
     */
    @Override
    public boolean solveMagicSquare()
    {
        return this.solveMagicSquare(0,0);
    }

    /**
     * Helper method in charge of determining if magic square can be solved.
     *
     * @param i int
     * @param j int
     * @return true if solved, else false
     */
    private boolean solveMagicSquare(int i, int j)
    {
        // Check for column
        if (j >= super.n)
        {
            j=0;
            i++;
        }

        // check for line
        if (i >= super.n)
        {
            // display all solutions
//            super.printMagicSquare();
//            return false;

            // display first solution
            return true;
        }

        // Iterate trough all values from 1 to super.n²
        for (int v=1; v <= super.n2; v++)
        {
            t[i][j] = v; // assigned here to simplify sum checks

            // Check if couple i j v is possible value
            if (possibleValue(i, j, v))
            {
                // try this value (record changes)
                taken[v] = true;
                // recursive call
                if (solveMagicSquare(i, j + 1))
                {
                    return true;
                }

                taken[v] = false;
            }
            t[i][j] = 0; // undo assignment
        }
        return false;
    }

    private boolean possibleValue(final int i, final int j, final int v)
    {
        if (taken[v])
        {
            return false;
        }
        if (j == super.n - 1 && super.sumLine(i) != super.sum)
        {
            return false;
        }
        if (i == super.n - 1 && super.sumColumn(j) != super.sum)
        {
            return false;
        }
        if (i == super.n - 1 && j == n-1 && super.sumDiagonal1() != super.sum)
        {
            return false;
        }
        if (i == super.n - 1 && j == 0 && super.sumDiagonal2() != super.sum)
        {
            return false;
        }

        return true;
    }
}
