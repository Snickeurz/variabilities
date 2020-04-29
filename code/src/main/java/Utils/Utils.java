package Utils;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.util.iterators.DisposableRangeIterator;
public class Utils
{
    /**
     * Returns a {@code String} representation of the value of a variable.
     * @param var The variable
     *
     * @return A {@literal String} representing the variable value
     */
    public static String getVarDomain(IntVar var)
    {
        return var.getName() + "=" + getDomain(var);
    }

    public static String getDomain(IntVar var) { // else use toString of vars
        if (var.isInstantiated()) {
            return String.valueOf(var.getValue());
        }

        StringBuilder res = new StringBuilder();
        DisposableRangeIterator rit = var.getRangeIterator(true);
        String c = "";
        while (rit.hasNext()) {
            int from = rit.min();
            int to = rit.max();
            res.append(c).append(from);
//            uncomment the following if to display 2..3 as 2:3
//            if (from + 1 == to) {
//                res.append(":").append(to);
//            } else
            if (from != to) {
                res.append("..").append(to);
            }
            c = ":";
            rit.next();
        }
        rit.dispose();
        return res.toString();
    }

    public static void displayDomains(IntVar... vars) {
        String c = "";
        for (IntVar var : vars) {
            System.out.print(c + getVarDomain(var));
            c = ", ";
        }
        System.out.println();
    }

    public static void reduceDomains(Model model) {
        try {
            model.getSolver().propagate();
        } catch (ContradictionException ex) {
            System.err.println("ERROR: " + ex);
            System.exit(1);
        }
    }
}