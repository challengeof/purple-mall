package net.caidingke.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.DbDefault;
import io.ebean.annotation.Index;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.caidingke.common.constants.Const;
import net.caidingke.domain.base.BaseModel;
import net.caidingke.domain.finder.CustomerFinder;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * @author bowen
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Index(columnNames = {"username", "telephone"})
public class Customer extends BaseModel {

    public static final CustomerFinder find = new CustomerFinder();
    private static final long serialVersionUID = 3024180838410408495L;
    /**
     * 用户名
     */
    @Column(unique = true, nullable = false)
    private String username;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * 用户手机号
     */
    private String telephone;
    /**
     * 用户是否启用
     */
    @DbDefault("true")
    @DbComment("Whether the user is enabled ")
    private boolean enabled;
    /**
     * token
     */
    @JsonIgnore
    public String token;
    /**
     * 头像
     */
    private String avatar = Const.DEFAULT_AVATAR;
    /**
     * 昵称
     */
    public String nickname;
    /**
     * 性别 {@link net.caidingke.domain.enums.Gender}
     */
    public int gender;
    /**
     * MM-dd 生日
     */
    public String birthday;
    /**
     * 微信openid
     */
    public String openId;
    /**
     * 微信unionid
     */
    public String unionId;
}
