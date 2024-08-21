package org.cis1200;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.io.BufferedReader;
import java.util.NoSuchElementException;

//code written using UPenn CIS 1200 HW project as template
public class LineIterator implements Iterator<String> {
    private BufferedReader reader;
    private String currLine;

    //constructs a line iterator from a provided file
    public LineIterator(File file) {
        readerLineIterator(FileUtilities.fileToReader(file));
    }

    //constructs line iterator from a buffered reader
    public void readerLineIterator(BufferedReader reader) {
        if (reader == null) {
            throw new IllegalArgumentException();
        }
        this.reader = reader;
        try {
            this.currLine = reader.readLine();
        } catch (IOException e) {
            currLine = null;
        }
    }

    //returns true if the file has lines left to read
    @Override
    public boolean hasNext() {
        try {
            boolean next = (currLine != null);
            if (!next) {
                this.reader.close();
            }
            return (next);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    //returns the next line of the file
    @Override
    public String next() {
        if (hasNext()) {
            String result = currLine;
            try {
                currLine = this.reader.readLine();
            } catch (IOException e) {
                currLine = null;
            }
            return result;
        } else {
            throw new NoSuchElementException();
        }
    }

}