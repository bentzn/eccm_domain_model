package eu.europa.ec.digit.contentmanagement.domain.api.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Find classes in the classpath (reads JARs and classpath folders).
 * 
 * @author P&aring;l Brattberg, brattberg@gmail.com | adjusted by bentzn
 */
public class ClasspathScanner {

    static boolean DEBUG = false;
    private static String[] acExcludePackages = new String[0];

    public static List<Class<?>> getAllKnownClasses(String... excludePackages) {
        if (excludePackages != null)
            acExcludePackages = excludePackages;
        List<Class<?>> classFiles = new ArrayList<>();
        List<File> classLocations = getClassLocationsForCurrentClasspath();
        for (File file : classLocations) {
            classFiles.addAll(getClassesFromPath(file));
        }
        return classFiles;
    }



    private static Collection<? extends Class<?>> getClassesFromPath(File file) {
        if (file.toString().contains(".maven"))
            return new LinkedList<>();

        System.out.println(file);
        if (file.isDirectory()) {
            return getClassesFromDirectory(file);
        }
        else {
            return getClassesFromJarFile(file);
        }
    }



    private static String fromFileToClassName(final String fileName) {
        return fileName.substring(0, fileName.length() - 6).replaceAll("/|\\\\", "\\.");
    }



    private static List<Class<?>> getClassesFromJarFile(File path) {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        try {
            if (path.canRead()) {
                JarFile jar = new JarFile(path);
                try {
                    Enumeration<JarEntry> en = jar.entries();
                    outer: while (en.hasMoreElements()) {
                        JarEntry entry = en.nextElement();
                        if (entry.getName().endsWith("class")) {
                            try {
                                String className = fromFileToClassName(entry.getName());
                                for (String excludePackage : acExcludePackages) {
                                    if (className.startsWith(excludePackage))
                                        continue outer;
                                }
                                System.out.println(className);
                                Class<?> claz = Class.forName(className);
                                classes.add(claz);
                            }
                            catch (ClassNotFoundException | NoClassDefFoundError | ExceptionInInitializerError | UnsatisfiedLinkError | UnsupportedClassVersionError e) {
                                // ignore
                            }
                        }
                    }
                }
                finally {
                    jar.close();
                }
            }
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to read classes from jar file: " + path, e);
        }

        return classes;
    }



    private static List<Class<?>> getClassesFromDirectory(File path) {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        // get jar files from top-level directory
        List<File> jarFiles = listFiles(path, new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jar");
            }
        }, false);
        for (File file : jarFiles) {
            classes.addAll(getClassesFromJarFile(file));
        }

        // get all class-files
        List<File> classFiles = listFiles(path, new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".class");
            }
        }, true);

        // List<URL> urlList = new ArrayList<URL>();
        // List<String> classNameList = new ArrayList<String>();
        int substringBeginIndex = path.getAbsolutePath().length() + 1;
        for (File classfile : classFiles) {
            String className = classfile.getAbsolutePath().substring(substringBeginIndex);
            className = fromFileToClassName(className);

            try {
                classes.add(Class.forName(className));
            }
            catch (Throwable e) {
                e.printStackTrace();
            }

        }

        return classes;
    }



    private static List<File> listFiles(File directory, FilenameFilter filter, boolean recurse) {
        List<File> files = new ArrayList<File>();
        File[] entries = directory.listFiles();

        // Go over entries
        for (File entry : entries) {
            // If there is no filter or the filter accepts the
            // file / directory, add it to the list
            if (filter == null || filter.accept(directory, entry.getName())) {
                files.add(entry);
            }

            // If the file is a directory and the recurse flag
            // is set, recurse into the directory
            if (recurse && entry.isDirectory()) {
                files.addAll(listFiles(entry, filter, recurse));
            }
        }

        // Return collection of files
        return files;
    }



    public static List<File> getClassLocationsForCurrentClasspath() {
        List<File> urls = new ArrayList<File>();
        String javaClassPath = System.getProperty("java.class.path");
        if (javaClassPath != null) {
            for (String path : javaClassPath.split(File.pathSeparator)) {
                urls.add(new File(path));
            }
        }
        return urls;
    }

    // // todo: this is only partial, probably
    // public static URL normalize(URL url) throws MalformedURLException {
    // String spec = url.getFile();
    //
    // // get url base - remove everything after ".jar!/??" , if exists
    // final int i = spec.indexOf("!/");
    // if (i != -1) {
    // spec = spec.substring(0, spec.indexOf("!/"));
    // }
    //
    // // uppercase windows drive
    // url = new URL(url, spec);
    // final String file = url.getFile();
    // final int i1 = file.indexOf(':');
    // if (i1 != -1) {
    // String drive = file.substring(i1 - 1, 2).toUpperCase();
    // url = new URL(url, file.substring(0, i1 - 1) + drive +
    // file.substring(i1));
    // }
    //
    // return url;
    // }

}