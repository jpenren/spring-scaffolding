package io.github.jass;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Created by jpenren on 24/1/18.
 */
final class EntityFinder {

    public interface FileVisitor {
        void visit(File file);
    }

    public static void find(String path, FileVisitor visitor) {
        find(new File(path), visitor);
    }

    public static void find(File rootFile, FileVisitor visitor) {
        if (rootFile.isDirectory()) {
            File[] files = rootFile.listFiles(new FileFilter() {
                
                @Override
                public boolean accept(File pathname) {
                    return pathname.isDirectory() || pathname.getName().endsWith(".java");
                }
            });

            for (File file : files) {
                find(file, visitor);
            }
        } else {
            if (isEntityCandidate(rootFile)) {
                visitor.visit(rootFile);
            }
        }
    }

    public static boolean isEntityCandidate(File file) {
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().contains("javax.persistence.")) {
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File " + file + " not accesible");
        }

        return false;
    }

}
