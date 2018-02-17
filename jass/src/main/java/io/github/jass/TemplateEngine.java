package io.github.jass;

/**
 * Created by jpenren on 26/1/18.
 */
interface TemplateEngine {

    void render(ClassMetadata classMetadata, Template template);
}
