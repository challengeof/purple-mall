package net.caidingke.domain.base;

import io.ebean.Model;
import io.ebean.annotation.WhenCreated;
import java.io.Serializable;
import java.util.Map;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import net.caidingke.common.mapper.JsonMapper;

/**
 * @author bowen
 */
@MappedSuperclass
@Getter
@Setter
public class BaseModel extends Model implements Serializable {

    private static final long serialVersionUID = -1322034841828005834L;
    /**
     * 主键id
     */
    @Id
    @GeneratedValue
    private Long id;
    /**
     * 创建时间
     */
    @WhenCreated
    private long createTime;

    @SuppressWarnings("unchecked")
    public Map<String, Object> toMap() {
        return JsonMapper.toMapWithType(this, String.class, Object.class);
    }

    public String toJson() {
        return JsonMapper.toJson(this);
    }

    public static <T> T restore(Map map, Class<T> clazz) {
        return JsonMapper.fromMap(map, clazz);
    }
}
