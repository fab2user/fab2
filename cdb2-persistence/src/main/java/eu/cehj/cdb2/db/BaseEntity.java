package eu.cehj.cdb2.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -8588354681550181582L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public BaseEntity() {
        super();
    }

    public BaseEntity(final BaseEntity bean) {
        super();

        if (bean == null) {
            return;
        }

        this.id = bean.getId();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity [id=" + this.id + "]";
    }
}
