package net.caidingke.cache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import net.caidingke.common.mapper.JsonMapper;
import net.caidingke.profile.SpringContextUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author bowen
 */
@Slf4j
public class RDSCache extends Cache {

    private static final int EXPIRE_SECONDS = 3600;

    private static StringRedisTemplate redisTemplate;

    static {
        RDSCache.redisTemplate = SpringContextUtils.getBean(StringRedisTemplate.class);
    }

    public static <T> T get(Class<T> cls, Long id, Function<Long, T> function) {
        return mget(cls, function, id).get(0);
    }

    public static <T> List<T> mget(Class<T> cls, Function<Long, T> function, Long... ids) {
        return ArrayUtils.isEmpty(ids)
                ? Collections.emptyList()
                : load(cls, Arrays.asList(ids), function);
    }

    public static <T> List<T> mget(Class<T> cls, Function<Long, T> function, List<Long> ids) {
        return CollectionUtils.isEmpty(ids) ? Collections.emptyList() : load(cls, ids, function);
    }

    private static <T> List<T> load(Class<T> cls, List<Long> ids, Function<Long, T> function) {

        List<String> cacheKeys =
                ids.stream().map(id -> getCacheKey(cls, id)).collect(Collectors.toList());
        List<Map> modelMaps =
                JsonMapper.strListToMapList(redisTemplate.opsForValue().multiGet(cacheKeys));

        List<T> entities = new ArrayList<>();
        for (int i = 0; i < ids.size(); i++) {
            Map modelMap = modelMaps.get(i);
            if (modelMap == null) {
                try {
                    Long id = ids.get(i);
                    T entity = function.apply(id);
                    if (entity != null) {
                        log.info("entity {} {} loaded.", cls.getSimpleName(), id);
                        redisTemplate
                                .opsForValue()
                                .set(getCacheKey(cls, id), JsonMapper.toJson(entity),
                                        EXPIRE_SECONDS, TimeUnit.SECONDS);
                        entities.add(entity);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                entities.add(JsonMapper.restore(modelMap, cls));
            }
        }

        return entities;
    }
}
