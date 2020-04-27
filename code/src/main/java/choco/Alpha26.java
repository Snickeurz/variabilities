package choco;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;
/*
 * The numbers 1-26 have been randomly assigned to letters A-Z.
 * The numbers beside each word are the total of the values
 * assigned to the letters in the word.
 * Find the value of each letter under the equations:
 *
 *    BALLET  45     GLEE  66     POLKA      59     SONG     61
 *    CELLO   43     JAZZ  58     QUARTET    50     SOPRANO  82
 *    CONCERT 74     LYRE  47     SAXOPHONE 134     THEME    72
 *    FLUTE   30     OBOE  53     SCALE      51     VIOLIN  100
 *    FUGUE   50     OPERA 65     SOLO       37     WALTZ    34
 *
 * Solution
 * [A, B,C, D, E,F, G, H, I, J, K,L,M, N, O, P,Q, R, S,T,U, V,W, X, Y, Z]
 * [5,13,9,16,20,4,24,21,25,17,23,2,8,12,10,19,7,11,15,3,1,26,6,22,14,18]
 *
 * @author Daniel Diaz
 */
public class Alpha26 {
    public static void main(String[] args) {
        Model model = new Model("ALPHA26");
        IntVar A = model.intVar("A", 1, 26);
        IntVar B = model.intVar("B", 1, 26);
        IntVar C = model.intVar("C", 1, 26);
        IntVar D = model.intVar("D", 1, 26);
        IntVar E = model.intVar("E", 1, 26);
        IntVar F = model.intVar("F", 1, 26);
        IntVar G = model.intVar("G", 1, 26);
        IntVar H = model.intVar("H", 1, 26);
        IntVar I = model.intVar("I", 1, 26);
        IntVar J = model.intVar("J", 1, 26);
        IntVar K = model.intVar("K", 1, 26);
        IntVar L = model.intVar("L", 1, 26);
        IntVar M = model.intVar("M", 1, 26);
        IntVar N = model.intVar("N", 1, 26);
        IntVar O = model.intVar("O", 1, 26);
        IntVar P = model.intVar("P", 1, 26);
        IntVar Q = model.intVar("Q", 1, 26);
        IntVar R = model.intVar("R", 1, 26);
        IntVar S = model.intVar("S", 1, 26);
        IntVar T = model.intVar("T", 1, 26);
        IntVar U = model.intVar("U", 1, 26);
        IntVar V = model.intVar("V", 1, 26);
        IntVar W = model.intVar("W", 1, 26);
        IntVar X = model.intVar("X", 1, 26);
        IntVar Y = model.intVar("Y", 1, 26);
        IntVar Z = model.intVar("Z", 1, 26);
        model.allDifferent(A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z).post();
        setConstraint(model, 45, B, A, L, L, E, T);
        setConstraint(model, 43, C, E, L, L, O);
        setConstraint(model, 74, C, O, N, C, E, R, T);
        setConstraint(model, 30, F, L, U, T, E);
        setConstraint(model, 50, F, U, G, U, E);
        setConstraint(model, 66, G, L, E, E);
        setConstraint(model, 58, J, A, Z, Z);
        setConstraint(model, 47, L, Y, R, E);
        setConstraint(model, 53, O, B, O, E);
        setConstraint(model, 65, O, P, E, R, A);
        setConstraint(model, 59, P, O, L, K, A);
        setConstraint(model, 50, Q, U, A, R, T, E, T);
        setConstraint(model, 134, S, A, X, O, P, H, O, N, E);
        setConstraint(model, 51, S, C, A, L, E);
        setConstraint(model, 37, S, O, L, O);
        setConstraint(model, 61, S, O, N, G);
        setConstraint(model, 82, S, O, P, R, A, N, O);
        setConstraint(model, 72, T, H, E, M, E);
        setConstraint(model, 100, V, I, O, L, I, N);
        setConstraint(model, 34, W, A, L, T, Z);
        Solver solver = model.getSolver();
        Solution solution = solver.findSolution();
        System.out.println(solution);
    }

    private static void setConstraint(final Model model, final int sum, final IntVar ... v)
    {
        model.sum(v,"=",sum).post();
    }
}