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
import net.caidingke.domain.finder.UserFinder;

/**
 * @author bowen
 */
@Entity
@Getter
@Setter
@Index(columnNames = {"username", "telephone"})
public class User extends BaseModel {

    public static final UserFinder find = new UserFinder();
    private static final long serialVersionUID = 7375806029667658870L;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String realName;

    @JsonIgnore
    private String password;

    @DbDefault("true")
    private boolean enabled;

    private String telephone;

    @DbComment("The date the user first registered")
    private long registered;

    private String email;

    private String headImgUrl;

}
