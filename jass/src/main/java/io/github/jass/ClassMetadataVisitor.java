package io.github.jass;

import java.util.List;
import java.util.Map;

import io.github.jass.AnnotationMetadata.AnnotationType;
import io.github.jass.parser.Java8BaseVisitor;
import io.github.jass.parser.Java8Parser.AnnotationContext;
import io.github.jass.parser.Java8Parser.ClassModifierContext;
import io.github.jass.parser.Java8Parser.ElementValuePairContext;
import io.github.jass.parser.Java8Parser.ElementValuePairListContext;
import io.github.jass.parser.Java8Parser.FieldDeclarationContext;
import io.github.jass.parser.Java8Parser.FieldModifierContext;
import io.github.jass.parser.Java8Parser.NormalClassDeclarationContext;
import io.github.jass.parser.Java8Parser.PackageDeclarationContext;
import io.github.jass.parser.Java8Parser.SingleTypeImportDeclarationContext;
import io.github.jass.parser.Java8Parser.VariableDeclaratorContext;

/**
 * Created by jpenren on 24/1/18.
 */
final class ClassMetadataVisitor extends Java8BaseVisitor<Object> {

    private final ClassMetadata classMetadata = new ClassMetadata();
    private final Map<String, ClassMetadata> classMetadataMap;

    public ClassMetadataVisitor(Map<String, ClassMetadata> classMetadataMap) {
        this.classMetadataMap = classMetadataMap;
    }

    @Override
    public Object visitPackageDeclaration(PackageDeclarationContext ctx) {
        classMetadata.setPackageName(ctx.packageName().getText());

        return visitChildren(ctx);
    }

    @Override
    public Object visitSingleTypeImportDeclaration(SingleTypeImportDeclarationContext ctx) {
        classMetadata.getImports().add(ctx.typeName().getText());

        return visitChildren(ctx);
    }

    @Override
    public Object visitNormalClassDeclaration(NormalClassDeclarationContext ctx) {
        String className = ctx.Identifier().getText();
        String qualifiedName = className.contains(".") ? className : classMetadata.getPackageName() + "." + className;
        classMetadata.setName(qualifiedName);
        classMetadataMap.put(qualifiedName, classMetadata);

        if (ctx.superclass() != null) {
            String parentClassName = ctx.superclass().classType().Identifier().getText();
            classMetadata.setParentClassName(getQualifiedName(parentClassName));
        }

        return visitChildren(ctx);
    }

    @Override
    public Object visitClassModifier(ClassModifierContext ctx) {
        AnnotationContext annotation = ctx.annotation();
        if (annotation != null) {
            AnnotationMetadata annotationMetadata = parse(annotation);
            classMetadata.getAnnotations().add(annotationMetadata);
        }

        return visitChildren(ctx);
    }

    @Override
    public Object visitFieldModifier(FieldModifierContext ctx) {
        AnnotationContext annotation = ctx.annotation();
        if (annotation != null) {
            FieldDeclarationContext fieldDeclaration = (FieldDeclarationContext) ctx.getParent();
            String fieldType = getQualifiedName(fieldDeclaration.unannType().getText());
            List<VariableDeclaratorContext> variables = fieldDeclaration.variableDeclaratorList().variableDeclarator();
            for (VariableDeclaratorContext var : variables) {
                String variableName = var.variableDeclaratorId().getText();
                FieldMetadata fieldMetadata = new FieldMetadata(variableName, fieldType);
                AnnotationMetadata annotationMetadata = parse(annotation);
                fieldMetadata.getAnnotations().add(annotationMetadata);
                classMetadata.getFields().put(variableName, fieldMetadata);
                if (annotationMetadata.getClassName().equals("javax.persistence.Id")) {
                    classMetadata.setPrimaryKey(fieldMetadata);
                }
            }
        }

        return visitChildren(ctx);
    }

    private AnnotationMetadata parse(AnnotationContext annotation) {
        if (annotation.normalAnnotation() != null) {
            String className = getQualifiedName(annotation.normalAnnotation().typeName().getText());
            AnnotationMetadata annotationMetadata = new AnnotationMetadata(AnnotationType.NORMAL, className);
            ElementValuePairListContext paramsList = annotation.normalAnnotation().elementValuePairList();
            for (ElementValuePairContext param : paramsList.elementValuePair()) {
                String name = param.Identifier().getText();
                String value = param.elementValue().getText();
                annotationMetadata.getParameters().put(name, value);
            }

            return annotationMetadata;
        }

        if (annotation.singleElementAnnotation() != null) {
            String className = getQualifiedName(annotation.singleElementAnnotation().typeName().getText());
            AnnotationMetadata annotationMetadata = new AnnotationMetadata(AnnotationType.SINGLE, className);
            String value = annotation.singleElementAnnotation().elementValue().getText();
            annotationMetadata.getParameters().put("default", value);

            return annotationMetadata;
        }

        String className = getQualifiedName(annotation.markerAnnotation().typeName().getText());
        return new AnnotationMetadata(AnnotationType.MARKER, className);
    }

    private String getQualifiedName(String className) {
        if (className.contains(".")) {
            return className;
        }

        String dotClassName = "." + className;
        for (String aImport : classMetadata.getImports()) {
            if (aImport.endsWith(dotClassName)) {
                return aImport;
            }
        }

        return className;
    }

}
