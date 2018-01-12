package eu.cehj.cdb2.business.service.db;

import java.io.Serializable;
import java.util.List;

import eu.cehj.cdb2.entity.BaseEntity;

public interface BaseService<T extends BaseEntity, ID extends Serializable> {

    public T save(T entity) throws Exception;

    public T get(ID id) throws Exception;

    public List<T> getAll() throws Exception;

    public void delete(ID id) throws Exception;

    public void delete(Iterable<T> entities) throws Exception;

}