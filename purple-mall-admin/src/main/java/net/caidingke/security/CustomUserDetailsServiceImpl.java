package net.caidingke.security;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.caidingke.business.exception.BusinessException;
import net.caidingke.business.exception.ErrorCode;
import net.caidingke.business.service.UserService;
import net.caidingke.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    private final UserService userService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(final String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(
                    "User " + username + " was not found in the "
                            + "database");
        }
        if (!user.isEnabled()) {
            throw new BusinessException(ErrorCode._10002);
        }
        List<GrantedAuthority> grantedAuthorities =
                userService.getUserRoles(user.getId()).stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return JwtUser.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(grantedAuthorities)
                .enabled(user.isEnabled())
                .build();

    }
}
