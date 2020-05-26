package exercicesCombinatoire;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

/**
 *
 * @author Daniel Diaz {@code <daniel.diaz@univ-paris1.fr>}
 */
public class SudokuSkeleton {

    static int[][] grid0 = { // hard
        {0, 0, 0, 2, 0, 0, 0, 3, 0},
        {0, 6, 0, 0, 8, 0, 9, 0, 4},
        {0, 0, 8, 4, 0, 7, 0, 0, 0},
        {1, 8, 0, 0, 0, 0, 0, 0, 3},
        {0, 0, 6, 0, 1, 0, 2, 0, 0},
        {2, 0, 0, 0, 0, 0, 0, 9, 5},
        {0, 0, 0, 1, 0, 8, 4, 0, 0},
        {4, 0, 7, 0, 3, 0, 0, 5, 0},
        {0, 1, 0, 0, 0, 4, 0, 0, 0}};

    static int[][] grid = { // very hard (diabolik)
        {0, 0, 9, 0, 0, 0, 0, 4, 0},
        {7, 5, 0, 0, 0, 0, 0, 9, 0},
        {4, 3, 0, 0, 9, 1, 0, 0, 0},
        {0, 0, 2, 5, 0, 0, 1, 8, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 7, 5, 0, 0, 3, 6, 0, 0},
        {0, 0, 0, 2, 6, 0, 0, 7, 5},
        {0, 6, 0, 0, 0, 0, 0, 1, 3},
        {0, 8, 0, 0, 0, 0, 9, 0, 0}};

    public static void main(String[] args) {
        Model model = new Model("Sodoku");

        int n = 3;
        int n2 = n * n;

        // *** DECLARE THE MAIN MATRIX ***
        // Use IntVar[][] t = model.intVarMatrix(#lines, #columns, lower bound, upper bound);

        // *** INITIALIZE t[i][j] according to grid[i][j] ***

        // *** SET CONSTRAINTS ON LINES ***

        // *** SET CONSTRAINTS ON COLUMNS ***

        // *** SET CONSTRAINTS ON REGIONS ***
        // NB: regions can be numbered (r) from 0 to n2 (excluded)
        // region r: first coord i0 = r / n * n and j0 = r % n * n
        // for a region r you can do 2 nested loops on i and j
        // i from i0 to i0+n (excluded) and j from j0 to j0+n (excluded)

        // *** SOLVE ***

    }

    private static void displaySudoku(int n, IntVar[][] t) {
        int n2 = n * n;
        int width = String.valueOf(n2).length();
        System.out.println("--- SOL --- " + n2 + "x" + n2);
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            s.append("+");
            for (int j = 0; j < n * 2 * width + 1; j++) {
                s.append("-");
            }
        }
        s.append("+");
        for (int i = 0; i < n2; i++) {
            if (i % n == 0) {
                System.out.println(" " + s);
            }
            for (int j = 0; j < n2; j++) {
                if (j % n == 0) {
                    System.out.print(" |");
                }
                System.out.printf(" %" + width + "d", t[i][j].getValue());
            }
            System.out.println(" |");
        }
        System.out.println(" " + s);
    }

}
