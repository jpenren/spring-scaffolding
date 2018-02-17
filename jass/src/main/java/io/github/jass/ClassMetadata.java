package io.github.jass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jpenren on 26/1/18.
 */
final class ClassMetadata {

    private final List<String> imports = new ArrayList<>();
    private final List<AnnotationMetadata> annotations = new ArrayList<>();
    private final Map<String, FieldMetadata> fields = new HashMap<>();
    private String packageName;
    private String name;
    private String parentClassName;
    private FieldMetadata primaryKey;
    
    public List<String> getImports() {
        return imports;
    }

    public List<AnnotationMetadata> getAnnotations() {
        return annotations;
    }

    public Map<String, FieldMetadata> getFields() {
        return fields;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getName() {
        return name;
    }

    public String getParentClassName() {
        return parentClassName;
    }

    public String getSimpleName(){
        return name.substring(name.lastIndexOf('.')+1);
    }

    public String getCamelCaseName() {
        String simpleName = getSimpleName();
        String firstLetter = simpleName.substring(0, 1).toLowerCase();

        return firstLetter + simpleName.substring(1);
    }

    public void setParentClassName(String parentClassName) {
        this.parentClassName = parentClassName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrimaryKey(FieldMetadata primaryKey) {
        this.primaryKey = primaryKey;
    }

    public FieldMetadata getPrimaryKey() {
        return primaryKey;
    }

    @Override
    public String toString() {
        return "ClassMetadata{name='" + name +", pk: '"+getPrimaryKey()+"' ,fields='"+fields+"'}";
    }
}
