package exercicesCombinatoire.magicsquare;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Magic Square by Bactracking version with cells tried as follows: diagonals,
 * concentric squares Can solve up to n = 7 (and some n = 8 in minutes)
 *
 * @author Daniel Diaz {@code <daniel.diaz@univ-paris1.fr>}
 */
public class MagicSquareBT3 extends MagicSquareAbstract {

    public static void main(String... args) {
        MagicSquareAbstract m = new MagicSquareBT3(6);
        if (m.solveMagicSquare()) {
            m.printMagicSquare();
        }
    }

    private final List<Integer> cellNo; // 0..n2-1 = i*n+j
    private final List<Integer> val;
    private final int sumL[];
    private final int sumC[];
    private int sumD1;
    private int sumD2;
    private final int nbL[];
    private final int nbC[];
    private int nbD1;
    private int nbD2;

    public MagicSquareBT3(int n) {
        super(n);
        cellNo = new ArrayList<>(n2);
        val = new LinkedList<>();
        sumL = new int[n]; // initialized to 0
        sumC = new int[n]; // initialized to 0
        sumD1 = sumD2 = 0;
        nbL = new int[n]; // initialized to 0
        nbC = new int[n]; // initialized to 0
        nbD1 = nbD2 = 0;
    }

    @Override
    public boolean solveMagicSquare() {
        //System.out.println("D1");
        for (int i = 0; i < n; i++) {  // diagonal 1 first
            addToCell(i, i);
        }
        //System.out.println("D2");

        for (int i = 0; i < n; i++) { // diagonal 2
            addToCell(i, n - i - 1);
        }

        for (int sq = 0; sq < n / 2; sq++) { // concentric squares (top, left, bottom, right)
            //System.out.println("SQ " + sq);
            for (int j = sq; j < n - sq; j++) {
                addToCell(sq, j);
            }
            for (int i = sq; i < n - sq; i++) {
                addToCell(i, sq);
            }
            for (int j = sq; j < n - sq; j++) {
                addToCell(n - sq - 1, j);
            }
            for (int i = sq; i < n - sq; i++) {
                addToCell(i, n - sq - 1);
            }
        }
        //System.out.println(cellNo);

        for (int i = 1; i <= n2; i++) {
            val.add(i);
        }
        Collections.shuffle(val);
        return solveMagicSquare(0);
    }

    private void addToCell(int i, int j) {
        int no = i * n + j;
        //System.out.println("    adding cell " + i + " " + j);
        if (!cellNo.contains(no)) {
            cellNo.add(no);
        }
    }

    private boolean solveMagicSquare(int cellInd) {
        if (cellInd >= n2) { // could be val.isEmpty()
            return true;
        }

        int no = cellNo.get(cellInd);
        int i = no / n;
        int j = no % n;
//        System.out.println("Solve cid:" + cellInd + "  no:" + no + "  i/j " + i + "  nbL:" + nbL[i]);
//        System.out.println(this);

        for (int k = 0; k < val.size(); k++) {
            int v = val.get(k);
            if (possibleValue(i, j, v)) {
                // try this value (record changes)
                t[i][j] = v;
                val.remove(k);
                sumL[i] += v;
                sumC[j] += v;
                nbL[i]++;
                nbC[j]++;
                if (i == j) {
                    sumD1 += v;
                    nbD1++;
                }
                if (j == n - i - 1) {
                    sumD2 += v;
                    nbD2++;
                }
                if (solveMagicSquare(cellInd + 1)) {
                    return true;
                }
                // backtrack (undo changes)
                t[i][j] = 0; // not really necessary
                val.add(k, v); // NB: it is important to place back the value at the same index
                sumL[i] -= v;
                sumC[j] -= v;
                nbL[i]--;
                nbC[j]--;
                if (i == j) {
                    sumD1 -= v;
                    nbD1--;
                }
                if (j == n - i - 1) {
                    sumD2 -= v;
                    nbD2--;
                }
            }
        }
        return false;
    }

    private boolean possibleValue(int i, int j, int v) {
        int sl = sumL[i] + v;
        if (sl > sum || (nbL[i] + 1 == n && sl != sum)) { // check line
            return false;
        }

        int sc = sumC[j] + v;
        if (sc > sum || (nbC[j] + 1 == n && sc != sum)) { // check column
            return false;
        }

        int sd1 = (i == j) ? sumD1 + v : 0;
        if (sd1 > 0 && (sd1 > sum || (nbD1 + 1 == n && sd1 != sum))) { // check diagonal 1
            return false;
        }

        int sd2 = (j == n - i - 1) ? sumD2 + v : 0;
        if (sd2 > 0 && (sd2 > sum || (nbD2 + 1 == n && sd2 != sum))) {// check diagonal 2
            return false;
        }
        return true;
    }
}
