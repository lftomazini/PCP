
import java.util.ArrayList;
import java.util.List;


/*
 * This program was created by Luis Felipe Tomazini
 * lffct001@bucknell.edu
 * Should not be reproduced partially or completely
 */
/**
 * Auxiliary class that contains pairs of strings
 *
 * @author lftomazini
 */
class Pair {

    public String w1;
    public String w2;

    public Pair(String w1, String w2) {
        this.w1 = w1;
        this.w2 = w2;
    }
}

/**
 * Class to solve the Post Correspondence Problem. Solution created based on an
 * algorithm from Prof Benoit Razet
 *
 * @see
 * http://www.bucknell.edu/academics/academic-departments/computer-science/faculty-and-staff/benoit-razet.html
 *
 * @author lftomazini
 */
public class PCP {

    /**
     * the second tape of the turing machine
     */
    public static String t2 = new String();

    /**
     * the third tape of the turing machine
     */
    public static String t3 = new String();

    /**
     * the current line of the array that is being read
     */
    public static int current = 0;

    /**
     * the maximum length of the solution for the PCP
     */
    public static final int MAX_POWER = 10;

    /**
     * the numerical base to be used in the methods, i.e. the number of pairs in
     * the input list
     */
    public static int BASE;

    public static ArrayList<String> PATHS = new ArrayList<>();

    /**
     * Picks a sequence of numbers from the array
     *
     * @param current indicated the current line of the array that is being read
     * @return the array with the sequence
     */
    public static int[] pick(int current) {
        String line = PATHS.get(current);
        int[] intArray = new int[line.length()];
        intArray = stringToInt(line);

        return intArray;
    }

    /**
     * Converts a String to an array of int
     *
     * @param str the String to be converted
     * @return
     */
    public static int[] stringToInt(String str) {
        int length = str.length();
        int[] intArray = new int[length];
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                System.out.println("Contains an invalid digit");
                break;
            }
            intArray[i] = Integer.parseInt(String.valueOf(str.charAt(i)));
        }
        return intArray;
    }

    /**
     * Adds the sequences of numbers to the PATHS array
     *
     */
    public static void addToArray() {
        for (int i = 1; i < Math.pow(BASE, MAX_POWER); i++) {
            PATHS.add(generate(i));
        }
    }

    /**
     * Generates an array sequence of the numbers in the given base
     *
     * @param aux
     * @return
     */
    public static String generate(int aux) {
        if (aux < BASE + 1) {
            return Character.toString((char) (aux + 48));
        } else {
            if (aux % BASE == 0) {
                return generate((aux / BASE) - 1) + generate(
                        ((aux - 1) % BASE + 1));
            } else {
                return generate(aux / BASE) + generate(aux % BASE);
            }
        }
    }

    /**
     * Semidecides if the given Post Correspondence Problem has a solution
     *
     * @param t1 the first tape of the turing machine containing pairs of string
     * to be used in the PCP
     * @return
     */
    public static boolean solvePCP(List<Pair> t1) {
        t2 = "";
        t3 = "";

        int index[] = pick(current);
        for (int i = 0; i < index.length; i++) {
            t2 = t2.concat(t1.get(index[i] - 1).w1);
            t3 = t3.concat(t1.get(index[i] - 1).w2);
        }

        if (t2.equals(t3)) {
            return true;
        } else {
            current++;
            solvePCP(t1);
            return true;
        }
    }

    /**
     * Test cases
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Pair> myList = new ArrayList<>();

        Pair p1 = new Pair("a", "baa");
        Pair p2 = new Pair("ab", "aa");
        Pair p3 = new Pair("bba", "bb");
        Pair[] pairs_1 = {p1, p2, p3};
        BASE = pairs_1.length;
        for (Pair pairs : pairs_1) {
            myList.add(pairs);
        }
//        Pair p4 = new Pair("baa", "b");
//        Pair p5 = new Pair("a", "baa");
//        Pair p6 = new Pair("b", "aa");
//        Pair[] pairs_2 = {p4, p5, p6};
//        BASE = pairs_2.length;
//        for (Pair pairs : pairs_2) {
//            myList.add(pairs);
//        }
//        Pair p7 = new Pair("ab", "a");
//        Pair p8 = new Pair("bbaaba", "a");
//        Pair p9 = new Pair("b", "bbbb");
//        Pair p10 = new Pair("bb", "ab");
//        Pair[] pairs_3 = {p7, p8, p9, p10};
//        BASE = pairs_3.length;
//        for (Pair pairs : pairs_3) {
//            myList.add(pairs);
//        }

        long startTime = System.nanoTime();

        addToArray();
        boolean check = solvePCP(myList);
        System.out.println(check);

        long elapsedTime = System.nanoTime() - startTime;
        System.out.printf("Elapsed time to check was: %d ns\n", elapsedTime);

    }
}
