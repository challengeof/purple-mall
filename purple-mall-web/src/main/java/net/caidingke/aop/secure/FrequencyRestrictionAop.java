package net.caidingke.aop.secure;

import com.google.common.base.Strings;
import net.caidingke.aop.BasicAop;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @author bowen
 */
@Aspect
@Component
public class FrequencyRestrictionAop extends BasicAop {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public FrequencyRestrictionAop(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // @Around(value = "execution(* *(..)) && @annotation(frequencyRestriction)")
    // public Object handle(ProceedingJoinPoint pjp, FrequencyRestriction frequencyRestriction)
    //         throws Throwable {
    //
    //     Optional<String> username = SecurityUtils.getCurrentUserLogin();
    //     if (!username.isPresent()) {
    //         return pjp.proceed();
    //     }
    //
    //     Method method = getMethod(pjp);
    //     String cacheKey = String
    //             .format("%s_%s_%s", Constants.APPLICATION_NAME, method.getName(), username.get());
    //
    //     long value = redisTemplate.opsForValue().increment(cacheKey);
    //
    //     if (value > 1) {
    //         throw new BizException(ErrorCode._10006);
    //     }
    //
    //     redisTemplate.expire(cacheKey, frequencyRestriction.interval(), TimeUnit.SECONDS);
    //
    //     return pjp.proceed();
    // }
    @Around(value = "execution(* *(..)) && @annotation(frequencyRestriction)")
    public Object handle(ProceedingJoinPoint pjp, FrequencyRestriction frequencyRestriction)
            throws Throwable {
        String cacheKey = null;
        try {
            String className = pjp.getTarget().getClass().getName();

            Signature signature = pjp.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            String[] parameterNames = methodSignature.getParameterNames();
            int currentUserAccountIndex = ArrayUtils.indexOf(parameterNames, "currentUserAccount");
            Object[] args = pjp.getArgs();
            Object currentUserAccount = args[currentUserAccountIndex];
            if (currentUserAccount == null) {
                return pjp.proceed();
            }

            Method method = methodSignature.getMethod();
            cacheKey = String
                    .format("%s:%s:%s:%s", "1", className, method.getName(), currentUserAccount);

            long value = redisTemplate.opsForValue().increment(cacheKey);

            if (value > 1) {
                throw new RuntimeException("测试");
            }

            redisTemplate.expire(cacheKey, frequencyRestriction.interval(), TimeUnit.SECONDS);

            return pjp.proceed();
        } finally {
            if (!Strings.isNullOrEmpty(cacheKey)) {
                try {
                    redisTemplate.delete(cacheKey);
                } catch (Throwable ignore) {

                }
            }

        }
    }
}
