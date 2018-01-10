package eu.cehj.cdb2.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eu.cehj.cdb2.entity.BaseEntity;
import eu.cehj.cdb2.service.BaseService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public abstract class BaseServiceImpl<T extends BaseEntity, ID extends Serializable> implements
BaseService<T, ID> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    protected EntityManager em;

    protected Class<T> entityClass;

    @Autowired
    protected CrudRepository<T, ID> repository;

    @SuppressWarnings("unchecked")
    public BaseServiceImpl() {

        final ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @Override
    public T save(final T entity) throws Exception {

        return this.repository.save(entity);
    }

    @Override
    public T get(final ID id) throws Exception {
        if (id == null) {
            throw new IllegalArgumentException("Required param 'ID' not present");
        }

        final T bean = this.repository.findOne(id);

        if (bean == null) {
            throw new NullPointerException("Bean does not exists for given ID:" + id);
        }

        return bean;
    }

    @Override
    public List<T> getAll() throws Exception {
        List<T> entities = (List<T>) this.repository.findAll();
        if (entities == null) {
            entities = new ArrayList<T>();
        }
        return entities;
    }

    @Override
    public void delete(final ID id) throws Exception {
        final T entity = this.repository.findOne(id);
        if (entity != null) {
            this.repository.delete(entity);
        } else {
            throw new EntityNotFoundException("No object found with the id #" + id);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void delete(final Iterable<T> entities) throws Exception {
        for (final T entity : entities) {
            this.delete((ID) entity.getId());
        }
    }

}
