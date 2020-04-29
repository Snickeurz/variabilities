package choco;

import Utils.Utils;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;

/**
 * Choco implementation of Vending Machine problem.
 */
public class VendingMachine
{
    /**
     * The machine's accepted coins.
     */
    static final int[] values = {200, 100, 50, 20, 10 , 5};

    /**
     * Number of each coin the machine have.
     */
    static public final int N = 10;

    /**
     * What the user give to the machine.
     */
    static public final int A = 200;

    /**
     * What a can cost (1,35$)
     */
    static public final int D = 135;

    public static void main(final String[] args) throws ContradictionException
    {
        // create a new model with given name
        Model model = new Model("Vending Machine");

        // 0 .. N values possible with length of given values
        IntVar[] C = model.intVarArray("C", values.length, 0, N);

        // Print the difference between what the user give and the real cost of soda
        System.out.println("Diff " + (A-D));

        // Scalar
        model.scalar(C, values, "=", A-D).post();

        // Number of coins that should be given back
        IntVar NC = model.intVar("Nb coins", 0 , A);

        // check sum
        model.sum(C, "=", NC).post();

        // get solver
        Solver solver = model.getSolver();

        solver.propagate();
        // Helper method
        Utils.displayDomains(C);

        // Check solutions
        int sols = 0;
        Solution solution;

        while ((solution = solver.findSolution()) != null)
        {
            sols ++;
            System.out.println(solution);
        }

        // print out solution
        System.out.println("#sols" + sols);


        // Other method
//        // Set values
//        int n = 140;
//        IntVar P200 = model.intVar("P200", 0, n);
//        IntVar P100 = model.intVar("P100", 0, n);
//        IntVar P50 = model.intVar("P50", 0, n);
//        IntVar P20 = model.intVar("P20", 0, n);
//        IntVar P10 = model.intVar("P10", 0, n);
//        IntVar P5 = model.intVar("P5", 0, n);
//        int A = 200;
//        int B = 135;
//
//        IntVar[] vars = new IntVar[] {
//                P200, P100, P50, P20, P10, P5
//        };
//
//        int[] coeffs = new int[] {
//                200, 100, 50, 20, 10, 5
//        };
//
//        model.scalar(vars, coeffs, "=", 0).post();
//        Solver solver = model.getSolver();
//        int c = 0;
//        Solution solution;
//
//        while ((solution = solver.findSolution()) != null)
//        {
//            System.out.println(solution);
//            c++;
//        }
    }
}
