package io.github.jass.plugin;


import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import io.github.jass.Scaffolder;

@Mojo( name = "generate")
public class ScaffoldingMojo extends AbstractMojo
{

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @SuppressWarnings("unchecked")
    public void execute() throws MojoExecutionException
    {
        File basedir = project.getBasedir();
        List<String> compileSourceRoots = project.getCompileSourceRoots();
        getLog().info("Scaffolding - Source paths: " + compileSourceRoots);
        
        new Scaffolder(compileSourceRoots, basedir.getName()).generate();
    }
}