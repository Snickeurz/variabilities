package choco;

import Utils.Utils;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;

public class VendingMachine
{

    static final int[] values = {200, 100, 50, 20, 10 , 5};

    static public final int N = 10;
    static public final int A = 200;
    static public final int D = 135;

    public static void main(final String[] args) throws ContradictionException
    {
        // create a new model
        Model model = new Model("Vending Machine");
        IntVar[] C = model.intVarArray("C", values.length, 0, N);

        System.out.println("Diff " + (A-D));
        model.scalar(C, values, "=", A-D).post();
        IntVar NC = model.intVar("Nb coins", 0 , A);
        // NC : number coins
        model.sum(C, "=", NC).post();

        Solver solver = model.getSolver();

        solver.propagate();
        Utils.displayDomains(C);

        int sols = 0;
        Solution solution;

        while ((solution = solver.findSolution()) != null)
        {
            sols ++;
            System.out.println(solution);
        }
        System.out.println("#sols" + sols);

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
