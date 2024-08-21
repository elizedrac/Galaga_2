package org.cis1200;

import java.io.*;

//code written using UPenn CIS 1200 HW project as template
//static FileIO methods
public class FileUtilities {

    //creates file reader
    public static BufferedReader fileToReader(File file) {
        try {
            return new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        } catch (NullPointerException e) {
            throw new IllegalArgumentException();
        }
    }

    //adds (or replaces) string to file
    public static void writeStringToFile(
            String s, File file,
            boolean append
    ) {
        BufferedWriter bw;

        try {
            bw = new BufferedWriter(new FileWriter(file, append));
            bw.write(s);
            bw.newLine();
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