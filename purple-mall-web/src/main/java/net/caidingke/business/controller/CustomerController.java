package net.caidingke.business.controller;

import javax.servlet.http.HttpServletRequest;
import net.caidingke.base.BasicController;
import net.caidingke.business.controller.request.CustomerRequest;
import net.caidingke.business.exception.BizException;
import net.caidingke.business.exception.ErrorCode;
import net.caidingke.business.service.CustomerService;
import net.caidingke.common.result.Result;
import net.caidingke.domain.Customer;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bowen
 */
@RestController
@RequestMapping("/customer/")
public class CustomerController extends BasicController {

    private final CustomerService customerService;

    private final AuthenticationManager authenticationManager;

    private final TokenProvider tokenProvider;

    @Autowired
    public CustomerController(CustomerService customerService,
            AuthenticationManager authenticationManager,
            TokenProvider tokenProvider) {
        this.customerService = customerService;
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/findById")
    public Result<Customer> findById(Long id) {
        Customer customer = customerService.findById(id);
        return ok(customer);
    }

    @PostMapping("/register")
    public Result<Customer> register(CustomerRequest userRequest) {
        Customer customer = customerService.register(userRequest);
        return ok(customer);
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
                throw new BizException(ErrorCode._10005);
            }

        } catch (BadCredentialsException e) {
            throw new BizException(ErrorCode._10005);
        }
        customerService.updatePassword(id, username, password);
        return ok();
    }

    @GetMapping("/test")
    public Result testTransactional() {
        customerService.testTransactional();

        return ok();
    }

}
