package exercice1;

import exercicesCombinatoire.KPermutations;

import java.util.List;

/**
 *
 */
public class ExerciceBis
{
    //     T W O
    // +   T W O
    // = F O U R
    public static void main(final String[] args)
    {
        int nbSols = 0;
        KPermutations perm = new KPermutations(6, 10);
        for (List<Integer> p : perm)
        {
            int T = p.get(0);
            int W = p.get(1);
            int O = p.get(2);
            int F = p.get(3);
            int U = p.get(4);
            int R = p.get(5);
            if ( T != 0 && F != 0 && isSolution(T, W, O, F, U, R))
            {
                System.out.print(String.format("\nSolution is T=%d , W=%d , O=%d , F=%d , U=%d , R=%d", T, W, O, F, U, R));
                nbSols ++;
            }
        }
        System.out.println("\nNombre de sols : " + nbSols);

        System.out.println("\n===========================================\n");
        System.out.println("\n===========================================\n");

        nbSols = 0;
        perm = new KPermutations(8, 10);
        for (List<Integer> p2 : perm)
        {
            int S = p2.get(0);
            int E = p2.get(1);
            int N = p2.get(2);
            int D = p2.get(3);
            int M = p2.get(4);
            int O = p2.get(5);
            int R = p2.get(6);
            int Y = p2.get(7);
            if ( S != 0 && M != 0 && isSolution2(S,E,N,D,M,O,R,Y))
            {
                System.out.print(String.format("\nSolution is S=%d , E=%d , N=%d , D=%d , M=%d , O=%d, R=%d, Y=%d", S, E, N, D, M, O, R, Y));
                nbSols ++;
            }
        }
        System.out.println("\nNombre de sols : " + nbSols);
    }

    private static boolean isSolution(int T, int W, int O, int F, int U, int R)
    {
        return digitsToInt(T, W, O) + digitsToInt(T, W, O) == digitsToInt(F, O, U, R);
    }

    private static boolean isSolution2(int S, int E, int N, int D, int M, int O, int R, int Y)
    {
        return digitsToInt(S, E, N, D) + digitsToInt(M, O, R, E) == digitsToInt(M, O, N , E, Y);
    }

    /**
     * Check weither the solution is ok or not.
     *
     */
    private static int digitsToInt(final int ... inputVar)
    {
        int result = 0;
        for (int var : inputVar)
        {
            result = result * 10 + var;
        }
        return result;
    }
}
