package <entity.packageName>;

public interface <entity.simpleName>Service {

    /**
    * Return a <entity.simpleName> identified by id
    */
    <entity.simpleName> find<entity.simpleName>ById(<entity.primaryKey.fieldType> id);

    Iterable\<<entity.simpleName>> findAll();

    void save(<entity.simpleName> <entity.camelCaseName>);

}
