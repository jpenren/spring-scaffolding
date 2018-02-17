package io.github.jass;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jpenren on 26/1/18.
 */
final class AnnotationMetadata {

    public enum AnnotationType {
        MARKER, SINGLE, NORMAL
    }

    private final AnnotationType type;
    private final String className;
    private final Map<String, String> parameters = new HashMap<>();

    public AnnotationMetadata(AnnotationType type, String className) {
        this.type = type;
        this.className = className;
    }

    public String getClassName() {
        return className;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    @Override
    public String toString() {
        return "AnnotationMetadata{" +
                "type=" + type +
                ", className='" + className + '\'' +
                ", parameters=" + parameters +
                '}';
    }

}
