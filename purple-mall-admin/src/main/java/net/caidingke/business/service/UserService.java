package net.caidingke.business.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.google.common.base.Strings;
import io.ebean.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;
import net.caidingke.business.controller.request.UserRequest;
import net.caidingke.business.exception.BusinessException;
import net.caidingke.business.exception.ErrorCode;
import net.caidingke.cache.RDSCache;
import net.caidingke.common.mapper.BeanUtils;
import net.caidingke.domain.User;
import net.caidingke.utils.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author bowen
 */
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, StringRedisTemplate redisTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    public User findById(Long id) {
        return RDSCache.get(User.class, id, User.find::byId);
    }

    @Cached(name = "user:", key = "#username", cacheType = CacheType.BOTH)
    public User findByUsername(String username) {
        return User.find.where().username.eq(username).findOne();
    }

    public List<String> getUserRoles(Long userId) {
        return new ArrayList<>();
    }

    @Transactional(rollbackFor = Exception.class)
    public User register(UserRequest request) {
        User exists = findByUsername(Strings.nullToEmpty(request.getUsername().trim()));
        if (exists != null) {
            throw new BusinessException(ErrorCode._10003);
        }
        User user = BeanUtils.convert(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRegistered(System.currentTimeMillis());
        user.insert();
        return user;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheInvalidate(name = "user:", key = "#username")
    public void updatePassword(Long id, String username, String password) {
        User user = User.find.byId(id);
        user.setPassword(passwordEncoder.encode(password));
        user.update();
        redisTemplate.delete(TokenProvider.generateKey(user.getUsername()));
    }

    public User findByEmail(String email) {
        this.testThis();
        return User.find.where().email.eq(email).findOne();
    }

    @Transactional
    public void testThis() {
        User user = new User();
        user.setUsername("TT");
        user.setRealName("bowen");
        user.insert();
        System.out.println(1 / 0);
    }

}
