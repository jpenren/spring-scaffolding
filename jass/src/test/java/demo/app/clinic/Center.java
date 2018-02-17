package demo.app.clinic;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import demo.app.NamedEntity;

@Entity(name = "center")
public class Center extends NamedEntity {

    @Id
    private Serializable mipk;

}
