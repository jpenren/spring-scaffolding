package demo.app;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import demo.app.clinic.Pet;

/**
 * Created by jpenren on 24/1/18.
 */
@ Entity(name = "entityd")
@javax.persistence.Table(name = "my-entity", catalog = "pepe")
public class Customer extends NamedEntity {


    @javax.persistence.Id
    private Long id;

    @Column
    private String demo;

    @javax.persistence.Column
    private String demo2;

    @OneToMany
    private List<Pet> pets;

    @Column
    public String getValue(){
        return null;
    }



}
