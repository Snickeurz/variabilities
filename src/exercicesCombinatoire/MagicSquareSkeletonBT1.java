package exercicesCombinatoire;

/**
 * Magic Square Skeleton
 */
public class MagicSquareSkeletonBT1 extends MagicSquareAbstract
{
    public static void main(final String... args)
    {
        MagicSquareAbstract m = new MagicSquareSkeletonBT1(4);
        if (m.solveMagicSquare()) {
            m.printMagicSquare();
        }
    }

    private final boolean[] taken; // taken[v] = true if and only if value v already taken

    public MagicSquareSkeletonBT1(final int n)
    {
        super(n);
        taken = new boolean[super.n2 +1]; // actual values in  1..n2
    }
    
    @Override
    public boolean solveMagicSquare()
    {
        // *** PUT YOUR CODE HERE ***
        // *** (compute a solution in the t[][] array) ***
        // *** (you can use the variables in the abstract class ***
        return solveMagicSquare(0,0);
    }

    private boolean solveMagicSquare(int i, int j)
    {
        if (j >= super.n)
        {
            j=0;
            i++;
        }

        if (i >= super.n)
        {
//            super.printMagicSquare();
//            return false;
            return true;
        }

        for (int v=1; v <= super.n2; v++) {
            t[i][j] = v; // assigned here to simplify sum checks
            if (possibleValue(i, j, v)) {
                // try this value (record changes)
                taken[v] = true;
                if (solveMagicSquare(i, j + 1)) {
                    return true;
                }

                taken[v] = false;
            }
            t[i][j] = 0; // undo
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
        if (i == super.n && super.sumColumn(j) != super.sum)
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
