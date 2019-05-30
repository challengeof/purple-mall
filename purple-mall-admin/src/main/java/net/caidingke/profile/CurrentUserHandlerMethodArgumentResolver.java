package net.caidingke.profile;

import java.security.Principal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.caidingke.business.service.UserService;
import net.caidingke.domain.User;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author bowen
 */
@Component
@NoArgsConstructor
@AllArgsConstructor
public class CurrentUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(CurrentUser.class) != null
                && methodParameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory) {
        if (this.supportsParameter(methodParameter)) {
            Principal principal = webRequest.getUserPrincipal();
            if (principal != null) {
                return userService.findByUsername(principal.getName());
            }
        }
        return null;
    }
}
