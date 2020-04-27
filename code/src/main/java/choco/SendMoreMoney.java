package choco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;

public class SendMoreMoney {
    public static void main(final String[] args)
    {
        Model model = new Model("SEND+MORE=MONEY");

        IntVar S = model.intVar("S", 1, 9);
        IntVar E = model.intVar("E", 0, 9);
        IntVar N = model.intVar("N", 0, 9);
        IntVar D = model.intVar("D", 0, 9);
        IntVar M = model.intVar("M", 1, 9);
        IntVar O = model.intVar("O", 0, 9);
        IntVar R = model.intVar("R", 0, 9);
        IntVar Y = model.intVar("Y", 0, 9);


        model.allDifferent(S, E, N, D, M, O, R, Y).post();


        IntVar[] vars = new IntVar[] {
                S, E, N, D,
                M, O, R, E,
                M, O, N, E, Y
        };


        int[] coeffs = new int[] {
                1000, 100, 10, 1,
                1000, 100, 10, 1,
                -10000, -1000, -100, -10, -1
        };



        model.scalar(vars, coeffs, "=", 0).post();

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
