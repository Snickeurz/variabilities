package choco.sudoku;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;

import java.util.Scanner;

import static java.lang.System.exit;

/**
 * Choco implementation of Sudoku.
 */
public class Sudoku
{
    /**
     * Grid with difficulty "hard".
     */
    static int[][] GRID_HARD = { // hard
        {0, 0, 0, 2, 0, 0, 0, 3, 0},
        {0, 6, 0, 0, 8, 0, 9, 0, 4},
        {0, 0, 8, 4, 0, 7, 0, 0, 0},
        {1, 8, 0, 0, 0, 0, 0, 0, 3},
        {0, 0, 6, 0, 1, 0, 2, 0, 0},
        {2, 0, 0, 0, 0, 0, 0, 9, 5},
        {0, 0, 0, 1, 0, 8, 4, 0, 0},
        {4, 0, 7, 0, 3, 0, 0, 5, 0},
        {0, 1, 0, 0, 0, 4, 0, 0, 0}};

    /**
     * Grid with difficulty "diabolik".
     */
    static int[][] GRID_DIABOLIK = { // very hard (diabolik)
        {0, 0, 9, 0, 0, 0, 0, 4, 0},
        {7, 5, 0, 0, 0, 0, 0, 9, 0},
        {4, 3, 0, 0, 9, 1, 0, 0, 0},
        {0, 0, 2, 5, 0, 0, 1, 8, 0},
        {0, 0, 0, 0, 1, 0, 0, 0, 0},
        {0, 7, 5, 0, 0, 3, 6, 0, 0},
        {0, 0, 0, 2, 6, 0, 0, 7, 5},
        {0, 6, 0, 0, 0, 0, 0, 1, 3},
        {0, 8, 0, 0, 0, 0, 9, 0, 0}};

    /**
     * Grid with 16x16.
     */
    static int[][] MONSTER_GRID = {
            {0,3,0,0,0,4,12,0,0,14,9,0,0,0,13,0},
            {5,4,12,0,0,10,9,0,0,8,15,0,0,2,16,1},
            {0,0,10,9,0,13,0,1,12,0,2,0,6,4,0,0},
            {0,0,7,2,3,14,0,5,6,0,16,11,9,10,0,0},
            {9,0,0,0,5,0,1,0,0,6,0,8,0,0,0,10},
            {0,0,0,0,0,0,10,16,4,7,0,0,0,0,0,0},
            {0,1,0,11,0,0,0,0,0,0,0,0,4,0,5,0},
            {16,0,15,0,0,0,11,6,14,3,0,0,0,1,0,2},
            {2,0,14,0,0,0,4,13,8,10,0,0,0,5,0,16},
            {0,9,0,12,0,0,0,0,0,0,0,0,8,0,1,0},
            {0,0,0,0,0,0,14,15,16,11,0,0,0,0,0,0},
            {10,0,0,0,8,0,7,0,0,1,0,5,0,0,0,15},
            {0,0,4,1,11,8,0,12,10,0,14,9,15,16,0,0},
            {0,0,3,8,0,16,0,9,5,0,1,0,2,6,0,0},
            {14,16,9,0,0,7,6,0,0,2,8,0,0,12,3,13},
            {0,15,0,0,0,2,5,0,0,16,6,0,0,0,4,0}
    };

    /**
     * Grid with 25x25.
     */
    static int[][] GOD_LIKE = {
            { 0, 0, 0, 0, 0,     9, 1, 2, 0, 0,     0, 6, 17, 19, 16,   10, 0, 0, 5, 0,     20, 11, 13, 0, 0 },
            { 0, 20, 0, 0, 0,    14, 8, 0, 17, 0,   0, 3, 18, 0, 7,     0, 0, 0, 0, 0,      0, 2, 0, 0, 22 },
            { 1, 12, 0, 15, 25,  0, 0, 11, 22, 0,   14, 0, 23, 0, 0,    0, 0, 0, 20, 18,    0, 0, 7, 9 ,4 },
            { 22, 14, 0, 0, 0,   0, 4, 0, 0, 0,     0, 11, 0, 0, 0,     21, 24, 17, 8, 23,  15, 18, 10, 0, 0 },
            { 23, 5, 0, 0, 0,    0, 3, 12, 0, 7,    10, 0, 0, 0, 0,     25, 6, 0, 13, 0,    1, 21, 0, 0, 24 },
            { 0, 0, 0, 0, 0,     12, 0, 6, 13, 0,   0, 0, 16, 0, 15,    18, 0, 23, 11, 0,   21, 0, 1, 0, 0 },
            { 0, 13, 12, 0, 0,   0, 0, 9, 0, 23,    18, 21, 0, 0, 0,    14, 0, 0, 10, 0,    2, 24, 15, 0, 25 },
            { 24, 0, 23, 7, 11,  0, 0, 0, 25, 0,    0, 19, 0, 0, 0,     0, 0, 15, 2, 0,     9, 0, 5, 0, 6 },
            { 19, 2, 9, 17, 0,   0, 0, 1, 0, 0,     11, 0, 14, 0, 0,    13, 0, 24, 4, 0,    3, 0, 16, 18, 0 },
            { 8, 0, 3, 0, 16,    24, 0, 0, 0, 0,    7, 13, 0, 0, 23,    0, 0, 0, 9, 0,      0, 19, 0, 22, 0 },
            { 3, 0, 0, 0, 22,    0, 0, 0, 12, 4,    0, 1, 21, 0, 0,     0, 0, 0, 18, 16,    0, 0, 0, 19, 20 },
            { 0, 0, 8, 13, 0,    0, 14, 0, 0, 25,   3, 0, 19, 12, 20,   9, 0, 0, 7, 6,      0, 1, 0, 0, 16 },
            { 0, 0, 19, 16, 0,   0, 18, 3, 0, 21,   13, 0, 9, 0, 8,     12, 0, 4, 25, 0,    0, 10, 24, 0, 0 },
            { 15, 0, 0, 4, 0,    10, 19, 0, 0, 24,  5, 16, 7, 0, 18,    20, 0, 0, 17, 0,    0, 3, 9, 0, 0 },
            { 12, 6, 0, 0, 0,    22, 7, 0, 0, 0,    0, 0, 24, 14, 0,    19, 23, 0, 0, 0,    18, 0, 0, 0, 21 },
            { 0, 22, 0, 24, 0,   0, 2, 0, 0, 0,     23, 0, 0, 13, 6,    0, 0, 0, 0, 11,     16, 0, 20, 0, 17 },
            { 0, 8, 5, 0, 23,    0, 24, 21, 0, 12,  0, 0, 25, 0, 22,    0, 0, 16, 0, 0,     0, 13, 19, 2, 18 },
            { 16, 0, 15, 0, 1,   0, 13, 19, 0, 0,   0, 0, 0, 7, 0,      0, 5, 0, 0, 0,      24, 25, 21, 0, 14 },
            { 4, 0, 20, 18, 14,  0, 23, 0, 0, 3,    0, 0, 0, 17, 2,     1, 0, 9, 0, 0,      0, 0, 11, 7, 0 },
            { 0, 0, 11, 0, 12,   0, 15, 22, 0, 17,  16, 0, 20, 0, 0,    0, 10, 7, 0, 25,    0, 0, 0, 0, 0 },
            { 20, 0, 0, 22, 3,   0, 25, 0, 9, 19,   0, 0, 0, 0, 24,     11, 0, 12, 21, 0,   0, 0, 0, 10, 1 },
            { 0, 0, 18, 11, 4,   23, 20, 17, 5, 8,  0, 0, 0, 1, 0,      0, 0, 0, 14, 0,     0, 0, 0, 25, 2 },
            { 9, 10, 24, 0, 0,   13, 12, 0, 0, 0,   0, 0, 22, 0, 5,     0, 17, 19, 0, 0,    23, 20, 0, 21, 7 },
            { 2, 0, 0, 12, 0,    0, 0, 0, 0, 0,     20, 0, 8, 18, 0,    0, 3, 0, 23, 9,     0, 0, 0, 14, 0 },
            { 0, 0, 7, 23, 21,   0, 22, 0, 0, 6,    19, 12, 11, 25, 0,  0, 0, 20, 16, 2,    0, 0, 0, 0, 0 } };

    /**
     * Main method.
     *
     * @param args jvm args
     */
    public static void main(final String[] args)
    {
//        do
//        {
//            showMenu();
//            Scanner input = new Scanner(System.in);
//            int choice = input.nextInt();
//            switch (choice)
//            {
//                case 1:
//                    generateSudoku();
//                    break;
//                case 2:
//                    sudoku9x9();
//                    break;
//                case 3:
//                    sudoku16x16();
//                    break;
//                case 4:
//                    sudoku25x25();
//                    break;
//                case 5:
//                default:
//                    System.out.println("Bye bye");
//                    exit(0);
//            }
//        } while (true);


         // Ex 2
         // generateSudoku();
         // Ex 1
         // sudoku9x9();
//          sudoku16x16();
          sudoku25x25();
    }

    public static void showMenu()
    {
        System.out.println("Menu sudoku");
        System.out.println("############");
        System.out.println("1) Generate random grid");
        System.out.println("2) Solve 9x9 grid");
        System.out.println("3) Solve 16x16 grid");
        System.out.println("4) Solve 25x25 grid");
        System.out.println("5) Exit program");
        System.out.println("Enter choice : ");
    }

    /**
     * Exercice 1 : Solve 9x9 sudoku grid.
     */
    public static void sudoku9x9()
    {
        // Exercice 1
        // *** DECLARE THE MAIN ATTRIBUTES ***
        // Create a new choco model
        Model model = new Model("Sodoku9x9");

        // declare sizes
        int n = 3;
        int n2 = n * n;

        // choose grid
        // 9x9
        int[][] mySudokuGrid = GRID_HARD;
        // int[][] mySudokuGrid = GRID_DIABOLIK;

        // Set boundaries values
        // We want values from 1 to 9 (0 is for blank case, i.e find a value to fit in)
        // My lower value is 1
        int lowerBound = 1;
        // My higher value is 9
        int upperBound = n2;

         solve(model, mySudokuGrid, n, n2, lowerBound, upperBound);
    }

    public static void sudoku16x16()
    {
        // Exercice 1
        // *** DECLARE THE MAIN ATTRIBUTES ***
        // Create a new choco model
        Model model = new Model("Sodoku16x16");

        // declare sizes
        int n = 4;
        int n2 = n * n;

        // choose grid
        // 16x16
        int[][] mySudokuGrid = MONSTER_GRID;

        // Set boundaries values
        // We want values from 1 to 9 (0 is for blank case, i.e find a value to fit in)
        // My lower value is 1
        int lowerBound = 1;
        // My higher value is n²
        int upperBound = n2;

         solve(model, mySudokuGrid, n, n2, lowerBound, upperBound);
    }

    public static void sudoku25x25()
    {
        // Exercice 1
        // *** DECLARE THE MAIN ATTRIBUTES ***
        // Create a new choco model
        Model model = new Model("Sodoku25x25");

        // declare sizes
        int n = 5;
        int n2 = n * n;

        // choose grid
        // 25x25
        int[][] mySudokuGrid = GOD_LIKE;

        // Set boundaries values
        // My lower value is 1
        int lowerBound = 1;
        // My higher value is n²
        int upperBound = n2;

         solve(model, mySudokuGrid, n, n2, lowerBound, upperBound);
    }

    /**
     * Helper method in charge of solving sudoku.
     *
     * @param model current model
     * @param mySudokuGrid current sudoku grid
     * @param n size
     * @param n2 size²
     * @param lowerBound min value
     * @param upperBound highest value
     */
    public static void solve(final Model model, final int[][] mySudokuGrid, final int n, final int n2, final int lowerBound, final int upperBound)
    {
        // Start time
        long startTime = System.nanoTime();

        // Declare tab with n2*n2 dimension (n2 row and n2 column)
        IntVar[][] t = new IntVar[n2][n2];

        // *** INITIALIZE t[i][j] according to grid[i][j] ***
        // Here we need to iterate over t[][]
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                // If my current cell is > 0, then i pick the current value
                if (mySudokuGrid[i][j] > 0)
                {
                    t[i][j] = model.intVar(mySudokuGrid[i][j]);
                }
                // my current cell is equal to 0, need a domain from lower to upper bound
                else
                {
                    t[i][j] = model.intVar("X", lowerBound, upperBound);
                }
            }
        }


        // *** SET CONSTRAINTS ON LINES ***
        IntVar[] lines = new IntVar[n2];
        // *** SET CONSTRAINTS ON COLUMNS ***
        IntVar[] cols = new IntVar[n2];
        // *** SET CONSTRAINTS ON REGION ***
        IntVar[] region = new IntVar[n2];
        for (int i = 0; i < n2; i++)
        {
            // Feed constraints
            for (int j = 0; j < n2; j++)
            {
                lines[j] = t[i][j];
                cols[j] = t[j][i];
            }
            // Say to choco to use differents values for each vars because of sudoku's rules
            model.allDifferent(lines).post();
            model.allDifferent(cols).post();
        }


        // for a region r you can do 2 nested loops on i and j
        for (int r = 0; r < n2; r++)
        {
            // Set up a counter
            int counter = 0;

            // region r: first coord i0 = r / n * n
            int i0 = r / n * n;
            // and j0 = r % n * n
            int j0 = r % n * n;

            // i from i0 to i0+n (excluded)
            for (int i = 0; i < n ; i++)
            {
                // and j from j0 to j0+n (excluded)
                for (int j = 0; j < n; j++)
                {
                    region[counter] = t[i + i0][j + j0];
                    counter++;
                }
            }
            model.allDifferent(region).post();
        }

        // Get solver
        Solver solver = model.getSolver();

        // Tell to solver to propagate constraints in order to reach endpoint
        try
        {
            solver.propagate();
        }
        catch (ContradictionException ce)
        {
            System.out.println("Le problème n'a pas de solution !");
            exit(1);
        }

        // *** SOLVE ***
        // find solution
        Solution solution = solver.findSolution();


        // if solution is null, then we are doomed
        if (solution == null)
        {
            System.out.println("Damn, it's a K.O !");
        }
        // Victory, we go a solution here
        else
        {
            // Call helper method to display sudoku
            displaySudoku(n, t);

            solver.showShortStatisticsOnShutdown();

            // Add end time
            long endTime = System.nanoTime();
            // calcul duration
            long duration = (endTime - startTime);

            // print out
            System.out.println("");
            System.out.println("Solution found in : " + duration / 1000000 + " ms");
        }
    }

    /**
     * Helper method in charge of solving sudoku.
     *
     * @param model current model
     * @param mySudokuGrid current sudoku grid
     * @param n size
     * @param n2 size²
     * @param lowerBound min value
     * @param upperBound highest value
     *
     * @return Array IntVar
     */
    public static IntVar[][] solveBis(final Model model, final int[][] mySudokuGrid, final int n, final int n2, final int lowerBound, final int upperBound)
    {
        // Start time
        long startTime = System.nanoTime();

        // Declare tab with n2*n2 dimension (n2 row and n2 column)
        IntVar[][] t = new IntVar[n2][n2];

        // *** INITIALIZE t[i][j] according to grid[i][j] ***
        // Here we need to iterate over t[][]
        for (int i = 0; i < n2; i++)
        {
            for (int j = 0; j < n2; j++)
            {
                // If my current cell is > 0, then i pick the current value
                if (mySudokuGrid[i][j] > 0)
                {
                    t[i][j] = model.intVar(mySudokuGrid[i][j]);
                }
                // my current cell is equal to 0, need a domain from lower to upper bound
                else
                {
                    t[i][j] = model.intVar("X", lowerBound, upperBound);
                }
            }
        }


        // *** SET CONSTRAINTS ON LINES ***
        IntVar[] lines = new IntVar[n2];
        // *** SET CONSTRAINTS ON COLUMNS ***
        IntVar[] cols = new IntVar[n2];
        // *** SET CONSTRAINTS ON REGION ***
        IntVar[] region = new IntVar[n2];
        for (int i = 0; i < n2; i++)
        {
            // Feed constraints
            for (int j = 0; j < n2; j++)
            {
                lines[j] = t[i][j];
                cols[j] = t[j][i];
            }
            // Say to choco to use differents values for each vars because of sudoku's rules
            model.allDifferent(lines).post();
            model.allDifferent(cols).post();
        }


        // for a region r you can do 2 nested loops on i and j
        for (int r = 0; r < n2; r++)
        {
            // Set up a counter
            int counter = 0;

            // region r: first coord i0 = r / n * n
            int i0 = r / n * n;
            // and j0 = r % n * n
            int j0 = r % n * n;

            // i from i0 to i0+n (excluded)
            for (int i = 0; i < n ; i++)
            {
                // and j from j0 to j0+n (excluded)
                for (int j = 0; j < n; j++)
                {
                    region[counter] = t[i + i0][j + j0];
                    counter++;
                }
            }
            model.allDifferent(region).post();
        }

        // Get solver
        Solver solver = model.getSolver();

        // Tell to solver to propagate constraints in order to reach endpoint
        try
        {
            solver.propagate();
        }
        catch (ContradictionException ce)
        {
            System.out.println("Le problème n'a pas de solution !");
            exit(1);
        }

        // *** SOLVE ***
        // find solution
        Solution solution = solver.findSolution();


        // if solution is null, then we are doomed
        if (solution == null)
        {
            System.out.println("Damn, it's a K.O !");
        }
        // Victory, we go a solution here
        else
        {
            return t;
        }

        return null;
    }


    /**
     *  Generate sudoku grid 9x9.
     *
     *  Steps :
     *  <p>
     *      1) Generate grid of 0.
     *         Then put random number in cell.
     *  </p>
     *
     * <p>
     *     2) Solv the grid and keep solution.
     * </p>
     *
     * <p>
     *     3) iterate trought solution and remove random number.
     * </p>
     *
     */
    public static void generateSudoku()
    {
        System.out.println("Generating a sudoku 9x9");

        // Create a new choco model
        Model model = new Model("generatedSodoku");

        // declare sizes
        int n = 3;
        int n2 = n * n;
        int emptyCell = 0;

        // Create a 9x9 grid
        int[][] myGrid = new int[n2][n2];

        // Create empty grid 9x9
        for (int x = 0; x < n2; x++)
        {
            for (int y = 0; y < n2; y++)
            {
                myGrid[x][y] = emptyCell;
            }
        }

        // set random value
        myGrid[0][0] = randomGenerator(9);

        IntVar[][] mySolvedGrid = solveBis(model, myGrid, n, n2, 1, 9);

        // Remove random cells
        for (int i = 0; i < n2; i++)
        {
            mySolvedGrid[i][randomGenerator(i)] = model.intVar(emptyCell);

            for (int j = 0; j < n2; j++)
            {
                mySolvedGrid[j][randomGenerator(i)] = model.intVar(emptyCell);
            }
        }

        displaySudoku(n, mySolvedGrid);
    }

    /**
     * Helper method for generating random value.
     *
     * @param num value
     * @return random value
     */
    public static int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }


    /**
     * Helper method in charge of drawing.
     *
     * @param n number
     * @param t given IntVar array
     */
    private static void displaySudoku(final int n, final IntVar[][] t)
    {
        // *** Set up variables ***
        // n2 means n²
        int n2 = n * n;
        int width = String.valueOf(n2).length();

        // outprint title
        System.out.println("***************************************");
        System.out.println("****** SOLUTION FOR SUDOKU " + n2 + "x" + n2 + " ********");
        System.out.println("***************************************");

        System.out.println("");

        // StringBuilder
        StringBuilder s = new StringBuilder();

        // Iterate trought 0 to N
        for (int i = 0; i < n; i++)
        {
            // draw first corner
            s.append("¤");
            // draw line
            for (int j = 0; j < n * 2 * width + 1; j++)
            {
                s.append("¤");
            }
        }
        // add end corner
        s.append("¤");

        // Iterate trought
        for (int i = 0; i < n2; i++)
        {
            if (i % n == 0)
            {
                System.out.println(" " + s);
            }
            for (int j = 0; j < n2; j++)
            {
                if (j % n == 0)
                {
                    System.out.print(" |");
                }
                System.out.printf(" %" + width + "d", t[i][j].getValue());
            }
            System.out.println(" |");
        }
        System.out.println(" " + s);
    }

}
