package <entity.packageName>;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional(readOnly = true)
class <entity.simpleName>ServiceImpl implements <entity.simpleName>Service {

    private final <entity.simpleName>Repository <entity.camelCaseName>Repository;

    @Autowired
    public <entity.simpleName>ServiceImpl(<entity.simpleName>Repository <entity.camelCaseName>Repository) {
        this.<entity.camelCaseName>Repository = <entity.camelCaseName>Repository;
    }

    @Override
    public <entity.simpleName> find<entity.simpleName>ById(<entity.primaryKey.fieldType> id) {
        return <entity.camelCaseName>Repository.findOne(id);
    }

    @Override
    public Iterable\<<entity.simpleName>> findAll() {
        return <entity.camelCaseName>Repository.findAll();
    }

    @Override
    @Transactional(readOnly = false)
    public void save(<entity.simpleName> <entity.camelCaseName>) {
        <entity.camelCaseName>Repository.save(<entity.camelCaseName>);
    }

}
