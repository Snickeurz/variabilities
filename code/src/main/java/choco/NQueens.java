package choco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class NQueens
{
    public static void main(final String[] args)
    {
        Model model = new Model("N Queens");

        // Number of queens:
        int numberQueens = 8;

        IntVar[] myQueens = model.intVarArray("myQueens", numberQueens, 0, numberQueens-1);
        model.allDifferent(myQueens).post();
        for (int i = 0; i < numberQueens; i++)
        {
            for (int j = i+1; j < numberQueens; j++)
            {
//                model.arithm(myQueens[i], "!=", myQueens[j]).post();
                model.arithm(myQueens[i], "!=", myQueens[j], "+", j-i).post();
                model.arithm(myQueens[i], "!=", myQueens[j], "-", j-i).post();
            }
        }

        Solver solver = model.getSolver();

        Solution solution;
        int c = 0;

        while ((solution = solver.findSolution()) != null)
        {
            System.out.println(solution);
            c++;
        }

        System.out.println("Solutions au nombre de : " + c);
    }
}
