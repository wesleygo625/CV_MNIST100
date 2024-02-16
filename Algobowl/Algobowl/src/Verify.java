import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Vector;

public class Verify {
    public static void main(String[] args) throws FileNotFoundException {
        int num = 631;
        String iFile = "input.txt";
        String rFile = "ALGOBOWL/verification_outputs/verification_outputs/output_from_" + num + "_to_571.txt";
        int[] targets = readInput(iFile);
        int[][] eqs = readResult(rFile);
        String message = verifyResult(targets, eqs);
        System.out.println(message);
    }

    //Read in and store input file
    public static int[] readInput (String iFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(iFile));
        int numTargets = Integer.parseInt(scanner.nextLine());
        int[] targets = new int[numTargets];
        for(int i = 0; i < numTargets; i++) {
            targets[i] = scanner.nextInt();
        }
        return targets;
    }

    //Read in and store results file
    public static int[][] readResult (String rFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(rFile));
        int numEqs = Integer.parseInt(scanner.nextLine());
        int[][] eqs = new int[numEqs][2];
        for(int i = 0; i < numEqs; i++) {
            eqs[i][0] = scanner.nextInt();
            eqs[i][1] = scanner.nextInt();
        }
        if (scanner.hasNextLine()) { //If they have more lines than they say, set eqs to null
            if (scanner.nextLine() != "") {
                eqs = null;
            }
        }
        return eqs;
    }

    //Check for any errors
    public static String verifyResult (int[] targets, int[][] eqs) {
        Vector<Integer> usable = new Vector<>();
        if (eqs == null) {
            return "Wrong number of calculations, incorrect.";
        }
        usable.add(1);
        for (int i = 0; i < eqs.length; i++) { //Make sure all numbers used have been calculated
            if (!usable.contains(eqs[i][0])) {
                return "Program has not used " + eqs[i][0] + " before, incorrect.";
            } else if (!usable.contains(eqs[i][1])) {
                return "Program has not used " + eqs[i][1] + " before, incorrect.";
            } else {
                usable.add(eqs[i][0] + eqs[i][1]);
            }
        }
        for (int i = 0; i < targets.length; i++) { //Make sure all targets are calculated
            if (!usable.contains(targets[i])) {
                return "Program did not calculate " + targets[i] + ", incorrect.";
            }
        }
        return "Correct!";
    }
}