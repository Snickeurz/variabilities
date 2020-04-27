package exercicesCombinatoire.magicsquare;

/**
 * Magic Square by Bactracking
 * Simple version with cells tried by line and values in ascending order
 * Can solve n = 3 and n=4
 *
 * @author Daniel Diaz {@code <daniel.diaz@univ-paris1.fr>}
 */
public abstract class MagicSquareAbstract {

    protected final int n;
    protected final int n2;
    protected final int sum;
    protected final int t[][];

    public MagicSquareAbstract(int n) {
        this.n = n;
        n2 = n * n;
        sum = n * (n2 + 1) / 2;
        System.out.println("n: " + n + " sum: " + sum);
        t = new int[n][n];
    }

    @Override
    public String toString() {
        String s = "";
        int width = String.valueOf(n2).length();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s = s + " " + String.format(" %" + width + "d", t[i][j]);
            }
            s += "\n";
        }
        return s;

    }

    public void printMagicSquare() {
        System.out.println(this);
        checkSolution();
    }

    public abstract boolean solveMagicSquare();

    private void checkSolution() {
        int s;
        for (int i = 0; i < n; i++) {
            if ((s = sumLine(i)) != sum) {
                System.err.println("ERROR: line #" + i + " = " + s + " != " + sum);
            }
        }
        for (int j = 0; j < n; j++) {
            if ((s = sumColumn(j)) != sum) {
                System.err.println("ERROR: column #" + j + " = " + s + " != " + sum);
            }
        }
        if ((s = sumDiagonal1()) != sum) {
            System.err.println("ERROR: diagonal1 = " + s + " != " + sum);
        }
        if ((s = sumDiagonal2()) != sum) {
            System.err.println("ERROR: diagonal2 = " + s + " != " + sum);
        }
    }

    protected int sumLine(int i) {
        int s = 0;
        for (int j = 0; j < n; j++) {
            s += t[i][j];
        }
        return s;
    }

    protected int sumColumn(int j) {
        int s = 0;
        for (int i = 0; i < n; i++) {
            s += t[i][j];
        }
        return s;
    }

    protected int sumDiagonal1() {
        int s = 0;
        for (int i = 0; i < n; i++) {
            s += t[i][i];
        }
        return s;
    }

    protected int sumDiagonal2() {
        int s = 0;
        for (int i = 0; i < n; i++) {
            s += t[i][n - i - 1];
        }
        return s;
    }
}
