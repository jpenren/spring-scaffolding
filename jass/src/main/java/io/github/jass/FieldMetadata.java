package io.github.jass;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jpenren on 26/1/18.
 */
final class FieldMetadata {

    private final List<AnnotationMetadata> annotations = new ArrayList<>();
    private final String fieldName;
    private final String fieldType;

    public FieldMetadata(String fieldName, String fieldType) {
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }

    public List<AnnotationMetadata> getAnnotations() {
        return annotations;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    @Override
    public String toString() {
        return "FieldMetadata{" +
                "fieldName='" + fieldName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                '}';
    }

}
