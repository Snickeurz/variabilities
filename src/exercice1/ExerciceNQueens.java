package exercice1;

import exercicesCombinatoire.Permutations;

import java.util.List;

/**
 * Queens naive  Generate and Test - permutation
 *
 * Ennoncé :
 * Il faut pouvoir placer n reines sur n colonnes et éviter quelles soit en position d'attaque.
 * Exemple :
 * Qi  : ligne
 * i : column
 *
 * Qi [3,1,4,0,2]
 * i   0,1,2,3,4
 *
 * Si Qi == i alors c'est pas OK car attaque en diagonal
 * donc typiquement 4/2 et 2/4 pas OK car en diagonal
 */
public class ExerciceNQueens
{
    public static void main (String[] args)
    {
        System.out.println("Exercice des N Queens");

        // ///////////////////////////
        // ////////N QUEENS///////////
        // ///////////////////////////
        int nbSols = 0;
        // Number of permutations
        int n = 8;
        // Call class
        Permutations perm = new Permutations(n);
        // Iterate trought the List
        for (List<Integer> p : perm)
        {
            // Check if current permutation is a solution
            if (isSolution(p))
            {
                // My current permutation is a solution so i print it
                System.out.println("\n" + p);
                // Increase number of solutions
                nbSols ++;
                // to stop after first solution
//                break;
            }
        }
        System.out.println("\nNombre de sols : " + nbSols);
    }

    public static boolean isSolution(List<Integer> p)
    {
        // First queen @Col 0 & @Row 0
        for (int xCol = 0; xCol < p.size(); xCol++)
        {
            int xRow = p.get(xCol);
            // Start with +1 (look at right)
            for (int yCol = xCol + 1; yCol < p.size(); yCol++)
            {
                int yRow = p.get(yCol);
                // Check distance for diagonal
                if (yCol - xCol == Math.abs(xRow - yRow))
                {
                    // Return false bc it's not a solution
                    return false;
                }
            }
        }
        // This is a solution so return true
        return true;
    }
}
