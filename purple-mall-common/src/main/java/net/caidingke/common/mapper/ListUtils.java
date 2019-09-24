package net.caidingke.common.mapper;

import com.google.common.base.Strings;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author bowen
 */
@SuppressWarnings("unchecked")
public class ListUtils {

    private static <T> List<Map> toMaps(List<T> list) {
        return CollectionUtils.isEmpty(list) ? Collections.EMPTY_LIST : list.stream().map(JsonMapper::toMap).collect(Collectors.toList());
    }

    public static <T> List<T> toEntities(List<String> list, Class<T> cls) {
        return list.stream().map(str -> Strings.isNullOrEmpty(str) ? null : JsonMapper.fromJson(str, cls)).collect(Collectors.toList());
    }

    private static <T> Map<Long, T> toMapBy(List<T> list, String key) {

        if (list == null) {
            return null;
        }

        if (list.size() == 0) {
            return Collections.emptyMap();
        }

        Map<Long, T> map = new LinkedHashMap<>();
        for (T entity : list) {
            try {
                if (entity == null) {
                    continue;
                }
                Field field = entity.getClass().getField(key);
                Object value = field.get(entity);
                map.put((Long) value, entity);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return map;
    }

    public static <T> List<String> getKeys(List<T> list, String key) {

        if (list == null) {
            return null;
        }

        if (list.size() == 0) {
            return Collections.emptyList();
        }

        List<String> keys = new ArrayList<>();
        for (T entity : list) {
            try {
                Field field = entity.getClass().getField(key);
                Object value = field.get(entity);
                keys.add(value.toString());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return keys;
    }

    public static Map<String, Map> indexByKey(List<Map> list, String key) {

        if (list == null) {
            return null;
        }

        if (list.size() == 0) {
            return Collections.emptyMap();
        }

        Map<String, Map> map = new LinkedHashMap<>();
        for (Map data : list) {
            map.put(String.valueOf(data.get(key)), data);
        }

        return map;
    }

    public static <O, Q, T> List<Map> inject(List<T> models, String key, String injectKey, FetchFunction<O, Q> function) {
        return inject2(toMaps(models), key, injectKey, function);
    }

    public static <T, Q> List<Map> inject2(List<Map> list, String key, String injectKey, FetchFunction<T, Q> function) {
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }

        List<Q> ids = new ArrayList<>();
        for (Map info : list) {
            if (info == null) {
                continue;
            }

            Q id = (Q) info.get(key);
            if (id != null) {
                ids.add(id);
            }
        }

        List<T> entities = function.apply(ids);

        Map<Long, T> entitiesMap = toMapBy(entities, "id");

        for (Map info : list) {
            if (info == null) {
                continue;
            }

            Long id = (Long) info.get(key);
            if (id != null) {
                T entity = entitiesMap.get(id);
                info.put(injectKey, entity == null ? Collections.EMPTY_MAP : entity);
            }
        }

        return list;
    }

    public static <O, Q, T> List<Map> injectVariable(List<T> models, String key, String injectKey, FetchFunction<O, Q> callback) {
        return injectVariable2(toMaps(models), key, injectKey, callback);
    }

    public static <T, Q> List<Map> injectVariable2(List<Map> list, String key, String injectKey, FetchFunction<T, Q> callback) {
        if (CollectionUtils.isEmpty(list)) {
            return list;
        }

        List<Q> ids = new ArrayList<>();

        for (Map info : list) {
            Map map = info;
            String[] keyStrings = key.split("\\.");

            for (int i = 0; i < keyStrings.length - 1; i++) {
                map = (Map) map.get(keyStrings[i]);
            }

            ids.add((Q) map.get(keyStrings[keyStrings.length - 1]));
        }

        List<T> entities = callback.apply(ids);

        for (int i = 0; i < list.size(); i++) {
            Map map = list.get(i);
            Object[] keyStrings = key.split("\\.");

            for (int j = 0; j < keyStrings.length - 1; j++) {
                map = (Map) map.get(keyStrings[j]);
            }

            map.put(injectKey, entities.get(i));
        }

        return list;
    }

    @FunctionalInterface
    public interface FetchFunction<T, Q> {
        /**
         * 函数
         *
         * @param ids 函数的参数
         * @return 函数的结果
         */
        List<T> apply(List<Q> ids);
    }

    public static <T> void removeNullObjects(List<T> objects) {
        if (CollectionUtils.isNotEmpty(objects)) {
            objects.removeIf(Objects::isNull);
        }
    }
}
