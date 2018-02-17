package io.github.jass;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import io.github.jass.EntityFinder.FileVisitor;
import io.github.jass.parser.Java8Lexer;
import io.github.jass.parser.Java8Parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jpenren on 26/1/18.
 */
public final class Scaffolder {

    private static final ClassMetadata DUMMY = new ClassMetadata();
    private final Map<String, ClassMetadata> classMetadataMap = new HashMap<>();
    private final List<String> sourcePaths;
    private final String outputPath;

    public Scaffolder(List<String> sourcePaths, String outputPath) {
        this.sourcePaths = sourcePaths;
        this.outputPath = outputPath;
    }

    public void generate() {
        // Build classMetadata tree
        for (String path : sourcePaths) {
            EntityFinder.find(path, new FileVisitor() {

                @Override
                public void visit(File file) {
                    try {
                        Java8Lexer lexer = new Java8Lexer(CharStreams.fromPath(file.toPath()));
                        CommonTokenStream stream = new CommonTokenStream(lexer);
                        Java8Parser parser = new Java8Parser(stream);
                        ClassMetadataVisitor entityAnalyzerListener = new ClassMetadataVisitor(classMetadataMap);
                        entityAnalyzerListener.visit(parser.compilationUnit());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            List<ClassMetadata> entities = new ArrayList<>();
            for (Map.Entry<String, ClassMetadata> entry : classMetadataMap.entrySet()) {
                ClassMetadata classMetadata = entry.getValue();
                resolveDependencies(classMetadata);
                if (isEntity(classMetadata)) {
                    entities.add(classMetadata);
                }
            }

            TemplateEngine serviceTemplate = new TemplateEngineImpl("templates/", outputPath);
            List<Template> templates = TemplateRegistry.getTemplates("mvc");
            for (ClassMetadata entity : entities) {
                for (Template template : templates) {
                    serviceTemplate.render(entity, template);
                }
            }
        }
    }

    private void resolveDependencies(ClassMetadata classMetadata) {
        if (classMetadata.getParentClassName() != null) {
            ClassMetadata parent = resolveParent(classMetadata);
            if (parent.getParentClassName() != null) {
                resolveDependencies(parent);
            }

            classMetadata.getFields().putAll(parent.getFields());
            if (classMetadata.getPrimaryKey() == null) {
                classMetadata.setPrimaryKey(parent.getPrimaryKey());
            }
        }
    }

    private ClassMetadata resolveParent(ClassMetadata classMetadata) {
        String className = classMetadata.getParentClassName();
        String qualifiedName = classMetadata.getPackageName() + "." + classMetadata.getParentClassName();

        if (classMetadataMap.containsKey(className)) {
            return classMetadataMap.get(className);
        }

        if (classMetadataMap.containsKey(qualifiedName)) {
            return classMetadataMap.get(qualifiedName);
        }

        return DUMMY;
    }

    private boolean isEntity(ClassMetadata classMetadata) {
        List<AnnotationMetadata> annotations = classMetadata.getAnnotations();
        for (AnnotationMetadata annotation : annotations) {
            if (annotation.getClassName().equals("javax.persistence.Entity")) {
                return true;
            }
        }

        return false;
    }

}
