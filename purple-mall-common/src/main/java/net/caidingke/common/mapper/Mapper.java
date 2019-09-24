package net.caidingke.common.mapper;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import static org.joor.Reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author bowen
 */
public class Mapper {

    private static <T> List<Map<String, Object>> toMaps(List<T> list) {
        return CollectionUtils.isEmpty(list) ? Collections.EMPTY_LIST : list.stream().map(m -> JsonMapper.toMapWithType(m, String.class, Object.class)).collect(Collectors.toList());
    }

    public static <T, O, Q> Map<String, Map<String, Object>> listBeanToMap(List<O> models, String key, String toMapBy, FetchFunction<T, Q> function) {
        checkParams(key, toMapBy, function);
        return listMapToMap(toMaps(models), key, toMapBy, function);
    }

    public static <T, Q> Map<String, Map<String, Object>> listMapToMap(List<Map<String, Object>> models, String key, String toMapBy, FetchFunction<T, Q> function) {
        checkParams(key, toMapBy, function);
        Map<String, Map<String, Object>> result = Maps.newHashMap();
        if (CollectionUtils.isEmpty(models)) {
            return result;
        }

        List<Q> ids = models.stream().map(m -> (Q) m.get(key)).collect(Collectors.toList());

        List<T> entities = function.apply(ids);

        Map<String, T> entitiesMap = toMapBy(entities, toMapBy);

        for (Q id : ids) {
            T entity = entitiesMap.get(id);
            result.putIfAbsent(String.valueOf(id), entity == null ? Collections.EMPTY_MAP : JsonMapper.toMapWithType(entity, String.class, Object.class));
        }

        return result;
    }

    public static <T, Q> Map<String, Map<String, Object>> mapToMap(Map<String, Object> model, String key, String toMapBy, FetchFunction<T, Q> function) {
        checkParams(key, toMapBy, function);
        return listMapToMap(Lists.newArrayList(model), key, toMapBy, function);
    }

    public static Map<String, Map<String, Object>> enumToMap(Class<?> clazz, String key) {
        Map<String, Map<String, Object>> result = Maps.newHashMap();
        Map<String, Object> res = new HashMap<>();
        if (!clazz.isEnum()) {
            throw new RuntimeException("Type that are not supported.");
        }
        try {
            Method method = clazz.getMethod("values");
            Enum[] enums = (Enum[]) method.invoke(null, null);
            for (Enum anEnum : enums) {
                Method getValue = anEnum.getClass().getMethod("getValue");
                res.putIfAbsent(anEnum.name(), getValue.invoke(anEnum));
            }
            result.put(key, res);
        } catch (Exception e) {
            throw new RuntimeException("Type that are not supported.");
        }

        return result;
    }

    public static <T, O, Q, R> Map<String, Map<String, Object>> litBeanToMapWithField(List<O> list, String key, String toMapBy, FetchFunction<T, Q> fetchFunction, Function<T, R> function) {
        checkParams(key, toMapBy, fetchFunction, function);
        List<Map<String, Object>> models = toMaps(list);
        Map<String, Map<String, Object>> result = Maps.newHashMap();
        if (CollectionUtils.isEmpty(models)) {
            return result;
        }

        List<Q> ids = models.stream().map(m -> (Q) m.get(key)).collect(Collectors.toList());

        List<T> entities = fetchFunction.apply(ids);

        Map<Q, T> entitiesMap = toMapBy(entities, toMapBy);

        Map<String, Object> interiorMap = new HashMap<>();
        for (Q id : ids) {
            T entity = entitiesMap.get(id);
            if (entity != null) {
                interiorMap.put(String.valueOf(id), function.apply(entity));
            }
        }
        result.putIfAbsent(key, interiorMap);

        return result;
    }

    private static <Q, T> Map<Q, T> toMapBy(List<T> list, String key) {

        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyMap();
        }

        Map<Q, T> map = new LinkedHashMap<>(list.size());
        for (T entity : list) {
            try {
                if (entity == null) {
                    continue;
                }
                Field field = entity.getClass().getDeclaredField(key);
                on(entity.getClass()).field(key);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                Object value = field.get(entity);
                map.put((Q) value, entity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return map;
    }

    private static void checkParams(String key, String toMapBy, FetchFunction fetchFunction, Function function) {
        checkParams(key, toMapBy, fetchFunction);
        Preconditions.checkNotNull(function, "function can not be null");
    }

    private static void checkParams(String key, String toMapBy, FetchFunction fetchFunction) {
        Preconditions.checkNotNull(key, "key can not be null");
        Preconditions.checkNotNull(toMapBy, "toMapBy can not be null");
        Preconditions.checkNotNull(fetchFunction, "fetchFunction can not be null");
    }

    @FunctionalInterface
    public interface FetchFunction<T, Q> {

        List<T> apply(List<Q> ids);

    }
}
