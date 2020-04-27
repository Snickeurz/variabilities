package exercice1;

/**
 *
 */
public class Exercice
{
    //     T W O
    // +   T W O
    // = F O U R
    public static void main(final String[] args)
    {
        int solutionCounter = 0;

        // Iteration
        // First step, iterate trought 10 possibilities for T
        for (int T=1; T <= 9; T++)
        {
            // Iteration for W
            for (int W =0; W<=9; W++)
            {
                // If W == T then do nothing and continue
                if (W==T)
                {
                    continue;
                }
                // iteration for O
                for (int O = 0; O <= 9; O++)
                {
                    // Check if current O is same value of T or W
                    // if yes, do nothing and continue
                    if (O == T || O == W)
                    {
                        continue;
                    }
                    // iteration for F
                    for (int F=0; F<=9; F++)
                    {
                        if (F == O || F == W || F == T)
                        {
                            continue;
                        }
                        for (int U = 0; U <=9; U++)
                        {
                            if (U == F ||U == O || U == W || U == T)
                            {
                                continue;
                            }
                            for (int R = 0; R <= 9; R++)
                            {
                                if (R == U || R == F || R == O || R == W || R == T)
                                {
                                    continue;
                                }
                                if (isSolution(T, W, O) + isSolution(T, W, O) == isSolution(F, O, U, R))
                                {
                                    System.out.print(String.format("Solution is T=%d , W=%d , O=%d , F=%d , U=%d , R=%d", T, W, O, F, U, R));
                                    System.out.print("\n");
                                    solutionCounter++;
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.print(String.format("Number of solution %d", solutionCounter));
    }

    /**
     * Check weither the solution is ok or not.
     *
     */
    private static int isSolution(final int ... inputVar)
    {
        int result = 0;
        for (int var : inputVar)
        {
            result = result * 10 + var;
        }
        return result;
    }
}
