package net.caidingke.aop;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.CachingParanamer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @author bowen
 */
public class BasicAop {

    protected final static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    private static final CachingParanamer cachingParanamer = new CachingParanamer(
            new BytecodeReadingParanamer());
    public static final Map<String, Field> FIELD_MAP = new ConcurrentHashMap<>();
    public static final Map<String, Method> METHOD_MAP = new ConcurrentHashMap<>();

    protected Type getReturnType(ProceedingJoinPoint pjp) {
        Method method = getMethod(pjp);
        return method.getGenericReturnType();
    }

    protected Method getMethod(JoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        return signature.getMethod();
    }

    protected String[] getArgNames(JoinPoint pjp) {
        return cachingParanamer.lookupParameterNames(getMethod(pjp));
    }
}
