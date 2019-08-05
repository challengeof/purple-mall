package net.caidingke.business.service;

import com.alicp.jetcache.anno.CacheInvalidate;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.google.common.base.Strings;
import io.ebean.annotation.Transactional;
import net.caidingke.aop.cache.RDSCache;
import net.caidingke.business.controller.request.CustomerRequest;
import net.caidingke.business.exception.BizException;
import net.caidingke.business.exception.ErrorCode;
import net.caidingke.common.mapper.BeanUtils;
import net.caidingke.domain.Book;
import net.caidingke.domain.Customer;
import net.caidingke.utils.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author bowen
 */
@Service
public class CustomerService {

    private final PasswordEncoder passwordEncoder;

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public CustomerService(PasswordEncoder passwordEncoder, StringRedisTemplate redisTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
    }

    public Customer findById(Long id) {
        return RDSCache.get(Customer.class, id, Customer.find::byId);
    }

    @Cached(name = "customer:", key = "#username", cacheType = CacheType.BOTH)
    public Customer findByUsername(String username) {
        return Customer.find.where().username.eq(username).findOne();
    }

    @Transactional(rollbackFor = Exception.class)
    public Customer register(CustomerRequest request) {
        Customer exists = findByUsername(Strings.nullToEmpty(request.getUsername().trim()));
        if (exists != null) {
            throw new BizException(ErrorCode._10003);
        }
        Customer customer = BeanUtils.convert(request, Customer.class);
        customer.setPassword(passwordEncoder.encode(request.getPassword()));
        customer.insert();
        return customer;
    }

    @Transactional(rollbackFor = Exception.class)
    @CacheInvalidate(name = "customer:", key = "#username")
    public void updatePassword(Long id, String username, String password) {
        Customer customer = Customer.find.byId(id);
        customer.setPassword(passwordEncoder.encode(password));
        customer.update();
        redisTemplate.delete(TokenProvider.generateKey(customer.getUsername()));
    }

    @org.springframework.transaction.annotation.Transactional
    public void testTransactional() {
        Book book = new Book();
        book.setName("追风筝的孩子");
        book.insert();
        test();
    }

    private void test() {
        Book book = new Book();
        book.setName("算法");
        book.save();
        throw new RuntimeException();
    }
}
