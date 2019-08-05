package net.caidingke.aop.secure;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import net.caidingke.aop.BasicAop;
import net.caidingke.business.exception.BizException;
import net.caidingke.business.exception.ErrorCode;
import net.caidingke.common.Constants;
import net.caidingke.security.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

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

    @Around(value = "execution(* *(..)) && @annotation(frequencyRestriction)")
    public Object handle(ProceedingJoinPoint pjp, FrequencyRestriction frequencyRestriction)
            throws Throwable {

        Optional<String> username = SecurityUtils.getCurrentUserLogin();
        if (!username.isPresent()) {
            return pjp.proceed();
        }

        Method method = getMethod(pjp);
        String cacheKey = String
                .format("%s_%s_%s", Constants.APPLICATION_NAME, method.getName(), username.get());

        long value = redisTemplate.opsForValue().increment(cacheKey);

        if (value > 1) {
            throw new BizException(ErrorCode._10006);
        }

        redisTemplate.expire(cacheKey, frequencyRestriction.interval(), TimeUnit.SECONDS);

        return pjp.proceed();
    }
}
