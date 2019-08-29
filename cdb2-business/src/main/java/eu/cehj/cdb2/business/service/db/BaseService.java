package eu.cehj.cdb2.business.service.db;

import java.io.Serializable;
import java.util.List;

import eu.cehj.cdb2.common.dto.BaseDTO;
import eu.cehj.cdb2.entity.BaseEntity;

/**
 * interface that provides default methods to return directly a DTO when querying the database.
 * It is generic and must be instantiate with the base entity we query, the DTO to be return, and the type of the Primary key.
 * It is quite similar to the Spring *Repository classes hierarchy, but the main difference is that some methods return a DTO, and not an entity.
 */
public interface BaseService<T extends BaseEntity, U extends BaseDTO,  ID extends Serializable> {

	public T save(T entity);

	public T get(ID id);

	public List<T> getAll();

	public List<U> getAllDTO();

	public void delete(ID id);

	public void delete(Iterable<T> entities);

	public void physicalDelete(ID id);

	public void physicalDelete(Iterable<T> entities);

	public U save(U dto);

	public U getDTO(ID id);

	public T populateEntityFromDTO(final U dto);

	public U populateDTOFromEntity(final T entity);

}