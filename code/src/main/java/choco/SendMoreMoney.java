package choco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.variables.IntVar;

public class SendMoreMoney
{
    public static void main(final String[] args)
    {
        //        // Create a new model
        Model model = new Model("SendMoreMoney");

        // create new var
        IntVar S = model.intVar("S", 1, 9);
        IntVar E = model.intVar("E", 0, 9);
        IntVar N = model.intVar("N", 0, 9);
        IntVar D = model.intVar("D", 0, 9);
        IntVar M = model.intVar("M", 0, 9);
        IntVar O = model.intVar("O", 0, 9);
        IntVar R = model.intVar("R", 0, 9);
        IntVar Y = model.intVar("Y", 0, 9);


        model.allDifferent(S,E,N,D,M,O,R,Y).post();


        IntVar[] vars = new IntVar[] {
                S, E, N, D,
                M, O, R, E,
                M, O, N, E, Y
        };

        int[] coefs = new int[] {
                1000, 100, 10, 1,
                1000, 100, 10, 1,
                -10000, -1000, -100, -10, -1
        };

        model.scalar(vars, coefs, "=", 0).post();

        Solver solver = model.getSolver();

        solver.setSearch(Search.inputOrderLBSearch(vars));

        solver.solve();
        System.out.println(solver.findSolution());
    }
}
