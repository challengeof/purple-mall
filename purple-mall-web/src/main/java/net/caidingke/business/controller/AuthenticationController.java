package net.caidingke.business.controller;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import net.caidingke.base.BasicController;
import net.caidingke.business.exception.AuthException;
import net.caidingke.business.exception.ErrorCode;
import net.caidingke.common.config.PurpleProperties;
import net.caidingke.common.result.Result;
import net.caidingke.security.JwtUser;
import net.caidingke.utils.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bowen
 */
@RestController
public class AuthenticationController extends BasicController {

    private final PurpleProperties purpleProperties;

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    private final UserDetailsService userDetailsService;

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            TokenProvider tokenProvider,
            UserDetailsService userDetailsService,
            StringRedisTemplate stringRedisTemplate, PurpleProperties purpleProperties) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
        this.stringRedisTemplate = stringRedisTemplate;
        this.purpleProperties = purpleProperties;
    }

    @PostMapping(value = "/login")
    public Result<Map<String, String>> createAuthenticationToken(String username, String password)
            throws AuthenticationException {
        authenticate(username, password);
        final String token = tokenProvider.generateToken(username);
        stringRedisTemplate.opsForValue().set(TokenProvider.generateKey(username), token);
        return ok(ImmutableMap.of("token", token));
    }

    @PostMapping(value = "/login/wechat")
    public Result<Map<String, String>> loginByWeChat(String code) {
        return null;
    }

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public Result refreshAndGetAuthenticationToken(HttpServletRequest request) {
        String authToken = request.getHeader(purpleProperties.getAuth().getHeader());
        final String token = authToken.substring(7);
        String username = tokenProvider.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);

        if (tokenProvider.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String refreshedToken = tokenProvider.refreshToken(token);
            stringRedisTemplate.opsForValue().set(TokenProvider.generateKey(username), refreshedToken);
            return ok(ImmutableMap.of("token", refreshedToken));
        }
        return errorThrow(ErrorCode._10007);
    }

    private void authenticate(String username, String password) {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        try {
            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(username, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (DisabledException e) {
            throw new AuthException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthException("Bad credentials!", e);
        }
    }
}
