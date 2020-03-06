package net.caidingke.domain;

import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Version;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
public class Ebean extends BaseModel {

    @Version
    private Long version;

    private String name;
}
