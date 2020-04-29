package choco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

/**
 * Choco implementation of NQueens problem.
 */
public class NQueens
{
    public static void main(final String[] args)
    {
        // Create a new model with a given name
        Model model = new Model("N Queens");

        // Declare number of queens:
        int numberQueens = 8;

        // Create an array with queens
        IntVar[] myQueens = model.intVarArray("myQueens", numberQueens, 0, numberQueens-1);

        // Comment following lines if code @line 25 is enabled
        model.allDifferent(myQueens).post();

        for (int i = 0; i < numberQueens; i++)
        {
            for (int j = i+1; j < numberQueens; j++)
            {
                // Comment following lines if code @line 21 is enabled
                // model.arithm(myQueens[i], "!=", myQueens[j]).post();

                // Check if queens are not equal
                model.arithm(myQueens[i], "!=", myQueens[j], "+", j-i).post();
                model.arithm(myQueens[i], "!=", myQueens[j], "-", j-i).post();
            }
        }

        // Get a solver
        Solver solver = model.getSolver();

        // Check solutions
        Solution solution;
        int c = 0;
        while ((solution = solver.findSolution()) != null)
        {
            System.out.println(solution);
            c++;
        }
        // print out solution
        System.out.println("Solutions au nombre de : " + c);
    }
}
