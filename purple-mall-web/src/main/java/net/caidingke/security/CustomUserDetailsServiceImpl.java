package net.caidingke.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.caidingke.business.service.CustomerService;
import net.caidingke.domain.Customer;
import org.assertj.core.util.Lists;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author bowen
 */
@Component("userDetailsService")
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final CustomerService customerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(final String username) {
        Customer customer = customerService.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException(
                    "User " + username + " was not found in the "
                            + "database");
        }
        return JwtUser.builder()
                .username(customer.getUsername())
                .password(customer.getPassword())
                .authorities(Lists.newArrayList())
                .enabled(customer.isEnabled())
                .build();

    }
}
