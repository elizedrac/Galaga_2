package org.cis1200;

import java.io.*;
import java.nio.file.Paths;
import java.util.List;

/**
 * This class organizes some static methods for working with File IO.
 */
public class FileUtilities {

    /**
     * Takes in a filename and creates a BufferedReader.
     * See Java's documentation for BufferedReader to learn how to construct one
     * given a path to a file.
     *
     * @return a BufferedReader of the provided file contents
     * @throws IllegalArgumentException if filePath is null or if the file
     *                                  doesn't exist
     */
    public static BufferedReader fileToReader(File file) {
        try {
            return new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Given a {@code List} of {@code String}s, writes them to a file (one
     * {@code String} per
     * line in the file). This method uses {@code BufferedWriter}, the flip side to
     * {@code BufferedReader}. It may be useful to look at the JavaDocs for
     * {@code FileWriter}.
     * <p>
     * You may assume none of the arguments or strings passed in will be null.
     * <p>
     * If the process of opening the file or writing the data triggers an
     * {@code IOException}, you
     * should catch it and stop writing. (You can also print an error message to the
     * terminal, but we will not test that behavior.)
     *
     * @param append         a boolean indicating whether the new tweets
     *                       should be appended to the current file or should
     *                       overwrite its previous contents
     */
    public static void writeStringsToFile(
            String s, File file,
            boolean append
    ) {
        BufferedWriter bw;

        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(s);
            bw.newLine();
//            for (String s : stringsToWrite) {
//                bw.write(s);
//                bw.newLine();
//            }
            bw.close();
        } catch (IOException e) {
        }
    }

    //creates a new file
    public static File createFile(String filename) {
        try {
            File file = new File(filename);
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}