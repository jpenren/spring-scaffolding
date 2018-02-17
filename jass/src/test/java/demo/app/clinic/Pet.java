package demo.app.clinic;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by jpenren on 24/1/18.
 */
@ Entity(name = "entityd")
@Table(name = "my-entity", catalog = "pepe")
@Deprecated
public class Pet {


    @javax.persistence.Id
    private Integer id;

    @Column(name = "demo")
    private String demo;

    @Column
    private String demo2;

    @Column
    public String getValue(){
        return null;
    }

}
