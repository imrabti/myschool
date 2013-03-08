package com.gsr.myschool.server.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class FileUtils {
    static public List getFileListing(File aStartingDir, String[] filter) throws FileNotFoundException {
        validateDirectory(aStartingDir);
        List result = new ArrayList();

        File[] filesAndDirs = aStartingDir.listFiles();
        List filesDirs = Arrays.asList(filesAndDirs);
        Iterator filesIter = filesDirs.iterator();
        File file;
        while (filesIter.hasNext()) {
            file = (File) filesIter.next();
            for (int i = 0; i < filter.length; i++) {
                if (file.getName().endsWith(filter[i])) {
                    result.add(file);
                    break;
                }
            }

            if (!file.isFile()) {
                List deeperList = getFileListing(file, filter);
                result.addAll(deeperList);
            }

        }
        Collections.sort(result);

        return result;
    }

    static private void validateDirectory(File aDirectory)
            throws FileNotFoundException {
        if (aDirectory == null) {
            throw new IllegalArgumentException("Directory should not be null.");
        }
        if (!aDirectory.exists()) {
            throw new FileNotFoundException("Directory does not exist: " + aDirectory);
        }
        if (!aDirectory.isDirectory()) {
            throw new IllegalArgumentException("Is not a directory: " + aDirectory);
        }
        if (!aDirectory.canRead()) {
            throw new IllegalArgumentException("Directory cannot be read: " + aDirectory);
        }
    }

    public static ArrayList fileListinArrayList(String inputFolder, String[] filter) {
        ArrayList theListing = new ArrayList();
        try {
            File tempDir = new File(inputFolder);
            List files = FileUtils.getFileListing(tempDir, filter);

            Iterator filesIter = files.iterator();
            while (filesIter.hasNext()) {
                String temp = filesIter.next().toString();
                theListing.add(temp);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return theListing;
    }

    public static void abraseAllPaths(String path, int times) {
        for (int i = 0; i < times; i++) {
            abrasePath(path);
        }
    }

    public static void abrasePath(String path) {
        List<String> list = FileUtils.fileListinArrayList(path, new String[]{""});
        try {
            System.err.println("files are:");
            for (String str : list) {
                System.err.println(str);
                File test = new File(str);
                test.delete();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
}
