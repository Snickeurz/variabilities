package exercicesCombinatoire.magicsquare;

/**
 * Magic Square Skeleton
 */
public class MagicSquareSkeleton extends MagicSquareAbstract
{
    public static void main(final String... args)
    {
        MagicSquareAbstract m = new MagicSquareSkeleton(3);
        if (m.solveMagicSquare()) {
            m.printMagicSquare();
        }
    }

    public MagicSquareSkeleton(final int n)
    {
        super(n);
    }
    
    @Override
    public boolean solveMagicSquare()
    {
        // *** PUT YOUR CODE HERE ***
        // *** (compute a solution in the t[][] array) ***
        // *** (you can use the variables in the abstract class ***
        return false;
    }
}
