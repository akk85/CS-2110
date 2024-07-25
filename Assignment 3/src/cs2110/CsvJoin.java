package cs2110;


import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class CsvJoin {

    /**
     * Load a table from a Simplified CSV file and return a row-major list-of-lists representation.
     * The CSV file is assumed to be in the platform's default encoding. Throws an IOException if
     * there is a problem reading the file.
     */
    public static Seq<Seq<String>> csvToList(String file) throws IOException {
        //Create a linked Seq to store the csv data
        Seq<Seq<String>> table = new LinkedSeq<>();

        //Open the csv file for reading
        try (FileReader newFileReader = new FileReader(file);
                //Create a scanner to read the file line by line
                Scanner scanner = new Scanner(newFileReader)) {
            while (scanner.hasNextLine()) {
                //Read a line from the csv file
                String line = scanner.nextLine();
                //Split the line into tokens using a comma
                String[] tokens = line.split(",", -1);
                // Create a new linkedSeq to represent the current row
                Seq<String> row = new LinkedSeq<String>();
                for (String token : tokens) {
                    // Append each token to the current row
                    row.append(token);
                }
                //Append the row to the output table
                table.append(row);
            }
        }
        //Return the linkedSeq containing the csv data.
        return table;
    }

    /**
     * Checks and returns whether the given table is valid based on the following conditions: 1. No
     * element in the table should be an empty string. 2. All rows should have the same number of
     * columns, ensuring that the table is rectangular.
     */
    private static boolean isValidTable(Seq<Seq<String>> table) {
        // Iterate through each row in the table
        for (Seq<String> row : table) {
            // Iterate through each element in the row
            for (String element : row) {
                // Check if the element is an empty string
                if (element.isEmpty()) {
                    return false; // Return false if an empty string is found
                }
            }
        }

        // Check if all rows have the same number of columns (rectangular table)
        int numColumns = table.get(0).size();
        for (Seq<String> row : table) {
            if (row.size() != numColumns) {
                return false; // Return false if the number of columns is inconsistent
            }
        }

        return true; // If no issues are found, the table is valid
    }

    /**
     * Return the left outer join of tables `left` and `right`, joined on their first column. Result
     * will represent a rectangular table, with empty strings filling in any columns from `right`
     * when there is no match. Requires that `left` and `right` represent rectangular tables with at
     * least 1 column.
     */
    /**
     * Return the left outer join of tables `left` and `right`, joined on their first column. Result
     * will represent a rectangular table, with empty strings filling in any columns from `right`
     * when there is no match. Requires that `left` and `right` represent rectangular tables with at
     * least 1 column.
     */
    public static Seq<Seq<String>> join(Seq<Seq<String>> left, Seq<Seq<String>> right) {
        // Check for validity of tables
        assert isValidTable(left);
        assert isValidTable(right);

        Seq<Seq<String>> resultTable = new LinkedSeq<>();

        for (Seq<String> leftRow : left) {
            String key = leftRow.get(0);  // The state name, or the first column value
            // Try to find a matching row in the right table
            Seq<String> matchingRow = null;
            for (Seq<String> rightRow : right) {
                if (rightRow.get(0).equals(key)) {
                    matchingRow = rightRow;
                    break;
                }
            }

            LinkedSeq<String> newRow = new LinkedSeq<>();
            for (String leftVal : leftRow) {
                newRow.append(leftVal);
            }

            if (matchingRow != null) {
                boolean isFirst = true;
                for (String rightVal : matchingRow) {
                    if (!isFirst) { // skip the first column from the right table
                        newRow.append(rightVal);
                    }
                    isFirst = false;
                }
            } else {
                for (int i = 1; i < right.get(0).size();
                        i++) {  // add empty strings for missing columns
                    newRow.append("");
                }
            }

            resultTable.append(newRow);
        }

        return resultTable;
    }

    /**
     * Entry point for the CsvJoin program. The program performs a join operation on two CSV files.
     * It expects exactly two program arguments: paths to the "left" and "right" CSV files. The
     * program checks the validity of the input tables and then performs a join operation. The
     * resulting joined table is printed to the standard output in the Simplified CSV format. Exit
     * codes: 0: Program executed successfully. 1: An error occurred, e.g., incorrect number of
     * arguments, file reading issues, or non-rectangular tables. Error messages are printed to the
     * standard error output.
     */

    public static void main(String[] args) {
        //Check if the correct number of command line argument is provided(two arguments expected)
        if (args.length != 2) {
            System.err.println("Usage: cs2110.CsvJoin <left_table.csv> <right_table.csv>");
            System.exit(1); //Exit the program with an error code (1)
        }
        // Declare variable to store left and right tables as seq of strings
        Seq<Seq<String>> left, right;
        try {
            //Attempt to read cvs data from first file and store it in "left"
            left = CsvJoin.csvToList(args[0]);
            //Attempt to read cvs data from second file and store it in "right"
            right = CsvJoin.csvToList(args[1]);
        } catch (IOException e) {
            //handle an IO Exception which may occur if there is an issue reading the file
            System.err.println("Error: Could not read input tables");
            System.err.println(e.getMessage());
            System.exit(1); //Exit the program with an error code (1)
            return; //Return to terminate the program if an error occurs
        }
        //Check if the input tables left amd right are valid rectangular tables
        if (!isValidTable(left) || !isValidTable(right)) {
            System.err.println("Error: Input tables are not rectangular.");
            System.exit(1); //Exit the program with an error code (1)
            return; //Return to terminate the program if an error occurs
        }
        //join left and right table and store the result.
        Seq<Seq<String>> joinedTable = CsvJoin.join(left, right);

        outputCsvFormat((joinedTable));
    }

    /**
     * Helper function to output the joined table in a simplified csv format Outputs the provided
     * table in a Simplified CSV format. The method takes a table (list of lists) and prints each
     * row to the standard output. Each element in a row is separated by a comma, and each row is
     * printed on a new line.
     */
    private static void outputCsvFormat(Seq<Seq<String>> joinedTable) {
        //loop through the rows in the joinedTable and print them out as cvs rows
        for (Seq<String> row : joinedTable) {
            //Join the string in the row with commas and print the result in cvs row
            System.out.println(String.join(",", row));
        }
    }

}