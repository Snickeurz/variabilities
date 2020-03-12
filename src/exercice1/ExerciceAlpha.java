package exercice1;

import exercicesCombinatoire.Permutations;

import java.util.List;

public class ExerciceAlpha
{
    public static void main(String[] args)
    {
        // ///////////////////////////
        // ////////ALPHA 13///////////
        // ///////////////////////////
        int nbSols = 0;
        Permutations perm = new Permutations(13, 1);
        for (List<Integer> p : perm)
        {
            int A = p.get(0);
            int B = p.get(1);
            int C = p.get(2);
            int E = p.get(3);
            int F = p.get(4);
            int L = p.get(5);
            int N = p.get(6);
            int O = p.get(7);
            int Q = p.get(8);
            int R = p.get(9);
            int T = p.get(10);
            int U = p.get(11);
            int S = p.get(12);

            if (B+A+L+L+E+T == 24 && C+O+N+C+E+R+T == 48 && O+B+O+E == 34 && C+E+L+L+O==29 && F+L+U+T+E==32 && Q+U+A+R+T+E+T == 39 && S+O+L+O == 37)
            {
                System.out.println("OK + " + p);
                nbSols ++;
            }
        }
        System.out.println("\nNombre de sols : " + nbSols);
    }

//    public static void main(String[] args)
//    {
//        // ///////////////////////////
//        // ////////ALPHA 12///////////
//        // ///////////////////////////
//        int nbSols = 0;
//        Permutations perm = new Permutations(12, 1);
//        for (List<Integer> p : perm)
//        {
//            int A = p.get(0);
//            int B = p.get(1);
//            int C = p.get(2);
//            int E = p.get(3);
//            int F = p.get(4);
//            int L = p.get(5);
//            int N = p.get(6);
//            int O = p.get(7);
//            int Q = p.get(8);
//            int R = p.get(9);
//            int T = p.get(10);
//            int U = p.get(11);
//
//            if (B+A+L+L+E+T == 24 && C+O+N+C+E+R+T == 48 && O+B+O+E == 34 && C+E+L+L+O==29 && F+L+U+T+E==32 && Q+U+A+R+T+E+T == 39)
//            {
//                System.out.println("OK + " + p);
//                nbSols ++;
//            }
//        }
//        System.out.println("\nNombre de sols : " + nbSols);
//    }

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
