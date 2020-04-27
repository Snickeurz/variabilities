package exercicesCombinatoire.magicsquare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 *  Implementation Back tracking of Magic Square.
 *
 *  variables line by line, values : random order.
 * <p>
 *
 * @author Nicolas sibaud
 */
public class MagicSquareBT2 extends MagicSquareAbstract
{
    public static void main(final String... args)
    {
        // Create new instance
        MagicSquareAbstract m = new MagicSquareBT2(5);

        // Try to solve magic square
        if (m.solveMagicSquare())
        {
            // Magic square is solved, print it
            m.printMagicSquare();
        }
    }

    private final List<Integer> values;

    /**
     * Constructor.
     *
     * @param n size of magic square
     */
    public MagicSquareBT2(final int n)
    {
        // super constructor
        super(n);

        values = new ArrayList<>(super.n2);
        Collections.shuffle(values);
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
            t[i][j] = values.get(v); // assigned here to simplify sum checks

            // Check if couple i j v is possible value
            if (possibleValue(i, j, v))
            {
                // recursive call
                if (solveMagicSquare(i, j + 1))
                {
                    return true;
                }

                values.add(i,v);
            }
            t[i][j] = 0; // undo assignment
        }
        return false;
    }

    private boolean possibleValue(final int i, final int j, final int v)
    {
        int sl = super.sumLine(i);
        int sc = super.sumColumn(j);
        int sd1 = super.sumDiagonal1();
        int sd2 = super.sumDiagonal2();

        if (sl != super.sum)
        {
            return false;
        }
        if (sc != super.sum)
        {
            return false;
        }
        if (sd1 != super.sum)
        {
            return false;
        }
        if (sd2 != super.sum)
        {
            return false;
        }

        return true;
    }
}
