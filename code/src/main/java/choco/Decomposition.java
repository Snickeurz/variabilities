package choco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.constraints.nary.automata.FA.FiniteAutomaton;
import org.chocosolver.solver.variables.IntVar;

public class Decomposition
{
//    public static void main (final String[] args)
//    {
//        // given a model
//        Model model = new Model("Decomposition");
//        // given n
//        int n = 5;
//
//        IntVar[] myTab = model.intVarArray("myTab", n, 0, n);
//        model.sum(myTab, "=", n).post();
//
//        Solver solver = model.getSolver();
//
//        // Check solutions
//        Solution solution;
//        int c = 0;
//
//        while (solver.solve())
//        {
//            for(int i = 0; i < n; i++)
//            {
//                int x = myTab[i].getValue();
//                if (x!= 0)
//                {
//                    System.out.println("X : " + x );
//                }
//            }
//            System.out.println();
//            c++;
//        }
////        while ((solution = solver.findSolution()) != null)
////        {
////            System.out.println(solution);
////            c++;
////        }
//
//        // Print out results
//        System.out.println("Solutions au nombre de : " + c);
//    }


    // with autoate
    public static void main (final String[] args)
    {
        // given a model
        Model model = new Model("Decomposition");
        // given n // can't work with value @11 since 10 cannot be refered
        int n = 8;

        IntVar[] myTab = model.intVarArray("myTab", n, 0, n);

        model.regular(myTab, new FiniteAutomaton("[1-<"+n+">]+[0]*")).post();
//        model.regular(myTab, new FiniteAutomaton("[1 <"+n+">]+0*")).post();
        model.sum(myTab, "=", n).post();

        Solver solver = model.getSolver();

        // Check solutions
        Solution solution;
        int c = 0;

        while (solver.solve())
        {
            for(int i = 0; i < n; i++)
            {
                int x = myTab[i].getValue();
                if (x!= 0)
                {
                    System.out.println("X : " + x );
                }
            }
            System.out.println();
            c++;
        }
//        while ((solution = solver.findSolution()) != null)
//        {
//            System.out.println(solution);
//            c++;
//        }

        // Print out results
        System.out.println("Solutions au nombre de : " + c);
    }
}
