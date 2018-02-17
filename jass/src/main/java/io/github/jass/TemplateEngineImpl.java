package io.github.jass;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STRawGroupDir;
import org.stringtemplate.v4.StringRenderer;
import org.stringtemplate.v4.misc.ErrorManager;

/**
 * Created by jpenren on 26/1/18.
 */
final class TemplateEngineImpl implements TemplateEngine {
    
    private final Path basePath;
    private final Path outputPath;

    public TemplateEngineImpl(String basePath, String outputPath) {
        this.basePath = Paths.get(basePath);
        this.outputPath = Paths.get(outputPath);
    }

    @Override
    public void render(ClassMetadata classMetadata, Template template) {
        try {
            File outputFile = getOutputFile(classMetadata, template);
            outputFile.getParentFile().mkdirs();
            
            STGroup group = new STRawGroupDir(basePath+File.separator);
            group.registerRenderer(String.class, new StringRenderer());
            ST tmpl = group.getInstanceOf(template.getName());
            tmpl.add("entity", classMetadata);
            tmpl.write(outputFile, ErrorManager.DEFAULT_ERROR_LISTENER, "UTF-8");
        } catch (Exception e) {
            throw new IllegalStateException("Error processing template", e);
        }
    }

    private File getOutputFile(ClassMetadata classMetadata, Template template) {
        Path path = resolvePath(classMetadata, template);
        STGroup group = new STGroup();
        group.registerRenderer(String.class, new StringRenderer());
        ST tmpl = new ST(group, template.getOutputFileName());
        String render = tmpl.add("entity", classMetadata).render();
        
        return path.resolve(render).toFile();
    }

    private Path resolvePath(ClassMetadata classMetadata, Template template) {
        if (template instanceof ResolvableLocation) {
            File file = outputPath.resolve("src/main/webapp").toFile();
            if (file.exists()) {
                return file.toPath().resolve("/WEB-INF/templates");
            }

            // Spring Boot default path
            return outputPath.resolve("src/main/resources/templates");
        }

        String classPath = pathFromQualifiedName(classMetadata.getName());
        return outputPath.resolve("src/main/java").resolve(classPath);
    }

    private String pathFromQualifiedName(String qualifiedName) {
        if (qualifiedName.contains(".")) {
            return qualifiedName.substring(0, qualifiedName.lastIndexOf('.')).replaceAll("\\.", File.separator);
        }

        return File.separator;
    }
    
}
