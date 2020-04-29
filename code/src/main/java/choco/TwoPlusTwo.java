package choco;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

/**
 * Choco implementation of TwoTwoFour.
 */
public class TwoPlusTwo
{

    public static void main(String[] args)
    {
        // Create a new model with a given name
        Model model = new Model("TWO+TWO=FOUR");

        // 1 .. 9 range
        IntVar T = model.intVar("T",1,9);
        IntVar F = model.intVar("F", 1,9);

        // 0 .. 9 range
        IntVar W = model.intVar("W", 0,9);
        IntVar O = model.intVar("O", 0,9);
        IntVar U = model.intVar("U", 0,9);
        IntVar R = model.intVar("R", 0,9);

        // We want all vars all different
        model.allDifferent(T, W, O, F, U, R).post();

        // Create an array with vars
        IntVar[] vars = new IntVar[] {
                T, W, O,
                T, W, O,
             F, O, U, R
        };

        // Create an array with coefficients
        int[] coefs = new int[] {
                100, 10, 1,
                100, 10, 1,
                -1000, -100, -10, -1,
        };

        // Creates a scalar constraint which ensures that Sum(vars[i]*coeffs[i]) operator scalar
        model.scalar(vars, coefs, "=", 0).post();

        // Get a solver
        Solver solver = model.getSolver();

        // Check solution
        Solution solution;
        int c = 0;
        while ((solution = solver.findSolution()) != null)
        {
            System.out.println(solution);
            c++;
        }
        // print out solution
        System.out.println(String.format("%d solutions trouvé", c));
    }


}
