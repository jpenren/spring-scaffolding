package <entity.packageName>.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

import <entity.name>Service;

@Controller
class <entity.simpleName>Controller {

    private final <entity.simpleName>Service <entity.camelCaseName>Service;

    @Autowired
    public <entity.simpleName>Controller(<entity.simpleName>Service <entity.camelCaseName>Service) {
        this.<entity.camelCaseName>Service = <entity.camelCaseName>Service;
    }
    
    @GetMapping
    public String index() {
        return "index";
    }

}
