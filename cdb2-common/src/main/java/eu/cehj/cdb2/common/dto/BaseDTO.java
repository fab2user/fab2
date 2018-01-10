package eu.cehj.cdb2.common.dto;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import eu.cehj.cdb2.entity.BaseEntity;

public abstract class BaseDTO implements Serializable{

    private static final long serialVersionUID = -1698240108910080832L;

    private Long id;

    public BaseDTO() {
        super();
    }

    public BaseDTO(final BaseEntity baseEntity) {
        super();

        BeanUtils.copyProperties(baseEntity, this);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

}
