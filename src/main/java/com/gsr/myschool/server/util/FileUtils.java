package com.gsr.myschool.server.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


/**
 * @author javapractices.com
 * @author Bouayad Mehdi Class utilitaire permettant de lister de maniere
 *         recursive les fichiers d'un repertoire
 */
public final class FileUtils {

    /**
     * Recursively walk a directory tree and return a List of all Files found;
     * the List is sorted using File.compareTo.
     *
     * @param aStartingDir is a valid directory, which can be read.
     */
    static public List getFileListing(File aStartingDir, String[] filter)
            throws FileNotFoundException {
        validateDirectory(aStartingDir);
        List result = new ArrayList();

        File[] filesAndDirs = aStartingDir.listFiles();
        List filesDirs = Arrays.asList(filesAndDirs);
        Iterator filesIter = filesDirs.iterator();
        File file = null;
        while (filesIter.hasNext()) {
            file = (File) filesIter.next();

            for (int i = 0; i < filter.length; i++) {
                if (file.getName().endsWith(filter[i])) {
                    result.add(file); // always add, even if directory
                    break;
                }
            }

            if (!file.isFile()) {
                // must be a directory
                // recursive call!
                List deeperList = getFileListing(file, filter);
                result.addAll(deeperList);
            }

        }
        Collections.sort(result);
        return result;
    }

    /**
     * Directory is valid if it exists, does not represent a file, and can be
     * read.
     */
    static private void validateDirectory(File aDirectory)
            throws FileNotFoundException {
        if (aDirectory == null) {
            throw new IllegalArgumentException("Directory should not be null.");
        }
        if (!aDirectory.exists()) {
            throw new FileNotFoundException("Directory does not exist: "
                    + aDirectory);
        }
        if (!aDirectory.isDirectory()) {
            throw new IllegalArgumentException("Is not a directory: "
                    + aDirectory);
        }
        if (!aDirectory.canRead()) {
            throw new IllegalArgumentException("Directory cannot be read: "
                    + aDirectory);
        }
    }

    /**
     * Puts all the files of the input folder having the specified extension on
     * an array
     */
    public static ArrayList fileListinArrayList(String inputFolder,
                                                String[] filter) {
        ArrayList theListing = new ArrayList();
        try {

            File tempDir = new File(inputFolder);
            List files = FileUtils.getFileListing(tempDir, filter);

            // print out all file names, and display the order of File.compareTo
            Iterator filesIter = files.iterator();
            while (filesIter.hasNext()) {
                String temp = filesIter.next().toString();
                // utils.debug(temp);
                theListing.add(temp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            // throw ex;
        }

        return theListing;

    }

    /**
     * Puts all the ClassNames of the input folder having the specified
     * extension on an array
     */
    public static ArrayList DAOClassNameinArrayList(String inputFolder,
                                                    String[] filter) {
        ArrayList theListing = new ArrayList();
        try {

            File tempDir = new File(inputFolder);
            List files = FileUtils.getFileListing(tempDir, filter);

            // print out all file names, and display the order of File.compareTo
            Iterator filesIter = files.iterator();
            while (filesIter.hasNext()) {
                String temp = filesIter.next().toString();
                temp = temp.substring(temp.lastIndexOf("\\") + 1, temp
                        .lastIndexOf("."));
                // utils.debug(temp);
                theListing.add(temp);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            // throw ex;
        }
        return theListing;

    }


    public FileUtils() {

    }

    public static void main(String[] aArguments) {
    }

    // Deletes all files and subdirectories under dir.
    // Returns true if all deletions were successful.
    // If a deletion fails, the method stops attempting to delete and returns
    // false.
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }

    public static boolean deleteRecursiveDir(File dir,
                                             Collection<String> ignored) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                if (!ignored.contains(children[i]))
                    deleteRecursiveDir(new File(dir, children[i]), ignored);
            }
            return true;
        } else
            return dir.delete();
    }


    public static void abrasePath(String path, int times) {

        for (int i = 0; i < times; i++)
            abrasePath(path);

    }

    public static void abraseAllPaths(String path, int times) {

        String apps[] = new String[]{"/preinscription"};
        for (String s_path : apps) {
            for (int i = 0; i < times; i++)
                abrasePath(s_path);
        }

    }


    public static void abrasePath(String path) {

        List<String> list = FileUtils.fileListinArrayList(path,
                new String[]{""});

        try {
            System.err.println("files are:");
            for (String str : list) {
                System.err.println(str);
                File test = new File(str);
                test.delete();
            }


        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void vanishPath(String path) {

        List<String> list = FileUtils.fileListinArrayList(path,
                new String[]{""});

        try {
            System.err.println("files are:");
            for (String str : list) {
                System.err.println(str);
                File test = new File(str);
                test.delete();
                test.createNewFile();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        } catch (RuntimeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
