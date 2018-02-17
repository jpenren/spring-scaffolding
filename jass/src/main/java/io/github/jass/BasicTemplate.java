package io.github.jass;

class BasicTemplate implements Template {

    private final String name;
    private final String outputFileName;

    public BasicTemplate(String name, String outputFileName) {
        this.name = name;
        this.outputFileName = outputFileName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOutputFileName() {
        return outputFileName;
    }
}
