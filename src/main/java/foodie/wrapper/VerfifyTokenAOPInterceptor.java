package foodie.wrapper;

import com.auth0.jwt.interfaces.Claim;
import foodie.exceptions.UnauthorizedException;
import foodie.util.JwtUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Component
@Aspect()
public class VerfifyTokenAOPInterceptor {

    private static final Logger logger = LogManager.getLogger(VerfifyTokenAOPInterceptor.class.getName());


    @Pointcut("@within(foodie.wrapper.VerifyToken)||@annotation(foodie.wrapper.VerifyToken)")
    public void pointcut() {}

    @Before("pointcut()")
    public void before(JoinPoint point) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        final String token = request.getHeader("Authorization");
        if (token == null) {
            throw new UnauthorizedException();
        }

        Map<String, Claim> userData = JwtUtils.verifyToken(token);
        if (userData == null) {
            throw new UnauthorizedException("Please login!");
        }

        String email = userData.get("email").asString();
        String password = userData.get("password").asString();

        request.setAttribute("password", password);
        request.setAttribute("email", email);

        // TODO: token续期
    }
}
