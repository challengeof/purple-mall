package net.caidingke.business.controller;

import javax.servlet.http.HttpServletRequest;
import net.caidingke.base.BasicController;
import net.caidingke.business.controller.request.UserRequest;
import net.caidingke.business.exception.BusinessException;
import net.caidingke.business.exception.ErrorCode;
import net.caidingke.business.service.UserService;
import net.caidingke.common.result.Result;
import net.caidingke.domain.User;
import net.caidingke.utils.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bowen
 */
@RestController
public class UserController extends BasicController {

    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager,
            TokenProvider tokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/findById")
    public Result<User> findById(Long id) {
        User user = userService.findById(id);
        return ok(user);
    }

    @PostMapping("/register")
    public Result<User> register(UserRequest userRequest) {
        User user = userService.register(userRequest);
        return ok(user);
    }

    @PutMapping("/updatePassword")
    public Result updatePassword(HttpServletRequest request, Long id, String oldPassword,
            String password) {
        final String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        String username = tokenProvider.getUsernameFromToken(jwtToken.substring(7));
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, oldPassword));
            if (!authentication.isAuthenticated()) {
                throw new BusinessException(ErrorCode._10005);
            }

        } catch (BadCredentialsException e) {
            throw new BusinessException(ErrorCode._10005);
        }
        userService.updatePassword(id, username, password);
        return ok();
    }

    @GetMapping(value = "/testthis")
    public void testThis() {
        userService.findByEmail("ttt");
    }
}
