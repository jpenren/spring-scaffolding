package io.github.jass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TemplateRegistry {

    private static final Map<String, List<Template>> TEMPLATES = new HashMap<>();
    public static final String MVC = "mvc";
    public static final String REST = "rest";

    static {
        //Default templates
        add(MVC, Templates.source("Repository", "<entity.simpleName>Repository.java"));
        add(MVC, Templates.source("Service", "<entity.simpleName>Service.java"));
        add(MVC, Templates.source("ServiceImpl", "<entity.simpleName>ServiceImpl.java"));
        add(MVC, Templates.source("Controller" , "web/<entity.simpleName>Controller.java"));
        add(MVC, Templates.view("index", "<entity.simpleName;format=\"lower\">/index.html"));
//        add(MVC, Templates.source("RestController", "web/<entity.simpleName>RestController.java"));
        
        add(REST, Templates.source("RestController", "web/<entity.simpleName>RestController.java"));
    }
    
    private TemplateRegistry() {
    }

    public static List<Template> getTemplates(String applicationType) {
        return TEMPLATES.get(applicationType);
    }

    public static void add(String type, Template template) {
        if( !TEMPLATES.containsKey(type) ){
            TEMPLATES.put(type, new ArrayList<Template>());
        }

        TEMPLATES.get(type).add(template);
    }

}
