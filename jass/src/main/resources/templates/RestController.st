package <entity.packageName>.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import <entity.name>Service;

@RestController
class <entity.simpleName>RestController {

    private final <entity.simpleName>Service <entity.camelCaseName>Service;

    @Autowired
    public <entity.simpleName>RestController(<entity.simpleName>Service <entity.camelCaseName>Service) {
        this.<entity.camelCaseName>Service = <entity.camelCaseName>Service;
    }

}
