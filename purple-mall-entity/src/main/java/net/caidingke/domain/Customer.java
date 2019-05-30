package net.caidingke.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.DbDefault;
import io.ebean.annotation.Index;
import javax.persistence.Column;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.CustomerFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
@Index(columnNames = {"username", "telephone"})
public class Customer extends BaseModel {

    public static final CustomerFinder find = new CustomerFinder();
    private static final long serialVersionUID = 3024180838410408495L;
    @Column(unique = true, nullable = false)
    private String username;

    @JsonIgnore
    private String password;

    private String telephone;

    @DbComment("The date the user first registered")
    private long registered;

    @DbDefault("true")
    private boolean enabled;

    private String headImgUrl;
}
