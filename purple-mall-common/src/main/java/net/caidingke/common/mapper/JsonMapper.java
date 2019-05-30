package net.caidingke.common.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.common.base.Strings;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bowen
 */
@SuppressWarnings("unchecked")
@Slf4j
public class JsonMapper {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        MAPPER.enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
    }

    public static String toJson(Object object) {

        try {
            return MAPPER.writeValueAsString(object);
        } catch (IOException e) {
            log.error("write to json string error:" + object, e);
            throw new RuntimeException(e);
        }
    }

    public static <T> T fromJson(String jsonString, Class<T> clazz) {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }

        try {
            return MAPPER.readValue(jsonString, clazz);
        } catch (IOException e) {
            log.error("parse json string error:" + jsonString, e);
            return null;
        }
    }

    public static <T> T fromJson(String jsonString, JavaType javaType) {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }

        try {
            return MAPPER.readValue(jsonString, javaType);
        } catch (IOException e) {
            log.error("parse json string error:" + jsonString, e);
            return null;
        }
    }

    public static JavaType buildCollectionType(
            Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return MAPPER.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

    public static JavaType buildMapType(
            Class<? extends Map> mapClass, Class<?> keyClass, Class<?> valueClass) {
        return MAPPER.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
    }

    public static void update(String jsonString, Object object) {
        try {
            MAPPER.readerForUpdating(object).readValue(jsonString);
        } catch (IOException e) {
            log.error("update json string:" + jsonString + " to object:" + object + " error.", e);
        }
    }

    public static Map toMap(String jsonString) {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        try {
            return MAPPER.readValue(jsonString, Map.class);
        } catch (Exception e) {
            log.error("parse json string error:" + jsonString, e);
        }
        return null;
    }

    public static <K, V> Map<K, V> toMapWithType(
            String jsonString, Class<K> keyClass, Class<V> valueClass) {
        if (Strings.isNullOrEmpty(jsonString)) {
            return null;
        }
        try {
            JavaType javaType = buildMapType(Map.class, keyClass, valueClass);
            return MAPPER.readValue(jsonString, javaType);
        } catch (Exception e) {
            log.error("parse json string error:" + jsonString, e);
        }
        return null;
    }

    public static Map toMap(Object object) {
        return MAPPER.convertValue(object, Map.class);
    }

    public static <K, V> Map<K, V> toMapWithType(
            Object object, Class<K> keyClass, Class<V> valueClass) {
        JavaType javaType = buildMapType(Map.class, keyClass, valueClass);
        return MAPPER.convertValue(object, javaType);
    }

    public static <T> T fromMap(Map map, Class<T> clazz) {
        if (map != null) {
            try {
                return MAPPER.convertValue(map, clazz);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static <T> T readValue(InputStream in, Class<T> clazz) throws IOException {
        return MAPPER.readValue(in, clazz);
    }

    public static String toJsonP(String functionName, Object object) {
        return toJson(new JSONPObject(functionName, object));
    }

    public static List<Map> strListToMapList(Collection<String> stringList) {
        List<Map> mapList = new ArrayList<>();
        if (stringList != null) {
            mapList.addAll(stringList.stream().map(JsonMapper::toMap).collect(Collectors.toList()));
        }

        return mapList;
    }

    public static <T> List<T> toListWithType(String json, Class<T> elementClass) {
        JavaType javaType = buildCollectionType(List.class, elementClass);
        try {
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static <T> T restore(Map map, Class<T> clazz) {
        if (map != null) {
            try {
                return MAPPER.convertValue(map, clazz);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static void enableEnumUseToString() {
        MAPPER.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        MAPPER.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }
}
