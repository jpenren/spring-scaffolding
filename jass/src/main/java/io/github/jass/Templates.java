package io.github.jass;

public final class Templates {
    
    private Templates() {
    }
    
    public static Template source(String name, String outputFileName) {
        return new BasicTemplate(name, outputFileName);
    }
    
    public static Template view(String name, String outputFileName) {
        return new ViewTemplate(name, outputFileName);
    } 

}
