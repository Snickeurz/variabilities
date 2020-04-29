package choco;

import exercicesCombinatoire.magicsquare.MagicSquareAbstract;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

public class MagicSquare extends MagicSquareAbstract
{
    public MagicSquare(final int n)
    {
        super(n);
    }

    public static void main(String[] args)
    {
        MagicSquare ms = new MagicSquare(6);

        // Call implementation
        if (ms.solveMagicSquare())
        {
            // Solution found so print them
            ms.printMagicSquare();
        }
    }

    @Override
    public boolean solveMagicSquare()
    {
        // Create new model
        Model model = new Model("Magic Square with size " + super.n);

        // Matrix the shit out it
        IntVar[][] values = model.intVarMatrix("v", super.n, super.n, 1, super.n2);
        // Arrays
        IntVar[] all = new IntVar[super.n2];
        IntVar[] diagonales1 = new IntVar[super.n];
        IntVar[] diagonales2 = new IntVar[super.n];

        // For each lines
        for (int i = 0; i < super.n; i++)
        {
            // Create array for lines
            IntVar[] lines = new IntVar[super.n];
            // Create array for column
            IntVar[] column = new IntVar[super.n];

            // for each column
            for (int j = 0; j < super.n ; j++)
            {
                all[i * super.n + j] = values[i][j];
                lines[j] = values[i][j];
                column[j] = values[j][i];
            }
            // Sum the shit to check
            model.sum(lines, "=", sum).post();
            model.sum(column, "=", sum).post();
            diagonales1[i] = values[i][i];
            diagonales2[i] = values[i] [super.n - i - 1];
        }

        model.allDifferent(all).post();
        model.sum(diagonales1, "=", sum).post();
        model.sum(diagonales2, "=", sum).post();

        Solver solver = model.getSolver();

        Solution solution = solver.findSolution();
        if (solution == null)
        {
            return false;
        }

        for (int i = 0; i < super.n; i++)
        {
            for (int j =0; j < super.n; j++)
            {
                super.t[i][j] = values[i][j].getValue();
            }
        }
        return true;
    }
}
