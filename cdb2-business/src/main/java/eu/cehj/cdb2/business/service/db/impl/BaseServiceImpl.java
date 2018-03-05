package eu.cehj.cdb2.business.service.db.impl;

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
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import eu.cehj.cdb2.business.service.db.BaseService;
import eu.cehj.cdb2.common.dto.BaseDTO;
import eu.cehj.cdb2.entity.BaseEntity;

@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
public abstract class BaseServiceImpl<T extends BaseEntity, U extends BaseDTO, ID extends Serializable, R extends JpaRepository<T, ID>> implements BaseService<T, U, ID> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @PersistenceContext
    protected EntityManager em;

    protected Class<T> entityClass;

    @Autowired
    protected R repository;

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
        List<T> entities = this.repository.findAll();
        if (entities == null) {
            entities = new ArrayList<T>();
        }
        return entities;
    }

    @Override
    public void delete(final ID id) throws Exception {
        final T entity = this.repository.findOne(id);
        if (entity != null) {
            entity.setDeleted(true);
            this.repository.save(entity);
        } else {
            throw new EntityNotFoundException("No object found with the id #" + id);
        }
    }

    @Override
    public void physicalDelete(final ID id) throws Exception {
        final T entity = this.repository.findOne(id);
        if (entity != null) {
            this.repository.delete(entity);
        } else {
            throw new EntityNotFoundException("No object found with the id #" + id);
        }
    }

    @Override
    public void delete(final Iterable<T> entities) throws Exception {
        for (final T entity : entities) {
            entity.setDeleted(true);
            this.save(entity);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void physicalDelete(final Iterable<T> entities) throws Exception {
        for (final T entity : entities) {
            this.delete((ID) entity.getId());
        }
    }

    @Override
    public U save(final U dto) throws Exception {
        final T entity = this.populateEntityFromDTO(dto);
        this.repository.save(entity);
        return this.populateDTOFromEntity(entity);
    }

    @Override
    public List<U> getAllDTO() throws Exception {
        final List<T> entities = this.repository.findAll();
        final List<U> dtos = new ArrayList<U>(entities.size());
        for (final T entity : entities) {
            final U dto = this.populateDTOFromEntity(entity);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public U getDTO(final ID id) throws Exception {
        final T entity = this.get(id);
        return this.populateDTOFromEntity(entity);
    }

    public abstract T populateEntityFromDTO(final U dto) throws Exception;

    public abstract U populateDTOFromEntity(final T entity) throws Exception;

}
